
package org.drip.spline.bspline;

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
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
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
 * <i>CubicRationalRightRaw</i> implements the TensionBasisHat interface in accordance with the raw right
 * cubic rational hat basis function laid out in the basic framework outlined in Koch and Lyche (1989), Koch
 * and Lyche (1993), and Kvasov (2000) Papers.
 *
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/SplineBuilderLibrary.md">Spline Builder Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/README.md">Basis Splines and Linear Compounders across a Broad Family of Spline Basis Functions</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/bspline/README.md">de Boor Rational/Exponential/Tension B-Splines</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class CubicRationalRightRaw extends org.drip.spline.bspline.TensionBasisHat {
	private org.drip.spline.bspline.RightHatShapeControl _rhsc = null;

	/**
	 * CubicRationalRightRaw constructor
	 * 
	 * @param dblLeftPredictorOrdinate The Left Predictor Ordinate
	 * @param dblRightPredictorOrdinate The Right Predictor Ordinate
	 * @param strShapeControlType Type of the Shape Controller to be used - NONE, LINEAR/QUADRATIC Rational
	 * @param dblTension Tension of the Tension Hat Function
	 * 
	 * @throws java.lang.Exception Thrown if the input is invalid
	 */

	public CubicRationalRightRaw (
		final double dblLeftPredictorOrdinate,
		final double dblRightPredictorOrdinate,
		final java.lang.String strShapeControlType,
		final double dblTension)
		throws java.lang.Exception
	{
		super (dblLeftPredictorOrdinate, dblRightPredictorOrdinate, dblTension);

		_rhsc = new org.drip.spline.bspline.RightHatShapeControl (dblLeftPredictorOrdinate,
			dblRightPredictorOrdinate, strShapeControlType, dblTension);
	}

	@Override public double evaluate (
		final double dblPredictorOrdinate)
		throws java.lang.Exception
	{
		if (!in (dblPredictorOrdinate)) return 0.;

		double dblCubicValue = (right() - dblPredictorOrdinate) * (right() - dblPredictorOrdinate) *
			(right() - dblPredictorOrdinate);

		return 0. == tension() ? dblCubicValue / 6. : dblCubicValue * _rhsc.evaluate (dblPredictorOrdinate);
	}

	@Override public double derivative (
		final double dblPredictorOrdinate,
		final int iOrder)
		throws java.lang.Exception
	{
		if (0 > iOrder)
			throw new java.lang.Exception ("CubicRationalRightRaw::derivative => Invalid Inputs");

		if (!in (dblPredictorOrdinate)) return 0.;

		if (0. != tension()) return super.derivative (dblPredictorOrdinate, iOrder);

		double dblGap = right() - dblPredictorOrdinate;

		if (1 == iOrder) return -0.5 * dblGap * dblGap;

		if (2 == iOrder) return dblGap;

		return 3 == iOrder ? -1. : 0.;
	}

	@Override public double integrate (
		final double dblBegin,
		final double dblEnd)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblBegin) || !org.drip.numerical.common.NumberUtil.IsValid
			(dblEnd))
			throw new java.lang.Exception ("CubicRationalRightRaw::integrate => Invalid Inputs");

		if (dblEnd >= dblBegin) return 0.;

		double dblBoundedBegin = org.drip.numerical.common.NumberUtil.Bound (dblBegin, left(), right());

		double dblBoundedEnd = org.drip.numerical.common.NumberUtil.Bound (dblEnd, left(), right());

		if (0. != tension()) return super.integrate (dblBoundedBegin, dblBoundedEnd);

		double dblBeginGap = right() - dblBoundedBegin;

		double dblEndGap = right() - dblBoundedEnd;

		return -0.25 * (dblEndGap * dblEndGap * dblEndGap * dblEndGap - dblBeginGap * dblBeginGap *
			dblBeginGap * dblBeginGap);
	}

	@Override public double normalizer()
	{
		double dblWidth = right() - left();

		return 0.25 * dblWidth * dblWidth * dblWidth * dblWidth;
	}
}
