
package org.drip.sample.allocation;

import java.util.Map;

import org.drip.analytics.support.CaseInsensitiveHashMap;
import org.drip.capital.allocation.CorrelationCategoryBetaManager;
import org.drip.capital.allocation.EntityCapital;
import org.drip.capital.allocation.EntityCapitalAssignmentSetting;
import org.drip.capital.allocation.EntityComponentCapital;
import org.drip.capital.allocation.EntityComponentCapitalAssignment;
import org.drip.capital.allocation.EntityComponentCorrelationCategory;
import org.drip.capital.entity.CapitalUnit;
import org.drip.capital.entity.ManagedSegmentL1;
import org.drip.capital.env.CapitalEstimationContextManager;
import org.drip.capital.explain.CapitalSegmentStandaloneMarginal;
import org.drip.capital.label.BusinessRegionRiskTypeCoordinate;
import org.drip.capital.label.CapitalSegmentCoordinate;
import org.drip.capital.label.Coordinate;
import org.drip.capital.setting.CapitalAllocationControl;
import org.drip.capital.setting.SimulationControl;
import org.drip.capital.setting.SimulationPnLControl;
import org.drip.service.common.FormatUtil;
import org.drip.service.env.EnvManager;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
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
 * <i>CLPExplain</i> zeds the GOC-Level Stand-alone for the Specified Managed Segment.
 * 
 *     MANAGED SEGMENT  - CLP
 *     
 * The References are:
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
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/allocation/README.md">Managed Segment Capital Allocation Schemes</a></li>
 *  </ul>
 *     
 * @author Lakshmi Krishnamurthy
 */

public class CLPExplain
{

	private static final CapitalUnit MakeCapitalUnit (
		final String business,
		final String region,
		final String riskType,
		final double varNotional)
		throws Exception
	{
		Coordinate capitalUnitCoordinate = new BusinessRegionRiskTypeCoordinate (
			business,
			region,
			riskType
		);

		return new CapitalUnit (
			capitalUnitCoordinate,
			CapitalEstimationContextManager.ContextContainer().capitalUnitStressEventContext().capitalUnitEventMap().get
			(
				capitalUnitCoordinate.fullyQualifiedName()
			),
			varNotional
		);
	}

	private static final EntityCapitalAssignmentSetting LowUniformBeta()
	{
		return EntityCapitalAssignmentSetting.UniformBeta (
			EntityComponentCorrelationCategory.LOW_CORRELATION,
			EntityComponentCorrelationCategory.LOW_CORRELATION,
			EntityComponentCorrelationCategory.LOW_CORRELATION,
			EntityComponentCorrelationCategory.HIGH_CORRELATION,
			EntityComponentCorrelationCategory.HIGH_CORRELATION
		);
	}

	private static final EntityCapitalAssignmentSetting MediumUniformBeta()
	{
		return EntityCapitalAssignmentSetting.UniformBeta (
			EntityComponentCorrelationCategory.MEDIUM_CORRELATION,
			EntityComponentCorrelationCategory.MEDIUM_CORRELATION,
			EntityComponentCorrelationCategory.MEDIUM_CORRELATION,
			EntityComponentCorrelationCategory.HIGH_CORRELATION,
			EntityComponentCorrelationCategory.HIGH_CORRELATION
		);
	}

	private static final EntityCapitalAssignmentSetting HighUniformBeta()
	{
		return EntityCapitalAssignmentSetting.UniformBeta (
			EntityComponentCorrelationCategory.HIGH_CORRELATION,
			EntityComponentCorrelationCategory.HIGH_CORRELATION,
			EntityComponentCorrelationCategory.HIGH_CORRELATION,
			EntityComponentCorrelationCategory.HIGH_CORRELATION,
			EntityComponentCorrelationCategory.HIGH_CORRELATION
		);
	}

	private static final EntityCapitalAssignmentSetting HighMedumLowUniformBeta()
	{
		double random = Math.random();

		if (random < 13. / 34.)
		{
			return LowUniformBeta();
		}

		return random < 29. / 34. ? MediumUniformBeta() : HighUniformBeta();
	}

	private static final EntityCapitalAssignmentSetting HighLowUniformBeta()
	{
		return Math.random() < 29. / 34. ? LowUniformBeta() : HighUniformBeta();
	}

