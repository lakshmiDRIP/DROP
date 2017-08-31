
package org.drip.execution.discrete;

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
 * Slice implements the Arithmetic Dynamics of the Price/Cost Movements exhibited by an Asset owing to the
 *  Volatility and the Market Impact Factors on a Trajectory Slice. The References are:
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

public class Slice implements org.drip.execution.sensitivity.ControlNodesGreekGenerator {
	private double _dblTimeInterval = java.lang.Double.NaN;
	private double _dblLeftHoldings = java.lang.Double.NaN;
	private double _dblRightHoldings = java.lang.Double.NaN;

	/**
	 * Slice Constructor
	 * 
	 * @param dblLeftHoldings The Left-of-Slice Holdings
	 * @param dblRightHoldings The Right-of-Slice Holdings
	 * @param dblTimeInterval The Discrete Time Interval
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public Slice (
		final double dblLeftHoldings,
		final double dblRightHoldings,
		final double dblTimeInterval)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (_dblLeftHoldings = dblLeftHoldings) ||
			!org.drip.quant.common.NumberUtil.IsValid (_dblRightHoldings = dblRightHoldings) ||
				!org.drip.quant.common.NumberUtil.IsValid (_dblTimeInterval = dblTimeInterval) || 0. >=
					_dblTimeInterval)
			throw new java.lang.Exception ("Slice Constructor => Invalid Inputs!");
	}

	/**
	 * Retrieve the Left-of-Slice Holdings
	 * 
	 * @return The Left-of-Slice Holdings
	 */

	public double leftHoldings()
	{
		return _dblLeftHoldings;
	}

	/**
	 * Retrieve the Right Holdings
	 * 
	 * @return The Right Holdings
	 */

	public double rightHoldings()
	{
		return _dblRightHoldings;
	}

	/**
	 * Retrieve the Evolution Time Interval of the Arithmetic Dynamics
	 * 
	 * @return The Evolution Time Interval of the Arithmetic Dynamics
	 */

	public double timeInterval()
	{
		return _dblTimeInterval;
	}

	/**
	 * Indicate if the Slice is a Sell
	 * 
	 * @return TRUE - The Slice is a Sell
	 */

	public boolean isSell()
	{
		return _dblLeftHoldings - _dblRightHoldings > 0.;
	}

	/**
	 * Generate the Price Evolution Increment Unit Realization given the Walk Realization
	 * 
	 * @param dblPreviousEquilibriumPrice The Previous Equilibrium Price
	 * @param ws Realized Walk Suite
	 * @param apep The Arithmetic Price Walk Evolution Parameters
	 * 
	 * @return The Realized Price Evolution Increment Unit given the Walk Realization
	 */

