
package org.drip.capital.label;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
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
 * <i>BusinessRegionRiskTypeCoordinate</i> implements the Capital Unit Coordinate based on Business, Region,
 * 	and Risk Type. The References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Bank for International Supervision(2005): Stress Testing at Major Financial Institutions: Survey
 * 				Results and Practice https://www.bis.org/publ/cgfs24.htm
 * 		</li>
 * 		<li>
 * 			Glasserman, P. (2004): <i>Monte Carlo Methods in Financial Engineering</i> <b>Springer</b>
 * 		</li>
 * 		<li>
 * 			Kupiec, P. H. (2000): Stress Tests and Risk Capital <i>Risk</i> <b>2 (4)</b> 27-39
 * 		</li>
 * 	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/CapitalAnalyticsLibrary.md">Capital Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/README.md">Basel Market Risk and Operational Capital</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/label/README.md">Economic Risk Capital Entity Labels</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class BusinessRegionRiskTypeCoordinate
	extends org.drip.capital.label.RegionRiskTypeCoordinate
{
	private java.lang.String _business = "";

	/**
	 * Construct a Standard Instance of BusinessRegionRiskTypeCoordinate from the FQN
	 * 
	 * @param fullyQualifiedName The FQN
	 * 
	 * @return Standard Instance of BusinessRegionRiskTypeCoordinate from the FQN
	 */

	public static final BusinessRegionRiskTypeCoordinate Standard (
		final java.lang.String fullyQualifiedName)
	{
		if (null == fullyQualifiedName || fullyQualifiedName.isEmpty())
		{
			return null;
		}

		java.lang.String[] businessRegionRiskType = org.drip.service.common.StringUtil.Split (
			fullyQualifiedName,
			org.drip.capital.label.Coordinate.FQN_DELIMITER
		);

		if (null == businessRegionRiskType || 3 != businessRegionRiskType.length)
		{
			return null;
		}

		try
		{
			return new BusinessRegionRiskTypeCoordinate (
				businessRegionRiskType[0],
				businessRegionRiskType[1],
				businessRegionRiskType[2]
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * BusinessRegionRiskTypeCoordinate Constructor
	 * 
	 * @param business iVAST Business
	 * @param region iVAST Region
	 * @param riskType iVAST  Risk Type
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public BusinessRegionRiskTypeCoordinate (
		final java.lang.String business,
		final java.lang.String region,
		final java.lang.String riskType)
		throws java.lang.Exception
	{
		super (
			region,
			riskType
		);

		if (null == (_business = business) || _business .isEmpty())
		{
			throw new java.lang.Exception (
				"BusinessRegionRiskTypeCoordinate Constructor => Invalid Inputs"
			);
		}
	}

	/**
	 * Retrieve the iVAST Business
	 * 
	 * @return The iVAST Business
	 */

	public java.lang.String business()
	{
		return _business;
	}

	@Override public java.lang.String fullyQualifiedName()
	{
		return _business + org.drip.capital.label.Coordinate.FQN_DELIMITER + region() +
			org.drip.capital.label.Coordinate.FQN_DELIMITER + riskType();
	}

	/**
	 * Retrieve the Region-Risk Type Node Identifier
	 * 
	 * @return The Region-Risk Type Node Identifier
	 */

	public org.drip.capital.label.RegionRiskTypeCoordinate regionRiskTypeCoordinate()
	{
		return org.drip.capital.label.RegionRiskTypeCoordinate.Standard (
			region() + org.drip.capital.label.Coordinate.FQN_DELIMITER + riskType()
		);
	}
}
