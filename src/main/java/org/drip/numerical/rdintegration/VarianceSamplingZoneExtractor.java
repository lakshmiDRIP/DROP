
package org.drip.numerical.rdintegration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.drip.function.definition.RdToR1;
import org.drip.measure.distribution.RdContinuous;
import org.drip.service.common.ArrayUtil;

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
 * <i>VarianceSamplingZoneExtractor</i> implements the Scheme for generating Variance Zones used in Recursive
 * 	Sampling Monte-Carlo Integration of R<sup>d</sup> To R<sup>1</sup> Integrand. The References are:
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

public class VarianceSamplingZoneExtractor
{
	private QuadratureSetting _quadratureSetting = null;
	private VarianceSamplingSetting _varianceSamplingSetting = null;

	private double[] inDimensionIntegrandSampleArray (
		final MonteCarloRunManifoldDiagnostics monteCarloRunManifoldDiagnostics,
		final double leftEdgeInDimensionBound,
		final double rightEdgeInDimensionBound,
		final int inDimensionIndex,
		final double[] variateArray)
	{
		RdToR1 integrand = _quadratureSetting.integrand();

		int inDimensionEstimationPointCount = _varianceSamplingSetting.inDimensionEstimationPointCount();

		double[] inDimensionIntegrandSampleArray = new double[inDimensionEstimationPointCount + 1];
		double inDimensionRange = (rightEdgeInDimensionBound - leftEdgeInDimensionBound) /
			inDimensionEstimationPointCount;

		for (int inDimensionPointIndex = 0;
			inDimensionPointIndex <= inDimensionEstimationPointCount;
			++inDimensionPointIndex)
		{
			variateArray[inDimensionIndex] =
				leftEdgeInDimensionBound + inDimensionPointIndex * inDimensionRange;

			try {
				inDimensionIntegrandSampleArray[inDimensionPointIndex] = integrand.evaluate (variateArray);
			} catch (Exception e) {
				e.printStackTrace();

				return null;
			}

			if (null != monteCarloRunManifoldDiagnostics) {
				if (!monteCarloRunManifoldDiagnostics.setRdValue (
					variateArray,
					inDimensionIntegrandSampleArray[inDimensionPointIndex]
				))
				{
					return null;
				}
			}
		}

		return inDimensionIntegrandSampleArray;
	}

	private double outOfDimensionAnchorVariance (
		final MonteCarloRunManifoldDiagnostics monteCarloRunManifoldDiagnostics,
		final double leftEdgeInDimensionBound,
		final double rightEdgeInDimensionBound,
		final int inDimensionIndex,
		final double[] variateArray)
		throws Exception
	{
		double[] inDimensionIntegrandSampleArray = inDimensionIntegrandSampleArray (
			monteCarloRunManifoldDiagnostics,
			leftEdgeInDimensionBound,
			rightEdgeInDimensionBound,
			inDimensionIndex,
			ArrayUtil.Duplicate (variateArray)
		);

		if (null == inDimensionIntegrandSampleArray) {
			throw new Exception (
				"VarianceSamplingZoneExtractor::outOfDimensionAnchorVariance => Cannot calculate in-dimension Integrand Sample Array"
			);
		}

		double outOfDimensionAnchorMean = 0.;
		double outOfDimensionAnchorVariance = 0.;

		for (int inDimensionSampleIndex = 0;
			inDimensionSampleIndex < inDimensionIntegrandSampleArray.length;
			++inDimensionSampleIndex)
		{
			outOfDimensionAnchorMean += inDimensionIntegrandSampleArray[inDimensionSampleIndex];
		}

		outOfDimensionAnchorMean /= inDimensionIntegrandSampleArray.length;

		for (int inDimensionSampleIndex = 0;
			inDimensionSampleIndex < inDimensionIntegrandSampleArray.length;
			++inDimensionSampleIndex)
		{
			double sampleGap =
				inDimensionIntegrandSampleArray[inDimensionSampleIndex] - outOfDimensionAnchorMean;
			outOfDimensionAnchorVariance += sampleGap * sampleGap;
		}

		if (null != monteCarloRunManifoldDiagnostics) {
			if (!monteCarloRunManifoldDiagnostics.setOutOfDimensionAnchorMeanAndVariance (
				variateArray,
				inDimensionIndex,
				outOfDimensionAnchorMean,
				outOfDimensionAnchorVariance
			))
			{
				throw new Exception (
					"VarianceSamplingZoneExtractor::outOfDimensionAnchorVariance => Cannot set out of Dimension Anchor Diagnostics"
				);
			}
		}

		return outOfDimensionAnchorVariance / inDimensionIntegrandSampleArray.length;
	}

