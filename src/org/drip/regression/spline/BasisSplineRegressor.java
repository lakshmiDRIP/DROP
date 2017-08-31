
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
 * BasisSplineRegressor implements the custom basis spline regressor for the given basis spline. As part of
 *  the regression run, it executes the following:
 *  - Calibrate and compute the left and the right Jacobian.
 *  - Reset right node and re-run calibration.
 *  - Compute an intermediate value Jacobian.
 *
 * @author Lakshmi Krishnamurthy
 */

public class BasisSplineRegressor extends org.drip.regression.core.UnitRegressionExecutor {
	private java.lang.String _strName = "";
	private org.drip.spline.segment.LatentStateResponseModel _seg1 = null;
	private org.drip.spline.segment.LatentStateResponseModel _seg2 = null;
	private org.drip.quant.calculus.WengertJacobian _wjLeft = null;
	private org.drip.quant.calculus.WengertJacobian _wjRight = null;
	private org.drip.quant.calculus.WengertJacobian _wjValue = null;

	/**
	 * Create an instance of Polynomial BasisSplineRegressor
	 * 
	 * @param strName Regressor Name
	 * @param strScenarioName Regressor Scenario Name
	 * @param iNumBasis Number of Basis Functions
	 * @param iCk Ck
	 * 
	 * @return The BasisSplineRegressor Instance
	 */

