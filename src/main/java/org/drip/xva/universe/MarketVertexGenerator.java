
package org.drip.xva.universe;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2018 Lakshmi Krishnamurthy
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

public class MarketVertexGenerator
{

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

	private int _spotDate = -1;
	private double[] _ycfWidth = null;
	private int[] _eventDateArray = null;
	private org.drip.xva.universe.TradeablesContainer _tradeablesContainer = null;
	private org.drip.measure.process.DiffusionEvolver _bankHazardRateEvolver = null;
	private org.drip.measure.process.DiffusionEvolver _bankSeniorRecoveryRateEvolver = null;
	private org.drip.measure.process.DiffusionEvolver _counterPartyRecoveryRateEvolver = null;
	private org.drip.measure.process.DiffusionEvolver _bankSubordinateRecoveryRateEvolver = null;
	private org.drip.measure.process.DiffusionEvolver _counterPartyHazardRateFinishVertexArrayEvolver = null;

	/**
	 * Construct a MarketVertexGenerator Instance from the Spot Date, the Period Tenor, and the Period Count
	 * 
	 * @param spotDate The Spot Date
	 * @param periodTenor The Period Tenor
	 * @param periodCount The Period Count
	 * @param tradeablesContainer The Tradeables Container Instance
	 * @param bankHazardRateEvolver The Bank Hazard Rate Diffusive Evolver
	 * @param bankSeniorRecoveryRateEvolver The Bank Senior Recovery Rate Diffusive Evolver
	 * @param bankSubordinateRecoveryRateEvolver The Bank Subordinate Rate Diffusive Evolver
	 * @param counterPartyHazardRateFinishVertexArrayEvolver The Counter Party Hazard Rate Diffusive Evolver
	 * @param counterPartyRecoveryRateEvolver The Counter Party Recovery Rate Diffusive Evolver
	 * 
	 * @return The MarketVertexGenerator Instance from the Spot Date, the Period Tenor, and the Period Count
	 */

