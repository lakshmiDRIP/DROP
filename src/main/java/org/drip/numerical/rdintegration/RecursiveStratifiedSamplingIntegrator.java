
package org.drip.numerical.rdintegration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.drip.function.definition.RdToR1;
import org.drip.measure.distribution.RdContinuous;

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
 * <i>RecursiveStratifiedSamplingIntegrator</i> implements the following routines for Monte-Carlo Integration
 * 	of R<sup>d</sup> To R<sup>1</sup> objective Function. The References are:
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

public class RecursiveStratifiedSamplingIntegrator
	extends UniformSamplingIntegrator
{
	private VarianceSamplingZoneExtractor _varianceSamplingZoneExtractor = null;

	private Map<String, Integer> quadratureZoneEquiSamplingMap (
		final Map<String, QuadratureZoneDecomposerMetric> quadratureZoneDecomposerMetricMap)
	{
		Map<String, Integer> quadratureZoneSamplingMap = new HashMap<String, Integer>();

		int zoneSamplingPointCount = samplingPointCount() / quadratureZoneDecomposerMetricMap.size();

		for (String quadratureZoneName : quadratureZoneDecomposerMetricMap.keySet()) {
			quadratureZoneSamplingMap.put (quadratureZoneName, zoneSamplingPointCount);
		}

		return quadratureZoneSamplingMap;
	}

	private Map<String, Integer> quadratureZoneMISERSamplingMap (
		final Map<String, QuadratureZoneDecomposerMetric> quadratureZoneDecomposerMetricMap)
	{
		Map<String, Integer> quadratureZoneSamplingMap = new HashMap<String, Integer>();

		double cumulativeGrossVarianceProxy = 0.;

		for (String quadratureZoneName : quadratureZoneDecomposerMetricMap.keySet()) {
			cumulativeGrossVarianceProxy +=
				quadratureZoneDecomposerMetricMap.get (quadratureZoneName).grossVarianceProxy();
		}

		int samplingPointCount = samplingPointCount();

		for (String quadratureZoneName : quadratureZoneDecomposerMetricMap.keySet()) {
			quadratureZoneSamplingMap.put (
				quadratureZoneName,
				(int) (
					samplingPointCount * quadratureZoneDecomposerMetricMap.get (
						quadratureZoneName
					).grossVarianceProxy() / cumulativeGrossVarianceProxy
				)
			);
		}

		return quadratureZoneSamplingMap;
	}

	/**
	 * <i>RecursiveStratifiedSamplingIntegrator</i> Constructor
	 * 
	 * @param integratorSetting Underlying <i>RdToR1IntegratorSetting</i> Instance
	 * @param varianceSamplingSetting Variance Sampling Setting Instance
	 * @param samplingPointCount Sampling Points Count
	 * @param rdContinuous Underlying R<sup>d</sup> Continuous Distribution
	 * @param diagnosticsOn TRUE - Diagnostics are turned on
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public RecursiveStratifiedSamplingIntegrator (
		final QuadratureSetting integratorSetting,
		final VarianceSamplingSetting varianceSamplingSetting,
		final int samplingPointCount,
		final RdContinuous rdContinuous,
		final boolean diagnosticsOn)
		throws Exception
	{
		super (integratorSetting, samplingPointCount, rdContinuous, diagnosticsOn);

		_varianceSamplingZoneExtractor = new VarianceSamplingZoneExtractor (
			integratorSetting,
			varianceSamplingSetting
		);
	}

	/**
	 * Retrieve the Variance Sampling Zone Extractor Instance
	 * 
	 * @return Variance Sampling Zone Extractor Instance
	 */

	public VarianceSamplingZoneExtractor varianceSamplingZoneExtractor()
	{
		return _varianceSamplingZoneExtractor;
	}

	/**
	 * Compute the <i>MonteCarloRun</i> Stratified Sampling Run
	 * 
	 * @return <i>MonteCarloRun</i> Stratified Sampling Run
	 */

	@Override public MonteCarloRun quadratureRun()
	{
		boolean diagnosticsOn = diagnosticsOn();

		RdToR1 integrand = integratorSetting().integrand();

		MonteCarloRun monteCarloRun = diagnosticsOn ?
			new MonteCarloRunStratifiedDiagnostics() : new MonteCarloRun();

		Map<String, QuadratureZoneDecomposerMetric> quadratureZoneDecomposerMetricMap =
			new HashMap<String, QuadratureZoneDecomposerMetric>();

		List<BoundedManifold> optimalQuadratureZoneList =
			_varianceSamplingZoneExtractor.optimalQuadratureZoneList (
				monteCarloRun,
				quadratureZoneDecomposerMetricMap,
				rdContinuous()
			);

		if (null == optimalQuadratureZoneList || null == quadratureZoneDecomposerMetricMap) {
			return null;
		}

		Map<String, Integer> quadratureZoneSamplingMap =
			VarianceSamplingSetting.EQUI_QUADRATURE_ZONE_SAMPLING ==
				_varianceSamplingZoneExtractor.varianceSamplingSetting().quadratureZoneSamplingScheme() ?
					quadratureZoneEquiSamplingMap (quadratureZoneDecomposerMetricMap) :
					quadratureZoneMISERSamplingMap (quadratureZoneDecomposerMetricMap);

		for (int zoneIndex = 0; zoneIndex < optimalQuadratureZoneList.size(); ++zoneIndex) {
			MonteCarloRun zoneMonteCarloRun = null;

			BoundedManifold quadratureZone = optimalQuadratureZoneList.get (zoneIndex);

			int samplingPointCount = quadratureZoneSamplingMap.get (quadratureZone.toString());

			try {
				zoneMonteCarloRun = new UniformSamplingIntegrator (
					new QuadratureSetting (integrand, quadratureZone),
					1 >= samplingPointCount ? 2 : samplingPointCount,
					rdContinuous(),
					diagnosticsOn
				).quadratureRun();
			} catch (Exception e) {
				e.printStackTrace();

				return null;
			}

			if (null == zoneMonteCarloRun) {
				return null;
			}

			if (0 == zoneIndex) {
				if (!monteCarloRun.setSamplingPointCount (zoneMonteCarloRun.samplingPointCount()) ||
					!monteCarloRun.setIntegrandMean (zoneMonteCarloRun.integrandMean()) ||
					!monteCarloRun.setIntegrandVolume (zoneMonteCarloRun.integrandVolume()) ||
					!monteCarloRun.setUnbiasedIntegrandVariance (
						zoneMonteCarloRun.unbiasedIntegrandVariance()
					)
				)
				{
					return null;
				}
			} else {
				if (!MonteCarloRun.Merge (monteCarloRun, zoneMonteCarloRun)) {
					return null;
				}
			}

			if (diagnosticsOn) {
				if (!((MonteCarloRunStratifiedDiagnostics) monteCarloRun).setIntegrandSampleArray (
					zoneIndex,
					((MonteCarloRunUniformDiagnostics) zoneMonteCarloRun).integrandSampleArray()
				))
				{
					return null;
				}
			}
		}

		return monteCarloRun;
	}
}
