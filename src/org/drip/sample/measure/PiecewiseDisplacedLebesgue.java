
package org.drip.sample.measure;

import org.drip.analytics.date.DateUtil;
import org.drip.analytics.date.JulianDate;
import org.drip.measure.lebesgue.R1PiecewiseDisplaced;
import org.drip.quant.common.FormatUtil;
import org.drip.service.env.EnvManager;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * 
 *  This file is part of DRIP, a free-software/open-source library for buy/side financial/trading model
 *  	libraries targeting analysts and developers
 *  	https://lakshmidrip.github.io/DRIP/
 *  
 *  DRIP is composed of four main libraries:
 *  
 *  - DRIP Fixed Income - https://lakshmidrip.github.io/DRIP-Fixed-Income/
 *  - DRIP Asset Allocation - https://lakshmidrip.github.io/DRIP-Asset-Allocation/
 *  - DRIP Numerical Optimizer - https://lakshmidrip.github.io/DRIP-Numerical-Optimizer/
 *  - DRIP Statistical Learning - https://lakshmidrip.github.io/DRIP-Statistical-Learning/
 * 
 *  - DRIP Fixed Income: Library for Instrument/Trading Conventions, Treasury Futures/Options,
 *  	Funding/Forward/Overnight Curves, Multi-Curve Construction/Valuation, Collateral Valuation and XVA
 *  	Metric Generation, Calibration and Hedge Attributions, Statistical Curve Construction, Bond RV
 *  	Metrics, Stochastic Evolution and Option Pricing, Interest Rate Dynamics and Option Pricing, LMM
 *  	Extensions/Calibrations/Greeks, Algorithmic Differentiation, and Asset Backed Models and Analytics.
 * 
 *  - DRIP Asset Allocation: Library for model libraries for MPT framework, Black Litterman Strategy
 *  	Incorporator, Holdings Constraint, and Transaction Costs.
 * 
 *  - DRIP Numerical Optimizer: Library for Numerical Optimization and Spline Functionality.
 * 
 *  - DRIP Statistical Learning: Library for Statistical Evaluation and Machine Learning.
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
 * PiecewiseDisplacedLebesgue demonstrates the Generation, the Reconciliation, and the Usage of a Piece-wise
 *  Displaced Linear Lebesgue Measure.
 *
 * @author Lakshmi Krishnamurthy
 */

public class PiecewiseDisplacedLebesgue {

