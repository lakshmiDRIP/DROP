
package org.drip.capital.stress;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
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
 * <i>PnLSeries</i> contains the PnL Series of a Single Event. The References are:
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
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/stress/README.md">Economic Risk Capital Stress Event Settings</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class PnLSeries
{
	private double[] _outcomeArray = null;

	/**
	 * Construct a Single Outcome Event PnL
	 * 
	 * @param outcome The PnL Outcome
	 * 
	 * @return The Single Outcome Event PnL
	 */

	public static final PnLSeries SingleOutcome (
		final double outcome)
	{
		try
		{
			return new PnLSeries (
				new double[]
				{
					outcome
				}
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct a Single Zero Outcome Event PnL
	 * 
	 * @return The Single Zero Outcome Event PnL
	 */

	public static final PnLSeries SingleZeroOutcome()
	{
		return SingleOutcome (
			0.
		);
	}

	/**
	 * PnLSeries Constructor
	 * 
	 * @param outcomeArray Array of PnL Outcomes
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public PnLSeries (
		final double[] outcomeArray)
		throws java.lang.Exception
	{
		if (null == (_outcomeArray = outcomeArray) || 0 == _outcomeArray.length ||
			!org.drip.numerical.common.NumberUtil.IsValid (_outcomeArray))
		{
			throw new java.lang.Exception (
				"PnLSeries Constructor => Invalid inputs"
			);
		}
	}

	/**
	 * Retrieve the Array of PnL Outcomes
	 * 
	 * @return Array of PnL Outcomes
	 */

	public double[] outcomeArray()
	{
		return _outcomeArray;
	}

	/**
	 * Retrieve the Count of PnL Outcomes
	 * 
	 * @return Count of PnL Outcomes
	 */

	public int count()
	{
		return _outcomeArray.length;
	}

	/**
	 * Retrieve the Composite of the Outcomes
	 * 
	 * @return Composite of the Outcomes
	 */

	public double composite()
	{
		double sum = 0.;

		for (double outcome : _outcomeArray)
		{
			sum = sum + outcome;
		}

		return sum / _outcomeArray.length;
	}

	@Override public java.lang.String toString()
	{
		java.lang.String representation = "[";

		for (double outcome : _outcomeArray)
		{
			representation = representation + outcome + ",";
		}

		return representation + "]";
	}
}
