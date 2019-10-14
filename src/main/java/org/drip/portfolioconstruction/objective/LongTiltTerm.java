
package org.drip.portfolioconstruction.objective;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
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
 * <i>LongTiltTerm</i> holds the Details of Long Tilt Unit Objective Term.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AssetAllocationAnalyticsLibrary.md">Asset Allocation Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction">Portfolio Construction</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/objective">Objective</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class LongTiltTerm
	extends org.drip.portfolioconstruction.objective.TiltTerm
{

	/**
	 * LongTiltTerm Constructor
	 * 
	 * @param name The Objective Term Name
	 * @param initialHoldingsArray The Initial Holdings Array
	 * @param magnitudeArray The Tilt Magnitude Block Attribute Array
	 * @param membershipArray The Tilt Membership Block Classification Array
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public LongTiltTerm (
		final java.lang.String name,
		final double[] initialHoldingsArray,
		final double[] magnitudeArray,
		final double[] membershipArray)
		throws java.lang.Exception
	{
		super (
			name,
			"OBJECTIVE_TERM_LONG_TILT",
			"Long Tilt Objective Term",
			initialHoldingsArray,
			magnitudeArray,
			membershipArray
		);
	}

	@Override public org.drip.function.definition.RdToR1 rdtoR1()
	{
		return new org.drip.function.definition.RdToR1 (null)
		{
			@Override public int dimension()
			{
				return initialHoldingsArray().length;
			}

			@Override public double evaluate (
				final double[] variateArray)
				throws java.lang.Exception
			{
				if (null == variateArray || !org.drip.numerical.common.NumberUtil.IsValid (variateArray))
				{
					throw new java.lang.Exception ("LongTiltTerm::rdToR1::evaluate => Invalid Inputs");
				}

				double tiltValue = 0.;
				int dimension = variateArray.length;

				if (dimension != dimension())
				{
					throw new java.lang.Exception ("LongTiltTerm::rdToR1::evaluate => Invalid Inputs");
				}

				double[] magnitudeArray = magnitudeArray();

				double[] membershipArray = membershipArray();

				double[] initialHoldingsArray = initialHoldingsArray();

				for (int dimensionIndex = 0; dimensionIndex < dimension; ++dimensionIndex)
				{
					tiltValue += magnitudeArray[dimensionIndex] * membershipArray[dimensionIndex] * (
						java.lang.Math.abs (variateArray[dimensionIndex]) -
						java.lang.Math.abs (initialHoldingsArray[dimensionIndex])
					);
				}

				return tiltValue;
			}

			@Override public double derivative (
				final double[] variateArray,
				final int variateIndex,
				final int derivativeOrder)
				throws java.lang.Exception
			{
				if (0 == derivativeOrder ||
					null == variateArray || !org.drip.numerical.common.NumberUtil.IsValid (variateArray))
				{
					throw new java.lang.Exception ("LongTiltTerm::rdToR1::derivative => Invalid Inputs");
				}

				int dimension = variateArray.length;

				if (dimension != dimension() || variateIndex >= dimension)
				{
					throw new java.lang.Exception ("LongTiltTerm::rdToR1::derivative => Invalid Inputs");
				}

				if (2 <= derivativeOrder)
				{
					return 0.;
				}

				return (variateArray[variateIndex] > initialHoldingsArray()[variateIndex] ? 1. : -1.) *
					magnitudeArray()[variateIndex] * membershipArray()[variateIndex];
			}
		};
	}
}
