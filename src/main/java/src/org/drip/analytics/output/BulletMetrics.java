
package org.drip.analytics.output;

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
 * <i>BulletMetrics</i> holds the results of the Bullet Cash flow metrics estimate output.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/README.md">Date, Cash Flow, and Cash Flow Period Measure Generation Utilities</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/output/README.md">Period Product Targeted Valuation Measures</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class BulletMetrics {

	/*
	 * Bullet Latent State Identification Support Fields
	 */

	private org.drip.state.identifier.EntityCDSLabel _creditLabel = null;
	private org.drip.state.identifier.FundingLabel _fundingLabel = null;
	private org.drip.state.identifier.FXLabel _fxLabel = null;

	/*
	 * Bullet Parameters Specification Fields
	 */

	private int _iTerminalDate = java.lang.Integer.MIN_VALUE;
	private int _iPayDate = java.lang.Integer.MIN_VALUE;
	private double _dblNotional = java.lang.Double.NaN;

	/*
	 * Bullet State Point Value Fields
	 */

	private double _dblSurvival = java.lang.Double.NaN;
	private double _dblDF = java.lang.Double.NaN;
	private double _dblFX = java.lang.Double.NaN;

	/*
	 * Bullet Convexity Adjustment Fields
	 */

	private org.drip.analytics.output.ConvexityAdjustment _convAdj = null;

	/**
	 * BulletMetrics Constructor
	 * 
	 * @param iTerminalDate Terminal Date
	 * @param iPayDate Pay Date
	 * @param dblNotional Notional
	 * @param dblSurvival Terminal Survival
	 * @param dblDF Terminal Discount Factor
	 * @param dblFX Terminal FX Rate
	 * @param convAdj Terminal Convexity Adjustment
	 * @param creditLabel The Credit Label
	 * @param fundingLabel The Funding Label
	 * @param fxLabel The FX Label
	 * 
	 * @throws java.lang.Exception Thrown if Inputs are invalid
	 */

	public BulletMetrics (
		final int iTerminalDate,
		final int iPayDate,
		final double dblNotional,
		final double dblSurvival,
		final double dblDF,
		final double dblFX,
		final org.drip.analytics.output.ConvexityAdjustment convAdj,
		final org.drip.state.identifier.EntityCDSLabel creditLabel,
		final org.drip.state.identifier.FundingLabel fundingLabel,
		final org.drip.state.identifier.FXLabel fxLabel)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (_dblNotional = dblNotional) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_dblSurvival = dblSurvival) ||
				!org.drip.numerical.common.NumberUtil.IsValid (_dblDF = dblDF) ||
					!org.drip.numerical.common.NumberUtil.IsValid (_dblFX = dblFX) || null == (_convAdj =
						convAdj) || null == (_fundingLabel = fundingLabel))
			throw new java.lang.Exception ("BulletMetrics ctr: Invalid Inputs");

		_fxLabel = fxLabel;
		_iPayDate = iPayDate;
		_creditLabel = creditLabel;
		_iTerminalDate = iTerminalDate;
	}

	/**
	 * Retrieve the Terminal Date
	 * 
	 * @return The Terminal Date
	 */

	public int terminalDate()
	{
		return _iTerminalDate;
	}

	/**
	 * Retrieve the Pay Date
	 * 
	 * @return The Pay Date
	 */

	public int payDate()
	{
		return _iPayDate;
	}

	/**
	 * Retrieve the Terminal Notional
	 * 
	 * @return The Terminal Notional
	 */

	public double notional()
	{
		return _dblNotional;
	}

	/**
	 * Retrieve the Terminal Survival Probability
	 * 
	 * @return The Terminal Survival Probability
	 */

	public double survival()
	{
		return _dblSurvival;
	}

	/**
	 * Retrieve the Terminal DF
	 * 
	 * @return The Terminal DF
	 */

	public double df()
	{
		return _dblDF;
	}

	/**
	 * Retrieve the Terminal FX Rate
	 * 
	 * @return The Terminal FX Rate
	 */

	public double fx()
	{
		return _dblFX;
	}

	/**
	 * Retrieve the Terminal Annuity in the Pay Currency
	 * 
	 * @return The Terminal Annuity in the Pay Currency
	 */

	public double annuity()
	{
		return _dblNotional * _dblSurvival * _dblDF * _dblFX;
	}

	/**
	 * Retrieve the Terminal Convexity Adjustment
	 * 
	 * @return The Terminal Convexity Adjustment
	 */

	public org.drip.analytics.output.ConvexityAdjustment convexityAdjustment()
	{
		return _convAdj;
	}

	/**
	 * Retrieve the Terminal Survival Probability Loading Coefficient for the specified Credit Latent State
	 * 
	 * @param creditLabel The Credit Label
	 * 
	 * @return The Terminal Survival Probability Loading Coefficient for the specified Credit Latent State
	 */

	public java.util.Map<java.lang.Integer, java.lang.Double> survivalProbabilityCreditLoading (
		final org.drip.state.identifier.EntityCDSLabel creditLabel)
	{
		if (null == creditLabel || !creditLabel.match (_creditLabel)) return null;

		java.util.Map<java.lang.Integer, java.lang.Double> mapSurvivalProbabilityLoading = new
			java.util.TreeMap<java.lang.Integer, java.lang.Double>();

		mapSurvivalProbabilityLoading.put (_iPayDate, _dblNotional * _dblDF * _dblFX *
			_convAdj.cumulative());

		return mapSurvivalProbabilityLoading;
	}

	/**
	 * Retrieve the Discount Factor Loading Coefficient for the specified Funding Latent State
	 * 
	 * @param fundingLabel The Funding Label
	 * 
	 * @return The Discount Factor Loading Coefficient for the specified Funding Latent State
	 */

	public java.util.Map<java.lang.Integer, java.lang.Double> discountFactorFundingLoading (
		final org.drip.state.identifier.FundingLabel fundingLabel)
	{
		if (null == fundingLabel || !fundingLabel.match (_fundingLabel)) return null;

		java.util.Map<java.lang.Integer, java.lang.Double> mapDiscountFactorLoading = new
			java.util.TreeMap<java.lang.Integer, java.lang.Double>();

		mapDiscountFactorLoading.put (_iPayDate, _dblNotional * _dblSurvival * _dblFX *
			_convAdj.cumulative());

		return mapDiscountFactorLoading;
	}

	/**
	 * Retrieve the FX Loading Coefficient for the specified FX Latent State
	 * 
	 * @param fxLabel The FX Label
	 * 
	 * @return The FX Loading Coefficient for the specified FX Latent State
	 */

	public java.util.Map<java.lang.Integer, java.lang.Double> fxFXLoading (
		final org.drip.state.identifier.FXLabel fxLabel)
	{
		if (null == fxLabel || !fxLabel.match (_fxLabel)) return null;

		java.util.Map<java.lang.Integer, java.lang.Double> mapFXLoading = new
			java.util.TreeMap<java.lang.Integer, java.lang.Double>();

		mapFXLoading.put (_iPayDate, _dblNotional * _dblSurvival * _dblDF * _convAdj.cumulative());

		return mapFXLoading;
	}

	/**
	 * Retrieve the Credit Label
	 * 
	 * @return The Credit Label
	 */

	public org.drip.state.identifier.EntityCDSLabel creditLabel()
	{
		return _creditLabel;
	}

	/**
	 * Retrieve the Funding Label
	 * 
	 * @return The Funding Label
	 */

	public org.drip.state.identifier.FundingLabel fundingLabel()
	{
		return _fundingLabel;
	}

	/**
	 * Retrieve the FX Label
	 * 
	 * @return The FX Label
	 */

	public org.drip.state.identifier.FXLabel fxLabel()
	{
		return _fxLabel;
	}
}
