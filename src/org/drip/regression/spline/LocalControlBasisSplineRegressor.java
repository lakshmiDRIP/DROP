
package org.drip.regression.spline;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
 * Copyright (C) 2012 Lakshmi Krishnamurthy
 * 
 *  This file is part of DRIP, a free-software/open-source library for buy/side financial/trading model
 *  	libraries targeting analysts and developers
 *  	https://lakshmidrip.github.io/DRIP/
 *  
 *  DRIP is composed of four main libraries:
 *  
 *  - DRIP Fixed Income - https://lakshmidrip.github.io/DRIP-Fixed-Income/
 *  - DRIP Asset Allocation - https://lakshmidrip.github.io/DRIP-Asset-Allocation/
 *  - DRIP Numerical Optimizer - https://lakshmidrip.github.io/DRIP-Numerical-Optimizer/
 *  - DRIP Statistical Learning - https://lakshmidrip.github.io/DRIP-Statistical-Learning/
 * 
 *  - DRIP Fixed Income: Library for Instrument/Trading Conventions, Treasury Futures/Options,
 *  	Funding/Forward/Overnight Curves, Multi-Curve Construction/Valuation, Collateral Valuation and XVA
 *  	Metric Generation, Calibration and Hedge Attributions, Statistical Curve Construction, Bond RV
 *  	Metrics, Stochastic Evolution and Option Pricing, Interest Rate Dynamics and Option Pricing, LMM
 *  	Extensions/Calibrations/Greeks, Algorithmic Differentiation, and Asset Backed Models and Analytics.
 * 
 *  - DRIP Asset Allocation: Library for model libraries for MPT framework, Black Litterman Strategy
 *  	Incorporator, Holdings Constraint, and Transaction Costs.
 * 
 *  - DRIP Numerical Optimizer: Library for Numerical Optimization and Spline Functionality.
 * 
 *  - DRIP Statistical Learning: Library for Statistical Evaluation and Machine Learning.
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
 * LocalControlBasisSplineRegressor implements the local control basis spline regressor for the given basis
 *  spline. As part of the regression run, it executes the following:
 *  - Calibrate and compute the left and the right Jacobian.
 *  - Insert the Local Control Hermite, Cardinal, and Catmull-Rom knots.
 *  - Run Regressor for the C1 Local Control C1 Slope Insertion Bessel/Hermite Spline.
 *  - Compute an intermediate value Jacobian.
 *
 * @author Lakshmi Krishnamurthy
 */

public class LocalControlBasisSplineRegressor extends org.drip.regression.core.UnitRegressionExecutor {
	private org.drip.spline.stretch.MultiSegmentSequence _mss = null;
	private org.drip.spline.stretch.MultiSegmentSequence _mssBesselHermite = null;
	private org.drip.spline.stretch.MultiSegmentSequence _mssHermiteInsert = null;
	private org.drip.spline.stretch.MultiSegmentSequence _mssCardinalInsert = null;
	private org.drip.spline.stretch.MultiSegmentSequence _mssCatmullRomInsert = null;

	private final boolean DumpRNVD (
		final java.lang.String strStretchName,
		final org.drip.spline.stretch.MultiSegmentSequence mss,
		final org.drip.regression.core.RegressionRunDetail rrd)
	{
		double dblX = 0.;
		double dblXMax = 4.;

		while (dblX <= dblXMax) {
			try {
				if (!rrd.set (getName() + "_" + strStretchName + "_" + dblX,
					org.drip.quant.common.FormatUtil.FormatDouble (mss.responseValue (dblX), 1, 2, 1.) +
						" | " + mss.monotoneType (dblX)))
					return false;
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return false;
			}

			if (!rrd.set (getName() + "_" + strStretchName + "_" + dblX + "_Jack",
				mss.jackDResponseDCalibrationInput (dblX, 1).displayString()))
				return false;

			dblX += 0.5;
		}

		return true;
	}

	/**
	 * LocalControlBasisSplineRegressor constructor
	 * 
	 * @param strName Regressor Name
	 * @param strScenarioName Regression Scenario Name
	 * @param strBasisSpline Basis Spline
	 * @param fsbp Basis Set Builder Parameters
	 * @param iCk Continuity Ck
	 * 
	 * @throws java.lang.Exception Thrown if inputs are invalid
	 */

