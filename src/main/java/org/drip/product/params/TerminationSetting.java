
package org.drip.product.params;

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
 * <i>TerminationSetting</i> class contains the current "liveness" state of the component, and, if inactive,
 * how it entered that state. It exports serialization into and de-serialization out of byte arrays.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Project</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product">Product</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/params">Params</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/FixedIncome">Fixed Income Analytics</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class TerminationSetting implements org.drip.product.params.Validatable {
	private boolean _bIsDefaulted = false;
	private boolean _bIsPerpetual = false;
	private boolean _bHasBeenExercised = false;
	private org.drip.analytics.daycount.DateAdjustParams _dap = null;

	/**
	 * Construct the TerminationSetting object from the perpetual flag, defaulted flag, and the has
	 * 	been exercised flag.
	 * 
	 * @param bIsPerpetual True (component is perpetual)
	 * @param bIsDefaulted True (component has defaulted)
	 * @param bHasBeenExercised True (component has been exercised)
	 * @param dap The Termination Date Adjustment Parameters
	 */

	public TerminationSetting (
		final boolean bIsPerpetual,
		final boolean bIsDefaulted,
		final boolean bHasBeenExercised,
		final org.drip.analytics.daycount.DateAdjustParams dap)
	{
		_dap = dap;
		_bIsPerpetual = bIsPerpetual;
		_bIsDefaulted = bIsDefaulted;
		_bHasBeenExercised = bHasBeenExercised;
	}

	@Override public boolean validate()
	{
		return true;
	}

	/**
	 * Indicate if the contract has defaulted
	 * 
	 * @return TRUE - The Contract has defaulted
	 */

	public boolean defaulted()
	{
		return _bIsDefaulted;
	}

	/**
	 * Indicate if the contract is perpetual
	 * 
	 * @return TRUE - The Contract is Perpetual
	 */

	public boolean perpetual()
	{
		return _bIsPerpetual;
	}

	/**
	 * Indicate if the contract has been exercised
	 * 
	 * @return TRUE - The Contract has been exercised
	 */

	public boolean exercised()
	{
		return _bHasBeenExercised;
	}

	/**
	 * Retrieve the Termination Setting Date Adjustment Parameters
	 * 
	 * @return The Termination Setting Date Adjustment Parameters
	 */

	public org.drip.analytics.daycount.DateAdjustParams dap()
	{
		return _dap;
	}
}
