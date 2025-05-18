
package org.drip.service.api;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2025 Lakshmi Krishnamurthy
 * Copyright (C) 2024 Lakshmi Krishnamurthy
 * Copyright (C) 2023 Lakshmi Krishnamurthy
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
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
 * <i>InstrMetric</i> contains the fields that hold the result of the PnL metric calculations. It provides
 * 	the following Functions:
 * 	<ul>
 * 		<li><i>InstrMetric</i> Constructor</li>
 * 		<li>Retrieve the Forward Metric</li>
 * 		<li>Retrieve the PnL Metric</li>
 * 		<li>Reduce the PnL/forward metrics to an array</li>
 * 	</ul>
 *
 * <br>
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationSupportLibrary.md">Computation Support</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/README.md">Environment, Product/Definition Containers, and Scenario/State Manipulation APIs</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/api/README.md">Horizon Roll Attribution Service API</a></td></tr>
 *  </table>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class InstrMetric
{
	private ForwardRates _forwardRates = null;
	private ProductDailyPnL _productDailyPnL = null;

	/**
	 * <i>InstrMetric</i> constructor
	 * 
	 * @param forwardRates The Forward Rates Metric
	 * @param productDailyPnL The Daily Carry/Roll PnL Metric
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public InstrMetric (
		final ForwardRates forwardRates,
		final ProductDailyPnL productDailyPnL)
		throws Exception
	{
		if (null == (_forwardRates = forwardRates) || null == (_productDailyPnL = productDailyPnL)) {
			throw new Exception ("InstrMetric Constructor: Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Forward Metric
	 * 
	 * @return The Forward Metric
	 */

	public ForwardRates fwdMetric()
	{
		return _forwardRates;
	}

	/**
	 * Retrieve the PnL Metric
	 * 
	 * @return The PnL Metric
	 */

	public ProductDailyPnL pnlMetric()
	{
		return _productDailyPnL;
	}

	/**
	 * Reduce the PnL/forward metrics to an array
	 * 
	 * @return The Array containing the PnL/forward metrics
	 */

	public double[] toArray()
	{
		double[] forwardRatesArray = _forwardRates.toArray();

		double[] pnLMetricArray = _productDailyPnL.toArray();

		int i = 0;
		double[] instrMetricArray = new double[forwardRatesArray.length + pnLMetricArray.length];

		for (double pnLMetric : pnLMetricArray) {
			instrMetricArray[i++] = pnLMetric;
		}

		for (double forwardRates : forwardRatesArray)
			instrMetricArray[i++] = forwardRates;

		return instrMetricArray;
	}

	@Override public String toString()
	{
		StringBuffer stringBuffer = new StringBuffer();

		boolean firstMetric = true;

		for (double metric : toArray()) {
			if (firstMetric) {
				firstMetric = false;
			} else {
				stringBuffer.append (",");
			}

			stringBuffer.append (metric);
		}

		return stringBuffer.toString();
	}
}
