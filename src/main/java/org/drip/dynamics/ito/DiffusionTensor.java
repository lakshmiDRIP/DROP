
package org.drip.dynamics.ito;

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
 * <i>DiffusionTensor</i> Diffusion Tensor generates Cross-Product from the Multivariate Volatility
 * 	Functions. The References are:
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
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/dynamics/ito/README.md">Ito Stochastic Process Dynamics Foundation</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class DiffusionTensor
{
	private org.drip.dynamics.ito.RdToR1Volatility[][] _volatilityFunctionGrid = null;

	/**
	 * DiffusionTensor Constructor
	 * 
	 * @param volatilityFunctionGrid  Square Volatility Grid
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public DiffusionTensor (
		final org.drip.dynamics.ito.RdToR1Volatility[][] volatilityFunctionGrid)
		throws java.lang.Exception
	{
		if (null == (_volatilityFunctionGrid = volatilityFunctionGrid))
		{
			throw new java.lang.Exception (
				"DiffusionTensor Constructor => Invalid Inputs"
			);
		}

		int factorCount = 0;
		int dimension = _volatilityFunctionGrid.length;

		if (0 == dimension)
		{
			throw new java.lang.Exception (
				"DiffusionTensor Constructor => Invalid Inputs"
			);
		}

		for (int dimensionIndex = 0;
			dimensionIndex < dimension;
			++dimensionIndex)
		{
			if (null == _volatilityFunctionGrid[dimensionIndex])
			{
				throw new java.lang.Exception (
					"DiffusionTensor Constructor => Invalid Inputs"
				);
			}

			if (0 == dimensionIndex)
			{
				if (0 == (factorCount = _volatilityFunctionGrid[dimensionIndex].length))
				{
					throw new java.lang.Exception (
						"DiffusionTensor Constructor => Invalid Inputs"
					);
				}
			}

			if (null == _volatilityFunctionGrid[dimensionIndex] ||
					factorCount != _volatilityFunctionGrid[dimensionIndex].length)
			{
				throw new java.lang.Exception (
					"DiffusionTensor Constructor => Invalid Inputs"
				);
			}
		}
	}

	/**
	 * Retrieve the Square Volatility Grid
	 * 
	 * @return The Square Volatility Grid
	 */

	public org.drip.dynamics.ito.RdToR1Volatility[][] volatilityFunctionGrid()
	{
		return _volatilityFunctionGrid;
	}

	/**
	 * Retrieve the Dimension Count
	 * 
	 * @return The Dimension Count
	 */

	public int dimension()
	{
		return _volatilityFunctionGrid.length;
	}

	/**
	 * Retrieve the Factor Count
	 * 
	 * @return The Factor Count
	 */

	public int factorCount()
	{
		return _volatilityFunctionGrid[0].length;
	}

	/**
	 * Estimate the Diffusion Coefficient
	 * 
	 * @param timeRdVertex  R<sup>d</sup> Property Variate/Time Coordinate Vertex
	 * @param variateIndexI Variate Index I
	 * @param variateIndexJ Variate Index J
	 * 
	 * @return The Diffusion Coefficient
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double diffusionCoefficient (
		final org.drip.dynamics.ito.TimeRdVertex timeRdVertex,
		final int variateIndexI,
		final int variateIndexJ)
		throws java.lang.Exception
	{
		if (null == timeRdVertex)
		{
			throw new java.lang.Exception (
				"DiffusionTensor::diffusionCoefficient => Invalid Inputs"
			);
		}

		double diffusionCoefficient = 0.;
		int dimension = _volatilityFunctionGrid.length;
		int factorCount = _volatilityFunctionGrid[0].length;

		if (dimension >= variateIndexI || dimension >= variateIndexJ)
		{
			throw new java.lang.Exception (
				"DiffusionTensor::diffusionCoefficient => Invalid Inputs"
			);
		}

		for (int factorIndex = 0;
			factorIndex < factorCount;
			++factorIndex)
		{
			diffusionCoefficient = diffusionCoefficient +
				_volatilityFunctionGrid[variateIndexI][factorIndex].volatility (
					timeRdVertex
				) * _volatilityFunctionGrid[variateIndexJ][factorIndex].volatility (
					timeRdVertex
				);
		}

		return 0.5 * diffusionCoefficient;
	}
}
