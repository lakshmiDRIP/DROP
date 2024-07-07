
package org.drip.dynamics.process;

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
 * <i>RdStochasticEvolver</i> implements the R<sup>d</sup> Stochastic Evolver. The References are:
 *  
 * 	<br><br>
 *  <ul>
 * 		<li>
 * 			Doob, J. L. (1942): The Brownian Movement and Stochastic Equations <i>Annals of Mathematics</i>
 * 				<b>43 (2)</b> 351-369
 * 		</li>
 * 		<li>
 * 			Gardiner, C. W. (2009): <i>Stochastic Methods: A Handbook for the Natural and Social Sciences
 * 				4<sup>th</sup> Edition</i> <b>Springer-Verlag</b>
 * 		</li>
 * 		<li>
 * 			Kadanoff, L. P. (2000): <i>Statistical Physics: Statics, Dynamics, and Re-normalization</i>
 * 				<b>World Scientific</b>
 * 		</li>
 * 		<li>
 * 			Karatzas, I., and S. E. Shreve (1991): <i>Brownian Motion and Stochastic Calculus 2<sup>nd</sup>
 * 				Edition</i> <b>Springer-Verlag</b>
 * 		</li>
 * 		<li>
 * 			Risken, H., and F. Till (1996): <i>The Fokker-Planck Equation – Methods of Solution and
 * 				Applications</i> <b>Springer</b>
 * 		</li>
 *  </ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/dynamics/README.md">HJM, Hull White, LMM, and SABR Dynamic Evolution Models</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/dynamics/process/README.md">Ito-Dynamics Based Stochastic Process</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class RdStochasticEvolver
{
	private org.drip.dynamics.ito.RdToR1Drift[] _driftFunctionArray = null;
	private org.drip.dynamics.ito.RdStochasticDriver _stochasticDriver = null;
	private org.drip.dynamics.ito.RdToR1Volatility[][] _volatilityFunctionGrid = null;

	private double[] pointDriftArray (
		final org.drip.dynamics.ito.TimeRdVertex currentVertex)
	{
		int dimension = _driftFunctionArray.length;
		double[] pointDriftArray = new double[dimension];

		for (int dimensionIndex = 0;
			dimensionIndex < dimension;
			++dimensionIndex)
		{
			try
			{
				pointDriftArray[dimensionIndex] = _driftFunctionArray[dimensionIndex].drift (
					currentVertex
				);
			}
			catch (java.lang.Exception e)
			{
				e.printStackTrace();

				return null;
			}
		}

		return pointDriftArray;
	}

	private double[][] pointVolatilityGrid (
		final org.drip.dynamics.ito.TimeRdVertex currentVertex)
	{
		int dimension = _volatilityFunctionGrid.length;
		double[][] pointVolatilityGrid = new double[dimension][dimension];

		for (int dimensionIndexI = 0;
			dimensionIndexI < dimension;
			++dimensionIndexI)
		{
			for (int dimensionIndexJ = 0;
				dimensionIndexJ < dimension;
				++dimensionIndexJ)
			{
				try
				{
					pointVolatilityGrid[dimensionIndexI][dimensionIndexJ] =
						_volatilityFunctionGrid[dimensionIndexI][dimensionIndexJ].volatility (
							currentVertex
						);
				}
				catch (java.lang.Exception e)
				{
					e.printStackTrace();

					return null;
				}
			}
		}

		return pointVolatilityGrid;
	}

	/**
	 * RdStochasticEvolver Constructor
	 * 
	 * @param driftFunctionArray The Drift Function Array
	 * @param volatilityFunctionGrid The Volatility Function Grid
	 * @param stochasticDriver The Stochastic Driver
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public RdStochasticEvolver (
		final org.drip.dynamics.ito.RdToR1Drift[] driftFunctionArray,
		final org.drip.dynamics.ito.RdToR1Volatility[][] volatilityFunctionGrid,
		final org.drip.dynamics.ito.RdStochasticDriver stochasticDriver)
		throws java.lang.Exception
	{
		if (null == (_driftFunctionArray = driftFunctionArray) ||
			null == (_volatilityFunctionGrid = volatilityFunctionGrid) ||
			null == (_stochasticDriver = stochasticDriver))
		{
			throw new java.lang.Exception (
				"RdStochasticEvolver Constructor => Invalid Inputs"
			);
		}

		int dimension = _driftFunctionArray.length;

		if (0 == dimension ||
			dimension != _volatilityFunctionGrid.length ||
			null == _volatilityFunctionGrid[0] ||
			dimension != _volatilityFunctionGrid[0].length)
		{
			throw new java.lang.Exception (
				"RdStochasticEvolver Constructor => Invalid Inputs"
			);
		}

		for (int dimensionIndexI = 0;
			dimensionIndexI < dimension;
			++dimensionIndexI)
		{
			if (null == _driftFunctionArray[dimensionIndexI])
			{
				throw new java.lang.Exception (
					"RdStochasticEvolver Constructor => Invalid Inputs"
				);
			}

			for (int dimensionIndexJ = 0;
				dimensionIndexJ < dimension;
				++dimensionIndexJ)
			{
				if (null == _volatilityFunctionGrid[dimensionIndexI][dimensionIndexJ])
				{
					throw new java.lang.Exception (
						"RdStochasticEvolver Constructor => Invalid Inputs"
					);
				}
			}
		}
	}

	/**
	 * Retrieve the Drift Function Array
	 * 
	 * @return The Drift Function Array
	 */

	public org.drip.dynamics.ito.RdToR1Drift[] driftFunctionArray()
	{
		return _driftFunctionArray;
	}

	/**
	 * Retrieve the Volatility Function Grid
	 * 
	 * @return The Volatility Function Grid
	 */

	public org.drip.dynamics.ito.RdToR1Volatility[][] volatilityFunctionGrid()
	{
		return _volatilityFunctionGrid;
	}

	/**
	 * Retrieve the Stochastic Driver
	 * 
	 * @return The Stochastic Driver
	 */

	public org.drip.dynamics.ito.RdStochasticDriver stochasticDriver()
	{
		return _stochasticDriver;
	}

	/**
	 * Generate the Next Vertex in the Iteration
	 * 
	 * @param currentVertex The Current Vertex
	 * @param timeIncrement The Time Increment
	 * 
	 * @return The Next Vertex
	 */

	public org.drip.dynamics.ito.TimeRdVertex evolve (
		final org.drip.dynamics.ito.TimeRdVertex currentVertex,
		final double timeIncrement)
	{
		if (null == currentVertex ||
			!org.drip.numerical.common.NumberUtil.IsValid (
				timeIncrement
			)
		)
		{
			return null;
		}

		double[] pointDriftArray = pointDriftArray (
			currentVertex
		);

		if (null == pointDriftArray)
		{
			return null;
		}

		double[] volatilityShiftArray = org.drip.numerical.linearalgebra.MatrixUtil.Product (
			pointVolatilityGrid (
				currentVertex
			),
			_stochasticDriver.emitSingle()
		);

		if (null == volatilityShiftArray)
		{
			return null;
		}

		int dimension = _driftFunctionArray.length;
		double[] xNextArray = new double[dimension];

		double[] xCurrentArray = currentVertex.xArray();

		for (int dimensionIndex = 0;
			dimensionIndex < dimension;
			++dimensionIndex)
		{
			xNextArray[dimensionIndex] = xCurrentArray[dimensionIndex] +
				pointDriftArray[dimensionIndex] * timeIncrement +
				volatilityShiftArray[dimensionIndex];
		}

		try
		{
			return new org.drip.dynamics.ito.TimeRdVertex (
				currentVertex.t() + timeIncrement,
				xNextArray
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Fokker Planck PDF Generator corresponding to R<sup>d</sup> Stochastic Evolver
	 * 
	 * @return The Fokker Planck PDF Generator corresponding to R<sup>d</sup> Stochastic Evolver
	 */

	public org.drip.dynamics.kolmogorov.RdFokkerPlanck fokkerPlanckGenerator()
	{
		try
		{
			return new org.drip.dynamics.kolmogorov.RdFokkerPlanck (
				_driftFunctionArray,
				new org.drip.dynamics.ito.DiffusionTensor (
					_volatilityFunctionGrid
				),
				null
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
