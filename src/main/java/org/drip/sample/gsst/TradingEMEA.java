
package org.drip.sample.gsst;

import java.util.Map;

import org.drip.capital.entity.CapitalUnitEventContainer;
import org.drip.capital.env.CapitalEstimationContextManager;
import org.drip.capital.shell.CapitalUnitStressEventContext;
import org.drip.capital.stress.Event;
import org.drip.capital.stress.SystemicEventContainer;
import org.drip.capital.stress.PnLSeries;
import org.drip.capital.stress.EventSpecification;
import org.drip.numerical.common.FormatUtil;
import org.drip.service.env.EnvManager;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Quantitative Risk Analytics
 */

/**
 * <i>TradingEMEA</i> zeds the Child Coordinates and their corresponding GSST Dump - Scenario Names, Loss
 * Amount, and Probability for the following Coordinates:
 *  
 *    - REGION    == EMEA
 *    - RISK TYPE == Trading
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
 * @author Lakshmi Krishnamurthy
 */

public class TradingEMEA
{

	private static final String DisplayStressEventPnL (
		final PnLSeries stressEventPnL)
		throws Exception
	{
		String stressEventPnLDisplay = " ";

		for (double pnlOutcome : stressEventPnL.outcomeArray())
		{
			stressEventPnLDisplay = stressEventPnLDisplay +
				FormatUtil.FormatDouble (pnlOutcome, 3, 1, 1.) + " | ";
		}

		return stressEventPnLDisplay + FormatUtil.FormatDouble (stressEventPnL.composite(), 3, 1, 1.);
	}

	private static final void DisplayStressScenario (
		final Map<String, CapitalUnitEventContainer> coordinateStressScenarioMap,
		final java.lang.String coordinateFQN)
		throws Exception
	{
		SystemicEventContainer coordinateStressScenarioEvents =
			coordinateStressScenarioMap.get (coordinateFQN).systemicEventContainer();

		System.out.println ("\t|----------------------------------||");

		System.out.println (
			"\t|[" + coordinateFQN + "] => " +
			coordinateStressScenarioEvents.eventType()
		);

		System.out.println ("\t|----------------------------------||");

		System.out.println ("\t|------------------------------------------------------------------------||");

		Map<String, Event> eventMap = coordinateStressScenarioEvents.eventMap();

		for (Map.Entry<String, Event> eventMapEntry : eventMap.entrySet())
		{
			Event coordinateStressEvent = eventMapEntry.getValue();

			EventSpecification stressEvent = coordinateStressEvent.specification();

			System.out.println (
				"\t\t[Name => " + stressEvent.name() + "] | " +
				"[Probability => " + FormatUtil.FormatDouble (stressEvent.probability(), 1, 4, 1.) + "] | " +
				"[PnL => " + DisplayStressEventPnL (coordinateStressEvent.aggregatePnLSeries()) + "] ||"
			);
		}

		System.out.println ("\t|------------------------------------------------------------------------||");
	}

	public static final void main (
		final String[] argumentArray)
		throws Exception
	{
		EnvManager.InitEnv ("");

		String region = "EMEA";
		String riskType = "Trading";

		CapitalUnitStressEventContext gocStressEventContext =
			CapitalEstimationContextManager.ContextContainer().capitalUnitStressEventContext();

		Map<String, CapitalUnitEventContainer> gocEventContainerMap = gocStressEventContext.capitalUnitEventMap();

		for (String fqn : gocStressEventContext.matchingCapitalUnitCoordinateSet (
			region,
			riskType
		))
		{
			DisplayStressScenario (
				gocEventContainerMap,
				fqn
			);
		}

		EnvManager.TerminateEnv();
	}
}
