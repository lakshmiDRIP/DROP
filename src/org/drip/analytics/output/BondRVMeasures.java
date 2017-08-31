
package org.drip.analytics.output;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
 * Copyright (C) 2012 Lakshmi Krishnamurthy
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
 * BondRVMeasures encapsulates the comprehensive set of RV measures calculated for the bond to the
 *  appropriate exercise:
 * 	- Workout Information
 * 	- Price, Yield, and Yield01
 * 	- Spread Measures: Asset Swap/Credit/G/I/OAS/PECS/TSY/Z
 * 	- Basis Measures: Bond Basis, Credit Basis, Yield Basis
 * 	- Duration Measures: Macaulay/Modified Duration, Convexity
 *
 * @author Lakshmi Krishnamurthy
 */

public class BondRVMeasures {
	private double _dblPECS = java.lang.Double.NaN;
	private double _dblPrice = java.lang.Double.NaN;
	private double _dblGSpread = java.lang.Double.NaN;
	private double _dblISpread = java.lang.Double.NaN;
	private double _dblYield01 = java.lang.Double.NaN;
	private double _dblZSpread = java.lang.Double.NaN;
	private double _dblOASpread = java.lang.Double.NaN;
	private double _dblBondBasis = java.lang.Double.NaN;
	private double _dblConvexity = java.lang.Double.NaN;
	private double _dblTSYSpread = java.lang.Double.NaN;
	private double _dblCreditBasis = java.lang.Double.NaN;
	private org.drip.param.valuation.WorkoutInfo _wi = null;
	private double _dblDiscountMargin = java.lang.Double.NaN;
	private double _dblAssetSwapSpread = java.lang.Double.NaN;
	private double _dblMacaulayDuration = java.lang.Double.NaN;
	private double _dblModifiedDuration = java.lang.Double.NaN;

	/**
	 * BondRVMeasures ctr
	 * 
	 * @param dblPrice BondRV Clean Price
	 * @param dblBondBasis BondRV Bond Basis
	 * @param dblZSpread BondRV Z Spread
	 * @param dblGSpread BondRV G Spread
	 * @param dblISpread BondRV I Spread
	 * @param dblOASpread BondRV OAS
	 * @param dblTSYSpread BondRV TSY Spread
	 * @param dblDiscountMargin BondRV Asset Swap Spread
	 * @param dblAssetSwapSpread BondRV Asset Swap Spread
	 * @param dblCreditBasis BondRV Credit Basis
	 * @param dblPECS BondRV PECS
	 * @param dblYield01 BondRV Yield01
	 * @param dblModifiedDuration BondRV Modified Duration
	 * @param dblMacaulayDuration BondRV Macaulay Duration
	 * @param dblConvexity BondRV Convexity
	 * @param wi BondRV work-out info
	 * 
	 * @throws java.lang.Exception Thrown if inputs are invalid
	 */

	public BondRVMeasures (
		final double dblPrice,
		final double dblBondBasis,
		final double dblZSpread,
		final double dblGSpread,
		final double dblISpread,
		final double dblOASpread,
		final double dblTSYSpread,
		final double dblDiscountMargin,
		final double dblAssetSwapSpread,
		final double dblCreditBasis,
		final double dblPECS,
		final double dblYield01,
		final double dblModifiedDuration,
		final double dblMacaulayDuration,
		final double dblConvexity,
		final org.drip.param.valuation.WorkoutInfo wi)
		throws java.lang.Exception
	{
		if (null == (_wi = wi)) throw new java.lang.Exception ("BondRVMeasures ctr: Invalid inputs!");

		_dblPECS = dblPECS;
		_dblPrice = dblPrice;
		_dblGSpread = dblGSpread;
		_dblISpread = dblISpread;
		_dblYield01 = dblYield01;
		_dblZSpread = dblZSpread;
		_dblOASpread = dblOASpread;
		_dblBondBasis = dblBondBasis;
		_dblConvexity = dblConvexity;
		_dblTSYSpread = dblTSYSpread;
		_dblCreditBasis = dblCreditBasis;
		_dblDiscountMargin = dblDiscountMargin;
		_dblAssetSwapSpread = dblAssetSwapSpread;
		_dblMacaulayDuration = dblMacaulayDuration;
		_dblModifiedDuration = dblModifiedDuration;
	}

	/**
	 * Retrieve the Work-out Info
	 * 
	 * @return Work-out Info
	 */

	public org.drip.param.valuation.WorkoutInfo wi()
	{
		return _wi;
	}

	/**
	 * Retrieve the PECS
	 * 
	 * @return PECS
	 */

	public double pecs()
	{
		return _dblPECS;
	}

	/**
	 * Retrieve the Price
	 * 
	 * @return Price
	 */

	public double price()
	{
		return _dblPrice;
	}

