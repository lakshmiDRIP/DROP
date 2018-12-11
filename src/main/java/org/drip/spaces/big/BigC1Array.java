
package org.drip.spaces.big;

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
 * <i>BigC1Array</i> contains the Functionality to Process and Manipulate the Character Array backing the Big
 * String.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/StatisticalLearningLibrary.md">Statistical Learning Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces">Spaces</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces/big">Big</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class BigC1Array {
	private int _iLength = -1;
	private char[] _ach = null;

	private int WrapIndex (
		final int iIndex)
	{
		if (iIndex >= _iLength) return iIndex - _iLength;

		if (iIndex < 0) return iIndex + _iLength;

		return iIndex;
	}

	/**
	 * BigC1Array Constructor
	 * 
	 * @param ach Character Array
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public BigC1Array (
		final char[] ach)
		throws java.lang.Exception
	{
		if (null == (_ach = ach) || 0 == (_iLength = _ach.length))
			throw new java.lang.Exception ("BigC1Array ctr => Invalid Inputs");
	}

	/**
	 * Retrieve the Character Array
	 * 
	 * @return The Character Array
	 */

	public char[] charArray()
	{
		return _ach;
	}

	/**
	 * Translate the String at around the Pivot Index using the String Block
	 * 
	 * @param iPivotIndex The Pivot Index
	 * @param iBlockSize The Block Size
	 * 
	 * @return TRUE - The Translation succeeded
	 */

	public boolean translateAtPivot (
		final int iPivotIndex,
		final int iBlockSize)
	{
		if (0 >= iPivotIndex || 0 >= iBlockSize) return false;

		int iLength = _ach.length;
		char[] achTemp = new char[iBlockSize];

		if (iPivotIndex >= iLength || iBlockSize >= iLength - iPivotIndex - 1) return false;

		for (int i = iPivotIndex - iBlockSize; i < iPivotIndex; ++i)
			achTemp[i - iPivotIndex + iBlockSize] = _ach[i];

		for (int i = 0; i < iLength - iBlockSize; ++i)
			_ach[WrapIndex (iPivotIndex - iBlockSize + i)] = _ach[WrapIndex (iPivotIndex + i)];

		for (int i = 0; i < iBlockSize; ++i)
			_ach[WrapIndex (iPivotIndex - 2 * iBlockSize + i)] = achTemp[i];

		return true;
	}
}
