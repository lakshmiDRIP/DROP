
package org.drip.function.rdtor1descent;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
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
 * <i>LineStepEvolutionControl</i> contains the Parameters required to compute the Valid a Line Step. The
 * References are:
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Armijo, L. (1966): Minimization of Functions having Lipschitz-Continuous First Partial
 * 				Derivatives <i>Pacific Journal of Mathematics</i> <b>16 (1)</b> 1-3
 * 		</li>
 * 		<li>
 * 			Nocedal, J., and S. Wright (1999): <i>Numerical Optimization</i> <b>Wiley</b>
 * 		</li>
 * 		<li>
 * 			Wolfe, P. (1969): Convergence Conditions for Ascent Methods <i>SIAM Review</i> <b>11 (2)</b>
 * 				226-235
 * 		</li>
 * 		<li>
 * 			Wolfe, P. (1971): Convergence Conditions for Ascent Methods; II: Some Corrections <i>SIAM
 * 				Review</i> <b>13 (2)</b> 185-188
 * 		</li>
 * 	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/README.md">R<sup>d</sup> To R<sup>d</sup> Function Analysis</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/rdtor1descent/README.md">R<sup>d</sup> To R<sup>1</sup> Gradient Descent Techniques</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class LineStepEvolutionControl
{
	private int _reductionStepCount = -1;
	private double _reductionFactor = java.lang.Double.NaN;
	private org.drip.function.rdtor1descent.LineEvolutionVerifier _lineEvolutionVerifier = null;

	/**
	 * Retrieve the Nocedal-Wright-Armijo Verifier Based Standard LineStepEvolutionControl Instance
	 * 
	 * @param maximizerCheck TRUE - Perform a Check for the Function Maxima
	 * 
	 * @return The Nocedal-Wright-Armijo Verifier Based Standard LineStepEvolutionControl Instance
	 */

	public static final LineStepEvolutionControl NocedalWrightArmijo (
		final boolean maximizerCheck)
	{
		try
		{
			return new LineStepEvolutionControl (
				org.drip.function.rdtor1descent.ArmijoEvolutionVerifier.NocedalWrightStandard (
					maximizerCheck
				),
				0.75,
				1
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Retrieve the Nocedal-Wright-Weak Curvature Verifier Based Standard LineStepEvolutionControl Instance
	 * 
	 * @return The Nocedal-Wright-Weak Curvature Verifier Based Standard LineStepEvolutionControl Instance
	 */

	public static final LineStepEvolutionControl NocedalWrightWeakCurvature()
	{
		try
		{
			return new LineStepEvolutionControl (
				org.drip.function.rdtor1descent.CurvatureEvolutionVerifier.NocedalWrightStandard (
					false
				),
				0.75,
				1
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Retrieve the Nocedal-Wright-Strong Curvature Verifier Based Standard LineStepEvolutionControl Instance
	 * 
	 * @return The Nocedal-Wright-Strong Curvature Verifier Based Standard LineStepEvolutionControl Instance
	 */

	public static final LineStepEvolutionControl NocedalWrightStrongCurvature()
	{
		try
		{
			return new LineStepEvolutionControl (
				org.drip.function.rdtor1descent.CurvatureEvolutionVerifier.NocedalWrightStandard (
					true
				),
				0.75,
				1
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Retrieve the Nocedal-Wright-Weak Wolfe Verifier Based Standard LineStepEvolutionControl Instance
	 * 
	 * @param maximizerCheck TRUE - Perform a Check for the Function Maxima
	 * 
	 * @return The Nocedal-Wright-Weak Wolfe Verifier Based Standard LineStepEvolutionControl Instance
	 */

	public static final LineStepEvolutionControl NocedalWrightWeakWolfe (
		final boolean maximizerCheck)
	{
		try
		{
			return new LineStepEvolutionControl (
				org.drip.function.rdtor1descent.WolfeEvolutionVerifier.NocedalWrightStandard (
					maximizerCheck,
					false
				),
				0.75,
				1
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Retrieve the Nocedal-Wright-Strong Wolfe Verifier Based Standard LineStepEvolutionControl Instance
	 * 
	 * @param maximizerCheck TRUE - Perform a Check for the Function Maxima
	 * 
	 * @return The Nocedal-Wright-Strong Wolfe Verifier Based Standard LineStepEvolutionControl Instance
	 */

	public static final LineStepEvolutionControl NocedalWrightStrongWolfe (
		final boolean maximizerCheck)
	{
		try
		{
			return new LineStepEvolutionControl (
				org.drip.function.rdtor1descent.WolfeEvolutionVerifier.NocedalWrightStandard (
					maximizerCheck,
					true
				),
				0.75,
				1
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * LineStepEvolutionControl Constructor
	 * 
	 * @param lineEvolutionVerifier The Line Evolution Verifier Instance
	 * @param reductionFactor The Per-Step Reduction Factor
	 * @param reductionStepCount Count of Reduction Steps
	 * 
	 * @throws java.lang.Exception Thrown if Inputs are Invalid
	 */

	public LineStepEvolutionControl (
		final org.drip.function.rdtor1descent.LineEvolutionVerifier lineEvolutionVerifier,
		final double reductionFactor,
		final int reductionStepCount)
		throws java.lang.Exception
	{
		if (null == (_lineEvolutionVerifier = lineEvolutionVerifier) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_reductionFactor = reductionFactor) ||
				1. <= _reductionFactor ||
			0 >= (_reductionStepCount = reductionStepCount))
		{
			throw new java.lang.Exception ("LineStepEvolutionControl Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Line Evolution Verifier Instance
	 * 
	 * @return The Line Evolution Verifier Instance
	 */

	public org.drip.function.rdtor1descent.LineEvolutionVerifier lineEvolutionVerifier()
	{
		return _lineEvolutionVerifier;
	}

	/**
	 * Retrieve the Reduction Factor per Step
	 * 
	 * @return The Reduction Factor per Step
	 */

	public double reductionFactor()
	{
		return _reductionFactor;
	}

	/**
	 * Retrieve the Count of Reduction Steps
	 * 
	 * @return The Count of Reduction Steps
	 */

	public int reductionStepCount()
	{
		return _reductionStepCount;
	}
}
