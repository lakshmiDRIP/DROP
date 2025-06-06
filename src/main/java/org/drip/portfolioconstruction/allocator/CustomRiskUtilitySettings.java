
package org.drip.portfolioconstruction.allocator;

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
 * Copyright (C) 2016 Lakshmi Krishnamurthy
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
 * <i>CustomRiskUtilitySettings</i> contains the settings used to generate the Risk Objective Utility
 * Function. It accommodates both the Risk Tolerance and Risk Aversion Variants.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AssetAllocationAnalyticsLibrary.md">Asset Allocation Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/README.md">Portfolio Construction under Allocation Constraints</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/allocator/README.md">MVO Based Portfolio Allocation Construction</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class CustomRiskUtilitySettings
{
	private double _riskAversion = java.lang.Double.NaN;
	private double _riskTolerance = java.lang.Double.NaN;

	/**
	 * The Variance Minimizer CustomRiskUtilitySettings Instance
	 * 
	 * @return The Variance Minimizer CustomRiskUtilitySettings Instance
	 */

	public static final CustomRiskUtilitySettings VarianceMinimizer()
	{
		try
		{
			return new CustomRiskUtilitySettings (
				1.,
				0.
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * The Risk Tolerant Variance Minimizer CustomRiskUtilitySettings Instance
	 * 
	 * @param riskTolerance The Risk Tolerance Parameter
	 * 
	 * @return The Risk Tolerant Variance Minimizer CustomRiskUtilitySettings Instance
	 */

	public static final CustomRiskUtilitySettings RiskTolerant (
		final double riskTolerance)
	{
		try
		{
			return new CustomRiskUtilitySettings (
				1.,
				riskTolerance
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * The Risk Aversion Variance Minimizer CustomRiskUtilitySettings Instance
	 * 
	 * @param riskAversion The Risk Aversion Parameter
	 * 
	 * @return The Risk Aversion Variance Minimizer CustomRiskUtilitySettings Instance
	 */

	public static final CustomRiskUtilitySettings RiskAversion (
		final double riskAversion)
	{
		try
		{
			return new CustomRiskUtilitySettings (
				riskAversion,
				1.
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * CustomRiskUtilitySettings Constructor
	 * 
	 * @param riskAversion The Risk Aversion Parameter
	 * @param riskTolerance The Risk Tolerance Parameter
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public CustomRiskUtilitySettings (
		final double riskAversion,
		final double riskTolerance)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (_riskAversion = riskAversion) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_riskTolerance = riskTolerance) ||
				0. > _riskTolerance)
		{
			throw new java.lang.Exception ("CustomRiskUtilitySettings Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Risk Aversion Factor
	 * 
	 * @return The Risk Aversion Factor
	 */

	public double riskAversion()
	{
		return _riskAversion;
	}

	/**
	 * Retrieve the Risk Tolerance Factor
	 * 
	 * @return The Risk Tolerance Factor
	 */

	public double riskTolerance()
	{
		return _riskTolerance;
	}

	/**
	 * Retrieve the Custom Risk Objective Utility Multivariate
	 * 
	 * @param assetIDArray Array of the Asset IDs
	 * @param assetUniverseStatisticalProperties The Asset Universe Statistical Properties Instance
	 * 
	 * @return The Custom Risk Objective Utility Multivariate
	 */

	public org.drip.function.definition.RdToR1 riskObjectiveUtility (
		final java.lang.String[] assetIDArray,
		final org.drip.portfolioconstruction.params.AssetUniverseStatisticalProperties
			assetUniverseStatisticalProperties)
	{
		if (null == assetIDArray || null == assetUniverseStatisticalProperties)
		{
			return null;
		}

		int assetCount = assetIDArray.length;
		double[] expectedReturnsArray = new double[assetCount];
		double[][] covarianceMatrix = new double[assetCount][assetCount];
		org.drip.portfolioconstruction.params.AssetStatisticalProperties[] assetStatisticalPropertiesArray =
			new org.drip.portfolioconstruction.params.AssetStatisticalProperties[assetCount];

		if (0 == assetCount)
		{
			return null;
		}

		for (int assetIndex = 0; assetIndex < assetCount; ++assetIndex)
		{
			if (null == (assetStatisticalPropertiesArray[assetIndex] =
				assetUniverseStatisticalProperties.assetStatisticalProperties (assetIDArray[assetIndex])))
			{
				return null;
			}

			expectedReturnsArray[assetIndex] = assetStatisticalPropertiesArray[assetIndex].expectedReturn();
		}

		for (int assetIndexI = 0; assetIndexI < assetCount; ++assetIndexI)
		{
			double dblVarianceI = assetStatisticalPropertiesArray[assetIndexI].variance();

			for (int assetIndexJ = 0; assetIndexJ < assetCount; ++assetIndexJ)
			{
				try
				{
					covarianceMatrix[assetIndexI][assetIndexJ] = java.lang.Math.sqrt (
						dblVarianceI * assetStatisticalPropertiesArray[assetIndexJ].variance()
					) * (
						assetIndexI == assetIndexJ ? 1. : assetUniverseStatisticalProperties.correlation (
							assetIDArray[assetIndexI], assetIDArray[assetIndexJ]
						)
					);
				}
				catch (java.lang.Exception e)
				{
					e.printStackTrace();

					return null;
				}
			}
		}

		try
		{
			return new org.drip.function.rdtor1.RiskObjectiveUtilityMultivariate (
				covarianceMatrix,
				expectedReturnsArray,
				_riskAversion,
				_riskTolerance,
				assetUniverseStatisticalProperties.riskFreeRate()
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
