
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
 * <i>EmpiricalLearningMetricEstimator</i> is the Estimator of the Empirical Loss and Risk, as well as the
 * 	corresponding Covering Numbers.
 *
 * <br><br>
 * The References are:
 *  
 * <br><br>
 * <ul>
 * 	<li>
 *  	Alon, N., S. Ben-David, N. Cesa Bianchi, and D. Haussler (1997): Scale-sensitive Dimensions, Uniform
 *  		Convergence, and Learnability <i>Journal of Association of Computational Machinery</i> <b>44
 *  		(4)</b> 615-631
 * 	</li>
 * 	<li>
 *  	Anthony, M., and P. L. Bartlett (1999): <i>Artificial Neural Network Learning - Theoretical
 *  		Foundations</i> <b>Cambridge University Press</b> Cambridge, UK
 * 	</li>
 * 	<li>
 *  	Kearns, M. J., R. E. Schapire, and L. M. Sellie (1994): <i>Towards Efficient Agnostic Learning</i>
 *  		Machine Learning <b>17 (2)</b> 115-141
 * 	</li>
 * 	<li>
 *  	Lee, W. S., P. L. Bartlett, and R. C. Williamson (1998): The Importance of Convexity in Learning with
 *  		Squared Loss <i>IEEE Transactions on Information Theory</i> <b>44</b> 1974-1980
 * 	</li>
 * 	<li>
 *  	Vapnik, V. N. (1998): <i>Statistical learning Theory</i> <b>Wiley</b> New York
 * 	</li>
 * </ul>
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

public interface EmpiricalLearningMetricEstimator {

	/**
	 * Retrieve the Underlying Learner Function Class
	 * 
	 * @return The Underlying Learner Function Class
	 */

	public abstract org.drip.spaces.functionclass.NormedRxToNormedR1Finite functionClass();

	/**
	 * Retrieve the Regularizer Function
	 * 
	 * @return The Regularizer Function
	 */

	public abstract org.drip.learning.regularization.RegularizationFunction regularizerFunction();

	/**
	 * Retrieve the Loss Class Sample Covering Number - L-Infinity or L-p based Based
	 * 
	 * @param gvvi The Validated Instance Vector Sequence
	 * @param dblEpsilon The Deviation of the Empirical Mean from the Population Mean
	 * @param bSupremum TRUE To Use the Supremum Metric in place of the Built-in Metric
	 * 
	 * @return The Loss Class Sample Covering Number - L-Infinity or L-p based Based
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public abstract double lossSampleCoveringNumber (
		final org.drip.spaces.instance.GeneralizedValidatedVector gvvi,
		final double dblEpsilon,
		final boolean bSupremum)
		throws java.lang.Exception;

	/**
	 * Compute the Empirical Sample Loss
	 * 
	 * @param funcLearnerR1ToR1 The R^1 To R^1 Learner Function
	 * @param gvviX The Validated Predictor Instance
	 * @param gvviY The Validated Response Instance
	 * 
	 * @return The Empirical Loss
	 * 
	 * @throws java.lang.Exception Thrown if the Empirical Loss cannot be computed
	 */

	public abstract double empiricalLoss (
		final org.drip.function.definition.R1ToR1 funcLearnerR1ToR1,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviX,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviY)
		throws java.lang.Exception;

	/**
	 * Compute the Empirical Sample Loss
	 * 
	 * @param funcLearnerRdToR1 The R^d To R^1 Learner Function
	 * @param gvviX The Validated Predictor Instance
	 * @param gvviY The Validated Response Instance
	 * 
	 * @return The Empirical Loss
	 * 
	 * @throws java.lang.Exception Thrown if the Empirical Loss cannot be computed
	 */

	public abstract double empiricalLoss (
		final org.drip.function.definition.RdToR1 funcLearnerRdToR1,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviX,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviY)
		throws java.lang.Exception;

