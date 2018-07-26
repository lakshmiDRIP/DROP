
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

	public static final double IR_CRQ = 0.28;

	/**
	 * Correlation between Interest Rate and Credit Non-Qualifying Risk Classes
	 */

	public static final double IR_CRNQ = 0.18;

	/**
	 * Correlation between Interest Rate and Equity Risk Classes
	 */

	public static final double IR_EQ = 0.18;

	/**
	 * Correlation between Interest Rate and Commodity Risk Classes
	 */

	public static final double IR_CT = 0.30;

	/**
	 * Correlation between Interest Rate and FX Risk Classes
	 */

	public static final double IR_FX = 0.22;

	/**
	 * Correlation between Credit Qualifying and Credit Non-Qualifying Risk Classes
	 */

	public static final double CRQ_CRNQ = 0.30;

	/**
	 * Correlation between Credit Qualifying and Equity Risk Classes
	 */

	public static final double CRQ_EQ = 0.66;

	/**
	 * Correlation between Credit Qualifying and Commodity Risk Classes
	 */

	public static final double CRQ_CT = 0.46;

	/**
	 * Correlation between Credit Qualifying and FX Risk Classes
	 */

	public static final double CR_FX = 0.27;

	/**
	 * Correlation between Credit Non Qualifying and Equity Risk Classes
	 */

	public static final double CRNQ_EQ = 0.23;

	/**
	 * Correlation between Credit Non Qualifying and Commodity Risk Classes
	 */

	public static final double CRNQ_CT = 0.25;

	/**
	 * Correlation between Credit Non Qualifying and FX Risk Classes
	 */

	public static final double CRNQ_FX = 0.18;

	/**
	 * Correlation between Equity and Commodity Risk Classes
	 */

	public static final double EQ_CT = 0.39;

	/**
	 * Correlation between Equity and FX Risk Classes
	 */

	public static final double EQ_FX = 0.24;

	/**
	 * Correlation between Commodity and FX Risk Classes
	 */

	public static final double CT_FX = 0.32;

	/**
	 * Retrieve the Correlation between Interest Rate and Credit Qualifying Risk Classes
	 * 
	 * @return Correlation between Interest Rate and Credit Qualifying Risk Classes
	 */

	public static final double IR_CRQ()
	{
		return IR_CRQ;
	}

	/**
	 * Retrieve the Correlation between Interest Rate and Credit Qualifying Risk Classes
	 * 
	 * @return Correlation between Interest Rate and Credit Qualifying Risk Classes
	 */

	public static final double CRQ_IR()
	{
		return IR_CRQ;
	}

	/**
	 * Retrieve the Correlation between Interest Rate and Credit Non Qualifying Risk Classes
	 * 
	 * @return Correlation between Interest Rate and Credit Non Qualifying Risk Classes
	 */

	public static final double IR_CRNQ()
	{
		return IR_CRNQ;
	}

	/**
	 * Retrieve the Correlation between Interest Rate and Credit Non Qualifying Risk Classes
	 * 
	 * @return Correlation between Interest Rate and Credit Non Qualifying Risk Classes
	 */

	public static final double CRNQ_IR()
	{
		return IR_CRNQ;
	}

	/**
	 * Retrieve the Correlation between Interest Rate and Equity Risk Classes
	 * 
	 * @return Correlation between Interest Rate and Equity Risk Classes
	 */

	public static final double IR_EQ()
	{
		return IR_EQ;
	}

	/**
	 * Retrieve the Correlation between Interest Rate and Equity Risk Classes
	 * 
	 * @return Correlation between Interest Rate and Equity Risk Classes
	 */

	public static final double EQ_IR()
	{
		return IR_EQ;
	}

	/**
	 * Retrieve the Correlation between Interest Rate and Commodity Risk Classes
	 * 
	 * @return Correlation between Interest Rate and Commodity Risk Classes
	 */

	public static final double IR_CT()
	{
		return IR_CT;
	}

	/**
	 * Retrieve the Correlation between Interest Rate and Commodity Risk Classes
	 * 
	 * @return Correlation between Interest Rate and Commodity Risk Classes
	 */

	public static final double CT_IR()
	{
		return IR_CT;
	}

	/**
	 * Retrieve the Correlation between Interest Rate and FX Risk Classes
	 * 
	 * @return Correlation between Interest Rate and FX Risk Classes
	 */

	public static final double IR_FX()
	{
		return IR_FX;
	}

	/**
	 * Retrieve the Correlation between Interest Rate and FX Risk Classes
	 * 
	 * @return Correlation between Interest Rate and FX Risk Classes
	 */

	public static final double FX_IR()
	{
		return IR_FX;
	}

	/**
	 * Retrieve the Correlation between Credit Qualifying and Credit Non-Qualifying Risk Classes
	 * 
	 * @return Correlation between Credit Qualifying and Credit Non-Qualifying Risk Classes
	 */

	public static final double CRQ_CRNQ()
	{
		return CRQ_CRNQ;
	}

	/**
	 * Retrieve the Correlation between Credit Qualifying and Credit Non-Qualifying Risk Classes
	 * 
	 * @return Correlation between Credit Qualifying and Credit Non-Qualifying Risk Classes
	 */

	public static final double CNRQ_CNQ()
	{
		return CRQ_CRNQ;
	}

	/**
	 * Retrieve the Correlation between Credit Qualifying and Equity Risk Classes
	 * 
	 * @return Correlation between Credit Qualifying and Equity Risk Classes
	 */

	public static final double CRQ_EQ()
	{
		return CRQ_EQ;
	}

	/**
	 * Retrieve the Correlation between Credit Qualifying and Equity Risk Classes
	 * 
	 * @return Correlation between Credit Qualifying and Equity Risk Classes
	 */

	public static final double EQ_CRQ()
	{
		return CRQ_EQ;
	}

	/**
	 * Retrieve the Correlation between Credit Qualifying and Commodity Risk Classes
	 * 
	 * @return Correlation between Credit Qualifying and Commodity Risk Classes
	 */

	public static final double CRQ_CT()
	{
		return CRQ_CT;
	}

	/**
	 * Retrieve the Correlation between Credit Qualifying and Commodity Risk Classes
	 * 
	 * @return Correlation between Credit Qualifying and Commodity Risk Classes
	 */

	public static final double CT_CRQ()
	{
		return CRQ_CT;
	}

	/**
	 * Retrieve the Correlation between Credit Qualifying and FX Risk Classes
	 * 
	 * @return Correlation between Credit Qualifying and FX Risk Classes
	 */

	public static final double CRQ_FX()
	{
		return CR_FX;
	}

	/**
	 * Retrieve the Correlation between Credit Qualifying and FX Risk Classes
	 * 
	 * @return Correlation between Credit Qualifying and FX Risk Classes
	 */

	public static final double FX_CRQ()
	{
		return CR_FX;
	}

	/**
	 * Retrieve the Correlation between Credit Non Qualifying and Equity Risk Classes
	 * 
	 * @return Correlation between Credit Non Qualifying and Equity Risk Classes
	 */

	public static final double CNRQ_EQ()
	{
		return CRNQ_EQ;
	}

	/**
	 * Retrieve the Correlation between Credit Non Qualifying and Equity Risk Classes
	 * 
	 * @return Correlation between Credit Non Qualifying and Equity Risk Classes
	 */

	public static final double EQ_CNRQ()
	{
		return CRNQ_EQ;
	}

	/**
	 * Retrieve the Correlation between Credit Non Qualifying and Commodity Risk Classes
	 * 
	 * @return Correlation between Credit Non Qualifying and Commodity Risk Classes
	 */

	public static final double CNRQ_CT()
	{
		return CRNQ_CT;
	}

	/**
	 * Retrieve the Correlation between Credit Non Qualifying and Commodity Risk Classes
	 * 
	 * @return Correlation between Credit Non Qualifying and Commodity Risk Classes
	 */

	public static final double CT_CNRQ()
	{
		return CRNQ_CT;
	}

	/**
	 * Retrieve the Correlation between Credit Non Qualifying and FX Risk Classes
	 * 
	 * @return Correlation between Credit Non Qualifying and FX Risk Classes
	 */

	public static final double CreditNonQualifying_FX()
	{
		return CRNQ_FX;
	}

	/**
	 * Retrieve the Correlation between Credit Non Qualifying and FX Risk Classes
	 * 
	 * @return Correlation between Credit Non Qualifying and FX Risk Classes
	 */

	public static final double FX_CNRQ()
	{
		return CRNQ_FX;
	}

	/**
	 * Retrieve the Correlation between Equity and Commodity Risk Classes
	 * 
	 * @return Correlation between Equity and Commodity Risk Classes
	 */

	public static final double EQ_CT()
	{
		return EQ_CT;
	}

	/**
	 * Retrieve the Correlation between Equity and Commodity Risk Classes
	 * 
	 * @return Correlation between Equity and Commodity Risk Classes
	 */

	public static final double CT_EQ()
	{
		return EQ_CT;
	}

	/**
	 * Retrieve the Correlation between Equity and FX Risk Classes
	 * 
	 * @return Correlation between Equity and FX Risk Classes
	 */

	public static final double EQ_FX()
	{
		return EQ_FX;
	}

	/**
	 * Retrieve the Correlation between Equity and FX Risk Classes
	 * 
	 * @return Correlation between Equity and FX Risk Classes
	 */

	public static final double FX_EQ()
	{
		return EQ_FX;
	}

	/**
	 * Retrieve the Correlation between Commodity and FX Risk Classes
	 * 
	 * @return Correlation between Commodity and FX Risk Classes
	 */

	public static final double CT_FX()
	{
		return CT_FX;
	}

	/**
	 * Retrieve the Correlation between Commodity and FX Risk Classes
	 * 
	 * @return Correlation between Commodity and FX Risk Classes
	 */

	public static final double FX_CT()
	{
		return CT_FX;
	}
}
