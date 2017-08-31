
package org.drip.analytics.definition;

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
 * NodeStructure exposes the stub that implements the latent state's Node Structure (e.g., a Deterministic
 *  Term Structure) - by Construction, this is expected to be non-local.
 *
 * @author Lakshmi Krishnamurthy
 */

public abstract class NodeStructure implements org.drip.analytics.definition.Curve {
	protected java.lang.String _strName = "";
	protected java.lang.String _strCurrency = "";
	protected int _iEpochDate = java.lang.Integer.MIN_VALUE;
	protected org.drip.state.identifier.LatentStateLabel _label = null;

	protected NodeStructure (
		final int iEpochDate,
		final org.drip.state.identifier.LatentStateLabel label,
		final java.lang.String strCurrency)
		throws java.lang.Exception
	{
		if (null == (_label = label) || null == (_strCurrency = strCurrency) || _strCurrency.isEmpty())
			throw new java.lang.Exception ("NodeStructure ctr: Invalid Inputs");

		_iEpochDate = iEpochDate;
	}

	@Override public org.drip.state.identifier.LatentStateLabel label()
	{
		return _label;
	}

	@Override public java.lang.String currency()
	{
		return _strCurrency;
	}

	@Override public org.drip.analytics.date.JulianDate epoch()
	{
		try {
			return new org.drip.analytics.date.JulianDate (_iEpochDate);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override public boolean setCCIS (
		final org.drip.analytics.input.CurveConstructionInputSet ccis)
	{
		return false;
	}

	@Override public org.drip.product.definition.CalibratableComponent[] calibComp()
	{
		return null;
	}

	@Override public org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> manifestMeasure (
		final java.lang.String strInstr)
	{
		return null;
	}

	@Override public org.drip.state.representation.LatentState parallelShiftManifestMeasure (
		final java.lang.String strManifestMeasure,
		final double dblShift)
	{
		return null;
	}

	@Override public org.drip.state.representation.LatentState shiftManifestMeasure (
		final int iSpanIndex,
		final java.lang.String strManifestMeasure,
		final double dblShift)
	{
		return null;
	}

	@Override public org.drip.state.representation.LatentState customTweakManifestMeasure (
		final java.lang.String strManifestMeasure,
		final org.drip.param.definition.ManifestMeasureTweak rvtp)
	{
		return null;
	}

	@Override public org.drip.state.representation.LatentState parallelShiftQuantificationMetric (
		final double dblShift)
	{
		return null;
	}

	@Override public org.drip.state.representation.LatentState customTweakQuantificationMetric (
		final org.drip.param.definition.ManifestMeasureTweak rvtp)
	{
		return null;
	}

	/**
	 * Get the Market Node at the given Predictor Ordinate
	 * 
	 * @param iPredictorOrdinate The Predictor Ordinate
	 * 
	 * @return The Node evaluated from the Term Structure
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public abstract double node (
		final int iPredictorOrdinate)
		throws java.lang.Exception;

	/**
	 * Get the Market Node Derivative at the given Predictor Ordinate
	 * 
	 * @param iPredictorOrdinate The Predictor Ordinate
	 * @param iOrder Order of the Derivative
	 * 
	 * @return The Node Derivative evaluated from the Term Structure
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public abstract double nodeDerivative (
		final int iPredictorOrdinate,
		final int iOrder)
		throws java.lang.Exception;

	/**
	 * Get the Market Node at the given Maturity
	 * 
	 * @param dt The Julian Maturity Date
	 * 
	 * @return The Node evaluated from the Term Structure
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double node (
		final org.drip.analytics.date.JulianDate dt)
		throws java.lang.Exception
	{
		if (null == dt) throw new java.lang.Exception ("NodeStructure::node => Invalid Inputs");

		return node (dt.julian());
	}

	/**
	 * Get the Market Node at the given Maturity
	 * 
	 * @param strTenor The Maturity Tenor
	 * 
	 * @return The Node evaluated from the Term Structure
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double node (
		final java.lang.String strTenor)
		throws java.lang.Exception
	{
		if (null == strTenor || strTenor.isEmpty())
			throw new java.lang.Exception ("NodeStructure::node => Invalid Inputs");

		return node (epoch().addTenor (strTenor).julian());
	}

	/**
	 * Get the Market Node Derivative at the given Maturity
	 * 
	 * @param dt The Julian Maturity Date
	 * @param iOrder Order of the Derivative
	 * 
	 * @return The Node Derivative evaluated from the Term Structure
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double nodeDerivative (
		final org.drip.analytics.date.JulianDate dt,
		final int iOrder)
		throws java.lang.Exception
	{
		if (null == dt) throw new java.lang.Exception ("TermStructure::nodeDerivative => Invalid Inputs");

		return nodeDerivative (dt.julian(), iOrder);
	}

	/**
	 * Get the Market Node Derivative at the given Maturity
	 * 
	 * @param strTenor The Maturity Tenor
	 * @param iOrder Order of the Derivative
	 * 
	 * @return The Node Derivative evaluated from the Term Structure
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double nodeDerivative (
		final java.lang.String strTenor,
		final int iOrder)
		throws java.lang.Exception
	{
		if (null == strTenor || strTenor.isEmpty())
			throw new java.lang.Exception ("TermStructure::nodeDerivative => Invalid Inputs");

		return nodeDerivative (epoch().addTenor (strTenor).julian(), iOrder);
	}
}
