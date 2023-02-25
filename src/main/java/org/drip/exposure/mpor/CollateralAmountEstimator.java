
package org.drip.exposure.mpor;

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
 * <i>CollateralAmountEstimator</i> estimates the Amount of Collateral Hypothecation that is to be Posted
 * during a Single Run of a Collateral Hypothecation Group Valuation. The References are:
 *  
 * <br><br>
 *  	<ul>
 *  		<li>
 *  			Burgard, C., and M. Kjaer (2014): PDE Representations of Derivatives with Bilateral Counter-
 *  				party Risk and Funding Costs <i>Journal of Credit Risk</i> <b>7 (3)</b> 1-19
 *  		</li>
 *  		<li>
 *  			Burgard, C., and M. Kjaer (2014): In the Balance, Risk, 24 (11) 72-75
 *  		</li>
 *  		<li>
 *  			Gregory, J. (2009): Being Two-faced over Counter-party Credit Risk <i>Risk</i> <b>20 (2)</b>
 *  				86-90
 *  		</li>
 *  		<li>
 *  			Li, B., and Y. Tang (2007): Quantitative Analysis, Derivatives Modeling, and Trading
 *  				Strategies in the Presence of Counter-party Credit Risk for the Fixed Income Market
 *  				<i>World Scientific Publishing </i> <b>Singapore</b>
 *  		</li>
 *  		<li>
 *  			Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives
 *  				Pricing <i>Risk</i> <b>21 (2)</b> 97-102
 *  		</li>
 *  		<li>
 *  	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ExposureAnalyticsLibrary.md">Exposure Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/README.md">Exposure Group Level Collateralized/Uncollateralized Exposure</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/mpor/README.md">Margin Period Collateral Amount Estimation</a></li>
 *  </ul>
 *  
 * @author Lakshmi Krishnamurthy
 */

public class CollateralAmountEstimator
{
	private double _currentBalance = java.lang.Double.NaN;
	private org.drip.measure.bridge.BrokenDateInterpolator _brokenDateInterpolator = null;
	private org.drip.xva.proto.PositionGroupSpecification _positionGroupSpecification = null;

	/**
	 * CollateralAmountEstimator Constructor
	 * 
	 * @param positionGroupSpecification The Position Group Specification
	 * @param brokenDateInterpolator The Stochastic Value Broken Date Bridge Estimator
	 * @param currentBalance The Current Collateral Balance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public CollateralAmountEstimator (
		final org.drip.xva.proto.PositionGroupSpecification positionGroupSpecification,
		final org.drip.measure.bridge.BrokenDateInterpolator brokenDateInterpolator,
		final double currentBalance)
		throws java.lang.Exception
	{
		if (null == (_positionGroupSpecification = positionGroupSpecification) ||
			null == (_brokenDateInterpolator = brokenDateInterpolator))
		{
			throw new java.lang.Exception ("CollateralAmountEstimator Constructor => Invalid Inputs");
		}

		_currentBalance = currentBalance;
	}

	/**
	 * Retrieve the Position Group Specification
	 * 
	 * @return The Position Group Specification
	 */

	public org.drip.xva.proto.PositionGroupSpecification positionGroupSpecification()
	{
		return _positionGroupSpecification;
	}

	/**
	 * Retrieve the Stochastic Value Broken Date Bridge Estimator
	 * 
	 * @return The Stochastic Value Broken Date Bridge Estimator
	 */

	public org.drip.measure.bridge.BrokenDateInterpolator brokenDateBridge()
	{
		return _brokenDateInterpolator;
	}

	/**
	 * Retrieve the Current Collateral Balance
	 * 
	 * @return The Current Collateral Balance
	 */

	public double currentCollateralBalance()
	{
		return _currentBalance;
	}

