
package org.drip.portfolioconstruction.constraint;

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
 * <i>LimitRiskTermVariance</i> holds the Details of a Variance Based Limit Risk Constraint Term.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AssetAllocationAnalyticsLibrary.md">Asset Allocation Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction">Portfolio Construction</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/constraint">Constraint</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class LimitRiskTermVariance
	extends org.drip.portfolioconstruction.constraint.LimitRiskTerm
{
	private double[] _benchmarkHoldingsArray = null;

	/**
	 * LimitRiskTermVariance Constructor
	 * 
	 * @param name Name of the LimitRiskTermVariance Constraint
	 * @param scope Scope of the LimitRiskTermVariance Constraint
	 * @param unit Unit of the LimitRiskTermVariance Constraint
	 * @param minimum Minimum Limit Value of the LimitRiskTermVariance Constraint
	 * @param maximum Maximum Limit Value of the LimitRiskTermVariance Constraint
	 * @param assetCovarianceMatrix Asset Co-variance Matrix
	 * @param benchmarkHoldingsArray Array of the Benchmark Holdings Array
	 * 
	 * @throws java.lang.Exception Throw if the Inputs are Invalid
	 */

	public LimitRiskTermVariance (
		final java.lang.String name,
		final org.drip.portfolioconstruction.optimizer.Scope scope,
		final org.drip.portfolioconstruction.optimizer.Unit unit,
		final double minimum,
		final double maximum,
		final double[][] assetCovarianceMatrix,
		final double[] benchmarkHoldingsArray)
		throws java.lang.Exception
	{
		super (
			name,
			"CT_LIMIT_TOTAL_RISK",
			"Limits the Variance Based Total Risk",
			scope,
			unit,
			minimum,
			maximum,
			assetCovarianceMatrix
		);

		int benchmarkHoldingsCount = null == (_benchmarkHoldingsArray = benchmarkHoldingsArray) ?
			0 : _benchmarkHoldingsArray.length;

		if (0 != benchmarkHoldingsCount && (assetCovarianceMatrix[0].length != benchmarkHoldingsCount ||
			!org.drip.numerical.common.NumberUtil.IsValid (_benchmarkHoldingsArray)))
		{
			throw new java.lang.Exception ("LimitRiskTermVariance Constructor => Invalid Benchmark");
		}
	}

	/**
	 * Retrieve the Constricted Benchmark Holdings
	 * 
	 * @return The Constricted Benchmark Holdings
	 */

	public double[] benchmarkHoldingsArray()
	{
		return _benchmarkHoldingsArray;
	}

	@Override public org.drip.function.definition.RdToR1 rdtoR1()
	{
		return new org.drip.function.definition.RdToR1 (null)
		{
			@Override public int dimension()
			{
				return assetCovarianceMatrix().length;
			}

			@Override public double evaluate (
				final double[] finalHoldingsArray)
				throws java.lang.Exception
			{
				double[][] assetCovarianceMatrix = assetCovarianceMatrix();

				int assetCount = assetCovarianceMatrix.length;
				double variance = 0;

				if (null == finalHoldingsArray ||
					!org.drip.numerical.common.NumberUtil.IsValid (finalHoldingsArray) ||
					finalHoldingsArray.length != assetCount)
				{
					throw new java.lang.Exception
					("LimitRiskTermVariance::rdToR1::evaluate => Invalid Variate Dimension");
				}

				for (int assetIndexI = 0; assetIndexI < assetCount; ++assetIndexI)
				{
					double dblHoldingsOffsetI = finalHoldingsArray[assetIndexI];

					if (null != _benchmarkHoldingsArray)
					{
						dblHoldingsOffsetI -= _benchmarkHoldingsArray[assetIndexI];
					}

					for (int assetIndexJ = 0; assetIndexJ < assetCount; ++assetIndexJ)
					{
						double dblHoldingsOffsetJ = finalHoldingsArray[assetIndexJ];

						if (null != _benchmarkHoldingsArray)
						{
							dblHoldingsOffsetJ -= _benchmarkHoldingsArray[assetIndexJ];
						}

						variance += dblHoldingsOffsetI * assetCovarianceMatrix[assetIndexI][assetIndexJ] *
							dblHoldingsOffsetJ;
					}
				}

				return variance;
			}
		};
	}
}
