
package org.drip.spline.bspline;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
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
 * <i>BasisHatPairGenerator</i> implements the generation functionality behind the hat basis function pair.
 * It provides the following functionality:
 *  <ul>
 *  	<li>
 * 			Generate the array of the Hyperbolic Phy and Psy Hat Function Pair.
 *  	</li>
 *  	<li>
 * 			Generate the array of the Hyperbolic Phy and Psy Hat Function Pair From their Raw Counterparts.
 *  	</li>
 *  	<li>
 * 		Generate the array of the Cubic Rational Phy and Psy Hat Function Pair From their Raw Counterparts.
 *  	</li>
 *  	<li>
 * 			Generate the array of the Custom Phy and Psy Hat Function Pair From their Raw Counterparts.
 *  	</li>
 *  </ul>
 *
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/SplineBuilderLibrary.md">Spline Builder Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline">Spline</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/bspline">B Spline</a></li>
 *  </ul>
 * <br><br>
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
