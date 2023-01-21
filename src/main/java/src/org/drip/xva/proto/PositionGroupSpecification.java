
package org.drip.xva.proto;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
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
 * <i>PositionGroupSpecification</i> contains the Specification of a Named Position Group. The References
 * are:
 *
 *  <br><br>
 *  <ul>
 *  	<li>
 *  		Burgard, C., and M. Kjaer (2014): PDE Representations of Derivatives with Bilateral Counter-party
 *  			Risk and Funding Costs <i>Journal of Credit Risk</i> <b>7 (3)</b> 1-19
 *  	</li>
 *  	<li>
 *  		Burgard, C., and M. Kjaer (2014): In the Balance <i>Risk</i> <b>24 (11)</b> 72-75
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
 *  <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/XVAAnalyticsLibrary.md">XVA Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/README.md">Valuation Adjustments that account for Collateral, CC Credit/Debt and Funding Overhead</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/proto/README.md">Collateral, Counter Party, Netting Groups</a></li>
 *  </ul>
 * <br><br>
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
				org.drip.service.common.StringUtil.GUID(),
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
		if (!org.drip.numerical.common.NumberUtil.IsValid (clientThreshold) || 0. > clientThreshold ||
			!org.drip.numerical.common.NumberUtil.IsValid (dealerThreshold) || 0. < dealerThreshold)
		{
			return null;
		}

		try
		{
			return new PositionGroupSpecification (
				org.drip.service.common.StringUtil.GUID(),
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
			!org.drip.numerical.common.NumberUtil.IsValid (_minimumTransferAmount = minimumTransferAmount) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_independentAmount = independentAmount) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_hedgeError = hedgeError))
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