	public static final BasisSplineRegressor CreatePolynomialSplineRegressor (
		final java.lang.String strName,
		final java.lang.String strScenarioName,
		final int iNumBasis,
		final int iCk)
	{
		try {
			org.drip.spline.basis.FunctionSet fs =
				org.drip.spline.basis.FunctionSetBuilder.PolynomialBasisSet (new
					org.drip.spline.basis.PolynomialFunctionSetParams (iNumBasis));

			return null == fs ? null : new BasisSplineRegressor (strName, strScenarioName, fs, iCk);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Create an instance of Bernstein Polynomial BasisSplineRegressor
	 * 
	 * @param strName Regressor Name
	 * @param strScenarioName Regressor Scenario Name
	 * @param iNumBasis Number of Basis Functions
	 * @param iCk Ck
	 * 
	 * @return The BasisSplineRegressor Instance
	 */

	public static final BasisSplineRegressor CreateBernsteinPolynomialSplineRegressor (
		final java.lang.String strName,
		final java.lang.String strScenarioName,
		final int iNumBasis,
		final int iCk)
	{
		try {
			org.drip.spline.basis.FunctionSet fs =
				org.drip.spline.basis.FunctionSetBuilder.BernsteinPolynomialBasisSet (new
					org.drip.spline.basis.PolynomialFunctionSetParams (iNumBasis));

			return null == fs ? null : new BasisSplineRegressor (strName, strScenarioName, fs, iCk);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Create an instance of Exponential BasisSplineRegressor
	 * 
	 * @param strName Regressor Name
	 * @param strScenarioName Regressor Scenario Name
	 * @param dblTension Tension Parameter
	 * 
	 * @return The BasisSplineRegressor Instance
	 */

	public static final BasisSplineRegressor CreateExponentialTensionSplineRegressor (
		final java.lang.String strName,
		final java.lang.String strScenarioName,
		final double dblTension)
	{
		try {
			org.drip.spline.basis.FunctionSet fs =
				org.drip.spline.basis.FunctionSetBuilder.ExponentialTensionBasisSet (new
					org.drip.spline.basis.ExponentialTensionSetParams (dblTension));

			return null == fs ? null : new BasisSplineRegressor (strName, strScenarioName, fs, 2);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Create an instance of Hyperbolic BasisSplineRegressor
	 * 
	 * @param strName Regressor Name
	 * @param strScenarioName Regressor Scenario Name
	 * @param dblTension Tension Parameter
	 * 
	 * @return The BasisSplineRegressor Instance
	 */

	public static final BasisSplineRegressor CreateHyperbolicTensionSplineRegressor (
		final java.lang.String strName,
		final java.lang.String strScenarioName,
		final double dblTension)
	{
		try {
			org.drip.spline.basis.FunctionSet fs =
				org.drip.spline.basis.FunctionSetBuilder.HyperbolicTensionBasisSet (new
					org.drip.spline.basis.ExponentialTensionSetParams (dblTension));

			return null == fs ? null : new BasisSplineRegressor (strName, strScenarioName, fs, 2);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Create an instance of the Kaklis-Pandelis BasisSplineRegressor
	 * 
	 * @param strName Regressor Name
	 * @param strScenarioName Regressor Scenario Name
	 * @param iKPPolynomialTension KP Polynomial Tension Parameter
	 * 
	 * @return The BasisSplineRegressor Instance
	 */

	public static final BasisSplineRegressor CreateKaklisPandelisSplineRegressor (
		final java.lang.String strName,
		final java.lang.String strScenarioName,
		final int iKPPolynomialTension)
	{
		try {
			org.drip.spline.basis.FunctionSet fs =
				org.drip.spline.basis.FunctionSetBuilder.KaklisPandelisBasisSet (new
					org.drip.spline.basis.KaklisPandelisSetParams (iKPPolynomialTension));

			return null == fs ? null : new BasisSplineRegressor (strName, strScenarioName, fs, 2);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	protected BasisSplineRegressor (
		final java.lang.String strName,
		final java.lang.String strScenarioName,
		final org.drip.spline.basis.FunctionSet fs,
		final int iCk)
		throws java.lang.Exception
	{
		super (strName, strScenarioName);

		org.drip.spline.params.SegmentInelasticDesignControl segParams =
			org.drip.spline.params.SegmentInelasticDesignControl.Create (iCk, 2);

		org.drip.spline.params.ResponseScalingShapeControl rssc = new
			org.drip.spline.params.ResponseScalingShapeControl (true, new
				org.drip.function.r1tor1.QuadraticRationalShapeControl (1.));

		if (null == (_seg1 = org.drip.spline.segment.LatentStateResponseModel.Create (1.0, 3.0, fs, rssc,
			segParams)) || null == (_seg2 = org.drip.spline.segment.LatentStateResponseModel.Create (3.0,
				6.0, fs, rssc, segParams)))
			throw new java.lang.Exception ("BasisSplineRegressor ctr: Cant create the segments");
	}

	@Override public boolean preRegression()
	{
		return true;
	}

	@Override public boolean execRegression()
	{
		try {
			return null != (_wjLeft = _seg1.jackDCoeffDEdgeParams (25., 0., 20.25, null)) && null !=
				(_wjRight = _seg2.jackDCoeffDEdgeParams (_seg1, "Default", 16., null, java.lang.Double.NaN,
					null)) && _seg2.calibrate (_seg1, 14., null) && null != (_wjValue =
						_seg2.jackDResponseDEdgeInput (5., 1));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override public boolean postRegression (
		final org.drip.regression.core.RegressionRunDetail rnvd)
	{
		try {
			if (!rnvd.set (_strName + "_Seg1_1_0", "" + _seg1.responseValue (1.))) return false;

			if (!rnvd.set (_strName + "_Seg1_3_0", "" + _seg1.responseValue (3.))) return false;

			if (!rnvd.set (_strName + "_Seg1_Jack", _wjLeft.displayString()));

			if (!rnvd.set (_strName + "_Seg1_Head_Jack", _seg1.jackDCoeffDEdgeInputs().displayString()));

			if (!rnvd.set (_strName + "_Seg1_Monotone", _seg1.monotoneType().toString()));

			if (!rnvd.set (_strName + "_Seg2_3_0", "" + _seg2.responseValue (3.))) return false;

			if (!rnvd.set (_strName + "_Seg2_6_0", "" + _seg2.responseValue (6.))) return false;

			if (!rnvd.set (_strName + "_Seg2_Jack", _wjRight.displayString()));

			if (!rnvd.set (_strName + "_Seg2_Head_Jack", _seg2.jackDCoeffDEdgeInputs().displayString()));

			if (!rnvd.set (_strName + "_Seg2_Monotone", _seg2.monotoneType().toString()));

			return rnvd.set (_strName + "_Seg2_Value_Jack", _wjValue.displayString());
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return false;
	}
}
