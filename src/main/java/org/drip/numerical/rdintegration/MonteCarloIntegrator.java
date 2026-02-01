
package org.drip.numerical.rdintegration;

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
 * <i>MonteCarloIntegrator</i> implements the following routines for Monte-Carlo Integration of R<sup>d</sup>
 * 	To R<sup>1</sup> objective Function. The References are:
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

public abstract class MonteCarloIntegrator
{
	private int _samplingPointCount = 0;
	private boolean _diagnosticsOn = false;
	private QuadratureSetting _integratorSetting = null;

	private MonteCarloRun quadratureRun (
		final double[] integrandValueArray)
	{
		double cumulativeIntegrandValue = 0.;
		double cumulativeIntegrandSquaredValue = 0.;

		for (int integrandValueIndex = 0;
			integrandValueIndex < integrandValueArray.length;
			++integrandValueIndex)
		{
			cumulativeIntegrandValue += integrandValueArray[integrandValueIndex];
			cumulativeIntegrandSquaredValue +=
				integrandValueArray[integrandValueIndex] * integrandValueArray[integrandValueIndex];
		}

		double integrandMean = cumulativeIntegrandValue / _samplingPointCount;

		try {
			return _diagnosticsOn ? MonteCarloRunDiagnostics.Standard (
				_samplingPointCount,
				integrandMean,
				_integratorSetting.zone().integrandVolume(),
				(cumulativeIntegrandSquaredValue - (_samplingPointCount * integrandMean * integrandMean)) /
					(_samplingPointCount - 1)
			) : MonteCarloRun.Standard (
				_samplingPointCount,
				integrandMean,
				_integratorSetting.zone().integrandVolume(),
				(cumulativeIntegrandSquaredValue - (_samplingPointCount * integrandMean * integrandMean)) /
					(_samplingPointCount - 1)
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * <i>MonteCarloIntegrator</i> Constructor
	 * 
	 * @param integratorSetting Underlying <i>RdToR1IntegratorSetting</i> Instance
	 * @param samplingPointCount Sampling Points Count
	 * @param diagnosticsOn TRUE - Diagnostics are turned on
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public MonteCarloIntegrator (
		final QuadratureSetting integratorSetting,
		final int samplingPointCount,
		final boolean diagnosticsOn)
		throws Exception
	{
		if (null == (_integratorSetting = integratorSetting) ||
			0 >= (_samplingPointCount = samplingPointCount))
		{
			throw new Exception ("MonteCarloIntegrator Constructor => Invalid Inputs");
		}

		_diagnosticsOn = diagnosticsOn;
	}

	/**
	 * Retrieve the Underlying <i>RdToR1IntegratorSetting</i> Instance
	 * 
	 * @return Underlying <i>RdToR1IntegratorSetting</i> Instance
	 */

	public QuadratureSetting integratorSetting()
	{
		return _integratorSetting;
	}

	/**
	 * Retrieve the Count of Sampling Points
	 * 
	 * @return Count of Sampling Points
	 */

	public int samplingPointCount()
	{
		return _samplingPointCount;
	}

	/**
	 * Indicate if Diagnostics are turned on
	 * 
	 * @return TRUE - Diagnostics are turned on
	 */

	public boolean diagnosticsOn()
	{
		return _diagnosticsOn;
	}

	/**
	 * Generate the Array of Integrand Sampling Points needed for computing the Quadrature
	 * 
	 * @return Array of Integrand Sampling Points needed for computing the Quadrature
	 */

	public abstract double[] integrandSampleArray();

	/**
	 * Compute the <i>RdToR1MonteCarloRun</i> Uniform Sampling Run
	 * 
	 * @return <i>RdToR1MonteCarloRun</i> Uniform Sampling Run
	 */

	public MonteCarloRun quadratureRun()
	{
		double[] integrandSampleArray = integrandSampleArray();

		if (null == integrandSampleArray) {
			return null;
		}

		MonteCarloRun monteCarloRun = quadratureRun (integrandSampleArray);

		if (null == monteCarloRun) {
			return null;
		}

		if (monteCarloRun instanceof MonteCarloRunDiagnostics) {
			if (!((MonteCarloRunDiagnostics) monteCarloRun).setIntegrandSampleArray (integrandSampleArray)) {
				return null;
			}
		}

		return monteCarloRun;
	}
}
