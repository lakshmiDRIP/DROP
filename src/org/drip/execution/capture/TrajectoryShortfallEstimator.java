
package org.drip.execution.capture;

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
 * TrajectoryShortfallEstimator estimates the Price/Short Fall Distribution associated with the Trading
 *  Trajectory generated using the specified Evolution Parameters. The References are:
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

public class TrajectoryShortfallEstimator implements
	org.drip.execution.sensitivity.ControlNodesGreekGenerator {
	private org.drip.execution.strategy.DiscreteTradingTrajectory _tt = null;

	/**
	 * TrajectoryShortfallEstimator Constructor
	 * 
	 * @param tt The Trading Trajectory Instance
	 *  
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public TrajectoryShortfallEstimator (
		final org.drip.execution.strategy.DiscreteTradingTrajectory tt)
		throws java.lang.Exception
	{
		if (null == (_tt = tt))
			throw new java.lang.Exception ("TrajectoryShortfallEstimator Constructor => Invalid Inputs");
	}

	/**
	 * Retrieve the Underlying Trading Trajectory Instance
	 * 
	 * @return The Underlying Trading Trajectory Instance
	 */

	public org.drip.execution.strategy.DiscreteTradingTrajectory trajectory()
	{
		return _tt;
	}

	/**
	 * Generate the Detailed Cost Realization Sequence given the Specified Inputs
	 * 
	 * @param dblStartingEquilibriumPrice The Starting Equilibrium Price
	 * @param aWS Array of the Realized Walk Random Variable Suite
	 * @param apep The Price Evolution Parameters
	 * 
	 * @return The Detailed Cost Realization Sequence given the Specified Inputs
	 */

	public org.drip.execution.capture.TrajectoryShortfallRealization totalCostRealizationDetail (
		final double dblStartingEquilibriumPrice,
		final org.drip.execution.dynamics.WalkSuite[] aWS,
		final org.drip.execution.dynamics.ArithmeticPriceEvolutionParameters apep)
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblStartingEquilibriumPrice) || null == aWS)
			return null;

		double[] adblExecutionTimeNode = _tt.executionTimeNode();

		double[] adblHoldings = _tt.holdings();

		int iNumTimeNode = adblExecutionTimeNode.length;
		double dblPreviousEquilibriumPrice = dblStartingEquilibriumPrice;

		if (aWS.length + 1 != iNumTimeNode) return null;

		java.util.List<org.drip.execution.discrete.ShortfallIncrement> lsSI = new
			java.util.ArrayList<org.drip.execution.discrete.ShortfallIncrement>();

		for (int i = 1; i < iNumTimeNode; ++i) {
			org.drip.execution.discrete.ShortfallIncrement si = null;

			try {
				si = ( new org.drip.execution.discrete.Slice (adblHoldings[i - 1], adblHoldings[i],
					adblExecutionTimeNode[i] - adblExecutionTimeNode[i - 1])).costIncrementRealization
						(dblPreviousEquilibriumPrice, aWS[i - 1], apep);
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}

			if (null == si) return null;

			lsSI.add (si);

			dblPreviousEquilibriumPrice = si.compositePriceIncrement().newEquilibriumPrice();
		}

		try {
			return new org.drip.execution.capture.TrajectoryShortfallRealization (lsSI);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the Detailed Total Cost Distribution for the Trading Trajectory
	 * 
	 * @param apep The Price Evolution Parameters
	 * 
	 * @return The Detailed Total Cost Distribution for the Trading Trajectory
	 */

	public org.drip.execution.capture.TrajectoryShortfallAggregate totalCostDistributionDetail (
		final org.drip.execution.dynamics.ArithmeticPriceEvolutionParameters apep)
	{
		double[] adblExecutionTimeNode = _tt.executionTimeNode();

		double[] adblHoldings = _tt.holdings();

		int iNumTimeNode = adblExecutionTimeNode.length;

		java.util.List<org.drip.execution.discrete.ShortfallIncrementDistribution> lsSID = new
			java.util.ArrayList<org.drip.execution.discrete.ShortfallIncrementDistribution>();

		for (int i = 1; i < iNumTimeNode; ++i) {
			org.drip.execution.discrete.Slice s = null;

			try {
				s = new org.drip.execution.discrete.Slice (adblHoldings[i - 1], adblHoldings[i],
					adblExecutionTimeNode[i] - adblExecutionTimeNode[i - 1]);
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}

			lsSID.add (s.costIncrementDistribution (apep));
		}

		try {
			return new org.drip.execution.capture.TrajectoryShortfallAggregate (lsSID);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the Total Cost Distribution Synopsis Distribution for the Trading Trajectory
	 * 
	 * @param apep Arithmetic Price Evolution Parameters Instance
	 * 
	 * @return The Total Cost Distribution Synopsis Distribution for the Trading Trajectory
	 */

	public org.drip.measure.gaussian.R1UnivariateNormal totalCostDistributionSynopsis (
		final org.drip.execution.dynamics.ArithmeticPriceEvolutionParameters apep)
	{
		org.drip.execution.capture.TrajectoryShortfallAggregate tsa = totalCostDistributionDetail (apep);

		return null == tsa ? null : tsa.totalCostDistribution();
	}

	@Override public org.drip.execution.sensitivity.ControlNodesGreek permanentImpactExpectation (
		final org.drip.execution.dynamics.ArithmeticPriceEvolutionParameters apep)
	{
		double[] adblExecutionTimeNode = _tt.executionTimeNode();

		double dblValue = 0.;
		int iNumTimeNode = adblExecutionTimeNode.length;
		double[] adblTrajectoryJacobian = new double[iNumTimeNode];
		double[][] aadblTrajectoryHessian = new double[iNumTimeNode][iNumTimeNode];

		double[] adblHoldings = _tt.holdings();

		java.util.List<org.drip.execution.sensitivity.ControlNodesGreek> lsCNG = new
			java.util.ArrayList<org.drip.execution.sensitivity.ControlNodesGreek>();

		for (int i = 1; i < iNumTimeNode; ++i) {
			org.drip.execution.discrete.Slice s = null;

			try {
				s = new org.drip.execution.discrete.Slice (adblHoldings[i - 1], adblHoldings[i],
					adblExecutionTimeNode[i] - adblExecutionTimeNode[i - 1]);
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}

			org.drip.execution.sensitivity.ControlNodesGreek cng = s.permanentImpactExpectation (apep);

			if (null == cng) return null;

			lsCNG.add (cng);

			dblValue = dblValue + cng.value();

			double[] adblSliceJacobian = cng.jacobian();

			double[][] aadblSliceHessian = cng.hessian();

			adblTrajectoryJacobian[i] = adblTrajectoryJacobian[i] + adblSliceJacobian[1];
			adblTrajectoryJacobian[i - 1] = adblTrajectoryJacobian[i - 1] + adblSliceJacobian[0];
			aadblTrajectoryHessian[i][i] = aadblTrajectoryHessian[i][i] + aadblSliceHessian[1][1];
			aadblTrajectoryHessian[i][i - 1] = aadblTrajectoryHessian[i][i - 1] + aadblSliceHessian[1][0];
			aadblTrajectoryHessian[i - 1][i] = aadblTrajectoryHessian[i - 1][i] + aadblSliceHessian[0][1];
			aadblTrajectoryHessian[i - 1][i - 1] = aadblTrajectoryHessian[i - 1][i - 1] +
				aadblSliceHessian[0][0];
		}

		try {
			return new org.drip.execution.sensitivity.TrajectoryControlNodesGreek (dblValue,
				adblTrajectoryJacobian, aadblTrajectoryHessian, lsCNG);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override public org.drip.execution.sensitivity.ControlNodesGreek permanentImpactVariance (
		final org.drip.execution.dynamics.ArithmeticPriceEvolutionParameters apep)
	{
		double[] adblExecutionTimeNode = _tt.executionTimeNode();

		double dblValue = 0.;
		int iNumTimeNode = adblExecutionTimeNode.length;
		double[] adblTrajectoryJacobian = new double[iNumTimeNode];
		double[][] aadblTrajectoryHessian = new double[iNumTimeNode][iNumTimeNode];

		double[] adblHoldings = _tt.holdings();

		java.util.List<org.drip.execution.sensitivity.ControlNodesGreek> lsCNG = new
			java.util.ArrayList<org.drip.execution.sensitivity.ControlNodesGreek>();

		for (int i = 1; i < iNumTimeNode; ++i) {
			org.drip.execution.discrete.Slice s = null;

			try {
				s = new org.drip.execution.discrete.Slice (adblHoldings[i - 1], adblHoldings[i],
					adblExecutionTimeNode[i] - adblExecutionTimeNode[i - 1]);
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}

			org.drip.execution.sensitivity.ControlNodesGreek cng = s.permanentImpactVariance (apep);

			if (null == cng) return null;

			lsCNG.add (cng);

			dblValue = dblValue + cng.value();

			double[] adblSliceJacobian = cng.jacobian();

			double[][] aadblSliceHessian = cng.hessian();

			adblTrajectoryJacobian[i] = adblTrajectoryJacobian[i] + adblSliceJacobian[1];
			adblTrajectoryJacobian[i - 1] = adblTrajectoryJacobian[i - 1] + adblSliceJacobian[0];
			aadblTrajectoryHessian[i][i] = aadblTrajectoryHessian[i][i] + aadblSliceHessian[1][1];
			aadblTrajectoryHessian[i][i - 1] = aadblTrajectoryHessian[i][i - 1] + aadblSliceHessian[1][0];
			aadblTrajectoryHessian[i - 1][i] = aadblTrajectoryHessian[i - 1][i] + aadblSliceHessian[0][1];
			aadblTrajectoryHessian[i - 1][i - 1] = aadblTrajectoryHessian[i - 1][i - 1] +
				aadblSliceHessian[0][0];
		}

		try {
			return new org.drip.execution.sensitivity.TrajectoryControlNodesGreek (dblValue,
				adblTrajectoryJacobian, aadblTrajectoryHessian, lsCNG);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override public org.drip.execution.sensitivity.ControlNodesGreek temporaryImpactExpectation (
		final org.drip.execution.dynamics.ArithmeticPriceEvolutionParameters apep)
	{
		double[] adblExecutionTimeNode = _tt.executionTimeNode();

		double dblValue = 0.;
		int iNumTimeNode = adblExecutionTimeNode.length;
		double[] adblTrajectoryJacobian = new double[iNumTimeNode];
		double[][] aadblTrajectoryHessian = new double[iNumTimeNode][iNumTimeNode];

		double[] adblHoldings = _tt.holdings();

		java.util.List<org.drip.execution.sensitivity.ControlNodesGreek> lsCNG = new
			java.util.ArrayList<org.drip.execution.sensitivity.ControlNodesGreek>();

		for (int i = 1; i < iNumTimeNode; ++i) {
			org.drip.execution.discrete.Slice s = null;

			try {
				s = new org.drip.execution.discrete.Slice (adblHoldings[i - 1], adblHoldings[i],
					adblExecutionTimeNode[i] - adblExecutionTimeNode[i - 1]);
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}

			org.drip.execution.sensitivity.ControlNodesGreek cng = s.temporaryImpactExpectation (apep);

			if (null == cng) return null;

			lsCNG.add (cng);

			dblValue = dblValue + cng.value();

			double[] adblSliceJacobian = cng.jacobian();

			double[][] aadblSliceHessian = cng.hessian();

			adblTrajectoryJacobian[i] = adblTrajectoryJacobian[i] + adblSliceJacobian[1];
			adblTrajectoryJacobian[i - 1] = adblTrajectoryJacobian[i - 1] + adblSliceJacobian[0];
			aadblTrajectoryHessian[i][i] = aadblTrajectoryHessian[i][i] + aadblSliceHessian[1][1];
			aadblTrajectoryHessian[i][i - 1] = aadblTrajectoryHessian[i][i - 1] + aadblSliceHessian[1][0];
			aadblTrajectoryHessian[i - 1][i] = aadblTrajectoryHessian[i - 1][i] + aadblSliceHessian[0][1];
			aadblTrajectoryHessian[i - 1][i - 1] = aadblTrajectoryHessian[i - 1][i - 1] +
				aadblSliceHessian[0][0];
		}

		try {
			return new org.drip.execution.sensitivity.TrajectoryControlNodesGreek (dblValue,
				adblTrajectoryJacobian, aadblTrajectoryHessian, lsCNG);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override public org.drip.execution.sensitivity.ControlNodesGreek temporaryImpactVariance (
		final org.drip.execution.dynamics.ArithmeticPriceEvolutionParameters apep)
	{
		double[] adblExecutionTimeNode = _tt.executionTimeNode();

		double dblValue = 0.;
		int iNumTimeNode = adblExecutionTimeNode.length;
		double[] adblTrajectoryJacobian = new double[iNumTimeNode];
		double[][] aadblTrajectoryHessian = new double[iNumTimeNode][iNumTimeNode];

		double[] adblHoldings = _tt.holdings();

		java.util.List<org.drip.execution.sensitivity.ControlNodesGreek> lsCNG = new
			java.util.ArrayList<org.drip.execution.sensitivity.ControlNodesGreek>();

		for (int i = 1; i < iNumTimeNode; ++i) {
			org.drip.execution.discrete.Slice s = null;

			try {
				s = new org.drip.execution.discrete.Slice (adblHoldings[i - 1], adblHoldings[i],
					adblExecutionTimeNode[i] - adblExecutionTimeNode[i - 1]);
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}

			org.drip.execution.sensitivity.ControlNodesGreek cng = s.temporaryImpactVariance (apep);

			if (null == cng) return null;

			lsCNG.add (cng);

			dblValue = dblValue + cng.value();

			double[] adblSliceJacobian = cng.jacobian();

			double[][] aadblSliceHessian = cng.hessian();

			adblTrajectoryJacobian[i] = adblTrajectoryJacobian[i] + adblSliceJacobian[1];
			adblTrajectoryJacobian[i - 1] = adblTrajectoryJacobian[i - 1] + adblSliceJacobian[0];
			aadblTrajectoryHessian[i][i] = aadblTrajectoryHessian[i][i] + aadblSliceHessian[1][1];
			aadblTrajectoryHessian[i][i - 1] = aadblTrajectoryHessian[i][i - 1] + aadblSliceHessian[1][0];
			aadblTrajectoryHessian[i - 1][i] = aadblTrajectoryHessian[i - 1][i] + aadblSliceHessian[0][1];
			aadblTrajectoryHessian[i - 1][i - 1] = aadblTrajectoryHessian[i - 1][i - 1] +
				aadblSliceHessian[0][0];
		}

		try {
			return new org.drip.execution.sensitivity.TrajectoryControlNodesGreek (dblValue,
				adblTrajectoryJacobian, aadblTrajectoryHessian, lsCNG);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override public org.drip.execution.sensitivity.ControlNodesGreek marketDynamicsExpectation (
		final org.drip.execution.dynamics.ArithmeticPriceEvolutionParameters apep)
	{
		double[] adblExecutionTimeNode = _tt.executionTimeNode();

		double dblValue = 0.;
		int iNumTimeNode = adblExecutionTimeNode.length;
		double[] adblTrajectoryJacobian = new double[iNumTimeNode];
		double[][] aadblTrajectoryHessian = new double[iNumTimeNode][iNumTimeNode];

		double[] adblHoldings = _tt.holdings();

		java.util.List<org.drip.execution.sensitivity.ControlNodesGreek> lsCNG = new
			java.util.ArrayList<org.drip.execution.sensitivity.ControlNodesGreek>();

		for (int i = 1; i < iNumTimeNode; ++i) {
			org.drip.execution.discrete.Slice s = null;

			try {
				s = new org.drip.execution.discrete.Slice (adblHoldings[i - 1], adblHoldings[i],
					adblExecutionTimeNode[i] - adblExecutionTimeNode[i - 1]);
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}

			org.drip.execution.sensitivity.ControlNodesGreek cng = s.marketDynamicsExpectation (apep);

			if (null == cng) return null;

			lsCNG.add (cng);

			dblValue = dblValue + cng.value();

			double[] adblSliceJacobian = cng.jacobian();

			double[][] aadblSliceHessian = cng.hessian();

			adblTrajectoryJacobian[i] = adblTrajectoryJacobian[i] + adblSliceJacobian[1];
			adblTrajectoryJacobian[i - 1] = adblTrajectoryJacobian[i - 1] + adblSliceJacobian[0];
			aadblTrajectoryHessian[i][i] = aadblTrajectoryHessian[i][i] + aadblSliceHessian[1][1];
			aadblTrajectoryHessian[i][i - 1] = aadblTrajectoryHessian[i][i - 1] + aadblSliceHessian[1][0];
			aadblTrajectoryHessian[i - 1][i] = aadblTrajectoryHessian[i - 1][i] + aadblSliceHessian[0][1];
			aadblTrajectoryHessian[i - 1][i - 1] = aadblTrajectoryHessian[i - 1][i - 1] +
				aadblSliceHessian[0][0];
		}

		try {
			return new org.drip.execution.sensitivity.TrajectoryControlNodesGreek (dblValue,
				adblTrajectoryJacobian, aadblTrajectoryHessian, lsCNG);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override public org.drip.execution.sensitivity.ControlNodesGreek marketDynamicsVariance (
		final org.drip.execution.dynamics.ArithmeticPriceEvolutionParameters apep)
	{
		double[] adblExecutionTimeNode = _tt.executionTimeNode();

		double dblValue = 0.;
		int iNumTimeNode = adblExecutionTimeNode.length;
		double[] adblTrajectoryJacobian = new double[iNumTimeNode];
		double[][] aadblTrajectoryHessian = new double[iNumTimeNode][iNumTimeNode];

		double[] adblHoldings = _tt.holdings();

		java.util.List<org.drip.execution.sensitivity.ControlNodesGreek> lsCNG = new
			java.util.ArrayList<org.drip.execution.sensitivity.ControlNodesGreek>();

		for (int i = 1; i < iNumTimeNode; ++i) {
			org.drip.execution.discrete.Slice s = null;

			try {
				s = new org.drip.execution.discrete.Slice (adblHoldings[i - 1], adblHoldings[i],
					adblExecutionTimeNode[i] - adblExecutionTimeNode[i - 1]);
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}

			org.drip.execution.sensitivity.ControlNodesGreek cng = s.marketDynamicsVariance (apep);

			if (null == cng) return null;

			lsCNG.add (cng);

			dblValue = dblValue + cng.value();

			double[] adblSliceJacobian = cng.jacobian();

			double[][] aadblSliceHessian = cng.hessian();

			adblTrajectoryJacobian[i] = adblTrajectoryJacobian[i] + adblSliceJacobian[1];
			adblTrajectoryJacobian[i - 1] = adblTrajectoryJacobian[i - 1] + adblSliceJacobian[0];
			aadblTrajectoryHessian[i][i] = aadblTrajectoryHessian[i][i] + aadblSliceHessian[1][1];
			aadblTrajectoryHessian[i][i - 1] = aadblTrajectoryHessian[i][i - 1] + aadblSliceHessian[1][0];
			aadblTrajectoryHessian[i - 1][i] = aadblTrajectoryHessian[i - 1][i] + aadblSliceHessian[0][1];
			aadblTrajectoryHessian[i - 1][i - 1] = aadblTrajectoryHessian[i - 1][i - 1] +
				aadblSliceHessian[0][0];
		}

		try {
			return new org.drip.execution.sensitivity.TrajectoryControlNodesGreek (dblValue,
				adblTrajectoryJacobian, aadblTrajectoryHessian, lsCNG);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override public org.drip.execution.sensitivity.ControlNodesGreek expectationContribution (
		final org.drip.execution.dynamics.ArithmeticPriceEvolutionParameters apep)
	{
		double[] adblExecutionTimeNode = _tt.executionTimeNode();

		double dblValue = 0.;
		int iNumTimeNode = adblExecutionTimeNode.length;
		double[] adblTrajectoryJacobian = new double[iNumTimeNode];
		double[][] aadblTrajectoryHessian = new double[iNumTimeNode][iNumTimeNode];

		double[] adblHoldings = _tt.holdings();

		java.util.List<org.drip.execution.sensitivity.ControlNodesGreek> lsCNG = new
			java.util.ArrayList<org.drip.execution.sensitivity.ControlNodesGreek>();

		for (int i = 1; i < iNumTimeNode; ++i) {
			org.drip.execution.discrete.Slice s = null;

			try {
				s = new org.drip.execution.discrete.Slice (adblHoldings[i - 1], adblHoldings[i],
					adblExecutionTimeNode[i] - adblExecutionTimeNode[i - 1]);
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}

			org.drip.execution.sensitivity.ControlNodesGreek cng = s.expectationContribution (apep);

			if (null == cng) return null;

			lsCNG.add (cng);

			dblValue = dblValue + cng.value();

			double[] adblSliceJacobian = cng.jacobian();

			double[][] aadblSliceHessian = cng.hessian();

			adblTrajectoryJacobian[i] = adblTrajectoryJacobian[i] + adblSliceJacobian[1];
			adblTrajectoryJacobian[i - 1] = adblTrajectoryJacobian[i - 1] + adblSliceJacobian[0];
			aadblTrajectoryHessian[i][i] = aadblTrajectoryHessian[i][i] + aadblSliceHessian[1][1];
			aadblTrajectoryHessian[i][i - 1] = aadblTrajectoryHessian[i][i - 1] + aadblSliceHessian[1][0];
			aadblTrajectoryHessian[i - 1][i] = aadblTrajectoryHessian[i - 1][i] + aadblSliceHessian[0][1];
			aadblTrajectoryHessian[i - 1][i - 1] = aadblTrajectoryHessian[i - 1][i - 1] +
				aadblSliceHessian[0][0];
		}

		try {
			return new org.drip.execution.sensitivity.TrajectoryControlNodesGreek (dblValue,
				adblTrajectoryJacobian, aadblTrajectoryHessian, lsCNG);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override public org.drip.execution.sensitivity.ControlNodesGreek varianceContribution (
		final org.drip.execution.dynamics.ArithmeticPriceEvolutionParameters apep)
	{
		double[] adblExecutionTimeNode = _tt.executionTimeNode();

		double dblValue = 0.;
		int iNumTimeNode = adblExecutionTimeNode.length;
		double[] adblTrajectoryJacobian = new double[iNumTimeNode];
		double[][] aadblTrajectoryHessian = new double[iNumTimeNode][iNumTimeNode];

		double[] adblHoldings = _tt.holdings();

		java.util.List<org.drip.execution.sensitivity.ControlNodesGreek> lsCNG = new
			java.util.ArrayList<org.drip.execution.sensitivity.ControlNodesGreek>();

		for (int i = 1; i < iNumTimeNode; ++i) {
			org.drip.execution.discrete.Slice s = null;

			try {
				s = new org.drip.execution.discrete.Slice (adblHoldings[i - 1], adblHoldings[i],
					adblExecutionTimeNode[i] - adblExecutionTimeNode[i - 1]);
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}

			org.drip.execution.sensitivity.ControlNodesGreek cng = s.varianceContribution (apep);

			if (null == cng) return null;

			lsCNG.add (cng);

			dblValue = dblValue + cng.value();

			double[] adblSliceJacobian = cng.jacobian();

			double[][] aadblSliceHessian = cng.hessian();

			adblTrajectoryJacobian[i] = adblTrajectoryJacobian[i] + adblSliceJacobian[1];
			adblTrajectoryJacobian[i - 1] = adblTrajectoryJacobian[i - 1] + adblSliceJacobian[0];
			aadblTrajectoryHessian[i][i] = aadblTrajectoryHessian[i][i] + aadblSliceHessian[1][1];
			aadblTrajectoryHessian[i][i - 1] = aadblTrajectoryHessian[i][i - 1] + aadblSliceHessian[1][0];
			aadblTrajectoryHessian[i - 1][i] = aadblTrajectoryHessian[i - 1][i] + aadblSliceHessian[0][1];
			aadblTrajectoryHessian[i - 1][i - 1] = aadblTrajectoryHessian[i - 1][i - 1] +
				aadblSliceHessian[0][0];
		}

		try {
			return new org.drip.execution.sensitivity.TrajectoryControlNodesGreek (dblValue,
				adblTrajectoryJacobian, aadblTrajectoryHessian, lsCNG);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Estimate the Optimal Adjustment Attributable to the Serial Correlation
	 *  
	 * @param apep The Arithmetic Price Walk Parameters
	 * 
	 * @return The Optimal Adjustment Attributable to the Serial Correlation
	 */

	public org.drip.execution.discrete.OptimalSerialCorrelationAdjustment[] serialCorrelationAdjustment (
		final org.drip.execution.dynamics.ArithmeticPriceEvolutionParameters apep)
	{
		double[] adblExecutionTimeNode = _tt.executionTimeNode();

		int iNumTimeNode = adblExecutionTimeNode.length;
		org.drip.execution.discrete.OptimalSerialCorrelationAdjustment[] aOSCA = new
			org.drip.execution.discrete.OptimalSerialCorrelationAdjustment[iNumTimeNode];

		double[] adblHoldings = _tt.holdings();

		try {
			aOSCA[0] = new org.drip.execution.discrete.OptimalSerialCorrelationAdjustment (0., 0.);
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		for (int i = 1; i < iNumTimeNode; ++i) {
			org.drip.execution.discrete.Slice s = null;

			try {
				s = new org.drip.execution.discrete.Slice (adblHoldings[i - 1], adblHoldings[i],
					adblExecutionTimeNode[i] - adblExecutionTimeNode[i - 1]);
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}

			if (null == (aOSCA[i] = s.serialCorrelationAdjustment (apep))) return null;
		}

		return aOSCA;
	}
}
