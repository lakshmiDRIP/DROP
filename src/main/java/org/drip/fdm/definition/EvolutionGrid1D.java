
package org.drip.fdm.definition;

import org.drip.numerical.common.NumberUtil;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2025 Lakshmi Krishnamurthy
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
 * <i>EvolutionGrid1D</i> maintains the Time and Factor Predictor Grids R<sup>1</sup> State Response
 * 	Evolution. The References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Datta, B. N. (2010): <i>Numerical Linear Algebra and Applications 2<sup>nd</sup> Edition</i>
 * 				<b>SIAM</b> Philadelphia, PA
 * 		</li>
 * 		<li>
 * 			Cebeci, T. (2002): <i>Convective Heat Transfer</i> <b>Horizon Publishing</b> Hammond, IN
 * 		</li>
 * 		<li>
 * 			Crank, J., and P. Nicolson (1947): A Practical Method for Numerical Evaluation of Solutions of
 * 				Partial Differential Equations of the Heat Conduction Type <i>Proceedings of the Cambridge
 * 				Philosophical Society</i> <b>43 (1)</b> 50-67
 * 		</li>
 * 		<li>
 * 			Thomas, J. W. (1995): <i>Numerical Partial Differential Equations: Finite Difference Methods</i>
 * 				<b>Springer-Verlag</b> Berlin, Germany
 * 		</li>
 * 		<li>
 * 			Wikipedia (2023): Alternating-direction implicit method
 * 				https://en.wikipedia.org/wiki/Alternating-direction_implicit_method
 * 		</li>
 * 		<li>
 * 			Wikipedia (2024): Crank–Nicolson method
 * 				https://en.wikipedia.org/wiki/Crank%E2%80%93Nicolson_method
 * 		</li>
 * 	</ul>
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/pde/README.md">Numerical Solution Schemes for PDEs</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/fdm/definition/README.md">Finite Difference PDE Evolver Schemes</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class EvolutionGrid1D
{
	private double[] _timeArray = null;
	private double[] _factorPredictorArray = null;

	/**
	 * Construct a Standard 1D Evolution Grid from the Inputs
	 * 
	 * @param startTime Starting Time
	 * @param timeStep Time Step Width
	 * @param timeNodeCount Time Node Count
	 * @param factorPredictorStart Begin Factor Predictor
	 * @param factorPredictorEnd End Factor Predictor
	 * @param factorPredictorNodeCount Factor Predictor Node Count
	 * 
	 * @return Standard 1D Evolution Grid
	 */

	public static final EvolutionGrid1D Standard (
		final double startTime,
		final double timeStep,
		final int timeNodeCount,
		final double factorPredictorStart,
		final double factorPredictorEnd,
		final int factorPredictorNodeCount)
	{
		if (!NumberUtil.IsValid (startTime) || 0. >= startTime ||
			!NumberUtil.IsValid (timeStep) || 0. >= timeStep ||
				0 >= timeNodeCount ||
			!NumberUtil.IsValid (factorPredictorStart) || !NumberUtil.IsValid (factorPredictorEnd) ||
				1 >= factorPredictorNodeCount)
		{
			return null;
		}

		double factorPredictorWidth = (factorPredictorEnd - factorPredictorStart) / (
			factorPredictorNodeCount - 1
		);
		double[] factorPredictorArray = new double[factorPredictorNodeCount];
		factorPredictorArray[0] = factorPredictorStart;
		double[] timeArray = new double[timeNodeCount];
		timeArray[0] = startTime;

		for (int i = 1; i < timeNodeCount; ++i) {
			timeArray[i] = timeArray[i - 1] + timeStep;
		}

		for (int i = 1; i < factorPredictorNodeCount; ++i) {
			factorPredictorArray[i] = factorPredictorArray[i - 1] + factorPredictorWidth;
		}

		try {
			return new EvolutionGrid1D (timeArray, factorPredictorArray);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * <i>EvolutionGrid1D</i> Constructor
	 * 
	 * @param timeArray Increasing Array of Time Nodes
	 * @param factorPredictorArray Increasing Array of Factor Predictors
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public EvolutionGrid1D (
		final double[] timeArray,
		final double[] factorPredictorArray)
		throws Exception
	{
		if (null == (_timeArray = timeArray) || 0 == _timeArray.length ||
				!NumberUtil.IsValid (_timeArray[0]) ||
			null == (_factorPredictorArray = factorPredictorArray) || 0 == _factorPredictorArray.length ||
				!NumberUtil.IsValid (_factorPredictorArray[0]))
		{
			throw new Exception ("EvolutionGrid1D Constructor => Invalid Inputs");
		}

		for (int i = 1; i < _timeArray.length; ++i) {
			if (!NumberUtil.IsValid (_timeArray[i]) || _timeArray[i] <= _timeArray[i - 1]) {
				throw new Exception ("EvolutionGrid1D Constructor => Invalid Inputs");
			}
		}

		for (int i = 1; i < _factorPredictorArray.length; ++i) {
			if (!NumberUtil.IsValid (_factorPredictorArray[i]) ||
				_factorPredictorArray[i] <= _factorPredictorArray[i - 1])
			{
				throw new Exception ("EvolutionGrid1D Constructor => Invalid Inputs");
			}
		}
	}

	/**
	 * Retrieve the Array of Time Nodes
	 * 
	 * @return Array of Time Nodes
	 */

	public double[] timeArray()
	{
		return _timeArray;
	}

	/**
	 * Retrieve the Array of Factor Predictors
	 * 
	 * @return Array of Factor Predictors
	 */

	public double[] factorPredictorArray()
	{
		return _factorPredictorArray;
	}
}
