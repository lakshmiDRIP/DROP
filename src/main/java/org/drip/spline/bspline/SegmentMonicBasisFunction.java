
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
 * <i>SegmentMonicBasisFunction</i> implements the local monic B Spline that envelopes the predictor
 * ordinates, and the corresponding set of ordinates/basis functions. SegmentMonicBasisFunction uses the
 * left/right TensionBasisHat instances to achieve its implementation goals.
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

public class SegmentMonicBasisFunction extends org.drip.spline.bspline.SegmentBasisFunction {
	private org.drip.spline.bspline.TensionBasisHat _tbhLeft = null;
	private org.drip.spline.bspline.TensionBasisHat _tbhRight = null;

	/**
	 * SegmentMonicBasisFunction constructor
	 * 
	 * @param tbhLeft Left Tension Basis Hat Function
	 * @param tbhRight Right Tension Basis Hat Function
	 * 
	 * @throws java.lang.Exception Thrown if Inputs are invalid
	 */

	public SegmentMonicBasisFunction (
		final org.drip.spline.bspline.TensionBasisHat tbhLeft,
		final org.drip.spline.bspline.TensionBasisHat tbhRight)
		throws java.lang.Exception
	{
		super (2, tbhLeft.left(), tbhRight.left(), tbhRight.right());

		if (null == (_tbhLeft = tbhLeft) || null == (_tbhRight = tbhRight))
			throw new java.lang.Exception ("SegmentMonicBasisFunction ctr: Invalid Inputs");
	}

	@Override public double evaluate (
		final double dblPredictorOrdinate)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblPredictorOrdinate))
			throw new java.lang.Exception ("SegmentMonicBasisFunction::evaluate => Invalid Inputs");

		if (dblPredictorOrdinate < leading() || dblPredictorOrdinate > trailing()) return 0.;

		return dblPredictorOrdinate < following() ? _tbhLeft.evaluate (dblPredictorOrdinate) :
			_tbhRight.evaluate (dblPredictorOrdinate);
	}

	@Override public double derivative (
		final double dblPredictorOrdinate,
		final int iOrder)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblPredictorOrdinate))
			throw new java.lang.Exception ("SegmentMonicBasisFunction::derivative => Invalid Inputs");

		if (dblPredictorOrdinate < leading() || dblPredictorOrdinate > trailing()) return 0.;

		return dblPredictorOrdinate < following() ? _tbhLeft.derivative (dblPredictorOrdinate, iOrder) :
			_tbhRight.derivative (dblPredictorOrdinate, iOrder);
	}

	@Override public double integrate (
		final double dblBegin,
		final double dblEnd)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblBegin) || !org.drip.quant.common.NumberUtil.IsValid
			(dblEnd))
			throw new java.lang.Exception ("SegmentMonicBasisFunction::integrate => Invalid Inputs");

		if (dblBegin >= dblEnd) return 0.;

		if (dblBegin <= leading()) {
			if (dblEnd <= leading()) return 0.;

			if (dblEnd <= following()) return _tbhLeft.integrate (leading(), dblEnd);

			if (dblEnd <= trailing())
				return _tbhLeft.integrate (leading(), following()) + _tbhRight.integrate (following(),
					dblEnd);

			return _tbhLeft.integrate (leading(), following()) + _tbhRight.integrate (following(),
				trailing());
		}

		if (dblBegin <= following()) {
			if (dblEnd <= following()) return _tbhLeft.integrate (dblBegin, dblEnd);

			if (dblEnd <= trailing())
				return _tbhLeft.integrate (dblBegin, following()) + _tbhRight.integrate (following(),
					dblEnd);

			return _tbhLeft.integrate (dblBegin, following()) + _tbhRight.integrate (following(),
				trailing());
		}

		if (dblBegin <= trailing()) {
			if (dblEnd <= trailing()) return _tbhRight.integrate (following(), dblEnd);

			return _tbhRight.integrate (following(), trailing());
		}

		return 0.;
	}

	@Override public double normalizer()
		throws java.lang.Exception
	{
		return _tbhLeft.integrate (leading(), following()) + _tbhRight.integrate (following(), trailing());
	}

	@Override public double normalizedCumulative (
		final double dblPredictorOrdinate)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblPredictorOrdinate))
			throw new java.lang.Exception
				("SegmentMonicBasisFunction::normalizedCumulative => Invalid Inputs");

		if (dblPredictorOrdinate <= leading()) return 0.;

		if (dblPredictorOrdinate >= trailing()) return 1.;

		if (dblPredictorOrdinate <= following())
			return _tbhLeft.integrate (leading(), dblPredictorOrdinate) / normalizer();

		return (_tbhLeft.integrate (leading(), following()) + _tbhRight.integrate (following(),
			dblPredictorOrdinate)) / normalizer();
	}
}
