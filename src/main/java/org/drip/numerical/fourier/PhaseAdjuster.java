
package org.drip.numerical.fourier;

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
 * Copyright (C) 2015 Lakshmi Krishnamurthy
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
 * <i>PhaseAdjuster</i> implements the functionality specifically meant for enhancing stability of the
 * Fourier numerical Routines.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/README.md">Numerical Quadrature, Differentiation, Eigenization, Linear Algebra, and Utilities</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/fourier/README.md">Specific Fourier Transform Functionality - Rotation Counter, Phase Adjuster</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class PhaseAdjuster {

	/**
	 * No Multi-Valued Principal Branch Tracking
	 */

	public static final int MULTI_VALUE_BRANCH_PHASE_TRACKER_NONE = 0;

	/**
	 * Multi-Valued Logarithm Principal Branch Tracking Using Rotating Counting
	 */

	public static final int MULTI_VALUE_BRANCH_PHASE_TRACKER_ROTATION_COUNT = 1;

	/**
	 * Multi-Valued Logarithm PLUS Power Principal Branch Tracking Using the Kahl-Jackel Algorithm
	 */

	public static final int MULTI_VALUE_BRANCH_POWER_PHASE_TRACKER_KAHL_JACKEL = 2;

	/**
	 * Handling the Branch Switching of the Complex Power Function according Kahl-Jackel algorithm:
	 * 	- http://www.pjaeckel.webspace.virginmedia.com/NotSoComplexLogarithmsInTheHestonModel.pdf
	 * 
	 * @param cnGNumerator The Log G Numerator
	 * @param cnGDenominator The Log G Denominator
	 * @param iN Number of Numerator Counted rotations
	 * @param iM Number of Numerator Counted rotations
	 * 
	 * @return The Branch Switching Log Adjustment
	 */

	public static final org.drip.numerical.complex.CartesianC1 PowerLogPhaseTracker (
		final org.drip.numerical.complex.CartesianC1 cnGNumerator,
		final org.drip.numerical.complex.CartesianC1 cnGDenominator,
		final int iN,
		final int iM)
	{
		if (null == cnGNumerator || null == cnGNumerator || iN < 0 || iM < 0) return null;

		double dblAbsDenominator = cnGDenominator.abs();

		if (0. == dblAbsDenominator) return null;

		try {
			return new org.drip.numerical.complex.CartesianC1 (java.lang.Math.log (cnGNumerator.abs() /
				dblAbsDenominator), cnGNumerator.argument() - cnGDenominator.argument() + 2. *
					java.lang.Math.PI * (iN - iM));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
