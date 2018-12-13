
package org.drip.exposure.mpor;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting risk, transaction costs, exposure, margin
 *  	calculations, valuation adjustment, and portfolio construction within and across fixed income,
 *  	credit, commodity, equity, FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three modules:
 *  
 *  - DROP Analytics Core - https://lakshmidrip.github.io/DROP-Analytics-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Numerical Core - https://lakshmidrip.github.io/DROP-Numerical-Core/
 * 
 * 	DROP Analytics Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Asset Backed Analytics
 * 	- XVA Analytics
 * 	- Exposure and Margin Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Numerical Core implements libraries for the following:
 * 	- Statistical Learning
 * 	- Numerical Optimizer
 * 	- Spline Builder
 * 	- Algorithm Support
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
 * <i>PathVariationMarginTrajectoryEstimator</i> computes the Variation Margin Estimate/Posting from the
 * specified Dense Uncollateralized Exposures and Trade Payments along the specified Path Trajectory. The
 * References are:
 *  
 * <br><br>
 *  	<ul>
 *  		<li>
 *  			Burgard, C., and M. Kjaer (2013): Funding Costs, Funding Strategies <i>Risk</i> <b>23
 *  				(12)</b> 82-87
 *  		</li>
 *  		<li>
 *  			Burgard, C., and M. Kjaer (2014): In the Balance <i>Risk</i> <b>24 (11)</b> 72-75
 *  		</li>
 *  		<li>
 *  			Burgard, C., and M. Kjaer (2014): PDE Representations of Derivatives with Bilateral Counter-
 *  				party Risk and Funding Costs <i>Journal of Credit Risk</i> <b>7 (3)</b> 1-19
 *  		</li>
 *  		<li>
 *  			Burgard, C., and M. Kjaer (2017): Derivatives Funding, Netting, and Accounting
 *  				https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2534011 <b>eSSRN</b>
 *  		</li>
 *  		<li>
 *  			Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives
 *  				Pricing <i>Risk</i> <b>21 (2)</b> 97-102
 *  		</li>
 *  		<li>
 *  	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ExposureAnalyticsLibrary.md">Exposure Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/README.md">Exposure</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/mpor/README.md">MPoR</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class PathVariationMarginTrajectoryEstimator
{
	private int[] _exposureDateArray = null;
	private java.lang.String _calendar = "";
	private org.drip.exposure.mpor.TradePayment[] _tradePaymentTrajectory = null;
	private org.drip.exposure.csatimeline.AndersenPykhtinSokolLag _csaTimelineLag = null;
	private java.util.Map<java.lang.Integer, java.lang.Double> _variationMarginEstimateTrajectory = null;

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
	 * Generate a Standard Instance of PathVariationMarginTrajectoryEstimator
	 * 
	 * @param exposureDateArray Array of Exposure Dates
	 * @param calendar The Date Adjustment Calendar
	 * @param variationMarginTradePaymentVertex The Variation Margin Trade Payment Trajectory Generator
	 * @param marketPath The Market Path
	 * @param csaTimelineLag The CSA Time-line Lag Parameters
	 * 
	 * @return The Standard Instance of PathVariationMarginTrajectoryEstimator
	 */

	public static final PathVariationMarginTrajectoryEstimator Standard (
		final int[] exposureDateArray,
		final java.lang.String calendar,
		final org.drip.exposure.mpor.VariationMarginTradePaymentVertex variationMarginTradePaymentVertex,
		final org.drip.exposure.universe.MarketPath marketPath,
		final org.drip.exposure.csatimeline.AndersenPykhtinSokolLag csaTimelineLag)
	{
		if (null == exposureDateArray)
		{
			return null;
		}

		int exposureDateCount = exposureDateArray.length;
		org.drip.exposure.mpor.TradePayment[] tradePaymentTrajectory = 0 == exposureDateCount ? null : new
			org.drip.exposure.mpor.TradePayment[exposureDateCount];

		if (0 == exposureDateCount)
		{
			return null;
		}

		for (int exposureDateIndex = 0; exposureDateIndex < exposureDateCount; ++exposureDateIndex)
		{
			tradePaymentTrajectory[exposureDateIndex] = variationMarginTradePaymentVertex.tradePayment (
				exposureDateArray[exposureDateIndex],
				marketPath
			);
		}

		try
		{
			return new PathVariationMarginTrajectoryEstimator (
				exposureDateArray,
				calendar,
				org.drip.exposure.mpor.VariationMarginTrajectoryBuilder.Grid (
					exposureDateArray,
					variationMarginTradePaymentVertex,
					marketPath
				),
				tradePaymentTrajectory,
				csaTimelineLag
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * PathVariationMarginTrajectoryEstimator Constructor
	 * 
	 * @param exposureDateArray Array of Exposure Dates
	 * @param calendar The Date Adjustment Calendar
	 * @param variationMarginEstimateTrajectory The Variation Margin Estimate Trajectory
	 * @param tradePaymentTrajectory The Trade Payment Trajectory
	 * @param csaTimelineLag The CSA Time-line Lag Parameters
	 * 
	 * @throws java.lang.Exception Throws if the Inputs are Invalid
	 */

	public PathVariationMarginTrajectoryEstimator (
		final int[] exposureDateArray,
		final java.lang.String calendar,
		final java.util.Map<java.lang.Integer, java.lang.Double> variationMarginEstimateTrajectory,
		final org.drip.exposure.mpor.TradePayment[] tradePaymentTrajectory,
		final org.drip.exposure.csatimeline.AndersenPykhtinSokolLag csaTimelineLag)
		throws java.lang.Exception
	{
		if (null == (_exposureDateArray = exposureDateArray) ||
			null == (_calendar = calendar) || _calendar.isEmpty() ||
			null == (_variationMarginEstimateTrajectory = variationMarginEstimateTrajectory) ||
			null == (_tradePaymentTrajectory = tradePaymentTrajectory) ||
			null == (_csaTimelineLag = csaTimelineLag))
		{
			throw new java.lang.Exception
				("PathVariationMarginTrajectoryEstimator Constructor => Invalid Inputs");
		}

		int exposureDateCount = _exposureDateArray.length;

		if (0 == exposureDateCount || exposureDateCount != _tradePaymentTrajectory.length)
		{
			throw new java.lang.Exception
				("PathVariationMarginTrajectoryEstimator Constructor => Invalid Inputs");
		}

		for (int exposureDateIndex = 0; exposureDateIndex < exposureDateCount; ++exposureDateIndex)
		{
			if (null == _tradePaymentTrajectory[exposureDateIndex])
			{
				throw new java.lang.Exception
					("PathVariationMarginTrajectoryEstimator Constructor => Invalid Inputs");
			}
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
	 * Retrieve the CSA Events Timeline Lag
	 * 
	 * @return The CSA Events Timeline Lag
	 */

	final org.drip.exposure.csatimeline.AndersenPykhtinSokolLag csaTimelineLag()
	{
		return _csaTimelineLag;
	}

	/**
	 * Retrieve the Variation Margin Estimate Trajectory
	 * 
	 * @return The Variation Margin Estimate Trajectory
	 */

	public java.util.Map<java.lang.Integer, java.lang.Double> variationMarginEstimateTrajectory()
	{
		return _variationMarginEstimateTrajectory;
	}

	/**
	 * Retrieve the Trade Payment Trajectory
	 * 
	 * @return The Trade Payment Trajectory
	 */

	public org.drip.exposure.mpor.TradePayment[] tradePaymentTrajectory()
	{
		return _tradePaymentTrajectory;
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
			org.drip.exposure.mpor.TradePayment tradePayment = _tradePaymentTrajectory[exposureDateIndex];

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
}
