
package org.drip.product.calib;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
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
 * ProductQuoteSet implements the Calibratable type-free Product Quote Shell. The derived calibration sets
 * 	provide custom accessors.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class ProductQuoteSet {
	private org.drip.state.representation.LatentStateSpecification[] _aLSS = null;

	private org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> _mapQuote = new
		org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>();

	/**
	 * Product Quote Set Constructor
	 * 
	 * @param aLSS Array of Latent State Specifications
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public ProductQuoteSet (
		final org.drip.state.representation.LatentStateSpecification[] aLSS)
		throws java.lang.Exception
	{
		if (null == (_aLSS = aLSS)) throw new java.lang.Exception ("ProductQuoteSet ctr: Invalid Inputs");

		int iNumLSS = _aLSS.length;

		if (0 == iNumLSS) throw new java.lang.Exception ("ProductQuoteSet ctr: Invalid Inputs");

		for (int i = 0; i < iNumLSS; ++i) {
			if (null == _aLSS[i]) throw new java.lang.Exception ("ProductQuoteSet ctr: Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Array of Latent State Specification
	 * 
	 * @return The Array of Latent State Specification
	 */

	public org.drip.state.representation.LatentStateSpecification[] lss()
	{
		return _aLSS;
	}

	/**
	 * Indicate if the requested Latent State Type is contained in the Quote Set
	 * 
	 * @param strLatentStateType The Requested Latent State Type
	 * 
	 * @return TRUE - The requested Latent State Type is contained in the Quote Set
	 */

	public boolean containsLatentStateType (
		final java.lang.String strLatentStateType)
	{
		if (null == strLatentStateType || strLatentStateType.isEmpty()) return false;

		for (org.drip.state.representation.LatentStateSpecification lss : _aLSS) {
			if (lss.latentState().equalsIgnoreCase (strLatentStateType)) return true;
		}

		return false;
	}

	/**
	 * Retrieve the Forward Latent State Label, if it exists
	 * 
	 * @return The Forward Latent State Label
	 */

	public org.drip.state.identifier.ForwardLabel forwardLabel()
	{
		for (org.drip.state.representation.LatentStateSpecification lss : _aLSS) {
			if (lss.latentState().equalsIgnoreCase
				(org.drip.analytics.definition.LatentStateStatic.LATENT_STATE_FORWARD))
				return (org.drip.state.identifier.ForwardLabel) lss.label();
		}

		return null;
	}

	/**
	 * Retrieve the Funding Latent State Label, if it exists
	 * 
	 * @return The Funding Latent State Label
	 */

	public org.drip.state.identifier.FundingLabel fundingLabel()
	{
		for (org.drip.state.representation.LatentStateSpecification lss : _aLSS) {
			if (lss.latentState().equalsIgnoreCase
				(org.drip.analytics.definition.LatentStateStatic.LATENT_STATE_FUNDING))
				return (org.drip.state.identifier.FundingLabel) lss.label();
		}

		return null;
	}

	/**
	 * Retrieve the FX Latent State Label, if it exists
	 * 
	 * @return The FX Latent State Label
	 */

	public org.drip.state.identifier.FXLabel fxLabel()
	{
		for (org.drip.state.representation.LatentStateSpecification lss : _aLSS) {
			if (lss.latentState().equalsIgnoreCase
				(org.drip.analytics.definition.LatentStateStatic.LATENT_STATE_FX))
				return (org.drip.state.identifier.FXLabel) lss.label();
		}

		return null;
	}

	/**
	 * Retrieve the Govvie Latent State Label, if it exists
	 * 
	 * @return The Govvie Latent State Label
	 */

	public org.drip.state.identifier.GovvieLabel govvieLabel()
	{
		for (org.drip.state.representation.LatentStateSpecification lss : _aLSS) {
			if (lss.latentState().equalsIgnoreCase
				(org.drip.analytics.definition.LatentStateStatic.LATENT_STATE_GOVVIE))
				return (org.drip.state.identifier.GovvieLabel) lss.label();
		}

		return null;
	}

	/**
	 * Retrieve the Volatility Latent State Label, if it exists
	 * 
	 * @return The Volatility Latent State Label
	 */

	public org.drip.state.identifier.VolatilityLabel volatilityLabel()
	{
		for (org.drip.state.representation.LatentStateSpecification lss : _aLSS) {
			if (lss.latentState().equalsIgnoreCase
				(org.drip.analytics.definition.LatentStateStatic.LATENT_STATE_VOLATILITY))
				return (org.drip.state.identifier.VolatilityLabel) lss.label();
		}

		return null;
	}

	/**
	 * Indicate if the requested Latent State Quantification Metric is contained in the Quote Set
	 * 
	 * @param strLatentStateQuantificationMetric The Requested Latent State Quantification Metric
	 * 
	 * @return TRUE - The requested Latent State Quantification Metric is contained in the Quote Set
	 */

	public boolean containsLatentStateQuantificationMetric (
		final java.lang.String strLatentStateQuantificationMetric)
	{
		if (null == strLatentStateQuantificationMetric || strLatentStateQuantificationMetric.isEmpty())
			return false;

		for (org.drip.state.representation.LatentStateSpecification lss : _aLSS) {
			if (lss.latentStateQuantificationMetric().equalsIgnoreCase (strLatentStateQuantificationMetric))
				return true;
		}

		return false;
	}

	/**
	 * Indicate if the Specified External Latent State Specification is contained in the Array
	 * 
	 * @param strLatentState The Latent State
	 * @param strLatentStateQuantificationMetric The Latent State Quantification Metric
	 * @param label The Specific Latent State Label
	 * 
	 * @return TRUE - The Specified External Latent State Specification is contained in the Array
	 */

	public boolean contains (
		final java.lang.String strLatentState,
		final java.lang.String strLatentStateQuantificationMetric,
		final org.drip.state.identifier.LatentStateLabel label)
	{
		org.drip.state.representation.LatentStateSpecification lssExternal = null;

		try {
			lssExternal = new org.drip.state.representation.LatentStateSpecification (strLatentState,
				strLatentStateQuantificationMetric, label);
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return false;
		}

		for (org.drip.state.representation.LatentStateSpecification lss : _aLSS) {
			if (lss.match (lssExternal)) return true;
		}

		return false;
	}

	/**
	 * Set the named Manifest Measure Quote Value
	 * 
	 * @param strManifestMeasure The Manifest Measure
	 * @param dblManifestMeasureQuote The Quote Value
	 * 
	 * @return TRUE - The Manifest Measure Quote Value successfully set
	 */

	public boolean set (
		final java.lang.String strManifestMeasure,
		final double dblManifestMeasureQuote)
	{
		if (null == strManifestMeasure || strManifestMeasure.isEmpty() ||
			!org.drip.quant.common.NumberUtil.IsValid (dblManifestMeasureQuote))
			return false;

		_mapQuote.put (strManifestMeasure, dblManifestMeasureQuote);

		return true;
	}

	/**
	 * Retrieve the Quote corresponding to the Specified Manifest Measure
	 * 
	 * @param strManifestMeasure The Manifest Measure
	 * 
	 * @return The Corresponding Quote
	 * 
	 * @throws java.lang.Exception Thrown if the Quote cannot be extracted
	 */

	public double get (
		final java.lang.String strManifestMeasure)
		throws java.lang.Exception
	{
		if (null == strManifestMeasure || strManifestMeasure.isEmpty() || !_mapQuote.containsKey
			(strManifestMeasure))
			throw new java.lang.Exception ("ProductQuoteSet::get - Invalid Inputs");

		return _mapQuote.get (strManifestMeasure);
	}

	/**
	 * Indicate if the Manifest Measure is available
	 * 
	 * @param strManifestMeasure The Manifest Measure
	 * 
	 * @return TRUE - The Manifest Measure is Available
	 */

	public boolean contains (
		final java.lang.String strManifestMeasure)
	{
		return null != strManifestMeasure && !strManifestMeasure.isEmpty() && _mapQuote.containsKey
			(strManifestMeasure);
	}

	/**
	 * Return the Set of Fields Available
	 * 
	 * @return The Set of Fields Available
	 */

	public java.util.Set<java.lang.String> fields()
	{
		return _mapQuote.keySet();
	}
}