	private static final void Allocate (
		final CapitalUnit[] capitalUnitArray,
		final CapitalSegmentStandaloneMarginal capitalSegmentStandaloneMarginal,
		final CapitalAllocationControl marginalProRataCapitalAllocationControl,
		final CapitalAllocationControl standaloneProRataCapitalAllocationControl,
		final CapitalAllocationControl fixedFloatFloatCapitalAllocationControl,
		final CapitalAllocationControl fixedFloatCapitalAllocationControl,
		final CapitalAllocationControl floatFloatCapitalAllocationControl)
		throws Exception
	{
		EntityComponentCapitalAssignment marginalProRataEntityComponentCapitalAssignment =
			capitalSegmentStandaloneMarginal.betaAllocation (
				marginalProRataCapitalAllocationControl
			);

		Map<String, EntityComponentCapital> marginalProRataEntityComponentCapitalMap =
			marginalProRataEntityComponentCapitalAssignment.entityComponentCapitalMap();

		EntityComponentCapitalAssignment standaloneProRataEntityComponentCapitalAssignment =
			capitalSegmentStandaloneMarginal.betaAllocation (
				standaloneProRataCapitalAllocationControl
			);

		Map<String, EntityComponentCapital> standaloneProRataEntityComponentCapitalMap =
			standaloneProRataEntityComponentCapitalAssignment.entityComponentCapitalMap();

		EntityComponentCapitalAssignment fixedFloatFloatEntityComponentCapitalAssignment =
			capitalSegmentStandaloneMarginal.betaAllocation (
				fixedFloatFloatCapitalAllocationControl
			);

		Map<String, EntityComponentCapital> fixedFloatFloatEntityComponentCapitalMap =
			fixedFloatFloatEntityComponentCapitalAssignment.entityComponentCapitalMap();

		EntityComponentCapitalAssignment fixedFloatEntityComponentCapitalAssignment =
			capitalSegmentStandaloneMarginal.betaAllocation (
				fixedFloatCapitalAllocationControl
			);

		Map<String, EntityComponentCapital> fixedFloatEntityComponentCapitalMap =
			fixedFloatEntityComponentCapitalAssignment.entityComponentCapitalMap();

		EntityComponentCapitalAssignment floatFloatEntityComponentCapitalAssignment =
			capitalSegmentStandaloneMarginal.betaAllocation (
				floatFloatCapitalAllocationControl
			);

		Map<String, EntityComponentCapital> floatFloatEntityComponentCapitalMap =
			floatFloatEntityComponentCapitalAssignment.entityComponentCapitalMap();

		System.out.println (
			"\t|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------||"
		);

		System.out.println (
			"\t|                                                                              CAPITAL UNIT RC ALLOCATION                                                                              ||"
		);

		System.out.println (
			"\t|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------||"
		);

		System.out.println (
			"\t|    L -> R:                                                                                                                                                                           ||"
		);

		System.out.println (
			"\t|            - Marginal Pro-Rata Fractional                                                                                                                                            ||"
		);

		System.out.println (
			"\t|            - Standalone Pro-Rata Fractional                                                                                                                                          ||"
		);

		System.out.println (
			"\t|            - Three-Beta Fixed-Float-Float Fractional                                                                                                                                 ||"
		);

		System.out.println (
			"\t|            - Two-Beta Fixed-Float Fractional                                                                                                                                         ||"
		);

		System.out.println (
			"\t|            - Two-Beta Float-Float Fractional                                                                                                                                         ||"
		);

		System.out.println (
			"\t|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------||"
		);

		System.out.println (
			"\t|            - Marginal Pro-Rata Absolute                                                                                                                                              ||"
		);

		System.out.println (
			"\t|            - Standalone Pro-Rata Absolute                                                                                                                                            ||"
		);

		System.out.println (
			"\t|            - Three-Beta Fixed-Float-Float Absolute                                                                                                                                   ||"
		);

		System.out.println (
			"\t|            - Two-Beta Fixed-Float Absolute                                                                                                                                           ||"
		);

		System.out.println (
			"\t|            - Two-Beta Float-Float Absolute                                                                                                                                           ||"
		);

		System.out.println (
			"\t|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------||"
		);

		System.out.println (
			"\t|            - Capital Unit Coordinate                                                                                                                                                 ||"
		);

		System.out.println (
			"\t|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------||"
		);

		for (int capitalUnitIndex = 0;
			capitalUnitIndex < capitalUnitArray.length;
			++capitalUnitIndex)
		{
			String capitalUnitFQN = capitalUnitArray[capitalUnitIndex].coordinate().fullyQualifiedName();

			EntityCapital marginalProRataEntityComponentCapitalTotal =
				marginalProRataEntityComponentCapitalMap.get (
					capitalUnitFQN
				).total();

			EntityCapital standaloneProRataEntityComponentCapitalTotal =
				standaloneProRataEntityComponentCapitalMap.get (
					capitalUnitFQN
				).total();

			EntityCapital fixedFloatFloatEntityComponentCapitalTotal =
				fixedFloatFloatEntityComponentCapitalMap.get (
					capitalUnitFQN
				).total();

			EntityCapital fixedFloatEntityComponentCapitalTotal = fixedFloatEntityComponentCapitalMap.get (
				capitalUnitFQN
			).total();

			EntityCapital floatFloatEntityComponentCapitalTotal = floatFloatEntityComponentCapitalMap.get (
				capitalUnitFQN
			).total();

			System.out.println (
				"\t|    " +
				FormatUtil.FormatDouble (
					marginalProRataEntityComponentCapitalTotal.fractional(), 2, 2, 100.
				) + "% | " +
				FormatUtil.FormatDouble (
					standaloneProRataEntityComponentCapitalTotal.fractional(), 2, 2, 100.
				) + "% | " +
				FormatUtil.FormatDouble (
					fixedFloatFloatEntityComponentCapitalTotal.fractional(), 2, 2, 100.
				) + "% | " +
				FormatUtil.FormatDouble (
					fixedFloatEntityComponentCapitalTotal.fractional(), 2, 2, 100.
				) + "% | " +
				FormatUtil.FormatDouble (
					floatFloatEntityComponentCapitalTotal.fractional(), 2, 2, 100.
				) + "% || " + FormatUtil.FormatDouble (
					marginalProRataEntityComponentCapitalTotal.absolute(), 10, 2, 1.
				) + " | " + FormatUtil.FormatDouble (
					standaloneProRataEntityComponentCapitalTotal.absolute(), 10, 2, 1.
				) + " | " + FormatUtil.FormatDouble (
					fixedFloatFloatEntityComponentCapitalTotal.absolute(), 10, 2, 1.
				) + " | " + FormatUtil.FormatDouble (
					fixedFloatEntityComponentCapitalTotal.absolute(), 10, 2, 1.
				) + " | " + FormatUtil.FormatDouble (
					floatFloatEntityComponentCapitalTotal.absolute(), 10, 2, 1.
				) + " <= " + capitalUnitFQN
			);
		}

		System.out.println (
			"\t|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------||"
		);

		System.out.println (
			"\t|   " +
			FormatUtil.FormatDouble (
				1., 2, 2, 100.
			) + "% |" +
			FormatUtil.FormatDouble (
				1., 2, 2, 100.
			) + "% |" +
			FormatUtil.FormatDouble (
				1., 2, 2, 100.
			) + "% |" +
			FormatUtil.FormatDouble (
				1., 2, 2, 100.
			) + "% |" +
			FormatUtil.FormatDouble (
				1., 2, 2, 100.
			) + "% || " + FormatUtil.FormatDouble (
				marginalProRataEntityComponentCapitalAssignment.allocatedTotalCapital(), 10, 2, 1.
			) + " | " + FormatUtil.FormatDouble (
				standaloneProRataEntityComponentCapitalAssignment.allocatedTotalCapital(), 10, 2, 1.
			) + " | " + FormatUtil.FormatDouble (
				fixedFloatFloatEntityComponentCapitalAssignment.allocatedTotalCapital(), 10, 2, 1.
			) + " | " + FormatUtil.FormatDouble (
				fixedFloatEntityComponentCapitalAssignment.allocatedTotalCapital(), 10, 2, 1.
			) + " | " + FormatUtil.FormatDouble (
				floatFloatEntityComponentCapitalAssignment.allocatedTotalCapital(), 10, 2, 1.
			) + " <= TOTAL"
		);

		System.out.println (
			"\t|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------||"
		);

		System.out.println();
	}

