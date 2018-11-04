
package org.drip.analytics.definition;

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
 * Copyright (C) 2012 Lakshmi Krishnamurthy
 * Copyright (C) 2011 Lakshmi Krishnamurthy
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
 * <i>Curve</i> extends the Latent State to abstract the functionality required among all financial curve. It
 *  exposes the following functionality:
 *  <ul>
 *  	<li>Set the Epoch and the Identifiers</li>
 *  	<li>Set up/retrieve the Calibration Inputs</li>
 *  	<li>Retrieve the Latent State Metric Measures</li>
 *  </ul>
 *  <br>
 *  <ul>
 *		<li><b>Module</b>        = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics">Analytics</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/definition">Definition</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/FixedIncome">Fixed Income</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public interface Curve extends org.drip.state.representation.LatentState {

	/**
	 * Get the Curve Latent State Identifier Label
	 * 
	 * @return The Curve Latent State Identifier Label
	 */

	public abstract org.drip.state.identifier.LatentStateLabel label();

	/**
	 * Get the Epoch Date
	 * 
	 * @return The Epoch Date
	 */

	public abstract org.drip.analytics.date.JulianDate epoch();

	/**
	 * Get the Currency
	 * 
	 * @return Currency
	 */

	public abstract java.lang.String currency();

	/**
	 * Set the Curve Construction Input Set Parameters
	 * 
	 * @param ccis The Curve Construction Input Set Parameters
	 * 
	 * @return TRUE - Inputs successfully Set
	 */

	public boolean setCCIS (
		final org.drip.analytics.input.CurveConstructionInputSet ccis);

	/**
	 * Retrieve the Calibration Components
	 * 
	 * @return Array of Calibration Components
	 */

	public abstract org.drip.product.definition.CalibratableComponent[] calibComp();

	/**
	 * Retrieve the Manifest Measure Map of the given Instrument used to construct the Curve
	 * 
	 * @param strInstrumentCode The Calibration Instrument's Code whose Manifest Measure Map is sought
	 * 
	 * @return The Manifest Measure Map of the given Instrument used to construct the Curve
	 */

	public abstract org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> manifestMeasure (
		final java.lang.String strInstrumentCode);
}
