
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
 * <i>LimitRiskTermMarginal</i> holds the Details of a Relative Marginal Contribution Based Limit Risk
 * Constraint Term.
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

public class LimitRiskTermMarginal
	extends org.drip.portfolioconstruction.constraint.LimitRiskTerm
{
	private double[] _initialHoldingsArray = null;

	/**
	 * LimitRiskTermMarginal Constructor
	 * 
	 * @param name Name of the LimitRiskTermMarginal Constraint
	 * @param scope Scope of the LimitRiskTermMarginal Constraint
	 * @param unit Unit of the LimitRiskTermMarginal Constraint
	 * @param minimum Minimum Limit Value of the LimitRiskTermMarginal Constraint
	 * @param maximum Maximum Limit Value of the LimitRiskTermMarginal Constraint
	 * @param assetCovarianceMatrix Asset Co-variance
	 * @param initialHoldingsArray Array of the Initial Holdings
	 * 
	 * @throws java.lang.Exception Throw if the Inputs are Invalid
	 */

	public LimitRiskTermMarginal (
		final java.lang.String name,
		final org.drip.portfolioconstruction.optimizer.Scope scope,
		final org.drip.portfolioconstruction.optimizer.Unit unit,
		final double minimum,
		final double maximum,
		final double[][] assetCovarianceMatrix,
		final double[] initialHoldingsArray)
		throws java.lang.Exception
	{
		super (
			name,
			"CT_LIMIT_MARGINAL_RISK_CONTRIBUTION",
			"Limits the Marginal Contribution to the Total Risk",
			scope,
			unit,
			minimum,
			maximum,
			assetCovarianceMatrix
		);

		if (null == (_initialHoldingsArray = initialHoldingsArray) ||
			_initialHoldingsArray.length != assetCovarianceMatrix[0].length ||
			!org.drip.numerical.common.NumberUtil.IsValid (_initialHoldingsArray))
		{
			throw new java.lang.Exception ("LimitRiskTermMarginal Constructor => Invalid Initial Holdings");
		}
	}

	/**
	 * Retrieve the Initial Holdings Array
	 * 
	 * @return The Initial Holdings Array
	 */

	public double[] initialHoldingsArray()
	{
		return _initialHoldingsArray;
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
				double marginalVariance = 0;

				if (null == finalHoldingsArray ||
					!org.drip.numerical.common.NumberUtil.IsValid (finalHoldingsArray) ||
					finalHoldingsArray.length != assetCount)
				{
					throw new java.lang.Exception
						("LimitRiskTermMarginal::rdToR1::evaluate => Invalid Variate Dimension");
				}

				for (int assetIndexI = 0; assetIndexI < assetCount; ++assetIndexI)
				{
					double dblHoldingsDifferentialI = finalHoldingsArray[assetIndexI] -
						_initialHoldingsArray[assetIndexI];

					for (int assetIndexJ = 0; assetIndexJ < assetCount; ++assetIndexJ)
					{
						marginalVariance += dblHoldingsDifferentialI *
							assetCovarianceMatrix[assetIndexI][assetIndexJ] * (
								finalHoldingsArray[assetIndexJ] - _initialHoldingsArray[assetIndexJ]
							);
					}
				}

				return marginalVariance;
			}
		};
	}
}
