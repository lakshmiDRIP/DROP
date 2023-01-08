
package org.drip.sample.algo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
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
 * <i>FavoriteGenres</i> is the most listened to genre.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/StatisticalLearningLibrary.md">Statistical Learning Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/algo/README.md">C<sup>x</sup> R<sup>x</sup> In-Place Manipulation</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class FavoriteGenres
{

	private static final Map<String, String> SongGenreMap (
		final Map<String, List<String>> genreSongListMap)
	{
		Map<String, String> songGenreMap = new HashMap<String, String>();

		for (Map.Entry<String, List<String>> genreSongListEntry : genreSongListMap.entrySet())
		{
			String genre = genreSongListEntry.getKey();

			for (String song : genreSongListEntry.getValue())
			{
				songGenreMap.put (
					song,
					genre
				);
			}
		}

		return songGenreMap;
	}

	private static final Map<String, List<String>> UserGenreListMap (
		final Map<String, List<String>> userSongListMap,
		final Map<String, String> songGenreMap)
	{
		Map<String, List<String>> userGenreListMap = new HashMap<String, List<String>>();

		for (Map.Entry<String, List<String>> userSongListEntry : userSongListMap.entrySet())
		{
			String user = userSongListEntry.getKey();

			List<String> genreList = new ArrayList<String>();

			for (String song : userSongListEntry.getValue())
			{
				genreList.add (
					songGenreMap.get (
						song
					)
				);
			}

			userGenreListMap.put (
				user,
				genreList
			);
		}

		return userGenreListMap;
	}

	private static final Map<String, List<String>> LeadingUserGenreMap (
		final Map<String, List<String>> userSongListMap,
		final Map<String, List<String>> genreSongListMap)
	{
		Map<String, String> songGenreMap = SongGenreMap (
			genreSongListMap
		);

		Map<String, List<String>> userGenreListMap = UserGenreListMap (
			userSongListMap,
			songGenreMap
		);

		Map<String, List<String>> leadingUserGenreMap = new HashMap<String, List<String>>();

		for (Map.Entry<String, List<String>> userGenreListEntry : userGenreListMap.entrySet())
		{
			int leadingGenreCount = 0;

			List<String> leadingGenreList = new ArrayList<String>();

			Map<String, Integer> genreCountMap = new HashMap<String, Integer>();

			for (String genre : userGenreListEntry.getValue())
			{
				int currentGenreCount = genreCountMap.containsKey (
					genre
				) ? genreCountMap.get (
					genre
				) + 1 : 1;

				genreCountMap.put (
					genre,
					currentGenreCount
				);

				if (leadingGenreCount < currentGenreCount)
				{
					leadingGenreCount = currentGenreCount;

					leadingGenreList.clear();

					leadingGenreList.add (
						genre
					);
				}
				else if (leadingGenreCount == currentGenreCount)
				{
					leadingGenreList.add (
						genre
					);
				}
			}

			leadingUserGenreMap.put (
				userGenreListEntry.getKey(),
				leadingGenreList
			);
		}

		return leadingUserGenreMap;
	}

	private static final void Test1()
	{
		Map<String, List<String>> userSongListMap = new HashMap<String, List<String>>();

		List<String> davidSongList = new ArrayList<String>();

		davidSongList.add (
			"song1"
		);

		davidSongList.add (
			"song2"
		);

		davidSongList.add (
			"song3"
		);

		davidSongList.add (
			"song4"
		);

		davidSongList.add (
			"song8"
		);

		userSongListMap.put (
			"david",
			davidSongList
		);

		List<String> emmaSongList = new ArrayList<String>();

		emmaSongList.add (
			"song5"
		);

		emmaSongList.add (
			"song6"
		);

		emmaSongList.add (
			"song7"
		);

		userSongListMap.put (
			"emma",
			emmaSongList
		);

		Map<String, List<String>> genreSongListMap = new HashMap<String, List<String>>();

		List<String> rockSongList = new ArrayList<String>();

		rockSongList.add (
			"song1"
		);

		rockSongList.add (
			"song3"
		);

		genreSongListMap.put (
			"rock",
			rockSongList
		);

		List<String> dubstepSongList = new ArrayList<String>();

		dubstepSongList.add (
			"song7"
		);

		genreSongListMap.put (
			"dubstep",
			dubstepSongList
		);

		List<String> technoSongList = new ArrayList<String>();

		technoSongList.add (
			"song2"
		);

		technoSongList.add (
			"song4"
		);

		genreSongListMap.put (
			"techno",
			technoSongList
		);

		List<String> popSongList = new ArrayList<String>();

		popSongList.add (
			"song5"
		);

		popSongList.add (
			"song6"
		);

		genreSongListMap.put (
			"pop",
			popSongList
		);

		List<String> jazzSongList = new ArrayList<String>();

		jazzSongList.add (
			"song8"
		);

		jazzSongList.add (
			"song9"
		);

		genreSongListMap.put (
			"jazz",
			jazzSongList
		);

		System.out.println (
			LeadingUserGenreMap (
				userSongListMap,
				genreSongListMap
			)
		);
	}

	private static final void Test2()
		throws Exception
	{
		Map<String, List<String>> userSongListMap = new HashMap<String, List<String>>();

		List<String> davidSongList = new ArrayList<String>();

		davidSongList.add (
			"song1"
		);

		davidSongList.add (
			"song2"
		);

		userSongListMap.put (
			"david",
			davidSongList
		);

		List<String> emmaSongList = new ArrayList<String>();

		emmaSongList.add (
			"song3"
		);

		emmaSongList.add (
			"song4"
		);

		userSongListMap.put (
			"emma",
			emmaSongList
		);

		System.out.println (
			LeadingUserGenreMap (
				userSongListMap,
				new HashMap<String, List<String>>()
			)
		);
	}

	public static final void main (
		final String[] argumentArray)
		throws Exception
	{
		Test1();

		Test2();
	}
}
