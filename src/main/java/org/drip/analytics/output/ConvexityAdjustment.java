
package org.drip.analytics.output;

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
 * <i>ConvexityAdjustment</i> holds the dynamical convexity Adjustments between the Latent States.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/README.md">Analytics</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/output/README.md">Output</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class ConvexityAdjustment {
	private double _dblCollateralCredit = 1.;
	private double _dblCollateralForward = 1.;
	private double _dblCollateralFunding = 1.;
	private double _dblCollateralFX = 1.;
	private double _dblCreditForward = 1.;
	private double _dblCreditFunding = 1.;
	private double _dblCreditFX = 1.;
	private double _dblForwardFunding = 1.;
	private double _dblForwardFX = 1.;
	private double _dblFundingFX = 1.;

	/**
	 * Empty ConvexityAdjustment Constructor
	 */

	public ConvexityAdjustment()
	{
	}

	/**
	 * Set the Collateral/Credit Convexity Adjustment
	 * 
	 * @param dblCollateralCredit The Collateral/Credit Convexity Adjustment
	 * 
	 * @return TRUE - The Collateral/Credit Convexity Adjustment successfully set
	 */

	public boolean setCollateralCredit (
		final double dblCollateralCredit)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblCollateralCredit)) return false;

		_dblCollateralCredit = dblCollateralCredit;
		return true;
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
	 * Set the Collateral/Forward Convexity Adjustment
	 * 
	 * @param dblCollateralForward The Collateral/Forward Convexity Adjustment
	 * 
	 * @return TRUE - The Collateral/Forward Convexity Adjustment successfully set
	 */

	public boolean setCollateralForward (
		final double dblCollateralForward)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblCollateralForward)) return false;

		_dblCollateralForward = dblCollateralForward;
		return true;
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
	 * Set the Collateral/Funding Convexity Adjustment
	 * 
	 * @param dblCollateralFunding The Collateral/Funding Convexity Adjustment
	 * 
	 * @return TRUE - The Collateral/Funding Convexity Adjustment successfully set
	 */

	public boolean setCollateralFunding (
		final double dblCollateralFunding)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblCollateralFunding)) return false;

		_dblCollateralFunding = dblCollateralFunding;
		return true;
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
	 * Set the Collateral/FX Convexity Adjustment
	 * 
	 * @param dblCollateralFX The Collateral/FX Convexity Adjustment
	 * 
	 * @return TRUE - The Collateral/FX Convexity Adjustment successfully set
	 */

	public boolean setCollateralFX (
		final double dblCollateralFX)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblCollateralFX)) return false;

		_dblCollateralFX = dblCollateralFX;
		return true;
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
	 * Set the Credit/Forward Convexity Adjustment
	 * 
	 * @param dblCreditForward The Credit/Forward Convexity Adjustment
	 * 
	 * @return TRUE - The Credit/Forward Convexity Adjustment successfully set
	 */

	public boolean setCreditForward (
		final double dblCreditForward)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblCreditForward)) return false;

		_dblCreditForward = dblCreditForward;
		return true;
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
	 * Set the Credit/Funding Convexity Adjustment
	 * 
	 * @param dblCreditFunding The Credit/Funding Convexity Adjustment
	 * 
	 * @return TRUE - The Credit/Funding Convexity Adjustment successfully set
	 */

	public boolean setCreditFunding (
		final double dblCreditFunding)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblCreditFunding)) return false;

		_dblCreditFunding = dblCreditFunding;
		return true;
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
	 * Set the Credit/FX Convexity Adjustment
	 * 
	 * @param dblCreditFX The Credit/FX Convexity Adjustment
	 * 
	 * @return TRUE - The Credit/FX Convexity Adjustment successfully set
	 */

	public boolean setCreditFX (
		final double dblCreditFX)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblCreditFX)) return false;

		_dblCreditFX = dblCreditFX;
		return true;
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
	 * Set the Forward/Funding Convexity Adjustment
	 * 
	 * @param dblForwardFunding The Forward/Funding Convexity Adjustment
	 * 
	 * @return TRUE - The Forward/Funding Convexity Adjustment successfully set
	 */

	public boolean setForwardFunding (
		final double dblForwardFunding)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblForwardFunding)) return false;

		_dblForwardFunding = dblForwardFunding;
		return true;
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
	 * Set the Forward/FX Convexity Adjustment
	 * 
	 * @param dblForwardFX The Forward/FX Convexity Adjustment
	 * 
	 * @return TRUE - The Forward/FX Convexity Adjustment successfully set
	 */

	public boolean setForwardFX (
		final double dblForwardFX)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblForwardFX)) return false;

		_dblForwardFX = dblForwardFX;
		return true;
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
	 * Set the Funding/FX Convexity Adjustment
	 * 
	 * @param dblFundingFX The Funding/FX Convexity Adjustment
	 * 
	 * @return TRUE - The Funding/FX Convexity Adjustment successfully set
	 */

	public boolean setFundingFX (
		final double dblFundingFX)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblFundingFX)) return false;

		_dblFundingFX = dblFundingFX;
		return true;
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
}
