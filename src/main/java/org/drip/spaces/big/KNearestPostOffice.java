
package org.drip.spaces.big;

import java.util.List;
import java.util.TreeMap;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2025 Lakshmi Krishnamurthy
 * Copyright (C) 2024 Lakshmi Krishnamurthy
 * Copyright (C) 2023 Lakshmi Krishnamurthy
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
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
 * <i>KNearestPostOffice</i> implements a Locator of the k Nearest Services. It provides the following
 * 	Functionality:
 *
 *  <ul>
 * 		<li>KNearestPostOffice Constructor</li>
 * 		<li>Retrieve List of the Post Office Locations</li>
 * 		<li>Retrieve my Location</li>
 * 		<li>Retrieve K</li>
 * 		<li>Return the k Closest Post Offices</li>
 *  </ul>
 *
 *  <br>
 *  <style>table, td, th {
 *  	padding: 1px; border: 2px solid #008000; border-radius: 8px; background-color: #dfff00;
 *		text-align: center; color:  #0000ff;
 *  }
 *  </style>
 *  
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/StatisticalLearningLibrary.md">Statistical Learning Library</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces/README.md">R<sup>1</sup> and R<sup>d</sup> Vector/Tensor Spaces (Validated and/or Normed), and Function Classes</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces/big/README.md">Big-data In-place Manipulator</a></td></tr>
 *  </table>
 *
 * @author Lakshmi Krishnamurthy
 */

public class KNearestPostOffice
{
	private int _k = -1;
	private int[] _myLocationCoordinateArray = null;
	private List<int[]> _postOfficeLocationList = null;

	/**
	 * KNearestPostOffice Constructor
	 * 
	 * @param postOfficeLocationList List of the Post Office Locations
	 * @param myLocationCoordinateArray My Location Coordinate Array
	 * @param k k
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public KNearestPostOffice (
		final java.util.List<int[]> postOfficeLocationList,
		final int[] myLocationCoordinateArray,
		final int k)
		throws Exception
	{
		if (null == (_postOfficeLocationList = postOfficeLocationList) ||
			null == (_myLocationCoordinateArray = myLocationCoordinateArray) ||
				2 != _myLocationCoordinateArray.length ||
			0 >= (_k = k))
		{
			throw new Exception ("KNearestPostOffice Constructor => Invalid Inputs");
		}

		int postOfficeCount = _postOfficeLocationList.size();

		if (_k > postOfficeCount) {
			throw new Exception ("KNearestPostOffice Constructor => Invalid Inputs");
		}

		for (int[] postOfficeLocation : _postOfficeLocationList) {
			if (null == postOfficeLocation || 2 != postOfficeLocation.length) {
				throw new Exception ("KNearestPostOffice Constructor => Invalid Inputs");
			}
		}
	}

	/**
	 * Retrieve List of the Post Office Locations
	 * 
	 * @return List of the Post Office Locations
	 */

	public List<int[]> postOfficeLocationList()
	{
		return _postOfficeLocationList;
	}

	/**
	 * Retrieve my Location
	 * 
	 * @return My Location
	 */

	public int[] myLocation()
	{
		return _myLocationCoordinateArray;
	}

	/**
	 * Retrieve K
	 * 
	 * @return K
	 */

	public int k()
	{
		return _k;
	}

	/**
	 * Return the k Closest Post Offices
	 * 
	 * @return k Closest Post Offices
	 */

	public int[][] closest()
	{
		int listIndex = 0;
		int[][] closestPostOfficeIndexOrderArray = new int[_k][];

		Integer[] sortedPostOfficeLocationIndexArray = new Integer[_postOfficeLocationList.size()];

		TreeMap<Integer, Integer> postOfficeDistanceMap = new TreeMap<Integer, Integer>();

		for (int[] postOfficeLocation : _postOfficeLocationList) {
			int xDistance = postOfficeLocation[0] - _myLocationCoordinateArray[0];
			int yDistance = postOfficeLocation[1] - _myLocationCoordinateArray[1];

			postOfficeDistanceMap.put (xDistance * xDistance + yDistance * yDistance, listIndex++);
		}

		postOfficeDistanceMap.values().toArray (sortedPostOfficeLocationIndexArray);

		for (int sortedPostOfficeIndex = 0; sortedPostOfficeIndex < _k; ++sortedPostOfficeIndex) {
			closestPostOfficeIndexOrderArray[sortedPostOfficeIndex] = _postOfficeLocationList.get (
				sortedPostOfficeLocationIndexArray[sortedPostOfficeIndex]
			);
		}

		return closestPostOfficeIndexOrderArray;
	}
}
