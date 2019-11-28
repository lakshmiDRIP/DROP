
package org.drip.spline.segment;

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
 * <i>LatentStateInelastic</i> contains the spline segment in-elastic fields - in this case the start/end
 * ranges. It exports the following functions:
 *
 * <br><br>
 *  <ul>
 *  	<li>
 * 			Retrieve the Segment Left/Right Predictor Ordinate
 *  	</li>
 *  	<li>
 * 			Find out if the Predictor Ordinate is inside the segment - inclusive of left/right
 *  	</li>
 *  	<li>
 * 			Get the Width of the Predictor Ordinate in this Segment
 *  	</li>
 *  	<li>
 * 			Transform the Predictor Ordinate to the Local Segment Predictor Ordinate
 *  	</li>
 *  	<li>
 * 			Transform the Local Predictor Ordinate to the Segment Ordinate
 *  	</li>
 *  </ul>
 *
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/SplineBuilderLibrary.md">Spline Builder Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/README.md">Basis Splines and Linear Compounders across a Broad Family of Spline Basis Functions</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/segment/README.md">Flexure Penalizing Best Fit Segment</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class LatentStateInelastic implements java.lang.Comparable<LatentStateInelastic> {
	private double _dblPredictorOrdinateLeft = java.lang.Double.NaN;
	private double _dblPredictorOrdinateRight = java.lang.Double.NaN;

	/**
	 * LatentStateInelastic constructor
	 * 
	 * @param dblPredictorOrdinateLeft Segment Predictor Ordinate Left
	 * @param dblPredictorOrdinateRight Segment Predictor Ordinate Right
	 * 
	 * @throws java.lang.Exception Thrown if inputs are invalid
	 */

	public LatentStateInelastic (
		final double dblPredictorOrdinateLeft,
		final double dblPredictorOrdinateRight)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (_dblPredictorOrdinateLeft = dblPredictorOrdinateLeft)
			|| !org.drip.numerical.common.NumberUtil.IsValid (_dblPredictorOrdinateRight =
				dblPredictorOrdinateRight) || _dblPredictorOrdinateLeft >= _dblPredictorOrdinateRight)
			throw new java.lang.Exception ("LatentStateInelastic ctr: Invalid inputs!");
	}

	/**
	 * Retrieve the Segment Left Predictor Ordinate
	 * 
	 * @return Segment Left Predictor Ordinate
	 */

	public double left()
	{
		return _dblPredictorOrdinateLeft;
	}

	/**
	 * Retrieve the Segment Right Predictor Ordinate
	 * 
	 * @return Segment Right Predictor Ordinate
	 */

	public double right()
	{
		return _dblPredictorOrdinateRight;
	}

	/**
	 * Find out if the Predictor Ordinate is inside the segment - inclusive of left/right.
	 * 
	 * @param dblPredictorOrdinate Predictor Ordinate
	 * 
	 * @return TRUE - Predictor Ordinate is inside the segment
	 * 
	 * @throws java.lang.Exception Thrown if the input is invalid
	 */

	public boolean in (
		final double dblPredictorOrdinate)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblPredictorOrdinate))
			throw new java.lang.Exception ("LatentStateInelastic::in => Invalid Inputs");

		return _dblPredictorOrdinateLeft <= dblPredictorOrdinate && _dblPredictorOrdinateRight >=
			dblPredictorOrdinate;
	}

	/**
	 * Get the Width of the Predictor Ordinate in this Segment
	 * 
	 * @return Segment Width
	 */

	public double width()
	{
		return _dblPredictorOrdinateRight - _dblPredictorOrdinateLeft;
	}

	/**
	 * Transform the Predictor Ordinate to the Local Segment Predictor Ordinate
	 * 
	 * @param dblPredictorOrdinate The Global Predictor Ordinate
	 * 
	 * @return Local Segment Predictor Ordinate
	 * 
	 * @throws java.lang.Exception Thrown if the input is invalid
	 */

	public double localize (
		final double dblPredictorOrdinate)
		throws java.lang.Exception
	{
		if (!in (dblPredictorOrdinate))
			throw new java.lang.Exception ("LatentStateInelastic::localize: Invalid inputs!");

		return (dblPredictorOrdinate - _dblPredictorOrdinateLeft) / (_dblPredictorOrdinateRight -
			_dblPredictorOrdinateLeft);
	}

	/**
	 * Transform the Local Predictor Ordinate to the Segment Ordinate
	 * 
	 * @param dblLocalPredictorOrdinate The Local Segment Predictor Ordinate
	 * 
	 * @return The Segment Ordinate
	 * 
	 * @throws java.lang.Exception Thrown if the input is invalid
	 */

	public double delocalize (
		final double dblLocalPredictorOrdinate)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblLocalPredictorOrdinate))
			throw new java.lang.Exception ("LatentStateInelastic::delocalize => Invalid Inputs");

		return _dblPredictorOrdinateLeft + dblLocalPredictorOrdinate * (_dblPredictorOrdinateRight -
			_dblPredictorOrdinateLeft);
	}

	@Override public int hashCode()
	{
		long lBits = java.lang.Double.doubleToLongBits ((int) _dblPredictorOrdinateLeft);

		return (int) (lBits ^ (lBits >>> 32));
	}

	@Override public int compareTo (
		final org.drip.spline.segment.LatentStateInelastic ieOther)
	{
		if (_dblPredictorOrdinateLeft > ieOther._dblPredictorOrdinateLeft) return 1;

		if (_dblPredictorOrdinateLeft < ieOther._dblPredictorOrdinateLeft) return -1;

		return 0;
	}
}
