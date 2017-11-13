
package org.drip.portfolioconstruction.objective;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
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
 * TiltLongTerm holds the Details of Tilt Long Unit Objective Term.
 *
 * @author Lakshmi Krishnamurthy
 */

public class TiltLongTerm extends org.drip.portfolioconstruction.optimizer.ObjectiveTerm {
	private double[] _adblMagnitude = null;
	private double[] _adblMembership = null;
	private org.drip.portfolioconstruction.composite.BlockAttribute _baTiltWeight = null;
	private org.drip.portfolioconstruction.composite.BlockClassification _bcTiltSet = null;

	/**
	 * TiltLongTerm Constructor
	 * 
	 * @param strName The Objective Term Name
	 * @param strID The Objective Term ID
	 * @param strDescription The Objective Term Description
	 * @param holdingsInitial The Initial Holdings
	 * @param baTiltWeight The Tilt Weight Block Attribute
	 * @param bcTiltSet The Tilt Set Block Classification
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public TiltLongTerm (
		final java.lang.String strName,
		final java.lang.String strID,
		final java.lang.String strDescription,
		final org.drip.portfolioconstruction.composite.Holdings holdingsInitial,
		final org.drip.portfolioconstruction.composite.BlockAttribute baTiltWeight,
		final org.drip.portfolioconstruction.composite.BlockClassification bcTiltSet)
		throws java.lang.Exception
	{
		super (strName, strID, strDescription, holdingsInitial);

		if (null == (_baTiltWeight = baTiltWeight) || null == (_bcTiltSet = bcTiltSet))
			throw new java.lang.Exception ("TiltLongTerm Constructor => Invalid Inputs");

		if (null == (_adblMagnitude = _baTiltWeight.constrict (holdingsInitial)))
			throw new java.lang.Exception ("TiltLongTerm Constructor => Invalid Inputs");

		if (null == (_adblMembership = _bcTiltSet.constrict (holdingsInitial)))
			throw new java.lang.Exception ("TiltLongTerm Constructor => Invalid Inputs");
	}

	/**
	 * Retrieve the Array of Tilt Weights
	 *  
	 * @return The Array of Tilt Weights
	 */

	public double[] weight()
	{
		return _adblMagnitude;
	}

	/**
	 * Retrieve the Tilt Weight Block Attribute
	 *  
	 * @return The Tilt Weight Block Attribute
	 */

	public org.drip.portfolioconstruction.composite.BlockAttribute weightAttribute()
	{
		return _baTiltWeight;
	}

	/**
	 * Retrieve the Array of Tilt Set Membership
	 *  
	 * @return The Array of Tilt Set Membership
	 */

	public double[] membership()
	{
		return _adblMembership;
	}

	/**
	 * Retrieve the Tilt Set Block Classification
	 *  
	 * @return The Tilt Set Block Classification
	 */

	public org.drip.portfolioconstruction.composite.BlockClassification membershipClassification()
	{
		return _bcTiltSet;
	}

	@Override public org.drip.function.definition.RdToR1 rdtoR1()
	{
		return new org.drip.function.definition.RdToR1 (null) {
			@Override public int dimension()
			{
				return _adblMagnitude.length;
			}

			@Override public double evaluate (
				final double[] adblVariate)
				throws java.lang.Exception
			{
				if (null == adblVariate || !org.drip.quant.common.NumberUtil.IsValid (adblVariate))
					throw new java.lang.Exception ("TiltLongTerm::rdToR1::evaluate => Invalid Inputs");

				double dblValue = 0.;
				int iDimension = adblVariate.length;

				if (iDimension != dimension())
					throw new java.lang.Exception ("TiltLongTerm::rdToR1::evaluate => Invalid Inputs");

				double[] adblInitialHoldings = initialHoldingsArray();

				for (int i = 0; i < iDimension; ++i)
					dblValue += _adblMagnitude[i] * _adblMembership[i] * (java.lang.Math.abs (adblVariate[i])
						- java.lang.Math.abs (adblInitialHoldings[i]));

				return dblValue;
			}

			@Override public double derivative (
				final double[] adblVariate,
				final int iVariateIndex,
				final int iOrder)
				throws java.lang.Exception
			{
				if (0 == iOrder || null == adblVariate || !org.drip.quant.common.NumberUtil.IsValid
					(adblVariate))
					throw new java.lang.Exception ("TiltLongTerm::rdToR1::derivative => Invalid Inputs");

				int iDimension = adblVariate.length;

				if (iDimension != dimension() || iVariateIndex >= iDimension)
					throw new java.lang.Exception ("TiltLongTerm::rdToR1::derivative => Invalid Inputs");

				if (2 <= iOrder) return 0.;

				double dblVariateHoldings = initialHoldingsArray()[iVariateIndex];

				return (adblVariate[iVariateIndex] > dblVariateHoldings ? 1. : -1.) *
					_adblMagnitude[iVariateIndex] * _adblMembership[iVariateIndex];
			}
		};
	}
}
