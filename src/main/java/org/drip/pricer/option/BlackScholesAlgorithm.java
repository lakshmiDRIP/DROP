
package org.drip.pricer.option;

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
 * <i>BlackScholesAlgorithm</i> implements the Black Scholes based European Call and Put Options Pricer.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Project</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/pricer">Pricer</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/pricer/option">Option</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/FixedIncome">Fixed Income Analytics</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class BlackScholesAlgorithm extends org.drip.pricer.option.FokkerPlanckGenerator {

	/**
	 * Empty BlackScholesAlgorithm Constructor - nothing to be filled in with
	 */

	public BlackScholesAlgorithm()
	{
	}

	@Override public double payoff (
		final double dblStrike,
		final double dblTimeToExpiry,
		final double dblRiskFreeRate,
		final double dblUnderlier,
		final boolean bIsPut,
		final boolean bIsForward,
		final double dblVolatility,
		final boolean bAsPrice)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblStrike) ||
			!org.drip.quant.common.NumberUtil.IsValid (dblUnderlier) ||
				!org.drip.quant.common.NumberUtil.IsValid (dblVolatility) ||
					!org.drip.quant.common.NumberUtil.IsValid (dblTimeToExpiry) ||
						!org.drip.quant.common.NumberUtil.IsValid (dblRiskFreeRate))
			throw new java.lang.Exception ("BlackScholesAlgorithm::payoff => Invalid Inputs");

		double dblD1D2Diff = dblVolatility * java.lang.Math.sqrt (dblTimeToExpiry);

		double dblDF = java.lang.Math.exp (-1. * dblRiskFreeRate * dblTimeToExpiry);

		double dblD1 = java.lang.Double.NaN;
		double dblD2 = java.lang.Double.NaN;
		double dblForward = bIsForward ? dblUnderlier : dblUnderlier / dblDF;

		if (0. != dblVolatility) {
			dblD1 = (java.lang.Math.log (dblForward / dblStrike) + dblTimeToExpiry * (dblRiskFreeRate + (0.5
				* dblVolatility * dblVolatility))) / dblD1D2Diff;

			dblD2 = dblD1 - dblD1D2Diff;
		} else {
			dblD1 = dblForward > dblStrike ? java.lang.Double.POSITIVE_INFINITY :
				java.lang.Double.NEGATIVE_INFINITY;
			dblD2 = dblD1;
		}

		double dblCallPayoff = dblForward * org.drip.measure.gaussian.NormalQuadrature.CDF (dblD1) - dblStrike *
			org.drip.measure.gaussian.NormalQuadrature.CDF (dblD2);

		if (!bAsPrice) return bIsPut ? dblCallPayoff + dblStrike - dblForward : dblCallPayoff;

		return bIsPut ? dblDF * (dblCallPayoff + dblStrike - dblForward) : dblDF * dblCallPayoff;
	}

	@Override public org.drip.pricer.option.Greeks greeks (
		final double dblStrike,
		final double dblTimeToExpiry,
		final double dblRiskFreeRate,
		final double dblUnderlier,
		final boolean bIsPut,
		final boolean bIsForward,
		final double dblVolatility)
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblStrike) ||
			!org.drip.quant.common.NumberUtil.IsValid (dblUnderlier) ||
				!org.drip.quant.common.NumberUtil.IsValid (dblVolatility) ||
					!org.drip.quant.common.NumberUtil.IsValid (dblTimeToExpiry) ||
						!org.drip.quant.common.NumberUtil.IsValid (dblRiskFreeRate))
			return null;

		double dblTimeRoot = java.lang.Math.sqrt (dblTimeToExpiry);

		double dblDF = java.lang.Math.exp (-1. * dblRiskFreeRate * dblTimeToExpiry);

		double dblVega = java.lang.Double.NaN;
		double dblVeta = java.lang.Double.NaN;
		double dblCharm = java.lang.Double.NaN;
		double dblColor = java.lang.Double.NaN;
		double dblGamma = java.lang.Double.NaN;
		double dblSpeed = java.lang.Double.NaN;
		double dblVanna = java.lang.Double.NaN;
		double dblVomma = java.lang.Double.NaN;
		double dblUltima = java.lang.Double.NaN;
		double dblCallProb1 = java.lang.Double.NaN;
		double dblCallProb2 = java.lang.Double.NaN;
		double dblTimeDecay = java.lang.Double.NaN;
		double dblATMCallProb1 = java.lang.Double.NaN;
		double dblATMCallProb2 = java.lang.Double.NaN;
		double dblD1D2Diff = dblVolatility * dblTimeRoot;
		double dblForward = bIsForward ? dblUnderlier : dblUnderlier / dblDF;

		double dblATMD1 = dblTimeToExpiry * (dblRiskFreeRate + (0.5 * dblVolatility * dblVolatility)) /
			dblD1D2Diff;
		double dblATMD2 = dblATMD1 - dblD1D2Diff;

		double dblD1 = dblATMD1 + (java.lang.Math.log (dblForward / dblStrike)) / dblD1D2Diff;

		double dblD2 = dblD1 - dblD1D2Diff;
		double dblD1D2 = dblD1 * dblD2;

		try {
			dblCallProb1 = org.drip.measure.gaussian.NormalQuadrature.CDF (dblD1);

			dblCallProb2 = org.drip.measure.gaussian.NormalQuadrature.CDF (dblD2);

			dblATMCallProb1 = org.drip.measure.gaussian.NormalQuadrature.CDF (dblATMD1);

			dblATMCallProb2 = org.drip.measure.gaussian.NormalQuadrature.CDF (dblATMD2);

			double dblD1Density = org.drip.measure.gaussian.NormalQuadrature.Density (dblD1);

			dblVega = dblForward * dblD1Density * dblTimeRoot;
			dblVomma = dblVega * dblD1 * dblD2 / dblVolatility;
			dblGamma = dblD1Density / (dblForward * dblD1D2Diff);
			dblUltima = -1. * dblVega * (dblD1D2 * (1. - dblD1D2) + dblD1 * dblD1 + dblD2 * dblD2) /
				(dblVolatility * dblVolatility);
			dblSpeed = -1. * dblGamma / dblForward * (1. + (dblD1 / dblD1D2Diff));
			dblTimeDecay = -0.5 * dblForward * dblD1Density * dblVolatility / dblTimeRoot;
			dblVanna = dblVega / dblForward * (1. - (dblD1 / dblD1D2Diff));
			dblCharm = dblD1Density * (2. * dblRiskFreeRate * dblTimeToExpiry - dblD2 * dblD1D2Diff) / (2. *
				dblVolatility * dblD1D2Diff);
			dblVeta = dblForward * dblD1Density * dblTimeRoot * ((dblRiskFreeRate * dblD1 / (dblD1D2Diff))
				- ((1. + dblD1D2) / (2. * dblTimeToExpiry)));
			dblColor = -0.5 * dblD1Density / (dblForward * dblTimeToExpiry * dblD1D2Diff) * (1. + dblD1 *
				(2. * dblRiskFreeRate * dblTimeToExpiry - dblD2 * dblD1D2Diff) / dblD1D2Diff);
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		double dblExpectedCallPayoff = dblForward * dblCallProb1 - dblStrike * dblCallProb2;
		double dblExpectedATMCallPayoff = dblStrike * (dblATMCallProb1 - dblATMCallProb2);
		double dblCallRho = dblStrike * dblTimeToExpiry * dblCallProb2;
		double dblCallPrice = dblDF * dblExpectedCallPayoff;

		try {
			if (!bIsPut)
				return new org.drip.pricer.option.Greeks (
					dblDF,
					dblVolatility,
					dblExpectedCallPayoff,
					dblExpectedATMCallPayoff,
					dblCallPrice,
					dblCallProb1,
					dblCallProb2,
					dblCallProb1,
					dblVega,
					dblTimeDecay - dblRiskFreeRate * dblStrike * dblCallProb2,
					dblCallRho,
					dblGamma,
					dblVanna,
					dblVomma,
					dblCharm,
					dblVeta,
					dblColor,
					dblSpeed,
					dblUltima
				);

			double dblPutProb1 = org.drip.measure.gaussian.NormalQuadrature.CDF (-1. * dblD1);

			double dblPutProb2 = org.drip.measure.gaussian.NormalQuadrature.CDF (-1. * dblD2);

			return new org.drip.pricer.option.PutGreeks (
				dblDF,
				dblVolatility,
				dblExpectedCallPayoff + dblStrike - dblForward,
				dblExpectedATMCallPayoff,
				dblDF * (dblStrike * dblPutProb2 - dblForward * dblPutProb1),
				dblCallPrice + dblDF * (dblStrike - dblForward),
				dblPutProb1,
				dblPutProb2,
				-1. * dblPutProb1,
				dblVega,
				dblTimeDecay + dblRiskFreeRate * dblStrike * dblPutProb2,
				-1. * dblStrike * dblTimeToExpiry * dblPutProb2,
				dblGamma,
				dblVanna,
				dblVomma,
				dblCharm,
				dblVeta,
				dblColor,
				dblSpeed,
				dblUltima
			);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
