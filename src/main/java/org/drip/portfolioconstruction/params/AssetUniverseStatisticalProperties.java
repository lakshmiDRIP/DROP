
package org.drip.portfolioconstruction.params;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
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
 * <i>AssetUniverseStatisticalProperties</i> holds the Statistical Properties of a Pool of Assets.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AssetAllocationAnalyticsLibrary.md">Asset Allocation Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/README.md">Portfolio Construction under Allocation Constraints</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/params/README.md">Asset Universe Statistical Properties Container</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class AssetUniverseStatisticalProperties
{
	private double _riskFreeRate = java.lang.Double.NaN;

	private
		org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.portfolioconstruction.params.AssetStatisticalProperties>
			_assetUniverseStatisticalPropertiesMap = new
				org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.portfolioconstruction.params.AssetStatisticalProperties>();

	private org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double> _correlationMap =
		new org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();

	/**
	 * Construct an Instance of AssetUniverseStatisticalProperties from the corresponding MultivariateMetrics
	 * 	Instance
	 * 
	 * @param multivariateMoments The MultivariateMetrics Instance
	 * 
	 * @return The AssetUniverseStatisticalProperties Instance
	 */

	public static final AssetUniverseStatisticalProperties FromMultivariateMetrics (
		final org.drip.measure.statistics.MultivariateMoments multivariateMoments)
	{
		if (null == multivariateMoments)
		{
			return null;
		}

		java.util.Set<java.lang.String> assetSet = multivariateMoments.variateList();

		if (null == assetSet || 0 == assetSet.size())
		{
			return null;
		}

		try
		{
			AssetUniverseStatisticalProperties assetUniverseStatisticalProperties =
				new AssetUniverseStatisticalProperties (0.);

			for (java.lang.String asset : assetSet)
			{
				if (!assetUniverseStatisticalProperties.setAssetStatisticalProperties (
					new org.drip.portfolioconstruction.params.AssetStatisticalProperties (
						asset,
						asset,
						multivariateMoments.mean (asset),
						multivariateMoments.variance (asset)
					)
				))
				{
					return null;
				}
			}

			for (java.lang.String asset1 : assetSet)
			{
				for (java.lang.String asset2 : assetSet)
				{
					if (!assetUniverseStatisticalProperties.setCorrelation (
						asset1,
						asset2,
						multivariateMoments.correlation (
							asset1,
							asset2
						)
					))
					{
						return null;
					}
				}
			}

			return assetUniverseStatisticalProperties;
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * AssetUniverseStatisticalProperties Constructor
	 * 
	 * @param riskFreeRate The Risk Free Rate
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public AssetUniverseStatisticalProperties (
		final double riskFreeRate)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (
			_riskFreeRate = riskFreeRate
		))
		{
			throw new java.lang.Exception (
				"AssetUniverseStatisticalProperties Constructor => Invalid Inputs"
			);
		}
	}

	/**
	 * Set the AssetStatisticalProperties Instance
	 * 
	 * @param assetStatisticalProperties AssetStatisticalProperties Instance
	 * 
	 * @return TRUE - AssetStatisticalProperties Instance Successfully added
	 */

	public boolean setAssetStatisticalProperties (
		final org.drip.portfolioconstruction.params.AssetStatisticalProperties assetStatisticalProperties)
	{
		if (null == assetStatisticalProperties)
		{
			return false;
		}

		_assetUniverseStatisticalPropertiesMap.put (
			assetStatisticalProperties.id(),
			assetStatisticalProperties
		);

		return true;
	}

	/**
	 * Set the Correlation Between the Specified Pair of Assets
	 * 
	 * @param id1 Asset #1
	 * @param id2 Asset #2
	 * @param correlation Cross-asset Correlation
	 * 
	 * @return Correlation Between the Specified Pair of Assets
	 */

	public boolean setCorrelation (
		final java.lang.String id1,
		final java.lang.String id2,
		final double correlation)
	{
		if (null == id1 || id1.isEmpty() ||
			null == id2 || id2.isEmpty() ||
			!org.drip.numerical.common.NumberUtil.IsValid (
				correlation
			) || 1. < correlation || -1. > correlation
		)
		{
			return false;
		}

		_correlationMap.put (
			id1 + "@#" + id2,
			correlation
		);

		_correlationMap.put (
			id2 + "@#" + id1,
			correlation
		);

		return true;
	}

	/**
	 * Retrieve the Risk Free Rate
	 * 
	 * @return The Risk Free Rate
	 */

	public double riskFreeRate()
	{
		return _riskFreeRate;
	}

	/**
	 * Retrieve the AssetStatisticalProperties Instance corresponding to the specified ID
	 * 
	 * @param id The AssetStatisticalProperties ID
	 * 
	 * @return The AssetStatisticalProperties Instance
	 */

	public org.drip.portfolioconstruction.params.AssetStatisticalProperties assetStatisticalProperties (
		final java.lang.String id)
	{
		return null == id || id.isEmpty() || !_assetUniverseStatisticalPropertiesMap.containsKey (
			id
		) ? null : _assetUniverseStatisticalPropertiesMap.get (
			id
		);
	}

	/**
	 * Retrieve the Correlation between the Specified Assets
	 * 
	 * @param id1 Asset #1
	 * @param id2 Asset #2
	 * 
	 * @return Correlation between the Specified Assets
	 * 
	 * @throws java.lang.Exception Thtrown if the Inputs are Invalid
	 */

	public double correlation (
		final java.lang.String id1,
		final java.lang.String id2)
		throws java.lang.Exception
	{
		if (null == id1 || id1.isEmpty() ||
			null == id2 || id2.isEmpty())
		{
			throw new java.lang.Exception (
				"AssetUniverseStatisticalProperties::correlation => Invalid Inputs"
			);
		}

		if (id1.equalsIgnoreCase (
			id2
		))
		{
			return 1.;
		}

		java.lang.String strCorrelationSlot = id1 + "@#" + id2;

		if (!_correlationMap.containsKey (
			strCorrelationSlot
		))
		{
			throw new java.lang.Exception (
				"AssetUniverseStatisticalProperties::correlation => Invalid Inputs"
			);
		}

		return _correlationMap.get (
			strCorrelationSlot
		);
	}

	/**
	 * Retrieve the Asset Expected Returns Array
	 * 
	 * @param idArray Array of Asset IDs
	 * 
	 * @return The Asset Covariance Matrix
	 */

	public double[] expectedReturns (
		final java.lang.String[] idArray)
	{
		if (null == idArray)
		{
			return null;
		}

		int assetCount = idArray.length;
		double[] expectedReturnsArray = new double[assetCount];

		if (0 == assetCount)
		{
			return null;
		}

		for (int assetIndex = 0;
			assetIndex < assetCount;
			++assetIndex)
		{
			org.drip.portfolioconstruction.params.AssetStatisticalProperties assetStatisticalProperties =
				assetStatisticalProperties (
					idArray[assetIndex]
				);

			if (null == assetStatisticalProperties)
			{
				return null;
			}

			expectedReturnsArray[assetIndex] = assetStatisticalProperties.expectedReturn();
		}

		return expectedReturnsArray;
	}

	/**
	 * Retrieve the Asset Covariance Matrix
	 * 
	 * @param idArray Array of Asset IDs
	 * 
	 * @return The Asset Covariance Matrix
	 */

	public double[][] covariance (
		final java.lang.String[] idArray)
	{
		if (null == idArray)
		{
			return null;
		}

		int assetCount = idArray.length;
		double[][] covarianceMatrix = new double[assetCount][assetCount];

		if (0 == assetCount)
		{
			return null;
		}

		for (int assetIndexI = 0;
			assetIndexI < assetCount;
			++assetIndexI)
		{
			org.drip.portfolioconstruction.params.AssetStatisticalProperties assetStatisticalPropertiesI =
				assetStatisticalProperties (idArray[assetIndexI]);

			if (null == assetStatisticalPropertiesI)
			{
				return null;
			}

			double dblVarianceI = assetStatisticalPropertiesI.variance();

			for (int assetIndexJ = 0;
				assetIndexJ < assetCount;
				++assetIndexJ)
			{
				org.drip.portfolioconstruction.params.AssetStatisticalProperties assetStatisticalPropertiesJ
					= assetStatisticalProperties (
						idArray[assetIndexJ]
					);

				if (null == assetStatisticalPropertiesJ)
				{
					return null;
				}

				try
				{
					covarianceMatrix[assetIndexI][assetIndexJ] = java.lang.Math.sqrt (
						dblVarianceI * assetStatisticalPropertiesJ.variance()
					) * correlation (
						idArray[assetIndexI],
						idArray[assetIndexJ]
					);
				}
				catch (java.lang.Exception e)
				{
					e.printStackTrace();

					return null;
				}
			}
		}

		return covarianceMatrix;
	}
}