	public org.drip.execution.discrete.PriceIncrement priceIncrementRealization (
		final double dblPreviousEquilibriumPrice,
		final org.drip.execution.dynamics.WalkSuite ws,
		final org.drip.execution.dynamics.ArithmeticPriceEvolutionParameters apep)
	{
		if (null == ws || null == apep) return null;

		org.drip.execution.parameters.ArithmeticPriceDynamicsSettings apds =
			apep.arithmeticPriceDynamicsSettings();

		double dblSerialCorrelation = apds.serialCorrelation();

		double dblTimeUnitSQRT = java.lang.Math.sqrt (_dblTimeInterval);

		double dblExecutionRate = (_dblRightHoldings - _dblLeftHoldings) / _dblTimeInterval;

		try {
			double dblMarketCoreJumpUnit = apds.epochVolatility() * dblTimeUnitSQRT;

			return new org.drip.execution.discrete.PriceIncrement (
				dblPreviousEquilibriumPrice,
				new org.drip.execution.evolution.MarketImpactComponent (
					_dblTimeInterval * apds.drift(),
					0.,
					_dblTimeInterval * apep.permanentExpectation().epochImpactFunction().evaluate (dblExecutionRate),
					apep.temporaryExpectation().epochImpactFunction().evaluate (dblExecutionRate)
				),
				new org.drip.execution.evolution.MarketImpactComponent (
					dblMarketCoreJumpUnit * java.lang.Math.sqrt (1. + dblSerialCorrelation * dblSerialCorrelation) *
						ws.currentWanderer(),
					dblMarketCoreJumpUnit * dblSerialCorrelation * ws.previousWanderer(),
					dblTimeUnitSQRT * apep.permanentVolatility().epochImpactFunction().evaluate (dblExecutionRate) *
						ws.permanentImpactWanderer(),
					dblTimeUnitSQRT * apep.temporaryVolatility().epochImpactFunction().evaluate (dblExecutionRate) *
						ws.temporaryImpactWanderer()
				)
			);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the Cost Evolution Increment Unit Realization given the Walk Realization
	 * 
	 * @param dblPreviousEquilibriumPrice The Previous Equilibrium Price
	 * @param ws Realized Walk Suite
	 * @param apep The Arithmetic Price Walk Evolution Parameters
	 * 
	 * @return The Cost Evolution Increment Unit Realization given the Walk Realization
	 */

	public org.drip.execution.discrete.ShortfallIncrement costIncrementRealization (
		final double dblPreviousEquilibriumPrice,
		final org.drip.execution.dynamics.WalkSuite ws,
		final org.drip.execution.dynamics.ArithmeticPriceEvolutionParameters apep)
	{
		return org.drip.execution.discrete.ShortfallIncrement.Standard (
			priceIncrementRealization (dblPreviousEquilibriumPrice, ws, apep),
			_dblLeftHoldings,
			_dblRightHoldings - _dblLeftHoldings
		);
	}

	/**
	 * Generate the R^1 Normal Cost Increment Distribution
	 * 
	 * @param apep The Arithmetic Price Walk Evolution Parameters
	 * 
	 * @return The R^1 Normal Cost Increment Distribution
	 */

	public org.drip.execution.discrete.ShortfallIncrementDistribution costIncrementDistribution (
		final org.drip.execution.dynamics.ArithmeticPriceEvolutionParameters apep)
	{
		double dblTradeAmount = _dblRightHoldings > _dblLeftHoldings ? _dblRightHoldings - _dblLeftHoldings :
			_dblLeftHoldings - _dblRightHoldings;
		double dblExecutionRate = dblTradeAmount / _dblTimeInterval;

		org.drip.execution.parameters.ArithmeticPriceDynamicsSettings apds =
			apep.arithmeticPriceDynamicsSettings();

		try {
			double dblMarketCoreVolatility = apds.epochVolatility();

			double dblTemporaryVolatility = apep.temporaryVolatility().epochImpactFunction().evaluate
				(dblTradeAmount, _dblTimeInterval);

			return new org.drip.execution.discrete.ShortfallIncrementDistribution (
				_dblTimeInterval * _dblRightHoldings * apep.permanentExpectation().epochImpactFunction().evaluate
					(dblExecutionRate),
				dblTradeAmount * apep.temporaryExpectation().epochImpactFunction().evaluate (dblExecutionRate),
				-1. * _dblRightHoldings * apds.drift() * _dblTimeInterval,
				0.,
				dblExecutionRate * dblExecutionRate * dblTemporaryVolatility * dblTemporaryVolatility *
					_dblTimeInterval,
				_dblRightHoldings * _dblRightHoldings * dblMarketCoreVolatility * dblMarketCoreVolatility *
					_dblTimeInterval
			);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override public org.drip.execution.sensitivity.ControlNodesGreek permanentImpactExpectation (
		final org.drip.execution.dynamics.ArithmeticPriceEvolutionParameters apep)
	{
		if (null == apep) return null;

		double dblTradeAmount = _dblRightHoldings - _dblLeftHoldings;
		double dblSign = _dblRightHoldings < _dblLeftHoldings ? -1. : 1.;

		org.drip.execution.impact.TransactionFunction tfPermanentDrift =
			apep.permanentExpectation().epochImpactFunction();

		try {
			double dblPermanentDrift = tfPermanentDrift.evaluate (dblTradeAmount, _dblTimeInterval);

			double dblPermanentDriftLeftJacobian = tfPermanentDrift.leftHoldingsDerivative (dblTradeAmount,
				_dblTimeInterval, 1);

			double dblPermanentDriftRightJacobian = tfPermanentDrift.rightHoldingsDerivative (dblTradeAmount,
				_dblTimeInterval, 1);

			double dblPermanentDriftLeftHessian = tfPermanentDrift.leftHoldingsDerivative (dblTradeAmount,
				_dblTimeInterval, 2);

			double dblPermanentDriftRightHessian = tfPermanentDrift.rightHoldingsDerivative (dblTradeAmount,
				_dblTimeInterval, 2);

			double dblPermanentDriftCrossHessian = tfPermanentDrift.crossHoldingsDerivative (dblTradeAmount,
				_dblTimeInterval);

			double dblPermanentDriftImpact = dblSign * _dblTimeInterval * _dblRightHoldings *
				dblPermanentDrift;
			double dblPermanentDriftImpactLeftJacobian = dblSign * _dblTimeInterval * _dblRightHoldings *
				dblPermanentDriftLeftJacobian;
			double dblPermanentDriftImpactRightJacobian = dblSign * _dblTimeInterval * _dblRightHoldings *
				dblPermanentDriftRightJacobian + dblSign * _dblTimeInterval * dblPermanentDrift;
			double dblPermanentDriftImpactLeftHessian = dblSign * _dblTimeInterval * _dblRightHoldings *
				dblPermanentDriftLeftHessian;
			double dblPermanentDriftImpactRightHessian = dblSign * _dblTimeInterval * _dblRightHoldings *
				dblPermanentDriftRightHessian + 2. *  dblSign * _dblTimeInterval *
					dblPermanentDriftRightJacobian;
			double dblPermanentDriftImpactCrossHessian = dblSign * _dblTimeInterval *
				dblPermanentDriftLeftJacobian + dblSign * _dblTimeInterval * _dblRightHoldings *
					dblPermanentDriftCrossHessian;

			return new org.drip.execution.sensitivity.ControlNodesGreek (
				dblPermanentDriftImpact,
				new double[] {
					dblPermanentDriftImpactLeftJacobian,
					dblPermanentDriftImpactRightJacobian
				},
				new double[][] {
					{dblPermanentDriftImpactLeftHessian, dblPermanentDriftImpactCrossHessian},
					{dblPermanentDriftImpactCrossHessian, dblPermanentDriftImpactRightHessian}
				}
			);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override public org.drip.execution.sensitivity.ControlNodesGreek permanentImpactVariance (
		final org.drip.execution.dynamics.ArithmeticPriceEvolutionParameters apep)
	{
		try {
			return new org.drip.execution.sensitivity.ControlNodesGreek (0., new double[] {0., 0.}, new
				double[][] {{0., 0.}, {0., 0.}});
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override public org.drip.execution.sensitivity.ControlNodesGreek temporaryImpactExpectation (
		final org.drip.execution.dynamics.ArithmeticPriceEvolutionParameters apep)
	{
		if (null == apep) return null;

		double dblTradeAmount = _dblRightHoldings - _dblLeftHoldings;

		org.drip.execution.impact.TransactionFunction tfTemporaryDrift =
			apep.temporaryExpectation().epochImpactFunction();

		try {
			double dblTemporaryDrift = tfTemporaryDrift.evaluate (dblTradeAmount, _dblTimeInterval);

			double dblTemporaryDriftLeftJacobian = tfTemporaryDrift.leftHoldingsDerivative (dblTradeAmount,
				_dblTimeInterval, 1);

			double dblTemporaryDriftRightJacobian = tfTemporaryDrift.rightHoldingsDerivative
				(dblTradeAmount, _dblTimeInterval, 1);

			double dblTemporaryDriftLeftHessian = tfTemporaryDrift.leftHoldingsDerivative (dblTradeAmount,
				_dblTimeInterval, 2);

			double dblTemporaryDriftRightHessian = tfTemporaryDrift.rightHoldingsDerivative (dblTradeAmount,
				_dblTimeInterval, 2);

			double dblTemporaryDriftCrossHessian = tfTemporaryDrift.crossHoldingsDerivative (dblTradeAmount,
				_dblTimeInterval);

			double dblTemporaryDriftImpact = dblTradeAmount * dblTemporaryDrift;

			double dblTemporaryDriftImpactLeftJacobian = -1. * dblTemporaryDrift + dblTradeAmount *
				dblTemporaryDriftLeftJacobian;
			double dblTemporaryDriftImpactRightJacobian = dblTemporaryDrift + dblTradeAmount *
				dblTemporaryDriftRightJacobian;
			double dblTemporaryDriftImpactLeftHessian = -2. * dblTemporaryDriftLeftJacobian + dblTradeAmount
				* dblTemporaryDriftLeftHessian;
			double dblTemporaryDriftImpactRightHessian = 2. * dblTemporaryDriftRightJacobian + dblTradeAmount
				* dblTemporaryDriftRightHessian;
			double dblTemporaryDriftImpactCrossHessian = -1. * dblTemporaryDriftRightJacobian +
				dblTemporaryDriftLeftJacobian + dblTradeAmount * dblTemporaryDriftCrossHessian;

			return new org.drip.execution.sensitivity.ControlNodesGreek (
				dblTemporaryDriftImpact,
				new double[] {
					dblTemporaryDriftImpactLeftJacobian,
					dblTemporaryDriftImpactRightJacobian
				},
				new double[][] {
					{dblTemporaryDriftImpactLeftHessian, dblTemporaryDriftImpactCrossHessian},
					{dblTemporaryDriftImpactCrossHessian, dblTemporaryDriftImpactRightHessian}
				}
			);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override public org.drip.execution.sensitivity.ControlNodesGreek temporaryImpactVariance (
		final org.drip.execution.dynamics.ArithmeticPriceEvolutionParameters apep)
	{
		if (null == apep) return null;

		double dblTradeAmount = _dblRightHoldings - _dblLeftHoldings;
		double dblTradeAmountSquared = dblTradeAmount * dblTradeAmount;

		org.drip.execution.impact.TransactionFunction tfTemporaryVolatility =
			apep.temporaryVolatility().epochImpactFunction();

		try {
			if (null == tfTemporaryVolatility)
				return new org.drip.execution.sensitivity.ControlNodesGreek (0., new double[] {0., 0.}, new
					double[][] {{0., 0.}, {0., 0.}});

			double dblTemporaryVolatility = tfTemporaryVolatility.evaluate (dblTradeAmount,
				_dblTimeInterval);

			double dblTemporaryVolatilityLeftJacobian = tfTemporaryVolatility.leftHoldingsDerivative
				(dblTradeAmount, _dblTimeInterval, 1);

			double dblTemporaryVolatilityRightJacobian = tfTemporaryVolatility.rightHoldingsDerivative
				(dblTradeAmount, _dblTimeInterval, 1);

			double dblTemporaryVolatilityLeftHessian = tfTemporaryVolatility.leftHoldingsDerivative
				(dblTradeAmount, _dblTimeInterval, 2);

			double dblTemporaryVolatilityRightHessian = tfTemporaryVolatility.rightHoldingsDerivative
				(dblTradeAmount, _dblTimeInterval, 2);

			double dblTemporaryVolatilityCrossHessian = tfTemporaryVolatility.crossHoldingsDerivative
				(dblTradeAmount, _dblTimeInterval);

			double dblTemporaryVolatilitySquared = dblTemporaryVolatility * dblTemporaryVolatility;

			double dblTemporaryVarianceCrossHessian =
				2. * dblTradeAmountSquared * dblTemporaryVolatilityLeftJacobian * dblTemporaryVolatilityRightJacobian * _dblTimeInterval
					+ 2. * dblTradeAmountSquared * dblTemporaryVolatility * dblTemporaryVolatilityCrossHessian * _dblTimeInterval
						+ 4. * dblTradeAmount * dblTemporaryVolatility * dblTemporaryVolatilityLeftJacobian * _dblTimeInterval
							- 4. * dblTradeAmount * dblTemporaryVolatility * dblTemporaryVolatilityRightJacobian * _dblTimeInterval
								- 2. * dblTemporaryVolatilitySquared * _dblTimeInterval;

			return new org.drip.execution.sensitivity.ControlNodesGreek (
				dblTradeAmountSquared * dblTemporaryVolatilitySquared * _dblTimeInterval,
				new double[] {
					2. * dblTradeAmountSquared * dblTemporaryVolatility * dblTemporaryVolatilityLeftJacobian * _dblTimeInterval
						- 2. * dblTradeAmount * dblTemporaryVolatilitySquared * _dblTimeInterval,
					2. * dblTradeAmountSquared * dblTemporaryVolatility * dblTemporaryVolatilityRightJacobian * _dblTimeInterval
						+ 2. * dblTradeAmount * dblTemporaryVolatilitySquared * _dblTimeInterval
				},
				new double[][] {
					{
						2. * dblTradeAmountSquared * dblTemporaryVolatilityLeftJacobian * dblTemporaryVolatilityLeftJacobian * _dblTimeInterval
							+ 2. * dblTradeAmountSquared * dblTemporaryVolatility * dblTemporaryVolatilityLeftHessian * _dblTimeInterval
								- 8. * dblTradeAmount * dblTemporaryVolatility * dblTemporaryVolatilityLeftJacobian * _dblTimeInterval
									+ 2. * dblTemporaryVolatilitySquared * _dblTimeInterval,
						dblTemporaryVarianceCrossHessian
					}, {
						dblTemporaryVarianceCrossHessian,
						2. * dblTradeAmountSquared * dblTemporaryVolatilityRightJacobian * dblTemporaryVolatilityRightJacobian * _dblTimeInterval
							+ 2. * dblTradeAmountSquared * dblTemporaryVolatility * dblTemporaryVolatilityRightHessian * _dblTimeInterval
								+ 8. * dblTradeAmount * dblTemporaryVolatility * dblTemporaryVolatilityRightJacobian * _dblTimeInterval
									+ 2. * dblTemporaryVolatilitySquared * _dblTimeInterval
					}
				}
			);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override public org.drip.execution.sensitivity.ControlNodesGreek marketDynamicsExpectation (
		final org.drip.execution.dynamics.ArithmeticPriceEvolutionParameters apep)
	{
		if (null == apep) return null;

		double dblDrift = apep.arithmeticPriceDynamicsSettings().drift();

		try {
			return new org.drip.execution.sensitivity.ControlNodesGreek (
				-1. * _dblTimeInterval * dblDrift * _dblRightHoldings,
				new double[] {
					0.,
					-1. * _dblTimeInterval * dblDrift
				}, new
				double[][] {
					{0., 0.},
					{0., 0.}
				}
			);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override public org.drip.execution.sensitivity.ControlNodesGreek marketDynamicsVariance (
		final org.drip.execution.dynamics.ArithmeticPriceEvolutionParameters apep)
	{
		if (null == apep) return null;

		try {
			double dblVolatility = apep.arithmeticPriceDynamicsSettings().epochVolatility();

			return new org.drip.execution.sensitivity.ControlNodesGreek (
				_dblTimeInterval * dblVolatility * dblVolatility * _dblRightHoldings * _dblRightHoldings,
				new double[] {
					0.,
					2. * _dblTimeInterval * dblVolatility * dblVolatility * _dblRightHoldings
				}, new double[][] {
					{0., 0.},
					{0., 2. * _dblTimeInterval * dblVolatility * dblVolatility}
				}
			);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override public org.drip.execution.sensitivity.ControlNodesGreek expectationContribution (
		final org.drip.execution.dynamics.ArithmeticPriceEvolutionParameters apep)
	{
		org.drip.execution.sensitivity.ControlNodesGreek cngPermanentImpact = permanentImpactExpectation
			(apep);

		if (null == cngPermanentImpact) return null;

		org.drip.execution.sensitivity.ControlNodesGreek cngTemporaryImpact = temporaryImpactExpectation
			(apep);

		if (null == cngTemporaryImpact) return null;

		org.drip.execution.sensitivity.ControlNodesGreek cngMarketCore = marketDynamicsExpectation (apep);

		if (null == cngMarketCore) return null;

		double[][] aadblPermanentImpactExpectationHessian = cngPermanentImpact.hessian();

		double[][] aadblTemporaryImpactExpectationHessian = cngTemporaryImpact.hessian();

		double[] adblPermanentImpactExpectationJacobian = cngPermanentImpact.jacobian();

		double[] adblTemporaryImpactExpectationJacobian = cngTemporaryImpact.jacobian();

		double[][] aadblMarketCoreExpectationHessian = cngMarketCore.hessian();

		double[] adblMarketCoreExpectationJacobian = cngMarketCore.jacobian();

		int iNumSliceNode = adblMarketCoreExpectationJacobian.length;
		double[][] aadblHessian = new double[iNumSliceNode][iNumSliceNode];
		double[] adblJacobian = new double[iNumSliceNode];

		for (int i = 0; i < iNumSliceNode; ++i) {
			adblJacobian[i] = adblPermanentImpactExpectationJacobian[i] +
				adblTemporaryImpactExpectationJacobian[i] + adblMarketCoreExpectationJacobian[i];

			for (int j = 0; j < iNumSliceNode; ++j)
				aadblHessian[i][j] = aadblPermanentImpactExpectationHessian[i][j] +
					aadblTemporaryImpactExpectationHessian[i][j] + aadblMarketCoreExpectationHessian[i][j];
		}

		try {
			return new org.drip.execution.sensitivity.ControlNodesGreek (cngPermanentImpact.value() +
				cngTemporaryImpact.value() + cngMarketCore.value(), adblJacobian, aadblHessian);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override public org.drip.execution.sensitivity.ControlNodesGreek varianceContribution (
		final org.drip.execution.dynamics.ArithmeticPriceEvolutionParameters apep)
	{
		org.drip.execution.sensitivity.ControlNodesGreek cngPermanentImpact = permanentImpactVariance (apep);

		if (null == cngPermanentImpact) return null;

		org.drip.execution.sensitivity.ControlNodesGreek cngTemporaryImpact = temporaryImpactVariance (apep);

		if (null == cngTemporaryImpact) return null;

		org.drip.execution.sensitivity.ControlNodesGreek cngMarketCore = marketDynamicsVariance (apep);

		if (null == cngMarketCore) return null;

		double[][] aadblPermanentImpactExpectationHessian = cngPermanentImpact.hessian();

		double[][] aadblTemporaryImpactExpectationHessian = cngTemporaryImpact.hessian();

		double[] adblPermanentImpactExpectationJacobian = cngPermanentImpact.jacobian();

		double[] adblTemporaryImpactExpectationJacobian = cngTemporaryImpact.jacobian();

		double[][] aadblMarketCoreExpectationHessian = cngMarketCore.hessian();

		double[] adblMarketCoreExpectationJacobian = cngMarketCore.jacobian();

		int iNumSliceNode = adblMarketCoreExpectationJacobian.length;
		double[][] aadblHessian = new double[iNumSliceNode][iNumSliceNode];
		double[] adblJacobian = new double[iNumSliceNode];

		for (int i = 0; i < iNumSliceNode; ++i) {
			adblJacobian[i] = adblPermanentImpactExpectationJacobian[i] +
				adblTemporaryImpactExpectationJacobian[i] + adblMarketCoreExpectationJacobian[i];

			for (int j = 0; j < iNumSliceNode; ++j)
				aadblHessian[i][j] = aadblPermanentImpactExpectationHessian[i][j] +
					aadblTemporaryImpactExpectationHessian[i][j] + aadblMarketCoreExpectationHessian[i][j];
		}

		try {
			return new org.drip.execution.sensitivity.ControlNodesGreek (cngPermanentImpact.value() +
				cngTemporaryImpact.value() + cngMarketCore.value(), adblJacobian, aadblHessian);
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

	public org.drip.execution.discrete.OptimalSerialCorrelationAdjustment serialCorrelationAdjustment (
		final org.drip.execution.dynamics.ArithmeticPriceEvolutionParameters apep)
	{
		if (null == apep) return null;

		org.drip.execution.parameters.ArithmeticPriceDynamicsSettings apds =
			apep.arithmeticPriceDynamicsSettings();

		double dblTradeRate = (_dblRightHoldings - _dblLeftHoldings) / _dblTimeInterval;

		org.drip.execution.impact.TransactionFunction miTemporary =
			apep.temporaryExpectation().epochImpactFunction();

		try {
			double dblRhoSigma = apds.serialCorrelation() * apds.epochVolatility();

			double dblDenominator = 1. / (dblTradeRate * miTemporary.derivative (dblTradeRate, 2) + 2. *
				miTemporary.derivative (dblTradeRate, 1));

			return new org.drip.execution.discrete.OptimalSerialCorrelationAdjustment (dblDenominator *
				dblRhoSigma * java.lang.Math.pow (_dblTimeInterval, 1.5), 0.5 * dblDenominator * dblRhoSigma
					* dblRhoSigma * _dblTimeInterval * _dblTimeInterval);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
