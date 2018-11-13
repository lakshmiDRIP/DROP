
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
 * <i>LimitBudgetTermTransactionCharge</i> holds the Details of a After Transaction Charge Limit Budget
 * Constraint Term.
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

public class LimitBudgetTermTransactionCharge extends
	org.drip.portfolioconstruction.constraint.LimitBudgetTerm
{
	private double[] _adblInitialHoldings = null;
	private org.drip.portfolioconstruction.cost.TransactionCharge[] _aTransactionCharge = null;

	/**
	 * LimitBudgetTermTransactionCharge Constructor
	 * 
	 * @param strName Name of the Constraint
	 * @param scope Scope of the Constraint - ACCOUNT/ASSET/SET
	 * @param unit Unit of the Constraint
	 * @param dblBudget Budget Value of the Constraint
	 * @param adblPrice Array of Asset Prices
	 * @param adblInitialHoldings Array of Initial Holdings
	 * @param aTransactionCharge Array of Transaction Charge Instances
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public LimitBudgetTermTransactionCharge (
		final java.lang.String strName,
		final org.drip.portfolioconstruction.optimizer.Scope scope,
		final org.drip.portfolioconstruction.optimizer.Unit unit,
		final double dblBudget,
		final double[] adblPrice,
		final double[] adblInitialHoldings,
		final org.drip.portfolioconstruction.cost.TransactionCharge[] aTransactionCharge)
		throws java.lang.Exception
	{
		super (
			strName,
			"CT_AFTER_TRANSACTION_CHARGE_BUDGET",
			"Constrains the After Transaction Charge Allocation Budget",
			scope,
			unit,
			dblBudget,
			adblPrice
		);

		if (null == (_adblInitialHoldings = adblInitialHoldings) || 0 == _adblInitialHoldings.length ||
			!org.drip.quant.common.NumberUtil.IsValid (_adblInitialHoldings))
			throw new java.lang.Exception ("LimitBudgetTermTransactionCharge Constructor => Invalid Inputs");

		int iNumAsset = _adblInitialHoldings.length;

		if (null == (_aTransactionCharge = aTransactionCharge) || iNumAsset != _aTransactionCharge.length ||
			iNumAsset != adblPrice.length)
			throw new java.lang.Exception ("LimitBudgetTermTransactionCharge Constructor => Invalid Inputs");

		for (int i = 0; i < iNumAsset; ++i)
		{
			if (null == _aTransactionCharge[i])
				throw new java.lang.Exception
					("LimitBudgetTermTransactionCharge Constructor => Invalid Inputs");
		}
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
				return price().length;
			}

			@Override public double evaluate (
				final double[] adblFinalHoldings)
				throws java.lang.Exception
			{
				double[] adblPrice = price();

				double dblConstraintValue = 0.;
				int iNumAsset = adblPrice.length;

				if (null == adblFinalHoldings || !org.drip.quant.common.NumberUtil.IsValid
					(adblFinalHoldings) || adblFinalHoldings.length != iNumAsset)
					throw new java.lang.Exception
						("LimitBudgetTermTransactionCharge::rdToR1::evaluate => Invalid Variate Dimension");

				for (int i = 0; i < iNumAsset; ++i) {
					dblConstraintValue += (adblPrice[i] * (adblFinalHoldings[i] - _adblInitialHoldings[i]) -
						_aTransactionCharge[i].estimate (
							_adblInitialHoldings[i],
							adblFinalHoldings[i]
						)
					);
				}

				return dblConstraintValue;
			}
		};
	}
}

