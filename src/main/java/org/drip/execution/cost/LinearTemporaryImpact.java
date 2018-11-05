
package org.drip.execution.cost;

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
 * <i>LinearTemporaryImpact</i> computes and holds the Optimal Trajectory using the Linear Temporary Impact
 *  Function for the given set of Inputs. It provides a Default Unconstrained Trajectory Implementation. The
 *  References are:
 * 
 * <br>
 * 	<ul>
 * 		<li>
 * 			Bertsimas, D., and A. W. Lo (1998): Optimal Control of Execution Costs <i>Journal of Financial
 * 				Markets</i> <b>1</b> 1-50
 * 		</li>
 * 		<li>
 * 			Almgren, R., and N. Chriss (2000): Optimal Execution of Portfolio Transactions <i>Journal of
 * 				Risk</i> <b>3 (2)</b> 5-39
 * 		</li>
 * 		<li>
 * 			Brunnermeier, L. K., and L. H. Pedersen (2005): Predatory Trading <i>Journal of Finance</i> <b>60
 * 				(4)</b> 1825-1863
 * 		</li>
 * 		<li>
 * 			Almgren, R., and J. Lorenz (2006): Bayesian Adaptive Trading with a Daily Cycle <i>Journal of
 * 				Trading</i> <b>1 (4)</b> 38-46
 * 		</li>
 * 		<li>
 * 			Kissell, R., and R. Malamut (2007): Algorithmic Decision Making Framework <i>Journal of
 * 				Trading</i> <b>1 (1)</b> 12-21
 * 		</li>
 * 	</ul>
 * <br>
 *  <ul>
 *		<li><b>Module</b>        = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution">Execution</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/cost">Cost</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/TransactionCost">Transaction Cost Analytics</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class LinearTemporaryImpact {
	private double _dblSpotTime = java.lang.Double.NaN;
	private double _dblFinishTime = java.lang.Double.NaN;
	private double _dblSpotHoldings = java.lang.Double.NaN;
	private double _dblGrossPriceChange = java.lang.Double.NaN;
	private double _dblTransactionCostGain = java.lang.Double.NaN;
	private double _dblStaticTransactionCost = java.lang.Double.NaN;
	private double _dblInstantaneousTradeRate = java.lang.Double.NaN;
	private org.drip.execution.bayesian.PriorConditionalCombiner _pcc = null;
	private org.drip.execution.impact.TransactionFunctionLinear _tflTemporary = null;
	private org.drip.execution.optimum.EfficientTradingTrajectoryContinuous _ectt = null;

	/**
	 * Generate an Unconstrained LinearTemporaryImpact Instance
	 * 
	 * @param dblSpotTime Spot Time
	 * @param dblFinishTime Finish Time
	 * @param dblSpotHoldings Spot Holdings
	 * @param pcc The Prior/Conditional Combiner
	 * @param dblGrossPriceChange The Gross Price Change
	 * @param tflTemporary The Temporary Linear Impact Function
	 * 
	 * @return The Unconstrained LinearTemporaryImpact Instance
	 */

	public static final LinearTemporaryImpact Unconstrained (
		final double dblSpotTime,
		final double dblFinishTime,
		final double dblSpotHoldings,
		final org.drip.execution.bayesian.PriorConditionalCombiner pcc,
		final double dblGrossPriceChange,
		final org.drip.execution.impact.TransactionFunctionLinear tflTemporary)
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblSpotTime) ||
			!org.drip.quant.common.NumberUtil.IsValid (dblFinishTime) ||
				!org.drip.quant.common.NumberUtil.IsValid (dblSpotHoldings) || null == pcc || null ==
					tflTemporary)
			return null;

		final double dblHorizon = dblFinishTime - dblSpotTime;

		org.drip.measure.gaussian.R1UnivariateNormal r1unPosterior = pcc.posteriorDriftDistribution
			(dblGrossPriceChange);

		if (null == r1unPosterior) return null;

		final double dblScaledDrift = 0.25 * r1unPosterior.mean() / tflTemporary.slope();

		org.drip.function.definition.R1ToR1 r1ToR1Holdings = new org.drip.function.definition.R1ToR1 (null) {
			@Override public double evaluate (
				final double dblTau)
				throws java.lang.Exception
			{
				if (0. >= dblHorizon) return 0.;

				if (!org.drip.quant.common.NumberUtil.IsValid (dblTau))
					throw new java.lang.Exception
						("LinearTemporaryImpact::Holdings::evaluate => Invalid Inputs");

				if (dblTau <= dblSpotTime) return dblSpotHoldings;

				if (dblTau >= dblFinishTime) return 0.;

				return (dblFinishTime - dblTau) * (dblSpotHoldings / (dblFinishTime - dblSpotTime) -
					dblScaledDrift * (dblTau - dblSpotTime));
			}
		};

		try {
			return new LinearTemporaryImpact (dblSpotTime, dblFinishTime, dblSpotHoldings, pcc,
				dblGrossPriceChange, tflTemporary, dblFinishTime - dblSpotTime, r1ToR1Holdings, 0 >=
					dblHorizon ? 0. : dblSpotHoldings / dblHorizon + dblScaledDrift * dblHorizon);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	protected LinearTemporaryImpact (
		final double dblSpotTime,
		final double dblFinishTime,
		final double dblSpotHoldings,
		final org.drip.execution.bayesian.PriorConditionalCombiner pcc,
		final double dblGrossPriceChange,
		final org.drip.execution.impact.TransactionFunctionLinear tflTemporary,
		final double dblCharacteristicTime,
		final org.drip.function.definition.R1ToR1 r1ToR1Holdings,
		final double dblInstantaneousTradeRate)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (_dblSpotTime = dblSpotTime) ||
			!org.drip.quant.common.NumberUtil.IsValid (_dblFinishTime = dblFinishTime) ||
				!org.drip.quant.common.NumberUtil.IsValid (_dblSpotHoldings = dblSpotHoldings) || null ==
					(_pcc = pcc) || !org.drip.quant.common.NumberUtil.IsValid (_dblGrossPriceChange =
						dblGrossPriceChange) || null == (_tflTemporary = tflTemporary) || null ==
							r1ToR1Holdings || !org.drip.quant.common.NumberUtil.IsValid
								(_dblInstantaneousTradeRate = dblInstantaneousTradeRate))
			throw new java.lang.Exception ("LinearTemporaryImpact Constructor => Invalid Inputs");

		final double dblLiquidityCoefficient = _tflTemporary.slope();

		double dblDriftEstimate = _pcc.posteriorDriftDistribution (_dblGrossPriceChange).mean();

		final double dblExecutionTime = _dblFinishTime - _dblSpotTime;
		_dblStaticTransactionCost = _dblSpotHoldings * _dblSpotHoldings * dblLiquidityCoefficient /
			dblExecutionTime + 0.5 * _dblSpotHoldings * dblDriftEstimate * dblExecutionTime -
				dblExecutionTime * dblExecutionTime * dblExecutionTime * dblDriftEstimate * dblDriftEstimate
					/ (48. * dblLiquidityCoefficient);

		double dblDriftConfidence = _pcc.prior().confidence();

		final double dblPriceVolatility = _pcc.conditional().priceVolatility();

		org.drip.function.definition.R1ToR1 r1ToR1HoldingsSquared = new org.drip.function.definition.R1ToR1
			(null) {
			@Override public double evaluate (
				final double dblTime)
				throws java.lang.Exception
			{
				double dblHoldings = r1ToR1Holdings.evaluate (dblTime);

				return dblHoldings * dblHoldings;
			}
		};

		final double dblRho = dblPriceVolatility * dblPriceVolatility / (dblDriftConfidence *
			dblDriftConfidence * dblExecutionTime);

		org.drip.function.definition.R1ToR1 r1ToR1Quadrature = new org.drip.function.definition.R1ToR1 (null)
		{
			@Override public double evaluate (
				final double dblDelta)
				throws java.lang.Exception
			{
				if (!org.drip.quant.common.NumberUtil.IsValid (dblDelta))
					throw new java.lang.Exception
						("LinearTemporaryImpact::r1ToR1Quadrature::evaluate => Invalid Inputs");

				double dblRemainingTime = 1. - dblDelta;
				double dblDimensionlessTime = dblDelta + dblRho;
				return dblRemainingTime * dblRemainingTime * dblRemainingTime / (dblDimensionlessTime *
					dblDimensionlessTime);
			}
		};

		_dblTransactionCostGain = dblPriceVolatility * dblPriceVolatility * dblExecutionTime *
			dblExecutionTime / (48. * linearTemporaryImpact().slope()) * r1ToR1Quadrature.integrate (0., 1.);

		org.drip.function.definition.R1ToR1 r1ToR1TradeRate = new org.drip.function.definition.R1ToR1 (null)
		{
			@Override public double evaluate (
				final double dblS)
				throws java.lang.Exception
			{
				return r1ToR1Holdings.derivative (dblS, 1);
			}
		};

		final org.drip.function.definition.R1ToR1 r1ToR1TransactionCostExpectationRate = new
			org.drip.function.definition.R1ToR1 (null) {
			@Override public double evaluate (
				final double dblTime)
				throws java.lang.Exception
			{
				double dblTradeRate = r1ToR1Holdings.derivative (dblTime, 1);

				return dblLiquidityCoefficient * dblLiquidityCoefficient * dblTradeRate * dblTradeRate;
			}
		};

		org.drip.function.definition.R1ToR1 r1ToR1TransactionCostExpectation = new
			org.drip.function.definition.R1ToR1 (null) {
			@Override public double evaluate (
				final double dblTime)
				throws java.lang.Exception
			{
				return r1ToR1TransactionCostExpectationRate.integrate (dblTime, dblExecutionTime);
			}
		};

		final org.drip.function.definition.R1ToR1 r1ToR1TransactionCostVarianceRate = new
			org.drip.function.definition.R1ToR1 (null) {
			@Override public double evaluate (
				final double dblTime)
				throws java.lang.Exception
			{
				double dblHoldings = r1ToR1Holdings.evaluate (dblTime);

				return dblPriceVolatility * dblPriceVolatility * dblHoldings * dblHoldings;
			}
		};

		org.drip.function.definition.R1ToR1 r1ToR1TransactionCostVariance = new
			org.drip.function.definition.R1ToR1 (null) {
			@Override public double evaluate (
				final double dblTime)
				throws java.lang.Exception
			{
				return r1ToR1TransactionCostVarianceRate.integrate (dblTime, dblExecutionTime);
			}
		};

		_ectt = new org.drip.execution.optimum.EfficientTradingTrajectoryContinuous (dblExecutionTime,
			_dblStaticTransactionCost + _dblTransactionCostGain, dblPriceVolatility * dblPriceVolatility *
				r1ToR1HoldingsSquared.integrate (_dblSpotTime, _dblFinishTime), dblCharacteristicTime,
					dblLiquidityCoefficient * _dblSpotHoldings / (dblExecutionTime * dblLiquidityCoefficient
						* java.lang.Math.sqrt (dblExecutionTime)), r1ToR1Holdings, r1ToR1TradeRate,
							r1ToR1TransactionCostExpectation, r1ToR1TransactionCostVariance);
	}

	/**
	 * Retrieve the Spot Time
	 * 
	 * @return The Spot Time
	 */

	public double spotTime()
	{
		return _dblSpotTime;
	}

	/**
	 * Retrieve the Finish Time
	 * 
	 * @return The Finish Time
	 */

	public double finishTime()
	{
		return _dblFinishTime;
	}

	/**
	 * Retrieve the Spot Holdings
	 * 
	 * @return The Spot Holdings
	 */

	public double spotHoldings()
	{
		return _dblSpotHoldings;
	}

	/**
	 * Retrieve the Prior/Conditional Distributions Combiner
	 * 
	 * @return The Prior/Conditional Distributions Combiner
	 */

	public org.drip.execution.bayesian.PriorConditionalCombiner combiner()
	{
		return _pcc;
	}

	/**
	 * Retrieve the Gross Price Change
	 * 
	 * @return The Gross Price Change
	 */

	public double grossPriceChange()
	{
		return _dblGrossPriceChange;
	}

	/**
	 * Retrieve the Drift Expectation Estimate
	 * 
	 * @return The Drift Expectation Estimate
	 */

	public double driftExpectationEstimate()
	{
		return _pcc.posteriorDriftDistribution (_dblGrossPriceChange).mean();
	}

	/**
	 * Retrieve the Drift Volatility Estimate
	 * 
	 * @return The Drift Volatility Estimate
	 */

	public double driftVolatilityEstimate()
	{
		return java.lang.Math.sqrt (_pcc.posteriorDriftDistribution (_dblGrossPriceChange).variance());
	}

	/**
	 * Retrieve the Linear Temporary Market Impact Function
	 * 
	 * @return The Linear Temporary Market Impact Function
	 */

	public org.drip.execution.impact.TransactionFunctionLinear linearTemporaryImpact()
	{
		return _tflTemporary;
	}

	/**
	 * Retrieve the Holdings Trajectory
	 * 
	 * @return The Holdings Trajectory
	 */

	public org.drip.execution.optimum.EfficientTradingTrajectoryContinuous trajectory()
	{
		return _ectt;
	}

	/**
	 * Retrieve the Instantaneous Trade Rate
	 * 
	 * @return The Instantaneous Trade Rate
	 */

	public double instantaneousTradeRate()
	{
		return _dblInstantaneousTradeRate;
	}

	/**
	 * Estimate of the Static Transaction Cost
	 * 
	 * @return The Static Transaction Cost Estimate
	 */

	public double staticTransactionCost()
	{
		return _dblStaticTransactionCost;
	}

	/**
	 * Estimate the Transaction Cost Gain available from the Bayesian Drift
	 * 
	 * @return The Transaction Cost Gain available from the Bayesian Drift
	 */

	public double transactionCostGain()
	{
		return _dblTransactionCostGain;
	}
}
