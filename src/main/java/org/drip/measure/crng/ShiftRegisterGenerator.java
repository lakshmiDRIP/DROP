
package org.drip.measure.crng;

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
 * <i>ShiftRegisterGenerator</i> implements a RNG based on the Shift Register Generation Scheme.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalOptimizerLibrary.md">Numerical Optimizer Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure">Measure</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/crng">Continuous Random Number Generator</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class ShiftRegisterGenerator extends org.drip.measure.crng.RandomNumberGenerator {
	private boolean[] _abA = null;
	private boolean[] _abX = null;
	private long _lMaximumPeriod = java.lang.Long.MIN_VALUE;

	/**
	 * Construct a Standard Instance of ShiftRegisterGenerator from the Specified Period Power
	 * 
	 * @param iK The Period Power
	 * 
	 * @return Instance of ShiftRegisterGenerator
	 */

	public static final ShiftRegisterGenerator Standard (
		final int iK)
	{
		if (3 >= iK) return null;

		boolean[] abA = new boolean[iK];
		boolean[] abX = new boolean[iK];

		for (int i = 0; i < iK; ++i) {
			abA[i] = true;

			abX[i] = 1 == (System.nanoTime() % 2) ? true : false;
		}

		try {
			return new ShiftRegisterGenerator (abA, abX);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * ShiftRegisterGenerator Constructor
	 * 
	 * @param abA Array of Coefficients
	 * @param abX Array of State Values
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public ShiftRegisterGenerator (
		final boolean[] abA,
		final boolean[] abX)
		throws java.lang.Exception
	{
		if (null == (_abA = abA) || null == (_abX = abX))
			throw new java.lang.Exception ("ShiftRegisterGenerator Constructor => Invalid Inputs");

		_lMaximumPeriod = 1;
		int iK = _abA.length;

		if (0 == iK || iK != _abX.length)
			throw new java.lang.Exception ("ShiftRegisterGenerator Constructor => Invalid Inputs");

		for (int i = 0; i < iK; ++i)
			_lMaximumPeriod = _lMaximumPeriod * 2;

		_lMaximumPeriod -= 1;
	}

	/**
	 * Retrieve the Array of Coefficients
	 * 
	 * @return The Array of Coefficients
	 */

	public boolean[] a()
	{
		return _abA;
	}

	/**
	 * Retrieve the Array of State Values
	 * 
	 * @return The Array of State Values
	 */

	public boolean[] x()
	{
		return _abX;
	}

	/**
	 * Retrieve the Maximum Period
	 * 
	 * @return The Maximum Period
	 */

	public long maximumPeriod()
	{
		return _lMaximumPeriod;
	}

	/**
	 * Generate the Next Long in the Sequence
	 * 
	 * @return The Next Long in the Sequence
	 */

	public long nextLong()
	{
		long lNext = 0L;
		int iTwosIndex = 1;
		int iK = _abA.length;

		for (int i = 0; i < iK; ++i) {
			if (_abA[i] && _abX[i]) lNext = lNext + iTwosIndex;

			iTwosIndex *= 2;
		}

		_abX[iK - 1] = 1 == (lNext  % 2) ? true : false;

		for (int i = 0; i < iK - 1; ++i)
			_abX[i] = _abX[i + 1];

		return lNext;
	}

	@Override public double nextDouble01()
	{
		return ((double) nextLong()) / ((double) _lMaximumPeriod);
	}
}
