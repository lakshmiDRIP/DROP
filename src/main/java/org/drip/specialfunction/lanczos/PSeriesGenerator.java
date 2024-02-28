
package org.drip.specialfunction.lanczos;

import java.util.TreeMap;

import org.drip.numerical.estimation.R0ToR1Series;

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
 * <i>PSeriesGenerator</i> generates the Terms of the Lanczos P Series. The References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Godfrey, P. (2001): Lanczos Implementation of the Gamma Function
 * 				http://www.numericana.com/answer/info/godfrey.htm
 * 		</li>
 * 		<li>
 * 			Press, W. H., S. A. Teukolsky, W. T. Vetterling, and B. P. Flannery (2007): <i>Numerical Recipes:
 * 				The Art of Scientific Computing 3rd Edition</i> <b>Cambridge University Press</b> New York
 * 		</li>
 * 		<li>
 * 			Pugh, G. R. (2004): <i>An Analysis of the Lanczos Gamma Approximation</i> Ph. D. <b>University of
 * 				British Columbia</b>
 * 		</li>
 * 		<li>
 * 			Toth V. T. (2016): Programmable Calculators – The Gamma Function
 * 				http://www.rskey.org/CMS/index.php/the-library/11
 * 		</li>
 * 		<li>
 * 			Wikipedia (2019): Lanczos Approximation https://en.wikipedia.org/wiki/Lanczos_approximation
 * 		</li>
 * 	</ul>
 * 
 * 	It provides the following functionality:
 *
 *  <ul>
 * 		<li>Construct a Standard Instance of the Lanczos P Series Generator</li>
 * 		<li><i>PSeriesGenerator</i> Constructor</li>
 * 		<li>Retrieve the Chebyshev Coefficient Matrix</li>
 * 		<li>Retrieve the Series Term Count</li>
 *  </ul>
 *
 *  <br>
 *  <style>table, td, th {
 *  	padding: 1px; border: 2px solid #008000; border-radius: 8px; background-color: #dfff00;
 *		text-align: center; color:  #0000ff;
 *  }
 *  </style>
 *  
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FunctionAnalysisLibrary.md">Function Analysis Library</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/specialfunction/README.md">Special Function Implementation and Analysis</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/specialfunction/lanczos/README.md">Lanczos Scheme for Gamma Estimate</a></td></tr>
 *  </table>
 *
 * @author Lakshmi Krishnamurthy
 */

public class PSeriesGenerator extends R0ToR1Series
{
	private double[][] _chebyshevCoefficientMatrix = null;

	/**
	 * Construct a Standard Instance of the Lanczos P Series Generator
	 * 
	 * @param g Lanczos g Control
	 * @param termCount Lanczos Series Term Count
	 * 
	 * @return Standard Instance of the Lanczos P Series Generator
	 */

	public static final PSeriesGenerator Standard (
		final int g,
		final int termCount)
	{
		double[][] chebyshevCoefficientMatrix = ChebyshevCoefficientMatrix.Rollout (2 * termCount);

		if (null == chebyshevCoefficientMatrix) {
			return null;
		}

		TreeMap<Integer, Double> chebyshevCoefficientWeightMap = new TreeMap<Integer, Double>();

		double sqrt2OverPI = Math.sqrt (2.) / Math.PI;

		for (int termIndex = 0; termIndex <= termCount; ++termIndex) {
			chebyshevCoefficientWeightMap.put (
				termIndex,
				sqrt2OverPI * chebyshevCoefficientMatrix[2 * termCount][2 * termIndex]
			);
		}

		try {
			return new PSeriesGenerator (
				new PSeriesTerm (g),
				chebyshevCoefficientWeightMap,
				chebyshevCoefficientMatrix
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * <i>PSeriesGenerator</i> Constructor
	 * 
	 * @param pSeriesTerm Lanczos P Series Term
	 * @param chebyshevCoefficientWeightMap Chebyshev Coefficient Term Weight Map
	 * @param chebyshevCoefficientMatrix Chebyshev Coefficient Matrix
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public PSeriesGenerator (
		final PSeriesTerm pSeriesTerm,
		final TreeMap<Integer, Double> chebyshevCoefficientWeightMap,
		final double[][] chebyshevCoefficientMatrix)
		throws Exception
	{
		super (pSeriesTerm, false, chebyshevCoefficientWeightMap, true);

		_chebyshevCoefficientMatrix = chebyshevCoefficientMatrix;
	}

	/**
	 * Retrieve the Chebyshev Coefficient Matrix
	 * 
	 * @return The Chebyshev Coefficient Matrix
	 */

	public double[][] _chebyshevCoefficientMatrix()
	{
		return _chebyshevCoefficientMatrix;
	}

	/**
	 * Retrieve the Series Term Count
	 * 
	 * @return The Series Term Count
	 */

	public int termCount()
	{
		return _chebyshevCoefficientMatrix.length;
	}
}
