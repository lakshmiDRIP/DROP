
package org.drip.regression.curvejacobian;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
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
 * CashJacobianRegressorSet implements the regression analysis set for the Cash product related Sensitivity
 *  Jacobians. Specifically, it computes the PVDF micro-Jack.
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
				org.drip.quant.calculus.WengertJacobian wjPVDF = null;
				org.drip.quant.calculus.WengertJacobian aWJComp[] = null;
				org.drip.product.definition.CalibratableComponent aCompCalib[] = null;

				@Override public boolean preRegression() {
					int NUM_CASH_INSTR = 7;
					int aiDate[] = new int[NUM_CASH_INSTR];
					double adblRate[] = new double[NUM_CASH_INSTR];
					double adblCompCalibValue[] = new double[NUM_CASH_INSTR];
					aWJComp = new org.drip.quant.calculus.WengertJacobian[NUM_CASH_INSTR];
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
