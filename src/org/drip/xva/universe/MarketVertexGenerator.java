
package org.drip.xva.universe;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
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
 * MarketVertexGenerator generates the Market Realizations at a Trajectory Vertex needed for computing the
 *  Valuation Adjustment. The References are:<br><br>
 *  
 *  - Burgard, C., and M. Kjaer (2013): Funding Strategies, Funding Costs <i>Risk</i> <b>24 (12)</b>
 *  	82-87.<br><br>
 *  
 *  - Burgard, C., and M. Kjaer (2014): PDE Representations of Derivatives with Bilateral Counter-party Risk
 *  	and Funding Costs <i>Journal of Credit Risk</i> <b>7 (3)</b> 1-19.<br><br>
 *  
 *  - Burgard, C., and M. Kjaer (2014): In the Balance <i>Risk</i> <b>24 (11)</b> 72-75.<br><br>
 *  
 *  - Gregory, J. (2009): Being Two-faced over Counter-party Credit Risk <i>Risk</i> <b>20 (2)</b>
 *  	86-90.<br><br>
 * 
 *  - Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives Pricing
 *  	<i>Risk</i> <b>21 (2)</b> 97-102.<br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class MarketVertexGenerator {

	/**
	 * Asset Numeraire Wanderer Index
	 */

	public static final int ASSET = 0;

	/**
	 * Overnight Index Policy Numeraire Wanderer Index
	 */

	public static final int OVERNIGHT_INDEX = 1;

	/**
	 * Collateral Scheme Numeraire Wanderer Index
	 */

	public static final int COLLATERAL_SCHEME = 2;

	/**
	 * Bank Hazard Rate Wanderer Index
	 */

	public static final int BANK_HAZARD_RATE = 3;

	/**
	 * Bank Senior Funding Wanderer Index
	 */

	public static final int BANK_SENIOR_FUNDING = 4;

	/**
	 * Bank Senior Recovery Rate Wanderer Index
	 */

	public static final int BANK_SENIOR_RECOVERY_RATE = 5;

	/**
	 * Bank Subordinate Funding Wanderer Index
	 */

	public static final int BANK_SUBORDINATE_FUNDING = 6;

	/**
	 * Bank Subordinate Recovery Rate Wanderer Index
	 */

	public static final int BANK_SUBORDINATE_RECOVERY_RATE = 7;

	/**
	 * Counter Party Hazard Rate Wanderer Index
	 */

	public static final int COUNTER_PARTY_HAZARD_RATE = 8;

	/**
	 * Counter Party Funding Wanderer Index
	 */

	public static final int COUNTER_PARTY_FUNDING = 9;

	/**
	 * Counter Party Recovery Rate Wanderer Index
	 */

	public static final int COUNTER_PARTY_RECOVERY_RATE = 10;

	private int _iSpotDate = -1;
	private int[] _aiEventDate = null;
	private double[] _adblTimeWidth = null;
	private double[][] _aadblCorrelationMatrix = null;
	private org.drip.xva.universe.TradeablesContainer _tc = null;
	private org.drip.measure.process.DiffusionEvolver _deBankHazardRate = null;
	private org.drip.measure.process.DiffusionEvolver _deBankSeniorRecoveryRate = null;
	private org.drip.measure.process.DiffusionEvolver _deBankSubordinateRecoveryRate = null;
	private org.drip.measure.process.DiffusionEvolver _deCounterPartyHazardRate = null;
	private org.drip.measure.process.DiffusionEvolver _deCounterPartyRecoveryRate = null;

	/**
	 * MarketVertexGenerator Constructor
	 * 
	 * @param iSpotDate The Spot Date
	 * @param aiEventDate Array of the Event Dates
	 * @param aadblCorrelationMatrix The Correlation Matrix
	 * @param tc The Tradeables Container Instance
	 * @param deBankHazardRate The Bank Hazard Rate Diffusive Evolver
	 * @param deBankSeniorRecoveryRate The Bank Senior Recovery Rate Diffusive Evolver
	 * @param deBankSubordinateRecoveryRate The Bank Subordinate Rate Diffusive Evolver
	 * @param deCounterPartyHazardRate The Counter Party Hazard Rate Diffusive Evolver
	 * @param deCounterPartyRecoveryRate The Counter Party Recovery Rate Diffusive Evolver
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public MarketVertexGenerator (
		final int iSpotDate,
		final int[] aiEventDate,
		final double[][] aadblCorrelationMatrix,
		final org.drip.xva.universe.TradeablesContainer tc,
		final org.drip.measure.process.DiffusionEvolver deBankHazardRate,
		final org.drip.measure.process.DiffusionEvolver deBankSeniorRecoveryRate,
		final org.drip.measure.process.DiffusionEvolver deBankSubordinateRecoveryRate,
		final org.drip.measure.process.DiffusionEvolver deCounterPartyHazardRate,
		final org.drip.measure.process.DiffusionEvolver deCounterPartyRecoveryRate)
		throws java.lang.Exception
	{
		if (0 >= (_iSpotDate = iSpotDate) ||
			null == (_aiEventDate = aiEventDate) ||
			null == (_aadblCorrelationMatrix = aadblCorrelationMatrix) ||
			null == (_tc = tc) ||
			null == (_deBankHazardRate = deBankHazardRate) ||
			null == (_deBankSeniorRecoveryRate = deBankSeniorRecoveryRate) ||
			null == (_deCounterPartyHazardRate = deCounterPartyHazardRate) ||
			null == (_deCounterPartyRecoveryRate = deCounterPartyRecoveryRate))
			throw new java.lang.Exception ("MarketVertexGenerator Constructor => Invalid Inputs");

		int iNumEventVertex = _aiEventDate.length;
		int iNumDimension = _aadblCorrelationMatrix.length;
		_deBankSubordinateRecoveryRate = deBankSubordinateRecoveryRate;
		_adblTimeWidth = 0 == iNumEventVertex ? null : new double[iNumEventVertex];

		if (0 == iNumEventVertex || 11 != iNumDimension || 0. >= (_adblTimeWidth[0] = ((double)
			(_aiEventDate[0] - _iSpotDate)) / 365.25))
			throw new java.lang.Exception ("MarketVertexGenerator Constructor => Invalid Inputs");

		for (int iEventVertex = 1; iEventVertex < iNumEventVertex; ++iEventVertex) {
			if (0. >= (_adblTimeWidth[iEventVertex] = ((double) (_aiEventDate[iEventVertex] -
				_aiEventDate[iEventVertex - 1])) / 365.25))
				throw new java.lang.Exception ("MarketVertexGenerator Constructor => Invalid Inputs");
		}

		for (int iDimension = 0; iDimension < iNumDimension; ++iDimension) {
			if (null == _aadblCorrelationMatrix[iDimension] || 11 !=
				_aadblCorrelationMatrix[iDimension].length || !org.drip.quant.common.NumberUtil.IsValid
					(_aadblCorrelationMatrix[iDimension]))
				throw new java.lang.Exception ("MarketVertexGenerator Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Spot Date
	 * 
	 * @return The Spot Date
	 */

	public int spotDate()
	{
		return _iSpotDate;
	}

	/**
	 * Retrieve the Event Date Array
	 * 
	 * @return The Event Date Array
	 */

	public int[] eventDate()
	{
		return _aiEventDate;
	}

	/**
	 * Retrieve the Time Width Array
	 * 
	 * @return The Time Width Array
	 */

	public double[] timeWidth()
	{
		return _adblTimeWidth;
	}

	/**
	 * Retrieve the Latent State Correlation Matrix
	 * 
	 * @return The Latent State Correlation Matrix
	 */

	public double[][] correlationMatrix()
	{
		return _aadblCorrelationMatrix;
	}

	/**
	 * Retrieve the Bank Hazard Rate Evolver
	 * 
	 * @return The Bank Hazard Rate Evolver
	 */

	public org.drip.measure.process.DiffusionEvolver bankHazardRateEvolver()
	{
		return _deBankHazardRate;
	}

	/**
	 * Retrieve the Bank Senior Recovery Rate Evolver
	 * 
	 * @return The Bank Senior Recovery Rate Evolver
	 */

	public org.drip.measure.process.DiffusionEvolver bankSeniorRecoveryRateEvolver()
	{
		return _deBankSeniorRecoveryRate;
	}

	/**
	 * Retrieve the Bank Subordinate Recovery Rate Evolver
	 * 
	 * @return The Bank Subordinate Recovery Rate Evolver
	 */

	public org.drip.measure.process.DiffusionEvolver bankSubordinateRecoveryRateEvolver()
	{
		return _deBankSubordinateRecoveryRate;
	}

	/**
	 * Retrieve the Counter Party Rate Evolver
	 * 
	 * @return The Counter Party Rate Evolver
	 */

	public org.drip.measure.process.DiffusionEvolver counterPartyHazardRateEvolver()
	{
		return _deCounterPartyHazardRate;
	}

	/**
	 * Retrieve the Counter Party Recovery Rate Evolver
	 * 
	 * @return The Counter Party Recovery Rate Evolver
	 */

	public org.drip.measure.process.DiffusionEvolver counterPartyRecoveryRateEvolver()
	{
		return _deCounterPartyRecoveryRate;
	}

	/**
	 * Generated the Sequence of the Simulated Market Vertexes
	 * 
	 * @param mvInitial The Initial Market Vertex
	 * 
	 * @return The Array of the Simulated Market Vertexes
	 */

	public org.drip.xva.universe.MarketVertex[] marketVertex (
		final org.drip.xva.universe.MarketVertex mvInitial)
	{
		if (null == mvInitial) return null;

		org.drip.xva.universe.Tradeable tAsset = _tc.asset();

		org.drip.xva.universe.Tradeable tBankSubordinateFunding = _tc.bankSubordinateFunding();

		int iNumEventVertex = _aiEventDate.length;
		boolean bAssetEvolutionOn = null != tAsset;
		double dblBankSurvivalProbabilityExponent = 0.;
		double dblCounterPartySurvivalProbabilityExponent = 0.;
		org.drip.measure.realization.JumpDiffusionVertex[] aJDVAssetNumeraire = null;
		org.drip.measure.realization.JumpDiffusionVertex[] aJDVBankHazardRate = null;
		org.drip.measure.realization.JumpDiffusionVertex[] aJDVBankSeniorRecoveryRate = null;
		org.drip.measure.realization.JumpDiffusionVertex[] aJDVCounterPartyHazardRate = null;
		org.drip.measure.realization.JumpDiffusionVertex[] aJDVOvernightIndexNumeraire = null;
		org.drip.measure.realization.JumpDiffusionVertex[] aJDVCounterPartyRecoveryRate = null;
		org.drip.measure.realization.JumpDiffusionVertex[] aJDVCollateralSchemeNumeraire = null;
		org.drip.measure.realization.JumpDiffusionVertex[] aJDVBankSeniorFundingNumeraire = null;
		org.drip.measure.realization.JumpDiffusionVertex[] aJDVBankSubordinateRecoveryRate = null;
		org.drip.measure.realization.JumpDiffusionVertex[] aJDVCounterPartyFundingNumeraire = null;
		org.drip.measure.realization.JumpDiffusionVertex[] aJDVBankSubordinateFundingNumeraire = null;
		org.drip.xva.universe.MarketVertex[] aMV = new
			org.drip.xva.universe.MarketVertex[iNumEventVertex + 1];
		int iTerminalDate = _aiEventDate[iNumEventVertex - 1];

		double[][] aadblUnitEvolverSequence = org.drip.quant.linearalgebra.Matrix.Transpose
			(org.drip.measure.discrete.SequenceGenerator.GaussianJoint (iNumEventVertex,
				_aadblCorrelationMatrix));

		if (null == aadblUnitEvolverSequence) return null;

		org.drip.xva.universe.EntityMarketVertex emvBankInitial = mvInitial.bank();

		org.drip.xva.universe.EntityMarketVertex emvCounterPartyInitial = mvInitial.bank();

		double dblBankSubordinateRecoveryRateStart = emvBankInitial.subordinateRecoveryRate();

		org.drip.xva.universe.NumeraireMarketVertex nmvSubordinateFunding =
			emvBankInitial.subordinateFundingNumeraire();

		double dblBankSubordinateFundingNumeraireTerminal = null == nmvSubordinateFunding ?
			java.lang.Double.NaN : nmvSubordinateFunding.forward();

		boolean bSingleBankBond = null == tBankSubordinateFunding ||
			!org.drip.quant.common.NumberUtil.IsValid (dblBankSubordinateFundingNumeraireTerminal) ||
				!org.drip.quant.common.NumberUtil.IsValid (dblBankSubordinateRecoveryRateStart);

		try {
			aJDVAssetNumeraire = !bAssetEvolutionOn ? null : tAsset.numeraireEvolver().vertexSequence (new
				org.drip.measure.realization.JumpDiffusionVertex (_iSpotDate, mvInitial.assetNumeraire(), 0.,
					false), org.drip.measure.realization.JumpDiffusionEdgeUnit.Diffusion (_adblTimeWidth,
						aadblUnitEvolverSequence[ASSET]), _adblTimeWidth);

			aJDVOvernightIndexNumeraire = _tc.overnightIndex().numeraireEvolver().vertexSequenceReverse (new
				org.drip.measure.realization.JumpDiffusionVertex (iTerminalDate,
					mvInitial.overnightIndexNumeraire().forward(), 0., false),
						org.drip.measure.realization.JumpDiffusionEdgeUnit.Diffusion (_adblTimeWidth,
							aadblUnitEvolverSequence[OVERNIGHT_INDEX]), _adblTimeWidth);

			aJDVCollateralSchemeNumeraire = _tc.collateralScheme().numeraireEvolver().vertexSequenceReverse
				(new org.drip.measure.realization.JumpDiffusionVertex (iTerminalDate,
					mvInitial.collateralSchemeNumeraire().forward(), 0., false),
						org.drip.measure.realization.JumpDiffusionEdgeUnit.Diffusion (_adblTimeWidth,
							aadblUnitEvolverSequence[COLLATERAL_SCHEME]), _adblTimeWidth);

			aJDVBankHazardRate = _deBankHazardRate.vertexSequence (new
				org.drip.measure.realization.JumpDiffusionVertex (_iSpotDate, emvBankInitial.hazardRate(),
					0., false), org.drip.measure.realization.JumpDiffusionEdgeUnit.Diffusion (_adblTimeWidth,
						aadblUnitEvolverSequence[BANK_HAZARD_RATE]), _adblTimeWidth);

			aJDVBankSeniorFundingNumeraire =
				_tc.bankSubordinateFunding().numeraireEvolver().vertexSequenceReverse (new
					org.drip.measure.realization.JumpDiffusionVertex (iTerminalDate,
						emvBankInitial.seniorFundingNumeraire().forward(), 0., false),
							org.drip.measure.realization.JumpDiffusionEdgeUnit.Diffusion (_adblTimeWidth,
								aadblUnitEvolverSequence[BANK_SENIOR_FUNDING]), _adblTimeWidth);

			aJDVBankSeniorRecoveryRate = _deBankSeniorRecoveryRate.vertexSequence (new
				org.drip.measure.realization.JumpDiffusionVertex (_iSpotDate,
					emvBankInitial.seniorRecoveryRate(), 0., false),
						org.drip.measure.realization.JumpDiffusionEdgeUnit.Diffusion (_adblTimeWidth,
							aadblUnitEvolverSequence[BANK_SENIOR_RECOVERY_RATE]), _adblTimeWidth);

			aJDVBankSubordinateFundingNumeraire = bSingleBankBond ? null :
				tBankSubordinateFunding.numeraireEvolver().vertexSequenceReverse (new
					org.drip.measure.realization.JumpDiffusionVertex (iTerminalDate,
						dblBankSubordinateFundingNumeraireTerminal, 0., false),
							org.drip.measure.realization.JumpDiffusionEdgeUnit.Diffusion (_adblTimeWidth,
								aadblUnitEvolverSequence[BANK_SUBORDINATE_FUNDING]), _adblTimeWidth);

			aJDVBankSubordinateRecoveryRate = bSingleBankBond ? null :
				_deBankSubordinateRecoveryRate.vertexSequence (new
					org.drip.measure.realization.JumpDiffusionVertex (_iSpotDate,
						dblBankSubordinateRecoveryRateStart, 0., false),
							org.drip.measure.realization.JumpDiffusionEdgeUnit.Diffusion (_adblTimeWidth,
								aadblUnitEvolverSequence[BANK_SUBORDINATE_RECOVERY_RATE]), _adblTimeWidth);

			aJDVCounterPartyHazardRate = _deCounterPartyHazardRate.vertexSequence (new
				org.drip.measure.realization.JumpDiffusionVertex (_iSpotDate,
					emvCounterPartyInitial.hazardRate(), 0., false),
						org.drip.measure.realization.JumpDiffusionEdgeUnit.Diffusion (_adblTimeWidth,
							aadblUnitEvolverSequence[COUNTER_PARTY_HAZARD_RATE]), _adblTimeWidth);

			aJDVCounterPartyFundingNumeraire =
				_tc.counterPartyFunding().numeraireEvolver().vertexSequenceReverse (new
					org.drip.measure.realization.JumpDiffusionVertex (iTerminalDate,
						emvCounterPartyInitial.seniorFundingNumeraire().forward(), 0., false),
							org.drip.measure.realization.JumpDiffusionEdgeUnit.Diffusion (_adblTimeWidth,
								aadblUnitEvolverSequence[COUNTER_PARTY_FUNDING]), _adblTimeWidth);

			aJDVCounterPartyRecoveryRate = _deCounterPartyRecoveryRate.vertexSequence (new
				org.drip.measure.realization.JumpDiffusionVertex (_iSpotDate,
					emvCounterPartyInitial.seniorRecoveryRate(), 0., false),
						org.drip.measure.realization.JumpDiffusionEdgeUnit.Diffusion (_adblTimeWidth,
							aadblUnitEvolverSequence[COUNTER_PARTY_RECOVERY_RATE]), _adblTimeWidth);
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		double dblOvernightIndexNumeraireStart = aJDVOvernightIndexNumeraire[0].value();

		double dblCollateralSchemeNumeraireStart = aJDVCollateralSchemeNumeraire[0].value();

		double dblBankSeniorFundingNumeraireStart = aJDVBankSeniorFundingNumeraire[0].value();

		double dblBankSubordinateFundingNumeraireStart = bSingleBankBond ? java.lang.Double.NaN :
			aJDVBankSubordinateFundingNumeraire[0].value();

		double dblCounterPartyFundingNumeraireStart = aJDVCounterPartyFundingNumeraire[0].value();

		double dblOvernightIndexNumeraireInitial = dblOvernightIndexNumeraireStart;
		double dblCollateralSchemeNumeraireInitial = dblCollateralSchemeNumeraireStart;
		double dblBankSeniorFundingNumeraireInitial = dblBankSeniorFundingNumeraireStart;
		double dblCounterPartyFundingNumeraireInitial = dblCounterPartyFundingNumeraireStart;
		double dblBankSubordinateFundingNumeraireInitial = dblBankSubordinateFundingNumeraireStart;

		for (int iEventVertex = 1; iEventVertex <= iNumEventVertex; ++iEventVertex) {
			double dblBankHazardRate = aJDVBankHazardRate[iEventVertex].value();

			double dblCounterPartyHazardRate = aJDVCounterPartyHazardRate[iEventVertex].value();

			double dblOvernightIndexNumeraireFinish = aJDVOvernightIndexNumeraire[iEventVertex].value();

			double dblCollateralSchemeNumeraireFinish =  aJDVCollateralSchemeNumeraire[iEventVertex].value();

			double dblBankSeniorFundingNumeraireFinish =
				aJDVBankSeniorFundingNumeraire[iEventVertex].value();

			double dblBankSubordinateFundingNumeraireFinish = bSingleBankBond ? java.lang.Double.NaN :
				aJDVBankSubordinateFundingNumeraire[iEventVertex].value();

			double dblCounterPartyFundingNumeraireFinish =
				aJDVCounterPartyFundingNumeraire[iEventVertex].value();

			double dblTimeWidth = _adblTimeWidth[iEventVertex - 1];
			dblCounterPartySurvivalProbabilityExponent += dblCounterPartyHazardRate * dblTimeWidth;
			dblBankSurvivalProbabilityExponent += dblBankHazardRate * dblTimeWidth;
			double dblTimeWidthReciprocal = 1. / dblTimeWidth;

			double dblOvernightIndexRate = dblTimeWidthReciprocal * java.lang.Math.log
				(dblOvernightIndexNumeraireFinish / dblOvernightIndexNumeraireStart);

			try {
				org.drip.xva.universe.EntityMarketVertex emvBank = new org.drip.xva.universe.EntityMarketVertex (
					java.lang.Math.exp (-1. * dblBankSurvivalProbabilityExponent),
					dblBankHazardRate,
					aJDVBankSeniorRecoveryRate[iEventVertex].value(),
					dblTimeWidthReciprocal * java.lang.Math.log (dblBankSeniorFundingNumeraireFinish /
						dblBankSeniorFundingNumeraireStart) - dblOvernightIndexRate,
					new org.drip.xva.universe.NumeraireMarketVertex (
						dblBankSeniorFundingNumeraireInitial,
						dblBankSeniorFundingNumeraireFinish
					),
					bSingleBankBond ? java.lang.Double.NaN : aJDVBankSubordinateRecoveryRate[iEventVertex].value(),
					bSingleBankBond ? java.lang.Double.NaN : dblTimeWidthReciprocal * java.lang.Math.log
						(dblBankSubordinateFundingNumeraireFinish / dblBankSubordinateFundingNumeraireStart)
						- dblOvernightIndexRate,
					bSingleBankBond ? null : new org.drip.xva.universe.NumeraireMarketVertex (
						dblBankSubordinateFundingNumeraireInitial,
						dblBankSubordinateFundingNumeraireFinish
					)
				);

				org.drip.xva.universe.EntityMarketVertex emvCounterParty = new org.drip.xva.universe.EntityMarketVertex (
					java.lang.Math.exp (-1. * dblCounterPartySurvivalProbabilityExponent),
					dblCounterPartyHazardRate,
					aJDVCounterPartyRecoveryRate[iEventVertex].value(),
					dblTimeWidthReciprocal * java.lang.Math.log (dblCounterPartyFundingNumeraireFinish /
						dblCounterPartyFundingNumeraireStart) - dblOvernightIndexRate,
					new org.drip.xva.universe.NumeraireMarketVertex (
						dblCounterPartyFundingNumeraireInitial,
						dblCounterPartyFundingNumeraireFinish
					),
					java.lang.Double.NaN,
					java.lang.Double.NaN,
					null
				);

				aMV[iEventVertex] = new org.drip.xva.universe.MarketVertex (
					new org.drip.analytics.date.JulianDate (_aiEventDate[iEventVertex - 1]),
					bAssetEvolutionOn ? aJDVAssetNumeraire[iEventVertex].value() : java.lang.Double.NaN,
					dblOvernightIndexRate,
					new org.drip.xva.universe.NumeraireMarketVertex (
						dblOvernightIndexNumeraireInitial,
						dblOvernightIndexNumeraireFinish
					),
					dblTimeWidthReciprocal * java.lang.Math.log (dblCollateralSchemeNumeraireFinish /
						dblCollateralSchemeNumeraireStart) - dblOvernightIndexRate,
					new org.drip.xva.universe.NumeraireMarketVertex (
						dblCollateralSchemeNumeraireInitial,
						dblCollateralSchemeNumeraireFinish
					),
					emvBank,
					emvCounterParty
				);
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}

			dblOvernightIndexNumeraireStart = dblOvernightIndexNumeraireFinish;
			dblCollateralSchemeNumeraireStart = dblCollateralSchemeNumeraireFinish;
			dblBankSeniorFundingNumeraireStart = dblBankSeniorFundingNumeraireFinish;
			dblCounterPartyFundingNumeraireStart = dblCounterPartyFundingNumeraireFinish;
			dblBankSubordinateFundingNumeraireStart = dblBankSubordinateFundingNumeraireFinish;
		}

		try {
			aMV[0] = new org.drip.xva.universe.MarketVertex (
				mvInitial.anchor(),
				mvInitial.assetNumeraire(),
				mvInitial.overnightIndexRate(),
				new org.drip.xva.universe.NumeraireMarketVertex (
					dblOvernightIndexNumeraireInitial,
					dblOvernightIndexNumeraireInitial
				),
				aMV[1].collateralSchemeRate(),
				new org.drip.xva.universe.NumeraireMarketVertex (
					dblCollateralSchemeNumeraireInitial,
					dblCollateralSchemeNumeraireInitial
				),
				new org.drip.xva.universe.EntityMarketVertex (
					emvBankInitial.survivalProbability(),
					emvBankInitial.hazardRate(),
					emvBankInitial.seniorRecoveryRate(),
					aMV[1].bank().seniorFundingSpread(),
					new org.drip.xva.universe.NumeraireMarketVertex (
						dblBankSeniorFundingNumeraireInitial,
						dblBankSeniorFundingNumeraireInitial
					),
					emvBankInitial.subordinateRecoveryRate(),
					aMV[1].bank().subordinateFundingSpread(),
					bSingleBankBond ? null : new org.drip.xva.universe.NumeraireMarketVertex (
						dblBankSubordinateFundingNumeraireInitial,
						dblBankSubordinateFundingNumeraireInitial
					)
				),
				new org.drip.xva.universe.EntityMarketVertex (
					emvCounterPartyInitial.survivalProbability(),
					emvCounterPartyInitial.hazardRate(),
					emvCounterPartyInitial.seniorRecoveryRate(),
					aMV[1].counterParty().seniorFundingSpread(),
					new org.drip.xva.universe.NumeraireMarketVertex (
						dblCounterPartyFundingNumeraireInitial,
						dblCounterPartyFundingNumeraireInitial
					),
					java.lang.Double.NaN,
					java.lang.Double.NaN,
					null
				)
			);
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		return aMV;
	}
}
