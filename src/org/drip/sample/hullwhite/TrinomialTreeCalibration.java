
package org.drip.sample.hullwhite;

import java.util.Map;

import org.drip.analytics.date.*;
import org.drip.dynamics.hullwhite.*;
import org.drip.function.r1tor1.FlatUnivariate;
import org.drip.quant.common.FormatUtil;
import org.drip.sequence.random.BoxMullerGaussian;
import org.drip.service.env.EnvManager;
import org.drip.state.identifier.FundingLabel;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
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
 * TrinomialTreeCalibration demonstrates the Construction and Calibration of the Hull-White Trinomial Tree
 * 	and the Eventual Evolution of the Short Rate on it.
 *
 * @author Lakshmi Krishnamurthy
 */

public class TrinomialTreeCalibration {

	private static final String SourceToTarget (
		final String strKey)
	{
		String[] astrNode = strKey.split ("#");

		String[] astrSourceNode = astrNode[0].split (",");

		String[] astrTargetNode = astrNode[1].split (",");

		return "[" +
			astrSourceNode[0] + "," +
			FormatUtil.FormatDouble (Double.parseDouble (astrSourceNode[1]), 1, 0, 1.) + "] => [" +
			astrTargetNode[0] + "," +
			FormatUtil.FormatDouble (Double.parseDouble (astrTargetNode[1]), 1, 0, 1.) + "]";
	}

	private static final SingleFactorStateEvolver HullWhiteEvolver (
		final String strCurrency,
		final double dblSigma,
		final double dblA,
		final double dblStartingForwardRate)
		throws Exception
	{
		return new SingleFactorStateEvolver (
			FundingLabel.Standard (strCurrency),
			dblSigma,
			dblA,
			new FlatUnivariate (dblStartingForwardRate),
			new BoxMullerGaussian (
				0.,
				1.
			)
		);
	}

	private static void EmitNodeDetails (
		final TrinomialTreeTransitionMetrics hwtm,
		final TrinomialTreeNodeMetrics hwnm)
		throws Exception
	{
		System.out.println ("\n\n\t|----------------------------------------------------------|");

		System.out.println ("\t|    NODE [" + hwnm.timeIndex() + ", " + hwnm.xStochasticIndex() + "]                                           |");

		System.out.println ("\t|----------------------------------------------------------|");

		System.out.println ("\t|        Expected Terminal X                  :  " + FormatUtil.FormatDouble (hwtm.expectedTerminalX(), 1, 6, 1.) + " |");

		System.out.println ("\t|        X Variance                           :  " + FormatUtil.FormatDouble (hwtm.xVariance(), 1, 6, 1.) + " |");

		System.out.println ("\t|        X Stochastic Volatility Shift        :  " + FormatUtil.FormatDouble (hwtm.xStochasticShift(), 1, 6, 1.) + " |");

		System.out.println ("\t|        X Tree Stochastic Displacement Index :   " + hwtm.treeStochasticDisplacementIndex() + "        |");

		System.out.println ("\t|        Probability Up                       :  " + FormatUtil.FormatDouble (hwtm.probabilityUp(), 1, 6, 1.) + " |");

		System.out.println ("\t|        Probability Stay                     :  " + FormatUtil.FormatDouble (hwtm.probabilityStay(), 1, 6, 1.) + " |");

		System.out.println ("\t|        Probability Down                     :  " + FormatUtil.FormatDouble (hwtm.probabilityDown(), 1, 6, 1.) + " |");

		System.out.println ("\t|        Node X Value                         :  " + FormatUtil.FormatDouble (hwnm.x(), 1, 6, 1.) + " |");

		System.out.println ("\t|        Node Alpha                           :  " + FormatUtil.FormatDouble (hwnm.alpha(), 1, 6, 1.) + " |");

		System.out.println ("\t|        Node Short Rate                      :  " + FormatUtil.FormatDouble (hwnm.shortRate(), 1, 6, 1.) + " |");

		System.out.println ("\t|----------------------------------------------------------|");
	}

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		JulianDate dtSpot = DateUtil.CreateFromYMD (
			2011,
			DateUtil.MAY,
			18
		);

		double dblA = 0.1;
		double dblSigma = 0.01;
		String strCurrency = "USD";
		String[] astrTenor = {
			"3M", "6M", "9M"
		};
		double[] adblQuote = {
			0.0026, 0.00412, 0.00572
		};

