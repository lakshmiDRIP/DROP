
package org.drip.regression.spline;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
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
 *  	graph builder/navigator, and computational support.
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
 *  - Graph Algorithm
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
 * <i>HermiteBasisSplineRegressor</i> implements the Hermite basis spline regressor for the given basis
 * spline. As part of the regression run, it executes the following:
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

public class HermiteBasisSplineRegressor extends org.drip.regression.spline.BasisSplineRegressor {
	private java.lang.String _strName = "";
	private org.drip.spline.segment.LatentStateResponseModel _seg1 = null;
	private org.drip.spline.segment.LatentStateResponseModel _seg2 = null;
	private org.drip.numerical.differentiation.WengertJacobian _wjLeft = null;
	private org.drip.numerical.differentiation.WengertJacobian _wjRight = null;
	private org.drip.numerical.differentiation.WengertJacobian _wjValue = null;

	/**
	 * Create an instance of Hermite BasisSplineRegressor
	 * 
	 * @param strName Regressor Name
	 * @param strScenarioName Regressor Scenario Name
	 * @param iNumBasis Number of Basis Functions
	 * @param iCk Ck
	 * 
	 * @return The BasisSplineRegressor Instance
	 */

	public static final org.drip.regression.spline.BasisSplineRegressor CreateHermiteSplineRegressor (
		final java.lang.String strName,
		final java.lang.String strScenarioName,
		final int iNumBasis,
		final int iCk)
	{
		try {
			org.drip.spline.basis.FunctionSet fs =
				org.drip.spline.basis.FunctionSetBuilder.PolynomialBasisSet (new
					org.drip.spline.basis.PolynomialFunctionSetParams (iNumBasis));

			return null == fs ? null : new HermiteBasisSplineRegressor (strName, strScenarioName, fs, iCk);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	private HermiteBasisSplineRegressor (
		final java.lang.String strName,
		final java.lang.String strScenarioName,
		final org.drip.spline.basis.FunctionSet fs,
		final int iCk)
		throws java.lang.Exception
	{
		super (strName, strScenarioName, fs, iCk);

		org.drip.spline.params.SegmentInelasticDesignControl segParams =
			org.drip.spline.params.SegmentInelasticDesignControl.Create (iCk, 1);

		org.drip.spline.params.ResponseScalingShapeControl rssc = new
			org.drip.spline.params.ResponseScalingShapeControl (true, new
				org.drip.function.r1tor1custom.QuadraticRationalShapeControl (1.));

		if (null == (_seg1 = org.drip.spline.segment.LatentStateResponseModel.Create (0.0, 1.0, fs, rssc,
			segParams)) || null == (_seg2 = org.drip.spline.segment.LatentStateResponseModel.Create (1.0,
				2.0, fs, rssc, segParams)))
			throw new java.lang.Exception ("HermiteBasisSplineRegressor ctr: Cant create the segments");
	}

	@Override public boolean execRegression()
	{
		try {
			return null != (_wjLeft = _seg1.jackDCoeffDEdgeParams (new double[] {0., 1.}, new double[] {1.,
				4.}, new double[] {1.}, new double[] {6.}, null, null)) && null != (_wjRight =
					_seg2.jackDCoeffDEdgeParams (new double[] {1., 2.}, new double[] {4., 15.}, new double[]
						{6.}, new double[] {17.}, null, null)) && _seg2.calibrate (_seg1, 14., null) && null
							!= (_wjValue = _seg2.jackDResponseDEdgeInput (1.5, 1));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override public boolean postRegression (
		final org.drip.regression.core.RegressionRunDetail rnvd)
	{
		try {
			if (!rnvd.set (_strName + "_Seg1_0_0", "" + _seg1.responseValue (0.))) return false;

			if (!rnvd.set (_strName + "_Seg1_1_0", "" + _seg1.responseValue (1.))) return false;

			if (!rnvd.set (_strName + "_Seg1_Jack", _wjLeft.displayString()));

			if (!rnvd.set (_strName + "_Seg1_Head_Jack", _seg1.jackDCoeffDEdgeInputs().displayString()));

			if (!rnvd.set (_strName + "_Seg1_Monotone", _seg1.monotoneType().toString()));

			if (!rnvd.set (_strName + "_Seg2_1_0", "" + _seg2.responseValue (1.))) return false;

			if (!rnvd.set (_strName + "_Seg2_2_0", "" + _seg2.responseValue (2.))) return false;

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
