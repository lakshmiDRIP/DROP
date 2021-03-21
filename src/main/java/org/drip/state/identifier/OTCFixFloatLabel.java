
package org.drip.state.identifier;

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
 * <i>OTCFixFloatLabel</i> contains the Index Parameters referencing a Payment on an OTC Fix/Float IRS Par
 * Rate Index.
 *
 *  <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/README.md">Latent State Inference and Creation Utilities</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/identifier/README.md">Latent State Identifier Labels</a></li>
 *  </ul>
 * <br><br>
 *  
 * @author Lakshmi Krishnamurthy
 */

public class OTCFixFloatLabel extends org.drip.state.identifier.FloaterLabel
{
	private java.lang.String _strFixFloatTenor = "";

	/**
	 * Construct a OTCFixFloatLabel from the corresponding Fully Qualified Name
	 * 
	 * @param strFullyQualifiedName The Fully Qualified Name
	 * 
	 * @return OTCFixFloatLabel Instance
	 */

	public static final OTCFixFloatLabel Standard (
		final java.lang.String strFullyQualifiedName)
	{
		if (null == strFullyQualifiedName || strFullyQualifiedName.isEmpty()) return null;

		java.lang.String[] astr = strFullyQualifiedName.split ("-");

		if (null == astr || 3 != astr.length) return null;

		java.lang.String strTenor = astr[1];
		java.lang.String strCurrency = astr[0];
		java.lang.String strFixFloatTenor = astr[2];

		org.drip.market.definition.FloaterIndex floaterIndex = "ON".equalsIgnoreCase (strTenor) ||
			"1D".equalsIgnoreCase (strTenor) ?
				org.drip.market.definition.OvernightIndexContainer.IndexFromJurisdiction (strCurrency) :
					org.drip.market.definition.IBORIndexContainer.IndexFromJurisdiction (strCurrency);

		try {
			return new OTCFixFloatLabel (
				floaterIndex,
				strTenor,
				strFixFloatTenor
			);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * OTCFixFloatLabel Constructor
	 * 
	 * @param floaterIndex Floater Index
	 * @param strForwardTenor Forward Tenor
	 * @param strFixFloatTenor Fix Float Tenor
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public OTCFixFloatLabel (
		final org.drip.market.definition.FloaterIndex floaterIndex,
		final java.lang.String strForwardTenor,
		final java.lang.String strFixFloatTenor)
		throws java.lang.Exception
	{
		super (floaterIndex, strForwardTenor);

		if (null == (_strFixFloatTenor = strFixFloatTenor) || _strFixFloatTenor.isEmpty())
			throw new java.lang.Exception ("OTCFixFloatLabel Constructor => Invalid Inputs");
	}

	/**
	 * Retrieve the Fix Float Tenor
	 * 
	 * @return The Fix Float Tenor
	 */

	public java.lang.String fixFloatTenor()
	{
		return _strFixFloatTenor;
	}

	@Override public java.lang.String fullyQualifiedName()
	{
		return super.fullyQualifiedName() + "::" + _strFixFloatTenor;
	}

	@Override public boolean match (
		final org.drip.state.identifier.LatentStateLabel lslOther)
	{
		if (!(lslOther instanceof org.drip.state.identifier.OTCFixFloatLabel)) return false;

		return super.match (lslOther);
	}
}
