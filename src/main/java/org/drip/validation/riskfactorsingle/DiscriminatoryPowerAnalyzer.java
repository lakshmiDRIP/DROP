
package org.drip.validation.riskfactorsingle;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
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
 * <i>DiscriminatoryPowerAnalyzer</i> implements the Discriminatory Power Analyzer for the given Sample
 * across the One/More Hypothesis at a Single Event.
 *
 *  <br><br>
 *  <ul>
 *  	<li>
 *  		Anfuso, F., D. Karyampas, and A. Nawroth (2017): A Sound Basel III Compliant Framework for
 *  			Back-testing Credit Exposure Models
 *  			https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2264620 <b>eSSRN</b>
 *  	</li>
 *  	<li>
 *  		Diebold, F. X., T. A. Gunther, and A. S. Tay (1998): Evaluating Density Forecasts with
 *  			Applications to Financial Risk Management <i>International Economic Review</i> <b>39 (4)</b>
 *  			863-883
 *  	</li>
 *  	<li>
 *  		Kenyon, C., and R. Stamm (2012): <i>Discounting, LIBOR, CVA, and Funding: Interest Rate and
 *  			Credit Pricing</i> <b>Palgrave Macmillan</b>
 *  	</li>
 *  	<li>
 *  		Wikipedia (2018): Probability Integral Transform
 *  			https://en.wikipedia.org/wiki/Probability_integral_transform
 *  	</li>
 *  	<li>
 *  		Wikipedia (2019): p-value https://en.wikipedia.org/wiki/P-value
 *  	</li>
 *  </ul>
 *
 *  <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ModelValidationAnalyticsLibrary.md">Model Validation Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/validation/README.md">Risk Factor and Hypothesis Validation, Evidence Processing, and Model Testing</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/validation/riskfactorsingle/README.md">Single Risk Factor Aggregate Tests</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class DiscriminatoryPowerAnalyzer
{
	private org.drip.validation.distance.GapTestSetting _gapTestSetting = null;
	private org.drip.validation.hypothesis.ProbabilityIntegralTransform _sampleProbabilityIntegralTransform =
		null;

	/**
	 * Construct a DiscriminatoryPowerAnalyzer Instance from the Sample
	 * 
	 * @param sample The Sample Instance
	 * @param gapTestSetting The Distance Gap Test Setting
	 * 
	 * @return The DiscriminatoryPowerAnalyzer Instance
	 */

	public static final DiscriminatoryPowerAnalyzer FromSample (
		final org.drip.validation.evidence.Sample sample,
		final org.drip.validation.distance.GapTestSetting gapTestSetting)
	{
		try
		{
			return null == sample ? null : new DiscriminatoryPowerAnalyzer (
				sample.nativeProbabilityIntegralTransform(),
				gapTestSetting
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * DiscriminatoryPowerAnalyzer Constructor
	 * 
	 * @param sampleProbabilityIntegralTransform Sample Probability Integral Transform
	 * @param gapTestSetting The Distance Gap Test Setting
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public DiscriminatoryPowerAnalyzer (
		final org.drip.validation.hypothesis.ProbabilityIntegralTransform sampleProbabilityIntegralTransform,
		final org.drip.validation.distance.GapTestSetting gapTestSetting)
		throws java.lang.Exception
	{
		if (null == (_sampleProbabilityIntegralTransform = sampleProbabilityIntegralTransform) ||
			null == (_gapTestSetting = gapTestSetting))
		{
			throw new java.lang.Exception ("DiscriminatoryPowerAnalyzer Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Sample Probability Integral Transform
	 * 
	 * @return The Sample Probability Integral Transform
	 */

	public org.drip.validation.hypothesis.ProbabilityIntegralTransform sampleProbabilityIntegralTransform()
	{
		return _sampleProbabilityIntegralTransform;
	}

	/**
	 * Retrieve the Gap Test Setting
	 * 
	 * @return The Gap Test Setting
	 */

	public org.drip.validation.distance.GapTestSetting gapTestSetting()
	{
		return _gapTestSetting;
	}

	/**
	 * Run the Gap Test for the Hypothesis
	 * 
	 * @param hypothesis The Ensemble Hypothesis
	 * 
	 * @return The Sample-Hypothesis Gap Test Outcome
	 */

	public org.drip.validation.distance.GapTestOutcome gapTest (
		final org.drip.validation.evidence.Ensemble hypothesis)
	{
		try
		{
			return null == hypothesis ? null : new
				org.drip.validation.hypothesis.ProbabilityIntegralTransformTest (
					hypothesis.nativeProbabilityIntegralTransform()
				).distanceTest (
					_sampleProbabilityIntegralTransform,
					_gapTestSetting
				);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the Gap Test Outcomes for the specified Hypothesis Suite
	 * 
	 * @param hypothesisSuite The Hypothesis Suite
	 * 
	 * @return The Suite of Gap Test Outcomes
	 */

	public org.drip.validation.distance.HypothesisOutcomeSuite hypothesisGapTest (
		final org.drip.validation.distance.HypothesisSuite hypothesisSuite)
	{
		if (null == hypothesisSuite)
		{
			return null;
		}

		java.util.Map<java.lang.String, org.drip.validation.evidence.Ensemble> hypothesisMap =
			hypothesisSuite.hypothesisMap();

		if (0 == hypothesisMap.size())
		{
			return null;
		}

		org.drip.validation.distance.HypothesisOutcomeSuite hypothesisOutcomeSuite = new
			org.drip.validation.distance.HypothesisOutcomeSuite();

		for (java.util.Map.Entry<java.lang.String, org.drip.validation.evidence.Ensemble> hypothesisMapEntry
			: hypothesisMap.entrySet())
		{
			org.drip.validation.distance.GapTestOutcome gapTestOutcome = gapTest
				(hypothesisMapEntry.getValue());

			if (null == gapTestOutcome)
			{
				continue;
			}

			hypothesisOutcomeSuite.add (
				hypothesisMapEntry.getKey(),
				gapTestOutcome
			);
		}

		return hypothesisOutcomeSuite;
	}
}
