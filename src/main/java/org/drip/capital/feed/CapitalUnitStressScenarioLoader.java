
package org.drip.capital.feed;

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
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/CapitalAnalyticsLibrary.md">Capital Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/README.md">Basel Market Risk and Operational Capital</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/feed/README.md">Risk Capital Estimation - Feed Processors</a></li>
 *  </ul>
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
			return java.lang.Double.parseDouble (string);
		}
		catch (java.lang.Exception e)
		{
		}

		return 0.;
	}

	/**
	 * Load the Capital Unit Correlated Stress Scenarios
	 * 
	 * @param capitalUnitCorrelatedInputFile Capital Unit Correlated Stress Scenario Specifications File
	 * @param skipHeader TRUE - Interpret the First Row as a Header
	 * 
	 * @return The Map of Capital Unit Correlated Stress Scenarios
	 */

	public static final java.util.Map<java.lang.String, org.drip.capital.feed.CapitalUnitCorrelatedScenario>
		LoadCorrelated (
			final java.lang.String capitalUnitCorrelatedInputFile,
			final boolean skipHeader)
	{
		if (null == capitalUnitCorrelatedInputFile || capitalUnitCorrelatedInputFile.isEmpty())
		{
			return null;
		}

		boolean firstLine = true;
		java.lang.String capitalUnitCorrelatedLine = "";
		java.io.BufferedReader capitalUnitCorrelatedBufferedReader = null;

		java.util.Map<java.lang.String, org.drip.capital.feed.CapitalUnitCorrelatedScenario>
			capitalUnitCorrelatedScenarioMap = new
				org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.capital.feed.CapitalUnitCorrelatedScenario>();

		try
		{
			capitalUnitCorrelatedBufferedReader = new java.io.BufferedReader (
				new java.io.FileReader (
					capitalUnitCorrelatedInputFile
				)
			);

			while (null != (capitalUnitCorrelatedLine = capitalUnitCorrelatedBufferedReader.readLine()))
			{
				if (firstLine)
				{
					firstLine = false;

					if (skipHeader)
					{
						continue;
					}
				}

				java.lang.String[] capitalUnitCorrelatedFieldArray =
					org.drip.service.common.StringUtil.Split (
						capitalUnitCorrelatedLine,
						","
					);

				if (null == capitalUnitCorrelatedFieldArray || 9 > capitalUnitCorrelatedFieldArray.length)
				{
					return null;
				}

				capitalUnitCorrelatedScenarioMap.put (
					new org.drip.capital.label.CapitalUnitCoordinate (
						capitalUnitCorrelatedFieldArray[0],
						capitalUnitCorrelatedFieldArray[2]
					).fullyQualifiedName(),
					new org.drip.capital.feed.CapitalUnitCorrelatedScenario (
						capitalUnitCorrelatedFieldArray[1],
						capitalUnitCorrelatedFieldArray[3],
						org.drip.capital.shell.SystemicScenarioPnLSeries.SingleOutcome (
							StringToDouble (
								capitalUnitCorrelatedFieldArray[7]
							), StringToDouble (
								capitalUnitCorrelatedFieldArray[8]
							), StringToDouble (
								capitalUnitCorrelatedFieldArray[4]
							),
							0.,
							StringToDouble (
								capitalUnitCorrelatedFieldArray[6]
							),
							StringToDouble (
								capitalUnitCorrelatedFieldArray[5]
							)
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
				capitalUnitCorrelatedBufferedReader.close();
			}
			catch (java.lang.Exception e2)
			{
				capitalUnitCorrelatedBufferedReader = null;
			}
		}

		return capitalUnitCorrelatedScenarioMap;
	}

	/**
	 * Load the Capital Unit Idiosyncratic Stress Scenarios
	 * 
	 * @param capitalUnitIdiosyncraticInputFile Capital Unit Idiosyncratic Stress Scenario Specifications
	 * 	File
	 * @param skipHeader TRUE - Interpret the First Row as a Header
	 * 
	 * @return The Map of Capital Unit Idiosyncratic Stress Scenarios
	 */

	public static final java.util.Map<java.lang.String, org.drip.capital.feed.CapitalUnitIdiosyncraticScenario>
		LoadIdiosyncratic (
			final java.lang.String capitalUnitIdiosyncraticInputFile,
			final boolean skipHeader)
	{
		if (null == capitalUnitIdiosyncraticInputFile || capitalUnitIdiosyncraticInputFile.isEmpty())
		{
			return null;
		}

		boolean firstLine = true;
		java.lang.String capitalUnitIdiosyncraticLine = "";
		java.io.BufferedReader capitalUnitIdiosyncraticBufferedReader = null;

		java.util.Map<java.lang.String, org.drip.capital.feed.CapitalUnitIdiosyncraticScenario>
			capitalUnitIdiosyncraticScenarioMap = new
				org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.capital.feed.CapitalUnitIdiosyncraticScenario>();

		try
		{
			capitalUnitIdiosyncraticBufferedReader = new java.io.BufferedReader (
				new java.io.FileReader (
					capitalUnitIdiosyncraticInputFile
				)
			);

			while (null != (capitalUnitIdiosyncraticLine =
				capitalUnitIdiosyncraticBufferedReader.readLine()))
			{
				if (firstLine)
				{
					firstLine = false;

					if (skipHeader)
					{
						continue;
					}
				}

				java.lang.String[] capitalUnitIdiosyncraticFieldArray =
					org.drip.service.common.StringUtil.Split (
						capitalUnitIdiosyncraticLine,
						","
					);

				if (null == capitalUnitIdiosyncraticFieldArray ||
					6 > capitalUnitIdiosyncraticFieldArray.length)
				{
					return null;
				}

				capitalUnitIdiosyncraticScenarioMap.put (
					new org.drip.capital.label.CapitalUnitCoordinate (
						capitalUnitIdiosyncraticFieldArray[0],
						capitalUnitIdiosyncraticFieldArray[2]
					).fullyQualifiedName(),
					new org.drip.capital.feed.CapitalUnitIdiosyncraticScenario (
						capitalUnitIdiosyncraticFieldArray[1],
						capitalUnitIdiosyncraticFieldArray[3],
						StringToDouble (
							capitalUnitIdiosyncraticFieldArray[4]
						), StringToDouble (
							capitalUnitIdiosyncraticFieldArray[5]
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
				capitalUnitIdiosyncraticBufferedReader.close();
			}
			catch (java.lang.Exception e2)
			{
				capitalUnitIdiosyncraticBufferedReader = null;
			}
		}

		return capitalUnitIdiosyncraticScenarioMap;
	}

	/**
	 * Load the Capital Unit Systemic Stress Scenarios
	 * 
	 * @param capitalUnitSystemicInputFile Capital Unit Systemic Stress Scenario Specifications File
	 * @param skipHeader TRUE - Interpret the First Row as a Header
	 * 
	 * @return The Map of Capital Unit Systemic Stress Scenarios
	 */

	public static final java.util.Map<java.lang.String, org.drip.capital.shell.SystemicScenarioPnLSeriesPAA>
		LoadSystemic (
			final java.lang.String capitalUnitSystemicInputFile,
			final boolean skipHeader)
	{
		if (null == capitalUnitSystemicInputFile || capitalUnitSystemicInputFile.isEmpty())
		{
			return null;
		}

		boolean firstLine = true;
		int capitalUnitLineIndex = 0;
		int capitalUnitLineArrayCount = 6;
		java.lang.String capitalUnitSystemicLine = "";
		java.io.BufferedReader capitalUnitSystemicBufferedReader = null;
		java.lang.String[] capitalUnitLineArray = new java.lang.String[capitalUnitLineArrayCount];

		java.util.Map<java.lang.String, org.drip.capital.shell.SystemicScenarioPnLSeriesPAA>
			capitalUnitSystemicPAAMap = new
				org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.capital.shell.SystemicScenarioPnLSeriesPAA>();

		try
		{
			capitalUnitSystemicBufferedReader = new java.io.BufferedReader (
				new java.io.FileReader (
					capitalUnitSystemicInputFile
				)
			);

			while (null != (capitalUnitSystemicLine = capitalUnitSystemicBufferedReader.readLine()))
			{
				if (firstLine)
				{
					firstLine = false;

					if (skipHeader)
					{
						continue;
					}
				}

				capitalUnitLineArray[capitalUnitLineIndex++] = capitalUnitSystemicLine;

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
						java.lang.String[] capitalUnitLineSystemicFieldArray =
							org.drip.service.common.StringUtil.Split (
								capitalUnitLineArray[capitalUnitLineArrayIndex],
								","
							);

						if (null == capitalUnitLineSystemicFieldArray ||
							6 > capitalUnitLineSystemicFieldArray.length)
						{
							return null;
						}

						if (!systemicScenarioPnLPAA.addDecompositionEntry (
							capitalUnitLineSystemicFieldArray[4],
							capitalUnitLineSystemicFieldArray[3],
							StringToDouble (
								capitalUnitLineSystemicFieldArray[5]
							)
						))
						{
							return null;
						}

						if (0 == capitalUnitLineArrayIndex)
						{
							capitalUnitRiskType = capitalUnitLineSystemicFieldArray[0];
							capitalUnitID = capitalUnitLineSystemicFieldArray[1];
						}
					}

					capitalUnitSystemicPAAMap.put (
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
				capitalUnitSystemicBufferedReader.close();
			}
			catch (java.lang.Exception e2)
			{
				capitalUnitSystemicBufferedReader = null;
			}
		}

		return capitalUnitSystemicPAAMap;
	}

	/**
	 * Load the Capital Unit Stress Scenarios
	 * 
	 * @param capitalUnitCorrelatedInputFile Capital Unit Correlated Stress Scenario Specifications File
	 * @param capitalUnitIdiosyncraticInputFile Capital Unit Idiosyncratic Stress Scenario Specifications
	 * 	File
	 * @param capitalUnitSystemicInputFile Capital Unit Systemic Stress Scenario Specifications File
	 * @param skipHeader TRUE - Interpret the First Row as a Header
	 * 
	 * @return The Map of Capital Unit Stress Scenarios
	 */

	public static final org.drip.capital.shell.CapitalUnitStressEventContext LoadStressScenario (
		final java.lang.String capitalUnitCorrelatedInputFile,
		final java.lang.String capitalUnitIdiosyncraticInputFile,
		final java.lang.String capitalUnitSystemicInputFile,
		final boolean skipHeader)
	{

		java.util.Map<String, org.drip.capital.shell.SystemicScenarioPnLSeriesPAA>
			capitalUnitSystemicScenarioMap = LoadSystemic (
				capitalUnitSystemicInputFile,
				skipHeader
			);

		if (null == capitalUnitSystemicScenarioMap)
		{
			return null;
		}

		java.util.Map<String, org.drip.capital.feed.CapitalUnitCorrelatedScenario>
			capitalUnitCorrelatedScenarioMap = LoadCorrelated (
				capitalUnitCorrelatedInputFile,
				skipHeader
			);

		if (null == capitalUnitCorrelatedScenarioMap)
		{
			return null;
		}

		java.util.Map<String, org.drip.capital.feed.CapitalUnitIdiosyncraticScenario>
			capitalUnitIdiosyncraticScenarioMap = LoadIdiosyncratic (
				capitalUnitIdiosyncraticInputFile,
				skipHeader
			);

		if (null == capitalUnitIdiosyncraticScenarioMap)
		{
			return null;
		}

		org.drip.capital.shell.CapitalUnitStressEventContext capitalUnitStressEventContext =
			new org.drip.capital.shell.CapitalUnitStressEventContext();

		java.util.Set<java.lang.String> systemicStressCapitalUnitCoordinateFQNSet =
			capitalUnitSystemicScenarioMap.keySet();

		for (java.lang.String systemicStressCapitalUnitCoordinateFQN :
			systemicStressCapitalUnitCoordinateFQNSet)
		{
			org.drip.capital.shell.SystemicScenarioPnLSeriesPAA scenarioPnLPAA =
				capitalUnitSystemicScenarioMap.get (
					systemicStressCapitalUnitCoordinateFQN
				);

			if (!capitalUnitStressEventContext.addSystemic (
				systemicStressCapitalUnitCoordinateFQN,
				scenarioPnLPAA.aggregatePnL(),
				scenarioPnLPAA
			))
			{
				return null;
			}
		}

		java.util.Set<java.lang.String> correlatedScenarioCapitalUnitCoordinateFQNSet =
			capitalUnitCorrelatedScenarioMap.keySet();

		for (java.lang.String correlatedScenarioCapitalUnitCoordinateFQN :
			correlatedScenarioCapitalUnitCoordinateFQNSet)
		{
			org.drip.capital.feed.CapitalUnitCorrelatedScenario capitalUnitCorrelatedScenario =
				capitalUnitCorrelatedScenarioMap.get (
					correlatedScenarioCapitalUnitCoordinateFQN
				);

			if (!capitalUnitStressEventContext.addCorrelated (
				correlatedScenarioCapitalUnitCoordinateFQN,
				capitalUnitCorrelatedScenario.scenarioName(),
				capitalUnitCorrelatedScenario.scenarioPnL()
			))
			{
				return null;
			}
		}

		java.util.Set<java.lang.String> idiosyncraticScenarioCapitalUnitCoordinateFQNSet =
			capitalUnitIdiosyncraticScenarioMap.keySet();

		for (java.lang.String capitalUnitCoordinateFQN : idiosyncraticScenarioCapitalUnitCoordinateFQNSet)
		{
			org.drip.capital.feed.CapitalUnitIdiosyncraticScenario capitalUnitIdiosyncraticScenario =
				capitalUnitIdiosyncraticScenarioMap.get (
					capitalUnitCoordinateFQN
				);

			if (!capitalUnitStressEventContext.addIdiosyncratic (
				capitalUnitCoordinateFQN,
				capitalUnitIdiosyncraticScenario.scenarioName(),
				capitalUnitIdiosyncraticScenario.probability(),
				capitalUnitIdiosyncraticScenario.pnl()
			))
			{
				return null;
			}
		}

		return capitalUnitStressEventContext;
	}
}
