
package org.drip.service.api;

import org.drip.analytics.date.JulianDate;
import org.drip.state.discount.DiscountCurve;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2025 Lakshmi Krishnamurthy
 * Copyright (C) 2024 Lakshmi Krishnamurthy
 * Copyright (C) 2023 Lakshmi Krishnamurthy
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
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
 * <i>FixFloatFundingInstrument</i> contains the Fix Float Instrument Inputs for the Funding Curve
 * 	Construction Purposes. It provides the following Functions:
 * 	<ul>
 * 	</ul>
 *
 * <br>
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationSupportLibrary.md">Computation Support</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/README.md">Environment, Product/Definition Containers, and Scenario/State Manipulation APIs</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/api/README.md">Horizon Roll Attribution Service API</a></td></tr>
 *  </table>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class FixFloatFundingInstrument
{
	private int _latentStateType = -1;
	private double[] _adblQuote = null;
	private String _strCurrency = "";
	private String[] _astrMaturityTenor = null;
	private DiscountCurve _dc = null;
	private JulianDate _dtSpot = null;

	/**
	 * FixFloatFundingInstrument Constructor
	 * 
	 * @param dtSpot Spot Date
	 * @param strCurrency Currency
	 * @param astrMaturityTenor Array of the Maturity Tenors
	 * @param adblQuote Array of Quotes
	 * @param iLatentStateType Latent State Type
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public FixFloatFundingInstrument (
		final JulianDate dtSpot,
		final String strCurrency,
		final String[] astrMaturityTenor,
		final double[] adblQuote,
		final int iLatentStateType)
		throws Exception
	{
		if (null == (_dc = org.drip.service.template.LatentMarketStateBuilder.FundingCurve (_dtSpot = dtSpot,
			_strCurrency = strCurrency, null, null, "ForwardRate", null, "ForwardRate", _astrMaturityTenor =
				astrMaturityTenor, _adblQuote = adblQuote, "SwapRate", _latentStateType =
					iLatentStateType)))
			throw new Exception ("FixFloatFundingInstrument Constructor => Invalid Inputs");
	}

	/**
	 * Retrieve the Spot Date
	 * 
	 * @return The Spot Date
	 */

	public JulianDate spotDate()
	{
		return _dtSpot;
	}

	/**
	 * Retrieve the Currency
	 * 
	 * @return The Currency
	 */

	public String currency()
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
		return _latentStateType;
	}

	/**
	 * Retrieve the Array of the Maturity Tenors
	 * 
	 * @return Array of Maturity Tenors
	 */

	public String[] maturityTenors()
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

	public DiscountCurve fundingState()
	{
		return _dc;
	}
}
