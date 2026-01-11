
package org.drip.sample.numeraire;

import org.drip.measure.crng.RandomSequenceGenerator;
import org.drip.measure.dynamics.*;
import org.drip.measure.realization.*;
import org.drip.numerical.linearalgebra.R1MatrixUtil;
import org.drip.service.common.FormatUtil;
import org.drip.service.env.EnvManager;

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
 * <i>R1JointJumpDiffusion</i> demonstrates the Joint Evolution of R<sup>1</sup> Jump Diffusion Variates -
 * 	the Continuous Asset, the Collateral, the Bank, and the Counter-Party Numeraires involved in the Dynamic
 * 	XVA Replication Portfolio of the Burgard and Kjaer (2011) Methodology. The References are:
 *  
 * <br><br>
 *  <ul>
 *  	<li>
 *  		Burgard, C., and M. Kjaer (2014): PDE Representations of Derivatives with Bilateral Counter-party
 *  			Risk and Funding Costs <i>Journal of Credit Risk</i> <b>7 (3)</b> 1-19
 *  	</li>
 *  	<li>
 *  		Cesari, G., J. Aquilina, N. Charpillon, X. Filipovic, G. Lee, and L. Manda (2009): <i>Modeling,
 *  			Pricing, and Hedging Counter-party Credit Exposure - A Technical Guide</i> <b>Springer
 *  			Finance</b> New York
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
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/numeraire/README.md">R<sup>1</sup> Joint Jump Diffusion Numeraire</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class R1JointJumpDiffusion {

	private static final double[][] NumeraireSequence (
		final int iCount,
		final double[][] aadblCorrelation,
		final String strHeader)
		throws Exception
	{
		double[][] aadblGaussianJoint = RandomSequenceGenerator.GaussianJoint (
			iCount,
			aadblCorrelation
		);

		System.out.println();

		System.out.println ("\t||----------------------------------------------------||");

		System.out.println (strHeader);

		System.out.println ("\t||----------------------------------------------------||");

		for (int i = 0; i < iCount; ++i) {
			String strDump = "\t||" + FormatUtil.FormatDouble (i, 2, 0, 1.) + " |";

			for (int j = 0; j < aadblCorrelation.length; ++j)
				strDump = strDump + " " + FormatUtil.FormatDouble (aadblGaussianJoint[i][j], 1, 6, 1.) + " |";

			System.out.println (strDump + "|");
		}

		System.out.println ("\t||----------------------------------------------------||");

		System.out.println();

		return R1MatrixUtil.Transpose (aadblGaussianJoint);
	}

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

		double dblTimeWidth = 1. / 24.;
		double dblTime = 0.;
		double[][] aadblCorrelation = new double[][] {
			{1.00, 0.20, 0.15, 0.05}, // #0 ASSET
			{0.20, 1.00, 0.13, 0.25}, // #1 COLLATERAL
			{0.15, 0.13, 1.00, 0.00}, // #2 BANK
			{0.05, 0.25, 0.00, 1.00}  // #3 COUNTER PARTY
		};
		double dblAssetDrift = 0.06;
		double dblAssetVolatility = 0.15;
		double dblInitialAssetNumeraire = 1.;

		double dblZeroCouponBankBondDrift = 0.03;
		double dblZeroCouponBankBondVolatility = 0.10;
		double dblBankHazardRate = 0.03;
		double dblBankRecoveryRate = 0.45;
		double dblInitialBankNumeraire = 1.;

		double dblZeroCouponCollateralBondDrift = 0.01;
		double dblZeroCouponCollateralBondVolatility = 0.05;
		double dblInitialCollateralNumeraire = 1.;

		double dblZeroCouponCounterPartyBondDrift = 0.03;
		double dblZeroCouponCounterPartyBondVolatility = 0.10;
		double dblCounterPartyHazardRate = 0.05;
		double dblCounterPartyRecoveryRate = 0.30;
		double dblInitialCounterPartyNumeraire = 1.;

		int iNumTimeStep = (int) (1. / dblTimeWidth);

		DiffusionEvolver meAsset = new DiffusionEvolver (
			DiffusionEvaluatorLogarithmic.Standard (
				dblAssetDrift,
				dblAssetVolatility
			)
		);

		DiffusionEvolver meZeroCouponCollateralBond = new DiffusionEvolver (
			DiffusionEvaluatorLogarithmic.Standard (
				dblZeroCouponCollateralBondDrift,
				dblZeroCouponCollateralBondVolatility
			)
		);

		JumpDiffusionEvolver meZeroCouponBankBond = new JumpDiffusionEvolver (
			DiffusionEvaluatorLogarithmic.Standard (
				dblZeroCouponBankBondDrift,
				dblZeroCouponBankBondVolatility
			),
			HazardJumpEvaluator.Standard (
				dblBankHazardRate,
				dblBankRecoveryRate
			)
		);

		JumpDiffusionEvolver meZeroCouponCounterPartyBond = new JumpDiffusionEvolver (
			DiffusionEvaluatorLogarithmic.Standard (
				dblZeroCouponCounterPartyBondDrift,
				dblZeroCouponCounterPartyBondVolatility
			),
			HazardJumpEvaluator.Standard (
				dblCounterPartyHazardRate,
				dblCounterPartyRecoveryRate
			)
		);

		double[][] aadblNumeraireTimeSeries = NumeraireSequence (
			iNumTimeStep,
			aadblCorrelation,
			"\t|| ASSET, COLLATERAL, BANK, COUNTER PARTY REALIZATION ||"
		);

		double[] adblBankDefaultIndicator = RandomSequenceGenerator.Uniform (iNumTimeStep);

		double[] adblCounterPartyDefaultIndicator = RandomSequenceGenerator.Uniform (iNumTimeStep);

		double[] adblTimeWidth = new double[iNumTimeStep];

		for (int i = 0; i < iNumTimeStep; ++i)
			adblTimeWidth[i] = dblTimeWidth;

		JumpDiffusionEdge[] aR1AssetLR = meAsset.incrementSequence (
			new JumpDiffusionVertex (
				dblTime,
				dblInitialAssetNumeraire,
				0.,
				false
			),
			JumpDiffusionEdgeUnit.Diffusion (
				adblTimeWidth,
				aadblNumeraireTimeSeries[0]
			),
			dblTimeWidth
		);

		JumpDiffusionEdge[] aR1CollateralLR = meZeroCouponCollateralBond.incrementSequence (
			new JumpDiffusionVertex (
				dblTime,
				dblInitialCollateralNumeraire,
				0.,
				false
			),
			JumpDiffusionEdgeUnit.Diffusion (
				adblTimeWidth,
				aadblNumeraireTimeSeries[1]
			),
			dblTimeWidth
		);

		JumpDiffusionEdge[] aR1BankLR = meZeroCouponBankBond.incrementSequence (
			new JumpDiffusionVertex (
				dblTime,
				dblInitialBankNumeraire,
				0.,
				false
			),
			JumpDiffusionEdgeUnit.JumpDiffusion (
				adblTimeWidth,
				aadblNumeraireTimeSeries[2],
				adblBankDefaultIndicator
			),
			dblTimeWidth
		);

		JumpDiffusionEdge[] aR1CounterPartyLR = meZeroCouponCounterPartyBond.incrementSequence (
			new JumpDiffusionVertex (
				dblTime,
				dblInitialCounterPartyNumeraire,
				0.,
				false
			),
			JumpDiffusionEdgeUnit.JumpDiffusion (
				adblTimeWidth,
				aadblNumeraireTimeSeries[3],
				adblCounterPartyDefaultIndicator
			),
			dblTimeWidth
		);

		System.out.println();

		System.out.println("\t||----------------------------------------------------------------------------------------------------------------------------------||");

		System.out.println("\t||                  BURGARD & KJAER (2011) CORRELATED JOINT ASSET/COLLATERAL/BANK/COUNTER-PARTY NUMERAIRE EVOLUTION                 ||");

		System.out.println("\t||----------------------------------------------------------------------------------------------------------------------------------||");

		System.out.println("\t||    L -> R:                                                                                                                       ||");

		System.out.println("\t||          - Time                                                                                                                  ||");

		System.out.println("\t||          - Asset Numeraire Finish Value                                                                                          ||");

		System.out.println("\t||          - Asset Numeraire Start Value                                                                                           ||");

		System.out.println("\t||          - Asset Numeraire Continuous Wander Realization                                                                         ||");

		System.out.println("\t||          - Collateral Numeraire Finish Value                                                                                     ||");

		System.out.println("\t||          - Collateral Numeraire Start Value                                                                                      ||");

		System.out.println("\t||          - Collateral Numeraire Continuous Wander Realization                                                                    ||");

		System.out.println("\t||          - Bank Numeraire Finish Value                                                                                           ||");

		System.out.println("\t||          - Bank Numeraire Start Value                                                                                            ||");

		System.out.println("\t||          - Bank Numeraire Continuous Wander Realization                                                                          ||");

		System.out.println("\t||          - Counter-Party Numeraire Finish Value                                                                                  ||");

		System.out.println("\t||          - Counter-Party Numeraire Start Value                                                                                   ||");

		System.out.println("\t||          - Counter-Party Numeraire Continuous Wander Realization                                                                 ||");

		System.out.println("\t||----------------------------------------------------------------------------------------------------------------------------------||");

		for (int i = 0; i < iNumTimeStep; ++i) {
			dblTime = dblTime + dblTimeWidth;

			System.out.println (
				"\t|| " +
				FormatUtil.FormatDouble (dblTime, 1, 4, 1.) + " => " +
				FormatUtil.FormatDouble (aR1AssetLR[i].start(), 1, 4, 1.) + " | " +
				FormatUtil.FormatDouble (aR1AssetLR[i].finish(), 1, 4, 1.) + " | " +
				FormatUtil.FormatDouble (aR1AssetLR[i].diffusionWander(), 1, 4, 1.) + " ||" +
				FormatUtil.FormatDouble (aR1CollateralLR[i].start(), 1, 4, 1.) + " | " +
				FormatUtil.FormatDouble (aR1CollateralLR[i].finish(), 1, 4, 1.) + " | " +
				FormatUtil.FormatDouble (aR1CollateralLR[i].diffusionWander(), 1, 4, 1.) + " ||" +
				FormatUtil.FormatDouble (aR1BankLR[i].start(), 1, 4, 1.) + " | " +
				FormatUtil.FormatDouble (aR1BankLR[i].finish(), 1, 4, 1.) + " | " +
				FormatUtil.FormatDouble (aR1BankLR[i].diffusionWander(), 1, 4, 1.) + " ||" +
				FormatUtil.FormatDouble (aR1CounterPartyLR[i].start(), 1, 4, 1.) + " | " +
				FormatUtil.FormatDouble (aR1CounterPartyLR[i].finish(), 1, 4, 1.) + " | " +
				FormatUtil.FormatDouble (aR1CounterPartyLR[i].diffusionWander(), 1, 4, 1.) + " ||"
			);
		}

		System.out.println("\t||----------------------------------------------------------------------------------------------------------------------------------||");

		System.out.println();

		EnvManager.TerminateEnv();
	}
}
