
package org.drip.regression.fixedpointfinder;

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
 * <i>OpenRegressorSet</i> implements the regression run for the Open (i.e., Newton) Fixed Point Search
 * Method.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationSupportLibrary.md">Computation Support</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/regression/README.md">Regression Engine Core and the Unit Regressors</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/regression/fixedpointfinder/README.md">Fixed Point Finder Regression Engine</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class OpenRegressorSet implements org.drip.regression.core.RegressorSet {
	private org.drip.function.definition.R1ToR1 _of = null;
	private java.lang.String _strRegressionScenario = "org.drip.math.solver1D.FixedPointFinderNewton";

	private java.util.List<org.drip.regression.core.UnitRegressor> _setRegressors = new
		java.util.ArrayList<org.drip.regression.core.UnitRegressor>();

	/**
	 * OpenRegressorSet Constructor
	 */

	public OpenRegressorSet()
	{
		_of = new org.drip.function.definition.R1ToR1 (null)
		{
			public double evaluate (
				final double dblVariate)
				throws java.lang.Exception
			{
				if (java.lang.Double.isNaN (dblVariate))
					throw new java.lang.Exception
						("FixedPointFinderRegressorOF.evalTarget => Invalid variate!");

				/* return java.lang.Math.cos (dblVariate) - dblVariate * dblVariate * dblVariate;

				return dblVariate * dblVariate * dblVariate - 3. * dblVariate * dblVariate + 2. *
					dblVariate;

				return dblVariate * dblVariate * dblVariate + 4. * dblVariate + 4.;

				return 32. * dblVariate * dblVariate * dblVariate * dblVariate * dblVariate * dblVariate
					- 48. * dblVariate * dblVariate * dblVariate * dblVariate + 18. * dblVariate *
						dblVariate - 1.; */

				return 1. + 3. * dblVariate - 2. * java.lang.Math.sin (dblVariate);
			}

			@Override public double integrate (
				final double dblBegin,
				final double dblEnd)
				throws java.lang.Exception
			{
				return org.drip.numerical.r1integration.Integrator.Boole (this, dblBegin, dblEnd);
			}
		};
	}

	@Override public boolean setupRegressors()
	{
		try {
			_setRegressors.add (new org.drip.regression.core.UnitRegressionExecutor
				("NewtonFixedPointFinder", _strRegressionScenario)
			{
				org.drip.function.r1tor1solver.FixedPointFinderNewton fpfbNewton = null;
				org.drip.function.r1tor1solver.FixedPointFinderOutput fpfopNewton = null;

				@Override public boolean preRegression()
				{
					try {
						fpfbNewton = new org.drip.function.r1tor1solver.FixedPointFinderNewton (0., _of, true);

						return true;
					} catch (java.lang.Exception e) {
						e.printStackTrace();
					}

					return false;
				}

				@Override public boolean execRegression()
				{
					if (null == (fpfopNewton = fpfbNewton.findRoot())) return false;

					return true;
				}

				@Override public boolean postRegression (
					final org.drip.regression.core.RegressionRunDetail rnvd)
				{
					rnvd.set ("FixedPoint", "" + fpfopNewton.getRoot());

					return true;
				}
			});
		} catch (java.lang.Exception e) {
			e.printStackTrace();
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
