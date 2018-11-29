
package org.drip.param.pricer;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
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
 *  This file is part of DROP, an open-source library targeting risk, transaction costs, exposure, margin
 *  	calculations, and portfolio construction within and across fixed income, credit, commodity, equity,
 *  	FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three main modules:
 *  
 *  - DROP Analytics Core - https://lakshmidrip.github.io/DROP-Analytics-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Numerical Core - https://lakshmidrip.github.io/DROP-Numerical-Core/
 * 
 * 	DROP Analytics Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Asset Backed Analytics
 * 	- XVA Analytics
 * 	- Exposure and Margin Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Numerical Core implements libraries for the following:
 * 	- Statistical Learning Library
 * 	- Numerical Optimizer Library
 * 	- Machine Learning Library
 * 	- Spline Builder Library
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
 * 	- Javadoc                  => https://lakshmidrip.github.io/DROP/Javadoc/index.html
 * 	- Technical Specifications => https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal
 * 	- Release Versions         => https://lakshmidrip.github.io/DROP/version.html
 * 	- Community Credits        => https://lakshmidrip.github.io/DROP/credits.html
 * 	- Issues Catalog           => https://github.com/lakshmiDRIP/DROP/issues
 * 	- JUnit                    => https://lakshmidrip.github.io/DROP/junit/index.html
 * 	- Jacoco                   => https://lakshmidrip.github.io/DROP/jacoco/index.html
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
 * on/off, survival to pay/end date, and the discretization scheme
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/param">Param</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/param/pricer">Pricer</a></li>
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
