
package org.drip.capital.setting;

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
 * <i>HorizonTailPnLControl</i> holds the Horizon/Tail Adjustment Control Parameters. The References are:
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
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/setting/README.md">Economic Risk Capital Simulation Settings</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public abstract class HorizonTailPnLControl
{
	private int _horizon = -1;
	private double _degreesOfFreedom = java.lang.Double.NaN;
	private double _varConfidenceLevel = java.lang.Double.NaN;
	private double _expectedShortfallConfidenceLevel = java.lang.Double.NaN;

	/**
	 * Construct the Standard Stress Instance of HorizonTailPnLControl
	 * 
	 * @return Standard Stress Instance of HorizonTailPnLControl
	 */

	public static final HorizonTailPnLControl StandardStress()
	{
		try
		{
			return new HorizonTailPnLControl (
				1,
				5.,
				0.9997,
				0.98
			)
			{
				@Override public double tailDistributionScaler()
				{
					return 2.;
				}

				@Override public double grossScaler()
				{
					return 2.;
				}
			};
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * HorizonTailPnLControl Constructor
	 * 
	 * @param horizon Horizon
	 * @param degreesOfFreedom PnL Distribution Degrees of Freedom
	 * @param varConfidenceLevel VaR Confidence Level
	 * @param expectedShortfallConfidenceLevel Expected Short-fall Confidence Level
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public HorizonTailPnLControl (
		final int horizon,
		final double degreesOfFreedom,
		final double varConfidenceLevel,
		final double expectedShortfallConfidenceLevel)
		throws java.lang.Exception
	{
		if (0 >= (_horizon = horizon) ||
			java.lang.Double.isNaN (_degreesOfFreedom = degreesOfFreedom) || 0. >= _degreesOfFreedom ||
			!org.drip.numerical.common.NumberUtil.IsValid (_varConfidenceLevel = varConfidenceLevel) ||
				0. >= _varConfidenceLevel || 1. <= _varConfidenceLevel ||
			!org.drip.numerical.common.NumberUtil.IsValid
				(_expectedShortfallConfidenceLevel = expectedShortfallConfidenceLevel) ||
				0. >= _expectedShortfallConfidenceLevel || 1. <= _expectedShortfallConfidenceLevel)
		{
			throw new java.lang.Exception ("HorizonTailPnLControl Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Horizon in Days
	 * 
	 * @return Horizon in Days
	 */

	public int horizon()
	{
		return _horizon;
	}

	/**
	 * Retrieve the PnL Distribution Degrees of Freedom
	 * 
	 * @return PnL Distribution Degrees of Freedom
	 */

	public double degreesOfFreedom()
	{
		return _degreesOfFreedom;
	}

	/**
	 * Retrieve the VaR Confidence Level
	 * 
	 * @return VaR Confidence Level
	 */

	public double varConfidenceLevel()
	{
		return _varConfidenceLevel;
	}

	/**
	 * Retrieve the Expected Short-fall Confidence Level
	 * 
	 * @return Expected Short-fall Confidence Level
	 */

	public double expectedShortfallConfidenceLevel()
	{
		return _expectedShortfallConfidenceLevel;
	}

	/**
	 * Retrieve the Horizon Scaler
	 * 
	 * @return Horizon Scaler
	 */

	public double horizonScaler()
	{
		return java.lang.Math.sqrt (_horizon);
	}

	/**
	 * Retrieve the Tail Distribution Scaler
	 * 
	 * @return Tail Distribution Scaler
	 */

	public abstract double tailDistributionScaler();

	/**
	 * Retrieve the Gross (Horizon X Tail) Scaler
	 * 
	 * @return Gross (Horizon X Tail) Scaler
	 */

	public double grossScaler()
	{
		return horizonScaler() * tailDistributionScaler();
	}
}
