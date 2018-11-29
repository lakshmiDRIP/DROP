
package org.drip.measure.discrete;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting risk, transaction costs, exposure, margin
 *  	calculations, and portfolio construction within and across fixed income, credit, commodity, equity,
 *  	FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three main modules:
 *  
 *  - DROP Analytics Core - https://lakshmidrip.github.io/DROP-Analytics-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Numerical Core - https://lakshmidrip.github.io/DROP-Numerical-Core/
 * 
 * 	DROP Analytics Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Asset Backed Analytics
 * 	- XVA Analytics
 * 	- Exposure and Margin Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Numerical Core implements libraries for the following:
 * 	- Statistical Learning Library
 * 	- Numerical Optimizer Library
 * 	- Machine Learning Library
 * 	- Spline Builder Library
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
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
 * <i>VertexRd</i> holds the R<sup>d</sup> Realizations at the Individual Vertexes.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalOptimizerLibrary.md">Numerical Optimizer Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure">Measure</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/discrete">Discrete</a></li>
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
			!org.drip.quant.common.NumberUtil.IsValid (adblRealization))
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
