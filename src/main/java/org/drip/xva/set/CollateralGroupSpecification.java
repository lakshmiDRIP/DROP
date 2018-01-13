
package org.drip.xva.set;

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
 * CollateralGroupSpecification contains the Specifications of a Collateral Group. The References are:
 *  
 *  - Burgard, C., and M. Kjaer (2014): PDE Representations of Derivatives with Bilateral Counter-party Risk
 *  	and Funding Costs, Journal of Credit Risk, 7 (3) 1-19.
 *  
 *  - Burgard, C., and M. Kjaer (2014): In the Balance, Risk, 24 (11) 72-75.
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

public class CollateralGroupSpecification extends org.drip.xva.set.RollUpGroupSpecification
{
	private double _independentAmount = java.lang.Double.NaN;
	private double _minimumTransferAmount = java.lang.Double.NaN;
	private org.drip.function.definition.R1ToR1 _bankThresholdFunction = null;
	private org.drip.function.definition.R1ToR1[] _counterPartyThresholdFunctionArray = null;

	/**
	 * Generate a Zero-Threshold Instance of the Named Collateral Group
	 * 
	 * @param name The Collateral Group Name
	 * 
	 * @return The Zero-Threshold Instance of the Named Collateral Group
	 */

	public static final CollateralGroupSpecification ZeroThreshold (
		final java.lang.String name)
	{
		try
		{
			return new CollateralGroupSpecification (
				org.drip.quant.common.StringUtil.GUID(),
				name,
				null,
				null,
				0.,
				0.
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate a Fixed-Threshold Instance of the Named Collateral Group
	 * 
	 * @param name The Collateral Group Name
	 * @param counterPartyThreshold The Fixed Counter Party Threshold
	 * @param bankThreshold The Fixed Bank Threshold
	 * 
	 * @return The Fixed-Threshold Instance of the Named Collateral Group
	 */

	public static final CollateralGroupSpecification FixedThreshold (
		final java.lang.String name,
		final double counterPartyThreshold,
		final double bankThreshold)
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (counterPartyThreshold) || 0. > counterPartyThreshold ||
			!org.drip.quant.common.NumberUtil.IsValid (bankThreshold) || 0. < bankThreshold)
		{
			return null;
		}

		try
		{
			return new CollateralGroupSpecification (
				org.drip.quant.common.StringUtil.GUID(),
				name,
				new org.drip.function.r1tor1.FlatUnivariate[]
				{
					new org.drip.function.r1tor1.FlatUnivariate (counterPartyThreshold)
				},
				new org.drip.function.r1tor1.FlatUnivariate (bankThreshold),
				0.,
				0.
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * CollateralGroupSpecification Constructor
	 * 
	 * @param id The Collateral Group ID
	 * @param name The Collateral Group Name
	 * @param counterPartyThresholdFunctionArray The Array of Collateral Group Counter Party Threshold R^1 - R^1
	 * 		Functions
	 * @param bankThresholdFunction The Collateral Group Bank Threshold R^1 - R^1 Function
	 * @param minimumTransferAmount The Collateral Group Minimum Transfer Amount
	 * @param independentAmount The Collateral Group Independent Amount
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public CollateralGroupSpecification (
		final java.lang.String id,
		final java.lang.String name,
		final org.drip.function.definition.R1ToR1[] counterPartyThresholdFunctionArray,
		final org.drip.function.definition.R1ToR1 bankThresholdFunction,
		final double minimumTransferAmount,
		final double independentAmount)
		throws java.lang.Exception
	{
		super (
			id,
			name
		);

		if (!org.drip.quant.common.NumberUtil.IsValid (_minimumTransferAmount = minimumTransferAmount) ||
			!org.drip.quant.common.NumberUtil.IsValid (_independentAmount = independentAmount))
		{
			throw new java.lang.Exception ("CollateralGroupSpecification Constructor => Invalid Inputs");
		}

		_bankThresholdFunction = bankThresholdFunction;
		_counterPartyThresholdFunctionArray = counterPartyThresholdFunctionArray;
	}

	/**
	 * Retrieve the Array of the Collateral Group Counter Party Threshold R^1 - R^1 Functions
	 * 
	 * @return The Array of the Collateral Group Counter Party Threshold R^1 - R^1 Functions
	 */

	public org.drip.function.definition.R1ToR1[] counterPartyThreshold()
	{
		return _counterPartyThresholdFunctionArray;
	}

	/**
	 * Retrieve the Collateral Group Bank Threshold R^1 - R^1 Function
	 * 
	 * @return The Collateral Group Bank Threshold R^1 - R^1 Function
	 */

	public org.drip.function.definition.R1ToR1 bankThreshold()
	{
		return _bankThresholdFunction;
	}

	/**
	 * Retrieve the Collateral Group Minimum Transfer Amount
	 * 
	 * @return The Collateral Group Minimum Transfer Amount
	 */

	public double minimumTransferAmount()
	{
		return _minimumTransferAmount;
	}

	/**
	 * Retrieve the Collateral Group Independent Amount
	 * 
	 * @return The Collateral Group Independent Amount
	 */

	public double independentAmount()
	{
		return _independentAmount;
	}

	/**
	 * Retrieve the Flag specifying whether the Collateral Group is Uncollateralized
	 * 
	 * @return TRUE - The Collateral Group is Uncollateralized
	 */

	public boolean isUncollateralized()
	{
		return null == _counterPartyThresholdFunctionArray && null == _bankThresholdFunction;
	}
}
