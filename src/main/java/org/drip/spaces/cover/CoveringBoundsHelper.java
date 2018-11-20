
package org.drip.spaces.cover;

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
 * <i>CoveringBoundsHelper</i> contains the assortment of Utilities used in the Computation of Upper Bounds
 * for Normed Single Function Spaces and Function Space Products. The References are:
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
 * <br><br>
 *  <ul>
 *		<li><b>Project</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces">Spaces</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces/cover">Covering Number</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/StatisticalLearning">Statistical Learning Library</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class CoveringBoundsHelper {

	/**
	 * Compute the Dyadic Entropy Number from the nth Entropy Number
	 * 
	 * @param dblLogNEntropyNumber Log of the nth Entropy Number
	 * 
	 * @return The Dyadic Entropy Number
	 * 
	 * @throws java.lang.Exception Thrown if the Dyadic Entropy Number cannot be calculated
	 */

	public static final double DyadicEntropyNumber (
		final double dblLogNEntropyNumber)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblLogNEntropyNumber))
			throw new java.lang.Exception ("CoveringBoundsHelper::DyadicEntropyNumber => Invalid Inputs");

		return 1. + (dblLogNEntropyNumber / java.lang.Math.log (2.));
	}

	/**
	 * Compute the Upper Bound for the Entropy Number of the Operator Custom Covering Number Metric Product
	 *  across both the Function Classes
	 * 
	 * @param mocbA The Maurey Operator Covering Bounds for Class A
	 * @param mocbB The Maurey Operator Covering Bounds for Class B
	 * @param iEntropyNumberIndexA Entropy Number Index for Class A
	 * @param iEntropyNumberIndexB Entropy Number Index for Class B
	 * 
	 * @return The Upper Bound for the Entropy Number of the Operator Custom Covering Number Metric Product
	 *  across both the Function Classes
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public static final double CarlStephaniProductBound (
		final org.drip.spaces.cover.MaureyOperatorCoveringBounds mocbA,
		final org.drip.spaces.cover.MaureyOperatorCoveringBounds mocbB,
		final int iEntropyNumberIndexA,
		final int iEntropyNumberIndexB)
		throws java.lang.Exception
	{
		if (null == mocbA || null == mocbB)
			throw new java.lang.Exception
				("CoveringBoundsHelper::CarlStephaniProductBound => Invalid Maurey Bounds for the Function Class");

		return mocbA.entropyNumberUpperBound (iEntropyNumberIndexA) * mocbB.entropyNumberUpperBound
			(iEntropyNumberIndexB);
	}

	/**
	 * Compute the Upper Bound for the Entropy Number of the Operator Custom Covering Number Metric Product
	 *  across both the Function Classes using the Function Class Norm
	 * 
	 * @param mocbA The Maurey Operator Covering Bounds for Class A
	 * @param mocbB The Maurey Operator Covering Bounds for Class B
	 * @param dblNormA The Function Class A Norm
	 * @param dblNormB The Function Class B Norm
	 * @param iEntropyNumberIndex Entropy Number Index for either Class
	 * 
	 * @return The Upper Bound for the Entropy Number of the Operator Custom Covering Number Metric
	 * 	Product across both the Function Classes using the Function Norm
	 */

	public static final org.drip.spaces.cover.CarlStephaniNormedBounds CarlStephaniProductNorm (
		final org.drip.spaces.cover.MaureyOperatorCoveringBounds mocbA,
		final org.drip.spaces.cover.MaureyOperatorCoveringBounds mocbB,
		final double dblNormA,
		final double dblNormB,
		final int iEntropyNumberIndex)
	{
		if (null == mocbA || null == mocbB) return null;

		try {
			return new org.drip.spaces.cover.CarlStephaniNormedBounds (mocbA.entropyNumberUpperBound
				(iEntropyNumberIndex) * dblNormB, mocbB.entropyNumberUpperBound (iEntropyNumberIndex) *
					dblNormA);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
