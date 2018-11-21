
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
 * <i>TensionProcessedBasisHat</i> implements the processed hat basis function of the form laid out in the
 * basic framework outlined in Koch and Lyche (1989), Koch and Lyche (1993), and Kvasov (2000) Papers.
 *
 * <br><br>
 *  <ul>
 *		<li><b>Project</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline">Spaces</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/bspline">B Spline</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/SplineBuilder">Spline Builder Library</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class TensionProcessedBasisHat extends org.drip.spline.bspline.TensionBasisHat {
	private int _iDerivOrder = -1;
	private org.drip.spline.bspline.TensionBasisHat _tbhRaw = null;

	/**
	 * TensionProcessedBasisHat constructor
	 * 
	 * @param tbhRaw The Raw TBH
	 * @param iDerivOrder Derivative Order off of the Raw TBH
	 * 
	 * @throws java.lang.Exception Thrown if the input is invalid
	 */

	public TensionProcessedBasisHat (
		final org.drip.spline.bspline.TensionBasisHat tbhRaw,
		final int iDerivOrder)
		throws java.lang.Exception
	{
		super (tbhRaw.left(), tbhRaw.right(), tbhRaw.tension());

		if (null == (_tbhRaw = tbhRaw) || 0 >= (_iDerivOrder = iDerivOrder))
			throw new java.lang.Exception ("TensionProcessedBasisHat ctr: Invalid Input");
	}

	@Override public double evaluate (
		final double dblPredictorOrdinate)
		throws java.lang.Exception
	{
		return in (dblPredictorOrdinate) ? _tbhRaw.derivative (dblPredictorOrdinate, _iDerivOrder) : 0.;
	}

	@Override public double derivative (
		final double dblPredictorOrdinate,
		final int iOrder)
		throws java.lang.Exception
	{
		if (0 > iOrder)
			throw new java.lang.Exception ("TensionProcessedBasisHat::derivative => Invalid Inputs");

		if (!in (dblPredictorOrdinate)) return 0.;

		return _tbhRaw.derivative (dblPredictorOrdinate, iOrder + _iDerivOrder);
	}

	@Override public double integrate (
		final double dblBegin,
		final double dblEnd)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblBegin) || !org.drip.quant.common.NumberUtil.IsValid
			(dblEnd))
			throw new java.lang.Exception ("TensionProcessedBasisHat::integrate => Invalid Inputs");

		double dblBoundedBegin = org.drip.quant.common.NumberUtil.Bound (dblBegin, left(), right());

		double dblBoundedEnd = org.drip.quant.common.NumberUtil.Bound (dblEnd, left(), right());

		if (dblBoundedBegin >= dblBoundedEnd) return 0.;

		if (1 == _iDerivOrder) return _tbhRaw.evaluate (dblBoundedEnd) - _tbhRaw.evaluate (dblBoundedBegin);

		return _tbhRaw.derivative (dblBoundedEnd, _iDerivOrder - 1) - _tbhRaw.derivative (dblBoundedBegin,
			_iDerivOrder - 1);
	}

	@Override public double normalizer()
		throws java.lang.Exception
	{
		if (1 == _iDerivOrder) return _tbhRaw.evaluate (right()) - _tbhRaw.evaluate (left());

		return _tbhRaw.derivative (right(), _iDerivOrder - 1) - _tbhRaw.derivative (left(), _iDerivOrder -
			1);
	}
}
