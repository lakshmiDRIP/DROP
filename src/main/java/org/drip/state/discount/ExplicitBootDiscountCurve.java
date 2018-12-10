
package org.drip.state.discount;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
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
 * <i>ExplicitBootDiscountCurve</i> exposes the functionality associated with the bootstrapped Discount
 * Curve.
 *
 *  <br><br>
 *  <ul>
 *  	<li>
 *  		Generate a curve shifted using targeted basis at specific nodes
 *  	</li>
 *  	<li>
 *  		Generate scenario tweaked Latent State from the base forward curve corresponding to mode adjusted
 *  			(flat/parallel/custom) manifest measure/quantification metric
 *  	</li>
 *  	<li>
 *  		Retrieve array of latent state manifest measure, instrument quantification metric, and the array
 *  			of calibration components
 *  	</li>
 *  	<li>
 *  		Set/retrieve curve construction input instrument sets
 *  	</li>
 *  </ul>
 *
 *  <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state">State</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/discount">Discount</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public abstract class ExplicitBootDiscountCurve extends org.drip.state.discount.MergedDiscountForwardCurve
	implements org.drip.analytics.definition.ExplicitBootCurve {

	protected ExplicitBootDiscountCurve (
		final int iEpochDate,
		final java.lang.String strCurrency)
		throws java.lang.Exception
	{
		super (iEpochDate, strCurrency, null);
	}

	/**
	 * Create a shifted curve from an array of basis shifts
	 * 
	 * @param aiDate Array of dates
	 * @param adblBasis Array of basis
	 * 
	 * @return Discount Curve
	 */

	public abstract ExplicitBootDiscountCurve createBasisRateShiftedCurve (
		final int[] aiDate,
		final double[] adblBasis);

	@Override public boolean setCCIS (
		final org.drip.analytics.input.CurveConstructionInputSet ccis)
	{
		return null != (_ccis = ccis);
	}

	@Override public org.drip.product.definition.CalibratableComponent[] calibComp()
	{
		return null == _ccis ? null : _ccis.components();
	}

	@Override public org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> manifestMeasure (
		final java.lang.String strInstrumentCode)
	{
		if (null == _ccis) return null;

		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>
			mapQuote = _ccis.quoteMap();

		if (null == mapQuote || !mapQuote.containsKey (strInstrumentCode)) return null;

		return mapQuote.get (strInstrumentCode);
	}
}
