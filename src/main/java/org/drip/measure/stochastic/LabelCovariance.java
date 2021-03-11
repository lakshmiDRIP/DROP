
package org.drip.measure.stochastic;

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
 * <i>LabelCovariance</i> holds the Covariance between any Stochastic Variates identified by their Labels, as
 * well as their Means. The References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 *  		Andersen, L. B. G., M. Pykhtin, and A. Sokol (2017): Credit Exposure in the Presence of Initial
 *  			Margin https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2806156 <b>eSSRN</b>
 * 		</li>
 * 		<li>
 *  		Albanese, C., S. Caenazzo, and O. Frankel (2017): Regression Sensitivities for Initial Margin
 *  			Calculations https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2763488 <b>eSSRN</b>
 * 		</li>
 * 		<li>
 *  		Anfuso, F., D. Aziz, P. Giltinan, and K. Loukopoulus (2017): A Sound Modeling and Back-testing
 *  			Framework for Forecasting Initial Margin Requirements
 *  			https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2716279 <b>eSSRN</b>
 * 		</li>
 * 		<li>
 *  		Caspers, P., P. Giltinan, R. Lichters, and N. Nowaczyk (2017): Forecasting Initial Margin
 *  			Requirements - A Model Evaluation https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2911167
 *  			<b>eSSRN</b>
 * 		</li>
 * 		<li>
 *  		International Swaps and Derivatives Association (2017): SIMM v2.0 Methodology
 *  			https://www.isda.org/a/oFiDE/isda-simm-v2.pdf
 * 		</li>
 * 	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/README.md">R<sup>d</sup> Continuous/Discrete Probability Measures</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/stochastic/README.md">R<sup>1</sup> R<sup>1</sup> To R<sup>1</sup> Process</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class LabelCovariance extends org.drip.measure.stochastic.LabelCorrelation
{
	private double[] _meanArray = null;
	private double[] _volatilityArray = null;
	private org.drip.measure.gaussian.Covariance _covariance = null;

	/**
	 * LabelCovariance Constructor
	 * 
	 * @param labelList The List of Labels
	 * @param meanArray Array of Variate Means
	 * @param volatilityArray Array of Variate Volatilities
	 * @param correlationMatrix The Correlation Matrix
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public LabelCovariance (
		final java.util.List<java.lang.String> labelList,
		final double[] meanArray,
		final double[] volatilityArray,
		final double[][] correlationMatrix)
		throws java.lang.Exception
	{
		super (
			labelList,
			correlationMatrix
		);

		if (null == (_meanArray = meanArray) ||
			null == (_volatilityArray = volatilityArray))
		{
			throw new java.lang.Exception ("LabelCovariance Constructor => Invalid Inputs");
		}

		int variateCount = correlationMatrix.length;
		double[][] covarianceMatrix = new double[variateCount][variateCount];

		if (variateCount != _meanArray.length ||
			variateCount != _volatilityArray.length)
		{
			throw new java.lang.Exception ("LabelCovariance Constructor => Invalid Inputs");
		}

		for (int variateIndexI = 0; variateIndexI < variateCount; ++variateIndexI)
		{
			if (!org.drip.numerical.common.NumberUtil.IsValid (_meanArray[variateIndexI]) ||
				!org.drip.numerical.common.NumberUtil.IsValid (_volatilityArray[variateIndexI]) ||
				0. > _volatilityArray[variateIndexI])
			{
				throw new java.lang.Exception ("LabelCovariance Constructor => Invalid Inputs");
			}

			for (int variateIndexJ = 0; variateIndexJ < variateCount; ++variateIndexJ)
			{
				covarianceMatrix[variateIndexI][variateIndexJ] =
					correlationMatrix[variateIndexI][variateIndexJ] * _volatilityArray[variateIndexI] *
					_volatilityArray[variateIndexJ];
			}
		}

		_covariance = new org.drip.measure.gaussian.Covariance (covarianceMatrix);
	}

	/**
	 * Retrieve the Array of Variate Means
	 * 
	 * @return The Array of Variate Means
	 */

	public double[] meanArray()
	{
		return _meanArray;
	}

	/**
	 * Retrieve the Array of Variate Volatilities
	 * 
	 * @return The Array of Variate Volatilities
	 */

	public double[] volatilityArray()
	{
		return _volatilityArray;
	}

	/**
	 * Retrieve the Correlation Matrix
	 * 
	 * @return The Correlation Matrix
	 */

	public double[][] correlationMatrix()
	{
		return _matrix;
	}

	/**
	 * Retrieve the Covariance Matrix
	 * 
	 * @return The Covariance Matrix
	 */

	public double[][] covarianceMatrix()
	{
		return _covariance.covarianceMatrix();
	}

	/**
	 * Retrieve the Precision Matrix
	 * 
	 * @return The Precision Matrix
	 */

	public double[][] precisionMatrix()
	{
		return _covariance.precisionMatrix();
	}

	/**
	 * Retrieve the Mean of the Latent State
	 * 
	 * @param label Latent State Label
	 * 
	 * @return Mean of the Latent State
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double mean (
		final java.lang.String label)
		throws java.lang.Exception
	{
		if (null == label || !_labelList.contains (label))
		{
			throw new java.lang.Exception ("LabelCovariance::mean => Invalid Inputs");
		}

		return _meanArray[_labelIndexMap.get (label)];
	}

	/**
	 * Retrieve the Volatility of the Latent State
	 * 
	 * @param label Latent State Label
	 * 
	 * @return Volatility of the Latent State
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double volatility (
		final java.lang.String label)
		throws java.lang.Exception
	{
		if (null == label || !_labelList.contains (label))
		{
			throw new java.lang.Exception ("LabelCovariance::volatility => Invalid Inputs");
		}

		return _volatilityArray[_labelIndexMap.get (label)];
	}
}
