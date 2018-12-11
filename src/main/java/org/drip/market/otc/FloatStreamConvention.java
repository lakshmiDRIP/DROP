
package org.drip.market.otc;

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
 * <i>FloatStreamConvention</i> contains the details of the Floating Stream of an OTC IBOR/Overnight Fix-
 * Float Swap Contract.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/market">Market</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/market/otc">OTC</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class FloatStreamConvention {
	private java.lang.String _strCompositePeriodTenor = "";
	private org.drip.state.identifier.ForwardLabel _forwardLabel = null;

	/**
	 * FloatStreamConvention Constructor
	 * 
	 * @param forwardLabel The Forward Label
	 * @param strCompositePeriodTenor Composite Period Tenor
	 * 
	 * @throws java.lang.Exception Thrown if Inputs are invalid
	 */

	public FloatStreamConvention (
		final org.drip.state.identifier.ForwardLabel forwardLabel,
		final java.lang.String strCompositePeriodTenor)
		throws java.lang.Exception
	{
		if (null == (_forwardLabel = forwardLabel) || null == (_strCompositePeriodTenor =
			strCompositePeriodTenor) || _strCompositePeriodTenor.isEmpty())
			throw new java.lang.Exception ("FloatStreamConvention ctr: Invalid Inputs");
	}

	/**
	 * Retrieve the Forward Label
	 * 
	 * @return The Forward Label
	 */

	public org.drip.state.identifier.ForwardLabel floaterIndex()
	{
		return _forwardLabel;
	}

	/**
	 * Retrieve the Composite Period Tenor
	 * 
	 * @return The Composite Period Tenor
	 */

	public java.lang.String compositePeriodTenor()
	{
		return _strCompositePeriodTenor;
	}

	/**
	 * Create a Floating Stream Instance
	 * 
	 * @param dtEffective Effective Date
	 * @param strMaturityTenor Maturity Tenor
	 * @param dblBasis Basis
	 * @param dblNotional Notional
	 * 
	 * @return The Fixed Stream Instance
	 */

	public org.drip.product.rates.Stream createStream (
		final org.drip.analytics.date.JulianDate dtEffective,
		final java.lang.String strMaturityTenor,
		final double dblBasis,
		final double dblNotional)
	{
		boolean bOvernight = _forwardLabel.overnight();

		try {
			org.drip.param.period.ComposableFloatingUnitSetting cfus = new
				org.drip.param.period.ComposableFloatingUnitSetting (bOvernight ? "ON" :
					_forwardLabel.tenor(), bOvernight ?
						org.drip.analytics.support.CompositePeriodBuilder.EDGE_DATE_SEQUENCE_OVERNIGHT :
							org.drip.analytics.support.CompositePeriodBuilder.EDGE_DATE_SEQUENCE_REGULAR,
								null, _forwardLabel,
									org.drip.analytics.support.CompositePeriodBuilder.REFERENCE_PERIOD_IN_ADVANCE,
				dblBasis);

			org.drip.param.period.CompositePeriodSetting cps = new
				org.drip.param.period.CompositePeriodSetting (bOvernight ? 360 :
					org.drip.analytics.support.Helper.TenorToFreq (_strCompositePeriodTenor),
						_strCompositePeriodTenor, _forwardLabel.currency(), null, dblNotional, null, null,
							null, null);

			java.util.List<java.lang.Integer> lsEdgeDate = bOvernight ? 
				org.drip.analytics.support.CompositePeriodBuilder.OvernightEdgeDates (dtEffective,
					dtEffective.addTenor (strMaturityTenor), null):
						org.drip.analytics.support.CompositePeriodBuilder.RegularEdgeDates (dtEffective,
							_strCompositePeriodTenor, strMaturityTenor, null);

			return new org.drip.product.rates.Stream
				(org.drip.analytics.support.CompositePeriodBuilder.FloatingCompositeUnit (lsEdgeDate, cps,
					cfus));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override public java.lang.String toString()
	{
		return "[FLOAT: " + _forwardLabel.fullyQualifiedName() + " | " + _strCompositePeriodTenor + "]";
	}
}
