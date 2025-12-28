
package org.drip.measure.statistics;

import java.util.List;

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
 * <i>UnivariateDiscreteThin</i> analyzes and computes the "Thin" Statistics for the Realized Univariate
 * 	Sequence. It provides the following Functionality:
 *
 *  <ul>
 * 		<li>Generate a <i>UnivariateDiscreteThin</i> Instance from the specified List of Double's</li>
 * 		<li><i>UnivariateDiscreteThin</i> Constructor</li>
 * 		<li>Retrieve the Sequence Average</li>
 * 		<li>Retrieve the Sequence Error</li>
 * 		<li>Retrieve the Sequence Maximum</li>
 * 		<li>Retrieve the Sequence Minimum</li>
 *  </ul>
 *
 *	<br>
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/README.md">R<sup>d</sup> Continuous/Discrete Probability Measures</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/statistics/README.md">R<sup>1</sup> R<sup>d</sup> Thin Thick Moments</a></td></tr>
 *  </table>
 *	<br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class UnivariateDiscreteThin
{
	private double _error = Double.NaN;
	private double _average = Double.NaN;
	private double _maximum = Double.NaN;
	private double _minimum = Double.NaN;

	/**
	 * Generate a <i>UnivariateDiscreteThin</i> Instance from the specified List of Double's
	 * 
	 * @param r1List The List of R<sup>1</sup>
	 * 
	 * @return The <i>UnivariateDiscreteThin</i> Instance
	 */

	public static final UnivariateDiscreteThin FromList (
		final List<Double> r1List)
	{
		if (null == r1List) {
			return null;
		}

		int listSize = r1List.size();

		if (0 == listSize) {
			return null;
		}

		double[] sequence = new double[listSize];

		for (int index = 0; index < listSize; ++index) {
			sequence[index] = r1List.get (index);
		}

		try {
			return new UnivariateDiscreteThin (sequence);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * <i>UnivariateDiscreteThin</i> Constructor
	 * 
	 * @param sequence The Univariate Sequence
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public UnivariateDiscreteThin (
		final double[] sequence)
		throws Exception
	{
		if (null == sequence) {
			throw new Exception ("UnivariateDiscreteThin Constructor => Invalid Inputs");
		}

		_error = 0.;
		_average = 0.;
		_maximum = 0.;
		_minimum = 0.;
		int sequenceSize = sequence.length;

		if (0 == sequenceSize) {
			throw new Exception ("UnivariateDiscreteThin Constructor => Invalid Inputs");
		}

		for (int sequenceIndex = 0; sequenceIndex < sequenceSize; ++sequenceIndex) {
			if (!NumberUtil.IsValid (sequence[sequenceIndex])) {
				throw new Exception ("UnivariateDiscreteThin Constructor => Invalid Inputs");
			}

			if (0 == sequenceIndex) {
				_maximum = sequence[0];
				_minimum = sequence[0];
			} else {
				if (_maximum < sequence[sequenceIndex]) {
					_maximum = sequence[sequenceIndex];
				}

				if (_minimum > sequence[sequenceIndex]) {
					_minimum = sequence[sequenceIndex];
				}
			}

			_average = _average + sequence[sequenceIndex];
		}

		_average /= sequenceSize;

		for (int sequenceIndex = 0; sequenceIndex < sequenceSize; ++sequenceIndex) {
			_error = _error + Math.abs (_average - sequence[sequenceIndex]);
		}

		_error /= sequenceSize;
	}

	/**
	 * Retrieve the Sequence Average
	 * 
	 * @return The Sequence Average
	 */

	public double average()
	{
		return _average;
	}

	/**
	 * Retrieve the Sequence Error
	 * 
	 * @return The Sequence Error
	 */

	public double error()
	{
		return _error;
	}

	/**
	 * Retrieve the Sequence Maximum
	 * 
	 * @return The Sequence Maximum
	 */

	public double maximum()
	{
		return _maximum;
	}

	/**
	 * Retrieve the Sequence Minimum
	 * 
	 * @return The Sequence Minimum
	 */

	public double minimum()
	{
		return _minimum;
	}
}
