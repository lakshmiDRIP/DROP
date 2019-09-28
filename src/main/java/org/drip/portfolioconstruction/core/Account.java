
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
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AssetAllocationAnalyticsLibrary.md">Asset Allocation Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction">Portfolio Construction</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/core">Core</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class Account extends org.drip.portfolioconstruction.core.Block
{
	private org.drip.portfolioconstruction.composite.Holdings _holdings = null;
	private org.drip.portfolioconstruction.composite.AlphaGroup _alphaGroup = null;
	private org.drip.portfolioconstruction.composite.Benchmark _trackingBenchmark = null;
	private org.drip.portfolioconstruction.composite.Benchmark _objectiveBenchmark = null;
	private org.drip.portfolioconstruction.core.TaxAccountingScheme _taxAccountingScheme = null;
	private org.drip.portfolioconstruction.risk.AssetCovariance _assetCovarianceRiskModel = null;
	private org.drip.portfolioconstruction.risk.AlphaUncertaintyGroup _alphaUncertaintyGroup = null;
	private org.drip.portfolioconstruction.composite.TransactionChargeGroup _transactionChargeGroup = null;

	/**
	 * Account Constructor
	 * 
	 * @param name The Account Name
	 * @param id The Account ID
	 * @param description The Account Description
	 * @param holdings The Account Holdings
	 * @param taxAccountingScheme The Tax Accounting Scheme
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public Account (
		final java.lang.String name,
		final java.lang.String id,
		final java.lang.String description,
		final org.drip.portfolioconstruction.composite.Holdings holdings,
		final org.drip.portfolioconstruction.core.TaxAccountingScheme taxAccountingScheme)
		throws java.lang.Exception
	{
		super (
			name,
			id,
			description
		);

		_holdings = holdings;
		_taxAccountingScheme = taxAccountingScheme;
	}

	/**
	 * Retrieve the Holdings
	 * 
	 * @return The Holdings
	 */

	public org.drip.portfolioconstruction.composite.Holdings holdings()
	{
		return _holdings;
	}

	/**
	 * Retrieve the Currency
	 * 
	 * @return The Currency
	 */

	public java.lang.String currency()
	{
		return _holdings.currency();
	}

	/**
	 * Retrieve the Tracking Benchmark Instance
	 * 
	 * @return The Tracking Benchmark Instance
	 */

	public org.drip.portfolioconstruction.composite.Benchmark trackingBenchmark()
	{
		return _trackingBenchmark;
	}

	/**
	 * Retrieve the Objective Benchmark Instance
	 * 
	 * @return The Objective Benchmark Instance
	 */

	public org.drip.portfolioconstruction.composite.Benchmark objectiveBenchmark()
	{
		return _objectiveBenchmark;
	}

	/**
	 * Retrieve the Alpha Group Instance
	 * 
	 * @return The Alpha Group Instance
	 */

	public org.drip.portfolioconstruction.composite.AlphaGroup alphaGroup()
	{
		return _alphaGroup;
	}

	/**
	 * Retrieve the Alpha Uncertainty Group Instance
	 * 
	 * @return The Alpha Uncertainty Group Instance
	 */

	public org.drip.portfolioconstruction.risk.AlphaUncertaintyGroup alphaUncertaintyGroup()
	{
		return _alphaUncertaintyGroup;
	}

	/**
	 * Retrieve the Asset Co-variance Risk Model
	 * 
	 * @return The Asset Co-variance Risk Model
	 */

	public org.drip.portfolioconstruction.risk.AssetCovariance assetCovariance()
	{
		return _assetCovarianceRiskModel;
	}

	/**
	 * Retrieve the Transaction Cost Group Instance
	 * 
	 * @return The Transaction Cost Group Instance
	 */

	public org.drip.portfolioconstruction.composite.TransactionChargeGroup transactionChargeGroup()
	{
		return _transactionChargeGroup;
	}

	/**
	 * Retrieve the Tax Accounting Scheme
	 * 
	 * @return The Tax Accounting Scheme
	 */

	public org.drip.portfolioconstruction.core.TaxAccountingScheme taxAccountingScheme()
	{
		return _taxAccountingScheme;
	}

	/**
	 * Set the Tracking Benchmark Instance
	 * 
	 * @param trackingBenchmark The Tracking Benchmark
	 * 
	 * @return The Tracking Benchmark successfully set
	 */

	public boolean setTrackingBenchmark (
		final org.drip.portfolioconstruction.composite.Benchmark trackingBenchmark)
	{
		if (null == trackingBenchmark)
		{
			return false;
		}

		_trackingBenchmark = trackingBenchmark;
		return true;
	}

	/**
	 * Set the Objective Benchmark Instance
	 * 
	 * @param objectiveBenchmark The Objective Benchmark
	 * 
	 * @return The Objective Benchmark successfully set
	 */

	public boolean setObjectiveBenchmark (
		final org.drip.portfolioconstruction.composite.Benchmark objectiveBenchmark)
	{
		if (null == objectiveBenchmark)
		{
			return false;
		}

		_objectiveBenchmark = objectiveBenchmark;
		return true;
	}

	/**
	 * Set the Alpha Group
	 * 
	 * @param alphaGroup The Alpha Group Instance
	 * 
	 * @return The Alpha Group successfully set
	 */

	public boolean setAlphaGroup (
		final org.drip.portfolioconstruction.composite.AlphaGroup alphaGroup)
	{
		if (null == alphaGroup)
		{
			return false;
		}

		_alphaGroup = alphaGroup;
		return true;
	}

	/**
	 * Set the Alpha Uncertainty Group
	 * 
	 * @param alphaUncertaintyGroup The Alpha Uncertainty Group Instance
	 * 
	 * @return The Alpha Uncertainty Group successfully set
	 */

	public boolean setAlphaUncertaintyGroup (
		final org.drip.portfolioconstruction.risk.AlphaUncertaintyGroup alphaUncertaintyGroup)
	{
		if (null == alphaUncertaintyGroup)
		{
			return false;
		}

		_alphaUncertaintyGroup = alphaUncertaintyGroup;
		return true;
	}

	/**
	 * Set the Asset Co-variance Risk Model
	 * 
	 * @param assetCovarianceRiskModel The Asset Co-variance Risk Model
	 * 
	 * @return The Asset Co-variance Risk Model
	 */

	public boolean setAssetCovariance (
		final org.drip.portfolioconstruction.risk.AssetCovariance assetCovarianceRiskModel)
	{
		if (null == assetCovarianceRiskModel)
		{
			return false;
		}

		_assetCovarianceRiskModel = assetCovarianceRiskModel;
		return true;
	}

	/**
	 * Set the Transaction Cost Group
	 * 
	 * @param transactionChargeGroup The Transaction Cost Group Instance
	 * 
	 * @return The Transaction Cost Group successfully set
	 */

	public boolean setTransactionCostGroup (
		final org.drip.portfolioconstruction.composite.TransactionChargeGroup transactionChargeGroup)
	{
		if (null == transactionChargeGroup)
		{
			return false;
		}

		_transactionChargeGroup = transactionChargeGroup;
		return true;
	}
}
