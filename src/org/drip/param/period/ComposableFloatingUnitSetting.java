
package org.drip.param.period;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
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
 * ComposableFloatingUnitSetting contains the cash flow periods' composable sub period details.
 *
 * @author Lakshmi Krishnamurthy
 */

public class ComposableFloatingUnitSetting extends org.drip.param.period.ComposableUnitBuilderSetting {
	private int _iReferencePeriodArrearsType = -1;
	private double _dblSpread = java.lang.Double.NaN;
	private org.drip.state.identifier.ForwardLabel _forwardLabel = null;

	/**
	 * ComposableFloatingUnitSetting constructor
	 * 
	 * @param strTenor Unit Tenor
	 * @param iEdgeDateSequenceScheme Edge Date Generation Scheme
	 * @param dapEdge Date Adjust Parameter Settings for the Edge Dates
	 * @param forwardLabel Forward Label
	 * @param iReferencePeriodArrearsType Reference Period Arrears Type
	 * @param dblSpread Floater Spread
	 * 
	 * @throws java.lang.Exception Thrown if Inputs are invalid
	 */

	public ComposableFloatingUnitSetting (
		final java.lang.String strTenor,
		final int iEdgeDateSequenceScheme,
		final org.drip.analytics.daycount.DateAdjustParams dapEdge,
		final org.drip.state.identifier.ForwardLabel forwardLabel,
		final int iReferencePeriodArrearsType,
		final double dblSpread)
		throws java.lang.Exception
	{
		super (strTenor, iEdgeDateSequenceScheme, dapEdge);

		if (null == (_forwardLabel = forwardLabel) || !org.drip.quant.common.NumberUtil.IsValid (_dblSpread =
			dblSpread))
			throw new java.lang.Exception ("ComposableFloatingUnitSetting ctr: Invalid Inputs");

		_iReferencePeriodArrearsType = iReferencePeriodArrearsType;
	}

	/**
	 * Retrieve the Forward Label
	 * 
	 * @return The Forward Label
	 */

	public org.drip.state.identifier.ForwardLabel forwardLabel()
	{
		return _forwardLabel;
	}

	/**
	 * Retrieve the Reference Period Arrears Type
	 * 
	 * @return The Reference Period Arrears Type
	 */

	public int referencePeriodArrearsType()
	{
		return _iReferencePeriodArrearsType;
	}

	/**
	 * Retrieve the Floating Unit Spread
	 * 
	 * @return The Floating Unit Spread
	 */

	public double spread()
	{
		return _dblSpread;
	}
}
