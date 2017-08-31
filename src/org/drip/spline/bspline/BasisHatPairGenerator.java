
package org.drip.spline.bspline;

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
 * BasisHatPairGenerator implements the generation functionality behind the hat basis function pair. It
 * 	provides the following functionality:
 * 	- Generate the array of the Hyperbolic Phy and Psy Hat Function Pair.
 * 	- Generate the array of the Hyperbolic Phy and Psy Hat Function Pair From their Raw Counterparts.
 * 	- Generate the array of the Cubic Rational Phy and Psy Hat Function Pair From their Raw Counterparts.
 * 	- Generate the array of the Custom Phy and Psy Hat Function Pair From their Raw Counterparts.
 *
 * @author Lakshmi Krishnamurthy
 */

public class BasisHatPairGenerator {

	/**
	 * Raw Tension Hyperbolic B Spline Basis Hat Phy and Psy
	 */

	public static final java.lang.String RAW_TENSION_HYPERBOLIC = "RAW_TENSION_HYPERBOLIC";

	/**
	 * Processed Tension Hyperbolic B Spline Basis Hat Phy and Psy
	 */

	public static final java.lang.String PROCESSED_TENSION_HYPERBOLIC = "PROCESSED_TENSION_HYPERBOLIC";

	/**
	 * Processed Cubic Rational B Spline Basis Hat Phy and Psy
	 */

	public static final java.lang.String PROCESSED_CUBIC_RATIONAL = "PROCESSED_CUBIC_RATIONAL";

	/**
	 * Generate the array of the Hyperbolic Phy and Psy Hat Function Pair
	 * 
	 * @param dblPredictorOrdinateLeading The Leading Predictor Ordinate
	 * @param dblPredictorOrdinateFollowing The Following Predictor Ordinate
	 * @param dblPredictorOrdinateTrailing The Trailing Predictor Ordinate
	 * @param dblTension Tension
	 * 
	 * @return The array of Hyperbolic Phy and Psy Hat Function Pair
	 */

