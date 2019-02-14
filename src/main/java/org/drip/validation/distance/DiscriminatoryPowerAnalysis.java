
package org.drip.validation.distance;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
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
 * <i>DiscriminatoryPowerAnalyzer</i> implements the Discriminatory Power Analyzer for the given Sample
 * across the One/More Hypothesis.
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
 *  			Applications to Financial Risk Management, International Economic Review 39 (4) 863-883
 *  	</li>
 *  	<li>
 *  		Kenyon, C., and R. Stamm (2012): Discounting, LIBOR, CVA, and Funding: Interest Rate and Credit
 *  			Pricing, Palgrave Macmillan
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
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ModelValidationAnalyticsLibrary.md">Model Validation Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/validation">Model Validation Suite</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/validation/distance">Hypothesis Target Distance Test Builders</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class DiscriminatoryPowerAnalysis
{
	private org.drip.validation.distance.GapLossFunction _gapLossFunction = null;
	private org.drip.validation.distance.GapLossWeightFunction _gapLossWeightFunction = null;
	private org.drip.validation.hypothesis.ProbabilityIntegralTransform _sampleProbabilityIntegralTransform =
		null;

	/**
	 * Construct a DiscriminatoryPowerAnalysis Instance from the Sample
	 * 
	 * @param sample The Sample Instance
	 * @param gapLossFunction Gap Loss Function
	 * @param gapLossWeightFunction Gap Loss Weight Function
	 * 
	 * @return The DiscriminatoryPowerAnalysis Instance
	 */

	public static final DiscriminatoryPowerAnalysis FromSample (
		final org.drip.validation.evidence.Sample sample,
		final org.drip.validation.distance.GapLossFunction gapLossFunction,
		final org.drip.validation.distance.GapLossWeightFunction gapLossWeightFunction)
	{
		try
		{
			return null == sample ? null : new DiscriminatoryPowerAnalysis (
				sample.nativeProbabilityIntegralTransform(),
				gapLossFunction,
				gapLossWeightFunction
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * DiscriminatoryPowerAnalysis Constructor
	 * 
	 * @param sampleProbabilityIntegralTransform Sample Probability Integral Transform
	 * @param gapLossFunction Gap Loss Function
	 * @param gapLossWeightFunction Gap Loss Weight Function
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public DiscriminatoryPowerAnalysis (
		final org.drip.validation.hypothesis.ProbabilityIntegralTransform sampleProbabilityIntegralTransform,
		final org.drip.validation.distance.GapLossFunction gapLossFunction,
		final org.drip.validation.distance.GapLossWeightFunction gapLossWeightFunction)
		throws java.lang.Exception
	{
		if (null == (_sampleProbabilityIntegralTransform = sampleProbabilityIntegralTransform) ||
			null == (_gapLossFunction = gapLossFunction) ||
			null == (_gapLossWeightFunction = gapLossWeightFunction))
		{
			throw new java.lang.Exception ("DiscriminatoryPowerAnalysis Constructor => Invalid Inputs");
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
	 * Retrieve the Gap Loss Function
	 * 
	 * @return The Gap Loss Function
	 */

	public org.drip.validation.distance.GapLossFunction gapLossFunction()
	{
		return _gapLossFunction;
	}

	/**
	 * Retrieve the Gap Loss Weight Function
	 * 
	 * @return The Gap Loss Weight Function
	 */

	public org.drip.validation.distance.GapLossWeightFunction gapLossWeightFunction()
	{
		return _gapLossWeightFunction;
	}

	/**
	 * Run the Gap Test for the Hypothesis
	 * 
	 * @param hypothesis The ENsemble Hypothesis
	 * 
	 * @return The Sample-Hypothesis Gap Test Outcome
	 */

	public org.drip.validation.distance.GapTestOutcome gapTest (
		final org.drip.validation.evidence.Ensemble hypothesis)
	{
		try
		{
			return null == hypothesis ? null : new
				org.drip.validation.hypothesis.ProbabilityIntegralTransformTest
					(hypothesis.nativeProbabilityIntegralTransform()).distanceTest (
						_sampleProbabilityIntegralTransform,
						_gapLossFunction,
						_gapLossWeightFunction
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
