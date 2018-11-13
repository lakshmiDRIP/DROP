
package org.drip.portfolioconstruction.constraint;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
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
 * <i>LimitChargeTermIssuer</i> constrains the Limit Issuer Transaction Charge Term.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module</b>        = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction">Portfolio Construction</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/constraint">Constraint/a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/AssetAllocation">Asset Allocation Library</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class LimitChargeTermIssuer extends org.drip.portfolioconstruction.optimizer.ConstraintTerm
{

	/**
	 * Construct a Static Instance of LimitChargeTermIssuer
	 * 
	 * @param strName Name of the LimitChargeTermIssuer Constraint
	 * @param scope Scope of the LimitChargeTermIssuer Constraint
	 * @param unit Unit of the LimitChargeTermIssuer Constraint
	 * @param dblMinimum Minimum Value for the LimitChargeTermIssuer Constraint
	 * @param dblMaximum Maximum Value for the LimitChargeTermIssuer Constraint
	 * @param adblInitialHoldings Array of Initial Holdings
	 * @param aTransactionCharge Array of Transaction Charge
	 * 
	 * @return Instance of LimitChargeTermIssuer
	 */

	public static final LimitChargeTermIssuer Standard (
		final java.lang.String strName,
		final org.drip.portfolioconstruction.optimizer.Scope scope,
		final org.drip.portfolioconstruction.optimizer.Unit unit,
		final double dblMinimum,
		final double dblMaximum,
		final double[] adblInitialHoldings,
		final org.drip.portfolioconstruction.cost.TransactionCharge[] aTransactionCharge)
	{
		try {
			return new LimitChargeTermIssuer (
				strName,
				"CT_LIMIT_TRANSACTION_CHARGE",
				"Constrains the Total Transaction Charge",
				scope,
				unit,
				dblMinimum,
				dblMaximum,
				adblInitialHoldings,
				aTransactionCharge);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct a Static Instance of GoldmanSachsShortfall LimitChargeTermIssuer
	 * 
	 * @param strName Name of the LimitChargeTermIssuer Constraint
	 * @param scope Scope of the LimitChargeTermIssuer Constraint
	 * @param unit Unit of the LimitChargeTermIssuer Constraint
	 * @param dblMinimum Minimum Value for the LimitChargeTermIssuer Constraint
	 * @param dblMaximum Maximum Value for the LimitChargeTermIssuer Constraint
	 * @param adblInitialHoldings Array of Initial Holdings
	 * @param aTransactionChargeGSS Array of GoldmanSachsShortfall Transaction Charge
	 * 
	 * @return Instance of GoldmanSachsShortfall LimitChargeTermIssuer
	 */

	public static final LimitChargeTermIssuer GoldmanSachsShortfall (
		final java.lang.String strName,
		final org.drip.portfolioconstruction.optimizer.Scope scope,
		final org.drip.portfolioconstruction.optimizer.Unit unit,
		final double dblMinimum,
		final double dblMaximum,
		final double[] adblInitialHoldings,
		final org.drip.portfolioconstruction.cost.TransactionChargeGoldmanSachsShortfall[]
			aTransactionChargeGSS)
	{
		try {
			return new LimitChargeTermIssuer (
				strName,
				"CT_LIMIT_GOLDMAN_SACHS_SHORTFALL",
				"Constrains the Total Transaction Charge using the Goldman Sachs Shortfall Model",
				scope,
				unit,
				dblMinimum,
				dblMaximum,
				adblInitialHoldings,
				aTransactionChargeGSS);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	private double[] _adblInitialHoldings = null;
	private org.drip.portfolioconstruction.cost.TransactionCharge[] _aTransactionCharge = null;

	/**
	 * LimitChargeTermIssuer Constructor
	 * 
	 * @param strName Name of the LimitChargeTermIssuer Constraint
	 * @param strID ID of the LimitChargeTermIssuer Constraint
	 * @param strDescription Description of the LimitChargeTermIssuer Constraint
	 * @param scope Scope of the LimitChargeTermIssuer Constraint
	 * @param unit Unit of the LimitChargeTermIssuer Constraint
	 * @param dblMinimum Minimum Value for the LimitChargeTermIssuer Constraint
	 * @param dblMaximum Maximum Value for the LimitChargeTermIssuer Constraint
	 * @param adblInitialHoldings Array of Initial Holdings
	 * @param aTransactionCharge Array of Transaction Charge
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public LimitChargeTermIssuer (
		final java.lang.String strName,
		final java.lang.String strID,
		final java.lang.String strDescription,
		final org.drip.portfolioconstruction.optimizer.Scope scope,
		final org.drip.portfolioconstruction.optimizer.Unit unit,
		final double dblMinimum,
		final double dblMaximum,
		final double[] adblInitialHoldings,
		final org.drip.portfolioconstruction.cost.TransactionCharge[] aTransactionCharge)
		throws java.lang.Exception
	{
		super (
			strName,
			strID,
			strDescription,
			"LIMIT_TRANSACTION_CHARGE",
			scope,
			unit,
			dblMinimum,
			dblMaximum
		);

		if (null == (_adblInitialHoldings = adblInitialHoldings) ||
			null == (_aTransactionCharge = aTransactionCharge))
			throw new java.lang.Exception ("LimitChargeTermIssuer Constructor => Invalid Inputs");

		int iNumAsset = _adblInitialHoldings.length;

		if (0 == iNumAsset || iNumAsset != _aTransactionCharge.length)
			throw new java.lang.Exception ("LimitChargeTermIssuer Constructor => Invalid Inputs");

		for (int i = 0; i < iNumAsset; ++i)
		{
			if (!org.drip.quant.common.NumberUtil.IsValid (_adblInitialHoldings[i]) ||
				null == _aTransactionCharge[i])
				throw new java.lang.Exception ("LimitChargeTermIssuer Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Array of Initial Holdings
	 * 
	 * @return The Initial Holdings Array
	 */

	public double[] initialHoldings()
	{
		return _adblInitialHoldings;
	}

	/**
	 * Retrieve the Array of Transaction Charges
	 * 
	 * @return The Transaction Charge Array
	 */

	public org.drip.portfolioconstruction.cost.TransactionCharge[] transactionCharge()
	{
		return _aTransactionCharge;
	}

	@Override public org.drip.function.definition.RdToR1 rdtoR1()
	{
		return new org.drip.function.definition.RdToR1 (null)
		{
			@Override public int dimension()
			{
				return initialHoldings().length;
			}

			@Override public double evaluate (
				final double[] adblFinalHoldings)
				throws java.lang.Exception
			{
				double dblConstraintValue = 0.;
				int iNumAsset = _adblInitialHoldings.length;

				if (null == adblFinalHoldings || !org.drip.quant.common.NumberUtil.IsValid
					(adblFinalHoldings) || adblFinalHoldings.length != iNumAsset)
					throw new java.lang.Exception
						("LimitChargeTermIssuer::rdToR1::evaluate => Invalid Variate Dimension");

				for (int i = 0; i < iNumAsset; ++i) {
					dblConstraintValue +=
						_aTransactionCharge[i].estimate (
							_adblInitialHoldings[i],
							adblFinalHoldings[i]
					);
				}

				return dblConstraintValue;
			}
		};
	}
}
