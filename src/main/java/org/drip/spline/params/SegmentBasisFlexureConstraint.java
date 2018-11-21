
package org.drip.spline.params;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
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
 * <i>SegmentBasisFlexureConstraint</i> holds the set of fields needed to characterize a single local linear
 * Constraint, expressed linearly as a combination of the local Predictor Ordinates and their corresponding
 * Response Basis Function Realizations. Constraints are expressed as
 * 
 * 			C := Sigma_(i,j) [W_i * B_i(x_j)] = V where
 * 
 * 	x_j - The Predictor Ordinate at Node j
 * 	B_i - The Coefficient for the Response Basis Function i
 * 	W_i - Weight applied for the Response Basis Function i
 * 	V - Value of the Constraint
 * 
 * SegmentBasisFlexureConstraint can be viewed as the localized basis function transpose of
 * SegmentResponseValueConstraint.
 *
 * <br><br>
 *  <ul>
 *		<li><b>Project</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline">Spaces</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/params">Params</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/SplineBuilder">Spline Builder Library</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class SegmentBasisFlexureConstraint {
	private double[] _adblResponseBasisCoeffWeight = null;
	private double _dblConstraintValue = java.lang.Double.NaN;

	/**
	 * SegmentBasisFlexureConstraint constructor
	 * 
	 * @param adblResponseBasisCoeffWeight The Weight for each of the Coefficients in the Basis Function Set
	 * @param dblConstraintValue The Constraint Value
	 *
	 * @throws java.lang.Exception Thrown if the inputs are invalid
	 */

	public SegmentBasisFlexureConstraint (
		double[] adblResponseBasisCoeffWeight,
		double dblConstraintValue)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (_dblConstraintValue = dblConstraintValue) || null ==
			(_adblResponseBasisCoeffWeight = adblResponseBasisCoeffWeight) || 0 ==
				_adblResponseBasisCoeffWeight.length)
			throw new java.lang.Exception ("SegmentBasisFlexureConstraint ctr: Invalid Inputs");
	}

	/**
	 * Retrieve the Array of the Response Basis Coefficient Weights
	 * 
	 * @return The Array of the Response Basis Coefficient Weights
	 */

	public double[] responseBasisCoeffWeights()
	{
		return _adblResponseBasisCoeffWeight;
	}

	/**
	 * Retrieve the Constraint Value
	 * 
	 * @return The Constraint Value
	 */

	public double contraintValue()
	{
		return _dblConstraintValue;
	}
}
