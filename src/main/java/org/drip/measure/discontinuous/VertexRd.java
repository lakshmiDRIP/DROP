
package org.drip.measure.discontinuous;

import java.util.ArrayList;
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
 * <i>VertexRd</i> holds the R<sup>d</sup> Realizations at the Individual Vertexes. It provides the following
 *  Functionality:
 *
 *  <ul>
 * 		<li>Construct a VertexRd Instance from the R<sup>d</sup> Sequence</li>
 * 		<li>Empty <i>VertexRd</i> Constructor</li>
 * 		<li>Retrieve the Vertex R<sup>d</sup> List</li>
 * 		<li>Add the Vertex Index and its corresponding Realization</li>
 * 		<li>Retrieve the Vertex Realization given the Vertex Index</li>
 * 		<li>Flatten out into a 2D Array</li>
 *  </ul>
 *
 *	<br>
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/README.md">R<sup>d</sup> Continuous/Discrete Probability Measures</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/discontinuous/README.md">Antithetic, Quadratically Re-sampled, De-biased Distribution</a></td></tr>
 *  </table>
 *	<br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class VertexRd
{
	private List<double[]> _nodeList = new ArrayList<double[]>();

	/**
	 * Construct a <i>VertexRd</i> Instance from the R<sup>d</sup> Sequence
	 * 
	 * @param sequenceArray The R<sup>d</sup> Sequence Array
	 * 
	 * @return The <i>VertexRd</i> Instance
	 */

	public static final VertexRd FromFlatForm (
		final double[][] sequenceArray)
	{
		if (null == sequenceArray) {
			return null;
		}

		int sequenceSize = sequenceArray.length;

		if (0 == sequenceSize) {
			return null;
		}

		VertexRd vertexRd = new VertexRd();

		for (int sequenceIndex = 0; sequenceIndex < sequenceSize; ++sequenceIndex) {
			if (null == sequenceArray[sequenceIndex] ||
				!vertexRd.add (sequenceIndex, sequenceArray[sequenceIndex]))
			{
				return null;
			}
		}

		return vertexRd;
	}

	/**
	 * Empty <i>VertexRd</i> Constructor
	 */

	public VertexRd()
	{
	}

	/**
	 * Retrieve the Vertex R<sup>d</sup> List
	 * 
	 * @return The Vertex R<sup>d</sup> List
	 */

	public List<double[]> nodeList()
	{
		return _nodeList;
	}

	/**
	 * Add the Vertex Index and its corresponding Realization
	 * 
	 * @param vertexIndex The Vertex Index
	 * @param realizationArray The R<sup>d</sup> Realization Array
	 * 
	 * @return TRUE - The Vertex Index/Realization successfully added
	 */

	public boolean add (
		final int vertexIndex,
		final double[] realizationArray)
	{
		if (-1 >= vertexIndex ||
			null == realizationArray || 0 == realizationArray.length ||
			!NumberUtil.IsValid (realizationArray))
		{
			return false;
		}

		_nodeList.add (vertexIndex, realizationArray);

		return true;
	}

	/**
	 * Retrieve the Vertex Realization given the Vertex Index
	 * 
	 * @param vertexIndex The Vertex Index
	 * 
	 * @return Array of Vertex Realizations
	 */

	public double[] indexRealization (
		final int vertexIndex)
	{
		return -1 >= vertexIndex ? null : _nodeList.get (vertexIndex);
	}

	/**
	 * Flatten out into a 2D Array
	 * 
	 * @return The 2D Array of the VertexRd Realizations
	 */

	public double[][] flatform()
	{
		int size = _nodeList.size();

		if (0 == size) {
			return null;
		}

		double[][] sequenceArray = new double[size][];

		for (int index = 0; index < size; ++index) {
			sequenceArray[index] = _nodeList.get (index);
		}

		return sequenceArray;
	}
}
