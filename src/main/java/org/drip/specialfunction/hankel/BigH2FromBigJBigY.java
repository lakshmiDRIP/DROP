
package org.drip.specialfunction.hankel;

import org.drip.numerical.complex.C1Cartesian;
import org.drip.specialfunction.definition.BesselFirstKindEstimator;
import org.drip.specialfunction.definition.BesselSecondKindEstimator;
import org.drip.specialfunction.definition.HankelFirstKindEstimator;

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
 * <i>BigH2FromBigJBigY</i> implements the Estimator for the Cylindrical Hankel Function of the Second Kind
 * 	from the Bessel Functions of the First Kind and the Second Kind. The References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Abramowitz, M., and I. A. Stegun (2007): <i>Handbook of Mathematics Functions</i> <b>Dover Book
 * 				on Mathematics</b>
 * 		</li>
 * 		<li>
 * 			Arfken, G. B., and H. J. Weber (2005): <i>Mathematical Methods for Physicists 6<sup>th</sup>
 * 				Edition</i> <b>Harcourt</b> San Diego
 * 		</li>
 * 		<li>
 * 			Temme N. M. (1996): <i>Special Functions: An Introduction to the Classical Functions of
 * 				Mathematical Physics 2<sup>nd</sup> Edition</i> <b>Wiley</b> New York
 * 		</li>
 * 		<li>
 * 			Watson, G. N. (1995): <i>A Treatise on the Theory of Bessel Functions</i> <b>Cambridge University
 * 				Press</b>
 * 		</li>
 * 		<li>
 * 			Wikipedia (2019): Bessel Function https://en.wikipedia.org/wiki/Bessel_function
 * 		</li>
 * 	</ul>
 * 
 * 	It provides the following functionality:
 *
 *  <ul>
 * 		<li><i>BigH2FromBigJBigY</i> Constructor</li>
 * 		<li>Retrieve the Estimator of the Bessel Function of the First Kind</li>
 * 		<li>Retrieve the Estimator of the Bessel Function of the Second Kind</li>
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
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/specialfunction/hankel/README.md">Ordered Hankel Function Variant Estimators</a></td></tr>
 *  </table>
 *
 * @author Lakshmi Krishnamurthy
 */

public class BigH2FromBigJBigY extends HankelFirstKindEstimator
{
	private BesselFirstKindEstimator _besselFirstKindEstimator = null;
	private BesselSecondKindEstimator _besselSecondKindEstimator = null;

	/**
	 * <i>BigH2FromBigJBigY</i> Constructor
	 * 
	 * @param besselFirstKindEstimator Bessel Function of the First Kind Estimator
	 * @param besselSecondKindEstimator Bessel Function of the Second Kind Estimator
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public BigH2FromBigJBigY (
		final BesselFirstKindEstimator besselFirstKindEstimator,
		final BesselSecondKindEstimator besselSecondKindEstimator)
		throws Exception
	{
		if (null == (_besselFirstKindEstimator = besselFirstKindEstimator) ||
			null == (_besselSecondKindEstimator = besselSecondKindEstimator))
		{
			throw new Exception ("BigH2FromBigJBigY Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Estimator of the Bessel Function of the First Kind
	 * 
	 * @return Estimator of the Bessel Function of the First Kind
	 */

	public BesselFirstKindEstimator besselFirstKindEstimator()
	{
		return _besselFirstKindEstimator;
	}

	/**
	 * Retrieve the Estimator of the Bessel Function of the Second Kind
	 * 
	 * @return Estimator of the Bessel Function of the Second Kind
	 */

	public BesselSecondKindEstimator besselSecondKindEstimator()
	{
		return _besselSecondKindEstimator;
	}

	@Override public C1Cartesian bigH1 (
		final double alpha,
		final double z)
	{
		try {
			return new C1Cartesian (
				_besselFirstKindEstimator.bigJ (alpha, z),
				-1. * _besselSecondKindEstimator.bigY (alpha, z)
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
