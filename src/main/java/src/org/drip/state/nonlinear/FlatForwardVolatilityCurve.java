
package org.drip.state.nonlinear;

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
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
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
 * <i>FlatForwardVolatilityCurve</i> manages the Volatility Latent State, using the Forward Volatility as the
 * State Response Representation.
 *
 *  <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/README.md">Latent State Inference and Creation Utilities</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/nonlinear/README.md">Nonlinear (i.e., Boot) Latent State Construction</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class FlatForwardVolatilityCurve extends org.drip.state.volatility.ExplicitBootVolatilityCurve {
	private int[] _aiPillarDate = null;
	private double[] _adblImpliedVolatility = null;

	/**
	 * FlatForwardVolatilityCurve Constructor
	 * 
	 * @param iEpochDate Epoch Date
	 * @param label Volatility Label
	 * @param strCurrency Currency
	 * @param aiPillarDate Array of the Pillar Dates
	 * @param adblImpliedVolatility Array of the corresponding Implied Volatility Nodes
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public FlatForwardVolatilityCurve (
		final int iEpochDate,
		final org.drip.state.identifier.VolatilityLabel label,
		final java.lang.String strCurrency,
		final int[] aiPillarDate,
		final double[] adblImpliedVolatility)
		throws java.lang.Exception
	{
		super (iEpochDate, label, strCurrency);

		if (null == (_aiPillarDate = aiPillarDate) || null == (_adblImpliedVolatility =
			adblImpliedVolatility))
			throw new java.lang.Exception ("FlatForwardVolatilityCurve ctr => Invalid Inputs");

		int iNumPillar = _aiPillarDate.length;

		if (0 == iNumPillar || iNumPillar != _adblImpliedVolatility.length)
			throw new java.lang.Exception ("FlatForwardVolatilityCurve ctr => Invalid Inputs");
	}

	@Override public double impliedVol (
		final int iDate)
		throws java.lang.Exception
	{
		if (iDate <= _aiPillarDate[0]) return _adblImpliedVolatility[0];

		int iNumPillar = _adblImpliedVolatility.length;

		for (int i = 1; i < iNumPillar; ++i) {
			if (_aiPillarDate[i - 1] <= iDate && _aiPillarDate[i] >= iDate)
				return _adblImpliedVolatility[i];
		}

		return _adblImpliedVolatility[iNumPillar - 1];
	}

	@Override public double node (
		final int iDate)
		throws java.lang.Exception
	{
		return 0.;
	}

	@Override public double vol (
		final int iDate)
		throws java.lang.Exception
	{
		return node (iDate);
	}

	@Override public double nodeDerivative (
		final int iDate,
		final int iOrder)
		throws java.lang.Exception
	{
		org.drip.function.definition.R1ToR1 au = new org.drip.function.definition.R1ToR1 (null) {
			@Override public double evaluate (
				double dblX)
				throws java.lang.Exception
			{
				return node ((int) dblX);
			}
		};

		return au.derivative (iDate, iOrder);
	}

	@Override public boolean setNodeValue (
		final int iNodeIndex,
		final double dblValue)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblValue) || iNodeIndex >
			_adblImpliedVolatility.length)
			return false;

		for (int i = iNodeIndex; i < _adblImpliedVolatility.length; ++i)
			_adblImpliedVolatility[i] = dblValue;

		return true;
	}

	@Override public boolean bumpNodeValue (
		final int iNodeIndex,
		final double dblValue)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblValue) || iNodeIndex >
			_adblImpliedVolatility.length)
			return false;

		for (int i = iNodeIndex; i < _adblImpliedVolatility.length; ++i)
			_adblImpliedVolatility[i] += dblValue;

		return true;
	}

	@Override public boolean setFlatValue (
		final double dblValue)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblValue)) return false;

		for (int i = 0; i < _adblImpliedVolatility.length; ++i)
			_adblImpliedVolatility[i] = dblValue;

		return true;
	}
}
