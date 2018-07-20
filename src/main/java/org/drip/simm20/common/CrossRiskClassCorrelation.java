
package org.drip.simm20.common;

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
 * CrossRiskClassCorrelation contains the Correlation between the Different Risk Classes. The References are:
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

public class CrossRiskClassCorrelation
{

	/**
	 * Correlation between Interest Rate and Credit Qualifying Risk Classes
	 */

	public static final double INTERESTRATE_CREDITQUALIFYING = 0.28;

	/**
	 * Correlation between Interest Rate and Credit Non-Qualifying Risk Classes
	 */

	public static final double INTERESTRATE_CREDITNONQUALIFYING = 0.18;

	/**
	 * Correlation between Interest Rate and Equity Risk Classes
	 */

	public static final double INTERESTRATE_EQUITY = 0.18;

	/**
	 * Correlation between Interest Rate and Commodity Risk Classes
	 */

	public static final double INTERESTRATE_COMMODITY = 0.30;

	/**
	 * Correlation between Interest Rate and FX Risk Classes
	 */

	public static final double INTERESTRATE_FX = 0.22;

	/**
	 * Correlation between Credit Qualifying and Credit Non-Qualifying Risk Classes
	 */

	public static final double CREDITQUALIFYING_CREDITNONQUALIFYING = 0.30;

	/**
	 * Correlation between Credit Qualifying and Equity Risk Classes
	 */

	public static final double CREDITQUALIFYING_EQUITY = 0.66;

	/**
	 * Correlation between Credit Qualifying and Commodity Risk Classes
	 */

	public static final double CREDITQUALIFYING_COMMODITY = 0.46;

	/**
	 * Correlation between Credit Qualifying and FX Risk Classes
	 */

	public static final double CREDITQUALIFYING_FX = 0.27;

	/**
	 * Correlation between Credit Non Qualifying and Equity Risk Classes
	 */

	public static final double CREDITNONQUALIFYING_EQUITY = 0.23;

	/**
	 * Correlation between Credit Non Qualifying and Commodity Risk Classes
	 */

	public static final double CREDITNONQUALIFYING_COMMODITY = 0.25;

	/**
	 * Correlation between Credit Non Qualifying and FX Risk Classes
	 */

	public static final double CREDITNONQUALIFYING_FX = 0.18;

	/**
	 * Correlation between Equity and Commodity Risk Classes
	 */

	public static final double EQUITY_COMMODITY = 0.39;

	/**
	 * Correlation between Equity and FX Risk Classes
	 */

	public static final double EQUITY_FX = 0.24;

	/**
	 * Correlation between Commodity and FX Risk Classes
	 */

	public static final double COMMODITY_FX = 0.32;

	/**
	 * Retrieve the Correlation between Interest Rate and Credit Qualifying Risk Classes
	 * 
	 * @return Correlation between Interest Rate and Credit Qualifying Risk Classes
	 */

	public static final double InterestRate_CreditQualifying()
	{
		return INTERESTRATE_CREDITQUALIFYING;
	}

	/**
	 * Retrieve the Correlation between Interest Rate and Credit Qualifying Risk Classes
	 * 
	 * @return Correlation between Interest Rate and Credit Qualifying Risk Classes
	 */

	public static final double CreditQualifying_InterestRate()
	{
		return INTERESTRATE_CREDITQUALIFYING;
	}

	/**
	 * Retrieve the Correlation between Interest Rate and Credit Non Qualifying Risk Classes
	 * 
	 * @return Correlation between Interest Rate and Credit Non Qualifying Risk Classes
	 */

	public static final double InterestRate_CreditNonQualifying()
	{
		return INTERESTRATE_CREDITNONQUALIFYING;
	}

	/**
	 * Retrieve the Correlation between Interest Rate and Credit Non Qualifying Risk Classes
	 * 
	 * @return Correlation between Interest Rate and Credit Non Qualifying Risk Classes
	 */

