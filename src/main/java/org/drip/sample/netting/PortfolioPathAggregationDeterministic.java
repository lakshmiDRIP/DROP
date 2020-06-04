
package org.drip.sample.netting;

import org.drip.analytics.date.*;
import org.drip.exposure.evolver.LatentStateVertexContainer;
import org.drip.exposure.universe.*;
import org.drip.measure.discrete.SequenceGenerator;
import org.drip.measure.dynamics.DiffusionEvaluatorLogarithmic;
import org.drip.measure.process.DiffusionEvolver;
import org.drip.measure.realization.*;
import org.drip.service.common.FormatUtil;
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
 * <i>PortfolioPathAggregationDeterministic</i> generates an Aggregation of the Portfolio Paths evolved using
 * 	Deterministic Market Parameters. The References are:
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
 *  <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/XVAAnalyticsLibrary.md">XVA Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/netting/README.md">Netting Portfolio Group Simulation Aggregation</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class PortfolioPathAggregationDeterministic {

	private static final double[][] CollateralPortfolioValueRealization (
		final DiffusionEvolver deCollateralPortfolioValue,
		final double dblCollateralPortfolioValueInitial,
		final double dblTime,
		final double dblTimeWidth,
		final int iNumStep,
		final int iNumPath)
		throws Exception
	{
		double[][] aablCollateralPortfolioValue = new double[iNumPath][iNumStep + 1];
		double[] adblTimeWidth = new double[iNumStep];

		for (int i = 0; i < iNumStep; ++i)
			adblTimeWidth[i] = dblTimeWidth;

		for (int i = 0; i < iNumPath; ++i) {
			JumpDiffusionEdge[] aJDE = deCollateralPortfolioValue.incrementSequence (
				new JumpDiffusionVertex (
					dblTime,
					dblCollateralPortfolioValueInitial,
					0.,
					false
				),
				JumpDiffusionEdgeUnit.Diffusion (
					adblTimeWidth,
					SequenceGenerator.Gaussian (iNumStep)
				),
				dblTimeWidth
			);

			aablCollateralPortfolioValue[i][0] = dblCollateralPortfolioValueInitial;

			for (int j = 1; j <= iNumStep; ++j)
				aablCollateralPortfolioValue[i][j] = aJDE[j - 1].finish();
		}

		return aablCollateralPortfolioValue;
	}

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		int iNumStep = 10;
		double dblTime = 5.;
		int iNumPath = 50000;
		double dblCollateralPortfolioValueDrift = 0.06;
		double dblCollateralPortfolioValueVolatility = 0.15;
		double dblCollateralPortfolioValueInitial = 1.;
		double dblOvernightNumeraireDrift = 0.004;
		double dblCSADrift = 0.01;
		double dblBankHazardRate = 0.015;
		double dblBankRecoveryRate = 0.40;
		double dblCounterPartyHazardRate = 0.030;
		double dblCounterPartyRecoveryRate = 0.30;

		double dblTimeWidth = dblTime / iNumStep;
		MarketVertex[] aMV = new MarketVertex[iNumStep + 1];
		JulianDate[] adtVertex = new JulianDate[iNumStep + 1];
		double[][] aadblCollateralBalance = new double[iNumPath][iNumStep + 1];
		double dblBankFundingSpread = dblBankHazardRate / (1. - dblBankRecoveryRate);
		MonoPathExposureAdjustment[] aMPEA = new MonoPathExposureAdjustment[iNumPath];
		double dblCounterPartyFundingSpread = dblCounterPartyHazardRate / (1. - dblCounterPartyRecoveryRate);

		JulianDate dtSpot = DateUtil.Today();

		double[][] aadblCollateralPortfolioValue = CollateralPortfolioValueRealization (
			new DiffusionEvolver (
				DiffusionEvaluatorLogarithmic.Standard (
					dblCollateralPortfolioValueDrift,
					dblCollateralPortfolioValueVolatility
				)
			),
			dblCollateralPortfolioValueInitial,
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

			for (int j = 0; j < iNumPath; ++j)
				aadblCollateralBalance[j][i] = 0.;
		}

		MarketPath mp = MarketPath.FromMarketVertexArray (aMV);

		for (int i = 0; i < iNumPath; ++i) {
			AlbaneseAndersen[] aHGVR = new AlbaneseAndersen[iNumStep + 1];

			for (int j = 0; j <= iNumStep; ++j) {
				aHGVR[j] = new AlbaneseAndersen (
					adtVertex[j],
					aadblCollateralPortfolioValue[i][j],
					0.,
					0.
				);
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

		JulianDate[] adtVertexNode = eaa.vertexDates();

		System.out.println();

		System.out.println ("\t|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|");

		String strDump = "\t|         DATE         =>" ;

		for (int i = 0; i < adtVertexNode.length; ++i)
			strDump = strDump + " " + adtVertexNode[i] + " |";

		System.out.println (strDump);

		System.out.println ("\t|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|");

		double[] adblExposure = eaa.collateralizedExposure();

		strDump = "\t|       EXPOSURE       =>";

		for (int j = 0; j < adblExposure.length; ++j)
			strDump = strDump + "   " + FormatUtil.FormatDouble (adblExposure[j], 1, 4, 1.) + "   |";

		System.out.println (strDump);

		double[] adblPositiveExposure = eaa.collateralizedPositiveExposure();

		strDump = "\t|  POSITIVE EXPOSURE   =>";

		for (int j = 0; j < adblPositiveExposure.length; ++j)
			strDump = strDump + "   " + FormatUtil.FormatDouble (adblPositiveExposure[j], 1, 4, 1.) + "   |";

		System.out.println (strDump);

		double[] adblNegativeExposure = eaa.collateralizedNegativeExposure();

		strDump = "\t|  NEGATIVE EXPOSURE   =>";

		for (int j = 0; j < adblNegativeExposure.length; ++j)
			strDump = strDump + "   " + FormatUtil.FormatDouble (adblNegativeExposure[j], 1, 4, 1.) + "   |";

		System.out.println (strDump);

		double[] adblExposurePV = eaa.collateralizedExposurePV();

		strDump = "\t|      EXPOSURE PV     =>";

		for (int j = 0; j < adblExposurePV.length; ++j)
			strDump = strDump + "   " + FormatUtil.FormatDouble (adblExposurePV[j], 1, 4, 1.) + "   |";

		System.out.println (strDump);

		double[] adblPositiveExposurePV = eaa.collateralizedPositiveExposurePV();

		strDump = "\t| POSITIVE EXPOSURE PV =>";

		for (int j = 0; j < adblPositiveExposurePV.length; ++j)
			strDump = strDump + "   " + FormatUtil.FormatDouble (adblPositiveExposurePV[j], 1, 4, 1.) + "   |";

		System.out.println (strDump);

		double[] adblNegativeExposurePV = eaa.collateralizedNegativeExposurePV();

		strDump = "\t| NEGATIVE EXPOSURE PV =>";

		for (int j = 0; j < adblNegativeExposurePV.length; ++j)
			strDump = strDump + "   " + FormatUtil.FormatDouble (adblNegativeExposurePV[j], 1, 4, 1.) + "   |";

		System.out.println (strDump);

		System.out.println ("\t|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|");

		System.out.println();

		System.out.println ("\t||-------------------||");

		System.out.println ("\t||  UCVA  => " + FormatUtil.FormatDouble (eaa.ucva().amount(), 2, 2, 100.) + "% ||");

		System.out.println ("\t|| FTDCVA => " + FormatUtil.FormatDouble (eaa.ftdcva().amount(), 2, 2, 100.) + "% ||");

		System.out.println ("\t||  CVA   => " + FormatUtil.FormatDouble (eaa.cva().amount(), 2, 2, 100.) + "% ||");

		System.out.println ("\t||  CVACL => " + FormatUtil.FormatDouble (eaa.cvacl().amount(), 2, 2, 100.) + "% ||");

		System.out.println ("\t||  DVA   => " + FormatUtil.FormatDouble (eaa.dva().amount(), 2, 2, 100.) + "% ||");

		System.out.println ("\t||  FVA   => " + FormatUtil.FormatDouble (eaa.fva().amount(), 2, 2, 100.) + "% ||");

		System.out.println ("\t||  FDA   => " + FormatUtil.FormatDouble (eaa.fda().amount(), 2, 2, 100.) + "% ||");

		System.out.println ("\t||  FCA   => " + FormatUtil.FormatDouble (eaa.fca().amount(), 2, 2, 100.) + "% ||");

		System.out.println ("\t||  FBA   => " + FormatUtil.FormatDouble (eaa.fba().amount(), 2, 2, 100.) + "% ||");

		System.out.println ("\t||  SFVA  => " + FormatUtil.FormatDouble (eaa.sfva().amount(), 2, 2, 100.) + "% ||");

		System.out.println ("\t||-------------------||");

		EnvManager.TerminateEnv();
	}
}
