
package org.drip.exposure.regressiontrade;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
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
 * <i>AdjustedVariationMarginEstimate</i> holds the Sparse Path Adjusted Variation Margin and the Daily Trade
 * Flows. The References are:
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
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ExposureAnalyticsLibrary.md">Exposure Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/README.md">Exposure Group Level Collateralized/Uncollateralized Exposure</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/regressiontrade/README.md">Exposure Regression under Margin and Trade Payments</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class AdjustedVariationMarginEstimate
{
	private double[] _adjustedVariationMarginEstimateArray = null;
	private org.drip.exposure.mpor.TradePayment[] _denseTradePaymentArray = null;

	/**
	 * AdjustedVariationMarginEstimate Constructor
	 * 
	 * @param adjustedVariationMarginEstimateArray The Adjusted Variation Margin Estimate Array
	 * @param denseTradePaymentArray The Dense Trade Payment Array
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public AdjustedVariationMarginEstimate (
		final double[] adjustedVariationMarginEstimateArray,
		final org.drip.exposure.mpor.TradePayment[] denseTradePaymentArray)
		throws java.lang.Exception
	{
		if (null == (_adjustedVariationMarginEstimateArray = adjustedVariationMarginEstimateArray) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_adjustedVariationMarginEstimateArray) ||
			null == (_denseTradePaymentArray = denseTradePaymentArray))
		{
			throw new java.lang.Exception ("AdjustedVariationMarginEstimate Constructor => Invalid Inputs");
		}

		int denseTradePaymentCount = _denseTradePaymentArray.length;
		int adjustedVariationMarginEstimate = _adjustedVariationMarginEstimateArray.length;

		if (0 == adjustedVariationMarginEstimate || denseTradePaymentCount < adjustedVariationMarginEstimate)
		{
			throw new java.lang.Exception ("AdjustedVariationMarginEstimate Constructor => Invalid Inputs");
		}

		for (int dateIndex = 0; dateIndex < denseTradePaymentCount; ++denseTradePaymentCount)
		{
			if (null == denseTradePaymentArray[dateIndex])
			{
				throw new java.lang.Exception
					("AdjustedVariationMarginEstimate Constructor => Invalid Inputs");
			}
		}
	}

	/**
	 * Retrieve the Path-wise Adjusted Variation Margin Estimate Array
	 * 
	 * @return The Path-wise Adjusted Variation Margin Estimate Array
	 */

	public double[] adjustedVariationMarginEstimateArray()
	{
		return _adjustedVariationMarginEstimateArray;
	}

	/**
	 * Retrieve the Path-wise Dense Trade Payment Array
	 * 
	 * @return The Path-wise Dense Trade Payment Array
	 */

	public org.drip.exposure.mpor.TradePayment[] denseTradePaymentArray()
	{
		return _denseTradePaymentArray;
	}
}
