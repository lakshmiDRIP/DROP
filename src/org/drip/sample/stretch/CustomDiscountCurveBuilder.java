	
package org.drip.sample.stretch;

/*
 * Java Imports
 */

import java.util.*;

import org.drip.function.r1tor1.QuadraticRationalShapeControl;
import org.drip.quant.common.FormatUtil;
import org.drip.spline.basis.*;
import org.drip.spline.params.*;
import org.drip.spline.stretch.*;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
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
 * CustomDiscountCurveBuilder contains samples that demo how to build a discount curve from purely the cash
 *  flows. It provides for elaborate curve builder control, both at the segment level and at the Stretch
 *  level. In particular, it shows the following:
 * 	- Construct a discount curve from the discount factors available purely from the cash and the euro-dollar
 *  	instruments.
 * 	- Construct a discount curve from the cash flows available from the swap instruments.
 * 
 * In addition, the sample demonstrates the following ways of controlling curve construction:
 * 	- Control over the type of segment basis spline
 * 	- Control over the polynomial basis spline order, Ck, and tension parameters
 * 	- Provision of custom shape controllers (in this case rational shape controller)
 * 	- Calculation of segment monotonicity and convexity
 * 
 * @author Lakshmi Krishnamurthy
 */

public class CustomDiscountCurveBuilder {

	/*
	 * Sample API demonstrating the creation of the segment builder parameters based on Koch-Lyche-Kvasov tension spline.
	 * 
	 *  	USE WITH CARE: This sample ignores errors and does not handle exceptions.
	 */

	private static final SegmentCustomBuilderControl MakeKLKTensionSCBC (
		final double dblTension)
		throws Exception
	{
		return new SegmentCustomBuilderControl (
			MultiSegmentSequenceBuilder.BASIS_SPLINE_KLK_HYPERBOLIC_TENSION, // Spline Type KLK Hyperbolic Basis Tension
			new ExponentialTensionSetParams (dblTension), // Segment Tension Parameter Value
			SegmentInelasticDesignControl.Create (2, 2), // Ck = 2; Curvature penalty (if necessary) order: 2
			new ResponseScalingShapeControl (
				true,
				new QuadraticRationalShapeControl (0.0)), // Univariate Rational Shape Controller
			null
		);
	}

	/*
	 * Sample API demonstrating the creation of the segment builder parameters based on polynomial spline.
	 * 
	 *  	USE WITH CARE: This sample ignores errors and does not handle exceptions.
	 */

	public static final SegmentCustomBuilderControl MakePolynomialSBP (
		final int iNumDegree)
		throws Exception
	{
		return new SegmentCustomBuilderControl (
			MultiSegmentSequenceBuilder.BASIS_SPLINE_POLYNOMIAL, // Spline Type Polynomial
			new PolynomialFunctionSetParams (iNumDegree + 1), // Polynomial of degree (i.e, cubic would be 3+1; 4 basis functions - 1 "intercept")
			SegmentInelasticDesignControl.Create (2, 2), // Ck = 2; Curvature penalty (if necessary) order: 2
			new ResponseScalingShapeControl (
				true,
				new QuadraticRationalShapeControl (0.0)), // Univariate Rational Shape Controller
			null
		);
	}

	/*
	 * Sample API demonstrating the creation of the segment builder parameters
	 * 
	 *  	USE WITH CARE: This sample ignores errors and does not handle exceptions.
	 */