	public static final org.drip.spline.bspline.TensionBasisHat[] HyperbolicTensionHatPair (
		final double dblPredictorOrdinateLeading,
		final double dblPredictorOrdinateFollowing,
		final double dblPredictorOrdinateTrailing,
		final double dblTension)
	{
		try {
			return new org.drip.spline.bspline.TensionBasisHat[] {new
				org.drip.spline.bspline.ExponentialTensionLeftHat (dblPredictorOrdinateLeading,
					dblPredictorOrdinateFollowing, dblTension), new
						org.drip.spline.bspline.ExponentialTensionRightHat (dblPredictorOrdinateFollowing,
							dblPredictorOrdinateTrailing, dblTension)};
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the array of the Hyperbolic Phy and Psy Hat Function Pair From their Raw Counterparts
	 * 
	 * @param dblPredictorOrdinateLeading The Leading Predictor Ordinate
	 * @param dblPredictorOrdinateFollowing The Following Predictor Ordinate
	 * @param dblPredictorOrdinateTrailing The Trailing Predictor Ordinate
	 * @param iDerivOrder The Derivative Order
	 * @param dblTension Tension
	 * 
	 * @return The array of Hyperbolic Phy and Psy Hat Function Pair
	 */

	public static final org.drip.spline.bspline.TensionBasisHat[] ProcessedHyperbolicTensionHatPair (
		final double dblPredictorOrdinateLeading,
		final double dblPredictorOrdinateFollowing,
		final double dblPredictorOrdinateTrailing,
		final int iDerivOrder,
		final double dblTension)
	{
		try {
			return new org.drip.spline.bspline.TensionBasisHat[] {new
				org.drip.spline.bspline.TensionProcessedBasisHat (new
					org.drip.spline.bspline.ExponentialTensionLeftRaw (dblPredictorOrdinateLeading,
						dblPredictorOrdinateFollowing, dblTension), iDerivOrder), new
							org.drip.spline.bspline.TensionProcessedBasisHat (new
								org.drip.spline.bspline.ExponentialTensionRightRaw
									(dblPredictorOrdinateFollowing, dblPredictorOrdinateTrailing,
										dblTension), iDerivOrder)};
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the array of the Cubic Rational Phy and Psy Hat Function Pair From their Raw Counterparts
	 * 
	 * @param strShapeControlType Type of the Shape Controller to be used - NONE, LINEAR/QUADRATIC Rational
	 * @param dblPredictorOrdinateLeading The Leading Predictor Ordinate
	 * @param dblPredictorOrdinateFollowing The Following Predictor Ordinate
	 * @param dblPredictorOrdinateTrailing The Trailing Predictor Ordinate
	 * @param iDerivOrder The Derivative Order
	 * @param dblTension Tension
	 * 
	 * @return The array of Cubic Rational Phy and Psy Hat Function Pair
	 */

	public static final org.drip.spline.bspline.TensionBasisHat[] ProcessedCubicRationalHatPair (
		final java.lang.String strShapeControlType,
		final double dblPredictorOrdinateLeading,
		final double dblPredictorOrdinateFollowing,
		final double dblPredictorOrdinateTrailing,
		final int iDerivOrder,
		final double dblTension)
	{
		try {
			return new org.drip.spline.bspline.TensionBasisHat[] {new
				org.drip.spline.bspline.TensionProcessedBasisHat (new
					org.drip.spline.bspline.CubicRationalLeftRaw (dblPredictorOrdinateLeading,
						dblPredictorOrdinateFollowing, strShapeControlType, dblTension), iDerivOrder), new
							org.drip.spline.bspline.TensionProcessedBasisHat (new
								org.drip.spline.bspline.CubicRationalRightRaw (dblPredictorOrdinateFollowing,
									dblPredictorOrdinateTrailing, strShapeControlType, dblTension),
										iDerivOrder)};
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the array of the Cubic Rational Phy and Psy Hat Function Pair From their Raw Counterparts
	 * 
	 * @param strHatType The Primitive Hat Type
	 * @param strShapeControlType Type of the Shape Controller to be used - NONE, LINEAR/QUADRATIC Rational
	 * @param dblPredictorOrdinateLeading The Leading Predictor Ordinate
	 * @param dblPredictorOrdinateFollowing The Following Predictor Ordinate
	 * @param dblPredictorOrdinateTrailing The Trailing Predictor Ordinate
	 * @param iDerivOrder The Derivative Order
	 * @param dblTension Tension
	 * 
	 * @return The array of Cubic Rational Phy and Psy Hat Function Pair
	 */

	public static final org.drip.spline.bspline.TensionBasisHat[] GenerateHatPair (
		final java.lang.String strHatType,
		final java.lang.String strShapeControlType,
		final double dblPredictorOrdinateLeading,
		final double dblPredictorOrdinateFollowing,
		final double dblPredictorOrdinateTrailing,
		final int iDerivOrder,
		final double dblTension)
	{
		if (null == strHatType || (!RAW_TENSION_HYPERBOLIC.equalsIgnoreCase (strHatType) &&
			!PROCESSED_TENSION_HYPERBOLIC.equalsIgnoreCase (strHatType) &&
				!PROCESSED_CUBIC_RATIONAL.equalsIgnoreCase (strHatType)))
				return null;

		if (org.drip.spline.bspline.BasisHatPairGenerator.RAW_TENSION_HYPERBOLIC.equalsIgnoreCase
			(strHatType))
			return HyperbolicTensionHatPair (dblPredictorOrdinateLeading, dblPredictorOrdinateFollowing,
				dblPredictorOrdinateTrailing, dblTension);

		if (org.drip.spline.bspline.BasisHatPairGenerator.PROCESSED_TENSION_HYPERBOLIC.equalsIgnoreCase
			(strHatType))
			return ProcessedHyperbolicTensionHatPair (dblPredictorOrdinateLeading,
				dblPredictorOrdinateFollowing, dblPredictorOrdinateTrailing, iDerivOrder, dblTension);

		return ProcessedCubicRationalHatPair (strShapeControlType, dblPredictorOrdinateLeading,
			dblPredictorOrdinateFollowing, dblPredictorOrdinateTrailing, iDerivOrder, dblTension);
	}
}
