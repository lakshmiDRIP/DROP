
package org.drip.spaces.cover;

import org.drip.function.definition.R1ToR1;
import org.drip.numerical.common.NumberUtil;

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
 * <i>ScaleSensitiveCoveringBounds</i> implements the Lower/Upper Bounds for the General Class of Functions
 * 	in terms of their scale-sensitive dimensions (i.e., the fat shattering coefficients). The References are:
 *
 * <br><br>
 *  <ul>
 *  	<li>
 *  		N. Alon, S. Ben-David, N. Cesa-Bianchi, and D. Haussler (1993): Scale-sensitive Dimensions,
 *  			Uniform-Convergence, and Learnability <i>Proceedings of the ACM Symposium on the Foundations
 *  				of Computer Science</i>
 *  	</li>
 *  	<li>
 *  		P. L. Bartlett, S. R. Kulkarni, and S. E. Posner (1997): Covering Numbers for Real-valued
 *  			Function Classes <i>IEEE Transactions on Information Theory</i> <b>43 (5)</b> 1721-1724 
 *  	</li>
 *  	<li>
 *  		D. Pollard (1984): <i>Convergence of Stochastic Processes</i> <b>Springer</b> New York
 *  	</li>
 *  </ul>
 *
 *  It provides the following Functionality:
 *
 *  <ul>
 * 		<li><i>ScaleSensitiveCoveringBounds</i> Constructor</li>
 * 		<li>Retrieve the Fat Shattering Coefficient Function</li>
 * 		<li>Retrieve the Sample Size</li>
 * 		<li>Compute the Minimum Sample Size required to Estimate the Cardinality corresponding to the Specified Cover</li>
 * 		<li>Compute the Cardinality for the Subset T (|x) that possesses the Specified Cover for the Restriction of the Input Function Class Family F (|x)</li>
 * 		<li>Compute the Log of the Weight Loading Coefficient for the Maximum Cover Term</li>
 *  </ul>
 *
 *  <br>
 *  <style>table, td, th {
 *  	padding: 1px; border: 2px solid #008000; border-radius: 8px; background-color: #dfff00;
 *		text-align: center; color:  #0000ff;
 *  }
 *  </style>
 *  
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/StatisticalLearningLibrary.md">Statistical Learning Library</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces/README.md">R<sup>1</sup> and R<sup>d</sup> Vector/Tensor Spaces (Validated and/or Normed), and Function Classes</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces/cover/README.md">Vector Spaces Covering Number Estimator</a></td></tr>
 *  </table>
 *
 * @author Lakshmi Krishnamurthy
 */

public class ScaleSensitiveCoveringBounds implements FunctionClassCoveringBounds
{
	private int _sampleSize = -1;
	private R1ToR1 _r1r1FatShatter = null;

	/**
	 * <i>ScaleSensitiveCoveringBounds</i> Constructor
	 * 
	 * @param r1r1FatShatter The Cover Fat Shattering Coefficient Function
	 * @param iSampleSize Sample Size
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public ScaleSensitiveCoveringBounds (
		final R1ToR1 r1r1FatShatter,
		final int iSampleSize)
		throws Exception
	{
		if (null == (_r1r1FatShatter = r1r1FatShatter) || 0 >= (_sampleSize = iSampleSize)) {
			throw new Exception ("ScaleSensitiveCoveringBounds ctr: Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Fat Shattering Coefficient Function
	 * 
	 * @return The Fat Shattering Coefficient Function
	 */

	public R1ToR1 fatShatteringFunction()
	{
		return _r1r1FatShatter;
	}

	/**
	 * Retrieve the Sample Size
	 * 
	 * @return The Sample Size
	 */

	public int sampleSize()
	{
		return _sampleSize;
	}

	/**
	 * Compute the Minimum Sample Size required to Estimate the Cardinality corresponding to the Specified
	 * 	Cover
	 * 
	 * @param cover The Cover
	 * 
	 * @return The Minimum Sample Size
	 * 
	 * @throws Exception Thrown if the Minimum Sample Size Cannot be computed
	 */

	public double sampleSizeLowerBound (
		final double cover)
		throws Exception
	{
		if (!NumberUtil.IsValid (cover) || 0. == cover) {
			throw new Exception ("ScaleSensitiveCoveringBounds::sampleSizeLowerBound => Invalid Inputs");
		}

		double log2 = Math.log (2.);

		return 2. * _r1r1FatShatter.evaluate (0.25 * cover) * Math.log (
			64. * Math.E * Math.E / (cover * log2)
		) / log2;
	}

	/**
	 * Compute the Cardinality for the Subset T (|x) that possesses the Specified Cover for the Restriction
	 * 	of the Input Function Class Family F (|x).
	 *  
	 * @param cover The Specified Cover
	 * 
	 * @return The Restricted Subset Cardinality
	 * 
	 * @throws Exception Thrown if the Restricted Subset Cardinality cannot be computed
	 */

	public double restrictedSubsetCardinality (
		final double cover)
		throws Exception
	{
		if (!NumberUtil.IsValid (cover) || 0. == cover) {
			throw new Exception (
				"ScaleSensitiveCoveringBounds::restrictedSubsetCardinality => Invalid Inputs"
			);
		}

		double log2 = Math.log (2.);

		double fatShatteringCoefficient = _r1r1FatShatter.evaluate (0.25 * cover);

		if (_sampleSize <
			2. * fatShatteringCoefficient * Math.log (64. * Math.E * Math.E / (cover * log2)) / log2)
		{
			throw new Exception (
				"ScaleSensitiveCoveringBounds::restrictedSubsetCardinality => Invalid Inputs"
			);
		}

		return 6. * fatShatteringCoefficient * Math.log (16. / cover) *
			Math.log (32. * Math.E * _sampleSize / (fatShatteringCoefficient * cover)) / log2 + log2;
	}

	/**
	 * Compute the Log of the Weight Loading Coefficient for the Maximum Cover Term in:
	 * 
	 * 	{Probability that the Empirical Error .gt. Cover} .lte. 4 * exp (-m * Cover^2 / 128) *
	 * 		[[Max Covering Number Over the Specified Sample]]
	 * 
	 * Reference is:
	 *
	 *	- D. Haussler (1995): Sphere Packing Numbers for Subsets of the Boolean n-Cube with Bounded
	 *		Vapnik-Chervonenkis Dimension, Journal of the COmbinatorial Theory A 69 (2) 217.
	 *
	 * @param cover The Specified Cover
	 * 
	 * @return Log of the Weight Loading Coefficient for the Maximum Cover Term
	 * 
	 * @throws Exception Thrown if the Log of the Weight Loading Coefficient cannot be computed
	 */

	public double upperProbabilityBoundWeight (
		final double cover)
		throws Exception
	{
		if (!NumberUtil.IsValid (cover) || 0. == cover) {
			throw new Exception (
				"ScaleSensitiveCoveringBounds::upperProbabilityBoundWeight => Invalid Inputs"
			);
		}

		return Math.log (4.) - (cover * cover * _sampleSize / 128.);
	}

	@Override public double logLowerBound (
		final double cover)
		throws Exception
	{
		return restrictedSubsetCardinality (cover);
	}

	@Override public double logUpperBound (
		final double cover)
		throws Exception
	{
		return _r1r1FatShatter.evaluate (4. * cover) / 32.;
	}
}
