
package org.drip.exposure.regression;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
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
 * <i>AndersenPykhtinSokolStretch</i> generates the Regression Based Path Exposures off of the Pillar
 * Vertexes using the Pykhtin (2009) Scheme. Eventual Unadjusted Variation Margin Calculation follows
 * Andersen, Pykhtin, and Sokol (2017). The References are:
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
 *  			Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives
 *  				Pricing <i>Risk</i> <b>21 (2)</b> 97-102
 *  		</li>
 *  	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ExposureAnalyticsLibrary.md">Exposure Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/README.md">Exposure Group Level Collateralized/Uncollateralized Exposure</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/regression/README.md">Regression Based Path Exposure Generation</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class AndersenPykhtinSokolStretch
{
	private int[] _sparseDateArray = null;
	private double[] _sparseExposureArray = null;
	private org.drip.exposure.mpor.TradePayment[] _denseTradePaymentArray = null;
	private org.drip.function.definition.R1ToR1[] _sparseLocalVolatilityArray = null;

	/**
	 * AndersenPykhtinSokolStretch Constructor
	 * 
	 * @param sparseDateArray Array of Sparse Exposure Dates
	 * @param sparseExposureArray Array of Sparse Exposures
	 * @param sparseLocalVolatilityArray Array of Sparse Local Volatility R1 To R1 Functions
	 * @param denseTradePaymentArray Array of Dense Trade Payments
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public AndersenPykhtinSokolStretch (
		final int[] sparseDateArray,
		final double[] sparseExposureArray,
		final org.drip.function.definition.R1ToR1[] sparseLocalVolatilityArray,
		final org.drip.exposure.mpor.TradePayment[] denseTradePaymentArray)
		throws java.lang.Exception
	{
		if (null == (_sparseDateArray = sparseDateArray) ||
			null == (_sparseExposureArray = sparseExposureArray) ||
			null == (_sparseLocalVolatilityArray = sparseLocalVolatilityArray) ||
			null == (_denseTradePaymentArray = denseTradePaymentArray))
		{
			throw new java.lang.Exception ("AndersenPykhtinSokolStretch Constructor => Invalid Inputs");
		}

		int sparseExposureDateCount = _sparseDateArray.length;
		int denseExposureDateCount = _denseTradePaymentArray.length;

		if (0 == sparseExposureDateCount ||
			sparseExposureDateCount != _sparseExposureArray.length ||
			!org.drip.numerical.common.NumberUtil.IsValid (_sparseExposureArray) ||
			sparseExposureDateCount != _sparseLocalVolatilityArray.length ||
			sparseExposureDateCount > denseExposureDateCount)
		{
			throw new java.lang.Exception ("AndersenPykhtinSokolStretch Constructor => Invalid Inputs");
		}

		for (int sparseExposureDateIndex = 0;
			sparseExposureDateIndex < sparseExposureDateCount;
			++sparseExposureDateIndex)
		{
			if (null == _sparseLocalVolatilityArray[sparseExposureDateIndex])
			{
				throw new java.lang.Exception ("AndersenPykhtinSokolStretch Constructor => Invalid Inputs");
			}
		}

		for (int denseExposureDateIndex = 0;
			denseExposureDateIndex < denseExposureDateCount;
			++denseExposureDateIndex)
		{
			if (null == _denseTradePaymentArray[denseExposureDateIndex])
			{
				throw new java.lang.Exception ("AndersenPykhtinSokolStretch Constructor => Invalid Inputs");
			}
		}
	}

	/**
	 * Retrieve the Sparse Exposure Date Array
	 * 
	 * @return The Sparse Exposure Date Array
	 */

	public int[] sparseDateArray()
	{
		return _sparseDateArray;
	}

	/**
	 * Retrieve the Sparse Exposure Array
	 * 
	 * @return The Sparse Exposure Array
	 */

	public double[] sparseExposureArray()
	{
		return _sparseExposureArray;
	}

	/**
	 * Retrieve the Sparse Local Volatility R1 To R1 Array
	 * 
	 * @return The Sparse Local Volatility R1 To R1 Array
	 */

	public org.drip.function.definition.R1ToR1[] sparseLocalVolatilityArray()
	{
		return _sparseLocalVolatilityArray;
	}

	/**
	 * Retrieve the Dense Trade Payment Array
	 * 
	 * @return The Dense Trade Payment Array
	 */

	public org.drip.exposure.mpor.TradePayment[] denseTradePaymentArray()
	{
		return _denseTradePaymentArray;
	}

	/**
	 * Generate the Dense (Complete) Segment Exposures
	 * 
	 * @param wanderTrajectory The Wander Date Trajectory
	 * 
	 * @return The Dense (Complete) Segment Exposures
	 */

	public double[] denseExposure (
		final double[] wanderTrajectory)
	{
		int epochDate = _sparseDateArray[0];
		int sparseExposureDateCount = _sparseDateArray.length;
		int denseExposureDateCount = _denseTradePaymentArray.length;
		double[] denseExposureTrajectory = new double[denseExposureDateCount];

		for (int sparseExposureDateIndex = 1;
			sparseExposureDateIndex < sparseExposureDateCount;
			++sparseExposureDateIndex)
		{
			try
			{
				new AndersenPykhtinSokolSegment (
					epochDate,
					new org.drip.exposure.regression.PillarVertex (
						_sparseDateArray[sparseExposureDateIndex - 1],
						_sparseExposureArray[sparseExposureDateIndex - 1]
					),
					new org.drip.exposure.regression.PillarVertex (
						_sparseDateArray[sparseExposureDateIndex],
						_sparseExposureArray[sparseExposureDateIndex]
					),
					_sparseLocalVolatilityArray[sparseExposureDateIndex]
				).denseExposureTrajectoryUpdate (
					denseExposureTrajectory,
					wanderTrajectory
				);
			}
			catch (java.lang.Exception e)
			{
				e.printStackTrace();

				return null;
			}
		}

		for (int denseExposureDateIndex = 0;
			denseExposureDateIndex < denseExposureDateCount;
			++denseExposureDateIndex)
		{
			org.drip.exposure.mpor.TradePayment tradePayment =
				_denseTradePaymentArray[denseExposureDateIndex];

			denseExposureTrajectory[denseExposureDateIndex] = denseExposureTrajectory[denseExposureDateIndex]
				+ tradePayment.dealer() - tradePayment.client();
		}

		return denseExposureTrajectory;
	}
}