	private double inDimensionVarianceProxy (
		final MonteCarloRunManifoldDiagnostics monteCarloRunManifoldDiagnostics,
		final BoundedManifold boundedManifold,
		final RdContinuous rdContinuous,
		final double inDimensionLeftBound,
		final double inDimensionRightBound,
		final int inDimensionIndex)
		throws Exception
	{
		int outOfDimensionEstimationPointCount =
			_varianceSamplingSetting.outOfDimensionEstimationPointCount();

		double inDimensionVarianceProxy = outOfDimensionAnchorVariance (
			monteCarloRunManifoldDiagnostics,
			inDimensionLeftBound,
			inDimensionRightBound,
			inDimensionIndex,
			boundedManifold.randomRd (UniformSamplingIntegrator.VALID_BOUNDED_VARIATE_TRIAL, rdContinuous)
		);

		for (int outOfDimensionIndex = 1;
			outOfDimensionIndex <= outOfDimensionEstimationPointCount;
			++outOfDimensionIndex)
		{
			inDimensionVarianceProxy += outOfDimensionAnchorVariance (
				monteCarloRunManifoldDiagnostics,
				inDimensionLeftBound,
				inDimensionRightBound,
				inDimensionIndex,
				boundedManifold.randomRd (
					UniformSamplingIntegrator.VALID_BOUNDED_VARIATE_TRIAL,
					rdContinuous
				)
			);
		}

		if (null != monteCarloRunManifoldDiagnostics) {
			if (!monteCarloRunManifoldDiagnostics.setInDimensionVarianceProxy (
				inDimensionIndex,
				inDimensionVarianceProxy
			))
			{
				throw new Exception (
					"VarianceSamplingZoneExtractor::inDimensionVarianceProxy => Cannot set in Dimension Variance Proxy Diagnostics"
				);
			}
		}

		return inDimensionVarianceProxy;
	}

