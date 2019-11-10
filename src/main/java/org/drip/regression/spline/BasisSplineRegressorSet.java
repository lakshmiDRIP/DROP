
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
 * <i>BasisSplineRegressorSet</i> carries out regression testing for the following series of basis splines:
 * 
 * <br><br>
 *  <ul>
 *  	<li>
 * 			#1: Polynomial Basis Spline, n = 2 basis functions, and Ck = 0.
 *  	</li>
 *  	<li>
 * 			#2: Polynomial Basis Spline, n = 3 basis functions, and Ck = 1.
 *  	</li>
 *  	<li>
 * 			#3: Polynomial Basis Spline, n = 4 basis functions, and Ck = 1.
 *  	</li>
 *  	<li>
 * 			#4: Polynomial Basis Spline, n = 4 basis functions, and Ck = 2.
 *  	</li>
 *  	<li>
 * 			#5: Polynomial Basis Spline, n = 5 basis functions, and Ck = 1.
 *  	</li>
 *  	<li>
 * 			#6: Polynomial Basis Spline, n = 5 basis functions, and Ck = 2.
 *  	</li>
 *  	<li>
 * 			#7: Polynomial Basis Spline, n = 5 basis functions, and Ck = 3.
 *  	</li>
 *  	<li>
 * 			#8: Polynomial Basis Spline, n = 6 basis functions, and Ck = 1.
 *  	</li>
 *  	<li>
 * 			#9: Polynomial Basis Spline, n = 6 basis functions, and Ck = 2.
 *  	</li>
 *  	<li>
 * 			#10: Polynomial Basis Spline, n = 6 basis functions, and Ck = 3.
 *  	</li>
 *  	<li>
 * 			#11: Polynomial Basis Spline, n = 6 basis functions, and Ck = 4.
 *  	</li>
 *  	<li>
 * 			#12: Polynomial Basis Spline, n = 7 basis functions, and Ck = 1.
 *  	</li>
 *  	<li>
 * 			#13: Polynomial Basis Spline, n = 7 basis functions, and Ck = 2.
 *  	</li>
 *  	<li>
 * 			#14: Polynomial Basis Spline, n = 7 basis functions, and Ck = 3.
 *  	</li>
 *  	<li>
 * 			#15: Polynomial Basis Spline, n = 7 basis functions, and Ck = 4.
 *  	</li>
 *  	<li>
 * 			#16: Polynomial Basis Spline, n = 7 basis functions, and Ck = 5.
 *  	</li>
 *  	<li>
 * 			#17: Bernstein Polynomial Basis Spline, n = 4 basis functions, and Ck = 2.
 *  	</li>
 *  	<li>
 * 			#18: Exponential Tension Spline, n = 4 basis functions, Tension = 1., and Ck = 2.
 *  	</li>
 *  	<li>
 * 			#19: Hyperbolic Tension Spline, n = 4 basis functions, Tension = 1., and Ck = 2.
 *  	</li>
 *  	<li>
 * 			#20: Kaklis-Pandelis Tension Spline, n = 4 basis functions, KP = 2, and Ck = 2.
 *  	</li>
 *  	<li>
 * 			#21: C1 Hermite Local Spline, n = 4 basis functions, and Ck = 1.
 *  	</li>
 *  	<li>
 * 			#22: Hermite Local Spline with Local, Catmull-Rom, and Cardinal Knots, n = 4 basis functions, and
 * 				Ck = 1.
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

public class BasisSplineRegressorSet implements org.drip.regression.core.RegressorSet {
	private java.lang.String _strRegressionScenario = "PolynomialSplineRegressor";

	private java.util.List<org.drip.regression.core.UnitRegressor> _setRegressors = new
		java.util.ArrayList<org.drip.regression.core.UnitRegressor>();

	/**
	 * BasisSplineRegressorSet constructor - Creates the base spline parameter and initializes the
	 *	regression objects
	 */

	public BasisSplineRegressorSet()
	{
	}

