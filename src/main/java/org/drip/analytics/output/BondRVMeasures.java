
package org.drip.analytics.output;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
 * Copyright (C) 2012 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting analytics/risk, transaction cost analytics,
 *  	asset liability management analytics, capital, exposure, and margin analytics, valuation adjustment
 *  	analytics, and portfolio construction analytics within and across fixed income, credit, commodity,
 *  	equity, FX, and structured products. It also includes auxiliary libraries for algorithm support,
 *  	numerical analysis, numerical optimization, spline builder, model validation, statistical learning,
 *  	and computational support.
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
 * <i>BondRVMeasures</i> encapsulates the comprehensive set of RV measures calculated for the bond to the
 * appropriate exercise:
 *
 *	<br><br>
 *  <ul>
 * 		<li>
 * 			Work-out Information
 * 		</li>
 * 		<li>
 * 			Price, Yield, and Yield01
 * 		</li>
 * 		<li>
 * 			Spread Measures: Asset Swap/Credit/G/I/OAS/PECS/TSY/Z
 * 		</li>
 * 		<li>
 * 			Basis Measures: Bond Basis, Credit Basis, Yield Basis
 * 		</li>
 * 		<li>
 * 			Duration Measures: Macaulay/Modified Duration, Convexity
 * 		</li>
 *  </ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/README.md">Date, Cash Flow, and Cash Flow Period Measure Generation Utilities</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/output/README.md">Period Product Targeted Valuation Measures</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class BondRVMeasures {
	private double _dblPECS = java.lang.Double.NaN;
	private double _dblPrice = java.lang.Double.NaN;
	private double _dblGSpread = java.lang.Double.NaN;
	private double _dblISpread = java.lang.Double.NaN;
	private double _dblYield01 = java.lang.Double.NaN;
	private double _dblZSpread = java.lang.Double.NaN;
	private double _dblOASpread = java.lang.Double.NaN;
	private double _dblBondBasis = java.lang.Double.NaN;
	private double _dblConvexity = java.lang.Double.NaN;
	private double _dblTSYSpread = java.lang.Double.NaN;
	private double _dblCreditBasis = java.lang.Double.NaN;
	private org.drip.param.valuation.WorkoutInfo _wi = null;
	private double _dblDiscountMargin = java.lang.Double.NaN;
	private double _dblAssetSwapSpread = java.lang.Double.NaN;
	private double _dblMacaulayDuration = java.lang.Double.NaN;
	private double _dblModifiedDuration = java.lang.Double.NaN;

	/**
	 * BondRVMeasures ctr
	 * 
	 * @param dblPrice BondRV Clean Price
	 * @param dblBondBasis BondRV Bond Basis
	 * @param dblZSpread BondRV Z Spread
	 * @param dblGSpread BondRV G Spread
	 * @param dblISpread BondRV I Spread
	 * @param dblOASpread BondRV OAS
	 * @param dblTSYSpread BondRV TSY Spread
	 * @param dblDiscountMargin BondRV Asset Swap Spread
	 * @param dblAssetSwapSpread BondRV Asset Swap Spread
	 * @param dblCreditBasis BondRV Credit Basis
	 * @param dblPECS BondRV PECS
	 * @param dblYield01 BondRV Yield01
	 * @param dblModifiedDuration BondRV Modified Duration
	 * @param dblMacaulayDuration BondRV Macaulay Duration
	 * @param dblConvexity BondRV Convexity
	 * @param wi BondRV work-out info
	 * 
	 * @throws java.lang.Exception Thrown if inputs are invalid
	 */

	public BondRVMeasures (
		final double dblPrice,
		final double dblBondBasis,
		final double dblZSpread,
		final double dblGSpread,
		final double dblISpread,
		final double dblOASpread,
		final double dblTSYSpread,
		final double dblDiscountMargin,
		final double dblAssetSwapSpread,
		final double dblCreditBasis,
		final double dblPECS,
		final double dblYield01,
		final double dblModifiedDuration,
		final double dblMacaulayDuration,
		final double dblConvexity,
		final org.drip.param.valuation.WorkoutInfo wi)
		throws java.lang.Exception
	{
		if (null == (_wi = wi)) throw new java.lang.Exception ("BondRVMeasures ctr: Invalid inputs!");

		_dblPECS = dblPECS;
		_dblPrice = dblPrice;
		_dblGSpread = dblGSpread;
		_dblISpread = dblISpread;
		_dblYield01 = dblYield01;
		_dblZSpread = dblZSpread;
		_dblOASpread = dblOASpread;
		_dblBondBasis = dblBondBasis;
		_dblConvexity = dblConvexity;
		_dblTSYSpread = dblTSYSpread;
		_dblCreditBasis = dblCreditBasis;
		_dblDiscountMargin = dblDiscountMargin;
		_dblAssetSwapSpread = dblAssetSwapSpread;
		_dblMacaulayDuration = dblMacaulayDuration;
		_dblModifiedDuration = dblModifiedDuration;
	}

	/**
	 * Retrieve the Work-out Info
	 * 
	 * @return Work-out Info
	 */

	public org.drip.param.valuation.WorkoutInfo wi()
	{
		return _wi;
	}

	/**
	 * Retrieve the PECS
	 * 
	 * @return PECS
	 */

	public double pecs()
	{
		return _dblPECS;
	}

	/**
	 * Retrieve the Price
	 * 
	 * @return Price
	 */

	public double price()
	{
		return _dblPrice;
	}

	/**
	 * Retrieve the G Spread
	 * 
	 * @return G Spread
	 */

	public double gSpread()
	{
		return _dblGSpread;
	}

	/**
	 * Retrieve the I Spread
	 * 
	 * @return I Spread
	 */

	public double iSpread()
	{
		return _dblISpread;
	}

	/**
	 * Retrieve the Yield01
	 * 
	 * @return Yield01
	 */

	public double yield01()
	{
		return _dblYield01;
	}

	/**
	 * Retrieve the Z Spread
	 * 
	 * @return Z Spread
	 */

	public double zSpread()
	{
		return _dblZSpread;
	}

	/**
	 * Retrieve the OAS
	 * 
	 * @return OAS
	 */

	public double oas()
	{
		return _dblOASpread;
	}

	/**
	 * Retrieve the Bond Basis
	 * 
	 * @return Bond Basis
	 */

	public double bondBasis()
	{
		return _dblBondBasis;
	}

	/**
	 * Retrieve the Convexity
	 * 
	 * @return Convexity
	 */

	public double convexity()
	{
		return _dblConvexity;
	}

	/**
	 * Retrieve the TSY Spread
	 * 
	 * @return TSY Spread
	 */

	public double tsySpread()
	{
		return _dblTSYSpread;
	}

	/**
	 * Retrieve the Credit Basis
	 * 
	 * @return Credit Basis
	 */

	public double creditBasis()
	{
		return _dblCreditBasis;
	}

	/**
	 * Retrieve the Discount Margin
	 * 
	 * @return Discount Margin
	 */

	public double discountMargin()
	{
		return _dblDiscountMargin;
	}

	/**
	 * Retrieve the Asset Swap Spread
	 * 
	 * @return Asset Swap Spread
	 */

	public double asw()
	{
		return _dblAssetSwapSpread;
	}

	/**
	 * Retrieve the Macaulay Duration
	 * 
	 * @return Macaulay Duration
	 */

	public double macaulayDuration()
	{
		return _dblMacaulayDuration;
	}

	/**
	 * Retrieve the Modified Duration
	 * 
	 * @return Modified Duration
	 */

	public double modifiedDuration()
	{
		return _dblModifiedDuration;
	}

	/**
	 * Return the state as a measure map
	 * 
	 * @param strPrefix RV Measure name prefix
	 * 
	 * @return Map of the RV measures
	 */

	public org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> toMap (
		final java.lang.String strPrefix)
	{
		org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapRVMeasures = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>();

		mapRVMeasures.put (strPrefix + "AssetSwapSpread", _dblAssetSwapSpread);

		mapRVMeasures.put (strPrefix + "ASW", _dblAssetSwapSpread);

		mapRVMeasures.put (strPrefix + "BondBasis", _dblBondBasis);

		mapRVMeasures.put (strPrefix + "Convexity", _dblConvexity);

		mapRVMeasures.put (strPrefix + "CreditBasis", _dblCreditBasis);

		mapRVMeasures.put (strPrefix + "DiscountMargin", _dblDiscountMargin);

		mapRVMeasures.put (strPrefix + "Duration", _dblModifiedDuration);

		mapRVMeasures.put (strPrefix + "GSpread", _dblGSpread);

		mapRVMeasures.put (strPrefix + "ISpread", _dblISpread);

		mapRVMeasures.put (strPrefix + "MacaulayDuration", _dblMacaulayDuration);

		mapRVMeasures.put (strPrefix + "ModifiedDuration", _dblModifiedDuration);

		mapRVMeasures.put (strPrefix + "OAS", _dblOASpread);

		mapRVMeasures.put (strPrefix + "OASpread", _dblOASpread);

		mapRVMeasures.put (strPrefix + "OptionAdjustedSpread", _dblOASpread);

		mapRVMeasures.put (strPrefix + "PECS", _dblPECS);

		mapRVMeasures.put (strPrefix + "Price", _dblPrice);

		mapRVMeasures.put (strPrefix + "TSYSpread", _dblTSYSpread);

		mapRVMeasures.put (strPrefix + "WorkoutDate", (double) _wi.date());

		mapRVMeasures.put (strPrefix + "WorkoutFactor", _wi.factor());

		mapRVMeasures.put (strPrefix + "WorkoutType", (double) _wi.type());

		mapRVMeasures.put (strPrefix + "WorkoutYield", _wi.yield());

		mapRVMeasures.put (strPrefix + "Yield", _wi.yield());

		mapRVMeasures.put (strPrefix + "Yield01", _dblYield01);

		mapRVMeasures.put (strPrefix + "YieldBasis", _dblBondBasis);

		mapRVMeasures.put (strPrefix + "YieldSpread", _dblBondBasis);

		mapRVMeasures.put (strPrefix + "ZSpread", _dblZSpread);

		return mapRVMeasures;
	}
}