	public LocalControlBasisSplineRegressor (
		final java.lang.String strName,
		final java.lang.String strScenarioName,
		final java.lang.String strBasisSpline,
		final org.drip.spline.basis.FunctionSetBuilderParams fsbp,
		final int iCk)
		throws java.lang.Exception
	{
		super (strName, strScenarioName);

		double[] adblX = new double[] {0.00, 1.00,  2.00,  3.00,  4.00};
		int iNumSegment = adblX.length - 1;
		org.drip.spline.params.SegmentCustomBuilderControl[] aSCBC = new
			org.drip.spline.params.SegmentCustomBuilderControl[iNumSegment];

		for (int i = 0; i < iNumSegment; ++i)
			aSCBC[i] = new org.drip.spline.params.SegmentCustomBuilderControl (strBasisSpline, fsbp,
				org.drip.spline.params.SegmentInelasticDesignControl.Create (iCk, 1), new
					org.drip.spline.params.ResponseScalingShapeControl (true, new
						org.drip.function.r1tor1.QuadraticRationalShapeControl (1.)), null);

		if (null == (_mss =
			org.drip.spline.stretch.MultiSegmentSequenceBuilder.CreateUncalibratedStretchEstimator
				("SPLINE_STRETCH", adblX, aSCBC)))
			throw new java.lang.Exception
				("LocalControlBasisSplineRegressor ctr: Cannot Construct Stretch!");
	}

	@Override public boolean preRegression()
	{
		double[] adblY = new double[] {1.00, 4.00, 15.00, 40.00, 85.00};
		double[] adblDYDX = new double[] {1.00, 6.00, 17.00, 34.00, 57.00};

		org.drip.spline.params.SegmentCustomBuilderControl scbc = null;
		org.drip.spline.params.SegmentPredictorResponseDerivative[] aSPRDLeft = new
			org.drip.spline.params.SegmentPredictorResponseDerivative[adblY.length - 1];
		org.drip.spline.params.SegmentPredictorResponseDerivative[] aSPRDRight = new
			org.drip.spline.params.SegmentPredictorResponseDerivative[adblY.length - 1];

		for (int i = 0; i < adblY.length - 1; ++i) {
			try {
				aSPRDLeft[i] = new org.drip.spline.params.SegmentPredictorResponseDerivative (adblY[i], new
					double[] {adblDYDX[i]});

				aSPRDRight[i] = new org.drip.spline.params.SegmentPredictorResponseDerivative (adblY[i + 1],
					new double[] {adblDYDX[i + 1]});
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return false;
			}
		}

		try {
			scbc = new org.drip.spline.params.SegmentCustomBuilderControl
				(org.drip.spline.stretch.MultiSegmentSequenceBuilder.BASIS_SPLINE_POLYNOMIAL, new
					org.drip.spline.basis.PolynomialFunctionSetParams (4),
						org.drip.spline.params.SegmentInelasticDesignControl.Create (2, 2), new
							org.drip.spline.params.ResponseScalingShapeControl (true, new
								org.drip.function.r1tor1.QuadraticRationalShapeControl (1.)), null);
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return false;
		}

		org.drip.spline.params.SegmentCustomBuilderControl[] aSCBC = new
			org.drip.spline.params.SegmentCustomBuilderControl[adblY.length - 1]; 

		for (int i = 0; i < adblY.length - 1; ++i)
			aSCBC[i] = scbc;

		if (null == (_mssBesselHermite =
			org.drip.spline.pchip.LocalControlStretchBuilder.CreateBesselCubicSplineStretch
				("BESSEL_STRETCH", new double[] {0.00, 1.00,  2.00,  3.00,  4.00}, adblY, aSCBC, null,
					org.drip.spline.stretch.MultiSegmentSequence.CALIBRATE, true, true)))
			return false;

		return _mss.setupHermite (aSPRDLeft, aSPRDRight, null, null,
			org.drip.spline.stretch.MultiSegmentSequence.CALIBRATE_JACOBIAN);
	}

	@Override public boolean execRegression()
	{
		try {
			if (null == (_mssHermiteInsert = org.drip.spline.stretch.MultiSegmentSequenceModifier.InsertKnot
				(_mss, 2.5, new org.drip.spline.params.SegmentPredictorResponseDerivative (27.5, new double[]
					{25.5}), new org.drip.spline.params.SegmentPredictorResponseDerivative (27.5, new
						double[] {25.5}))))
				return false;
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return false;
		}

		if (null == (_mssCardinalInsert =
			org.drip.spline.stretch.MultiSegmentSequenceModifier.InsertCardinalKnot (_mss, 2.5, 0.)))
			return false;

		return null != (_mssCatmullRomInsert =
			org.drip.spline.stretch.MultiSegmentSequenceModifier.InsertCatmullRomKnot (_mss, 2.5));
	}

	@Override public boolean postRegression (
		final org.drip.regression.core.RegressionRunDetail rrd)
	{
		return DumpRNVD ("LOCAL_NO_KNOT", _mss, rrd) && DumpRNVD ("LOCAL_HERMITE_KNOT", _mssHermiteInsert,
			rrd) && DumpRNVD ("LOCAL_CARDINAL_KNOT", _mssCardinalInsert, rrd) && DumpRNVD
				("LOCAL_CATMULL_ROM_KNOT", _mssCatmullRomInsert, rrd) && DumpRNVD ("LOCAL_C1_BESSEL_HERMITE",
					_mssBesselHermite, rrd);
	}
}