	public static final double CreditNonQualifying_InterestRate()
	{
		return INTERESTRATE_CREDITNONQUALIFYING;
	}

	/**
	 * Retrieve the Correlation between Interest Rate and Equity Risk Classes
	 * 
	 * @return Correlation between Interest Rate and Equity Risk Classes
	 */

	public static final double InterestRate_Equity()
	{
		return INTERESTRATE_EQUITY;
	}

	/**
	 * Retrieve the Correlation between Interest Rate and Equity Risk Classes
	 * 
	 * @return Correlation between Interest Rate and Equity Risk Classes
	 */

	public static final double Equity_InterestRate()
	{
		return INTERESTRATE_EQUITY;
	}

	/**
	 * Retrieve the Correlation between Interest Rate and Commodity Risk Classes
	 * 
	 * @return Correlation between Interest Rate and Commodity Risk Classes
	 */

	public static final double InterestRate_Commodity()
	{
		return INTERESTRATE_COMMODITY;
	}

	/**
	 * Retrieve the Correlation between Interest Rate and Commodity Risk Classes
	 * 
	 * @return Correlation between Interest Rate and Commodity Risk Classes
	 */

	public static final double Commodity_InterestRate()
	{
		return INTERESTRATE_COMMODITY;
	}

	/**
	 * Retrieve the Correlation between Interest Rate and FX Risk Classes
	 * 
	 * @return Correlation between Interest Rate and FX Risk Classes
	 */

	public static final double InterestRate_FX()
	{
		return INTERESTRATE_FX;
	}

	/**
	 * Retrieve the Correlation between Interest Rate and FX Risk Classes
	 * 
	 * @return Correlation between Interest Rate and FX Risk Classes
	 */

	public static final double FX_InterestRate()
	{
		return INTERESTRATE_FX;
	}

	/**
	 * Retrieve the Correlation between Credit Qualifying and Credit Non-Qualifying Risk Classes
	 * 
	 * @return Correlation between Credit Qualifying and Credit Non-Qualifying Risk Classes
	 */

	public static final double CreditQualifying_CreditNonQualifying()
	{
		return CREDITQUALIFYING_CREDITNONQUALIFYING;
	}

	/**
	 * Retrieve the Correlation between Credit Qualifying and Credit Non-Qualifying Risk Classes
	 * 
	 * @return Correlation between Credit Qualifying and Credit Non-Qualifying Risk Classes
	 */

	public static final double CreditNonQualifying_CreditQualifying()
	{
		return CREDITQUALIFYING_CREDITNONQUALIFYING;
	}

	/**
	 * Retrieve the Correlation between Credit Qualifying and Equity Risk Classes
	 * 
	 * @return Correlation between Credit Qualifying and Equity Risk Classes
	 */

	public static final double CreditQualifying_Equity()
	{
		return CREDITQUALIFYING_EQUITY;
	}

	/**
	 * Retrieve the Correlation between Credit Qualifying and Equity Risk Classes
	 * 
	 * @return Correlation between Credit Qualifying and Equity Risk Classes
	 */

	public static final double Equity_CreditQualifying()
	{
		return CREDITQUALIFYING_EQUITY;
	}

	/**
	 * Retrieve the Correlation between Credit Qualifying and Commodity Risk Classes
	 * 
	 * @return Correlation between Credit Qualifying and Commodity Risk Classes
	 */

	public static final double CreditQualifying_Commodity()
	{
		return CREDITQUALIFYING_COMMODITY;
	}

	/**
	 * Retrieve the Correlation between Credit Qualifying and Commodity Risk Classes
	 * 
	 * @return Correlation between Credit Qualifying and Commodity Risk Classes
	 */

	public static final double Commodity_CreditQualifying()
	{
		return CREDITQUALIFYING_COMMODITY;
	}

	/**
	 * Retrieve the Correlation between Credit Qualifying and FX Risk Classes
	 * 
	 * @return Correlation between Credit Qualifying and FX Risk Classes
	 */