	/**
	 * Calculate the Margin Value at the Dealer Default Window
	 * 
	 * @param valuationDateJulian The Valuation Date
	 * 
	 * @return The Margin Value at the Dealer Default Window
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double dealerWindowMarginValue (
		final org.drip.analytics.date.JulianDate valuationDateJulian)
		throws java.lang.Exception
	{
		if (null == valuationDateJulian)
		{
			throw new java.lang.Exception
				("CollateralAmountEstimator::dealerWindowMarginValue => Invalid Inputs");
		}

		org.drip.analytics.date.JulianDate marginDate = valuationDateJulian.subtractDays
			(_positionGroupSpecification.dealerDefaultWindow());

		if (null == marginDate)
		{
			throw new java.lang.Exception
				("CollateralAmountEstimator::dealerWindowMarginValue => Invalid Inputs");
		}

		return _brokenDateInterpolator.interpolate (marginDate.julian());
	}

	/**
	 * Calculate the Dealer Margin Threshold
	 * 
	 * @param valuationDateJulian The Valuation Date
	 * 
	 * @return The Dealer Margin Threshold
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double dealerThreshold (
		final org.drip.analytics.date.JulianDate valuationDateJulian)
		throws java.lang.Exception
	{
		org.drip.function.definition.R1ToR1 dealerThresholdFunction =
			_positionGroupSpecification.dealerThresholdFunction();

		return null == dealerThresholdFunction ? 0. : dealerThresholdFunction.evaluate
			(valuationDateJulian.julian());
	}

	/**
	 * Calculate the Margin Amount Required to be Posted by the Dealer
	 * 
	 * @param valuationDateJulian The Valuation Date
	 * 
	 * @return The Margin Amount Required to be Posted by the Dealer
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double dealerPostingRequirement (
		final org.drip.analytics.date.JulianDate valuationDateJulian)
		throws java.lang.Exception
	{
		double dealerPostingRequirement = dealerWindowMarginValue (valuationDateJulian) - dealerThreshold
			(valuationDateJulian);

		return 0. < dealerPostingRequirement ? 0. : dealerPostingRequirement;
	}

	/**
	 * Calculate the Margin Value at the Client Default Window
	 * 
	 * @param valuationDateJulian The Valuation Date
	 * 
	 * @return The Margin Value at the Client Default Window
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double clientWindowMarginValue (
		final org.drip.analytics.date.JulianDate valuationDateJulian)
		throws java.lang.Exception
	{
		if (null == valuationDateJulian)
		{
			throw new java.lang.Exception
				("CollateralAmountEstimator::clientWindowMarginValue => Invalid Inputs");
		}

		org.drip.analytics.date.JulianDate marginDate = valuationDateJulian.subtractDays
			(_positionGroupSpecification.clientDefaultWindow());

		if (null == marginDate)
		{
			throw new java.lang.Exception
				("CollateralAmountEstimator::clientWindowMarginValue => Invalid Inputs");
		}

		return _brokenDateInterpolator.interpolate (marginDate.julian());
	}

	/**
	 * Calculate the Client Margin Threshold
	 * 
	 * @param valuationDateJulian The Valuation Date
	 * 
	 * @return The Client Margin Threshold
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double clientThreshold (
		final org.drip.analytics.date.JulianDate valuationDateJulian)
		throws java.lang.Exception
	{
		org.drip.function.definition.R1ToR1[] clientThresholdFunctionArray =
			_positionGroupSpecification.clientThresholdFunctionArray();

		return null == clientThresholdFunctionArray || null == clientThresholdFunctionArray[0] ? 0. :
			clientThresholdFunctionArray[0].evaluate (valuationDateJulian.julian());
	}

	/**
	 * Calculate the Margin Amount Required to be Posted by the Client
	 * 
	 * @param valuationDateJulian The Valuation Date
	 * 
	 * @return The Margin Amount Required to be Posted by the Client
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double clientPostingRequirement (
		final org.drip.analytics.date.JulianDate valuationDateJulian)
		throws java.lang.Exception
	{
		double clientPostingRequirement = clientWindowMarginValue (valuationDateJulian) - clientThreshold
			(valuationDateJulian);

		return 0. > clientPostingRequirement ? 0. : clientPostingRequirement;
	}

	/**
	 * Calculate the Gross Margin Amount Required to be Posted
	 * 
	 * @param valuationDateJulian The Valuation Date
	 * 
	 * @return The Gross Margin Amount Required to be Posted
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double postingRequirement (
		final org.drip.analytics.date.JulianDate valuationDateJulian)
		throws java.lang.Exception
	{
		return org.drip.numerical.common.NumberUtil.IsValid (_currentBalance) ? _currentBalance :
			dealerPostingRequirement (valuationDateJulian) + clientPostingRequirement (valuationDateJulian);
	}

	/**
	 * Generate the MarginAmountEstimatorOutput Instance
	 * 
	 * @param valuationDateJulian The Valuation Date
	 * 
	 * @return The MarginAmountEstimatorOutput Instance
	 */

	public org.drip.exposure.mpor.CollateralAmountEstimatorOutput output (
		final org.drip.analytics.date.JulianDate valuationDateJulian)
	{
		if (null == valuationDateJulian)
		{
			return null;
		}

		org.drip.analytics.date.JulianDate dealerMarginDate = valuationDateJulian.subtractDays
			(_positionGroupSpecification.dealerDefaultWindow());

		org.drip.analytics.date.JulianDate clientMarginDate = valuationDateJulian.subtractDays
			(_positionGroupSpecification.clientDefaultWindow());

		if (null == dealerMarginDate ||
			null == clientMarginDate)
		{
			return null;
		}

		org.drip.function.definition.R1ToR1[] clientThresholdFunctionArray =
			_positionGroupSpecification.clientThresholdFunctionArray();

		org.drip.function.definition.R1ToR1 dealerThresholdFunction =
			_positionGroupSpecification.dealerThresholdFunction();

		double valuationDate = valuationDateJulian.julian();

		try
		{
			double dealerWindowMarginValue = _brokenDateInterpolator.interpolate (dealerMarginDate.julian());

			double clientWindowMarginValue = _brokenDateInterpolator.interpolate (clientMarginDate.julian());

			double dealerThresholdValue = null == dealerThresholdFunction ? 0. :
				dealerThresholdFunction.evaluate (valuationDate);

			double clientThresholdValue = null == clientThresholdFunctionArray || null ==
				clientThresholdFunctionArray[0] ? 0. : clientThresholdFunctionArray[0].evaluate
					(valuationDate);

			double dealerPostingRequirement = dealerWindowMarginValue - dealerThresholdValue;
			dealerPostingRequirement = 0. < dealerPostingRequirement ? 0. : dealerPostingRequirement;
			double clientPostingRequirement = clientWindowMarginValue - clientThresholdValue;
			clientPostingRequirement = 0. > clientPostingRequirement ? 0. : clientPostingRequirement;

			return new org.drip.exposure.mpor.CollateralAmountEstimatorOutput (
				dealerMarginDate,
				clientMarginDate,
				dealerWindowMarginValue,
				dealerThresholdValue,
				dealerPostingRequirement,
				clientWindowMarginValue,
				clientThresholdValue,
				clientPostingRequirement,
				org.drip.numerical.common.NumberUtil.IsValid (_currentBalance) ? _currentBalance :
					dealerPostingRequirement + clientPostingRequirement);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
