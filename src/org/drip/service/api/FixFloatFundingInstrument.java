
package org.drip.service.api;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * 
 *  This file is part of DRIP, a free-software/open-source library for fixed income analysts and developers -
 * 		http://www.credit-trader.org/Begin.html
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
 * FixFloatFundingInstrument contains the Fix Float Instrument Inputs for the Funding Curve Construction
 * 	Purposes.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class FixFloatFundingInstrument {
	private int _iLatentStateType = -1;
	private double[] _adblQuote = null;
	private java.lang.String _strCurrency = "";
	private java.lang.String[] _astrMaturityTenor = null;
	private org.drip.state.discount.DiscountCurve _dc = null;
	private org.drip.analytics.date.JulianDate _dtSpot = null;

	/**
	 * FixFloatFundingInstrument Constructor
	 * 
	 * @param dtSpot Spot Date
	 * @param strCurrency Currency
	 * @param astrMaturityTenor Array of the Maturity Tenors
	 * @param adblQuote Array of Quotes
	 * @param iLatentStateType Latent State Type
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public FixFloatFundingInstrument (
		final org.drip.analytics.date.JulianDate dtSpot,
		final java.lang.String strCurrency,
		final java.lang.String[] astrMaturityTenor,
		final double[] adblQuote,
		final int iLatentStateType)
		throws java.lang.Exception
	{
		if (null == (_dc = org.drip.service.template.LatentMarketStateBuilder.FundingCurve (_dtSpot = dtSpot,
			_strCurrency = strCurrency, null, null, "ForwardRate", null, "ForwardRate", _astrMaturityTenor =
				astrMaturityTenor, _adblQuote = adblQuote, "SwapRate", _iLatentStateType =
					iLatentStateType)))
			throw new java.lang.Exception ("FixFloatFundingInstrument Constructor => Invalid Inputs");
	}

	/**
	 * Retrieve the Spot Date
	 * 
	 * @return The Spot Date
	 */

	public org.drip.analytics.date.JulianDate spotDate()
	{
		return _dtSpot;
	}

	/**
	 * Retrieve the Currency
	 * 
	 * @return The Currency
	 */

	public java.lang.String currency()
	{
		return _strCurrency;
	}

	/**
	 * Retrieve the Latent State Type
	 * 
	 * @return The Latent State Type
	 */

	public int latentStateType()
	{
		return _iLatentStateType;
	}

	/**
	 * Retrieve the Array of the Maturity Tenors
	 * 
	 * @return Array of Maturity Tenors
	 */

	public java.lang.String[] maturityTenors()
	{
		return _astrMaturityTenor;
	}

	/**
	 * Retrieve the Array of Quotes
	 * 
	 * @return Array of Quotes
	 */

	public double[] quotes()
	{
		return _adblQuote;
	}

	/**
	 * Retrieve the Funding State
	 * 
	 * @return The Funding State Instance
	 */

	public org.drip.state.discount.DiscountCurve fundingState()
	{
		return _dc;
	}
}