	public static final double CreditQualifying_FX()
	{
		return CREDITQUALIFYING_FX;
	}

	/**
	 * Retrieve the Correlation between Credit Qualifying and FX Risk Classes
	 * 
	 * @return Correlation between Credit Qualifying and FX Risk Classes
	 */

	public static final double FX_CreditQualifying()
	{
		return CREDITQUALIFYING_FX;
	}

	/**
	 * Retrieve the Correlation between Credit Non Qualifying and Equity Risk Classes
	 * 
	 * @return Correlation between Credit Non Qualifying and Equity Risk Classes
	 */

	public static final double CreditNonQualifying_Equity()
	{
		return CREDITNONQUALIFYING_EQUITY;
	}

	/**
	 * Retrieve the Correlation between Credit Non Qualifying and Equity Risk Classes
	 * 
	 * @return Correlation between Credit Non Qualifying and Equity Risk Classes
	 */

	public static final double Equity_CreditNonQualifying()
	{
		return CREDITNONQUALIFYING_EQUITY;
	}

	/**
	 * Retrieve the Correlation between Credit Non Qualifying and Commodity Risk Classes
	 * 
	 * @return Correlation between Credit Non Qualifying and Commodity Risk Classes
	 */

	public static final double CreditNonQualifying_Commodity()
	{
		return CREDITNONQUALIFYING_COMMODITY;
	}

	/**
	 * Retrieve the Correlation between Credit Non Qualifying and Commodity Risk Classes
	 * 
	 * @return Correlation between Credit Non Qualifying and Commodity Risk Classes
	 */

	public static final double Commodity_CreditNonQualifying()
	{
		return CREDITNONQUALIFYING_COMMODITY;
	}

	/**
	 * Retrieve the Correlation between Credit Non Qualifying and FX Risk Classes
	 * 
	 * @return Correlation between Credit Non Qualifying and FX Risk Classes
	 */

	public static final double CreditNonQualifying_FX()
	{
		return CREDITNONQUALIFYING_FX;
	}

	/**
	 * Retrieve the Correlation between Credit Non Qualifying and FX Risk Classes
	 * 
	 * @return Correlation between Credit Non Qualifying and FX Risk Classes
	 */

	public static final double FX_CreditNonQualifying()
	{
		return CREDITNONQUALIFYING_FX;
	}

	/**
	 * Retrieve the Correlation between Equity and Commodity Risk Classes
	 * 
	 * @return Correlation between Equity and Commodity Risk Classes
	 */

	public static final double Equity_Commodity()
	{
		return EQUITY_COMMODITY;
	}

	/**
	 * Retrieve the Correlation between Equity and Commodity Risk Classes
	 * 
	 * @return Correlation between Equity and Commodity Risk Classes
	 */

	public static final double Commodity_Equity()
	{
		return EQUITY_COMMODITY;
	}

	/**
	 * Retrieve the Correlation between Equity and FX Risk Classes
	 * 
	 * @return Correlation between Equity and FX Risk Classes
	 */

	public static final double Equity_FX()
	{
		return EQUITY_FX;
	}

	/**
	 * Retrieve the Correlation between Equity and FX Risk Classes
	 * 
	 * @return Correlation between Equity and FX Risk Classes
	 */

	public static final double FX_Equity()
	{
		return EQUITY_FX;
	}

	/**
	 * Retrieve the Correlation between Commodity and FX Risk Classes
	 * 
	 * @return Correlation between Commodity and FX Risk Classes
	 */

	public static final double Commodity_FX()
	{
		return COMMODITY_FX;
	}

	/**
	 * Retrieve the Correlation between Commodity and FX Risk Classes
	 * 
	 * @return Correlation between Commodity and FX Risk Classes
	 */

	public static final double FX_Commodity()
	{
		return COMMODITY_FX;
	}
}
