
package org.drip.capital.env;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Quantitative Risk Analytics
 */

/**
 * <i>GSSTDefinitionContextManager</i> sets up the Predictor Scenario Specification Container. The References
 * 	are:
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

public class GSSTDefinitionContextManager
{
	private static org.drip.capital.shell.PredictorScenarioSpecificationContainer
		s_PredictorScenarioSpecificationContainer = null;

	private static final boolean UpdatePredictorScenarioSpecification (
		final java.lang.String predictor,
		final java.lang.String category,
		final java.lang.String[] marketSegmentArray,
		final java.lang.String stressScenarioTypeOfChange,
		final int stressScenarioUnit,
		final double dollarDecline,
		final double lostDecade,
		final double interestRateShock,
		final double deepDownturn,
		final double historical1974FY,
		final double historical2008FY,
		final double capitalBaseline1974FY,
		final double capitalBaseline2008FY)
	{
		org.drip.capital.gsstdesign.PredictorScenarioSpecification predictorScenarioSpecification = null;

		boolean predictorAvailable = s_PredictorScenarioSpecificationContainer.containsPredictor (
			predictor
		);

		if (predictorAvailable)
		{
			predictorScenarioSpecification =
				s_PredictorScenarioSpecificationContainer.predictorScenarioSpecification (
					predictor
				);
		}
		else
		{
			try
			{
				predictorScenarioSpecification =
					new org.drip.capital.gsstdesign.PredictorScenarioSpecification (
						predictor,
						category
					);
			}
			catch (java.lang.Exception e)
			{
				e.printStackTrace();

				return false;
			}
		}

		try
		{
			org.drip.capital.gsstdesign.StressScenarioSpecification stressScenarioSpecification =
				new org.drip.capital.gsstdesign.StressScenarioSpecification (
					new org.drip.capital.gsstdesign.StressScenarioQuantification (
						stressScenarioTypeOfChange,
						stressScenarioUnit
					),
					new org.drip.capital.gsstdesign.HypotheticalScenarioDefinition (
						dollarDecline,
						lostDecade,
						interestRateShock,
						deepDownturn
					),
					new org.drip.capital.gsstdesign.HistoricalScenarioDefinition (
						historical1974FY,
						historical2008FY
					),
					new org.drip.capital.gsstdesign.CapitalBaselineDefinition (
						capitalBaseline1974FY,
						capitalBaseline2008FY
					)
				);

			for (java.lang.String marketSegment : marketSegmentArray)
			{
				if (!predictorScenarioSpecification.addStressScenarioSpecification (
					marketSegment,
					stressScenarioSpecification
				))
				{
					return false;
				}
			}
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();

			return false;
		}

		if (!predictorAvailable)
		{
			s_PredictorScenarioSpecificationContainer.addPredictorScenarioSpecification (
				predictorScenarioSpecification
			);
		}

		return true;
	}

	/**
	 * Initialize the GSST Design Context Manager
	 * 
	 * @return TRUE - The GSST Design Context Manager successfully initialized
	 */

	public static final boolean Init()
	{
		s_PredictorScenarioSpecificationContainer =
			new org.drip.capital.shell.PredictorScenarioSpecificationContainer();

		if (!UpdatePredictorScenarioSpecification (
			"GDP",
			"Macro-economic variables",
			new java.lang.String[]
			{
				org.drip.capital.gsstdesign.MarketSegment.DM,
				org.drip.capital.gsstdesign.MarketSegment.EM
			},
			org.drip.capital.gsstdesign.TypeOfChange.SCENARIO_GDP_GROWTH,
			org.drip.capital.gsstdesign.CriterionUnit.PERCENT_POINT,
			-0.8,
			-0.8,
			-1.5,
			-1.6,
			-2.3,
			-5.0,
			java.lang.Double.NaN,
			java.lang.Double.NaN
		))
		{
			return false;
		}

		if (!UpdatePredictorScenarioSpecification (
			"Unemployment",
			"Macro-economic variables",
			new java.lang.String[]
			{
				org.drip.capital.gsstdesign.MarketSegment.DM,
				org.drip.capital.gsstdesign.MarketSegment.EM
			},
			org.drip.capital.gsstdesign.TypeOfChange.PEAK_VS_CURRENT_LEVEL,
			org.drip.capital.gsstdesign.CriterionUnit.PERCENT_POINT,
			1.7,
			1.8,
			2.0,
			2.3,
			3.7,
			4.0,
			java.lang.Double.NaN,
			java.lang.Double.NaN
		))
		{
			return false;
		}

		if (!UpdatePredictorScenarioSpecification (
			"House Prices (Case/Shiller)",
			"Macro-economic variables",
			new java.lang.String[]
			{
				org.drip.capital.gsstdesign.MarketSegment.DM,
				org.drip.capital.gsstdesign.MarketSegment.EM
			},
			org.drip.capital.gsstdesign.TypeOfChange.CHANGE_VS_CURRENT,
			org.drip.capital.gsstdesign.CriterionUnit.PERCENT,
			 -7.7,
			-13.3,
			-16.0,
			-17.2,
			 -5.0,
			-20.0,
			java.lang.Double.NaN,
			java.lang.Double.NaN
		))
		{
			return false;
		}

		if (!UpdatePredictorScenarioSpecification (
			"IR Level",
			"Market factors",
			new java.lang.String[]
			{
				org.drip.capital.gsstdesign.MarketSegment.DM_LO_VOL,
				org.drip.capital.gsstdesign.MarketSegment.EM_LO_VOL
			},
			org.drip.capital.gsstdesign.TypeOfChange.CHANGE_VS_4_Q_FORWARD,
			org.drip.capital.gsstdesign.CriterionUnit.BASIS_POINT,
			  86,
			 -64,
			 175,
			-134,
			 183,
			-200,
			 150,
			-100
		))
		{
			return false;
		}

		if (!UpdatePredictorScenarioSpecification (
			"IR Level",
			"Market factors",
			new java.lang.String[]
			{
				org.drip.capital.gsstdesign.MarketSegment.EM_HI_VOL
			},
			org.drip.capital.gsstdesign.TypeOfChange.CHANGE_VS_4_Q_FORWARD,
			org.drip.capital.gsstdesign.CriterionUnit.BASIS_POINT,
			  86,
			  50,
			 275,
			 100,
			 112,
			 200,
			 112,
			 200
		))
		{
			return false;
		}

		if (!UpdatePredictorScenarioSpecification (
			"IR Slope (10y-3mo, bps)",
			"Market factors",
			new java.lang.String[]
			{
				org.drip.capital.gsstdesign.MarketSegment.DM_LO_VOL,
				org.drip.capital.gsstdesign.MarketSegment.EM_LO_VOL
			},
			org.drip.capital.gsstdesign.TypeOfChange.CHANGE_VS_4_Q_FORWARD,
			org.drip.capital.gsstdesign.CriterionUnit.BASIS_POINT,
			-124,
			 -42,
			 -50,
			-102,
			 -53,
			 129,
			-100,
			 100
		))
		{
			return false;
		}

		if (!UpdatePredictorScenarioSpecification (
			"IR Slope (10y-3mo, bps)",
			"Market factors",
			new java.lang.String[]
			{
				org.drip.capital.gsstdesign.MarketSegment.EM_HI_VOL
			},
			org.drip.capital.gsstdesign.TypeOfChange.CHANGE_VS_4_Q_FORWARD,
			org.drip.capital.gsstdesign.CriterionUnit.BASIS_POINT,
			-124,
			 -54,
			 -50,
			-109,
			-122,
			-218,
			-174,
			-218
		))
		{
			return false;
		}

		if (!UpdatePredictorScenarioSpecification (
			"Credit Spread including Munis",
			"Market factors",
			new java.lang.String[]
			{
				org.drip.capital.gsstdesign.MarketSegment.DM,
				org.drip.capital.gsstdesign.MarketSegment.EM
			},
			org.drip.capital.gsstdesign.TypeOfChange.CHANGE_AS_PERCENT_OF_CALENDAR_2008_SPREAD_WIDENING,
			org.drip.capital.gsstdesign.CriterionUnit.PERCENT,
			 30,
			 25,
			 40,
			 50,
			 56,
			100,
			 80,
			100
		))
		{
			return false;
		}

		if (!UpdatePredictorScenarioSpecification (
			"Spot FX (USD vs. all other currencies)",
			"Market factors",
			new java.lang.String[]
			{
				org.drip.capital.gsstdesign.MarketSegment.DM_LO_VOL,
				org.drip.capital.gsstdesign.MarketSegment.EM_LO_VOL
			},
			org.drip.capital.gsstdesign.TypeOfChange.CHANGE_VS_CURRENT,
			org.drip.capital.gsstdesign.CriterionUnit.PERCENT,
			-7.0,
			 4.8,
			 3.0,
			12.0,
			-8.0,
			 8.0,
			-8.0,
			 8.0
		))
		{
			return false;
		}

		if (!UpdatePredictorScenarioSpecification (
			"Spot FX (USD vs. all other currencies)",
			"Market factors",
			new java.lang.String[]
			{
				org.drip.capital.gsstdesign.MarketSegment.EM_HI_VOL
			},
			org.drip.capital.gsstdesign.TypeOfChange.CHANGE_VS_CURRENT,
			org.drip.capital.gsstdesign.CriterionUnit.PERCENT,
			-10.0,
			  4.8,
			  6.0,
			 24.0,
			 -8.0,
			 24.0,
			 -8.0,
			 24.0
		))
		{
			return false;
		}

		if (!UpdatePredictorScenarioSpecification (
			"Equity and Equity Indices",
			"Market factors",
			new java.lang.String[]
			{
				org.drip.capital.gsstdesign.MarketSegment.DM
			},
			org.drip.capital.gsstdesign.TypeOfChange.CHANGE_VS_CURRENT,
			org.drip.capital.gsstdesign.CriterionUnit.PERCENT,
			-15.1,
			-17.0,
			-25.0,
			-30.2,
			-30.0,
			-40.0,
			-25.5,
			-32.0
		))
		{
			return false;
		}

		if (!UpdatePredictorScenarioSpecification (
			"Equity and Equity Indices",
			"Market factors",
			new java.lang.String[]
			{
				org.drip.capital.gsstdesign.MarketSegment.EM
			},
			org.drip.capital.gsstdesign.TypeOfChange.CHANGE_VS_CURRENT,
			org.drip.capital.gsstdesign.CriterionUnit.PERCENT,
			-18.9,
			-21.0,
			-35.0,
			-37.8,
			-40.0,
			-55.0,
			-30.5,
			-40.0
		))
		{
			return false;
		}

		if (!UpdatePredictorScenarioSpecification (
			"Private Equity",
			"Market factors",
			new java.lang.String[]
			{
				org.drip.capital.gsstdesign.MarketSegment.DM
			},
			org.drip.capital.gsstdesign.TypeOfChange.CHANGE_VS_CURRENT,
			org.drip.capital.gsstdesign.CriterionUnit.PERCENT,
			-12.1,
			-13.6,
			-20.0,
			-24.2,
			-24.0,
			-32.0,
			java.lang.Double.NaN,
			java.lang.Double.NaN
		))
		{
			return false;
		}

		if (!UpdatePredictorScenarioSpecification (
			"Private Equity",
			"Market factors",
			new java.lang.String[]
			{
				org.drip.capital.gsstdesign.MarketSegment.EM
			},
			org.drip.capital.gsstdesign.TypeOfChange.CHANGE_VS_CURRENT,
			org.drip.capital.gsstdesign.CriterionUnit.PERCENT,
			-15.1,
			-17.0,
			-28.0,
			-30.2,
			-32.0,
			-44.0,
			java.lang.Double.NaN,
			java.lang.Double.NaN
		))
		{
			return false;
		}

		if (!UpdatePredictorScenarioSpecification (
			"Hedge Funds",
			"Market factors",
			new java.lang.String[]
			{
				org.drip.capital.gsstdesign.MarketSegment.DM,
				org.drip.capital.gsstdesign.MarketSegment.EM
			},
			org.drip.capital.gsstdesign.TypeOfChange.CHANGE_VS_CURRENT,
			org.drip.capital.gsstdesign.CriterionUnit.PERCENT,
			 -6.1,
			 -6.0,
			-10.0,
			-12.1,
			-12.0,
			-16.0,
			java.lang.Double.NaN,
			java.lang.Double.NaN
		))
		{
			return false;
		}

		if (!UpdatePredictorScenarioSpecification (
			"ABX index 07-1 AAA",
			"Market factors",
			new java.lang.String[]
			{
				org.drip.capital.gsstdesign.MarketSegment.DM,
				org.drip.capital.gsstdesign.MarketSegment.EM
			},
			org.drip.capital.gsstdesign.TypeOfChange.CHANGE_VS_CURRENT,
			org.drip.capital.gsstdesign.CriterionUnit.PERCENT,
			-17.0,
			-10.0,
			-10.8,
			-25.0,
			-15.0,
			-55.0,
			-20.0,
			-40.0
		))
		{
			return false;
		}

		if (!UpdatePredictorScenarioSpecification (
			"Energy (WTI, 1MFt)",
			"Market factors",
			new java.lang.String[]
			{
				org.drip.capital.gsstdesign.MarketSegment.DM,
				org.drip.capital.gsstdesign.MarketSegment.EM
			},
			org.drip.capital.gsstdesign.TypeOfChange.CHANGE_VS_CURRENT,
			org.drip.capital.gsstdesign.CriterionUnit.PERCENT,
			 59.6,
			-14.9,
			 20.0,
			-36.2,
			160.0,
			-55.0,
			112.0,
			-32.0
		))
		{
			return false;
		}

		if (!UpdatePredictorScenarioSpecification (
			"Natural Gas (NYMEX, 3MFt)",
			"Market factors",
			new java.lang.String[]
			{
				org.drip.capital.gsstdesign.MarketSegment.DM,
				org.drip.capital.gsstdesign.MarketSegment.EM
			},
			org.drip.capital.gsstdesign.TypeOfChange.CHANGE_VS_CURRENT,
			org.drip.capital.gsstdesign.CriterionUnit.PERCENT,
			 55.6,
			-15.6,
			 18.5,
			-31.1,
			 90.9,
			-20.0,
			 89.0,
			-28.0
		))
		{
			return false;
		}

		if (!UpdatePredictorScenarioSpecification (
			"Copper (Spot)",
			"Market factors",
			new java.lang.String[]
			{
				org.drip.capital.gsstdesign.MarketSegment.DM,
				org.drip.capital.gsstdesign.MarketSegment.EM
			},
			org.drip.capital.gsstdesign.TypeOfChange.CHANGE_VS_CURRENT,
			org.drip.capital.gsstdesign.CriterionUnit.PERCENT,
			 51.9,
			-20.0,
			 17.3,
			-40.0,
			 84.7,
			-53.0,
			 83.0,
			-36.0
		))
		{
			return false;
		}

		if (!UpdatePredictorScenarioSpecification (
			"Gold (Spot)",
			"Market factors",
			new java.lang.String[]
			{
				org.drip.capital.gsstdesign.MarketSegment.DM,
				org.drip.capital.gsstdesign.MarketSegment.EM
			},
			org.drip.capital.gsstdesign.TypeOfChange.CHANGE_VS_CURRENT,
			org.drip.capital.gsstdesign.CriterionUnit.PERCENT,
			 45.6,
			 -8.3,
			 20.0,
			-16.7,
			 72.0,
			  2.0,
			 73.0,
			-15.0
		))
		{
			return false;
		}

		if (!UpdatePredictorScenarioSpecification (
			"Corn (3MFt)",
			"Market factors",
			new java.lang.String[]
			{
				org.drip.capital.gsstdesign.MarketSegment.DM,
				org.drip.capital.gsstdesign.MarketSegment.EM
			},
			org.drip.capital.gsstdesign.TypeOfChange.CHANGE_VS_CURRENT,
			org.drip.capital.gsstdesign.CriterionUnit.PERCENT,
			 33.1,
			-11.7,
			 11.0,
			-23.3,
			 54.1,
			-14.0,
			 53.0,
			-21.0
		))
		{
			return false;
		}

		if (!UpdatePredictorScenarioSpecification (
			"Other Commodities",
			"Market factors",
			new java.lang.String[]
			{
				org.drip.capital.gsstdesign.MarketSegment.DM,
				org.drip.capital.gsstdesign.MarketSegment.EM
			},
			org.drip.capital.gsstdesign.TypeOfChange.CHANGE_VS_CURRENT,
			org.drip.capital.gsstdesign.CriterionUnit.PERCENT,
			 30,
			-10,
			 10,
			-20,
			 49,
			-29,
			 48,
			-18
		))
		{
			return false;
		}

		if (!UpdatePredictorScenarioSpecification (
			"Equity Implied Vols (VIX)",
			"Market factors",
			new java.lang.String[]
			{
				org.drip.capital.gsstdesign.MarketSegment.DM,
				org.drip.capital.gsstdesign.MarketSegment.EM
			},
			org.drip.capital.gsstdesign.TypeOfChange.VOLATILITY_POINT_CHANGE_AS_PERCENT_OF_2008_VOLATILITY_POINT_CHANGE,
			org.drip.capital.gsstdesign.CriterionUnit.VOLATILITY_POINT,
			 37.9,
			 42.6,
			 67.5,
			 75.6,
			 75.0,
			100.0,
			 63.8,
			 80.0
		))
		{
			return false;
		}

		if (!UpdatePredictorScenarioSpecification (
			"Implied Vols - Other",
			"Market factors",
			new java.lang.String[]
			{
				org.drip.capital.gsstdesign.MarketSegment.DM,
				org.drip.capital.gsstdesign.MarketSegment.EM
			},
			org.drip.capital.gsstdesign.TypeOfChange.CHANGE_VS_CURRENT,
			org.drip.capital.gsstdesign.CriterionUnit.PERCENT,
			21.5,
			16.0,
			30.0,
			33.5,
			45.0,
			50.0,
			40.0,
			25.0
		))
		{
			return false;
		}

		if (!UpdatePredictorScenarioSpecification (
			"Wholesale LGD",
			"Other",
			new java.lang.String[]
			{
				org.drip.capital.gsstdesign.MarketSegment.DM,
				org.drip.capital.gsstdesign.MarketSegment.EM
			},
			org.drip.capital.gsstdesign.TypeOfChange.NONE,
			org.drip.capital.gsstdesign.CriterionUnit.PERCENT,
			35,
			35,
			50,
			50,
			50,
			50,
			50,
			50
		))
		{
			return false;
		}

		if (!UpdatePredictorScenarioSpecification (
			"Likelihood of scenario",
			"Other",
			new java.lang.String[]
			{
				org.drip.capital.gsstdesign.MarketSegment.DM,
				org.drip.capital.gsstdesign.MarketSegment.EM
			},
			org.drip.capital.gsstdesign.TypeOfChange.NONE,
			org.drip.capital.gsstdesign.CriterionUnit.ABSOLUTE,
			0.02,
			0.02,
			0.02,
			0.02,
			0.02,
			0.02,
			0.02,
			0.02
		))
		{
			return false;
		}

		return true;
	}

	/**
	 * Retrieve the Predictor Scenario Specification Container
	 * 
	 * @return The Predictor Scenario Specification Container
	 */

	public static final org.drip.capital.shell.PredictorScenarioSpecificationContainer
		PredictorScenarioSpecificationContainer()
	{
		return s_PredictorScenarioSpecificationContainer;
	}
}
