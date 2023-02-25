
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
 * <i>CompositePeriodCouponMetrics</i> holds the results of the compounded Composed period Full Coupon
 * Metrics Estimate Output.
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

public class CompositePeriodCouponMetrics {
	private double _dblDCF = 0.;
	private double _dblRate = 0.;
	private double _dblCreditFX = 0.;
	private double _dblForwardFX = 0.;
	private double _dblFundingFX = 0.;
	private double _dblCollateralFX = 0.;
	private double _dblCreditForward = 0.;
	private double _dblCreditFunding = 0.;
	private double _dblForwardFunding = 0.;
	private double _dblCollateralCredit = 0.;
	private double _dblCollateralForward = 0.;
	private double _dblCollateralFunding = 0.;
	private java.util.List<org.drip.analytics.output.UnitPeriodMetrics> _lsUPM = null;

	/**
	 * CompositePeriodCouponMetrics Instance from the list of the composite period metrics
	 * 
	 * @param lsUPM List of Unit Period Metrics
	 * 
	 * @return Instance of CompositePeriodCouponMetrics
	 */

	public static final CompositePeriodCouponMetrics Create (
		final java.util.List<org.drip.analytics.output.UnitPeriodMetrics> lsUPM)
	{
		try {
			CompositePeriodCouponMetrics cpm = new CompositePeriodCouponMetrics (lsUPM);

			return cpm.initialize() ? cpm : null;
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	protected CompositePeriodCouponMetrics (
		final java.util.List<org.drip.analytics.output.UnitPeriodMetrics> lsUPM)
		throws java.lang.Exception
	{
		if (null == (_lsUPM = lsUPM) || 0 == _lsUPM.size())
			throw new java.lang.Exception ("CompositePeriodCouponMetrics ctr: Invalid Inputs");
	}

	protected boolean initialize()
	{
		double dblAmount = 0.;

		for (org.drip.analytics.output.UnitPeriodMetrics upm : _lsUPM) {
			org.drip.analytics.output.ConvexityAdjustment convAdj = upm.convAdj();

			double dblUnitRate = upm.rate();

			double dblUnitDCF = upm.dcf();

			double dblUnitAmount = dblUnitDCF * dblUnitRate;
			dblAmount += dblUnitAmount;
			_dblDCF += dblUnitDCF;

			_dblCollateralCredit += convAdj.collateralCredit() * dblUnitDCF;

			_dblCollateralForward += convAdj.collateralForward() * dblUnitDCF;

			_dblCollateralFunding += convAdj.collateralFunding() * dblUnitDCF;

			_dblCollateralFX += convAdj.collateralFX() * dblUnitDCF;

			_dblCreditForward += convAdj.creditForward() * dblUnitDCF;

			_dblCreditFunding += convAdj.creditFunding() * dblUnitDCF;

			_dblCreditFX += convAdj.creditFX() * dblUnitDCF;

			_dblForwardFunding += convAdj.forwardFunding() * dblUnitDCF;

			_dblForwardFX += convAdj.forwardFX() * dblUnitDCF;

			_dblFundingFX += convAdj.fundingFX() * dblUnitDCF;
		}

		_dblCollateralCredit /= _dblDCF;
		_dblCollateralForward /= _dblDCF;
		_dblCollateralFunding /= _dblDCF;
		_dblCollateralFX /= _dblDCF;
		_dblCreditForward /= _dblDCF;
		_dblCreditFunding /= _dblDCF;
		_dblCreditFX /= _dblDCF;
		_dblForwardFunding /= _dblDCF;
		_dblForwardFX /= _dblDCF;
		_dblFundingFX /= _dblDCF;
		_dblRate = dblAmount / _dblDCF;
		return true;
	}

	/**
	 * Retrieve the Composite DCF
	 * 
	 * @return The Composite DCF
	 */

	public double dcf()
	{
		return _dblDCF;
	}

	/**
	 * Retrieve the Composite Rate
	 * 
	 * @return The Composite Rate
	 */

	public double rate()
	{
		return _dblRate;
	}

	/**
	 * Retrieve the Collateral/Credit Convexity Adjustment
	 * 
	 * @return The Collateral/Credit Convexity Adjustment
	 */

	public double collateralCredit()
	{
		return _dblCollateralCredit;
	}

	/**
	 * Retrieve the Collateral/Forward Convexity Adjustment
	 * 
	 * @return The Collateral/Forward Convexity Adjustment
	 */

	public double collateralForward()
	{
		return _dblCollateralForward;
	}

	/**
	 * Retrieve the Collateral/Funding Convexity Adjustment
	 * 
	 * @return The Collateral/Funding Convexity Adjustment
	 */

	public double collateralFunding()
	{
		return _dblCollateralFunding;
	}

	/**
	 * Retrieve the Collateral/FX Convexity Adjustment
	 * 
	 * @return The Collateral/FX Convexity Adjustment
	 */

	public double collateralFX()
	{
		return _dblCollateralFX;
	}

	/**
	 * Retrieve the Credit/Forward Convexity Adjustment
	 * 
	 * @return The Credit/Forward Convexity Adjustment
	 */

	public double creditForward()
	{
		return _dblCreditForward;
	}

	/**
	 * Retrieve the Credit/Funding Convexity Adjustment
	 * 
	 * @return The Credit/Funding Convexity Adjustment
	 */

	public double creditFunding()
	{
		return _dblCreditFunding;
	}

	/**
	 * Retrieve the Credit/FX Convexity Adjustment
	 * 
	 * @return The Credit/FX Convexity Adjustment
	 */

	public double creditFX()
	{
		return _dblCreditFX;
	}

	/**
	 * Retrieve the Forward/Funding Convexity Adjustment
	 * 
	 * @return The Forward/Funding Convexity Adjustment
	 */

	public double forwardFunding()
	{
		return _dblForwardFunding;
	}

	/**
	 * Retrieve the Forward/FX Convexity Adjustment
	 * 
	 * @return The Forward/FX Convexity Adjustment
	 */

	public double forwardFX()
	{
		return _dblForwardFX;
	}

	/**
	 * Retrieve the Funding/FX Convexity Adjustment
	 * 
	 * @return The Funding/FX Convexity Adjustment
	 */

	public double fundingFX()
	{
		return _dblFundingFX;
	}

	/**
	 * Retrieve the Compounding Convexity Correction
	 * 
	 * @return The Compounding Convexity Correction
	 */

	public double compounding()
	{
		return _dblCollateralForward * _dblCreditForward * _dblForwardFunding * _dblForwardFX;
	}

	/**
	 * Retrieve the Cumulative Convexity Correction
	 * 
	 * @return The Cumulative Convexity Correction
	 */

	public double cumulative()
	{
		return _dblCollateralCredit * _dblCollateralForward * _dblCollateralFunding * _dblCollateralFX *
			_dblCreditForward * _dblCreditFunding * _dblCreditFX * _dblForwardFunding * _dblForwardFX *
				_dblFundingFX;
	}

	/**
	 * Retrieve the List of the Unit Period Metrics
	 * 
	 * @return The List of the Unit Period Metrics
	 */

	public java.util.List<org.drip.analytics.output.UnitPeriodMetrics> unitMetrics()
	{
		return _lsUPM;
	}
}
