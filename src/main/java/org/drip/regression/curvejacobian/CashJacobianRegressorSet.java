
package org.drip.regression.curvejacobian;

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
 * <i>CashJacobianRegressorSet</i> implements the regression analysis set for the Cash product related
 * Sensitivity Jacobians. Specifically, it computes the PVDF micro-Jack.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationSupportLibrary.md">Computation Support</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/regression/README.md">Regression Engine Core and the Unit Regressors</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/regression/curvejacobian/README.md">Curve Jacobian Reconciliation Regression Engine</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class CashJacobianRegressorSet implements org.drip.regression.core.RegressorSet {
	private java.lang.String _strRegressionScenario =
		"org.drip.analytics.definition.CashDiscountCurve.CompPVDFJacobian";

	private java.util.List<org.drip.regression.core.UnitRegressor> _setRegressors = new
		java.util.ArrayList<org.drip.regression.core.UnitRegressor>();

	@Override public java.util.List<org.drip.regression.core.UnitRegressor> getRegressorSet()
	{
		return _setRegressors;
	}

	@Override public boolean setupRegressors()
	{
		try {
			_setRegressors.add (new org.drip.regression.core.UnitRegressionExecutor ("CashJacobian",
				_strRegressionScenario) {
				org.drip.analytics.date.JulianDate dtStart = null;
				org.drip.state.discount.MergedDiscountForwardCurve dcCash = null;
				org.drip.numerical.differentiation.WengertJacobian wjPVDF = null;
				org.drip.numerical.differentiation.WengertJacobian aWJComp[] = null;
				org.drip.product.definition.CalibratableComponent aCompCalib[] = null;

				@Override public boolean preRegression() {
					int NUM_CASH_INSTR = 7;
					int aiDate[] = new int[NUM_CASH_INSTR];
					double adblRate[] = new double[NUM_CASH_INSTR];
					double adblCompCalibValue[] = new double[NUM_CASH_INSTR];
					aWJComp = new org.drip.numerical.differentiation.WengertJacobian[NUM_CASH_INSTR];
					java.lang.String astrCalibMeasure[] = new java.lang.String[NUM_CASH_INSTR];
					aCompCalib = new
						org.drip.product.definition.CalibratableComponent[NUM_CASH_INSTR];

					if (null == (dtStart = org.drip.analytics.date.DateUtil.CreateFromYMD (2011, 4, 6)))
						return false;

					aiDate[0] = dtStart.addDays (3).julian(); // ON

					aiDate[1] = dtStart.addDays (4).julian(); // 1D (TN)

					aiDate[2] = dtStart.addDays (9).julian(); // 1W

					aiDate[3] = dtStart.addDays (16).julian(); // 2W

					aiDate[4] = dtStart.addDays (32).julian(); // 1M

					aiDate[5] = dtStart.addDays (62).julian(); // 2M

					aiDate[6] = dtStart.addDays (92).julian(); // 3M

					adblCompCalibValue[0] = .0013;
					adblCompCalibValue[1] = .0017;
					adblCompCalibValue[2] = .0017;
					adblCompCalibValue[3] = .0018;
					adblCompCalibValue[4] = .0020;
					adblCompCalibValue[5] = .0023;
					adblCompCalibValue[6] = .0026;

					for (int i = 0; i < NUM_CASH_INSTR; ++i) {
						adblRate[i] = 0.01;
						astrCalibMeasure[i] = "Rate";

						try {
							aCompCalib[i] = org.drip.product.creator.SingleStreamComponentBuilder.Deposit
								(dtStart.addDays (2), new org.drip.analytics.date.JulianDate (aiDate[i]),
									org.drip.state.identifier.ForwardLabel.Create ("USD", "3M"));
						} catch (java.lang.Exception e) {
							e.printStackTrace();

							return false;
						}
					}

					return null != (dcCash =
						org.drip.state.creator.ScenarioDiscountCurveBuilder.NonlinearBuild (dtStart, "USD",
							aCompCalib, adblCompCalibValue, astrCalibMeasure, null));
				}

				@Override public boolean execRegression()
				{
					for (int i = 0; i < aCompCalib.length; ++i) {
						try {
							if (null == (aWJComp[i] = aCompCalib[i].jackDDirtyPVDManifestMeasure (new
								org.drip.param.valuation.ValuationParams (dtStart, dtStart, "USD"), null,
									org.drip.param.creator.MarketParamsBuilder.Create (dcCash, null,
										null, null, null, null, null), null)))
								return false;
						} catch (java.lang.Exception e) {
							e.printStackTrace();

							return false;
						}
					}

					return null != (wjPVDF = dcCash.compJackDPVDManifestMeasure (dtStart));
				}

				@Override public boolean postRegression (
					final org.drip.regression.core.RegressionRunDetail rnvd)
				{
					for (int i = 0; i < aCompCalib.length; ++i) {
						if (!rnvd.set ("PVDFMicroJack_" + aCompCalib[i].name(), aWJComp[i].displayString()))
							return false;
					}

					return rnvd.set ("CompPVDFJacobian", wjPVDF.displayString());
				}
			});
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return true;
	}

	@Override public java.lang.String getSetName()
	{
		return _strRegressionScenario;
	}
}
