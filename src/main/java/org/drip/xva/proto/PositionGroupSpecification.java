
package org.drip.xva.proto;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2018 Lakshmi Krishnamurthy
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
 * PositionGroupSpecification contains the Specification of a Named Position Group. The References are:
 *  
 *  - Burgard, C., and M. Kjaer (2014): PDE Representations of Derivatives with Bilateral Counter-party Risk
 *  	and Funding Costs, Journal of Credit Risk, 7 (3) 1-19.
 *  
 *  - Burgard, C., and M. Kjaer (2014): In the Balance, Risk, 24 (11) 72-75.
 *  
 *  - Gregory, J. (2009): Being Two-faced over Counter-party Credit Risk, Risk 20 (2) 86-90.
 *  
 *  - Albanese, C., L. Andersen, and, S. Iabichino (2015): The FVA Puzzle: Accounting, Risk Management, and
 *  	Collateral Trading <b>https://papers.ssrn.com/sol3/paper.cfm?abstract_id_2517301</b><br><br>
 * 
 *  - Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives Pricing, Risk
 *  	21 (2) 97-102.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class PositionGroupSpecification extends org.drip.xva.proto.ObjectSpecification
{
	private int _closeOutScheme = -1;
	private int _brokenDateScheme = -1;
	private int _clientDefaultWindow = -1;
	private int _dealerDefaultWindow = -1;
	private int _positionReplicationScheme = -1;
	private double _hedgeError = java.lang.Double.NaN;
	private double _independentAmount = java.lang.Double.NaN;
	private double _minimumTransferAmount = java.lang.Double.NaN;
	private org.drip.function.definition.R1ToR1 _dealerThresholdFunction = null;
	private org.drip.function.definition.R1ToR1[] _clientThresholdFunctionArray = null;

	/**
	 * Generate a Zero-Threshold Instance of the Named Position Group
	 * 
	 * @param name The Collateral Group Name
	 * @param positionReplicationScheme Position Replication Scheme
	 * @param brokenDateScheme Broken Date Interpolation Scheme
	 * @param hedgeError Hedge Error
	 * @param closeOutScheme Close Out Scheme
	 * 
	 * @return The Zero-Threshold Instance of the Named Position Group
	 */

	public static final PositionGroupSpecification ZeroThreshold (
		final java.lang.String name,
		final int positionReplicationScheme,
		final int brokenDateScheme,
		final double hedgeError,
		final int closeOutScheme)
	{
		try
		{
			return new PositionGroupSpecification (
				org.drip.quant.common.StringUtil.GUID(),
				name,
				14,
				14,
				null,
				null,
				0.,
				0.,
				positionReplicationScheme,
				brokenDateScheme,
				hedgeError,
				closeOutScheme
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate a Fixed-Threshold Instance of the Named Position Group
	 * 
	 * @param name The Collateral Group Name
	 * @param clientThreshold The Fixed Client Threshold
	 * @param dealerThreshold The Fixed Dealer Threshold
	 * @param positionReplicationScheme Position Replication Scheme
	 * @param brokenDateScheme Broken Date Interpolation Scheme
	 * @param hedgeError Hedge Error
	 * @param closeOutScheme Close Out Scheme
	 * 
	 * @return The Fixed-Threshold Instance of the Named Position Group
	 */

	public static final PositionGroupSpecification FixedThreshold (
		final java.lang.String name,
		final double clientThreshold,
		final double dealerThreshold,
		final int positionReplicationScheme,
		final int brokenDateScheme,
		final double hedgeError,
		final int closeOutScheme)
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (clientThreshold) || 0. > clientThreshold ||
			!org.drip.quant.common.NumberUtil.IsValid (dealerThreshold) || 0. < dealerThreshold)
		{
			return null;
		}

		try
		{
			return new PositionGroupSpecification (
				org.drip.quant.common.StringUtil.GUID(),
				name,
				14,
				14,
				new org.drip.function.r1tor1.FlatUnivariate[]
				{
					new org.drip.function.r1tor1.FlatUnivariate (clientThreshold)
				},
				new org.drip.function.r1tor1.FlatUnivariate (dealerThreshold),
				0.,
				0.,
				positionReplicationScheme,
				brokenDateScheme,
				hedgeError,
				closeOutScheme
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * PositionGroupSpecification Constructor
	 * 
	 * @param id The Exposure Roll Up Group ID
	 * @param name The Exposure Roll Up Group Name
	 * @param clientDefaultWindow The Client Default Window
	 * @param dealerDefaultWindow The Dealer Default Window
	 * @param clientThresholdFunctionArray The Array of Collateral Group Client Threshold R^1 - R^1 Functions
	 * @param dealerThresholdFunction The Collateral Group Dealer Threshold R^1 - R^1 Function
	 * @param minimumTransferAmount The Collateral Group Minimum Transfer Amount
	 * @param independentAmount The Collateral Group Independent Amount
	 * @param positionReplicationScheme Position Replication Scheme
	 * @param brokenDateScheme Broken Date Interpolation Scheme
	 * @param hedgeError Hedge Error
	 * @param closeOutScheme Close Out Scheme
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public PositionGroupSpecification (
		final java.lang.String id,
		final java.lang.String name,
		final int clientDefaultWindow,
		final int dealerDefaultWindow,
		final org.drip.function.definition.R1ToR1[] clientThresholdFunctionArray,
		final org.drip.function.definition.R1ToR1 dealerThresholdFunction,
		final double minimumTransferAmount,
		final double independentAmount,
		final int positionReplicationScheme,
		final int brokenDateScheme,
		final double hedgeError,
		final int closeOutScheme)
		throws java.lang.Exception
	{
		super (
			id,
			name
		);

		if (-1 >= (_clientDefaultWindow = clientDefaultWindow) ||
			-1 >= (_dealerDefaultWindow = dealerDefaultWindow) ||
			!org.drip.quant.common.NumberUtil.IsValid (_minimumTransferAmount = minimumTransferAmount) ||
			!org.drip.quant.common.NumberUtil.IsValid (_independentAmount = independentAmount) ||
			!org.drip.quant.common.NumberUtil.IsValid (_hedgeError = hedgeError))
		{
			throw new java.lang.Exception ("PositionGroupSpecification Constructor => Invalid Inputs");
		}

		_closeOutScheme = closeOutScheme;
		_brokenDateScheme = brokenDateScheme;
		_dealerThresholdFunction = dealerThresholdFunction;
		_positionReplicationScheme = positionReplicationScheme;
		_clientThresholdFunctionArray = clientThresholdFunctionArray;
	}

	/**
	 * Retrieve the Client Default Window
	 * 
	 * @return The Client Default Window
	 */

	public int clientDefaultWindow()
	{
		return _clientDefaultWindow;
	}

	/**
	 * Retrieve the Dealer Default Window
	 * 
	 * @return The Dealer Default Window
	 */

	public int dealerDefaultWindow()
	{
		return _dealerDefaultWindow;
	}

	/**
	 * Retrieve the Array of the Collateral Group Client Threshold R^1 - R^1 Functions
	 * 
	 * @return The Array of the Collateral Group Client Threshold R^1 - R^1 Functions
	 */

	public org.drip.function.definition.R1ToR1[] clientThresholdFunctionArray()
	{
		return _clientThresholdFunctionArray;
	}

	/**
	 * Retrieve the Collateral Group Dealer Threshold R^1 - R^1 Function
	 * 
	 * @return The Collateral Group Dealer Threshold R^1 - R^1 Function
	 */

	public org.drip.function.definition.R1ToR1 dealerThresholdFunction()
	{
		return _dealerThresholdFunction;
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
	 * Retrieve the Position Replication Scheme
	 * 
	 * @return The Position Replication Scheme
	 */

	public int positionReplicationScheme()
	{
		return _positionReplicationScheme;
	}

	/**
	 * Retrieve the Broken Date Interpolation Scheme
	 * 
	 * @return The Broken Date Interpolation Scheme
	 */

	public int brokenDateScheme()
	{
		return _brokenDateScheme;
	}

	/**
	 * Retrieve the Hedge Error
	 * 
	 * @return The Hedge Error
	 */

	public double hedgeError()
	{
		return _hedgeError;
	}

	/**
	 * Retrieve the Close Out Scheme
	 * 
	 * @return The Close Out Scheme
	 */

	public int closeOutScheme()
	{
		return _closeOutScheme;
	}

	/**
	 * Retrieve the Flag specifying whether the Collateral Group is Uncollateralized
	 * 
	 * @return TRUE - The Collateral Group is Uncollateralized
	 */

	public boolean isUncollateralized()
	{
		return null == _clientThresholdFunctionArray && null == _dealerThresholdFunction;
	}
}
