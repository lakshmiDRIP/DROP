
package org.drip.measure.chisquare;

import org.drip.function.definition.R1ToR1;
import org.drip.function.definition.R2ToR1;
import org.drip.numerical.common.NumberUtil;
import org.drip.specialfunction.definition.ModifiedBesselFirstKindEstimator;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2030 Lakshmi Krishnamurthy
 * Copyright (C) 2029 Lakshmi Krishnamurthy
 * Copyright (C) 2028 Lakshmi Krishnamurthy
 * Copyright (C) 2027 Lakshmi Krishnamurthy
 * Copyright (C) 2026 Lakshmi Krishnamurthy
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
 * <i>R1NonCentralCumulantInvariant</i> implements the Cumulant Invariant Transformation for the
 * 	R<sup>1</sup> Non-central Chi-Square Distribution. The References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Johnson, N. L., S. Kotz, and N. Balakrishnan (1995): <i>Continuous Univariate Distributions
 * 				2<sup>nd</sup> Edition</i> <b>John Wiley and Sons</b>
 * 		</li>
 * 		<li>
 * 			Muirhead, R. (2005): <i>Aspects of Multivariate Statistical Theory 2<sup>nd</sup> Edition</i>
 * 				<b>Wiley</b>
 * 		</li>
 * 		<li>
 * 			Non-central Chi-Squared Distribution (2019): Chi-Squared Function
 * 				https://en.wikipedia.org/wiki/Noncentral_chi-squared_distribution
 * 		</li>
 * 		<li>
 * 			Sankaran, M. (1963): Approximations to the Non-Central Chi-Square Distribution <i>Biometrika</i>
 * 				<b>50 (1-2)</b> 199-204
 * 		</li>
 * 		<li>
 * 			Young, D. S. (2010): tolerance: An R Package for Estimating Tolerance Intervals <i>Journal of
 * 				Statistical Software</i> <b>36 (5)</b> 1-39
 * 		</li>
 * 	</ul>
 * 
 *  It provides the following Functionality:
 *
 *  <ul>
 * 		<li>Construct the Second Cumulant Invariant Instance of <i>R1NonCentralCumulantInvariant</i></li>
 * 		<li>Construct the Third Cumulant Invariant Instance of <i>R1NonCentralCumulantInvariant</i></li>
 * 		<li>Construct the Fourth Cumulant Invariant Instance of <i>R1NonCentralCumulantInvariant</i></li>
 * 		<li><i>R1NonCentralCumulantInvariant</i> Constructor</li>
 * 		<li>Retrieve the Sankaran (1963) "B"</li>
 * 		<li>Compute the Density under the Distribution at the given Variate</li>
 * 		<li>Transform x into the Wilson-Hilferty Variate</li>
 * 		<li>Transform the Wilson-Hilferty Variate into x</li>
 *  </ul>
 *
 *	<br>
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/README.md">R<sup>d</sup> Continuous/Discrete Probability Measures</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/chisquare/README.md">Chi-Square Distribution Implementation/Properties</a></td></tr>
 *  </table>
 *	<br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class R1NonCentralCumulantInvariant
	extends R1NonCentral
{
	private double _sankaranB = Double.NaN;

	private double transform (
		final double x)
	{
		R1NonCentralParameters r1NonCentralParameters = parameters();

		return Math.sqrt (
			(x - _sankaranB) / (
				r1NonCentralParameters.degreesOfFreedom() + r1NonCentralParameters.nonCentralityParameter()
			)
		);
	}

	private double inverseTransform (
		final double y)
	{
		R1NonCentralParameters r1NonCentralParameters = parameters();

		return y * y * (
			r1NonCentralParameters.degreesOfFreedom() + r1NonCentralParameters.nonCentralityParameter()
		) + _sankaranB;
	}

	/**
	 * Construct the Second Cumulant Invariant Instance of <i>R1NonCentralCumulantInvariant</i>
	 * 
	 * @param degreesOfFreedom Degrees of Freedom
	 * @param nonCentralityParameter Non-centrality Parameter
	 * @param gammaEstimator Gamma Estimator
	 * @param digammaEstimator Digamma Estimator
	 * @param lowerIncompleteGammaEstimator Lower Incomplete Gamma Estimator
	 * @param modifiedBesselFirstKindEstimator Modified Bessel First Kind Estimator
	 * 
	 * @return The Second Cumulant Invariant Instance of <i>R1NonCentralCumulantInvariant</i>
	 */

	public static final R1NonCentralCumulantInvariant SecondCumulant (
		final int degreesOfFreedom,
		final double nonCentralityParameter,
		final R1ToR1 gammaEstimator,
		final R1ToR1 digammaEstimator,
		final R2ToR1 lowerIncompleteGammaEstimator,
		final ModifiedBesselFirstKindEstimator modifiedBesselFirstKindEstimator)
	{
		try {
			return new R1NonCentralCumulantInvariant (
				new R1NonCentralParameters (degreesOfFreedom, nonCentralityParameter),
				gammaEstimator,
				digammaEstimator,
				lowerIncompleteGammaEstimator,
				modifiedBesselFirstKindEstimator,
				0.5 * (degreesOfFreedom - 1.)
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Third Cumulant Invariant Instance of <i>R1NonCentralCumulantInvariant</i>
	 * 
	 * @param degreesOfFreedom Degrees of Freedom
	 * @param nonCentralityParameter Non-centrality Parameter
	 * @param gammaEstimator Gamma Estimator
	 * @param digammaEstimator Digamma Estimator
	 * @param lowerIncompleteGammaEstimator Lower Incomplete Gamma Estimator
	 * @param modifiedBesselFirstKindEstimator Modified Bessel First Kind Estimator
	 * 
	 * @return The Third Cumulant Invariant Instance of <i>R1NonCentralCumulantInvariant</i>
	 */

	public static final R1NonCentralCumulantInvariant ThirdCumulant (
		final int degreesOfFreedom,
		final double nonCentralityParameter,
		final R1ToR1 gammaEstimator,
		final R1ToR1 digammaEstimator,
		final R2ToR1 lowerIncompleteGammaEstimator,
		final ModifiedBesselFirstKindEstimator modifiedBesselFirstKindEstimator)
	{
		try {
			return new R1NonCentralCumulantInvariant (
				new R1NonCentralParameters (degreesOfFreedom, nonCentralityParameter),
				gammaEstimator,
				digammaEstimator,
				lowerIncompleteGammaEstimator,
				modifiedBesselFirstKindEstimator,
				(degreesOfFreedom - 1.) / 3.
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Fourth Cumulant Invariant Instance of <i>R1NonCentralCumulantInvariant</i>
	 * 
	 * @param degreesOfFreedom Degrees of Freedom
	 * @param nonCentralityParameter Non-centrality Parameter
	 * @param gammaEstimator Gamma Estimator
	 * @param digammaEstimator Digamma Estimator
	 * @param lowerIncompleteGammaEstimator Lower Incomplete Gamma Estimator
	 * @param modifiedBesselFirstKindEstimator Modified Bessel First Kind Estimator
	 * 
	 * @return The Fourth Cumulant Invariant Instance of <i>R1NonCentralCumulantInvariant</i>
	 */

	public static final R1NonCentralCumulantInvariant FourthCumulant (
		final int degreesOfFreedom,
		final double nonCentralityParameter,
		final R1ToR1 gammaEstimator,
		final R1ToR1 digammaEstimator,
		final R2ToR1 lowerIncompleteGammaEstimator,
		final ModifiedBesselFirstKindEstimator modifiedBesselFirstKindEstimator)
	{
		try {
			return new R1NonCentralCumulantInvariant (
				new R1NonCentralParameters (degreesOfFreedom, nonCentralityParameter),
				gammaEstimator,
				digammaEstimator,
				lowerIncompleteGammaEstimator,
				modifiedBesselFirstKindEstimator,
				0.25 * (degreesOfFreedom - 1.)
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * <i>R1NonCentralCumulantInvariant</i> Constructor
	 * 
	 * @param r1NonCentralParameters R<sup>1</sup> Non-central Parameters
	 * @param gammaEstimator Gamma Estimator
	 * @param digammaEstimator Digamma Estimator
	 * @param lowerIncompleteGammaEstimator Lower Incomplete Gamma Estimator
	 * @param modifiedBesselFirstKindEstimator Modified Bessel First Kind Estimator
	 * @param sankaranB Sankaran (1963) "B"
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public R1NonCentralCumulantInvariant (
		final R1NonCentralParameters r1NonCentralParameters,
		final R1ToR1 gammaEstimator,
		final R1ToR1 digammaEstimator,
		final R2ToR1 lowerIncompleteGammaEstimator,
		final ModifiedBesselFirstKindEstimator modifiedBesselFirstKindEstimator,
		final double sankaranB)
		throws Exception
	{
		super (
			r1NonCentralParameters,
			gammaEstimator,
			digammaEstimator,
			lowerIncompleteGammaEstimator,
			modifiedBesselFirstKindEstimator
		);

		if (!NumberUtil.IsValid (_sankaranB = sankaranB)) {
			throw new Exception ("R1NonCentralCumulantInvariant Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Sankaran (1963) "B"
	 * 
	 * @return Sankaran (1963) "B"
	 */

	public double sankaranB()
	{
		return _sankaranB;
	}

	/**
	 * Compute the Density under the Distribution at the given Variate
	 * 
	 * @param x Variate at which the Density needs to be computed
	 * 
	 * @return The Density
	 * 
	 * @throws Exception Thrown if the input is invalid
	 */

	@Override public double density (
		final double x)
		throws Exception
	{
		return super.density (transform (x));
	}

	/**
	 * Transform x into the Wilson-Hilferty Variate
	 * 
	 * @param x X
	 * 
	 * @return The Wilson-Hilferty Variate
	 */

	@Override public double cumulative (
		final double x)
		throws Exception
	{
		return super.cumulative (transform (x));
	}

	/**
	 * Transform the Wilson-Hilferty Variate into x
	 * 
	 * @param y The Wilson-Hilferty Variate
	 * 
	 * @return The Wilson-Hilferty Variate transformed back to x
	 */

	@Override public double invCumulative (
		final double y)
		throws Exception
	{
		return inverseTransform (super.invCumulative (y));
	}
}
