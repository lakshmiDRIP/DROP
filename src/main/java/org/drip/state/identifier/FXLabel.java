
package org.drip.state.identifier;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
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
 * <i>FXLabel</i> contains the Identifier Parameters referencing the Latent State of the named FX Curve.
 * Currently it only contains the FX Code.
 *
 *  <br><br>
 *  <ul>
 *		<li><b>Project</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state">State</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/identifier">Identifier</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/FixedIncome">Fixed Income Analytics Library</a></li>
 *  </ul>
 * <br><br>
 *  
 * @author Lakshmi Krishnamurthy
 */

public class FXLabel implements org.drip.state.identifier.LatentStateLabel {
	private org.drip.product.params.CurrencyPair _cp = null;

	/**
	 * Make a Standard FX Label from the Currency Pair Instance
	 * 
	 * @param cp The Currency Pair Instance
	 * 
	 * @return The FX Label
	 */

	public static final FXLabel Standard (
		final org.drip.product.params.CurrencyPair cp)
	{
		try {
			return new FXLabel (cp);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Make a Standard FX Label from the Currency Pair Code
	 * 
	 * @param strCode The FX Code
	 * 
	 * @return The FX Label
	 */

	public static final FXLabel Standard (
		final java.lang.String strCode)
	{
		try {
			return new FXLabel (org.drip.product.params.CurrencyPair.FromCode (strCode));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * FXLabel constructor
	 * 
	 * @param cp The Currency Pair
	 * 
	 * @throws java.lang.Exception Thrown if the inputs are invalid
	 */

	private FXLabel (
		final org.drip.product.params.CurrencyPair cp)
		throws java.lang.Exception
	{
		if (null == (_cp = cp)) throw new java.lang.Exception ("FXLabel ctr: Invalid Inputs");
	}

	@Override public java.lang.String fullyQualifiedName()
	{
		return _cp.code();
	}

	@Override public boolean match (
		final org.drip.state.identifier.LatentStateLabel lslOther)
	{
		return null == lslOther || !(lslOther instanceof org.drip.state.identifier.FXLabel) ? false :
			_cp.code().equalsIgnoreCase (lslOther.fullyQualifiedName());
	}

	/**
	 * Delegate the Inverse FX Label
	 * 
	 * @return The Inverse FX Label
	 */

	public FXLabel inverse()
	{
		try {
			return new FXLabel (org.drip.product.params.CurrencyPair.FromCode (_cp.inverseCode()));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Retrieve the Currency Pair Instance
	 * 
	 * @return The Underlying Currency Pair Instance
	 */

	public org.drip.product.params.CurrencyPair currencyPair()
	{
		return _cp;
	}
}
