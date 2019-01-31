
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
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting risk, transaction costs, exposure, margin
 *  	calculations, valuation adjustment, and portfolio construction within and across fixed income,
 *  	credit, commodity, equity, FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three modules:
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
 * 	- Statistical Learning
 * 	- Numerical Optimizer
 * 	- Spline Builder
 * 	- Algorithm Support
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
 * 	- Repo Layout Taxonomy     => https://github.com/lakshmiDRIP/DROP/blob/master/Taxonomy.md
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
 * <i>TrinomialTreeCalibration</i> demonstrates the Construction and Calibration of the Hull-White Trinomial
 * Tree and the Eventual Evolution of the Short Rate on it.
 *  
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">Sample</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/hullwhite/README.md">Hull-White 1F Dynamics</a></li>
 *  </ul>
 * <br><br>
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

		EnvManager.TerminateEnv();
	}
}