	private static final SegmentCustomBuilderControl MakeSCBC (
		final String strBasisSpline)
		throws Exception
	{
		if (strBasisSpline.equalsIgnoreCase (MultiSegmentSequenceBuilder.BASIS_SPLINE_POLYNOMIAL)) // Polynomial Basis Spline
			return new SegmentCustomBuilderControl (
				MultiSegmentSequenceBuilder.BASIS_SPLINE_POLYNOMIAL, // Spline Type Polynomial
				new PolynomialFunctionSetParams (4), // Polynomial of order 3 (i.e, cubic - 4 basis functions - 1 "intercept")
				SegmentInelasticDesignControl.Create (2, 2), // Ck = 2; Curvature penalty (if necessary) order: 2
				new ResponseScalingShapeControl (
					true,
					new QuadraticRationalShapeControl (0.0)), // Univariate Rational Shape Controller
				null
			);

		if (strBasisSpline.equalsIgnoreCase (MultiSegmentSequenceBuilder.BASIS_SPLINE_EXPONENTIAL_TENSION)) // Exponential Tension Basis Spline
			return new SegmentCustomBuilderControl (
				MultiSegmentSequenceBuilder.BASIS_SPLINE_EXPONENTIAL_TENSION, // Spline Type Exponential Basis Tension
				new ExponentialTensionSetParams (1.), // Segment Tension Parameter Value = 1.
				SegmentInelasticDesignControl.Create (2, 2), // Ck = 2; Curvature penalty (if necessary) order: 2
				new ResponseScalingShapeControl (
					true,
					new QuadraticRationalShapeControl (0.0)), // Univariate Rational Shape Controller
				null
			);

		return null;
	}

	/*
	 * Generate the sample Swap Cash Flows to a given maturity, for the frequency/coupon.
	 * 	Cash Flow is in the form of <Date, Cash Amount> Map.
	 * 
	 *  	USE WITH CARE: This sample ignores errors and does not handle exceptions.
	 */

	private static final TreeMap<Double, Double> SwapCashFlow (
		final double dblCoupon,
		final int iFreq,
		final double dblTenorInYears)
	{
		TreeMap<Double, Double> mapCF = new TreeMap<Double, Double>();

		for (double dblCFDate = 1. / iFreq; dblCFDate < dblTenorInYears; dblCFDate += 1. / iFreq)
			mapCF.put (
				dblCFDate,
				dblCoupon / iFreq
			);

		mapCF.put (
			0.,
			-1.
		);

		mapCF.put (
			1. * dblTenorInYears,
			1. + dblCoupon / iFreq
		);

		return mapCF;
	}

	/**
	 * Generate the DRIP linear constraint corresponding to an exclusive swap segment. This constraint is
	 * 	used to calibrate the discount curve in this segment.
	 *  
	 *  	USE WITH CARE: This sample ignores errors and does not handle exceptions.
	 */

	private static final SegmentResponseValueConstraint GenerateSegmentConstraint (
		final TreeMap<Double, Double> mapCF,
		final MultiSegmentSequence mssDF)
		throws Exception
	{
		double dblValue = 0.;

		List<Double> lsTime = new ArrayList<Double>();

		List<Double> lsWeight = new ArrayList<Double>();

		for (Map.Entry<Double, Double> me : mapCF.entrySet()) {
			double dblTime = me.getKey();

			if (null != mssDF && mssDF.in (dblTime))
				dblValue += mssDF.responseValue (dblTime) * me.getValue();
			else {
				lsTime.add (me.getKey());

				lsWeight.add (me.getValue());
			}
		}

		int iSize = lsTime.size();

		double[] adblNode = new double[iSize];
		double[] adblNodeWeight = new double[iSize];

		for (int i = 0; i < iSize; ++i) {
			adblNode[i] = lsTime.get (i);

			adblNodeWeight[i] = lsWeight.get (i);
		}

		return new SegmentResponseValueConstraint (
			adblNode,
			adblNodeWeight,
			-dblValue
		);
	}

	/**
	 * The set of Par Swap Quotes.
	 * 
	 *  	USE WITH CARE: This sample ignores errors and does not handle exceptions.
	 */

	private static final Map<Double, Double> SwapQuotes()
	{
		Map<Double, Double> mapSwapQuotes = new TreeMap<Double, Double>();

		mapSwapQuotes.put (4., 0.0166);

		mapSwapQuotes.put (5., 0.0206);

		mapSwapQuotes.put (6., 0.0241);

		mapSwapQuotes.put (7., 0.0269);

		mapSwapQuotes.put (8., 0.0292);

		mapSwapQuotes.put (9., 0.0311);

		mapSwapQuotes.put (10., 0.0326);

		mapSwapQuotes.put (11., 0.0340);

		mapSwapQuotes.put (12., 0.0351);

		mapSwapQuotes.put (15., 0.0375);

		mapSwapQuotes.put (20., 0.0393);

		mapSwapQuotes.put (25., 0.0402);

		mapSwapQuotes.put (30., 0.0407);

		mapSwapQuotes.put (40., 0.0409);

		mapSwapQuotes.put (50., 0.0409);

		return mapSwapQuotes;
	}

