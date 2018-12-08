
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
 * <i>Cardinality</i> contains the Type and the Measure of the Cardinality of the given Vector Space.
 *
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/StatisticalLearningLibrary.md">Statistical Learning Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces">Spaces</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces/tensor">Tensor</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class Cardinality {

	/**
	 * Cardinality Type - Countably Finite
	 */

	public static final int CARD_COUNTABLY_FINITE = 1;

	/**
	 * Cardinality Type - Countably Infinite
	 */

	public static final int CARD_COUNTABLY_INFINITE = 2;

	/**
	 * Cardinality Type - Uncountably Infinite
	 */

	public static final int CARD_UNCOUNTABLY_INFINITE = 3;

	private int _iType = -1;
	private double _dblNumber = java.lang.Double.NaN;

	/**
	 * Countably Finite Cardinality
	 * 
	 * @param dblNumber The Cardinality Number
	 * 
	 * @return The Cardinality Instance
	 */

	public static final Cardinality CountablyFinite (
		final double dblNumber)
	{
		try {
			return new Cardinality (CARD_COUNTABLY_FINITE, dblNumber);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Countably Infinite Cardinality
	 * 
	 * @return The Cardinality Instance
	 */

	public static final Cardinality CountablyInfinite()
	{
		try {
			return new Cardinality (CARD_COUNTABLY_INFINITE, java.lang.Double.POSITIVE_INFINITY);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Uncountably Infinite Cardinality
	 * 
	 * @return The Cardinality Instance
	 */

	public static final Cardinality UncountablyInfinite()
	{
		try {
			return new Cardinality (CARD_UNCOUNTABLY_INFINITE, java.lang.Double.POSITIVE_INFINITY);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Cardinality Constructor
	 * 
	 * @param iType Cardinality Type
	 * @param dblNumber Cardinality Number
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public Cardinality (
		final int iType,
		final double dblNumber)
		throws java.lang.Exception
	{
		if ((CARD_COUNTABLY_FINITE != (_iType = iType) && CARD_COUNTABLY_INFINITE != _iType &&
			CARD_UNCOUNTABLY_INFINITE != _iType) || java.lang.Double.isNaN (_dblNumber = dblNumber))
			throw new java.lang.Exception ("Cardinality ctr => Invalid Inputs");
	}

	/**
	 * Retrieve the Cardinality Type
	 * 
	 * @return The Cardinality Type
	 */

	public int type()
	{
		return _iType;
	}

	/**
	 * Retrieve the Cardinality Number
	 * 
	 * @return The Cardinality Number
	 */

	public double number()
	{
		return _dblNumber;
	}

	/**
	 * Indicate if the Current Instance matches the "Other" Cardinality Instance
	 * 
	 * @param cardOther The "Other" Cardinality Instance
	 * 
	 * @return TRUE - The Instances Match
	 */

	public boolean match (
		final Cardinality cardOther)
	{
		return null != cardOther && cardOther.type() == _iType && cardOther.number() == _dblNumber;
	}
}
