
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
 * <i>FloatingStreamQuoteSet</i> extends the ProductQuoteSet by implementing the Calibration Parameters for
 * the Floating Stream. Currently it exposes the PV and the Spread Quote Fields.
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

public class FloatingStreamQuoteSet extends org.drip.product.calib.ProductQuoteSet {

	/**
	 * FloatingStreamQuoteSet Constructor
	 * 
	 * @param aLSS Array of Latent State Specification
	 * 
	 * @throws java.lang.Exception Thrown if Inputs are invalid
	 */

	public FloatingStreamQuoteSet (
		final org.drip.state.representation.LatentStateSpecification[] aLSS)
		throws java.lang.Exception
	{
		super (aLSS);
	}

	/**
	 * Set the PV
	 * 
	 * @param dblPV The PV
	 * 
	 * @return TRUE - PV successfully set
	 */

	public boolean setPV (
		final double dblPV)
	{
		return set ("PV", dblPV);
	}

	/**
	 * Indicate if the PV Field exists
	 * 
	 * @return TRUE - PV Field Exists
	 */

	public boolean containsPV()
	{
		return contains ("PV");
	}

	/**
	 * Retrieve the PV
	 * 
	 * @return The PV
	 * 
	 * @throws java.lang.Exception Thrown if the PV Field does not exist
	 */

	public double pv()
		throws java.lang.Exception
	{
		return get ("PV");
	}

	/**
	 * Set the Forward Rate
	 * 
	 * @param dblForwardRate The Forward Rate
	 * 
	 * @return TRUE - The Forward Rate successfully set
	 */

	public boolean setForwardRate (
		final double dblForwardRate)
	{
		return set ("ForwardRate", dblForwardRate);
	}

	/**
	 * Indicate if the Forward Rate Field exists
	 * 
	 * @return TRUE - Forward Rate Field Exists
	 */

	public boolean containsForwardRate()
	{
		return contains ("ForwardRate");
	}

	/**
	 * Retrieve the Forward Rate
	 * 
	 * @return The Forward Rate
	 * 
	 * @throws java.lang.Exception Thrown if the Forward Rate Field does not exist
	 */

	public double forwardRate()
		throws java.lang.Exception
	{
		return get ("ForwardRate");
	}

	/**
	 * Set the Spread
	 * 
	 * @param dblSpread The Spread
	 * 
	 * @return TRUE - The Spread successfully set
	 */

	public boolean setSpread (
		final double dblSpread)
	{
		return set ("Spread", dblSpread);
	}

	/**
	 * Indicate if the Spread Field exists
	 * 
	 * @return TRUE - Spread Field Exists
	 */

	public boolean containsSpread()
	{
		return contains ("Spread");
	}

	/**
	 * Retrieve the Spread
	 * 
	 * @return The Spread
	 * 
	 * @throws java.lang.Exception Thrown if the Spread Field does not exist
	 */

	public double spread()
		throws java.lang.Exception
	{
		return get ("Spread");
	}
}
