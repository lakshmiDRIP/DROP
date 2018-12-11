
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
 * <i>SegmentBasisFunction</i> is the abstract class over which the local ordered envelope functions for the
 * B Splines are implemented. It exposes the following stubs:
 *  <ul>
 *  	<li>
 * 			Retrieve the Order of the B Spline.
 *  	</li>
 *  	<li>
 * 			Retrieve the Leading Predictor Ordinate.
 *  	</li>
 *  	<li>
 * 			Retrieve the Following Predictor Ordinate.
 *  	</li>
 *  	<li>
 * 			Retrieve the Trailing Predictor Ordinate.
 *  	</li>
 *  	<li>
 * 			Compute the complete Envelope Integrand - this will serve as the Envelope Normalizer.
 *  	</li>
 *  	<li>
 * 			Evaluate the Cumulative Normalized Integrand up to the given ordinate.
 *  	</li>
 *  </ul>
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

public abstract class SegmentBasisFunction extends org.drip.function.definition.R1ToR1 {
	private int _iBSplineOrder = -1;
	private double _dblLeadingPredictorOrdinate = java.lang.Double.NaN;
	private double _dblTrailingPredictorOrdinate = java.lang.Double.NaN;
	private double _dblFollowingPredictorOrdinate = java.lang.Double.NaN;

	protected SegmentBasisFunction (
		final int iBSplineOrder,
		final double dblLeadingPredictorOrdinate,
		final double dblFollowingPredictorOrdinate,
		final double dblTrailingPredictorOrdinate)
		throws java.lang.Exception
	{
		super (null);

		if (!org.drip.quant.common.NumberUtil.IsValid (_dblLeadingPredictorOrdinate =
			dblLeadingPredictorOrdinate) || !org.drip.quant.common.NumberUtil.IsValid
				(_dblFollowingPredictorOrdinate = dblFollowingPredictorOrdinate) ||
					!org.drip.quant.common.NumberUtil.IsValid (_dblTrailingPredictorOrdinate =
						dblTrailingPredictorOrdinate) || _dblLeadingPredictorOrdinate >=
							_dblFollowingPredictorOrdinate || _dblFollowingPredictorOrdinate >=
								_dblTrailingPredictorOrdinate || 2 > (_iBSplineOrder = iBSplineOrder))
			throw new java.lang.Exception ("SegmentBasisFunction ctr: Invalid Inputs");
	}

	/**
	 * Retrieve the Order of the B Spline
	 * 
	 * @return The Order of the B Spline
	 */

	public int bSplineOrder()
	{
		return _iBSplineOrder;
	}

	/**
	 * Retrieve the Leading Predictor Ordinate
	 * 
	 * @return The Leading Predictor Ordinate
	 */

	public double leading()
	{
		return _dblLeadingPredictorOrdinate;
	}

	/**
	 * Retrieve the Following Predictor Ordinate
	 * 
	 * @return The Following Predictor Ordinate
	 */

	public double following()
	{
		return _dblFollowingPredictorOrdinate;
	}

	/**
	 * Retrieve the Trailing Predictor Ordinate
	 * 
	 * @return The Trailing Predictor Ordinate
	 */

	public double trailing()
	{
		return _dblTrailingPredictorOrdinate;
	}

	/**
	 * Compute the complete Envelope Integrand - this will serve as the Envelope Normalizer.
	 * 
	 * @return The Complete Envelope Integrand.
	 * 
	 * @throws java.lang.Exception Thrown if the Complete Envelope Integrand cannot be calculated.
	 */

	public abstract double normalizer()
		throws java.lang.Exception;

	/**
	 * Evaluate the Cumulative Normalized Integrand up to the given ordinate
	 * 
	 * @param dblPredictorOrdinate The Predictor Ordinate
	 * 
	 * @return The Cumulative Normalized Integrand up to the given ordinate
	 * 
	 * @throws java.lang.Exception Thrown if input is invalid
	 */

	public abstract double normalizedCumulative (
		final double dblPredictorOrdinate)
		throws java.lang.Exception;
}
