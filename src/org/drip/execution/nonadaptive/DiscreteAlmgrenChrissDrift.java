
package org.drip.execution.nonadaptive;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
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
 * DiscreteAlmgrenChrissDrift generates the Trade/Holdings List of Optimal Execution Schedule for the Equally
 *  Spaced Trading Intervals based on the Linear Impact Evolution Walk Parameters with Drift specified. The
 *  References are:
 * 
 * 	- Almgren, R., and N. Chriss (1999): Value under Liquidation, Risk 12 (12).
 * 
 * 	- Almgren, R., and N. Chriss (2000): Optimal Execution of Portfolio Transactions, Journal of Risk 3 (2)
 * 		5-39.
 * 
 * 	- Bertsimas, D., and A. W. Lo (1998): Optimal Control of Execution Costs, Journal of Financial Markets,
 * 		1, 1-50.
 *
 * 	- Chan, L. K. C., and J. Lakonishak (1995): The Behavior of Stock Prices around Institutional Trades,
 * 		Journal of Finance, 50, 1147-1174.
 *
 * 	- Keim, D. B., and A. Madhavan (1997): Transaction Costs and Investment Style: An Inter-exchange
 * 		Analysis of Institutional Equity Trades, Journal of Financial Economics, 46, 265-292.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class DiscreteAlmgrenChrissDrift extends org.drip.execution.nonadaptive.StaticOptimalSchemeDiscrete {

	private double KappaTau (
		final double dblKappaTildaSquared,
		final double dblTau)
	{
		double dblKappaTildaSquaredTauSquared = dblKappaTildaSquared * dblTau * dblTau;

		return java.lang.Math.log (0.5 * (2. + dblKappaTildaSquaredTauSquared + dblTau * java.lang.Math.sqrt
			(dblKappaTildaSquared * (dblKappaTildaSquaredTauSquared + 4.))));
	}

	/**
	 * Create the Standard DiscreteAlmgrenChrissDrift Instance
	 * 
	 * @param dblStartHoldings Trajectory Start Holdings
	 * @param dblFinishTime Trajectory Finish Time
	 * @param iNumInterval The Number of Fixed Intervals
	 * @param lpep Linear Impact Price Walk Parameters
	 * @param dblRiskAversion The Risk Aversion Parameter
	 * 
	 * @return The DiscreteAlmgrenChrissDrift Instance
	 */

	public static final DiscreteAlmgrenChrissDrift Standard (
		final double dblStartHoldings,
		final double dblFinishTime,
		final int iNumInterval,
		final org.drip.execution.dynamics.LinearPermanentExpectationParameters lpep,
		final double dblRiskAversion)
	{
		try {
			return new DiscreteAlmgrenChrissDrift
				(org.drip.execution.strategy.DiscreteTradingTrajectoryControl.FixedInterval (new
					org.drip.execution.strategy.OrderSpecification (dblStartHoldings, dblFinishTime),
						iNumInterval), lpep, new org.drip.execution.risk.MeanVarianceObjectiveUtility
							(dblRiskAversion));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	private DiscreteAlmgrenChrissDrift (
		final org.drip.execution.strategy.DiscreteTradingTrajectoryControl dttc,
		final org.drip.execution.dynamics.LinearPermanentExpectationParameters lpep,
		final org.drip.execution.risk.MeanVarianceObjectiveUtility mvou)
		throws java.lang.Exception
	{
		super (dttc, lpep, mvou);
	}

	@Override public org.drip.execution.optimum.EfficientTradingTrajectoryDiscrete generate()
	{
		org.drip.execution.strategy.DiscreteTradingTrajectoryControl dttc = control();

		double[] adblTNode = dttc.executionTimeNodes();

		org.drip.execution.dynamics.LinearPermanentExpectationParameters lpep =
			(org.drip.execution.dynamics.LinearPermanentExpectationParameters) priceEvolutionParameters();

		org.drip.execution.impact.TransactionFunction tfTemporaryExpectation =
			lpep.temporaryExpectation().epochImpactFunction();

		if (!(tfTemporaryExpectation instanceof org.drip.execution.impact.TransactionFunctionLinear))
			return null;

		double dblEpochVolatility = java.lang.Double.NaN;
		org.drip.execution.impact.TransactionFunctionLinear tflTemporaryExpectation =
			(org.drip.execution.impact.TransactionFunctionLinear) tfTemporaryExpectation;

		double dblX = dttc.startHoldings();

		org.drip.execution.parameters.ArithmeticPriceDynamicsSettings apds =
			lpep.arithmeticPriceDynamicsSettings();

		double dblAlpha = apds.drift();

		double dblEta = tflTemporaryExpectation.slope();

		try {
			dblEpochVolatility = apds.epochVolatility();
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		double dblGamma = lpep.linearPermanentExpectation().epochLiquidityFunction().slope();

		int iNumNode = adblTNode.length;
		final double dblSigma = dblEpochVolatility;
		double dblTau = adblTNode[1] - adblTNode[0];
		double dblSigmaSquared = dblSigma * dblSigma;
		double[] adblHoldings = new double[iNumNode];
		double[] adblTradeList = new double[iNumNode - 1];
		double dblT = adblTNode[iNumNode - 1] - adblTNode[0];
		double dblEtaTilda = dblEta - 0.5 * dblGamma * dblTau;
		double[] adblHoldingsDriftAdjustment = new double[iNumNode];
		double[] adblTradeListDriftAdjustment = new double[iNumNode - 1];

		double dblLambdaSigmaSquared = ((org.drip.execution.risk.MeanVarianceObjectiveUtility)
			objectiveUtility()).riskAversion() * dblSigmaSquared;

		double dblResidualHolding = 0.5 * dblAlpha / dblLambdaSigmaSquared;
		double dblKappaTildaSquared = dblLambdaSigmaSquared / dblEtaTilda;

		double dblKappaTau = KappaTau (dblKappaTildaSquared, dblTau);

		double dblHalfKappaTau = 0.5 * dblKappaTau;
		double dblKappa = dblKappaTau / dblTau;
		double dblKappaT = dblKappa * dblT;

		double dblSinhKappaT = java.lang.Math.sinh (dblKappaT);

		double dblSinhHalfKappaTau = java.lang.Math.sinh (dblHalfKappaTau);

		double dblInverseSinhKappaT = 1. / dblSinhKappaT;
		double dblTrajectoryScaler = dblInverseSinhKappaT * dblX;
		double dblTradeListScaler = 2. * dblSinhHalfKappaTau * dblTrajectoryScaler;
		double dblTrajectoryAdjustmentScaler = dblInverseSinhKappaT * dblResidualHolding;
		double dblTradeListAdjustmentScaler = 2. * dblSinhHalfKappaTau * dblTrajectoryAdjustmentScaler;

		for (int i = 0; i < iNumNode; ++i) {
			adblHoldingsDriftAdjustment[i] = dblResidualHolding * (1. - dblInverseSinhKappaT *
				(java.lang.Math.sinh (dblKappa * (dblT - adblTNode[i])) + java.lang.Math.sinh (dblKappa *
					adblTNode[i])));

			adblHoldings[i] = dblTrajectoryScaler * java.lang.Math.sinh (dblKappa * (dblT - adblTNode[i])) +
				adblHoldingsDriftAdjustment[i];

			if (i < iNumNode - 1) {
				adblTradeListDriftAdjustment[i] = -1. * dblTradeListAdjustmentScaler * (java.lang.Math.cosh
					(dblKappa * dblTau * (0.5 + i)) - java.lang.Math.cosh (dblKappa * (dblT - dblTau * (0.5 +
						i))));

				adblTradeList[i] = -1. * dblTradeListScaler * java.lang.Math.cosh (dblKappa * (dblT - dblTau
					* (0.5 + i))) + adblTradeListDriftAdjustment[i];
			}
		}

		try {
			org.drip.measure.gaussian.R1UnivariateNormal r1un = (new
				org.drip.execution.capture.TrajectoryShortfallEstimator (new
					org.drip.execution.strategy.DiscreteTradingTrajectory (adblTNode, adblHoldings,
						adblTradeList))).totalCostDistributionSynopsis (lpep);

			return null == r1un ? null : new org.drip.execution.optimum.AlmgrenChrissDriftDiscrete
				(adblTNode, adblHoldings, adblTradeList, adblHoldingsDriftAdjustment,
					adblTradeListDriftAdjustment, java.lang.Math.sqrt (dblKappaTildaSquared), dblKappa,
						dblResidualHolding, dblAlpha * dblResidualHolding * dblT * (1. - (dblTau *
							java.lang.Math.tanh (0.5 * dblKappa * dblT) / (dblT * java.lang.Math.tanh
								(dblHalfKappaTau)))), r1un.mean(), r1un.variance(), dblEpochVolatility * dblX
									/ (dblT * dblEpochVolatility * java.lang.Math.sqrt (dblT)));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
