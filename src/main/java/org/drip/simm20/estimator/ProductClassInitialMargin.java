
package org.drip.simm20.estimator;

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
 * ProductClassInitialMargin holds the Initial Margin Estimates for a Single Product Class across the Six
 *  Risk Factors - Interest Rate, Credit Qualifying, Credit Non-Qualifying, Equity, Commodity, and FXA. The
 *  References are:
 *  
 *  - Andersen, L. B. G., M. Pykhtin, and A. Sokol (2017): Credit Exposure in the Presence of Initial Margin,
 *  	https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2806156, eSSRN.
 *  
 *  - Albanese, C., S. Caenazzo, and O. Frankel (2017): Regression Sensitivities for Initial Margin
 *  	Calculations, https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2763488, eSSRN.
 *  
 *  - Anfuso, F., D. Aziz, P. Giltinan, and K. Loukopoulus (2017): A Sound Modeling and Back-testing
 *  	Framework for Forecasting Initial Margin Requirements,
 *  	https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2716279, eSSRN.
 *  
 *  - Caspers, P., P. Giltinan, R. Lichters, and N. Nowaczyk (2017): Forecasting Initial Margin Requirements
 *  	- A Model Evaluation https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2911167, eSSRN.
 *  
 *  - International Swaps and Derivatives Association (2017): SIMM v2.0 Methodology,
 *  	https://www.isda.org/a/oFiDE/isda-simm-v2.pdf.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class ProductClassInitialMargin
{
	private double _ct = java.lang.Double.NaN;
	private double _eq = java.lang.Double.NaN;
	private double _fx = java.lang.Double.NaN;
	private double _ir = java.lang.Double.NaN;
	private double _crq = java.lang.Double.NaN;
	private double _crnq = java.lang.Double.NaN;

	/**
	 * ProductClassInitialMargin Constructor
	 * 
	 * @param ir Interest Rate IM
	 * @param crq Credit (Qualifying) IM
	 * @param crnq Credit (Non-Qualifying) IM
	 * @param eq Equity IM
	 * @param fx FX IM
	 * @param ct Commodity IM
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public ProductClassInitialMargin (
		final double ir,
		final double crq,
		final double crnq,
		final double eq,
		final double fx,
		final double ct)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (_ir = ir) || 0. > _ir ||
			!org.drip.quant.common.NumberUtil.IsValid (_crq = crq) || 0. > _crq ||
			!org.drip.quant.common.NumberUtil.IsValid (_crnq = crnq) || 0. > _crnq ||
			!org.drip.quant.common.NumberUtil.IsValid (_eq = eq) || 0. > _eq ||
			!org.drip.quant.common.NumberUtil.IsValid (_fx = fx) || 0. > _fx ||
			!org.drip.quant.common.NumberUtil.IsValid (_ct = ct) || 0. > _ct)
		{
			throw new java.lang.Exception ("ProductClassInitialMarginConstructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Interest Rate IM
	 * 
	 * @return The Interest Rate IM
	 */

	public double ir()
	{
		return _ir;
	}

	/**
	 * Retrieve the Credit (Qualifying) IM
	 * 
	 * @return The Credit (Qualifying) IM
	 */

	public double crq()
	{
		return _crq;
	}

	/**
	 * Retrieve the Credit (Non-Qualifying) IM
	 * 
	 * @return The Credit (Non-Qualifying) IM
	 */

	public double crnq()
	{
		return _crnq;
	}

	/**
	 * Retrieve the Equity IM
	 * 
	 * @return The Equity IM
	 */

	public double eq()
	{
		return _eq;
	}

	/**
	 * Retrieve the FX IM
	 * 
	 * @return The FX IM
	 */

	public double fx()
	{
		return _fx;
	}

	/**
	 * Retrieve the Commodity IM
	 * 
	 * @return The Commodity IM
	 */

	public double ct()
	{
		return _ct;
	}

	/**
	 * Compute the Total IM
	 * 
	 * @return The Total IM
	 */

	public double total()
	{
		double totalIM = 0.;
		totalIM = totalIM + _ir *_ir;
		totalIM = totalIM + _crq * _crq;
		totalIM = totalIM + _crnq * _crnq;
		totalIM = totalIM + _eq * _eq ;
		totalIM = totalIM + _fx * _fx ;
		totalIM = totalIM + _ct * _ct ;
		totalIM = totalIM + org.drip.simm20.common.CrossRiskClassCorrelation.INTERESTRATE_CREDITQUALIFYING *
			_ir * _crq;
		totalIM = totalIM + org.drip.simm20.common.CrossRiskClassCorrelation.INTERESTRATE_CREDITNONQUALIFYING
			* _ir * _crnq;
		totalIM = totalIM + org.drip.simm20.common.CrossRiskClassCorrelation.INTERESTRATE_EQUITY * _ir * _eq;
		totalIM = totalIM + org.drip.simm20.common.CrossRiskClassCorrelation.INTERESTRATE_FX * _ir * _fx;
		totalIM = totalIM + org.drip.simm20.common.CrossRiskClassCorrelation.INTERESTRATE_COMMODITY * _ir *
			_ct;
		totalIM = totalIM +
			org.drip.simm20.common.CrossRiskClassCorrelation.CREDITQUALIFYING_CREDITNONQUALIFYING * _crq *
				_crnq;
		totalIM = totalIM + org.drip.simm20.common.CrossRiskClassCorrelation.CREDITQUALIFYING_EQUITY * _crq *
			_eq;
		totalIM = totalIM + org.drip.simm20.common.CrossRiskClassCorrelation.CREDITQUALIFYING_FX * _crq *
			_fx;
		totalIM = totalIM + org.drip.simm20.common.CrossRiskClassCorrelation.CREDITQUALIFYING_COMMODITY *
			_crq * _ct;
		totalIM = totalIM + org.drip.simm20.common.CrossRiskClassCorrelation.CREDITNONQUALIFYING_EQUITY *
			_crnq * _eq;
		totalIM = totalIM + org.drip.simm20.common.CrossRiskClassCorrelation.CREDITNONQUALIFYING_FX * _crnq *
			_fx;
		totalIM = totalIM + org.drip.simm20.common.CrossRiskClassCorrelation.CREDITNONQUALIFYING_COMMODITY *
			_crnq * _ct;
		totalIM = totalIM + org.drip.simm20.common.CrossRiskClassCorrelation.EQUITY_FX * _eq * _fx;
		totalIM = totalIM + org.drip.simm20.common.CrossRiskClassCorrelation.EQUITY_COMMODITY * _eq * _ct;
		totalIM = totalIM + org.drip.simm20.common.CrossRiskClassCorrelation.COMMODITY_FX * _fx * _ct;
		return totalIM;
	}
}
