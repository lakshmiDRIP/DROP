
package org.drip.service.scenario;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
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
 * <i>EOSMetricsReplicator</i> generates the EOS Metrics for Bonds with Embedded Option Schedules.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AlgorithmSupportLibrary.md">Algorithm Support Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service">Service</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/scenario">Scenario</a></li>
 *  </ul>
 * <br><br>
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
			!org.drip.numerical.common.NumberUtil.IsValid (_dblPrice = dblPrice))
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
