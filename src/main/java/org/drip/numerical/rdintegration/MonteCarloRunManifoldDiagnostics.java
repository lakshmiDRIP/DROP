
package org.drip.numerical.rdintegration;

import java.util.HashMap;
import java.util.Map;

import org.drip.service.common.ArrayUtil;
import org.drip.service.common.FormatUtil;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2030 Lakshmi Krishnamurthy
 * Copyright (C) 2029 Lakshmi Krishnamurthy
 * Copyright (C) 2028 Lakshmi Krishnamurthy
 * Copyright (C) 2027 Lakshmi Krishnamurthy
 * Copyright (C) 2026 Lakshmi Krishnamurthy
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
 * <i>MonteCarloRunManifoldDiagnostics</i> holds the Results and Diagnostics of a R<sup>d</sup> To
 * 	R<sup>1</sup> Stratified Monte-Carlo Integration Manifold. The References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Kroese, D. P., T. Taimre, and Z. I. Botev (2011): <i>Handbook of Monte Carlo Methods</i>
 * 				<b>John Wiley and Sons</b> Hoboken NJ
 * 		</li>
 * 		<li>
 * 			MacKay, D. (2003): <i>Information Theory, Inference, and Learning Algorithms</i> <b>Cambridge
 * 				University Press</b> New York NY
 * 		</li>
 * 		<li>
 * 			Newman, M. E. J., and G. T. Barkema (1999): <i>Monte Carlo Methods in Statistical Physics</i>
 * 				<b>Oxford University Press</b> Oxford UK
 * 		</li>
 * 		<li>
 * 			Press, W. H., S. A. Teukolsky, W. T. Vetterling, B. P. Flannery (2007): <i>Numerical Recipes: The
 * 				Art of Scientific Computing 3<sup>rd</sup> Edition</i> <b>Cambridge University Press</b> New
 * 				York NY
 * 		</li>
 * 		<li>
 * 			Wikipedia (2025): Monte Carlo Integration https://en.wikipedia.org/wiki/Monte_Carlo_integration
 * 		</li>
 * 	</ul>
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/README.md">Numerical Quadrature, Differentiation, Eigenization, Linear Algebra, and Utilities</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/rdintegration/README.md">R<sup>d</sup> to R<sup>1</sup> Numerical Integration Schemes</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class MonteCarloRunManifoldDiagnostics
{
	private String _manifoldName = "";
	private String[] _childZoneArray = null;
	private Map<String, Double> _rdToValueMap = null;
	private Map<String, Double> _outOfDimensionAnchorMeanMap = null;
	private Map<Integer, Double> _inDimensionVarianceProxyMap = null;
	private Map<String, Double> _outOfDimensionAnchorVarianceMap = null;
	private QuadratureZoneDecomposerMetric _quadratureZoneDecomposerMetric = null;

	/**
	 * MonteCarloRunManifoldDiagnostics Constructor
	 * 
	 * @param manifoldName Manifold Name
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public MonteCarloRunManifoldDiagnostics (
		final String manifoldName)
		throws Exception
	{
		if (null == (_manifoldName = manifoldName) || _manifoldName.isEmpty()) {
			throw new Exception ("MonteCarloRunManifoldDiagnostics Constructor => Invalid Inputs");
		}

		_rdToValueMap = new HashMap<String, Double>();

		_outOfDimensionAnchorMeanMap = new HashMap<String, Double>();

		_inDimensionVarianceProxyMap = new HashMap<Integer, Double>();

		_outOfDimensionAnchorVarianceMap = new HashMap<String, Double>();
	}

	/**
	 * Retrieve the Manifold Name
	 * 
	 * @return Manifold Name
	 */

	public String manifoldName()
	{
		return _manifoldName;
	}

	/**
	 * Retrieve the R<sup>d</sup> Point to Value Map
	 * 
	 * @return R<sup>d</sup> Point to Value Map
	 */

	public Map<String, Double> rdToValueMap()
	{
		return _rdToValueMap;
	}

	/**
	 * Retrieve the R<sup>d</sup> Point to Out-Of-Dimension Anchor Mean Map
	 * 
	 * @return R<sup>d</sup> Point to Out-Of-Dimension Anchor Mean Map
	 */

	public Map<String, Double> outOfDimensionAnchorMeanMap()
	{
		return _outOfDimensionAnchorMeanMap;
	}

	/**
	 * Retrieve the R<sup>d</sup> Point to Out-Of-Dimension Anchor Variance Map
	 * 
	 * @return R<sup>d</sup> Point to Out-Of-Dimension Anchor Variance Map
	 */

	public Map<String, Double> outOfDimensionAnchorVarianceMap()
	{
		return _outOfDimensionAnchorVarianceMap;
	}

	/**
	 * Retrieve the In-Dimension Variance Proxy Map
	 * 
	 * @return In-Dimension Variance Proxy Map
	 */

	public Map<Integer, Double> inDimensionVarianceProxyMap()
	{
		return _inDimensionVarianceProxyMap;
	}

	/**
	 * Retrieve the Quadrature Zone Decomposer Metric
	 * 
	 * @return Quadrature Zone Decomposer Metric
	 */

	public QuadratureZoneDecomposerMetric quadratureZoneDecomposerMetric()
	{
		return _quadratureZoneDecomposerMetric;
	}

	/**
	 * Retrieve the Array of the Divided sub-Quadrature Zones
	 * 
	 * @return Array of the Divided sub-Quadrature Zones
	 */

	public String[] childZoneArray()
	{
		return _childZoneArray;
	}

	/**
	 * Set the R<sup>d</sup> Point Value
	 * 
	 * @param rdArray R<sup>d</sup> Point
	 * @param value Value
	 * 
	 * @return TRUE - R<sup>d</sup> Point Value successfully Set
	 */

	public boolean setRdValue (
		final double[] rdArray,
		final double value)
	{
		if (null == rdArray || 0 == rdArray.length) {
			return false;
		}

		_rdToValueMap.put ("(" + FormatUtil.FormatRd (rdArray, 0, 4, 1., ",") + ")", value);

		return true;
	}

	/**
	 * Set the Out-Of-Dimension Anchor Variance
	 * 
	 * @param rdArray R<sup>d</sup> Point
	 * @param inDimensionIndex In-dimension Index
	 * @param outOfDimensionAnchorMean Out-Of-Dimension Anchor Mean
	 * @param outOfDimensionAnchorVariance Out-Of-Dimension Anchor Variance
	 * 
	 * @return TRUE - Out-Of-Dimension Anchor Variance successfully Set
	 */

	public boolean setOutOfDimensionAnchorMeanAndVariance (
		final double[] rdArray,
		final int inDimensionIndex,
		final double outOfDimensionAnchorMean,
		final double outOfDimensionAnchorVariance)
	{
		if (null == rdArray || 0 == rdArray.length) {
			return false;
		}

		double[] rdOutOfDimensionAnchor = ArrayUtil.Duplicate (rdArray);

		if (null == rdOutOfDimensionAnchor) {
			return false;
		}

		rdOutOfDimensionAnchor[inDimensionIndex] = Double.NaN;

		String outOfDimensionAnchorKey =
			"(" + FormatUtil.FormatRd (rdArray, 0, 4, 1., ",") + "::" + inDimensionIndex + ")";

		_outOfDimensionAnchorMeanMap.put (outOfDimensionAnchorKey, outOfDimensionAnchorMean);

		_outOfDimensionAnchorVarianceMap.put (outOfDimensionAnchorKey, outOfDimensionAnchorVariance);

		return true;
	}

	/**
	 * Set the In-dimension Variance Proxy
	 * 
	 * @param inDimensionIndex In-dimension Index
	 * @param inDimensionVarianceProxy In-dimension Variance Proxy
	 * 
	 * @return In-dimension Variance Proxy
	 */

	public boolean setInDimensionVarianceProxy (
		final int inDimensionIndex,
		final double inDimensionVarianceProxy)
	{
		_inDimensionVarianceProxyMap.put (inDimensionIndex, inDimensionVarianceProxy);

		return true;
	}

	/**
	 * Set the Quadrature Zone Decomposer Metric Instance
	 * 
	 * @param quadratureZoneDecomposerMetric Quadrature Zone Decomposer Metric Instance
	 * 
	 * @return TRUE - The Quadrature Zone Decomposer Metric Instance sucessfully set
	 */

	public boolean setQuadratureZoneDecomposerMetric (
		final QuadratureZoneDecomposerMetric quadratureZoneDecomposerMetric)
	{
		if (null == quadratureZoneDecomposerMetric) {
			return false;
		}

		_quadratureZoneDecomposerMetric = quadratureZoneDecomposerMetric;
		return true;
	}

	/**
	 * Set the Array of sub-quadrature Zones
	 * 
	 * @param childZoneArray Array of sub-quadrature Zones
	 * 
	 * @return TRUE - Array of sub-quadrature Zones successfully Set
	 */

	public boolean setChildZoneArray (
		final String[] childZoneArray)
	{
		if (null == childZoneArray || 2 != childZoneArray.length) {
			return false;
		}

		_childZoneArray = new String[2];
		_childZoneArray[1] = childZoneArray[1];
		_childZoneArray[0] = childZoneArray[0];
		return true;
	}

	/**
	 * Convert the State to a JSON-lite Form using a Prefix
	 * 
	 * @param prefix Prefix
	 * 
	 * @return State to a JSON-lite Form using a Prefix
	 */

	public String toString (
		final String prefix)
	{
		return prefix + "[Manifold Diagnostics => " + _manifoldName + "]" +
			"\n\t" + prefix + "----" +
			"\n\t" + prefix + "{R^d to Integrand Value Map => " + _rdToValueMap + "}" +
			"\n\t" + prefix + "{Out-of-Dimension Anchor Mean Map => " + _outOfDimensionAnchorMeanMap + "}" +
			"\n\t" + prefix + "{Out-of-Dimension Anchor Variance Map => " + _outOfDimensionAnchorVarianceMap
				+ "}" +
			"\n\t" + prefix + "{In Dimension Variance Proxy Map => " + _inDimensionVarianceProxyMap + "}" +
			"\n\t" + _quadratureZoneDecomposerMetric.toString (prefix) +
			"\n\t" + prefix + "{Child Zone => " +
				(null == _childZoneArray ? "" : _childZoneArray[0] + "," + _childZoneArray[1]) + "}";
	}

	/**
	 * Convert the State to a JSON-lite Form
	 * 
	 * @return State to a JSON-lite Form
	 */

	@Override public String toString()
	{
		return toString ("");
	}
}
