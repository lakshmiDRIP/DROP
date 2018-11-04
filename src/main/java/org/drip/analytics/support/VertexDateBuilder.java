
package org.drip.analytics.support;

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
 * <i>VertexDateBuilder</i> exports Static Functions that create Vertex Dates using different Schemes. The
 *  References are:
 *  <ul>
 *  	<li>
 *  		Burgard, C., and M. Kjaer (2013): Funding Strategies, Funding Costs <i>Risk</i> <b>24 (12)</b>
 *  			82-87
 *  	</li>
 *  	<li>
 *  		Burgard, C., and M. Kjaer (2014): PDE Representations of Derivatives with Bilateral Counter-party
 *  			Risk and Funding Costs <i>Journal of Credit Risk</i> <b>7 (3)</b> 1-19
 *  	</li>
 *  	<li>
 *  		Burgard, C., and M. Kjaer (2014): In the Balance <i>Risk</i> <b>24 (11)</b> 72-75
 *  	</li>
 *  	<li>
 *  		Gregory, J. (2009): Being Two-faced over Counter-party Credit Risk <i>Risk</i> <b>20 (2)</b>
 *  			86-90
 *  	</li>
 *  	<li>
 *  		Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives Pricing
 *  		<i>Risk</i> <b>21 (2)</b> 97-102
 *  	</li>
 *  </ul>
 *  <br>
 *  <ul>
 *		<li><b>Module</b>        = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics">Analytics</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/support">Support</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/FixedIncome">Fixed Income</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class VertexDateBuilder
{

	/**
	 * Construct an Array of Dates from the Spot Date and the Vertex Tenor Array
	 * 
	 * @param spotDate The Spot Date
	 * @param vertexTenorArray The Vertex Tenor Array
	 * 
	 * @return The Array of Dates
	 */

	public static final int[] SpotDateVertexTenor (
		final int spotDate,
		final java.lang.String[] vertexTenorArray)
	{
		if (0 >= spotDate || null == vertexTenorArray) return null;

		int vertexCount = vertexTenorArray.length;
		int[] vertexDateArray = 0 == vertexCount ? null : new int[vertexCount];

		if (0 == vertexCount) return null;

		org.drip.analytics.date.JulianDate spotDateJulian = new org.drip.analytics.date.JulianDate
			(spotDate);

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
		{
			org.drip.analytics.date.JulianDate vertexDateJulian = spotDateJulian.addTenor
				(vertexTenorArray[vertexIndex]);

			if (null == vertexDateJulian)
			{
				return null;
			}

			vertexDateArray[vertexIndex] = vertexDateJulian.julian();
		}

		return vertexDateArray;
	}

	/**
	 * Construct an Array of Vertex Dates from the Spot Date, Tenor Spacing Width, and the Vertex Count
	 * 
	 * @param spotDate The Spot Date
	 * @param periodTenor The Tenor Spacing Width
	 * @param vertexCount The Number of Vertexes
	 * 
	 * @return The Array of Vertex Dates
	 */

	public static final int[] SpotDatePeriodTenor (
		final int spotDate,
		final java.lang.String periodTenor,
		final int vertexCount)
	{
		if (0 >= spotDate || 0 >= vertexCount)
		{
			return null;
		}

		int[] vertexDateArray = new int[vertexCount];

		org.drip.analytics.date.JulianDate previousDateJulian = new org.drip.analytics.date.JulianDate
			(spotDate);

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
		{
			org.drip.analytics.date.JulianDate vertexDateJulian = previousDateJulian.addTenor (periodTenor);

			if (null == vertexDateJulian)
			{
				return null;
			}

			vertexDateArray[vertexIndex] = vertexDateJulian.julian();

			previousDateJulian = vertexDateJulian;
		}

		return vertexDateArray;
	}

	/**
	 * Generate Equal Width Vertex Dates from the specified Spot Date and the Terminal Date
	 * 
	 * @param spotDate The Spot Date
	 * @param terminalDate The Terminal Date
	 * @param vertexCount The Number of Vertexes
	 * 
	 * @return Array of Equal Width Vertex Dates
	 */

	public static final int[] EqualWidth (
		final int spotDate,
		final int terminalDate,
		final int vertexCount)
	{
		if (0 >= spotDate || terminalDate <= spotDate || 0 >= vertexCount)
		{
			return null;
		}

		int[] vertexDateArray = new int[vertexCount];
		vertexDateArray[vertexCount - 1] = terminalDate;
		double periodWidth = ((double) (terminalDate - spotDate)) / vertexCount;

		for (int vertexIndex = 0; vertexIndex < vertexCount - 1; ++vertexIndex)
		{
			vertexDateArray[vertexIndex] = spotDate + (int) ((vertexIndex + 1) * periodWidth);
		}

		return vertexDateArray;
	}
}
