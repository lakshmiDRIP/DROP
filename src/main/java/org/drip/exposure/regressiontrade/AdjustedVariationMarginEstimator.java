
package org.drip.exposure.regressiontrade;

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
 * AdjustedVariationMarginEstimator coordinates the Generation of the Path-specific Trade Payment Adjusted
 *  Variation Margin Flows. The References are:
 *  
 *  - Andersen, L. B. G., M. Pykhtin, and A. Sokol (2017): Re-thinking Margin Period of Risk,
 *  	https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2902737, eSSRN.
 *  
 *  - Andersen, L. B. G., M. Pykhtin, and A. Sokol (2017): Credit Exposure in the Presence of Initial Margin,
 *  	https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2806156, eSSRN.
 *  
 *  - Albanese, C., and L. Andersen (2014): Accounting for OTC Derivatives: Funding Adjustments and the
 *  	Re-Hypothecation Option, eSSRN, https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2482955.
 *  
 *  - Burgard, C., and M. Kjaer (2017): Derivatives Funding, Netting, and Accounting, eSSRN,
 *  	https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2534011.
 * 
 *  - Pykhtin, M. (2009): Modeling Counter-party Credit Exposure in the Presence of Margin Agreements,
 *  	http://www.risk-europe.com/protected/michael-pykhtin.pdf.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class AdjustedVariationMarginEstimator
{
	private org.drip.exposure.universe.MarketPath _marketPath = null;
	private org.drip.exposure.mpor.VariationMarginTradePaymentVertex _marginTradePaymentGenerator = null;

	private static final double CumulativeTradePayment (
		final org.drip.exposure.mpor.TradePayment[] denseTradePaymentArray,
		final int startIndex,
		final int endIndex)
	{
		double cumulativeTradePayment = 0.;

		for (int index = startIndex + 1; index <= endIndex; ++index)
		{
			cumulativeTradePayment += (denseTradePaymentArray[index].dealer() +
				denseTradePaymentArray[index].client());
		}

		return cumulativeTradePayment;
	}

	/**
	 * AdjustedVariationMarginEstimator Constructor
	 * 
	 * @param marginTradePaymentGenerator The Path-wise Variation Margin/Trade Payment Generator
	 * @param marketPath The Market Path
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public AdjustedVariationMarginEstimator (
		final org.drip.exposure.mpor.VariationMarginTradePaymentVertex marginTradePaymentGenerator,
		final org.drip.exposure.universe.MarketPath marketPath)
		throws java.lang.Exception
	{
		if (null == (_marginTradePaymentGenerator = marginTradePaymentGenerator) ||
			null == (_marketPath = marketPath))
		{
			throw new java.lang.Exception ("AdjustedVariationMarginEstimator Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Path-wise Variation Margin/Trade Payment Generator
	 * 
	 * @return The Path-wise Variation Margin/Trade Payment Generator
	 */

	public org.drip.exposure.mpor.VariationMarginTradePaymentVertex marginTradePaymentGenerator()
	{
		return _marginTradePaymentGenerator;
	}

	/**
	 * Retrieve the Path-wise Market Path
	 * 
	 * @return The Path-wise Market Path
	 */

	public org.drip.exposure.universe.MarketPath marketPath()
	{
		return _marketPath;
	}

	/**
	 * Generate the Path-wise Variation Margin Estimate on the Exposure Dates
	 * 
	 * @param exposureDateArray The Path-wise Exposure Dates
	 * 
	 * @return The Path-wise Variation Margin Estimate on the Exposure Dates
	 */

	public double[] variationMarginEstimate (
		final int[] exposureDateArray)
	{
		if (null == exposureDateArray)
		{
			return null;
		}

		int exposureDateCount = exposureDateArray.length;
		double[] variationMarginEstimateArray = 0 == exposureDateCount ? null : new double[exposureDateCount];

		if (0 == exposureDateCount)
		{
			return null;
		}

		for (int exposureDateIndex = 0; exposureDateIndex < exposureDateCount; ++exposureDateIndex)
		{
			try
			{
				variationMarginEstimateArray[exposureDateIndex] =
					_marginTradePaymentGenerator.variationMarginEstimate (
						exposureDateArray[exposureDateIndex],
						_marketPath
					);
			}
			catch (java.lang.Exception e)
			{
				e.printStackTrace();

				return null;
			}
		}

		return variationMarginEstimateArray;
	}

	/**
	 * Retrieve the Dense Trade Payment Array across the Exposure Date Range
	 * 
	 * @param startDate The Exposure Range Start Date
	 * @param endDate The Exposure Range End Date
	 * 
	 * @return The Dense Trade Payment Array
	 */

	public org.drip.exposure.mpor.TradePayment[] denseTradePayment (
		final int startDate,
		final int endDate)
	{
		return _marginTradePaymentGenerator.denseTradePaymentArray (
			startDate,
			endDate,
			_marketPath
		);
	}

	/**
	 * Generate the Path-wise Andersen Pykhtin Sokol (2017) Variation Margin Estimates on the Exposure Dates
	 * 
	 * @param exposureDateArray The Path-wise Exposure Dates
	 * 
	 * @return The Path-wise Andersen Pykhtin Sokol (2017) Variation Margin Estimates on the Exposure Dates
	 */

	public org.drip.exposure.regressiontrade.AndersenPykhtinSokolPath andersenPykhtinSokolPath (
		final int[] exposureDateArray)
	{
		double[] variationMarginEstimateArray = variationMarginEstimate (exposureDateArray);

		if (null == variationMarginEstimateArray)
		{
			return null;
		}

		int exposureDateCount = variationMarginEstimateArray.length;
		org.drip.exposure.regressiontrade.AndersenPykhtinSokolPath andersenPykhtinSokolPath = null;

		org.drip.exposure.mpor.TradePayment[] denseTradePaymentArray = denseTradePayment (
			exposureDateArray[0],
			exposureDateArray[exposureDateArray.length - 1]
		);

		try
		{
			andersenPykhtinSokolPath = new org.drip.exposure.regressiontrade.AndersenPykhtinSokolPath
				(denseTradePaymentArray);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();

			return null;
		}

		for (int exposureDateIndex = 0; exposureDateIndex < exposureDateCount; ++exposureDateIndex)
		{
			double periodCumulativeTradePayment = 0 == exposureDateIndex ? 0. : CumulativeTradePayment (
				denseTradePaymentArray,
				exposureDateArray[exposureDateIndex - 1] - exposureDateArray[0],
				exposureDateArray[exposureDateIndex] - exposureDateArray[0]
			);

			if (!andersenPykhtinSokolPath.addVariationMarginEstimateVertex (
				exposureDateArray[exposureDateIndex],
				variationMarginEstimateArray[exposureDateIndex],
				variationMarginEstimateArray[exposureDateIndex] - periodCumulativeTradePayment
			))
			{
				return null;
			}
		}

		return andersenPykhtinSokolPath;
	}

	/**
	 * Generate the Path-wise Andersen Pykhtin Sokol (2017) Adjusted Variation Margin Estimates
	 * 
	 * @param exposureDateArray The Path-wise Exposure Dates
	 * 
	 * @return The Path-wise Andersen Pykhtin Sokol (2017) Adjusted Variation Margin Estimates
	 */

	public org.drip.exposure.regressiontrade.AdjustedVariationMarginEstimate adjustedVariationMarginEstimate (
		final int[] exposureDateArray)
	{
		double[] variationMarginEstimateArray = variationMarginEstimate (exposureDateArray);

		if (null == variationMarginEstimateArray)
		{
			return null;
		}

		int exposureDateCount = variationMarginEstimateArray.length;
		double[] adjustedVariationMarginEstimateArray = new double[exposureDateCount];

		org.drip.exposure.mpor.TradePayment[] denseTradePaymentArray = denseTradePayment (
			exposureDateArray[0],
			exposureDateArray[exposureDateArray.length - 1]
		);

		if (null == denseTradePaymentArray)
		{
			return null;
		}

		for (int exposureDateIndex = 0; exposureDateIndex < exposureDateCount; ++exposureDateIndex)
		{
			adjustedVariationMarginEstimateArray[exposureDateIndex] =
				variationMarginEstimateArray[exposureDateIndex] - (0 == exposureDateIndex ? 0. :
					CumulativeTradePayment (
						denseTradePaymentArray,
						exposureDateArray[exposureDateIndex - 1] - exposureDateArray[0],
						exposureDateArray[exposureDateIndex] - exposureDateArray[0]
					)
				);
		}

		try
		{
			return new org.drip.exposure.regressiontrade.AdjustedVariationMarginEstimate (
				adjustedVariationMarginEstimateArray,
				denseTradePaymentArray
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