	/**
	 * Sample Function illustrating the construction of the discount curve off of swap cash flows and
	 *  detailed segment level controls for the swap instruments.Further, the Segment Builder Parameters
	 *  for the cash/swap bridging stretch shown here illustrate using an exponential/hyperbolic spline with
	 *  very high tension (100000.) to "stitch" the cash stretch with the swaps Stretch.
	 * 
	 * Each of the respective stretches have their own tension settings, so the "high" tension
	 *  ensures that there is no propagation of derivatives and therefore high locality.
	 *  
	 *  	USE WITH CARE: This sample ignores errors and does not handle exceptions.
	 */

	private static final MultiSegmentSequence BuildSwapCurve (
		MultiSegmentSequence mss,
		final BoundarySettings bs,
		final int iCalibrationDetail)
		throws Exception
	{
		boolean bFirstNode = true;

		/*
		 * Iterate through the swap instruments and their quotes.
		 */

		for (Map.Entry<Double, Double> meSwapQuote : SwapQuotes().entrySet()) {
			double dblTenorInYears = meSwapQuote.getKey(); // Swap Maturity in Years

			double dblQuote = meSwapQuote.getValue(); // Par Swap Quote

			/*
			 * Generate the Cash flow for the swap Instrument
			 */

			TreeMap<Double, Double> mapCF = SwapCashFlow (
				dblQuote,
				2,
				dblTenorInYears
			);

			/*
			 * Convert the Cash flow into a DRIP segment constraint using the "prior" curve stretch
			 */

			SegmentResponseValueConstraint srvc = GenerateSegmentConstraint (
				mapCF,
				mss
			);

			/*
			 * If it is the head segment, create a stretch instance for the discount curve.
			 */

			if (null == mss) {
				/*
				 * Set the Segment Builder Parameters. This may be set on a segment-by-segment basis.
				 */

				SegmentCustomBuilderControl scbc = MakeSCBC (MultiSegmentSequenceBuilder.BASIS_SPLINE_EXPONENTIAL_TENSION);

				/*
				 * Start off with a single segment stretch, with the corresponding Builder Parameters
				 */

				mss = MultiSegmentSequenceBuilder.CreateUncalibratedStretchEstimator (
					"SWAP",
					new double[] {0., dblTenorInYears},
					new SegmentCustomBuilderControl[] {scbc}
				);

				/*
				 * Set the stretch up by carrying out a "Natural Boundary" Spline Calibration
				 */

				mss.setup (
					1.,
					new SegmentResponseValueConstraint[] {srvc},
					null,
					bs,
					iCalibrationDetail
				);
			} else {
				/*
				 * The Segment Builder Parameters shown here illustrate using an exponential/hyperbolic
				 *  spline with high tension (15.) to "stitch" the cash stretch with the swaps stretch.
				 *  
				 * Each of the respective stretches have their own tension settings, so the "high" tension
				 *  ensures that there is no propagation of derivatives and therefore high locality.
				 */

				SegmentCustomBuilderControl scbcLocal = null;

				if (bFirstNode) {
					bFirstNode = false;

					scbcLocal = MakeKLKTensionSCBC (1.);
				} else
					scbcLocal = MakeKLKTensionSCBC (1.);

				/*
				 * If not the head segment, just append the exclusive swap instrument segment to the tail of
				 * 	the current stretch state, using the constraint generated from the swap cash flow.
				 */

				mss = org.drip.spline.stretch.MultiSegmentSequenceModifier.AppendSegment (
					mss,
					dblTenorInYears,
					srvc,
					scbcLocal,
					bs,
					iCalibrationDetail
				);
			}
		}

		return mss;
	}

	/**
	 * The set of Cash Discount Factors.
	 * 
	 *  	USE WITH CARE: This sample ignores errors and does not handle exceptions.
	 */

