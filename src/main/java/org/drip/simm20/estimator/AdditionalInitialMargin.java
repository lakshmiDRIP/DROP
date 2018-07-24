
package org.drip.simm20.estimator;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * 
 *  This file is part of DRIP, a free-software/open-source library for buy/side financial/trading model
 *  	libraries targeting analysts and developers
 *  	https://lakshmidrip.github.io/DRIP/
 *  
 *  DRIP is composed of four main libraries:
 *  
 *  - DRIP Fixed Income - https://lakshmidrip.github.io/DRIP-Fixed-Income/
 *  - DRIP Asset Allocation - https://lakshmidrip.github.io/DRIP-Asset-Allocation/
 *  - DRIP Numerical Optimizer - https://lakshmidrip.github.io/DRIP-Numerical-Optimizer/
 *  - DRIP Statistical Learning - https://lakshmidrip.github.io/DRIP-Statistical-Learning/
 * 
 *  - DRIP Fixed Income: Library for Instrument/Trading Conventions, Treasury Futures/Options,
 *  	Funding/Forward/Overnight Curves, Multi-Curve Construction/Valuation, Collateral Valuation and XVA
 *  	Metric Generation, Calibration and Hedge Attributions, Statistical Curve Construction, Bond RV
 *  	Metrics, Stochastic Evolution and Option Pricing, Interest Rate Dynamics and Option Pricing, LMM
 *  	Extensions/Calibrations/Greeks, Algorithmic Differentiation, and Asset Backed Models and Analytics.
 * 
 *  - DRIP Asset Allocation: Library for model libraries for MPT framework, Black Litterman Strategy
 *  	Incorporator, Holdings Constraint, and Transaction Costs.
 * 
 *  - DRIP Numerical Optimizer: Library for Numerical Optimization and Spline Functionality.
 * 
 *  - DRIP Statistical Learning: Library for Statistical Evaluation and Machine Learning.
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
 * AdditionalInitialMargin holds the Additional Initial Margin along with the Product Specific Add-On
 *  Components. The References are:
 *  
 *  - Andersen, L. B. G., M. Pykhtin, and A. Sokol (2017): Credit Exposure in the Presence of Initial Margin,
 *  	https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2806156, eSSRN.
 *  
 *  - Albanese, C., S. Caenazzo, and O. Frankel (2017): Regression Sensitivities for Initial Margin
 *  	Calculations, https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2763488, eSSRN.
 *  
 *  - Anfuso, F., D. Aziz, P. Giltinan, and K. Loukopoulus (2017): A Sound Modeling and Back-testing
 *  	Framework for Forecasting Initial Margin Requirements,
 *  	https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2716279, eSSRN.
 *  
 *  - Caspers, P., P. Giltinan, R. Lichters, and N. Nowaczyk (2017): Forecasting Initial Margin Requirements
 *  	- A Model Evaluation https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2911167, eSSRN.
 *  
 *  - International Swaps and Derivatives Association (2017): SIMM v2.0 Methodology,
 *  	https://www.isda.org/a/oFiDE/isda-simm-v2.pdf.
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
				org.drip.simm20.estimator.ProductClassMultiplicativeScale.MS_RATESFX_DEFAULT,
				org.drip.simm20.estimator.ProductClassMultiplicativeScale.MS_CREDIT_DEFAULT,
				org.drip.simm20.estimator.ProductClassMultiplicativeScale.MS_EQUITY_DEFAULT,
				org.drip.simm20.estimator.ProductClassMultiplicativeScale.MS_COMMODITY_DEFAULT,
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
