
package org.drip.numerical.rdintegration;

import java.util.ArrayList;
import java.util.List;

import org.drip.function.definition.RdToR1;
import org.drip.service.common.ArrayUtil;
import org.drip.service.env.EnvManager;

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
		final MonteCarloRunStratifiedDiagnostics monteCarloRunDiagnostics,
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

			if (null != monteCarloRunDiagnostics) {
				if (!monteCarloRunDiagnostics.setRdValue (
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

	private double outOfDimensionAnchorVarianceProxy (
		final MonteCarloRunStratifiedDiagnostics monteCarloRunDiagnostics,
		final double leftEdgeInDimensionBound,
		final double rightEdgeInDimensionBound,
		final int inDimensionIndex,
		final double[] variateArray)
		throws Exception
	{
		double[] inDimensionIntegrandSampleArray = inDimensionIntegrandSampleArray (
			monteCarloRunDiagnostics,
			leftEdgeInDimensionBound,
			rightEdgeInDimensionBound,
			inDimensionIndex,
			ArrayUtil.Duplicate (variateArray)
		);

		if (null == inDimensionIntegrandSampleArray) {
			throw new Exception (
				"VarianceSamplingZoneExtractor::outOfDimensionAnchorVarianceProxy => Cannot calculate in-dimension Integrand Sample Array"
			);
		}

		double outOfDimensionAnchorMean = 0.;
		double outOfDimensionVarianceProxy = 0.;

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
			outOfDimensionVarianceProxy += sampleGap * sampleGap;
		}

		if (null != monteCarloRunDiagnostics) {
			if (!monteCarloRunDiagnostics.setOutOfDimensionAnchorMeanAndVarianceProxy (
				variateArray,
				inDimensionIndex,
				outOfDimensionAnchorMean,
				outOfDimensionVarianceProxy
			))
			{
				throw new Exception (
					"VarianceSamplingZoneExtractor::outOfDimensionAnchorVarianceProxy => Cannot set out of Dimension Anchor Diagnostics"
				);
			}
		}

		return outOfDimensionVarianceProxy;
	}

	private double inDimensionVarianceProxy (
		final MonteCarloRun monteCarloRun,
		final double[] leftBoundArray,
		final double[] rightBoundArray,
		final int inDimensionIndex)
		throws Exception
	{
		QuadratureZone zone = _quadratureSetting.zone();

		int outOfDimensionEstimationPointCount =
			_varianceSamplingSetting.outOfDimensionEstimationPointCount();

		double[] variateGapArray = new double[_quadratureSetting.integrand().dimension()];

		double[] rightEdgeArray = zone.rightBoundArray();

		double[] leftEdgeArray = zone.leftBoundArray();

		for (int dimensionIndex = 0; dimensionIndex < variateGapArray.length; ++dimensionIndex) {
			variateGapArray[dimensionIndex] = (
				rightEdgeArray[dimensionIndex] - leftEdgeArray[dimensionIndex]
			) / outOfDimensionEstimationPointCount;
		}

		double[] variateArray = ArrayUtil.Duplicate (leftEdgeArray);

		double varianceProxy = outOfDimensionAnchorVarianceProxy (
			null == monteCarloRun || !(monteCarloRun instanceof MonteCarloRunStratifiedDiagnostics) ?
				null : (MonteCarloRunStratifiedDiagnostics) monteCarloRun,
			leftBoundArray[inDimensionIndex],
			rightBoundArray[inDimensionIndex],
			inDimensionIndex,
			variateArray
		);

		for (int outOfDimensionIndex = 1;
			outOfDimensionIndex <= outOfDimensionEstimationPointCount;
			++outOfDimensionIndex)
		{
			for (int dimensionIndex = 0; dimensionIndex < variateGapArray.length; ++dimensionIndex) {
				variateArray[dimensionIndex] += variateGapArray[dimensionIndex];
			}

			varianceProxy += outOfDimensionAnchorVarianceProxy (
				null == monteCarloRun || !(monteCarloRun instanceof MonteCarloRunStratifiedDiagnostics) ?
					null : (MonteCarloRunStratifiedDiagnostics) monteCarloRun,
				leftBoundArray[inDimensionIndex],
				rightBoundArray[inDimensionIndex],
				inDimensionIndex,
				variateArray
			);
		}

		return varianceProxy;
	}

	private QuadratureZoneOptimizationMetric optimizationMetric (
		final MonteCarloRun monteCarloRun,
		final double[] leftBoundArray,
		final double[] rightBoundArray)
	{
		int maximumSwingDimensionIndex = 0;

		try {
			double maximumSwingMetric = inDimensionVarianceProxy (
				monteCarloRun,
				leftBoundArray,
				rightBoundArray,
				0
			);

			for (int dimensionIndex = 1;
				dimensionIndex < _quadratureSetting.integrand().dimension();
				++dimensionIndex)
			{
				double swingMetric = inDimensionVarianceProxy (
					monteCarloRun,
					leftBoundArray,
					rightBoundArray,
					dimensionIndex
				);

				if (swingMetric > maximumSwingMetric) {
					maximumSwingMetric = swingMetric;
					maximumSwingDimensionIndex = dimensionIndex;
				}
			}

			return new QuadratureZoneOptimizationMetric (maximumSwingDimensionIndex, maximumSwingMetric);
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
	 * Sub-divide the Variance Zones
	 * 
	 * @param monteCarloRun <i>MonteCarloRun</i> Stratified Sampling Run
	 * 
	 * @return Array of Divided Variance Zones
	 */

	public QuadratureZone[] subDivideVarianceZones (
		final MonteCarloRun monteCarloRun)
	{
		QuadratureZone quadratureZone = _quadratureSetting.zone();

		QuadratureZoneOptimizationMetric quadratureOptimizationMetric = optimizationMetric (
			monteCarloRun,
			quadratureZone.leftBoundArray(),
			quadratureZone.rightBoundArray()
		);

		return null == quadratureOptimizationMetric ? null : quadratureZone.evenlySplitAcrossDimension (
			quadratureOptimizationMetric.maximumSwingDimensionIndex()
		);
	}

	/**
	 * Compute the List of Optimized Quadrature Zones
	 * 
	 * @param monteCarloRun <i>MonteCarloRun</i> Stratified Sampling Run
	 * @param quadratureZoneList List of Input Quadrature Zones
	 * 
	 * @return List of Optimized Quadrature Zones
	 */

	public List<QuadratureZone> subDivideVarianceZones (
		final MonteCarloRun monteCarloRun,
		final List<QuadratureZone> quadratureZoneList)
	{
		if (null == quadratureZoneList || 0 == quadratureZoneList.size()) {
			return null;
		}

		int quadratureZoneSize = quadratureZoneList.size();

		if (0 == quadratureZoneSize) {
			return null;
		}

		int optimalQuadratureZoneIndex = 0;

		QuadratureZone quadratureZone = quadratureZoneList.get (0);

		QuadratureZoneOptimizationMetric optimalQuadratureOptimizationMetric = optimizationMetric (
			monteCarloRun,
			quadratureZone.leftBoundArray(),
			quadratureZone.rightBoundArray()
		);

		if (null == optimalQuadratureOptimizationMetric) {
			return null;
		}

		for (int quadratureZoneIndex = 1; quadratureZoneIndex < quadratureZoneSize; ++quadratureZoneIndex) {
			quadratureZone = quadratureZoneList.get (quadratureZoneIndex);

			QuadratureZoneOptimizationMetric quadratureOptimizationMetric = optimizationMetric (
				monteCarloRun,
				quadratureZone.leftBoundArray(),
				quadratureZone.rightBoundArray()
			);

			if (quadratureOptimizationMetric.varianceProxy() >
				optimalQuadratureOptimizationMetric.varianceProxy())
			{
				optimalQuadratureZoneIndex = quadratureZoneIndex;
				optimalQuadratureOptimizationMetric = quadratureOptimizationMetric;
			}
		}

		List<QuadratureZone> subDividedQuadratureZoneList = new ArrayList<QuadratureZone>();

		for (int quadratureZoneIndex = 0; quadratureZoneIndex < quadratureZoneSize; ++quadratureZoneIndex) {
			QuadratureZone originalQuadratureZone = quadratureZoneList.get (quadratureZoneIndex);

			if (optimalQuadratureZoneIndex == quadratureZoneIndex) {
				QuadratureZoneOptimizationMetric quadratureOptimizationMetric = optimizationMetric (
					monteCarloRun,
					originalQuadratureZone.leftBoundArray(),
					originalQuadratureZone.rightBoundArray()
				);

				if (null == quadratureOptimizationMetric) {
					return null;
				}

				QuadratureZone[] quadratureZoneArray = originalQuadratureZone.evenlySplitAcrossDimension (
					optimalQuadratureOptimizationMetric.maximumSwingDimensionIndex()
				);

				if (null == quadratureZoneArray) {
					return null;
				}

				subDividedQuadratureZoneList.add (quadratureZoneArray[0]);

				subDividedQuadratureZoneList.add (quadratureZoneArray[1]);
			} else {
				subDividedQuadratureZoneList.add (originalQuadratureZone);
			}
		}

		return subDividedQuadratureZoneList;
	}

	/**
	 * Extract the List of "Optimal" Quadrature Zones
	 * 
	 * @param monteCarloRun <i>MonteCarloRun</i> Stratified Sampling Run
	 * 
	 * @return List of "Optimal" Quadrature Zones
	 */

	public List<QuadratureZone> optimalQuadratureZoneList (
		final MonteCarloRun monteCarloRun)
	{
		List<QuadratureZone> optimalQuadratureZoneList = new ArrayList<QuadratureZone>();

		int zoneIterationCount = _varianceSamplingSetting.zoneIterationCount();

		optimalQuadratureZoneList.add (_quadratureSetting.zone());

		while (0 <= --zoneIterationCount) {
			List<QuadratureZone> augmentedQuadratureZoneList = subDivideVarianceZones (
				monteCarloRun,
				optimalQuadratureZoneList
			);

			if (null == augmentedQuadratureZoneList) {
				return null;
			}

			optimalQuadratureZoneList = augmentedQuadratureZoneList;
		}

		return optimalQuadratureZoneList;
	}

	public static final void main (
		final String[] argumentArray)
		throws Exception
	{
		EnvManager.InitEnv ("");

		int zoneIterationCount = 5;
		int inDimensionEstimationPointCount = 5;
		int outOfDimensionEstimationPointCount = 5;

		double[] leftBoundArray = new double[] {
			0.,
			0.,
			0.
		};

		double[] rightBoundArray = new double[] {
			1.,
			9.,
			4.
		};

		RdToR1 integrand = new RdToR1 (null)
		{
			@Override public int dimension()
			{
				return 3;
			}

			@Override public double evaluate (
				final double[] variateArray)
				throws Exception
			{
				return variateArray[0] *
					variateArray[1] * variateArray[1] * variateArray[1] *
					variateArray[2] * variateArray[2];
			}
		};

		QuadratureZone masterQuadratureZone = new QuadratureZone (leftBoundArray, rightBoundArray);

		VarianceSamplingZoneExtractor varianceSamplingZoneExtractor = new VarianceSamplingZoneExtractor (
			new QuadratureSetting (integrand, masterQuadratureZone),
			new VarianceSamplingSetting (
				zoneIterationCount,
				inDimensionEstimationPointCount,
				outOfDimensionEstimationPointCount
			)
		);

		double masterVolume = masterQuadratureZone.integrandVolume();

		List<QuadratureZone> quadratureZoneList = new ArrayList<QuadratureZone>();

		quadratureZoneList.add (masterQuadratureZone);

		List<QuadratureZone> augmentedQuadratureZoneList =
			varianceSamplingZoneExtractor.subDivideVarianceZones (null, quadratureZoneList);

		System.out.println (augmentedQuadratureZoneList.get (0));

		System.out.println (augmentedQuadratureZoneList.get (1));

		double[] integrandSampleArray = varianceSamplingZoneExtractor.inDimensionIntegrandSampleArray (
			null,
			leftBoundArray[1],
			rightBoundArray[1],
			1,
			rightBoundArray
		);

		for (int i = 0; i < integrandSampleArray.length; ++i) {
			System.out.println (integrandSampleArray[i]);
		}

		System.out.println (
			"\t\t\t" + varianceSamplingZoneExtractor.inDimensionVarianceProxy (
				null,
				leftBoundArray,
				rightBoundArray,
				1
			)
		);

		List<QuadratureZone> optimalQuadratureZoneList =
			varianceSamplingZoneExtractor.optimalQuadratureZoneList (null);

		System.out.println (optimalQuadratureZoneList);

		double quadratureVolumeSum = 0.;

		for (QuadratureZone quadratureZone : optimalQuadratureZoneList) {
			quadratureVolumeSum += quadratureZone.integrandVolume();
		}

		System.out.println ("\t\t" + masterVolume + " | " + quadratureVolumeSum);
	}
}
