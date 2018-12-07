
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

public class LimitRiskTermMarginal extends org.drip.portfolioconstruction.constraint.LimitRiskTerm
{
	private double[] _adblInitialHoldings = null;

	/**
	 * LimitRiskTermMarginal Constructor
	 * 
	 * @param strName Name of the LimitRiskTermMarginal Constraint
	 * @param scope Scope of the LimitRiskTermMarginal Constraint
	 * @param unit Unit of the LimitRiskTermMarginal Constraint
	 * @param dblMinimum Minimum Limit Value of the LimitRiskTermMarginal Constraint
	 * @param dblMaximum Maximum Limit Value of the LimitRiskTermMarginal Constraint
	 * @param aadblAssetCovariance Asset Co-variance
	 * @param adblInitialHoldings Array of the Initial Holdings
	 * 
	 * @throws java.lang.Exception Throw if the Inputs are Invalid
	 */

	public LimitRiskTermMarginal (
		final java.lang.String strName,
		final org.drip.portfolioconstruction.optimizer.Scope scope,
		final org.drip.portfolioconstruction.optimizer.Unit unit,
		final double dblMinimum,
		final double dblMaximum,
		final double[][] aadblAssetCovariance,
		final double[] adblInitialHoldings)
		throws java.lang.Exception
	{
		super (
			strName,
			"CT_LIMIT_MARGINAL_RISK_CONTRIBUTION",
			"Limits the Marginal Contribution to the Total Risk",
			scope,
			unit,
			dblMinimum,
			dblMaximum,
			aadblAssetCovariance
		);

		if (null == (_adblInitialHoldings = adblInitialHoldings) ||
			_adblInitialHoldings.length != aadblAssetCovariance[0].length ||
			!org.drip.quant.common.NumberUtil.IsValid (_adblInitialHoldings))
			throw new java.lang.Exception ("LimitRiskTermMarginal Constructor => Invalid Initial Holdings");
	}

	/**
	 * Retrieve the Initial Holdings Array
	 * 
	 * @return The Initial Holdings Array
	 */

	public double[] initialHoldings()
	{
		return _adblInitialHoldings;
	}

	@Override public org.drip.function.definition.RdToR1 rdtoR1()
	{
		return new org.drip.function.definition.RdToR1 (null)
		{
			@Override public int dimension()
			{
				return assetCovariance().length;
			}

			@Override public double evaluate (
				final double[] adblFinalHoldings)
				throws java.lang.Exception
			{
				double[][] aadblAssetCovariance = assetCovariance();

				int iNumAsset = aadblAssetCovariance.length;
				double dblMarginalVariance = 0;

				if (null == adblFinalHoldings || !org.drip.quant.common.NumberUtil.IsValid
					(adblFinalHoldings) || adblFinalHoldings.length != iNumAsset)
					throw new java.lang.Exception
						("LimitRiskTermMarginal::rdToR1::evaluate => Invalid Variate Dimension");

				for (int i = 0; i < iNumAsset; ++i)
				{
					double dblHoldingsDifferentialI = adblFinalHoldings[i] - _adblInitialHoldings[i];

					for (int j = 0; j < iNumAsset; ++j)
						dblMarginalVariance += dblHoldingsDifferentialI * aadblAssetCovariance[i][j] *
							(adblFinalHoldings[j] - _adblInitialHoldings[j]);
				}

				return dblMarginalVariance;
			}
		};
	}
}
