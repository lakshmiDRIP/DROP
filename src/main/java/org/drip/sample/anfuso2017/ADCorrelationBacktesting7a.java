
package org.drip.sample.anfuso2017;

import java.util.ArrayList;
import java.util.List;

import org.drip.measure.crng.RandomNumberGenerator;
import org.drip.measure.discrete.CorrelatedPathVertexDimension;
import org.drip.measure.stochastic.LabelCovariance;
import org.drip.measure.stochastic.LabelRdVertex;
import org.drip.service.env.EnvManager;
import org.drip.state.identifier.EntityEquityLabel;
import org.drip.state.identifier.FXLabel;
import org.drip.validation.evidence.Sample;
import org.drip.validation.riskfactorjoint.NormalSampleCohort;

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
 * <i>ADCorrelationBacktesting7a</i> demonstrates the Horizon Multi-Factor Gap PIT Quantiles set out in Table
 * 7a of Anfuso, Karyampas, and Nawroth (2017).
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
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ModelValidationLibrary.md">Model Validation Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">Sample</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/validation/anfuso2013">Anfuso, Karyampas, and Nawroth (2013) Replications</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class ADCorrelationBacktesting7a {

	private static final Sample CorrelatedSample (
		final List<String> labelList,
		final double[] annualMeanArray,
		final double[] annualVolatilityArray,
		final double[][] correlationMatrix,
		final int vertexCount,
		final double horizon)
		throws Exception
	{
		double horizonSQRT = Math.sqrt (horizon);

		CorrelatedPathVertexDimension correlatedPathVertexDimension = new CorrelatedPathVertexDimension (
			new RandomNumberGenerator(),
			correlationMatrix,
			vertexCount,
			1,
			false,
			null
		);

		double[][] realization =
			correlatedPathVertexDimension.straightMultiPathVertexRd()[0].flatform();

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
		{
			for (int entityIndex = 0; entityIndex < correlationMatrix.length; ++entityIndex)
			{
				realization[vertexIndex][entityIndex] =
					realization[vertexIndex][entityIndex] * annualVolatilityArray[entityIndex] * horizonSQRT +
					annualMeanArray[entityIndex] * horizon;
			}
		}

		return new NormalSampleCohort (
			new LabelRdVertex (
				labelList,
				realization
			),
			new LabelCovariance (
				labelList,
				annualMeanArray,
				annualVolatilityArray,
				correlationMatrix
			),
			horizon
		).reduce (
			labelList.get (0),
			labelList.get (1)
		);
	}

	public static final void main (
		final String[] argumentArray)
		throws Exception
	{
		EnvManager.InitEnv ("");

		int vertexCount = 390;
		String currency = "USD";
		double horizon = 1. / 12.;
		String equityEntity = "SNP500";
		String fxCurrencyPair = "CHF/USD";
		double[][] correlation = 
		{
			{1.000, 0.500},	// SNP500
			{0.500, 1.000},	// CHFUSD
		};
		double[] annualMeanArray =
		{
			0.06,
			0.01
		};
		double[] annualVolatilityArray =
		{
			0.1,
			0.1
		};

		List<String> labelList = new ArrayList<String>();

		labelList.add (
			EntityEquityLabel.Standard (
				equityEntity,
				currency
			).fullyQualifiedName()
		);

		labelList.add (FXLabel.Standard (fxCurrencyPair).fullyQualifiedName());

		System.out.println (
			CorrelatedSample (
				labelList,
				annualMeanArray,
				annualVolatilityArray,
				correlation,
				vertexCount,
				horizon
			)
		);

		EnvManager.TerminateEnv();
	}
}
