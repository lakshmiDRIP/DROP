
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
 * <i>FixFloatQuoteSet</i> extends the ProductQuoteSet by implementing the Calibration Parameters for the
 * Fix-Float Swap Component. Currently it exposes the PV, the Reference Basis, and the Derived Basis Quote
 * Fields.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Project</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product">Product</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/calib">Calibrator</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/FixedIncome">Fixed Income Analytics</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class FixFloatQuoteSet extends org.drip.product.calib.ProductQuoteSet {

	/**
	 * FixFloatQuoteSet Constructor
	 * 
	 * @param aLSS Array of Latent State Specification
	 * 
	 * @throws java.lang.Exception Thrown if Inputs are invalid
	 */

	public FixFloatQuoteSet (
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
	 * Set the Derived Par Basis Spread
	 * 
	 * @param dblDerivedParBasisSpread The Derived Par Basis Spread
	 * 
	 * @return TRUE - The Derived Par Basis Spread successfully set
	 */

	public boolean setDerivedParBasisSpread (
		final double dblDerivedParBasisSpread)
	{
		return set ("DerivedParBasisSpread", dblDerivedParBasisSpread);
	}

	/**
	 * Indicate if the Derived Par Basis Spread Field exists
	 * 
	 * @return TRUE - The Derived Par Basis Spread Field Exists
	 */

	public boolean containsDerivedParBasisSpread()
	{
		return contains ("DerivedParBasisSpread");
	}

	/**
	 * Retrieve the Derived Par Basis Spread
	 * 
	 * @return The Derived Par Basis Spread
	 * 
	 * @throws java.lang.Exception Thrown if the Derived Par Basis Spread Field does not exist
	 */

	public double derivedParBasisSpread()
		throws java.lang.Exception
	{
		return get ("DerivedParBasisSpread");
	}

	/**
	 * Set the Reference Par Basis Spread
	 * 
	 * @param dblReferenceParBasisSpread The Reference Par Basis Spread
	 * 
	 * @return TRUE - The Reference Par Basis Spread successfully set
	 */

	public boolean setReferenceParBasisSpread (
		final double dblReferenceParBasisSpread)
	{
		return set ("ReferenceParBasisSpread", dblReferenceParBasisSpread);
	}

	/**
	 * Indicate if the Reference Par Basis Spread Field exists
	 * 
	 * @return TRUE - The Reference Par Basis Spread Field Exists
	 */

	public boolean containsReferenceParBasisSpread()
	{
		return contains ("ReferenceParBasisSpread");
	}

	/**
	 * Retrieve the Reference Par Basis Spread
	 * 
	 * @return The Reference Par Basis Spread
	 * 
	 * @throws java.lang.Exception Thrown if the Reference Par Basis Spread Field does not exist
	 */

	public double referenceParBasisSpread()
		throws java.lang.Exception
	{
		return get ("ReferenceParBasisSpread");
	}

	/**
	 * Set the Swap Rate
	 * 
	 * @param dblSwapRate The Swap Rate
	 * 
	 * @return TRUE - The Swap Rate successfully set
	 */

	public boolean setSwapRate (
		final double dblSwapRate)
	{
		return set ("SwapRate", dblSwapRate);
	}

	/**
	 * Indicate if the Swap Rate Field exists
	 * 
	 * @return TRUE - The Swap Rate Field Exists
	 */

	public boolean containsSwapRate()
	{
		return contains ("SwapRate");
	}

	/**
	 * Retrieve the Swap Rate
	 * 
	 * @return The Swap Rate
	 * 
	 * @throws java.lang.Exception Thrown if the Swap Rate Field does not exist
	 */

	public double swapRate()
		throws java.lang.Exception
	{
		return get ("SwapRate");
	}

	/**
	 * Set the Rate
	 * 
	 * @param dblRate The Rate
	 * 
	 * @return TRUE - The Rate successfully set
	 */

	public boolean setRate (
		final double dblRate)
	{
		return set ("Rate", dblRate);
	}

	/**
	 * Indicate if the Rate Field exists
	 * 
	 * @return TRUE - The Rate Field Exists
	 */

	public boolean containsRate()
	{
		return contains ("Rate");
	}

	/**
	 * Retrieve the Rate
	 * 
	 * @return The Rate
	 * 
	 * @throws java.lang.Exception Thrown if the Rate Field does not exist
	 */

	public double rate()
		throws java.lang.Exception
	{
		return get ("Rate");
	}
}
