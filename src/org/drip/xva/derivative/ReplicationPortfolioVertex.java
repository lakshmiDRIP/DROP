
package org.drip.xva.derivative;

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
 * ReplicationPortfolioVertex contains the Dynamic Replicating Portfolio of the Pay-out using the Assets in
 *  the Economy, from the Bank's View Point. The References are:
 *  
 *  - Burgard, C., and M. Kjaer (2014): PDE Representations of Derivatives with Bilateral Counter-party Risk
 *  	and Funding Costs, Journal of Credit Risk, 7 (3) 1-19.
 *  
 *  - Cesari, G., J. Aquilina, N. Charpillon, X. Filipovic, G. Lee, and L. Manda (2009): Modeling, Pricing,
 *  	and Hedging Counter-party Credit Exposure - A Technical Guide, Springer Finance, New York.
 *  
 *  - Gregory, J. (2009): Being Two-faced over Counter-party Credit Risk, Risk 20 (2) 86-90.
 *  
 *  - Li, B., and Y. Tang (2007): Quantitative Analysis, Derivatives Modeling, and Trading Strategies in the
 *  	Presence of Counter-party Credit Risk for the Fixed Income Market, World Scientific Publishing,
 *  	Singapore.
 * 
 *  - Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives Pricing, Risk
 *  	21 (2) 97-102.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class ReplicationPortfolioVertex {
	private double _dblCashAccount = java.lang.Double.NaN;
	private double _dblAssetNumeraireUnits = java.lang.Double.NaN;
	private double _dblBankSeniorNumeraireUnits = java.lang.Double.NaN;
	private double _dblCounterPartyNumeraireUnits = java.lang.Double.NaN;
	private double _dblBankSubordinateNumeraireUnits = java.lang.Double.NaN;

	/**
	 * Construct a ReplicationPortfolioVertex Instance without the Zero Recovery Bank Numeraire
	 * 
	 * @param dblAssetNumeraireUnits The Asset Numeraire Units
	 * @param dblBankSeniorNumeraireUnits The Bank Senior Numeraire Units
	 * @param dblCounterPartyNumeraireUnits The Counter Party Numeraire Replication Units
	 * @param dblCashAccount The Cash Account
	 * 
	 * @return The ReplicationPortfolioVertex Instance without the Zero Recovery Bank Numeraire
	 */

	public static final ReplicationPortfolioVertex Standard (
		final double dblAssetNumeraireUnits,
		final double dblBankSeniorNumeraireUnits,
		final double dblCounterPartyNumeraireUnits,
		final double dblCashAccount)
	{
		try {
			return new ReplicationPortfolioVertex (dblAssetNumeraireUnits, dblBankSeniorNumeraireUnits, 0.,
				dblCounterPartyNumeraireUnits, dblCashAccount);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * ReplicationPortfolioVertex Constructor
	 * 
	 * @param dblAssetNumeraireUnits The Asset Numeraire Units
	 * @param dblBankSeniorNumeraireUnits The Bank Senior Numeraire Units
	 * @param dblBankSubordinateNumeraireUnits The Bank Subordinate Numeraire Units
	 * @param dblCounterPartyNumeraireUnits The Counter Party Numeraire Units
	 * @param dblCashAccount The Cash Account
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public ReplicationPortfolioVertex (
		final double dblAssetNumeraireUnits,
		final double dblBankSeniorNumeraireUnits,
		final double dblBankSubordinateNumeraireUnits,
		final double dblCounterPartyNumeraireUnits,
		final double dblCashAccount)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (_dblBankSeniorNumeraireUnits =
			dblBankSeniorNumeraireUnits) || !org.drip.quant.common.NumberUtil.IsValid
				(_dblBankSubordinateNumeraireUnits = dblBankSubordinateNumeraireUnits) ||
					!org.drip.quant.common.NumberUtil.IsValid (_dblCounterPartyNumeraireUnits =
						dblCounterPartyNumeraireUnits) || !org.drip.quant.common.NumberUtil.IsValid
							(_dblCashAccount = dblCashAccount))
			throw new java.lang.Exception ("ReplicationPortfolioVertex Constructor => Invalid Inputs");

		_dblAssetNumeraireUnits = dblAssetNumeraireUnits;
	}

	/**
	 * Retrieve the Number of Asset Numeraire Units
	 * 
	 * @return The Number of Asset Numeraire Units
	 */

	public double assetNumeraireUnits()
	{
		return _dblAssetNumeraireUnits;
	}

	/**
	 * Retrieve the Number of Bank Senior Numeraire Units
	 * 
	 * @return The Number of Bank Senior Numeraire Units
	 */

	public double bankSeniorNumeraireUnits()
	{
		return _dblBankSeniorNumeraireUnits;
	}

	/**
	 * Retrieve the Number of Bank Subordinate Numeraire Units
	 * 
	 * @return The Number of Bank Subordinate Numeraire Units
	 */

	public double bankSubordinateNumeraireUnits()
	{
		return _dblBankSubordinateNumeraireUnits;
	}

	/**
	 * Retrieve the Counter Party Numeraire Units
	 * 
	 * @return The Counter Party Numeraire Units
	 */

	public double counterPartyNumeraireUnits()
	{
		return _dblCounterPartyNumeraireUnits;
	}

	/**
	 * Retrieve the Cash Account Amount
	 * 
	 * @return The Cash Account Amount
	 */

	public double cashAccount()
	{
		return _dblCashAccount;
	}

	/**
	 * Compute the Market Value of the Bank Position Pre-Default
	 * 
	 * @param mv The Market Vertex
	 * 
	 * @return The Market Value of the Bank Position Pre-Default
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double bankPreDefaultPositionValue (
		final org.drip.xva.universe.MarketVertex mv)
		throws java.lang.Exception
	{
		if (null == mv)
			throw new java.lang.Exception
				("ReplicationPortfolioVertex::bankPreDefaultPositionValue => Invalid Inputs");

		org.drip.xva.universe.EntityMarketVertex emvBank = mv.bank();

		double dblValue = -1. * emvBank.seniorFundingNumeraire().forward() * _dblBankSeniorNumeraireUnits;

		org.drip.xva.universe.NumeraireMarketVertex nmvBankSubordinateFunding =
			emvBank.subordinateFundingNumeraire();

		if (null != nmvBankSubordinateFunding)
			dblValue -= nmvBankSubordinateFunding.forward() * _dblBankSubordinateNumeraireUnits;

		return dblValue;
	}

	/**
	 * Compute the Market Value of the Bank Position Post-Default
	 * 
	 * @param mv The Market Vertex
	 * 
	 * @return The Market Value of the Bank Position Post-Default
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double bankPostDefaultPositionValue (
		final org.drip.xva.universe.MarketVertex mv)
		throws java.lang.Exception
	{
		if (null == mv)
			throw new java.lang.Exception
				("ReplicationPortfolioVertex::bankPostDefaultPositionValue => Invalid Inputs");

		org.drip.xva.universe.EntityMarketVertex emvBank = mv.bank();

		double dblValue = emvBank.seniorFundingNumeraire().forward() * _dblBankSeniorNumeraireUnits *
			emvBank.seniorRecoveryRate();

		org.drip.xva.universe.NumeraireMarketVertex nmvBankSubordinateFunding =
			emvBank.subordinateFundingNumeraire();

		if (null != nmvBankSubordinateFunding)
			dblValue -= nmvBankSubordinateFunding.forward() * _dblBankSubordinateNumeraireUnits *
				emvBank.subordinateRecoveryRate();

		return dblValue;
	}
}
