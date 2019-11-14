
package org.drip.service.scenario;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting analytics/risk, transaction cost analytics,
 *  	asset liability management analytics, capital, exposure, and margin analytics, valuation adjustment
 *  	analytics, and portfolio construction analytics within and across fixed income, credit, commodity,
 *  	equity, FX, and structured products. It also includes auxiliary libraries for algorithm support,
 *  	numerical analysis, numerical optimization, spline builder, model validation, statistical learning,
 *  	and computational support.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three modules:
 *  
 *  - DROP Product Core - https://lakshmidrip.github.io/DROP-Product-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Computational Core - https://lakshmidrip.github.io/DROP-Computational-Core/
 * 
 * 	DROP Product Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Loan Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 *  - Asset Liability Management Analytics
 * 	- Capital Estimation Analytics
 * 	- Exposure Analytics
 * 	- Margin Analytics
 * 	- XVA Analytics
 * 
 * 	DROP Computational Core implements libraries for the following:
 * 	- Algorithm Support
 * 	- Computation Support
 * 	- Function Analysis
 *  - Model Validation
 * 	- Numerical Analysis
 * 	- Numerical Optimizer
 * 	- Spline Builder
 *  - Statistical Learning
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
 * 	- Repo Layout Taxonomy     => https://github.com/lakshmiDRIP/DROP/blob/master/Taxonomy.md
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
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationSupportLibrary.md">Computation Support</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/README.md">Environment, Product/Definition Containers, and Scenario/State Manipulation APIs</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/scenario/README.md">Custom Scenario Service Metric Generator</a></li>
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
