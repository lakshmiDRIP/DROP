
package org.drip.state.nonlinear;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting risk, transaction costs, exposure, margin
 *  	calculations, and portfolio construction within and across fixed income, credit, commodity, equity,
 *  	FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three main modules:
 *  
 *  - DROP Analytics Core - https://lakshmidrip.github.io/DROP-Analytics-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Numerical Core - https://lakshmidrip.github.io/DROP-Numerical-Core/
 * 
 * 	DROP Analytics Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Asset Backed Analytics
 * 	- XVA Analytics
 * 	- Exposure and Margin Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Numerical Core implements libraries for the following:
 * 	- Statistical Learning Library
 * 	- Numerical Optimizer Library
 * 	- Machine Learning Library
 * 	- Spline Builder Library
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
 * 	- Javadoc                  => https://lakshmidrip.github.io/DROP/Javadoc/index.html
 * 	- Technical Specifications => https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal
 * 	- Release Versions         => https://lakshmidrip.github.io/DROP/version.html
 * 	- Community Credits        => https://lakshmidrip.github.io/DROP/credits.html
 * 	- Issues Catalog           => https://github.com/lakshmiDRIP/DROP/issues
 * 	- JUnit                    => https://lakshmidrip.github.io/DROP/junit/index.html
 * 	- Jacoco                   => https://lakshmidrip.github.io/DROP/jacoco/index.html
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
 *		<li><b>Project</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state">State</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/nonlinear">Non Linear</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/FixedIncome">Fixed Income Analytics Library</a></li>
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
		if (!org.drip.quant.common.NumberUtil.IsValid (dblValue) || iNodeIndex >
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
		if (!org.drip.quant.common.NumberUtil.IsValid (dblValue) || iNodeIndex >
			_adblImpliedVolatility.length)
			return false;

		for (int i = iNodeIndex; i < _adblImpliedVolatility.length; ++i)
			_adblImpliedVolatility[i] += dblValue;

		return true;
	}

	@Override public boolean setFlatValue (
		final double dblValue)
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblValue)) return false;

		for (int i = 0; i < _adblImpliedVolatility.length; ++i)
			_adblImpliedVolatility[i] = dblValue;

		return true;
	}
}
