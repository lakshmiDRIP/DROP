
package org.drip.xva.csadynamics;

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
 * FundingBasisEvolver implements a Two Factor Stochastic Funding Model Evolver with a Log Normal Forward
 * 	Process and a Mean Reverting Diffusion Process for the Funding Spread. The References are:
 *  
 *  - Antonov, A., and M. Arneguy (2009): Analytical Formulas for Pricing CMS Products in the LIBOR Market
 *  	Model with Stochastic Volatility, https://papers.ssrn.com/sol3/Papers.cfm?abstract_id=1352606, eSSRN.
 *  
 *  - Burgard, C., and M. Kjaer (2009): Modeling and successful Management of Credit Counter-party Risk of
 *  	Derivative Portfolios, ICBI Conference, Rome.
 *  
 *  - Gregory, J. (2009): Being Two-faced over Counter-party Credit Risk, Risk 20 (2) 86-90.
 *  
 *  - Johannes, M., and S. Sundaresan (2007): Pricing Collateralized Swaps, Journal of Finance 62 383-410.
 * 
 *  - Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives Pricing, Risk
 *  	21 (2) 97-102.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class FundingBasisEvolver
{
	private double _correlation = java.lang.Double.NaN;
	private org.drip.measure.dynamics.DiffusionEvaluatorLogarithmic _underlyingEvolver = null;
	private org.drip.measure.dynamics.DiffusionEvaluatorMeanReversion _fundingSpreadEvolver = null;

	/**
	 * FundingBasisEvolver Constructor
	 * 
	 * @param underlyingEvolver The Underlying Diffusion Evaluator
	 * @param fundingSpreadEvolver The Funding Spread Diffusion Evaluator
	 * @param correlation Correlation between the Underlying and the Funding Spread Processes
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public FundingBasisEvolver (
		final org.drip.measure.dynamics.DiffusionEvaluatorLogarithmic underlyingEvolver,
		final org.drip.measure.dynamics.DiffusionEvaluatorMeanReversion fundingSpreadEvolver,
		final double correlation)
		throws java.lang.Exception
	{
		if (null == (_underlyingEvolver = underlyingEvolver) || null == (_fundingSpreadEvolver =
			fundingSpreadEvolver) ||
			!org.drip.quant.common.NumberUtil.IsValid (_correlation = correlation) ||
			1. < _correlation || -1. > _correlation)
		{
			throw new java.lang.Exception ("FundingBasisEvolver Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Underlying Diffusion Evaluator
	 * 
	 * @return The Underlying Diffusion Evaluator
	 */

	public org.drip.measure.dynamics.DiffusionEvaluatorLogarithmic underlyingEvolver()
	{
		return _underlyingEvolver;
	}

	/**
	 * Retrieve the Funding Spread Diffusion Evaluator
	 * 
	 * @return The Funding Spread Diffusion Evaluator
	 */

	public org.drip.measure.dynamics.DiffusionEvaluatorMeanReversion fundingSpreadEvolver()
	{
		return _fundingSpreadEvolver;
	}

	/**
	 * Retrieve the Correlation between the Underlying and the Funding Spread Processes
	 * 
	 * @return The Correlation between the Underlying and the Funding Spread Processes
	 */

	public double underlyingFundingSpreadCorrelation()
	{
		return _correlation;
	}

	/**
	 * Generate the CSA Forward Diffusion Process
	 * 
	 * @return The CSA Forward Diffusion Process
	 */

	public org.drip.measure.process.DiffusionEvolver csaForwardProcess()
	{
		try {
			org.drip.measure.dynamics.LocalEvaluator driftEvolver = new
				org.drip.measure.dynamics.LocalEvaluator()
			{
				@Override public double value (
					final org.drip.measure.realization.JumpDiffusionVertex vertex)
					throws java.lang.Exception
				{
					return 0.;
				}
			};

			org.drip.measure.dynamics.LocalEvaluator volatilityEvolver = new
				org.drip.measure.dynamics.LocalEvaluator()
			{
				@Override public double value (
					final org.drip.measure.realization.JumpDiffusionVertex vertex)
					throws java.lang.Exception
				{
					if (null == vertex)
						throw new java.lang.Exception
							("FundingBasisEvolver::CSAForwardVolatility::Evaluator::value => Invalid Inputs");

					return vertex.value() * _underlyingEvolver.volatilityValue();
				}
			};

			return new org.drip.measure.process.DiffusionEvolver (new
				org.drip.measure.dynamics.DiffusionEvaluator (
					driftEvolver,
					volatilityEvolver
				)
			);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the Funding Numeraire Diffusion Process
	 * 
	 * @param tenor The Tenor of the Underlying Forward
	 * 
	 * @return The Funding Numeraire Diffusion Process
	 */

	public org.drip.measure.process.DiffusionEvolver fundingNumeraireProcess (
		final java.lang.String tenor)
	{
		try {
			double meanReversionSpeed = _fundingSpreadEvolver.meanReversionRate();

			double b = org.drip.analytics.support.Helper.TenorToYearFraction (tenor);

			if (0. != meanReversionSpeed)
				b = (1. - java.lang.Math.exp (-1. * meanReversionSpeed * b)) /
					meanReversionSpeed;

			final double dblPiterbarg2010BFactor = b;

			org.drip.measure.dynamics.LocalEvaluator leDrift = new org.drip.measure.dynamics.LocalEvaluator()
			{
				@Override public double value (
					final org.drip.measure.realization.JumpDiffusionVertex jdv)
					throws java.lang.Exception
				{
					return 0.;
				}
			};

			org.drip.measure.dynamics.LocalEvaluator leVolatility = new
				org.drip.measure.dynamics.LocalEvaluator() {
				@Override public double value (
					final org.drip.measure.realization.JumpDiffusionVertex jdv)
					throws java.lang.Exception
				{
					if (null == jdv)
						throw new java.lang.Exception
							("FundingBasisEvolver::CSAFundingNumeraireVolatility::Evaluator::value => Invalid Inputs");

					return -1. * jdv.value() * dblPiterbarg2010BFactor * _fundingSpreadEvolver.volatilityValue();
				}
			};

			return new org.drip.measure.process.DiffusionEvolver (new
				org.drip.measure.dynamics.DiffusionEvaluator (leDrift, leVolatility));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the Funding Spread Numeraire Diffusion Process
	 * 
	 * @param tenor The Tenor of the Underlying Forward
	 * 
	 * @return The Funding Spread Numeraire Diffusion Process
	 */

	public org.drip.measure.process.DiffusionEvolver fundingSpreadNumeraireProcess (
		final java.lang.String tenor)
	{
		try {
			double meanReversionSpeed = _fundingSpreadEvolver.meanReversionRate();

			double b = org.drip.analytics.support.Helper.TenorToYearFraction (tenor);

			if (0. != meanReversionSpeed)
				b = (1. - java.lang.Math.exp (-1. * meanReversionSpeed * b)) /
					meanReversionSpeed;

			final double dblPiterbarg2010BFactor = b;

			org.drip.measure.dynamics.LocalEvaluator leDrift = new org.drip.measure.dynamics.LocalEvaluator()
			{
				@Override public double value (
					final org.drip.measure.realization.JumpDiffusionVertex jdv)
					throws java.lang.Exception
				{
					return 0.;
				}
			};

			org.drip.measure.dynamics.LocalEvaluator leVolatility = new
				org.drip.measure.dynamics.LocalEvaluator() {
				@Override public double value (
					final org.drip.measure.realization.JumpDiffusionVertex jdv)
					throws java.lang.Exception
				{
					if (null == jdv)
						throw new java.lang.Exception
							("FundingBasisEvolver::CSAFundingSpreadNumeraireVolatility::Evaluator::value => Invalid Inputs");

					return -1. * jdv.value() * dblPiterbarg2010BFactor * _fundingSpreadEvolver.volatilityValue();
				}
			};

			return new org.drip.measure.process.DiffusionEvolver (new
				org.drip.measure.dynamics.DiffusionEvaluator (leDrift, leVolatility));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Compute the CSA vs. No CSA Forward Ratio
	 * 
	 * @param tenor The Tenor of the Underlying Forward
	 * 
	 * @return The CSA vs. No CSA Forward Ratio
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double CSANoCSARatio (
		final java.lang.String tenor)
		throws java.lang.Exception
	{
		double underlyingVolatility = _underlyingEvolver.volatilityValue();

		double meanReversionSpeed = _fundingSpreadEvolver.meanReversionRate();

		double fundingSpreadVolatility = _fundingSpreadEvolver.volatilityValue();

		double maturity = org.drip.analytics.support.Helper.TenorToYearFraction (tenor);

		if (0. == meanReversionSpeed)
		{
			return java.lang.Math.exp (-0.5 * _correlation * underlyingVolatility *
				fundingSpreadVolatility * maturity * maturity);
		}

		double b = (1. - java.lang.Math.exp (-1. * meanReversionSpeed * maturity)) /
			meanReversionSpeed;

		return java.lang.Math.exp (-1. * _correlation * underlyingVolatility *
			fundingSpreadVolatility * (maturity - b) / meanReversionSpeed);
	}
}
