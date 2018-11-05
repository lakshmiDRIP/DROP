
package org.drip.dynamics.sabr;

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
 *  	calculations, and portfolio construction within and across fixed income, credit, commodity, equity,
 *  	FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three main modules:
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
 * <i>ForwardRateUpdate</i> contains the Increment and Snapshot of the Forward Rate Latent State evolved
 * through the SABR Dynamics.
 * 	<br>
 *  <ul>
 *		<li><b>Module</b>        = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/dynamics">Dynamics</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/dynamics/sabr">SABR</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/Fixed Income">Fixed Income Analytics</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class ForwardRateUpdate extends org.drip.dynamics.evolution.LSQMPointUpdate {
	private org.drip.state.identifier.ForwardLabel _lslForward = null;

	/**
	 * ForwardRateUpdate Creator
	 * 
	 * @param lslForward The Forward Rate Latent State Label
	 * @param iInitialDate The Initial Date
	 * @param iFinalDate The Final Date
	 * @param iTargetPointDate The Target Point Date
	 * @param dblForwardRate The Forward Rate
	 * @param dblForwardRateIncrement The Forward Rate Increment
	 * @param dblForwardRateVolatility The Forward Volatility 
	 * @param dblForwardRateVolatilityIncrement The Forward Volatility Rate
	 * 
	 * @return Instance of ForwardRateUpdate
	 */

	public static final ForwardRateUpdate Create (
		final org.drip.state.identifier.ForwardLabel lslForward,
		final int iInitialDate,
		final int iFinalDate,
		final int iTargetPointDate,
		final double dblForwardRate,
		final double dblForwardRateIncrement,
		final double dblForwardRateVolatility,
		final double dblForwardRateVolatilityIncrement)
	{
		org.drip.dynamics.evolution.LSQMPointRecord lrSnapshot = new
			org.drip.dynamics.evolution.LSQMPointRecord();

		if (!lrSnapshot.setQM (lslForward,
			org.drip.analytics.definition.LatentStateStatic.FORWARD_QM_FORWARD_RATE, dblForwardRate))
			return null;

		if (!lrSnapshot.setQM (org.drip.state.identifier.VolatilityLabel.Standard (lslForward),
			org.drip.analytics.definition.LatentStateStatic.VOLATILITY_QM_SABR_VOLATILITY,
				dblForwardRateVolatility))
			return null;

		org.drip.dynamics.evolution.LSQMPointRecord lrIncrement = new
			org.drip.dynamics.evolution.LSQMPointRecord();

		if (!lrIncrement.setQM (lslForward,
			org.drip.analytics.definition.LatentStateStatic.FORWARD_QM_FORWARD_RATE,
				dblForwardRateIncrement))
			return null;

		if (!lrIncrement.setQM (org.drip.state.identifier.VolatilityLabel.Standard (lslForward),
			org.drip.analytics.definition.LatentStateStatic.VOLATILITY_QM_SABR_VOLATILITY,
				dblForwardRateVolatilityIncrement))
			return null;

		try {
			return new ForwardRateUpdate (lslForward, iInitialDate, iFinalDate, iTargetPointDate, lrSnapshot,
				lrIncrement);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	private ForwardRateUpdate (
		final org.drip.state.identifier.ForwardLabel lslForward,
		final int iInitialDate,
		final int iFinalDate,
		final int iViewDate,
		final org.drip.dynamics.evolution.LSQMPointRecord lrSnapshot,
		final org.drip.dynamics.evolution.LSQMPointRecord lrIncrement)
		throws java.lang.Exception
	{
		super (iInitialDate, iFinalDate, iViewDate, lrSnapshot, lrIncrement);

		if (null == (_lslForward = lslForward))
			throw new java.lang.Exception ("ForwardRateUpdate ctr: Invalid Inputs");
	}

	/**
	 * Retrieve the Forward Rate
	 * 
	 * @return The Forward Rate
	 * 
	 * @throws java.lang.Exception Thrown if the Forward Rate is not available
	 */

	public double forwardRate()
		throws java.lang.Exception
	{
		return snapshot().qm (_lslForward,
			org.drip.analytics.definition.LatentStateStatic.FORWARD_QM_FORWARD_RATE);
	}

	/**
	 * Retrieve the Forward Rate Increment
	 * 
	 * @return The Forward Rate Increment
	 * 
	 * @throws java.lang.Exception Thrown if the Forward Rate Increment is not available
	 */

	public double forwardRateIncrement()
		throws java.lang.Exception
	{
		return increment().qm (_lslForward,
			org.drip.analytics.definition.LatentStateStatic.FORWARD_QM_FORWARD_RATE);
	}

	/**
	 * Retrieve the Forward Rate Volatility
	 * 
	 * @return The Forward Rate Volatility
	 * 
	 * @throws java.lang.Exception Thrown if the Forward Rate Volatility is not available
	 */

	public double forwardRateVolatility()
		throws java.lang.Exception
	{
		return snapshot().qm (org.drip.state.identifier.VolatilityLabel.Standard (_lslForward),
			org.drip.analytics.definition.LatentStateStatic.VOLATILITY_QM_SABR_VOLATILITY);
	}

	/**
	 * Retrieve the Forward Rate Volatility Increment
	 * 
	 * @return The Forward Rate Volatility Increment
	 * 
	 * @throws java.lang.Exception Thrown if the Forward Rate Volatility Increment is not available
	 */

	public double forwardRateVolatilityIncrement()
		throws java.lang.Exception
	{
		return increment().qm (org.drip.state.identifier.VolatilityLabel.Standard (_lslForward),
			org.drip.analytics.definition.LatentStateStatic.VOLATILITY_QM_SABR_VOLATILITY);
	}
}
