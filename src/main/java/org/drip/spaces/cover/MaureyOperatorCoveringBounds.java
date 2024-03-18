
package org.drip.spaces.cover;

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
 * <i>MaureyOperatorCoveringBounds</i> implements the estimate the Upper Bounds and/or Absolute Values of the
 * 	Covering Number for the Hilbert R<sup>d</sup> To Supremum R<sup>d</sup> Operator Class. The Main
 * 	References are:
 *
 * <br><br>
 *  <ul>
 *  	<li>
 *  		Carl, B. (1985): Inequalities of the Bernstein-Jackson type and the Degree of Compactness of
 *  			Operators in Banach Spaces <i>Annals of the Fourier Institute</i> <b>35 (3)</b> 79-118
 *  	</li>
 *  	<li>
 *  		Carl, B., and I. Stephani (1990): <i>Entropy, Compactness, and the Approximation of Operators</i>
 *  			<b>Cambridge University Press</b> Cambridge UK 
 *  	</li>
 *  	<li>
 *  		Williamson, R. C., A. J. Smola, and B. Scholkopf (2000): Entropy Numbers of Linear Function
 *  			Classes, in: <i>Proceedings of the 13th Annual Conference on Computational Learning
 *  				Theory</i> <b>ACM</b> New York
 *  	</li>
 *  </ul>
 *
 *  It provides the following Functionality:
 *
 *  <ul>
 * 		<li>Maurey Constant - from the Hilbert - Supremum Identity Map Estimate</li>
 * 		<li>Maurey Constant - from the Williamson-Smola-Scholkopf Estimate</li>
 * 		<li>Construct an Instance Hilbert To Supremum Identity Map based Maurey Operator Covering Bounds</li>
 * 		<li>Construct an Instance of the Maurey Operator Covering Bounds based upon the Williamson, Smola, and Scholkopf Estimate</li>
 * 		<li><i>MaureyOperatorCoveringBounds</i> Constructor</li>
 * 		<li>Retrieve the Maurey Constant</li>
 * 		<li>Retrieve the Supremum Dimension</li>
 * 		<li>Retrieve the Operator Norm of Interest</li>
 * 		<li>Compute the Upper Bound for the Dyadic Entropy Number</li>
 * 		<li>Compute the Upper Bound for the Entropy Number</li>
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

public class MaureyOperatorCoveringBounds
{

	/**
	 * Maurey Constant - from the Hilbert - Supremum Identity Map Estimate
	 */

	public static final double HILBERT_SUPREMUM_IDENTITY_CONSTANT = 1.86;

	/**
	 * Maurey Constant - from the Williamson-Smola-Scholkopf Estimate
	 */

	public static final double WILLIAMSON_SMOLA_SCHOLKOPF_CONSTANT = 103.;

	private int _iSupremumDimension = -1;
	private double _dblOperatorNorm = java.lang.Double.NaN;
	private double _dblMaureyConstant = java.lang.Double.NaN;

	/**
	 * Construct an Instance Hilbert To Supremum Identity Map based Maurey Operator Covering Bounds
	 * 
	 * @param iSupremumDimension The Operator Supremum Output Space Dimension
	 * @param dblOperatorNorm The Operator Norm of Interest
	 * 
	 * @return The Instance Hilbert To Supremum Identity Map based Maurey Operator Covering Bounds
	 */

	public static final MaureyOperatorCoveringBounds HilbertSupremumIdentityMap (
		final int iSupremumDimension,
		final double dblOperatorNorm)
	{
		try {
			return new MaureyOperatorCoveringBounds (HILBERT_SUPREMUM_IDENTITY_CONSTANT,
				iSupremumDimension, dblOperatorNorm);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an Instance of the Maurey Operator Covering Bounds based upon the Williamson, Smola, and
	 *  Scholkopf Estimate
	 * 
	 * @param iSupremumDimension The Operator Supremum Output Space Dimension
	 * @param dblOperatorNorm The Operator Norm of Interest
	 * 
	 * @return Maurey Operator Covering Bounds based upon the Williamson, Smola, and Scholkopf Estimate
	 */

	public static final MaureyOperatorCoveringBounds WilliamsonSmolaScholkopfEstimate (
		final int iSupremumDimension,
		final double dblOperatorNorm)
	{
		try {
			return new MaureyOperatorCoveringBounds (WILLIAMSON_SMOLA_SCHOLKOPF_CONSTANT,
				iSupremumDimension, dblOperatorNorm);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * <i>MaureyOperatorCoveringBounds</i> Constructor
	 * 
	 * @param dblMaureyConstant The Maurey Constant
	 * @param iSupremumDimension The Operator Supremum Output Space Dimension
	 * @param dblOperatorNorm The Operator Norm of Interest
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public MaureyOperatorCoveringBounds (
		final double dblMaureyConstant,
		final int iSupremumDimension,
		final double dblOperatorNorm)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (_dblMaureyConstant = dblMaureyConstant) || 0 >=
			(_iSupremumDimension = iSupremumDimension) || !org.drip.numerical.common.NumberUtil.IsValid
				(_dblOperatorNorm = dblOperatorNorm))
			throw new java.lang.Exception ("MaureyOperatorCoveringBounds ctr => Invalid Inputs");
	}

	/**
	 * Retrieve the Maurey Constant
	 * 
	 * @return The Maurey Constant
	 */

	public double maureyConstant()
	{
		return _dblMaureyConstant;
	}

	/**
	 * Retrieve the Supremum Dimension
	 * 
	 * @return The Supremum Dimension
	 */

	public int supremumDimension()
	{
		return _iSupremumDimension;
	}

	/**
	 * Retrieve the Operator Norm of Interest
	 * 
	 * @return The Operator Norm of Interest
	 */

	public double operatorNorm()
	{
		return _dblOperatorNorm;
	}

	/**
	 * Compute the Upper Bound for the Dyadic Entropy Number
	 * 
	 * @param iEntropyNumberIndex The Entropy Number Index
	 * 
	 * @return The Upper Bound for the DyadicEntropy Number
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double dyadicEntropyUpperBound (
		final int iEntropyNumberIndex)
		throws java.lang.Exception
	{
		if (0 >= iEntropyNumberIndex)
			throw new java.lang.Exception
				("MaureyOperatorCoveringBounds::dyadicEntropyUpperBound => Invalid Inputs");

		return _dblMaureyConstant * _dblOperatorNorm * java.lang.Math.sqrt ((java.lang.Math.log (1. +
			(((double) _iSupremumDimension) / ((double) iEntropyNumberIndex))) / iEntropyNumberIndex));
	}

	/**
	 * Compute the Upper Bound for the Entropy Number
	 * 
	 * @param iEntropyNumberIndex The Entropy Number Index
	 * 
	 * @return The Upper Bound for the Entropy Number
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double entropyNumberUpperBound (
		final int iEntropyNumberIndex)
		throws java.lang.Exception
	{
		if (0 >= iEntropyNumberIndex)
			throw new java.lang.Exception
				("MaureyOperatorCoveringBounds::entropyNumberUpperBound => Invalid Inputs");

		double dblLogNPlus1 = 1. + java.lang.Math.log (iEntropyNumberIndex);

		return _dblMaureyConstant * _dblOperatorNorm * java.lang.Math.sqrt ((1. + (((double)
			_iSupremumDimension) / dblLogNPlus1)) / dblLogNPlus1);
	}
}
