
package org.drip.spaces.cover;

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
 * <i>CoveringBoundsHelper</i> contains the assortment of Utilities used in the Computation of Upper Bounds
 * 	for Normed Single Function Spaces and Function Space Products. The References are:
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
 * 		<li>Compute the Dyadic Entropy Number from the n<sup>th</sup> Entropy Number</li>
 * 		<li>Compute the Upper Bound for the Entropy Number of the Operator Custom Covering Number Metric Product across both the Function Classes</li>
 * 		<li>Compute the Upper Bound for the Entropy Number of the Operator Custom Covering Number Metric Product across both the Function Classes using the Function Class Norm</li>
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

public class CoveringBoundsHelper
{

	/**
	 * Compute the Dyadic Entropy Number from the n<sup>th</sup> Entropy Number
	 * 
	 * @param logNEntropyNumber Log of the n<sup>th</sup> Entropy Number
	 * 
	 * @return The Dyadic Entropy Number
	 * 
	 * @throws Exception Thrown if the Dyadic Entropy Number cannot be calculated
	 */

	public static final double DyadicEntropyNumber (
		final double logNEntropyNumber)
		throws Exception
	{
		if (!NumberUtil.IsValid (logNEntropyNumber)) {
			throw new Exception ("CoveringBoundsHelper::DyadicEntropyNumber => Invalid Inputs");
		}

		return 1. + (logNEntropyNumber / Math.log (2.));
	}

	/**
	 * Compute the Upper Bound for the Entropy Number of the Operator Custom Covering Number Metric Product
	 *  across both the Function Classes
	 * 
	 * @param maureyOperatorCoveringBoundsA The Maurey Operator Covering Bounds for Class A
	 * @param maureyOperatorCoveringBoundsB The Maurey Operator Covering Bounds for Class B
	 * @param entropyNumberIndexA Entropy Number Index for Class A
	 * @param entropyNumberIndexB Entropy Number Index for Class B
	 * 
	 * @return The Upper Bound for the Entropy Number of the Operator Custom Covering Number Metric Product
	 *  across both the Function Classes
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public static final double CarlStephaniProductBound (
		final MaureyOperatorCoveringBounds maureyOperatorCoveringBoundsA,
		final MaureyOperatorCoveringBounds maureyOperatorCoveringBoundsB,
		final int entropyNumberIndexA,
		final int entropyNumberIndexB)
		throws Exception
	{
		if (null == maureyOperatorCoveringBoundsA || null == maureyOperatorCoveringBoundsB) {
			throw new Exception (
				"CoveringBoundsHelper::CarlStephaniProductBound => Invalid Maurey Bounds for the Function Class"
			);
		}

		return maureyOperatorCoveringBoundsA.entropyNumberUpperBound (entropyNumberIndexA) *
			maureyOperatorCoveringBoundsB.entropyNumberUpperBound (entropyNumberIndexB);
	}

	/**
	 * Compute the Upper Bound for the Entropy Number of the Operator Custom Covering Number Metric Product
	 *  across both the Function Classes using the Function Class Norm
	 * 
	 * @param maureyOperatorCoveringBoundsA The Maurey Operator Covering Bounds for Class A
	 * @param maureyOperatorCoveringBoundsB The Maurey Operator Covering Bounds for Class B
	 * @param normA The Function Class A Norm
	 * @param normB The Function Class B Norm
	 * @param entropyNumberIndex Entropy Number Index for either Class
	 * 
	 * @return The Upper Bound for the Entropy Number of the Operator Custom Covering Number Metric Product
	 *  across both the Function Classes using the Function Norm
	 */

	public static final CarlStephaniNormedBounds CarlStephaniProductNorm (
		final MaureyOperatorCoveringBounds maureyOperatorCoveringBoundsA,
		final MaureyOperatorCoveringBounds maureyOperatorCoveringBoundsB,
		final double normA,
		final double normB,
		final int entropyNumberIndex)
	{
		if (null == maureyOperatorCoveringBoundsA || null == maureyOperatorCoveringBoundsB) {
			return null;
		}

		try {
			return new CarlStephaniNormedBounds (
				maureyOperatorCoveringBoundsA.entropyNumberUpperBound (entropyNumberIndex) * normB,
				maureyOperatorCoveringBoundsB.entropyNumberUpperBound (entropyNumberIndex) * normA
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
