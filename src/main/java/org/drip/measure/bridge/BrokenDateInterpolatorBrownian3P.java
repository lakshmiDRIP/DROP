
package org.drip.measure.bridge;

import org.drip.numerical.common.NumberUtil;

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
 * <i>BrokenDateInterpolatorBrownian3P</i> Interpolates the Broken Dates using Three Stochastic Value Nodes
 * 	using the Three Point Brownian Bridge Scheme. It provides the following Functionality:
 *
 *  <ul>
 * 		<li><i>BrokenDateInterpolatorBrownian3P</i> Constructor</li>
 * 		<li>Retrieve T1</li>
 * 		<li>Retrieve T2</li>
 * 		<li>Retrieve T3</li>
 * 		<li>Retrieve V1</li>
 * 		<li>Retrieve V2</li>
 * 		<li>Retrieve V3</li>
 * 		<li>Retrieve the Brownian Bridge Factor</li>
 * 		<li>Interpolate the Value at T</li>
 *  </ul>
 *
 *	<br>
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/README.md">R<sup>d</sup> Continuous/Discrete Probability Measures</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/bridge/README.md">Broken Date Brownian Bridge Interpolator</a></td></tr>
 *  </table>
 *	<br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class BrokenDateInterpolatorBrownian3P
	implements BrokenDateInterpolator
{
	private double _t1 = Double.NaN;
	private double _t2 = Double.NaN;
	private double _t3 = Double.NaN;
	private double _v1 = Double.NaN;
	private double _v2 = Double.NaN;
	private double _v3 = Double.NaN;
	private double _bridgeFactor = Double.NaN;

	/**
	 * <i>BrokenDateInterpolatorBrownian3P</i> Constructor
	 * 
	 * @param dblT1 T1
	 * @param dblT2 T2
	 * @param dblT3 T3
	 * @param dblV1 V1
	 * @param dblV2 V2
	 * @param dblV3 V3
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public BrokenDateInterpolatorBrownian3P (
		final double dblT1,
		final double dblT2,
		final double dblT3,
		final double dblV1,
		final double dblV2,
		final double dblV3)
		throws Exception
	{
		if (!NumberUtil.IsValid (_t1 = dblT1) ||
			!NumberUtil.IsValid (_t2 = dblT2) ||
			!NumberUtil.IsValid (_t3 = dblT3) ||
			!NumberUtil.IsValid (_v1 = dblV1) ||
			!NumberUtil.IsValid (_v2 = dblV2) ||
			!NumberUtil.IsValid (_v3 = dblV3) ||
			_t1 >= _t2 ||
			_t2 >= _t3)
		{
			throw new Exception ("BrokenDateInterpolatorBrownian3P Constructor => Invalid Inputs");
		}

		double t2MinusT1 = _t2 - _t1;
		double t3MinusT1 = _t3 - _t1;
		double t3MinusT2 = _t3 - _t2;

		_bridgeFactor = Math.sqrt (
			t3MinusT1 / (t3MinusT2 * t2MinusT1)) * (_v2 - (t3MinusT2 * _v1 / t3MinusT1) -
				t2MinusT1 * _v3 / t3MinusT1
		);
	}

	/**
	 * Retrieve T1
	 * 
	 * @return T1
	 */

	public double t1()
	{
		return _t1;
	}

	/**
	 * Retrieve T2
	 * 
	 * @return T2
	 */

	public double t2()
	{
		return _t2;
	}

	/**
	 * Retrieve T3
	 * 
	 * @return T3
	 */

	public double t3()
	{
		return _t3;
	}

	/**
	 * Retrieve V1
	 * 
	 * @return V1
	 */

	public double v1()
	{
		return _v1;
	}

	/**
	 * Retrieve V2
	 * 
	 * @return V2
	 */

	public double v2()
	{
		return _v2;
	}

	/**
	 * Retrieve V3
	 * 
	 * @return V3
	 */

	public double v3()
	{
		return _v3;
	}

	/**
	 * Retrieve the Brownian Bridge Factor
	 * 
	 * @return The Brownian Bridge Factor
	 */

	public double bridgeFactor()
	{
		return _bridgeFactor;
	}

	/**
	 * Interpolate the Value at T
	 * 
	 * @param t T
	 * 
	 * @return The Interpolated Value
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	@Override public double interpolate (
		final double t)
		throws Exception
	{
		if (!NumberUtil.IsValid (t) || t < _t1 || t > _t3) {
			throw new Exception ("BrokenDateInterpolatorBrownian3P::interpolate => Invalid Inputs");
		}

		double t3MinusT1 = _t3 - _t1;
		double t3MinusT = _t3 - t;
		double tMinusT1 = t - _t1;

		return (t3MinusT * _v1 / t3MinusT1) + (tMinusT1 * _v3 / t3MinusT1) +
			_bridgeFactor * Math.sqrt (t3MinusT * tMinusT1 / t3MinusT1);
	}
}
