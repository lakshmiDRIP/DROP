
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
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AssetAllocationAnalyticsLibrary.md">Asset Allocation Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction">Portfolio Construction</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/constraint">Constraint</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class LimitChargeTermIssuer
	extends org.drip.portfolioconstruction.optimizer.ConstraintTerm
{
	private double[] _initialHoldingsArray = null;
	private org.drip.portfolioconstruction.cost.TransactionCharge[] _transactionChargeArray = null;


	/**
	 * Construct a Static Instance of LimitChargeTermIssuer
	 * 
	 * @param name Name of the LimitChargeTermIssuer Constraint
	 * @param scope Scope of the LimitChargeTermIssuer Constraint
	 * @param unit Unit of the LimitChargeTermIssuer Constraint
	 * @param minimum Minimum Value for the LimitChargeTermIssuer Constraint
	 * @param maximum Maximum Value for the LimitChargeTermIssuer Constraint
	 * @param initialHoldingsArray Array of Initial Holdings
	 * @param transactionChargeArray Array of Transaction Charge
	 * 
	 * @return Instance of LimitChargeTermIssuer
	 */

	public static final LimitChargeTermIssuer Standard (
		final java.lang.String name,
		final org.drip.portfolioconstruction.optimizer.Scope scope,
		final org.drip.portfolioconstruction.optimizer.Unit unit,
		final double minimum,
		final double maximum,
		final double[] initialHoldingsArray,
		final org.drip.portfolioconstruction.cost.TransactionCharge[] transactionChargeArray)
	{
		try
		{
			return new LimitChargeTermIssuer (
				name,
				"CT_LIMIT_TRANSACTION_CHARGE",
				"Constrains the Total Transaction Charge",
				scope,
				unit,
				minimum,
				maximum,
				initialHoldingsArray,
				transactionChargeArray
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct a Static Instance of GoldmanSachsShortfall LimitChargeTermIssuer
	 * 
	 * @param name Name of the LimitChargeTermIssuer Constraint
	 * @param scope Scope of the LimitChargeTermIssuer Constraint
	 * @param unit Unit of the LimitChargeTermIssuer Constraint
	 * @param minimum Minimum Value for the LimitChargeTermIssuer Constraint
	 * @param maximum Maximum Value for the LimitChargeTermIssuer Constraint
	 * @param initialHoldingsArray Array of Initial Holdings
	 * @param goldmanSachsShortfallTransactionChargeArray Array of GoldmanSachsShortfall Transaction Charge
	 * 
	 * @return Instance of GoldmanSachsShortfall LimitChargeTermIssuer
	 */

	public static final LimitChargeTermIssuer GoldmanSachsShortfall (
		final java.lang.String name,
		final org.drip.portfolioconstruction.optimizer.Scope scope,
		final org.drip.portfolioconstruction.optimizer.Unit unit,
		final double minimum,
		final double maximum,
		final double[] initialHoldingsArray,
		final org.drip.portfolioconstruction.cost.TransactionChargeGoldmanSachsShortfall[]
			goldmanSachsShortfallTransactionChargeArray)
	{
		try
		{
			return new LimitChargeTermIssuer (
				name,
				"CT_LIMIT_GOLDMAN_SACHS_SHORTFALL",
				"Constrains the Total Transaction Charge using the Goldman Sachs Shortfall Model",
				scope,
				unit,
				minimum,
				maximum,
				initialHoldingsArray,
				goldmanSachsShortfallTransactionChargeArray
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * LimitChargeTermIssuer Constructor
	 * 
	 * @param name Name of the LimitChargeTermIssuer Constraint
	 * @param strID ID of the LimitChargeTermIssuer Constraint
	 * @param strDescription Description of the LimitChargeTermIssuer Constraint
	 * @param scope Scope of the LimitChargeTermIssuer Constraint
	 * @param unit Unit of the LimitChargeTermIssuer Constraint
	 * @param minimum Minimum Value for the LimitChargeTermIssuer Constraint
	 * @param maximum Maximum Value for the LimitChargeTermIssuer Constraint
	 * @param initialHoldingsArray Array of Initial Holdings
	 * @param transactionChargeArray Array of Transaction Charge
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public LimitChargeTermIssuer (
		final java.lang.String name,
		final java.lang.String strID,
		final java.lang.String strDescription,
		final org.drip.portfolioconstruction.optimizer.Scope scope,
		final org.drip.portfolioconstruction.optimizer.Unit unit,
		final double minimum,
		final double maximum,
		final double[] initialHoldingsArray,
		final org.drip.portfolioconstruction.cost.TransactionCharge[] transactionChargeArray)
		throws java.lang.Exception
	{
		super (
			name,
			strID,
			strDescription,
			"LIMIT_TRANSACTION_CHARGE",
			scope,
			unit,
			minimum,
			maximum
		);

		if (null == (_initialHoldingsArray = initialHoldingsArray) ||
			null == (_transactionChargeArray = transactionChargeArray))
		{
			throw new java.lang.Exception ("LimitChargeTermIssuer Constructor => Invalid Inputs");
		}

		int assetCount = _initialHoldingsArray.length;

		if (0 == assetCount || assetCount != _transactionChargeArray.length)
		{
			throw new java.lang.Exception ("LimitChargeTermIssuer Constructor => Invalid Inputs");
		}

		for (int assetIndex = 0; assetIndex < assetCount; ++assetIndex)
		{
			if (!org.drip.numerical.common.NumberUtil.IsValid (_initialHoldingsArray[assetIndex]) ||
				null == _transactionChargeArray[assetIndex])
			{
				throw new java.lang.Exception ("LimitChargeTermIssuer Constructor => Invalid Inputs");
			}
		}
	}

	/**
	 * Retrieve the Array of Initial Holdings
	 * 
	 * @return The Initial Holdings Array
	 */

	public double[] initialHoldingsArray()
	{
		return _initialHoldingsArray;
	}

	/**
	 * Retrieve the Array of Transaction Charges
	 * 
	 * @return The Transaction Charge Array
	 */

	public org.drip.portfolioconstruction.cost.TransactionCharge[] transactionChargeArray()
	{
		return _transactionChargeArray;
	}

	@Override public org.drip.function.definition.RdToR1 rdtoR1()
	{
		return new org.drip.function.definition.RdToR1 (null)
		{
			@Override public int dimension()
			{
				return _initialHoldingsArray.length;
			}

			@Override public double evaluate (
				final double[] finalHoldingsArray)
				throws java.lang.Exception
			{
				double limitChargeIssuer = 0.;
				int assetCount = _initialHoldingsArray.length;

				if (null == finalHoldingsArray ||
					!org.drip.numerical.common.NumberUtil.IsValid (finalHoldingsArray) ||
					finalHoldingsArray.length != assetCount)
				{
					throw new java.lang.Exception
						("LimitChargeTermIssuer::rdToR1::evaluate => Invalid Variate Dimension");
				}

				for (int assetIndex = 0; assetIndex < assetCount; ++assetIndex)
				{
					limitChargeIssuer += _transactionChargeArray[assetIndex].estimate (
						_initialHoldingsArray[assetIndex],
						finalHoldingsArray[assetIndex]
					);
				}

				return limitChargeIssuer;
			}
		};
	}
}
