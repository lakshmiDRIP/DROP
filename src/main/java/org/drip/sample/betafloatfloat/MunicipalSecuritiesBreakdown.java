
package org.drip.sample.betafloatfloat;

import java.util.Map;

import org.drip.analytics.support.CaseInsensitiveHashMap;
import org.drip.capital.allocation.CorrelationCategoryBeta;
import org.drip.capital.allocation.CorrelationCategoryBetaManager;
import org.drip.capital.allocation.EntityCapital;
import org.drip.capital.allocation.EntityCapitalAssignmentSetting;
import org.drip.capital.allocation.EntityComponentCapital;
import org.drip.capital.allocation.EntityComponentCapitalAssignment;
import org.drip.capital.allocation.EntityComponentCorrelationCategory;
import org.drip.capital.allocation.EntityElasticityAttribution;
import org.drip.capital.entity.CapitalUnit;
import org.drip.capital.entity.ManagedSegmentL1;
import org.drip.capital.env.CapitalEstimationContextManager;
import org.drip.capital.explain.AllocatedPnLAttribution;
import org.drip.capital.explain.CapitalSegmentStandaloneMarginal;
import org.drip.capital.explain.PnLAttribution;
import org.drip.capital.label.BusinessRegionRiskTypeCoordinate;
import org.drip.capital.label.CapitalSegmentCoordinate;
import org.drip.capital.label.Coordinate;
import org.drip.capital.setting.CapitalAllocationControl;
import org.drip.capital.setting.SimulationControl;
import org.drip.capital.setting.SimulationPnLControl;
import org.drip.numerical.common.FormatUtil;
import org.drip.service.env.EnvManager;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Quantitative Risk Analytics
 */

/**
 * <i>MunicipalSecuritiesBreakdown</i> zeds the Managed Sub-segment Level Allocation for the Specified
 * 	Managed Segment using the Two Beta Scheme.
 * 
 *     MANAGED SEGMENT  - Municipal Securities
 * 
 *     HIGH   - Floating Beta
 *     LOW    - Floating Beta
 *     
 * The References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Bank for International Supervision (2005): Stress Testing at Major Financial Institutions: Survey
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
 * @author Lakshmi Krishnamurthy
 */

