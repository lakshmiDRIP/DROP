
package org.drip.learning.rxtor1;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
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
 * <i>EmpiricalPenaltySupremumMetrics</i> computes Efron-Stein Metrics for the Penalty Supremum R<sup>x</sup>
 * To R<sup>1</sup> Functions.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/StatisticalLearningLibrary.md">Statistical Learning</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/learning">Learning</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/learning/rxtor1">R<sup>x</sup> To R<sup>1</sup></a></li>
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
