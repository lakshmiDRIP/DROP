
package org.drip.product.creator;

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
 * <i>DualStreamComponentBuilder</i> contains the suite of helper functions for creating the Stream-based
 * Dual Streams from different kinds of inputs. In particular, it exposes the following functionality:
 * 
 * <br><br>
 * <ul>
 * 	<li>
 *  	Construction of the fix-float swap component.
 *  </li>
 * 	<li>
 *  	Construction of the float-float swap component.
 *  </li>
 * 	<li>
 *  	Construction of the generic dual stream component.
 *  </li>
 * </ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product">Product</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/creator">Creator</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class DualStreamComponentBuilder {

	/**
	 * Make the FixFloatComponent Instance from the Reference Fixed and the Derived Floating Streams
	 * 
	 * @param fixReference The Reference Fixed Stream
	 * @param floatDerived The Derived Floating Stream
	 * @param csp Cash Settle Parameters
	 * 
	 * @return The FixFloatComponent Instance
	 */

	public static final org.drip.product.rates.FixFloatComponent MakeFixFloat (
		final org.drip.product.rates.Stream fixReference,
		final org.drip.product.rates.Stream floatDerived,
		final org.drip.param.valuation.CashSettleParams csp)
	{
		try {
			return new org.drip.product.rates.FixFloatComponent (fixReference, floatDerived, csp);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Make the FloatFloatComponent Instance from the Reference and the Derived Floating Streams
	 * 
	 * @param floatReference The Reference Floating Stream
	 * @param floatDerived The Derived Floating Stream
	 * @param csp Cash Settle Parameters
	 * 
	 * @return The FloatFloatComponent Instance
	 */

	public static final org.drip.product.rates.FloatFloatComponent MakeFloatFloat (
		final org.drip.product.rates.Stream floatReference,
		final org.drip.product.rates.Stream floatDerived,
		final org.drip.param.valuation.CashSettleParams csp)
	{
		try {
			return new org.drip.product.rates.FloatFloatComponent (floatReference, floatDerived, csp);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
