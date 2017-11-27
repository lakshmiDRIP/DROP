
package org.drip.portfolioconstruction.objective;

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
 * FixedChargeSellTerm implements the Objective Term that optimizes the Charge incurred by the Sell Trades in
 *  the Target Portfolio under a Fixed Charge from the Starting Allocation.
 *
 * @author Lakshmi Krishnamurthy
 */

public class FixedChargeSellTerm extends org.drip.portfolioconstruction.objective.TransactionChargeTerm
{

	/**
	 * FixedChargeSellTerm Conastructor
	 * 
	 * @param strName Name of the Objective Term
	 * @param adblInitialHoldings Initial Holdings
	 * @param aTCF Array of Asset Transaction Charge Instances
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public FixedChargeSellTerm (
		final java.lang.String strName,
		final double[] adblInitialHoldings,
		final org.drip.portfolioconstruction.cost.TransactionChargeFixed[] aTCF)
		throws java.lang.Exception
	{
		super (
			strName,
			"OT_FIXED_SELL_CHARGE",
			"Fixed Charge Sell Only Transaction Charge Objective Function",
			adblInitialHoldings,
			aTCF
		);
	}

	@Override public org.drip.function.definition.RdToR1 rdtoR1()
	{
		return new org.drip.function.definition.RdToR1 (null)
		{
			@Override public int dimension()
			{
				return initialHoldings().length;
			}

			@Override public double evaluate (
				final double[] adblVariate)
				throws java.lang.Exception
			{
				if (null == adblVariate || !org.drip.quant.common.NumberUtil.IsValid (adblVariate))
					throw new java.lang.Exception ("FixedChargeSellTerm::rdToR1::evaluate => Invalid Input");

				org.drip.portfolioconstruction.cost.TransactionChargeFixed[] aTCF =
					(org.drip.portfolioconstruction.cost.TransactionChargeFixed[]) transactionCharge();

				double[] adblInitialHoldings = initialHoldings();

				int iNumAsset = aTCF.length;
				double dblFixedChargeSellTerm = 0.;

				if (adblVariate.length != iNumAsset)
					throw new java.lang.Exception
						("FixedChargeSellTerm::rdToR1::evaluate => Invalid Variate Dimension");

				for (int i = 0; i < iNumAsset; ++i) {
					if (adblVariate[i] < adblInitialHoldings[i])
						dblFixedChargeSellTerm += aTCF[i].estimate (
							adblInitialHoldings[i],
							adblVariate[i]
						);
				}

				return dblFixedChargeSellTerm;
			}
		};
	}
}
