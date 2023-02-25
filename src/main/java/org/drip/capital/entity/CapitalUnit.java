
package org.drip.capital.entity;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
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
 * <i>CapitalUnit</i> implements the VaR and the Stress Functionality for the specified Capital Unit. The
 * 	References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Bank for International Supervision(2005): Stress Testing at Major Financial Institutions: Survey
 * 				Results and Practice https://www.bis.org/publ/cgfs24.htm
 * 		</li>
 * 		<li>
 * 			Glasserman, P. (2004): <i>Monte Carlo Methods in Financial Engineering</i> <b>Springer</b>
 * 		</li>
 * 		<li>
 * 			Kupiec, P. H. (2000): Stress Tests and Risk Capital <i>Risk</i> <b>2 (4)</b> 27-39
 * 		</li>
 * 	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/CapitalAnalyticsLibrary.md">Capital Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/README.md">Basel Market Risk and Operational Capital</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/entity/README.md">Economic Risk Capital Estimation Nodes</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class CapitalUnit
	implements org.drip.capital.entity.CapitalSimulator
{
	private double _notional = java.lang.Double.NaN;
	private org.drip.capital.label.Coordinate _coordinate = null;
	private org.drip.capital.entity.CapitalUnitEventContainer _stressEventContainer = null;

	private org.drip.capital.simulation.PathPnLRealization pathPnLRealization (
		final int pathIndex,
		final double pnlScaler,
		final org.drip.capital.simulation.FSPnLDecomposition fsPnLDecomposition,
		final java.util.Map<java.lang.String, java.lang.Double> fsTypeVolatilityAjustmentMap,
		final double stressPnLScaler,
		final org.drip.capital.simulation.StressEventIndicator stressEventIndicator)
	{
		if (null == fsPnLDecomposition || null == stressEventIndicator)
		{
			return null;
		}

		org.drip.capital.stress.SystemicEventContainer gsstEventContainer =
			_stressEventContainer.systemicEventContainer();

		org.drip.capital.stress.IdiosyncraticEventContainer iBSSTEventContainer =
			_stressEventContainer.idiosyncraticEventContainer();

		try
		{
			return new org.drip.capital.simulation.PathPnLRealization (
				pathIndex,
				fsPnLDecomposition.applyVolatilityAdjustment (
					fsTypeVolatilityAjustmentMap,
					pnlScaler
				),
				null == gsstEventContainer ? null : gsstEventContainer.realizeIncidenceEnsemble (
					stressPnLScaler,
					stressEventIndicator.systemic()
				),
				null == iBSSTEventContainer ? null : iBSSTEventContainer.realizeIncidenceEnsemble (
					stressPnLScaler,
					stressEventIndicator.idiosyncraticMap()
				)
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * CapitalUnit Constructor
	 * 
	 * @param coordinate Capital Unit Coordinate
	 * @param stressEventContainer Capital Unit Stress Event Container
	 * @param notional The Capital Unit Notional
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public CapitalUnit (
		final org.drip.capital.label.Coordinate coordinate,
		final org.drip.capital.entity.CapitalUnitEventContainer stressEventContainer,
		final double notional)
		throws java.lang.Exception
	{
		if (null == (_coordinate = coordinate) ||
			null == (_stressEventContainer = stressEventContainer) ||
			!org.drip.numerical.common.NumberUtil.IsValid (
				_notional = notional
			)
		)
		{
			throw new java.lang.Exception (
				"CapitalUnit Constructor => Invalid Inputs"
			);
		}
	}

	/**
	 * Retrieve the Capital Unit Coordinate
	 * 
	 * @return The Capital Unit Coordinate
	 */

	public org.drip.capital.label.Coordinate coordinate()
	{
		return _coordinate;
	}

	/**
	 * Retrieve the Capital Unit Stress Event Container
	 * 
	 * @return The Capital Unit Stress Event Container
	 */

	public org.drip.capital.entity.CapitalUnitEventContainer stressEventContainer()
	{
		return _stressEventContainer;
	}

	/**
	 * Retrieve the Capital Unit Notional
	 * 
	 * @return The Capital Unit Notional
	 */

	public double notional()
	{
		return _notional;
	}

	@Override public org.drip.capital.simulation.PathPnLRealization[] pathPnLRealizationArray (
		final org.drip.capital.setting.SimulationControl simulationControl,
		final org.drip.capital.setting.SimulationPnLControl simulationPnLControl)
	{
		if (null == simulationControl ||
			null == simulationPnLControl)
		{
			return null;
		}

		org.drip.capital.stress.IdiosyncraticEventContainer idiosyncraticEventContainer =
			_stressEventContainer.idiosyncraticEventContainer();

		java.util.Set<java.lang.String> idiosyncraticEventSet = null == idiosyncraticEventContainer ? null :
			idiosyncraticEventContainer.eventMap().keySet();

		org.drip.capital.setting.HorizonTailFSPnLControl pnlControl = simulationPnLControl.noStress();

		java.util.Map<java.lang.String, java.lang.Double> fsTypeVolatilityAjustmentMap =
			pnlControl.fsTypeVolatilityAjustmentMap();

		int systemicStressIncidenceSampling = simulationControl.systemicStressIncidenceSampling();

		double stressPnLScaler = simulationPnLControl.stress().grossScaler();

		int pathCount = simulationControl.pathCount();

		double pnlScaler = pnlControl.grossScaler();

		org.drip.capital.simulation.PathPnLRealization[] pathPnLRealizationArray =
			new org.drip.capital.simulation.PathPnLRealization[pathCount];

		for (int pathIndex = 0;
			pathIndex < pathCount;
			++pathIndex)
		{
			if (null == (
				pathPnLRealizationArray[pathIndex] = pathPnLRealization (
					pathIndex,
					pnlScaler,
					org.drip.capital.simulation.FSPnLDecomposition.Standard (
						_notional
					),
					fsTypeVolatilityAjustmentMap,
					stressPnLScaler,
					org.drip.capital.setting.SimulationControl.SYSTEMIC_STRESS_INCIDENCE_RANDOM_SAMPLING
						== systemicStressIncidenceSampling ?
						org.drip.capital.simulation.StressEventIndicator.RandomSystemic (
							idiosyncraticEventSet
						) : org.drip.capital.simulation.StressEventIndicator.CustomSystemic (
							idiosyncraticEventSet,
							((double) pathIndex) / ((double) pathCount)
						)
					)
				))
			{
				return null;
			}
		}

		return pathPnLRealizationArray;
	}

	@Override public org.drip.capital.simulation.CapitalUnitPathEnsemble pathEnsemble (
		final org.drip.capital.setting.SimulationControl simulationControl,
		final org.drip.capital.setting.SimulationPnLControl simulationPnLControl)
	{
		org.drip.capital.simulation.PathPnLRealization[] pathPnLRealizationArray = pathPnLRealizationArray
		(
			simulationControl,
			simulationPnLControl
		);

		if (null == pathPnLRealizationArray)
		{
			return null;
		}

		org.drip.capital.simulation.CapitalUnitPathEnsemble capitalUnitPathEnsemble =
			new org.drip.capital.simulation.CapitalUnitPathEnsemble();

		for (org.drip.capital.simulation.PathPnLRealization pathPnLRealization : pathPnLRealizationArray)
		{
			if (!capitalUnitPathEnsemble.addPathPnLRealization (
				pathPnLRealization
			))
			{
				return null;
			}
		}

		return capitalUnitPathEnsemble;
	}
}
