
package org.drip.execution.bayesian;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
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
 * <i>PriorConditionalCombiner</i> holds the Distributions associated with the Prior Drift and the
 *  Conditional Price Distributions. It uses them to generate the resulting Joint, Posterior, and MAP Implied
 *  Posterior Distributions. The References are:
 * 
 * <br>
 * 	<ul>
 * 		<li>
 * 			Bertsimas, D., and A. W. Lo (1998): Optimal Control of Execution Costs <i>Journal of Financial
 * 				Markets</i> <b>1</b> 1-50
 * 		</li>
 * 		<li>
 * 			Almgren, R., and N. Chriss (2000): Optimal Execution of Portfolio Transactions <i>Journal of
 * 				Risk</i> <b>3 (2)</b> 5-39
 * 		</li>
 * 		<li>
 * 			Brunnermeier, L. K., and L. H. Pedersen (2005): Predatory Trading <i>Journal of Finance</i> <b>60
 * 				(4)</b> 1825-1863
 * 		</li>
 * 		<li>
 * 			Almgren, R., and J. Lorenz (2006): Bayesian Adaptive Trading with a Daily Cycle <i>Journal of
 * 				Trading</i> <b>1 (4)</b> 38-46
 * 		</li>
 * 		<li>
 * 			Kissell, R., and R. Malamut (2007): Algorithmic Decision Making Framework <i>Journal of
 * 				Trading</i> <b>1 (1)</b> 12-21
 * 		</li>
 * 	</ul>
 * <br>
 *  <ul>
 *		<li><b>Module</b>        = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution">Execution</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/bayesian">Bayesian</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/TransactionCost">Transaction Cost Analytics</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class PriorConditionalCombiner {
	private org.drip.execution.bayesian.PriorDriftDistribution _pdd = null;
	private org.drip.execution.bayesian.ConditionalPriceDistribution _cpd = null;

	/**
	 * PriorConditionalCombiner Constructor
	 * 
	 * @param pdd The Prior Drift Distribution Instance
	 * @param cpd The Conditional Price Distribution Instance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public PriorConditionalCombiner (
		final org.drip.execution.bayesian.PriorDriftDistribution pdd,
		final org.drip.execution.bayesian.ConditionalPriceDistribution cpd)
		throws java.lang.Exception
	{
		if (null == (_pdd = pdd) || null == (_cpd = cpd))
			throw new java.lang.Exception ("PriorConditionalCombiner Constructor => Invalid Inputs");
	}

	/**
	 * Retrieve the Prior Drift Distribution Instance
	 * 
	 * @return The Prior Drift Distribution Instance
	 */

	public org.drip.execution.bayesian.PriorDriftDistribution prior()
	{
		return _pdd;
	}

	/**
	 * Retrieve the Conditional Price Distribution Instance
	 * 
	 * @return The Conditional Price Distribution Instance
	 */

	public org.drip.execution.bayesian.ConditionalPriceDistribution conditional()
	{
		return _cpd;
	}

	/**
	 * Generate the Joint Price Distribution
	 * 
	 * @return The Joint Price Distribution
	 */

	public org.drip.measure.gaussian.R1UnivariateNormal jointPriceDistribution()
	{
		double dblTime = _cpd.time();

		try {
			return new org.drip.measure.gaussian.R1UnivariateNormal (_pdd.expectation() * dblTime,
				_pdd.variance() * dblTime * dblTime +_cpd.variance());
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the Posterior Drift Distribution
	 * 
	 * @param dblDeltaS The Price Change (Final - Initial)
	 * 
	 * @return The Posterior Drift Distribution
	 */

	public org.drip.measure.gaussian.R1UnivariateNormal posteriorDriftDistribution (
		final double dblDeltaS)
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblDeltaS)) return null;

		double dblT = _cpd.time();

		double dblNuSquared = _pdd.variance();

		double dblSigmaSquared = _cpd.variance() / dblT;

		double dblPrecisionSquared = 1. / (dblSigmaSquared + dblNuSquared * dblT);

		try {
			return new org.drip.measure.gaussian.R1UnivariateNormal ((_pdd.expectation() * dblSigmaSquared +
				dblNuSquared * dblDeltaS) * dblPrecisionSquared, dblSigmaSquared * dblNuSquared *
					dblPrecisionSquared);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
