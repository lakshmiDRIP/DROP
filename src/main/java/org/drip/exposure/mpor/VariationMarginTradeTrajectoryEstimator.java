
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
 * VariationMarginTradeTrajectoryEstimator computes the Variation Margin Estimate/Posting and Trade Payments
 *  for a Realized Market Path. The References are:
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

public class VariationMarginTradeTrajectoryEstimator
{
	private int[] _exposureDateArray = null;
	private java.lang.String _calendar = "";
	private org.drip.exposure.universe.MarketPath _marketPath = null;
	private org.drip.exposure.csatimeline.AndersenPykhtinSokolLag _csaTimelineLag = null;
	private org.drip.exposure.mpor.VariationMarginTradePaymentVertex _variationMarginTradePaymentVertex =
		null;

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

			if (clientTradePaymentDate > startDate && clientTradePaymentDate <= endDate)
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

	private static final double VariationMarginPosting (
		final java.util.Map<java.lang.Integer, java.lang.Double> variationMarginEstimateTrajectory,
		final int variationMarginPostingStartDate,
		final int variationMarginPostingEndDate)
	{
		double variationMarginPosting = java.lang.Double.NaN;

		for (java.util.Map.Entry<java.lang.Integer, java.lang.Double> variationMarginEstimateTrajectoryEntry
			: variationMarginEstimateTrajectory.entrySet())
		{
			int variationMarginEstimateDate = variationMarginEstimateTrajectoryEntry.getKey();

			double variationMarginEstimate = variationMarginEstimateTrajectoryEntry.getValue();

			if (variationMarginEstimateDate >= variationMarginPostingStartDate &&
				variationMarginEstimateDate <= variationMarginPostingEndDate)
			{
				if (java.lang.Double.isNaN (variationMarginPosting))
				{
					variationMarginPosting = variationMarginEstimate;
				}
				else
				{
					if (variationMarginEstimate < variationMarginPosting)
					{
						variationMarginPosting = variationMarginEstimate;
					}
				}
			}
		}

		return java.lang.Double.isNaN (variationMarginPosting) ? 0. : variationMarginPosting;
	}

	/**
	 * VariationMarginTradeTrajectoryEstimator Constructor
	 * 
	 * @param exposureDateArray Array of Exposure Dates
	 * @param calendar The Date Adjustment Calendar
	 * @param variationMarginTradePaymentVertex The Variation Margin Trade Payment Trajectory Generator
	 * @param marketPath The Market Path
	 * @param csaTimelineLag The CSA Time-line Lag Parameters
	 * 
	 * @throws java.lang.Exception Throws if the Inputs are Invalid
	 */

