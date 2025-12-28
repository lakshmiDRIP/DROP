
package org.drip.validation.riskfactorjoint;

import java.util.List;

import org.drip.measure.crng.RandomNumberGenerator;
import org.drip.measure.discrete.CorrelatedPathVertexDimension;
import org.drip.measure.discrete.VertexRd;
import org.drip.measure.identifier.LabelledVertexCorrelation;
import org.drip.measure.identifier.LabelledVertexCovariance;
import org.drip.measure.identifier.LabelledVertexRd;
import org.drip.numerical.common.NumberUtil;
import org.drip.validation.evidence.R1Sample;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2025 Lakshmi Krishnamurthy
 * Copyright (C) 2024 Lakshmi Krishnamurthy
 * Copyright (C) 2023 Lakshmi Krishnamurthy
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
 * <i>NormalLatentStateSampleCohort</i> holds the Joint Realizations from a Multivariate Normal Distribution
 * 	and its Reduction to a Synthetic Single Risk Factor.
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
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/validation/riskfactorjoint/README.md">Joint Risk Factor Aggregate Tests</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class NormalLatentStateSampleCohort
	implements LatentStateSampleCohort
{
	private double _horizon = Double.NaN;
	private LabelledVertexRd _labelRdVertex = null;
	private LabelledVertexCovariance _labelCovariance = null;

	/**
	 * Generate a Correlated <i>NormalLatentStateSampleCohort</i>
	 * 
	 * @param labelList Label List
	 * @param annualMeanArray Array of Annual Means
	 * @param annualVolatilityArray Array of Annual Volatilities
	 * @param correlationMatrix Correlation Matrix
	 * @param vertexCount Vertex Count
	 * @param horizon Horizon
	 * 
	 * @return <i>NormalLatentStateSampleCohort</i> Instance
	 */

	public static final NormalLatentStateSampleCohort Correlated (
		final List<String> labelList,
		final double[] annualMeanArray,
		final double[] annualVolatilityArray,
		final double[][] correlationMatrix,
		final int vertexCount,
		final double horizon)
	{
		if (!NumberUtil.IsValid (horizon)) {
			return null;
		}

		CorrelatedPathVertexDimension correlatedPathVertexDimension = null;

		try {
			correlatedPathVertexDimension = new CorrelatedPathVertexDimension (
				new RandomNumberGenerator(),
				correlationMatrix,
				vertexCount,
				1,
				false,
				null
			);
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}

		VertexRd[] vertexRdArray = correlatedPathVertexDimension.straightMultiPathVertexRd();

		if (null == vertexRdArray || null == vertexRdArray[0]) {
			return null;
		}

		double[][] realization = vertexRdArray[0].flatform();

		if (null == realization) {
			return null;
		}

		double horizonSQRT = Math.sqrt (horizon);

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
			for (int entityIndex = 0; entityIndex < correlationMatrix.length; ++entityIndex) {
				realization[vertexIndex][entityIndex] =
					realization[vertexIndex][entityIndex] * annualVolatilityArray[entityIndex] * horizonSQRT
					+ annualMeanArray[entityIndex] * horizon;
			}
		}

		try {
			return new NormalLatentStateSampleCohort (
				new LabelledVertexRd (labelList, realization),
				new LabelledVertexCovariance (labelList, annualMeanArray, annualVolatilityArray, correlationMatrix),
				horizon
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * <i>NormalLatentStateSampleCohort</i> Constructor
	 * 
	 * @param labelRdVertex R<sup>d</sup> Labeled Vertex
	 * @param labelCovariance R<sup>d</sup> Labeled Covariance
	 * @param horizon Horizon
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public NormalLatentStateSampleCohort (
		final LabelledVertexRd labelRdVertex,
		final LabelledVertexCovariance labelCovariance,
		final double horizon)
		throws Exception
	{
		if (null == (_labelRdVertex = labelRdVertex) ||
			null == (_labelCovariance = labelCovariance) ||
			!NumberUtil.IsValid (_horizon = horizon) || 0. >= _horizon)
		{
			throw new Exception ("NormalLatentStateSampleCohort Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Latent State Label Covariance
	 * 
	 * @return The Latent State Label Covariance
	 */

	public LabelledVertexCorrelation labelCovariance()
	{
		return _labelCovariance;
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

	@Override public List<String> labelList()
	{
		return _labelCovariance.idList();
	}

	@Override public LabelledVertexRd vertexRd()
	{
		return _labelRdVertex;
	}

	@Override public R1Sample reduce (
		final String label1,
		final String label2)
	{
		double annualMean1 = Double.NaN;
		double annualMean2 = Double.NaN;
		double correlation = Double.NaN;
		double annualPrecision1 = Double.NaN;
		double annualPrecision2 = Double.NaN;
		double annualVolatility1 = Double.NaN;
		double annualVolatility2 = Double.NaN;

		try {
			annualMean1 = _labelCovariance.mean (label1);

			annualMean2 = _labelCovariance.mean (label2);

			correlation = _labelCovariance.entry (label1, label2);

			annualPrecision1 = (1. / (annualVolatility1 = _labelCovariance.volatility (label1)));

			annualPrecision2 = (1. / (annualVolatility2 = _labelCovariance.volatility (label2)));
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}

		double[] vertexR1_1 = _labelRdVertex.vertexR1 (label1);

		double[] vertexR1_2 = _labelRdVertex.vertexR1 (label2);

		if (null == vertexR1_1 || null == vertexR1_2) {
			return null;
		}

		int cohortCount = vertexR1_1.length;
		double[] cohortRealization = new double[cohortCount];

		double cohortScale = Math.exp (_horizon * (0.5 * (annualVolatility1 + annualVolatility2) -
			(1. + correlation) - (annualMean1 * annualPrecision1 + annualMean2 * annualPrecision2)));

		for (int cohortIndex = 0; cohortIndex < cohortCount; ++cohortIndex) {
			cohortRealization[cohortIndex] = cohortScale * Math.pow (
				vertexR1_1[cohortIndex],
				annualPrecision1
			) * Math.pow (vertexR1_2[cohortIndex], annualPrecision2);
		}

		try {
			return new R1Sample (cohortRealization);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
