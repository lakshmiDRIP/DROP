
package org.drip.capital.shell;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Quantitative Risk Analytics
 */

/**
 * <i>SystemicScenarioPnLSeries</i> contains the PnL Series of a Systemic Stress Scenario. The References
 *	are:
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

public class SystemicScenarioPnLSeries
{
	private org.drip.capital.stress.PnLSeries _lostDecade = null;
	private org.drip.capital.stress.PnLSeries _baseline1974 = null;
	private org.drip.capital.stress.PnLSeries _baseline2008 = null;
	private org.drip.capital.stress.PnLSeries _deepDownturn = null;
	private org.drip.capital.stress.PnLSeries _dollarDecline = null;
	private org.drip.capital.stress.PnLSeries _interestRateShock = null;

	/**
	 * Construct the SystemicScenarioPnLSeries with Zeros
	 * 
	 * @return The SystemicScenarioPnLSeries with Zeros
	 */

	public static final SystemicScenarioPnLSeries ZERO()
	{
		try
		{
			return new SystemicScenarioPnLSeries (
				org.drip.capital.stress.PnLSeries.SingleZeroOutcome(),
				org.drip.capital.stress.PnLSeries.SingleZeroOutcome(),
				org.drip.capital.stress.PnLSeries.SingleZeroOutcome(),
				org.drip.capital.stress.PnLSeries.SingleZeroOutcome(),
				org.drip.capital.stress.PnLSeries.SingleZeroOutcome(),
				org.drip.capital.stress.PnLSeries.SingleZeroOutcome()
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the SystemicScenarioPnLSeries with Single Outcome
	 * 
	 * @param baseline1974PnL 1974 Baseline PnL
	 * @param baseline2008PnL 2008 Baseline PnL
	 * @param deepDownturnPnL Deep Down-turn PnL
	 * @param dollarDeclinePnL Dollar Decline PnL
	 * @param interestRateShockPnL Interest Rate Shock PnL
	 * @param lostDecadePnL Lost Decade PnL
	 * 
	 * @return The SystemicScenarioPnLSeries with Single Outcome
	 */

	public static final SystemicScenarioPnLSeries SingleOutcome (
		final double baseline1974PnL,
		final double baseline2008PnL,
		final double deepDownturnPnL,
		final double dollarDeclinePnL,
		final double interestRateShockPnL,
		final double lostDecadePnL)
	{
		try
		{
			return new SystemicScenarioPnLSeries (
				org.drip.capital.stress.PnLSeries.SingleOutcome (
					baseline1974PnL
				),
				org.drip.capital.stress.PnLSeries.SingleOutcome (
					baseline2008PnL
				),
				org.drip.capital.stress.PnLSeries.SingleOutcome (
					deepDownturnPnL
				),
				org.drip.capital.stress.PnLSeries.SingleOutcome (
					dollarDeclinePnL
				),
				org.drip.capital.stress.PnLSeries.SingleOutcome (
					interestRateShockPnL
				),
				org.drip.capital.stress.PnLSeries.SingleOutcome (
					lostDecadePnL
				)
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Aggregate the Array of SystemicScenarioPnLSeries onto a Composite SystemicScenarioPnLSeries
	 * 
	 * @param systemicScenarioPnLSeriesArray Array of SystemicScenarioPnLSeries
	 * 
	 * @return The Aggregated, Composite SystemicScenarioPnLSeries
	 */

	public static final SystemicScenarioPnLSeries AggregateComposite (
		final SystemicScenarioPnLSeries[] systemicScenarioPnLSeriesArray)
	{
		if (null == systemicScenarioPnLSeriesArray)
		{
			return null;
		}

		double lostDecadePnLComposite = 0.;
		double baseline1974PnLComposite = 0.;
		double baseline2008PnLComposite = 0.;
		double deepDownturnPnLComposite = 0.;
		double dollarDeclinePnLComposite = 0.;
		double interestRateShockPnLComposite = 0.;
		int pnlCount = systemicScenarioPnLSeriesArray.length;

		if (0 == pnlCount)
		{
			return null;
		}

		for (int pnlIndex = 0;
			pnlIndex < pnlCount;
			++pnlIndex)
		{
			if (null != systemicScenarioPnLSeriesArray[pnlIndex])
			{
				lostDecadePnLComposite = lostDecadePnLComposite +
					systemicScenarioPnLSeriesArray[pnlIndex].lostDecade().composite();

				baseline1974PnLComposite = baseline1974PnLComposite +
					systemicScenarioPnLSeriesArray[pnlIndex].baseline1974().composite();

				baseline2008PnLComposite = baseline2008PnLComposite +
					systemicScenarioPnLSeriesArray[pnlIndex].baseline2008().composite();

				deepDownturnPnLComposite = deepDownturnPnLComposite +
					systemicScenarioPnLSeriesArray[pnlIndex].deepDownturn().composite();

				dollarDeclinePnLComposite = dollarDeclinePnLComposite +
					systemicScenarioPnLSeriesArray[pnlIndex].dollarDecline().composite();

				interestRateShockPnLComposite = interestRateShockPnLComposite +
					systemicScenarioPnLSeriesArray[pnlIndex].interestRateShock().composite();
			}
		}

		try
		{
			return new SystemicScenarioPnLSeries (
				org.drip.capital.stress.PnLSeries.SingleOutcome (
					baseline1974PnLComposite
				),
				org.drip.capital.stress.PnLSeries.SingleOutcome (
					baseline2008PnLComposite
				),
				org.drip.capital.stress.PnLSeries.SingleOutcome (
					deepDownturnPnLComposite
				),
				org.drip.capital.stress.PnLSeries.SingleOutcome (
					dollarDeclinePnLComposite
				),
				org.drip.capital.stress.PnLSeries.SingleOutcome (
					interestRateShockPnLComposite
				),
				org.drip.capital.stress.PnLSeries.SingleOutcome (
					lostDecadePnLComposite
				)
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * SystemicScenarioPnLSeries Constructor
	 * 
	 * @param baseline1974 1974 Baseline PnL Series
	 * @param baseline2008 2008 Baseline PnL Series
	 * @param deepDownturn Deep Down-turn PnL Series
	 * @param dollarDecline Dollar Decline PnL Series
	 * @param interestRateShock Interest Rate Shock PnL Series
	 * @param lostDecade Lost Decade PnL Series
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public SystemicScenarioPnLSeries (
		final org.drip.capital.stress.PnLSeries baseline1974,
		final org.drip.capital.stress.PnLSeries baseline2008,
		final org.drip.capital.stress.PnLSeries deepDownturn,
		final org.drip.capital.stress.PnLSeries dollarDecline,
		final org.drip.capital.stress.PnLSeries interestRateShock,
		final org.drip.capital.stress.PnLSeries lostDecade)
		throws java.lang.Exception
	{
		if (null == (_baseline1974 = baseline1974) ||
			null == (_baseline2008 = baseline2008) ||
			null == (_deepDownturn = deepDownturn) ||
			null == (_dollarDecline = dollarDecline) ||
			null == (_interestRateShock = interestRateShock) ||
			null == (_lostDecade = lostDecade))
		{
			throw new java.lang.Exception ("SystemicScenarioPnLSeries Constructor => Invalid inputs");
		}
	}

	/**
	 * Retrieve the 1974 Baseline PnL Series
	 * 
	 * @return The 1974 Baseline PnL Series
	 */

	public org.drip.capital.stress.PnLSeries baseline1974()
	{
		return _baseline1974;
	}

	/**
	 * Retrieve the 2008 Baseline PnL Series
	 * 
	 * @return The 2008 Baseline PnL Series
	 */

	public org.drip.capital.stress.PnLSeries baseline2008()
	{
		return _baseline2008;
	}

	/**
	 * Retrieve the Deep Down-turn PnL Series
	 * 
	 * @return The Deep Down-turn PnL Series
	 */

	public org.drip.capital.stress.PnLSeries deepDownturn()
	{
		return _deepDownturn;
	}

	/**
	 * Retrieve the Dollar Decline PnL Series
	 * 
	 * @return The Dollar Decline PnL Series
	 */

	public org.drip.capital.stress.PnLSeries dollarDecline()
	{
		return _dollarDecline;
	}

	/**
	 * Retrieve the Interest Rate Shock PnL Series
	 * 
	 * @return The Interest Rate Shock PnL Series
	 */

	public org.drip.capital.stress.PnLSeries interestRateShock()
	{
		return _interestRateShock;
	}

	/**
	 * Retrieve the Lost Decade PnL Series
	 * 
	 * @return The Lost Decade PnL Series
	 */

	public org.drip.capital.stress.PnLSeries lostDecade()
	{
		return _lostDecade;
	}

	@Override public java.lang.String toString()
	{
		return "[" + _baseline1974.toString() + " | " +
			_baseline2008.toString() + " | " +
			_deepDownturn.toString() + " | " +
			_dollarDecline.toString() + " | " +
			_interestRateShock.toString() + " | " +
			_lostDecade.toString() + "]";
	}
}
