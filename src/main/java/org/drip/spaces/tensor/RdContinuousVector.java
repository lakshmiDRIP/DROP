
package org.drip.spaces.tensor;

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
 * <i>RdContinuousVector</i> implements the Normed/non-normed, Bounded/Unbounded Continuous R<sup>d</sup>
 * Vector Spaces. The Reference we've used is:
 *
 * <br><br>
 *  <ul>
 *  	<li>
 *  		Carl, B., and I. Stephani (1990): <i>Entropy, Compactness, and the Approximation of Operators</i>
 *  			<b>Cambridge University Press</b> Cambridge UK 
 *  	</li>
 *  </ul>
 *
 * <br><br>
 *  <ul>
 *		<li><b>Project</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces">Spaces</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces/tensor">Tensor</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/StatisticalLearning">Statistical Learning Library</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class RdContinuousVector extends org.drip.spaces.tensor.RdAggregate {

	/**
	 * Construct the RdContinuousVector Instance
	 * 
	 * @param iDimension The Space Dimension
	 * 
	 * @return The RdContinuousVector Instance
	 */

	public static final RdContinuousVector Standard (
		final int iDimension)
	{
		try {
			return 0 >= iDimension ? null : new RdContinuousVector (new
				org.drip.spaces.tensor.R1ContinuousVector[iDimension]);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * RdContinuousVector Constructor
	 * 
	 * @param aR1CV Array of the Continuous R^1 Vector Spaces
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public RdContinuousVector (
		final org.drip.spaces.tensor.R1ContinuousVector[] aR1CV)
		throws java.lang.Exception
	{
		super (aR1CV);
	}

	@Override public double[] leftDimensionEdge()
	{
		org.drip.spaces.tensor.R1GeneralizedVector[] aR1GV = vectorSpaces();

		int iDimension = aR1GV.length;
		double[] adblLeftEdge = new double[iDimension];

		for (int i = 0; i < iDimension; ++i)
			adblLeftEdge[i] = ((org.drip.spaces.tensor.R1ContinuousVector) aR1GV[i]).leftEdge();

		return adblLeftEdge;
	}

	@Override public double[] rightDimensionEdge()
	{
		org.drip.spaces.tensor.R1GeneralizedVector[] aR1GV = vectorSpaces();

		int iDimension = aR1GV.length;
		double[] adblRightEdge = new double[iDimension];

		for (int i = 0; i < iDimension; ++i)
			adblRightEdge[i] = ((org.drip.spaces.tensor.R1ContinuousVector) aR1GV[i]).rightEdge();

		return adblRightEdge;
	}

	@Override public org.drip.spaces.tensor.Cardinality cardinality()
	{
		return org.drip.spaces.tensor.Cardinality.UncountablyInfinite();
	}

	@Override public double leftEdge()
	{
		double[] adblLeftEdge = leftDimensionEdge();

		int iDimension = adblLeftEdge.length;
		double dblLeftEdge = adblLeftEdge[0];

		for (int i = 1; i < iDimension; ++i) {
			if (dblLeftEdge > adblLeftEdge[i]) dblLeftEdge = adblLeftEdge[i];
		}

		return dblLeftEdge;
	}

	@Override public double rightEdge()
	{
		double[] adblRightEdge = rightDimensionEdge();

		int iDimension = adblRightEdge.length;
		double dblRightEdge = adblRightEdge[0];

		for (int i = 1; i < iDimension; ++i) {
			if (dblRightEdge < adblRightEdge[i]) dblRightEdge = adblRightEdge[i];
		}

		return dblRightEdge;
	}

	@Override public double hyperVolume()
		throws java.lang.Exception
	{
		if (!isPredictorBounded())
			throw new java.lang.Exception ("ContinuousVectorRd::hyperVolume => Space not Bounded");

		double[] adblLeftEdge = leftDimensionEdge();

		double dblHyperVolume = 1.;
		int iDimension = adblLeftEdge.length;

		double[] adblRightEdge = rightDimensionEdge();

		for (int i = 0; i < iDimension; ++i)
			dblHyperVolume *= (adblRightEdge[i] - adblLeftEdge[i]);

		return dblHyperVolume;
	}
}
