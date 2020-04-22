
package org.drip.sample.burgard2013;

import org.drip.analytics.date.*;
import org.drip.exposure.evolver.LatentStateVertexContainer;
import org.drip.exposure.universe.*;
import org.drip.measure.discrete.SequenceGenerator;
import org.drip.measure.dynamics.DiffusionEvaluatorLinear;
import org.drip.measure.process.DiffusionEvolver;
import org.drip.measure.realization.*;
import org.drip.measure.statistics.UnivariateDiscreteThin;
import org.drip.numerical.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.state.identifier.OTCFixFloatLabel;
import org.drip.xva.basel.*;
import org.drip.xva.definition.*;
import org.drip.xva.gross.*;
import org.drip.xva.hypothecation.*;
import org.drip.xva.netting.CollateralGroupPath;
import org.drip.xva.strategy.*;
import org.drip.xva.vertex.BurgardKjaerBuilder;

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
 * <i>BilateralCSAUncollateralizedFunding</i> examines the Basel BCBS 2012 OTC Accounting Impact to a
 * Portfolio of 10 Swaps resulting from the Addition of a New Swap - Comparison via both FVA/FDA and FCA/FBA
 * Schemes. Simulation is carried out under the following Criteria using one of the Generalized Burgard Kjaer
 * (2013) Scheme.
 *  
 * <br><br>
 *  <ul>
 *  	<li>
 *    		Collateralization Status - Uncollateralized
 *  	</li>
 *  	<li>
 *    		Aggregation Unit         - Funding Group
 *  	</li>
 *  	<li>
 *    		Added Swap Type          - Zero Upfront Par Swap (Neutral)
 *  	</li>
 *  	<li>
 *    		Market Dynamics          - Deterministic (Static Market Evolution)
 *  	</li>
 *  	<li>
 *    		Funding Strategy         - Gold Plated Two Way CSA
 *  	</li>
 *  </ul>
 *  
 * The References are:
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
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/burgard2013/README.md">Burgard Kjaer (2013) Valuation Adjustments</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class BilateralCSAUncollateralizedFunding {

	private static final double[] ATMSwapRateOffsetRealization (
		final DiffusionEvolver deATMSwapRateOffset,
		final double dblATMSwapRateOffsetInitial,
		final double[] adblRandom,
		final double dblTime,
		final double dblTimeWidth,
		final int iNumStep)
		throws Exception
	{
		double[] adblATMSwapRateOffset = new double[iNumStep + 1];
		adblATMSwapRateOffset[0] = dblATMSwapRateOffsetInitial;
		double[] adblTimeWidth = new double[iNumStep];

		for (int i = 0; i < iNumStep; ++i)
			adblTimeWidth[i] = dblTimeWidth;


		JumpDiffusionEdge[] aJDE = deATMSwapRateOffset.incrementSequence (
			new JumpDiffusionVertex (
				dblTime,
				dblATMSwapRateOffsetInitial,
				0.,
				false
			),
			JumpDiffusionEdgeUnit.Diffusion (
				adblTimeWidth,
				adblRandom
			),
			dblTimeWidth
		);

		for (int j = 1; j <= iNumStep; ++j)
			adblATMSwapRateOffset[j] = aJDE[j - 1].finish();

		return adblATMSwapRateOffset;
	}

	private static final double[] SwapPortfolioValueRealization (
		final DiffusionEvolver deATMSwapRate,
		final double dblATMSwapRateStart,
		final double[] adblRandom,
		final int iNumStep,
		final double dblTime,
		final double dblTimeWidth,
		final double dblTimeMaturity,
		final double dblSwapNotional)
		throws Exception
	{
		double[] adblSwapPortfolioValueRealization = new double[iNumStep + 1];
		int iMaturityStep = (int) (dblTimeMaturity / dblTimeWidth);

		for (int i = 0; i < iNumStep; ++i)
			adblSwapPortfolioValueRealization[i] = 0.;

		double[] adblATMSwapRateOffsetRealization = ATMSwapRateOffsetRealization (
			deATMSwapRate,
			dblATMSwapRateStart,
			adblRandom,
			dblTime,
			dblTimeWidth,
			iNumStep
		);

		for (int j = 0; j <= iNumStep; ++j)
			adblSwapPortfolioValueRealization[j] = j > iMaturityStep ? 0. :
				dblSwapNotional * dblTimeWidth * (iMaturityStep - j) * adblATMSwapRateOffsetRealization[j];

		return adblSwapPortfolioValueRealization;
	}

	private static final ExposureAdjustmentAggregator[] Mix (
		final double dblTimeMaturity1,
		final double dblATMSwapRateOffsetStart1,
		final double dblSwapNotional1,
		final double dblTimeMaturity2,
		final double dblATMSwapRateOffsetStart2,
		final double dblSwapNotional2)
		throws Exception
	{
		int iNumStep = 10;
		int iNumPath = 100000;
		int iNumVertex = 10;
		double dblTime = 5.;
		double dblATMSwapRateOffsetDrift = 0.0;
		double dblATMSwapRateOffsetVolatility = 0.25;
		double dblOvernightNumeraireDrift = 0.004;
		double dblCSADrift = 0.01;
		double dblBankHazardRate = 0.015;
		double dblBankSeniorRecoveryRate = 0.40;
		double dblBankSubordinateRecoveryRate = 0.15;
		double dblCounterPartyHazardRate = 0.030;
		double dblCounterPartyRecoveryRate = 0.30;

		JulianDate dtSpot = DateUtil.Today();

		double dblTimeWidth = dblTime / iNumStep;
		JulianDate[] adtVertex = new JulianDate[iNumStep + 1];
		MarketVertex[] aMV = new MarketVertex[iNumStep + 1];
		double[][] aadblPortfolio1Value = new double[iNumPath][iNumStep + 1];
		double[][] aadblPortfolio2Value = new double[iNumPath][iNumStep + 1];
		double[][] aadblCollateralBalance = new double[iNumPath][iNumStep + 1];
		MonoPathExposureAdjustment[] aCPGPGround = new MonoPathExposureAdjustment[iNumPath];
		MonoPathExposureAdjustment[] aCPGPExtended = new MonoPathExposureAdjustment[iNumPath];
		double dblBankSeniorFundingSpread = dblBankHazardRate / (1. - dblBankSeniorRecoveryRate);
		double dblBankSubordinateFundingSpread = dblBankHazardRate / (1. - dblBankSubordinateRecoveryRate);
		double dblCounterPartyFundingSpread = dblCounterPartyHazardRate / (1. - dblCounterPartyRecoveryRate);

		CloseOut cog = new CloseOutBilateral (
			dblBankSeniorRecoveryRate,
			dblCounterPartyRecoveryRate
		);

		DiffusionEvolver deATMSwapRateOffset = new DiffusionEvolver (
			DiffusionEvaluatorLinear.Standard (
				dblATMSwapRateOffsetDrift,
				dblATMSwapRateOffsetVolatility
			)
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
				dblOvernightNumeraireDrift,
				Math.exp (-0.5 * dblOvernightNumeraireDrift * iNumStep),
				dblCSADrift,
				Math.exp (-0.5 * dblCSADrift * iNumStep),
				new MarketVertexEntity (
					Math.exp (-0.5 * dblBankHazardRate * i),
					dblBankHazardRate,
					dblBankSeniorRecoveryRate,
					dblBankSeniorFundingSpread,
					Math.exp (-0.5 * dblBankHazardRate * (1. - dblBankSeniorRecoveryRate) * iNumStep),
					dblBankSubordinateRecoveryRate,
					dblBankSubordinateFundingSpread,
						Math.exp (-0.5 * dblBankHazardRate * (1. - dblBankSubordinateRecoveryRate) * iNumStep)
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

		for (int i = 0; i < iNumPath; ++i) {
			aadblPortfolio1Value[i] = SwapPortfolioValueRealization (
				deATMSwapRateOffset,
				dblATMSwapRateOffsetStart1,
				SequenceGenerator.Gaussian (iNumStep),
				iNumVertex,
				dblTime,
				dblTimeWidth,
				dblTimeMaturity1,
				dblSwapNotional1
			);

			aadblPortfolio2Value[i] = SwapPortfolioValueRealization (
				deATMSwapRateOffset,
				dblATMSwapRateOffsetStart2,
				SequenceGenerator.Gaussian (iNumStep),
				iNumVertex,
				dblTime,
				dblTimeWidth,
				dblTimeMaturity2,
				dblSwapNotional2
			);

			CollateralGroupVertex[] aCGV1 = new CollateralGroupVertex[iNumStep + 1];
			CollateralGroupVertex[] aCGV2 = new CollateralGroupVertex[iNumStep + 1];

			for (int j = 0; j <= iNumStep; ++j) {
				aadblCollateralBalance[i][j] = 0.;

				if (0 != j) {
					aCGV1[j] = BurgardKjaerBuilder.GoldPlatedTwoWayCSA (
						adtVertex[j],
						aadblPortfolio1Value[i][j],
						0.,
						new MarketEdge (
							aMV[j - 1],
							aMV[j]
						),
						cog
					);

					aCGV2[j] = BurgardKjaerBuilder.GoldPlatedTwoWayCSA (
						adtVertex[j],
						aadblPortfolio2Value[i][j],
						0.,
						new MarketEdge (
							aMV[j - 1],
							aMV[j]
						),
						cog
					);
				} else {
					aCGV1[j] = BurgardKjaerBuilder.Initial (
						adtVertex[j],
						aadblPortfolio1Value[i][0],
						aMV[j],
						cog
					);

					aCGV2[j] = BurgardKjaerBuilder.Initial (
						adtVertex[j],
						aadblPortfolio2Value[i][0],
						aMV[j],
						cog
					);
				}
			}

			MarketPath np = MarketPath.FromMarketVertexArray (aMV);

			CollateralGroupPath[] aCGP1 = new CollateralGroupPath[] {
				new CollateralGroupPath (
					aCGV1,
					np
				)
			};

			CollateralGroupPath[] aCGP2 = new CollateralGroupPath[] {
				new CollateralGroupPath (
					aCGV2,
					np
				)
			};

			aCPGPGround[i] = new MonoPathExposureAdjustment (
				new AlbaneseAndersenFundingGroupPath[] {
					new AlbaneseAndersenFundingGroupPath (
						new AlbaneseAndersenNettingGroupPath[] {
							new AlbaneseAndersenNettingGroupPath (
								aCGP1,
								np
							)
						},
						np
					)
				}
			);

			aCPGPExtended[i] = new MonoPathExposureAdjustment (
				new AlbaneseAndersenFundingGroupPath[] {
					new AlbaneseAndersenFundingGroupPath (
						new AlbaneseAndersenNettingGroupPath[] {
							new AlbaneseAndersenNettingGroupPath (
								aCGP1,
								np
							),
							new AlbaneseAndersenNettingGroupPath (
								aCGP2,
								np
							)
						},
						np
					)
				}
			);
		}

		return new ExposureAdjustmentAggregator[] {
			new ExposureAdjustmentAggregator (aCPGPGround),
			new ExposureAdjustmentAggregator (aCPGPExtended)
		};
	}

	private static final void CPGDDump (
		final String strHeader,
		final ExposureAdjustmentDigest ead)
		throws Exception
	{
		System.out.println();

		UnivariateDiscreteThin udtUCOLVA = ead.ucolva();

		UnivariateDiscreteThin udtFTDCOLVA = ead.ftdcolva();

		UnivariateDiscreteThin udtUCVA = ead.ucva();

		UnivariateDiscreteThin udtFTDCVA = ead.ftdcva();

		UnivariateDiscreteThin udtCVACL = ead.cvacl();

		UnivariateDiscreteThin udtCVA = ead.cva();

		UnivariateDiscreteThin udtDVA = ead.dva();

		UnivariateDiscreteThin udtFVA = ead.fva();

		UnivariateDiscreteThin udtFDA = ead.fda();

		UnivariateDiscreteThin udtFCA = ead.fca();

		UnivariateDiscreteThin udtFBA = ead.fba();

		UnivariateDiscreteThin udtSFVA = ead.sfva();

		System.out.println (
			"\t||-----------------------------------------------------------------------------------------------------------------------------------||"
		);

		System.out.println (strHeader);

		System.out.println (
			"\t||-----------------------------------------------------------------------------------------------------------------------------------||"
		);

		System.out.println (
			"\t||  OODLE  => UCOLVA  | FTDCOLVA |  UCVA   | FTDCVA  |  CVACL  |   CVA   |   DVA   |   FVA   |   FDA   |   FCA   |   FBA   |   SFVA  ||"
		);

		System.out.println (
			"\t||-----------------------------------------------------------------------------------------------------------------------------------||"
		);

		System.out.println (
			"\t|| Average => " +
			FormatUtil.FormatDouble (udtUCOLVA.average(), 2, 2, 1.) + "  |  " +
			FormatUtil.FormatDouble (udtFTDCOLVA.average(), 2, 2, 1.) + "  | " +
			FormatUtil.FormatDouble (udtUCVA.average(), 2, 2, 1.) + "  | " +
			FormatUtil.FormatDouble (udtFTDCVA.average(), 2, 2, 1.) + "  | " +
			FormatUtil.FormatDouble (udtCVACL.average(), 2, 2, 1.) + "  | " +
			FormatUtil.FormatDouble (udtCVA.average(), 2, 2, 1.) + "  | " +
			FormatUtil.FormatDouble (udtDVA.average(), 2, 2, 1.) + "  | " +
			FormatUtil.FormatDouble (udtFVA.average(), 2, 2, 1.) + "  | " +
			FormatUtil.FormatDouble (udtFDA.average(), 2, 2, 1.) + "  | " +
			FormatUtil.FormatDouble (udtFCA.average(), 2, 2, 1.) + "  | " +
			FormatUtil.FormatDouble (udtFBA.average(), 2, 2, 1.) + "  | " + 
			FormatUtil.FormatDouble (udtSFVA.average(), 2, 2, 1.) + "  ||"
		);

		System.out.println (
			"\t|| Minimum => " +
			FormatUtil.FormatDouble (udtUCOLVA.minimum(), 2, 2, 1.) + "  |  " +
			FormatUtil.FormatDouble (udtFTDCOLVA.minimum(), 2, 2, 1.) + "  | " +
			FormatUtil.FormatDouble (udtUCVA.minimum(), 2, 2, 1.) + "  | " +
			FormatUtil.FormatDouble (udtFTDCVA.minimum(), 2, 2, 1.) + "  | " +
			FormatUtil.FormatDouble (udtCVACL.minimum(), 2, 2, 1.) + "  | " +
			FormatUtil.FormatDouble (udtCVA.minimum(), 2, 2, 1.) + "  | " +
			FormatUtil.FormatDouble (udtDVA.minimum(), 2, 2, 1.) + "  | " +
			FormatUtil.FormatDouble (udtFVA.minimum(), 2, 2, 1.) + "  | " +
			FormatUtil.FormatDouble (udtFDA.minimum(), 2, 2, 1.) + "  | " +
			FormatUtil.FormatDouble (udtFCA.minimum(), 2, 2, 1.) + "  | " +
			FormatUtil.FormatDouble (udtFBA.minimum(), 2, 2, 1.) + "  | " + 
			FormatUtil.FormatDouble (udtSFVA.minimum(), 2, 2, 1.) + "  ||"
		);

		System.out.println (
			"\t|| Maximum => " +
			FormatUtil.FormatDouble (udtUCOLVA.maximum(), 2, 2, 1.) + "  |  " +
			FormatUtil.FormatDouble (udtFTDCOLVA.maximum(), 2, 2, 1.) + "  | " +
			FormatUtil.FormatDouble (udtUCVA.maximum(), 2, 2, 1.) + "  | " +
			FormatUtil.FormatDouble (udtFTDCVA.maximum(), 2, 2, 1.) + "  | " +
			FormatUtil.FormatDouble (udtCVACL.maximum(), 2, 2, 1.) + "  | " +
			FormatUtil.FormatDouble (udtCVA.maximum(), 2, 2, 1.) + "  | " +
			FormatUtil.FormatDouble (udtDVA.maximum(), 2, 2, 1.) + "  | " +
			FormatUtil.FormatDouble (udtFVA.maximum(), 2, 2, 1.) + "  | " +
			FormatUtil.FormatDouble (udtFDA.maximum(), 2, 2, 1.) + "  | " +
			FormatUtil.FormatDouble (udtFCA.maximum(), 2, 2, 1.) + "  | " +
			FormatUtil.FormatDouble (udtFBA.maximum(), 2, 2, 1.) + "  | " + 
			FormatUtil.FormatDouble (udtSFVA.maximum(), 2, 2, 1.) + "  ||"
		);

		System.out.println (
			"\t||  Error  => " +
			FormatUtil.FormatDouble (udtUCOLVA.error(), 2, 2, 1.) + "  |  " +
			FormatUtil.FormatDouble (udtFTDCOLVA.error(), 2, 2, 1.) + "  | " +
			FormatUtil.FormatDouble (udtUCVA.error(), 2, 2, 1.) + "  | " +
			FormatUtil.FormatDouble (udtFTDCVA.error(), 2, 2, 1.) + "  | " +
			FormatUtil.FormatDouble (udtCVACL.error(), 2, 2, 1.) + "  | " +
			FormatUtil.FormatDouble (udtCVA.error(), 2, 2, 1.) + "  | " +
			FormatUtil.FormatDouble (udtDVA.error(), 2, 2, 1.) + "  | " +
			FormatUtil.FormatDouble (udtFVA.error(), 2, 2, 1.) + "  | " +
			FormatUtil.FormatDouble (udtFDA.error(), 2, 2, 1.) + "  | " +
			FormatUtil.FormatDouble (udtFCA.error(), 2, 2, 1.) + "  | " +
			FormatUtil.FormatDouble (udtFBA.error(), 2, 2, 1.) + "  | " + 
			FormatUtil.FormatDouble (udtSFVA.error(), 2, 2, 1.) + "  ||"
		);

		System.out.println (
			"\t||-----------------------------------------------------------------------------------------------------------------------------------||"
		);
	}

	private static final void CPGDDiffDump (
		final String strHeader,
		final ExposureAdjustmentDigest eadGround,
		final ExposureAdjustmentDigest eadExpanded)
		throws Exception
	{
		System.out.println();

		System.out.println (
			"\t||-----------------------------------------------------------------------------------------------------------------------------------||"
		);

		System.out.println (strHeader);

		System.out.println (
			"\t||-----------------------------------------------------------------------------------------------------------------------------------||"
		);

		System.out.println (
			"\t||  OODLE  => UCOLVA  | FTDCOLVA |  UCVA   | FTDCVA  |  CVACL  |   CVA   |   DVA   |   FVA   |   FDA   |   FCA   |   FBA   |   SFVA  ||"
		);

		System.out.println (
			"\t||-----------------------------------------------------------------------------------------------------------------------------------||"
		);

		System.out.println (
			"\t|| Average => " +
			FormatUtil.FormatDouble (eadExpanded.ucolva().average() - eadGround.ucolva().average(), 3, 1, 10000.) + "  |  " +
			FormatUtil.FormatDouble (eadExpanded.ftdcolva().average() - eadGround.ftdcolva().average(), 3, 1, 10000.) + "  | " +
			FormatUtil.FormatDouble (eadExpanded.ucva().average() - eadGround.ucva().average(), 3, 1, 10000.) + "  | " +
			FormatUtil.FormatDouble (eadExpanded.ftdcva().average() - eadGround.ftdcva().average(), 3, 1, 10000.) + "  | " +
			FormatUtil.FormatDouble (eadExpanded.cvacl().average() - eadGround.cvacl().average(), 3, 1, 10000.) + "  | " +
			FormatUtil.FormatDouble (eadExpanded.cva().average() - eadGround.cva().average(), 3, 1, 10000.) + "  | " +
			FormatUtil.FormatDouble (eadExpanded.dva().average() - eadGround.dva().average(), 3, 1, 10000.) + "  | " +
			FormatUtil.FormatDouble (eadExpanded.fva().average() - eadGround.fva().average(), 3, 1, 10000.) + "  | " +
			FormatUtil.FormatDouble (eadExpanded.fda().average() - eadGround.fda().average(), 3, 1, 10000.) + "  | " +
			FormatUtil.FormatDouble (eadExpanded.fca().average() - eadGround.fca().average(), 3, 1, 10000.) + "  | " +
			FormatUtil.FormatDouble (eadExpanded.fba().average() - eadGround.fba().average(), 3, 1, 10000.) + "  | " + 
			FormatUtil.FormatDouble (eadExpanded.sfva().average() - eadGround.sfva().average(), 3, 1, 10000.) + "  ||"
		);

		System.out.println (
			"\t||-----------------------------------------------------------------------------------------------------------------------------------||"
		);
	}

	private static final void BaselAccountingMetrics (
		final String strHeader,
		final ExposureAdjustmentAggregator cpgaGround,
		final ExposureAdjustmentAggregator cpgaExpanded)
		throws Exception
	{
		OTCAccountingModus oasFCAFBA = new OTCAccountingModusFCAFBA (cpgaGround);

		OTCAccountingModus oasFVAFDA = new OTCAccountingModusFVAFDA (cpgaGround);

		OTCAccountingPolicy oapFCAFBA = oasFCAFBA.feePolicy (cpgaExpanded);

		OTCAccountingPolicy oapFVAFDA = oasFVAFDA.feePolicy (cpgaExpanded);

		System.out.println();

		System.out.println (
			"\t||---------------------------------------------------------------------||"
		);

		System.out.println (strHeader);

		System.out.println (
			"\t||---------------------------------------------------------------------||"
		);

		System.out.println (
			"\t|| L -> R:                                                             ||"
		);

		System.out.println (
			"\t||         - Accounting Type (FCA/FBA vs. FVA/FDA)                     ||"
		);

		System.out.println (
			"\t||         - Contra Asset Adjustment                                   ||"
		);

		System.out.println (
			"\t||         - Contra Liability Adjustment                               ||"
		);

		System.out.println (
			"\t||         - FTP (Funding Transfer Pricing) (bp)                       ||"
		);

		System.out.println (
			"\t||         - CET1 (Common Equity Tier I) Change (bp)                   ||"
		);

		System.out.println (
			"\t||         - CL (Contra Liability) Change (bp)                         ||"
		);

		System.out.println (
			"\t||         - PFV (Porfolio Value) Change (Income) (bp)                 ||"
		);

		System.out.println (
			"\t||---------------------------------------------------------------------||"
		);

		System.out.println ("\t|| FCA/FBA Accounting => " +
			FormatUtil.FormatDouble (oasFCAFBA.contraAssetAdjustment(), 1, 4, 1.) + " | " +
			FormatUtil.FormatDouble (oasFCAFBA.contraLiabilityAdjustment(), 1, 4, 1.) + " | " +
			FormatUtil.FormatDouble (oapFCAFBA.fundingTransferPricing(), 3, 0, 10000.) + " | " +
			FormatUtil.FormatDouble (oapFCAFBA.cet1Change(), 3, 0, 10000.) + " | " +
			FormatUtil.FormatDouble (oapFCAFBA.contraLiabilityChange(), 3, 0, 10000.) + " | " +
			FormatUtil.FormatDouble (oapFCAFBA.portfolioValueChange(), 3, 0, 10000.) + " || "
		);

		System.out.println ("\t|| FVA/FDA Accounting => " +
			FormatUtil.FormatDouble (oasFVAFDA.contraAssetAdjustment(), 1, 4, 1.) + " | " +
			FormatUtil.FormatDouble (oasFVAFDA.contraLiabilityAdjustment(), 1, 4, 1.) + " | " +
			FormatUtil.FormatDouble (oapFVAFDA.fundingTransferPricing(), 3, 0, 10000.) + " | " +
			FormatUtil.FormatDouble (oapFVAFDA.cet1Change(), 3, 0, 10000.) + " | " +
			FormatUtil.FormatDouble (oapFVAFDA.contraLiabilityChange(), 3, 0, 10000.) + " | " +
			FormatUtil.FormatDouble (oapFVAFDA.portfolioValueChange(), 3, 0, 10000.) + " || "
		);

		System.out.println (
			"\t||---------------------------------------------------------------------||"
		);

		System.out.println();
	}

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		ExposureAdjustmentAggregator[] aCPGA = Mix (
			5.,
			0.,
			100.,
			5.,
			0.,
			1.
		);

		ExposureAdjustmentAggregator cpgaGround = aCPGA[0];
		ExposureAdjustmentAggregator cpgaExtended = aCPGA[1];

		ExposureAdjustmentDigest cpgdGround = cpgaGround.digest();

		ExposureAdjustmentDigest cpgdExtended = cpgaExtended.digest();

		CPGDDump (
			"\t||                                                  GROUND BOOK ADJUSTMENT METRICS                                                   ||",
			cpgdGround
		);

		CPGDDump (
			"\t||                                                 EXTENDED BOOK ADJUSTMENT METRICS                                                  ||",
			cpgdExtended
		);

		CPGDDiffDump (
			"\t||                                             TRADE INCREMENT ADJUSTMENT METRICS (bp)                                               ||",
			cpgdGround,
			cpgdExtended
		);

		BaselAccountingMetrics (
			"\t||           ALBANESE & ANDERSEN (2015) BCBS OTC ACCOUNTING            ||",
			cpgaGround,
			cpgaExtended
		);

		EnvManager.TerminateEnv();
	}
}
