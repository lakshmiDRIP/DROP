
package org.drip.sample.graphsearch;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
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
 * <i>Connected</i> reads in a file and outputs whether two specified cities are connected. The References
 * 	are:
 * 
 * <br><br>
 *  <ul>
 *  	<li>
 *  		Aziz, A., and A. Prakash (2010): Algorithms for Interviews
 *  			http://users.ece.utexas.edu/~adnan/afi-samples-new.pdf
 *  	</li>
 *  	<li>
 *  		Coppin, B. (2004): <i>Artificial Intelligence Illuminated</i> <b>Jones and Bartlett Learning</b>
 *  	</li>
 *  	<li>
 *  		Cormen, T., C. E. Leiserson, R. Rivest, and C. Stein (2009): <i>Introduction to Algorithms
 *  			3<sup>rd</sup> Edition</i> <b>MIT Press</b>
 *  	</li>
 *  	<li>
 *  		Russell, S., and P. Norvig (2003): <i>Artificial Intelligence: Modern Approach 2<sup>nd</sup>
 *  			Edition</i> <b>Prentice Hall</b>
 *  	</li>
 *  	<li>
 *  		Wikipedia (2020): Breadth-first Search https://en.wikipedia.org/wiki/Breadth-first_search
 *  	</li>
 *  </ul>
 *
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/GraphAlgorithmLibrary.md">Graph Algorithm Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/graphsearch/README.md">Breadth/Depth First Search/Ordering</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class Connected
{
	private Map<String, List<String>> _cityNeighborListMap = new HashMap<String, List<String>>();

	private Connected()
	{
	}

	private boolean addPair (final String city1, final String city2)
	{
		if (null == city1 || null == city2) return false;

		if (_cityNeighborListMap.containsKey (city1))
			_cityNeighborListMap.get (city1).add (city2);
		else {
			List<String> neighborList = new ArrayList<String>();

			neighborList.add (city2);

			_cityNeighborListMap.put (city1, neighborList);
		}

		if (_cityNeighborListMap.containsKey (city2))
			_cityNeighborListMap.get (city2).add (city1);
		else {
			List<String> neighborList = new ArrayList<String>();

			neighborList.add (city1);

			_cityNeighborListMap.put (city2, neighborList);
		}

		return true;
	}

	private String citiesReachable (final String startCity, final String endCity)
	{
		if (null == startCity || null == endCity || !_cityNeighborListMap.containsKey (startCity) ||
			!_cityNeighborListMap.containsKey (endCity))
			return "no";

		List<String> cityStack = new ArrayList<String>();

		cityStack.add (startCity);

		Set<String> visitedCitySet = new HashSet<String>();

		while (!cityStack.isEmpty()) {
			String currentCity = cityStack.remove (cityStack.size() - 1);

			if (currentCity.equals (endCity)) return "yes";

			visitedCitySet.add (currentCity);

			if (!_cityNeighborListMap.containsKey (currentCity)) continue;

			for (String neighbor : _cityNeighborListMap.get (currentCity)) {
				if (!visitedCitySet.contains (neighbor)) cityStack.add (neighbor);
			}
		}

		return "no";
	}

	private static final boolean SourceDoesNotExist (String result) throws Exception
	{
		Connected connected = new Connected();

		connected.addPair ("A", "B");

		connected.addPair ("A", "C");

		connected.addPair ("B", "D");

		String source = "F";
		String target = "D";

		return result.equalsIgnoreCase (connected.citiesReachable (source, target));
	}

	private static final boolean DestinationDoesNotExist (String result) throws Exception
	{
		Connected connected = new Connected();

		connected.addPair ("A", "B");

		connected.addPair ("A", "C");

		connected.addPair ("B", "D");

		String source = "A";
		String target = "F";

		return result.equalsIgnoreCase (connected.citiesReachable (source, target));
	}

	private static final boolean DisjointDistinctSegmentConnection (String result) throws Exception
	{
		Connected connected = new Connected();

		connected.addPair ("A", "B");

		connected.addPair ("A", "C");

		connected.addPair ("B", "D");

		connected.addPair ("E", "F");

		connected.addPair ("E", "G");

		connected.addPair ("F", "G");

		String source = "A";
		String target = "F";

		return result.equalsIgnoreCase (connected.citiesReachable (source, target));
	}

	private static final boolean DisjointSameSegmentConnection (String result) throws Exception
	{
		Connected connected = new Connected();

		connected.addPair ("A", "B");

		connected.addPair ("A", "C");

		connected.addPair ("B", "D");

		connected.addPair ("E", "F");

		connected.addPair ("E", "G");

		connected.addPair ("F", "G");

		String source = "A";
		String target = "D";

		return result.equalsIgnoreCase (connected.citiesReachable (source, target));
	}

	private static final boolean ConnectionInsideCycle (String result) throws Exception
	{
		Connected connected = new Connected();

		connected.addPair ("A", "B");

		connected.addPair ("A", "C");

		connected.addPair ("B", "D");

		connected.addPair ("B", "G");

		connected.addPair ("G", "F");

		connected.addPair ("E", "F");

		connected.addPair ("E", "G");

		connected.addPair ("H", "G");

		String source = "C";
		String target = "E";

		return result.equalsIgnoreCase (connected.citiesReachable (source, target));
	}

	private static final boolean ConnectionInsideTree (String result) throws Exception
	{
		Connected connected = new Connected();

		connected.addPair ("A", "B");

		connected.addPair ("A", "C");

		connected.addPair ("B", "E");

		connected.addPair ("E", "F");

		connected.addPair ("E", "G");

		connected.addPair ("F", "G");

		connected.addPair ("G", "H");

		String source = "C";
		String target = "H";

		return result.equalsIgnoreCase (connected.citiesReachable (source, target));
	}

	/**
	 * Entry Point
	 * 
	 * @param argumentArray Command Line Argument Array
	 * 
	 * @throws Exception Thrown on Error/Exception Situation
	 */

	public static final void main (String[] argumentArray) throws Exception
	{
		System.out.println ("Test #1 (Source Does Not Exist) Passed: " + SourceDoesNotExist ("no"));

		System.out.println ("Test #2 (Destination Does Not Exist) Passed: " + DestinationDoesNotExist ("no"));

		System.out.println ("Test #3 (Disjoint Distinct Segment Connection) Passed: " + DisjointDistinctSegmentConnection ("no"));

		System.out.println ("Test #4 (Disjoint Same Segment Connection) Passed: " + DisjointSameSegmentConnection ("yes"));

		System.out.println ("Test #5 (Connection Inside Cycle) Passed: " + ConnectionInsideCycle ("yes"));

		System.out.println ("Test #6 (Connection Inside Tree) Passed: " + ConnectionInsideTree ("yes"));
	}

	/**
	 * Process From File
	 * 
	 * @param argumentArray File
	 * 
	 * @throws Exception Thrown if the File cannot be processed
	 */

	public static final void ProcessFromFile (String[] argumentArray) throws Exception
	{
		Connected connected = new Connected();

		BufferedReader bufferedReader = new BufferedReader (new FileReader (argumentArray[0]));

		String line = "";

		while (null != (line = bufferedReader.readLine())) {
			String[] cityPair = line.split (",");

			connected.addPair (cityPair[0].trim(), cityPair[1].trim());
		}

		System.out.println (connected.citiesReachable (argumentArray[1], argumentArray[2]));

		bufferedReader.close();
	}
}
