
package org.drip.service.scenario;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
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
 * EOSMetricsReplicator generates the EOS Metrics for Bonds with Embedded Option Schedules.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class EOSMetricsReplicator
{
	private int _iNumPath = -1;
	private double _dblPrice = java.lang.Double.NaN;
	private org.drip.product.credit.BondComponent _bond = null;
	private org.drip.measure.process.DiffusionEvolver _de = null;
	private org.drip.state.sequence.GovvieBuilderSettings _gbs = null;
	private org.drip.param.valuation.ValuationParams _valParams = null;
	private org.drip.param.market.CurveSurfaceQuoteContainer _csqc = null;

	/**
	 * Standard Static EOSMetricsReplicator Creator
	 * 
	 * @param bond Bond Instance
	 * @param valParams Valuation Parameters
	 * @param csqc Market Parameters
	 * @param gbs Govvie Builder Settings
	 * @param dblLogNormalVolatility Long Normal Treasury Forward Volatility
	 * @param dblPrice Market Price
	 * 
	 * @return EOSMetricsReplicator Instance
	 */

	public static final EOSMetricsReplicator Standard (
		final org.drip.product.credit.BondComponent bond,
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.state.sequence.GovvieBuilderSettings gbs,
		final double dblLogNormalVolatility,
		final double dblPrice)
	{
		try {
			return new EOSMetricsReplicator (
				bond,
				valParams,
				csqc,
				gbs,
				new org.drip.measure.process.DiffusionEvolver (
					org.drip.measure.dynamics.DiffusionEvaluatorLogarithmic.Standard (
						0.,
						dblLogNormalVolatility
					)
				),
				50,
				dblPrice
			);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * EOSMetricsReplicator Constructor
	 * 
	 * @param bond Bond Instance
	 * @param valParams Valuation Parameters
	 * @param csqc Market Parameters
	 * @param gbs Govvie Builder Settings
	 * @param de Diffusion Evolver
	 * @param iNumPath Number of Simulation Paths
	 * @param dblPrice Market Price
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid/Inconsistent
	 */

	public EOSMetricsReplicator (
		final org.drip.product.credit.BondComponent bond,
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.state.sequence.GovvieBuilderSettings gbs,
		final org.drip.measure.process.DiffusionEvolver de,
		final int iNumPath,
		final double dblPrice)
		throws java.lang.Exception
	{
		if (null == (_bond = bond) || (!_bond.callable() && _bond.putable()) ||
			null == (_valParams = valParams) ||
			null == (_csqc = csqc) ||
			null == (_gbs = gbs) ||
			null == (_de = de) ||
			0 >= (_iNumPath = iNumPath) ||
			!org.drip.quant.common.NumberUtil.IsValid (_dblPrice = dblPrice))
			throw new java.lang.Exception ("EOSMetricsReplicator Constructor => Invalid Inputs");
	}

	/**
	 * Retrieve the Underlying Bond
	 * 
	 * @return The Underlying Bond
	 */

	public org.drip.product.credit.BondComponent bond()
	{
		return _bond;
	}

	/**
	 * Retrieve the Valuation Parameters
	 * 
	 * @return The Valuation Parameters
	 */

	public org.drip.param.valuation.ValuationParams valuationParameters()
	{
		return _valParams;
	}

	/**
	 * Retrieve the Market Parameters
	 * 
	 * @return The Market Parameters
	 */

	public org.drip.param.market.CurveSurfaceQuoteContainer marketParameters()
	{
		return _csqc;
	}

	/**
	 * Retrieve the Price
	 * 
	 * @return The Price
	 */

	public double price()
	{
		return _dblPrice;
	}

	/**
	 * Retrieve the Diffusion Evolver
	 * 
	 * @return The Diffusion Evolver
	 */

	public org.drip.measure.process.DiffusionEvolver diffusionEvolver()
	{
		return _de;
	}

	/**
	 * Retrieve the Govvie Builder Settings
	 * 
	 * @return The Govvie Builder Settings
	 */

	public org.drip.state.sequence.GovvieBuilderSettings govvieBuilderSetting()
	{
		return _gbs;
	}

	/**
	 * Retrieve the Number of Simulation Paths
	 * 
	 * @return The Number of Simulation Paths
	 */

	public int numPath()
	{
		return _iNumPath;
	}

	/**
	 * Generate an Instance of a Replication Run
	 * 
	 * @return Instance of a Replication Run
	 */

	public org.drip.analytics.output.BondEOSMetrics generateRun()
	{
		return _bond.callable() ? _bond.callMetrics (
			_valParams,
			_csqc,
			null,
			_dblPrice,
			_gbs,
			_de,
			_iNumPath
		) :  _bond.putMetrics (
			_valParams,
			_csqc,
			null,
			_dblPrice,
			_gbs,
			_de,
			_iNumPath
		);
	}
}
