
package org.drip.xva.dynamics;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * 
 *  This file is part of DRIP, a free-software/open-source library for buy/side financial/trading model
 *  	libraries targeting analysts and developers
 *  	https://lakshmidrip.github.io/DRIP/
 *  
 *  DRIP is composed of four main libraries:
 *  
 *  - DRIP Fixed Income - https://lakshmidrip.github.io/DRIP-Fixed-Income/
 *  - DRIP Asset Allocation - https://lakshmidrip.github.io/DRIP-Asset-Allocation/
 *  - DRIP Numerical Optimizer - https://lakshmidrip.github.io/DRIP-Numerical-Optimizer/
 *  - DRIP Statistical Learning - https://lakshmidrip.github.io/DRIP-Statistical-Learning/
 * 
 *  - DRIP Fixed Income: Library for Instrument/Trading Conventions, Treasury Futures/Options,
 *  	Funding/Forward/Overnight Curves, Multi-Curve Construction/Valuation, Collateral Valuation and XVA
 *  	Metric Generation, Calibration and Hedge Attributions, Statistical Curve Construction, Bond RV
 *  	Metrics, Stochastic Evolution and Option Pricing, Interest Rate Dynamics and Option Pricing, LMM
 *  	Extensions/Calibrations/Greeks, Algorithmic Differentiation, and Asset Backed Models and Analytics.
 * 
 *  - DRIP Asset Allocation: Library for model libraries for MPT framework, Black Litterman Strategy
 *  	Incorporator, Holdings Constraint, and Transaction Costs.
 * 
 *  - DRIP Numerical Optimizer: Library for Numerical Optimization and Spline Functionality.
 * 
 *  - DRIP Statistical Learning: Library for Statistical Evaluation and Machine Learning.
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *   	you may not use this file except in compliance with the License.
 *   
 *  You may obtain a copy of the License at
 *  	http://www.apache.org/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  	distributed under the License is distributed on an "AS IS" BASIS,
 *  	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  
 *  See the License for the specific language governing permissions and
 *  	limitations under the License.
 */

