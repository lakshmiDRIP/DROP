
package org.drip.analytics.input;

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
 * <i>CurveConstructionInputSet</i> interface contains the Parameters needed for the Curve
 * Calibration/Estimation. It's methods expose access to the following:
 *
 *	<br><br>
 *  <ul>
 *  	<li>
 *  		Calibration Valuation Parameters
 *  	</li>
 *  	<li>
 *  		Calibration Quoting Parameters
 *  	</li>
 *  	<li>
 *  		Array of Calibration Instruments
 *  	</li>
 *  	<li>
 *  		Map of Calibration Quotes
 *  	</li>
 *  	<li>
 *  		Map of Calibration Measures
 *  	</li>
 *  	<li>
 *  		Double Map of the Date/Index Fixings
 *  	</li>
 * </ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/README.md">Analytics</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/input/README.md">Input</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public interface CurveConstructionInputSet {

	/**
	 * Retrieve the Valuation Parameter
	 * 
	 * @return The Valuation Parameter
	 */

	public abstract org.drip.param.valuation.ValuationParams valuationParameter();

	/**
	 * Retrieve the Market Parameters
	 * 
	 * @return The Market Parameters
	 */

	public abstract org.drip.param.market.CurveSurfaceQuoteContainer marketParameters();

	/**
	 * Retrieve the Pricer Parameters
	 * 
	 * @return The Pricer Parameters
	 */

	public abstract org.drip.param.pricer.CreditPricerParams pricerParameter();

	/**
	 * Retrieve the Quoting Parameter
	 * 
	 * @return The Quoting Parameter
	 */

	public abstract org.drip.param.valuation.ValuationCustomizationParams quotingParameter();

	/**
	 * Retrieve the Array of the Calibration Components
	 * 
	 * @return The Array of the Calibration Components
	 */

	public abstract org.drip.product.definition.CalibratableComponent[] components();

	/**
	 * Retrieve the Calibration Quote Map
	 * 
	 * @return The Calibration Quote Map
	 */

	public abstract
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>
			quoteMap();

	/**
	 * Retrieve the Map containing the array of the Calibration Measures
	 * 
	 * @return The Map containing the array of the Calibration Measures
	 */

	public abstract org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.String[]> measures();

	/**
	 * Retrieve the Latent State Fixings Container
	 * 
	 * @return The Latent State Fixings Container
	 */

	public abstract org.drip.param.market.LatentStateFixingsContainer fixing();
}
