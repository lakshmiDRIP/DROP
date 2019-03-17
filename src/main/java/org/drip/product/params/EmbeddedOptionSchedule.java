
package org.drip.product.params;

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
 * Copyright (C) 2012 Lakshmi Krishnamurthy
 * Copyright (C) 2011 Lakshmi Krishnamurthy
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
 * <i>EmbeddedOptionSchedule</i> is a place holder for the embedded option schedule for the component. It
 * contains the schedule of exercise dates and factors, the exercise notice period, and the option is to call
 * or put. Further, if the option is of the type fix-to-float on exercise, contains the post-exercise floater
 * index and floating spread. If the exercise is not discrete (American option), the exercise dates/factors
 * are discretized according to a pre-specified discretization grid. It exports serialization into and de-
 * serialization out of byte arrays.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product">Product</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/params">Params</a></li>
 *  </ul>
 * <br><br>
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
					(org.drip.numerical.common.StringUtil.MakeIntegerArrayFromStringTokenizer (new
						java.util.StringTokenizer (strDates, ";")),
							org.drip.numerical.common.StringUtil.MakeDoubleArrayFromStringTokenizer (new
								java.util.StringTokenizer (strFactors, ";")), bIsPut, iNoticePeriod,
									bFixToFloatOnExercise, dblFixToFloatExerciseDate, strFloatIndex,
										dblFixToFloatSpread);
			} catch (java.lang.Exception e) {
				e.printStackTrace();
			}

			return null;
		}

		return FromAmerican (iScheduleStart,
			org.drip.numerical.common.StringUtil.MakeIntegerArrayFromStringTokenizer (new
				java.util.StringTokenizer (strDates, ";")),
					org.drip.numerical.common.StringUtil.MakeDoubleArrayFromStringTokenizer (new
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
		return FromAmerican (iValDate, aiDate, adblFactor, bIsPut, iNoticePeriod, 30, bFixToFloatOnExercise,
			dblFixToFloatExerciseDate, strFloatIndex, dblFixToFloatSpread);
	}

	/**
	 * Create the discretized American EOS schedule from the array of dates and factors
	 * 
	 * @param iValDate Valuation Date - date to which the component is assumed to not have been exercised
	 * @param aiDate Array of dates
	 * @param adblFactor Matched Array of Factors
	 * @param bIsPut True (Put), False (Call)
	 * @param iNoticePeriod Exercise Notice Period
	 * @param iCallDiscretization Call Discretization Period Unit
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
		final int iCallDiscretization,
		final boolean bFixToFloatOnExercise,
		final double dblFixToFloatExerciseDate,
		final java.lang.String strFloatIndex,
		final double dblFixToFloatSpread)
	{
		if (null == aiDate || aiDate.length == 0 || null == adblFactor || adblFactor.length == 0 ||
			aiDate.length != adblFactor.length || 0 >= iCallDiscretization)
			return null;

		int iCallDate = aiDate[0];

		java.util.ArrayList<java.lang.Integer> liCallDates = new java.util.ArrayList<java.lang.Integer>();

		java.util.ArrayList<java.lang.Double> ldblCallFactors = new java.util.ArrayList<java.lang.Double>();

		for (int i = 1; i < aiDate.length; ++i) {
			while (iCallDate <= aiDate[i]) {
				liCallDates.add (iCallDate);

				ldblCallFactors.add (adblFactor[i - 1]);

				iCallDate += iCallDiscretization;
			}
		}

		int[] aiEOSDate = new int[liCallDates.size()];

		int i = 0;

		for (int iEOSDate : liCallDates)
			aiEOSDate[i++] = iEOSDate;

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
