
package org.drip.capital.feed;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Quantitative Risk Analytics
 */

/**
 * <i>CapitalUnitStressScenarioLoader</i> loads the Stress Scenario Specifications of a Capital Unit. The
 * 	References are:
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

public class CapitalUnitStressScenarioLoader
{

	@SuppressWarnings ("deprecation") private static final double StringToDouble (
		final java.lang.String string)
	{
		if (null == string|| string.isEmpty())
		{
			return 0.;
		}

		try
		{
			return new java.lang.Double (string).doubleValue();
		}
		catch (java.lang.Exception e)
		{
		}

		return 0.;
	}

	/**
	 * Load the Capital Unit cBSST Stress Scenarios
	 * 
	 * @param capitalUnitCBSSTInputFile Capital Unit cBSST Stress Scenario Specifications File
	 * @param skipHeader TRUE - Interpret the First Row as a Header
	 * 
	 * @return The Map of Capital Unit cBSST Stress Scenarios
	 */

	public static final java.util.Map<java.lang.String, org.drip.capital.feed.CapitalUnitCBSSTScenario>
		LoadCBSST (
			final java.lang.String capitalUnitCBSSTInputFile,
			final boolean skipHeader)
	{
		if (null == capitalUnitCBSSTInputFile || capitalUnitCBSSTInputFile.isEmpty())
		{
			return null;
		}

		boolean firstLine = true;
		java.lang.String capitalUnitCBSSTLine = "";
		java.io.BufferedReader capitalUnitCBSSTBufferedReader = null;

		java.util.Map<java.lang.String, org.drip.capital.feed.CapitalUnitCBSSTScenario>
			capitalUnitCBSSTScenarioMap = new
				org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.capital.feed.CapitalUnitCBSSTScenario>();

		try
		{
			capitalUnitCBSSTBufferedReader = new java.io.BufferedReader (
				new java.io.FileReader (
					capitalUnitCBSSTInputFile
				)
			);

			while (null != (capitalUnitCBSSTLine = capitalUnitCBSSTBufferedReader.readLine()))
			{
				if (firstLine)
				{
					firstLine = false;

					if (skipHeader)
					{
						continue;
					}
				}

				java.lang.String[] capitalUnitCBSSTFieldArray =
					org.drip.numerical.common.StringUtil.Split (
						capitalUnitCBSSTLine,
						","
					);

				if (null == capitalUnitCBSSTFieldArray || 9 > capitalUnitCBSSTFieldArray.length)
				{
					return null;
				}

				capitalUnitCBSSTScenarioMap.put (
					new org.drip.capital.label.CapitalUnitCoordinate (
						capitalUnitCBSSTFieldArray[0],
						capitalUnitCBSSTFieldArray[2]
					).fullyQualifiedName(),
					new org.drip.capital.feed.CapitalUnitCBSSTScenario (
						capitalUnitCBSSTFieldArray[1],
						capitalUnitCBSSTFieldArray[3],
						org.drip.capital.shell.SystemicScenarioPnLSeries.SingleOutcome (
							StringToDouble (capitalUnitCBSSTFieldArray[7]),
							StringToDouble (capitalUnitCBSSTFieldArray[8]),
							StringToDouble (capitalUnitCBSSTFieldArray[4]),
							0.,
							StringToDouble (capitalUnitCBSSTFieldArray[6]),
							StringToDouble (capitalUnitCBSSTFieldArray[5])
						)
					)
				);
			}
		}
		catch (java.lang.Exception e1)
		{
			e1.printStackTrace();
		}
		finally
		{
			try
			{
				capitalUnitCBSSTBufferedReader.close();
			}
			catch (java.lang.Exception e2)
			{
				capitalUnitCBSSTBufferedReader = null;
			}
		}

		return capitalUnitCBSSTScenarioMap;
	}

	/**
	 * Load the Capital Unit iBSST Stress Scenarios
	 * 
	 * @param capitalUnitIBSSTInputFile Capital Unit iBSST Stress Scenario Specifications File
	 * @param skipHeader TRUE - Interpret the First Row as a Header
	 * 
	 * @return The Map of Capital Unit iBSST Stress Scenarios
	 */

	public static final java.util.Map<java.lang.String, org.drip.capital.feed.CapitalUnitIBSSTScenario>
		LoadIBSST (
			final java.lang.String capitalUnitIBSSTInputFile,
			final boolean skipHeader)
	{
		if (null == capitalUnitIBSSTInputFile || capitalUnitIBSSTInputFile.isEmpty())
		{
			return null;
		}

		boolean firstLine = true;
		java.lang.String capitalUnitIBSSTLine = "";
		java.io.BufferedReader capitalUnitIBSSTBufferedReader = null;

		java.util.Map<java.lang.String, org.drip.capital.feed.CapitalUnitIBSSTScenario>
			capitalUnitIBSSTScenarioMap = new
				org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.capital.feed.CapitalUnitIBSSTScenario>();

		try
		{
			capitalUnitIBSSTBufferedReader = new java.io.BufferedReader (
				new java.io.FileReader (
					capitalUnitIBSSTInputFile
				)
			);

			while (null != (capitalUnitIBSSTLine = capitalUnitIBSSTBufferedReader.readLine()))
			{
				if (firstLine)
				{
					firstLine = false;

					if (skipHeader)
					{
						continue;
					}
				}

				java.lang.String[] capitalUnitIBSSTFieldArray =
					org.drip.numerical.common.StringUtil.Split (
						capitalUnitIBSSTLine,
						","
					);

				if (null == capitalUnitIBSSTFieldArray || 6 > capitalUnitIBSSTFieldArray.length)
				{
					return null;
				}

				capitalUnitIBSSTScenarioMap.put (
					new org.drip.capital.label.CapitalUnitCoordinate (
						capitalUnitIBSSTFieldArray[0],
						capitalUnitIBSSTFieldArray[2]
					).fullyQualifiedName(),
					new org.drip.capital.feed.CapitalUnitIBSSTScenario (
						capitalUnitIBSSTFieldArray[1],
						capitalUnitIBSSTFieldArray[3],
						StringToDouble (capitalUnitIBSSTFieldArray[4]),
						StringToDouble (capitalUnitIBSSTFieldArray[5])
					)
				);
			}
		}
		catch (java.lang.Exception e1)
		{
			e1.printStackTrace();
		}
		finally
		{
			try
			{
				capitalUnitIBSSTBufferedReader.close();
			}
			catch (java.lang.Exception e2)
			{
				capitalUnitIBSSTBufferedReader = null;
			}
		}

		return capitalUnitIBSSTScenarioMap;
	}

	/**
	 * Load the Capital Unit GSST Stress Scenarios
	 * 
	 * @param capitalUnitGSSTInputFile Capital Unit GSST Stress Scenario Specifications File
	 * @param skipHeader TRUE - Interpret the First Row as a Header
	 * 
	 * @return The Map of Capital Unit GSST Stress Scenarios
	 */

	public static final java.util.Map<java.lang.String, org.drip.capital.shell.SystemicScenarioPnLSeriesPAA>
		LoadGSST (
			final java.lang.String capitalUnitGSSTInputFile,
			final boolean skipHeader)
	{
		if (null == capitalUnitGSSTInputFile || capitalUnitGSSTInputFile.isEmpty())
		{
			return null;
		}

		boolean firstLine = true;
		int capitalUnitLineIndex = 0;
		int capitalUnitLineArrayCount = 6;
		java.lang.String capitalUnitGSSTLine = "";
		java.io.BufferedReader capitalUnitGSSTBufferedReader = null;
		java.lang.String[] capitalUnitLineArray = new java.lang.String[capitalUnitLineArrayCount];

		java.util.Map<java.lang.String, org.drip.capital.shell.SystemicScenarioPnLSeriesPAA>
			capitalUnitGSSTPAAMap = new
				org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.capital.shell.SystemicScenarioPnLSeriesPAA>();

		try
		{
			capitalUnitGSSTBufferedReader = new java.io.BufferedReader (
				new java.io.FileReader (
					capitalUnitGSSTInputFile
				)
			);

			while (null != (capitalUnitGSSTLine = capitalUnitGSSTBufferedReader.readLine()))
			{
				if (firstLine)
				{
					firstLine = false;

					if (skipHeader)
					{
						continue;
					}
				}

				capitalUnitLineArray[capitalUnitLineIndex++] = capitalUnitGSSTLine;

				if (capitalUnitLineArrayCount == capitalUnitLineIndex)
				{
					capitalUnitLineIndex = 0;
					java.lang.String capitalUnitID = "";
					java.lang.String capitalUnitRiskType = "";

					org.drip.capital.shell.SystemicScenarioPnLSeriesPAA systemicScenarioPnLPAA =
						new org.drip.capital.shell.SystemicScenarioPnLSeriesPAA();

					for (int capitalUnitLineArrayIndex = 0;
						capitalUnitLineArrayIndex < capitalUnitLineArrayCount;
						++capitalUnitLineArrayIndex)
					{
						java.lang.String[] capitalUnitLineCSSTFieldArray =
							org.drip.numerical.common.StringUtil.Split (
								capitalUnitLineArray[capitalUnitLineArrayIndex],
								","
							);

						if (null == capitalUnitLineCSSTFieldArray || 6 > capitalUnitLineCSSTFieldArray.length)
						{
							return null;
						}

						if (!systemicScenarioPnLPAA.addDecompositionEntry (
							capitalUnitLineCSSTFieldArray[4],
							capitalUnitLineCSSTFieldArray[3],
							StringToDouble (capitalUnitLineCSSTFieldArray[5])
						))
						{
							return null;
						}

						if (0 == capitalUnitLineArrayIndex)
						{
							capitalUnitRiskType = capitalUnitLineCSSTFieldArray[0];
							capitalUnitID = capitalUnitLineCSSTFieldArray[1];
						}
					}

					capitalUnitGSSTPAAMap.put (
						new org.drip.capital.label.CapitalUnitCoordinate (
							capitalUnitID,
							capitalUnitRiskType
						).fullyQualifiedName(),
						systemicScenarioPnLPAA
					);
				}
			}
		}
		catch (java.lang.Exception e1)
		{
			e1.printStackTrace();
		}
		finally
		{
			try
			{
				capitalUnitGSSTBufferedReader.close();
			}
			catch (java.lang.Exception e2)
			{
				capitalUnitGSSTBufferedReader = null;
			}
		}

		return capitalUnitGSSTPAAMap;
	}

	/**
	 * Load the Capital Unit Stress Scenarios
	 * 
	 * @param capitalUnitCBSSTInputFile Capital Unit cBSST Stress Scenario Specifications File
	 * @param capitalUnitIBSSTInputFile Capital Unit iBSST Stress Scenario Specifications File
	 * @param capitalUnitGSSTInputFile Capital Unit GSST Stress Scenario Specifications File
	 * @param skipHeader TRUE - Interpret the First Row as a Header
	 * 
	 * @return The Map of Capital Unit Stress Scenarios
	 */

	public static final org.drip.capital.shell.CapitalUnitStressEventContext LoadStressScenario (
		final java.lang.String capitalUnitCBSSTInputFile,
		final java.lang.String capitalUnitIBSSTInputFile,
		final java.lang.String capitalUnitGSSTInputFile,
		final boolean skipHeader)
	{

		java.util.Map<String, org.drip.capital.shell.SystemicScenarioPnLSeriesPAA> capitalUnitGSSTScenarioMap =
			LoadGSST (
				capitalUnitGSSTInputFile,
				skipHeader
			);

		if (null == capitalUnitGSSTScenarioMap)
		{
			return null;
		}

		java.util.Map<String, org.drip.capital.feed.CapitalUnitCBSSTScenario> capitalUnitCBSSTScenarioMap =
			LoadCBSST (
				capitalUnitCBSSTInputFile,
				skipHeader
			);

		if (null == capitalUnitCBSSTScenarioMap)
		{
			return null;
		}

		java.util.Map<String, org.drip.capital.feed.CapitalUnitIBSSTScenario> capitalUnitIBSSTScenarioMap =
			LoadIBSST (
				capitalUnitIBSSTInputFile,
				skipHeader
			);

		if (null == capitalUnitIBSSTScenarioMap)
		{
			return null;
		}

		org.drip.capital.shell.CapitalUnitStressEventContext capitalUnitStressEventContext =
			new org.drip.capital.shell.CapitalUnitStressEventContext();

		java.util.Set<java.lang.String> gsstCapitalUnitCoordinateFQNSet =
			capitalUnitGSSTScenarioMap.keySet();

		for (java.lang.String gsstCapitalUnitCoordinateFQN : gsstCapitalUnitCoordinateFQNSet)
		{
			org.drip.capital.shell.SystemicScenarioPnLSeriesPAA scenarioPnLPAA = capitalUnitGSSTScenarioMap.get (
				gsstCapitalUnitCoordinateFQN
			);

			org.drip.capital.shell.SystemicScenarioPnLSeries aggregateGSSTPnL = scenarioPnLPAA.aggregatePnL();

			if (!capitalUnitStressEventContext.addGSST (
				gsstCapitalUnitCoordinateFQN,
				aggregateGSSTPnL,
				scenarioPnLPAA
			))
			{
				return null;
			}
		}

		java.util.Set<java.lang.String> cBSSTCapitalUnitCoordinateFQNSet =
			capitalUnitCBSSTScenarioMap.keySet();

		for (java.lang.String cBSSTCapitalUnitCoordinateFQN : cBSSTCapitalUnitCoordinateFQNSet)
		{
			org.drip.capital.feed.CapitalUnitCBSSTScenario capitalUnitCBSSTScenario =
				capitalUnitCBSSTScenarioMap.get (
					cBSSTCapitalUnitCoordinateFQN
				);

			org.drip.capital.shell.SystemicScenarioPnLSeries cBSSTScenarioPnL =
				capitalUnitCBSSTScenario.scenarioPnL();

			if (!capitalUnitStressEventContext.addCBSST (
				cBSSTCapitalUnitCoordinateFQN,
				capitalUnitCBSSTScenario.scenarioName(),
				cBSSTScenarioPnL
			))
			{
				return null;
			}
		}

		java.util.Set<java.lang.String> iBSSTCapitalUnitCoordinateFQNSet =
			capitalUnitIBSSTScenarioMap.keySet();

		for (java.lang.String capitalUnitCoordinateFQN : iBSSTCapitalUnitCoordinateFQNSet)
		{
			org.drip.capital.feed.CapitalUnitIBSSTScenario capitalUnitIBSSTScenario =
				capitalUnitIBSSTScenarioMap.get (
					capitalUnitCoordinateFQN
				);

			if (!capitalUnitStressEventContext.addIBSST (
				capitalUnitCoordinateFQN,
				capitalUnitIBSSTScenario.scenarioName(),
				capitalUnitIBSSTScenario.probability(),
				capitalUnitIBSSTScenario.pnl()
			))
			{
				return null;
			}
		}

		return capitalUnitStressEventContext;
	}
}
