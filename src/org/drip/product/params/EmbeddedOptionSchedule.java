
package org.drip.product.params;

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
 * Copyright (C) 2011 Lakshmi Krishnamurthy
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
 * EmbeddedOptionSchedule is a place holder for the embedded option schedule for the component. It contains
 *  the schedule of exercise dates and factors, the exercise notice period, and the option is to call or put.
 *  Further, if the option is of the type fix-to-float on exercise, contains the post-exercise floater index
 *  and floating spread. If the exercise is not discrete (American option), the exercise dates/factors are
 *  discretized according to a pre-specified discretization grid. It exports serialization into and
 *  de-serialization out of byte arrays.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class EmbeddedOptionSchedule {
	public static final int CALL_NOTICE_PERIOD_DEFAULT = 30;

	private int _aiDate[] = null;
	private boolean _bIsPut = false;
	private double _adblFactor[] = null;
	private java.lang.String _strFloatIndex = "";
	private boolean _bFixToFloatOnExercise = false;
	private int _iNoticePeriod = CALL_NOTICE_PERIOD_DEFAULT;
	private double _dblFixToFloatSpread = java.lang.Double.NaN;
	private double _dblFixToFloatExerciseDate = java.lang.Double.NaN;

	/**
	 * Create the EOS from the dates/factors string arrays
	 * 
	 * @param strDates String representing the date array
	 * @param strFactors String representing the factor array
	 * @param iNoticePeriod Exercise Notice Period
	 * @param bIsPut True (Put), False (Call)
	 * @param bIsDiscrete True (Discrete), False (Continuous)
	 * @param iScheduleStart Schedule start Date
	 * @param bFixToFloatOnExercise True - component becomes a floater on call
	 * @param dblFixToFloatExerciseDate Date at which the fix to float conversion happens
	 * @param strFloatIndex Floater Rate Index
	 * @param dblFixToFloatSpread Floater Spread
	 * 
	 * @return EOS object
	 */

	public static final EmbeddedOptionSchedule CreateFromDateFactorSet (
		final java.lang.String strDates,
		final java.lang.String strFactors,
		final int iNoticePeriod,
		final boolean bIsPut,
		final boolean bIsDiscrete,
		final int iScheduleStart,
		final boolean bFixToFloatOnExercise,
		final double dblFixToFloatExerciseDate,
		final java.lang.String strFloatIndex,
		final double dblFixToFloatSpread)
	{
		if (null == strDates || strDates.isEmpty() || null == strFactors || strFactors.isEmpty())
			return null;

		if (bIsDiscrete) {
			try {
				return new EmbeddedOptionSchedule
					(org.drip.quant.common.StringUtil.MakeIntegerArrayFromStringTokenizer (new
						java.util.StringTokenizer (strDates, ";")),
							org.drip.quant.common.StringUtil.MakeDoubleArrayFromStringTokenizer (new
								java.util.StringTokenizer (strFactors, ";")), bIsPut, iNoticePeriod,
									bFixToFloatOnExercise, dblFixToFloatExerciseDate, strFloatIndex,
										dblFixToFloatSpread);
			} catch (java.lang.Exception e) {
				e.printStackTrace();
			}

			return null;
		}

		return FromAmerican (iScheduleStart,
			org.drip.quant.common.StringUtil.MakeIntegerArrayFromStringTokenizer (new
				java.util.StringTokenizer (strDates, ";")),
					org.drip.quant.common.StringUtil.MakeDoubleArrayFromStringTokenizer (new
						java.util.StringTokenizer (strFactors, ";")), bIsPut, iNoticePeriod,
							bFixToFloatOnExercise, dblFixToFloatExerciseDate, strFloatIndex,
								dblFixToFloatSpread);
	}

	/**
	 * Create the discretized American EOS schedule from the array of dates and factors
	 * 
	 * @param iValDate Valuation Date - date to which the component is assumed to not have been exercised
	 * @param aiDate Array of dates
	 * @param adblFactor Matched Array of Factors
	 * @param bIsPut True (Put), False (Call)
	 * @param iNoticePeriod Exercise Notice Period
	 * @param bFixToFloatOnExercise True - component becomes a floater on call
	 * @param dblFixToFloatExerciseDate Date at which the fix to float conversion happens
	 * @param strFloatIndex Floater Rate Index
	 * @param dblFixToFloatSpread Floater Spread
	 * 
	 * @return Discretized EOS
	 */

	public static final EmbeddedOptionSchedule FromAmerican (
		final int iValDate,
		final int aiDate[],
		final double adblFactor[],
		final boolean bIsPut,
		final int iNoticePeriod,
		final boolean bFixToFloatOnExercise,
		final double dblFixToFloatExerciseDate,
		final java.lang.String strFloatIndex,
		final double dblFixToFloatSpread)
	{
		if (null == aiDate || aiDate.length == 0 || null == adblFactor || adblFactor.length == 0 ||
			aiDate.length != adblFactor.length)
			return null;

		int i = 0;
		int iCallDiscretization = 30;
		int iScheduleStart = iValDate;

		if (iValDate < aiDate[0]) iScheduleStart = aiDate[0];

		java.util.ArrayList<java.lang.Integer> liCallDates = new java.util.ArrayList<java.lang.Integer>();

		java.util.ArrayList<java.lang.Double> ldblCallFactors = new java.util.ArrayList<java.lang.Double>();

		for (; i < aiDate.length; ++i) {
			int iCallDate = iScheduleStart;

			if (0 != i) iCallDate = aiDate[i - 1];

			while (iCallDate <= aiDate[i]) {
				liCallDates.add (iCallDate);

				ldblCallFactors.add (adblFactor[i]);

				iCallDate += iCallDiscretization;
			}
		}

		int[] aiEOSDate = new int[liCallDates.size()];

		i = 0;

		for (int iCallDate : liCallDates)
			aiEOSDate[i++] = iCallDate;

		double[] adblEOSFactor = new double[ldblCallFactors.size()];

		i = 0;

		for (double dblCallFactor : ldblCallFactors)
			adblEOSFactor[i++] = dblCallFactor;

		try {
			return new EmbeddedOptionSchedule (aiEOSDate, adblEOSFactor, bIsPut, iNoticePeriod,
				bFixToFloatOnExercise, dblFixToFloatExerciseDate, strFloatIndex, dblFixToFloatSpread);
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}
	}

	/**
	 * Construct the EOS from the array of dates and factors
	 * 
	 * @param aiDate Array of dates
	 * @param adblFactor Matched Array of Factors
	 * @param bIsPut True (Put), False (Call)
	 * @param iNoticePeriod Exercise Notice Period
	 * @param bFixToFloatOnExercise True - component becomes a floater on call
	 * @param dblFixToFloatExerciseDate Date at which the fix to float conversion happens
	 * @param strFloatIndex Floater Rate Index
	 * @param dblFixToFloatSpread Floater Spread
	 * 
	 * @throws java.lang.Exception Thrown if inputs are invalid
	 */

	public EmbeddedOptionSchedule (
		final int[] aiDate,
		final double[] adblFactor,
		final boolean bIsPut,
		final int iNoticePeriod,
		final boolean bFixToFloatOnExercise,
		final double dblFixToFloatExerciseDate,
		final java.lang.String strFloatIndex,
		final double dblFixToFloatSpread)
		throws java.lang.Exception
	{
		if (null == aiDate || null == adblFactor || aiDate.length != adblFactor.length)
			throw new java.lang.Exception ("EmbeddedOptionSchedule ctr => Invalid params");

		_aiDate = new int[aiDate.length];
		_adblFactor = new double[adblFactor.length];

		for (int i = 0; i < _aiDate.length; ++i)
			_aiDate[i] = aiDate[i];

		for (int i = 0; i < _adblFactor.length; ++i)
			_adblFactor[i] = adblFactor[i];

		_bIsPut = bIsPut;
		_iNoticePeriod = iNoticePeriod;
		_strFloatIndex = strFloatIndex;
		_dblFixToFloatSpread = dblFixToFloatSpread;
		_bFixToFloatOnExercise = bFixToFloatOnExercise;
		_dblFixToFloatExerciseDate = dblFixToFloatExerciseDate;
	}

	/**
	 * Construct a Deep Copy EOS from another EOS
	 * 
	 * @param eosOther The Other EOS
	 */

	public EmbeddedOptionSchedule (
		final EmbeddedOptionSchedule eosOther)
	{
		_aiDate = new int[eosOther._aiDate.length];
		_adblFactor = new double[eosOther._adblFactor.length];

		for (int i = 0; i < _aiDate.length; ++i)
			_aiDate[i] = eosOther._aiDate[i];

		for (int i = 0; i < _adblFactor.length; ++i)
			_adblFactor[i] = eosOther._adblFactor[i];

		_bIsPut = eosOther._bIsPut;
		_iNoticePeriod = eosOther._iNoticePeriod;
		_strFloatIndex = eosOther._strFloatIndex;
		_dblFixToFloatSpread = eosOther._dblFixToFloatSpread;
		_bFixToFloatOnExercise = eosOther._bFixToFloatOnExercise;
		_dblFixToFloatExerciseDate = eosOther._dblFixToFloatExerciseDate;
	}

	/**
	 * Whether the component is putable or callable
	 * 
	 * @return True (Put), False (Call)
	 */

	public boolean isPut()
	{
		return _bIsPut;
	}

	/**
	 * Get the array of dates
	 * 
	 * @return The array of dates
	 */

	public int[] dates()
	{
		return _aiDate;
	}

	/**
	 * Get the array of factors
	 * 
	 * @return The array of factors
	 */

	public double[] factors()
	{
		return _adblFactor;
	}

	/**
	 * Get the specific indexed factor
	 * 
	 * @param iIndex Factor index
	 * 
	 * @return Factor corresponding to the index
	 */

	public double factor (
		final int iIndex)
	{
		return _adblFactor[iIndex];
	}

	/**
	 * Retrieve the exercise notice period
	 * 
	 * @return Minimum Exercise Notice Period in Days
	 */

	public int exerciseNoticePeriod()
	{
		return _iNoticePeriod;
	}

	/**
	 * Return whether the component is fix to float on exercise
	 * 
	 * @return True (component becomes a floater on call), False (component does not change)
	 */

	public boolean isFixToFloatOnExercise()
	{
		return _bFixToFloatOnExercise;
	}

	/**
	 * Generate the Possible Exercise Dates from the Spot Date and the Notice Period
	 * 
	 * @param iSpotDate The Spot Date
	 * 
	 * @return Array of Possible Exercise Dates from the Spot Date and the Notice Period
	 */

	public int[] exerciseDates (
		final int iSpotDate)
	{
		java.util.List<java.lang.Integer> lsDate = new java.util.ArrayList<java.lang.Integer>();

		int iExerciseSize = _aiDate.length;
		int iExerciseCutOff = iSpotDate + _iNoticePeriod;

		for (int i = 0; i < iExerciseSize; ++i) {
			if (_aiDate[i] >= iExerciseCutOff) lsDate.add (_aiDate[i]);
		}

		int iSize = lsDate.size();

		if (0 == iSize) return null;

		int[] aiExerciseDate = new int[iSize];

		for (int i = 0; i < iSize; ++i)
			aiExerciseDate[i] = lsDate.get (i);

		return aiExerciseDate;
	}

	/**
	 * Generate the Possible Exercise Factors from the Spot Date and the Notice Period
	 * 
	 * @param iSpotDate The Spot Date
	 * 
	 * @return Array of Possible Exercise Factors from the Spot Date and the Notice Period
	 */

	public double[] exerciseFactors (
		final int iSpotDate)
	{
		java.util.List<java.lang.Double> lsFactor = new java.util.ArrayList<java.lang.Double>();

		int iExerciseSize = _aiDate.length;
		int iExerciseCutOff = iSpotDate + _iNoticePeriod;

		for (int i = 0; i < iExerciseSize; ++i) {
			if (_aiDate[i] >= iExerciseCutOff) lsFactor.add (_adblFactor[i]);
		}

		int iSize = lsFactor.size();

		if (0 == iSize) return null;

		double[] aiExerciseFactor = new double[iSize];

		for (int i = 0; i < iSize; ++i)
			aiExerciseFactor[i] = lsFactor.get (i);

		return aiExerciseFactor;
	}

	/**
	 * Retrieve the Next Exercise Date, starting from the Spot
	 * 
	 * @param iSpotDate The Spot Date
	 * 
	 * @return Next Exercise Date
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public int nextDate (
		final int iSpotDate)
		throws java.lang.Exception
	{
		int iExerciseSize = _aiDate.length;

		for (int i = 0; i < iExerciseSize; ++i) {
			if (_aiDate[i] - _iNoticePeriod >= iSpotDate) return _aiDate[i];
		}

		throw new java.lang.Exception ("EmbeddedOptionSchedule::nextDate => Invalid Inputs");
	}

	/**
	 * Retrieve the Exercise Factor corresponding to the Next Exercise Date, starting from the Spot
	 * 
	 * @param iSpotDate The Spot Date
	 * 
	 * @return Next Exercise Factor
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double nextFactor (
		final int iSpotDate)
		throws java.lang.Exception
	{
		int iExerciseSize = _aiDate.length;

		for (int i = 0; i < iExerciseSize; ++i) {
			if (_aiDate[i] - _iNoticePeriod >= iSpotDate) return _adblFactor[i];
		}

		throw new java.lang.Exception ("EmbeddedOptionSchedule::nextFactor => Invalid Inputs");
	}
}
