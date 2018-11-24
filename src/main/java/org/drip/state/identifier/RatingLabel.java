
package org.drip.state.identifier;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
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
 * <i>RatingLabel</i> contains the Identifier Parameters referencing the Label corresponding to the Credit
 * Rating Latent State. Currently it holds the Ratings Agency Name and the Rated Code.
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

public class RatingLabel implements org.drip.state.identifier.LatentStateLabel {
	private java.lang.String _strCode = "";
	private java.lang.String _strAgency = "";

	/**
	 * Make a Standard Rating Label from the Rating Agency and the Rated Code.
	 * 
	 * @param strAgency The Rating Agency
	 * @param strCode The Rated Code
	 * 
	 * @return The Rating Label
	 */

	public static final RatingLabel Standard (
		final java.lang.String strAgency,
		final java.lang.String strCode)
	{
		try {
			return new RatingLabel (strAgency, strCode);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * RatingsLabel constructor
	 * 
	 * @param strAgency The Ratings Agency
	 * @param strCode The Rated Code
	 * 
	 * @throws java.lang.Exception Thrown if the inputs are invalid
	 */

	public RatingLabel (
		final java.lang.String strAgency,
		final java.lang.String strCode)
		throws java.lang.Exception
	{
		if (null == (_strAgency = strAgency) || _strAgency.isEmpty() || null == (_strCode = strCode) ||
			_strCode.isEmpty())
			throw new java.lang.Exception ("RatingLabel ctr: Invalid Inputs");
	}

	@Override public java.lang.String fullyQualifiedName()
	{
		return _strAgency + "::" + _strCode;
	}

	@Override public boolean match (
		final org.drip.state.identifier.LatentStateLabel lslOther)
	{
		if (null == lslOther || !(lslOther instanceof org.drip.state.identifier.RatingLabel)) return false;

		org.drip.state.identifier.RatingLabel rlOther = (org.drip.state.identifier.RatingLabel) lslOther;

		return _strAgency.equalsIgnoreCase (rlOther.agency()) && _strCode.equalsIgnoreCase (rlOther.code());
	}

	/**
	 * Retrieve the Ratings Agency
	 * 
	 * @return The Ratings Agency
	 */

	public java.lang.String agency()
	{
		return _strAgency;
	}

	/**
	 * Retrieve the Rated Code
	 * 
	 * @return The Rated Code
	 */

	public java.lang.String code()
	{
		return _strCode;
	}
}
