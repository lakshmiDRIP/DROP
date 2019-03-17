
package org.drip.spline.bspline;

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
 * <i>ExponentialTensionRightRaw</i> implements the TensionBasisHat interface in accordance with the raw
 * right exponential hat basis function laid out in the basic framework outlined in Koch and Lyche (1989),
 * Koch and Lyche (1993), and Kvasov (2000) Papers.
 *
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/SplineBuilderLibrary.md">Spline Builder Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline">Spline</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/bspline">B Spline</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class ExponentialTensionRightRaw extends org.drip.spline.bspline.TensionBasisHat {

	/**
	 * ExponentialTensionRightRaw constructor
	 * 
	 * @param dblLeftPredictorOrdinate The Left Predictor Ordinate
	 * @param dblRightPredictorOrdinate The Right Predictor Ordinate
	 * @param dblTension Tension of the Tension Hat Function
	 * 
	 * @throws java.lang.Exception Thrown if the input is invalid
	 */

	public ExponentialTensionRightRaw (
		final double dblLeftPredictorOrdinate,
		final double dblRightPredictorOrdinate,
		final double dblTension)
		throws java.lang.Exception
	{
		super (dblLeftPredictorOrdinate, dblRightPredictorOrdinate, dblTension);
	}

	@Override public double evaluate (
		final double dblPredictorOrdinate)
		throws java.lang.Exception
	{
		if (!in (dblPredictorOrdinate)) return 0.;

		double dblAdjPredictorOrdinate = tension() * (right() - dblPredictorOrdinate);

		return (java.lang.Math.sinh (dblAdjPredictorOrdinate) - dblAdjPredictorOrdinate) / (tension() *
			tension() * java.lang.Math.sinh (tension() * (right() - left())));
	}

	@Override public double derivative (
		final double dblPredictorOrdinate,
		final int iOrder)
		throws java.lang.Exception
	{
		if (0 > iOrder)
			throw new java.lang.Exception ("ExponentialTensionRightRaw::derivative => Invalid Inputs");

		if (!in (dblPredictorOrdinate)) return 0.;

		double dblWidth = right() - left();

		if (1 == iOrder)
			return (1. - java.lang.Math.cosh (tension() * (right() - dblPredictorOrdinate))) / (tension() *
				java.lang.Math.sinh (tension() * dblWidth));

		return java.lang.Math.pow (-tension(), iOrder - 2) * (0 == iOrder % 2 ? java.lang.Math.sinh
			(tension() * (right() - dblPredictorOrdinate)) : java.lang.Math.cosh (tension() * (right() -
				dblPredictorOrdinate))) / java.lang.Math.sinh (tension() * dblWidth);
	}

	@Override public double integrate (
		final double dblBegin,
		final double dblEnd)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblBegin) || !org.drip.numerical.common.NumberUtil.IsValid
			(dblEnd))
			throw new java.lang.Exception ("ExponentialTensionRightRaw::integrate => Invalid Inputs");

		double dblBoundedBegin = org.drip.numerical.common.NumberUtil.Bound (dblBegin, left(), right());

		double dblBoundedEnd = org.drip.numerical.common.NumberUtil.Bound (dblEnd, left(), right());

		if (dblBoundedBegin >= dblBoundedEnd) return 0.;

		if (0. == tension()) return dblBoundedEnd - dblBoundedBegin;

		double dblBoundedBeginRightGap = right() - dblBoundedBegin;

		double dblBoundedEndRightGap = right() - dblBoundedEnd;

		return (0.5 * tension() * tension() * (dblBoundedEndRightGap * dblBoundedEndRightGap -
			dblBoundedBeginRightGap * dblBoundedBeginRightGap) + java.lang.Math.cosh (tension() *
				dblBoundedBeginRightGap) - java.lang.Math.cosh (tension() * dblBoundedEndRightGap)) /
					(tension() * tension() * tension() * java.lang.Math.sinh (tension() * (right() -
						left())));
	}

	@Override public double normalizer()
		throws java.lang.Exception
	{
		double dblWidth = right() - left();

		return (-0.5 * tension() * tension() * dblWidth * dblWidth + java.lang.Math.cosh (tension() *
			dblWidth) - 1.) / (tension() * tension() * tension() * java.lang.Math.sinh (tension() *
				dblWidth));
	}
}
