
package org.drip.measure.realization;

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
 * <i>StochasticEdgeJump</i> holds the Edge of the Jump Stochastic Evaluator Outcome.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/README.md">R<sup>d</sup> Continuous/Discrete Probability Measures</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/realization/README.md">Stochastic Jump Diffusion Vertex Edge</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class StochasticEdgeJump {
	private boolean _bOccurred = false;
	private double _dblTarget = java.lang.Double.NaN;
	private double _dblHazardRate = java.lang.Double.NaN;
	private double _dblHazardIntegral = java.lang.Double.NaN;

	/**
	 * StochasticEdgeJump Constructor
	 * 
	 * @param bOccurred TRUE - The Jump Occurred in this Edge Period
	 * @param dblHazardRate The Hazard Rate
	 * @param dblHazardIntegral The Level Hazard Integral
	 * @param dblTarget The Jump Target
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public StochasticEdgeJump (
		final boolean bOccurred,
		final double dblHazardRate,
		final double dblHazardIntegral,
		final double dblTarget)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (_dblHazardRate = dblHazardRate) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_dblHazardIntegral = dblHazardIntegral) ||
				!org.drip.numerical.common.NumberUtil.IsValid (_dblTarget = dblTarget))
			throw new java.lang.Exception ("StochasticEdgeJump Constructor => Invalid Inputs");

		_bOccurred = bOccurred;
	}

	/**
	 * Retrieve the "Jump Occurred in this Level Period" Flag
	 * 
	 * @return The "Jump Occurred in this Level Period" Flag
	 */

	public final boolean jumpOccurred()
	{
		return _bOccurred;
	}

	/**
	 * Retrieve the Jump Occurrence Probability Density
	 * 
	 * @return The Jump Occurrence Probability Density
	 */

	public final double hazardRate()
	{
		return _dblHazardRate;
	}

	/**
	 * Retrieve the Jump Occurrence Hazard Integral
	 * 
	 * @return The Jump Occurrence Hazard Integral
	 */

	public final double hazardIntegral()
	{
		return _dblHazardIntegral;
	}

	/**
	 * Retrieve the Jump Target Value
	 * 
	 * @return The Jump Target Value
	 */

	public final double target()
	{
		return _dblTarget;
	}
}
