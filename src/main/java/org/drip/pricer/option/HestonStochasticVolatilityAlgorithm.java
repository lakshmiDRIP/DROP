
package org.drip.pricer.option;

import java.util.Map;
import java.util.TreeMap;

import org.drip.numerical.common.NumberUtil;
import org.drip.numerical.complex.C1Util;
import org.drip.numerical.complex.C1Cartesian;
import org.drip.numerical.fourier.PhaseAdjuster;
import org.drip.numerical.fourier.RotationCountPhaseTracker;
import org.drip.param.pricer.HestonOptionPricerParams;

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
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
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
 * <i>HestonStochasticVolatilityAlgorithm</i> implements the Heston 1993 Stochastic Volatility European Call
 * and Put Options Pricer.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/pricer/README.md">Custom Pricing Algorithms and the Derivative Fokker Planck Trajectory Generators</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/pricer/option/README.md">Deterministic/Stochastic Volatility Settings/Greeks</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class HestonStochasticVolatilityAlgorithm extends org.drip.pricer.option.FokkerPlanckGenerator {

	/**
	 * Payoff Transformation Type - The Original Heston 1993 Scheme
	 */

	public static final int PAYOFF_TRANSFORM_SCHEME_HESTON_1993 = 1;

	/**
	 * Payoff Transformation Type - The Albrecher, Mayer, Schoutens, and Tistaert Scheme
	 */

	public static final int PAYOFF_TRANSFORM_SCHEME_AMST_2007 = 1;

	private static final double FOURIER_FREQ_INIT = 0.01;
	private static final double FOURIER_FREQ_INCREMENT = 0.1;
	private static final double FOURIER_FREQ_FINAL = 25.;

	private HestonOptionPricerParams _fphp = null;

	class PhaseCorrectedF {
		double _dblCorrectedPhase = Double.NaN;
		C1Cartesian _cnF = null;

		PhaseCorrectedF (
			final double dblCorrectedPhase,
			final C1Cartesian cnF)
		{
			_cnF = cnF;
			_dblCorrectedPhase = dblCorrectedPhase;
		}
	}

	private PhaseCorrectedF fourierTransformHeston93 (
		final double dblStrike,
		final double dbTimeToExpiry,
		final double dblRiskFreeRate,
		final double dblSpot,
		final double dblInitialVolatility,
		final double dblA,
		final double dblFreq,
		final double dblB,
		final double dblU,
		final RotationCountPhaseTracker rcpt)
	{
		try {
			C1Cartesian cnSmallDLHS = new C1Cartesian (dblB, -1. * _fphp.rho() * _fphp.sigma() * dblFreq);

			C1Cartesian d = C1Util.Square (cnSmallDLHS);

			if (null == d) return null;

			double dblSigmaScaler = _fphp.sigma() * _fphp.sigma();

			if (null == (d = C1Util.Add (d, new
				C1Cartesian (dblSigmaScaler * dblFreq * dblFreq, -2. * dblSigmaScaler * dblFreq * dblU))))
				return null;

			if (null == (d = C1Util.SquareRoot (d))) return null;

			C1Cartesian gNumerator = C1Util.Subtract (cnSmallDLHS, d);

			if (null == gNumerator) return null;

			C1Cartesian g = C1Util.Add (cnSmallDLHS, d);

			if (null == g) return null;

			if (null == (g = C1Util.Divide (gNumerator, g))) return null;

			int iM = 0;
			int iN = 0;

			if (PhaseAdjuster.MULTI_VALUE_BRANCH_POWER_PHASE_TRACKER_KAHL_JACKEL == _fphp.phaseTrackerType())
			{
				iM = (int) ((g.argument() + Math.PI) / (2. * Math.PI));

				iN = (int) ((g.argument() + (dbTimeToExpiry * d.argument()) + Math.PI) / (2. * Math.PI));
			}

			C1Cartesian cnExpTTEScaledSmallD = C1Util.Scale (d, -1. * dbTimeToExpiry);

			if (null == cnExpTTEScaledSmallD) return null;

			if (null == (cnExpTTEScaledSmallD = C1Util.Exponentiate (cnExpTTEScaledSmallD)))
				return null;

			C1Cartesian cnD = new C1Cartesian (
				1. - cnExpTTEScaledSmallD.real(),
				-1. * cnExpTTEScaledSmallD.imaginary()
			);

			C1Cartesian cnInvGExpTTEScaledSmallD = C1Util.Multiply (cnExpTTEScaledSmallD, g);

			if (null == cnInvGExpTTEScaledSmallD) return null;

			cnInvGExpTTEScaledSmallD = new C1Cartesian (1. -
				cnInvGExpTTEScaledSmallD.real(),
				-1. * cnInvGExpTTEScaledSmallD.imaginary()
			);

			if (null == (cnD = C1Util.Divide (cnD, cnInvGExpTTEScaledSmallD)))
				return null;

			if (null == (cnD = C1Util.Multiply (gNumerator, cnD)))
				return null;

			dblSigmaScaler = 1. / dblSigmaScaler;

			if (null == (cnD = C1Util.Scale (cnD, dblSigmaScaler))) return null;

			C1Cartesian c = new C1Cartesian (1. - g.real(), -1. * g.imaginary());

			if (PhaseAdjuster.MULTI_VALUE_BRANCH_POWER_PHASE_TRACKER_KAHL_JACKEL == _fphp.phaseTrackerType())
			{
				if (null == (c = PhaseAdjuster.PowerLogPhaseTracker (cnInvGExpTTEScaledSmallD, c, iN, iM)))
					return null;
			} else if (PhaseAdjuster.MULTI_VALUE_BRANCH_PHASE_TRACKER_ROTATION_COUNT
				== _fphp.phaseTrackerType())
			{
				if (null == (c = C1Util.Logarithm (c))) return null;

				c = new C1Cartesian (c.real(), rcpt.updateAndApply (c.argument(), true));
			}

			double dblCorrectedPhase = c.argument();

			if (null == (c = C1Util.Scale (c, -2.))) return null;

			C1Cartesian cnTTEScaledGNumerator = C1Util.Scale (gNumerator, dbTimeToExpiry);

			if (null == cnTTEScaledGNumerator) return null;

			if (null == (c = C1Util.Add (cnTTEScaledGNumerator, c))) return null;

			if (null == (c = C1Util.Scale (c, dblA * dblSigmaScaler))) return null;

			if (null == (c = C1Util.Add (new
				C1Cartesian (0., dblRiskFreeRate * dbTimeToExpiry * dblFreq), c)))
				return null;

			C1Cartesian cnF = C1Util.Scale (cnD, dblInitialVolatility);

			if (null == cnF) return null;

			if (null == (cnF = C1Util.Add (cnF, new C1Cartesian (0., Math.log (dblSpot) * dblFreq))))
				return null;

			if (null == (cnF = C1Util.Add (cnF, c))) return null;

			if (null == (cnF = C1Util.Add (cnF, new C1Cartesian (0., -1. * Math.log (dblStrike) * dblFreq))))
				return null;

			if (null == (cnF = C1Util.Exponentiate (cnF))) return null;

			if (null == (cnF = C1Util.Divide (cnF, new C1Cartesian (0., dblFreq))))
				return null;

			return new PhaseCorrectedF (dblCorrectedPhase, cnF);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	private PhaseCorrectedF fourierTransformAMST07 (
		final double dblStrike,
		final double dbTimeToExpiry,
		final double dblRiskFreeRate,
		final double dblSpot,
		final double dblInitialVolatility,
		final double dblA,
		final double dblFreq,
		final double dblB,
		final double dblU,
		final org.drip.numerical.fourier.RotationCountPhaseTracker rcpt)
	{
		try {
			C1Cartesian cnSmallDLHS = new C1Cartesian (dblB, -1. * _fphp.rho() * _fphp.sigma() * dblFreq);

			C1Cartesian d1 = org.drip.numerical.complex.C1Util.Square (cnSmallDLHS);

			if (null == d1) return null;

			double dblSigmaScaler = _fphp.sigma() * _fphp.sigma();

			if (null == (d1 = C1Util.Add (d1, new
				C1Cartesian (dblSigmaScaler * dblFreq * dblFreq, -2. * dblSigmaScaler
					* dblFreq * dblU))))
				return null;

			if (null == (d1 = C1Util.SquareRoot (d1))) return null;

			C1Cartesian gNumerator = C1Util.Add (cnSmallDLHS, d1);

			if (null == gNumerator) return null;

			C1Cartesian g = C1Util.Subtract (cnSmallDLHS, d1);

			if (null == g) return null;

			if (null == (g = C1Util.Divide (gNumerator, g))) return null;

			int iM = 0;
			int iN = 0;

			if (org.drip.numerical.fourier.PhaseAdjuster.MULTI_VALUE_BRANCH_POWER_PHASE_TRACKER_KAHL_JACKEL ==
				_fphp.phaseTrackerType()) {
				iM = (int) ((g.argument() + java.lang.Math.PI) / (2. * java.lang.Math.PI));

				iN = (int) ((g.argument() + (dbTimeToExpiry * d1.argument()) + java.lang.Math.PI) /
					(2. * java.lang.Math.PI));
			}

			C1Cartesian cnExpTTEScaledSmallD = C1Util.Scale (d1, dbTimeToExpiry);

			if (null == cnExpTTEScaledSmallD) return null;

			if (null == (cnExpTTEScaledSmallD = C1Util.Exponentiate (cnExpTTEScaledSmallD)))
				return null;

			C1Cartesian d = new C1Cartesian (
				1. - cnExpTTEScaledSmallD.real(),
				-1. * cnExpTTEScaledSmallD.imaginary()
			);

			org.drip.numerical.complex.C1Cartesian cnInvGExpTTEScaledSmallD =
				org.drip.numerical.complex.C1Util.Multiply (g, cnExpTTEScaledSmallD);

			if (null == cnInvGExpTTEScaledSmallD) return null;

			cnInvGExpTTEScaledSmallD = new org.drip.numerical.complex.C1Cartesian (1. -
				cnInvGExpTTEScaledSmallD.real(), -1. * cnInvGExpTTEScaledSmallD.imaginary());

			if (null == (d = C1Util.Divide (d, cnInvGExpTTEScaledSmallD)))
				return null;

			if (null == (d = C1Util.Multiply (gNumerator, d)))
				return null;

			dblSigmaScaler = 1. / dblSigmaScaler;

			if (null == (d = C1Util.Scale (d, dblSigmaScaler))) return null;

			C1Cartesian c = new C1Cartesian (1. - g.real(), -1. * g.imaginary());

			if (PhaseAdjuster.MULTI_VALUE_BRANCH_POWER_PHASE_TRACKER_KAHL_JACKEL == _fphp.phaseTrackerType())
			{
				if (null == (c = PhaseAdjuster.PowerLogPhaseTracker (cnInvGExpTTEScaledSmallD, c, iN, iM)))
					return null;
			} else if (PhaseAdjuster.MULTI_VALUE_BRANCH_PHASE_TRACKER_ROTATION_COUNT
				== _fphp.phaseTrackerType()) {
				if (null == (c = C1Util.Logarithm (c))) return null;

				c = new C1Cartesian (c.real(), rcpt.updateAndApply (c.argument(), true));
			}

			double dblCorrectedPhase = c.argument();

			if (null == (c = C1Util.Scale (c, -2.))) return null;

			C1Cartesian tteScaledGNumerator = C1Util.Scale (gNumerator, dbTimeToExpiry);

			if (null == tteScaledGNumerator) return null;

			if (null == (c = C1Util.Add (tteScaledGNumerator, c)))
				return null;

			if (null == (c = C1Util.Scale (c, dblA * dblSigmaScaler)))
				return null;

			if (null == (c = C1Util.Add (new
				C1Cartesian (0., dblRiskFreeRate * dbTimeToExpiry * dblFreq), c)))
				return null;

			C1Cartesian f = C1Util.Scale (d, dblInitialVolatility);

			if (null == f) return null;

			if (null == (f = C1Util.Add (f, new C1Cartesian (0., Math.log (dblSpot) * dblFreq))))
				return null;

			if (null == (f = C1Util.Add (f, c))) return null;

			if (null == (f = C1Util.Add (f, new C1Cartesian (0., -1. * Math.log (dblStrike) * dblFreq))))
				return null;

			if (null == (f = C1Util.Exponentiate (f))) return null;

			if (null == (f = C1Util.Divide (f, new C1Cartesian (0., dblFreq))))
				return null;

			return new PhaseCorrectedF (dblCorrectedPhase, f);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	private PhaseCorrectedF payoffTransform (
		final double dblStrike,
		final double dbTimeToExpiry,
		final double dblRiskFreeRate,
		final double dblSpot,
		final double dblInitialVolatility,
		final double dblA,
		final double dblFreq,
		final double dblB,
		final double dblU,
		final RotationCountPhaseTracker rcpt)
	{
		if (PhaseAdjuster.MULTI_VALUE_BRANCH_PHASE_TRACKER_ROTATION_COUNT == _fphp.phaseTrackerType() &&
			null == rcpt)
		{
			return null;
		}

		if (PAYOFF_TRANSFORM_SCHEME_HESTON_1993 == _fphp.payoffTransformScheme())
			return fourierTransformHeston93 (dblStrike, dbTimeToExpiry, dblRiskFreeRate, dblSpot,
				dblInitialVolatility, dblA, dblFreq, dblB, dblU, rcpt);

		if (PAYOFF_TRANSFORM_SCHEME_AMST_2007 == _fphp.payoffTransformScheme())
			return fourierTransformAMST07 (dblStrike, dbTimeToExpiry, dblRiskFreeRate, dblSpot,
				dblInitialVolatility, dblA, dblFreq, dblB, dblU, rcpt);

		return null;
	}

	/**
	 * HestonStochasticVolatilityAlgorithm constructor
	 * 
	 * @param fphp The Heston Algorithm Parameters
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public HestonStochasticVolatilityAlgorithm (
		final HestonOptionPricerParams fphp)
		throws Exception
	{
		if (null == (_fphp = fphp))
			throw new Exception ("HestonStochasticVolatilityAlgorithm ctr: Invalid Inputs");
	}

	/**
	 * Record the Details of a Single Phase Adjustment Run
	 * 
	 * @param dblStrike Strike
	 * @param dbTimeToExpiry TTE
	 * @param dblRiskFreeRate Risk Free Rate
	 * @param dblSpot Spot
	 * @param dblInitialVolatility Initial Volatility
	 * @param bLeft TRUE - Phase Correction applied to Left
	 * 
	 * @return Map of the Phase Correction Record
	 */

	public java.util.Map<java.lang.Double, java.lang.Double> recordPhase (
		final double dblStrike,
		final double dbTimeToExpiry,
		final double dblRiskFreeRate,
		final double dblSpot,
		final double dblInitialVolatility,
		final boolean bLeft)
	{
		if (!NumberUtil.IsValid (dblStrike) ||!NumberUtil.IsValid (dblSpot) ||
			!NumberUtil.IsValid (dblInitialVolatility) || !NumberUtil.IsValid (dbTimeToExpiry) ||
			!NumberUtil.IsValid (dblRiskFreeRate))
			return null;

		int i = 0;
		double dblU1 = 0.5;
		double dblU2 = -0.5;
		double dblPreviousPhase = 0.;

		RotationCountPhaseTracker rcpt =
			PhaseAdjuster.MULTI_VALUE_BRANCH_PHASE_TRACKER_ROTATION_COUNT == _fphp.phaseTrackerType() ?
				new RotationCountPhaseTracker() : null;

		double dblA = _fphp.kappa() * _fphp.theta();

		double dblB2 = _fphp.kappa() + _fphp.lambda();

		double dblB1 = dblB2 - _fphp.rho() * _fphp.sigma();

		Map<Double, Double> mapPhaseRun = new TreeMap<Double, Double>();

		for (double dblFreq = FOURIER_FREQ_INIT; dblFreq <= FOURIER_FREQ_FINAL; dblFreq +=
			FOURIER_FREQ_INCREMENT, ++i) {
			PhaseCorrectedF pcf = bLeft ? payoffTransform (dblStrike, dbTimeToExpiry, dblRiskFreeRate,
				dblSpot, dblInitialVolatility, dblA, dblFreq, dblB1, dblU1, rcpt) : payoffTransform
					(dblStrike, dbTimeToExpiry, dblRiskFreeRate, dblSpot, dblInitialVolatility, dblA,
						dblFreq, dblB2, dblU2, rcpt);

			if (null != rcpt) {
				if (0 == i)
					dblPreviousPhase = rcpt.getPreviousPhase();
				else if (1 == i) {
					double dblCurrentPhase = rcpt.getPreviousPhase();

					if (dblCurrentPhase < dblPreviousPhase) {
						if (!rcpt.setDirection (RotationCountPhaseTracker.APPLY_BACKWARD))
							return null;
					} else if (dblCurrentPhase > dblPreviousPhase) {
						if (!rcpt.setDirection (RotationCountPhaseTracker.APPLY_FORWARD))
							return null;
					} else
						return null;
				}
			}

			mapPhaseRun.put (dblFreq, pcf._dblCorrectedPhase);
		}

		return mapPhaseRun;
	}

	@Override public double payoff (
		final double dblStrike,
		final double dblTimeToExpiry,
		final double dblRiskFreeRate,
		final double dblUnderlier,
		final boolean bIsPut,
		final boolean bIsForward,
		final double dblInitialVolatility,
		final boolean bAsPrice)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblStrike) ||
			!org.drip.numerical.common.NumberUtil.IsValid (dblUnderlier) ||
				!org.drip.numerical.common.NumberUtil.IsValid (dblInitialVolatility) ||
					!org.drip.numerical.common.NumberUtil.IsValid (dblTimeToExpiry) ||
						!org.drip.numerical.common.NumberUtil.IsValid (dblRiskFreeRate))
			throw new java.lang.Exception ("HestonStochasticVolatilityAlgorithm::payoff => Invalid Inputs");

		org.drip.numerical.fourier.RotationCountPhaseTracker rcpt1 =
			org.drip.numerical.fourier.PhaseAdjuster.MULTI_VALUE_BRANCH_PHASE_TRACKER_ROTATION_COUNT ==
				_fphp.phaseTrackerType() ? new org.drip.numerical.fourier.RotationCountPhaseTracker() : null;

		org.drip.numerical.fourier.RotationCountPhaseTracker rcpt2 =
			org.drip.numerical.fourier.PhaseAdjuster.MULTI_VALUE_BRANCH_PHASE_TRACKER_ROTATION_COUNT ==
				_fphp.phaseTrackerType() ? new org.drip.numerical.fourier.RotationCountPhaseTracker() : null;

		double dblA = _fphp.kappa() * _fphp.theta();

		double dblB2 = _fphp.kappa() + _fphp.lambda();

		double dblB1 = dblB2 - _fphp.rho() * _fphp.sigma();

		double dblDF = java.lang.Math.exp (-1. * dblRiskFreeRate * dblTimeToExpiry);

		int i = 0;
		double dblU1 = 0.5;
		double dblU2 = -0.5;
		double dblCallProb1 = 0.;
		double dblCallProb2 = 0.;
		double dblPreviousPhase = 0.;
		double dblSpot = bIsForward ? dblUnderlier * dblDF : dblUnderlier;

		for (double dblFreq = FOURIER_FREQ_INIT; dblFreq <= FOURIER_FREQ_FINAL; dblFreq +=
			FOURIER_FREQ_INCREMENT, ++i) {
			PhaseCorrectedF pcf1 = payoffTransform (dblStrike, dblTimeToExpiry, dblRiskFreeRate, dblSpot,
				dblInitialVolatility, dblA, dblFreq, dblB1, dblU1, rcpt1);

			if (null != rcpt1) {
				if (0 == i)
					dblPreviousPhase = rcpt1.getPreviousPhase();
				else if (1 == i) {
					double dblCurrentPhase = rcpt1.getPreviousPhase();

					if (dblCurrentPhase < dblPreviousPhase) {
						if (!rcpt1.setDirection
							(org.drip.numerical.fourier.RotationCountPhaseTracker.APPLY_BACKWARD))
							throw new java.lang.Exception
								("HestonStochasticVolatilityAlgorithm::payoff => Cannot compute payoff");
					} else if (dblCurrentPhase > dblPreviousPhase) {
						if (!rcpt1.setDirection
							(org.drip.numerical.fourier.RotationCountPhaseTracker.APPLY_FORWARD))
							throw new java.lang.Exception
								("HestonStochasticVolatilityAlgorithm::payoff => Cannot compute payoff");
					} else
						throw new java.lang.Exception
							("HestonStochasticVolatilityAlgorithm::payoff => Cannot compute payoff");
				}
			}

			PhaseCorrectedF pcf2 = payoffTransform (dblStrike, dblTimeToExpiry, dblRiskFreeRate, dblSpot,
				dblInitialVolatility, dblA, dblFreq, dblB2, dblU2, rcpt2);

			if (null != rcpt2) {
				if (0 == i)
					dblPreviousPhase = rcpt2.getPreviousPhase();
				else if (1 == i) {
					double dblCurrentPhase = rcpt2.getPreviousPhase();

					if (dblCurrentPhase < dblPreviousPhase) {
						if (!rcpt2.setDirection
							(org.drip.numerical.fourier.RotationCountPhaseTracker.APPLY_BACKWARD))
							throw new java.lang.Exception
								("HestonStochasticVolatilityAlgorithm::payoff => Cannot compute payoff");
					} else if (dblCurrentPhase > dblPreviousPhase) {
						if (!rcpt2.setDirection
							(org.drip.numerical.fourier.RotationCountPhaseTracker.APPLY_FORWARD))
							throw new java.lang.Exception
								("HestonStochasticVolatilityAlgorithm::payoff => Cannot compute payoff");
					} else
						throw new java.lang.Exception
							("HestonStochasticVolatilityAlgorithm::payoff => Cannot compute payoff");
				}
			}

			dblCallProb1 += pcf1._cnF.real() * FOURIER_FREQ_INCREMENT;

			dblCallProb2 += pcf2._cnF.real() * FOURIER_FREQ_INCREMENT;
		}

		double dblForward = dblSpot / dblDF;
		double dblPIScaler = 1. / java.lang.Math.PI;
		double dblCallPayoff = dblForward * (0.5 + dblCallProb1 * dblPIScaler) - dblStrike * (0.5 +
			dblCallProb2 * dblPIScaler);

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
		final double dblInitialVolatility)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblStrike) ||
			!org.drip.numerical.common.NumberUtil.IsValid (dblUnderlier) ||
				!org.drip.numerical.common.NumberUtil.IsValid (dblInitialVolatility) ||
					!org.drip.numerical.common.NumberUtil.IsValid (dblTimeToExpiry) ||
						!org.drip.numerical.common.NumberUtil.IsValid (dblRiskFreeRate))
			return null;

		org.drip.numerical.fourier.RotationCountPhaseTracker rcpt1 =
			org.drip.numerical.fourier.PhaseAdjuster.MULTI_VALUE_BRANCH_PHASE_TRACKER_ROTATION_COUNT ==
				_fphp.phaseTrackerType() ? new org.drip.numerical.fourier.RotationCountPhaseTracker() : null;

		org.drip.numerical.fourier.RotationCountPhaseTracker rcpt2 =
			org.drip.numerical.fourier.PhaseAdjuster.MULTI_VALUE_BRANCH_PHASE_TRACKER_ROTATION_COUNT ==
				_fphp.phaseTrackerType() ? new org.drip.numerical.fourier.RotationCountPhaseTracker() : null;

		double dblA = _fphp.kappa() * _fphp.theta();

		double dblB2 = _fphp.kappa() + _fphp.lambda();

		double dblB1 = dblB2 - _fphp.rho() * _fphp.sigma();

		double dblDF = java.lang.Math.exp (-1. * dblRiskFreeRate * dblTimeToExpiry);

		int i = 0;
		double dblU1 = 0.5;
		double dblU2 = -0.5;
		double dblCallProb1 = 0.;
		double dblCallProb2 = 0.;
		double dblPreviousPhase = 0.;
		double dblSpot = bIsForward ? dblUnderlier * dblDF : dblUnderlier;

		for (double dblFreq = FOURIER_FREQ_INIT; dblFreq <= FOURIER_FREQ_FINAL; dblFreq +=
			FOURIER_FREQ_INCREMENT, ++i) {
			PhaseCorrectedF pcf1 = payoffTransform (dblStrike, dblTimeToExpiry, dblRiskFreeRate, dblSpot,
				dblInitialVolatility, dblA, dblFreq, dblB1, dblU1, rcpt1);

			if (null != rcpt1) {
				if (0 == i)
					dblPreviousPhase = rcpt1.getPreviousPhase();
				else if (1 == i) {
					double dblCurrentPhase = rcpt1.getPreviousPhase();

					if (dblCurrentPhase < dblPreviousPhase) {
						if (!rcpt1.setDirection
							(org.drip.numerical.fourier.RotationCountPhaseTracker.APPLY_BACKWARD))
							return null;
					} else if (dblCurrentPhase > dblPreviousPhase) {
						if (!rcpt1.setDirection
							(org.drip.numerical.fourier.RotationCountPhaseTracker.APPLY_FORWARD))
							return null;
					} else
						return null;
				}
			}

			PhaseCorrectedF pcf2 = payoffTransform (dblStrike, dblTimeToExpiry, dblRiskFreeRate, dblSpot,
				dblInitialVolatility, dblA, dblFreq, dblB2, dblU2, rcpt2);

			if (null != rcpt2) {
				if (0 == i)
					dblPreviousPhase = rcpt2.getPreviousPhase();
				else if (1 == i) {
					double dblCurrentPhase = rcpt2.getPreviousPhase();

					if (dblCurrentPhase < dblPreviousPhase) {
						if (!rcpt2.setDirection
							(org.drip.numerical.fourier.RotationCountPhaseTracker.APPLY_BACKWARD))
							return null;
					} else if (dblCurrentPhase > dblPreviousPhase) {
						if (!rcpt2.setDirection
							(org.drip.numerical.fourier.RotationCountPhaseTracker.APPLY_FORWARD))
							return null;
					} else
						return null;
				}
			}

			dblCallProb1 += pcf1._cnF.real() * FOURIER_FREQ_INCREMENT;

			dblCallProb2 += pcf2._cnF.real() * FOURIER_FREQ_INCREMENT;
		}

		double dblForward = dblSpot / dblDF;
		double dblPIScaler = 1. / java.lang.Math.PI;
		dblCallProb1 = 0.5 + dblCallProb1 * dblPIScaler;
		dblCallProb2 = 0.5 + dblCallProb2 * dblPIScaler;
		double dblATMCallPayoff = dblForward * (dblCallProb1 - dblCallProb2);
		double dblCallPrice = dblSpot * dblCallProb1 - dblStrike * dblDF * dblCallProb2;
		double dblExpectedCallPayoff = dblForward * dblCallProb1 - dblStrike * dblDF * dblCallProb2;

		try {
			if (!bIsPut)
				return new org.drip.pricer.option.Greeks (
					dblDF,
					dblInitialVolatility,
					dblExpectedCallPayoff,
					dblATMCallPayoff,
					dblCallPrice,
					dblCallProb1,
					dblCallProb2,
					dblCallProb1,
					java.lang.Double.NaN,
					java.lang.Double.NaN,
					java.lang.Double.NaN,
					java.lang.Double.NaN,
					java.lang.Double.NaN,
					java.lang.Double.NaN,
					java.lang.Double.NaN,
					java.lang.Double.NaN,
					java.lang.Double.NaN,
					java.lang.Double.NaN,
					java.lang.Double.NaN
				);

			double dblPutPriceFromParity = dblCallPrice + dblStrike * dblDF - dblSpot;

			return new org.drip.pricer.option.PutGreeks (
				dblDF,
				dblInitialVolatility,
				dblExpectedCallPayoff + dblStrike - dblSpot,
				dblATMCallPayoff,
				dblPutPriceFromParity,
				dblPutPriceFromParity,
				java.lang.Double.NaN,
				java.lang.Double.NaN,
				java.lang.Double.NaN,
				java.lang.Double.NaN,
				java.lang.Double.NaN,
				java.lang.Double.NaN,
				java.lang.Double.NaN,
				java.lang.Double.NaN,
				java.lang.Double.NaN,
				java.lang.Double.NaN,
				java.lang.Double.NaN,
				java.lang.Double.NaN,
				java.lang.Double.NaN,
				java.lang.Double.NaN
			);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
