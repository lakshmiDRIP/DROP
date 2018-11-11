
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
 * <i>CrossFloatSwapConvention</i> contains the Details of the Cross-Currency Floating Swap of an OTC
 * Contact.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module</b>        = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/market">Market</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/market/otc">Over-the-Counter</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/FixedIncome">Fixed Income Analytics</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class CrossFloatSwapConvention {
	private int _iSpotLag = -1;
	private int _iFixingType = -1;
	private boolean _bFXMTM = false;
	private org.drip.market.otc.CrossFloatStreamConvention _crossStreamDerived = null;
	private org.drip.market.otc.CrossFloatStreamConvention _crossStreamReference = null;

	private org.drip.product.rates.Stream floatingStream (
		final org.drip.analytics.date.JulianDate dtEffective,
		final org.drip.market.otc.CrossFloatStreamConvention cfsc,
		final org.drip.param.period.FixingSetting fxFixingSetting,
		final java.lang.String strMaturityTenor,
		final double dblBasis,
		final double dblNotional)
	{
		java.lang.String strFloaterTenor = cfsc.tenor();

		org.drip.state.identifier.ForwardLabel forwardLabel = org.drip.state.identifier.ForwardLabel.Create
			(org.drip.market.definition.IBORIndexContainer.IndexFromJurisdiction (cfsc.currency()),
				strFloaterTenor);

		if (null == forwardLabel) return null;

		try {
			org.drip.param.period.ComposableFloatingUnitSetting cfusReference = new
				org.drip.param.period.ComposableFloatingUnitSetting (strFloaterTenor,
					org.drip.analytics.support.CompositePeriodBuilder.EDGE_DATE_SEQUENCE_REGULAR, null,
						forwardLabel,
							org.drip.analytics.support.CompositePeriodBuilder.REFERENCE_PERIOD_IN_ADVANCE,
								cfsc.applySpread() ? dblBasis : 0.);

			org.drip.param.period.CompositePeriodSetting cpsReference = new
				org.drip.param.period.CompositePeriodSetting
					(org.drip.analytics.support.Helper.TenorToFreq (strFloaterTenor),
						strFloaterTenor, _crossStreamReference.currency(), null, dblNotional, null, null,
							fxFixingSetting, null);

			java.util.List<java.lang.Integer> lsReferenceEdgeDate =
				org.drip.analytics.support.CompositePeriodBuilder.RegularEdgeDates (dtEffective,
					strFloaterTenor, strMaturityTenor, null);

			return new org.drip.product.rates.Stream
				(org.drip.analytics.support.CompositePeriodBuilder.FloatingCompositeUnit
					(lsReferenceEdgeDate, cpsReference, cfusReference));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * CrossFloatSwapConvention Constructor
	 * 
	 * @param crossStreamReference Reference Cross Float Stream
	 * @param crossStreamDerived Derived Cross Float Stream
	 * @param iFixingType Fixing Type
	 * @param bFXMTM TRUE - The Cross Currency Swap is of the FX MTM type
	 * @param iSpotLag Spot Lag
	 * 
	 * @throws java.lang.Exception Thrown if Inputs are Invalid
	 */

	public CrossFloatSwapConvention (
		final org.drip.market.otc.CrossFloatStreamConvention crossStreamReference,
		final org.drip.market.otc.CrossFloatStreamConvention crossStreamDerived,
		final int iFixingType,
		final boolean bFXMTM,
		final int iSpotLag)
		throws java.lang.Exception
	{
		if (null == (_crossStreamReference = crossStreamReference) || null == (_crossStreamDerived =
			crossStreamDerived) || !org.drip.param.period.FixingSetting.ValidateType (_iFixingType =
				iFixingType) || 0 > (_iSpotLag = iSpotLag))
			throw new java.lang.Exception ("CrossFloatSwapConvention ctr: Invalid Inputs");

		_bFXMTM = bFXMTM;
	}

	/**
	 * Retrieve the Reference Convention
	 * 
	 * @return The Reference Convention
	 */

	public org.drip.market.otc.CrossFloatStreamConvention referenceConvention()
	{
		return _crossStreamReference;
	}

	/**
	 * Retrieve the Derived Convention
	 * 
	 * @return The Derived Convention
	 */

	public org.drip.market.otc.CrossFloatStreamConvention derivedConvention()
	{
		return _crossStreamDerived;
	}

	/**
	 * Retrieve the Fixing Setting Type
	 * 
	 * @return The Fixing Setting Type
	 */

	public int fixingType()
	{
		return _iFixingType;
	}

	/**
	 * Retrieve the Spot Lag
	 * 
	 * @return The Spot Lag
	 */

	public int spotLag()
	{
		return _iSpotLag;
	}

	/**
	 * Retrieve the FX MTM Flag
	 * 
	 * @return The FX MTM Flag
	 */

	public boolean isFXMTM()
	{
		return _bFXMTM;
	}

	/**
	 * Create an Instance of the Float-Float Component
	 * 
	 * @param dtSpot Spot Date
	 * @param strMaturityTenor The Maturity Tenor
	 * @param dblBasis Basis
	 * @param dblReferenceNotional Notional of the Reference Stream
	 * @param dblDerivedNotional Notional of the Derived Stream
	 * 
	 * @return Instance of the Float-Float Component
	 */

	public org.drip.product.rates.FloatFloatComponent createFloatFloatComponent (
		final org.drip.analytics.date.JulianDate dtSpot,
		final java.lang.String strMaturityTenor,
		final double dblBasis,
		final double dblReferenceNotional,
		final double dblDerivedNotional)
	{
		if (null == dtSpot) return null;

		org.drip.analytics.date.JulianDate dtEffective = dtSpot.addBusDays (_iSpotLag,
			org.drip.market.definition.IBORIndexContainer.IndexFromJurisdiction
				(_crossStreamReference.currency()).calendar() + "," +
					org.drip.market.definition.IBORIndexContainer.IndexFromJurisdiction
						(_crossStreamDerived.currency()).calendar());

		try {
			org.drip.param.period.FixingSetting fxFixingSetting = _bFXMTM ? null : new
				org.drip.param.period.FixingSetting (_iFixingType, null, dtEffective.julian());

			return new org.drip.product.rates.FloatFloatComponent (floatingStream (dtEffective,
				_crossStreamReference, fxFixingSetting, strMaturityTenor, dblBasis, dblReferenceNotional),
					floatingStream (dtEffective, _crossStreamDerived, fxFixingSetting, strMaturityTenor,
						dblBasis, dblDerivedNotional), null);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override public java.lang.String toString()
	{
		return _crossStreamReference + " " + _crossStreamDerived + " " + _iFixingType + " " + _iSpotLag + " "
			+ _bFXMTM;
	}
}
