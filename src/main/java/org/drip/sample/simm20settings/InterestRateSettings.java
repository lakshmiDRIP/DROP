
package org.drip.sample.simm20settings;

import java.util.List;
import java.util.Map;

import org.drip.measure.stochastic.LabelCorrelation;
import org.drip.quant.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.simm20.rates.IRSettingsContainer;
import org.drip.simm20.rates.IRSystemics;
import org.drip.simm20.rates.IRWeight;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * 
 *  This file is part of DRIP, a free-software/open-source library for buy/side financial/trading model
 *  	libraries targeting analysts and developers
 *  	https://lakshmidrip.github.io/DRIP/
 *  
 *  DRIP is composed of four main libraries:
 *  
 *  - DRIP Fixed Income - https://lakshmidrip.github.io/DRIP-Fixed-Income/
 *  - DRIP Asset Allocation - https://lakshmidrip.github.io/DRIP-Asset-Allocation/
 *  - DRIP Numerical Optimizer - https://lakshmidrip.github.io/DRIP-Numerical-Optimizer/
 *  - DRIP Statistical Learning - https://lakshmidrip.github.io/DRIP-Statistical-Learning/
 * 
 *  - DRIP Fixed Income: Library for Instrument/Trading Conventions, Treasury Futures/Options,
 *  	Funding/Forward/Overnight Curves, Multi-Curve Construction/Valuation, Collateral Valuation and XVA
 *  	Metric Generation, Calibration and Hedge Attributions, Statistical Curve Construction, Bond RV
 *  	Metrics, Stochastic Evolution and Option Pricing, Interest Rate Dynamics and Option Pricing, LMM
 *  	Extensions/Calibrations/Greeks, Algorithmic Differentiation, and Asset Backed Models and Analytics.
 * 
 *  - DRIP Asset Allocation: Library for model libraries for MPT framework, Black Litterman Strategy
 *  	Incorporator, Holdings Constraint, and Transaction Costs.
 * 
 *  - DRIP Numerical Optimizer: Library for Numerical Optimization and Spline Functionality.
 * 
 *  - DRIP Statistical Learning: Library for Statistical Evaluation and Machine Learning.
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
 * InterestRateSettings demonstrates the Extraction and Display of ISDA SIMM 2.0 Single/Cross Currency
 * 	Interest Rate Tenor Risk Weights, Systemics, and Correlations. The References are:
 *  
 *  - Andersen, L. B. G., M. Pykhtin, and A. Sokol (2017): Credit Exposure in the Presence of Initial Margin,
 *  	https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2806156, eSSRN.
 *  
 *  - Albanese, C., S. Caenazzo, and O. Frankel (2017): Regression Sensitivities for Initial Margin
 *  	Calculations, https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2763488, eSSRN.
 *  
 *  - Anfuso, F., D. Aziz, P. Giltinan, and K. Loukopoulus (2017): A Sound Modeling and Back-testing
 *  	Framework for Forecasting Initial Margin Requirements,
 *  	https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2716279, eSSRN.
 *  
 *  - Caspers, P., P. Giltinan, R. Lichters, and N. Nowaczyk (2017): Forecasting Initial Margin Requirements
 *  	- A Model Evaluation https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2911167, eSSRN.
 *  
 *  - International Swaps and Derivatives Association (2017): SIMM v2.0 Methodology,
 *  	https://www.isda.org/a/oFiDE/isda-simm-v2.pdf.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class InterestRateSettings
{

	private static final void RegularVolatility()
		throws Exception
	{
		System.out.println ("\t||-----------------------------------------------------------------------------------||");

		System.out.println ("\t||                  REGULAR VOLATILITY CURRENCY SET and RISK WEIGHTS                 ||");

		System.out.println ("\t||-----------------------------------------------------------------------------------||");

		System.out.println (
			"\t|| Currency Set => " +
			IRSettingsContainer.RegularVolatilityCurrencySet() + " ||"
		);

		System.out.println ("\t||-----------------------------------------------------------------------------------||");

		System.out.println
			("\t||------------------------------------------------------------------------------------------------------------------------------------------------------||");

		IRWeight usdRiskWeight = IRSettingsContainer.RiskWeight ("USD");

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

		System.out.println ("\t||                    LOW VOLATILITY CURRENCY SET and RISK WEIGHTS                   ||");

		System.out.println ("\t||-----------------------------------------------------------------------------------||");

		System.out.println (
			"\t|| Currency Set => " +
			IRSettingsContainer.LowVolatilityCurrencySet()
		);

		System.out.println ("\t||-----------------------------------------------------------------------------------||");

		System.out.println
			("\t||----------------------------------------------------------------------------------------------------------------------------------------------------||");

		IRWeight jpyRiskWeight = IRSettingsContainer.RiskWeight ("JPY");

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

		System.out.println ("\t||                   HIGH VOLATILITY CURRENCY SET and RISK WEIGHTS                   ||");

		System.out.println ("\t||-----------------------------------------------------------------------------------||");

		System.out.println (
			"\t|| Currency Set => " +
			IRSettingsContainer.HighVolatilityCurrencySet()
		);

		System.out.println ("\t||-----------------------------------------------------------------------------------||");

		System.out.println
			("\t||-----------------------------------------------------------------------------------------------------------------------------------------------------------||");

		IRWeight inrRiskWeight = IRSettingsContainer.RiskWeight ("INR");

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
		LabelCorrelation singleCurveTenorCorrelation = IRSettingsContainer.SingleCurveTenorCorrelation();

		List<String> tenorList = singleCurveTenorCorrelation.labelList();

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
				IRSystemics.SINGLE_CURRENCY_CURVE_INFLATION_RISK_WEIGHT, 2, 2, 1.
			) + " ||"
		);

		System.out.println (
			"\t|| Single Currency Basis Swap Spread Risk Weight                       => " +
			FormatUtil.FormatDouble (
				IRSystemics.SINGLE_CURRENCY_CURVE_BASIS_SWAP_SPREAD_RISK_WEIGHT, 2, 2, 1.
			) + " ||"
		);

		System.out.println (
			"\t|| Single Currency Vega Risk Weight                                    => " +
			FormatUtil.FormatDouble (
				IRSystemics.VEGA_RISK_WEIGHT, 2, 2, 1.
			) + " ||"
		);

		System.out.println (
			"\t|| Single Currency Cross Curve Correlation                             => " +
			FormatUtil.FormatDouble (
				IRSystemics.SINGLE_CURRENCY_CROSS_CURVE_CORRELATION, 2, 2, 1.
			) + " ||"
		);

		System.out.println (
			"\t|| Single Currency Curve Inflation Correlation                         => " +
			FormatUtil.FormatDouble (
				IRSystemics.SINGLE_CURRENCY_CURVE_INFLATION_CORRELATION, 2, 2, 1.
			) + " ||"
		);

		System.out.println (
			"\t|| Single Currency Curve Volatility Inflation Volatility Correlation   => " +
			FormatUtil.FormatDouble (
				IRSystemics.SINGLE_CURRENCY_CURVE_VOLATILITY_INFLATION_VOLATILITY_CORRELATION, 2, 2, 1.
			) + " ||"
		);

		System.out.println (
			"\t|| Single Currency Curve Basis Swap Spread Correlation                 => " +
			FormatUtil.FormatDouble (
				IRSystemics.SINGLE_CURRENCY_CURVE_BASIS_SWAP_SPREAD_CORRELATION, 2, 2, 1.
			) + " ||"
		);

		System.out.println (
			"\t|| Single Currency Basis Swap Spread Inflation Correlation             => " +
			FormatUtil.FormatDouble (
				IRSystemics.SINGLE_CURRENCY_BASIS_SWAP_SPREAD_INFLATION_CORRELATION, 2, 2, 1.
			) + " ||"
		);

		System.out.println (
			"\t|| Cross Currency Correlation                                          => " +
			FormatUtil.FormatDouble (
				IRSystemics.CROSS_CURRENCY_CORRELATION, 2, 2, 1.
			) + " ||"
		);

		System.out.println ("\t||-------------------------------------------------------------------------------||");
	}

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
