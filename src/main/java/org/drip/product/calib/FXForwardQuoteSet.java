
package org.drip.product.calib;

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
 * <i>FXForwardQuoteSet</i> extends the ProductQuoteSet by implementing the Calibration Parameters for the FX
 * Forward Component. Currently it exposes the Outright and the PIP Fields.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/README.md">Product Components/Baskets for Credit, FRA, FX, Govvie, Rates, and Option AssetClasses</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/calib/README.md">Curve/Surface Calibration Quote Sets</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class FXForwardQuoteSet extends org.drip.product.calib.ProductQuoteSet {

	/**
	 * FXForwardQuoteSet Constructor
	 * 
	 * @param aLSS Array of Latent State Specification
	 * 
	 * @throws java.lang.Exception Thrown if Inputs are invalid
	 */

	public FXForwardQuoteSet (
		final org.drip.state.representation.LatentStateSpecification[] aLSS)
		throws java.lang.Exception
	{
		super (aLSS);
	}

	/**
	 * Set the Terminal FX Forward Outright
	 * 
	 * @param dblFXForwardOutright The Terminal FX Forward Outright
	 * 
	 * @return TRUE - The Terminal FX Forward Outright successfully set
	 */

	public boolean setOutright (
		final double dblFXForwardOutright)
	{
		return set ("Outright", dblFXForwardOutright);
	}

	/**
	 * Indicate if the Terminal FX Forward Outright Field exists
	 * 
	 * @return TRUE - Terminal FX Forward Outright Field Exists
	 */

	public boolean containsOutright()
	{
		return contains ("Outright");
	}

	/**
	 * Retrieve the Terminal FX Forward Outright
	 * 
	 * @return Terminal FX Forward Outright Basis
	 * 
	 * @throws java.lang.Exception Thrown if the Terminal FX Forward Outright Field does not exist
	 */

	public double outright()
		throws java.lang.Exception
	{
		return get ("Outright");
	}

	/**
	 * Set the Terminal FX Forward PIP
	 * 
	 * @param dblFXForwardPIP The Terminal FX Forward PIP
	 * 
	 * @return TRUE - The Terminal FX Forward PIP successfully set
	 */

	public boolean setPIP (
		final double dblFXForwardPIP)
	{
		return set ("PIP", dblFXForwardPIP);
	}

	/**
	 * Indicate if the Terminal FX Forward PIP Field exists
	 * 
	 * @return TRUE - Terminal FX Forward PIP Field Exists
	 */

	public boolean containsPIP()
	{
		return contains ("PIP");
	}

	/**
	 * Retrieve the Terminal FX Forward PIP
	 * 
	 * @return Terminal FX Forward PIP Basis
	 * 
	 * @throws java.lang.Exception Thrown if the Terminal FX Forward PIP Field does not exist
	 */

	public double pip()
		throws java.lang.Exception
	{
		return get ("PIP");
	}
}
