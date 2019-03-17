
package org.drip.sample.measure;

import org.drip.analytics.date.*;
import org.drip.measure.lebesgue.R1PiecewiseLinear;
import org.drip.numerical.common.FormatUtil;
import org.drip.service.env.EnvManager;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
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
 * <i>PiecewiseLinearLebesgue</i> demonstrates the Generation, the Reconciliation, and the Usage of a
 * Piece-wise Linear Lebesgue Measure.
 *  
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalSupportLibrary.md">Numerical Support Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">Sample</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/measure/README.md">Probability Measure Generators</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class PiecewiseLinearLebesgue {

	private static final void RPLL (
		final String strMessage,
		final double dblXMin,
		final double dblXMax,
		final double[] adblX,
		final double[] adblProb)
		throws Exception
	{
		R1PiecewiseLinear rpll = R1PiecewiseLinear.Standard (
			dblXMin,
			dblXMax,
			adblX,
			adblProb
		);

		double[] adblQuintile = new double[] {
			0.25,
			0.50,
			0.75
		};

		String strDump = "\t|| " + strMessage + " | ";

		/* for (int i = 0; i < adblX.length; ++i)
			strDump +=
				FormatUtil.FormatDouble (adblX[i], 3, 3, 1.) + " =>" +
				FormatUtil.FormatDouble (rpll.cumulative (adblX[i]), 1, 2, 1.) + " | "; */

		for (int i = 0; i < adblQuintile.length; ++i)
			strDump += " " +
				FormatUtil.FormatDouble (rpll.invCumulative (adblQuintile[i]), 3, 3, 1.) + " =>" +
				FormatUtil.FormatDouble (adblQuintile[i], 1, 2, 1.) + "   | ";

		double[] adblDensity = rpll.piecewiseDensities();

		for (int i = 0; i < adblDensity.length; ++i)
			strDump += FormatUtil.FormatDouble (adblDensity[i], 1, 9, 1.) + ",";

		System.out.println (strDump + " ||");
	}

	private static final void DateRPLL (
		final String strMessage,
		final double dblXMin,
		final double dblXMax,
		final double[] adblX,
		final double[] adblProb)
		throws Exception
	{
		R1PiecewiseLinear rpll = R1PiecewiseLinear.Standard (
			dblXMin,
			dblXMax,
			adblX,
			adblProb
		);

		double[] adblQuintile = new double[] {
			0.25,
			0.50,
			0.75
		};

		String strDump = "\t|| " + strMessage + " | ";

		for (int i = 0; i < adblQuintile.length; ++i)
			strDump +=
				new JulianDate ((int) rpll.invCumulative (adblQuintile[i])) + " =>" +
				FormatUtil.FormatDouble (adblQuintile[i], 1, 2, 1.) + " | ";

		double[] adblDensity = rpll.piecewiseDensities();

		for (int i = 0; i < adblDensity.length; ++i)
			strDump += FormatUtil.FormatDouble (adblDensity[i], 1, 9, 1.) + ",";

		System.out.println (strDump + " ||");
	}

	public static final void main (
		final String[] args)
		throws Exception
	{
		EnvManager.InitEnv ("");

		System.out.println();

		System.out.println ("\t||-----------------------------------------------------------------------------------------------------------------------------------------------------------||");

		System.out.println ("\t||             FIELD                |                       CUMULATIVE PROBABILITY                    |               PROBABILITY DENSITY NODES              ||");

		System.out.println ("\t||-----------------------------------------------------------------------------------------------------------------------------------------------------------||");

		RPLL (
			"Age (Months In Balance)         ",
			0.,
			60.,
			new double[] {3., 8., 15.},
			new double[] {0.25, 0.50, 0.75}
		);

		DateRPLL (
			"Vintage                         ",
			DateUtil.CreateFromDDMMMYYYY ("01-FEB-2007").julian(),
			DateUtil.CreateFromDDMMMYYYY ("01-FEB-2015").julian(),
			new double[] {
				DateUtil.CreateFromDDMMMYYYY ("01-APR-2012").julian(),
				DateUtil.CreateFromDDMMMYYYY ("01-MAR-2013").julian(),
				DateUtil.CreateFromDDMMMYYYY ("01-FEB-2014").julian()
			},
			new double[] {0.25, 0.50, 0.75}
		);

		RPLL (
			"Original Principal ('000s)      ",
			0.5,
			35.,
			new double[] {8., 12., 20.},
			new double[] {0.25, 0.50, 0.75}
		);

		RPLL (
			"Monthly Gross Income ('000s)    ",
			0.25,
			725.549,
			new double[] {3.75, 5.167, 7.333},
			new double[] {0.25, 0.50, 0.75}
		);

		RPLL (
			"Coupon (%)                      ",
			5.3,
			29.,
			new double[] {10.6, 13.5, 16.3},
			new double[] {0.25, 0.50, 0.75}
		);

		RPLL (
			"FICO At Origination             ",
			612.,
			847.,
			new double[] {677., 692., 717.},
			new double[] {0.25, 0.50, 0.75}
		);

		RPLL (
			"DTI ex Mortgage (%)             ",
			0.,
			30.,
			new double[] {11., 16., 22.},
			new double[] {0.25, 0.50, 0.75}
		);

		RPLL (
			"Total Borrower Accounts         ",
			1.,
			162.,
			new double[] {16., 23., 31.},
			new double[] {0.25, 0.50, 0.75}
		);

		RPLL (
			"Revolving Utilization Rate (%)  ",
			0.,
			892.,
			new double[] {40., 58., 75.},
			new double[] {0.25, 0.50, 0.75}
		);

		RPLL (
			"Inquiries in Last 6 Months      ",
			0.,
			33.,
			new double[] {1.},
			new double[] {0.75}
		);

		RPLL (
			"Months Since Last Delinquency   ",
			0.,
			188.,
			new double[] {16., 31., 50.},
			new double[] {0.25, 0.50, 0.75}
		);

		RPLL (
			"Months Since Last Public Record ",
			0.,
			129.,
			new double[] {55., 79., 102.},
			new double[] {0.25, 0.50, 0.75}
		);

		RPLL (
			"Total Open Credit Lines         ",
			0.,
			90.,
			new double[] {8., 10., 14.},
			new double[] {0.25, 0.50, 0.75}
		);

		System.out.println ("\t||-----------------------------------------------------------------------------------------------------------------------------------------------------------||");

		EnvManager.TerminateEnv();
	}
}
