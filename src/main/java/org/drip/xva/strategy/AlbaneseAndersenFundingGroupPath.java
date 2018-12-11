
package org.drip.xva.strategy;

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
 * <i>AlbaneseAndersenFundingGroupPath</i> rolls up the Path Realizations of the Sequence in a Single Path
 * Projection Run over Multiple Collateral Groups onto a Single Funding Group in accordance with the Albanese
 * Andersen (2014) Scheme. The References are:
 *
 *  <br><br>
 *  <ul>
 *  	<li>
 *  		Albanese, C., and L. Andersen (2014): Accounting for OTC Derivatives: Funding Adjustments and the
 *  			Re-Hypothecation Option https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2482955
 *  			<b>eSSRN</b>
 *  	</li>
 *  	<li>
 *  		Burgard, C., and M. Kjaer (2014): PDE Representations of Derivatives with Bilateral Counter-party
 *  			Risk and Funding Costs <i>Journal of Credit Risk</i> <b>7 (3)</b> 1-19
 *  	</li>
 *  	<li>
 *  		Burgard, C., and M. Kjaer (2014): In the Balance <i>Risk</i> <b>24 (11)</b> 72-75
 *  	</li>
 *  	<li>
 *  		Gregory, J. (2009): Being Two-faced over Counter-party Credit Risk <i>Risk</i> <b>20 (2)</b>
 *  			86-90
 *  	</li>
 *  	<li>
 *  		Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives Pricing
 *  			<i>Risk</i> <b>21 (2)</b> 97-102
 *  	</li>
 *  </ul>
 *
 *  <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/XVAAnalyticsLibrary.md">XVA Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva">XVA</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/strategy">Strategy</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class AlbaneseAndersenFundingGroupPath extends org.drip.xva.netting.FundingGroupPath
{

	/**
	 * Generate a "Mono" AlbaneseAndersenFundingGroupPath Instance
	 * 
	 * @param creditDebtGroupPath The "Mono" Credit Debt Group Path
	 * @param marketPath The Market Path
	 * 
	 * @return The "Mono" AlbaneseAndersenFundingGroupPath Instance
	 */

	public static final AlbaneseAndersenFundingGroupPath Mono (
		final org.drip.xva.netting.CreditDebtGroupPath creditDebtGroupPath,
		final org.drip.exposure.universe.MarketPath marketPath)
	{
		try
		{
			return new org.drip.xva.strategy.AlbaneseAndersenFundingGroupPath (
				new org.drip.xva.netting.CreditDebtGroupPath[]
				{
					creditDebtGroupPath
				},
				marketPath
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * AlbaneseAndersenFundingGroupPath Constructor
	 * 
	 * @param creditDebtGroupPathArray Array of the Credit Debt Group Trajectory Paths
	 * @param marketPath The Market Path
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public AlbaneseAndersenFundingGroupPath (
		final org.drip.xva.netting.CreditDebtGroupPath[] creditDebtGroupPathArray,
		final org.drip.exposure.universe.MarketPath marketPath)
		throws java.lang.Exception
	{
		super (
			creditDebtGroupPathArray,
			marketPath
		);
	}

	@Override public double fundingValueAdjustment()
	{
		return unilateralFundingValueAdjustment();
	}

	@Override public double fundingDebtAdjustment()
	{
		return unilateralFundingDebtAdjustment();
	}

	@Override public double fundingCostAdjustment()
	{
		return unilateralFundingValueAdjustment();
	}

	@Override public double fundingBenefitAdjustment()
	{
		return unilateralFundingDebtAdjustment();
	}

	@Override public double[] periodFundingValueAdjustment()
	{
		return periodBilateralFundingValueAdjustment();
	}

	@Override public double[] periodFundingDebtAdjustment()
	{
		return periodBilateralFundingDebtAdjustment();
	}

	@Override public double[] periodFundingCostAdjustment()
	{
		return periodUnilateralFundingValueAdjustment();
	}

	@Override public double[] periodFundingBenefitAdjustment()
	{
		return periodUnilateralFundingDebtAdjustment();
	}
}