	/**
	 * Compute the Supremum Empirical Sample Loss
	 * 
	 * @param gvviX The Validated Predictor Instance
	 * @param gvviY The Validated Response Instance
	 * 
	 * @return The Supremum Empirical Sample Loss
	 * 
	 * @throws java.lang.Exception Thrown if the Supremum Empirical Sample Loss cannot be computed
	 */

	public abstract org.drip.learning.rxtor1.EmpiricalPenaltySupremum supremumEmpiricalLoss (
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviX,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviY)
		throws java.lang.Exception;

	/**
	 * Compute the Structural Sample Loss
	 * 
	 * @param funcLearnerR1ToR1 The R^1 To R^1 Learner Function
	 * @param gvvi The Validated Predictor Instance
	 * 
	 * @return The Structural Loss
	 * 
	 * @throws java.lang.Exception Thrown if the Structural Loss cannot be computed
	 */

	public abstract double structuralLoss (
		final org.drip.function.definition.R1ToR1 funcLearnerR1ToR1,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvvi)
		throws java.lang.Exception;

	/**
	 * Compute the Structural Sample Loss
	 * 
	 * @param funcLearnerRdToR1 The R^d To R^1 Learner Function
	 * @param gvvi The Validated Predictor Instance
	 * 
	 * @return The Structural Loss
	 * 
	 * @throws java.lang.Exception Thrown if the Structural Loss cannot be computed
	 */

	public abstract double structuralLoss (
		final org.drip.function.definition.RdToR1 funcLearnerRdToR1,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvvi)
		throws java.lang.Exception;

	/**
	 * Compute the Supremum Structural Sample Loss
	 * 
	 * @param gvviX The Validated Predictor Instance
	 * 
	 * @return The Supremum Structural Sample Loss
	 * 
	 * @throws java.lang.Exception Thrown if the Supremum Structural Sample Loss cannot be computed
	 */

	public abstract org.drip.learning.rxtor1.EmpiricalPenaltySupremum supremumStructuralLoss (
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviX)
		throws java.lang.Exception;

	/**
	 * Compute the Regularized Sample Loss (Empirical + Structural)
	 * 
	 * @param funcLearnerR1ToR1 The R^1 To R^1 Learner Function
	 * @param gvviX The Validated Predictor Instance
	 * @param gvviY The Validated Response Instance
	 * 
	 * @return The Regularized Loss
	 * 
	 * @throws java.lang.Exception Thrown if the Regularized Loss cannot be computed
	 */

	public abstract double regularizedLoss (
		final org.drip.function.definition.R1ToR1 funcLearnerR1ToR1,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviX,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviY)
		throws java.lang.Exception;

	/**
	 * Compute the Regularized Sample Loss (Empirical + Structural)
	 * 
	 * @param funcLearnerRdToR1 The R^d To R^1 Learner Function
	 * @param gvviX The Validated Predictor Instance
	 * @param gvviY The Validated Response Instance
	 * 
	 * @return The Regularized Loss
	 * 
	 * @throws java.lang.Exception Thrown if the Regularized Loss cannot be computed
	 */

	public abstract double regularizedLoss (
		final org.drip.function.definition.RdToR1 funcLearnerRdToR1,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviX,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviY)
		throws java.lang.Exception;

	/**
	 * Compute the Supremum Regularized Sample Loss
	 * 
	 * @param gvviX The Validated Predictor Instance
	 * @param gvviY The Validated Response Instance
	 * 
	 * @return The Supremum Regularized Sample Loss
	 * 
	 * @throws java.lang.Exception Thrown if the Supremum Regularized Sample Loss cannot be computed
	 */

	public abstract org.drip.learning.rxtor1.EmpiricalPenaltySupremum supremumRegularizedLoss (
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviX,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviY)
		throws java.lang.Exception;

	/**
	 * Compute the Empirical Sample Risk
	 * 
	 * @param distR1R1 R^1 R^1 Multivariate Measure
	 * @param funcLearnerR1ToR1 The R^1 To R^1 Learner Function
	 * @param gvviX The Validated Predictor Instance
	 * @param gvviY The Validated Response Instance
	 * 
	 * @return The Empirical Sample Risk
	 * 
	 * @throws java.lang.Exception Thrown if the Empirical Sample Risk cannot be computed
	 */

