
package org.drip.sample.simmsettings;

import java.util.List;
import java.util.Map;

import org.drip.measure.state.LabelledRdCorrelation;
import org.drip.service.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.simm.rates.IRSettingsContainer24;
import org.drip.simm.rates.IRSystemics24;
import org.drip.simm.rates.IRWeight;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2022 Lakshmi Krishnamurthy
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
 * <i>InterestRate24</i> demonstrates the Extraction and Display of ISDA SIMM 2.4 Single/Cross Currency
 * 	Interest Rate Tenor Risk Weights, Systemics, and Correlations. The References are:
 * 
 * <br><br>
 *  <ul>
 *  	<li>
 *  		Andersen, L. B. G., M. Pykhtin, and A. Sokol (2017): Credit Exposure in the Presence of Initial
 *  			Margin https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2806156 <b>eSSRN</b>
 *  	</li>
 *  	<li>
 *  		Albanese, C., S. Caenazzo, and O. Frankel (2017): Regression Sensitivities for Initial Margin
 *  			Calculations https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2763488 <b>eSSRN</b>
 *  	</li>
 *  	<li>
 *  		Anfuso, F., D. Aziz, P. Giltinan, and K. Loukopoulus (2017): A Sound Modeling and Back-testing
 *  			Framework for Forecasting Initial Margin Requirements
 *  				https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2716279 <b>eSSRN</b>
 *  	</li>
 *  	<li>
 *  		Caspers, P., P. Giltinan, R. Lichters, and N. Nowaczyk (2017): Forecasting Initial Margin
 *  			Requirements - A Model Evaluation https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2911167
 *  				<b>eSSRN</b>
 *  	</li>
 *  	<li>
 *  		International Swaps and Derivatives Association (2021): SIMM v2.4 Methodology
 *  			https://www.isda.org/a/CeggE/ISDA-SIMM-v2.4-PUBLIC.pdf
 *  	</li>
 *  </ul>
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/MarginAnalyticsLibrary.md">Initial and Variation Margin Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/simmsettings/README.md">ISDA SIMM Calibration Parameter Settings</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class InterestRate24
{

	private static final void RegularVolatility()
		throws Exception
	{
		System.out.println ("\t||-----------------------------------------------------------------------------------||");

		System.out.println ("\t||               2.4 REGULAR VOLATILITY CURRENCY SET and RISK WEIGHTS                ||");

		System.out.println ("\t||-----------------------------------------------------------------------------------||");

		System.out.println (
			"\t|| Currency Set => " +
			IRSettingsContainer24.RegularVolatilityCurrencySet() + " ||"
		);

		System.out.println ("\t||-----------------------------------------------------------------------------------||");

		System.out.println
			("\t||------------------------------------------------------------------------------------------------------------------------------------------------------||");

		IRWeight usdRiskWeight = IRSettingsContainer24.RiskWeight ("USD");

		String tenorWeightSequence = "\t|| ";

		for (Map.Entry<String, Double> tenorWeightEntry : usdRiskWeight.tenorDelta().entrySet())
		{
			tenorWeightSequence = tenorWeightSequence + " " + tenorWeightEntry.getKey() + " => " +
				FormatUtil.FormatDouble (tenorWeightEntry.getValue(), 1, 0, 1.) + " |";
		}

		System.out.println (tenorWeightSequence + "|");

		System.out.println
			("\t||------------------------------------------------------------------------------------------------------------------------------------------------------||");

		System.out.println();
	}

	private static final void LowVolatility()
		throws Exception
	{
		System.out.println ("\t||-----------------------------------------------------------------------------------||");

		System.out.println ("\t||                 2.4 LOW VOLATILITY CURRENCY SET and RISK WEIGHTS                  ||");

		System.out.println ("\t||-----------------------------------------------------------------------------------||");

		System.out.println (
			"\t|| Currency Set => " +
			IRSettingsContainer24.LowVolatilityCurrencySet()
		);

		System.out.println ("\t||-----------------------------------------------------------------------------------||");

		System.out.println
			("\t||----------------------------------------------------------------------------------------------------------------------------------------------------||");

		IRWeight jpyRiskWeight = IRSettingsContainer24.RiskWeight ("JPY");

		String tenorWeightSequence = "\t|| ";

		for (Map.Entry<String, Double> tenorWeightEntry : jpyRiskWeight.tenorDelta().entrySet())
		{
			tenorWeightSequence = tenorWeightSequence + " " + tenorWeightEntry.getKey() + " => " +
				FormatUtil.FormatDouble (tenorWeightEntry.getValue(), 1, 0, 1.) + " |";
		}

		System.out.println (tenorWeightSequence + "|");

		System.out.println
			("\t||----------------------------------------------------------------------------------------------------------------------------------------------------||");

		System.out.println();
	}

	private static final void HighVolatility()
		throws Exception
	{
		System.out.println ("\t||-----------------------------------------------------------------------------------||");

		System.out.println ("\t||                2.4 HIGH VOLATILITY CURRENCY SET and RISK WEIGHTS                  ||");

		System.out.println ("\t||-----------------------------------------------------------------------------------||");

		System.out.println (
			"\t|| Currency Set => " +
			IRSettingsContainer24.HighVolatilityCurrencySet()
		);

		System.out.println ("\t||-----------------------------------------------------------------------------------||");

		System.out.println
			("\t||-----------------------------------------------------------------------------------------------------------------------------------------------------------||");

		IRWeight inrRiskWeight = IRSettingsContainer24.RiskWeight ("INR");

		String tenorWeightSequence = "\t|| ";

		for (Map.Entry<String, Double> tenorWeightEntry : inrRiskWeight.tenorDelta().entrySet())
		{
			tenorWeightSequence = tenorWeightSequence + " " + tenorWeightEntry.getKey() + " => " +
				FormatUtil.FormatDouble (tenorWeightEntry.getValue(), 1, 0, 1.) + " |";
		}

		System.out.println (tenorWeightSequence + "|");

		System.out.println
			("\t||-----------------------------------------------------------------------------------------------------------------------------------------------------------||");

		System.out.println();
	}

	private static final void SingleCurrencyTenorCorrelation()
		throws Exception
	{
		LabelledRdCorrelation singleCurveTenorCorrelation = IRSettingsContainer24.SingleCurveTenorCorrelation();

		List<String> tenorList = singleCurveTenorCorrelation.idList();

		System.out.println
			("\t||------------------------------------------------------------------------------------------||");

		System.out.println
			("\t||                          INTEREST RATE CROSS TENOR CORRELATION                           ||");

		System.out.println
			("\t||------------------------------------------------------------------------------------------||");

		String rowDump = "\t||      ";

		for (String tenor : tenorList)
		{
			rowDump = rowDump + "  " + tenor + "  ";
		}

		System.out.println (rowDump + "||");

		System.out.println
			("\t||------------------------------------------------------------------------------------------||");

		for (String innerTenor : tenorList)
		{
			rowDump = "\t|| " + innerTenor + " ";

			for (String outerTenor : tenorList)
			{
				rowDump = rowDump + " " +
					FormatUtil.FormatDouble (
						singleCurveTenorCorrelation.entry (
							innerTenor,
							outerTenor
						),
					3, 0, 100.) + "% ";
			}

			System.out.println (rowDump + " ||");
		}

		System.out.println
			("\t||------------------------------------------------------------------------------------------||");

		System.out.println();
	}

	private static final void StaticParametersDump()
		throws Exception
	{
		System.out.println ("\t||-------------------------------------------------------------------------------||");

		System.out.println ("\t||                SYSTEMATIC FACTOR RISK WEIGHTS AND CORRELATIONS                ||");

		System.out.println ("\t||-------------------------------------------------------------------------------||");

		System.out.println (
			"\t|| Single Currency Inflation Risk Weight                               => " +
			FormatUtil.FormatDouble (
				IRSystemics24.SINGLE_CURRENCY_CURVE_INFLATION_RISK_WEIGHT, 2, 2, 1.
			) + " ||"
		);

		System.out.println (
			"\t|| Single Currency Basis Swap Spread Risk Weight                       => " +
			FormatUtil.FormatDouble (
				IRSystemics24.SINGLE_CURRENCY_CURVE_BASIS_SWAP_SPREAD_RISK_WEIGHT, 2, 2, 1.
			) + " ||"
		);

		System.out.println (
			"\t|| Single Currency Vega Risk Weight                                    => " +
			FormatUtil.FormatDouble (
				IRSystemics24.VEGA_RISK_WEIGHT, 2, 2, 1.
			) + " ||"
		);

		System.out.println (
			"\t|| Single Currency Cross Curve Correlation                             => " +
			FormatUtil.FormatDouble (
				IRSystemics24.SINGLE_CURRENCY_CROSS_CURVE_CORRELATION, 2, 2, 1.
			) + " ||"
		);

		System.out.println (
			"\t|| Single Currency Curve Inflation Correlation                         => " +
			FormatUtil.FormatDouble (
				IRSystemics24.SINGLE_CURRENCY_CURVE_INFLATION_CORRELATION, 2, 2, 1.
			) + " ||"
		);

		System.out.println (
			"\t|| Single Currency Curve Volatility Inflation Volatility Correlation   => " +
			FormatUtil.FormatDouble (
				IRSystemics24.SINGLE_CURRENCY_CURVE_VOLATILITY_INFLATION_VOLATILITY_CORRELATION, 2, 2, 1.
			) + " ||"
		);

		System.out.println (
			"\t|| Single Currency Curve Basis Swap Spread Correlation                 => " +
			FormatUtil.FormatDouble (
				IRSystemics24.SINGLE_CURRENCY_CURVE_BASIS_SWAP_SPREAD_CORRELATION, 2, 2, 1.
			) + " ||"
		);

		System.out.println (
			"\t|| Single Currency Basis Swap Spread Inflation Correlation             => " +
			FormatUtil.FormatDouble (
				IRSystemics24.SINGLE_CURRENCY_BASIS_SWAP_SPREAD_INFLATION_CORRELATION, 2, 2, 1.
			) + " ||"
		);

		System.out.println (
			"\t|| Cross Currency Correlation                                          => " +
			FormatUtil.FormatDouble (
				IRSystemics24.CROSS_CURRENCY_CORRELATION, 2, 2, 1.
			) + " ||"
		);

		System.out.println ("\t||-------------------------------------------------------------------------------||");
	}

	/**
	 * Entry Point
	 * 
	 * @param args Command Line Argument Array
	 * 
	 * @throws Exception Thrown on Error/Exception Situation
	 */

	public final static void main (
		final String[] args)
		throws Exception
	{
		EnvManager.InitEnv ("");

		RegularVolatility();

		LowVolatility();

		HighVolatility();

		SingleCurrencyTenorCorrelation();

		StaticParametersDump();

		EnvManager.TerminateEnv();
	}
}
