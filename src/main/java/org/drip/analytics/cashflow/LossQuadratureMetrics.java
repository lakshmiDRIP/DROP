
package org.drip.analytics.cashflow;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
 * Copyright (C) 2012 Lakshmi Krishnamurthy
 * Copyright (C) 2011 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting risk, transaction costs, exposure, margin
 *  	calculations, valuation adjustment, and portfolio construction within and across fixed income,
 *  	credit, commodity, equity, FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three modules:
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
 * <i>LossPeriodCurveFactors</i> is an Implementation of the Period Class enhanced by the Loss Period
 * Measures. It exports the following Functionality:
 *
 *	<br><br>
 *  <ul>
 *  	<li>
 * 			Start/End Survival Probabilities
 *  	</li>
 *  	<li>
 * 			Period Effective Notional/Recovery/Discount Factor
 *  	</li>
 *  	<li>
 * 			Serialization into and De-serialization out of Byte Arrays
 *  	</li>
 *  </ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/README.md">Analytics</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/cashflow/README.md">Cash Flow</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class LossQuadratureMetrics {
	private int _iEndDate = java.lang.Integer.MIN_VALUE;
	private int _iStartDate = java.lang.Integer.MIN_VALUE;
	private double _dblAccrualDCF = java.lang.Double.NaN;
	private double _dblEffectiveDF = java.lang.Double.NaN;
	private double _dblEndSurvival = java.lang.Double.NaN;
	private double _dblStartSurvival = java.lang.Double.NaN;
	private double _dblEffectiveNotional = java.lang.Double.NaN;
	private double _dblEffectiveRecovery = java.lang.Double.NaN;

	/**
	 * Create an Instance of the LossPeriodCurveFactors using the Period's Dates and Curves to generate the
	 *  Curve Measures
	 * 
	 * @param iStartDate Period Start Date
	 * @param iEndDate Period End Date
	 * @param dblAccrualDCF Period's Accrual Day Count Fraction
	 * @param dblEffectiveNotional Period's Effective Notional
	 * @param dblEffectiveRecovery Period's Effective Recovery
	 * @param dc Discount Curve
	 * @param cc Credit Curve
	 * @param iDefaultLag Default Pay Lag
	 * 
	 * @return LossPeriodCurveFactors Instance
	 */

	public static final LossQuadratureMetrics MakeDefaultPeriod (
		final int iStartDate,
		final int iEndDate,
		final double dblAccrualDCF,
		final double dblEffectiveNotional,
		final double dblEffectiveRecovery,
		final org.drip.state.discount.MergedDiscountForwardCurve dc,
		final org.drip.state.credit.CreditCurve cc,
		final int iDefaultLag)
	{
		if (
			!org.drip.numerical.common.NumberUtil.IsValid (dblAccrualDCF) ||
			!org.drip.numerical.common.NumberUtil.IsValid (dblEffectiveNotional) ||
			!org.drip.numerical.common.NumberUtil.IsValid (dblEffectiveRecovery) ||
			null == dc ||
			null == cc
		)
			return null;

		try {
			return new LossQuadratureMetrics (
				iStartDate,
				iEndDate,
				cc.survival (iStartDate),
				cc.survival (iEndDate),
				dblAccrualDCF,
				dblEffectiveNotional,
				dblEffectiveRecovery,
				dc.effectiveDF (
					iStartDate + iDefaultLag,
					iEndDate + iDefaultLag
				)
			);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Create a LossPeriodCurveFactors Instance from the Period Dates and the Curve Measures
	 * 
	 * @param iStartDate Period Start Date
	 * @param iEndDate Period End Date
	 * @param dblAccrualDCF Period's Accrual Day Count Fraction
	 * @param dblEffectiveNotional Period's Effective Notional
	 * @param dc Discount Curve
	 * @param cc Credit Curve
	 * @param iDefaultLag Default Pay Lag
	 * 
	 * @return LossPeriodCurveFactors instance
	 */

	public static final LossQuadratureMetrics MakeDefaultPeriod (
		final int iStartDate,
		final int iEndDate,
		final double dblAccrualDCF,
		final double dblEffectiveNotional,
		final org.drip.state.discount.MergedDiscountForwardCurve dc,
		final org.drip.state.credit.CreditCurve cc,
		final int iDefaultLag)
	{
		if (
			!org.drip.numerical.common.NumberUtil.IsValid (dblAccrualDCF) ||
			!org.drip.numerical.common.NumberUtil.IsValid (dblEffectiveNotional) ||
			null == dc ||
			null == cc
		)
			return null;

		try {
			return new LossQuadratureMetrics (
				iStartDate,
				iEndDate,
				cc.survival (iStartDate),
				cc.survival (iEndDate),
				dblAccrualDCF,
				dblEffectiveNotional,
				cc.effectiveRecovery (
					iStartDate + iDefaultLag,
					iEndDate + iDefaultLag
				),
				dc.effectiveDF (
					iStartDate + iDefaultLag,
					iEndDate + iDefaultLag
				)
			);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * LossPeriodCurveFactors Constructor
	 * 
	 * @param iStartDate Period Start Date
	 * @param iEndDate Period End Date
	 * @param dblStartSurvival Period Start Survival
	 * @param dblEndSurvival Period End Survival
	 * @param dblAccrualDCF Period Accrual DCF
	 * @param dblEffectiveNotional Period Effective Notional
	 * @param dblEffectiveRecovery Period Effective Recovery
	 * @param dblEffectiveDF Period Effective Discount Factor
	 * 
	 * @throws java.lang.Exception Thrown if inputs are invalid
	 */

	public LossQuadratureMetrics (
		final int iStartDate,
		final int iEndDate,
		final double dblStartSurvival,
		final double dblEndSurvival,
		final double dblAccrualDCF,
		final double dblEffectiveNotional,
		final double dblEffectiveRecovery,
		final double dblEffectiveDF)
		throws java.lang.Exception
	{
		if (
			!org.drip.numerical.common.NumberUtil.IsValid (_dblStartSurvival = dblStartSurvival) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_dblEndSurvival = dblEndSurvival) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_dblAccrualDCF = dblAccrualDCF) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_dblEffectiveNotional = dblEffectiveNotional) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_dblEffectiveRecovery = dblEffectiveRecovery) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_dblEffectiveDF = dblEffectiveDF)
		)
			throw new java.lang.Exception ("LossPeriodCurveFactors Constructor => Invalid params");

		_iEndDate = iEndDate;
		_iStartDate = iStartDate;
	}

	/**
	 * Period Start Date
	 * 
	 * @return Period Start Date
	 */

	public int startDate()
	{
		return _iStartDate;
	}

	/**
	 * Survival Probability at the Period Beginning
	 * 
	 * @return Survival Probability at the Period Beginning
	 */

	public double startSurvival()
	{
		return _dblStartSurvival;
	}

	/**
	 * Period End Date
	 * 
	 * @return Period End Date
	 */

	public int endDate()
	{
		return _iEndDate;
	}

	/**
	 * Survival at the Period End
	 * 
	 * @return Survival at the Period End
	 */

	public double endSurvival()
	{
		return _dblEndSurvival;
	}

	/**
	 * Get the Period Effective Notional
	 * 
	 * @return Period Effective Notional
	 */

	public double effectiveNotional()
	{
		return _dblEffectiveNotional;
	}

	/**
	 * Get the Period Effective Recovery
	 * 
	 * @return Period Effective Recovery
	 */

	public double effectiveRecovery()
	{
		return _dblEffectiveRecovery;
	}

	/**
	 * Get the Period Effective Discount Factor
	 * 
	 * @return Period Effective Discount Factor
	 */

	public double effectiveDF()
	{
		return _dblEffectiveDF;
	}

	/**
	 * Get the Period Accrual Day Count Fraction
	 * 
	 * @return Period Accrual Day Count Fraction
	 */

	public double accrualDCF()
	{
		return _dblAccrualDCF;
	}
}
