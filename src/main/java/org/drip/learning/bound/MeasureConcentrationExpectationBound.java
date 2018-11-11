
package org.drip.learning.bound;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
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
 * <i>MeasureConcentrationExpectationBound</i> provides the Upper Bound of the Expected Loss between
 * Empirical Outcome and the Prediction of the given Learner Class using the Concentration of Measure
 * Inequalities. This is expressed as C n<sup>a</sup>, where n is the Size of the Sample, and 'C' and 'a' are
 * Constants specific to the Learning Class. The References are:
 * <br><br>
 * <ul>
 * 	<li>
 *  	Boucheron, S., G. Lugosi, and P. Massart (2003): Concentration Inequalities Using the Entropy Method
 *  		<i>Annals of Probability</i> <b>31</b> 1583-1614
 * 	</li>
 * 	<li>
 *  	Lugosi, G. (2002): Pattern Classification and Learning Theory, in: <i>L. Györ, editor, Principles
 *  		of Non-parametric Learning</i> <b>Springer</b> Wien 5-62
 * 	</li>
 * </ul>
 * 
 * <br><br>
 * 	<ul>
 *		<li><b>Module</b>        = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/learning">Learning</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/learning/bound">Bound</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/StatisticalLearning">Statistical Learning Library</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class MeasureConcentrationExpectationBound {
	private double _dblConstant = java.lang.Double.NaN;
	private double _dblExponent = java.lang.Double.NaN;

	/**
	 * MeasureConcentrationExpectationBound Constructor
	 * 
	 * @param dblConstant Asymptote Constant
	 * @param dblExponent Asymptote Exponent
	 * 
	 * @throws java.lang.Exception Thrown if the Constant and/or Exponent is Invalid
	 */

	public MeasureConcentrationExpectationBound (
		final double dblConstant,
		final double dblExponent)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (_dblConstant = dblConstant) ||
			!org.drip.quant.common.NumberUtil.IsValid (_dblExponent = dblExponent))
			throw new java.lang.Exception ("MeasureConcentrationExpectationBound ctr: Invalid Inputs!");
	}

	/**
	 * Retrieve the Asymptote Constant
	 * 
	 * @return The Asymptote Constant
	 */

	public double constant()
	{
		return _dblConstant;
	}

	/**
	 * Retrieve the Asymptote Exponent
	 * 
	 * @return The Asymptote Exponent
	 */

	public double exponent()
	{
		return _dblExponent;
	}

	/**
	 * Compute the Expected Loss Upper Bound between the Sample and the Population for the specified Sample
	 *  Size
	 * 
	 * @param iSampleSize The Sample Size
	 * 
	 * @return The Expected Loss Upper Bound the Sample and the Population for the specified Sample Size
	 * 
	 * @throws java.lang.Exception Thrown if the Expected Loss Upper Bound cannot be computed
	 */

	public double lossExpectationUpperBound (
		final int iSampleSize)
		throws java.lang.Exception
	{
		if (0 >= iSampleSize)
			throw new java.lang.Exception
				("MeasureConcentrationExpectationBound::lossExpectationUpperBound => Invalid Inputs");

		return _dblConstant * java.lang.Math.pow (iSampleSize, _dblExponent);
	}
}
