
package org.drip.xva.universe;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
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
 * MarketVertexGenerator generates the Market Realizations at a Trajectory Vertex needed for computing the
 *  Valuation Adjustment. The References are:<br><br>
 *  
 *  - Burgard, C., and M. Kjaer (2013): Funding Strategies, Funding Costs <i>Risk</i> <b>24 (12)</b>
 *  	82-87.<br><br>
 *  
 *  - Burgard, C., and M. Kjaer (2014): PDE Representations of Derivatives with Bilateral Counter-party Risk
 *  	and Funding Costs <i>Journal of Credit Risk</i> <b>7 (3)</b> 1-19.<br><br>
 *  
 *  - Burgard, C., and M. Kjaer (2014): In the Balance <i>Risk</i> <b>24 (11)</b> 72-75.<br><br>
 *  
 *  - Gregory, J. (2009): Being Two-faced over Counter-party Credit Risk <i>Risk</i> <b>20 (2)</b>
 *  	86-90.<br><br>
 * 
 *  - Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives Pricing
 *  	<i>Risk</i> <b>21 (2)</b> 97-102.<br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class MarketVertexGenerator
{

	/**
	 * Asset Numeraire Wanderer Index
	 */

	public static final int ASSET = 0;

	/**
	 * Overnight Index Policy Numeraire Wanderer Index
	 */

	public static final int OVERNIGHT_INDEX = 1;

	/**
	 * Collateral Scheme Numeraire Wanderer Index
	 */

	public static final int COLLATERAL_SCHEME = 2;

	/**
	 * Dealer Hazard Rate Wanderer Index
	 */

	public static final int BANK_HAZARD_RATE = 3;

	/**
	 * Dealer Senior Funding Wanderer Index
	 */

	public static final int DEALER_SENIOR_FUNDING = 4;

	/**
	 * Dealer Senior Recovery Rate Wanderer Index
	 */

	public static final int DEALER_SENIOR_RECOVERY_RATE = 5;

	/**
	 * Dealer Subordinate Funding Wanderer Index
	 */

	public static final int DEALER_SUBORDINATE_FUNDING = 6;

	/**
	 * Dealer Subordinate Recovery Rate Wanderer Index
	 */

	public static final int DEALER_SUBORDINATE_RECOVERY_RATE = 7;

	/**
	 * Client Hazard Rate Wanderer Index
	 */

	public static final int CLIENT_HAZARD_RATE = 8;

	/**
	 * Client Funding Wanderer Index
	 */

	public static final int CLIENT_FUNDING = 9;

	/**
	 * Client Recovery Rate Wanderer Index
	 */

	public static final int CLIENT_RECOVERY_RATE = 10;

	private int _spotDate = -1;
	private double[] _ycfWidth = null;
	private int[] _eventDateArray = null;
	private org.drip.xva.evolver.EntityDynamicsContainer _entityLatentStateEvolver = null;
	private org.drip.xva.evolver.PrimarySecurityDynamicsContainer _tradeablesContainer = null;

	/**
	 * Construct a MarketVertexGenerator Instance from the Spot Date, the Period Tenor, and the Period Count
	 * 
	 * @param spotDate The Spot Date
	 * @param periodTenor The Period Tenor
	 * @param periodCount The Period Count
	 * @param tradeablesContainer The Tradeables Container Instance
	 * @param entityLatentStateEvolver The Dealer/Client Entity Latent State Evolver
	 * 
	 * @return The MarketVertexGenerator Instance from the Spot Date, the Period Tenor, and the Period Count
	 */

	public static final MarketVertexGenerator PeriodHorizon (
		final int spotDate,
		final java.lang.String periodTenor,
		final int periodCount,
		final org.drip.xva.evolver.PrimarySecurityDynamicsContainer tradeablesContainer,
		final org.drip.xva.evolver.EntityDynamicsContainer entityLatentStateEvolver)
	{
		try
		{
			return new MarketVertexGenerator (
				spotDate,
				org.drip.analytics.support.VertexDateBuilder.SpotDatePeriodTenor (
					spotDate,
					periodTenor,
					periodCount
				),
				tradeablesContainer,
				entityLatentStateEvolver
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * MarketVertexGenerator Constructor
	 * 
	 * @param spotDate The Spot Date
	 * @param eventDateArray Array of the Event Dates
	 * @param tradeablesContainer The Tradeables Container Instance
	 * @param entityLatentStateEvolver The Dealer/Client Entity Latent State Evolver
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public MarketVertexGenerator (
		final int spotDate,
		final int[] eventDateArray,
		final org.drip.xva.evolver.PrimarySecurityDynamicsContainer tradeablesContainer,
		final org.drip.xva.evolver.EntityDynamicsContainer entityLatentStateEvolver)
		throws java.lang.Exception
	{
		if (0 >= (_spotDate = spotDate) ||
			null == (_eventDateArray = eventDateArray) ||
			null == (_tradeablesContainer = tradeablesContainer) ||
			null == (_entityLatentStateEvolver = entityLatentStateEvolver))
		{
			throw new java.lang.Exception ("MarketVertexGenerator Constructor => Invalid Inputs");
		}

		int eventVertexCount = _eventDateArray.length;
		_ycfWidth = 0 == eventVertexCount ? null : new double[eventVertexCount];

		if (0 == eventVertexCount ||
			0. >= (_ycfWidth[0] = ((double) (_eventDateArray[0] - _spotDate)) / 365.25))
		{
			throw new java.lang.Exception ("MarketVertexGenerator Constructor => Invalid Inputs");
		}

		for (int eventVertexIndex = 1; eventVertexIndex < eventVertexCount; ++eventVertexIndex)
		{
			if (0. >= (_ycfWidth[eventVertexIndex] = ((double) (_eventDateArray[eventVertexIndex] -
				_eventDateArray[eventVertexIndex - 1])) / 365.25))
			{
				throw new java.lang.Exception ("MarketVertexGenerator Constructor => Invalid Inputs");
			}
		}
	}

	/**
	 * Retrieve the Spot Date
	 * 
	 * @return The Spot Date
	 */

	public int spotDate()
	{
		return _spotDate;
	}

	/**
	 * Retrieve the Entity Latent State Evolver
	 * 
	 * @return The Entity Latent State Evolver
	 */

	public org.drip.xva.evolver.EntityDynamicsContainer entityLatentStateEvolver()
	{
		return _entityLatentStateEvolver;
	}

	/**
	 * Retrieve the Vertex Date Array
	 * 
	 * @return The Vertex Date Array
	 */

	public int[] vertexDates()
	{
		int eventDateCount = _eventDateArray.length;
		int[] vertexDateArray = new int[eventDateCount + 1];
		vertexDateArray[0] = _spotDate;

		for (int i = 0; i < eventDateCount; ++i)
			vertexDateArray[i + 1] = _eventDateArray[i];

		return vertexDateArray;
	}

	/**
	 * Retrieve the Time Width Array
	 * 
	 * @return The Time Width Array
	 */

	public double[] timeWidth()
	{
		return _ycfWidth;
	}

	/**
	 * Generated the Sequence of the Simulated Market Vertexes
	 * 
	 * @param initialMarketVertex The Initial Market Vertex
	 * @param unitEvolverSequence Dual Array of Unit Evolver Sequence
	 * 
	 * @return The Array of the Simulated Market Vertexes
	 */

	public org.drip.xva.universe.MarketVertex[] marketVertex (
		final org.drip.xva.universe.MarketVertex initialMarketVertex,
		final double[][] unitEvolverSequence)
	{
		if (null == initialMarketVertex || null == unitEvolverSequence)
		{
			return null;
		}

		int dimensionCount = unitEvolverSequence.length;

		if (11 != dimensionCount)
		{
			return null;
		}

		org.drip.xva.evolver.PrimarySecurity positionManifest = _tradeablesContainer.position();

		org.drip.xva.evolver.PrimarySecurity dealerSubordinateFundingNumeraire =
			_tradeablesContainer.dealerSubordinateFunding();

		double dealerSurvivalProbabilityExponent = 0.;
		int eventVertexCount = _eventDateArray.length;
		double clientSurvivalProbabilityExponent = 0.;
		boolean positionEvolutionOn = null != positionManifest;
		int terminalDate = _eventDateArray[eventVertexCount - 1];
		org.drip.measure.realization.JumpDiffusionVertex[] csaNumeraireVertexArray = null;
		org.drip.measure.realization.JumpDiffusionVertex[] clientHazardRateVertexArray = null;
		org.drip.measure.realization.JumpDiffusionVertex[] dealerHazardRateVertexArray = null;
		org.drip.measure.realization.JumpDiffusionVertex[] positionManifestVertexArray = null;
		org.drip.measure.realization.JumpDiffusionVertex[] overnightNumeraireVertexArray = null;
		org.drip.measure.realization.JumpDiffusionVertex[] clientRecoveryRateVertexArray = null;
		org.drip.measure.realization.JumpDiffusionVertex[] clientFundingNumeraireVertexArray = null;
		org.drip.measure.realization.JumpDiffusionVertex[] dealerSeniorRecoveryRateVertexArray = null;
		org.drip.measure.realization.JumpDiffusionVertex[] dealerSeniorFundingNumeraireVertexArray = null;
		org.drip.measure.realization.JumpDiffusionVertex[] dealerSubordinateRecoveryRateVertexArray = null;
		org.drip.measure.realization.JumpDiffusionVertex[] dealerSubordinateFundingNumeraireVertexArray =
			null;
		org.drip.xva.universe.MarketVertex[] marketVertexArray = new
			org.drip.xva.universe.MarketVertex[eventVertexCount + 1];

		org.drip.xva.universe.MarketVertexEntity initialDealerVertex = initialMarketVertex.dealer();

		double initialDealerSubordinateRecovery = initialDealerVertex.subordinateRecoveryRate();

		org.drip.xva.universe.MarketVertexEntity initialClientVertex = initialMarketVertex.client();

		double terminalDealerSubordinateFundingNumeraire =
			initialDealerVertex.subordinateFundingReplicator();

		org.drip.measure.process.DiffusionEvolver dealerSubordinateRecoveryRateEvolver =
			_entityLatentStateEvolver.dealerSubordinateRecoveryRateEvolver();

		boolean useSingleDealerBondOnly =
			null == dealerSubordinateFundingNumeraire ||
			null == dealerSubordinateRecoveryRateEvolver ||
			!org.drip.quant.common.NumberUtil.IsValid (terminalDealerSubordinateFundingNumeraire) ||
			!org.drip.quant.common.NumberUtil.IsValid (initialDealerSubordinateRecovery);

		try
		{
			positionManifestVertexArray = !positionEvolutionOn ? null :
				positionManifest.evolver().vertexSequence (
					new org.drip.measure.realization.JumpDiffusionVertex (
						_spotDate,
						initialMarketVertex.positionManifestValue(),
						0.,
						false
					),
					org.drip.measure.realization.JumpDiffusionEdgeUnit.Diffusion (
						_ycfWidth,
						unitEvolverSequence[ASSET]
					),
					_ycfWidth
				);

			overnightNumeraireVertexArray = _tradeablesContainer.overnight().evolver().vertexSequenceReverse (
				new org.drip.measure.realization.JumpDiffusionVertex (
					terminalDate,
					initialMarketVertex.overnightReplicator(),
					0.,
					false
				),
				org.drip.measure.realization.JumpDiffusionEdgeUnit.Diffusion (
					_ycfWidth,
					unitEvolverSequence[OVERNIGHT_INDEX]
				),
				_ycfWidth
			);

			csaNumeraireVertexArray = _tradeablesContainer.csa().evolver().vertexSequenceReverse (
				new org.drip.measure.realization.JumpDiffusionVertex (
					terminalDate,
					initialMarketVertex.csaReplicator(),
					0.,
					false
				),
				org.drip.measure.realization.JumpDiffusionEdgeUnit.Diffusion (
					_ycfWidth,
					unitEvolverSequence[COLLATERAL_SCHEME]
				),
			_ycfWidth);

			dealerHazardRateVertexArray = _entityLatentStateEvolver.dealerHazardRateEvolver().vertexSequence (
				new org.drip.measure.realization.JumpDiffusionVertex (
					_spotDate,
					initialDealerVertex.hazardRate(),
					0.,
					false
				),
				org.drip.measure.realization.JumpDiffusionEdgeUnit.Diffusion (
					_ycfWidth,
					unitEvolverSequence[BANK_HAZARD_RATE]
				),
				_ycfWidth
			);

			dealerSeniorFundingNumeraireVertexArray =
				_tradeablesContainer.dealerSubordinateFunding().evolver().vertexSequenceReverse (
					new org.drip.measure.realization.JumpDiffusionVertex (
						terminalDate,
						initialDealerVertex.seniorFundingReplicator(),
						0.,
						false
					),
					org.drip.measure.realization.JumpDiffusionEdgeUnit.Diffusion (
						_ycfWidth,
						unitEvolverSequence[DEALER_SENIOR_FUNDING]
					),
					_ycfWidth
				);

			dealerSeniorRecoveryRateVertexArray =
				_entityLatentStateEvolver.dealerSeniorRecoveryRateEvolver().vertexSequence (
					new org.drip.measure.realization.JumpDiffusionVertex (
						_spotDate,
						initialDealerVertex.seniorRecoveryRate(),
						0.,
						false
					),
					org.drip.measure.realization.JumpDiffusionEdgeUnit.Diffusion (
						_ycfWidth,
						unitEvolverSequence[DEALER_SENIOR_RECOVERY_RATE]
					),
					_ycfWidth
				);

			dealerSubordinateFundingNumeraireVertexArray = useSingleDealerBondOnly ? null :
				dealerSubordinateFundingNumeraire.evolver().vertexSequenceReverse (
					new org.drip.measure.realization.JumpDiffusionVertex (
						terminalDate,
						terminalDealerSubordinateFundingNumeraire,
						0.,
						false
					),
					org.drip.measure.realization.JumpDiffusionEdgeUnit.Diffusion (
						_ycfWidth,
						unitEvolverSequence[DEALER_SUBORDINATE_FUNDING]
					),
					_ycfWidth
				);

			dealerSubordinateRecoveryRateVertexArray = useSingleDealerBondOnly ? null :
				dealerSubordinateRecoveryRateEvolver.vertexSequence (
					new org.drip.measure.realization.JumpDiffusionVertex (
						_spotDate,
						initialDealerSubordinateRecovery,
						0.,
						false
					),
					org.drip.measure.realization.JumpDiffusionEdgeUnit.Diffusion (
						_ycfWidth,
						unitEvolverSequence[DEALER_SUBORDINATE_RECOVERY_RATE]
					),
					_ycfWidth
				);

			clientHazardRateVertexArray =
				_entityLatentStateEvolver.clientHazardRateEvolver().vertexSequence (
					new org.drip.measure.realization.JumpDiffusionVertex (
						_spotDate,
						initialClientVertex.hazardRate(),
						0.,
						false
					),
					org.drip.measure.realization.JumpDiffusionEdgeUnit.Diffusion (
						_ycfWidth,
						unitEvolverSequence[CLIENT_HAZARD_RATE]
					),
					_ycfWidth
				);

			clientFundingNumeraireVertexArray =
				_tradeablesContainer.clientFunding().evolver().vertexSequenceReverse (
					new org.drip.measure.realization.JumpDiffusionVertex (
						terminalDate,
						initialClientVertex.seniorFundingReplicator(),
						0.,
						false
					),
					org.drip.measure.realization.JumpDiffusionEdgeUnit.Diffusion (
						_ycfWidth,
						unitEvolverSequence[CLIENT_FUNDING]
					),
					_ycfWidth
				);

			clientRecoveryRateVertexArray =
				_entityLatentStateEvolver.clientRecoveryRateEvolver().vertexSequence (
					new org.drip.measure.realization.JumpDiffusionVertex (
						_spotDate,
						initialClientVertex.seniorRecoveryRate(),
						0.,
						false
					),
					org.drip.measure.realization.JumpDiffusionEdgeUnit.Diffusion (
						_ycfWidth,
						unitEvolverSequence[CLIENT_RECOVERY_RATE]
					),
					_ycfWidth
				);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();

			return null;
		}

		double initialCSANumeraire = csaNumeraireVertexArray[0].value();

		double initialOvernightNumeraire = overnightNumeraireVertexArray[0].value();

		double initialDealerSeniorFundingNumeraire = dealerSeniorFundingNumeraireVertexArray[0].value();

		double initialDealerSubordinateFundingNumeraire = useSingleDealerBondOnly ? java.lang.Double.NaN :
			dealerSubordinateFundingNumeraireVertexArray[0].value();

		double initialClientFundingNumeraire = clientFundingNumeraireVertexArray[0].value();

		double csaNumeraireStart = initialCSANumeraire;
		double overnightIndexNumeraireStart = initialOvernightNumeraire;
		double clientFundingNumeraireStart = initialClientFundingNumeraire;
		double dealerSeniorFundingNumeraireStart = initialDealerSeniorFundingNumeraire;
		double dealerSubordinateFundingNumeraireStart = initialDealerSubordinateFundingNumeraire;

		for (int eventVertexIndex = 1; eventVertexIndex <= eventVertexCount; ++eventVertexIndex)
		{
			double dealerHazardRate = dealerHazardRateVertexArray[eventVertexIndex].value();

			double csaNumeraireFinish =  csaNumeraireVertexArray[eventVertexIndex].value();

			double overnightNumeraireFinish = overnightNumeraireVertexArray[eventVertexIndex].value();

			double clientHazardRateFinish = clientHazardRateVertexArray[eventVertexIndex].value();

			double dealerSeniorFundingNumeraireFinish =
				dealerSeniorFundingNumeraireVertexArray[eventVertexIndex].value();

			double dealerSubordinateFundingNumeraireFinish = useSingleDealerBondOnly ? java.lang.Double.NaN :
				dealerSubordinateFundingNumeraireVertexArray[eventVertexIndex].value();

			double clientFundingNumeraireFinish =
				clientFundingNumeraireVertexArray[eventVertexIndex].value();

			double timeWidth = _ycfWidth[eventVertexIndex - 1];
			double timeWidthReciprocal = 1. / timeWidth;
			dealerSurvivalProbabilityExponent += dealerHazardRate * timeWidth;
			clientSurvivalProbabilityExponent += clientHazardRateFinish * timeWidth;

			double overnightRate = timeWidthReciprocal * java.lang.Math.log
				(overnightNumeraireFinish / initialOvernightNumeraire);

			try
			{
				org.drip.xva.universe.MarketVertexEntity dealerMarketVertex = new
					org.drip.xva.universe.MarketVertexEntity (
						java.lang.Math.exp (-1. * dealerSurvivalProbabilityExponent),
						dealerHazardRate,
						dealerSeniorRecoveryRateVertexArray[eventVertexIndex].value(),
						timeWidthReciprocal * java.lang.Math.log (dealerSeniorFundingNumeraireFinish /
							initialDealerSeniorFundingNumeraire) - overnightRate,
						dealerSeniorFundingNumeraireFinish,
						useSingleDealerBondOnly ? java.lang.Double.NaN :
							dealerSubordinateRecoveryRateVertexArray[eventVertexIndex].value(),
						useSingleDealerBondOnly ? java.lang.Double.NaN : timeWidthReciprocal *
							java.lang.Math.log (dealerSubordinateFundingNumeraireFinish /
								initialDealerSubordinateFundingNumeraire) - overnightRate,
						dealerSubordinateFundingNumeraireFinish
					);

				org.drip.xva.universe.MarketVertexEntity clientMarketVertex = new
					org.drip.xva.universe.MarketVertexEntity (
						java.lang.Math.exp (-1. * clientSurvivalProbabilityExponent),
						clientHazardRateFinish,
						clientRecoveryRateVertexArray[eventVertexIndex].value(),
						timeWidthReciprocal * java.lang.Math.log (clientFundingNumeraireFinish /
							initialClientFundingNumeraire) - overnightRate,
						clientFundingNumeraireFinish,
						java.lang.Double.NaN,
						java.lang.Double.NaN,
						java.lang.Double.NaN
					);

				marketVertexArray[eventVertexIndex] = new org.drip.xva.universe.MarketVertex (
					new org.drip.analytics.date.JulianDate (_eventDateArray[eventVertexIndex - 1]),
					positionEvolutionOn ? positionManifestVertexArray[eventVertexIndex].value() :
						java.lang.Double.NaN,
					overnightRate,
					overnightNumeraireFinish,
					timeWidthReciprocal * java.lang.Math.log (csaNumeraireFinish / initialCSANumeraire) -
						overnightRate,
					csaNumeraireFinish,
					dealerMarketVertex,
					clientMarketVertex
				);
			}
			catch (java.lang.Exception e)
			{
				e.printStackTrace();

				return null;
			}

			initialCSANumeraire = csaNumeraireFinish;
			initialOvernightNumeraire = overnightNumeraireFinish;
			initialClientFundingNumeraire = clientFundingNumeraireFinish;
			initialDealerSeniorFundingNumeraire = dealerSeniorFundingNumeraireFinish;
			initialDealerSubordinateFundingNumeraire = dealerSubordinateFundingNumeraireFinish;
		}

		try
		{
			marketVertexArray[0] = new org.drip.xva.universe.MarketVertex (
				initialMarketVertex.anchorDate(),
				initialMarketVertex.positionManifestValue(),
				initialMarketVertex.overnightRate(),
				overnightIndexNumeraireStart,
				marketVertexArray[1].csaRate(),
				csaNumeraireStart,
				new org.drip.xva.universe.MarketVertexEntity (
					initialDealerVertex.survivalProbability(),
					initialDealerVertex.hazardRate(),
					initialDealerVertex.seniorRecoveryRate(),
					marketVertexArray[1].dealer().seniorFundingSpread(),
					dealerSeniorFundingNumeraireStart,
					initialDealerVertex.subordinateRecoveryRate(),
					marketVertexArray[1].dealer().subordinateFundingSpread(),
					dealerSubordinateFundingNumeraireStart
				),
				new org.drip.xva.universe.MarketVertexEntity (
					initialClientVertex.survivalProbability(),
					initialClientVertex.hazardRate(),
					initialClientVertex.seniorRecoveryRate(),
					marketVertexArray[1].client().seniorFundingSpread(),
					clientFundingNumeraireStart,
					java.lang.Double.NaN,
					java.lang.Double.NaN,
					java.lang.Double.NaN
				)
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();

			return null;
		}

		return marketVertexArray;
	}
}
