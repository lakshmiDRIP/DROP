
package org.drip.simm.estimator;

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
 * ProductClassMargin holds the Initial Margin Estimates for a Single Product Class across the Six Risk
 *  Factors - Interest Rate, Credit Qualifying, Credit Non-Qualifying, Equity, Commodity, and FX. The
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

public class ProductClassMargin
{
	private org.drip.simm.margin.RiskClassAggregate _fxRiskClassAggregate = null;
	private org.drip.simm.margin.RiskClassAggregateIR _irRiskClassAggregate = null;
	private org.drip.simm.margin.RiskClassAggregate _equityRiskClassAggregate = null;
	private org.drip.simm.margin.RiskClassAggregate _commodityRiskClassAggregate = null;
	private org.drip.simm.margin.RiskClassAggregateCR _creditQualifyingRiskClassAggregate = null;
	private org.drip.simm.margin.RiskClassAggregateCR _creditNonQualifyingRiskClassAggregate = null;

	/**
	 * ProductClassMargin Constructor
	 * 
	 * @param irRiskClassAggregate IR Risk Class Aggregate
	 * @param creditQualifyingRiskClassAggregate Credit Qualifying Risk Class Aggregate
	 * @param creditNonQualifyingRiskClassAggregate Credit Non-Qualifying Risk Class Aggregate
	 * @param equityRiskClassAggregate Equity Risk Class Aggregate
	 * @param fxRiskClassAggregate FX Risk Class Aggregate
	 * @param commodityRiskClassAggregate Commodity Risk Class Aggregate
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public ProductClassMargin (
		final org.drip.simm.margin.RiskClassAggregateIR irRiskClassAggregate,
		final org.drip.simm.margin.RiskClassAggregateCR creditQualifyingRiskClassAggregate,
		final org.drip.simm.margin.RiskClassAggregateCR creditNonQualifyingRiskClassAggregate,
		final org.drip.simm.margin.RiskClassAggregate equityRiskClassAggregate,
		final org.drip.simm.margin.RiskClassAggregate fxRiskClassAggregate,
		final org.drip.simm.margin.RiskClassAggregate commodityRiskClassAggregate)
		throws java.lang.Exception
	{
		_irRiskClassAggregate = irRiskClassAggregate;
		_fxRiskClassAggregate = fxRiskClassAggregate;
		_equityRiskClassAggregate = equityRiskClassAggregate;
		_commodityRiskClassAggregate = commodityRiskClassAggregate;
		_creditQualifyingRiskClassAggregate = creditQualifyingRiskClassAggregate;
		_creditNonQualifyingRiskClassAggregate = creditNonQualifyingRiskClassAggregate;

		if ((null == _equityRiskClassAggregate &&
			null == _commodityRiskClassAggregate &&
			null == _fxRiskClassAggregate &&
			null == _irRiskClassAggregate &&
			null == _creditQualifyingRiskClassAggregate &&
			null == _creditNonQualifyingRiskClassAggregate))
		{
			throw new java.lang.Exception ("ProductClassMargin => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Interest Rate Risk Class Aggregate
	 * 
	 * @return The Interest Rate Risk Class Aggregate
	 */

	public org.drip.simm.margin.RiskClassAggregateIR irRiskClassAggregate()
	{
		return _irRiskClassAggregate;
	}

	/**
	 * Retrieve the Credit Qualifying Risk Class Aggregate
	 * 
	 * @return The Credit Qualifying Risk Class Aggregate
	 */

	public org.drip.simm.margin.RiskClassAggregateCR creditQualifyingRiskClassAggregate()
	{
		return _creditQualifyingRiskClassAggregate;
	}

	/**
	 * Retrieve the Credit Non-Qualifying Risk Class Aggregate
	 * 
	 * @return The Credit Non-Qualifying Risk Class Aggregate
	 */

	public org.drip.simm.margin.RiskClassAggregateCR creditNonQualifyingRiskClassAggregate()
	{
		return _creditNonQualifyingRiskClassAggregate;
	}

	/**
	 * Retrieve the Equity Risk Class Aggregate
	 * 
	 * @return The Equity Risk Class Aggregate
	 */

	public org.drip.simm.margin.RiskClassAggregate equityRiskClassAggregate()
	{
		return _equityRiskClassAggregate;
	}

	/**
	 * Retrieve the FX Risk Class Aggregate
	 * 
	 * @return The FX Risk Class Aggregate
	 */

	public org.drip.simm.margin.RiskClassAggregate fxRiskClassAggregate()
	{
		return _fxRiskClassAggregate;
	}

	/**
	 * Retrieve the Commodity Risk Class Aggregate
	 * 
	 * @return The Commodity Risk Class Aggregate
	 */

	public org.drip.simm.margin.RiskClassAggregate commodityRiskClassAggregate()
	{
		return _commodityRiskClassAggregate;
	}

