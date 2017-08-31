
package org.drip.spaces.cover;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * 
 *  This file is part of DRIP, a free-software/open-source library for fixed income analysts and developers -
 * 		http://www.credit-trader.org/Begin.html
 * 
 *  DRIP is a free, full featured, fixed income rates, credit, and FX analytics library with a focus towards
 *  	pricing/valuation, risk, and market making.
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
 * MaureyOperatorCoveringBounds implements the estimate the Upper Bounds and/or Absolute Values of the
 * 	Covering Number for the Hilbert R^d To Supremum R^d Operator Class. The Main References are:
 * 
 *  1) Carl, B. (1985): Inequalities of the Bernstein-Jackson type and the Degree of Compactness of Operators
 *  	in Banach Spaces, Annals of the Fourier Institute 35 (3) 79-118.
 *  
 *  2) Carl, B., and I. Stephani (1990): Entropy, Compactness, and the Approximation of Operators, Cambridge
 *  	University Press, Cambridge UK. 
 *  
 *  3) Williamson, R. C., A. J. Smola, and B. Scholkopf (2000): Entropy Numbers of Linear Function Classes,
 *  	in: Proceedings of the 13th Annual Conference on Computational Learning Theory, ACM New York.
 *
 * @author Lakshmi Krishnamurthy
 */

public class MaureyOperatorCoveringBounds {

	/**
	 * Maurey Constant - from the Hilbert - Supremum Identity Map Estimate
	 */

	public static final double HILBERT_SUPREMUM_IDENTITY_CONSTANT = 1.86;

	/**
	 * Maurey Constant - from the Williamson-Smola-Scholkopf Estimate
	 */

	public static final double WILLIAMSON_SMOLA_SCHOLKOPF_CONSTANT = 103.;

	private int _iSupremumDimension = -1;
	private double _dblOperatorNorm = java.lang.Double.NaN;
	private double _dblMaureyConstant = java.lang.Double.NaN;

	/**
	 * Construct an Instance Hilbert To Supremum Identity Map based Maurey Operator Covering Bounds
	 * 
	 * @param iSupremumDimension The Operator Supremum Output Space Dimension
	 * @param dblOperatorNorm The Operator Norm of Interest
	 * 
	 * @return The Instance Hilbert To Supremum Identity Map based Maurey Operator Covering Bounds
	 */

	public static final MaureyOperatorCoveringBounds HilbertSupremumIdentityMap (
		final int iSupremumDimension,
		final double dblOperatorNorm)
	{
		try {
			return new MaureyOperatorCoveringBounds (HILBERT_SUPREMUM_IDENTITY_CONSTANT,
				iSupremumDimension, dblOperatorNorm);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an Instance of the Maurey Operator Covering Bounds based upon the Williamson, Smola, and
	 *  Scholkopf Estimate
	 * 
	 * @param iSupremumDimension The Operator Supremum Output Space Dimension
	 * @param dblOperatorNorm The Operator Norm of Interest
	 * 
	 * @return Maurey Operator Covering Bounds based upon the Williamson, Smola, and Scholkopf Estimate
	 */

	public static final MaureyOperatorCoveringBounds WilliamsonSmolaScholkopfEstimate (
		final int iSupremumDimension,
		final double dblOperatorNorm)
	{
		try {
			return new MaureyOperatorCoveringBounds (WILLIAMSON_SMOLA_SCHOLKOPF_CONSTANT,
				iSupremumDimension, dblOperatorNorm);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * MaureyOperatorCoveringBounds Constructor
	 * 
	 * @param dblMaureyConstant The Maurey Constant
	 * @param iSupremumDimension The Operator Supremum Output Space Dimension
	 * @param dblOperatorNorm The Operator Norm of Interest
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public MaureyOperatorCoveringBounds (
		final double dblMaureyConstant,
		final int iSupremumDimension,
		final double dblOperatorNorm)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (_dblMaureyConstant = dblMaureyConstant) || 0 >=
			(_iSupremumDimension = iSupremumDimension) || !org.drip.quant.common.NumberUtil.IsValid
				(_dblOperatorNorm = dblOperatorNorm))
			throw new java.lang.Exception ("MaureyOperatorCoveringBounds ctr => Invalid Inputs");
	}

	/**
	 * Retrieve the Maurey Constant
	 * 
	 * @return The Maurey Constant
	 */

	public double maureyConstant()
	{
		return _dblMaureyConstant;
	}

	/**
	 * Retrieve the Supremum Dimension
	 * 
	 * @return The Supremum Dimension
	 */

	public int supremumDimension()
	{
		return _iSupremumDimension;
	}

	/**
	 * Retrieve the Operator Norm of Interest
	 * 
	 * @return The Operator Norm of Interest
	 */

	public double operatorNorm()
	{
		return _dblOperatorNorm;
	}

	/**
	 * Compute the Upper Bound for the Dyadic Entropy Number
	 * 
	 * @param iEntropyNumberIndex The Entropy Number Index
	 * 
	 * @return The Upper Bound for the DyadicEntropy Number
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double dyadicEntropyUpperBound (
		final int iEntropyNumberIndex)
		throws java.lang.Exception
	{
		if (0 >= iEntropyNumberIndex)
			throw new java.lang.Exception
				("MaureyOperatorCoveringBounds::dyadicEntropyUpperBound => Invalid Inputs");

		return _dblMaureyConstant * _dblOperatorNorm * java.lang.Math.sqrt ((java.lang.Math.log (1. +
			(((double) _iSupremumDimension) / ((double) iEntropyNumberIndex))) / iEntropyNumberIndex));
	}

	/**
	 * Compute the Upper Bound for the Entropy Number
	 * 
	 * @param iEntropyNumberIndex The Entropy Number Index
	 * 
	 * @return The Upper Bound for the Entropy Number
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double entropyNumberUpperBound (
		final int iEntropyNumberIndex)
		throws java.lang.Exception
	{
		if (0 >= iEntropyNumberIndex)
			throw new java.lang.Exception
				("MaureyOperatorCoveringBounds::entropyNumberUpperBound => Invalid Inputs");

		double dblLogNPlus1 = 1. + java.lang.Math.log (iEntropyNumberIndex);

		return _dblMaureyConstant * _dblOperatorNorm * java.lang.Math.sqrt ((1. + (((double)
			_iSupremumDimension) / dblLogNPlus1)) / dblLogNPlus1);
	}
}