	/**
	 * Retrieve the G Spread
	 * 
	 * @return G Spread
	 */

	public double gSpread()
	{
		return _dblGSpread;
	}

	/**
	 * Retrieve the I Spread
	 * 
	 * @return I Spread
	 */

	public double iSpread()
	{
		return _dblISpread;
	}

	/**
	 * Retrieve the Yield01
	 * 
	 * @return Yield01
	 */

	public double yield01()
	{
		return _dblYield01;
	}

	/**
	 * Retrieve the Z Spread
	 * 
	 * @return Z Spread
	 */

	public double zSpread()
	{
		return _dblZSpread;
	}

	/**
	 * Retrieve the OAS
	 * 
	 * @return OAS
	 */

	public double oas()
	{
		return _dblOASpread;
	}

	/**
	 * Retrieve the Bond Basis
	 * 
	 * @return Bond Basis
	 */

	public double bondBasis()
	{
		return _dblBondBasis;
	}

	/**
	 * Retrieve the Convexity
	 * 
	 * @return Convexity
	 */

	public double convexity()
	{
		return _dblConvexity;
	}

	/**
	 * Retrieve the TSY Spread
	 * 
	 * @return TSY Spread
	 */

	public double tsySpread()
	{
		return _dblTSYSpread;
	}

	/**
	 * Retrieve the Credit Basis
	 * 
	 * @return Credit Basis
	 */

	public double creditBasis()
	{
		return _dblCreditBasis;
	}

	/**
	 * Retrieve the Discount Margin
	 * 
	 * @return Discount Margin
	 */

	public double discountMargin()
	{
		return _dblDiscountMargin;
	}

	/**
	 * Retrieve the Asset Swap Spread
	 * 
	 * @return Asset Swap Spread
	 */

	public double asw()
	{
		return _dblAssetSwapSpread;
	}

	/**
	 * Retrieve the Macaulay Duration
	 * 
	 * @return Macaulay Duration
	 */

	public double macaulayDuration()
	{
		return _dblMacaulayDuration;
	}

	/**
	 * Retrieve the Modified Duration
	 * 
	 * @return Modified Duration
	 */

	public double modifiedDuration()
	{
		return _dblModifiedDuration;
	}

	/**
	 * Return the state as a measure map
	 * 
	 * @param strPrefix RV Measure name prefix
	 * 
	 * @return Map of the RV measures
	 */

	public org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> toMap (
		final java.lang.String strPrefix)
	{
		org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapRVMeasures = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>();

		mapRVMeasures.put (strPrefix + "AssetSwapSpread", _dblAssetSwapSpread);

		mapRVMeasures.put (strPrefix + "ASW", _dblAssetSwapSpread);

		mapRVMeasures.put (strPrefix + "BondBasis", _dblBondBasis);

		mapRVMeasures.put (strPrefix + "Convexity", _dblConvexity);

		mapRVMeasures.put (strPrefix + "CreditBasis", _dblCreditBasis);

		mapRVMeasures.put (strPrefix + "DiscountMargin", _dblDiscountMargin);

		mapRVMeasures.put (strPrefix + "Duration", _dblModifiedDuration);

		mapRVMeasures.put (strPrefix + "GSpread", _dblGSpread);

		mapRVMeasures.put (strPrefix + "ISpread", _dblISpread);

		mapRVMeasures.put (strPrefix + "MacaulayDuration", _dblMacaulayDuration);

		mapRVMeasures.put (strPrefix + "ModifiedDuration", _dblModifiedDuration);

		mapRVMeasures.put (strPrefix + "OAS", _dblOASpread);

		mapRVMeasures.put (strPrefix + "OASpread", _dblOASpread);

		mapRVMeasures.put (strPrefix + "OptionAdjustedSpread", _dblOASpread);

		mapRVMeasures.put (strPrefix + "PECS", _dblPECS);

		mapRVMeasures.put (strPrefix + "Price", _dblPrice);

		mapRVMeasures.put (strPrefix + "TSYSpread", _dblTSYSpread);

		mapRVMeasures.put (strPrefix + "WorkoutDate", (double) _wi.date());

		mapRVMeasures.put (strPrefix + "WorkoutFactor", _wi.factor());

		mapRVMeasures.put (strPrefix + "WorkoutType", (double) _wi.type());

		mapRVMeasures.put (strPrefix + "WorkoutYield", _wi.yield());

		mapRVMeasures.put (strPrefix + "Yield", _wi.yield());

		mapRVMeasures.put (strPrefix + "Yield01", _dblYield01);

		mapRVMeasures.put (strPrefix + "YieldBasis", _dblBondBasis);

		mapRVMeasures.put (strPrefix + "YieldSpread", _dblBondBasis);

		mapRVMeasures.put (strPrefix + "ZSpread", _dblZSpread);

		return mapRVMeasures;
	}
}
