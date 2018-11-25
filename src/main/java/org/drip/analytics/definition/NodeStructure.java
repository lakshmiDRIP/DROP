
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
 * <i>NodeStructure</i> exposes the stub that implements the latent state's Node Structure (e.g., a
 * Deterministic Term Structure) - by Construction, this is expected to be non-local.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics">Analytics</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/definition">Definition</a></li>
 *  </ul>
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
