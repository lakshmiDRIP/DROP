
package org.drip.regression.spline;

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
 * Copyright (C) 2012 Lakshmi Krishnamurthy
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
 * <i>BasisSplineRegressor</i> implements the custom basis spline regressor for the given basis spline. As
 * part of the regression run, it executes the following:
 * 
 * <br><br>
 *  <ul>
 *  	<li>
 *  		Calibrate and compute the left and the right Jacobian.
 *  	</li>
 *  	<li>
 *  		Reset right node and re-run calibration.
 *  	</li>
 *  	<li>
 *  		Compute an intermediate value Jacobian.
 *  	</li>
 *  </ul>
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationSupportLibrary.md">Computation Support</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/regression/README.md">Regression Engine Core and the Unit Regressors</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/regression/spline/README.md">Custom Basis Spline Regression Engine</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class BasisSplineRegressor extends org.drip.regression.core.UnitRegressionExecutor {
	private java.lang.String _strName = "";
	private org.drip.spline.segment.LatentStateResponseModel _seg1 = null;
	private org.drip.spline.segment.LatentStateResponseModel _seg2 = null;
	private org.drip.numerical.differentiation.WengertJacobian _wjLeft = null;
	private org.drip.numerical.differentiation.WengertJacobian _wjRight = null;
	private org.drip.numerical.differentiation.WengertJacobian _wjValue = null;

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