	public static final MarketVertexGenerator PeriodHorizon (
		final int spotDate,
		final java.lang.String periodTenor,
		final int periodCount,
		final org.drip.xva.universe.TradeablesContainer tradeablesContainer,
		final org.drip.measure.process.DiffusionEvolver bankHazardRateEvolver,
		final org.drip.measure.process.DiffusionEvolver bankSeniorRecoveryRateEvolver,
		final org.drip.measure.process.DiffusionEvolver bankSubordinateRecoveryRateEvolver,
		final org.drip.measure.process.DiffusionEvolver counterPartyHazardRateFinishVertexArrayEvolver,
		final org.drip.measure.process.DiffusionEvolver counterPartyRecoveryRateEvolver)
	{
		try
		{
			return new MarketVertexGenerator (
				spotDate,
				org.drip.xva.universe.VertexDateBuilder.SpotDatePeriodTenor (
					spotDate,
					periodTenor,
					periodCount
				),
				tradeablesContainer,
				bankHazardRateEvolver,
				bankSeniorRecoveryRateEvolver,
				bankSubordinateRecoveryRateEvolver,
				counterPartyHazardRateFinishVertexArrayEvolver,
				counterPartyRecoveryRateEvolver
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * MarketVertexGenerator Constructor
	 * 
	 * @param spotDate The Spot Date
	 * @param eventDateArray Array of the Event Dates
	 * @param tradeablesContainer The Tradeables Container Instance
	 * @param bankHazardRateEvolver The Bank Hazard Rate Diffusive Evolver
	 * @param bankSeniorRecoveryRateEvolver The Bank Senior Recovery Rate Diffusive Evolver
	 * @param bankSubordinateRecoveryRateEvolver The Bank Subordinate Rate Diffusive Evolver
	 * @param counterPartyHazardRateFinishVertexArrayEvolver The Counter Party Hazard Rate Diffusive Evolver
	 * @param counterPartyRecoveryRateEvolver The Counter Party Recovery Rate Diffusive Evolver
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public MarketVertexGenerator (
		final int spotDate,
		final int[] eventDateArray,
		final org.drip.xva.universe.TradeablesContainer tradeablesContainer,
		final org.drip.measure.process.DiffusionEvolver bankHazardRateEvolver,
		final org.drip.measure.process.DiffusionEvolver bankSeniorRecoveryRateEvolver,
		final org.drip.measure.process.DiffusionEvolver bankSubordinateRecoveryRateEvolver,
		final org.drip.measure.process.DiffusionEvolver counterPartyHazardRateFinishVertexArrayEvolver,
		final org.drip.measure.process.DiffusionEvolver counterPartyRecoveryRateEvolver)
		throws java.lang.Exception
	{
		if (0 >= (_spotDate = spotDate) ||
			null == (_eventDateArray = eventDateArray) ||
			null == (_tradeablesContainer = tradeablesContainer) ||
			null == (_bankHazardRateEvolver = bankHazardRateEvolver) ||
			null == (_bankSeniorRecoveryRateEvolver = bankSeniorRecoveryRateEvolver) ||
			null == (_counterPartyHazardRateFinishVertexArrayEvolver =
				counterPartyHazardRateFinishVertexArrayEvolver) ||
			null == (_counterPartyRecoveryRateEvolver = counterPartyRecoveryRateEvolver))
		{
			throw new java.lang.Exception ("MarketVertexGenerator Constructor => Invalid Inputs");
		}

		int eventVertexCount = _eventDateArray.length;
		_ycfWidth = 0 == eventVertexCount ? null : new double[eventVertexCount];
		_bankSubordinateRecoveryRateEvolver = bankSubordinateRecoveryRateEvolver;

		if (0 == eventVertexCount ||
			0. >= (_ycfWidth[0] = ((double) (_eventDateArray[0] - _spotDate)) / 365.25))
		{
			throw new java.lang.Exception ("MarketVertexGenerator Constructor => Invalid Inputs");
		}

		for (int eventVertexIndex = 1; eventVertexIndex < eventVertexCount; ++eventVertexIndex)
		{
			if (0. >= (_ycfWidth[eventVertexIndex] = ((double) (_eventDateArray[eventVertexIndex] -
				_eventDateArray[eventVertexIndex - 1])) / 365.25))
			{
				throw new java.lang.Exception ("MarketVertexGenerator Constructor => Invalid Inputs");
			}
		}
	}

	/**
	 * Retrieve the Spot Date
	 * 
	 * @return The Spot Date
	 */

	public int spotDate()
	{
		return _spotDate;
	}

	/**
	 * Retrieve the Vertex Date Array
	 * 
	 * @return The Vertex Date Array
	 */

	public int[] vertexDates()
	{
		int eventDateCount = _eventDateArray.length;
		int[] vertexDateArray = new int[eventDateCount + 1];
		vertexDateArray[0] = _spotDate;

		for (int i = 0; i < eventDateCount; ++i)
			vertexDateArray[i + 1] = _eventDateArray[i];

		return vertexDateArray;
	}

	/**
	 * Retrieve the Time Width Array
	 * 
	 * @return The Time Width Array
	 */

	public double[] timeWidth()
	{
		return _ycfWidth;
	}

	/**
	 * Retrieve the Bank Hazard Rate Evolver
	 * 
	 * @return The Bank Hazard Rate Evolver
	 */

	public org.drip.measure.process.DiffusionEvolver bankHazardRateEvolver()
	{
		return _bankHazardRateEvolver;
	}

	/**
	 * Retrieve the Bank Senior Recovery Rate Evolver
	 * 
	 * @return The Bank Senior Recovery Rate Evolver
	 */

	public org.drip.measure.process.DiffusionEvolver bankSeniorRecoveryRateEvolver()
	{
		return _bankSeniorRecoveryRateEvolver;
	}

	/**
	 * Retrieve the Bank Subordinate Recovery Rate Evolver
	 * 
	 * @return The Bank Subordinate Recovery Rate Evolver
	 */

	public org.drip.measure.process.DiffusionEvolver bankSubordinateRecoveryRateEvolver()
	{
		return _bankSubordinateRecoveryRateEvolver;
	}

	/**
	 * Retrieve the Counter Party Rate Evolver
	 * 
	 * @return The Counter Party Rate Evolver
	 */

	public org.drip.measure.process.DiffusionEvolver counterPartyHazardRateFinishVertexArrayEvolver()
	{
		return _counterPartyHazardRateFinishVertexArrayEvolver;
	}

	/**
	 * Retrieve the Counter Party Recovery Rate Evolver
	 * 
	 * @return The Counter Party Recovery Rate Evolver
	 */

	public org.drip.measure.process.DiffusionEvolver counterPartyRecoveryRateEvolver()
	{
		return _counterPartyRecoveryRateEvolver;
	}

	/**
	 * Generated the Sequence of the Simulated Market Vertexes
	 * 
	 * @param initialMarketVertex The Initial Market Vertex
	 * @param unitEvolverSequence Dual Array of Unit Evolver Sequence
	 * 
	 * @return The Array of the Simulated Market Vertexes
	 */

	public org.drip.xva.universe.MarketVertex[] marketVertex (
		final org.drip.xva.universe.MarketVertex initialMarketVertex,
		final double[][] unitEvolverSequence)
	{
		if (null == initialMarketVertex || null == unitEvolverSequence)
		{
			return null;
		}

		int dimensionCount = unitEvolverSequence.length;

		if (11 != dimensionCount)
		{
			return null;
		}

		org.drip.xva.universe.Tradeable positionManifest = _tradeablesContainer.positionManifest();

		org.drip.xva.universe.Tradeable bankSubordinateFundingNumeraire =
			_tradeablesContainer.bankSubordinateFunding();

		double bankSurvivalProbabilityExponent = 0.;
		int eventVertexCount = _eventDateArray.length;
		double counterPartySurvivalProbabilityExponent = 0.;
		boolean positionEvolutionOn = null != positionManifest;
		int terminalDate = _eventDateArray[eventVertexCount - 1];
		org.drip.measure.realization.JumpDiffusionVertex[] csaNumeraireVertexArray = null;
		org.drip.measure.realization.JumpDiffusionVertex[] bankHazardRateVertexArray = null;
		org.drip.measure.realization.JumpDiffusionVertex[] positionManifestVertexArray = null;
		org.drip.measure.realization.JumpDiffusionVertex[] overnightNumeraireVertexArray = null;
		org.drip.measure.realization.JumpDiffusionVertex[] bankSeniorRecoveryRateVertexArray = null;
		org.drip.measure.realization.JumpDiffusionVertex[] counterPartyHazardRateFinishVertexArray = null;
		org.drip.measure.realization.JumpDiffusionVertex[] counterPartyRecoveryRateVertexArray = null;
		org.drip.measure.realization.JumpDiffusionVertex[] bankSeniorFundingNumeraireVertexArray = null;
		org.drip.measure.realization.JumpDiffusionVertex[] bankSubordinateRecoveryRateVertexArray = null;
		org.drip.measure.realization.JumpDiffusionVertex[] counterPartyFundingNumeraireVertexArray = null;
		org.drip.measure.realization.JumpDiffusionVertex[] bankSubordinateFundingNumeraireVertexArray = null;
		org.drip.xva.universe.MarketVertex[] marketVertexArray = new
			org.drip.xva.universe.MarketVertex[eventVertexCount + 1];

		org.drip.xva.universe.MarketVertexEntity initialBankVertex = initialMarketVertex.bank();

		double initialBankSubordinateRecovery = initialBankVertex.subordinateRecoveryRate();

		org.drip.xva.universe.MarketVertexEntity initialCounterPartyVertex =
			initialMarketVertex.counterParty();

		double terminalBankSubordinateFundingNumeraire = initialBankVertex.subordinateFundingReplicator();

		boolean useSingleBankBondOnly = null == bankSubordinateFundingNumeraire ||
			!org.drip.quant.common.NumberUtil.IsValid (terminalBankSubordinateFundingNumeraire) ||
				!org.drip.quant.common.NumberUtil.IsValid (initialBankSubordinateRecovery);

		try
		{
			positionManifestVertexArray = !positionEvolutionOn ? null :
				positionManifest.evolver().vertexSequence (
					new org.drip.measure.realization.JumpDiffusionVertex (
						_spotDate,
						initialMarketVertex.positionManifestValue(),
						0.,
						false
					),
					org.drip.measure.realization.JumpDiffusionEdgeUnit.Diffusion (
						_ycfWidth,
						unitEvolverSequence[ASSET]
					),
					_ycfWidth
				);

			overnightNumeraireVertexArray = _tradeablesContainer.overnight().evolver().vertexSequenceReverse (
				new org.drip.measure.realization.JumpDiffusionVertex (
					terminalDate,
					initialMarketVertex.overnightReplicator(),
					0.,
					false
				),
				org.drip.measure.realization.JumpDiffusionEdgeUnit.Diffusion (
					_ycfWidth,
					unitEvolverSequence[OVERNIGHT_INDEX]
				),
				_ycfWidth
			);

			csaNumeraireVertexArray = _tradeablesContainer.csa().evolver().vertexSequenceReverse (
				new org.drip.measure.realization.JumpDiffusionVertex (
					terminalDate,
					initialMarketVertex.csaReplicator(),
					0.,
					false
				),
				org.drip.measure.realization.JumpDiffusionEdgeUnit.Diffusion (
					_ycfWidth,
					unitEvolverSequence[COLLATERAL_SCHEME]
				),
			_ycfWidth);

			bankHazardRateVertexArray = _bankHazardRateEvolver.vertexSequence (
				new org.drip.measure.realization.JumpDiffusionVertex (
					_spotDate,
					initialBankVertex.hazardRate(),
					0.,
					false
				),
				org.drip.measure.realization.JumpDiffusionEdgeUnit.Diffusion (
					_ycfWidth,
					unitEvolverSequence[BANK_HAZARD_RATE]
				),
				_ycfWidth
			);

			bankSeniorFundingNumeraireVertexArray =
				_tradeablesContainer.bankSubordinateFunding().evolver().vertexSequenceReverse (
					new org.drip.measure.realization.JumpDiffusionVertex (
						terminalDate,
						initialBankVertex.seniorFundingReplicator(),
						0.,
						false
					),
					org.drip.measure.realization.JumpDiffusionEdgeUnit.Diffusion (
						_ycfWidth,
						unitEvolverSequence[BANK_SENIOR_FUNDING]
					),
					_ycfWidth
				);

			bankSeniorRecoveryRateVertexArray = _bankSeniorRecoveryRateEvolver.vertexSequence (
				new org.drip.measure.realization.JumpDiffusionVertex (
					_spotDate,
					initialBankVertex.seniorRecoveryRate(),
					0.,
					false
				),
				org.drip.measure.realization.JumpDiffusionEdgeUnit.Diffusion (
					_ycfWidth,
					unitEvolverSequence[BANK_SENIOR_RECOVERY_RATE]
				),
				_ycfWidth
			);

			bankSubordinateFundingNumeraireVertexArray = useSingleBankBondOnly ? null :
				bankSubordinateFundingNumeraire.evolver().vertexSequenceReverse (
					new org.drip.measure.realization.JumpDiffusionVertex (
						terminalDate,
						terminalBankSubordinateFundingNumeraire,
						0.,
						false
					),
					org.drip.measure.realization.JumpDiffusionEdgeUnit.Diffusion (
						_ycfWidth,
						unitEvolverSequence[BANK_SUBORDINATE_FUNDING]
					),
					_ycfWidth
				);

			bankSubordinateRecoveryRateVertexArray = useSingleBankBondOnly ? null :
				_bankSubordinateRecoveryRateEvolver.vertexSequence (
					new org.drip.measure.realization.JumpDiffusionVertex (
						_spotDate,
						initialBankSubordinateRecovery,
						0.,
						false
					),
					org.drip.measure.realization.JumpDiffusionEdgeUnit.Diffusion (
						_ycfWidth,
						unitEvolverSequence[BANK_SUBORDINATE_RECOVERY_RATE]
					),
					_ycfWidth
				);

			counterPartyHazardRateFinishVertexArray = _counterPartyHazardRateFinishVertexArrayEvolver.vertexSequence (
				new org.drip.measure.realization.JumpDiffusionVertex (
					_spotDate,
					initialCounterPartyVertex.hazardRate(),
					0.,
					false
				),
				org.drip.measure.realization.JumpDiffusionEdgeUnit.Diffusion (
					_ycfWidth,
					unitEvolverSequence[COUNTER_PARTY_HAZARD_RATE]
				),
				_ycfWidth
			);

			counterPartyFundingNumeraireVertexArray =
				_tradeablesContainer.counterPartyFunding().evolver().vertexSequenceReverse (
					new org.drip.measure.realization.JumpDiffusionVertex (
						terminalDate,
						initialCounterPartyVertex.seniorFundingReplicator(),
						0.,
						false
					),
					org.drip.measure.realization.JumpDiffusionEdgeUnit.Diffusion (
						_ycfWidth,
						unitEvolverSequence[COUNTER_PARTY_FUNDING]
					),
					_ycfWidth
				);

			counterPartyRecoveryRateVertexArray = _counterPartyRecoveryRateEvolver.vertexSequence (
				new org.drip.measure.realization.JumpDiffusionVertex (
					_spotDate,
					initialCounterPartyVertex.seniorRecoveryRate(),
					0.,
					false
				),
				org.drip.measure.realization.JumpDiffusionEdgeUnit.Diffusion (
					_ycfWidth,
					unitEvolverSequence[COUNTER_PARTY_RECOVERY_RATE]
				),
				_ycfWidth
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();

			return null;
		}

		double initialCSANumeraire = csaNumeraireVertexArray[0].value();

		double initialOvernightNumeraire = overnightNumeraireVertexArray[0].value();

		double initialBankSeniorFundingNumeraire = bankSeniorFundingNumeraireVertexArray[0].value();

		double initialBankSubordinateFundingNumeraire = useSingleBankBondOnly ? java.lang.Double.NaN :
			bankSubordinateFundingNumeraireVertexArray[0].value();

		double initialCounterPartyFundingNumeraire = counterPartyFundingNumeraireVertexArray[0].value();

		double csaNumeraireStart = initialCSANumeraire;
		double overnightIndexNumeraireStart = initialOvernightNumeraire;
		double bankSeniorFundingNumeraireStart = initialBankSeniorFundingNumeraire;
		double counterPartyFundingNumeraireStart = initialCounterPartyFundingNumeraire;
		double bankSubordinateFundingNumeraireStart = initialBankSubordinateFundingNumeraire;

		for (int eventVertexIndex = 1; eventVertexIndex <= eventVertexCount; ++eventVertexIndex)
		{
			double bankHazardRate = bankHazardRateVertexArray[eventVertexIndex].value();

			double csaNumeraireFinish =  csaNumeraireVertexArray[eventVertexIndex].value();

			double overnightNumeraireFinish = overnightNumeraireVertexArray[eventVertexIndex].value();

			double counterPartyHazardRateFinish =
				counterPartyHazardRateFinishVertexArray[eventVertexIndex].value();

			double bankSeniorFundingNumeraireFinish =
				bankSeniorFundingNumeraireVertexArray[eventVertexIndex].value();

			double bankSubordinateFundingNumeraireFinish = useSingleBankBondOnly ? java.lang.Double.NaN :
				bankSubordinateFundingNumeraireVertexArray[eventVertexIndex].value();

			double counterPartyFundingNumeraireFinish =
				counterPartyFundingNumeraireVertexArray[eventVertexIndex].value();

			double timeWidth = _ycfWidth[eventVertexIndex - 1];
			double timeWidthReciprocal = 1. / timeWidth;
			bankSurvivalProbabilityExponent += bankHazardRate * timeWidth;
			counterPartySurvivalProbabilityExponent += counterPartyHazardRateFinish * timeWidth;

			double overnightRate = timeWidthReciprocal * java.lang.Math.log
				(overnightNumeraireFinish / initialOvernightNumeraire);

			try
			{
				org.drip.xva.universe.MarketVertexEntity bankMarketVertex = new
					org.drip.xva.universe.MarketVertexEntity (
						java.lang.Math.exp (-1. * bankSurvivalProbabilityExponent),
						bankHazardRate,
						bankSeniorRecoveryRateVertexArray[eventVertexIndex].value(),
						timeWidthReciprocal * java.lang.Math.log (bankSeniorFundingNumeraireFinish /
							initialBankSeniorFundingNumeraire) - overnightRate,
						bankSeniorFundingNumeraireFinish,
						useSingleBankBondOnly ? java.lang.Double.NaN :
							bankSubordinateRecoveryRateVertexArray[eventVertexIndex].value(),
						useSingleBankBondOnly ? java.lang.Double.NaN : timeWidthReciprocal *
							java.lang.Math.log (bankSubordinateFundingNumeraireFinish /
								initialBankSubordinateFundingNumeraire) - overnightRate,
						bankSubordinateFundingNumeraireFinish
					);

				org.drip.xva.universe.MarketVertexEntity counterPartyMarketVertex = new
					org.drip.xva.universe.MarketVertexEntity (
						java.lang.Math.exp (-1. * counterPartySurvivalProbabilityExponent),
						counterPartyHazardRateFinish,
						counterPartyRecoveryRateVertexArray[eventVertexIndex].value(),
						timeWidthReciprocal * java.lang.Math.log (counterPartyFundingNumeraireFinish /
							initialCounterPartyFundingNumeraire) - overnightRate,
						counterPartyFundingNumeraireFinish,
						java.lang.Double.NaN,
						java.lang.Double.NaN,
						java.lang.Double.NaN
					);

				marketVertexArray[eventVertexIndex] = new org.drip.xva.universe.MarketVertex (
					new org.drip.analytics.date.JulianDate (_eventDateArray[eventVertexIndex - 1]),
					positionEvolutionOn ? positionManifestVertexArray[eventVertexIndex].value() :
						java.lang.Double.NaN,
					overnightRate,
					overnightNumeraireFinish,
					timeWidthReciprocal * java.lang.Math.log (csaNumeraireFinish / initialCSANumeraire) -
						overnightRate,
					csaNumeraireFinish,
					bankMarketVertex,
					counterPartyMarketVertex
				);
			}
			catch (java.lang.Exception e)
			{
				e.printStackTrace();

				return null;
			}

			initialCSANumeraire = csaNumeraireFinish;
			initialOvernightNumeraire = overnightNumeraireFinish;
			initialBankSeniorFundingNumeraire = bankSeniorFundingNumeraireFinish;
			initialCounterPartyFundingNumeraire = counterPartyFundingNumeraireFinish;
			initialBankSubordinateFundingNumeraire = bankSubordinateFundingNumeraireFinish;
		}

		try
		{
			marketVertexArray[0] = new org.drip.xva.universe.MarketVertex (
				initialMarketVertex.anchorDate(),
				initialMarketVertex.positionManifestValue(),
				initialMarketVertex.overnightRate(),
				overnightIndexNumeraireStart,
				marketVertexArray[1].csaRate(),
				csaNumeraireStart,
				new org.drip.xva.universe.MarketVertexEntity (
					initialBankVertex.survivalProbability(),
					initialBankVertex.hazardRate(),
					initialBankVertex.seniorRecoveryRate(),
					marketVertexArray[1].bank().seniorFundingSpread(),
					bankSeniorFundingNumeraireStart,
					initialBankVertex.subordinateRecoveryRate(),
					marketVertexArray[1].bank().subordinateFundingSpread(),
					bankSubordinateFundingNumeraireStart
				),
				new org.drip.xva.universe.MarketVertexEntity (
					initialCounterPartyVertex.survivalProbability(),
					initialCounterPartyVertex.hazardRate(),
					initialCounterPartyVertex.seniorRecoveryRate(),
					marketVertexArray[1].counterParty().seniorFundingSpread(),
					counterPartyFundingNumeraireStart,
					java.lang.Double.NaN,
					java.lang.Double.NaN,
					java.lang.Double.NaN
				)
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();

			return null;
		}

		return marketVertexArray;
	}
}
