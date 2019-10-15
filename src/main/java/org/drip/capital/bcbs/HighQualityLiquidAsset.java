
package org.drip.capital.bcbs;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
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
 * 	- Statistical Learning
 * 	- Numerical Optimizer
 * 	- Spline Builder
 * 	- Algorithm Support
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
 * <i>HighQualityLiquidAsset</i> contains the Amounts and the Settings associated with Levels 1, 2A, and 2B.
 * The References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Basel Committee on Banking Supervision (2017): Basel III Leverage Ratio Framework and Disclosure
 * 				Requirements https://www.bis.org/publ/bcbs270.pdf
 * 		</li>
 * 		<li>
 * 			Central Banking (2013): Fed and FDIC agree 6% Leverage Ratio for US SIFIs
 * 				https://www.centralbanking.com/central-banking/news/2280726/fed-and-fdic-agree-6-leverage-ratio-for-us-sifis
 * 		</li>
 * 		<li>
 * 			European Banking Agency (2013): Implementing Basel III in Europe: CRD IV Package
 * 				https://eba.europa.eu/regulation-and-policy/implementing-basel-iii-europe
 * 		</li>
 * 		<li>
 * 			Federal Reserve (2013): Liquidity Coverage Ratio – Liquidity Risk Measurements, Standards, and
 * 				Monitoring
 * 				https://web.archive.org/web/20131102074614/http:/www.federalreserve.gov/FR_notice_lcr_20131024.pdf
 * 		</li>
 * 		<li>
 * 			Wikipedia (2018): Basel III https://en.wikipedia.org/wiki/Basel_III
 * 		</li>
 * 	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ExposureAnalyticsLibrary.md">Exposure Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/bcbs/README.md">BCBS</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/bcbs/core/README.md">Core</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class HighQualityLiquidAsset
{
	private double _level1 = java.lang.Double.NaN;
	private double _level2A = java.lang.Double.NaN;
	private double _level2B = java.lang.Double.NaN;

	/**
	 * HighQualityLiquidAsset Constructor
	 * 
	 * @param level1 Level 1 HQLA
	 * @param level2A Level 2A HQLA
	 * @param level2B Level 2B HQLA
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public HighQualityLiquidAsset (
		final double level1,
		final double level2A,
		final double level2B)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (_level1 = level1) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_level2A = level2A) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_level2B = level2B))
		{
			throw new java.lang.Exception ("HighQualityLiquidAsset Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Amount of Level 1 Assets
	 * 
	 * @return The Amount of Level 1 Assets
	 */

	public double level1()
	{
		return _level1;
	}

	/**
	 * Retrieve the Amount of Level 2A Assets
	 * 
	 * @return The Amount of Level 2A Assets
	 */

	public double level2A()
	{
		return _level2A;
	}

	/**
	 * Retrieve the Amount of Level 2B Assets
	 * 
	 * @return The Amount of Level 2B Assets
	 */

	public double level2B()
	{
		return _level2B;
	}

	/**
	 * Retrieve the Total HQLA
	 * 
	 * @return The Total HQLA
	 */

	public double total()
	{
		return _level1 + _level2A + _level2B;
	}

	/**
	 * Retrieve the Level 2 Shares to the Total HQLA
	 * 
	 * @return The Level 2 Shares to the Total HQLA
	 */

	public double level2Ratio()
	{
		return (_level2A + _level2B) / (_level1 + _level2A + _level2B);
	}

	/**
	 * Retrieve the Level 2B Share to the Total HQLA
	 * 
	 * @return The Level 2B Shares to the Total HQLA
	 */

	public double level2BRatio()
	{
		return _level2B / (_level1 + _level2A + _level2B);
	}

	/**
	 * Apply the appropriate Risk Weight and Hair cut to each of the Level x Assets
	 *  
	 * @param hqlaSettings THe HQLA Settings
	 * 
	 * @return The Risk Weight and Hair cut to each of the Level x Assets
	 */

	public HighQualityLiquidAsset applyRiskWeightAndHaircut (
		final org.drip.capital.bcbs.HighQualityLiquidAssetSettings hqlaSettings)
	{
		if (null == hqlaSettings)
		{
			return null;
		}

		try
		{
			return new HighQualityLiquidAsset (
				_level1,
				_level2A * (1. - hqlaSettings.level2AHaircut()) / (1. + hqlaSettings.level2ARiskWeight()),
				_level2B * (1. - hqlaSettings.level2BHaircut()) / (1. + hqlaSettings.level2BRiskWeight())
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Verify if the HQLA is Compliant with the Level 2 and 2B Standards
	 * 
	 * @param hqlaStandard The HQLA Standard
	 * 
	 * @return TRUE - The HQLA is Compliant with the Level 2 and 2B Standards
	 */

	public boolean isCompliant (
		final org.drip.capital.bcbs.HighQualityLiquidAssetStandard hqlaStandard)
	{
		return null == hqlaStandard ? false :
			level2Ratio() <= hqlaStandard.level2Ratio() &&
			level2BRatio() <= hqlaStandard.level2BRatio();
	}

	/**
	 * Compute the Risk Weight and Hair cut HQLA Total
	 *  
	 * @param hqlaSettings THe HQLA Settings
	 * 
	 * @return The Risk Weight and Hair cut HQLA Total
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double totalRiskWeightAndHaircut (
		final org.drip.capital.bcbs.HighQualityLiquidAssetSettings hqlaSettings)
		throws java.lang.Exception
	{
		HighQualityLiquidAsset hqla = applyRiskWeightAndHaircut (hqlaSettings);

		if (null == hqla)
		{
			throw new java.lang.Exception
				("HighQualityLiquidAsset::totalRiskWeightAndHaircut => Invalid Inputs");
		}

		return hqla.total();
	}
}