/**
 * PathSimulator drives the Simulation for various Latent States and Exposures. The References are:
 *  
 *  - Burgard, C., and M. Kjaer (2014): PDE Representations of Derivatives with Bilateral Counter-party Risk
 *  	and Funding Costs, Journal of Credit Risk, 7 (3) 1-19.
 *  
 *  - Burgard, C., and M. Kjaer (2014): In the Balance, Risk, 24 (11) 72-75.
 *  
 *  - Albanese, C., and L. Andersen (2014): Accounting for OTC Derivatives: Funding Adjustments and the
 *  	Re-Hypothecation Option, eSSRN, https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2482955.
 *  
 *  - Burgard, C., and M. Kjaer (2017): Derivatives Funding, Netting, and Accounting, eSSRN,
 *  	https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2534011.
 * 
 *  - Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives Pricing, Risk
 *  	21 (2) 97-102.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class PathSimulator
{
	private int _iCount = -1;
	private org.drip.xva.dynamics.GroupSettings _groupSettings = null;
	private org.drip.xva.dynamics.EvolutionControl _evolutionControl = null;
	private org.drip.xva.dynamics.DiffusionSettings _diffusionSettings = null;

	private double[] vertexValueRealization (
		final org.drip.measure.process.DiffusionEvolver deValue,
		final double dblInitialValue,
		final double[] adblRandom)
	{
		int iNumTimeStep = _evolutionControl.numTimeSteps();

		double dblTimeWidth = _evolutionControl.timeWidth();

		double[] adblValue = new double[iNumTimeStep + 1];
		double[] adblTimeWidth = new double[iNumTimeStep];
		org.drip.measure.realization.JumpDiffusionVertex[] aJumpDiffusionVertex = null;

		for (int i = 0; i < iNumTimeStep; ++i)
			adblTimeWidth[i] = dblTimeWidth;

		try {
			aJumpDiffusionVertex = deValue.vertexSequenceReverse (
				new org.drip.measure.realization.JumpDiffusionVertex (
					_evolutionControl.horizonTime(),
					dblInitialValue,
					0.,
					false
				),
				org.drip.measure.realization.JumpDiffusionEdgeUnit.Diffusion (
					adblTimeWidth,
					adblRandom
				),
				adblTimeWidth
			);
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		for (int j = 0; j <= iNumTimeStep; ++j)
			adblValue[j] = aJumpDiffusionVertex[j].value();

		return adblValue;
	}

	private org.drip.xva.universe.MarketPath generateMarketPath (
		final org.drip.analytics.date.JulianDate[] dateVertexArray,
		final org.drip.xva.dynamics.StateEntityRealization initialStateEntityRealization,
		final double[][] aadblRandom)
	{
		int iNumTimeStep = _evolutionControl.numTimeSteps();

		org.drip.xva.universe.MarketVertex[] marketVertexArray = new
			org.drip.xva.universe.MarketVertex[iNumTimeStep + 1];

		org.drip.measure.process.DiffusionEvolver dePortfolioValue =
			_diffusionSettings.portfolioValueEvolver();

		org.drip.measure.process.DiffusionEvolver deOvernightNumeraire =
			_diffusionSettings.overnightNumeraireEvolver();

		org.drip.measure.process.DiffusionEvolver deCSANumeraire = _diffusionSettings.csaNumeraireEvolver();

		org.drip.measure.process.DiffusionEvolver deBankHazardRate =
			_diffusionSettings.bankHazardRateEvolver();

		org.drip.measure.process.DiffusionEvolver deBankRecoveryRate =
			_diffusionSettings.bankRecoveryRateEvolver();

		org.drip.measure.process.DiffusionEvolver deBankFundingSpread =
			_diffusionSettings.bankFundingSpreadEvolver();

		org.drip.measure.process.DiffusionEvolver deCounterPartyHazardRate =
			_diffusionSettings.counterPartyHazardRateEvolver();

		org.drip.measure.process.DiffusionEvolver deCounterPartyRecoveryRate =
			_diffusionSettings.counterPartyRecoveryRateEvolver();

		org.drip.measure.process.DiffusionEvolver deCounterPartyFundingSpread =
			_diffusionSettings.counterPartyFundingSpreadEvolver();

		double[] adblPortfolioValue = vertexValueRealization (
			dePortfolioValue,
			initialStateEntityRealization.portfolioValue(),
			aadblRandom[0]
		);

		if (null == adblPortfolioValue) return null;

		double[] adblOvernightNumeraire = vertexValueRealization (
			deOvernightNumeraire,
			initialStateEntityRealization.overnightNumeraire(),
			aadblRandom[1]
		);

		if (null == adblOvernightNumeraire) return null;

		double[] adblCSANumeraire = vertexValueRealization (
			deCSANumeraire,
			initialStateEntityRealization.csaNumeraire(),
			aadblRandom[2]
		);

		if (null == adblCSANumeraire) return null;

		double[] adblBankHazardRate = vertexValueRealization (
			deBankHazardRate,
			initialStateEntityRealization.bankHazardRate(),
			aadblRandom[3]
		);

		if (null == adblBankHazardRate) return null;

		double[] adblBankRecoveryRate = vertexValueRealization (
			deBankRecoveryRate,
			initialStateEntityRealization.bankRecoveryRate(),
			aadblRandom[4]
		);

		if (null == adblBankRecoveryRate) return null;

		double[] adblBankFundingSpread = vertexValueRealization (
			deBankFundingSpread,
			initialStateEntityRealization.bankFundingSpread(),
			aadblRandom[5]
		);

		if (null == adblBankFundingSpread) return null;

		double[] adblCounterPartyHazardRate = vertexValueRealization (
			deCounterPartyHazardRate,
			initialStateEntityRealization.counterPartyHazardRate(),
			aadblRandom[6]
		);

		if (null == adblCounterPartyHazardRate) return null;

		double[] adblCounterPartyRecoveryRate = vertexValueRealization (
			deCounterPartyRecoveryRate,
			initialStateEntityRealization.counterPartyRecoveryRate(),
			aadblRandom[7]
		);

		if (null == adblCounterPartyRecoveryRate) return null;

		double[] adblCounterPartyFundingSpread = vertexValueRealization (
			deCounterPartyFundingSpread,
			initialStateEntityRealization.counterPartyFundingSpread(),
			aadblRandom[8]
		);

		if (null == adblCounterPartyFundingSpread) return null;

		try
		{
			for (int j = 0; j <= iNumTimeStep; ++j)
			{
				marketVertexArray[j] = new org.drip.xva.universe.MarketVertex (
					dateVertexArray[j],
					java.lang.Double.NaN,
					0 == j ? 0. : 2. * java.lang.Math.log (adblOvernightNumeraire[0] /
						adblOvernightNumeraire[j]) / j,
					new org.drip.xva.universe.NumeraireMarketVertex (
						adblOvernightNumeraire[0],
						adblOvernightNumeraire[j]
					),
					0 == j ? 0. : 2. * java.lang.Math.log (adblCSANumeraire[0] / adblCSANumeraire[j]) / j,
					new org.drip.xva.universe.NumeraireMarketVertex (
						adblCSANumeraire[0],
						adblCSANumeraire[j]
					),
					new org.drip.xva.universe.EntityMarketVertex (
						java.lang.Math.exp (-0.5 * adblBankHazardRate[j] * j),
						adblBankHazardRate[j],
						adblBankRecoveryRate[j],
						adblBankFundingSpread[j],
						new org.drip.xva.universe.NumeraireMarketVertex (
							java.lang.Math.exp (-0.5 * adblBankHazardRate[j] * (1. - adblBankRecoveryRate[j])
								* iNumTimeStep),
							java.lang.Math.exp (-0.5 * adblBankHazardRate[j] * (1. - adblBankRecoveryRate[j])
								* (iNumTimeStep - j))
						),
						java.lang.Double.NaN,
						java.lang.Double.NaN,
						null
					),
					new org.drip.xva.universe.EntityMarketVertex (
						java.lang.Math.exp (-0.5 * adblCounterPartyHazardRate[j] * j),
						adblCounterPartyHazardRate[j],
						adblCounterPartyRecoveryRate[j],
						adblCounterPartyFundingSpread[j],
						new org.drip.xva.universe.NumeraireMarketVertex (
							java.lang.Math.exp (-0.5 * adblCounterPartyHazardRate[j] *
								(1. - adblCounterPartyRecoveryRate[j]) * iNumTimeStep),
							java.lang.Math.exp (-0.5 * adblCounterPartyHazardRate[j] *
								(1. - adblCounterPartyRecoveryRate[j]) * (iNumTimeStep - j))
						),
						java.lang.Double.NaN,
						java.lang.Double.NaN,
						null
					)
				);
			}

			return new org.drip.xva.universe.MarketPath (marketVertexArray);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	private org.drip.xva.hypothecation.CollateralGroupPath[] generateCollateralGroupVertexes (
		final org.drip.analytics.date.JulianDate[] dateVertexArray,
		final org.drip.xva.universe.MarketPath marketPath)
	{
		int iNumTimeStep = _evolutionControl.numTimeSteps();

		org.drip.xva.universe.MarketVertex[] marketVertexes = marketPath.vertexes();

		org.drip.xva.set.CollateralGroupSpecification collateralGroupSpecification =
			_groupSettings.collateralGroupSpecification();

		org.drip.xva.set.CounterPartyGroupSpecification counterPartyGroupSpecification =
			_groupSettings.counterPartyGroupSpecification();

		org.drip.xva.hypothecation.CollateralGroupVertex[] collateralGroupVertexArray = new
			org.drip.xva.hypothecation.CollateralGroupVertex[iNumTimeStep + 1];

		for (int j = 0; j <= iNumTimeStep; ++j)
		{
			try
			{
				collateralGroupVertexArray[j] = new org.drip.xva.hypothecation.AlbaneseAndersenVertex (
					dateVertexArray[j],
					marketVertexes[j].assetNumeraire(),
					0.,
					0 == j ? 0. :
						new org.drip.xva.hypothecation.CollateralAmountEstimator (
							collateralGroupSpecification,
							counterPartyGroupSpecification,
							new org.drip.measure.bridge.BrokenDateInterpolatorLinearT (
								dateVertexArray[j - 1].julian(),
								dateVertexArray[j].julian(),
								marketVertexes[j - 1].assetNumeraire(),
								marketVertexes[j].assetNumeraire()
							),
							java.lang.Double.NaN
						).postingRequirement (dateVertexArray[j])
				);
			}
			catch (java.lang.Exception e)
			{
				e.printStackTrace();

				return null;
			}
		}

		try
		{
			return new org.drip.xva.hypothecation.CollateralGroupPath[]
			{
				new org.drip.xva.hypothecation.CollateralGroupPath (collateralGroupVertexArray)
			};
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	private org.drip.xva.cpty.MonoPathExposureAdjustment singleTrajectory (
		final org.drip.analytics.date.JulianDate[] dateVertexArray,
		final org.drip.xva.dynamics.StateEntityRealization initialStateEntityRealization,
		final double[][] aadblRandom)
	{
		org.drip.xva.universe.MarketPath marketPath = generateMarketPath (
			dateVertexArray,
			initialStateEntityRealization,
			aadblRandom
		);

		if (null == marketPath) return null;

		org.drip.xva.hypothecation.CollateralGroupPath[] collateralGroupPathArray =
			generateCollateralGroupVertexes (
				dateVertexArray,
				marketPath
			);

		try
		{
			return new org.drip.xva.cpty.MonoPathExposureAdjustment (
				new org.drip.xva.strategy.AlbaneseAndersenNettingGroupPath[]
				{
					new org.drip.xva.strategy.AlbaneseAndersenNettingGroupPath (
						collateralGroupPathArray,
						marketPath
					)
				},
				new org.drip.xva.strategy.AlbaneseAndersenFundingGroupPath[]
				{
					new org.drip.xva.strategy.AlbaneseAndersenFundingGroupPath (
						collateralGroupPathArray,
						marketPath
					)
				}
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Retrieve the Path Count
	 * 
	 * @return The Path Count
	 */

	public int count()
	{
		return _iCount;
	}

	/**
	 * Retrieve the Diffusion Settings
	 * 
	 * @return The Diffusion Settings
	 */

	public org.drip.xva.dynamics.DiffusionSettings diffusors()
	{
		return _diffusionSettings;
	}

	/**
	 * Retrieve the Evolution Control
	 * 
	 * @return The Evolution Control
	 */

	public org.drip.xva.dynamics.EvolutionControl evolutionControl()
	{
		return _evolutionControl;
	}

	/**
	 * Simulate the Realized State/Entity Values and their Aggregates over the Paths
	 * 
	 * @param initialStateEntityRealization The Starting State Entity Realization
	 * 
	 * @return The Exposure Adjustment Digest - Simulation Result
	 */

	public org.drip.xva.cpty.ExposureAdjustmentDigest simulate (
		final org.drip.xva.dynamics.StateEntityRealization initialStateEntityRealization)
	{
		int iNumTimeStep = _evolutionControl.numTimeSteps();

		double[][] aadblCorrelation = _diffusionSettings.correlationMatrix();

		org.drip.analytics.date.JulianDate dtSpot = _evolutionControl.spotDate();

		org.drip.analytics.date.JulianDate[] dateVertexArray = new
			org.drip.analytics.date.JulianDate[iNumTimeStep + 1];
		org.drip.xva.cpty.PathExposureAdjustment[] pathExposureAdjustmentArray = new
			org.drip.xva.cpty.PathExposureAdjustment[_iCount];

		for (int j = 0; j <= iNumTimeStep; ++j)
		{
			if (null == (dateVertexArray[j] = dtSpot.addMonths (6 * j))) return null;
		}

		for (int i = 0; i < _iCount; ++i)
		{
			double[][] aadblRandom = org.drip.quant.linearalgebra.Matrix.Transpose (
				org.drip.measure.discrete.SequenceGenerator.GaussianJoint (
					iNumTimeStep,
					aadblCorrelation
				)
			);

			if (null == aadblRandom) return null;

			if (null == (pathExposureAdjustmentArray[i] = singleTrajectory (
				dateVertexArray,
				initialStateEntityRealization,
				aadblRandom)))
				return null;
		}

		try
		{
			return new org.drip.xva.cpty.ExposureAdjustmentAggregator (pathExposureAdjustmentArray).digest();
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
