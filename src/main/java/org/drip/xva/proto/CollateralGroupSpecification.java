
package org.drip.xva.proto;

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

public class CollateralGroupSpecification extends org.drip.xva.proto.ExposureGroupSpecification
{
	private int _closeOutScheme = -1;
	private int _brokenDateScheme = -1;
	private int _bankDefaultWindow = -1;
	private int _counterPartyDefaultWindow = -1;
	private int _positionReplicationScheme = -1;
	private double _hedgeError = java.lang.Double.NaN;
	private double _independentAmount = java.lang.Double.NaN;
	private org.drip.state.identifier.CSALabel _csaLabel = null;
	private double _minimumTransferAmount = java.lang.Double.NaN;
	private org.drip.function.definition.R1ToR1 _bankThresholdFunction = null;
	private org.drip.function.definition.R1ToR1[] _counterPartyThresholdFunctionArray = null;

	/**
	 * Generate a Zero-Threshold Instance of the Named Collateral Group
	 * 
	 * @param name The Collateral Group Name
	 * @param overnightLabel The Overnight Latent State Label
	 * @param csaLabel The CSA Latent State Label
	 * @param positionReplicationScheme Position Replication Scheme
	 * @param brokenDateScheme Broken Date Interpolation Scheme
	 * @param hedgeError Hedge Error
	 * @param closeOutScheme Close Out Scheme
	 * 
	 * @return The Zero-Threshold Instance of the Named Collateral Group
	 */

	public static final CollateralGroupSpecification ZeroThreshold (
		final java.lang.String name,
		final org.drip.state.identifier.OvernightLabel overnightLabel,
		final org.drip.state.identifier.CSALabel csaLabel,
		final int positionReplicationScheme,
		final int brokenDateScheme,
		final double hedgeError,
		final int closeOutScheme)
	{
		try
		{
			return new CollateralGroupSpecification (
				org.drip.quant.common.StringUtil.GUID(),
				name,
				overnightLabel,
				csaLabel,
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
	 * Generate a Fixed-Threshold Instance of the Named Collateral Group
	 * 
	 * @param name The Collateral Group Name
	 * @param overnightLabel The Overnight Latent State Label
	 * @param csaLabel The CSA Latent State Label
	 * @param counterPartyThreshold The Fixed Counter Party Threshold
	 * @param bankThreshold The Fixed Bank Threshold
	 * @param positionReplicationScheme Position Replication Scheme
	 * @param brokenDateScheme Broken Date Interpolation Scheme
	 * @param hedgeError Hedge Error
	 * @param closeOutScheme Close Out Scheme
	 * 
	 * @return The Fixed-Threshold Instance of the Named Collateral Group
	 */

	public static final CollateralGroupSpecification FixedThreshold (
		final java.lang.String name,
		final org.drip.state.identifier.OvernightLabel overnightLabel,
		final org.drip.state.identifier.CSALabel csaLabel,
		final double counterPartyThreshold,
		final double bankThreshold,
		final int positionReplicationScheme,
		final int brokenDateScheme,
		final double hedgeError,
		final int closeOutScheme)
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
				overnightLabel,
				csaLabel,
				14,
				14,
				new org.drip.function.r1tor1.FlatUnivariate[]
				{
					new org.drip.function.r1tor1.FlatUnivariate (counterPartyThreshold)
				},
				new org.drip.function.r1tor1.FlatUnivariate (bankThreshold),
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
	 * CollateralGroupSpecification Constructor
	 * 
	 * @param id The Collateral Group ID
	 * @param name The Collateral Group Name
	 * @param overnightLabel The Overnight Latent State Label
	 * @param csaLabel The CSA Latent State Label
	 * @param counterPartyDefaultWindow The Counter Party Default Window
	 * @param bankDefaultWindow The Bank Default Window
	 * @param counterPartyThresholdFunctionArray The Array of Collateral Group Counter Party Threshold R^1 - R^1
	 * 		Functions
	 * @param bankThresholdFunction The Collateral Group Bank Threshold R^1 - R^1 Function
	 * @param minimumTransferAmount The Collateral Group Minimum Transfer Amount
	 * @param independentAmount The Collateral Group Independent Amount
	 * @param positionReplicationScheme Position Replication Scheme
	 * @param brokenDateScheme Broken Date Interpolation Scheme
	 * @param hedgeError Hedge Error
	 * @param closeOutScheme Close Out Scheme
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public CollateralGroupSpecification (
		final java.lang.String id,
		final java.lang.String name,
		final org.drip.state.identifier.OvernightLabel overnightLabel,
		final org.drip.state.identifier.CSALabel csaLabel,
		final int counterPartyDefaultWindow,
		final int bankDefaultWindow,
		final org.drip.function.definition.R1ToR1[] counterPartyThresholdFunctionArray,
		final org.drip.function.definition.R1ToR1 bankThresholdFunction,
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
			name,
			overnightLabel
		);

		if (null == (_csaLabel = csaLabel) ||
			-1 >= (_counterPartyDefaultWindow = counterPartyDefaultWindow) ||
			-1 >= (_bankDefaultWindow = bankDefaultWindow) ||
			!org.drip.quant.common.NumberUtil.IsValid (_minimumTransferAmount = minimumTransferAmount) ||
			!org.drip.quant.common.NumberUtil.IsValid (_independentAmount = independentAmount) ||
			!org.drip.quant.common.NumberUtil.IsValid (_hedgeError = hedgeError))
		{
			throw new java.lang.Exception ("CollateralGroupSpecification Constructor => Invalid Inputs");
		}

		_closeOutScheme = closeOutScheme;
		_brokenDateScheme = brokenDateScheme;
		_bankThresholdFunction = bankThresholdFunction;
		_positionReplicationScheme = positionReplicationScheme;
		_counterPartyThresholdFunctionArray = counterPartyThresholdFunctionArray;
	}

	/**
	 * Retrieve the CSA Label
	 * 
	 * @return The CSA Label
	 */

	public org.drip.state.identifier.CSALabel csaLabel()
	{
		return _csaLabel;
	}

	/**
	 * Retrieve the Counter Party Default Window
	 * 
	 * @return The Counter Party Default Window
	 */

	public int counterPartyDefaultWindow()
	{
		return _counterPartyDefaultWindow;
	}

	/**
	 * Retrieve the Bank Default Window
	 * 
	 * @return The Bank Default Window
	 */

	public int bankDefaultWindow()
	{
		return _bankDefaultWindow;
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
		return null == _counterPartyThresholdFunctionArray && null == _bankThresholdFunction;
	}
}
