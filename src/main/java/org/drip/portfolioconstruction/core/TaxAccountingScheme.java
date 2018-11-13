
package org.drip.portfolioconstruction.core;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
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
 * <i>TaxAccountingScheme</i> contains the Attributes for the specified Tax Accounting Scheme.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module</b>        = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction">Portfolio Construction</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/core">Core/a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/AssetAllocation">Asset Allocation Library</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class TaxAccountingScheme extends org.drip.portfolioconstruction.core.Block {
	private int _iWashDays = -1;
	private int _iShortTermDays = -1;
	private double _dblLongTermTaxRate = java.lang.Double.NaN;
	private double _dblShortTermTaxRate = java.lang.Double.NaN;

	/**
	 * TaxAccountingScheme Constructor
	 * 
	 * @param strName The Name
	 * @param strID The ID
	 * @param strDescription The Description
	 * @param dblShortTermTaxRate Short Term Tax Rate
	 * @param dblLongTermTaxRate Long Term Tax Rate
	 * @param iShortTermDays Short Term Days
	 * @param iWashDays Wash Days
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public TaxAccountingScheme (
		final java.lang.String strName,
		final java.lang.String strID,
		final java.lang.String strDescription,
		final double dblShortTermTaxRate,
		final double dblLongTermTaxRate,
		final int iShortTermDays,
		final int iWashDays)
		throws java.lang.Exception
	{
		super (strName, strID, strDescription);

		if (!org.drip.quant.common.NumberUtil.IsValid (_dblShortTermTaxRate = dblShortTermTaxRate) ||
			!org.drip.quant.common.NumberUtil.IsValid (_dblLongTermTaxRate = dblLongTermTaxRate) || -1 >=
				(_iShortTermDays = iShortTermDays) || -1 >= (_iWashDays = iWashDays))
			throw new java.lang.Exception ("TaxAccountingScheme Constructor => Invalid Inputs");
	}

	/**
	 * Retrieve the Short Term Tax Rate
	 * 
	 * @return The Short Term Tax Rate
	 */

	public double shortTermTaxRate()
	{
		return _dblShortTermTaxRate;
	}

	/**
	 * Retrieve the Long Term Tax Rate
	 * 
	 * @return The Long Term Tax Rate
	 */

	public double longTermTaxRate()
	{
		return _dblLongTermTaxRate;
	}

	/**
	 * Retrieve the Short Term Days
	 * 
	 * @return The Short Term Days
	 */

	public int shortTermDays()
	{
		return _iShortTermDays;
	}

	/**
	 * Retrieve the Wash Days
	 * 
	 * @return The Wash Days
	 */

	public int washDays()
	{
		return _iWashDays;
	}
}
