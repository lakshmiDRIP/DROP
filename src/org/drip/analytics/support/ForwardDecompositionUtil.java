
package org.drip.analytics.support;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
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
 * ForwardDecompositionUtil contains the utility functions needed to carry out periodic decomposition at MTM
 *  sync points for the given stream.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class ForwardDecompositionUtil {

	/**
	 * Decompose the Stream into an Array of Single Forward Period Floating Streams
	 * 
	 * @param fs The Stream
	 * @param iNumPeriodsToAccumulate Number of Forward Periods to roll into one
	 * 
	 * @return The Array of Single Forward Period Streams
	 */

	public static final org.drip.product.rates.Stream[] SinglePeriodStreamDecompose (
		final org.drip.product.rates.Stream fs,
		final int iNumPeriodsToAccumulate)
	{
		if (null == fs) return null;

		java.util.List<org.drip.analytics.cashflow.CompositePeriod> lsCouponFlow = fs.cashFlowPeriod();

		int iNumPeriods = lsCouponFlow.size();

		int iCFPIndex = 0;
		int iNumPeriodsAccumulated = 0;
		int iNumForward = iNumPeriods / iNumPeriodsToAccumulate;
		org.drip.product.rates.Stream[] aFS = new org.drip.product.rates.Stream[iNumForward];

		java.util.List<java.util.List<org.drip.analytics.cashflow.CompositePeriod>> lslsCouponPeriod = new
			java.util.ArrayList<java.util.List<org.drip.analytics.cashflow.CompositePeriod>>();

		for (int i = 0; i < iNumForward; ++i)
			lslsCouponPeriod.add (new java.util.ArrayList<org.drip.analytics.cashflow.CompositePeriod>());

		for (org.drip.analytics.cashflow.CompositePeriod cfp : lsCouponFlow) {
			java.util.List<org.drip.analytics.cashflow.CompositePeriod> lsCouponPeriod = lslsCouponPeriod.get
				(iCFPIndex);

			lsCouponPeriod.add (cfp);

			if (++iNumPeriodsAccumulated != iNumPeriodsToAccumulate) continue;

			iNumPeriodsAccumulated = 0;

			try {
				aFS[iCFPIndex++] = new org.drip.product.rates.Stream (lsCouponPeriod);
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		return aFS;
	}

	/**
	 * Decompose the Dual Stream Component into an Array of Single Forward Period Dual Streams
	 * 
	 * @param dsc The Dual Stream
	 * 
	 * @return The Array of Single Forward Period Dual Streams
	 */

	public static final org.drip.product.definition.CalibratableComponent[] DualStreamForwardArray (
		final org.drip.product.rates.DualStreamComponent dsc)
	{
		if (null == dsc) return null;

		org.drip.product.rates.Stream streamDerived = dsc.derivedStream();

		org.drip.product.rates.Stream streamReference = dsc.referenceStream();

		int iNumForward = 0;
		org.drip.product.rates.Stream[] aStreamDerivedForward = null;
		org.drip.product.rates.Stream[] aStreamReferenceForward = null;

		int iDerivedStreamTenorMonths = 12 / streamDerived.freq();

		int iReferenceStreamTenorMonths = 12 / streamReference.freq();

		if (iReferenceStreamTenorMonths > iDerivedStreamTenorMonths) {
			if (null == (aStreamReferenceForward = SinglePeriodStreamDecompose (streamReference, 1)) || 0 ==
				(iNumForward = aStreamReferenceForward.length))
				return null;

			if (null == (aStreamDerivedForward = SinglePeriodStreamDecompose (streamDerived,
				iReferenceStreamTenorMonths / iDerivedStreamTenorMonths)) || iNumForward !=
					aStreamDerivedForward.length)
				return null;
		} else {
			if (null == (aStreamDerivedForward = SinglePeriodStreamDecompose (streamDerived, 1)) || 0 ==
				(iNumForward = aStreamDerivedForward.length))
				return null;

			if (null == (aStreamReferenceForward = SinglePeriodStreamDecompose (streamReference,
				iDerivedStreamTenorMonths / iReferenceStreamTenorMonths)) || iNumForward !=
					aStreamReferenceForward.length)
				return null;
		}

		org.drip.product.definition.CalibratableComponent[] aRC = new
			org.drip.product.definition.CalibratableComponent[iNumForward];

		for (int i = 0; i < iNumForward; ++i) {
			try {
				if (null == (aRC[i] = org.drip.product.creator.DualStreamComponentBuilder.MakeFloatFloat
					(aStreamReferenceForward[i], aStreamDerivedForward[i], new
						org.drip.param.valuation.CashSettleParams (0, streamDerived.couponCurrency(), 0))))
					return null;

				aRC[i].setPrimaryCode (streamReference.name() + "::" + streamDerived.name() + "_" + i);
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		return aRC;
	}

	/**
	 * Decompose the Rates Component into an Array of Single Forward Rates Components
	 * 
	 * @param rc The Rates Component
	 * 
	 * @return The Array of Single Forward Period Rates Components
	 */

	public static final org.drip.product.definition.CalibratableComponent[]
		CalibratableFixedIncomeComponentForwardArray (
			final org.drip.product.definition.CalibratableComponent rc)
	{
		return null != rc && rc instanceof org.drip.product.rates.DualStreamComponent ?
			DualStreamForwardArray ((org.drip.product.rates.DualStreamComponent) rc) : null;
	}
}
