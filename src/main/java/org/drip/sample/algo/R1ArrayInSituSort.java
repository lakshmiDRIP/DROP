
package org.drip.sample.algo;

import org.drip.quant.common.*;
import org.drip.service.env.EnvManager;
import org.drip.spaces.big.BigR1Array;

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
 *  	calculations, valuation adjustment, and portfolio construction within and across fixed income,
 *  	credit, commodity, equity, FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three modules:
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
 * 	- Statistical Learning
 * 	- Numerical Optimizer
 * 	- Spline Builder
 * 	- Algorithm Support
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
 * <i>R1ArrayInSituSort</i> demonstrates the Functionality that conducts an in-place Sorting of an Instance
 * of BigDoubleArray using a variety of Sorting Algorithms.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AlgorithmSupportLibrary.md">Algorithm Support Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">Sample</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/algo/README.md">"Big" Algorithm Support</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class R1ArrayInSituSort {

	private static void QuickSort (
		final int iNumRandom,
		final String strPrefix)
		throws Exception
	{
		double[] adblA = new double[iNumRandom];
		double[] adblAOrig = new double[iNumRandom];

		for (int i = 0; i < iNumRandom; ++i) {
			adblA[i] = Math.random();

			adblAOrig[i] = adblA[i];
		}

		BigR1Array ba = new BigR1Array (adblA);

		ba.quickSort();

		System.out.println ("\n\t---------------------------------------------");

		for (int i = 0; i < iNumRandom; ++i)
			System.out.println (
				"\t||  " + strPrefix + "  " +
				FormatUtil.FormatDouble (adblAOrig[i], 1, 4, 1.) + " | " +
				FormatUtil.FormatDouble (adblA[i], 1, 4, 1.) + " ||"
			);

		System.out.println ("\t---------------------------------------------");
	}

	private static void MergeSort (
		final int iNumRandom,
		final String strPrefix)
		throws Exception
	{
		double[] adblA = new double[iNumRandom];
		double[] adblAOrig = new double[iNumRandom];

		for (int i = 0; i < iNumRandom; ++i) {
			adblA[i] = Math.random();

			adblAOrig[i] = adblA[i];
		}

		BigR1Array ba = new BigR1Array (adblA);

		ba.mergeSort();

		System.out.println ("\n\t---------------------------------------------");

		for (int i = 0; i < iNumRandom; ++i)
			System.out.println (
				"\t||  " + strPrefix + "  " +
				FormatUtil.FormatDouble (adblAOrig[i], 1, 4, 1.) + " | " +
				FormatUtil.FormatDouble (adblA[i], 1, 4, 1.) + " ||"
			);

		System.out.println ("\t---------------------------------------------");
	}

	private static void HeapSort (
		final int iNumRandom,
		final String strPrefix)
		throws Exception
	{
		double[] adblA = new double[iNumRandom];
		double[] adblAOrig = new double[iNumRandom];

		for (int i = 0; i < iNumRandom; ++i) {
			adblA[i] = Math.random();

			adblAOrig[i] = adblA[i];
		}

		BigR1Array ba = new BigR1Array (adblA);

		ba.heapSort();

		System.out.println ("\n\t---------------------------------------------");

		for (int i = 0; i < iNumRandom; ++i)
			System.out.println (
				"\t||  " + strPrefix + "  " +
				FormatUtil.FormatDouble (adblAOrig[i], 1, 4, 1.) + " | " +
				FormatUtil.FormatDouble (adblA[i], 1, 4, 1.) + " ||"
			);

		System.out.println ("\t---------------------------------------------");
	}

	private static void InsertionSort (
		final int iNumRandom,
		final String strPrefix)
		throws Exception
	{
		double[] adblA = new double[iNumRandom];
		double[] adblAOrig = new double[iNumRandom];

		for (int i = 0; i < iNumRandom; ++i) {
			adblA[i] = Math.random();

			adblAOrig[i] = adblA[i];
		}

		System.out.println ("\n\t---------------------------------------------");

		NumberUtil.Print1DArray (
			"\t|  ORIGINAL  ",
			adblA,
			6,
			false
		);

		System.out.println ("\t---------------------------------------------");

		BigR1Array ba = new BigR1Array (adblA);

		ba.insertionSort();

		System.out.println ("\n\t---------------------------------------------");

		for (int i = 0; i < iNumRandom; ++i)
			System.out.println (
				"\t||  " + strPrefix + "  " +
				FormatUtil.FormatDouble (adblAOrig[i], 1, 4, 1.) + " | " +
				FormatUtil.FormatDouble (adblA[i], 1, 4, 1.) + " ||"
			);

		System.out.println ("\t---------------------------------------------");
	}

	public static void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv (
			"",
			true
		);

		int iNumRandom = 50;

		QuickSort (
			iNumRandom,
			"QUICKSORT"
		);

		MergeSort (
			iNumRandom,
			"MERGESORT"
		);

		InsertionSort (
			iNumRandom,
			"INSERTIONSORT"
		);

		HeapSort (
			iNumRandom,
			"HEAPSORT"
		);

		EnvManager.TerminateEnv();
	}
}
