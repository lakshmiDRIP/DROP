
package org.drip.service.scenario;

import org.drip.analytics.output.BondEOSMetrics;
import org.drip.measure.dynamics.DiffusionEvaluatorLogarithmic;
import org.drip.measure.realization.DiffusionEvolver;
import org.drip.numerical.common.NumberUtil;
import org.drip.param.market.CurveSurfaceQuoteContainer;
import org.drip.param.valuation.ValuationParams;
import org.drip.product.credit.BondComponent;
import org.drip.state.sequence.GovvieBuilderSettings;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2025 Lakshmi Krishnamurthy
 * Copyright (C) 2024 Lakshmi Krishnamurthy
 * Copyright (C) 2023 Lakshmi Krishnamurthy
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
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
 *  	graph builder/navigator, and computational support.
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
 *  - Graph Algorithm
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
 * <i>EOSMetricsReplicator</i> generates the EOS Metrics for Bonds with Embedded Option Schedules. It
 * 	provides the following Functionality:
 *
 *  <ul>
 * 		<li>Standard Static <i>EOSMetricsReplicator</i> Creator</li>
 * 		<li><i>EOSMetricsReplicator</i> Constructor</li>
 * 		<li>Retrieve the Underlying Bond</li>
 * 		<li>Retrieve the Valuation Parameters</li>
 * 		<li>Retrieve the Market Parameters</li>
 * 		<li>Retrieve the Price</li>
 * 		<li>Retrieve the Diffusion Evolver</li>
 * 		<li>Retrieve the Govvie Builder Settings</li>
 * 		<li>Retrieve the Number of Simulation Paths</li>
 * 		<li>Generate an Instance of a Replication Run</li>
 *  </ul>
 *
 *	<br>
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationSupportLibrary.md">Computation Support</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/README.md">Environment, Product/Definition Containers, and Scenario/State Manipulation APIs</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/scenario/README.md">Custom Scenario Service Metric Generator</a></td></tr>
 *  </table>
 *	<br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class EOSMetricsReplicator
{
	private int _pathCount = -1;
	private double _price = Double.NaN;
	private BondComponent _bondComponent = null;
	private ValuationParams _valuationParams = null;
	private DiffusionEvolver _diffusionEvolver = null;
	private GovvieBuilderSettings _govvieBuilderSettings = null;
	private CurveSurfaceQuoteContainer _curveSurfaceQuoteContainer = null;

	/**
	 * Standard Static <i>EOSMetricsReplicator</i> Creator
	 * 
	 * @param bondComponent Bond Instance
	 * @param valuationParams Valuation Parameters
	 * @param curveSurfaceQuoteContainer Market Parameters
	 * @param govvieBuilderSettings Govvie Builder Settings
	 * @param logNormalVolatility Long Normal Treasury Forward Volatility
	 * @param price Market Price
	 * 
	 * @return <i>EOSMetricsReplicator</i> Instance
	 */

	public static final EOSMetricsReplicator Standard (
		final BondComponent bondComponent,
		final ValuationParams valuationParams,
		final CurveSurfaceQuoteContainer curveSurfaceQuoteContainer,
		final GovvieBuilderSettings govvieBuilderSettings,
		final double logNormalVolatility,
		final double price)
	{
		try {
			return new EOSMetricsReplicator (
				bondComponent,
				valuationParams,
				curveSurfaceQuoteContainer,
				govvieBuilderSettings,
				new DiffusionEvolver (DiffusionEvaluatorLogarithmic.Standard (0., logNormalVolatility)),
				50,
				price
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * <i>EOSMetricsReplicator</i> Constructor
	 * 
	 * @param bondComponent Bond Instance
	 * @param valuationParams Valuation Parameters
	 * @param curveSurfaceQuoteContainer Market Parameters
	 * @param govvieBuilderSettings Govvie Builder Settings
	 * @param diffusionEvolver Diffusion Evolver
	 * @param pathCount Number of Simulation Paths
	 * @param price Market Price
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid/Inconsistent
	 */

	public EOSMetricsReplicator (
		final BondComponent bondComponent,
		final ValuationParams valuationParams,
		final CurveSurfaceQuoteContainer curveSurfaceQuoteContainer,
		final GovvieBuilderSettings govvieBuilderSettings,
		final DiffusionEvolver diffusionEvolver,
		final int pathCount,
		final double price)
		throws Exception
	{
		if (null == (_bondComponent = bondComponent) ||
			(!_bondComponent.callable() && _bondComponent.putable()) ||
			null == (_valuationParams = valuationParams) ||
			null == (_curveSurfaceQuoteContainer = curveSurfaceQuoteContainer) ||
			null == (_govvieBuilderSettings = govvieBuilderSettings) ||
			null == (_diffusionEvolver = diffusionEvolver) ||
			0 >= (_pathCount = pathCount) ||
			!NumberUtil.IsValid (_price = price))
		{
			throw new Exception ("EOSMetricsReplicator Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Underlying Bond
	 * 
	 * @return The Underlying Bond
	 */

	public BondComponent bond()
	{
		return _bondComponent;
	}

	/**
	 * Retrieve the Valuation Parameters
	 * 
	 * @return The Valuation Parameters
	 */

	public ValuationParams valuationParameters()
	{
		return _valuationParams;
	}

	/**
	 * Retrieve the Market Parameters
	 * 
	 * @return The Market Parameters
	 */

	public CurveSurfaceQuoteContainer marketParameters()
	{
		return _curveSurfaceQuoteContainer;
	}

	/**
	 * Retrieve the Price
	 * 
	 * @return The Price
	 */

	public double price()
	{
		return _price;
	}

	/**
	 * Retrieve the Diffusion Evolver
	 * 
	 * @return The Diffusion Evolver
	 */

	public DiffusionEvolver diffusionEvolver()
	{
		return _diffusionEvolver;
	}

	/**
	 * Retrieve the Govvie Builder Settings
	 * 
	 * @return The Govvie Builder Settings
	 */

	public GovvieBuilderSettings govvieBuilderSetting()
	{
		return _govvieBuilderSettings;
	}

	/**
	 * Retrieve the Number of Simulation Paths
	 * 
	 * @return The Number of Simulation Paths
	 */

	public int numPath()
	{
		return _pathCount;
	}

	/**
	 * Generate an Instance of a Replication Run
	 * 
	 * @return Instance of a Replication Run
	 */

	public BondEOSMetrics generateRun()
	{
		return _bondComponent.callable() ? _bondComponent.callMetrics (
			_valuationParams,
			_curveSurfaceQuoteContainer,
			null,
			_price,
			_govvieBuilderSettings,
			_diffusionEvolver,
			_pathCount
		) :  _bondComponent.putMetrics (
			_valuationParams,
			_curveSurfaceQuoteContainer,
			null,
			_price,
			_govvieBuilderSettings,
			_diffusionEvolver,
			_pathCount
		);
	}
}
