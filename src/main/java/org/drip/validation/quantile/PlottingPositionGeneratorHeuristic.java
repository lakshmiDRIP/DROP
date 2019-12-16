
package org.drip.validation.quantile;

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
 * <i>PlottingPositionGeneratorHeuristic</i> holds the Expected Order Statistic Based Heuristic Plotting
 * Position Generation Schemes.
 *
 *  <br><br>
 *  <ul>
 *  	<li>
 *  		Filliben, J. J. (1975): The Probability Plot Correlation Coefficient Test for Normality
 *  			<i>Technometrics, American Society for Quality</i> <b>17 (1)</b> 111-117
 *  	</li>
 *  	<li>
 *  		Gibbons, J. D., and S. Chakraborti (2003): <i>Non-parametric Statistical Inference 4th
 *  			Edition</i> <b>CRC Press</b>
 *  	</li>
 *  	<li>
 *  		Gnanadesikan, R. (1977): <i>Methods for Statistical Analysis of Multivariate Observations</i>
 *  			<b>Wiley</b>
 *  	</li>
 *  	<li>
 *  		Thode, H. C. (2002): <i>Testing for Normality</i> <b>Marcel Dekker</b> New York
 *  	</li>
 *  	<li>
 *  		Wikipedia (2018): Q-Q Plot https://en.wikipedia.org/wiki/Q%E2%80%93Q_plot
 *  	</li>
 *  </ul>
 *
 *  <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ModelValidationAnalyticsLibrary.md">Model Validation Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/validation/README.md">Risk Factor and Hypothesis Validation, Evidence Processing, and Model Testing</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/validation/quantile/README.md">Quantile Based Graphical Numerical Validators</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class PlottingPositionGeneratorHeuristic extends
	org.drip.validation.quantile.PlottingPositionGenerator
{

	/**
	 * Standard Heuristic
	 */

	public static final double RELIEF_STANDARD = 0.;

	/**
	 * Bernard and Bos-Levenbach (1953) Heuristic
	 */

	public static final double RELIEF_BERNARD_BOS_LEVENBACH_1953 = 0.3;

	/**
	 * NIST (2013) Heuristic
	 */

	public static final double RELIEF_NIST_2013 = 0.3175;

	/**
	 * Yu and Huang (2001) Heuristic
	 */

	public static final double RELIEF_YU_HUANG_2001 = 0.326;

	/**
	 * BMDP (2018) Heuristic
	 */

	public static final double RELIEF_BMDP_2018 = 0.333333;

	/**
	 * Blom (1958) Heuristic
	 */

	public static final double RELIEF_BLOM_1958 = 0.375;

	/**
	 * Cunnane (1978) Heuristic
	 */

	public static final double RELIEF_CUNNANE_1978 = 0.4;

	/**
	 * Gringorten (1963) Heuristic
	 */

	public static final double RELIEF_GRINGORTEN_1963 = 0.44;

	/**
	 * Hazen (1913) Heuristic
	 */

	public static final double RELIEF_HAZEN_1913 = 0.5;

	/**
	 * Larsen, Currant, and Hunt (1980) Heuristic
	 */

	public static final double RELIEF_LARSEN_CURRANT_HUNT_1980 = 0.567;

	/**
	 * Filliben (1975) Heuristic
	 */

	public static final double RELIEF_FILLIBEN_1975 = 1.;

	/**
	 * Construct the Standard Version of the PlottingPositionGeneratorHeuristic
	 * 
	 * @param orderStatisticCount The Order Statistic Count
	 * 
	 * @return Standard Version of the PlottingPositionGeneratorHeuristic
	 */

	public static final PlottingPositionGeneratorHeuristic Standard (
		final int orderStatisticCount)
	{
		try
		{
			return new PlottingPositionGeneratorHeuristic (
				orderStatisticCount,
				RELIEF_STANDARD
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Bernard Bos-Levenbach (1953) Version of the PlottingPositionGeneratorHeuristic
	 * 
	 * @param orderStatisticCount The Order Statistic Count
	 * 
	 * @return Bernard Bos-Levenbach (1953) Version of the PlottingPositionGeneratorHeuristic
	 */

	public static final PlottingPositionGeneratorHeuristic BernardBosLevenbach1953 (
		final int orderStatisticCount)
	{
		try
		{
			return new PlottingPositionGeneratorHeuristic (
				orderStatisticCount,
				RELIEF_BERNARD_BOS_LEVENBACH_1953
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the NIST (2013) Version of the PlottingPositionGeneratorHeuristic
	 * 
	 * @param orderStatisticCount The Order Statistic Count
	 * 
	 * @return NIST (2013) Version of the PlottingPositionGeneratorHeuristic
	 */

	public static final PlottingPositionGeneratorHeuristic NIST2013 (
		final int orderStatisticCount)
	{
		try
		{
			return new PlottingPositionGeneratorHeuristic (
				orderStatisticCount,
				RELIEF_NIST_2013
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Yu and Huang (2001) Version of the PlottingPositionGeneratorHeuristic
	 * 
	 * @param orderStatisticCount The Order Statistic Count
	 * 
	 * @return Yu and Huang (2001) Version of the PlottingPositionGeneratorHeuristic
	 */

	public static final PlottingPositionGeneratorHeuristic YuHuang2001 (
		final int orderStatisticCount)
	{
		try
		{
			return new PlottingPositionGeneratorHeuristic (
				orderStatisticCount,
				RELIEF_YU_HUANG_2001
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the BMDP (2018) Version of the PlottingPositionGeneratorHeuristic
	 * 
	 * @param orderStatisticCount The Order Statistic Count
	 * 
	 * @return BMDP (2018) Version of the PlottingPositionGeneratorHeuristic
	 */

	public static final PlottingPositionGeneratorHeuristic BMDP2018 (
		final int orderStatisticCount)
	{
		try
		{
			return new PlottingPositionGeneratorHeuristic (
				orderStatisticCount,
				RELIEF_BMDP_2018
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Blom (1958) Version of the PlottingPositionGeneratorHeuristic
	 * 
	 * @param orderStatisticCount The Order Statistic Count
	 * 
	 * @return Blom (1958) Version of the PlottingPositionGeneratorHeuristic
	 */

	public static final PlottingPositionGeneratorHeuristic Blom1958 (
		final int orderStatisticCount)
	{
		try
		{
			return new PlottingPositionGeneratorHeuristic (
				orderStatisticCount,
				RELIEF_BLOM_1958
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Cunnane (1978) Version of the PlottingPositionGeneratorHeuristic
	 * 
	 * @param orderStatisticCount The Order Statistic Count
	 * 
	 * @return Cunnane (1978) Version of the PlottingPositionGeneratorHeuristic
	 */

	public static final PlottingPositionGeneratorHeuristic Cunnane1978 (
		final int orderStatisticCount)
	{
		try
		{
			return new PlottingPositionGeneratorHeuristic (
				orderStatisticCount,
				RELIEF_CUNNANE_1978
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Gringorten (1963) Version of the PlottingPositionGeneratorHeuristic
	 * 
	 * @param orderStatisticCount The Order Statistic Count
	 * 
	 * @return Gringorten (1963) Version of the PlottingPositionGeneratorHeuristic
	 */

	public static final PlottingPositionGeneratorHeuristic Gringorten1963 (
		final int orderStatisticCount)
	{
		try
		{
			return new PlottingPositionGeneratorHeuristic (
				orderStatisticCount,
				RELIEF_GRINGORTEN_1963
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Hazen (1913) Version of the PlottingPositionGeneratorHeuristic
	 * 
	 * @param orderStatisticCount The Order Statistic Count
	 * 
	 * @return Hazen (1913) Version of the PlottingPositionGeneratorHeuristic
	 */

	public static final PlottingPositionGeneratorHeuristic Hazen1913 (
		final int orderStatisticCount)
	{
		try
		{
			return new PlottingPositionGeneratorHeuristic (
				orderStatisticCount,
				RELIEF_HAZEN_1913
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Larsen, Currant, and Hunt (1980) Version of the PlottingPositionGeneratorHeuristic
	 * 
	 * @param orderStatisticCount The Order Statistic Count
	 * 
	 * @return Larsen, Currant, and Hunt (1980) Version of the PlottingPositionGeneratorHeuristic
	 */

	public static final PlottingPositionGeneratorHeuristic LarsenCurrantHunt1980 (
		final int orderStatisticCount)
	{
		try
		{
			return new PlottingPositionGeneratorHeuristic (
				orderStatisticCount,
				RELIEF_LARSEN_CURRANT_HUNT_1980
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Filliben (1975) Version of the PlottingPositionGeneratorHeuristic
	 * 
	 * @param orderStatisticCount The Order Statistic Count
	 * 
	 * @return Filliben (1975) Version of the PlottingPositionGeneratorHeuristic
	 */

	public static final PlottingPositionGeneratorHeuristic Filliben1975 (
		final int orderStatisticCount)
	{
		try
		{
			return new PlottingPositionGeneratorHeuristic (
				orderStatisticCount,
				RELIEF_FILLIBEN_1975
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	private double _relief = java.lang.Double.NaN;

	/**
	 * PlottingPositionGeneratorHeuristic Constructor
	 * 
	 * @param orderStatisticCount Count of Order Statistics
	 * @param relief Leading Relief
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public PlottingPositionGeneratorHeuristic (
		final int orderStatisticCount,
		final double relief)
		throws java.lang.Exception
	{
		super (orderStatisticCount);

		if (!org.drip.numerical.common.NumberUtil.IsValid (_relief = relief) || 0. > _relief || 1. < _relief)
		{
			throw new java.lang.Exception
				("PlottingPositionGeneratorHeuristic Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Leading Relief
	 * 
	 * @return The Leading Relief
	 */

	public double relief()
	{
		return _relief;
	}

	@Override public org.drip.validation.quantile.PlottingPosition[] generate()
	{
		int orderStatisticCount = orderStatisticCount();

		org.drip.validation.quantile.PlottingPosition[] plottingPositionArray = new
			org.drip.validation.quantile.PlottingPosition[orderStatisticCount];

		for (int orderStatisticIndex = 1; orderStatisticIndex <= orderStatisticCount; ++orderStatisticIndex)
		{
			try
			{
				plottingPositionArray[orderStatisticIndex - 1] =
					new org.drip.validation.quantile.PlottingPosition (
						orderStatisticIndex,
						(((double) orderStatisticIndex) - _relief) /
							(1. + orderStatisticCount - 2. * _relief)
					);
			}
			catch (java.lang.Exception e)
			{
				e.printStackTrace();

				return null;
			}
		}

		return plottingPositionArray;
	}
}
