
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
 * <i>CreditComponent</i> is the base abstract class on top of which all credit components are implemented.
 * Its methods expose Credit Valuation Parameters, product specific recovery, and coupon/loss cash flows.
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

public abstract class CreditComponent extends org.drip.product.definition.CalibratableComponent {

	/**
	 * Generate the loss flow for the credit component based on the pricer parameters
	 * 
	 * @param valParams ValuationParams
	 * @param pricerParams PricerParams
	 * @param csqc ComponentMarketParams
	 * 
	 * @return List of ProductLossPeriodCurveMeasures
	 */

	public abstract java.util.List<org.drip.analytics.cashflow.LossQuadratureMetrics> lossFlow (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc);

	/**
	 * Get the recovery of the credit component for the given date
	 * 
	 * @param iDate JulianDate
	 * @param cc Credit Curve
	 * 
	 * @return Recovery
	 * 
	 * @throws java.lang.Exception Thrown if recovery cannot be calculated
	 */

	public abstract double recovery (
		final int iDate,
		final org.drip.state.credit.CreditCurve cc)
		throws java.lang.Exception;

	/**
	 * Get the time-weighted recovery of the credit component between the given dates
	 * 
	 * @param iDate1 JulianDate #1
	 * @param iDate2 JulianDate #2
	 * @param cc Credit Curve
	 * 
	 * @return Recovery
	 * 
	 * @throws java.lang.Exception Thrown if recovery cannot be calculated
	 */

	public abstract double recovery (
		final int iDate1,
		final int iDate2,
		final org.drip.state.credit.CreditCurve cc)
		throws java.lang.Exception;

	/**
	 * Get the credit component's Credit Valuation Parameters
	 * 
	 * @return CompCRValParams
	 */

	public abstract org.drip.product.params.CreditSetting creditValuationParams();

	/**
	 * Generate the loss flow for the credit component based on the pricer parameters
	 * 
	 * @param dtSpot The Spot Date
	 * @param csqc The Component Market Parameters
	 * 
	 * @return List of ProductLossPeriodCurveMeasures
	 */

	public java.util.List<org.drip.analytics.cashflow.LossQuadratureMetrics> lossFlow (
		final org.drip.analytics.date.JulianDate dtSpot,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc)
	{
		return null == dtSpot ? null : lossFlow (org.drip.param.valuation.ValuationParams.Spot
			(dtSpot.julian()), org.drip.param.pricer.CreditPricerParams.Standard(), csqc);
	}
}
