
package org.drip.execution.nonadaptive;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
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
 * <i>DiscreteAlmgrenChriss</i> generates the Trade/Holdings List of Optimal Execution Schedule for the
 *  Equally Spaced Trading Intervals based on the No-Drift Linear Impact Evolution Walk Parameters specified.
 *  The References are:
 * 
 * <br>
 * 	<ul>
 * 		<li>
 * 			Almgren, R., and N. Chriss (1999): Value under Liquidation <i>Risk</i> <b>12 (12)</b>
 * 		</li>
 * 		<li>
 * 			Almgren, R., and N. Chriss (2000): Optimal Execution of Portfolio Transactions <i>Journal of
 * 				Risk</i> <b>3 (2)</b> 5-39
 * 		</li>
 * 		<li>
 * 			Bertsimas, D., and A. W. Lo (1998): Optimal Control of Execution Costs <i>Journal of Financial
 * 				Markets</i> <b>1</b> 1-50
 * 		</li>
 * 		<li>
 * 			Chan, L. K. C., and J. Lakonishak (1995): The Behavior of Stock Prices around Institutional
 * 				Trades <i>Journal of Finance</i> <b>50</b> 1147-1174
 * 		</li>
 * 		<li>
 * 			Keim, D. B., and A. Madhavan (1997): Transaction Costs and Investment Style: An Inter-exchange
 * 				Analysis of Institutional Equity Trades <i>Journal of Financial Economics</i> <b>46</b>
 * 				265-292
 * 		</li>
 * 	</ul>
 * <br>
 *  <ul>
 *		<li><b>Module</b>        = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution">Execution</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/nonadaptive">Non Adaptive</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/TransactionCost">Transaction Cost Analytics</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class DiscreteAlmgrenChriss extends org.drip.execution.nonadaptive.StaticOptimalSchemeDiscrete {

	private double KappaTau (
		final double dblKappaTildaSquared,
		final double dblTau)
	{
		double dblKappaTildaSquaredTauSquared = dblKappaTildaSquared * dblTau * dblTau;

		return java.lang.Math.log (0.5 * (2. + dblKappaTildaSquaredTauSquared + dblTau * java.lang.Math.sqrt
			(dblKappaTildaSquared * (dblKappaTildaSquaredTauSquared + 4.))));
	}

	/**
	 * Create the Standard DiscreteAlmgrenChriss Instance
	 * 
	 * @param dblStartHoldings Trajectory Start Holdings
	 * @param dblFinishTime Trajectory Finish Time
	 * @param iNumInterval The Number of Fixed Intervals
	 * @param lpep Linear Impact Price Walk Parameters
	 * @param dblRiskAversion The Risk Aversion Parameter
	 * 
	 * @return The DiscreteAlmgrenChriss Instance
	 */

	public static final DiscreteAlmgrenChriss Standard (
		final double dblStartHoldings,
		final double dblFinishTime,
		final int iNumInterval,
		final org.drip.execution.dynamics.LinearPermanentExpectationParameters lpep,
		final double dblRiskAversion)
	{
		try {
			return new DiscreteAlmgrenChriss
				(org.drip.execution.strategy.DiscreteTradingTrajectoryControl.FixedInterval (new
					org.drip.execution.strategy.OrderSpecification (dblStartHoldings, dblFinishTime),
						iNumInterval), lpep, new org.drip.execution.risk.MeanVarianceObjectiveUtility
							(dblRiskAversion));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	private DiscreteAlmgrenChriss (
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

		try {
			dblEpochVolatility = lpep.arithmeticPriceDynamicsSettings().epochVolatility();
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		double dblGamma = lpep.linearPermanentExpectation().epochLiquidityFunction().slope();

		double dblEta = tflTemporaryExpectation.slope();

		double dblX = dttc.startHoldings();

		int iNumNode = adblTNode.length;
		double dblXSquared = dblX * dblX;
		final double dblSigma = dblEpochVolatility;
		double dblTau = adblTNode[1] - adblTNode[0];
		double dblSigmaSquared = dblSigma * dblSigma;
		double[] adblHoldings = new double[iNumNode];
		double[] adblTradeList = new double[iNumNode - 1];
		double dblT = adblTNode[iNumNode - 1] - adblTNode[0];
		double dblEtaTilda = dblEta - 0.5 * dblGamma * dblTau;

		double dblKappaTildaSquared = ((org.drip.execution.risk.MeanVarianceObjectiveUtility)
			objectiveUtility()).riskAversion() * dblSigmaSquared / dblEtaTilda;

		double dblKappaTau = KappaTau (dblKappaTildaSquared, dblTau);

		double dblHalfKappaTau = 0.5 * dblKappaTau;
		double dblKappa = dblKappaTau / dblTau;
		double dblKappaT = dblKappa * dblT;

		double dblSinhKappaT = java.lang.Math.sinh (dblKappaT);

		double dblSinhKappaTau = java.lang.Math.sinh (dblKappaTau);

		double dblSinhHalfKappaTau = java.lang.Math.sinh (dblHalfKappaTau);

		double dblTSinhKappaTau = dblT * dblSinhKappaTau;
		double dblInverseSinhKappaT = 1. / dblSinhKappaT;
		double dblTrajectoryScaler = dblInverseSinhKappaT * dblX;
		double dblTradeListScaler = 2. * dblSinhHalfKappaTau * dblTrajectoryScaler;
		double dblReciprocalSinhKappaTSquared = dblInverseSinhKappaT * dblInverseSinhKappaT;

		for (int i = 0; i < iNumNode; ++i) {
			adblHoldings[i] = dblTrajectoryScaler * java.lang.Math.sinh (dblKappa * (dblT - adblTNode[i]));

			if (i < iNumNode - 1)
				adblTradeList[i] = -1. * dblTradeListScaler * java.lang.Math.cosh (dblKappa * (dblT - dblTau
					* (0.5 + i)));
		}

		try {
			return new org.drip.execution.optimum.AlmgrenChrissDiscrete (adblTNode, adblHoldings,
				adblTradeList, java.lang.Math.sqrt (dblKappaTildaSquared), dblKappa, 0.5 * dblGamma *
					dblXSquared + tflTemporaryExpectation.offset() * dblX + dblEtaTilda * dblXSquared *
						dblReciprocalSinhKappaTSquared * java.lang.Math.tanh (dblHalfKappaTau) * (dblTau *
							java.lang.Math.sinh (2. * dblKappaT) + 2. * dblTSinhKappaTau) / (2. * dblTau *
								dblTau), 0.5 * dblSigmaSquared * dblXSquared * dblReciprocalSinhKappaTSquared
									* (dblTau * dblSinhKappaT * java.lang.Math.cosh (dblKappa * (dblT -
										dblTau)) - dblTSinhKappaTau) / dblSinhKappaTau, dblEpochVolatility *
											dblX / (dblT * dblEpochVolatility * java.lang.Math.sqrt (dblT)));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
