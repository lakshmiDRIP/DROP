
package org.drip.historical.attribution;

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
 * <i>CDSMarketSnap</i> contains the Metrics Snapshot associated with the relevant Manifest Measures for the given
 * Credit Default Swap Position.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationSupportLibrary.md">Computation Support</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/historical/README.md">Historical State Processing Utilities</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/historical/attribution/README.md">Position Market Change Components Attribution</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class CDSMarketSnap extends org.drip.historical.attribution.PositionMarketSnap {

	/**
	 * CDSMarketSnap Constructor
	 * 
	 * @param dtSnap The Snapshot Date
	 * @param dblMarketValue The Snapshot Market Value
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public CDSMarketSnap (
		final org.drip.analytics.date.JulianDate dtSnap,
		final double dblMarketValue)
		throws java.lang.Exception
	{
		super (dtSnap, dblMarketValue);
	}

	/**
	 * Set the Fair Premium and Position Sensitivity
	 * 
	 * @param dblFairPremium The Fair Premium
	 * @param dblFairPremiumSensitivity The Position Fair Premium Sensitivity
	 * @param dblFairPremiumRollDown The Position Fair Premium Roll Down
	 * 
	 * @return TRUE - The Fair Premium and the Position Sensitivity successfully set
	 */

	public boolean setFairPremiumMarketFactor (
		final double dblFairPremium,
		final double dblFairPremiumSensitivity,
		final double dblFairPremiumRollDown)
	{
		return addManifestMeasureSnap ("FairPremium", dblFairPremium, dblFairPremiumSensitivity,
			dblFairPremiumRollDown);
	}

	/**
	 * Set the Effective Date
	 * 
	 * @param dtEffective The Effective Date
	 * 
	 * @return TRUE - The Effective Date successfully set
	 */

	public boolean setEffectiveDate (
		final org.drip.analytics.date.JulianDate dtEffective)
	{
		return setDate ("EffectiveDate", dtEffective);
	}

	/**
	 * Retrieve the Effective Date
	 * 
	 * @return The Effective Date
	 */

	public org.drip.analytics.date.JulianDate effectiveDate()
	{
		return date ("EffectiveDate");
	}

	/**
	 * Set the Maturity Date
	 * 
	 * @param dtMaturity The Maturity Date
	 * 
	 * @return TRUE - The Maturity Date successfully set
	 */

	public boolean setMaturityDate (
		final org.drip.analytics.date.JulianDate dtMaturity)
	{
		return setDate ("MaturityDate", dtMaturity);
	}

	/**
	 * Retrieve the Maturity Date
	 * 
	 * @return The Maturity Date
	 */

	public org.drip.analytics.date.JulianDate maturityDate()
	{
		return date ("MaturityDate");
	}

	/**
	 * Set the Initial Fair Premium
	 * 
	 * @param dblInitialFairPremium The Initial Fair Premium
	 * 
	 * @return TRUE - The Initial Fair Premium Successfully set
	 */

	public boolean setInitialFairPremium (
		final double dblInitialFairPremium)
	{
		return setR1 ("InitialFairPremium", dblInitialFairPremium);
	}

	/**
	 * Retrieve the Initial Fair Premium
	 * 
	 * @return The Initial Fair Premium
	 * 
	 * @throws java.lang.Exception Thrown if the Initial Fair Premium cannot be obtained
	 */

	public double initialFairPremium()
		throws java.lang.Exception
	{
		return r1 ("InitialFairPremium");
	}

	/**
	 * Set the Current Fair Premium
	 * 
	 * @param dblCurrentFairPremium The Current Fair Premium
	 * 
	 * @return TRUE - The Current Fair Premium Successfully Set
	 */

	public boolean setCurrentFairPremium (
		final double dblCurrentFairPremium)
	{
		return setR1 ("CurrentFairPremium", dblCurrentFairPremium);
	}

	/**
	 * Retrieve the Current Fair Premium
	 * 
	 * @return The Current Fair Premium
	 * 
	 * @throws java.lang.Exception Thrown if the Current Fair Premium cannot be obtained
	 */

	public double currentFairPremium()
		throws java.lang.Exception
	{
		return r1 ("CurrentFairPremium");
	}

	/**
	 * Set the Fixed Coupon
	 * 
	 * @param dblFixedCoupon The Fixed Coupon
	 * 
	 * @return TRUE - The Fixed Coupon Successfully Set
	 */

	public boolean setFixedCoupon (
		final double dblFixedCoupon)
	{
		return setR1 ("FixedCoupon", dblFixedCoupon);
	}

	/**
	 * Retrieve the Fixed Coupon
	 * 
	 * @return The Fixed Coupon
	 * 
	 * @throws java.lang.Exception Thrown if the Fixed Coupon cannot be obtained
	 */

	public double fixedCoupon()
		throws java.lang.Exception
	{
		return r1 ("FixedCoupon");
	}

	/**
	 * Set the Clean DV01
	 * 
	 * @param dblCleanDV01 The Clean DV01
	 * 
	 * @return TRUE - The Clean DV01 Successfully Set
	 */

	public boolean setCleanDV01 (
		final double dblCleanDV01)
	{
		return setR1 ("CleanDV01", dblCleanDV01);
	}

	/**
	 * Retrieve the Clean DV01
	 * 
	 * @return The Clean DV01
	 * 
	 * @throws java.lang.Exception Thrown if the Clean DV01 cannot be obtained
	 */

	public double cleanDV01()
		throws java.lang.Exception
	{
		return r1 ("CleanDV01");
	}

	/**
	 * Set the Roll Down Fair Premium
	 * 
	 * @param dblRollDownFairPremium The Roll Down Fair Premium
	 * 
	 * @return TRUE - The Roll Down Fair Premium Successfully Set
	 */

	public boolean setRollDownFairPremium (
		final double dblRollDownFairPremium)
	{
		return setR1 ("RollDownFairPremium", dblRollDownFairPremium);
	}

	/**
	 * Retrieve the Roll Down Fair Premium
	 * 
	 * @return The Roll Down Fair Premium
	 * 
	 * @throws java.lang.Exception Thrown if the Roll Down Fair Premium cannot be obtained
	 */

	public double rollDownFairPremium()
		throws java.lang.Exception
	{
		return r1 ("RollDownFairPremium");
	}

	/**
	 * Set the Accrued
	 * 
	 * @param dblAccrued The Accrued
	 * 
	 * @return TRUE - The Accrued successfully set
	 */

	public boolean setAccrued (
		final double dblAccrued)
	{
		return setR1 ("Accrued", dblAccrued);
	}

	/**
	 * Retrieve the Accrued
	 * 
	 * @return The Accrued
	 * 
	 * @throws java.lang.Exception Thrown if the Accrued cannot be obtained
	 */

	public double accrued()
		throws java.lang.Exception
	{
		return r1 ("Accrued");
	}

	/**
	 * Set the Cumulative Coupon Amount
	 * 
	 * @param dblCumulativeCouponAmount The Cumulative Coupon Amount
	 * 
	 * @return TRUE - The Cumulative Coupon Amount successfully set
	 */

	public boolean setCumulativeCouponAmount (
		final double dblCumulativeCouponAmount)
	{
		return setR1 ("CumulativeCouponAmount", dblCumulativeCouponAmount);
	}

	/**
	 * Retrieve the Cumulative Coupon Amount
	 * 
	 * @return The Cumulative Coupon Amount
	 * 
	 * @throws java.lang.Exception Thrown if the Cumulative Coupon Amount cannot be obtained
	 */

	public double cumulativeCouponAmount()
		throws java.lang.Exception
	{
		return r1 ("CumulativeCouponAmount");
	}

	/**
	 * Set the Credit Label
	 * 
	 * @param strCreditLabel Credit Label
	 * 
	 * @return TRUE - The Credit Label successfully set
	 */

	public boolean setCreditLabel (
		final java.lang.String strCreditLabel)
	{
		return setC1 ("CreditLabel", strCreditLabel);
	}

	/**
	 * Retrieve the Credit Label
	 * 
	 * @return The Credit Label
	 */

	public java.lang.String creditLabel()
	{
		return c1 ("CreditLabel");
	}

	/**
	 * Set the Recovery Rate
	 * 
	 * @param dblRecoveryRate The Recovery Rate
	 * 
	 * @return TRUE - The Recovery Rate successfully set
	 */

	public boolean setRecoveryRate (
		final double dblRecoveryRate)
	{
		return setR1 ("RecoveryRate", dblRecoveryRate);
	}

	/**
	 * Retrieve the Recovery Rate
	 * 
	 * @return The Recovery Rate
	 * 
	 * @throws java.lang.Exception Thrown if the Recovery Rate cannot be obtained
	 */

	public double recoveryRate()
		throws java.lang.Exception
	{
		return r1 ("RecoveryRate");
	}

	/**
	 * Set the Coupon PV
	 * 
	 * @param dblCouponPV The Coupon PV
	 * 
	 * @return TRUE - The Coupon PV successfully set
	 */

	public boolean setCouponPV (
		final double dblCouponPV)
	{
		return setR1 ("CouponPV", dblCouponPV);
	}

	/**
	 * Retrieve the Coupon PV
	 * 
	 * @return The Coupon PV
	 * 
	 * @throws java.lang.Exception Thrown if the Coupon PV cannot be obtained
	 */

	public double couponPV()
		throws java.lang.Exception
	{
		return r1 ("CouponPV");
	}

	/**
	 * Set the Loss PV
	 * 
	 * @param dblLossPV The Loss PV
	 * 
	 * @return TRUE - The Loss PV successfully set
	 */

	public boolean setLossPV (
		final double dblLossPV)
	{
		return setR1 ("LossPV", dblLossPV);
	}

	/**
	 * Retrieve the Loss PV
	 * 
	 * @return The Loss PV
	 * 
	 * @throws java.lang.Exception Thrown if the Loss PV cannot be obtained
	 */

	public double lossPV()
		throws java.lang.Exception
	{
		return r1 ("LossPV");
	}
}