	/**
	 * Compute the Total IM
	 * 
	 * @param labelCorrelation Cross Risk Class Label Correlation
	 * 
	 * @return The Total IM
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double total (
		final org.drip.measure.stochastic.LabelCorrelation labelCorrelation)
		throws java.lang.Exception
	{
		if (null == labelCorrelation)
		{
			throw new java.lang.Exception ("ProductClassMargin::total => Invalid Inputs");
		}

		double irIM = null == _irRiskClassAggregate ? 0. : _irRiskClassAggregate.margin();

		double fxIM = null == _fxRiskClassAggregate ? 0. : _fxRiskClassAggregate.margin();

		double equityIM = null == _equityRiskClassAggregate ? 0. : _equityRiskClassAggregate.margin();

		double commodityIM = null == _commodityRiskClassAggregate ? 0. :
			_commodityRiskClassAggregate.margin();

		double creditQualifyingIM = null == _creditQualifyingRiskClassAggregate ? 0. :
			_creditQualifyingRiskClassAggregate.margin();

		double creditNonQualifyingIM = null == _creditNonQualifyingRiskClassAggregate ? 0. :
			_creditNonQualifyingRiskClassAggregate.margin();

		double totalIM = 0.;
		totalIM = totalIM + irIM *irIM;
		totalIM = totalIM + creditQualifyingIM * creditQualifyingIM;
		totalIM = totalIM + creditNonQualifyingIM * creditNonQualifyingIM;
		totalIM = totalIM + equityIM * equityIM;
		totalIM = totalIM + fxIM * fxIM;
		totalIM = totalIM + commodityIM * commodityIM;

		totalIM = totalIM + labelCorrelation.entry (
			org.drip.simm.common.Chargram.IR,
			org.drip.simm.common.Chargram.CRQ
		) * irIM * creditQualifyingIM;

		totalIM = totalIM + labelCorrelation.entry (
			org.drip.simm.common.Chargram.IR,
			org.drip.simm.common.Chargram.CRNQ
		) * irIM * creditNonQualifyingIM;

		totalIM = totalIM + labelCorrelation.entry (
			org.drip.simm.common.Chargram.IR,
			org.drip.simm.common.Chargram.EQ
		) * irIM * equityIM;

		totalIM = totalIM + labelCorrelation.entry (
			org.drip.simm.common.Chargram.IR,
			org.drip.simm.common.Chargram.FX
		) * irIM * fxIM;

		totalIM = totalIM + labelCorrelation.entry (
			org.drip.simm.common.Chargram.IR,
			org.drip.simm.common.Chargram.CT
		) * irIM * commodityIM;

		totalIM = totalIM + labelCorrelation.entry (
			org.drip.simm.common.Chargram.CRQ,
			org.drip.simm.common.Chargram.CRNQ
		) * creditQualifyingIM * creditNonQualifyingIM;

		totalIM = totalIM + labelCorrelation.entry (
			org.drip.simm.common.Chargram.CRQ,
			org.drip.simm.common.Chargram.EQ
		) * creditQualifyingIM * equityIM;

		totalIM = totalIM + labelCorrelation.entry (
			org.drip.simm.common.Chargram.CRQ,
			org.drip.simm.common.Chargram.FX
		) * creditQualifyingIM * fxIM;

		totalIM = totalIM + labelCorrelation.entry (
			org.drip.simm.common.Chargram.CRQ,
			org.drip.simm.common.Chargram.CT
		) * creditQualifyingIM * commodityIM;

		totalIM = totalIM + labelCorrelation.entry (
			org.drip.simm.common.Chargram.CRNQ,
			org.drip.simm.common.Chargram.EQ
		) * creditNonQualifyingIM * equityIM;

		totalIM = totalIM + labelCorrelation.entry (
			org.drip.simm.common.Chargram.CRNQ,
			org.drip.simm.common.Chargram.FX
		) * creditNonQualifyingIM * fxIM;

		totalIM = totalIM + labelCorrelation.entry (
			org.drip.simm.common.Chargram.CRNQ,
			org.drip.simm.common.Chargram.CT
		) * creditNonQualifyingIM * commodityIM;

		totalIM = totalIM + labelCorrelation.entry (
			org.drip.simm.common.Chargram.EQ,
			org.drip.simm.common.Chargram.FX
		) * equityIM * fxIM;

		totalIM = totalIM + labelCorrelation.entry (
			org.drip.simm.common.Chargram.EQ,
			org.drip.simm.common.Chargram.CT
		) * equityIM * commodityIM;

		totalIM = totalIM + labelCorrelation.entry (
			org.drip.simm.common.Chargram.FX,
			org.drip.simm.common.Chargram.CT
		) * fxIM * commodityIM;

		return java.lang.Math.sqrt (totalIM);
	}
}
