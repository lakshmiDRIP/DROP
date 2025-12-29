
package org.drip.measure.discontinuous;

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
 * <i>VertexRd</i> holds the R<sup>d</sup> Realizations at the Individual Vertexes.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/README.md">R<sup>d</sup> Continuous/Discrete Probability Measures</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/discrete/README.md">Antithetic, Quadratically Re-sampled, De-biased Distribution</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class VertexRd {
	private java.util.List<double[]> _lsVertexRd = new java.util.ArrayList<double[]>();

	/**
	 * Construct a VertexRd Instance from the R^d Sequence
	 * 
	 * @param aadblSequence The R^d Sequence
	 * 
	 * @return The VertexRd Instance
	 */

	public static final VertexRd FromFlatForm (
		final double[][] aadblSequence)
	{
		if (null == aadblSequence) return null;

		int iSequenceSize = aadblSequence.length;

		if (0 == iSequenceSize) return null;

		VertexRd vertexRd = new VertexRd();

		for (int iSequence = 0; iSequence < iSequenceSize; ++iSequence) {
			if (null == aadblSequence[iSequence] || !vertexRd.add (iSequence, aadblSequence[iSequence]))
				return null;
		}

		return vertexRd;
	}

	/**
	 * Empty VertexRd Constructor
	 */

	public VertexRd()
	{
	}

	/**
	 * Retrieve the Vertex R^d List
	 * 
	 * @return The Vertex R^d List
	 */

	public java.util.List<double[]> vertexList()
	{
		return _lsVertexRd;
	}

	/**
	 * Add the Vertex Index and its corresponding Realization
	 * 
	 * @param iVertex The Vertex Index
	 * @param adblRealization The R^d Realization Array
	 * 
	 * @return TRUE - The Vertex Index/Realization successfully added
	 */

	public boolean add (
		final int iVertex,
		final double[] adblRealization)
	{
		if (-1 >= iVertex || null == adblRealization || 0 == adblRealization.length ||
			!org.drip.numerical.common.NumberUtil.IsValid (adblRealization))
			return false;

		_lsVertexRd.add (iVertex, adblRealization);

		return true;
	}

	/**
	 * Retrieve the Vertex Realization given the Vertex Index
	 * 
	 * @param iVertex The Vertex Index
	 * 
	 * @return Array of Vertex Realizations
	 */

	public double[] vertexRealization (
		final int iVertex)
	{
		return -1 >= iVertex ? null : _lsVertexRd.get (iVertex);
	}

	/**
	 * Flatten out into a 2D Array
	 * 
	 * @return The 2D Array of the VertexRd Realizations
	 */

	public double[][] flatform()
	{
		int iSize = _lsVertexRd.size();

		if (0 == iSize) return null;

		double[][] aadblSequence = new double[iSize][];

		for (int i = 0; i < iSize; ++i)
			aadblSequence[i] = _lsVertexRd.get (i);

		return aadblSequence;
	}
}
