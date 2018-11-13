
package org.drip.portfolioconstruction.core;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
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
 * <i>Account</i> holds the Current Portfolio (if any) along with the Creation/Maintenance Mandate.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module</b>        = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction">Portfolio Construction</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/core">Core/a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/AssetAllocation">Asset Allocation Library</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class Account extends org.drip.portfolioconstruction.core.Block {
	private org.drip.portfolioconstruction.composite.Holdings _ah = null;
	private org.drip.portfolioconstruction.composite.AlphaGroup _ag = null;
	private org.drip.portfolioconstruction.core.TaxAccountingScheme _tas = null;
	private org.drip.portfolioconstruction.composite.Benchmark _bmTracking = null;
	private org.drip.portfolioconstruction.risk.AlphaUncertaintyGroup _aug = null;
	private org.drip.portfolioconstruction.composite.Benchmark _bmObjective = null;
	private org.drip.portfolioconstruction.risk.AssetCovariance _acRiskModel = null;
	private org.drip.portfolioconstruction.composite.TransactionChargeGroup _tcg = null;

	/**
	 * Account Constructor
	 * 
	 * @param strName The Account Name
	 * @param strID The Account ID
	 * @param strDescription The Account Description
	 * @param ah The Account Holdings
	 * @param tas The Tax Accounting Scheme
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public Account (
		final java.lang.String strName,
		final java.lang.String strID,
		final java.lang.String strDescription,
		final org.drip.portfolioconstruction.composite.Holdings ah,
		final org.drip.portfolioconstruction.core.TaxAccountingScheme tas)
		throws java.lang.Exception
	{
		super (strName, strID, strDescription);

		_ah = ah;
		_tas = tas;
	}

	/**
	 * Retrieve the Holdings
	 * 
	 * @return The Holdings
	 */

	public org.drip.portfolioconstruction.composite.Holdings holdings()
	{
		return _ah;
	}

	/**
	 * Retrieve the Currency
	 * 
	 * @return The Currency
	 */

	public java.lang.String currency()
	{
		return _ah.currency();
	}

	/**
	 * Retrieve the Tracking Benchmark Instance
	 * 
	 * @return The Tracking Benchmark Instance
	 */

	public org.drip.portfolioconstruction.composite.Benchmark trackingBenchmark()
	{
		return _bmTracking;
	}

	/**
	 * Retrieve the Objective Benchmark Instance
	 * 
	 * @return The Objective Benchmark Instance
	 */

	public org.drip.portfolioconstruction.composite.Benchmark objectiveBenchmark()
	{
		return _bmObjective;
	}

	/**
	 * Retrieve the Alpha Group Instance
	 * 
	 * @return The Alpha Group Instance
	 */

	public org.drip.portfolioconstruction.composite.AlphaGroup alphaGroup()
	{
		return _ag;
	}

	/**
	 * Retrieve the Alpha Uncertainty Group Instance
	 * 
	 * @return The Alpha Uncertainty Group Instance
	 */

	public org.drip.portfolioconstruction.risk.AlphaUncertaintyGroup alphaUncertaintyGroup()
	{
		return _aug;
	}

	/**
	 * Retrieve the Risk Model
	 * 
	 * @return The Risk Model
	 */

	public org.drip.portfolioconstruction.risk.AssetCovariance riskModel()
	{
		return _acRiskModel;
	}

	/**
	 * Retrieve the Transaction Cost Group Instance
	 * 
	 * @return The Transaction Cost Group Instance
	 */

	public org.drip.portfolioconstruction.composite.TransactionChargeGroup transactionCostGroup()
	{
		return _tcg;
	}

	/**
	 * Retrieve the Tax Accounting Scheme
	 * 
	 * @return The Tax Accounting Scheme
	 */

	public org.drip.portfolioconstruction.core.TaxAccountingScheme taxAccountingScheme()
	{
		return _tas;
	}

	/**
	 * Set the Tracking Benchmark Instance
	 * 
	 * @param bmTracking The Tracking Benchmark
	 * 
	 * @return The Tracking Benchmark successfully set
	 */

	public boolean setTrackingBenchmark (
		final org.drip.portfolioconstruction.composite.Benchmark bmTracking)
	{
		if (null == bmTracking) return false;

		_bmTracking = bmTracking;
		return true;
	}

	/**
	 * Set the Objective Benchmark Instance
	 * 
	 * @param bmObjective The Objective Benchmark
	 * 
	 * @return The Objective Benchmark successfully set
	 */

	public boolean setObjectiveBenchmark (
		final org.drip.portfolioconstruction.composite.Benchmark bmObjective)
	{
		if (null == bmObjective) return false;

		_bmObjective = bmObjective;
		return true;
	}

	/**
	 * Set the Alpha Group
	 * 
	 * @param ag The Alpha Group Instance
	 * 
	 * @return The Alpha Group successfully set
	 */

	public boolean setAlphaGroup (
		final org.drip.portfolioconstruction.composite.AlphaGroup ag)
	{
		if (null == ag) return false;

		_ag = ag;
		return true;
	}

	/**
	 * Set the Alpha Uncertainty Group
	 * 
	 * @param aug The Alpha Uncertainty Group Instance
	 * 
	 * @return The Alpha Uncertainty Group successfully set
	 */

	public boolean setAlphaUncertaintyGroup (
		final org.drip.portfolioconstruction.risk.AlphaUncertaintyGroup aug)
	{
		if (null == aug) return false;

		_aug = aug;
		return true;
	}

	/**
	 * Set the Risk Model
	 * 
	 * @param acRiskModel The Risk Model
	 * 
	 * @return The Risk Model
	 */

	public boolean setRiskModel (
		final org.drip.portfolioconstruction.risk.AssetCovariance acRiskModel)
	{
		if (null == acRiskModel) return false;

		_acRiskModel = acRiskModel;
		return true;
	}

	/**
	 * Set the Transaction Cost Group
	 * 
	 * @param tcg The Transaction Cost Group Instance
	 * 
	 * @return The Transaction Cost Group successfully set
	 */

	public boolean setTransactionCostGroup (
		final org.drip.portfolioconstruction.composite.TransactionChargeGroup tcg)
	{
		if (null == tcg) return false;

		_tcg = tcg;
		return true;
	}
}
