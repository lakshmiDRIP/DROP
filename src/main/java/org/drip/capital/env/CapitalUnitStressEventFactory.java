
package org.drip.capital.env;

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
 * <i>CapitalUnitStressEventFactory</i> instantiates the Built-in GSST, iBSST, and cBSST Events at the
 * Capital Unit Coordinate Level. The References are:
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
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/env/README.md">Economic Risk Capital Parameter Factories</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class CapitalUnitStressEventFactory
{

	private static final org.drip.capital.shell.SystemicScenarioPnLSeries SystemicPnLSeries (
		final double baseline1974T0,
		final double baseline2008T0,
		final double deepDownturnT0,
		final double dollarDeclineT0,
		final double interestRateShockT0,
		final double lostDecadeT0,
		final double baseline1974TMinus1,
		final double baseline2008TMinus1,
		final double deepDownturnTMinus1,
		final double dollarDeclineTMinus1,
		final double interestRateShockTMinus1,
		final double lostDecadeTMinus1,
		final double baseline1974TMinus2,
		final double baseline2008TMinus2,
		final double deepDownturnTMinus2,
		final double dollarDeclineTMinus2,
		final double interestRateShockTMinus2,
		final double lostDecadeTMinus2)
		throws java.lang.Exception
	{
		return new org.drip.capital.shell.SystemicScenarioPnLSeries (
			new org.drip.capital.stress.PnLSeries ( // 1974 Baseline
				new double[]
				{
					baseline1974T0,
					baseline1974TMinus1,
					baseline1974TMinus2,
				}
			),
			new org.drip.capital.stress.PnLSeries ( // 2008 Baseline
				new double[]
				{
					baseline2008T0,
					baseline2008TMinus1,
					baseline2008TMinus2,
				}
			),
			new org.drip.capital.stress.PnLSeries ( // Deep Down-turn
				new double[]
				{
					deepDownturnT0,
					deepDownturnTMinus1,
					deepDownturnTMinus2,
				}
			),
			new org.drip.capital.stress.PnLSeries ( // Dollar Decline
				new double[]
				{
					dollarDeclineT0,
					dollarDeclineTMinus1,
					dollarDeclineTMinus2,
				}
			),
			new org.drip.capital.stress.PnLSeries ( // Interest Rate Shock
				new double[]
				{
					interestRateShockT0,
					interestRateShockTMinus1,
					interestRateShockTMinus2,
				}
			),
			new org.drip.capital.stress.PnLSeries ( // Lost Decade
				new double[]
				{
					lostDecadeT0,
					lostDecadeTMinus1,
					lostDecadeTMinus2,
				}
			)
		);
	}

	private static boolean LoadGSST (
		final org.drip.capital.shell.CapitalUnitStressEventContext capitalUnitStressEventContext)
	{
		try
		{
			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.CLP +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.ASIA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.AFS,
				SystemicPnLSeries (
					 -458380.0,
					 -622688.5,
					 -322792.0,
					 -195051.9,
					 -259148.0,
					 -162833.5,
					 -621498.0,
					 -841960.0,
					 -437867.0,
					 -264705.0,
					 -351611.0,
					 -221006.4,
					 -808363.0,
					-1091459.5,
					 -569845.0,
					 -344677.0,
					 -457714.0,
					 -287815.0
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.COMMODITIES_HOUSTON +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.ASIA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.AFS,
				SystemicPnLSeries (
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.CONSUMER_OTHER +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.ASIA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.AFS,
				SystemicPnLSeries (
					-6730.0,
					-6737.5,
					-1475.6,
					 -885.8,
					 -180.5,
					 -738.2,
					-6772.2,
					-6779.2,
					-1484.9,
					 -891.3,
					 -181.6,
					 -742.9,
					-7057.8,
					-7064.6,
					-1547.5,
					 -928.9,
					 -189.2,
					 -774.2
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.CORPORATE_CENTER +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.ASIA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.AFS,
				SystemicPnLSeries (
					-10413958.1,
					-12643473.7,
					 -6111907.7,
					 -3699993.8,
					 -4941859.6,
					 -3090236.1,
					-11388502.3,
					-13963597.0,
					 -7058627.2,
					 -4279168.5,
					 -5696352.6,
					 -3575246.2,
					 -8695801.0,
					-10462885.3,
					 -5052794.5,
					 -3062428.0,
					 -4090034.6,
					 -2558518.8
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.CREDIT_MARKETS +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.ASIA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.AFS,
				SystemicPnLSeries (
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.EM_CREDIT_TRADING +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.ASIA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.AFS,
				SystemicPnLSeries (
					-34317398.1,
					-45600125.8,
					-25002299.5,
					-15235061.4,
					-20156367.8,
					-12745559.6,
					-43861543.1,
					-58103919.0,
					-32200933.7,
					-19653694.3,
					-25980883.9,
					-16449008.6,
					-35610433.6,
					-46998714.9,
					-26060450.5,
					-15906054.9,
					-21026623.9,
					-13312508.6
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.GTS +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.ASIA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.AFS,
				SystemicPnLSeries (
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.IG_PRIMARY_LOANS +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.ASIA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.AFS,
				SystemicPnLSeries (
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.LOCAL_MARKETS +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.ASIA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.AFS,
				SystemicPnLSeries (
					-82136410.3,
					-90726176.9,
					-32353725.0,
					-19618464.1,
					-19885276.9,
					-16392501.5,
					-90952651.6,
					-99402253.7,
					-35194006.6,
					-21351743.6,
					-20469082.7,
					-17843199.1,
					-84997782.2,
					-94243925.6,
					-34641365.7,
					-21026042.1,
					-21660224.4,
					-17573025.6
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.OS_B +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.ASIA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.AFS,
				SystemicPnLSeries (
					  -6206.2,
					  -4475.7,
					  -4073.8,
					  -1651.1,
					  -3549.6,
					  -1608.8,
					-123211.1,
					 -85401.1,
					 -86244.3,
					 -34897.0,
					 -77158.4,
					 -34162.5,
					-106504.1,
					 -84448.6,
					 -58038.3,
					 -23652.1,
					 -46126.3,
					 -22689.2
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.RETAIL_BANKING +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.ASIA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.AFS,
				SystemicPnLSeries (
					-4622452.8,
					-5746897.6,
					-3248344.8,
					-1990094.1,
					-2619570.7,
					-1667161.6,
					-5180559.7,
					-6423446.0,
					-3639831.3,
					-2230880.4,
					-2935604.5,
					-1869077.3,
					-5652661.7,
					-6989699.5,
					-3974072.9,
					-2436903.4,
					-3206028.0,
					-2041934.2
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.RISK_TREASURY +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.ASIA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.AFS,
				SystemicPnLSeries (
					-85727015.9,
					 48713603.2,
					 60347166.3,
					-90074829.1,
					-73463937.4,
					 32156075.8,
					-90028051.3,
					 52076698.0,
					 59307233.8,
					-92159471.2,
					-71998547.7,
					 31722683.5,
					-79551036.7,
					 45098570.1,
					 51128995.3,
					-81648772.4,
					-63602859.6,
					 27369076.4
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.SECURITIZED_MARKETS +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.ASIA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.AFS,
				SystemicPnLSeries (
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.ADVISORY +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.EMEA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.AFS,
				SystemicPnLSeries (
					     0.0,
					     0.0,
					     0.0,
				         0.0,
				         0.0,
					     0.0,
					-22330.3,
					-24291.1,
					-10671.7,
					 -6443.9,
					   -26.3,
					 -5378.5,
					 68124.1,
					 73937.4,
					 32589.3,
					 19685.4,
					    80.4,
					 16432.2
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.CLP +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.EMEA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.AFS,
				SystemicPnLSeries (
					-101511126.,
					-107023839.,
					 -73272213.,
					 -46305166.,
					 -60138293.,
					 -39108668.,
					 -342566113,
					 -320548916,
					 -257619530,
					 -167060830,
					 -214161084,
					 -142031208,
					 -151791557,
					 -146757493,
					 -112508175,
					  -72637985,
					  -93313967,
					  -61694628
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.COMMODITIES_HOUSTON +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.EMEA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.AFS,
				SystemicPnLSeries (
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.CORPORATE_CENTER +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.EMEA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.AFS,
				SystemicPnLSeries (
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.EM_CREDIT_TRADING +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.EMEA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.AFS,
				SystemicPnLSeries (
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.G10_FX +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.EMEA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.AFS,
				SystemicPnLSeries (
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.G10_RATES +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.EMEA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.AFS,
				SystemicPnLSeries (
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.GTS +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.EMEA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.AFS,
				SystemicPnLSeries (
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.IG_PRIMARY_LOANS +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.EMEA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.AFS,
				SystemicPnLSeries (
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.INTERNATIONAL_RETAIL_BANKING +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.EMEA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.AFS,
				SystemicPnLSeries (
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.LEVERAGED_FINANCE +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.EMEA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.AFS,
				SystemicPnLSeries (
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0,
					0.0
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.LOCAL_MARKETS +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.EMEA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.AFS,
				SystemicPnLSeries (
					-142747608.2,
					-162255158.7,
					 -74895263.4,
					 -45585817.8,
					 -47397590.3,
					 -38126081.5,
					-140963977.0,
					-160569178.1,
					 -74879217.2,
					 -45592381.4,
					 -47734057.0,
					 -38135028.5,
					-109323283.1,
					-122440413.9,
					 -53329943.0,
					 -32386760.8,
					 -29698265.2,
					 -27071526.7
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.LONG_TERM_ASSET_GROUP +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.EMEA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.AFS,
				SystemicPnLSeries (
					183522.0,
					183042.5,
					128251.0,
					 80931.6,
					105206.0,
					 68311.5,
					185552.0,
					184587.0,
					129749.0,
					 81916.4,
					106460.0,
					 69151.2,
					186490.0,
					186004.0,
					130331.0,
					 82247.9,
					106914.0,
					 69423.5
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.OS_B +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.EMEA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.AFS,
				SystemicPnLSeries (
					     0.,
					     0.,
					     0.,
					     0.,
					     0.,
				         0.,
					-65643.6,
					-68572.0,
					-26507.3,
					-15952.2,
					  -143.4,
					-13303.5,
					     0.,
					     0.,
					     0.,
					     0.,
					     0.,
					     0.
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.RISK_TREASURY +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.EMEA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.AFS,
				SystemicPnLSeries (
					  46773575.3,
					-106639327.2,
					 -84618233.2,
					  87173562.8,
					  63606330.0,
					 -44704377.5,
					  60055776.2,
					-122814499.6,
					 -92089126.1,
					 105022053.0,
					  68042495.6,
					 -48915213.4,
					  34546876.1,
					-117318713.5,
					 -70141726.0,
					  83977958.9,
					  41868691.8,
					 -37657287.4
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.SECURITIZED_MARKETS +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.EMEA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.AFS,
				SystemicPnLSeries (
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.ADVISORY +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.LATIN_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.AFS,
				SystemicPnLSeries (
					-52313639.1,
					-54697073.0,
					-40882710.1,
					-26062226.3,
					-33724382.1,
					-22055163.6,
					  -897005.8,
					  -892785.6,
					  -183027.1,
					  -109830.1,
					   -25178.9,
					   -91528.0,
					 72699789.9,
					 81176781.2,
					 56462638.7,
					 35669528.9,
					 46375015.7,
					 30117604.4
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.CLP +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.LATIN_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.AFS,
				SystemicPnLSeries (
					-55750468.9,
					-65078261.0,
					-42267690.1,
					-26464319.4,
					-36086155.6,
					-22295708.6,
					-56375049.1,
					-65650971.4,
					-42753346.3,
					-26780229.3,
					-36517604.7,
					-22564386.8,
					-60297729.8,
					-70081708.7,
					-45738819.0,
					-28661145.6,
					-39084024.6,
					-24151606.0
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.EM_CREDIT_TRADING +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.LATIN_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.AFS,
				SystemicPnLSeries (
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.EQUITY_DERIVATIVES +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.LATIN_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.AFS,
				SystemicPnLSeries (
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.GTS +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.LATIN_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.AFS,
				SystemicPnLSeries (
					-47269660.4,
					-49523549.2,
					-37027432.9,
					-23707208.7,
					-30589483.2,
					-20087850.1,
					-48559397.1,
					-50774923.5,
					-38050036.8,
					-24371634.5,
					-31440421.9,
					-20652991.2,
					-50546027.0,
					-52806007.7,
					-39613346.7,
					-25378357.8,
					-32735570.0,
					-21507333.2
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.LEVERAGED_FINANCE +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.LATIN_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.AFS,
				SystemicPnLSeries (
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.LOCAL_MARKETS +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.LATIN_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.AFS,
				SystemicPnLSeries (
					-307303791.7,
					-354305740.0,
					-186599272.3,
					-114752856.5,
					-137021658.1,
					 -96228524.2,
					-364733065.9,
					-413340252.7,
					-232218823.2,
					-143892426.3,
					-173802360.7,
					-120899946.4,
					-406999809.5,
					-458934683.4,
					-270190833.3,
					-168175806.8,
					-208359798.0,
					-141466561.8
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.OS_B +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.LATIN_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.AFS,
				SystemicPnLSeries (
					3.5,
					3.5,
					1.1,
					0.7,
					0.1,
					0.6,
					-18537.9,
					-25155.1,
					-13967.3,
					 -8530.2,
					-11275.5,
					 -7140.4,
					15.1,
					15.1,
					 4.7,
					 2.9,
					 0.2,
					 2.4
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.RETAIL_BANKING +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.LATIN_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.AFS,
				SystemicPnLSeries (
					-16293466.0,
					-17269254.3,
					 -7315130.2,
					 -4543037.1,
					 -4557942.2,
					 -3820493.2,
					-15533335.3,
					-16503636.8,
					 -6997632.0,
					 -4343298.9,
					 -4373855.3,
					 -3651958.0,
					-16140931.9,
					-17168201.9,
					 -7304578.5,
					 -4533596.2,
					 -4582199.6,
					 -3811917.3
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.AI +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.NORTH_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.AFS,
				SystemicPnLSeries (
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.CAPITAL_MARKETS_ORGANIZATION +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.NORTH_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.AFS,
					SystemicPnLSeries (
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.CLP +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.NORTH_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.AFS,
				SystemicPnLSeries (
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.COMMODITIES_HOUSTON +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.NORTH_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.AFS,
				SystemicPnLSeries (
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.CORPORATE_CENTER +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.NORTH_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.AFS,
				SystemicPnLSeries (
					-1237872371.0,
					-1518130363.0,
					 -743564055.0,
					 -447574759.5,
					 -709523495.4,
					 -373289326.7,
					-1204680571.0,
					-1475126600.0,
					 -721335237.9,
					 -434307352.6,
					 -694722535.3,
					 -362248550.1,
					-1338096362.0,
					-1641343438.0,
					 -803838590.5,
					 -483830046.0,
					 -766297412.2,
					 -403521673.6
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.EQUITY_DERIVATIVES +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.NORTH_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.AFS,
				SystemicPnLSeries (
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.GLOBAL_SECURITIZED_MARKETS +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.NORTH_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.AFS,
				SystemicPnLSeries (
					-83069.6,
					-74149.4,
					-64040.3,
					-42088.3,
					-53586.2,
					-35909.7,
					-82085.2,
					-73139.3,
					-63301.7,
					-41618.7,
					-52978.1,
					-35512.5,
					-86283.1,
					-76419.0,
					-67698.5,
					-44927.1,
					-56901.6,
					-38444.9
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.GTS +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.NORTH_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.AFS,
				SystemicPnLSeries (
					-18116.6,
					-18130.3,
					 -2505.5,
					 -1503.3,
					 -2407.9,
					 -1252.8,
					-19729.4,
					-19744.0,
					 -2728.6,
					 -1637.2,
					 -2622.3,
					 -1364.3,
					-20755.8,
					-20770.7,
					 -2870.5,
					 -1722.4,
					 -2758.7,
					 -1435.3
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.LEVERAGED_FINANCE +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.NORTH_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.AFS,
				SystemicPnLSeries (
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.LOAN_PORTFOLIO_MANAGEMENT +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.NORTH_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.AFS,
				SystemicPnLSeries (
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.LOCAL_MARKETS +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.NORTH_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.AFS,
				SystemicPnLSeries (
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.LONG_TERM_ASSET_GROUP +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.NORTH_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.AFS,
				SystemicPnLSeries (
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.MUNICIPAL_SECURITIES +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.NORTH_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.AFS,
				SystemicPnLSeries (
					-0.3,
					-0.3,
					-0.2,
					-0.1,
					-0.2,
					-0.1,
					-0.3,
					-0.3,
					-0.2,
					-0.1,
					-0.2,
					-0.1,
					-0.3,
					-0.3,
					-0.2,
					-0.1,
					-0.2,
					-0.1
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.MUNICIPAL +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.NORTH_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.AFS,
				SystemicPnLSeries (
					-679876938.9,
					-799473945.9,
					-511058362.0,
					-319268630.9,
					-419061324.4,
					-268197973.0,
					 -857752370.0,
					-1009228879.0,
					 -646305287.4,
					 -400611632.4,
					 -527526605.1,
					 -335961297.0,
					-702151002.4,
					-833512924.4,
					-511354339.4,
					-312045217.9,
					-413712457.8,
					-260735585.2
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.OS_B +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.NORTH_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.AFS,
				SystemicPnLSeries (
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					6.9,
					9.1,
					4.9,
					3.0,
					3.9,
					2.5
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.OTHER_FI_UNDERWRITING +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.NORTH_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.AFS,
				SystemicPnLSeries (
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.PRIME_FINANCE +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.NORTH_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.AFS,
				SystemicPnLSeries (
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.PRIMERICA_FINANCIAL_SERVICES +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.NORTH_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.AFS,
				SystemicPnLSeries (
					-183696280.3,
					-208904274.9,
					-132435093.6,
					 -82857708.2,
					-108580773.2,
					 -69810055.3,
					-191798334.1,
					-217548060.1,
					-138341445.0,
					 -86613315.1,
					-113445692.1,
					 -72989530.7,
					-206954904.5,
					-232301232.8,
					-150019033.8,
					 -94353868.1,
					-123267518.9,
					 -79619156.2
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.PROJECT_FINANCE +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.NORTH_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.AFS,
				SystemicPnLSeries (
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.RETAIL_BANKING +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.NORTH_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.AFS,
				SystemicPnLSeries (
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.RISK_TREASURY +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.NORTH_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.AFS,
				SystemicPnLSeries (
					-126711028.6,
					  29077949.6,
					 240726519.1,
					 -44247610.7,
					-310443626.6,
					 130518863.8,
					 -84989811.7,
					  -8128945.9,
					 223313025.9,
					   2756285.2,
					-300378687.2,
					 120841940.7,
					-164197156.2,
					  26216575.3,
					 260173056.3,
					 -68994138.4,
					-347397866.8,
					 140223587.6
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.SECURITIZED_MARKETS +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.NORTH_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.AFS,
				SystemicPnLSeries (
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.COMMODITIES_HOUSTON +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.ASIA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.CVA,
				SystemicPnLSeries (
					-6981776.1,
					-8270921.6,
					-4981287.4,
					-3144685.4,
					-3623408.7,
					-2664817.9,
					-7397008.7,
					-8762791.7,
					-5277569.0,
					-3331733.1,
					-3838901.8,
					-2823332.8,
					-7974964.8,
					-9447483.4,
					-5689872.2,
					-3592008.1,
					-4138818.0,
					-3043891.1
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.CREDIT_MARKETS +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.ASIA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.CVA,
				SystemicPnLSeries (
					-197351,
					-233828,
					-140764,
					 -88855,
					-102417,
					 -75293,
					-219805,
					-260432,
					-156780,
					 -98964,
					-114069,
					 -83859,
					-236413,
					-280110,
					-168626,
					-106442,
					-122688,
					 -90195
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.CREDIT_TRADING +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.ASIA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.CVA,
				SystemicPnLSeries (
					-26829297,
					-31788260,
					-19136536,
					-12079542,
					-13923254,
					-10235818,
					-27532273,
					-32621185,
					-19637953,
					-12396052,
					-14288085,
					-10504024,
					-27661377,
					-32774136,
					-19730037,
					-12454171,
					-14355068,
					-10553271
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.EQUITY_DERIVATIVES +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.ASIA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.CVA,
				SystemicPnLSeries (
					-673,
					-798,
					-480,
					-304,
					-350,
					-258,
					-1708,
					-2023,
					-1218,
					 -769,
					 -886,
					 -651,
					-393,
					-465,
					-280,
					-177,
					-204,
					-150
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.G10_FX +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.ASIA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.CVA,
				SystemicPnLSeries (
					-75170158.6,
					-81721164.7,
					-55750031.4,
					-31023927.5,
					-39578652.9,
					-29709677.0,
					-66746262.3,
					-72479767.8,
					-49803283.6,
					-32055613.5,
					-36926983.2,
					-27661786.1,
					-64031147.6,
					-68340160.4,
					-44727457.6,
					-26878629.4,
					-33552061.4,
					-23860268.0
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.G10_RATES +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.ASIA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.CVA,
				SystemicPnLSeries (
					-52891619.0,
					-57656673.6,
					-38118894.5,
					-24167857.0,
					-28288951.1,
					-20465058.2,
					-53991380.9,
					-58475324.2,
					-39830429.4,
					-24704311.9,
					-28261353.4,
					-21327948.0,
					-55508076.0,
					-55666225.6,
					-37104818.8,
					-26200088.3,
					-26369188.8,
					-20626127.4
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.GWM +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.ASIA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.CVA,
				SystemicPnLSeries (
					-4620031,
					-5473974,
					-3295343,
					-2080115,
					-2397591,
					-1762617,
					-4490864,
					-5320932,
					-3203194,
					-2021965,
					-2330569,
					-1713342,
					-3611289,
					-4278786,
					-2575849,
					-1625934,
					-1874117,
					-1377776
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.GWM +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.ASIA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.CVA,
				SystemicPnLSeries (
					-1581,
					-1873,
					-1127,
					 -712,
					 -820,
					 -603,
					-1155,
					-1368,
					 -823,
					 -519,
					 -599,
					 -440,
					-1084,
					-1285,
					 -773,
					 -488,
					 -563,
					 -414
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.COMMODITIES_HOUSTON +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.EMEA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.CVA,
				SystemicPnLSeries (
					-20410957.6,
					-28828928.8,
					-20150772.8,
					 -5569929.5,
					-10436885.8,
					-10708624.5,
					-14680607.8,
					-21991215.1,
					-16191039.6,
					 -3226014.2,
					 -7516912.0,
					 -8590780.9,
					 -8918233.8,
					-16675487.4,
					-13458660.8,
					   338877.6,
					 -4515855.1,
					 -7111055.6
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.CREDIT_TRADING +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.EMEA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.CVA,
				SystemicPnLSeries (
					-109042794,
					-129197629,
					 -77776976,
					 -49095094,
					 -56588553,
					 -41601640,
					-111415604,
					-132009000,
					 -79469426,
					 -50163420,
					 -57819933,
					 -42506908,
					-107238631,
					-127059988,
					 -76490109,
					 -48282800,
					 -55652278,
					 -40913334
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.EQUITY_DERIVATIVES +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.EMEA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.CVA,
				SystemicPnLSeries (
					-14165,
					-16783,
					-10103,
					 -6377,
					 -7351,
					 -5404,
					-400565,
					-474602,
					-285711,
					-180349,
					-207874,
					-152821,
					-20673,
					-24495,
					-14747,
					 -9308,
					-10728,
					 -7887
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.G10_FX +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.EMEA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.CVA,
				SystemicPnLSeries (
					-129766540.0,
					-130250785.7,
					 -61730614.1,
					 -65922381.1,
					 -50838109.7,
					 -44633017.6,
					-136280185.9,
					-145682720.9,
					 -72711611.5,
					 -68715741.2,
					 -58397837.4,
					 -48677955.3,
					 -85509386.5,
					-102850859.8,
					 -59268370.2,
					 -42380363.4,
					 -34219958.1,
					 -37302010.2
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.G10_RATES +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.EMEA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.CVA,
				SystemicPnLSeries (
					-561592182.0,
					-456454306.1,
					 144187310.3,
					-269106308.8,
					-318500909.5,
					  48179649.7,
					-563716886.1,
					-469199268.6,
					 139471598.1,
					-267917817.9,
					-331074332.9,
					  47291829.0,
					-500937309.7,
					-404260131.2,
					 188308103.4,
					-239662026.8,
					-289233661.5,
					  68871311.2
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.GWM +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.EMEA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.CVA,
				SystemicPnLSeries (
					-10768581,
					-12758984,
					 -7680914,
					 -4848410,
					 -5588432,
					 -4108397,
					 -9660658,
					-11446272,
					 -6890644,
					 -4349592,
					 -5013467,
					 -3685699,
					-10009287,
					-11859338,
					 -7139326,
					 -4506552,
					 -5194392,
					 -3818704
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.OTHER_GLOBAL_MARKETS +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.EMEA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.CVA,
				SystemicPnLSeries (
					-22950980.8,
					  8342621.6,
					 51537237.6,
					 -8532236.0,
					-36467864.1,
					 26845656.7,
					-31841529.0,
					 13644799.3,
					 54206362.8,
					-14135997.0,
					-42233226.8,
					 28392727.7,
					-37998271.4,
					 18594981.4,
					 57077272.0,
					-19350452.8,
					-37742268.0,
					 29832167.5
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.COMMODITIES_HOUSTON +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.LATIN_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.CVA,
				SystemicPnLSeries (
					-1424088,
					-1687307,
					-1015761,
					 -641177,
					 -739041,
					 -543314,
					-1457217,
					-1726559,
					-1039390,
					 -656094,
					 -756232,
					 -555951,
					-1419955,
					-1682413,
					-1012811,
					 -639317,
					 -736895,
					 -541736
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.CREDIT_TRADING +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.LATIN_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.CVA,
				SystemicPnLSeries (
					-35435787,
					-41985529,
					-25275291,
					-15954502,
					-18389664,
					-13519338,
					-38441369,
					-45546646,
					-27419081,
					-17307725,
					-19949430,
					-14666018,
					-26948137,
					-31929075,
					-19221303,
					-12133050,
					-13984935,
					-10281163
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.EQUITY_DERIVATIVES +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.LATIN_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.CVA,
				SystemicPnLSeries (
					0,
					0,
					0,
					0,
					0,
					0,
					0,
					0,
					0,
					0,
					0,
					0,
					0,
					0,
					0,
					0,
					0,
					0
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.G10_FX +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.LATIN_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.CVA,
				SystemicPnLSeries (
					 2689664.0,
					 2001011.3,
					-1360417.5,
					 8876202.5,
					 6468975.8,
					  646730.9,
					  141793.9,
					 -961944.3,
					-2883474.7,
					 7298568.0,
					 5206876.5,
					 -317686.6,
					  207592.4,
					-1210376.9,
					-3250805.8,
					 9013234.2,
					 5733940.0,
					 -146127.2
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.G10_RATES +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.LATIN_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.CVA,
				SystemicPnLSeries (
					-33978117,
					-40258435,
					-24235576,
					-15298201,
					-17633196,
					-12963209,
					-35050754,
					-41529334,
					-25000659,
					-15781144,
					-18189852,
					-13372449,
					-32517736,
					-38528121,
					-23193928,
					-14640685,
					-16875316,
					-12406052
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.OTHER_GLOBAL_MARKETS +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.LATIN_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.CVA,
				SystemicPnLSeries (
					-11712,
					-13876,
					 -8354,
					 -5273,
					 -6078,
					 -4468,
					-4274,
					-5064,
					-3049,
					-1924,
					-2218,
					-1631,
					-49,
					-58,
					-35,
					-22,
					-25,
					-19
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.COMMODITIES_HOUSTON +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.NORTH_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.CVA,
				SystemicPnLSeries (
					-35303100.6,
					-62517684.8,
					-41802052.3,
					  8474706.0,
					-14549407.8,
					-23467934.0,
					-37233079.8,
					-64459876.2,
					-42397294.8,
					  8018530.7,
					-15116887.5,
					-23708115.6,
					-29164511.0,
					-55010189.4,
					-37746070.4,
					 12305078.6,
					-10848830.9,
					-21130424.1
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.CREDIT_MARKETS +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.NORTH_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.CVA,
				SystemicPnLSeries (
					-40668,
					-48184,
					-29007,
					-18310,
					-21105,
					-15515,
					-41669,
					-49371,
					-29722,
					-18761,
					-21625,
					-15898,
					-44419,
					-52630,
					-31683,
					-19999,
					-23052,
					-16947
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.CREDIT_TRADING +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.NORTH_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.CVA,
				SystemicPnLSeries (
					-60325359.6,
					-72068737.7,
					-44279897.5,
					-27127198.1,
					-31520574.0,
					-23692960.7,
					-61274132.7,
					-73150043.8,
					-44913283.3,
					-27572904.2,
					-31993381.2,
					-24035910.5,
					-57205092.2,
					-68411880.1,
					-42191283.1,
					-25792333.4,
					-29946359.9,
					-22574043.2
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.EQUITY_DERIVATIVES +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.NORTH_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.CVA,
				SystemicPnLSeries (
					4303838.5,
					5437460.3,
					2693727.5,
					1610798.9,
					2186297.7,
					1333563.7,
					5542561.6,
					6953319.4,
					3530072.8,
					2126015.2,
					2824791.4,
					1766762.0,
					6154553.9,
					7722534.3,
					3927962.3,
					2365031.4,
					3142543.5,
					1966138.6
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.G10_FX +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.NORTH_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.CVA,
				SystemicPnLSeries (
					-43894943,
					-52008213,
					-31308933,
					-19763137,
					-22779599,
					-16746650,
					-43724061,
					-51805752,
					-31187085,
					-19686204,
					-22690912,
					-16681432,
					-36581968,
					-43343561,
					-26092828,
					-16470563,
					-18984465,
					-13956637
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.G10_RATES +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.NORTH_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.CVA,
				SystemicPnLSeries (
					10282876.8,
					29990534.3,
					76124590.1,
					  258783.3,
					21280015.2,
					33535774.2,
					-2545210.5,
					25884862.2,
					79103222.7,
					-6058207.3,
					16194465.4,
					35027855.4,
					-2795701.3,
					32248603.5,
					81119886.7,
					-5633242.1,
					20223600.1,
					36492124.9
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.GWM +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.NORTH_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.CVA,
				SystemicPnLSeries (
					-11801942,
					-13983350,
					 -8417978,
					 -5313674,
					 -6124701,
					 -4502643,
					-12579886,
					-14905072,
					 -8972858,
					 -5663927,
					 -6528417,
					 -4799433,
					 -9528766,
					-11290008,
					 -6796586,
					 -4290202,
					 -4945025,
					 -3635376
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.LONG_TERM_ASSET_GROUP +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.NORTH_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.CVA,
				SystemicPnLSeries (
					-21772,
					-25796,
					-15529,
					 -9803,
					-11299,
					 -8306,
					-20419,
					-24194,
					-14564,
					 -9194,
					-10597,
					 -7790,
					-21873,
					-25916,
					-15601,
					 -9848,
					-11351,
					 -8345
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.MUNICIPAL_SECURITIES +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.NORTH_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.CVA,
				SystemicPnLSeries (
					-68272964,
					-80892139,
					-48697068,
					-30739013,
					-35430758,
					-26047267,
					-71151039,
					-84302183,
					-50749912,
					-32034825,
					-36924357,
					-27145301,
					-78430530,
					-92927171,
					-55942162,
					-35312317,
					-40702098,
					-29922542
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.MUNICIPAL +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.NORTH_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.CVA,
				SystemicPnLSeries (
					-20764622.7,
					-19885512.6,
					 -3211184.7,
					 -8667856.8,
					-15457566.8,
					 -2290860.8,
					-22299317.1,
					-21353953.0,
					 -3745290.2,
					 -9342631.7,
					-16430052.1,
					 -2589354.8,
					-25136021.1,
					-26135002.2,
					 -8582797.1,
					-10675009.4,
					-16751227.7,
					 -5097757.7
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.OTHER_GLOBAL_MARKETS +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.NORTH_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.CVA,
				SystemicPnLSeries (
					  -763514.5,
					-17432369.8,
					-35451853.7,
					 12327106.6,
					-13972178.1,
					-16138356.6,
					 -3595445.1,
					-20678847.1,
					-37572528.3,
					 11133701.0,
					-15466573.3,
					-17261402.4,
					  1863662.0,
					-18701871.3,
					-38344291.0,
					 15136307.9,
					-14492326.2,
					-17312492.3
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.CORPORATE_CENTER +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.ASIA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.PENSION,
				SystemicPnLSeries (
					        0.0,
					 36220293.5,
					-68645747.6,
					        0.0,
					        0.0,
					-40977220.6,
					        0.0,
					 36220293.5,
					-68645747.6,
			                0.0,
			                0.0,
					-40977220.6,
					        0.0,
					 36220293.5,
					-68645747.6,
			                0.0,
			                0.0,
					-40977220.6
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.CORPORATE_CENTER +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.EMEA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.PENSION,
				SystemicPnLSeries (
					        0.0,
					292049620.2,
					355021458.5,
					        0.0,
					        0.0,
					172336248.5,
					        0.0,
					 36220293.5,
					-68645747.6,
			                0.0,
			                0.0,
					-40977220.6,
					        0.0,
					288142544.9,
					311392116.7,
			                0.0,
			                0.0,
			        148639193.2
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.CORPORATE_CENTER +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.LATIN_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.PENSION,
				SystemicPnLSeries (
					         0.0,
				    -119554630.4,
					 -78725777.5,
					         0.0,
					         0.0,
					 -51121102.7,
					         0.0,
					-117632646.5,
					 -59472301.0,
					         0.0,
					         0.0,
					 -40928831.8,
					         0.0,
					-107888382.7,
					 -69258016.3,
					         0.0,
					         0.0,
					 -46303078.5
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.CORPORATE_CENTER +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.NORTH_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.PENSION,
				SystemicPnLSeries (
			                  0.0,
					 -748731945.4,
					-1936686153.0,
			                  0.0,
					          0.0,
					-1104323586.0,
			                  0.0,
			         -761735036.0,
			        -1951934764.0,
			                  0.0,
					          0.0,
					-1113789153.0,
			                  0.0,
			         -669322979.5,
			        -1853055398.7,
			                  0.0,
					          0.0,
					-1058276603.6
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.CASH +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.ASIA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				SystemicPnLSeries (
					-185571869.3,
					-226573781.0,
					-118993555.1,
					 -65694521.2,
					-174854704.8,
					 -68205914.3,
					-126748686.0,
					-165188893.4,
					-114780111.5,
					 -50906778.0,
					-148415215.0,
					 -63609389.1,
					-150712745.4,
					-199624921.3,
					-110888175.9,
					 -43221727.9,
					-141467222.4,
					 -61508454.2
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.COMMODITIES_HOUSTON +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.ASIA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				SystemicPnLSeries (
					 15527023.5,
					 -2729778.8,
					-19117723.7,
					 83154634.2,
					 37223232.9,
					 -7647340.3,
					15763609.1,
					15116339.5,
					20658668.7,
					40292370.4,
					13952408.5,
					 8214403.0,
					-4946707.6,
					25250511.5,
					33099300.2,
					25523554.9,
					 7616241.2,
					11346775.8
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.CONVERTS +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.ASIA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				SystemicPnLSeries (
					-4538030.6,
					-4674469.9,
					-2831747.2,
					-2564725.0,
					-3073438.7,
					-1632732.3,
					-4887032.8,
					-4834248.1,
					-2782402.8,
					-2843977.5,
					-3198487.2,
					-1676559.0,
					-3043250.6,
					-3344710.2,
					-1924003.5,
					-1694907.0,
					-1905147.2,
					-1206008.6
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.CORPORATE_CENTER +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.ASIA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				SystemicPnLSeries (
					-11608846.8,
					  5077525.5,
					  1540266.3,
					-12951030.1,
					 -3215681.4,
					  1083786.2,
					 -11309675.9,
					   4566632.8,
					   1415729.3,
					 -12630062.3,
					  -3227652.9,
					   1005590.4,
					-11997100.1,
					  4759220.2,
					  1350053.9,
					-13330114.0,
					 -3474287.5,
					  1001095.8
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.CREDIT_MARKETS +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.ASIA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				SystemicPnLSeries (
					-2287735.9,
					-3757057.1,
					-7266387.3,
					-5576691.9,
					-2873682.6,
					-3756013.3,
					-2654335.5,
					-4485852.8,
					-8028249.0,
					-5621076.3,
					-3563414.7,
					-4149710.0,
					-3597693.0,
					-3592720.3,
					-6312198.3,
					-7636750.1,
					-4626917.1,
					-3236439.8
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.CREDIT_TRADING +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.ASIA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				SystemicPnLSeries (
					-29184692.5,
					-26334771.4,
					-20503806.5,
					-16798451.4,
					-20465948.6,
					-10757541.5,
					 -8843174.9,
					 -8548310.3,
					-16592507.0,
					 -7436316.0,
					 -5036481.5,
					 -8984890.7,
					 -9797047.6,
					 -3483291.0,
					 -6084978.7,
					 -8379474.1,
					-10752624.3,
					 -3940548.6
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.EM_CREDIT_TRADING +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.ASIA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				SystemicPnLSeries (
					-155638323.9,
					-149748990.9,
					 -84018224.6,
					 -79726235.4,
					 -98511208.3,
					 -48750128.2,
					-187216631.2,
					-189810062.0,
					-112417230.2,
					 -91410070.9,
					-107576633.7,
					 -62257203.3,
					-113043689.4,
					 -94085783.5,
					 -49832232.6,
					 -56201945.9,
					 -65496382.1,
					 -28360517.1
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.EQUITY_DERIVATIVES +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.ASIA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				SystemicPnLSeries (
					124079216.7,
					188574625.3,
					 98271951.2,
					  7099071.5,
					 90662037.9,
					  2756949.3,
					149454708.5,
					211174792.6,
					102319719.4,
					 48602240.5,
					112055165.2,
					 39552553.8,
					 90388025.4,
					138432921.9,
					 77006722.9,
					 28727510.5,
					 64612760.6,
					 17894502.3
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.EQUITY_UNDERWRITING +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.ASIA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				SystemicPnLSeries (
					 -75570.3,
					 -75055.4,
					-127138.0,
					  72986.7,
					 -37181.9,
					 -30086.1,
					 -86263.5,
					 -88499.6,
					-141294.5,
					  65346.8,
					 -49160.2,
					 -38228.2,
					 -76286.0,
					 -75541.3,
					-127986.1,
					  73449.1,
					 -37433.9,
					 -30250.3
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.FINANCE +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.ASIA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				SystemicPnLSeries (
					 -5817783.2,
					  5079932.9,
					  3503857.0,
					-15743361.3,
					 -6361852.4,
					  2379811.1,
					-2984412.5,
					 2716255.4,
					  807167.1,
					-5423735.7,
					-2788349.0,
					  748226.6,
					-3845035.6,
					 3421231.9,
					 1577207.9,
					-9703261.5,
					-3610749.9,
					 1258036.2
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.G10_FX +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.ASIA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				SystemicPnLSeries (
					 55871148.8,
					-30894010.8,
					 11084652.3,
					 84351583.8,
					 -3706573.5,
					  2275592.2,
					 67056238.7,
					-33313791.2,
					  6268985.6,
					 96290771.1,
					 -1063697.8,
					  -543944.0,
					 68272617.4,
					-43119531.2,
					  7818035.6,
					 79391468.9,
					  5682948.0,
					  1673850.5
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.G10_RATES +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.ASIA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				SystemicPnLSeries (
					39190324.0,
					35846119.5,
					32838633.9,
					29408571.7,
					21259787.5,
					21777689.8,
					 68993466.9,
					 19909359.9,
					-23896796.6,
					 83534629.9,
					 83719894.1,
					-11328843.8,
					33682693.3,
					 1831032.7,
					95294122.6,
					67511366.4,
					43264169.3,
					36799513.1
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.GTS +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.ASIA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				SystemicPnLSeries (
					-1797712.9,
					-1476200.0,
					 -742818.0,
					-1130878.2,
					 -904534.9,
					 -358799.6,
					-1902049.1,
					-1553521.6,
					 -778184.3,
					-1192660.6,
					 -965562.8,
					 -376106.4,
					-1900853.4,
					-1450106.9,
					 -718889.0,
					-1244442.1,
					 -971920.1,
					 -344765.5
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.LOCAL_MARKETS +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.ASIA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				SystemicPnLSeries (
					-46345749.0,
					 46536551.0,
					-56436798.6,
					 32147913.8,
					 -9436012.6,
					 -7441103.4,
					-338332437.1,
				   	   3559815.4,
					 -22330714.8,
					 -95327219.6,
					-309420381.7,
					  16699437.1,
					-237518342.0,
					-165671328.9,
					-226479230.2,
					   2666611.6,
					-189193519.2,
					 -62693397.2
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.PECD +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.ASIA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				SystemicPnLSeries (
					-11989285.2,
					-17613662.9,
					-14650485.7,
					 -5247516.1,
					 -2645585.3,
					 -7475025.6,
					-15142797.6,
					-24645936.2,
					-20725331.6,
					 -6141524.6,
					 -4081504.6,
					-10058970.2,
					-18192269.3,
					-25784980.6,
					-21013064.1,
					 -8547207.9,
					 -6036922.1,
					-10890916.8
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.PRIME_FINANCE +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.ASIA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				SystemicPnLSeries (
					-10528192.1,
					-19130674.8,
					 15058018.0,
					 -9200521.7,
					  4497370.7,
					  6371432.8,
					 -9535974.1,
					-19839072.8,
					 14929877.1,
					 -8192639.9,
					  4748268.3,
					  6269210.7,
					-23157194.2,
					-34627315.1,
					 36651706.6,
					-22051173.2,
					 11072121.6,
					 15486924.2
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.RETAIL_BANKING +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.ASIA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				SystemicPnLSeries (
					 3891991.8,
					 3650025.9,
					 4454190.3,
					-1349125.4,
					 1308257.4,
					 1957477.4,
					8305922.5,
					9633785.5,
					7202565.9,
					2703176.2,
					2835787.0,
					2278303.1,
					 9176960.9,
					12338334.8,
					10333069.0,
					 6518449.9,
					 4902628.8,
					 3352399.6
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.RISK_TREASURY +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.ASIA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				SystemicPnLSeries (
					 10522883.5,
					-13087514.0,
					-37085489.9,
					 14711662.6,
					 29658473.5,
					-17634240.4,
					  8164046.7,
					 -5895109.0,
					-36314808.5,
					  5308425.2,
					 30527673.3,
					-17100873.1,
					  5577807.5,
					-10296999.1,
					-13688041.0,
					 13644474.9,
					  5476655.2,
					 -6098828.8
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.CASH +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.EMEA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				SystemicPnLSeries (
					12091423.6,
					-8068888.4,
					 2680948.5,
					 5739376.6,
					-6672707.1,
					-2737873.7,
					20504036.5,
					33989245.5,
					73601088.5,
					19175668.2,
					67950032.4,
					36980604.6,
					-17220540.7,
					 24225295.1,
					 57186876.8,
					-29937126.7,
					 12611892.1,
					 21869417.8
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.CLP +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.EMEA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				SystemicPnLSeries (
					0,
					0,
					0,
					0,
					0,
					0,
					0,
					0,
					0,
					0,
					0,
					0,
					0,
					0,
					0,
					0,
					0,
					0
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.COMMODITIES_HOUSTON +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.EMEA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				SystemicPnLSeries (
					 332493429.7,
					 -23380848.1,
					-103862002.7,
					 644837150.8,
					 190871837.5,
					 -23763838.0,
					274069005.2,
					 20108271.9,
					 18818413.2,
					475798994.2,
					129254024.8,
					 15853610.2,
					362278355.2,
					 30807782.7,
					109577060.1,
					872039262.8,
					265876830.7,
					 17144189.6
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.CONVERTS +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.EMEA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				SystemicPnLSeries (
					-12219402.7,
					-14842621.8,
					 -8247812.6,
					 -5928973.8,
					 -8079754.2,
					 -4190615.7,
					-16910338.7,
					-18513046.0,
					-10730256.2,
					 -8441093.5,
					-11132134.7,
					 -5512845.0,
					-11538067.9,
					-13688544.1,
					 -8970925.1,
					 -4968562.7,
					 -8127875.4,
					 -4468856.5
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.CREDIT_TRADING +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.EMEA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				SystemicPnLSeries (
					68417713.8,
					81403362.6,
					18995868.9,
					17201434.1,
					30537261.3,
					 2749468.2,
					 -95478242.1,
					-106776577.5,
					 -54583827.6,
					 -34005698.6,
					 -34704345.6,
					 -27665440.0,
					22126359.3,
					18141186.0,
					 1821682.6,
					20981715.5,
					30749640.3,
					 5974529.6
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.EM_CREDIT_TRADING +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.EMEA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				SystemicPnLSeries (
					18973324.8,
					38109338.2,
					23035609.3,
					 3850597.8,
					11246439.4,
					 9626723.9,
					64501129.5,
					88217068.7,
					84445344.0,
					43175565.7,
					53757200.2,
					46648080.1,
					20298509.5,
					36879263.3,
					29819086.6,
					 9415431.3,
					21594625.5,
					13038525.7
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.EQUITY_DERIVATIVES +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.EMEA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				SystemicPnLSeries (
					384472647.1,
					406145255.9,
					412373716.7,
					150277790.8,
					266744093.2,
					161634760.0,
					491300492.2,
					381842877.8,
					207652644.6,
					164638592.0,
					344258851.7,
					108900483.1,
					462378647.0,
					504277664.9,
					437406963.6,
					161341548.2,
					373034490.2,
					185048454.0
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.EQUITY_UNDERWRITING +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.EMEA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				SystemicPnLSeries (
					-1537394.1,
					-2773763.9,
					-2936889.8,
					 -972486.5,
					-2198309.7,
					-1598270.2,
					 -7587959.0,
					 -9477534.1,
					-10696174.3,
					 -5381250.3,
					 -8831211.2,
					 -6001410.3,
					-3358310.6,
					-4203867.5,
					-4712637.6,
					-2351022.7,
					-3892150.3,
					-2653420.9
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.FINANCE +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.EMEA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				SystemicPnLSeries (
					-18164070.2,
					 13362745.9,
					 -5180680.8,
					-26629516.2,
					  1126374.5,
					 -1513790.5,
					-12990031.8,
					  9343798.2,
					 -5841166.9,
					-20830336.9,
					  2131880.0,
					 -2006753.9,
					-13625305.1,
					 10145706.1,
					 -4651957.3,
					-21906685.7,
					  3020711.2,
					 -1427381.0
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.G10_FX +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.EMEA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				SystemicPnLSeries (
					138148454.4,
					196143407.4,
					237027929.4,
					 59434930.3,
					128927267.3,
					 66854461.8,
					448483865.4,
					531179900.2,
					455530801.8,
					251959547.7,
					284672713.7,
					137496473.3,
					 76258644.6,
					364780379.0,
					233721638.7,
					-75387883.8,
					180444738.8,
					 62507945.7
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.G10_RATES +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.EMEA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				SystemicPnLSeries (
					-97885140.7,
					-52184779.6,
					-83784747.4,
					-71630795.9,
					127433635.7,
					  4938582.8,
					  13644922.6,
					 -38380990.0,
					-203861232.2,
					  75497853.6,
					 -21178938.7,
					 -35544862.2,
					 109597031.1,
					-115652731.0,
					-566714680.3,
					 178940450.1,
					 174738194.9,
					-133241129.9
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.GTS +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.EMEA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				SystemicPnLSeries (
					27784227.7,
					18343745.4,
					 3330345.0,
					11291549.2,
					 4742945.7,
					   78040.3,
					1413865.0,
					-550843.7,
					-719909.2,
					1326222.2,
					 815504.6,
					-207009.8,
					 1451881.0,
					-1532388.3,
					-1618661.0,
					 1858603.8,
					  256087.3,
					 -804848.9
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.IG_BONDS +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.EMEA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				SystemicPnLSeries (
					-12815.8,
					  7050.5,
					 15403.0,
					-10760.5,
					-13616.5,
					  8426.4,
					-17309.0,
					  9096.2,
					 21710.5,
					-14003.9,
					-19547.9,
					 11931.9,
					  -67.2,
					-3004.1,
					 4929.0,
					 4388.8,
					-7042.8,
					 2830
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.LOAN_PORTFOLIO_MANAGEMENT +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.EMEA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				SystemicPnLSeries (
					0,
					0,
					0,
					0,
					0,
					0,
					0,
					0,
					0,
					0,
					0,
					0,
					0,
					0,
					0,
					0,
					0,
					0
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.LOCAL_MARKETS +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.EMEA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				SystemicPnLSeries (
					 11218619.9,
					 45050572.8,
					128929732.6,
					-56943227.9,
					 -5866405.7,
					 38565245.0,
					 106054081.8,
					 173330200.1,
					 327365468.5,
					-157112747.6,
					 103920820.4,
					  93604750.0,
					 95306261.8,
					 42382357.5,
					125628140.9,
					-64898962.6,
					 74200349.8,
					 28109635.5
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.MUNICIPAL +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.EMEA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				SystemicPnLSeries (
					  32486.3,
					 -27583.2,
					-113961.4,
					   8173.1,
					  83094.2,
					 -46238.2,
					 68500.8,
					 14075.9,
					-85581.8,
					 24685.7,
					103772.6,
					-31676.8,
					  32807.1,
					  -4577.0,
					-102377.4,
					   8268.2,
					 100868.4,
					 -51314.5
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.OS_B +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.EMEA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				SystemicPnLSeries (
					  97068.0,
					-140486.1,
					-284088.7,
					 190090.1,
					 -78748.3,
					-107827.2,
					 161375.7,
					-221049.4,
					-372761.8,
					 254835.6,
					-102447.0,
					-149498.3,
					 155065.3,
					-183490.2,
					-428743.0,
					 292477.6,
					-117894.5,
					-170611.7
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.OTHER_GLOBAL_MARKETS +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.EMEA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				SystemicPnLSeries (
					-17168.7,
					-24763.8,
					 -1579.1,
					  1193.0,
					  -415.6,
					  -801.9,
					0,
					0,
					0,
					0,
					0,
					0,
					-118612.3,
					-141986.6,
					-178767.2,
					 104651.2,
					 -51954.1,
					 -45331.2
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.PECD +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.EMEA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				SystemicPnLSeries (
					-32604248.9,
					-32467556.8,
					-21763710.0,
					-16716638.8,
					-17586967.7,
					-13706762.1,
					-45628665.1,
					-48406463.5,
					-29264651.8,
					-23902190.1,
					-24728065.6,
					-19174289.5,
					6693783.4,
					7573845.8,
					 830563.6,
					4455133.2,
					6296683.6,
					1993938.4
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.PRIME_FINANCE +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.EMEA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				SystemicPnLSeries (
					-11166229.6,
					-15798593.9,
					-38232598.9,
					-17553133.3,
					-54005680.6,
					-21565703.0,
					 -2982541.9,
					 -7805001.7,
					-15473322.2,
					 -4937898.1,
					-20288154.7,
					 -8611970.6,
					 2192417.9,
					-2388329.2,
					-2370738.2,
					 3212533.3,
					 -134169.8,
					-1087398.0
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.RISK_TREASURY +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.EMEA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				SystemicPnLSeries (
					-81336991.2,
					 30714433.9,
					 31160927.5,
					-90707426.7,
					-59393143.5,
					 17881849.6,
					 -92281528.6,
					  33534517.2,
					  25788702.4,
					-102195736.6,
					 -61150928.0,
					  15375980.5,
					-75170708.6,
					 18820929.7,
					 -3181541.5,
					-84164014.1,
					-43714383.2,
					   659463.4
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.SECURITIZED_MARKETS +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.EMEA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				SystemicPnLSeries (
					-104407563.0,
					-128221300.4,
					 -82014879.4,
					 -44242668.3,
					 -59213988.2,
					 -39310976.3,
					-119446476.5,
					-149099236.1,
					 -98725624.7,
					 -51161133.5,
					 -68628676.2,
					 -48161689.1,
					-131660150.8,
					-157930890.8,
					 -98459547.7,
					 -56746823.7,
					 -74970599.3,
					 -47561722.7
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.SHORT_TERM +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.EMEA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				SystemicPnLSeries (
					-1547446.3,
					  446342.3,
					  356485.0,
					-1104158.9,
					 -430735.0,
					  192597.5,
					-1234808.3,
					  612046.9,
					  424315.0,
					 -932938.0,
					 -305575.0,
					  225091.1,
					-2243336.6,
					 1134587.4,
					  768787.0,
					-1672545.2,
					 -543992.5,
					  407635.5
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.CARDS +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.LATIN_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				SystemicPnLSeries (
					-6756230.2,
					-6756230.2,
					-4745447.6,
					 3330138.6,
					-1290024.4,
					-2028588.4,
					-6747461.8,
					-6747461.8,
					-4739288.8,
					 3325816.6,
					-1288350.2,
					-2025955.6,
					-6343512.3,
					-6343512.3,
					-4455562.4,
					 3126710.3,
					-1211220.7,
					-1904668.0
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.CASH +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.LATIN_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				SystemicPnLSeries (
					-2458228.9,
					-1677411.5,
					 2400294.4,
					  548583.7,
					 1728209.1,
					   46945.4,
					  828959.1,
					 1213179.3,
					 6808666.1,
					-2074280.1,
					 3200058.2,
					 1108149.8,
					-9982370.1,
					-9884902.8,
					-5108252.4,
					 2348895.1,
					-2499723.2,
					-2365832.1
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.COMMODITIES_HOUSTON +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.LATIN_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				SystemicPnLSeries (
					 -9488.6,
					 21214.8,
					 14684.8,
					-10918.3,
					  4296.7,
					  6363.9,
					 -9619.9,
					 21527.2,
					 14943.3,
					-11095.3,
					  4347.6,
					  6469.5,
					 -9787.4,
					 21947.3,
					 15307.9,
					-11254.8,
					  4418.7,
					  6631.4
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.EM_CREDIT_TRADING +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.LATIN_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				SystemicPnLSeries (
					-58674541.8,
					-71239729.6,
					-55144590.1,
					-31848756.4,
					-39430137.5,
					-28721029.9,
					-79674835.6,
					-94279695.1,
					-72380434.6,
					-43099295.8,
					-54123256.7,
					-38594542.3,
					-50855800.5,
					-62854618.5,
					-53722556.8,
					-29232518.0,
					-37236686.0,
					-29041060.9
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.EQUITY_DERIVATIVES +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.LATIN_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				SystemicPnLSeries (
					-15637534.0,
					-18324843.8,
					 -7116912.0,
					  5843391.5,
					 -2953868.8,
					   182765.5,
					-16938482.6,
					-19322180.8,
					 -9167703.0,
					  3776185.5,
					 -5298699.4,
					 -1567847.3,
					-9490612.2,
					-9620444.1,
					-3210086.9,
					 6606317.6,
					  679548.2,
					 1884827.9
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.G10_FX +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.LATIN_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				SystemicPnLSeries (
					-2460.1,
					-5630.5,
					-4254.3,
					-5092.7,
					-3786.7,
					-3240.4,
					0,
					0,
					0,
					0,
					0,
					0,
					0,
					0,
					0,
					0,
					0,
					0
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.LOCAL_MARKETS +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.LATIN_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				SystemicPnLSeries (
					-45897572.8,
					-31381402.7,
					-22416121.7,
					-69395684.5,
					  4060396.5,
					-19899635.9,
					-298847370.8,
					-225503655.0,
					-355876495.0,
					  91972347.4,
					-112406258.4,
					 -98420276.6,
					-138793883.6,
					-158856561.6,
					-123917116.7,
					 -42598357.4,
					 -59546908.4,
					 -38339708.4
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.OTHER_CONSUMER +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.LATIN_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				SystemicPnLSeries (
					-0.2,
					 0.0,
					-0.1,
					 0.0,
					-0.1,
					 0.0,
					-0.2,
					 0.0,
					-0.1,
					 0.0,
					-0.1,
					 0.0,
					 274.7,
					 273.0,
					 462.4,
					-265.5,
					 135.3,
					 109.4
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.RETAIL_BANKING +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.LATIN_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				SystemicPnLSeries (
					-3196102.3,
					-3196102.3,
					-2244881.5,
					 1575355.4,
					 -610259.0,
					 -959644.1,
					-2927331.4,
					-2927331.4,
					-2056101.9,
					 1442878.6,
					 -558940.3,
					 -878944.4,
					-3473179.2,
					-3473179.2,
					-2439495.1,
					 1711926.4,
					 -663163.7,
					-1042837.8
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.AI +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.NORTH_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				SystemicPnLSeries (
					 1425329.1,
					-1787289.7,
					-2776615.3,
					 1948501.9,
					 -754808.0,
					-1186950.1,
					 1396229.8,
					-1750105.7,
					-2719928.3,
					 1908721.6,
					 -739397.9,
					-1162717.5,
					 1391924.7,
					-1743981.0,
					-2711541.6,
					 1902836.2,
					 -737118.0,
					-1159132.3
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.CAI +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.NORTH_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				SystemicPnLSeries (
					-2711570.2,
					 3758703.8,
					  481279.2,
					 -337739.8,
					  130833.2,
					  205737.7,
					 5750125.7,
					-6672722.1,
					-8177423.4,
					 5738542.6,
					-2222988.6,
					-3495692.6,
					 5744473.3,
					-6660977.0,
					-8188909.4,
					 5746602.9,
					-2226111.0,
					-3500602.6
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.CASH +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.NORTH_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				SystemicPnLSeries (
					  2649666.4,
					-35729212.5,
					-28300760.3,
					  6857015.9,
					-12398378.2,
					 -7079138.0,
					-19002493.2,
					-23967765.6,
					-25615310.5,
					-16403702.1,
					-20202478.3,
					 -8348839.4,
					 -3499992.2,
					-37102931.8,
					-21390970.2,
					 -2212445.8,
					-13026403.3,
					-11398180.0
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.CLP +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.NORTH_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				SystemicPnLSeries (
					0,
					0,
					0,
					0,
					0,
					0,
					0,
					0,
					0,
					0,
					0,
					0,
					0,
					0,
					0,
					0,
					0,
					0
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.COMMODITIES_HOUSTON +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.NORTH_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				SystemicPnLSeries (
					 -80375881.7,
					 -57369466.6,
					-120102624.6,
					 122149350.0,
					  17008334.1,
					 -50167741.3,
					  40367174.1,
					 -90561875.5,
					-141054808.0,
					 219984854.8,
					  64961918.8,
					 -58777665.4,
					  23239309.5,
					 -63379619.1,
					-144389711.9,
					 249063067.0,
					  82037810.5,
					 -65333440.9
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.CONVERTS +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.NORTH_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				SystemicPnLSeries (
					-22125024.6,
					-21441667.0,
					-10804065.8,
					-11803837.2,
					-14780893.6,
					 -5586773.4,
					-19030250.7,
					-18045497.6,
					 -8458751.3,
					 -9978115.8,
					-11710609.8,
					 -4374819.0,
					-20392288.9,
					-19365949.1,
					 -9461952.7,
					-11082549.1,
					-13731325.6,
					 -4979861.9
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.CREDIT_MACRO_HEDGE +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.NORTH_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				SystemicPnLSeries (
					220721109.9,
					271293953.6,
					151365921.7,
					 90330173.9,
					120813093.6,
					 75741779.4,
					278813100.0,
					345280186.1,
					190002300.7,
					108577430.6,
					148872104.5,
					 89738165.2,
					308267347.3,
					394434889.0,
					202855222.8,
					117427766.1,
					158474673.4,
					 98877322.0
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.CREDIT_MARKETS +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.NORTH_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				SystemicPnLSeries (
					30057238.4,
					32384172.7,
					 8379269.1,
					 9364975.5,
					11969073.2,
					 1813313.4,
					28945379.8,
					31328675.8,
					 7380177.7,
					 8687205.9,
					11594124.7,
					 1297403.1,
					32767678.6,
					37389767.0,
					11530532.3,
					11076416.3,
					12847952.8,
					 3372123.4
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.CREDIT_TRADING +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.NORTH_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				SystemicPnLSeries (
					-289441891.6,
					-358555827.2,
					-249161791.7,
					-130165302.6,
					-171589920.8,
					-127991710.3,
					-306928140.7,
					-356134432.6,
					-257518946.9,
					-148506429.6,
					-198600563.7,
					-136606461.9,
					-305257322.1,
					-376710927.8,
					-235263087.1,
					-126511422.4,
					-187678315.4,
					-116232670.1
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.EM_CREDIT_TRADING +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.NORTH_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				SystemicPnLSeries (
					-14525479.6,
					-18088101.8,
					-11860124.4,
					 -7310434.5,
					 -9180272.4,
					 -6778879.6,
					-12943176.7,
					-14143518.1,
					-11113999.8,
					 -9480274.2,
					-11172198.6,
					 -7171856.9,
					-12045158.9,
					-10860099.1,
					-10188189.0,
					-10663008.4,
					-12688735.5,
					 -6963414.6
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.EQUITIES +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.NORTH_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				SystemicPnLSeries (
					-109802.3,
					-137254.4,
					-146445.7,
					 -73374.7,
					-121099.0,
					 -82505.1,
					-109802.3,
					-137254.4,
					-146445.7,
					 -73374.7,
					-121099.0,
					 -82505.1,
					-109802.3,
					-137254.4,
					-146445.7,
					 -73374.7,
					-121099.0,
					 -82505.1
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.EQUITY_DERIVATIVES +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.NORTH_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				SystemicPnLSeries (
					334954858.5,
					314657153.7,
					337239514.3,
					229174260.7,
					355487564.7,
					241877820.5,
					568554528.6,
					689500954.7,
					681257169.9,
					314080145.5,
					590419639.7,
					336297910.4,
					429955662.1,
					581281183.9,
					561736436.6,
					229440354.7,
					454334661.9,
					231490959.2
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.FINANCE +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.NORTH_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				SystemicPnLSeries (
					-56226674.7,
					 46939949.8,
					 18300613.0,
					-59993323.0,
					-13839177.9,
					 11014765.8,
					-43488247.2,
					 36831445.8,
					 14590446.9,
					-45834513.6,
					-10346032.3,
					  8901517.8,
					-28749676.1,
					 21628984.5,
					  3645605.3,
					-31263913.4,
					 -2902934.5,
					  2745584.3
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.G10_FX +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.NORTH_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				SystemicPnLSeries (
					 5723195.4,
					15559137.2,
					28336485.5,
					-5769831.0,
					11688661.6,
					10579898.2,
					26173450.4,
					  -34555.5,
					12508565.2,
					17458157.3,
					15046835.2,
					 3768172.6,
					  694739.3,
					 3161060.4,
					15264728.9,
					 -779042.2,
					 5993986.3,
					 5511874.1
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.G10_RATES +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.NORTH_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				SystemicPnLSeries (
					-374540198.4,
					 -19443725.1,
					-255156824.9,
					-406301743.7,
					 264767757.0,
					  91108589.2,
					-550261620.6,
					-247688010.5,
					-681674425.2,
					-569730966.6,
					- 35445852.3,
					-182016921.7,
					-283142676.8,
					-220745583.8,
					-203187776.2,
					-191963200.5,
					-688755721.9,
					 -26733085.0
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.GLOBAL_SECURITIZED_MARKETS +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.NORTH_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				SystemicPnLSeries (
					-418937.1,
					-498732.2,
					-311713.8,
					-187028.3,
					-249371.1,
					-155856.9,
					-437265.7,
					-520552.1,
					-325351.3,
					-195210.8,
					-260281.1,
					-162675.6,
					-476814.4,
					-567633.7,
					-354777.3,
					-212866.4,
					-283821.9,
					-177388.7
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.GTS +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.NORTH_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				SystemicPnLSeries (
					-244.2,
					 178.6,
					  75.9,
					-309.9,
					 -50.1,
					  45.1,
					 -58.8,
					-151.9,
					  -3.4,
					  79.4,
					  30.3,
					  -5.6,
					-235.4,
					 175.0,
					  78.6,
					-305.3,
					 -55.1,
					  46.1
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.GWM +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.NORTH_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				SystemicPnLSeries (
					39964.1,
					39964.1,
					13572.7,
					13572.7,
					13572.8,
					13572.7,
					41737.6,
					41737.6,
					14175.0,
					14175.0,
					14175.1,
					14175.0,
					42002.8,
					42002.8,
					14265.1,
					14265.1,
					14265.1,
					14265.1
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.IG_BONDS +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.NORTH_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				SystemicPnLSeries (
					-318242.8,
					-973575.4,
					-810200.5,
					-100946.9,
					-375354.9,
					-434824.8,
					  81777.4,
					-587489.8,
					-433923.6,
					 190734.4,
					 -17589.8,
					-220812.3,
					 287568.0,
					-342419.7,
					-445286.6,
					 179883.0,
					 -34234.6,
					-270769.2
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.LEVERAGED_FINANCE +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.NORTH_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				SystemicPnLSeries (
					0,
					0,
					0,
					0,
					0,
					0,
					1815451.8,
					1654808.8,
					1348593.0,
					1274698.5,
					1651070.5,
					 783447.9,
					 92755993.2,
					131299254.0,
					 45742241.1,
					 18419488.8,
					 31314451.8,
					 12214699.8
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.LONG_TERM_ASSET_GROUP +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.NORTH_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				SystemicPnLSeries (
					5433419.0,
					5478111.4,
					4965400.9,
					3246098.2,
					4549457.4,
					2470654.5,
					7260901.0,
					7587477.9,
					6239858.3,
					4057619.9,
					5603415.0,
					3107068.7,
					4209082.9,
					3879637.3,
					3912999.3,
					2708571.0,
					3776987.3,
					1942857.9
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.MUNICIPAL_SECURITIES +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.NORTH_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				SystemicPnLSeries (
					 133564.8,
					-100166.6,
					 -59236.0,
					 105466.3,
					  22889.0,
					 -31171.6,
					 -268014.3,
					  157849.6,
					 1247144.3,
					  126534.5,
					-1270223.3,
					  677822.3,
					 -114738.5,
					   50699.2,
					 1090043.1,
					  224781.1,
					-1144591.3,
					  593808.0
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.MUNICIPAL +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.NORTH_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				SystemicPnLSeries (
					-228659584.8,
					-196486300.6,
					-121209228.9,
					-117621295.5,
					-220172894.6,
					 -59092154.6,
					-248623518.8,
					-194535775.4,
					-128951051.8,
					-136687561.4,
					-220025434.4,
					 -61686834.9,
					-291567858.2,
					-244088371.4,
					-147000405.2,
					-145440170.6,
					-270332396.3,
					 -68696555.3
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.OS_B +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.NORTH_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				SystemicPnLSeries (
					 24839.9,
					-14618.3,
					-16315.9,
					 22857.3,
					 22300.0,
					 -8479.8,
					 25528.2,
					-14881.4,
					-17246.1,
					 23321.4,
					 23341.9,
					 -8939.0,
					0,
					0,
					0,
					0,
					0,
					0
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.OTHER_GLOBAL_MARKETS +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.NORTH_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				SystemicPnLSeries (
					0.3,
					0.3,
					0.3,
					0.3,
					0.3,
					0.3,
					0.3,
					0.3,
					0.3,
					0.3,
					0.3,
					0.3,
					-13.6,
					-13.6,
					 -2.9,
					 -2.9,
					 -3.0,
					 -2.9
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.OTHER_SPECIAL_ASSET_POOL +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.NORTH_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				SystemicPnLSeries (
					7972525.2,
					8944458.8,
					4373435.0,
					3200643.4,
					3816054.4,
					2344420.2,
					8400790.9,
					9430113.1,
					4613140.0,
					3372428.6,
					4022535.3,
					2473135.9,
					8797353.9,
					9861829.6,
					4844913.6,
					3547604.6,
					4228314.2,
					2597299.7
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.OTHER_BAM +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.NORTH_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				SystemicPnLSeries (
					-542950.7,
					 532350.4,
					1057696.2,
					-742242.9,
					 287529.1,
					 452145.0,
					-550598.2,
					 539848.6,
					1072593.9,
					-752697.4,
					 291578.9,
					 458513.4,
					-558739.2,
					 547830.7,
					1088453.1,
					-763826.7,
					 295890.2,
					 465292.9
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.PECD +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.NORTH_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				SystemicPnLSeries (
					-60008499.7,
					-36156495.7,
					-39600396.2,
					-42645963.6,
					-43681445.1,
					-16883596.0,
					-71634634.8,
					  7016165.0,
					-95166114.9,
					-72036117.2,
					-85548973.9,
					-39686589.9,
					-291997098.1,
					-256229397.0,
					-246534263.7,
					-171168114.6,
					-212433313.0,
					-122129366.1
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.PRIME_FINANCE +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.NORTH_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				SystemicPnLSeries (
					  9113393.9,
					-10726377.1,
					-15321539.3,
					 12591308.4,
					 -4234509.0,
					 -7327857.2,
					  6756778.8,
					 -7302741.1,
					-11651829.1,
					  9234920.2,
					 -3236010.9,
					 -5465091.5,
					 5036207.2,
					-5899894.3,
					-8740798.6,
					 6816660.8,
					-2474701.4,
					-4107831.1
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.PROJECT_FINANCE +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.NORTH_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				SystemicPnLSeries (
					-33103.8,
					-39415.1,
					-24619.4,
					-14771.7,
					-19695.6,
					-12309.7,
					-33695.9,
					-40119.9,
					-25059.9,
					-15036.0,
					-20048.1,
					-12530.0,
					-33624.8,
					-40035.3,
					-25007.1,
					-15004.3,
					-20005.8,
					-12503.6
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.RATES_AND_CURRENCIES +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.NORTH_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				SystemicPnLSeries (
					17212202.9,
					-3292471.2,
					-5125062.1,
					13709702.5,
					 2530318.3,
					 -757033.2,
					11716479.6,
					-3904663.2,
					-7065535.9,
					 9360911.8,
					 1416685.3,
					-1766558.9,
					 12959550.8,
					 -9776633.9,
					-12998430.1,
					 13347218.6,
					 -5540844.3,
					 -3442553.4
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.REAL_ESTATE_LENDING +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.NORTH_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				SystemicPnLSeries (
					 2513429.6,
					-1528828.1,
					-5209545.7,
					 1567627.5,
					 4955646.6,
					-2639245.5,
					 2625001.0,
					-1602511.4,
					-5420582.8,
					 1645263.3,
					 5157240.7,
					-2746435.9,
					 2424624.8,
					-1482794.7,
					-5016285.8,
					 1517414.5,
					 4764381.6,
					-2541720.9
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.RETAIL_BANKING +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.NORTH_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				SystemicPnLSeries (
					 6859421.8,
					23502740.6,
					43606944.6,
					 3398379.2,
					-7668783.2,
					21926341.5,
					 8431391.8,
					23612558.4,
					47205822.8,
					 5859928.3,
					-9924734.9,
					23655124.3,
					 -9307473.5,
					  5809414.4,
					 28363080.5,
					 -3761010.8,
					-26900469.8,
					 14234593.8
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.RISK_TREASURY +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.NORTH_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				SystemicPnLSeries (
					  25934192.7,
					 -74265458.4,
					-278371879.4,
					 -10594539.7,
					 155775592.2,
					-129776427.4,
					 140814902.9,
					-118779659.8,
					-287405715.8,
					  96035343.7,
					 258208100.8,
					-142850176.7,
					 163751025.7,
					-131724028.9,
					-311056741.3,
					 108410626.2,
					 285851516.6,
					-155018974.6
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.SECURITIZED_MARKETS +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.NORTH_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				SystemicPnLSeries (
					-1061552629.0,
					-1236403925.0,
					 -968435450.8,
					 -512397553.6,
					 -619106569.7,
					 -482345905.6,
					-1063204514.0,
					-1251004883.0,
					 -988367855.9,
					 -526080829.1,
					 -632538589.6,
					 -499921368.8,
					-1137011426.0,
					-1345834287.0,
					-1021592130.0,
					 -552729380.3,
					 -695358253.8,
					 -516399507.7
				),
				null
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addGSST (
				org.drip.capital.definition.Business.SHORT_TERM +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.NORTH_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				SystemicPnLSeries (
					-1814110.8,
					  761165.0,
					  561956.4,
					-1313913.9,
					 -493731.3,
					  299743.4,
					-4375943.1,
					 1986961.4,
					 1600544.3,
					-3190657.7,
					-1331016.2,
					  850166.0,
					-3383940.0,
					 -147436.9,
					  263666.9,
					-2117711.8,
					-1228893.3,
					  159247.5
				),
				null
			))
			{
				return false;
			}

			return true;
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return false;
	}

	private static boolean LoadIBSST (
		final org.drip.capital.shell.CapitalUnitStressEventContext capitalUnitStressEventContext)
	{
		if (!capitalUnitStressEventContext.addIBSST (
			org.drip.capital.definition.Business.G10_RATES +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.Region.EMEA +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.RiskType.CVA,
			"Idiosyncretic Incremental Default Risk",
			0.02,
			-231900000.
		))
		{
			return false;
		}

		if (!capitalUnitStressEventContext.addIBSST (
			org.drip.capital.definition.Business.MUNICIPAL +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.Region.NORTH_AMERICA +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.RiskType.CVA,
			"Tenor Risk",
			0.02,
			-1580000.
		))
		{
			return false;
		}

		if (!capitalUnitStressEventContext.addIBSST (
			org.drip.capital.definition.Business.G10_RATES +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.Region.EMEA +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.RiskType.CVA,
			"Tenor Risk",
			0.02,
			-4890000.
		))
		{
			return false;
		}

		if (!capitalUnitStressEventContext.addIBSST (
			org.drip.capital.definition.Business.G10_RATES +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.Region.EMEA +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.RiskType.CVA,
			"Italy Hedge Bond/CDS Basis Risk",
			0.02,
			13166000.
		))
		{
			return false;
		}

		if (!capitalUnitStressEventContext.addIBSST (
			org.drip.capital.definition.Business.CORPORATE_CENTER +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.Region.ASIA +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.RiskType.PENSION,
			"Pension VaR APAC - Japan - Citibank Japan Ltd",
			0.02,
			-34082147.
		))
		{
			return false;
		}

		if (!capitalUnitStressEventContext.addIBSST (
			org.drip.capital.definition.Business.CORPORATE_CENTER +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.Region.ASIA +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.RiskType.PENSION,
			"Pension VaR APAC - Korea",
			0.02,
			-85344939.8
		))
		{
			return false;
		}

		if (!capitalUnitStressEventContext.addIBSST (
			org.drip.capital.definition.Business.CORPORATE_CENTER +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.Region.EMEA +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.RiskType.PENSION,
			"Pension VaR EMEA - Germany ROSE",
			0.02,
			-42327082.1
		))
		{
			return false;
		}

		if (!capitalUnitStressEventContext.addIBSST (
			org.drip.capital.definition.Business.CORPORATE_CENTER +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.Region.EMEA +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.RiskType.PENSION,
			"Pension VaR EMEA - UK Citi",
			0.02,
			-72032435.1
		))
		{
			return false;
		}

		if (!capitalUnitStressEventContext.addIBSST (
			org.drip.capital.definition.Business.CORPORATE_CENTER +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.Region.EMEA +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.RiskType.PENSION,
			"Pension VaR EMEA - Netherlands",
			0.02,
			-30992203.3
		))
		{
			return false;
		}

		if (!capitalUnitStressEventContext.addIBSST (
			org.drip.capital.definition.Business.CORPORATE_CENTER +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.Region.EMEA +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.RiskType.PENSION,
			"Pension VaR EMEA - UK PLAS",
			0.02,
			-90287300.3
		))
		{
			return false;
		}

		if (!capitalUnitStressEventContext.addIBSST (
			org.drip.capital.definition.Business.CORPORATE_CENTER +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.Region.EMEA +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.RiskType.PENSION,
			"Pension VaR EMEA - UK Medical",
			0.02,
			-43587451.
		))
		{
			return false;
		}

		if (!capitalUnitStressEventContext.addIBSST (
			org.drip.capital.definition.Business.CORPORATE_CENTER +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.Region.EMEA +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.RiskType.PENSION,
			"Pension VaR EMEA - Greece",
			0.02,
			-60622136.1
		))
		{
			return false;
		}

		if (!capitalUnitStressEventContext.addIBSST (
			org.drip.capital.definition.Business.CORPORATE_CENTER +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.Region.EMEA +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.RiskType.PENSION,
			"Pension VaR EMEA - CEP Small Plans",
			0.02,
			-7785565.6
		))
		{
			return false;
		}

		if (!capitalUnitStressEventContext.addIBSST (
			org.drip.capital.definition.Business.CORPORATE_CENTER +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.Region.EMEA +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.RiskType.PENSION,
			"Pension VaR EMEA - Swiss",
			0.02,
			-72800192.7
		))
		{
			return false;
		}

		if (!capitalUnitStressEventContext.addIBSST (
			org.drip.capital.definition.Business.CORPORATE_CENTER +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.Region.EMEA +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.RiskType.PENSION,
			"Pension VaR EMEA - German PRS",
			0.02,
			-7303489.2
		))
		{
			return false;
		}

		if (!capitalUnitStressEventContext.addIBSST (
			org.drip.capital.definition.Business.CORPORATE_CENTER +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.Region.LATIN_AMERICA +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.RiskType.PENSION,
			"Pension VaR LATAM - Mexico",
			0.02,
			-178766482.5
		))
		{
			return false;
		}

		if (!capitalUnitStressEventContext.addIBSST (
			org.drip.capital.definition.Business.CORPORATE_CENTER +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.Region.LATIN_AMERICA +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.RiskType.PENSION,
			"Pension VaR LATAM - Mexico Medical",
			0.02,
			-126936078.6
		))
		{
			return false;
		}

		if (!capitalUnitStressEventContext.addIBSST (
			org.drip.capital.definition.Business.CORPORATE_CENTER +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.Region.LATIN_AMERICA +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.RiskType.PENSION,
			"Pension VaR LATAM - Brazil",
			0.02,
			-79868049.2
		))
		{
			return false;
		}

		if (!capitalUnitStressEventContext.addIBSST (
			org.drip.capital.definition.Business.CORPORATE_CENTER +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.Region.NORTH_AMERICA +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.RiskType.PENSION,
			"Pension VaR NAM - US",
			0.02,
			-1612493729.
		))
		{
			return false;
		}

		if (!capitalUnitStressEventContext.addIBSST (
			org.drip.capital.definition.Business.CORPORATE_CENTER +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.Region.NORTH_AMERICA +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.RiskType.PENSION,
			"Pension VaR NAM - Small Plans",
			0.02,
			-110114233.4
		))
		{
			return false;
		}

		if (!capitalUnitStressEventContext.addIBSST (
			org.drip.capital.definition.Business.CORPORATE_CENTER +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.Region.NORTH_AMERICA +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.RiskType.PENSION,
			"Pension VaR NAM - US OPEB",
			0.02,
			-8510162.6
		))
		{
			return false;
		}

		if (!capitalUnitStressEventContext.addIBSST (
			org.drip.capital.definition.Business.CORPORATE_CENTER +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.Region.NORTH_AMERICA +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.RiskType.PENSION,
			"Pension VaR NAM - US Medical",
			0.02,
			-67068668.1
		))
		{
			return false;
		}

		if (!capitalUnitStressEventContext.addIBSST (
			org.drip.capital.definition.Business.CORPORATE_CENTER +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.Region.NORTH_AMERICA +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.RiskType.PENSION,
			"Pension VaR NAM - US Non-Qualified",
			0.02,
			-76737617.9
		))
		{
			return false;
		}

		if (!capitalUnitStressEventContext.addIBSST (
			org.drip.capital.definition.Business.CORPORATE_CENTER +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.Region.NORTH_AMERICA +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.RiskType.PENSION,
			"Pension VaR NAM - Canada",
			0.02,
			-2607881.8
		))
		{
			return false;
		}

		if (!capitalUnitStressEventContext.addIBSST (
			org.drip.capital.definition.Business.PRIME_FINANCE +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.Region.ASIA +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.RiskType.TRADING,
			"CNY/CNH spread",
			0.02,
			-595000.
		))
		{
			return false;
		}

		if (!capitalUnitStressEventContext.addIBSST (
			org.drip.capital.definition.Business.G10_RATES +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.Region.ASIA +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.RiskType.TRADING,
			"Received DTIBOR vs ZTIBOR (mostly 1.5Y-4Y)",
			0.10,
			-650000.
		))
		{
			return false;
		}

		if (!capitalUnitStressEventContext.addIBSST (
			org.drip.capital.definition.Business.EQUITY_DERIVATIVES +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.Region.ASIA +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.RiskType.TRADING,
			"Non-recourse margin financing - APAC",
			0.02,
			-55800000.
		))
		{
			return false;
		}

		if (!capitalUnitStressEventContext.addIBSST (
			org.drip.capital.definition.Business.G10_RATES +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.Region.ASIA +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.RiskType.TRADING,
			"Derivs discounting placeholder for process risk",
			0.02,
			-25000000.
		))
		{
			return false;
		}

		if (!capitalUnitStressEventContext.addIBSST (
			org.drip.capital.definition.Business.LOCAL_MARKETS +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.Region.ASIA +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.RiskType.TRADING,
			"Asian cross ccy basis risk.",
			0.05,
			-3126762.
		))
		{
			return false;
		}

		if (!capitalUnitStressEventContext.addIBSST (
			org.drip.capital.definition.Business.G10_RATES +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.Region.ASIA +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.RiskType.TRADING,
			"Swaps JPY Libor paid  JSCC vs received LCH Basis (include bilateral)",
			0.02,
			-1470000.
		))
		{
			return false;
		}

		if (!capitalUnitStressEventContext.addIBSST (
			org.drip.capital.definition.Business.LOCAL_MARKETS +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.Region.ASIA +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.RiskType.TRADING,
			"Asia bond swap spread risk.",
			0.05,
			-12390000.
		))
		{
			return false;
		}

		if (!capitalUnitStressEventContext.addIBSST (
			org.drip.capital.definition.Business.G10_RATES +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.Region.ASIA +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.RiskType.TRADING,
			"Stress P&L for vanilla swaption skew. The calculation is based on strike vol time series and a portfolio of vanilla swaptions that replicate the trading books' SABR skew risk.",
			0.10,
			-4600000.
		))
		{
			return false;
		}

		if (!capitalUnitStressEventContext.addIBSST (
			org.drip.capital.definition.Business.G10_RATES +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.Region.ASIA +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.RiskType.TRADING,
			"3M6M JPY basis Libor paid JSCC vs received LCH Basis (include bilateral)",
			0.02,
			-2200000.
		))
		{
			return false;
		}

		if (!capitalUnitStressEventContext.addIBSST (
			org.drip.capital.definition.Business.LOCAL_MARKETS +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.Region.ASIA +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.RiskType.TRADING,
			"Asia NDF implied DV01 risk.",
			0.05,
			-59768500.
		))
		{
			return false;
		}

		if (!capitalUnitStressEventContext.addIBSST (
			org.drip.capital.definition.Business.G10_RATES +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.Region.ASIA +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.RiskType.TRADING,
			"TONAR basis paid JSCC vs rec LCH Basis (include bilateral)",
			0.02,
			-1350000.
		))
		{
			return false;
		}

		if (!capitalUnitStressEventContext.addIBSST (
			org.drip.capital.definition.Business.EQUITY_DERIVATIVES +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.Region.ASIA +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.RiskType.TRADING,
			"Exotic risks from autocallables positions. Potential stress loss arising from large Market Sell-off across markets and drive up in correlation of multi-underlying trades",
			0.20,
			-9000000.
		))
		{
			return false;
		}

		if (!capitalUnitStressEventContext.addIBSST (
			org.drip.capital.definition.Business.EM_CREDIT_TRADING +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.Region.ASIA +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.RiskType.TRADING,
			"Binary write down risk inherent in AT1 instrument",
			0.00,
			3000000.
		))
		{
			return false;
		}

		if (!capitalUnitStressEventContext.addIBSST (
			org.drip.capital.definition.Business.LOCAL_MARKETS +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.Region.EMEA +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.RiskType.TRADING,
			"FX risk on lifetime earnings and price risk on tbills subject to churn requirements  ¿ does not include credit (no repayment) cross-border event",
			0.05,
			0.
		))
		{
			return false;
		}

		if (!capitalUnitStressEventContext.addIBSST (
			org.drip.capital.definition.Business.CASH +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.Region.EMEA +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.RiskType.TRADING,
			"Long/short single stock portfolio",
			0.02,
			-22300000.
		))
		{
			return false;
		}

		if (!capitalUnitStressEventContext.addIBSST (
			org.drip.capital.definition.Business.EM_CREDIT_TRADING +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.Region.EMEA +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.RiskType.TRADING,
			"SWWR on Egypt Reverse Repo trade. Assumes default of counterparty (also obligor of collateral). We assume 55% recovery on collateral notional. If this PnL is greater than losses in GSST (fulreval on CR01/IR01 exposure) then we take the difference to be Top 10 risk. ",
			0.00,
			0.
		))
		{
			return false;
		}

		if (!capitalUnitStressEventContext.addIBSST (
			org.drip.capital.definition.Business.EQUITY_DERIVATIVES +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.Region.EMEA +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.RiskType.TRADING,
			"Concentration Risk",
			0.02,
			-24900000.
		))
		{
			return false;
		}

		if (!capitalUnitStressEventContext.addIBSST (
			org.drip.capital.definition.Business.G10_RATES +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.Region.EMEA +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.RiskType.TRADING,
			"Finance desk TRS vs Cash basis",
			0.02,
			-55000000.
		))
		{
			return false;
		}

		if (!capitalUnitStressEventContext.addIBSST (
			org.drip.capital.definition.Business.EQUITY_DERIVATIVES +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.Region.EMEA +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.RiskType.TRADING,
			"Mortality and Lapse Risk (Stress Limit)",
			0.02,
			0.
		))
		{
			return false;
		}

		if (!capitalUnitStressEventContext.addIBSST (
			org.drip.capital.definition.Business.EQUITY_UNDERWRITING +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.Region.EMEA +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.RiskType.TRADING,
			"ECM - CB Notional",
			0.02,
			0.
		))
		{
			return false;
		}

		if (!capitalUnitStressEventContext.addIBSST (
			org.drip.capital.definition.Business.EQUITY_UNDERWRITING +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.Region.EMEA +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.RiskType.TRADING,
			"ECM: Block trade",
			0.02,
			0.
		))
		{
			return false;
		}

		if (!capitalUnitStressEventContext.addIBSST (
			org.drip.capital.definition.Business.LOCAL_MARKETS +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.Region.EMEA +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.RiskType.TRADING,
			"Cross border exposures in EGP FX mainly in London Hub",
			0.05,
			-46389724.
		))
		{
			return false;
		}

		if (!capitalUnitStressEventContext.addIBSST (
			org.drip.capital.definition.Business.LOCAL_MARKETS +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.Region.EMEA +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.RiskType.TRADING,
			"Exposures in pegged and managed Middle Easters ccys (notably AED and SAR).",
			0.05,
			-30865543.2
		))
		{
			return false;
		}

		if (!capitalUnitStressEventContext.addIBSST (
			org.drip.capital.definition.Business.EQUITY_DERIVATIVES +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.Region.EMEA +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.RiskType.TRADING,
			"Non-recourse margin financing - EMEA",
			0.02,
			-75000000.
		))
		{
			return false;
		}

		if (!capitalUnitStressEventContext.addIBSST (
			org.drip.capital.definition.Business.G10_RATES +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.Region.EMEA +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.RiskType.TRADING,
			"Repo Basis - Finance Desk",
			0.02,
			-79000000.
		))
		{
			return false;
		}

		if (!capitalUnitStressEventContext.addIBSST (
			org.drip.capital.definition.Business.G10_RATES +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.Region.EMEA +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.RiskType.TRADING,
			"Derivs discounting placeholder for risk to collateral assumption updates",
			0.02,
			-40000000.
		))
		{
			return false;
		}

		if (!capitalUnitStressEventContext.addIBSST (
			org.drip.capital.definition.Business.CASH +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.Region.EMEA +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.RiskType.TRADING,
			"Event Risk",
			0.01,
			-600000
		))
		{
			return false;
		}

		if (!capitalUnitStressEventContext.addIBSST (
			org.drip.capital.definition.Business.EQUITY_UNDERWRITING +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.Region.EMEA +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.RiskType.TRADING,
			"ECM: Rights Issue",
			0.02,
			0.
		))
		{
			return false;
		}

		if (!capitalUnitStressEventContext.addIBSST (
			org.drip.capital.definition.Business.LOCAL_MARKETS +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.Region.LATIN_AMERICA +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.RiskType.TRADING,
			"EM NY HUB: FX Off-shore/onshore BRL spread (offshore FX forward points  traded as a spread to the onshore FX points).Captured in VAR GSST doesn't capture spread risk",
			0.02,
			-425503.
		))
		{
			return false;
		}

		if (!capitalUnitStressEventContext.addIBSST (
			org.drip.capital.definition.Business.LOCAL_MARKETS +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.Region.LATIN_AMERICA +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.RiskType.TRADING,
			"EM NY HUB: DI Off-shore/onshore BRL Basis mainly from interest rate swaps - DI future contracts (DI One-day interbank deposit rate) . Captured in VAR GSST doesn't capture spread risk",
			0.02,
			-2099361.
		))
		{
			return false;
		}

		if (!capitalUnitStressEventContext.addIBSST (
			org.drip.capital.definition.Business.EM_CREDIT_TRADING +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.Region.LATIN_AMERICA +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.RiskType.TRADING,
			"SWWR on Argentina Reverse Repo trade. Assumes default of counterparty (also obligor of collateral). We assume 40% recovery on the claim if this loss is greater than the GSST stress loss (fulreval on CR01/IR01) then the difference will be Top 10 risk. $bn",
			0.00,
			-2600000.
		))
		{
			return false;
		}

		if (!capitalUnitStressEventContext.addIBSST (
			org.drip.capital.definition.Business.LOCAL_MARKETS +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.Region.LATIN_AMERICA +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.RiskType.TRADING,
			"EM NY HUB CLP cross currency spread/basis",
			0.02,
			-2282800.
		))
		{
			return false;
		}

		if (!capitalUnitStressEventContext.addIBSST (
			org.drip.capital.definition.Business.LOCAL_MARKETS +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.Region.LATIN_AMERICA +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.RiskType.TRADING,
			"Banamex: MXN bond swap rate basis mainly coming  from interest rate swaps (IBR) to hedge securities (GOV)",
			0.02,
			-11954300.
		))
		{
			return false;
		}

		if (!capitalUnitStressEventContext.addIBSST (
			org.drip.capital.definition.Business.LOCAL_MARKETS +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.Region.LATIN_AMERICA +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.RiskType.TRADING,
			"MXN cross currency spread/basis.",
			0.02,
			-5957644.
		))
		{
			return false;
		}

		if (!capitalUnitStressEventContext.addIBSST (
			org.drip.capital.definition.Business.LOCAL_MARKETS +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.Region.LATIN_AMERICA +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.RiskType.TRADING,
			"EM NY HUB COP cross currency spread/basis",
			0.02,
			-220800.
		))
		{
			return false;
		}

		if (!capitalUnitStressEventContext.addIBSST (
			org.drip.capital.definition.Business.LOCAL_MARKETS +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.Region.LATIN_AMERICA +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.RiskType.TRADING,
			"Mexico - ICG  Business is actively trading on FX market given current market volatility",
			0.10,
			-11971045.8
		))
		{
			return false;
		}

		if (!capitalUnitStressEventContext.addIBSST (
			org.drip.capital.definition.Business.LOCAL_MARKETS +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.Region.LATIN_AMERICA +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.RiskType.TRADING,
			"Banamex: Off-shore/onshore EM US Spread coming  from Agency bonds (Pemex)",
			0.02,
			0.
		))
		{
			return false;
		}

		if (!capitalUnitStressEventContext.addIBSST (
			org.drip.capital.definition.Business.G10_RATES +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.Region.NORTH_AMERICA +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.RiskType.TRADING,
			"CMM vs TBA - mismatched convexity + supply/demand (10bp and 5bp)",
			0.02,
			-25200000.
		))
		{
			return false;
		}

		if (!capitalUnitStressEventContext.addIBSST (
			org.drip.capital.definition.Business.G10_RATES +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.Region.NORTH_AMERICA +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.RiskType.TRADING,
			"Spread vega Long/Short",
			0.02,
			-420000.
		))
		{
			return false;
		}

		if (!capitalUnitStressEventContext.addIBSST (
			org.drip.capital.definition.Business.G10_RATES +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.Region.NORTH_AMERICA +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.RiskType.TRADING,
			"TIPS Real Curve / BEI",
			0.02,
			-500000.
		))
		{
			return false;
		}

		if (!capitalUnitStressEventContext.addIBSST (
			org.drip.capital.definition.Business.MUNICIPAL +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.Region.NORTH_AMERICA +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.RiskType.TRADING,
			"Idiosyncratic Risk for Puerto Rico ¿ Recent legislation changes which allow restructuring of the three local entities (PREPA/PRASA/Transportation) resulted in an average 20 point drop across all PR positions.",
			0.01,
			-22523746.6
		))
		{
			return false;
		}

		if (!capitalUnitStressEventContext.addIBSST (
			org.drip.capital.definition.Business.CASH +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.Region.NORTH_AMERICA +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.RiskType.TRADING,
			"Event Risk",
			0.01,
			-10673000.
		))
		{
			return false;
		}

		if (!capitalUnitStressEventContext.addIBSST (
			org.drip.capital.definition.Business.G10_RATES +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.Region.NORTH_AMERICA +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.RiskType.TRADING,
			"SOFR/OIS Spread",
			0.1,
			-1504000.
		))
		{
			return false;
		}

		if (!capitalUnitStressEventContext.addIBSST (
			org.drip.capital.definition.Business.EQUITY_DERIVATIVES +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.Region.NORTH_AMERICA +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.RiskType.TRADING,
			"Non-recourse margin financing - AM",
			0.02,
			-96600000.
		))
		{
			return false;
		}

		if (!capitalUnitStressEventContext.addIBSST (
			org.drip.capital.definition.Business.G10_RATES +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.Region.NORTH_AMERICA +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.RiskType.TRADING,
			"OTR/OFR Spread",
			0.10,
			-2500000.
		))
		{
			return false;
		}

		if (!capitalUnitStressEventContext.addIBSST (
			org.drip.capital.definition.Business.G10_RATES +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.Region.NORTH_AMERICA +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.RiskType.TRADING,
			"Spread Vega Skew",
			0.02,
			-139600000.
		))
		{
			return false;
		}

		if (!capitalUnitStressEventContext.addIBSST (
			org.drip.capital.definition.Business.CASH +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.Region.NORTH_AMERICA +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.RiskType.TRADING,
			"Long/short single stock portfolio (quant)",
			0.02,
			-15060000.
		))
		{
			return false;
		}

		if (!capitalUnitStressEventContext.addIBSST (
			org.drip.capital.definition.Business.G10_RATES +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.Region.NORTH_AMERICA +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.RiskType.TRADING,
			"Inflation 2FHW parameters",
			0.02,
			-2800000.
		))
		{
			return false;
		}

		if (!capitalUnitStressEventContext.addIBSST (
			org.drip.capital.definition.Business.G10_FX +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.Region.NORTH_AMERICA +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.RiskType.TRADING,
			"Stress P&L from DCF trades where underlying M&A trade doesn¿t complete and  adverse move in spot for underlying M&A related trades.",
			0.01,
			-39000000.
		))
		{
			return false;
		}

		if (!capitalUnitStressEventContext.addIBSST (
			org.drip.capital.definition.Business.G10_RATES +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.Region.NORTH_AMERICA +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.RiskType.TRADING,
			"US Treasury STRIPS basis",
			0.02,
			-6468000.
		))
		{
			return false;
		}

		if (!capitalUnitStressEventContext.addIBSST (
			org.drip.capital.definition.Business.G10_FX +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.Region.NORTH_AMERICA +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.RiskType.TRADING,
			"Sensitivity to jumps and turns in curve construction",
			0.05,
			-5640000.
		))
		{
			return false;
		}

		if (!capitalUnitStressEventContext.addIBSST (
			org.drip.capital.definition.Business.MUNICIPAL +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.Region.NORTH_AMERICA +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.RiskType.TRADING,
			"Illinois - Increased credit risk due to fiscal and pension liabilities",
			0.10,
			4316710.3
		))
		{
			return false;
		}

		if (!capitalUnitStressEventContext.addIBSST (
			org.drip.capital.definition.Business.G10_RATES +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.Region.NORTH_AMERICA +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.RiskType.TRADING,
			"Derivs discounting placeholder for risk to collateral assumption updates",
			0.02,
			-25000000.
		))
		{
			return false;
		}

		if (!capitalUnitStressEventContext.addIBSST (
			org.drip.capital.definition.Business.G10_RATES +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.Region.NORTH_AMERICA +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.RiskType.TRADING,
			"Stress P&L for vanilla swaption skew. The calculation is based on strike vol time series and a portfolio of vanilla swaptions that replicate the trading books' SABR skew risk.",
			0.10,
			-12700000.
		))
		{
			return false;
		}

		if (!capitalUnitStressEventContext.addIBSST (
			org.drip.capital.definition.Business.G10_RATES +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.Region.NORTH_AMERICA +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.RiskType.TRADING,
			"dVega/dRate as per tier3",
			0.02,
			-44000000.
		))
		{
			return false;
		}

		if (!capitalUnitStressEventContext.addIBSST (
			org.drip.capital.definition.Business.G10_RATES +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.Region.NORTH_AMERICA +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.RiskType.TRADING,
			"Bermudan peak market discount",
			0.02,
			-52200000.
		))
		{
			return false;
		}

		if (!capitalUnitStressEventContext.addIBSST (
			org.drip.capital.definition.Business.G10_FX +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.Region.NORTH_AMERICA +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.RiskType.TRADING,
			"Base rate basis OIS basis and money market basis",
			0.03,
			-10000000.
		))
		{
			return false;
		}

		if (!capitalUnitStressEventContext.addIBSST (
			org.drip.capital.definition.Business.G10_RATES +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.Region.NORTH_AMERICA +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.RiskType.TRADING,
			"Short Pass thru TBA option gamma",
			0.10,
			0.
		))
		{
			return false;
		}

		if (!capitalUnitStressEventContext.addIBSST (
			org.drip.capital.definition.Business.G10_RATES +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.Region.NORTH_AMERICA +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.RiskType.TRADING,
			"Short ZC inflation vega / gamma and cross-effects (RNIV item)",
			0.10,
			-10500000.
		))
		{
			return false;
		}

		if (!capitalUnitStressEventContext.addIBSST (
			org.drip.capital.definition.Business.CASH +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.Region.NORTH_AMERICA +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.RiskType.TRADING,
			"Long/short single stock portfolio",
			0.02,
			-6090000.
		))
		{
			return false;
		}

		if (!capitalUnitStressEventContext.addIBSST (
			org.drip.capital.definition.Business.COMMODITIES_HOUSTON +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.Region.NORTH_AMERICA +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.RiskType.TRADING,
			"Loss of Correlation across the Board",
			0.02,
			-67349070.2
		))
		{
			return false;
		}

		if (!capitalUnitStressEventContext.addIBSST (
			org.drip.capital.definition.Business.SECURITIZED_MARKETS +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.Region.NORTH_AMERICA +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.RiskType.TRADING,
			"Agency RMBS - GSE reform changes to underwriting standards and streamline refinance programs would cause most Agency RMBS priced at a premium.  Change by FHFA which would increase refinancing of existing loans.  Fed rate guidance continued watched.",
			0.10,
			-63935400.
		))
		{
			return false;
		}

		if (!capitalUnitStressEventContext.addIBSST (
			org.drip.capital.definition.Business.G10_FX +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.Region.NORTH_AMERICA +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.RiskType.TRADING,
			"Intramonth downside spot gamma stress impacts on any given ccy pair where expiries  or peaks in exposure may be larger or different than at m/end.  200mm loss indicator over +-10% range is used daily in reporting but there could be circumstance where individual pair has exposure greater than this particularly in USD pairs given size of options franchise.   This type of spot gamma risk captured in var/svar to 2.3SD/6SD so BSST reflects possibility of further gamma due to wider or more complex moves.",
			0.01,
			-152000000.
		))
		{
			return false;
		}

		return true;
	}

	private static boolean LoadCBSST (
		final org.drip.capital.shell.CapitalUnitStressEventContext capitalUnitStressEventContext)
	{
		try
		{
			if (!capitalUnitStressEventContext.addCBSST (
				org.drip.capital.definition.Business.G10_RATES +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.ASIA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				"Mgmt Option Flow Vol Option & CMS book cross-gamma placeholder",
				SystemicPnLSeries (
					0.,
					-2725000.,
					0.,
					0.,
					0.,
					0.,
					0.,
					-3275000.,
					0.,
					0.,
					0.,
					0.,
					0.,
					-4000000.,
					0.,
					0.,
					0.,
					0.
				)
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addCBSST (
				org.drip.capital.definition.Business.EQUITY_DERIVATIVES +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.ASIA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				"Asset/asset correlation",
				SystemicPnLSeries (
					-16000000., // 1974 Baseline
					-20000000., // 2008 Baseline
					-19000000., // Deep Down-turn
					0., // Dollar Decline
					0., // Interest Rate Shock
					-11000000., // Lost Decade
					-18400000.00,
					-23000000.00,
					-21900000.00,
					0.00,
					0.00,
					-12700000.00,
					-24800000.00,
					-31000000.00,
					-29500000.00,
					0.00,
					0.00,
					-17100000.00
				)
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addCBSST (
				org.drip.capital.definition.Business.EM_CREDIT_TRADING +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.ASIA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				"Structured credit desk holds short-term bond options. BSST shock on vol bumps",
				SystemicPnLSeries (
					0., // 1974 Baseline
					0., // 2008 Baseline
					0., // Deep Down-turn
					0., // Dollar Decline
					0., // Interest Rate Shock
					0.,  // Lost Decade
					0., // 1974 Baseline
					0., // 2008 Baseline
					0., // Deep Down-turn
					0., // Dollar Decline
					0., // Interest Rate Shock
					0.,  // Lost Decade
					0., // 1974 Baseline
					0., // 2008 Baseline
					0., // Deep Down-turn
					0., // Dollar Decline
					0., // Interest Rate Shock
					0.  // Lost Decade
				)
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addCBSST (
				org.drip.capital.definition.Business.G10_RATES +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.ASIA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				"PRDC book cross-gamma/correlation placeholder",
				SystemicPnLSeries (
					0., // 1974 Baseline
					-7500000, // 2008 Baseline
					0., // Deep Down-turn
					0., // Dollar Decline
					0., // Interest Rate Shock
					0.,  // Lost Decade
					0., // 1974 Baseline
					-5000000., // 2008 Baseline
					0., // Deep Down-turn
					0., // Dollar Decline
					0., // Interest Rate Shock
					0. , // Lost Decade
					0., // 1974 Baseline
					-5000000., // 2008 Baseline
					0., // Deep Down-turn
					0., // Dollar Decline
					0., // Interest Rate Shock
					0.  // Lost Decade
				)
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addCBSST (
				org.drip.capital.definition.Business.EQUITY_DERIVATIVES +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.ASIA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				"Asset/FX correlation",
				SystemicPnLSeries (
					-480000., // 1974 Baseline
					-600000., // 2008 Baseline
					-570000., // Deep Down-turn
					0., // Dollar Decline
					0., // Interest Rate Shock
					-330000.,  // Lost Decade
					-480000.00,
					-600000.00,
					-570000.00,
					0.00,
					0.00,
					-330000.00,
					-480000.00,
					-600000.00,
					-570000.00,
					0.00,
					0.00,
					-330000.00
				)
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addCBSST (
				org.drip.capital.definition.Business.EM_CREDIT_TRADING +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.ASIA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				"India Credit Trading desk holds INR LCY corporate bonds. GSST shocks were calibrated based on FCY shocks. Adjustments to capture difference on referencing onshore corporate bond spreads.",
				SystemicPnLSeries (
					16450600., // 1974 Baseline
					19800000., // 2008 Baseline
					11100000., // Deep Down-turn
					0., // Dollar Decline
					8522600., // Interest Rate Shock
					4558600.,  // Lost Decade
					19300000.00,
					23200000.00,
					13000000.00,
					0.00,
					9976000.00,
					5336000.00,
					12800000.00,
					15400000.00,
					8624000.00,
					0.00,
					6622000.00,
					3542000.00
				)
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addCBSST (
				org.drip.capital.definition.Business.EQUITY_DERIVATIVES +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.ASIA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				"Dividend Risk",
				SystemicPnLSeries (
					-20000000., // 1974 Baseline
					-25000000., // 2008 Baseline
					-23800000., // Deep Down-turn
					0., // Dollar Decline
					0., // Interest Rate Shock
					-13800000.,  // Lost Decade
					-14000000.00,
					-17500000.00,
					-16600000.00,
					0.00,
					0.00,
					-9625000.00,
					-16800000.00,
					-21000000.00,
					-20000000.00,
					0.00,
					0.00,
					-11600000.00
				)
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addCBSST (
				org.drip.capital.definition.Business.CONVERTS +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.ASIA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				"CB vs. CDS basis: Net CR01 from CB trading desks is currently small; however there is significant exposure to widening of the basis of CB vs CDS spreads.",
				SystemicPnLSeries (
					-1276515.9, // 1974 Baseline
					-1595644.9, // 2008 Baseline
					-957386.9, // Deep Down-turn
					0., // Dollar Decline
					0., // Interest Rate Shock
					-558475.7,  // Lost Decade
					-1336680.00,
					-1670850.00,
					-1002510.00,
					0.00,
					0.00,
					-584797.50,
					-839381.00,
					-1049226.30,
					-629535.80,
					0.00,
					0.00,
					-367229.20
				)
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addCBSST (
				org.drip.capital.definition.Business.EQUITY_DERIVATIVES +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.ASIA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				"Skew",
				SystemicPnLSeries (
					-2640000, // 1974 Baseline
					-3300000, // 2008 Baseline
					-3135000, // Deep Down-turn
					0., // Dollar Decline
					0., // Interest Rate Shock
					-1815000,  // Lost Decade
					-1600000.00,
					-2000000.00,
					-1900000.00,
					0.00,
					0.00,
					-1100000.00,
					-312000.00,
					-390000.00,
					-370500.00,
					0.00,
					0.00,
					-214500.00
				)
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addCBSST (
				org.drip.capital.definition.Business.EM_CREDIT_TRADING +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.ASIA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				"EMCT has ability to take advantage of the Index basis cheapening to lock in skew package at market levels (street-facing) versus fair value (facility).  Initial margin of $40mm protects the desk from Gap Risk if we face the situation of having positive MtM in excess of the IM against the counterparty and if they need to unwind the overall transaction.",
				SystemicPnLSeries (
					-1800000, // 1974 Baseline
					-3600000, // 2008 Baseline
					-1800000, // Deep Down-turn
					0., // Dollar Decline
					-1440000, // Interest Rate Shock
					-900000,  // Lost Decade
					-2050000.00,
					-4100000.00,
					-2050000.00,
					0.00,
					-1640000.00,
					-1025000.00,
					-300000.00,
					-600000.00,
					-300000.00,
					0.00,
					-240000.00,
					-150000.00
				)
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addCBSST (
				org.drip.capital.definition.Business.EM_CREDIT_TRADING +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.ASIA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				"Leveraged CLNs and TRS's",
				SystemicPnLSeries (
					-6750000, // 1974 Baseline
					-13500000, // 2008 Baseline
					-6750000, // Deep Down-turn
					0., // Dollar Decline
					-5400000, // Interest Rate Shock
					-3375000,  // Lost Decade
					-6750000, // 1974 Baseline
					-13500000, // 2008 Baseline
					-6750000, // Deep Down-turn
					0., // Dollar Decline
					-5400000, // Interest Rate Shock
					-3375000,  // Lost Decade
					-5500000.00,
					-11000000.00,
					-5500000.00,
					0.00,
					-4400000.00,
					-2750000.00
				)
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addCBSST (
				org.drip.capital.definition.Business.EQUITY_DERIVATIVES +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.ASIA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				"Borrow Cost",
				SystemicPnLSeries (
					-5520000, // 1974 Baseline
					-6900000, // 2008 Baseline
					-6555000, // Deep Down-turn
					0., // Dollar Decline
					0, // Interest Rate Shock
					-3795000,  // Lost Decade
					-4800000.00,
					-6000000.00,
					-5700000.00,
					0.00,
					0.00,
					-3300000.00,
					-4800000.00,
					-6000000.00,
					-5700000.00,
					0.00,
					0.00,
					-3300000.00
				)
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addCBSST (
				org.drip.capital.definition.Business.EM_CREDIT_TRADING +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.ASIA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				"Quanto Risk: Korea Sov/Corps & non-Korean names KRW credit protections arising from NTD and SN CDS.",
				SystemicPnLSeries (
					473776.2, // 1974 Baseline
					947552.5, // 2008 Baseline
					473776.2, // Deep Down-turn
					0., // Dollar Decline
					379021, // Interest Rate Shock
					236888.1,  // Lost Decade
					558272.30,
					1116544.60,
					558272.30,
					0.00,
					446617.80,
					279136.10,
					-1731284.60,
					-3462569.30,
					-1731284.60,
					0.00,
					-1385027.70,
					-865642.30
				)
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addCBSST (
				org.drip.capital.definition.Business.G10_RATES +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.EMEA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.CVA,
				"Italy Rate/Credit Model Parameter Risk",
				SystemicPnLSeries (
					-67100000,
					-83900000,
					-42000000,
					0,
					-33600000,
					-21000000,
					-64700000.00,
					-80900000.00,
					-40500000.00,
					0.00,
					-32400000.00,
					-20200000.00,
					0.00,
					0.00,
					0.00,
					0.00,
					0.00,
					0.00
				)
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addCBSST (
				org.drip.capital.definition.Business.G10_RATES +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.EMEA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.CVA,
				"New Financial CDS Shocks",
				SystemicPnLSeries (
					-22900000,
					-28663237.5,
					-14331618.7,
					0,
					-11465295,
					-7165809.4,
					-25300000.00,
					-31700000.00,
					-15800000.00,
					0.00,
					-12700000.00,
					-7921525.00,
					-25300000.00,
					-31700000.00,
					-15800000.00,
					0.00,
					-12700000.00,
					-7921525.00
				)
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addCBSST (
				org.drip.capital.definition.Business.G10_RATES +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.EMEA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.CVA,
				"Italy Hedge Bond/CDS Basis Risk",
				SystemicPnLSeries (
					-3057600,
					-3822000,
					-1911000,
					0,
					-1528800,
					-955500,
					-4720000.00,
					-5900000.00,
					-2950000.00,
					0.00,
					-2360000.00,
					-1475000.00,
					-4720000.00,
					-5900000.00,
					-2950000.00,
					0.00,
					-2360000.00,
					-1475000.00
				)
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addCBSST (
				org.drip.capital.definition.Business.CREDIT_TRADING +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.EMEA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				"Binary write down risk inherent in contingent convertibles instrument",
				SystemicPnLSeries (
					-36755873,
					-36755873,
					-18377936.5,
					0,
					-14702349.2,
					-9188968.3,
					-43087503.00,
					-43087503.00,
					-21543751.50,
					0.00,
					-17235001.20,
					-10771875.80,
					0,
					0,
					0,
					0,
					0,
					0
				)
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addCBSST (
				org.drip.capital.definition.Business.CREDIT_TRADING +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.EMEA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				"Underperformance of Financials: Sen vs Sub Basis & CDS Basis",
				SystemicPnLSeries (
					-24919668.7,
					-24919668.7,
					-12459834.3,
					0,
					-9967867.5,
					-6229917.2,
					-24814700.80,
					-24814700.80,
					-12407350.40,
					0.00,
					-9925880.30,
					-6203675.20,
					0,
					0,
					0,
					0,
					0,
					0
				)
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addCBSST (
				org.drip.capital.definition.Business.EQUITY_DERIVATIVES +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.EMEA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				"Asset/asset correlation",
				SystemicPnLSeries (
					-41600000,
					-52000000,
					-49400000,
					0,
					0,
					-28600000,
					-50600000.00,
					-63300000.00,
					-60100000.00,
					0.00,
					0.00,
					-34800000.00,
					0,
					0,
					0,
					0,
					0,
					0
				)
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addCBSST (
				org.drip.capital.definition.Business.EQUITY_DERIVATIVES +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.EMEA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				"Lack of GSST full reval Hybrids CIS",
				SystemicPnLSeries (
					-156000000,
					-156000000,
					-156000000,
					0,
					-156000000,
					-156000000,
					-68800000.00,
					-68800000.00,
					-68800000.00,
					0.00,
					-68800000.00,
					-68800000.00,
					0,
					0,
					0,
					0,
					0,
					0
				)
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addCBSST (
				org.drip.capital.definition.Business.CREDIT_TRADING +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.EMEA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				"Recovery Basis on multiple CDS default scenarios",
				SystemicPnLSeries (
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0.,
					0,
					0,
					0,
					0,
					0,
					0
				)
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addCBSST (
				org.drip.capital.definition.Business.CREDIT_TRADING +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.EMEA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				"ETF price shock reported as an add-on to current GSST.",
				SystemicPnLSeries (
					2596979.2,
					-2596979.2,
					-3895468.7,
					0,
					-973867.2,
					-1558187.5,
					778056.70,
					-778056.70,
					-1167085.10,
					0.00,
					-291771.30,
					-466834.00,
					0,
					0,
					0,
					0,
					0,
					0
				)
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addCBSST (
				org.drip.capital.definition.Business.EM_CREDIT_TRADING +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.EMEA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				"Quanto Risk: - Croatia Quanto (EUR/USD) because of large dollar issues in EUR - Romania RON repacks quanto CDS - Turkey TRY extinguishers - SoAf Sov/Corps ZAR extinguishers/quanto CDS",
				SystemicPnLSeries (
					350000,
					700000,
					350000,
					0,
					280000,
					175000,
					400000.00,
					800000.00,
					400000.00,
					0.00,
					320000.00,
					200000.00,
					0,
					0,
					0,
					0,
					0,
					0
				)
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addCBSST (
				org.drip.capital.definition.Business.EQUITY_DERIVATIVES +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.EMEA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				"Dividend Risk",
				SystemicPnLSeries (
					-72600000,
					-90700000,
					-86200000,
					0,
					0,
					-49900000,
					-83800000.00,
					-105000000.00,
					-99500000.00,
					0.00,
					0.00,
					-57600000.00,
					-5920000.00,
					-7400000.00,
					-7030000.00,
					0.00,
					0.00,
					-4070000.00
				)
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addCBSST (
				org.drip.capital.definition.Business.CREDIT_TRADING +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.EMEA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				"Quanto CDS on EU Flow desks",
				SystemicPnLSeries (
					-1000603.7,
					-1000603.7,
					-500301.9,
					0,
					-400241.5,
					-250150.9,
					-353968.50,
					-353968.50,
					-176984.20,
					0.00,
					-141587.40,
					-88492.10,
					0,
					0,
					0,
					0,
					0,
					0
				)
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addCBSST (
				org.drip.capital.definition.Business.PECD +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.EMEA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				"Correlation skew basis (bespoke vs index tranche)",
				SystemicPnLSeries (
					-1446762.7,
					-1569354.4,
					-784677.2,
					0,
					-627741.7,
					-392338.6,
					-1812552.20,
					-1966139.10,
					-983069.50,
					0.00,
					-786455.60,
					-491534.80,
					0,
					0,
					0,
					0,
					0,
					0
				)
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addCBSST (
				org.drip.capital.definition.Business.CREDIT_TRADING +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.EMEA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				"GSST add-on for more granular shocks for Distressed assets",
				SystemicPnLSeries (
					-2777186.9,
					-2777186.9,
					-1388593.5,
					0,
					-1110874.8,
					-694296.7,
					-8605594.60,
					-8605594.60,
					-4302797.30,
					0.00,
					-3442237.80,
					-2151398.60,
					0,
					0,
					0,
					0,
					0,
					0
				)
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addCBSST (
				org.drip.capital.definition.Business.EQUITY_DERIVATIVES +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.EMEA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				"Correlation",
				SystemicPnLSeries (
					-2080000,
					-2600000,
					-2470000,
					0,
					0,
					-1430000,
					-2400000.00,
					-3000000.00,
					-2850000.00,
					0.00,
					0.00,
					-1650000.00,
					-2080000,
					-2600000,
					-2470000,
					0,
					0,
					-1430000
				)
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addCBSST (
				org.drip.capital.definition.Business.PECD +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.EMEA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				"Secondary FX exposure not centrally hedged by treasury",
				SystemicPnLSeries (
					-129131,
					129131,
					193696.4,
					0,
					48424.1,
					77478.6,
					59545.90,
					-59545.90,
					-89318.90,
					0.00,
					-22329.70,
					-35727.60,
					-48128.20,
					48128.20,
					72192.40,
					0.00,
					18048.10,
					28876.90
				)
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addCBSST (
				org.drip.capital.definition.Business.EQUITY_DERIVATIVES +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.EMEA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				"Borrow Cost",
				SystemicPnLSeries (
					-16200000,
					-20200000,
					-19200000,
					0,0,
					-11100000,
					-18600000.00,
					-23200000.00,
					-22000000.00,
					0.00,
					0.00,
					-12800000.00,
					-20200000.00,
					-25300000.00,
					-24000000.00,
					0.00,
					0.00,
					-13900000.00
				)
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addCBSST (
				org.drip.capital.definition.Business.CREDIT_TRADING +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.EMEA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				"Sovereign CDS doc clause basis widening",
				SystemicPnLSeries (
					-1354676.3,
					-1354676.3,
					-677338.1,
					0,
					-541870.5,
					-338669.1,
					-862361.80,
					-862361.80,
					-431180.90,
					0.00,
					-344944.70,
					-215590.50,
					-862361.80,
					-862361.80,
					-431180.90,
					0.00,
					-344944.70,
					-215590.50
				)
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addCBSST (
				org.drip.capital.definition.Business.CREDIT_TRADING +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.EMEA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				"Callable Bonds - vega risk arising from yield volatility. In the current yield-to-worst calculation volatility is not modeled.",
				SystemicPnLSeries (
					0,
					0,
					0,
					0,
					0,
					0,
					-1299144.70,
					-1299144.70,
					-649572.40,
					0.00,
					-519657.90,
					-324786.20,
					-1299144.70,
					-1299144.70,
					-649572.40,
					0.00,
					-519657.90,
					-324786.20
				)
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addCBSST (
				org.drip.capital.definition.Business.EQUITY_DERIVATIVES +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.EMEA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				"Skew",
				SystemicPnLSeries (
					-7840000,
					-9800000,
					-9310000,
					0,
					0,
					-5390000,
					-5440000.00,
					-6800000.00,
					-6460000.00,
					0.00,
					0.00,
					-3740000.00,
					-4960000.00,
					-6200000.00,
					-5890000.00,
					0.00,
					0.00,
					-3410000.00
				)
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addCBSST (
				org.drip.capital.definition.Business.EQUITY_DERIVATIVES +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.EMEA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				"Asset/FX correlation",
				SystemicPnLSeries (
					-3520000,
					-4400000,
					-4180000,
					0,
					0,
					-2420000,
					-2400000.00,
					-3000000.00,
					-2850000.00,
					0.00,
					0.00,
					-1650000.00,
					-2960000.00,
					-3700000.00,
					-3515000.00,
					0.00,
					0.00,
					-2035000.00
				)
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addCBSST (
				org.drip.capital.definition.Business.CONVERTS +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.EMEA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				"ECM - CB CR01",
				SystemicPnLSeries (
					0,
					0,
					0,
					0,
					0,
					0,
					0,
					0,
					0,
					0,
					0,
					0,
					0,
					0,
					0,
					0,
					0,
					0
				)
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addCBSST (
				org.drip.capital.definition.Business.EM_CREDIT_TRADING +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.EMEA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				"Collateralized Loan GAP RISK : FX  Rates and Credit Gap Risk on Collateral posted . $bn",
				SystemicPnLSeries (
					-6200000,
					-12400000,
					-6200000,
					0,
					-4960000,
					-3100000,
					-6200000,
					-12400000,
					-6200000,
					0,
					-4960000,
					-3100000,
					-6200000.00,
					-12400000.00,
					-6200000.00,
					0.00,
					-4960000.00,
					-3100000.00
				)
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addCBSST (
				org.drip.capital.definition.Business.G10_RATES +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.EMEA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				"EU Finance desk long Italian bills",
				SystemicPnLSeries (
					0,
					-10000000,
					0,
					0,
					0,
					0,
					0,
					-10000000,
					0,
					0,
					0,
					0,
					0,
					-5200000,
					0,
					0,
					0,
					0
				)
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addCBSST (
				org.drip.capital.definition.Business.CONVERTS +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.EMEA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				"CB vs. CDS basis: Net CR01 from CB trading desks is currently small; however there is significant exposure to widening of the basis of CB vs CDS spreads.",
				SystemicPnLSeries (
					-2495272.7,
					-3119090.9,
					-1871454.5,
					0,
					0,
					-1091681.8,
					-2802909.10,
					-3503636.40,
					-2102181.80,
					0.00,
					0.00,
					-1226272.70,
					-2085090.90,
					-2606363.60,
					-1563818.20,
					0.00,
					0.00,
					-912227.30
				)
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addCBSST (
				org.drip.capital.definition.Business.CREDIT_TRADING +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.EMEA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				"Recovery Basis on Watchlist banks",
				SystemicPnLSeries (
					0,
					0,
					0,
					0,
					0,
					0,
					0,
					0,
					0,
					0,
					0,
					0,
					0,
					0,
					0,
					0,
					0,
					0
				)
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addCBSST (
				org.drip.capital.definition.Business.EM_CREDIT_TRADING +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.LATIN_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				"Collateralized Loan GAP RISK : FX and Credit Gap Risk on Collateral. $bn",
				SystemicPnLSeries (
					-10100000,
					-20200000,
					-10100000,
					0,
					-8080000,
					-5050000,
					-9465000.00,
					-18900000.00,
					-9465000.00,
					0.00,
					-7572000.00,
					-4732500.00,
					-9450000.00,
					-18900000.00,
					-9450000.00,
					0.00,
					-7560000.00,
					-4725000.00
				)
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addCBSST (
				org.drip.capital.definition.Business.EM_CREDIT_TRADING +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.LATIN_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				"Quanto Risk: - Chile Corporate CLP Repacks CLN - Colombian COP Extinguisher - Peru PEN Extinguisher and related quanto CDS hedges in CLP COP and PEN",
				SystemicPnLSeries (
					-1800000,
					-3600000,
					-1800000,
					0,
					-1440000,
					-900000,
					-1800000,
					-3600000,
					-1800000,
					0,
					-1440000,
					-900000,
					-1850000.00,
					-3700000.00,
					-1850000.00,
					0.00,
					-1480000.00,
					-925000.00
				)
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addCBSST (
				org.drip.capital.definition.Business.CREDIT_TRADING +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.NORTH_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.CVA,
				"Wrong-Way Risk",
				SystemicPnLSeries (
					-1280375.2,
					-1600469,
					-800234.5,
					0,
					-640187.6,
					-400117.3,
					-1641107.20,
					-2051384.00,
					-1025692.00,
					0.00,
					-820553.60,
					-512846.00,
					-1641107.20,
					-2051384.00,
					-1025692.00,
					0.00,
					-820553.60,
					-512846.00
				)
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addCBSST (
				org.drip.capital.definition.Business.MUNICIPAL +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.NORTH_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				"MCDX-single name CDS basis risk",
				SystemicPnLSeries (
					-5270193.7,
					-5270193.7,
					-2635096.8,
					0,
					-2108077.5,
					-1317548.4,
					-6107389.10,
					-6107389.10,
					-3053694.60,
					0.00,
					-2442955.70,
					-1526847.30,
					-5398708.60,
					-5398708.60,
					-2699354.30,
					0.00,
					-2159483.40,
					-1349677.10
				)
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addCBSST (
				org.drip.capital.definition.Business.CREDIT_TRADING +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.NORTH_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				"ETF price shock reported as an add-on to current GSST.",
				SystemicPnLSeries (
					7790937.5,
					-7790937.5,
					-11686406.2,
					0,
					-2921601.6,
					-4674562.5,
					2334170.10,
					-2334170.10,
					-3501255.20,
					0.00,
					-875313.80,
					-1400502.10,
					-9277411.20,
					9277411.20,
					13916116.80,
					0.00,
					3479029.20,
					5566446.70
				)
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addCBSST (
				org.drip.capital.definition.Business.PECD +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.NORTH_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				"Secondary FX exposure not centrally hedged by treasury",
				SystemicPnLSeries (
					-387392.9,
					387392.9,
					581089.3,
					0,
					145272.3,
					232435.7,
					178637.80,
					-178637.80,
					-267956.60,
					0.00,
					-66989.20,
					-107182.70,
					-144384.70,
					144384.70,
					216577.10,
					0.00,
					54144.30,
					86630.80
				)
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addCBSST (
				org.drip.capital.definition.Business.SECURITIZED_MARKETS +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.NORTH_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				"Pay up basis between the Agency Specified Pools and coresponding Agency TBAs",
				SystemicPnLSeries (
					-95173191.7,
					-118966489.6,
					-59483244.8,
					0,
					-47586595.8,
					-29741622.4,
					-98286136.90,
					-122857671.20,
					-61428835.60,
					0.00,
					-49143068.50,
					-30714417.80,
					-65652714.40,
					-82065893.00,
					-41032946.50,
					0.00,
					-32826357.20,
					-20516473.30
				)
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addCBSST (
				org.drip.capital.definition.Business.EQUITY_DERIVATIVES +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.NORTH_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				"Borrow Cost",
				SystemicPnLSeries (
					0,
					0,
					0,
					0,
					0,
					0,
					0,
					0,
					0,
					0,
					0,
					0,
					-2080000.00,
					-2600000.00,
					-2470000.00,
					0.00,
					0.00,
					-1430000.00
				)
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addCBSST (
				org.drip.capital.definition.Business.CREDIT_TRADING +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.NORTH_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				"Callable Bonds - vega risk arising from yield volatility. In the current yield-to-worst calculation volatility is not modeled.",
				SystemicPnLSeries (
					-4099820,
					-4099820,
					-2049910,
					0,
					-1639928,
					-1024955,
					-4099820,
					-4099820,
					-2049910,
					0,
					-1639928,
					-1024955,
					-3930702.60,
					-3930702.60,
					-1965351.30,
					0.00,
					-1572281.00,
					-982675.70
				)
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addCBSST (
				org.drip.capital.definition.Business.PECD +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.NORTH_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				"Sharp decline in loan prices leads to TRS termination triggers being breached requiring additional margin to be posted or close-out/liquidiation of hedges to commence",
				SystemicPnLSeries (
					-49003856,
					-98007711.9,
					-49003856,
					0,
					-39203084.8,
					-24501928,
					-45387291.40,
					-90774582.70,
					-45387291.40,
					0.00,
					-36309833.10,
					-22693645.70,
					-57208342.50,
					-114416685.00,
					-57208342.50,
					0.00,
					-45766674.00,
					-28604171.20
				)
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addCBSST (
				org.drip.capital.definition.Business.MUNICIPAL +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.NORTH_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				"Rating downgrade (by one letter grade) of the top name in the cash hedges for MMD rate locks",
				SystemicPnLSeries (
					-875292.2,
					-875292.2,
					-437646.1,
					0,
					-350116.9,
					-218823.1,
					-551317.30,
					-551317.30,
					-275658.60,
					0.00,
					-220526.90,
					-137829.30,
					-837126.20,
					-837126.20,
					-418563.10,
					0.00,
					-334850.50,
					-209281.50
				)
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addCBSST (
				org.drip.capital.definition.Business.MUNICIPAL +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.NORTH_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				"MMD rate lock-cash basis risk",
				SystemicPnLSeries (
					-974268,
					-974268,
					-487134,
					0,
					-389707.2,
					-243567,
					-1280002.30,
					-1280002.30,
					-640001.20,
					0.00,
					-512000.90,
					-320000.60,
					-5033860.20,
					-5033860.20,
					-2516930.10,
					0.00,
					-2013544.10,
					-1258465.10
				)
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addCBSST (
				org.drip.capital.definition.Business.G10_RATES +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.Region.NORTH_AMERICA +
				org.drip.capital.label.Coordinate.FQN_DELIMITER +
				org.drip.capital.definition.RiskType.TRADING,
				"Muni Ratio Vega/Correlation",
				SystemicPnLSeries (
					0,
					-1400000,
					-420000,
					0,
					0,
					0,
					0,
					-630000.00,
					-189000.00,
					0,
					0,
					0,
					0,
					-350000.00,
					-105000.00,
					0,
					0,
					0
				)
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addCBSST (
				org.drip.capital.definition.Business.G10_RATES +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.NORTH_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				"Agency desk European Sov (Spain Italy Ukraine) MV",
				SystemicPnLSeries (
					0,
					-4600,
					0,
					0,
					0,
					0,
					0,
					-4700,
					0,
					0,
					0,
					0,
					0,
					-4600,
					0,
					0,
					0,
					0
				)
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addCBSST (
				org.drip.capital.definition.Business.MUNICIPAL +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.NORTH_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				"TRS-MMD rate lock basis risk",
				SystemicPnLSeries (
					-3521333.4,
					-3521333.4,
					-1760666.7,
					0,
					-1408533.4,
					-880333.3,
					-4124042.40,
					-4124042.40,
					-2062021.20,
					0.00,
					-1649617.00,
					-1031010.60,
					-4054608.10,
					-4054608.10,
					-2027304.00,
					0.00,
					-1621843.20,
					-1013652.00
				)
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addCBSST (
				org.drip.capital.definition.Business.PECD +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.NORTH_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				"Severe tubulence in Credit markets leads to widening of the TRS funding leg spread.",
				SystemicPnLSeries (
					-35326235.1,
					-70652470.2,
					-35326235.1,
					0,
					-28260988.1,
					-17663117.5,
					-38695688.40,
					-77391376.90,
					-38695688.40,
					0.00,
					-30956550.70,
					-19347844.20,
					-41657141.60,
					-83314283.30,
					-41657141.60,
					0.00,
					-33325713.30,
					-20828570.80
				)
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addCBSST (
				org.drip.capital.definition.Business.DISTRESSED +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.NORTH_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				"Defaulted debt and distressed debt price shock with product differentiation - incremental to GSST one cut shock",
				SystemicPnLSeries (
					-2030000,
					12200000,
					-20300000,
					0,
					-20300000,
					-50800000,
					-3465000.00,
					20800000.00,
					-34700000.00,
					0.00,
					-34700000.00,
					-86600000.00,
					-1735000.00,
					10400000.00,
					-17400000.00,
					0.00,
					-17400000.00,
					-43400000.00
				)
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addCBSST (
				org.drip.capital.definition.Business.PECD +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.NORTH_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				"Leveraged index basis trades where client only posts margin up to the unlevered notional amount",
				SystemicPnLSeries (
					-5055250,
					-10100000,
					-5055250,
					0,
					-4044200,
					-2527625,
					-6019000.00,
					-12000000.00,
					-6019000.00,
					0.00,
					-4815200.00,
					-3009500.00,
					-6645901.50,
					-13291803.00,
					-6645901.50,
					0.00,
					-5316721.20,
					-3322950.80
				)
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addCBSST (
				org.drip.capital.definition.Business.CREDIT_TRADING +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.NORTH_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				"Seconary FX exposure not centrally hedged by Treasury",
				SystemicPnLSeries (
					-485813,
					485813,
					728719.6,
					0,
					182179.9,
					291487.9,
					-476646.20,
					476646.20,
					714969.40,
					0.00,
					178742.30,
					285987.70,
					-472867.20,
					472867.20,
					709300.80,
					0.00,
					177325.20,
					283720.30
				)
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addCBSST (
				org.drip.capital.definition.Business.G10_RATES +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.NORTH_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				"Possible calibration issues with SABR e.g. CMS - model risk",
				SystemicPnLSeries (
					0,
					-15000000,
					-9000000,
					0,
					0,
					0,
					0,
					-15000000,
					-9000000,
					0,
					0,
					0,
					0,
					-15000000,
					-9000000,
					0,
					0,
					0
				)
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addCBSST (
				org.drip.capital.definition.Business.EQUITY_DERIVATIVES +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.NORTH_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				"Asset/asset correlation",
				SystemicPnLSeries (
					-25500000,
					-31900000,
					-30300000,
					0,
					0,
					-17500000,
					-27700000.00,
					-34600000.00,
					-32870000.00,
					0.00,
					0.00,
					-19000000.00,
					-23000000.00,
					-28800000.00,
					-27400000.00,
					0.00,
					0.00,
					-15800000.00
				)
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addCBSST (
				org.drip.capital.definition.Business.EQUITY_DERIVATIVES +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.NORTH_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				"Dividend Risk",
				SystemicPnLSeries (
					-25400000,
					-31800000,
					-30200000,
					0,
					0,
					-17500000,
					-20100000.00,
					-25100000.00,
					-23800000.00,
					0.00,
					0.00,
					-13800000.00,
					-17400000.00,
					-21700000.00,
					-20600000.00,
					0.00,
					0.00,
					-11900000.00
				)
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addCBSST (
				org.drip.capital.definition.Business.EQUITY_DERIVATIVES +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.NORTH_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				"Skew",
				SystemicPnLSeries (
					-9280000,
					-11600000,
					-11000000,
					0,
					0,
					-6380000,
					-8560000.00,
					-10700000.00,
					-10200000.00,
					0.00,
					0.00,
					-5885000.00,
					-8240000.00,
					-10300000.00,
					-9785000.00,
					0.00,
					0.00,
					-5665000.00
				)
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addCBSST (
				org.drip.capital.definition.Business.EQUITY_DERIVATIVES +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.NORTH_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				"Asset/FX correlation",
				SystemicPnLSeries (
					-272000,
					-340000,
					-323000,
					0,
					0,
					-187000,
					0,
					0,
					0,
					0,
					0,
					0,
					-560000.00,
					-700000.00,
					-665000.00,
					0.00,
					0.00,
					-385000.00
				)
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addCBSST (
				org.drip.capital.definition.Business.CONVERTS +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.NORTH_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				"CB vs. CDS basis: Net CR01 from CB trading desks is currently small; however there is significant exposure to widening of the basis of CB vs CDS spreads.",
				SystemicPnLSeries (
					-9468363.6,
					-11835454.5,
					-7101272.7,
					0,
					0,
					-4142409.1,
					-4375272.70,
					-5469090.90,
					-3281454.50,
					0.00,
					0.00,
					-1914181.80,
					-4306909.10,
					-5383636.40,
					-3230181.80,
					0.00,
					0.00,
					-1884272.70
				)
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addCBSST (
				org.drip.capital.definition.Business.MUNICIPAL +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.NORTH_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				"Market Liquidity Contagion for Tobacco Sector ¿ As high yield institutional muni investors tend to hold both PR and Tobacco bonds liquidity will deteriorate for Tobacco when PR or other high yield sector market decline.",
				SystemicPnLSeries (
					-13600000,
					-13600000,
					-6801190,
					0,
					-5440952,
					-3400595,
					-16946918.10,
					-16946918.10,
					-8473459.00,
					0.00,
					-6778767.20,
					-4236729.50,
					-29000000.00,
					-29000000.00,
					-14500000.00,
					0.00,
					-11600000.00,
					-7240500.00
				)
			))
			{
				return false;
			}

			if (!capitalUnitStressEventContext.addCBSST (
				org.drip.capital.definition.Business.PECD +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.Region.NORTH_AMERICA +
					org.drip.capital.label.Coordinate.FQN_DELIMITER +
					org.drip.capital.definition.RiskType.TRADING,
				"Correlation skew basis (bespoke vs index tranche)",
				SystemicPnLSeries (
					-4340288.2,
					-4708063.1,
					-2354031.5,
					0,
					-1883225.2,
					-1177015.8,
					-5437656.70,
					-5898417.20,
					-2949208.60,
					0.00,
					-2359366.90,
					-1474604.30,
					-3535203.10,
					-3834759.00,
					-1917379.50,
					0.00,
					-1533903.60,
					-958689.80
				)
			))
			{
				return false;
			}

			return true;
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Instantiate the Built-in CapitalUnitStressEventContext
	 * 
	 * @return TRUE - The CapitalUnitStressEventContext Instance
	 */

	public static org.drip.capital.shell.CapitalUnitStressEventContext Instantiate()
	{
		org.drip.capital.shell.CapitalUnitStressEventContext capitalUnitStressEventContext =
			new org.drip.capital.shell.CapitalUnitStressEventContext();

		return LoadGSST (
			capitalUnitStressEventContext
		) && LoadIBSST (
			capitalUnitStressEventContext
		) && LoadCBSST (
			capitalUnitStressEventContext
		) ? capitalUnitStressEventContext : null;
	}
}
