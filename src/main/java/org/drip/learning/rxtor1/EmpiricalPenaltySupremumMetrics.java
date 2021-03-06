
package org.drip.learning.rxtor1;

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
 * Copyright (C) 2015 Lakshmi Krishnamurthy
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
 * <i>EmpiricalPenaltySupremumMetrics</i> computes Efron-Stein Metrics for the Penalty Supremum R<sup>x</sup>
 * To R<sup>1</sup> Functions.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/StatisticalLearningLibrary.md">Statistical Learning</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/learning">Agnostic Learning Bounds under Empirical Loss Minimization Schemes</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/learning/rxtor1">Statistical Learning Empirical Loss Penalizer</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class EmpiricalPenaltySupremumMetrics extends org.drip.sequence.functional.EfronSteinMetrics {
	private org.drip.learning.rxtor1.EmpiricalPenaltySupremumEstimator _epse = null;
	private org.drip.learning.bound.MeasureConcentrationExpectationBound _mceb = null;

	/**
	 * EmpiricalPenaltySupremumMetrics Constructor
	 * 
	 * @param epse R^x To R^1 The Empirical Penalty Supremum Estimator Instance
	 * @param aSSAM Array of the Individual Single Sequence Metrics
	 * @param mceb The Concentration-of-Measure Loss Expectation Bound Estimator
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public EmpiricalPenaltySupremumMetrics (
		final org.drip.learning.rxtor1.EmpiricalPenaltySupremumEstimator epse,
		final org.drip.sequence.metrics.SingleSequenceAgnosticMetrics[] aSSAM,
		final org.drip.learning.bound.MeasureConcentrationExpectationBound mceb)
		throws java.lang.Exception
	{
		super (epse, aSSAM);

		if (null == (_epse = epse) || null == (_mceb = mceb))
			throw new java.lang.Exception ("EmpiricalPenaltySupremumMetrics ctr: Invalid Inputs");
	}

	/**
	 * Retrieve the Empirical Penalty Supremum Function
	 * 
	 * @return The Empirical Penalty Supremum Function
	 */

	public org.drip.learning.rxtor1.EmpiricalPenaltySupremumEstimator empiricalPenaltySupremumEstimator()
	{
		return _epse;
	}

	/**
	 * Retrieve the Univariate Sequence Dependent Variance Bound
	 * 
	 * @param adblVariate The univariate Sequence
	 * 
	 * @return The Univariate Sequence Dependent Variance Bound
	 * 
	 * @throws java.lang.Exception Thrown if the Date Dependent Variance Bound cannot be Computed
	 */

	public double dataDependentVarianceBound (
		final double[] adblVariate)
		throws java.lang.Exception
	{
		return _epse.evaluate (adblVariate) / adblVariate.length;
	}

	/**
	 * Retrieve the Multivariate Sequence Dependent Variance Bound
	 * 
	 * @param aadblVariate The Multivariate Sequence
	 * 
	 * @return The Multivariate Sequence Dependent Variance Bound
	 * 
	 * @throws java.lang.Exception Thrown if the Date Dependent Variance Bound cannot be Computed
	 */

	public double dataDependentVarianceBound (
		final double[][] aadblVariate)
		throws java.lang.Exception
	{
		return _epse.evaluate (aadblVariate) / aadblVariate.length;
	}

	/**
	 * Compute the Lugosi Data-Dependent Variance Bound from the Sample and the Classifier Class Asymptotic
	 * 	Behavior. The Reference is:
	 * 
	 * 		G. Lugosi (2002): Pattern Classification and Learning Theory, in: L.Gyorfi, editor, Principles of
	 * 			Non-parametric Learning, 5-62, Springer, Wien.
	 * 
	 * @param adblVariate The Sample Univariate Array
	 * 
	 * @return The Lugosi Data-Dependent Variance Bound
	 * 
	 * @throws java.lang.Exception Thrown if the Lugosi Data-Dependent Variance Bound cannot be computed
	 */

	public double lugosiVarianceBound (
		final double[] adblVariate)
		throws java.lang.Exception
	{
		org.drip.function.definition.R1ToR1 supR1ToR1 = _epse.supremumR1ToR1 (adblVariate);

		if (null == supR1ToR1)
			throw new java.lang.Exception
				("EmpiricalPenaltySupremumMetrics::lugosiVarianceBound => Cannot Find Supremum Classifier");

		return dataDependentVarianceBound (adblVariate) + _mceb.constant() + java.lang.Math.pow
			(adblVariate.length, _mceb.exponent());
	}

	/**
	 * Compute the Lugosi Data-Dependent Variance Bound from the Sample and the Classifier Class Asymptotic
	 * 	Behavior. The Reference is:
	 * 
	 * 		G. Lugosi (2002): Pattern Classification and Learning Theory, in: L.Gyorfi, editor, Principles of
	 * 			Non-parametric Learning, 5-62, Springer, Wien.
	 * 
	 * @param aadblVariate The Sample Multivariate Array
	 * 
	 * @return The Lugosi Data-Dependent Variance Bound
	 * 
	 * @throws java.lang.Exception Thrown if the Lugosi Data-Dependent Variance Bound cannot be computed
	 */

	public double lugosiVarianceBound (
		final double[][] aadblVariate)
		throws java.lang.Exception
	{
		org.drip.function.definition.RdToR1 supRdToR1 = _epse.supremumRdToR1 (aadblVariate);

		if (null == supRdToR1)
			throw new java.lang.Exception
				("EmpiricalPenaltySupremumMetrics::lugosiVarianceBound => Cannot Find Supremum Classifier");

		return dataDependentVarianceBound (aadblVariate) + _mceb.constant() + java.lang.Math.pow
			(aadblVariate.length, _mceb.exponent());
	}
}
