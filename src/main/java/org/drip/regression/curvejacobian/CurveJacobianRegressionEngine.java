
package org.drip.regression.curvejacobian;

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
 * <i>CurveJacobianRegressionEngine</i> implements the RegressionEngine for the curve Jacobian regression. It
 * adds the CashJacobianRegressorSet, the EDFJacobianRegressorSet, the IRSJacobianRegressorSet, and the
 * DiscountCurveJacobianRegressorSet, and launches the regression engine.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Project</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/regression">Regression</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/regression/curvejacobian">Curve Jacobian</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/NumericalOptimizer">Numerical Optimizer Library</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class CurveJacobianRegressionEngine extends org.drip.regression.core.RegressionEngine {

	/**
	 * CurveJacobianRegressionEngine constructor
	 * 
	 * @param iNumRuns Number of regression runs
	 * @param iRegressionDetail Detailed desired of the regression run
	 * 
	 * @throws java.lang.Exception Thrown if the inputs are invalid
	 */

	public CurveJacobianRegressionEngine (
		final int iNumRuns,
		final int iRegressionDetail)
		throws java.lang.Exception
	{
		super (iNumRuns, iRegressionDetail);
	}

	@Override public boolean initRegressionEnv()
	{
		org.drip.service.env.EnvManager.InitEnv ("");

		return super.initRegressionEnv();
	}

	public static void main (
		final java.lang.String[] astrArgs)
		throws java.lang.Exception
	{
		CurveJacobianRegressionEngine cjre = new CurveJacobianRegressionEngine (10,
			org.drip.regression.core.RegressionEngine.REGRESSION_DETAIL_MODULE_UNIT_DECOMPOSED |
				org.drip.regression.core.RegressionEngine.REGRESSION_DETAIL_STATS);

		cjre.addRegressorSet (new org.drip.regression.curvejacobian.CashJacobianRegressorSet());

		cjre.addRegressorSet (new org.drip.regression.curvejacobian.EDFJacobianRegressorSet());

		cjre.addRegressorSet (new org.drip.regression.curvejacobian.IRSJacobianRegressorSet());

		cjre.addRegressorSet (new org.drip.regression.curvejacobian.DiscountCurveJacobianRegressorSet());

		cjre.launch();
	}
}
