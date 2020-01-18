
package org.drip.sample.burgard2012;

import org.drip.analytics.date.*;
import org.drip.exposure.evolver.LatentStateVertexContainer;
import org.drip.exposure.universe.*;
import org.drip.measure.discrete.SequenceGenerator;
import org.drip.measure.dynamics.DiffusionEvaluatorLinear;
import org.drip.measure.process.DiffusionEvolver;
import org.drip.measure.realization.*;
import org.drip.numerical.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.state.identifier.OTCFixFloatLabel;
import org.drip.xva.gross.*;
import org.drip.xva.netting.CollateralGroupPath;
import org.drip.xva.strategy.*;
import org.drip.xva.vertex.AlbaneseAndersen;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
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
 *  	and computational support.
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
 * <i>FixFloatVABank</i> illustrates the Fix-Float Swap Valuation Adjustment Metrics Dependence on the Bank
 * Spread using the Set of Netting Group Exposure Simulations. The References are:
 *  
 * <br><br>
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
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/XVAAnalyticsLibrary.md">XVA Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/burgard2012/README.md">Burgard Kjaer (2012) Valuation Adjustments</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class FixFloatVABank {

	private static final double[][] ATMSwapRateOffsetRealization (
		final DiffusionEvolver deATMSwapRateOffset,
		final double dblATMSwapRateOffsetInitial,
		final double dblTime,
		final double dblTimeWidth,
		final int iNumStep,
		final int iNumSimulation)
		throws Exception
	{
		double[][] aablATMSwapRateOffset = new double[iNumSimulation][iNumStep + 1];
		double[] adblTimeWidth = new double[iNumStep];

		for (int i = 0; i < iNumStep; ++i)
			adblTimeWidth[i] = dblTimeWidth;

		for (int i = 0; i < iNumSimulation; ++i) {
			JumpDiffusionEdge[] aJDE = deATMSwapRateOffset.incrementSequence (
				new JumpDiffusionVertex (
					dblTime,
					dblATMSwapRateOffsetInitial,
					0.,
					false
				),
				JumpDiffusionEdgeUnit.Diffusion (
					adblTimeWidth,
					SequenceGenerator.Gaussian (iNumStep)
				),
				dblTimeWidth
			);

			aablATMSwapRateOffset[i][0] = dblATMSwapRateOffsetInitial;

			for (int j = 1; j <= iNumStep; ++j)
				aablATMSwapRateOffset[i][j] = aJDE[j - 1].finish();
		}

		return aablATMSwapRateOffset;
	}

	public static final void VA (
		final double dblBankHazardRate)
		throws Exception
	{
		int iNumStep = 10;
		double dblTime = 5.;
		int iNumPath = 10000;
		double dblATMSwapRateOffsetDrift = 0.0;
		double dblATMSwapRateOffsetVolatility = 0.15;
		double dblATMSwapRateOffsetInitial = 0.;
		double dblCSADrift = 0.01;
		double dblBankRecoveryRate = 0.40;
		double dblCounterPartyHazardRate = 0.025;
		double dblCounterPartyRecoveryRate = 0.30;

		double dblTimeWidth = dblTime / iNumStep;
		MarketVertex[] aMV = new MarketVertex[iNumStep + 1];
		JulianDate[] adtVertex = new JulianDate[iNumStep + 1];
		MonoPathExposureAdjustment[] aMPEA = new MonoPathExposureAdjustment[iNumPath];
		double dblBankFundingSpread = dblBankHazardRate / (1. - dblBankRecoveryRate);
		double dblCounterPartyFundingSpread = dblCounterPartyHazardRate / (1. - dblCounterPartyRecoveryRate);

		JulianDate dtSpot = DateUtil.Today();

		double[][] aablATMSwapRateOffset = ATMSwapRateOffsetRealization (
			new DiffusionEvolver (
				DiffusionEvaluatorLinear.Standard (
					dblATMSwapRateOffsetDrift,
					dblATMSwapRateOffsetVolatility
				)
			),
			dblATMSwapRateOffsetInitial,
			dblTime,
			dblTimeWidth,
			iNumStep,
			iNumPath
		);

		for (int i = 0; i <= iNumStep; ++i)
		{
			LatentStateVertexContainer latentStateVertexContainer = new LatentStateVertexContainer();

			latentStateVertexContainer.add (
				OTCFixFloatLabel.Standard ("USD-3M-10Y"),
				Double.NaN
			);

			aMV[i] = MarketVertex.Nodal (
				adtVertex[i] = dtSpot.addMonths (6 * i),
				0.,
				1.,
				dblCSADrift,
				Math.exp (-0.5 * dblCSADrift * iNumStep),
				new MarketVertexEntity (
					Math.exp (-0.5 * dblBankHazardRate * i),
					dblBankHazardRate,
					dblBankRecoveryRate,
					dblBankFundingSpread,
					Math.exp (-0.5 * dblBankHazardRate * (1. - dblBankRecoveryRate) * iNumStep),
					Double.NaN,
					Double.NaN,
					Double.NaN
				),
				new MarketVertexEntity (
					Math.exp (-0.5 * dblCounterPartyHazardRate * i),
					dblCounterPartyHazardRate,
					dblCounterPartyRecoveryRate,
					dblCounterPartyFundingSpread,
					Math.exp (-0.5 * dblCounterPartyHazardRate * (1. - dblCounterPartyRecoveryRate) * iNumStep),
					Double.NaN,
					Double.NaN,
					Double.NaN
				),
				latentStateVertexContainer
			);
		}

		MarketPath mp = MarketPath.FromMarketVertexArray (aMV);

		for (int i = 0; i < iNumPath; ++i) {
			AlbaneseAndersen[] aHGVR = new AlbaneseAndersen[iNumStep + 1];

			for (int j = 0; j <= iNumStep; ++j)
				aHGVR[j] = new AlbaneseAndersen (
					adtVertex[j],
					dblTimeWidth * (iNumStep - j) * aablATMSwapRateOffset[i][j],
					0.,
					0.
				);

			CollateralGroupPath[] aHGP = new CollateralGroupPath[] {
				new CollateralGroupPath (
					aHGVR,
					mp
				)
			};

			aMPEA[i] = new MonoPathExposureAdjustment (
				new AlbaneseAndersenFundingGroupPath[] {
					AlbaneseAndersenFundingGroupPath.Mono (
						new AlbaneseAndersenNettingGroupPath (
							aHGP,
							mp
						),
						mp
					)
				}
			);
		}

		ExposureAdjustmentAggregator eaa = new ExposureAdjustmentAggregator (aMPEA);

		System.out.println ("\t|| " +
			FormatUtil.FormatDouble (dblBankHazardRate, 3, 0, 10000.) + " bp => " +
			FormatUtil.FormatDouble (eaa.ucva().amount(), 1, 2, 100.) + "% | " +
			FormatUtil.FormatDouble (eaa.ftdcva().amount(), 1, 2, 100.) + "% | " +
			FormatUtil.FormatDouble (eaa.cva().amount(), 1, 2, 100.) + "% | " +
			FormatUtil.FormatDouble (eaa.cvacl().amount(), 1, 2, 100.) + "% | " +
			FormatUtil.FormatDouble (eaa.dva().amount(), 1, 2, 100.) + "% | " +
			FormatUtil.FormatDouble (eaa.fva().amount(), 1, 2, 100.) + "% | " +
			FormatUtil.FormatDouble (eaa.fda().amount(), 1, 2, 100.) + "% | " +
			FormatUtil.FormatDouble (eaa.fca().amount(), 1, 2, 100.) + "% | " +
			FormatUtil.FormatDouble (eaa.fba().amount(), 1, 2, 100.) + "% | " +
			FormatUtil.FormatDouble (eaa.sfva().amount(), 1, 2, 100.) + "% | " +
			FormatUtil.FormatDouble (eaa.total(), 1, 2, 100.) + "% ||"
		);
	}

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		double[] adblBankHazardRate = new double[] {
			0.0025,
			0.0050,
			0.0075,
			0.0100,
			0.0125,
			0.0150,
			0.0175,
			0.0200,
			0.0225,
			0.0250,
			0.0275,
			0.0300
		};

		System.out.println();

		System.out.println ("\t||-------------------------------------------------------------------------------------------------------------||");

		System.out.println ("\t||                                      VA DEPENDENCE ON BANK HAZARD RATE                                      ||");

		System.out.println ("\t||-------------------------------------------------------------------------------------------------------------||");

		System.out.println ("\t||  Hazard =>  UCVA  | FTDCVA |   CVA  |  CVACL |   DVA  |   FVA  |   FDA  |   FCA  |   FBA  |  SFVA  |  Total ||");

		System.out.println ("\t||-------------------------------------------------------------------------------------------------------------||");

		for (double dblBankHazardRate : adblBankHazardRate)
			VA (dblBankHazardRate);

		System.out.println ("\t||-------------------------------------------------------------------------------------------------------------||");

		System.out.println();

		EnvManager.TerminateEnv();
	}
}
