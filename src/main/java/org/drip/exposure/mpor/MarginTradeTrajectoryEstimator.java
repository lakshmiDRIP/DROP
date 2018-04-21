
package org.drip.exposure.mpor;

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
 * MarginTradeTrajectoryEstimator computes the Margin Estimate/Posting and Trade Payments for a Realized
 *  Market Path. The References are:
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
 *  - Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives Pricing, Risk
 *  	21 (2) 97-102.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class MarginTradeTrajectoryEstimator
{
	private java.util.Map<java.lang.Integer, org.drip.exposure.mpor.MarginTradeVertexExposure>
		_marginTradeExposureTrajectory = null;

	private static final double ClientTradePayment (
		final java.util.Map<java.lang.Integer, java.lang.Double> clientTradePaymentTrajectory,
		final int startDate,
		final int endDate)
	{
		double clientTradePayment = 0.;

		for (java.util.Map.Entry<java.lang.Integer, java.lang.Double> clientTradePaymentEntry :
			clientTradePaymentTrajectory.entrySet())
		{
			int clientTradePaymentDate = clientTradePaymentEntry.getKey();

			if (clientTradePaymentDate > startDate && clientTradePaymentDate <= endDate)
			{
				clientTradePayment += clientTradePaymentEntry.getValue();
			}
		}

		return clientTradePayment;
	}

	private static final double NetTradePayment (
		final java.util.Map<java.lang.Integer, java.lang.Double> clientTradePaymentTrajectory,
		final java.util.Map<java.lang.Integer, java.lang.Double> dealerTradePaymentTrajectory,
		final int startDate,
		final int endDate)
	{
		double netTradePayment = 0.;

		for (java.util.Map.Entry<java.lang.Integer, java.lang.Double> clientTradePaymentEntry :
			clientTradePaymentTrajectory.entrySet())
		{
			int clientTradePaymentDate = clientTradePaymentEntry.getKey();

			if (clientTradePaymentDate >= startDate && clientTradePaymentDate <= endDate)
			{
				netTradePayment += clientTradePaymentEntry.getValue();
			}
		}

		for (java.util.Map.Entry<java.lang.Integer, java.lang.Double> dealerTradePaymentEntry :
			dealerTradePaymentTrajectory.entrySet())
		{
			int dealerTradePaymentDate = dealerTradePaymentEntry.getKey();

			if (dealerTradePaymentDate > startDate && dealerTradePaymentDate <= endDate)
			{
				netTradePayment += dealerTradePaymentEntry.getValue();
			}
		}

		return netTradePayment;
	}

	private static final double PeriodVariationMarginGap (
		final java.util.Map<java.lang.Integer, java.lang.Double> marginFlowMap,
		final int gapStartDate,
		final int gapEndDate)
	{
		double periodVariationMarginFlowGap = java.lang.Double.NaN;

		for (java.util.Map.Entry<java.lang.Integer, java.lang.Double> marginFlowMapEntry :
			marginFlowMap.entrySet())
		{
			int variationMarginFlowDate = marginFlowMapEntry.getKey();

			double periodVariationMarginFlow = marginFlowMapEntry.getValue();

			if (variationMarginFlowDate >= gapStartDate && variationMarginFlowDate <= gapEndDate)
			{
				if (java.lang.Double.isNaN (periodVariationMarginFlowGap))
				{
					periodVariationMarginFlowGap = periodVariationMarginFlow;
				}
				else
				{
					if (periodVariationMarginFlow < periodVariationMarginFlowGap)
					{
						periodVariationMarginFlowGap = periodVariationMarginFlow;
					}
				}
			}
		}

		return java.lang.Double.isNaN (periodVariationMarginFlowGap) ? 0. : periodVariationMarginFlowGap;
	}

	/**
	 * Construct a Standard Instance of MarginTradeTrajectoryEstimator
	 * 
	 * @param exposureDateArray The Forward Exposure Date Array
	 * @param calendar The Date Adjustment Calendar
	 * @param marginTradeVertex The Uncollateralized Margin Trade Vertex
	 * @param marketPath The Market Path
	 * @param csaTimelineLag The Andersen Pykhtin Sokol CSA Timeline Lag Parameterization
	 * 
	 * @return Standard Instance of MarginTradeTrajectoryEstimator
	 */

	public static final MarginTradeTrajectoryEstimator Standard (
		final int[] exposureDateArray,
		final java.lang.String calendar,
		final org.drip.exposure.mpor.MarginTradeVertex marginTradeVertex,
		final org.drip.exposure.universe.MarketPath marketPath,
		final org.drip.exposure.csatimeline.AndersenPykhtinSokolLag csaTimelineLag)
	{
		if (null == exposureDateArray ||
			null == marginTradeVertex ||
			null == marketPath ||
			null == csaTimelineLag)
		{
			return null;
		}

		java.util.Map<java.lang.Integer, java.lang.Double> clientTradePaymentTrajectory = new
			java.util.TreeMap<java.lang.Integer, java.lang.Double>();

		java.util.Map<java.lang.Integer, java.lang.Double> dealerTradePaymentTrajectory = new
			java.util.TreeMap<java.lang.Integer, java.lang.Double>();

		java.util.Map<java.lang.Integer, java.lang.Double> variationMarginPostingTrajectory = new
			java.util.TreeMap<java.lang.Integer, java.lang.Double>();

		java.util.Map<java.lang.Integer, org.drip.exposure.mpor.MarginTradeVertexExposure>
			marginTradeExposureTrajectory = new java.util.TreeMap<java.lang.Integer,
				org.drip.exposure.mpor.MarginTradeVertexExposure>();

		int exposureDateCount = exposureDateArray.length;
		double[] variationMarginEstimateArray = 0 == exposureDateCount? null : new double[exposureDateCount];

		if (0 == exposureDateCount)
		{
			return null;
		}

		try
		{
			for (int exposureDateIndex = 0; exposureDateIndex < exposureDateCount; ++exposureDateIndex)
			{
				variationMarginEstimateArray[exposureDateIndex] = marginTradeVertex.variationMarginEstimate (
					exposureDateArray[exposureDateIndex],
					marketPath
				);

				org.drip.exposure.mpor.TradePayment tradePayment = marginTradeVertex.tradePayment (
					exposureDateArray[exposureDateIndex],
					marketPath
				);

				if (null == tradePayment)
				{
					return null;
				}

				dealerTradePaymentTrajectory.put (
					exposureDateArray[exposureDateIndex],
					tradePayment.dealer()
				);

				clientTradePaymentTrajectory.put (
					exposureDateArray[exposureDateIndex],
					tradePayment.client()
				);

				variationMarginPostingTrajectory.put (
					exposureDateArray[exposureDateIndex],
					variationMarginEstimateArray[exposureDateIndex]
				);
			}

			for (int exposureDateIndex = 0; exposureDateIndex < exposureDateCount; ++exposureDateIndex)
			{
				org.drip.exposure.csatimeline.LastFlowDates csaEventDates =
					org.drip.exposure.csatimeline.LastFlowDates.SpotStandard (
						new org.drip.analytics.date.JulianDate (exposureDateArray[exposureDateIndex]),
						csaTimelineLag,
						calendar
					);

				if (null == csaEventDates)
				{
					return null;
				}

				marginTradeExposureTrajectory.put (
					exposureDateArray[exposureDateIndex],
					new org.drip.exposure.mpor.MarginTradeVertexExposure (
						variationMarginEstimateArray[exposureDateIndex],
						PeriodVariationMarginGap (
							variationMarginPostingTrajectory,
							csaEventDates.clientMargin().julian(),
							csaEventDates.dealerMargin().julian()
						),
						ClientTradePayment (
							clientTradePaymentTrajectory,
							csaEventDates.clientTrade().julian(),
							csaEventDates.dealerTrade().julian()
						),
						NetTradePayment (
							clientTradePaymentTrajectory,
							dealerTradePaymentTrajectory,
							csaEventDates.dealerTrade().julian(),
							csaEventDates.marginPeriodEnd().julian()
						),
						csaEventDates
					)
				);
			}

			return new MarginTradeTrajectoryEstimator (marginTradeExposureTrajectory);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * MarginTradeTrajectoryEstimator Constructor
	 * 
	 * @param marginTradeExposureTrajectory Margin Trade Exposure Trajectory
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public MarginTradeTrajectoryEstimator (
		final java.util.Map<java.lang.Integer, org.drip.exposure.mpor.MarginTradeVertexExposure>
			marginTradeExposureTrajectory)
		throws java.lang.Exception
	{
		if (null == (_marginTradeExposureTrajectory = marginTradeExposureTrajectory) ||
			0 == _marginTradeExposureTrajectory.size())
		{
			throw new java.lang.Exception ("MarginTradeTrajectoryEstimator Constuctor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Margin Trade Exposure Trajectory
	 * 
	 * @return The Margin Trade Exposure Trajectory
	 */

	public java.util.Map<java.lang.Integer, org.drip.exposure.mpor.MarginTradeVertexExposure>
		marginTradeExposureTrajectory()
	{
		return _marginTradeExposureTrajectory;
	}
}