	public abstract double empiricalRisk (
		final org.drip.measure.continuous.R1R1Distribution distR1R1,
		final org.drip.function.definition.R1ToR1 funcLearnerR1ToR1,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviX,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviY)
		throws java.lang.Exception;

	/**
	 * Compute the Empirical Sample Risk
	 * 
	 * @param distRdR1 R^d R^1 Multivariate Measure
	 * @param funcLearnerRdToR1 The R^d To R^1 Learner Function
	 * @param gvviX The Validated Predictor Instance
	 * @param gvviY The Validated Response Instance
	 * 
	 * @return The Empirical Sample Risk
	 * 
	 * @throws java.lang.Exception Thrown if the Empirical Sample Risk cannot be computed
	 */

	public abstract double empiricalRisk (
		final org.drip.measure.continuous.RdR1Distribution distRdR1,
		final org.drip.function.definition.RdToR1 funcLearnerRdToR1,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviX,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviY)
		throws java.lang.Exception;

	/**
	 * Compute the Supremum Empirical Sample Risk
	 * 
	 * @param distR1R1 R^1 R^1 Multivariate Measure
	 * @param gvviX The Validated Predictor Instance
	 * @param gvviY The Validated Response Instance
	 * 
	 * @return The Supremum Empirical Sample Loss
	 * 
	 * @throws java.lang.Exception Thrown if the Supremum Empirical Sample Loss cannot be computed
	 */

	public abstract org.drip.learning.rxtor1.EmpiricalPenaltySupremum supremumEmpiricalRisk (
		final org.drip.measure.continuous.R1R1Distribution distR1R1,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviX,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviY)
		throws java.lang.Exception;

	/**
	 * Compute the Supremum Empirical Sample Risk
	 * 
	 * @param distRdR1 R^d R^1 Multivariate Measure
	 * @param gvviX The Validated Predictor Instance
	 * @param gvviY The Validated Response Instance
	 * 
	 * @return The Supremum Empirical Sample Loss
	 * 
	 * @throws java.lang.Exception Thrown if the Supremum Empirical Sample Loss cannot be computed
	 */

	public abstract org.drip.learning.rxtor1.EmpiricalPenaltySupremum supremumEmpiricalRisk (
		final org.drip.measure.continuous.RdR1Distribution distRdR1,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviX,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviY)
		throws java.lang.Exception;

	/**
	 * Compute the Structural Sample Risk
	 * 
	 * @param distR1R1 R^1 R^1 Multivariate Measure
	 * @param funcLearnerR1ToR1 The R^1 To R^1 Learner Function
	 * @param gvviX The Validated Predictor Instance
	 * @param gvviY The Validated Response Instance
	 * 
	 * @return The Structural Risk
	 * 
	 * @throws java.lang.Exception Thrown if the Structural Risk cannot be computed
	 */

	public abstract double structuralRisk (
		final org.drip.measure.continuous.R1R1Distribution distR1R1,
		final org.drip.function.definition.R1ToR1 funcLearnerR1ToR1,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviX,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviY)
		throws java.lang.Exception;

	/**
	 * Compute the Structural Sample Risk
	 * 
	 * @param distRdR1 R^d R^1 Multivariate Measure
	 * @param funcLearnerRdToR1 The R^d To R^1 Learner Function
	 * @param gvviX The Validated Predictor Instance
	 * @param gvviY The Validated Response Instance
	 * 
	 * @return The Structural Risk
	 * 
	 * @throws java.lang.Exception Thrown if the Structural Risk cannot be computed
	 */

	public abstract double structuralRisk (
		final org.drip.measure.continuous.RdR1Distribution distRdR1,
		final org.drip.function.definition.RdToR1 funcLearnerRdToR1,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviX,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviY)
		throws java.lang.Exception;

