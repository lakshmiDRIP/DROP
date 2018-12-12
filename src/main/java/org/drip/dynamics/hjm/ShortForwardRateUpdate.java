
package org.drip.dynamics.hjm;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
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
 * 	- Statistical Learning Library
 * 	- Numerical Optimizer Library
 * 	- Machine Learning Library
 * 	- Spline Builder Library
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
 * <i>ShortForwardRateUpdate</i> contains the Instantaneous Snapshot of the Evolving Discount Latent State
 * Quantification Metrics.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/dynamics/README.md">Dynamics</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/dynamics/hjm/README.md">HJM</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class ShortForwardRateUpdate extends org.drip.dynamics.evolution.LSQMPointUpdate {
	private org.drip.state.identifier.ForwardLabel _lslForward = null;
	private org.drip.state.identifier.FundingLabel _lslFunding = null;

	/**
	 * Construct an Instance of ShortForwardRateUpdate
	 * 
	 * @param lslFunding The Funding Latent State Label
	 * @param lslForward The Forward Latent State Label
	 * @param iInitialDate The Initial Date
	 * @param iFinalDate The Final Date
	 * @param iTargetPointDate The Target Point Date
	 * @param dblInstantaneousForwardRate The Instantaneous Forward Rate
	 * @param dblInstantaneousForwardRateIncrement The Instantaneous Forward Rate Increment
	 * @param dblLIBORForwardRate The LIBOR Forward Rate
	 * @param dblLIBORForwardRateIncrement The LIBOR Forward Rate Increment
	 * @param dblShiftedLIBORForwardRate The Shifted LIBOR Forward Rate
	 * @param dblShiftedLIBORForwardRateIncrement The Shifted LIBOR Forward Rate Increment
	 * @param dblShortRate The Short Rate
	 * @param dblShortRateIncrement The Short Rate Increment
	 * @param dblCompoundedShortRate The Compounded Short Rate
	 * @param dblCompoundedShortRateIncrement The Compounded Short Rate Increment
	 * @param dblPrice The Price
	 * @param dblPriceIncrement The Price Increment
	 * 
	 * @return Instance of ShortForwardRateUpdate
	 */

	public static final ShortForwardRateUpdate Create (
		final org.drip.state.identifier.FundingLabel lslFunding,
		final org.drip.state.identifier.ForwardLabel lslForward,
		final int iInitialDate,
		final int iFinalDate,
		final int iTargetPointDate,
		final double dblInstantaneousForwardRate,
		final double dblInstantaneousForwardRateIncrement,
		final double dblLIBORForwardRate,
		final double dblLIBORForwardRateIncrement,
		final double dblShiftedLIBORForwardRate,
		final double dblShiftedLIBORForwardRateIncrement,
		final double dblShortRate,
		final double dblShortRateIncrement,
		final double dblCompoundedShortRate,
		final double dblCompoundedShortRateIncrement,
		final double dblPrice,
		final double dblPriceIncrement)
	{
		org.drip.dynamics.evolution.LSQMPointRecord lrSnapshot = new
			org.drip.dynamics.evolution.LSQMPointRecord();

		if (!lrSnapshot.setQM (lslFunding,
			org.drip.analytics.definition.LatentStateStatic.DISCOUNT_QM_ZERO_RATE, dblShortRate))
			return null;

		if (!lrSnapshot.setQM (lslFunding,
			org.drip.analytics.definition.LatentStateStatic.DISCOUNT_QM_COMPOUNDED_SHORT_RATE,
				dblCompoundedShortRate))
			return null;

		if (!lrSnapshot.setQM (lslFunding,
			org.drip.analytics.definition.LatentStateStatic.DISCOUNT_QM_DISCOUNT_FACTOR, dblPrice))
			return null;

		if (!lrSnapshot.setQM (lslForward,
			org.drip.analytics.definition.LatentStateStatic.FORWARD_QM_FORWARD_RATE, dblLIBORForwardRate))
			return null;

		if (!lrSnapshot.setQM (lslForward,
			org.drip.analytics.definition.LatentStateStatic.FORWARD_QM_SHIFTED_FORWARD_RATE,
				dblShiftedLIBORForwardRate))
			return null;

		if (!lrSnapshot.setQM (lslForward,
			org.drip.analytics.definition.LatentStateStatic.FORWARD_QM_INSTANTANEOUS_FORWARD_RATE,
				dblInstantaneousForwardRate))
			return null;

		org.drip.dynamics.evolution.LSQMPointRecord lrIncrement = new
			org.drip.dynamics.evolution.LSQMPointRecord();

		if (!lrIncrement.setQM (lslFunding,
			org.drip.analytics.definition.LatentStateStatic.DISCOUNT_QM_ZERO_RATE, dblShortRateIncrement))
			return null;

		if (!lrIncrement.setQM (lslFunding,
			org.drip.analytics.definition.LatentStateStatic.DISCOUNT_QM_COMPOUNDED_SHORT_RATE,
				dblCompoundedShortRateIncrement))
			return null;

		if (!lrIncrement.setQM (lslFunding,
			org.drip.analytics.definition.LatentStateStatic.DISCOUNT_QM_DISCOUNT_FACTOR, dblPriceIncrement))
			return null;

		if (!lrIncrement.setQM (lslForward,
			org.drip.analytics.definition.LatentStateStatic.FORWARD_QM_FORWARD_RATE,
				dblLIBORForwardRateIncrement))
			return null;

		if (!lrIncrement.setQM (lslForward,
			org.drip.analytics.definition.LatentStateStatic.FORWARD_QM_SHIFTED_FORWARD_RATE,
				dblShiftedLIBORForwardRateIncrement))
			return null;

		if (!lrIncrement.setQM (lslForward,
			org.drip.analytics.definition.LatentStateStatic.FORWARD_QM_INSTANTANEOUS_FORWARD_RATE,
				dblInstantaneousForwardRateIncrement))
			return null;

		try {
			return new ShortForwardRateUpdate (lslFunding, lslForward, iInitialDate, iFinalDate,
				iTargetPointDate, lrSnapshot, lrIncrement);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	private ShortForwardRateUpdate (
		final org.drip.state.identifier.FundingLabel lslFunding,
		final org.drip.state.identifier.ForwardLabel lslForward,
		final int iInitialDate,
		final int iFinalDate,
		final int iViewDate,
		final org.drip.dynamics.evolution.LSQMPointRecord lrSnapshot,
		final org.drip.dynamics.evolution.LSQMPointRecord lrIncrement)
		throws java.lang.Exception
	{
		super (iInitialDate, iFinalDate, iViewDate, lrSnapshot, lrIncrement);

		if (null == (_lslFunding = lslFunding) || null == (_lslForward = lslForward))
			throw new java.lang.Exception ("ShortForwardRateUpdate ctr: Invalid Inputs");
	}

	/**
	 * Retrieve the Instantaneous Forward Rate
	 * 
	 * @return The Instantaneous Forward Rate
	 * 
	 * @throws java.lang.Exception Thrown if the Instantaneous Forward Rate is not available
	 */

	public double instantaneousForwardRate()
		throws java.lang.Exception
	{
		return snapshot().qm (_lslForward,
			org.drip.analytics.definition.LatentStateStatic.FORWARD_QM_INSTANTANEOUS_FORWARD_RATE);
	}

	/**
	 * Retrieve the Instantaneous Forward Rate Increment
	 * 
	 * @return The Instantaneous Forward Rate Increment
	 * 
	 * @throws java.lang.Exception Thrown if the Instantaneous Forward Rate Increment is not available
	 */

	public double instantaneousForwardRateIncrement()
		throws java.lang.Exception
	{
		return increment().qm (_lslForward,
			org.drip.analytics.definition.LatentStateStatic.FORWARD_QM_INSTANTANEOUS_FORWARD_RATE);
	}

	/**
	 * Retrieve the LIBOR Forward Rate
	 * 
	 * @return The LIBOR Forward Rate
	 * 
	 * @throws java.lang.Exception Thrown if the Forward Rate is not available
	 */

	public double liborForwardRate()
		throws java.lang.Exception
	{
		return snapshot().qm (_lslForward,
			org.drip.analytics.definition.LatentStateStatic.FORWARD_QM_FORWARD_RATE);
	}

	/**
	 * Retrieve the LIBOR Forward Rate Increment
	 * 
	 * @return The LIBOR Forward Rate Increment
	 * 
	 * @throws java.lang.Exception Thrown if the Forward Rate Increment is not available
	 */

	public double liborForwardRateIncrement()
		throws java.lang.Exception
	{
		return increment().qm (_lslForward,
			org.drip.analytics.definition.LatentStateStatic.FORWARD_QM_FORWARD_RATE);
	}

	/**
	 * Retrieve the Shifted LIBOR Forward Rate
	 * 
	 * @return The Shifted LIBOR Forward Rate
	 * 
	 * @throws java.lang.Exception Thrown if the Shifted Forward Rate is not available
	 */

	public double shiftedLIBORForwardRate()
		throws java.lang.Exception
	{
		return snapshot().qm (_lslForward,
			org.drip.analytics.definition.LatentStateStatic.FORWARD_QM_SHIFTED_FORWARD_RATE);
	}

	/**
	 * Retrieve the Shifted LIBOR Forward Rate Increment
	 * 
	 * @return The Shifted LIBOR Forward Rate Increment
	 * 
	 * @throws java.lang.Exception Thrown if the Shifted Forward Rate Increment is not available
	 */

	public double shiftedLIBORForwardRateIncrement()
		throws java.lang.Exception
	{
		return increment().qm (_lslForward,
			org.drip.analytics.definition.LatentStateStatic.FORWARD_QM_SHIFTED_FORWARD_RATE);
	}

	/**
	 * Retrieve the Short Rate
	 * 
	 * @return The Short Rate
	 * 
	 * @throws java.lang.Exception Thrown if the Short Rate is not available
	 */

	public double shortRate()
		throws java.lang.Exception
	{
		return snapshot().qm (_lslFunding,
			org.drip.analytics.definition.LatentStateStatic.DISCOUNT_QM_ZERO_RATE);
	}

	/**
	 * Retrieve the Short Rate Increment
	 * 
	 * @return The Short Rate Increment
	 * 
	 * @throws java.lang.Exception Thrown if the Short Rate Increment is not available
	 */

	public double shortRateIncrement()
		throws java.lang.Exception
	{
		return increment().qm (_lslFunding,
			org.drip.analytics.definition.LatentStateStatic.DISCOUNT_QM_ZERO_RATE);
	}

	/**
	 * Retrieve the Compounded Short Rate
	 * 
	 * @return The Compounded Short Rate
	 * 
	 * @throws java.lang.Exception Thrown if the Compounded Short Rate is not available
	 */

	public double compoundedShortRate()
		throws java.lang.Exception
	{
		return snapshot().qm (_lslFunding,
			org.drip.analytics.definition.LatentStateStatic.DISCOUNT_QM_COMPOUNDED_SHORT_RATE);
	}

	/**
	 * Retrieve the Compounded Short Rate Increment
	 * 
	 * @return The Compounded Short Rate Increment
	 * 
	 * @throws java.lang.Exception Thrown if the Compounded Short Rate Increment is not available
	 */

	public double compoundedShortRateIncrement()
		throws java.lang.Exception
	{
		return increment().qm (_lslFunding,
			org.drip.analytics.definition.LatentStateStatic.DISCOUNT_QM_COMPOUNDED_SHORT_RATE);
	}

	/**
	 * Retrieve the Price
	 * 
	 * @return The Price
	 * 
	 * @throws java.lang.Exception Thrown if the Price is not available
	 */

	public double price()
		throws java.lang.Exception
	{
		return snapshot().qm (_lslFunding,
			org.drip.analytics.definition.LatentStateStatic.DISCOUNT_QM_DISCOUNT_FACTOR);
	}

	/**
	 * Retrieve the Price Increment
	 * 
	 * @return The Price Increment
	 * 
	 * @throws java.lang.Exception Thrown if the Price Increment is not available
	 */

	public double priceIncrement()
		throws java.lang.Exception
	{
		return increment().qm (_lslFunding,
			org.drip.analytics.definition.LatentStateStatic.DISCOUNT_QM_DISCOUNT_FACTOR);
	}
}
