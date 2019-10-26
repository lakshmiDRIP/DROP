
package org.drip.learning.bound;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
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
 * <i>EmpiricalLearnerLoss</i> Function computes the Empirical Loss of a Learning Operation resulting from
 * the Use of a Learning Function in Conjunction with the corresponding Empirical Realization. The References
 * are:
 * <br><br>
 * <ul>
 * 	<li>
 *  	Boucheron, S., G. Lugosi, and P. Massart (2003): Concentration Inequalities Using the Entropy Method
 *  		<i>Annals of Probability</i> <b>31</b> 1583-1614
 * 	</li>
 * 	<li>
 *  	Lugosi, G. (2002): Pattern Classification and Learning Theory, in: <i>L. Györ, editor, Principles
 *  		of Non-parametric Learning</i> <b>Springer</b> Wien 5-62
 * 	</li>
 * </ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/StatisticalLearningLibrary.md">Statistical Learning</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/learning">Agnostic Learning Bounds under Empirical Loss Minimization Schemes</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/learning/bound">Covering Numbers, Concentration, Lipschitz Bounds</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class EmpiricalLearnerLoss extends org.drip.function.definition.R1ToR1 {
	private double _dblRealization = java.lang.Double.NaN;
	private org.drip.function.definition.R1ToR1 _learner = null;

	/**
	 * EmpiricalLearnerLoss Constructor
	 * 
	 * @param learner The Learning Function
	 * @param dblRealization The Empirical Outcome
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public EmpiricalLearnerLoss (
		final org.drip.function.definition.R1ToR1 learner,
		final double dblRealization)
		throws java.lang.Exception
	{
		super (null);

		if (null == (_learner = learner) || !org.drip.numerical.common.NumberUtil.IsValid (_dblRealization =
			dblRealization))
			throw new java.lang.Exception ("EmpiricalLearnerLoss ctr: Invalid Inputs");
	}

	/**
	 * Retrieve the Empirical Realization
	 * 
	 * @return The Empirical Realization
	 */

	public double empiricalRealization()
	{
		return _dblRealization;
	}

	/**
	 * Retrieve the Learning Function
	 * 
	 * @return The Learning Function
	 */

	public org.drip.function.definition.R1ToR1 learner()
	{
		return _learner;
	}

	/**
	 * Compute the Loss for the specified Variate
	 * 
	 * @param dblVariate The Variate
	 * 
	 * @return Loss for the specified Variate
	 * 
	 * @throws java.lang.Exception Thrown if the Loss cannot be computed
	 */

	public double loss (
		final double dblVariate)
		throws java.lang.Exception
	{
		return _dblRealization - _learner.evaluate (dblVariate);
	}

	@Override public double evaluate (
		final double dblVariate)
		throws java.lang.Exception
	{
		return loss (dblVariate);
	}
}
