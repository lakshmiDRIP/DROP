
package org.drip.product.definition;

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
 * <i>ComponentMarketParamRef</i> interface provides stubs for name, IR curve, forward curve, credit curve,
 * TSY curve, and needed to value the component.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product">Product</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/credit">Credit</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public interface ComponentMarketParamRef {

	/**
	 * Get the component name
	 * 
	 * @return The component name
	 */

	public abstract java.lang.String name();

	/**
	 * Get the Map of Coupon Currencies
	 * 
	 * @return The Map of Coupon Currencies
	 */

	public abstract org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.String> couponCurrency();

	/**
	 * Get the Pay Currency
	 * 
	 * @return The Pay Currency
	 */

	public abstract java.lang.String payCurrency();

	/**
	 * Get the Principal Currency
	 * 
	 * @return The Principal Currency
	 */

	public abstract java.lang.String principalCurrency();

	/**
	 * Get the Credit Curve Latent State Identifier Label
	 * 
	 * @return The Credit Curve Latent State Identifier Label
	 */

	public abstract org.drip.state.identifier.EntityCDSLabel creditLabel();

	/**
	 * Get the Map of Forward Latent State Labels
	 * 
	 * @return The Map of the Forward Latent State Labels
	 */

	public abstract org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.identifier.ForwardLabel>
		forwardLabel();

	/**
	 * Get the Map of OTC Fix Float Latent State Labels
	 * 
	 * @return The Map of the OTC Fix Float Latent State Labels
	 */

	public abstract
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.identifier.OTCFixFloatLabel>
		otcFixFloatLabel();

	/**
	 * Get the Funding Curve Latent State Label
	 * 
	 * @return Funding Curve Latent State Label
	 */

	public abstract org.drip.state.identifier.FundingLabel fundingLabel();

	/**
	 * Get the Govvie Curve Latent State Label
	 * 
	 * @return Govvie Curve Latent State Label
	 */

	public abstract org.drip.state.identifier.GovvieLabel govvieLabel();

	/**
	 * Get the Map of FX Latent State Identifier Labels
	 * 
	 * @return The Map of FX Latent State Identifier Labels
	 */

	public abstract org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.identifier.FXLabel>
		fxLabel();

	/**
	 * Get the Map of Volatility Latent State Identifier Labels
	 * 
	 * @return The Map of Volatility Latent State Identifier Labels
	 */

	public abstract
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.identifier.VolatilityLabel>
			volatilityLabel();
}
