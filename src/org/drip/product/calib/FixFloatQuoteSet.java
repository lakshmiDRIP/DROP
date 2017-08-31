
package org.drip.product.calib;

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
 * FixFloatQuoteSet extends the ProductQuoteSet by implementing the Calibration Parameters for the Fix-Float
 *  Swap Component. Currently it exposes the PV, the Reference Basis, and the Derived Basis Quote Fields.
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
