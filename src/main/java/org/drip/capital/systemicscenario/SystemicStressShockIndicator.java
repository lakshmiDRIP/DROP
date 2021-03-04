
package org.drip.capital.systemicscenario;

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
 * <i>SystemicStressShockIndicator</i> holds the Directional Indicator Settings for a given Systemic Stress
 * 	Shock Event. The References are:
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
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/systemicscenario/README.md">Systemic Stress Scenario Design/Construction</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class SystemicStressShockIndicator
{

	/**
	 * Systemic Stress Shock Direction UP
	 */

	public static final int UP = 1;

	/**
	 * Systemic Stress Shock Direction DOWN
	 */

	public static final int DOWN = -1;

	/**
	 * Systemic Stress Shock Direction UNSPECIFIED
	 */

	public static final int UNSPECIFIED = 0;

	/**
	 * DEFLATIONARY Systemic Stress Scenario
	 */

	public static final java.lang.String DEFLATIONARY = "DEFLATIONARY";

	/**
	 * INFLATIONARY Systemic Stress Scenario
	 */

	public static final java.lang.String INFLATIONARY = "INFLATIONARY";

	/**
	 * Neither DEFLATIONARY nor INFLATIONARY Systemic Stress Scenario
	 */

	public static final java.lang.String NEITHER = "NEITHER";

	private int _energy = java.lang.Integer.MIN_VALUE;
	private int _commodities = java.lang.Integer.MIN_VALUE;
	private int _creditSpreads = java.lang.Integer.MIN_VALUE;
	private int _equityMarkets = java.lang.Integer.MIN_VALUE;
	private int _yieldCurveLevel = java.lang.Integer.MIN_VALUE;

	private static final boolean VerifyIndicator (
		final int indicator)
	{
		return DOWN == indicator || UP == indicator || UNSPECIFIED == indicator;
	}

	/**
	 * Construct a Deflationary Systemic Stress Shock Indicator
	 * 
	 * @return Deflationary Systemic Stress Shock Indicator
	 */

	public static final SystemicStressShockIndicator Deflationary()
	{
		try
		{
			return new SystemicStressShockIndicator (
				UP,
				DOWN,
				DOWN,
				DOWN,
				DOWN
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct a Inflationary Systemic Stress Shock Indicator
	 * 
	 * @return Inflationary Systemic Stress Shock Indicator
	 */

	public static final SystemicStressShockIndicator Inflationary()
	{
		try
		{
			return new SystemicStressShockIndicator (
				UP,
				DOWN,
				UP,
				UP,
				UP
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * SystemicStressShockIndicator Constructor
	 * 
	 * @param creditSpreads Credit Spreads Directional Indicator
	 * @param equityMarkets Equity Markets Directional Indicator
	 * @param yieldCurveLevel Yield Curve Level Directional Indicator
	 * @param commodities Commodities Directional Indicator
	 * @param energy Energy Directional Indicator
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public SystemicStressShockIndicator (
		final int creditSpreads,
		final int equityMarkets,
		final int yieldCurveLevel,
		final int commodities,
		final int energy)
		throws java.lang.Exception
	{
		if (!VerifyIndicator (
				_creditSpreads = creditSpreads
			) || !VerifyIndicator (
				_equityMarkets = equityMarkets
			) || !VerifyIndicator (
				_yieldCurveLevel = yieldCurveLevel
			) || !VerifyIndicator (
				_commodities = commodities
			) || !VerifyIndicator (
				_energy = energy
			)
		)
		{
			throw new java.lang.Exception (
				"SystemicStressShockIndicator Cnstructor => Invalid Inputs"
			);
		}
	}

	/**
	 * Retrieve the Credit Spreads Directional Indicator
	 * 
	 * @return The Credit Spreads Directional Indicator
	 */

	public int creditSpreads()
	{
		return _creditSpreads;
	}

	/**
	 * Retrieve the Equity Markets Directional Indicator
	 * 
	 * @return The Equity Markets Directional Indicator
	 */

	public int equityMarkets()
	{
		return _equityMarkets;
	}

	/**
	 * Retrieve the Yield Curve Level Directional Indicator
	 * 
	 * @return The Yield Curve Level Directional Indicator
	 */

	public int yieldCurveLevel()
	{
		return _yieldCurveLevel;
	}

	/**
	 * Retrieve the Commodities Directional Indicator
	 * 
	 * @return The Commodities Directional Indicator
	 */

	public int commodities()
	{
		return _commodities;
	}

	/**
	 * Retrieve the Energy Directional Indicator
	 * 
	 * @return The Energy Directional Indicator
	 */

	public int energy()
	{
		return _energy;
	}

	/**
	 * Indicate if the Scenario is DEFLATIONARY
	 * 
	 * @return TRUE - The Scenario is DEFLATIONARY
	 */

	public boolean isDeflationary()
	{
		return UP == _creditSpreads &&
			DOWN == _equityMarkets &&
			DOWN == _yieldCurveLevel && 
			DOWN == _commodities &&
			DOWN == _energy;
	}

	/**
	 * Indicate if the Scenario is INFLATIONARY
	 * 
	 * @return TRUE - The Scenario is INFLATIONARY
	 */

	public boolean isInflationary()
	{
		return UP == _creditSpreads &&
			DOWN == _equityMarkets &&
			UP == _yieldCurveLevel && 
			UP == _commodities &&
			UP == _energy;
	}

	/**
	 * Indicate the Inflation Type
	 * 
	 * @return The Inflation Type
	 */

	public java.lang.String inflationType()
	{
		if (isDeflationary())
		{
			return DEFLATIONARY;
		}

		return isInflationary() ? INFLATIONARY : NEITHER;
	}
}
