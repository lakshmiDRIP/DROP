
package org.drip.portfolioconstruction.constraint;

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
 * LimitRiskTermMarginal holds the Details of a Relative Marginal Contribution Based Limit Risk Constraint
 *  Term.
 *
 * @author Lakshmi Krishnamurthy
 */

public class LimitRiskTermMarginal extends org.drip.portfolioconstruction.constraint.LimitRiskTerm
{
	private double[] _adblInitialHoldings = null;

	/**
	 * LimitRiskTermMarginal Constructor
	 * 
	 * @param strName Name of the LimitRiskTermMarginal Constraint
	 * @param scope Scope of the LimitRiskTermMarginal Constraint
	 * @param unit Unit of the LimitRiskTermMarginal Constraint
	 * @param dblMinimum Minimum Limit Value of the LimitRiskTermMarginal Constraint
	 * @param dblMaximum Maximum Limit Value of the LimitRiskTermMarginal Constraint
	 * @param aadblAssetCovariance Asset Co-variance
	 * @param adblInitialHoldings Array of the Initial Holdings
	 * 
	 * @throws java.lang.Exception Throw if the Inputs are Invalid
	 */

	public LimitRiskTermMarginal (
		final java.lang.String strName,
		final org.drip.portfolioconstruction.optimizer.Scope scope,
		final org.drip.portfolioconstruction.optimizer.Unit unit,
		final double dblMinimum,
		final double dblMaximum,
		final double[][] aadblAssetCovariance,
		final double[] adblInitialHoldings)
		throws java.lang.Exception
	{
		super (
			strName,
			"CT_LIMIT_MARGINAL_RISK_CONTRIBUTION",
			"Limits the Marginal Contribution to the Total Risk",
			scope,
			unit,
			dblMinimum,
			dblMaximum,
			aadblAssetCovariance
		);

		if (null == (_adblInitialHoldings = adblInitialHoldings) || _adblInitialHoldings.length !=
			aadblAssetCovariance[0].length || !org.drip.quant.common.NumberUtil.IsValid
				(_adblInitialHoldings))
			throw new java.lang.Exception ("LimitRiskTermMarginal Constructor => Invalid Initial Holdings");
	}

	/**
	 * Retrieve the Initial Holdings Array
	 * 
	 * @return The Initial Holdings Array
	 */

	public double[] initialHoldings()
	{
		return _adblInitialHoldings;
	}

	@Override public org.drip.function.definition.RdToR1 rdtoR1()
	{
		return new org.drip.function.definition.RdToR1 (null)
		{
			@Override public int dimension()
			{
				return assetCovariance().length;
			}

			@Override public double evaluate (
				final double[] adblVariate)
				throws java.lang.Exception
			{
				double[][] aadblAssetCovariance = assetCovariance();

				int iNumAsset = aadblAssetCovariance.length;
				double dblMarginalVariance = 0;

				if (null == adblVariate || !org.drip.quant.common.NumberUtil.IsValid (adblVariate) ||
					adblVariate.length != iNumAsset)
					throw new java.lang.Exception
						("LimitRiskTermMarginal::rdToR1::evaluate => Invalid Variate Dimension");

				for (int i = 0; i < iNumAsset; ++i)
				{
					double dblHoldingsDifferentialI = adblVariate[i] - _adblInitialHoldings[i];

					for (int j = 0; j < iNumAsset; ++j)
						dblMarginalVariance += dblHoldingsDifferentialI * aadblAssetCovariance[i][j] *
							(adblVariate[j] - _adblInitialHoldings[j]);
				}

				return dblMarginalVariance;
			}
		};
	}
}
