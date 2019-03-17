
package org.drip.exposure.regressiontrade;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting risk, transaction costs, exposure, margin
 *  	calculations, valuation adjustment, and portfolio construction within and across fixed income,
 *  	credit, commodity, equity, FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three modules:
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
 * 	- Statistical Learning
 * 	- Numerical Optimizer
 * 	- Spline Builder
 * 	- Algorithm Support
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
 * <i>AndersenPykhtinSokolEnsemble</i> adjusts the Variation Margin, computes Path-wise Local Volatility, and
 * eventually estimates the Path-wise Unadjusted Variation Margin across the Suite of Simulated Paths. The
 * References are:
 *  
 * <br><br>
 *  	<ul>
 *  		<li>
 *  			Andersen, L. B. G., M. Pykhtin, and A. Sokol (2017): Re-thinking Margin Period of Risk
 *  				https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2902737 <b>eSSRN</b>
 *  		</li>
 *  		<li>
 *  			Andersen, L. B. G., M. Pykhtin, and A. Sokol (2017): Credit Exposure in the Presence of
 *  				Initial Margin https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2806156 <b>eSSRN</b>
 *  		</li>
 *  		<li>
 *  			Albanese, C., and L. Andersen (2014): Accounting for OTC Derivatives: Funding Adjustments and
 *  				the Re-Hypothecation Option https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2482955
 *  				<b>eSSRN</b>
 *  		</li>
 *  		<li>
 *  			Burgard, C., and M. Kjaer (2017): Derivatives Funding, Netting, and Accounting
 *  				https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2534011 <b>eSSRN</b>
 *  		</li>
 *  		<li>
 *  			Pykhtin, M. (2009): Modeling Counter-party Credit Exposure in the Presence of Margin
 *  				Agreements http://www.risk-europe.com/protected/michael-pykhtin.pdf
 *  		</li>
 *  	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ExposureAnalyticsLibrary.md">Exposure Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/README.md">Exposure</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/regressiontrade/README.md">Regression Trade</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class AndersenPykhtinSokolEnsemble
{
	private int[] _sparseExposureDateArray = null;
	private org.drip.exposure.universe.MarketPath[] _marketPathArray = null;
	private org.drip.exposure.mpor.VariationMarginTradePaymentVertex _marginTradePaymentGenerator = null;

	/**
	 * AndersenPykhtinSokolEnsemble Constructor
	 * 
	 * @param marginTradePaymentGenerator The Variation Margin Estimate and the Trade Payment Generator
	 * @param marketPathArray Array of Market Paths
	 * @param sparseExposureDateArray Array of Sparse Exposure Dates
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public AndersenPykhtinSokolEnsemble (
		final org.drip.exposure.mpor.VariationMarginTradePaymentVertex marginTradePaymentGenerator,
		final org.drip.exposure.universe.MarketPath[] marketPathArray,
		final int[] sparseExposureDateArray)
		throws java.lang.Exception
	{
		if (null == (_marginTradePaymentGenerator = marginTradePaymentGenerator) ||
			null == (_marketPathArray = marketPathArray) ||
			null == (_sparseExposureDateArray = sparseExposureDateArray))
		{
			throw new java.lang.Exception ("AndersenPykhtinSokolEnsemble => Invalid Inputs");
		}

		int pathCount = _marketPathArray.length;
		int sparseExposureDateCount = _sparseExposureDateArray.length;

		if (0 == pathCount || 0 == sparseExposureDateCount)
		{
			throw new java.lang.Exception ("AndersenPykhtinSokolEnsemble => Invalid Inputs");
		}

		for (int pathIndex = 0; pathIndex < pathCount; ++pathIndex)
		{
			if (null == _marketPathArray[pathIndex])
			{
				throw new java.lang.Exception ("AndersenPykhtinSokolEnsemble => Invalid Inputs");
			}
		}
	}

	/**
	 * Retrieve the Path-wise Variation Margin/Trade Payment Generator
	 * 
	 * @return The Path-wise Variation Margin/Trade Payment Generator
	 */

	public org.drip.exposure.mpor.VariationMarginTradePaymentVertex marginTradePaymentGenerator()
	{
		return _marginTradePaymentGenerator;
	}

	/**
	 * Retrieve the Array of Market Paths
	 * 
	 * @return The Array of Market Paths
	 */

	public org.drip.exposure.universe.MarketPath[] marketPathArray()
	{
		return _marketPathArray;
	}

	/**
	 * Retrieve the Array of Sparse Exposure Dates
	 * 
	 * @return The Array of Sparse Exposure Dates
	 */

	public int[] sparseExposureDateArray()
	{
		return _sparseExposureDateArray;
	}

	/**
	 * Retrieve the Number of Simulation Paths
	 * 
	 * @return The Number of Simulation Paths
	 */

	public int pathCount()
	{
		return _marketPathArray.length;
	}

	/**
	 * Retrieve the Number of Sparse Exposure Dates
	 * 
	 * @return The Number of Sparse Exposure Dates
	 */

	public int sparseExposureDateCount()
	{
		return _sparseExposureDateArray.length;
	}

	/**
	 * Generate the Path-wise Adjusted Variation Margin Estimator
	 * 
	 * @return The Path-wise Adjusted Variation Margin Estimator
	 */

	public org.drip.exposure.regressiontrade.AdjustedVariationMarginEstimator[]
		pathAdjustedVariationMarginEstimator()
	{
		int pathCount = _marketPathArray.length;
		org.drip.exposure.regressiontrade.AdjustedVariationMarginEstimator[]
			adjustedVariationMarginEstimatorArray = new
				org.drip.exposure.regressiontrade.AdjustedVariationMarginEstimator[pathCount];

		for (int pathIndex = 0; pathIndex < pathCount; ++pathIndex)
		{
			try
			{
				adjustedVariationMarginEstimatorArray[pathIndex] = new
					org.drip.exposure.regressiontrade.AdjustedVariationMarginEstimator (
						_marginTradePaymentGenerator,
						_marketPathArray[pathIndex]
					);
			}
			catch (java.lang.Exception e)
			{
				e.printStackTrace();

				return null;
			}
		}

		return adjustedVariationMarginEstimatorArray;
	}

	/**
	 * Generate the Path-wise Adjusted Variation Margin Estimate
	 * 
	 * @return The Path-wise Adjusted Variation Margin Estimate
	 */

	public org.drip.exposure.regressiontrade.AdjustedVariationMarginEstimate[]
		pathAdjustedVariationMarginEstimate()
	{
		int pathCount = _marketPathArray.length;
		org.drip.exposure.regressiontrade.AdjustedVariationMarginEstimate[]
			adjustedVariationMarginEstimateArray = new
				org.drip.exposure.regressiontrade.AdjustedVariationMarginEstimate[pathCount];

		for (int pathIndex = 0; pathIndex < pathCount; ++pathIndex)
		{
			try
			{
				adjustedVariationMarginEstimateArray[pathIndex] = new
					org.drip.exposure.regressiontrade.AdjustedVariationMarginEstimator (
						_marginTradePaymentGenerator,
						_marketPathArray[pathIndex]
					).adjustedVariationMarginEstimate (_sparseExposureDateArray);
			}
			catch (java.lang.Exception e)
			{
				e.printStackTrace();

				return null;
			}
		}

		return adjustedVariationMarginEstimateArray;
	}

	/**
	 * Generate the Ensemble Adjusted Variation Margin Dynamics
	 * 
	 * @return The Ensemble Adjusted Variation Margin Dynamics
	 */

	public org.drip.exposure.regressiontrade.AdjustedVariationMarginDynamics
		ensembleAdjustedVariationMarginDynamics()
	{
		int pathCount = _marketPathArray.length;
		org.drip.exposure.regressiontrade.AdjustedVariationMarginEstimate[]
			adjustedVariationMarginEstimateArray = new
				org.drip.exposure.regressiontrade.AdjustedVariationMarginEstimate[pathCount];

		for (int pathIndex = 0; pathIndex < pathCount; ++pathIndex)
		{
			try
			{
				adjustedVariationMarginEstimateArray[pathIndex] = new
					org.drip.exposure.regressiontrade.AdjustedVariationMarginEstimator (
						_marginTradePaymentGenerator,
						_marketPathArray[pathIndex]
					).adjustedVariationMarginEstimate (_sparseExposureDateArray);
			}
			catch (java.lang.Exception e)
			{
				e.printStackTrace();

				return null;
			}
		}

		try
		{
			return new org.drip.exposure.regressiontrade.AdjustedVariationMarginDynamics
				(adjustedVariationMarginEstimateArray);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the Ensemble Pillar Dynamics Array
	 * 
	 * @return The Ensemble Pillar Dynamics Array
	 */

	public org.drip.exposure.regression.PykhtinPillarDynamics[] ensemblePillarDynamics()
	{
		org.drip.exposure.regressiontrade.AdjustedVariationMarginDynamics adjustedVariationMarginDynamics =
			ensembleAdjustedVariationMarginDynamics();

		return null == adjustedVariationMarginDynamics ? null :
			adjustedVariationMarginDynamics.pillarDynamics();
	}

	/**
	 * Generate the Path-wise Dense Variation Margin Array
	 * 
	 * @param localVolatilityGenerationControl Local Volatility Generation Control
	 * @param wanderEnsemble The Wander Ensemble
	 * 
	 * @return The Path-wise Dense Variation Margin Array
	 */

	public double[][] denseVariationMargin (
		final org.drip.exposure.regression.LocalVolatilityGenerationControl localVolatilityGenerationControl,
		final double[][] wanderEnsemble)
	{
		if (null == wanderEnsemble)
		{
			return null;
		}

		int pathCount = _marketPathArray.length;
		double[][] denseVariationMargin = new double[pathCount][];
		int sparseExposureDateCount = _sparseExposureDateArray.length;
		org.drip.function.definition.R1ToR1[] sparseLocalVolatilityArray = new
			org.drip.function.definition.R1ToR1[sparseExposureDateCount];

		if (pathCount != wanderEnsemble.length)
		{
			return null;
		}

		for (int pathIndex = 0; pathIndex < pathCount; ++pathIndex)
		{
			if (null == wanderEnsemble[pathIndex] || 0 == wanderEnsemble[pathIndex].length ||
				!org.drip.numerical.common.NumberUtil.IsValid (wanderEnsemble[pathIndex]))
			{
				return null;
			}
		}

		org.drip.exposure.regressiontrade.AdjustedVariationMarginDynamics adjustedVariationMarginDynamics =
			ensembleAdjustedVariationMarginDynamics();

		org.drip.exposure.regression.PykhtinPillarDynamics[] pillarDynamicsArray =
			adjustedVariationMarginDynamics.pillarDynamics();

		for (int sparseExposureDateIndex = 0;
			sparseExposureDateIndex < sparseExposureDateCount;
			++sparseExposureDateIndex)
		{
			sparseLocalVolatilityArray[sparseExposureDateIndex] =
				pillarDynamicsArray[sparseExposureDateIndex].localVolatilityR1ToR1
					(localVolatilityGenerationControl);
		}

		org.drip.exposure.regressiontrade.AdjustedVariationMarginEstimate[]
			pathAdjustedVariationMarginEstimateArray =
				adjustedVariationMarginDynamics.adjustedVariationMarginEstimateArray();

		for (int pathIndex = 0; pathIndex < pathCount; ++pathIndex)
		{
			try
			{
				denseVariationMargin[pathIndex] = new
					org.drip.exposure.regression.AndersenPykhtinSokolStretch (
						_sparseExposureDateArray,
						pathAdjustedVariationMarginEstimateArray[pathIndex].adjustedVariationMarginEstimateArray(),
						sparseLocalVolatilityArray,
						pathAdjustedVariationMarginEstimateArray[pathIndex].denseTradePaymentArray()
					).denseExposure (wanderEnsemble[pathIndex]);
			}
			catch (java.lang.Exception e)
			{
				e.printStackTrace();
			}
		}

		return denseVariationMargin;
	}

	/**
	 * Generate the Dense Variation Margin Trajectory
	 * 
	 * @param localVolatilityGenerationControl Local Volatility Generation Control
	 * @param wanderEnsemble The Wander Ensemble
	 * 
	 * @return The Dense Variation Margin Trajectory
	 */

	public org.drip.exposure.regressiontrade.AndersenPykhtinSokolTrajectory[] denseTrajectory (
		final org.drip.exposure.regression.LocalVolatilityGenerationControl localVolatilityGenerationControl,
		final double[][] wanderEnsemble)
	{
		if (null == wanderEnsemble)
		{
			return null;
		}

		int pathCount = _marketPathArray.length;
		int denseExposureStartDate = _sparseExposureDateArray[0];
		int sparseExposureDateCount = _sparseExposureDateArray.length;
		int denseExposureEndDate = _sparseExposureDateArray[sparseExposureDateCount - 1];
		org.drip.function.definition.R1ToR1[] sparseLocalVolatilityArray = new
			org.drip.function.definition.R1ToR1[sparseExposureDateCount];
		org.drip.exposure.regressiontrade.AndersenPykhtinSokolTrajectory[]
			andersenPykhtinSokolTrajectoryArray = new
				org.drip.exposure.regressiontrade.AndersenPykhtinSokolTrajectory[pathCount];

		if (pathCount != wanderEnsemble.length)
		{
			return null;
		}

		for (int pathIndex = 0; pathIndex < pathCount; ++pathIndex)
		{
			if (null == wanderEnsemble[pathIndex] || 0 == wanderEnsemble[pathIndex].length ||
				!org.drip.numerical.common.NumberUtil.IsValid (wanderEnsemble[pathIndex]))
			{
				return null;
			}
		}

		org.drip.exposure.regressiontrade.AdjustedVariationMarginDynamics adjustedVariationMarginDynamics =
			ensembleAdjustedVariationMarginDynamics();

		org.drip.exposure.regression.PykhtinPillarDynamics[] pillarDynamicsArray =
			adjustedVariationMarginDynamics.pillarDynamics();

		for (int sparseExposureDateIndex = 0;
			sparseExposureDateIndex < sparseExposureDateCount;
			++sparseExposureDateIndex)
		{
			sparseLocalVolatilityArray[sparseExposureDateIndex] =
				pillarDynamicsArray[sparseExposureDateIndex].localVolatilityR1ToR1
					(localVolatilityGenerationControl);
		}

		org.drip.exposure.regressiontrade.AdjustedVariationMarginEstimate[]
			pathAdjustedVariationMarginEstimateArray =
				adjustedVariationMarginDynamics.adjustedVariationMarginEstimateArray();

		for (int pathIndex = 0; pathIndex < pathCount; ++pathIndex)
		{
			java.util.Map<java.lang.Integer, java.lang.Double> variationMarginEstimateTrajectory = new
				java.util.HashMap<java.lang.Integer, java.lang.Double>();

			try
			{
				org.drip.exposure.mpor.TradePayment[] tradePaymentTrajectory =
					pathAdjustedVariationMarginEstimateArray[pathIndex].denseTradePaymentArray();

				double[] denseExposureArray = new org.drip.exposure.regression.AndersenPykhtinSokolStretch (
					_sparseExposureDateArray,
					pathAdjustedVariationMarginEstimateArray[pathIndex].adjustedVariationMarginEstimateArray(),
					sparseLocalVolatilityArray,
					tradePaymentTrajectory
				).denseExposure (wanderEnsemble[pathIndex]);

				for (int denseExposureDate = denseExposureStartDate;
					denseExposureDate <= denseExposureEndDate;
					++denseExposureDate)
				{
					variationMarginEstimateTrajectory.put (
						denseExposureDate,
						denseExposureArray[denseExposureDate - denseExposureStartDate]
					);
				}

				andersenPykhtinSokolTrajectoryArray[pathIndex] = new
					org.drip.exposure.regressiontrade.AndersenPykhtinSokolTrajectory (
					variationMarginEstimateTrajectory,
					tradePaymentTrajectory
				);
			}
			catch (java.lang.Exception e)
			{
				e.printStackTrace();
			}
		}

		return andersenPykhtinSokolTrajectoryArray;
	}
}
