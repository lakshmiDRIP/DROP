
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
 * <i>SegmentPredictorResponseDerivative</i> contains the segment local parameters used for the segment
 * calibration. It holds the edge Y value and the derivatives. It exposes the following functions:
 *
 * <br><br>
 *  <ul>
 *  	<li>
 * 			Retrieve the Response Value as well as the DResponseDPredictorOrdinate Array.
 *  	<li>
 *  	</li>
 * 			Aggregate the 2 Predictor Ordinate Response Derivatives by applying the Cardinal Tension Weight.
 *  	</li>
 *  </ul>
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

public class SegmentPredictorResponseDerivative {
	private double _dblResponseValue = java.lang.Double.NaN;
	private double[] _adblDResponseDPredictorOrdinate = null;

	/**
	 * Aggregate the 2 Predictor Ordinate Response Derivatives by applying the Cardinal Tension Weight
	 * 
	 * @param sprdA Predictor Ordinate Response Derivative A
	 * @param sprdB Predictor Ordinate Response Derivative B
	 * @param dblCardinalTension Cardinal Tension
	 * 
	 * @return The Aggregated Predictor Ordinate Response Derivatives
	 */

	public static final SegmentPredictorResponseDerivative CardinalEdgeAggregate (
		final org.drip.spline.params.SegmentPredictorResponseDerivative sprdA,
		final org.drip.spline.params.SegmentPredictorResponseDerivative sprdB,
		final double dblCardinalTension)
	{
		if (null == sprdA || null == sprdB || !org.drip.quant.common.NumberUtil.IsValid (dblCardinalTension))
			return null;

		int iNumDeriv = 0;

		double[] adblEdgeDResponseDPredictorOrdinateA = sprdA.getDResponseDPredictorOrdinate();

		double[] adblEdgeDResponseDPredictorOrdinateB = sprdB.getDResponseDPredictorOrdinate();

		if ((null != adblEdgeDResponseDPredictorOrdinateA && null == adblEdgeDResponseDPredictorOrdinateB) ||
			(null == adblEdgeDResponseDPredictorOrdinateA && null != adblEdgeDResponseDPredictorOrdinateB) ||
				(null != adblEdgeDResponseDPredictorOrdinateA && null != adblEdgeDResponseDPredictorOrdinateB
					&& (iNumDeriv = adblEdgeDResponseDPredictorOrdinateA.length) !=
						adblEdgeDResponseDPredictorOrdinateB.length))
			return null;

		double dblAggregatedEdgeResponseValue = 0.5 * (1. - dblCardinalTension) * (sprdA.responseValue() +
			sprdB.responseValue());

		if (null == adblEdgeDResponseDPredictorOrdinateA || null == adblEdgeDResponseDPredictorOrdinateB || 0
			== iNumDeriv) {
			try {
				return new SegmentPredictorResponseDerivative (dblAggregatedEdgeResponseValue, null);
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		double[] adblEdgeDResponseDPredictorOrdinate = new double[iNumDeriv];

		for (int i = 0; i < iNumDeriv; ++i)
			adblEdgeDResponseDPredictorOrdinate[i] = 0.5 * (1. - dblCardinalTension) *
				(adblEdgeDResponseDPredictorOrdinateA[i] + adblEdgeDResponseDPredictorOrdinateB[i]);

		try {
			return new SegmentPredictorResponseDerivative (dblAggregatedEdgeResponseValue,
				adblEdgeDResponseDPredictorOrdinate);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * SegmentPredictorResponseDerivative constructor
	 * 
	 * @param dblResponseValue Edge Response Value
	 * @param adblDResponseDPredictorOrdinate Array of ordered Edge Derivatives
	 * 
	 * @throws java.lang.Exception Thrown if the inputs are invalid
	 */

	public SegmentPredictorResponseDerivative (
		final double dblResponseValue,
		final double[] adblDResponseDPredictorOrdinate)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (_dblResponseValue = dblResponseValue))
			throw new java.lang.Exception ("SegmentPredictorResponseDerivative ctr: Invalid Inputs!");

		_adblDResponseDPredictorOrdinate = adblDResponseDPredictorOrdinate;
	}

	/**
	 * Retrieve the Response Value
	 * 
	 * @return The Response Value
	 */

	public double responseValue()
	{
		return _dblResponseValue;
	}

	/**
	 * Retrieve the DResponseDPredictorOrdinate Array
	 * 
	 * @return DResponseDPredictorOrdinate Array
	 */

	public double[] getDResponseDPredictorOrdinate()
	{
		return _adblDResponseDPredictorOrdinate;
	}
}
