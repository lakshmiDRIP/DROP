
package org.drip.capital.explain;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting analytics/risk, transaction cost analytics,
 *  	asset liability management analytics, capital, exposure, and margin analytics, valuation adjustment
 *  	analytics, and portfolio construction analytics within and across fixed income, credit, commodity,
 *  	equity, FX, and structured products. It also includes auxiliary libraries for algorithm support,
 *  	numerical analysis, numerical optimization, spline builder, model validation, statistical learning,
 *  	and computational support.
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
 * <i>CapitalSegmentStandaloneMarginal</i> holds the Top-of-the-House Capital Attributions as well the
 * Segment-Level Contributions from the Stand-alone Capital Units. The References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Bank for International Supervision(2005): Stress Testing at Major Financial Institutions: Survey
 * 				Results and Practice https://www.bis.org/publ/cgfs24.htm
 * 		</li>
 * 		<li>
 * 			Glasserman, P. (2004): <i>Monte Carlo Methods in Financial Engineering</i> <b>Springer</b>
 * 		</li>
 * 		<li>
 * 			Kupiec, P. H. (2000): Stress Tests and Risk Capital <i>Risk</i> <b>2 (4)</b> 27-39
 * 		</li>
 * 	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/CapitalAnalyticsLibrary.md">Capital Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/README.md">Basel Market Risk and Operational Capital</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/explain/README.md">Economic Risk Capital Attribution Explain</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class CapitalSegmentStandaloneMarginal
	extends org.drip.capital.explain.CapitalUnitPnLAttribution
{
	private java.util.Map<java.lang.String, org.drip.capital.explain.PnLAttribution>
		_marginalPnLAttributionMap = null;

	private java.util.Map<java.lang.String, org.drip.capital.explain.PnLAttribution>
		_standalonePnLAttributionMap = null;

	private static final java.util.Map<java.lang.String, java.lang.Double> UpdateProRataNormalizerMap (
		final org.drip.capital.allocation.EntityElasticityAttribution
			standaloneEntityCapitalElasticityAttribution,
		final org.drip.capital.allocation.EntityElasticityAttribution
			marginalEntityCapitalElasticityAttribution)
	{
		java.util.Map<java.lang.String, java.lang.Double> proRataNormalizerMap =
			new org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();

		double systemicProRataStandalone = standaloneEntityCapitalElasticityAttribution.systemicProRata();

		if (0. != systemicProRataStandalone)
		{
			proRataNormalizerMap.put (
				"Systemic",
				marginalEntityCapitalElasticityAttribution.systemicProRata() / systemicProRataStandalone
			);
		}

		double correlatedProRataStandalone =
			standaloneEntityCapitalElasticityAttribution.correlatedProRata();

		if (0. != correlatedProRataStandalone)
		{
			proRataNormalizerMap.put (
				"Correlated",
				marginalEntityCapitalElasticityAttribution.correlatedProRata() / correlatedProRataStandalone
			);
		}

		double idiosyncraticProRataStandalone =
			standaloneEntityCapitalElasticityAttribution.idiosyncraticProRata();

		if (0. != idiosyncraticProRataStandalone)
		{
			proRataNormalizerMap.put (
				"Idiosyncratic",
				marginalEntityCapitalElasticityAttribution.idiosyncraticProRata() /
					idiosyncraticProRataStandalone
			);
		}

		double noStressProRataStandalone = standaloneEntityCapitalElasticityAttribution.noStressProRata();

		if (0. != noStressProRataStandalone)
		{
			proRataNormalizerMap.put (
				"NOSTRESS",
				marginalEntityCapitalElasticityAttribution.noStressProRata() / noStressProRataStandalone
			);
		}

		return proRataNormalizerMap;
	}

	/**
	 * CapitalSegmentStandaloneMarginal Constructor
	 * 
	 * @param pathPnLRealizationList Segment Level Merged Path PnL Realization List
	 * @param marginalPnLAttributionMap Capital Unit Marginal PnL Attribution Map
	 * @param standalonePnLAttributionMap Capital Unit Marginal PnL Attribution Map
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public CapitalSegmentStandaloneMarginal (
		final java.util.List<org.drip.capital.simulation.PathPnLRealization> pathPnLRealizationList,
		final java.util.Map<java.lang.String, org.drip.capital.explain.PnLAttribution>
			marginalPnLAttributionMap,
		final java.util.Map<java.lang.String, org.drip.capital.explain.PnLAttribution>
			standalonePnLAttributionMap)
		throws java.lang.Exception
	{
		super (
			pathPnLRealizationList
		);

		_marginalPnLAttributionMap = marginalPnLAttributionMap;
		_standalonePnLAttributionMap = standalonePnLAttributionMap;
	}

	/**
	 * Retrieve the Capital Unit Marginal PnL Attribution Map
	 * 
	 * @return The Capital Unit Marginal PnL Attribution Map
	 */

	public java.util.Map<java.lang.String, org.drip.capital.explain.PnLAttribution>
		marginalPnLAttributionMap()
	{
		return _marginalPnLAttributionMap;
	}

	/**
	 * Retrieve the Capital Unit Stand-alone PnL Attribution Map
	 * 
	 * @return The Capital Unit Stand-alone PnL Attribution Map
	 */

	public java.util.Map<java.lang.String, org.drip.capital.explain.PnLAttribution>
		standalonePnLAttributionMap()
	{
		return _standalonePnLAttributionMap;
	}

	/**
	 * Compute the Expected Short-fall Based Beta Allocation Map
	 * 
	 * @param capitalAllocationControl Capital Allocation Setting Control
	 * 
	 * @return The Expected Short-fall Based Beta Allocation Map
	 */

	public org.drip.capital.allocation.EntityComponentCapitalAssignment betaAllocation (
		final org.drip.capital.setting.CapitalAllocationControl capitalAllocationControl)
	{
		if (null == capitalAllocationControl)
		{
			return null;
		}

		org.drip.capital.allocation.EntityElasticityAttribution
			marginalEntityCapitalElasticityAttribution = null;
		org.drip.capital.allocation.EntityElasticityAttribution
			allocatedEntityCapitalElasticityAttribution = null;

		org.drip.capital.allocation.CorrelationCategoryBetaManager correlationCategoryBetaManager =
			capitalAllocationControl.correlationCategoryBetaManager();

		java.util.Map<java.lang.String, org.drip.capital.allocation.EntityCapitalAssignmentSetting>
			entityCapitalAssignmentSettingMap =
				capitalAllocationControl.entityCapitalAssignmentSettingMap();

		try
		{
			marginalEntityCapitalElasticityAttribution =
				new org.drip.capital.allocation.EntityElasticityAttribution (
					correlationCategoryBetaManager,
					true
				);

			allocatedEntityCapitalElasticityAttribution =
				new org.drip.capital.allocation.EntityElasticityAttribution (
					correlationCategoryBetaManager,
					false
				);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();

			return null;
		}

		for (java.util.Map.Entry<java.lang.String, org.drip.capital.explain.PnLAttribution>
			marginalPnLAttributionEntry : _marginalPnLAttributionMap.entrySet())
		{
			if (!marginalEntityCapitalElasticityAttribution.accumulate (
				marginalPnLAttributionEntry.getValue(),
				entityCapitalAssignmentSettingMap.get (
					marginalPnLAttributionEntry.getKey()
				)
			))
			{
				return null;
			}
		}

		java.util.Map<java.lang.String, org.drip.capital.explain.PnLAttribution> pnlAttributionMap =
			capitalAllocationControl.useMarginal() ? _marginalPnLAttributionMap :
				_standalonePnLAttributionMap;

		for (java.util.Map.Entry<java.lang.String, org.drip.capital.explain.PnLAttribution>
			pnlAttributionEntry : pnlAttributionMap.entrySet())
		{
			if (!allocatedEntityCapitalElasticityAttribution.accumulate (
				pnlAttributionEntry.getValue(),
				entityCapitalAssignmentSettingMap.get (
					pnlAttributionEntry.getKey()
				)
			))
			{
				return null;
			}
		}

		double floatingBetaCapitalStandalone = allocatedEntityCapitalElasticityAttribution.floating();

		double fixedBetaCapitalStandalone = allocatedEntityCapitalElasticityAttribution.fixed();

		double allocatedBetaCapital = marginalEntityCapitalElasticityAttribution.floating() +
			marginalEntityCapitalElasticityAttribution.fixed();

		if (0. == floatingBetaCapitalStandalone ||
			allocatedBetaCapital > fixedBetaCapitalStandalone)
		{
			return null;
		}

		double unitFloatBeta = (allocatedBetaCapital - fixedBetaCapitalStandalone) /
			floatingBetaCapitalStandalone;

		java.util.Map<java.lang.String, java.lang.Double> proRataNormalizerMap = UpdateProRataNormalizerMap (
			allocatedEntityCapitalElasticityAttribution,
			marginalEntityCapitalElasticityAttribution
		);

		if (null == proRataNormalizerMap)
		{
			return null;
		}

		double allocatedProRataCapital = marginalEntityCapitalElasticityAttribution.noStressProRata() +
			marginalEntityCapitalElasticityAttribution.systemicProRata() +
			marginalEntityCapitalElasticityAttribution.correlatedProRata() +
			marginalEntityCapitalElasticityAttribution.idiosyncraticProRata();

		java.util.Map<java.lang.String, org.drip.capital.allocation.EntityComponentCapital>
			entityComponentCapitalMap = new
				org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.capital.allocation.EntityComponentCapital>();

		for (java.util.Map.Entry<java.lang.String, org.drip.capital.explain.PnLAttribution>
			pnlAttributionEntry : pnlAttributionMap.entrySet())
		{
			java.lang.String capitalUnitCoordinate = pnlAttributionEntry.getKey();

			entityComponentCapitalMap.put (
				capitalUnitCoordinate,
				org.drip.capital.allocation.EntityComponentCapital.FromPnLAttribution (
					correlationCategoryBetaManager,
					entityCapitalAssignmentSettingMap.get (
						capitalUnitCoordinate
					),
					pnlAttributionEntry.getValue(),
					proRataNormalizerMap,
					unitFloatBeta,
					allocatedBetaCapital + allocatedProRataCapital
				)
			);
		}

		try
		{
			return new org.drip.capital.allocation.EntityComponentCapitalAssignment (
				allocatedEntityCapitalElasticityAttribution,
				entityComponentCapitalMap,
				unitFloatBeta,
				allocatedBetaCapital,
				allocatedProRataCapital
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