	/**
	 * Compute the Supremum Structural Sample Risk
	 * 
	 * @param distR1R1 R^1 R^1 Multivariate Measure
	 * @param gvviX The Validated Predictor Instance
	 * @param gvviY The Validated Response Instance
	 * 
	 * @return The Supremum Structural Sample Loss
	 * 
	 * @throws java.lang.Exception Thrown if the Supremum Structural Sample Risk cannot be computed
	 */

	public abstract org.drip.learning.rxtor1.EmpiricalPenaltySupremum supremumStructuralRisk (
		final org.drip.measure.continuous.R1R1Distribution distR1R1,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviX,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviY)
		throws java.lang.Exception;

	/**
	 * Compute the Supremum Structural Sample Risk
	 * 
	 * @param distRdR1 R^d R^1 Multivariate Measure
	 * @param gvviX The Validated Predictor Instance
	 * @param gvviY The Validated Response Instance
	 * 
	 * @return The Supremum Structural Sample Risk
	 * 
	 * @throws java.lang.Exception Thrown if the Supremum Structural Sample Risk cannot be computed
	 */

	public abstract org.drip.learning.rxtor1.EmpiricalPenaltySupremum supremumStructuralRisk (
		final org.drip.measure.continuous.RdR1Distribution distRdR1,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviX,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviY)
		throws java.lang.Exception;

	/**
	 * Compute the Regularized Sample Risk (Empirical + Structural)
	 * 
	 * @param distR1R1 R^1 R^1 Multivariate Measure
	 * @param funcLearnerR1ToR1 The R^1 To R^1 Learner Function
	 * @param gvviX The Validated Predictor Instance
	 * @param gvviY The Validated Response Instance
	 * 
	 * @return The Regularized Sample Risk
	 * 
	 * @throws java.lang.Exception Thrown if the Regularized Sample Risk cannot be computed
	 */

	public abstract double regularizedRisk (
		final org.drip.measure.continuous.R1R1Distribution distR1R1,
		final org.drip.function.definition.R1ToR1 funcLearnerR1ToR1,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviX,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviY)
		throws java.lang.Exception;

	/**
	 * Compute the Regularized Sample Risk (Empirical + Structural)
	 * 
	 * @param distRdR1 R^d R^1 Multivariate Measure
	 * @param funcLearnerRdToR1 The R^d To R^1 Learner Function
	 * @param gvviX The Validated Predictor Instance
	 * @param gvviY The Validated Response Instance
	 * 
	 * @return The Regularized Sample Risk
	 * 
	 * @throws java.lang.Exception Thrown if the Regularized Sample Risk cannot be computed
	 */

	public abstract double regularizedRisk (
		final org.drip.measure.continuous.RdR1Distribution distRdR1,
		final org.drip.function.definition.RdToR1 funcLearnerRdToR1,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviX,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviY)
		throws java.lang.Exception;

	/**
	 * Compute the Supremum Regularized Sample Risk
	 * 
	 * @param distR1R1 R^1 R^1 Multivariate Measure
	 * @param gvviX The Validated Predictor Instance
	 * @param gvviY The Validated Response Instance
	 * 
	 * @return The Supremum Regularized Sample Risk
	 * 
	 * @throws java.lang.Exception Thrown if the Supremum Regularized Sample Risk cannot be computed
	 */

	public abstract org.drip.learning.rxtor1.EmpiricalPenaltySupremum supremumRegularizedRisk (
		final org.drip.measure.continuous.R1R1Distribution distR1R1,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviX,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviY)
		throws java.lang.Exception;

	/**
	 * Compute the Supremum Regularized Sample Risk
	 * 
	 * @param distRdR1 R^d R^1 Multivariate Measure
	 * @param gvviX The Validated Predictor Instance
	 * @param gvviY The Validated Response Instance
	 * 
	 * @return The Supremum Regularized Sample Risk
	 * 
	 * @throws java.lang.Exception Thrown if the Supremum Regularized Sample Risk cannot be computed
	 */

	public abstract org.drip.learning.rxtor1.EmpiricalPenaltySupremum supremumRegularizedRisk (
		final org.drip.measure.continuous.RdR1Distribution distRdR1,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviX,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviY)
		throws java.lang.Exception;
}
