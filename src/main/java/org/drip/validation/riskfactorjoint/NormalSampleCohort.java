
package org.drip.validation.riskfactorjoint;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting analytics/risk, transaction cost analytics,
 *  	asset liability management analytics, capital, exposure, and margin analytics, valuation adjustment
 *  	analytics, and portfolio construction analytics within and across fixed income, credit, commodity,
 *  	equity, FX, and structured products. It also includes auxiliary libraries for algorithm support,
 *  	numerical analysis, numerical optimization, spline builder, model validation, statistical learning,
 *  	and computational support.
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
 * <i>NormalSampleCohort</i> holds the Joint Realizations from a Multivariate Normal Distribution and its
 * Reduction to a Synthetic Single Risk Factor.
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
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ModelValidationAnalyticsLibrary.md">Model Validation Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/validation/README.md">Risk Factor and Hypothesis Validation, Evidence Processing, and Model Testing</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/validation/riskfactorjoint/README.md">Joint Risk Factor Aggregate Tests</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class NormalSampleCohort implements org.drip.validation.riskfactorjoint.SampleCohort
{
	private double _horizon = java.lang.Double.NaN;
	private org.drip.measure.stochastic.LabelRdVertex _labelRdVertex = null;
	private org.drip.measure.stochastic.LabelCovariance _latentStateLabelCovariance = null;

	/**
	 * Generate a Correlated NormalSampleCohort
	 * 
	 * @param labelList Label List
	 * @param annualMeanArray Array of Annual Means
	 * @param annualVolatilityArray Array of Annual Volatilities
	 * @param correlationMatrix Correlation Matrix
	 * @param vertexCount Vertex Count
	 * @param horizon Horizon
	 * 
	 * @return NormalSampleCohort Instance
	 */

	public static final NormalSampleCohort Correlated (
		final java.util.List<java.lang.String> labelList,
		final double[] annualMeanArray,
		final double[] annualVolatilityArray,
		final double[][] correlationMatrix,
		final int vertexCount,
		final double horizon)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (horizon))
		{
			return null;
		}

		org.drip.measure.discrete.CorrelatedPathVertexDimension correlatedPathVertexDimension = null;

		try
		{
			correlatedPathVertexDimension = new org.drip.measure.discrete.CorrelatedPathVertexDimension (
				new org.drip.measure.crng.RandomNumberGenerator(),
				correlationMatrix,
				vertexCount,
				1,
				false,
				null
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();

			return null;
		}

		org.drip.measure.discrete.VertexRd[] vertexRdArray =
			correlatedPathVertexDimension.straightMultiPathVertexRd();

		if (null == vertexRdArray || null == vertexRdArray[0])
		{
			return null;
		}

		double[][] realization = vertexRdArray[0].flatform();

		if (null == realization)
		{
			return null;
		}

		double horizonSQRT = Math.sqrt (horizon);

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
		{
			for (int entityIndex = 0; entityIndex < correlationMatrix.length; ++entityIndex)
			{
				realization[vertexIndex][entityIndex] =
					realization[vertexIndex][entityIndex] * annualVolatilityArray[entityIndex] * horizonSQRT +
					annualMeanArray[entityIndex] * horizon;
			}
		}

		try
		{
			return new NormalSampleCohort (
				new org.drip.measure.stochastic.LabelRdVertex (
					labelList,
					realization
				),
				new org.drip.measure.stochastic.LabelCovariance (
					labelList,
					annualMeanArray,
					annualVolatilityArray,
					correlationMatrix
				),
				horizon
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * NormalSampleCohort Constructor
	 * 
	 * @param labelRdVertex R<sup>d</sup> Labeled Vertex
	 * @param latentStateLabelCovariance R<sup>d</sup> Labeled Covariance
	 * @param horizon Horizon
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public NormalSampleCohort (
		final org.drip.measure.stochastic.LabelRdVertex labelRdVertex,
		final org.drip.measure.stochastic.LabelCovariance latentStateLabelCovariance,
		final double horizon)
		throws java.lang.Exception
	{
		if (null == (_labelRdVertex = labelRdVertex) ||
			null == (_latentStateLabelCovariance = latentStateLabelCovariance) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_horizon = horizon) || _horizon <= 0.)
		{
			throw new java.lang.Exception ("NormalSampleCohort Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Latent State Label Covariance
	 * 
	 * @return The Latent State Label Covariance
	 */

	public org.drip.measure.stochastic.LabelCorrelation latentStateLabelCovariance()
	{
		return _latentStateLabelCovariance;
	}

	/**
	 * Retrieve the Sample Horizon
	 * 
	 * @return The Sample Horizon
	 */

	public double horizon()
	{
		return _horizon;
	}

	@Override public java.util.List<java.lang.String> latentStateLabelList()
	{
		return _latentStateLabelCovariance.labelList();
	}

	@Override public org.drip.measure.stochastic.LabelRdVertex vertexRd()
	{
		return _labelRdVertex;
	}

	@Override public org.drip.validation.evidence.Sample reduce (
		final java.lang.String label1,
		final java.lang.String label2)
	{
		double annualMean1 = java.lang.Double.NaN;
		double annualMean2 = java.lang.Double.NaN;
		double correlation = java.lang.Double.NaN;
		double annualPrecision1 = java.lang.Double.NaN;
		double annualPrecision2 = java.lang.Double.NaN;
		double annualVolatility1 = java.lang.Double.NaN;
		double annualVolatility2 = java.lang.Double.NaN;

		try
		{
			correlation = _latentStateLabelCovariance.entry (
				label1,
				label2
			);

			annualMean1 = _latentStateLabelCovariance.mean (label1);

			annualMean2 = _latentStateLabelCovariance.mean (label2);

			annualPrecision1 = (1. / (annualVolatility1 = _latentStateLabelCovariance.volatility (label1)));

			annualPrecision2 = (1. / (annualVolatility2 = _latentStateLabelCovariance.volatility (label2)));
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();

			return null;
		}

		double[] vertexR1_1 = _labelRdVertex.vertexR1 (label1);

		double[] vertexR1_2 = _labelRdVertex.vertexR1 (label2);

		if (null == vertexR1_1 || null == vertexR1_2)
		{
			return null;
		}

		int cohortCount = vertexR1_1.length;
		double[] cohortRealization = new double[cohortCount];
		double cohortScale = java.lang.Math.exp (_horizon * (0.5 * (annualVolatility1 + annualVolatility2) -
			(1. + correlation) - (annualMean1 * annualPrecision1 + annualMean2 * annualPrecision2)));

		for (int cohortIndex = 0; cohortIndex < cohortCount; ++cohortIndex)
		{
			cohortRealization[cohortIndex] = cohortScale * java.lang.Math.pow (
				vertexR1_1[cohortIndex],
				annualPrecision1
			) * java.lang.Math.pow (
				vertexR1_2[cohortIndex],
				annualPrecision2
			);
		}

		try
		{
			return new org.drip.validation.evidence.Sample (cohortRealization);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
