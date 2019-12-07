
package org.drip.xva.basel;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
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
 *  	and computational support.
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
 * <i>ValueAdjustment</i> holds the Value and the Attribution Category at the Level of a Portfolio. The
 * References are:
 *
 *  <br><br>
 *  <ul>
 *  	<li>
 *  		Albanese, C., and L. Andersen (2014): Accounting for OTC Derivatives: Funding Adjustments and the
 *  			Re-Hypothecation Option https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2482955
 *  			<b>eSSRN</b>
 *  	</li>
 *  	<li>
 *  		BCBS (2012): <i>Consultative Document: Application of Own Credit Risk Adjustments to
 *  			Derivatives</i> <b>Basel Committee on Banking Supervision</b>
 *  	</li>
 *  	<li>
 *  		Burgard, C., and M. Kjaer (2014): PDE Representations of Derivatives with Bilateral Counter-party
 *  			Risk and Funding Costs <i>Journal of Credit Risk</i> <b>7 (3)</b> 1-19
 *  	</li>
 *  	<li>
 *  		Burgard, C., and M. Kjaer (2014): In the Balance <i>Risk</i> <b>24 (11)</b> 72-75
 *  	</li>
 *  	<li>
 *  		Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives Pricing
 *  			<i>Risk</i> <b>21 (2)</b> 97-102
 *  	</li>
 *  </ul>
 *
 *  <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/XVAAnalyticsLibrary.md">XVA Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/README.md">Valuation Adjustments that account for Collateral, CC Credit/Debt and Funding Overhead</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/basel/README.md">XVA Based Basel Accounting Measures</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class ValueAdjustment
{
	private double _amount = java.lang.Double.NaN;
	private org.drip.xva.basel.ValueCategory _valueCategory = null;

	/**
	 * Construct the Collateralized Transaction Value Adjustment Instance
	 * 
	 * @param amount Valuation Adjustment Amount
	 * 
	 * @return The Collateralized Transaction Value Adjustment Instance
	 */

	public static final ValueAdjustment Collateralized (
		final double amount)
	{
		try
		{
			return new ValueAdjustment (
				amount,
				org.drip.xva.basel.ValueCategory.CF1()
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the UCVA Value Adjustment Instance
	 * 
	 * @param amount Valuation Adjustment Amount
	 * 
	 * @return The UCVA Value Adjustment Instance
	 */

	public static final ValueAdjustment UCVA (
		final double amount)
	{
		try
		{
			return new ValueAdjustment (
				amount,
				org.drip.xva.basel.ValueCategory.CF2()
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the FTDCVA Value Adjustment Instance
	 * 
	 * @param amount Valuation Adjustment Amount
	 * 
	 * @return The FTDCVA Value Adjustment Instance
	 */

	public static final ValueAdjustment FTDCVA (
		final double amount)
	{
		try
		{
			return new ValueAdjustment (
				amount,
				org.drip.xva.basel.ValueCategory.CF2()
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the DVA Value Adjustment Instance
	 * 
	 * @param amount Valuation Adjustment Amount
	 * 
	 * @return The DVA Value Adjustment Instance
	 */

	public static final ValueAdjustment DVA (
		final double amount)
	{
		try
		{
			return new ValueAdjustment (
				amount,
				org.drip.xva.basel.ValueCategory.CF3()
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the CVA Contra-Liability Value Adjustment Instance
	 * 
	 * @param amount Valuation Adjustment Amount
	 * 
	 * @return The CVA Contra-Liability Value Adjustment Instance
	 */

	public static final ValueAdjustment CVACL (
		final double amount)
	{
		try
		{
			return new ValueAdjustment (
				amount,
				org.drip.xva.basel.ValueCategory.CF3()
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the FVA Value Adjustment Instance
	 * 
	 * @param amount Valuation Adjustment Amount
	 * 
	 * @return The FVA Value Adjustment Instance
	 */

	public static final ValueAdjustment FVA (
		final double amount)
	{
		try {
			return new ValueAdjustment (amount, org.drip.xva.basel.ValueCategory.CF4());
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the FDA Value Adjustment Instance
	 * 
	 * @param amount Valuation Adjustment Amount
	 * 
	 * @return The FDA Value Adjustment Instance
	 */

	public static final ValueAdjustment FDA (
		final double amount)
	{
		try
		{
			return new ValueAdjustment (
				amount,
				org.drip.xva.basel.ValueCategory.CF5()
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the DVA2 Value Adjustment Instance
	 * 
	 * @param amount Valuation Adjustment Amount
	 * 
	 * @return The DVA2 Value Adjustment Instance
	 */

	public static final ValueAdjustment DVA2 (
		final double amount)
	{
		return FDA (amount);
	}

	/**
	 * Construct the COLVA Value Adjustment Instance
	 * 
	 * @param amount Valuation Adjustment Amount
	 * 
	 * @return The COLVA Value Adjustment Instance
	 */

	public static final ValueAdjustment COLVA (
		final double amount)
	{
		try
		{
			return new ValueAdjustment (
				amount,
				org.drip.xva.basel.ValueCategory.CF6()
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the HYBRID Value Adjustment Instance
	 * 
	 * @param amount Valuation Adjustment Amount
	 * 
	 * @return The HYBRID Value Adjustment Instance
	 */

	public static final ValueAdjustment HYBRID (
		final double amount)
	{
		try
		{
			return new ValueAdjustment (
				amount,
				org.drip.xva.basel.ValueCategory.HYBRID()
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * ValueAdjustment Constructor
	 * 
	 * @param amount Valuation Adjustment Amount
	 * @param valueCategory Valuation Adjustment Attribution Category
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public ValueAdjustment (
		final double amount,
		final org.drip.xva.basel.ValueCategory valueCategory)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (_amount = amount) ||
			null == (_valueCategory = valueCategory))
		{
			throw new java.lang.Exception ("ValueAdjustment Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Valuation Adjustment Amount
	 * 
	 * @return The Valuation Adjustment Amount
	 */

	public double amount()
	{
		return _amount;
	}

	/**
	 * Retrieve the Valuation Adjustment Attribution Category
	 * 
	 * @return The Valuation Adjustment Attribution Category
	 */

	public org.drip.xva.basel.ValueCategory valueCategory()
	{
		return _valueCategory;
	}
}
