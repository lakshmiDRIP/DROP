
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
 * <i>CalibratableComponent</i> abstract class provides implementation of Component's calibration interface.
 * It exposes stubs for getting/setting the component’s calibration code, generate calibrated measure values
 * from the market inputs, and compute micro-Jacobians (QuoteDF and PVDF micro-Jacks).
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Project</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product">Product</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/definition">Definition</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/FixedIncome">Fixed Income Analytics</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public abstract class CalibratableComponent extends org.drip.product.definition.Component {

	/**
	 * Return the primary code
	 * 
	 * @return Primary Code
	 */

	public abstract java.lang.String primaryCode();

	/**
	 * Set the component's primary code
	 * 
	 * @param strCode Primary Code
	 */

	public abstract void setPrimaryCode (
		final java.lang.String strCode);

	/**
	 * Get the component's secondary codes
	 * 
	 * @return Array of strings containing the secondary codes
	 */

	public java.lang.String[] secondaryCode()
	{
		return new java.lang.String[] {primaryCode()};
	}

	/**
	 * Generate the Product Specific Calibration Quote Set
	 * 
	 * @param aLSS Array of Latent State Specification
	 * 
	 * @return The Product Specific Calibration Quote Set
	 */

	public abstract org.drip.product.calib.ProductQuoteSet calibQuoteSet (
		final org.drip.state.representation.LatentStateSpecification[] aLSS);

	/**
	 * Generate a Map of the Calibration Measures
	 * 
	 * @param valParams Valuation Parameters
	 * @param pricerParams Pricer Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * 
	 * @return Map of the Calibration Measures
	 */

	public abstract org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> calibMeasures (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp);

	/**
	 * Generate the Calibratable Linearized Predictor/Response Constraint Weights for the Non-merged Funding
	 * 	Curve Discount Factor Latent State from the Component's Cash Flows. The Constraints here typically
	 *  correspond to Date/Cash Flow pairs and the corresponding leading PV.
	 * 
	 * @param valParams Valuation Parameters
	 * @param pricerParams Pricer Parameters
	 * @param csqs Component Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param pqs Product Quote Set
	 * 
	 * @return The Calibratable Linearized Predictor/Response Constraints (Date/Cash Flow pairs and the
	 * 	corresponding PV)
	 */

	public abstract org.drip.state.estimator.PredictorResponseWeightConstraint fundingPRWC (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final org.drip.product.calib.ProductQuoteSet pqs);

	/**
	 * Generate the Calibratable Linearized Predictor/Response Constraint Weights for the Non-merged Forward
	 *  Factor Latent State from the Component's Cash Flows. The Constraints here typically correspond to
	 *  Date/Cash Flow pairs and the corresponding leading PV.
	 * 
	 * @param valParams Valuation Parameters
	 * @param pricerParams Pricer Parameters
	 * @param csqs Component Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param pqs Product Quote Set
	 * 
	 * @return The Calibratable Linearized Predictor/Response Constraints (Date/Cash Flow pairs and the
	 * 	corresponding PV)
	 */

	public abstract org.drip.state.estimator.PredictorResponseWeightConstraint forwardPRWC (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final org.drip.product.calib.ProductQuoteSet pqs);

	/**
	 * Generate the Calibratable Linearized Predictor/Response Constraint Weights for the merged Funding and
	 *  Forward Latent States from the Component's Cash Flows. The Constraints here typically correspond to
	 *  Date/Cash Flow pairs and the corresponding leading PV.
	 * 
	 * @param valParams Valuation Parameters
	 * @param pricerParams Pricer Parameters
	 * @param csqs Component Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param pqs Product Quote Set
	 * 
	 * @return The Calibratable Linearized Predictor/Response Constraints (Date/Cash Flow pairs and the
	 * 	corresponding PV)
	 */

	public abstract org.drip.state.estimator.PredictorResponseWeightConstraint fundingForwardPRWC (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final org.drip.product.calib.ProductQuoteSet pqs);

	/**
	 * Generate the Calibratable Linearized Predictor/Response Constraint Weights for the Non-merged FX Curve
	 *  FX Forward Latent State from the Component's Cash Flows. The Constraints here typically correspond to
	 *  Date/Cash Flow pairs and the corresponding leading PV.
	 * 
	 * @param valParams Valuation Parameters
	 * @param pricerParams Pricer Parameters
	 * @param csqs Component Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param pqs Product Quote Set
	 * 
	 * @return The Calibratable Linearized Predictor/Response Constraints (Date/Cash Flow pairs and the
	 * 	corresponding FX Forward)
	 */

	public abstract org.drip.state.estimator.PredictorResponseWeightConstraint fxPRWC (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final org.drip.product.calib.ProductQuoteSet pqs);

	/**
	 * Generate the Calibratable Linearized Predictor/Response Constraint Weights for the Non-merged Govvie
	 *  Curve FX Forward Latent State from the Component's Cash Flows. The Constraints here typically
	 *  correspond to Date/Cash Flow pairs and the corresponding leading PV.
	 * 
	 * @param valParams Valuation Parameters
	 * @param pricerParams Pricer Parameters
	 * @param csqs Component Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param pqs Product Quote Set
	 * 
	 * @return The Calibratable Linearized Predictor/Response Constraints (Date/Cash Flow pairs and the
	 * 	corresponding Govvie Forward)
	 */

	public abstract org.drip.state.estimator.PredictorResponseWeightConstraint govviePRWC (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final org.drip.product.calib.ProductQuoteSet pqs);

	/**
	 * Generate the Calibratable Linearized Predictor/Response Constraint Weights for the Non-merged
	 *  Volatility Latent State from the Component's Cash Flows. The Constraints here typically correspond to
	 *  Date/Cash Flow pairs and the corresponding leading PV.
	 * 
	 * @param valParams Valuation Parameters
	 * @param pricerParams Pricer Parameters
	 * @param csqs Component Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param pqs Product Quote Set
	 * 
	 * @return The Calibratable Linearized Predictor/Response Constraints (Date/Cash Flow pairs and the
	 * 	corresponding PV)
	 */

	public abstract org.drip.state.estimator.PredictorResponseWeightConstraint volatilityPRWC (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final org.drip.product.calib.ProductQuoteSet pqs);

	/**
	 * Compute the Jacobian of the Dirty PV to the Calibrated Input Manifest Measures
	 * 
	 * @param valParams Valuation Parameters
	 * @param pricerParams Pricer Parameters
	 * @param csqs Component Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * 
	 * @return The micro-Jacobian
	 */

	public abstract org.drip.quant.calculus.WengertJacobian jackDDirtyPVDManifestMeasure (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp);

	/**
	 * Compute the micro-Jacobian of the given measure to the DF
	 * 
	 * @param strMainfestMeasure Manifest Measure Name
	 * @param valParams Valuation Parameters
	 * @param pricerParams Pricer Parameters
	 * @param csqs Component Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * 
	 * @return The micro-Jacobian
	 */

	public abstract org.drip.quant.calculus.WengertJacobian manifestMeasureDFMicroJack (
		final java.lang.String strMainfestMeasure,
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp);

	/**
	 * Generate the Calibratable Linearized Predictor/Response Constraint Weights for the Component from the
	 *  Market Inputs. The Constraints here typically correspond to Date/Cash Flow pairs and the
	 *  corresponding leading PV.
	 * 
	 * @param valParams Valuation Parameters
	 * @param pricerParams Pricer Parameters
	 * @param csqs Component Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param pqs The Product Calibration Quote Set
	 * 
	 * @return The Calibratable Linearized Predictor/Response Constraints (Date/Cash Flow pairs and the
	 * 	corresponding PV)
	 */

	public org.drip.state.estimator.PredictorResponseWeightConstraint calibPRWC (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final org.drip.product.calib.ProductQuoteSet pqs)
	{
		if (null == valParams || null == pqs) return null;

		if (pqs.containsLatentStateType
			(org.drip.analytics.definition.LatentStateStatic.LATENT_STATE_FUNDING) &&
				pqs.containsLatentStateQuantificationMetric
					(org.drip.analytics.definition.LatentStateStatic.DISCOUNT_QM_DISCOUNT_FACTOR) &&
						pqs.containsLatentStateType
							(org.drip.analytics.definition.LatentStateStatic.LATENT_STATE_FORWARD) &&
								pqs.containsLatentStateQuantificationMetric
									(org.drip.analytics.definition.LatentStateStatic.FORWARD_QM_FORWARD_RATE))
			return fundingForwardPRWC (valParams, pricerParams, csqs, vcp, pqs);

		if (pqs.containsLatentStateType
			(org.drip.analytics.definition.LatentStateStatic.LATENT_STATE_FUNDING) &&
				pqs.containsLatentStateQuantificationMetric
					(org.drip.analytics.definition.LatentStateStatic.DISCOUNT_QM_DISCOUNT_FACTOR))
			return fundingPRWC (valParams, pricerParams, csqs, vcp, pqs);

		if (pqs.containsLatentStateType
			(org.drip.analytics.definition.LatentStateStatic.LATENT_STATE_FORWARD) &&
				pqs.containsLatentStateQuantificationMetric
					(org.drip.analytics.definition.LatentStateStatic.FORWARD_QM_FORWARD_RATE))
			return forwardPRWC (valParams, pricerParams, csqs, vcp, pqs);

		if (pqs.containsLatentStateType
			(org.drip.analytics.definition.LatentStateStatic.LATENT_STATE_FX) &&
				pqs.containsLatentStateQuantificationMetric
					(org.drip.analytics.definition.LatentStateStatic.FX_QM_FORWARD_OUTRIGHT))
			return fxPRWC (valParams, pricerParams, csqs, vcp, pqs);

		if (pqs.containsLatentStateType
			(org.drip.analytics.definition.LatentStateStatic.LATENT_STATE_GOVVIE) &&
				pqs.containsLatentStateQuantificationMetric
					(org.drip.analytics.definition.LatentStateStatic.GOVVIE_QM_YIELD))
			return govviePRWC (valParams, pricerParams, csqs, vcp, pqs);

		if (pqs.containsLatentStateType
			(org.drip.analytics.definition.LatentStateStatic.LATENT_STATE_VOLATILITY))
			return volatilityPRWC (valParams, pricerParams, csqs, vcp, pqs);

		return null;
	}
}