	private static final void RPDL (
		final String strMessage,
		final double dblXMin,
		final double dblXMax,
		final double[] adblX,
		final double[] adblProb,
		final double dblXMean)
		throws Exception
	{
		R1PiecewiseDisplaced rpdl = R1PiecewiseDisplaced.Standard (
			dblXMin,
			dblXMax,
			adblX,
			adblProb,
			dblXMean
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
				FormatUtil.FormatDouble (rpdl.cumulative (adblX[i]), 1, 2, 1.) + " | "; */

		for (int i = 0; i < adblQuintile.length; ++i)
			strDump += " " +
				FormatUtil.FormatDouble (rpdl.invCumulative (adblQuintile[i]), 3, 3, 1.) + " =>" +
				FormatUtil.FormatDouble (adblQuintile[i], 1, 2, 1.) + "   | ";

		double[] adblDensitySlope = rpdl.piecewiseDensitySlopes();

		for (int i = 0; i < adblDensitySlope.length; ++i)
			strDump += FormatUtil.FormatDouble (adblDensitySlope[i], 1, 9, 1.) + ",";

		System.out.println (strDump + FormatUtil.FormatDouble (rpdl.densityDisplacement(), 1, 9, 1.) + " ||");
	}

	private static final void DateRPDL (
		final String strMessage,
		final double dblXMin,
		final double dblXMax,
		final double[] adblX,
		final double[] adblProb,
		final double dblXMean)
		throws Exception
	{
		R1PiecewiseDisplaced rpdl = R1PiecewiseDisplaced.Standard (
			dblXMin,
			dblXMax,
			adblX,
			adblProb,
			dblXMean
		);

		double[] adblQuintile = new double[] {
			0.25,
			0.50,
			0.75
		};

		String strDump = "\t|| " + strMessage + " | ";

		for (int i = 0; i < adblQuintile.length; ++i)
			strDump +=
				new JulianDate ((int) rpdl.invCumulative (adblQuintile[i])) + " =>" +
				FormatUtil.FormatDouble (adblQuintile[i], 1, 2, 1.) + " | ";

		double[] adblDensitySlope = rpdl.piecewiseDensitySlopes();

		for (int i = 0; i < adblDensitySlope.length; ++i)
			strDump += FormatUtil.FormatDouble (adblDensitySlope[i], 1, 9, 1.) + ",";

		System.out.println (strDump + FormatUtil.FormatDouble (rpdl.densityDisplacement(), 1, 9, 1.) + " ||");
	}

	public static final void main (
		final String[] args)
		throws Exception
	{
		EnvManager.InitEnv ("");

		System.out.println();

		System.out.println ("\t||-----------------------------------------------------------------------------------------------------------------------------------------------------------------------||");

		System.out.println ("\t||             FIELD                |                       CUMULATIVE PROBABILITY                    |                  PROBABILITY DENSITY PARAMETERS                  ||");

		System.out.println ("\t||-----------------------------------------------------------------------------------------------------------------------------------------------------------------------||");

		RPDL (
			"Age (Months In Balance)         ",
			0.,
			60.,
			new double[] {3., 8., 15.},
			new double[] {0.25, 0.50, 0.75},
			10.1
		);

		DateRPDL (
			"Vintage                         ",
			DateUtil.CreateFromDDMMMYYYY ("15-FEB-2007").julian(),
			DateUtil.CreateFromDDMMMYYYY ("15-FEB-2015").julian(),
			new double[] {
				DateUtil.CreateFromDDMMMYYYY ("15-APR-2012").julian(),
				DateUtil.CreateFromDDMMMYYYY ("15-MAR-2013").julian(),
				DateUtil.CreateFromDDMMMYYYY ("15-FEB-2014").julian()
			},
			new double[] {0.25, 0.50, 0.75},
			DateUtil.CreateFromDDMMMYYYY ("15-FEB-2013").julian()
		);

		RPDL (
			"Original Principal ('000s)      ",
			0.5,
			35.,
			new double[] {8., 12., 20.},
			new double[] {0.25, 0.50, 0.75},
			14.254
		);

		RPDL (
			"Monthly Gross Income ('000s)    ",
			0.25,
			725.549,
			new double[] {3.75, 5.167, 7.333},
			new double[] {0.25, 0.50, 0.75},
			6.066
		);

		RPDL (
			"Coupon (%)                      ",
			5.3,
			29.,
			new double[] {10.6, 13.5, 16.3},
			new double[] {0.25, 0.50, 0.75},
			13.70
		);

		RPDL (
			"FICO At Origination             ",
			612.,
			847.,
			new double[] {677., 692., 717.},
			new double[] {0.25, 0.50, 0.75},
			699.
		);

		RPDL (
			"DTI ex Mortgage (%)             ",
			0.,
			39.,
			new double[] {11., 16., 22.},
			new double[] {0.25, 0.50, 0.75},
			16.6
		);

		RPDL (
			"Total Borrower Accounts         ",
			1,
			162,
			new double[] {16., 23., 31.},
			new double[] {0.25, 0.50, 0.75},
			25
		);

		RPDL (
			"Revolving Utilization Rate (%)  ",
			0.,
			892.,
			new double[] {40., 58., 75.},
			new double[] {0.25, 0.50, 0.75},
			57.
		);

		RPDL (
			"Inquiries in Last 6 Months      ",
			0.,
			33.,
			new double[] {1.},
			new double[] {0.75},
			0.9
		);
	}
}
