
package org.drip.param.pricer;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
 * Copyright (C) 2012 Lakshmi Krishnamurthy
 * Copyright (C) 2011 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting analytics/risk, transaction cost analytics,
 *  	asset liability management analytics, capital, exposure, and margin analytics, valuation adjustment
 *  	analytics, and portfolio construction analytics within and across fixed income, credit, commodity,
 *  	equity, FX, and structured products. It also includes auxiliary libraries for algorithm support,
 *  	numerical analysis, numerical optimization, spline builder, model validation, statistical learning,
 *  	graph builder/navigator, and computational support.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three modules:
 *  
 *  - DROP Product Core - https://lakshmidrip.github.io/DROP-Product-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Computational Core - https://lakshmidrip.github.io/DROP-Computational-Core/
 * 
 * 	DROP Product Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Loan Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 *  - Asset Liability Management Analytics
 * 	- Capital Estimation Analytics
 * 	- Exposure Analytics
 * 	- Margin Analytics
 * 	- XVA Analytics
 * 
 * 	DROP Computational Core implements libraries for the following:
 * 	- Algorithm Support
 * 	- Computation Support
 * 	- Function Analysis
 *  - Graph Algorithm
 *  - Model Validation
 * 	- Numerical Analysis
 * 	- Numerical Optimizer
 * 	- Spline Builder
 *  - Statistical Learning
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
 * 	- Repo Layout Taxonomy     => https://github.com/lakshmiDRIP/DROP/blob/master/Taxonomy.md
 * 	- Javadoc                  => https://lakshmidrip.github.io/DROP/Javadoc/index.html
 * 	- Technical Specifications => https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal
 * 	- Release Versions         => https://lakshmidrip.github.io/DROP/version.html
 * 	- Community Credits        => https://lakshmidrip.github.io/DROP/credits.html
 * 	- Issues Catalog           => https://github.com/lakshmiDRIP/DROP/issues
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
 * <i>CreditPricerParams</i> contains the Credit Pricer Parameters - the discrete unit size, calibration mode
 * on/off, survival to pay/end date, and the discretization scheme.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/param/README.md">Product Cash Flow, Valuation, Market, Pricing, and Quoting Parameters</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/param/pricer/README.md">Pricing Parameters Customization Settings Control</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class CreditPricerParams implements org.drip.param.pricer.PricerParams {

	/*
	 * Loss period Grid discretization scheme
	 */

	/**
	 * Minimum number of days per unit
	 */

	public static final int PERIOD_DAY_STEPS_MINIMUM = 7;

	/**
	 * Discretization as a sequence of day steps
	 */

	public static final int PERIOD_DISCRETIZATION_DAY_STEP = 1;

	/**
	 * Discretization as a sequence of time space divided periods
	 */

	public static final int PERIOD_DISCRETIZATION_PERIOD_STEP = 2;

	/**
	 * No discretization at all - just the full coupon period
	 */

	public static final int PERIOD_DISCRETIZATION_FULL_COUPON = 3;

	private int _iUnitSize = 7;
	private boolean _bSurvToPayDate = false;
	private int _iDiscretizationScheme = PERIOD_DISCRETIZATION_DAY_STEP;
	private org.drip.param.definition.CalibrationParams _calibParams = null;

	/**
	 * Create the standard Credit pricer parameters object instance
	 * 
	 * @return CreditPricerParams object instance
	 */

	public static final CreditPricerParams Standard()
	{
		return new CreditPricerParams (7, null, false, PERIOD_DISCRETIZATION_DAY_STEP);
	}

	/**
	 * Create the pricer parameters from the discrete unit size, calibration mode on/off, survival to
	 * 	pay/end date, and the discretization scheme
	 * 
	 * @param iUnitSize Discretization Unit Size
	 * @param calibParams Optional Calibration Params
	 * @param bSurvToPayDate Survival to Pay Date (True) or Period End Date (false)
	 * @param iDiscretizationScheme Discretization Scheme In Use
	 */

	public CreditPricerParams (
		final int iUnitSize,
		final org.drip.param.definition.CalibrationParams calibParams,
		final boolean bSurvToPayDate,
		final int iDiscretizationScheme)
	{
		_iUnitSize = iUnitSize;
		_calibParams = calibParams;
		_bSurvToPayDate = bSurvToPayDate;
		_iDiscretizationScheme = iDiscretizationScheme;
	}

	/**
	 * Retrieve the Discretized Loss Unit Size
	 * 
	 * @return The Discretized Loss Unit Size
	 */

	public int unitSize()
	{
		return _iUnitSize;
	}

	/**
	 * Retrieve the Calibration Parameters Instance
	 * 
	 * @return The Calibration Parameters Instance
	 */

	public org.drip.param.definition.CalibrationParams calibParams()
	{
		return _calibParams;
	}

	/**
	 * Retrieve the flag indicating whether the Survival is to be computed to the Pay Date (TRUE) or not
	 * 
	 * @return TRUE - Survival is to be computed to the Pay Date
	 */

	public boolean survivalToPayDate()
	{
		return _bSurvToPayDate;
	}

	/**
	 * Retrieve the Discretization Scheme
	 * 
	 * @return The Discretization Scheme
	 */

	public int discretizationScheme()
	{
		return _iDiscretizationScheme;
	}
}