public class MunicipalSecuritiesBreakdown
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

	private static final void DisplayPnLAttribution (
		final String capitalSegmentFQN,
		final PnLAttribution pnlAttribution)
		throws Exception
	{
		Map<String, Double> gsstPnLExplainMap = pnlAttribution.gsstPnLExplainMap();

		Map<String, Double> cBSSTPnLExplainMap = pnlAttribution.cBSSTPnLExplainMap();

		Map<String, Double> iBSSTPnLExplainMap = pnlAttribution.iBSSTPnLExplainMap();

		Map<String, Integer> gsstInstanceCountMap = pnlAttribution.gsstInstanceCountMap();

		Map<String, Integer> cBSSTInstanceCountMap = pnlAttribution.cBSSTInstanceCountMap();

		Map<String, Integer> iBSSTInstanceCountMap = pnlAttribution.iBSSTInstanceCountMap();

		Map<String, Double> gsstGrossPnLExplainMap = pnlAttribution.gsstGrossPnLExplainMap();

		Map<String, Double> fsPnLDecompositionExplainMap = pnlAttribution.fsPnLDecompositionExplainMap();

		System.out.println ("\t|---------------------------------------------------------||");

		System.out.println ("\t|              CONTRIBUTORY PnL ATTRIBUTION               ||");

		System.out.println ("\t|---------------------------------------------------------||");

		System.out.println ("\t|    Capital Segment FQN => " + capitalSegmentFQN);

		System.out.println ("\t|    Expected Short-fall => " +
			FormatUtil.FormatDouble (pnlAttribution.expectedShortfall(), 10, 2, 1.)
		);

		if (null != gsstPnLExplainMap)
		{
			System.out.println ("\t|---------------------------------------------------------||");

			for (Map.Entry<String, Double> gsstPnLExplainEntry : gsstPnLExplainMap.entrySet())
			{
				System.out.println (
					"\t|\t GSST | " + gsstPnLExplainEntry.getKey() + " => " +
					FormatUtil.FormatDouble (gsstPnLExplainEntry.getValue(), 10, 2, 1.)
				);
			}
		}

		if (null != cBSSTPnLExplainMap)
		{
			System.out.println ("\t|---------------------------------------------------------||");

			for (Map.Entry<String, Double> cBSSTPnLExplainEntry : cBSSTPnLExplainMap.entrySet())
			{
				System.out.println (
					"\t|\tcBSST | " + cBSSTPnLExplainEntry.getKey() + " => " +
					FormatUtil.FormatDouble (cBSSTPnLExplainEntry.getValue(), 10, 2, 1.)
				);
			}
		}

		if (null != iBSSTPnLExplainMap)
		{
			System.out.println ("\t|---------------------------------------------------------||");

			for (Map.Entry<String, Double> iBSSTPnLExplainEntry : iBSSTPnLExplainMap.entrySet())
			{
				System.out.println (
					"\t|\tiBSST | " + iBSSTPnLExplainEntry.getKey() + " => " +
					FormatUtil.FormatDouble (iBSSTPnLExplainEntry.getValue(), 10, 2, 1.)
				);
			}
		}

		if (null != fsPnLDecompositionExplainMap)
		{
			System.out.println ("\t|---------------------------------------------------------||");

			for (Map.Entry<String, Double> fsPnLDecompositionExplainEntry :
				fsPnLDecompositionExplainMap.entrySet())
			{
				System.out.println (
					"\t|\t cVaR  | " + fsPnLDecompositionExplainEntry.getKey() + " => " +
					FormatUtil.FormatDouble (fsPnLDecompositionExplainEntry.getValue(), 10, 2, 1.)
				);
			}
		}

		System.out.println ("\t|---------------------------------------------------------||");

		if (null != gsstGrossPnLExplainMap)
		{
			for (Map.Entry<String, Double> gsstGrossPnLExplainEntry : gsstGrossPnLExplainMap.entrySet())
			{
				System.out.println (
					"\t|\t Gross  GSST | " + gsstGrossPnLExplainEntry.getKey() + " => " +
					FormatUtil.FormatDouble (gsstGrossPnLExplainEntry.getValue(), 10, 2, 1.)
				);
			}
		}

		System.out.println (
			"\t|\t GSST         => " +
			FormatUtil.FormatDouble (pnlAttribution.gsstPnL(), 10, 2, 1.)
		);

		System.out.println (
			"\t|\t Gross cBSST  => " +
			FormatUtil.FormatDouble (pnlAttribution.cBSSTPnL(), 10, 2, 1.)
		);

		System.out.println (
			"\t|\t Gross iBSST  => " +
			FormatUtil.FormatDouble (pnlAttribution.iBSSTGrossPnL(), 10, 2, 1.)
		);

		System.out.println (
			"\t|\t Gross cVaR   => " +
			FormatUtil.FormatDouble (pnlAttribution.fsGrossPnL(), 10, 2, 1.)
		);

		System.out.println ("\t|---------------------------------------------------------||");

		if (null != gsstInstanceCountMap)
		{
			for (Map.Entry<String, Integer> gsstInstanceCountEntry : gsstInstanceCountMap.entrySet())
			{
				System.out.println (
					"\t|\t GSST  Instance Count | " + gsstInstanceCountEntry.getKey() + " => " +
					FormatUtil.FormatDouble (gsstInstanceCountEntry.getValue(), 6, 0, 1.)
				);
			}
		}

		if (null != cBSSTInstanceCountMap)
		{
			for (Map.Entry<String, Integer> cBSSTInstanceCountEntry : cBSSTInstanceCountMap.entrySet())
			{
				System.out.println (
					"\t|\t cBSST Instance Count | " + cBSSTInstanceCountEntry.getKey() + " => " +
					FormatUtil.FormatDouble (cBSSTInstanceCountEntry.getValue(), 6, 0, 1.)
				);
			}
		}

		if (null != iBSSTInstanceCountMap)
		{
			for (Map.Entry<String, Integer> iBSSTInstanceCountEntry : iBSSTInstanceCountMap.entrySet())
			{
				System.out.println (
					"\t|\t iBSST Instance Count | " + iBSSTInstanceCountEntry.getKey() + " => " +
					FormatUtil.FormatDouble (iBSSTInstanceCountEntry.getValue(), 6, 0, 1.)
				);
			}
		}

		System.out.println ("\t|---------------------------------------------------------||");

		System.out.println();
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

	private static final EntityCapitalAssignmentSetting HighLowUniformBeta()
	{
		return Math.random() < 13. / 34. ? LowUniformBeta() : HighUniformBeta();
	}

	private static final void Allocate (
		final CapitalUnit[] capitalUnitArray,
		final EntityComponentCapitalAssignment entityComponentCapitalAssignment)
		throws Exception
	{

		double unitFloatBeta = entityComponentCapitalAssignment.unitFloatBeta();

		double allocatedBetaCapital = entityComponentCapitalAssignment.allocatedTotalCapital();

		Map<String, EntityComponentCapital> entityComponentCapitalMap =
			entityComponentCapitalAssignment.entityComponentCapitalMap();

		System.out.println (
			"\t|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------||"
		);

		System.out.println (
			"\t|                                                                         THREE BETA CAPITAL ALLOCATION                                                                            ||"
		);

		System.out.println (
			"\t|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------||"
		);

		System.out.println (
			"\t|      L -> R:                                                                                                                                                                     ||"
		);

		System.out.println (
			"\t|            - Capital Segment Allocation Fraction  - GSST                                                                                                                         ||"
		);

		System.out.println (
			"\t|            - Capital Segment Allocation Fraction  - cBSST                                                                                                                        ||"
		);

		System.out.println (
			"\t|            - Capital Segment Allocation Fraction  - iBSST                                                                                                                        ||"
		);

		System.out.println (
			"\t|            - Capital Segment Allocation Fraction  - No Stress                                                                                                                    ||"
		);

		System.out.println (
			"\t|            - Capital Segment Allocation Fraction  - Total                                                                                                                        ||"
		);

		System.out.println (
			"\t|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------||"
		);

		System.out.println (
			"\t|            - Capital Segment Allocation Absolute  - GSST                                                                                                                         ||"
		);

		System.out.println (
			"\t|            - Capital Segment Allocation Absolute  - cBSST                                                                                                                        ||"
		);

		System.out.println (
			"\t|            - Capital Segment Allocation Absolute  - iBSST                                                                                                                        ||"
		);

		System.out.println (
			"\t|            - Capital Segment Allocation Absolute  - No Stress                                                                                                                    ||"
		);

		System.out.println (
			"\t|            - Capital Segment Allocation Absolute  - Total                                                                                                                        ||"
		);

		System.out.println (
			"\t|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------||"
		);

		System.out.println (
			"\t|            - Capital Segment Coordinate                                                                                                                                          ||"
		);

		System.out.println (
			"\t|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------||"
		);

		for (int capitalUnitIndex = 0;
			capitalUnitIndex < capitalUnitArray.length;
			++capitalUnitIndex)
		{
			String capitalUnitFQN = capitalUnitArray[capitalUnitIndex].coordinate().fullyQualifiedName();

			EntityComponentCapital entityComponentCapital = entityComponentCapitalMap.get (
				capitalUnitFQN
			);

			EntityCapital entityComponentCapitalNoStress = entityComponentCapital.noStress();

			EntityCapital entityComponentCapitalTotal = entityComponentCapital.total();

			EntityCapital entityComponentCapitalIBSST = entityComponentCapital.iBSST();

			EntityCapital entityComponentCapitalCBSST = entityComponentCapital.cBSST();

			EntityCapital entityComponentCapitalGSST = entityComponentCapital.gsst();

			System.out.println (
				"\t|    " +
				FormatUtil.FormatDouble (entityComponentCapitalGSST.fractional(), 2, 2, 100.) + "% |" +
				FormatUtil.FormatDouble (entityComponentCapitalCBSST.fractional(), 2, 2, 100.) + "% |" +
				FormatUtil.FormatDouble (entityComponentCapitalIBSST.fractional(), 2, 2, 100.) + "% |" +
				FormatUtil.FormatDouble (entityComponentCapitalNoStress.fractional(), 2, 2, 100.) + "% | " +
				FormatUtil.FormatDouble (entityComponentCapitalTotal.fractional(), 2, 2, 100.) + "% || " +
				FormatUtil.FormatDouble (entityComponentCapitalGSST.absolute(), 10, 2, 1.) + " | " +
				FormatUtil.FormatDouble (entityComponentCapitalCBSST.absolute(), 10, 2, 1.) + " | " +
				FormatUtil.FormatDouble (entityComponentCapitalIBSST.absolute(), 10, 2, 1.) + " | " +
				FormatUtil.FormatDouble (entityComponentCapitalNoStress.absolute(), 10, 2, 1.) + " | " +
				FormatUtil.FormatDouble (entityComponentCapitalTotal.absolute(), 10, 2, 1.) +
					" <= " + capitalUnitFQN
			);
		}

		System.out.println (
			"\t|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------||"
		);

		EntityCapital entityCapitalGSST = entityComponentCapitalAssignment.gsst();

		EntityCapital entityCapitalCBSST = entityComponentCapitalAssignment.cBSST();

		EntityCapital entityCapitalIBSST = entityComponentCapitalAssignment.iBSST();

		EntityCapital entityCapitalNoStress = entityComponentCapitalAssignment.noStress();

		System.out.println (
			"\t|    " +
			FormatUtil.FormatDouble (entityCapitalGSST.fractional(), 2, 2, 100.) + "% |" +
			FormatUtil.FormatDouble (entityCapitalCBSST.fractional(), 2, 2, 100.) + "% |" +
			FormatUtil.FormatDouble (entityCapitalIBSST.fractional(), 2, 2, 100.) + "% |" +
			FormatUtil.FormatDouble (entityCapitalNoStress.fractional(), 2, 2, 100.) + "% |" +
			FormatUtil.FormatDouble (1., 2, 2, 100.) + "% || " +
			FormatUtil.FormatDouble (entityCapitalGSST.absolute(), 10, 2, 1.) + " | " +
			FormatUtil.FormatDouble (entityCapitalCBSST.absolute(), 10, 2, 1.) + " | " +
			FormatUtil.FormatDouble (entityCapitalIBSST.absolute(), 10, 2, 1.) + " | " +
			FormatUtil.FormatDouble (entityCapitalNoStress.absolute(), 10, 2, 1.) + " | " +
			FormatUtil.FormatDouble (entityComponentCapitalAssignment.allocatedTotalCapital(), 10, 2, 1.) +
			" <= TOTAL"
		);

		System.out.println (
			"\t|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------||"
		);

		System.out.println();

		EntityElasticityAttribution elasticityAttribution =
			entityComponentCapitalAssignment.elasticityAttribution();

		double fixedEntityCapital = elasticityAttribution.fixed();

		double floatingEntityCapital = elasticityAttribution.floating() * unitFloatBeta;

		System.out.println (
			"\t|-----------------------------------------------------------------------------||"
		);

		System.out.println (
			"\t| FIXED    Entity Capital                =>  " +
			FormatUtil.FormatDouble (fixedEntityCapital, 10, 2, 1.) + " | " +
			FormatUtil.FormatDouble (fixedEntityCapital / allocatedBetaCapital, 2, 2, 100.) + "%"
		);

		System.out.println (
			"\t| FLOATING Entity Capital                =>  " +
			FormatUtil.FormatDouble (floatingEntityCapital, 10, 2, 1.) + " | " +
			FormatUtil.FormatDouble (floatingEntityCapital / allocatedBetaCapital, 2, 2, 100.) + "%"
		);

		System.out.println (
			"\t|-----------------------------------------------------------------------------||"
		);

		System.out.println (
			"\t| Allocated Beta Capital                 =>  " +
			FormatUtil.FormatDouble (allocatedBetaCapital, 10, 2, 1.)
		);

		System.out.println (
			"\t| Unit Float Beta                        =>  " +
			FormatUtil.FormatDouble (unitFloatBeta, 1, 4, 1.)
		);

		System.out.println (
			"\t|-----------------------------------------------------------------------------||"
		);

		System.out.println();
	}

	public static final void main (
		final String[] argumentArray)
		throws Exception
	{
		EnvManager.InitEnv ("");

		double capitalUnitNotional = 10000.;
		String capitalSegmentFQN = "Municipal Securities";
		String[] regionArray =
		{
			"NORTH AMERICA",
			"NORTH AMERICA",
			"NORTH AMERICA",
		};
		String[] riskTypeArray =
		{
			"AFS",
			"CVA",
			"Trading",
		};

		CorrelationCategoryBetaManager correlationCategoryBetaManager =
			CorrelationCategoryBetaManager.TwoBetaFloatFloat (
				2.0
			);

		SimulationControl simulationControl = SimulationControl.Standard();

		SimulationPnLControl simulationPnLControl = SimulationPnLControl.Standard();

		Map<String, EntityCapitalAssignmentSetting> entityCapitalAssignmentSettingMap =
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

			entityCapitalAssignmentSettingMap.put (
				capitalUnitArray[capitalUnitIndex].coordinate().fullyQualifiedName(),
				HighLowUniformBeta()
			);
		}

		System.out.println ("\t|------------------------------------------------------------------");

		System.out.println ("\t|     3 Beta Fixed/Float/Float Segment Capital Unit Coordinates    ");

		System.out.println ("\t|------------------------------------------------------------------");

		System.out.println ("\t|    L -> R:");

		System.out.println ("\t|           - Correlation Category (1=HIGH; 2=MEDIUM; 3=LOW)");

		System.out.println ("\t|           - Beta Type (0=FIXED; 1=FLOAT)");

		System.out.println ("\t|           - Beta Value");

		System.out.println ("\t|------------------------------------------------------------------");

		for (int capitalUnitIndex = 0;
			capitalUnitIndex < riskTypeArray.length;
			++capitalUnitIndex)
		{
			String capitalUnitFQN = capitalUnitArray[capitalUnitIndex].coordinate().fullyQualifiedName();

			int capitalEntityCorrelationCategory = entityCapitalAssignmentSettingMap.get (
				capitalUnitFQN
			).allocationCorrelationCategory();

			CorrelationCategoryBeta correlationCategoryBeta =
				correlationCategoryBetaManager.correlationCategoryBeta (
					capitalEntityCorrelationCategory
				);

			System.out.println (
				"\t|    " + capitalEntityCorrelationCategory + " | " +
				correlationCategoryBeta.elasticity() + " | " +
				FormatUtil.FormatDouble (correlationCategoryBeta.loading(), 1, 1, 1.) + " <= " +
				capitalUnitFQN
			);
		}

		System.out.println ("\t|------------------------------------------------------------------");

		System.out.println ("\t|    [CAPITAL SEGMENT] => " + capitalSegmentFQN);

		System.out.println ("\t|------------------------------------------------------------------");

		System.out.println();

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

		EntityComponentCapitalAssignment entityComponentCapitalAssignment =
			capitalSegmentStandaloneMarginal.betaAllocation (
				new CapitalAllocationControl (
					false,
					null,
					correlationCategoryBetaManager,
					entityCapitalAssignmentSettingMap
				)
			);

		Map<String, EntityComponentCapital> entityComponentCapitalMap =
			entityComponentCapitalAssignment.entityComponentCapitalMap();

		Map<String, PnLAttribution> standalonePnLAttributionMap =
			capitalSegmentStandaloneMarginal.standalonePnLAttributionMap();

		for (Map.Entry<String, PnLAttribution> standalonePnLAttributionEntry :
			standalonePnLAttributionMap.entrySet())
		{
			String capitalUnitCoordinate = standalonePnLAttributionEntry.getKey();

			DisplayPnLAttribution (
				capitalUnitCoordinate,
				new AllocatedPnLAttribution (
					standalonePnLAttributionEntry.getValue(),
					entityComponentCapitalMap.get (
						capitalUnitCoordinate
					)
				)
			);
		}

		Allocate (
			capitalUnitArray,
			entityComponentCapitalAssignment
		);

		EnvManager.TerminateEnv();
	}
}