		SingleFactorStateEvolver hw = HullWhiteEvolver (
			strCurrency,
			dblSigma,
			dblA,
			adblQuote[0]
		);

		TrinomialTreeTransitionMetrics hwtmp0p0 = hw.evolveTrinomialTree (
			dtSpot.julian(),
			dtSpot.julian(),
			dtSpot.addTenor (astrTenor[0]).julian(),
			null
		);

		EmitNodeDetails (
			hwtmp0p0,
			hwtmp0p0.downNodeMetrics()
		);

		EmitNodeDetails (
			hwtmp0p0,
			hwtmp0p0.stayNodeMetrics()
		);

		EmitNodeDetails (
			hwtmp0p0,
			hwtmp0p0.upNodeMetrics()
		);

		TrinomialTreeTransitionMetrics hwtmp1n1 = hw.evolveTrinomialTree (
			dtSpot.julian(),
			dtSpot.addTenor (astrTenor[0]).julian(),
			dtSpot.addTenor (astrTenor[1]).julian(),
			hwtmp0p0.downNodeMetrics()
		);

		EmitNodeDetails (
			hwtmp1n1,
			hwtmp1n1.downNodeMetrics()
		);

		EmitNodeDetails (
			hwtmp1n1,
			hwtmp1n1.stayNodeMetrics()
		);

		EmitNodeDetails (
			hwtmp1n1,
			hwtmp1n1.upNodeMetrics()
		);

		TrinomialTreeTransitionMetrics hwtmp1n0 = hw.evolveTrinomialTree (
			dtSpot.julian(),
			dtSpot.addTenor (astrTenor[0]).julian(),
			dtSpot.addTenor (astrTenor[1]).julian(),
			hwtmp0p0.stayNodeMetrics()
		);

		EmitNodeDetails (
			hwtmp1n0,
			hwtmp1n0.downNodeMetrics()
		);

		EmitNodeDetails (
			hwtmp1n0,
			hwtmp1n0.stayNodeMetrics()
		);

		EmitNodeDetails (
			hwtmp1n0,
			hwtmp1n0.upNodeMetrics()
		);

		TrinomialTreeTransitionMetrics hwtmp1p1 = hw.evolveTrinomialTree (
			dtSpot.julian(),
			dtSpot.addTenor (astrTenor[0]).julian(),
			dtSpot.addTenor (astrTenor[1]).julian(),
			hwtmp0p0.upNodeMetrics()
		);

		EmitNodeDetails (
			hwtmp1p1,
			hwtmp1p1.downNodeMetrics()
		);

		EmitNodeDetails (
			hwtmp1p1,
			hwtmp1p1.stayNodeMetrics()
		);

		EmitNodeDetails (
			hwtmp1p1,
			hwtmp1p1.upNodeMetrics()
		);

		TrinomialTreeSequenceMetrics hwsm = hw.evolveTrinomialTreeSequence (
			dtSpot.julian(),
			30,
			2
		);

		System.out.println ("\n\t|-----------------------------------|");

		System.out.println ("\t| SOURCE TARGET PROBABILITY METRICS |");

		System.out.println ("\t|-----------------------------------|");

		Map<String, Double> mapProbSourceTarget = hwsm.sourceTargetTransitionProbability();

		for (Map.Entry<String, Double> me : mapProbSourceTarget.entrySet())
			System.out.println ("\t|    " + SourceToTarget (me.getKey()) + ": " + FormatUtil.FormatDouble (me.getValue(), 1, 6, 1.) + "    |");

		System.out.println ("\t|-----------------------------------|");

		System.out.println ("\n\t|-----------------------------------|");

		System.out.println ("\t| TARGET SOURCE PROBABILITY METRICS |");

		System.out.println ("\t|-----------------------------------|");

		Map<String, Double> mapProbTargetSource = hwsm.targetSourceTransitionProbability();

		for (Map.Entry<String, Double> me : mapProbTargetSource.entrySet())
			System.out.println ("\t|    " + SourceToTarget (me.getKey()) + ": " + FormatUtil.FormatDouble (me.getValue(), 1, 6, 1.) + "    |");

		System.out.println ("\t|-----------------------------------|");
	}
}