	private QuadratureZoneDecomposerMetric quadratureZoneDecomposerMetric (
		final MonteCarloRunManifoldDiagnostics monteCarloRunManifoldDiagnostics,
		final BoundedManifold boundedManifold,
		final RdContinuous rdContinuous)
	{
		double grossVarianceProxy = 0.;
		int peakVarianceProxyDimension = 0;

		double[] leftBoundArray = boundedManifold.leftCartesianBoundArray();

		double[] rightBoundArray = boundedManifold.rightCartesianBoundArray();

		try {
			double peakVarianceProxy = inDimensionVarianceProxy (
				monteCarloRunManifoldDiagnostics,
				boundedManifold,
				rdContinuous,
				leftBoundArray[0],
				rightBoundArray[0],
				0
			);

			grossVarianceProxy += peakVarianceProxy;

			for (int dimensionIndex = 1; dimensionIndex < leftBoundArray.length; ++dimensionIndex) {
				double inDimensionVarianceProxy = inDimensionVarianceProxy (
					monteCarloRunManifoldDiagnostics,
					boundedManifold,
					rdContinuous,
					leftBoundArray[dimensionIndex],
					rightBoundArray[dimensionIndex],
					dimensionIndex
				);

				grossVarianceProxy += inDimensionVarianceProxy;

				if (inDimensionVarianceProxy > peakVarianceProxy) {
					peakVarianceProxy = inDimensionVarianceProxy;
					peakVarianceProxyDimension = dimensionIndex;
				}
			}

			QuadratureZoneDecomposerMetric quadratureZoneDecomposerMetric =
				new QuadratureZoneDecomposerMetric (
					peakVarianceProxyDimension,
					peakVarianceProxy,
					grossVarianceProxy
				);

			if (null != monteCarloRunManifoldDiagnostics) {
				if (!monteCarloRunManifoldDiagnostics.setQuadratureZoneDecomposerMetric (
					quadratureZoneDecomposerMetric
				))
				{
					return null;
				}
			}

			return quadratureZoneDecomposerMetric;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * VarianceSamplingZoneExtractor Constructor
	 * 
	 * @param quadratureSetting Underlying Quadrature Setting Instance
	 * @param varianceSamplingSetting <i>VarianceSamplingSetting</i> Instance
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public VarianceSamplingZoneExtractor (
		final QuadratureSetting quadratureSetting,
		final VarianceSamplingSetting varianceSamplingSetting)
		throws Exception
	{
		if (null == (_quadratureSetting = quadratureSetting) ||
			null == (_varianceSamplingSetting = varianceSamplingSetting))
		{
			throw new Exception ("VarianceSamplingZoneExtractor Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Underlying Quadrature Setting Instance
	 * 
	 * @return Underlying Quadrature Setting Instance
	 */

	public QuadratureSetting quadratureSetting()
	{
		return _quadratureSetting;
	}

	/**
	 * Retrieve the <i>VarianceSamplingSetting</i> Instance
	 * 
	 * @return <i>VarianceSamplingSetting</i> Instance
	 */

	public VarianceSamplingSetting varianceSamplingSetting()
	{
		return _varianceSamplingSetting;
	}

	/**
	 * Compute the List of Optimized Quadrature Zones
	 * 
	 * @param monteCarloRunStratifiedDiagnostics <i>MonteCarloRun</i> Stratified Diagnostics
	 * @param quadratureZoneDecomposerMetricMap Map of Quadrature Zone Decomposer Metrics
	 * @param quadratureZoneList List of Input Quadrature Zones
	 * @param rdContinuous Sampling R<sup>d</sup> Continuous Distribution
	 * 
	 * @return List of Optimized Quadrature Zones
	 */

	public List<BoundedManifold> subDivideVarianceZones (
		final MonteCarloRunStratifiedDiagnostics monteCarloRunStratifiedDiagnostics,
		final Map<String, QuadratureZoneDecomposerMetric> quadratureZoneDecomposerMetricMap,
		final List<BoundedManifold> quadratureZoneList,
		final RdContinuous rdContinuous)
	{
		if (null == quadratureZoneList || 0 == quadratureZoneList.size()) {
			return null;
		}

		int quadratureZoneSize = quadratureZoneList.size();

		if (0 == quadratureZoneSize) {
			return null;
		}

		int optimalQuadratureZoneIndex = 0;
		MonteCarloRunManifoldDiagnostics monteCarloRunSubManifoldDiagnostics = null;
		QuadratureZoneDecomposerMetric optimalQuadratureZoneDecomposerMetric = null;

		BoundedManifold quadratureZone = quadratureZoneList.get (0);

		String quadratureZoneName = quadratureZone.toString();

		if (null != monteCarloRunStratifiedDiagnostics) {
			try {
				if (!monteCarloRunStratifiedDiagnostics.addManifold (
					monteCarloRunSubManifoldDiagnostics =
						new MonteCarloRunManifoldDiagnostics (quadratureZoneName)
				))
				{
					return null;
				}
			} catch (Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		if (quadratureZoneDecomposerMetricMap.containsKey (quadratureZoneName)) {
			optimalQuadratureZoneDecomposerMetric =
				quadratureZoneDecomposerMetricMap.get (quadratureZoneName);
		} else {
			QuadratureZoneDecomposerMetric quadratureZoneDecomposerMetric = quadratureZoneDecomposerMetric (
				monteCarloRunSubManifoldDiagnostics,
				quadratureZone,
				rdContinuous
			);

			if (null == quadratureZoneDecomposerMetric) {
				return null;
			}

			quadratureZoneDecomposerMetricMap.put (quadratureZoneName, quadratureZoneDecomposerMetric);

			optimalQuadratureZoneDecomposerMetric = quadratureZoneDecomposerMetric;
		}

		for (int quadratureZoneIndex = 1; quadratureZoneIndex < quadratureZoneSize; ++quadratureZoneIndex) {
			monteCarloRunSubManifoldDiagnostics = null;
			QuadratureZoneDecomposerMetric quadratureZoneDecomposerMetric = null;

			quadratureZone = quadratureZoneList.get (quadratureZoneIndex);

			quadratureZoneName = quadratureZone.toString();

			if (null != monteCarloRunStratifiedDiagnostics) {
				try {
					if (!monteCarloRunStratifiedDiagnostics.addManifold (
						monteCarloRunSubManifoldDiagnostics =
							new MonteCarloRunManifoldDiagnostics (quadratureZoneName)
					))
					{
						return null;
					}
				} catch (Exception e) {
					e.printStackTrace();

					return null;
				}
			}

			if (quadratureZoneDecomposerMetricMap.containsKey (quadratureZoneName)) {
				quadratureZoneDecomposerMetric =
					quadratureZoneDecomposerMetricMap.get (quadratureZoneName);
			} else {
				quadratureZoneDecomposerMetric = quadratureZoneDecomposerMetric (
					monteCarloRunSubManifoldDiagnostics,
					quadratureZone,
					rdContinuous
				);

				if (null == quadratureZoneDecomposerMetric) {
					return null;
				}

				quadratureZoneDecomposerMetricMap.put (quadratureZoneName, quadratureZoneDecomposerMetric);
			}

			if (quadratureZoneDecomposerMetric.peakVarianceProxy() >
				optimalQuadratureZoneDecomposerMetric.peakVarianceProxy())
			{
				optimalQuadratureZoneIndex = quadratureZoneIndex;
				optimalQuadratureZoneDecomposerMetric = quadratureZoneDecomposerMetric;
			}
		}

		List<BoundedManifold> subDividedQuadratureZoneList = new ArrayList<BoundedManifold>();

		for (int quadratureZoneIndex = 0; quadratureZoneIndex < quadratureZoneSize; ++quadratureZoneIndex) {
			BoundedManifold originalQuadratureZone = quadratureZoneList.get (quadratureZoneIndex);

			if (optimalQuadratureZoneIndex == quadratureZoneIndex) {
				BoundedManifold[] quadratureZoneArray = originalQuadratureZone.evenlySplitAcrossDimension (
					optimalQuadratureZoneDecomposerMetric.peakVarianceProxyDimension()
				);

				if (null == quadratureZoneArray) {
					return null;
				}

				subDividedQuadratureZoneList.add (quadratureZoneArray[0]);

				QuadratureZoneDecomposerMetric quadratureZoneDecomposerMetric = quadratureZoneDecomposerMetric (
					monteCarloRunSubManifoldDiagnostics,
					quadratureZoneArray[0],
					rdContinuous
				);

				if (null == quadratureZoneDecomposerMetric) {
					return null;
				}

				quadratureZoneDecomposerMetricMap.put (
					quadratureZoneArray[0].toString(),
					quadratureZoneDecomposerMetric
				);

				subDividedQuadratureZoneList.add (quadratureZoneArray[1]);

				quadratureZoneDecomposerMetric = quadratureZoneDecomposerMetric (
					monteCarloRunSubManifoldDiagnostics,
					quadratureZoneArray[1],
					rdContinuous
				);

				if (null == quadratureZoneDecomposerMetric) {
					return null;
				}

				quadratureZoneDecomposerMetricMap.put (
					quadratureZoneArray[1].toString(),
					quadratureZoneDecomposerMetric
				);

				if (null != monteCarloRunStratifiedDiagnostics) {
					MonteCarloRunManifoldDiagnostics manifoldDiagnostics =
						monteCarloRunStratifiedDiagnostics.manifold (originalQuadratureZone.toString());

					if (null != manifoldDiagnostics) {
						if (!manifoldDiagnostics.setChildZoneArray (
							new String[] {
								quadratureZoneArray[0].toString(),
								quadratureZoneArray[1].toString()
							}
						))
						{
							return null;
						}
					}
				}
			} else {
				subDividedQuadratureZoneList.add (originalQuadratureZone);
			}
		}

		return subDividedQuadratureZoneList;
	}

	/**
	 * Extract the List of "Optimal" Quadrature Zones
	 * 
	 * @param monteCarloRun <i>MonteCarloRun</i> Stratified Diagnostics
	 * @param quadratureZoneDecomposerMetricMap Quadrature Zone Decomposer Metric Map
	 * @param rdContinuous Sampling R<sup>d</sup> Continuous Distribution
	 * 
	 * @return List of "Optimal" Quadrature Zones
	 */

	public List<BoundedManifold> optimalQuadratureZoneList (
		final MonteCarloRun monteCarloRun,
		final Map<String, QuadratureZoneDecomposerMetric> quadratureZoneDecomposerMetricMap,
		final RdContinuous rdContinuous)
	{
		List<BoundedManifold> optimalQuadratureZoneList = new ArrayList<BoundedManifold>();

		int zoneIterationCount = _varianceSamplingSetting.zoneIterationCount();

		optimalQuadratureZoneList.add (_quadratureSetting.boundedManifold());

		while (0 <= --zoneIterationCount) {
			List<BoundedManifold> augmentedQuadratureZoneList = subDivideVarianceZones (
				monteCarloRun instanceof MonteCarloRunStratifiedDiagnostics ?
					(MonteCarloRunStratifiedDiagnostics) monteCarloRun : null,
				quadratureZoneDecomposerMetricMap,
				optimalQuadratureZoneList,
				rdContinuous
			);

			if (null == augmentedQuadratureZoneList) {
				return null;
			}

			optimalQuadratureZoneList = augmentedQuadratureZoneList;
		}

		return optimalQuadratureZoneList;
	}
}
