
package org.drip.function.e2erf;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
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
 * <i>BuiltInEntry</i> implements E<sub>2</sub> Entries of the Built-in Table of erf and erfc Values. The
 * References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Abramowitz, M., and I. A. Stegun (2007): <i>Handbook of Mathematics Functions</i> <b>Dover Book
 * 				on Mathematics</b>
 * 		</li>
 * 		<li>
 * 			Chang, S. H., P. C. Cosman, L. B. Milstein (2011): Chernoff-Type Bounds for Gaussian Error
 * 				Function <i>IEEE Transactions on Communications</i> <b>59 (11)</b> 2939-2944
 * 		</li>
 * 		<li>
 * 			Cody, W. J. (1991): Algorithm 715: SPECFUN – A Portable FORTRAN Package of Special Function
 * 				Routines and Test Drivers <i>ACM Transactions on Mathematical Software</i> <b>19 (1)</b>
 * 				22-32
 * 		</li>
 * 		<li>
 * 			Schopf, H. M., and P. H. Supancic (2014): On Burmann’s Theorem and its Application to Problems of
 * 				Linear and Non-linear Heat Transfer and Diffusion
 * 				https://www.mathematica-journal.com/2014/11/on-burmanns-theorem-and-its-application-to-problems-of-linear-and-nonlinear-heat-transfer-and-diffusion/#more-39602/
 * 		</li>
 * 		<li>
 * 			Wikipedia (2019): Error Function https://en.wikipedia.org/wiki/Error_function
 * 		</li>
 * 	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalOptimizerLibrary.md">Numerical Optimizer</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/feed/README.md">Function</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/feed/e2erf/README.md">E<sub>2</sub> erf and erf<sup>-1</sup> Implementations</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class BuiltInEntry
{
	private double _erf = java.lang.Double.NaN;
	private double _erfc = java.lang.Double.NaN;

	/**
	 * Generate a Table of Built-in E<sub>2</sub> erf/erfc Entries
	 * 
	 * @return The Table of Built-in E<sub>2</sub> erf/erfc Entries
	 */

	public static final java.util.Map<java.lang.Double, BuiltInEntry> Table()
	{
		java.util.Map<java.lang.Double, BuiltInEntry> builtInEntryTable = new
			java.util.TreeMap<java.lang.Double, BuiltInEntry>();

		try
		{
			builtInEntryTable.put (
				0.00,
				new BuiltInEntry (
					0.000000000,
					1.000000000
				)
			);

			builtInEntryTable.put (
				0.02,
				new BuiltInEntry (
					0.022564575,
					0.977435425
				)
			);

			builtInEntryTable.put (
				0.04,
				new BuiltInEntry (
					0.045111106,
					0.954888894
				)
			);

			builtInEntryTable.put (
				0.06,
				new BuiltInEntry (
					0.067621594,
					0.932378406
				)
			);

			builtInEntryTable.put (
				0.08,
				new BuiltInEntry (
					0.090078126,
					0.909921874
				)
			);

			builtInEntryTable.put (
				0.10,
				new BuiltInEntry (
					0.112462916,
					0.887537084
				)
			);

			builtInEntryTable.put (
				0.20,
				new BuiltInEntry (
					0.222792589,
					0.777297411
				)
			);

			builtInEntryTable.put (
				0.30,
				new BuiltInEntry (
					0.328626759,
					0.671373241
				)
			);

			builtInEntryTable.put (
				0.40,
				new BuiltInEntry (
					0.428392355,
					0.571607645
				)
			);

			builtInEntryTable.put (
				0.50,
				new BuiltInEntry (
					0.520499878,
					0.479500122
				)
			);

			builtInEntryTable.put (
				0.60,
				new BuiltInEntry (
					0.603856091,
					0.396143909
				)
			);

			builtInEntryTable.put (
				0.70,
				new BuiltInEntry (
					0.677801194,
					0.322198806
				)
			);

			builtInEntryTable.put (
				0.80,
				new BuiltInEntry (
					0.742100965,
					0.257899035
				)
			);

			builtInEntryTable.put (
				0.90,
				new BuiltInEntry (
					0.796908212,
					0.203091788
				)
			);

			builtInEntryTable.put (
				1.00,
				new BuiltInEntry (
					0.842700793,
					0.157299207
				)
			);

			builtInEntryTable.put (
				1.10,
				new BuiltInEntry (
					0.880205070,
					0.119794930
				)
			);

			builtInEntryTable.put (
				1.20,
				new BuiltInEntry (
					0.910313978,
					0.089686022
				)
			);

			builtInEntryTable.put (
				1.30,
				new BuiltInEntry (
					0.934007945,
					0.065992055
				)
			);

			builtInEntryTable.put (
				1.40,
				new BuiltInEntry (
					0.952285120,
					0.047714880
				)
			);

			builtInEntryTable.put (
				1.50,
				new BuiltInEntry (
					0.966105146,
					0.033894854
				)
			);

			builtInEntryTable.put (
				1.60,
				new BuiltInEntry (
					0.976348383,
					0.023651617
				)
			);

			builtInEntryTable.put (
				1.70,
				new BuiltInEntry (
					0.983790459,
					0.016209541
				)
			);

			builtInEntryTable.put (
				1.80,
				new BuiltInEntry (
					0.989090502,
					0.010909498
				)
			);

			builtInEntryTable.put (
				1.90,
				new BuiltInEntry (
					0.992790429,
					0.007209571
				)
			);

			builtInEntryTable.put (
				2.00,
				new BuiltInEntry (
					0.995322265,
					0.004677735
				)
			);

			builtInEntryTable.put (
				2.10,
				new BuiltInEntry (
					0.997020533,
					0.002979467
				)
			);

			builtInEntryTable.put (
				2.20,
				new BuiltInEntry (
					0.998137154,
					0.001862846
				)
			);

			builtInEntryTable.put (
				2.30,
				new BuiltInEntry (
					0.998856823,
					0.001143177
				)
			);

			builtInEntryTable.put (
				2.40,
				new BuiltInEntry (
					0.999311486,
					0.000688514
				)
			);

			builtInEntryTable.put (
				2.50,
				new BuiltInEntry (
					0.999593048,
					0.000406952
				)
			);

			builtInEntryTable.put (
				3.00,
				new BuiltInEntry (
					0.999977910,
					0.000022090
				)
			);

			builtInEntryTable.put (
				3.50,
				new BuiltInEntry (
					0.999999257,
					0.000000743
				)
			);

			return builtInEntryTable;
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * BuiltInEntry Constructor
	 * 
	 * @param erf E<sub>2</sub> erf Value
	 * @param erfc E<sub>2</sub> erfc Value
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public BuiltInEntry (
		final double erf,
		final double erfc)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (_erf = erf) ||
			!org.drip.quant.common.NumberUtil.IsValid (_erfc = erfc))
		{
			throw new java.lang.Exception ("BuiltInE2Entry Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the E<sub>2</sub> erf
	 * 
	 * @return The E<sub>2</sub> erf
	 */

	public double erf()
	{
		return _erf;
	}

	/**
	 * Retrieve the E<sub>2</sub> erfc
	 * 
	 * @return The E<sub>2</sub> erfc
	 */

	public double erfc()
	{
		return _erfc;
	}
}