	private static final Map<Double, Double> CashDFQuotes()
	{
		Map<Double, Double> mapDFCashQuotes = new TreeMap<Double, Double>();

		mapDFCashQuotes.put (0.005556, 0.999991);

		mapDFCashQuotes.put (0.019444, 0.999967);

		mapDFCashQuotes.put (0.038889, 0.999931);

		mapDFCashQuotes.put (0.083333, 0.999836);

		mapDFCashQuotes.put (0.166667, 0.999622);

		mapDFCashQuotes.put (0.250000, 0.999360);

		mapDFCashQuotes.put (0.500000, 0.998686);

		mapDFCashQuotes.put (0.750000, 0.997888);

		mapDFCashQuotes.put (1.000000, 0.996866);

		mapDFCashQuotes.put (1.250000, 0.995522);

		mapDFCashQuotes.put (1.500000, 0.993609);

		mapDFCashQuotes.put (1.750000, 0.991033);

		mapDFCashQuotes.put (2.000000, 0.987724);

		mapDFCashQuotes.put (2.250000, 0.983789);

		return mapDFCashQuotes;
	}

	/**
	 * Sample Function illustrating the construction of the discount curve off of discount factors and
	 *  detailed segment level controls for the cash instruments.
	 * 
	 *  	USE WITH CARE: This sample ignores errors and does not handle exceptions.
	 */

	private static final MultiSegmentSequence BuildCashCurve (
		final org.drip.spline.stretch.BoundarySettings bs,
		final int iCalibrationDetail)
		throws Exception
	{
		/*
		 * For the head segment, create a calibrated stretch instance for the discount curve.
		 */

		MultiSegmentSequence mssCash = MultiSegmentSequenceBuilder.CreateCalibratedStretchEstimator (
			"CASH",
			new double[] {0., 0.002778}, // t0 and t1 for the segment
			new double[] {1., 0.999996}, // the corresponding discount factors
			new SegmentCustomBuilderControl[] {
				// MakeSCBC (MultiSegmentSequenceBuilder.BASIS_SPLINE_EXPONENTIAL_TENSION)
				MakeKLKTensionSCBC (1.)
			}, // Exponential Tension Basis Spline
			null,
			bs,
			iCalibrationDetail // "Natural" Spline Boundary Condition + Calibrate the full stretch
		);

		/*
		 * Construct the discount curve by iterating through the cash instruments and their discount
		 * 	factors, and inserting them as "knots" onto the existing stretch.
		 */

		for (Map.Entry<Double, Double> meCashDFQuote : CashDFQuotes().entrySet()) {
			double dblTenorInYears = meCashDFQuote.getKey(); // Instrument Tenor in Years

			double dblDF = meCashDFQuote.getValue(); // Discount Factor

			/*
			 * Insert the instrument/quote as a "knot" entity into the stretch. Given the "natural" spline
			 */

			mssCash = MultiSegmentSequenceModifier.InsertKnot (
				mssCash,
				dblTenorInYears,
				dblDF,
				bs,
				iCalibrationDetail
			);
		}

		return mssCash;
	}

	/*
	 * This sample demonstrates the usage construction and usage of Custom Curve Building. It shows the following:
	 * 	- Construct the Cash Curve Sequence with the Standard Natural Boundary Condition.
	 * 	- Construct the Cash Curve Sequence with the Standard Financial Boundary Condition.
	 * 	- Construct the Cash Curve Sequence with the Standard Not-A-Knot Boundary Condition.
	 * 	- Display the DF and the monotonicity for the cash instruments.
	 * 	- Construct the Swap Curve Sequence with the Standard Natural Boundary Condition.
	 * 	- Construct the Swap Curve Sequence with the Standard Financial Boundary Condition.
	 * 	- Construct the Swap Curve Sequence with the Standard Not-A-Knot Boundary Condition.
	 * 	- Display the DF and the monotonicity for the swap instruments.
	 */

