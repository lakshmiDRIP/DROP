
package org.drip.function.e2erf;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
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
 * <i>AbramowitzStegunSeriesGenerator</i> implements the E<sub>2</sub> erf Abramowitz-Stegun Variant of
 * Series Term Generator. The References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Abramowitz, M., and I. A. Stegun (2007): <i>Handbook of Mathematics Functions</i> <b>Dover Book
 * 				on Mathematics</b>
 * 		</li>
 * 		<li>
 * 			Chang, S. H., P. C. Cosman, L. B. Milstein (2011): Chernoff-Type Bounds for Gaussian Error
 * 				Function <i>IEEE Transactions on Communications</i> <b>59 (11)</b> 2939-2944
 * 		</li>
 * 		<li>
 * 			Cody, W. J. (1991): Algorithm 715: SPECFUN – A Portable FORTRAN Package of Special Function
 * 				Routines and Test Drivers <i>ACM Transactions on Mathematical Software</i> <b>19 (1)</b>
 * 				22-32
 * 		</li>
 * 		<li>
 * 			Schopf, H. M., and P. H. Supancic (2014): On Burmann’s Theorem and its Application to Problems of
 * 				Linear and Non-linear Heat Transfer and Diffusion
 * 				https://www.mathematica-journal.com/2014/11/on-burmanns-theorem-and-its-application-to-problems-of-linear-and-nonlinear-heat-transfer-and-diffusion/#more-39602/
 * 		</li>
 * 		<li>
 * 			Wikipedia (2019): Error Function https://en.wikipedia.org/wiki/Error_function
 * 		</li>
 * 	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/README.md">R<sup>d</sup> To R<sup>d</sup> Function Analysis</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/e2erf/README.md">E<sub>2</sub> erf and erf<sup>-1</sup> Implementations</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class AbramowitzStegunSeriesGenerator extends org.drip.numerical.estimation.R1ToR1Series
{

	/**
	 * Construct a Inverse Polynomial Degree 4 Version of E<sub>2</sub> erf AbramowitzStegunSeriesGenerator
	 * 
	 * @return Inverse Polynomial Degree 4 Version of E<sub>2</sub> erf AbramowitzStegunSeriesGenerator
	 */

	public static final AbramowitzStegunSeriesGenerator InversePolynomial4()
	{
		java.util.TreeMap<java.lang.Integer, java.lang.Double> termWeightMap = new
			java.util.TreeMap<java.lang.Integer, java.lang.Double>();

		termWeightMap.put (
			0,
			1.000000
		);

		termWeightMap.put (
			1,
			0.278393
		);

		termWeightMap.put (
			2,
			0.230289
		);

		termWeightMap.put (
			3,
			0.000972
		);

		termWeightMap.put (
			4,
			0.078108
		);

		try
		{
			return new AbramowitzStegunSeriesGenerator (
				org.drip.numerical.estimation.R1ToR1SeriesTerm.Taylor(),
				termWeightMap
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct a Mixed Polynomial Degree 3 Version of E<sub>2</sub> erf AbramowitzStegunSeriesGenerator
	 * 
	 * @return Mixed Polynomial Degree 3 Version of E<sub>2</sub> erf AbramowitzStegunSeriesGenerator
	 */

	public static final AbramowitzStegunSeriesGenerator MixedPolynomial3()
	{
		java.util.TreeMap<java.lang.Integer, java.lang.Double> termWeightMap = new
			java.util.TreeMap<java.lang.Integer, java.lang.Double>();

		termWeightMap.put (
			1,
			0.3480242
		);

		termWeightMap.put (
			2,
			-0.0958798
		);

		termWeightMap.put (
			3,
			0.7478556
		);

		try
		{
			return new AbramowitzStegunSeriesGenerator (
				org.drip.numerical.estimation.R1ToR1SeriesTerm.Taylor(),
				termWeightMap
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct a Inverse Polynomial Degree 6 Version of E<sub>2</sub> erf AbramowitzStegunSeriesGenerator
	 * 
	 * @return Inverse Polynomial Degree 6 Version of E<sub>2</sub> erf AbramowitzStegunSeriesGenerator
	 */

	public static final AbramowitzStegunSeriesGenerator InversePolynomial6()
	{
		java.util.TreeMap<java.lang.Integer, java.lang.Double> termWeightMap = new
			java.util.TreeMap<java.lang.Integer, java.lang.Double>();

		termWeightMap.put (
			0,
			1.0000000000
		);

		termWeightMap.put (
			1,
			0.0705230784
		);

		termWeightMap.put (
			2,
			0.0422820123
		);

		termWeightMap.put (
			3,
			0.0092705272
		);

		termWeightMap.put (
			4,
			0.0001520143
		);

		termWeightMap.put (
			5,
			0.0002765672
		);

		termWeightMap.put (
			6,
			0.0000430638
		);

		try
		{
			return new AbramowitzStegunSeriesGenerator (
				org.drip.numerical.estimation.R1ToR1SeriesTerm.Taylor(),
				termWeightMap
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct a Mixed Polynomial Degree 5 Version of E<sub>2</sub> erf AbramowitzStegunSeriesGenerator
	 * 
	 * @return Mixed Polynomial Degree 5 Version of E<sub>2</sub> erf AbramowitzStegunSeriesGenerator
	 */

	public static final AbramowitzStegunSeriesGenerator MixedPolynomial5()
	{
		java.util.TreeMap<java.lang.Integer, java.lang.Double> termWeightMap = new
			java.util.TreeMap<java.lang.Integer, java.lang.Double>();

		termWeightMap.put (
			1,
			0.254829592
		);

		termWeightMap.put (
			2,
			-0.284496736
		);

		termWeightMap.put (
			3,
			1.421413741
		);

		termWeightMap.put (
			4,
			-1.453152027
		);

		termWeightMap.put (
			5,
			1.061405429
		);

		try
		{
			return new AbramowitzStegunSeriesGenerator (
				org.drip.numerical.estimation.R1ToR1SeriesTerm.Taylor(),
				termWeightMap
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Numerical Recipes Version of E<sub>2</sub> erf AbramowitzStegunSeriesGenerator
	 * 
	 * @return Numerical Recipes Version of E<sub>2</sub> erf AbramowitzStegunSeriesGenerator
	 */

	public static final AbramowitzStegunSeriesGenerator NumericalRecipe2007()
	{
		java.util.TreeMap<java.lang.Integer, java.lang.Double> termWeightMap = new
			java.util.TreeMap<java.lang.Integer, java.lang.Double>();

		termWeightMap.put (
			0,
			-1.26551223
		);

		termWeightMap.put (
			1,
			1.00002368
		);

		termWeightMap.put (
			2,
			0.37409196
		);

		termWeightMap.put (
			3,
			0.09678418
		);

		termWeightMap.put (
			4,
			-0.18628806
		);

		termWeightMap.put (
			5,
			0.27886807
		);

		termWeightMap.put (
			6,
			-1.13520398
		);

		termWeightMap.put (
			7,
			1.48851587
		);

		termWeightMap.put (
			8,
			-0.82215223
		);

		termWeightMap.put (
			9,
			0.17087277
		);

		try
		{
			return new AbramowitzStegunSeriesGenerator (
				org.drip.numerical.estimation.R1ToR1SeriesTerm.Taylor(),
				termWeightMap
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * AbramowitzStegunSeriesGenerator Constructor
	 * 
	 * @param r1ToR1SeriesTerm R<sup>1</sup> To R<sup>1</sup> Series Expansion Term
	 * @param termWeightMap Error Term Weight Map
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public AbramowitzStegunSeriesGenerator (
		final org.drip.numerical.estimation.R1ToR1SeriesTerm r1ToR1SeriesTerm,
		final java.util.TreeMap<java.lang.Integer, java.lang.Double> termWeightMap)
		throws java.lang.Exception
	{
		super (
			r1ToR1SeriesTerm,
			false,
			termWeightMap
		);
	}
}
