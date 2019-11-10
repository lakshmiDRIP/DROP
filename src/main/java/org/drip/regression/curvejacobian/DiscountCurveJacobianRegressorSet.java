
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
 * <i>DiscountCurveJacobianRegressorSet</i> implements the regression analysis for the full discount curve
 * (built from cash/future/swap) Sensitivity Jacobians. Specifically, it computes the PVDF micro-Jack.
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

public class DiscountCurveJacobianRegressorSet implements org.drip.regression.core.RegressorSet {
	private java.lang.String _strRegressionScenario =
		"org.drip.analytics.definition.IRSDiscountCurve.CompPVDFJacobian";

	private java.util.List<org.drip.regression.core.UnitRegressor> _setRegressors = new
		java.util.ArrayList<org.drip.regression.core.UnitRegressor>();

	@Override public java.util.List<org.drip.regression.core.UnitRegressor> getRegressorSet()
	{
		return _setRegressors;
	}

	@Override public boolean setupRegressors()
	{
		try {
			_setRegressors.add (new org.drip.regression.core.UnitRegressionExecutor ("DiscountCurveJacobian",
				_strRegressionScenario)
			{
				org.drip.analytics.date.JulianDate dtStart = null;
				org.drip.state.discount.MergedDiscountForwardCurve dcIRS = null;
				org.drip.numerical.differentiation.WengertJacobian wjPVDF = null;
				org.drip.numerical.differentiation.WengertJacobian aWJComp[] = null;
				org.drip.product.definition.CalibratableComponent aCompCalib[] = null;

				org.drip.param.market.LatentStateFixingsContainer lsfc = new
					org.drip.param.market.LatentStateFixingsContainer();

				@Override public boolean preRegression()
				{
					int NUM_DC_INSTR = 15;
					int aiDate[] = new int[NUM_DC_INSTR];
					double adblRate[] = new double[NUM_DC_INSTR];
					double adblCompCalibValue[] = new double[NUM_DC_INSTR];
					aWJComp = new org.drip.numerical.differentiation.WengertJacobian[NUM_DC_INSTR];
					java.lang.String astrCalibMeasure[] = new java.lang.String[NUM_DC_INSTR];
					aCompCalib = new org.drip.product.definition.CalibratableComponent[NUM_DC_INSTR];

					if (null == (dtStart = org.drip.analytics.date.DateUtil.CreateFromYMD (2011, 4, 6)))
						return false;

					aiDate[0] = dtStart.addDays ((int)(365.25 * 1 + 2)).julian(); // 4Y

					aiDate[1] = dtStart.addDays ((int)(365.25 * 2 + 2)).julian(); // 5Y

					aiDate[2] = dtStart.addDays ((int)(365.25 * 3 + 2)).julian(); // 6Y

					aiDate[3] = dtStart.addDays ((int)(365.25 * 7 + 2)).julian(); // 7Y

					aiDate[4] = dtStart.addDays ((int)(365.25 * 8 + 2)).julian(); // 8Y

					aiDate[5] = dtStart.addDays ((int)(365.25 * 9 + 2)).julian(); // 9Y

					aiDate[6] = dtStart.addDays ((int)(365.25 * 10 + 2)).julian(); // 10Y

					aiDate[7] = dtStart.addDays ((int)(365.25 * 11 + 2)).julian(); // 11Y

					aiDate[8] = dtStart.addDays ((int)(365.25 * 12 + 2)).julian(); // 12Y

					aiDate[9] = dtStart.addDays ((int)(365.25 * 15 + 2)).julian(); // 15Y

					aiDate[10] = dtStart.addDays ((int)(365.25 * 20 + 2)).julian(); // 20Y

					aiDate[11] = dtStart.addDays ((int)(365.25 * 25 + 2)).julian(); // 25Y

					aiDate[12] = dtStart.addDays ((int)(365.25 * 30 + 2)).julian(); // 30Y

					aiDate[13] = dtStart.addDays ((int)(365.25 * 40 + 2)).julian(); // 40Y

					aiDate[14] = dtStart.addDays ((int)(365.25 * 50 + 2)).julian(); // 50Y

					adblCompCalibValue[0] = .0166;
					adblCompCalibValue[1] = .0206;
					adblCompCalibValue[2] = .0241;
					adblCompCalibValue[3] = .0269;
					adblCompCalibValue[4] = .0292;
					adblCompCalibValue[5] = .0311;
					adblCompCalibValue[6] = .0326;
					adblCompCalibValue[7] = .0340;
					adblCompCalibValue[8] = .0351;
					adblCompCalibValue[9] = .0375;
					adblCompCalibValue[10] = .0393;
					adblCompCalibValue[11] = .0402;
					adblCompCalibValue[12] = .0407;
					adblCompCalibValue[13] = .0409;
					adblCompCalibValue[14] = .0409;
					org.drip.param.period.CompositePeriodSetting cpsFixed = null;
					org.drip.param.period.CompositePeriodSetting cpsFloating = null;
					org.drip.param.period.UnitCouponAccrualSetting ucasFixed = null;
					org.drip.param.period.ComposableFixedUnitSetting cfusFixed = null;
					org.drip.param.period.ComposableFloatingUnitSetting cfusFloating = null;

					try {
						ucasFixed = new org.drip.param.period.UnitCouponAccrualSetting (2, "Act/360", false,
							"Act/360", false, "USD", true,
								org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC);

						cfusFloating = new org.drip.param.period.ComposableFloatingUnitSetting ("3M",
							org.drip.analytics.support.CompositePeriodBuilder.EDGE_DATE_SEQUENCE_SINGLE,
								null, org.drip.state.identifier.ForwardLabel.Standard ("USD-3M"),
									org.drip.analytics.support.CompositePeriodBuilder.REFERENCE_PERIOD_IN_ADVANCE,
							0.);

						cfusFixed = new org.drip.param.period.ComposableFixedUnitSetting ("6M",
							org.drip.analytics.support.CompositePeriodBuilder.EDGE_DATE_SEQUENCE_REGULAR,
								null, 0., 0., "USD");

						cpsFloating = new org.drip.param.period.CompositePeriodSetting (4, "3M", "USD", null,
							-1., null, null, null, null);

						cpsFixed = new org.drip.param.period.CompositePeriodSetting (2, "6M", "USD", null,
							1., null, null, null, null);
					} catch (java.lang.Exception e) {
						e.printStackTrace();

						return false;
					}

					for (int i = 0; i < NUM_DC_INSTR; ++i) {
						adblRate[i] = 0.01;
						astrCalibMeasure[i] = "Rate";

						try {
							java.util.List<java.lang.Integer> lsFixedStreamEdgeDate =
								org.drip.analytics.support.CompositePeriodBuilder.BackwardEdgeDates (dtStart,
									new org.drip.analytics.date.JulianDate (aiDate[i]), "6M", null,
										org.drip.analytics.support.CompositePeriodBuilder.SHORT_STUB);

							java.util.List<java.lang.Integer> lsFloatingStreamEdgeDate =
								org.drip.analytics.support.CompositePeriodBuilder.BackwardEdgeDates (dtStart,
									new org.drip.analytics.date.JulianDate (aiDate[i]), "3M", null,
										org.drip.analytics.support.CompositePeriodBuilder.SHORT_STUB);

							org.drip.product.rates.Stream floatingStream = new org.drip.product.rates.Stream
								(org.drip.analytics.support.CompositePeriodBuilder.FloatingCompositeUnit
									(lsFloatingStreamEdgeDate, cpsFloating, cfusFloating));

							org.drip.product.rates.Stream fixedStream = new org.drip.product.rates.Stream
								(org.drip.analytics.support.CompositePeriodBuilder.FixedCompositeUnit
									(lsFixedStreamEdgeDate, cpsFixed, ucasFixed, cfusFixed));


							aCompCalib[i] = new org.drip.product.rates.FixFloatComponent (fixedStream,
								floatingStream, null);
						} catch (java.lang.Exception e) {
							e.printStackTrace();

							return false;
						}
					}

					lsfc.add (dtStart.addDays (2), org.drip.state.identifier.ForwardLabel.Standard
						("USD-6M"), 0.0042);

					return null != (dcIRS =
						org.drip.state.creator.ScenarioDiscountCurveBuilder.NonlinearBuild (dtStart, "USD",
							aCompCalib, adblCompCalibValue, astrCalibMeasure, lsfc));
				}

				@Override public boolean execRegression()
				{
					for (int i = 0; i < aCompCalib.length; ++i) {
						try {
							if (null == (aWJComp[i] = aCompCalib[i].jackDDirtyPVDManifestMeasure (new
								org.drip.param.valuation.ValuationParams (dtStart, dtStart, "USD"), null,
									org.drip.param.creator.MarketParamsBuilder.Create (dcIRS, null, null,
										null, null, null, lsfc), null)))
								return false;
						} catch (java.lang.Exception e) {
							e.printStackTrace();

							return false;
						}
					}

					return null != (wjPVDF = dcIRS.compJackDPVDManifestMeasure (dtStart));
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