	private static final void CustomCurveBuilderTest()
		throws Exception
	{
		/*
		 * Construct the Cash Curve Sequence with the Standard Natural Boundary Condition
		 */

		MultiSegmentSequence mssNaturalCash = BuildCashCurve (
			BoundarySettings.NaturalStandard(),
			MultiSegmentSequence.CALIBRATE
		);

		/*
		 * Construct the Cash Curve Sequence with the Standard Financial Boundary Condition
		 */

		MultiSegmentSequence mssFinancialCash = BuildCashCurve (
			BoundarySettings.FinancialStandard(),
			MultiSegmentSequence.CALIBRATE
		);

		/*
		 * Construct the Cash Curve Sequence with the Standard Not-A-Knot Boundary Condition
		 */

		MultiSegmentSequence mssNotAKnotCash = BuildCashCurve (
			BoundarySettings.NotAKnotStandard (1, 1),
			MultiSegmentSequence.CALIBRATE
		);

		double dblXShift = 0.1 * (mssNaturalCash.getRightPredictorOrdinateEdge() - mssNaturalCash.getLeftPredictorOrdinateEdge());

		System.out.println ("\n\t\t\t----------------       <====>    ------------------       <====>    ------------------");

		System.out.println ("\t\t\tNATURAL BOUNDARY       <====>   NOT A KNOT BOUNDARY       <====>    FINANCIAL BOUNDARY");

		System.out.println ("\t\t\t----------------       <====>    ------------------       <====>    ------------------\n");

		/*
		 * Display the DF and the monotonicity for the cash instruments.
		 */

		for (double dblX = mssNaturalCash.getLeftPredictorOrdinateEdge(); dblX <= mssNaturalCash.getRightPredictorOrdinateEdge(); dblX = dblX + dblXShift)
			System.out.println ("Cash DF[" +
				FormatUtil.FormatDouble (dblX, 1, 3, 1.) + "Y] => " +
				FormatUtil.FormatDouble (mssNaturalCash.responseValue (dblX), 1, 6, 1.) + " | " +
				mssNaturalCash.monotoneType (dblX) + "  <====>  " +
				FormatUtil.FormatDouble (mssNotAKnotCash.responseValue (dblX), 1, 6, 1.) + " | " +
				mssNotAKnotCash.monotoneType (dblX) + "  <====>  " +
				FormatUtil.FormatDouble (mssFinancialCash.responseValue (dblX), 1, 6, 1.) + " | " +
				mssNaturalCash.monotoneType (dblX));

		System.out.println ("\n");

		/*
		 * Construct the Swap Curve Sequence with the Standard Natural Boundary Condition
		 */

		MultiSegmentSequence mssNaturalSwap = BuildSwapCurve (
			mssNaturalCash,
			BoundarySettings.NaturalStandard(),
			MultiSegmentSequence.CALIBRATE
		);

		/*
		 * Construct the Swap Curve Sequence with the Standard Financial Boundary Condition
		 */

		MultiSegmentSequence mssFinancialSwap = BuildSwapCurve (
			mssFinancialCash,
			BoundarySettings.FinancialStandard(),
			MultiSegmentSequence.CALIBRATE
		);

		/*
		 * Construct the Swap Curve Sequence with the Standard Not-A-Knot Boundary Condition
		 */

		MultiSegmentSequence mssNotAKnotSwap = BuildSwapCurve (
			mssNotAKnotCash,
			BoundarySettings.NotAKnotStandard (1, 1),
			MultiSegmentSequence.CALIBRATE
		);

		/*
		 * Display the DF and the monotonicity for the swaps.
		 */

		dblXShift = 0.05 * (mssNaturalSwap.getRightPredictorOrdinateEdge() - mssNaturalSwap.getLeftPredictorOrdinateEdge());

		for (double dblX = mssNaturalSwap.getLeftPredictorOrdinateEdge(); dblX <= mssNaturalSwap.getRightPredictorOrdinateEdge(); dblX = dblX + dblXShift)
			System.out.println (
				"Swap DF   [" +
				FormatUtil.FormatDouble (dblX, 2, 0, 1.) + "Y] => " +
				FormatUtil.FormatDouble (mssNaturalSwap.responseValue (dblX), 1, 6, 1.) + " | " +
				mssNaturalSwap.monotoneType (dblX) + "  <====>  " +
				FormatUtil.FormatDouble (mssNotAKnotSwap.responseValue (dblX), 1, 6, 1.) + " | " +
				mssNotAKnotSwap.monotoneType (dblX) + "  <====>  " +
				FormatUtil.FormatDouble (mssFinancialSwap.responseValue (dblX), 1, 6, 1.) + " | " +
				mssFinancialSwap.monotoneType (dblX)
			);
	}

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		CustomCurveBuilderTest();
	}
}