	public VariationMarginTradeTrajectoryEstimator (
		final int[] exposureDateArray,
		final java.lang.String calendar,
		final org.drip.exposure.mpor.VariationMarginTradePaymentVertex variationMarginTradePaymentVertex,
		final org.drip.exposure.universe.MarketPath marketPath,
		final org.drip.exposure.csatimeline.AndersenPykhtinSokolLag csaTimelineLag)
		throws java.lang.Exception
	{
		if (null == (_exposureDateArray = exposureDateArray) || 0 == _exposureDateArray.length ||
			null == (_calendar = calendar) || _calendar.isEmpty() ||
			null == (_variationMarginTradePaymentVertex = variationMarginTradePaymentVertex) ||
			null == (_marketPath = marketPath) ||
			null == (_csaTimelineLag = csaTimelineLag))
		{
			throw new java.lang.Exception
				("VariationMarginTradeTrajectoryEstimator Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Array of Exposure Dates
	 * 
	 * @return The Array of Exposure Dates
	 */

	public int[] exposureDateArray()
	{
		return _exposureDateArray;
	}

	/**
	 * Retrieve the Date Adjustment Calendar
	 * 
	 * @return The Date Adjustment Calendar
	 */

	public java.lang.String calendar()
	{
		return _calendar;
	}

	/**
	 * Retrieve the Variation Margin Trade Payment Vertex Generator
	 * 
	 * @return The Variation Margin Trade Payment Vertex Generator
	 */

	public org.drip.exposure.mpor.VariationMarginTradePaymentVertex vertexGenerator()
	{
		return _variationMarginTradePaymentVertex;
	}

	/**
	 * Retrieve the Market Path
	 * 
	 * @return The Market Path
	 */

	public org.drip.exposure.universe.MarketPath marketPath()
	{
		return _marketPath;
	}

	/**
	 * Retrieve the CSA Events Timeline Lag
	 * 
	 * @return The CSA Events Timeline Lag
	 */

	final org.drip.exposure.csatimeline.AndersenPykhtinSokolLag csaTimelineLag()
	{
		return _csaTimelineLag;
	}

	/**
	 * Generate the Variation Margin Estimate Trajectory
	 * 
	 * @return The Variation Margin Estimate Trajectory
	 */

	public java.util.Map<java.lang.Integer, java.lang.Double> variationMarginEstimateTrajectory()
	{
		return org.drip.exposure.mpor.VariationMarginTrajectoryBuilder.DailyDense (
			_exposureDateArray,
			_variationMarginTradePaymentVertex,
			_marketPath
		);
	}

	/**
	 * Generate the Client and the Dealer Trade Payment Trajectories
	 * 
	 * @param clientTradePaymentTrajectory The Client Trade Payment Trajectory
	 * @param dealerTradePaymentTrajectory The Dealer Trade Payment Trajectory
	 * 
	 * @return TRUE - The Client and the Dealer Trade Payment Trajectories successfully generated
	 */

	public boolean tradePaymentTrajectory (
		final java.util.Map<java.lang.Integer, java.lang.Double> clientTradePaymentTrajectory,
		final java.util.Map<java.lang.Integer, java.lang.Double> dealerTradePaymentTrajectory)
	{
		if (null == clientTradePaymentTrajectory || null == dealerTradePaymentTrajectory)
		{
			return false;
		}

		int exposureDateCount = _exposureDateArray.length;

		for (int exposureDateIndex = 0; exposureDateIndex < exposureDateCount; ++exposureDateIndex)
		{
			org.drip.exposure.mpor.TradePayment tradePayment =
				_variationMarginTradePaymentVertex.tradePayment (
					_exposureDateArray[exposureDateIndex],
					_marketPath
				);

			if (null == tradePayment)
			{
				return false;
			}

			clientTradePaymentTrajectory.put (
				_exposureDateArray[exposureDateIndex],
				tradePayment.client()
			);

			dealerTradePaymentTrajectory.put (
				_exposureDateArray[exposureDateIndex],
				tradePayment.dealer()
			);
		}

		return true;
	}

	/**
	 * Generate the Array of CSA Event Dates
	 * 
	 * @return Array of CSA Event Dates
	 */

	public org.drip.exposure.csatimeline.LastFlowDates[] csaEventDates()
	{
		int exposureDateCount = _exposureDateArray.length;
		org.drip.exposure.csatimeline.LastFlowDates[] csaEventDateArray = new
			org.drip.exposure.csatimeline.LastFlowDates[exposureDateCount];

		for (int exposureDateIndex = 0; exposureDateIndex < exposureDateCount; ++exposureDateIndex)
		{
			if (null == (csaEventDateArray[exposureDateIndex] =
				org.drip.exposure.csatimeline.LastFlowDates.SpotStandard (
					new org.drip.analytics.date.JulianDate (_exposureDateArray[exposureDateIndex]),
					_csaTimelineLag,
					_calendar
				)))
			{
				return null;
			}
		}

		return csaEventDateArray;
	}

	/**
	 * Retrieve the Variation Margin Trade Payment Exposure Trajectory
	 * 
	 * @return The Variation Margin Trade Payment Exposure Trajectory
	 */

	public java.util.Map<java.lang.Integer, org.drip.exposure.mpor.VariationMarginTradeVertexExposure>
		trajectory()
	{
		java.util.Map<java.lang.Integer, java.lang.Double> variationMarginEstimateTrajectory =
			variationMarginEstimateTrajectory();

		java.util.Map<java.lang.Integer, java.lang.Double> clientTradePaymentTrajectory = new
			java.util.TreeMap<java.lang.Integer, java.lang.Double>();

		java.util.Map<java.lang.Integer, java.lang.Double> dealerTradePaymentTrajectory = new
			java.util.TreeMap<java.lang.Integer, java.lang.Double>();

		if (!tradePaymentTrajectory (
			clientTradePaymentTrajectory,
			dealerTradePaymentTrajectory
		))
		{
			return null;
		}

		java.util.Map<java.lang.Integer, org.drip.exposure.mpor.VariationMarginTradeVertexExposure>
			variationMarginTradeExposureTrajectory = new java.util.TreeMap<java.lang.Integer,
				org.drip.exposure.mpor.VariationMarginTradeVertexExposure>();

		org.drip.exposure.csatimeline.LastFlowDates[] csaEventDateArray = csaEventDates();

		int exposureDateCount = _exposureDateArray.length;

		try
		{
			for (int exposureDateIndex = 0; exposureDateIndex < exposureDateCount; ++exposureDateIndex)
			{
				variationMarginTradeExposureTrajectory.put (
					_exposureDateArray[exposureDateIndex],
					new org.drip.exposure.mpor.VariationMarginTradeVertexExposure (
						variationMarginEstimateTrajectory.get (_exposureDateArray[exposureDateIndex]),
						VariationMarginPosting (
							variationMarginEstimateTrajectory,
							csaEventDateArray[exposureDateIndex].clientVariationMarginPosting().julian(),
							csaEventDateArray[exposureDateIndex].dealerVariationMarginPosting().julian()
						),
						ClientTradePayment (
							clientTradePaymentTrajectory,
							csaEventDateArray[exposureDateIndex].clientTradePayment().julian(),
							csaEventDateArray[exposureDateIndex].dealerTradePayment().julian()
						),
						NetTradePayment (
							clientTradePaymentTrajectory,
							dealerTradePaymentTrajectory,
							csaEventDateArray[exposureDateIndex].dealerTradePayment().julian(),
							csaEventDateArray[exposureDateIndex].variationMarginPeriodEnd().julian()
						),
						csaEventDateArray[exposureDateIndex]
					)
				);
			}

			return variationMarginTradeExposureTrajectory;
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Retrieve the Variation Margin Trade Payment Exposure Trajectory
	 * 
	 * @return The Variation Margin Trade Payment Exposure Trajectory
	 */

	/* public java.util.Map<java.lang.Integer, org.drip.exposure.mpor.VariationMarginTradeVertexExposure>
		variationMarginTradeExposureTrajectory()
	{
		return _variationMarginTradeExposureTrajectory;
	} */
}
