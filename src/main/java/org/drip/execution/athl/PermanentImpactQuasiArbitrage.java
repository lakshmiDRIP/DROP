
package org.drip.execution.athl;

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
 * <i>PermanentImpactQuasiArbitrage</i> implements the Linear Permanent Market Impact with Coefficients that
 * have been determined empirically by Almgren, Thum, Hauptmann, and Li (2005), independent of the no Quasi-
 * Arbitrage Criterion identified by Huberman and Stanzl (2004). The References are:
 * 
 * <br><br>
 * 	<ul>
 * 	<li>
 * 		Almgren, R., and N. Chriss (2000): Optimal Execution of Portfolio Transactions <i>Journal of Risk</i>
 * 			<b>3 (2)</b> 5-39
 * 	</li>
 * 	<li>
 * 		Almgren, R. (2003): Optimal Execution with Nonlinear Impact Functions and Trading-Enhanced Risk
 * 			<i>Applied Mathematical Finance</i> <b>10 (1)</b> 1-18
 * 	</li>
 * 	<li>
 * 		Almgren, R., and N. Chriss (2003): Bidding Principles <i>Risk</i> 97-102
 * 	</li>
 * 	<li>
 * 		Almgren, R., C. Thum, E. Hauptmann, and H. Li (2005): Equity Market Impact <i>Risk</i> <b>18 (7)</b>
 * 			57-62
 * 	</li>
 * 	<li>
 * 		Huberman, G., and W. Stanzl (2004): Price Manipulation and Quasi-arbitrage <i>Econometrics</i>
 * 			<b>72 (4)</b> 1247-1275
 * 	</li>
 * 	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/TransactionCostAnalyticsLibrary.md">Transaction Cost Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/README.md">Optimal Impact/Capture Based Trading Trajectories - Deterministic, Stochastic, Static, and Dynamic</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/athl/README.md">Almgren-Thum-Hauptmann-Li Calibration</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class PermanentImpactQuasiArbitrage extends org.drip.execution.impact.TransactionFunctionPower {
	private double _dblLiquidityFactor = java.lang.Double.NaN;
	private org.drip.execution.parameters.AssetFlowSettings _afp = null;

	/**
	 * PermanentImpactQuasiArbitrage Constructor
	 * 
	 * @param afp The Asset Flow Parameters
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public PermanentImpactQuasiArbitrage (
		final org.drip.execution.parameters.AssetFlowSettings afp)
		throws java.lang.Exception
	{
		if (null == (_afp = afp))
			throw new java.lang.Exception ("PermanentImpactQuasiArbitrage Constructor => Invalid Inputs");

		_dblLiquidityFactor = java.lang.Math.pow (afp.inverseTurnover(),
			org.drip.execution.athl.CalibrationEmpirics.PERMANENT_IMPACT_INVERSE_TURNOVER_EXPONENT);
	}

	/**
	 * Retrieve the Liquidity Factor
	 * 
	 * @return The Liquidity Factor
	 */

	public double liquidityFactor()
	{
		return _dblLiquidityFactor;
	}

	/**
	 * Retrieve the Asset Flow Parameters
	 * 
	 * @return The Asset Flow Parameters
	 */

	public org.drip.execution.parameters.AssetFlowSettings assetFlowParameters()
	{
		return _afp;
	}

	@Override public double regularize (
		final double dblTradeInterval)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblTradeInterval) || 0 >= dblTradeInterval)
			throw new java.lang.Exception ("PermanentImpactQuasiArbitrage::regularize => Invalid Inputs");

		return 1. / (_afp.averageDailyVolume() * dblTradeInterval);
	}

	@Override public double modulate (
		final double dblTradeInterval)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblTradeInterval) || 0 >= dblTradeInterval)
			throw new java.lang.Exception ("PermanentImpactQuasiArbitrage::modulate => Invalid Inputs");

		return dblTradeInterval * _afp.dailyVolatility();
	}

	@Override public double constant()
	{
		return org.drip.execution.athl.CalibrationEmpirics.PERMANENT_IMPACT_COEFFICIENT *
			_dblLiquidityFactor;
	}

	@Override public double exponent()
	{
		return org.drip.execution.athl.CalibrationEmpirics.PERMANENT_IMPACT_EXPONENT_ATHL2005;
	}

	@Override public double evaluate (
		final double dblNormalizedX)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblNormalizedX))
			throw new java.lang.Exception ("PermanentImpactQuasiArbitrage::evaluate => Invalid Inputs");

		double dblAlpha = org.drip.execution.athl.CalibrationEmpirics.PERMANENT_IMPACT_EXPONENT_ATHL2005;
		double dblGamma = org.drip.execution.athl.CalibrationEmpirics.PERMANENT_IMPACT_COEFFICIENT;

		return 0.5 * dblGamma * (dblNormalizedX < 0. ? -1. : 1.) * java.lang.Math.pow (java.lang.Math.abs
			(dblNormalizedX), dblAlpha) * _dblLiquidityFactor;
	}

	@Override public double derivative  (
		final double dblNormalizedX,
		final int iOrder)
		throws java.lang.Exception
	{
		if (0 >= iOrder || !org.drip.numerical.common.NumberUtil.IsValid (dblNormalizedX))
			throw new java.lang.Exception ("PermanentImpactQuasiArbitrage::derivative => Invalid Inputs");

		double dblCoefficient = 1.;
		double dblAlpha = org.drip.execution.athl.CalibrationEmpirics.PERMANENT_IMPACT_EXPONENT_ATHL2005;
		double dblGamma = org.drip.execution.athl.CalibrationEmpirics.PERMANENT_IMPACT_COEFFICIENT;

		for (int i = 0; i < iOrder; ++i)
			dblCoefficient = dblCoefficient * (dblAlpha - i);

		return 0.5 * dblGamma * (dblNormalizedX < 0. ? -1. : 1.) * dblCoefficient * java.lang.Math.pow
			(java.lang.Math.abs (dblNormalizedX), dblAlpha - iOrder) * _dblLiquidityFactor;
	}
}