	public static final void main (
		final String[] argumentArray)
		throws Exception
	{
		EnvManager.InitEnv ("");

		double capitalUnitNotional = 10000.;
		String capitalSegmentFQN = "CLP";
		String[] regionArray =
		{
			"ASIA",
			"EMEA",
			"EMEA",
			"LATIN AMERICA",
			"NORTH AMERICA",
			"NORTH AMERICA",
		};
		String[] riskTypeArray =
		{
			"AFS",
			"AFS",
			"Trading",
			"AFS",
			"AFS",
			"Trading",
		};

		CorrelationCategoryBetaManager proRataCategoryManager =
			CorrelationCategoryBetaManager.TwoBetaFloatFloat (
				1.0
			);

		CorrelationCategoryBetaManager fixedFloatFloatCategoryManager =
			CorrelationCategoryBetaManager.ThreeBetaFixedFloatFloat (
				0.8,
				2.0
			);

		CorrelationCategoryBetaManager fixedFloatCategoryManager =
			CorrelationCategoryBetaManager.TwoBetaFixedFloat (
				0.8
			);

		CorrelationCategoryBetaManager floatFloatCategoryManager =
			CorrelationCategoryBetaManager.TwoBetaFloatFloat (
				2.0
			);

		SimulationControl simulationControl = SimulationControl.Standard();

		SimulationPnLControl simulationPnLControl = SimulationPnLControl.Standard();

		Map<String, EntityCapitalAssignmentSetting> proRataEntityCapitalAssignmentSettingMap =
			new CaseInsensitiveHashMap<EntityCapitalAssignmentSetting>();

		Map<String, EntityCapitalAssignmentSetting> fixedFloatFloatEntityCapitalAssignmentSettingMap =
			new CaseInsensitiveHashMap<EntityCapitalAssignmentSetting>();

		Map<String, EntityCapitalAssignmentSetting> fixedFloatEntityCapitalAssignmentSettingMap =
			new CaseInsensitiveHashMap<EntityCapitalAssignmentSetting>();

		Map<String, EntityCapitalAssignmentSetting> floatFloatEntityCapitalAssignmentSettingMap =
			new CaseInsensitiveHashMap<EntityCapitalAssignmentSetting>();

		CapitalUnit[] capitalUnitArray = new CapitalUnit[riskTypeArray.length];

		for (int capitalUnitIndex = 0;
			capitalUnitIndex < riskTypeArray.length;
			++capitalUnitIndex)
		{
			capitalUnitArray[capitalUnitIndex] = MakeCapitalUnit (
				capitalSegmentFQN,
				regionArray[capitalUnitIndex],
				riskTypeArray[capitalUnitIndex],
				capitalUnitNotional
			);

			proRataEntityCapitalAssignmentSettingMap.put (
				capitalUnitArray[capitalUnitIndex].coordinate().fullyQualifiedName(),
				LowUniformBeta()
			);

			fixedFloatFloatEntityCapitalAssignmentSettingMap.put (
				capitalUnitArray[capitalUnitIndex].coordinate().fullyQualifiedName(),
				HighMedumLowUniformBeta()
			);

			fixedFloatEntityCapitalAssignmentSettingMap.put (
				capitalUnitArray[capitalUnitIndex].coordinate().fullyQualifiedName(),
				HighLowUniformBeta()
			);

			floatFloatEntityCapitalAssignmentSettingMap.put (
				capitalUnitArray[capitalUnitIndex].coordinate().fullyQualifiedName(),
				HighLowUniformBeta()
			);
		}

		CapitalSegmentStandaloneMarginal capitalSegmentStandaloneMarginal = new ManagedSegmentL1 (
			new CapitalSegmentCoordinate (
				capitalSegmentFQN
			),
			capitalUnitArray
		).pathEnsemble (
			simulationControl,
			simulationPnLControl
		).marginalStandalonePnLAttribution (
			simulationPnLControl.stress().expectedShortfallConfidenceLevel()
		);

		Allocate (
			capitalUnitArray,
			capitalSegmentStandaloneMarginal,
			new CapitalAllocationControl (
				true,
				null,
				proRataCategoryManager,
				proRataEntityCapitalAssignmentSettingMap
			),
			new CapitalAllocationControl (
				false,
				null,
				proRataCategoryManager,
				proRataEntityCapitalAssignmentSettingMap
			),
			new CapitalAllocationControl (
				false,
				null,
				fixedFloatFloatCategoryManager,
				fixedFloatFloatEntityCapitalAssignmentSettingMap
			),
			new CapitalAllocationControl (
				false,
				null,
				fixedFloatCategoryManager,
				fixedFloatEntityCapitalAssignmentSettingMap
			),
			new CapitalAllocationControl (
				false,
				null,
				floatFloatCategoryManager,
				floatFloatEntityCapitalAssignmentSettingMap
			)
		);

		EnvManager.TerminateEnv();
	}
}
