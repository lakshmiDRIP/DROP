
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
 * <i>CapitalMetrics</i> holds the Realized Capital Metrics. The References are:
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

public class CapitalMetrics
{
	private double _tier1Ratio = java.lang.Double.NaN;
	private double _totalRatio = java.lang.Double.NaN;
	private double _leverageRatio = java.lang.Double.NaN;
	private double _commonEquityRatio = java.lang.Double.NaN;
	private double _totalPlusConservationBufferRatio = java.lang.Double.NaN;
	private double _commonEquityPlusConservationBufferRatio = java.lang.Double.NaN;

	/**
	 * CapitalMetrics Constructor
	 * 
	 * @param leverageRatio Leverage Ratio
	 * @param commonEquityRatio Common Equity Capital Ratio
	 * @param commonEquityPlusConservationBufferRatio Common Equity Capital Plus Capital Conservation Buffer
	 * 		Ratio
	 * @param tier1Ratio Tier 1 Capital Ratio
	 * @param totalRatio Total Capital Ratio
	 * @param totalPlusConservationBufferRatio Total Capital Plus Conservation Buffer Ratio
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public CapitalMetrics (
		final double leverageRatio,
		final double commonEquityRatio,
		final double commonEquityPlusConservationBufferRatio,
		final double tier1Ratio,
		final double totalRatio,
		final double totalPlusConservationBufferRatio)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (_leverageRatio = leverageRatio) ||
				0. > _leverageRatio ||
			!org.drip.numerical.common.NumberUtil.IsValid (_commonEquityRatio = commonEquityRatio) ||
				0. > _commonEquityRatio ||
			!org.drip.numerical.common.NumberUtil.IsValid (_commonEquityPlusConservationBufferRatio =
				commonEquityPlusConservationBufferRatio) || 0. > _commonEquityPlusConservationBufferRatio ||
			!org.drip.numerical.common.NumberUtil.IsValid (_tier1Ratio = tier1Ratio) || 0. > _tier1Ratio ||
			!org.drip.numerical.common.NumberUtil.IsValid (_totalRatio = totalRatio) || 0. > _totalRatio ||
			!org.drip.numerical.common.NumberUtil.IsValid (_totalPlusConservationBufferRatio =
				totalPlusConservationBufferRatio) || 0. > _totalPlusConservationBufferRatio)
		{
			throw new java.lang.Exception ("CapitalMetrics Contructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Leverage Ratio
	 * 
	 * @return The Leverage Ratio
	 */

	public double leverageRatio()
	{
		return _leverageRatio;
	}

	/**
	 * Retrieve the Common Equity Capital Ratio
	 * 
	 * @return The Common Equity Capital Ratio
	 */

	public double commonEquityRatio()
	{
		return _commonEquityRatio;
	}

	/**
	 * Retrieve the Capital Conservation Buffer Ratio
	 * 
	 * @return The Capital Conservation Buffer Ratio
	 */

	public double conservationBufferRatio()
	{
		return _commonEquityPlusConservationBufferRatio - _commonEquityRatio;
	}

	/**
	 * Retrieve the Common Equity Capital Plus Capital Conservation Buffer Ratio
	 * 
	 * @return The Common Equity Capital Plus Capital Conservation Buffer Ratio
	 */

	public double commonEquityPlusConservationBufferRatio()
	{
		return _commonEquityPlusConservationBufferRatio;
	}

	/**
	 * Retrieve the Tier 1 Capital Ratio
	 * 
	 * @return The Tier 1 Capital Ratio
	 */

	public double tier1Ratio()
	{
		return _tier1Ratio;
	}

	/**
	 * Retrieve the Total Capital Ratio
	 * 
	 * @return The Total Capital Ratio
	 */

	public double totalRatio()
	{
		return _totalRatio;
	}

	/**
	 * Retrieve the Total Capital Plus Conservation Buffer Ratio
	 * 
	 * @return The Total Capital Plus Conservation Buffer Ratio
	 */

	public double totalPlusConservationBufferRatio()
	{
		return _totalPlusConservationBufferRatio;
	}

	/**
	 * Verify if the Capital Metrics are Compliant with the Standard
	 * 
	 * @param capitalMetricsStandard The Capital Metrics Standard
	 * 
	 * @return TRUE - The Capital Metrics are Compliant with the Standard
	 */

	public boolean isCompliant (
		final org.drip.bcbs.core.CapitalMetrics capitalMetricsStandard)
	{
		return null == capitalMetricsStandard ? false :
			_leverageRatio >= capitalMetricsStandard.leverageRatio() &&
			_commonEquityRatio >= capitalMetricsStandard.commonEquityRatio() &&
			_commonEquityPlusConservationBufferRatio >=
				capitalMetricsStandard.commonEquityPlusConservationBufferRatio() &&
			_tier1Ratio >= capitalMetricsStandard.tier1Ratio() &&
			_totalRatio >= capitalMetricsStandard.totalRatio() &&
			_totalPlusConservationBufferRatio >= capitalMetricsStandard.totalPlusConservationBufferRatio();
	}
}
