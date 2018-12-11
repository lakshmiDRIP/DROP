
package org.drip.simm.estimator;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
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
 * <i>AdditionalInitialMargin</i> holds the Additional Initial Margin along with the Product Specific Add-On
 * Components. The References are:
 * 
 * <br><br>
 *  <ul>
 *  	<li>
 *  		Andersen, L. B. G., M. Pykhtin, and A. Sokol (2017): Credit Exposure in the Presence of Initial
 *  			Margin https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2806156 <b>eSSRN</b>
 *  	</li>
 *  	<li>
 *  		Albanese, C., S. Caenazzo, and O. Frankel (2017): Regression Sensitivities for Initial Margin
 *  			Calculations https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2763488 <b>eSSRN</b>
 *  	</li>
 *  	<li>
 *  		Anfuso, F., D. Aziz, P. Giltinan, and K. Loukopoulus (2017): A Sound Modeling and Back-testing
 *  			Framework for Forecasting Initial Margin Requirements
 *  				https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2716279 <b>eSSRN</b>
 *  	</li>
 *  	<li>
 *  		Caspers, P., P. Giltinan, R. Lichters, and N. Nowaczyk (2017): Forecasting Initial Margin
 *  			Requirements - A Model Evaluation https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2911167
 *  				<b>eSSRN</b>
 *  	</li>
 *  	<li>
 *  		International Swaps and Derivatives Association (2017): SIMM v2.0 Methodology
 *  			https://www.isda.org/a/oFiDE/isda-simm-v2.pdf
 *  	</li>
 *  </ul>
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ExposureAnalyticsLibrary.md">Exposure Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm">SIMM</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/estimator">Estimator</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class AdditionalInitialMargin
{
	private double _addOnFixed = java.lang.Double.NaN;
	private double _creditMultiplicativeScale = java.lang.Double.NaN;
	private double _equityMultiplicativeScale = java.lang.Double.NaN;
	private double _ratesFXMultiplicativeScale = java.lang.Double.NaN;
	private double _commodityMultiplicativeScale = java.lang.Double.NaN;
	private java.util.Map<java.lang.String, java.lang.Double> _productAddOnFactorMap = null;

	/**
	 * Construct a Standard Instance of AdditionalInitialMargin
	 * 
	 * @param addOnFixed The Fixed Add-On
	 * @param productAddOnFactorMap The Product Add-On Factor Map
	 * 
	 * @return The Standard AdditionalInitialMargin Instance
	 */

	public static final AdditionalInitialMargin Standard (
		final double addOnFixed,
		final java.util.Map<java.lang.String, java.lang.Double> productAddOnFactorMap)
	{
		try
		{
			return new AdditionalInitialMargin (
				org.drip.simm.common.ProductClassMultiplicativeScale.MS_RATESFX_DEFAULT,
				org.drip.simm.common.ProductClassMultiplicativeScale.MS_CREDIT_QUALIFYING_DEFAULT,
				org.drip.simm.common.ProductClassMultiplicativeScale.MS_EQUITY_DEFAULT,
				org.drip.simm.common.ProductClassMultiplicativeScale.MS_COMMODITY_DEFAULT,
				addOnFixed,
				productAddOnFactorMap
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * AdditionalInitialMargin Constructor
	 * 
	 * @param ratesFXMultiplicativeScale The RatesFX Multiplicative Scale
	 * @param creditMultiplicativeScale The Credit Multiplicative Scale
	 * @param equityMultiplicativeScale The Equity Multiplicative Scale
	 * @param commodityMultiplicativeScale The Commodity Multiplicative Scale
	 * @param addOnFixed The Fixed Add-On
	 * @param productAddOnFactorMap The Product Add-On Factor Map
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public AdditionalInitialMargin (
		final double ratesFXMultiplicativeScale,
		final double creditMultiplicativeScale,
		final double equityMultiplicativeScale,
		final double commodityMultiplicativeScale,
		final double addOnFixed,
		final java.util.Map<java.lang.String, java.lang.Double> productAddOnFactorMap)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (_ratesFXMultiplicativeScale =
				ratesFXMultiplicativeScale) || 1. > _ratesFXMultiplicativeScale ||
			!org.drip.quant.common.NumberUtil.IsValid (_creditMultiplicativeScale =
				creditMultiplicativeScale) || 1. > _creditMultiplicativeScale ||
			!org.drip.quant.common.NumberUtil.IsValid (_equityMultiplicativeScale =
				equityMultiplicativeScale) || 1. > _equityMultiplicativeScale ||
			!org.drip.quant.common.NumberUtil.IsValid (_commodityMultiplicativeScale =
				commodityMultiplicativeScale) || 1. > _commodityMultiplicativeScale ||
			!org.drip.quant.common.NumberUtil.IsValid (_addOnFixed = addOnFixed) || 0. > _addOnFixed)
		{
			throw new java.lang.Exception ("AdditionalInitialMargin Constructor => Invalid Inputs");
		}

		_productAddOnFactorMap = productAddOnFactorMap;
	}

	/**
	 * Retrieve the RatesFX Multiplicative Scale
	 * 
	 * @return The RatesFX Multiplicative Scale
	 */

	public double ratesFXMultiplicativeScale()
	{
		return _ratesFXMultiplicativeScale;
	}

	/**
	 * Retrieve the Credit Multiplicative Scale
	 * 
	 * @return The Credit Multiplicative Scale
	 */

	public double creditMultiplicativeScale()
	{
		return _creditMultiplicativeScale;
	}

	/**
	 * Retrieve the Equity Multiplicative Scale
	 * 
	 * @return The Equity Multiplicative Scale
	 */

	public double equityMultiplicativeScale()
	{
		return _equityMultiplicativeScale;
	}

	/**
	 * Retrieve the Commodity Multiplicative Scale
	 * 
	 * @return The Commodity Multiplicative Scale
	 */

	public double commodityMultiplicativeScale()
	{
		return _commodityMultiplicativeScale;
	}

	/**
	 * Retrieve the Fixed Add-On
	 * 
	 * @return The Fixed Add-On
	 */

	public double addOnFixed()
	{
		return _addOnFixed;
	}

	/**
	 * Retrieve the Product Add-On Factor Map
	 * 
	 * @return The Product Add-On Factor Map
	 */

	public java.util.Map<java.lang.String, java.lang.Double> productAddOnFactorMap()
	{
		return _productAddOnFactorMap;
	}

	/**
	 * Compute the Product Add On Estimate
	 * 
	 * @param productNotionalMap The Product Notional Map
	 * 
	 * @return The Product Add On Estimate
	 */

	public double productAddOn (
		final java.util.Map<java.lang.String, java.lang.Double> productNotionalMap)
	{
		if (null == productNotionalMap || 0 == productNotionalMap.size() ||
			null == _productAddOnFactorMap || 0 == _productAddOnFactorMap.size())
		{
			return 0.;
		}

		double productAddOn = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> productAddOnFactorEntry :
			_productAddOnFactorMap.entrySet())
		{
			java.lang.String productID = productAddOnFactorEntry.getKey();

			if (productNotionalMap.containsKey (productID))
			{
				productAddOn = productAddOn + productAddOnFactorEntry.getValue() * productNotionalMap.get
					(productID);
			}
		}

		return productAddOn;
	}

	/**
	 * Compute the Total IM Add On
	 * 
	 * @param productNotionalMap The Product Notional Map
	 * 
	 * @return The Total IM Add On
	 */

	public double total (
		final java.util.Map<java.lang.String, java.lang.Double> productNotionalMap)
	{
		return _addOnFixed + productAddOn (productNotionalMap);
	}
}