	@Override public boolean setupRegressors()
	{
		try {
			_setRegressors.add
				(org.drip.regression.spline.BasisSplineRegressor.CreatePolynomialSplineRegressor ("N2Ck0",
					_strRegressionScenario, 2, 0));

			_setRegressors.add
				(org.drip.regression.spline.BasisSplineRegressor.CreatePolynomialSplineRegressor ("N3Ck0",
					_strRegressionScenario, 3, 0));

			_setRegressors.add
				(org.drip.regression.spline.BasisSplineRegressor.CreatePolynomialSplineRegressor ("N3Ck1",
					_strRegressionScenario, 3, 1));

			_setRegressors.add
				(org.drip.regression.spline.BasisSplineRegressor.CreatePolynomialSplineRegressor ("N4Ck0",
					_strRegressionScenario, 4, 0));

			_setRegressors.add
				(org.drip.regression.spline.BasisSplineRegressor.CreatePolynomialSplineRegressor ("N4Ck1",
					_strRegressionScenario, 4, 1));

			_setRegressors.add
				(org.drip.regression.spline.BasisSplineRegressor.CreatePolynomialSplineRegressor ("N4Ck2",
					_strRegressionScenario, 4, 2));

			_setRegressors.add
				(org.drip.regression.spline.BasisSplineRegressor.CreatePolynomialSplineRegressor ("N5Ck0",
					_strRegressionScenario, 5, 0));

			_setRegressors.add
				(org.drip.regression.spline.BasisSplineRegressor.CreatePolynomialSplineRegressor ("N5Ck1",
					_strRegressionScenario, 5, 1));

			_setRegressors.add
				(org.drip.regression.spline.BasisSplineRegressor.CreatePolynomialSplineRegressor ("N5Ck2",
					_strRegressionScenario, 5, 2));

			_setRegressors.add
				(org.drip.regression.spline.BasisSplineRegressor.CreatePolynomialSplineRegressor ("N5Ck3",
					_strRegressionScenario, 5, 3));

			_setRegressors.add
				(org.drip.regression.spline.BasisSplineRegressor.CreatePolynomialSplineRegressor ("N6Ck0",
					_strRegressionScenario, 6, 0));

			_setRegressors.add
				(org.drip.regression.spline.BasisSplineRegressor.CreatePolynomialSplineRegressor ("N6Ck1",
					_strRegressionScenario, 6, 1));

			_setRegressors.add
				(org.drip.regression.spline.BasisSplineRegressor.CreatePolynomialSplineRegressor ("N6Ck2",
					_strRegressionScenario, 6, 2));

			_setRegressors.add
				(org.drip.regression.spline.BasisSplineRegressor.CreatePolynomialSplineRegressor ("N6Ck3",
					_strRegressionScenario, 6, 3));

			_setRegressors.add
				(org.drip.regression.spline.BasisSplineRegressor.CreatePolynomialSplineRegressor ("N6Ck4",
					_strRegressionScenario, 6, 4));

			_setRegressors.add
				(org.drip.regression.spline.BasisSplineRegressor.CreatePolynomialSplineRegressor ("N7Ck0",
					_strRegressionScenario, 7, 0));

			_setRegressors.add
				(org.drip.regression.spline.BasisSplineRegressor.CreatePolynomialSplineRegressor ("N7Ck1",
					_strRegressionScenario, 7, 1));

			_setRegressors.add
				(org.drip.regression.spline.BasisSplineRegressor.CreatePolynomialSplineRegressor ("N7Ck2",
					_strRegressionScenario, 7, 2));

			_setRegressors.add
				(org.drip.regression.spline.BasisSplineRegressor.CreatePolynomialSplineRegressor ("N7Ck3",
					_strRegressionScenario, 7, 3));

			_setRegressors.add
				(org.drip.regression.spline.BasisSplineRegressor.CreatePolynomialSplineRegressor ("N7Ck4",
					_strRegressionScenario, 7, 4));

			_setRegressors.add
				(org.drip.regression.spline.BasisSplineRegressor.CreatePolynomialSplineRegressor ("N7Ck5",
					_strRegressionScenario, 7, 5));

			_setRegressors.add
				(org.drip.regression.spline.BasisSplineRegressor.CreateExponentialTensionSplineRegressor
					("ExpTension", _strRegressionScenario, 1.));

			_setRegressors.add
				(org.drip.regression.spline.BasisSplineRegressor.CreateHyperbolicTensionSplineRegressor
					("HyperTension", _strRegressionScenario, 1.));

			_setRegressors.add
				(org.drip.regression.spline.BasisSplineRegressor.CreateKaklisPandelisSplineRegressor ("KP",
					_strRegressionScenario, 2));

			_setRegressors.add
				(org.drip.regression.spline.BasisSplineRegressor.CreateBernsteinPolynomialSplineRegressor
					("Bern_N4Ck2", _strRegressionScenario, 4, 2));

			_setRegressors.add
				(org.drip.regression.spline.HermiteBasisSplineRegressor.CreateHermiteSplineRegressor
					("Hermite_N4Ck1", _strRegressionScenario, 4, 1));

			_setRegressors.add (new org.drip.regression.spline.LocalControlBasisSplineRegressor
				("Hermite_Bessel_CatmullRom_Cardinal_N4Ck1", _strRegressionScenario,
					org.drip.spline.stretch.MultiSegmentSequenceBuilder.BASIS_SPLINE_POLYNOMIAL, new
						org.drip.spline.basis.PolynomialFunctionSetParams (4), 1));

			_setRegressors.add (new org.drip.regression.spline.LagrangePolynomialStretchRegressor
				("Lagrange_Polynomial_Stretch", _strRegressionScenario));
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return false;
		}

		return true;
	}

	@Override public java.util.List<org.drip.regression.core.UnitRegressor> getRegressorSet()
	{
		return _setRegressors;
	}

	@Override public java.lang.String getSetName()
	{
		return _strRegressionScenario;
	}
}
