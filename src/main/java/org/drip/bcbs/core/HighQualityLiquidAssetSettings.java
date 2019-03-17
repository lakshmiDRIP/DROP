
package org.drip.bcbs.core;

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
 * <i>HighQualityLiquidAssetSettings</i> holds the Risk-Weights and the Haircuts associated with Levels 1,
 * 2A, and 2B. The References are:
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

public class HighQualityLiquidAssetSettings
{
	private double _level1Haircut = java.lang.Double.NaN;
	private double _level2AHaircut = java.lang.Double.NaN;
	private double _level2BHaircut = java.lang.Double.NaN;
	private double _level1RiskWeight = java.lang.Double.NaN;
	private double _level2ARiskWeight = java.lang.Double.NaN;
	private double _level2BRiskWeight = java.lang.Double.NaN;

	/**
	 * Retrieve the Federal Reserve Version of the HQLA Settings Standard
	 * 
	 * @return The Federal Reserve Version of the HQLA Settings Standard
	 */

	public static final HighQualityLiquidAssetSettings FederalReserveStandard()
	{
		try
		{
			return new HighQualityLiquidAssetSettings (
				0.00,
				0.00,
				0.15,
				0.20,
				0.50,
				0.50
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * HighQualityLiquidAssetSettings Constructor
	 * 
	 * @param level1Haircut Level 1 HQLA Haircut
	 * @param level1RiskWeight Level 1 HQLA Risk-Weight
	 * @param level2AHaircut Level 2A HQLA Haircut
	 * @param level2ARiskWeight Level 2A HQLA Risk-Weight
	 * @param level2BHaircut Level 2B HQLA Haircut
	 * @param level2BRiskWeight Level 2B HQLA Risk-Weight
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public HighQualityLiquidAssetSettings (
		final double level1Haircut,
		final double level1RiskWeight,
		final double level2AHaircut,
		final double level2ARiskWeight,
		final double level2BHaircut,
		final double level2BRiskWeight)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (_level1Haircut = level1Haircut) ||
				0. > _level1Haircut || 1. < _level1Haircut ||
			!org.drip.numerical.common.NumberUtil.IsValid (_level1RiskWeight = level1RiskWeight) ||
				0. > _level1RiskWeight ||
			!org.drip.numerical.common.NumberUtil.IsValid (_level2AHaircut = level2AHaircut) ||
				0. > _level2AHaircut || 1. < _level2AHaircut ||
			!org.drip.numerical.common.NumberUtil.IsValid (_level2ARiskWeight = level2ARiskWeight) ||
				0. > _level2ARiskWeight ||
			!org.drip.numerical.common.NumberUtil.IsValid (_level2BHaircut = level2BHaircut) ||
				0. > _level2BHaircut || 1. < _level2BHaircut ||
			!org.drip.numerical.common.NumberUtil.IsValid (_level2BRiskWeight = level2BRiskWeight) ||
				0. > _level2BRiskWeight)
		{
			throw new java.lang.Exception ("HighQualityLiquidAssetSettings Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Level 1 Risk Weight
	 * 
	 * @return The Level 1 Risk Weight
	 */

	public double level1RiskWeight()
	{
		return _level1RiskWeight;
	}

	/**
	 * Retrieve the Level 1 Haircut
	 * 
	 * @return The Level 1 Haircut
	 */

	public double level1Haircut()
	{
		return _level1Haircut;
	}

	/**
	 * Retrieve the Level 2A Risk Weight
	 * 
	 * @return The Level 2A Risk Weight
	 */

	public double level2ARiskWeight()
	{
		return _level2ARiskWeight;
	}

	/**
	 * Retrieve the Level 2A Haircut
	 * 
	 * @return The Level 2A Haircut
	 */

	public double level2AHaircut()
	{
		return _level2AHaircut;
	}

	/**
	 * Retrieve the Level 2B Risk Weight
	 * 
	 * @return The Level 2B Risk Weight
	 */

	public double level2BRiskWeight()
	{
		return _level2BRiskWeight;
	}

	/**
	 * Retrieve the Level 2B Haircut
	 * 
	 * @return The Level 2B Haircut
	 */

	public double level2BHaircut()
	{
		return _level2BHaircut;
	}
}
