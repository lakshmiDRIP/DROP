
package org.drip.sample.xva;

import org.drip.analytics.date.*;
import org.drip.exposure.mpor.CollateralAmountEstimator;
import org.drip.exposure.mpor.CollateralAmountEstimatorOutput;
import org.drip.measure.bridge.BrokenDateInterpolatorLinearT;
import org.drip.measure.discrete.SequenceGenerator;
import org.drip.measure.dynamics.DiffusionEvaluatorLinear;
import org.drip.measure.process.DiffusionEvolver;
import org.drip.measure.realization.*;
import org.drip.service.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.xva.proto.*;
import org.drip.xva.settings.*;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting analytics/risk, transaction cost analytics,
 *  	asset liability management analytics, capital, exposure, and margin analytics, valuation adjustment
 *  	analytics, and portfolio construction analytics within and across fixed income, credit, commodity,
 *  	equity, FX, and structured products. It also includes auxiliary libraries for algorithm support,
 *  	numerical analysis, numerical optimization, spline builder, model validation, statistical learning,
 *  	graph builder/navigator, and computational support.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three modules:
 *  
 *  - DROP Product Core - https://lakshmidrip.github.io/DROP-Product-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Computational Core - https://lakshmidrip.github.io/DROP-Computational-Core/
 * 
 * 	DROP Product Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Loan Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 *  - Asset Liability Management Analytics
 * 	- Capital Estimation Analytics
 * 	- Exposure Analytics
 * 	- Margin Analytics
 * 	- XVA Analytics
 * 
 * 	DROP Computational Core implements libraries for the following:
 * 	- Algorithm Support
 * 	- Computation Support
 * 	- Function Analysis
 *  - Graph Algorithm
 *  - Model Validation
 * 	- Numerical Analysis
 * 	- Numerical Optimizer
 * 	- Spline Builder
 *  - Statistical Learning
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
 * <i>PortfolioCollateralEstimate</i> illustrates the Estimation of the Collateral Amount on a Single Trade
 * 	Collateral Portfolio. The References are:
 *
 *  <br><br>
 *  <ul>
 *  	<li>
 *  		Burgard, C., and M. Kjaer (2014): PDE Representations of Derivatives with Bilateral Counter-party
 *  			Risk and Funding Costs <i>Journal of Credit Risk</i> <b>7 (3)</b> 1-19
 *  	</li>
 *  	<li>
 *  		Burgard, C., and M. Kjaer (2014): In the Balance <i>Risk</i> <b>24 (11)</b> 72-75
 *  	</li>
 *  	<li>
 *  		Gregory, J. (2009): Being Two-faced over Counter-party Credit Risk <i>Risk</i> <b>20 (2)</b>
 *  			86-90
 *  	</li>
 *  	<li>
 *  		Li, B., and Y. Tang (2007): <i>Quantitative Analysis, Derivatives Modeling, and Trading
 *  			Strategies in the Presence of Counter-party Credit Risk for the Fixed Income Market</i>
 *  			<b>World Scientific Publishing</b> Singapore
 *  	</li>
 *  	<li>
 *  		Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives Pricing
 *  			<i>Risk</i> <b>21 (2)</b> 97-102
 *  	</li>
 *  </ul>
 *
 *  <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/XVAAnalyticsLibrary.md">XVA Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/xva/README.md">XVA Collateralized Uncollateralized Zero Threshold</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class PortfolioCollateralEstimate {

	/**
	 * Entry Point
	 * 
	 * @param astrArgs Command Line Argument Array
	 * 
	 * @throws Exception Thrown on Error/Exception Situation
	 */

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		int iNumStep = 40;
		double dblTime = 10.;
		double dblPortfolioDrift = 0.0;
		double dblPortfolioVolatility = 0.15;
		double dblPortfolioValueStart = 0.;
		double dblBankThreshold = -0.1;
		double dblCounterPartyThreshold = 0.1;

		JulianDate dtSpot = DateUtil.Today();

		JulianDate dtStart = dtSpot;
		double dblTimeWidth = dblTime / iNumStep;
		double[] adblTimeWidth = new double[iNumStep];

		for (int i = 0; i < iNumStep; ++i)
			adblTimeWidth[i] = dblTimeWidth;

		PositionGroupSpecification positionGroupSpecification = PositionGroupSpecification.FixedThreshold (
			"FIXEDTHRESHOLD",
			dblCounterPartyThreshold,
			dblBankThreshold,
			PositionReplicationScheme.ALBANESE_ANDERSEN_VERTEX,
			BrokenDateScheme.SQUARE_ROOT_OF_TIME,
			0.,
			CloseOutScheme.ISDA_92
		);

		DiffusionEvolver dePortfolio = new DiffusionEvolver (
			DiffusionEvaluatorLinear.Standard (
				dblPortfolioDrift,
				dblPortfolioVolatility
			)
		);

		JumpDiffusionEdge[] aJDESwapRate = dePortfolio.incrementSequence (
			new JumpDiffusionVertex (
				dblTime,
				dblPortfolioValueStart,
				0.,
				false
			),
			JumpDiffusionEdgeUnit.Diffusion (
				adblTimeWidth,
				SequenceGenerator.Gaussian (iNumStep)
			),
			dblTimeWidth
		);

		System.out.println();

		System.out.println ("\t||--------------------------------------------------------------------------------------------------------------------------||");

		System.out.println ("\t||                                       COLLATERAL AMOUNT ESTIMATION OUTPUT METRICS                                        ||");

		System.out.println ("\t||--------------------------------------------------------------------------------------------------------------------------||");

		System.out.println ("\t||    L -> R:                                                                                                               ||");

		System.out.println ("\t||            - Forward Date                                                                                                ||");

		System.out.println ("\t||            - Forward Value                                                                                               ||");

		System.out.println ("\t||            - Bank Margin Date                                                                                            ||");

		System.out.println ("\t||            - Counter Party Margin Date                                                                                   ||");

		System.out.println ("\t||            - Bank Window Margin Value                                                                                    ||");

		System.out.println ("\t||            - Counter Party Window Margin Value                                                                           ||");

		System.out.println ("\t||            - Bank Collateral Threshold                                                                                   ||");

		System.out.println ("\t||            - Counter Party Collateral Threshold                                                                          ||");

		System.out.println ("\t||            - Bank Posting Requirement                                                                                    ||");

		System.out.println ("\t||            - Counter Party Posting Requirement                                                                           ||");

		System.out.println ("\t||            - Gross Posting Requirement                                                                                   ||");

		System.out.println ("\t||--------------------------------------------------------------------------------------------------------------------------||");

		for (int i = 0; i < iNumStep; ++i) {
			JulianDate dtEnd = dtStart.addMonths (3);

			double dblPortfolioValueFinish = dblTimeWidth * (iNumStep - i) * aJDESwapRate[i].finish();

			CollateralAmountEstimator hae = new CollateralAmountEstimator (
				positionGroupSpecification,
				new BrokenDateInterpolatorLinearT (
					dtStart.julian(),
					dtEnd.julian(),
					dblPortfolioValueStart,
					dblPortfolioValueFinish
				),
				Double.NaN
			);

			CollateralAmountEstimatorOutput haeo = hae.output (dtEnd);

			System.out.println (
				"\t|| " +
				dtEnd + " => " +
				FormatUtil.FormatDouble (dblPortfolioValueFinish, 1, 4, 1.) + " | " +
				haeo.dealerMarginDate() + " | " +
				haeo.clientMarginDate() + " | " +
				FormatUtil.FormatDouble (haeo.dealerWindowMarginValue(), 1, 4, 1.) + " | " +
				FormatUtil.FormatDouble (haeo.clientWindowMarginValue(), 1, 4, 1.) + " | " +
				FormatUtil.FormatDouble (haeo.dealerCollateralThreshold(), 1, 4, 1.) + " | " +
				FormatUtil.FormatDouble (haeo.clientCollateralThreshold(), 1, 4, 1.) + " | " +
				FormatUtil.FormatDouble (haeo.dealerPostingRequirement(), 1, 4, 1.) + " | " +
				FormatUtil.FormatDouble (haeo.clientPostingRequirement(), 1, 4, 1.) + " | " +
				FormatUtil.FormatDouble (haeo.postingRequirement(), 1, 4, 1.) + " ||"
			);

			dtStart = dtEnd;
			dblPortfolioValueStart = dblPortfolioValueFinish;
		}

		System.out.println ("\t||--------------------------------------------------------------------------------------------------------------------------||");

		System.out.println();

		EnvManager.TerminateEnv();
	}
}
