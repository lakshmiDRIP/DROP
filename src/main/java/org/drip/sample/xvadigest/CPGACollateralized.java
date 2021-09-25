
package org.drip.sample.xvadigest;

import org.drip.analytics.date.*;
import org.drip.exposure.evolver.LatentStateVertexContainer;
import org.drip.exposure.mpor.CollateralAmountEstimator;
import org.drip.exposure.universe.*;
import org.drip.measure.bridge.BrokenDateInterpolatorLinearT;
import org.drip.measure.discrete.SequenceGenerator;
import org.drip.measure.dynamics.DiffusionEvaluatorLinear;
import org.drip.measure.process.DiffusionEvolver;
import org.drip.measure.realization.*;
import org.drip.measure.statistics.UnivariateDiscreteThin;
import org.drip.service.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.state.identifier.OTCFixFloatLabel;
import org.drip.xva.gross.*;
import org.drip.xva.netting.CollateralGroupPath;
import org.drip.xva.proto.*;
import org.drip.xva.settings.*;
import org.drip.xva.strategy.*;
import org.drip.xva.vertex.AlbaneseAndersen;

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
 * <i>CPGACollateralized</i> illustrates the Counter Party Aggregation over Netting Groups based
 * 	Collateralized Collateral Groups with several Fix-Float Swaps. The References are:
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
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/xvadigest/README.md">Basel XVA Accounting Metrics Digest</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class CPGACollateralized {

	private static final double[] ATMSwapRateOffsetRealization (
		final DiffusionEvolver deATMSwapRateOffset,
		final double dblATMSwapRateOffsetInitial,
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
				SequenceGenerator.Gaussian (iNumStep)
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
		final int iNumStep,
		final double dblTime,
		final double dblTimeWidth,
		final int iNumSwap)
		throws Exception
	{
		double[] adblSwapPortfolioValueRealization = new double[iNumStep + 1];

		for (int i = 0; i < iNumStep; ++i)
			adblSwapPortfolioValueRealization[i] = 0.;

		for (int i = 0; i < iNumSwap; ++i) {
			double[] adblATMSwapRateOffsetRealization = ATMSwapRateOffsetRealization (
				deATMSwapRate,
				dblATMSwapRateStart,
				dblTime,
				dblTimeWidth,
				iNumStep
			);

			for (int j = 0; j <= iNumStep; ++j)
				adblSwapPortfolioValueRealization[j] += dblTimeWidth * (iNumStep - j) * adblATMSwapRateOffsetRealization[j];
		}

		return adblSwapPortfolioValueRealization;
	}

	private static final double[][] SwapPortfolioValueRealization (
		final DiffusionEvolver deATMSwapRate,
		final double dblSwapPortfolioValueStart,
		final int iNumStep,
		final double dblTime,
		final double dblTimeWidth,
		final int iNumSwap,
		final int iNumSimulation)
		throws Exception
	{
		double[][] aadblSwapPortfolioValueRealization = new double[iNumSimulation][];

		for (int i = 0; i < iNumSimulation; ++i)
			aadblSwapPortfolioValueRealization[i] = SwapPortfolioValueRealization (
				deATMSwapRate,
				dblSwapPortfolioValueStart,
				iNumStep,
				dblTime,
				dblTimeWidth,
				iNumSwap
			);

		return aadblSwapPortfolioValueRealization;
	}

	private static final void UDTDump (
		final String strHeader,
		final JulianDate[] adtVertexNode,
		final UnivariateDiscreteThin[] aUDT)
		throws Exception
	{
		System.out.println ("\t|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|");

		System.out.println (strHeader);

		System.out.println ("\t|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|");

		String strDump = "\t|       DATE      =>" ;

		for (int i = 0; i < adtVertexNode.length; ++i)
			strDump = strDump + " " + adtVertexNode[i] + "  |";

		System.out.println (strDump);

		System.out.println ("\t|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|");

		 strDump = "\t|     AVERAGE     =>";

		for (int j = 0; j < aUDT.length; ++j)
			strDump = strDump + "   " + FormatUtil.FormatDouble (aUDT[j].average(), 2, 4, 1.) + "   |";

		System.out.println (strDump);

		strDump = "\t|     MAXIMUM     =>";

		for (int j = 0; j < aUDT.length; ++j)
			strDump = strDump + "   " + FormatUtil.FormatDouble (aUDT[j].maximum(), 2, 4, 1.) + "   |";

		System.out.println (strDump);

		strDump = "\t|     MINIMUM     =>";

		for (int j = 0; j < aUDT.length; ++j)
			strDump = strDump + "   " + FormatUtil.FormatDouble (aUDT[j].minimum(), 2, 4, 1.) + "   |";

		System.out.println (strDump);

		strDump = "\t|      ERROR      =>";

		for (int j = 0; j < aUDT.length; ++j)
			strDump = strDump + "   " + FormatUtil.FormatDouble (aUDT[j].error(), 2, 4, 1.) + "   |";

		System.out.println (strDump);

		System.out.println ("\t|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|");
	}

	private static final void UDTDump (
		final String strHeader,
		final UnivariateDiscreteThin udt)
		throws Exception
	{
		System.out.println (
			strHeader +
			FormatUtil.FormatDouble (udt.average(), 3, 2, 100.) + "% | " +
			FormatUtil.FormatDouble (udt.maximum(), 3, 2, 100.) + "% | " +
			FormatUtil.FormatDouble (udt.minimum(), 3, 2, 100.) + "% | " +
			FormatUtil.FormatDouble (udt.error(), 3, 2, 100.) + "% ||"
		);
	}

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		int iNumStep = 10;
		int iNumSwap = 10;
		double dblTime = 5.;
		int iNumPath = 10000;
		double dblATMSwapRateStart = 0.;
		double dblATMSwapRateDrift = 0.0;
		double dblATMSwapRateVolatility = 0.25;
		double dblOvernightNumeraireDrift = 0.004;
		double dblCSADrift = 0.01;
		double dblBankHazardRate = 0.015;
		double dblBankRecoveryRate = 0.40;
		double dblCounterPartyHazardRate = 0.030;
		double dblCounterPartyRecoveryRate = 0.30;
		double dblBankThreshold = -0.1;
		double dblCounterPartyThreshold = 0.1;

		JulianDate dtSpot = DateUtil.Today();

		double dblTimeWidth = dblTime / iNumStep;
		MarketVertex[] aMV = new MarketVertex[iNumStep + 1];
		JulianDate[] adtVertex = new JulianDate[iNumStep + 1];
		double dblBankFundingSpread = dblBankHazardRate / (1. - dblBankRecoveryRate);
		MonoPathExposureAdjustment[] aMPEA = new MonoPathExposureAdjustment[iNumPath];
		double dblCounterPartyFundingSpread = dblCounterPartyHazardRate / (1. - dblCounterPartyRecoveryRate);

		PositionGroupSpecification positionGroupSpecification = PositionGroupSpecification.FixedThreshold (
			"FIXEDTHRESHOLD",
			dblCounterPartyThreshold,
			dblBankThreshold,
			PositionReplicationScheme.ALBANESE_ANDERSEN_VERTEX,
			BrokenDateScheme.LINEAR_TIME,
			0.,
			CloseOutScheme.ISDA_92
		);

		double[][] aadblSwapPortfolioValueRealization = SwapPortfolioValueRealization (
			new DiffusionEvolver (
				DiffusionEvaluatorLinear.Standard (
					dblATMSwapRateDrift,
					dblATMSwapRateVolatility
				)
			),
			dblATMSwapRateStart,
			iNumStep,
			dblTime,
			dblTimeWidth,
			iNumSwap,
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
				dblOvernightNumeraireDrift,
				Math.exp (-0.5 * dblOvernightNumeraireDrift * (iNumStep - i)),
				dblCSADrift,
				Math.exp (-0.5 * dblCSADrift * (iNumStep - i)),
				new MarketVertexEntity (
					Math.exp (-0.5 * dblBankHazardRate * i),
					dblBankHazardRate,
					dblBankRecoveryRate,
					dblBankFundingSpread,
					Math.exp (-0.5 * dblBankHazardRate * (1. - dblBankRecoveryRate) * (iNumStep - i)),
					Double.NaN,
					Double.NaN,
					Double.NaN
				),
				new MarketVertexEntity (
					Math.exp (-0.5 * dblCounterPartyHazardRate * i),
					dblCounterPartyHazardRate,
					dblCounterPartyRecoveryRate,
					dblCounterPartyFundingSpread,
					Math.exp (-0.5 * dblCounterPartyHazardRate * (1. - dblCounterPartyRecoveryRate) * (iNumStep - i)),
					Double.NaN,
					Double.NaN,
					Double.NaN
				),
				latentStateVertexContainer
			);
		}

		MarketPath mp = MarketPath.FromMarketVertexArray (aMV);

		for (int i = 0; i < iNumPath; ++i) {
			JulianDate dtStart = dtSpot;
			double dblValueStart = dblTime * dblATMSwapRateStart;
			AlbaneseAndersen[] aHGVR = new AlbaneseAndersen[iNumStep + 1];

			for (int j = 0; j <= iNumStep; ++j) {
				JulianDate dtEnd = adtVertex[j];
				double dblCollateralBalance = 0.;
				double dblValueEnd = aadblSwapPortfolioValueRealization[i][j];

				if (0 != j) {
					CollateralAmountEstimator hae = new CollateralAmountEstimator (
						positionGroupSpecification,
						new BrokenDateInterpolatorLinearT (
							dtStart.julian(),
							dtEnd.julian(),
							dblValueStart,
							dblValueEnd
						),
						Double.NaN
					);

					dblCollateralBalance = hae.postingRequirement (dtEnd);
				}

				aHGVR[j] = new AlbaneseAndersen (
					adtVertex[j],
					aadblSwapPortfolioValueRealization[i][j],
					0.,
					dblCollateralBalance
				);

				dtStart = dtEnd;
				dblValueStart = dblValueEnd;
			}

			CollateralGroupPath[] aHGP = new CollateralGroupPath[] {
				new CollateralGroupPath (
					aHGVR,
					mp
				)
			};

			aMPEA[i] = new MonoPathExposureAdjustment (
				new AlbaneseAndersenFundingGroupPath[] {
					new AlbaneseAndersenFundingGroupPath (
						new AlbaneseAndersenNettingGroupPath[] {
							new AlbaneseAndersenNettingGroupPath (
								aHGP,
								mp
							)
						},
						mp
					)
				}
			);
		}

		ExposureAdjustmentAggregator eaa = new ExposureAdjustmentAggregator (aMPEA);

		ExposureAdjustmentDigest ead = eaa.digest();

		System.out.println();

		UDTDump (
			"\t|                                                                                COLLATERALIZED EXPOSURE                                                                                |",
			eaa.vertexDates(),
			ead.collateralizedExposure()
		);

		UDTDump (
			"\t|                                                                               UNCOLLATERALIZED EXPOSURE                                                                               |",
			eaa.vertexDates(),
			ead.uncollateralizedExposure()
		);

		UDTDump (
			"\t|                                                                                COLLATERALIZED EXPOSURE PV                                                                             |",
			eaa.vertexDates(),
			ead.collateralizedExposurePV()
		);

		UDTDump (
			"\t|                                                                               UNCOLLATERALIZED EXPOSURE PV                                                                            |",
			eaa.vertexDates(),
			ead.uncollateralizedExposurePV()
		);

		UDTDump (
			"\t|                                                                            COLLATERALIZED POSITIVE EXPOSURE PV                                                                        |",
			eaa.vertexDates(),
			ead.collateralizedPositiveExposure()
		);

		UDTDump (
			"\t|                                                                           UNCOLLATERALIZED POSITIVE EXPOSURE PV                                                                       |",
			eaa.vertexDates(),
			ead.uncollateralizedPositiveExposure()
		);

		UDTDump (
			"\t|                                                                            COLLATERALIZED NEGATIVE EXPOSURE PV                                                                        |",
			eaa.vertexDates(),
			ead.collateralizedNegativeExposure()
		);

		UDTDump (
			"\t|                                                                           UNCOLLATERALIZED NEGATIVE EXPOSURE PV                                                                       |",
			eaa.vertexDates(),
			ead.uncollateralizedNegativeExposure()
		);

		System.out.println();

		System.out.println ("\t||-----------------------------------------------------||");

		System.out.println ("\t||  UCVA CVA FTDCVA DVA FCA UNIVARIATE THIN STATISTICS ||");

		System.out.println ("\t||-----------------------------------------------------||");

		System.out.println ("\t||    L -> R:                                          ||");

		System.out.println ("\t||            - Path Average                           ||");

		System.out.println ("\t||            - Path Maximum                           ||");

		System.out.println ("\t||            - Path Minimum                           ||");

		System.out.println ("\t||            - Monte Carlo Error                      ||");

		System.out.println ("\t||-----------------------------------------------------||");

		UDTDump (
			"\t||  UCVA  => ",
			ead.ucva()
		);

		UDTDump (
			"\t|| FTDCVA => ",
			ead.ftdcva()
		);

		UDTDump (
			"\t||   CVA  => ",
			ead.cva()
		);

		UDTDump (
			"\t||  CVACL => ",
			ead.cvacl()
		);

		UDTDump (
			"\t||   DVA  => ",
			ead.dva()
		);

		UDTDump (
			"\t||   FVA  => ",
			ead.fva()
		);

		UDTDump (
			"\t||   FDA  => ",
			ead.fda()
		);

		UDTDump (
			"\t||   FCA  => ",
			ead.fca()
		);

		UDTDump (
			"\t||   FBA  => ",
			ead.fba()
		);

		UDTDump (
			"\t||  SFVA  => ",
			ead.sfva()
		);

		System.out.println ("\t||-----------------------------------------------------||");

		UDTDump (
			"\t||  Total => ",
			ead.totalVA()
		);

		System.out.println ("\t||-----------------------------------------------------||");

		System.out.println();

		EnvManager.TerminateEnv();
	}
}
