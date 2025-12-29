
package org.drip.measure.discontinuous;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
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
 * <i>QuadraticResampler</i> Quadratically Re-samples the Input Points to Convert it to a Standard Normal.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/README.md">R<sup>d</sup> Continuous/Discrete Probability Measures</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/discrete/README.md">Antithetic, Quadratically Re-sampled, De-biased Distribution</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class QuadraticResampler {
	private boolean _bDebias = false;
	private boolean _bMeanCenter = false;

	/**
	 * QuadraticResampler Constructor
	 * 
	 * @param bMeanCenter TRUE - The Sequence is to be Mean Centered
	 * @param bDebias TRUE - Remove the Sampling Bias
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public QuadraticResampler (
		final boolean bMeanCenter,
		final boolean bDebias)
		throws java.lang.Exception
	{
		_bDebias = bDebias;
		_bMeanCenter = bMeanCenter;
	}

	/**
	 * Indicate if the Sequence is to be Mean Centered
	 * 
	 * @return TRUE - The Sequence is to be Mean Centered
	 */

	public boolean meanCenter()
	{
		return _bMeanCenter;
	}

	/**
	 * Indicate if the Sampling Bias needs to be Removed
	 * 
	 * @return TRUE - The Sampling Bias needs to be Removed
	 */

	public boolean debias()
	{
		return _bDebias;
	}

	/**
	 * Transform the Input R^1 Sequence by applying Quadratic Sampling
	 * 
	 * @param adblSequence The Input R^1 Sequence
	 * 
	 * @return The Transformed Sequence
	 */

	public double[] transform (
		final double[] adblSequence)
	{
		if (null == adblSequence) return null;

		double dblMean = 0.;
		double dblVariance = 0.;
		int iSequenceSize = adblSequence.length;
		double[] adblTransfomedSequence = 0 == iSequenceSize ? null : new double[iSequenceSize];

		if (0 == iSequenceSize) return null;

		if (_bMeanCenter) {
			for (int i = 0; i < iSequenceSize; ++i)
				dblMean += adblSequence[i];

			dblMean = dblMean / iSequenceSize;
		}

		for (int i = 0; i < iSequenceSize; ++i) {
			double dblOffset = adblSequence[i] - dblMean;
			dblVariance += dblOffset * dblOffset;
		}

		double dblStandardDeviation = java.lang.Math.sqrt (dblVariance / (_bDebias ? iSequenceSize - 1 :
			iSequenceSize));

		for (int i = 0; i < iSequenceSize; ++i)
			adblTransfomedSequence[i] = adblSequence[i] / dblStandardDeviation;

		return adblTransfomedSequence;
	}

	/**
	 * Transform the Input R^d Sequence by applying Quadratic Sampling
	 * 
	 * @param aadblSequence The Input R^d Sequence
	 * 
	 * @return The Transformed Sequence
	 */

	public double[][] transform (
		final double[][] aadblSequence)
	{
		double[][] aadblFlippedSequence = org.drip.numerical.linearalgebra.R1MatrixUtil.Transpose (aadblSequence);

		if (null == aadblFlippedSequence) return null;

		int iDimension = aadblFlippedSequence.length;
		double[][] aadblFlippedTransformedSequence = new double[iDimension][];

		for (int i = 0; i < iDimension; ++i)
			aadblFlippedTransformedSequence[i] = transform (aadblFlippedSequence[i]);
		
		return org.drip.numerical.linearalgebra.R1MatrixUtil.Transpose (aadblFlippedTransformedSequence);
	}
}
