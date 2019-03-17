
package org.drip.product.params;

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
 * Copyright (C) 2013 Lakshmi Krishnamurthy
 * Copyright (C) 2012 Lakshmi Krishnamurthy
 * Copyright (C) 2011 Lakshmi Krishnamurthy
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
 * <i>CreditSetting</i> contains the credit related valuation parameters - use default pay lag, use curve or
 * the component recovery, component recovery, credit curve name, and whether there is accrual on default. It
 * exports serialization into and de-serialization out of byte arrays.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product">Product</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/params">Params</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class CreditSetting implements org.drip.product.params.Validatable {
	private int _iLossPayLag = -1;
	private boolean _bUseCurveRecovery = true;
	private boolean _bAccrualOnDefault = false;
	private java.lang.String _strCreditCurveName = "";
	private double _dblRecovery = java.lang.Double.NaN;

	/**
	 * Construct the CreditSetting from the default pay lag, use curve or the component recovery flag,
	 *  component recovery, credit curve name, and whether there is accrual on default
	 * 
	 * @param iLossPayLag Loss Pay Lag
	 * @param dblRecovery Component Recovery
	 * @param bUseCurveRecovery Use the Curve Recovery (True) or Component Recovery (False)
	 * @param strCreditCurveName Credit curve name
	 * @param bAccrualOnDefault Accrual paid on default (True) 
	 */

	public CreditSetting (
		final int iLossPayLag,
		final double dblRecovery,
		final boolean bUseCurveRecovery,
		final java.lang.String strCreditCurveName,
		final boolean bAccrualOnDefault)
	{
		_iLossPayLag = iLossPayLag;
		_dblRecovery = dblRecovery;
		_bAccrualOnDefault = bAccrualOnDefault;
		_bUseCurveRecovery = bUseCurveRecovery;
		_strCreditCurveName = strCreditCurveName;
	}

	@Override public boolean validate()
	{
		if (null == _strCreditCurveName || _strCreditCurveName.isEmpty()) return true;

		if (!org.drip.numerical.common.NumberUtil.IsValid (_dblRecovery) && !_bUseCurveRecovery) return false;

		return true;
	}

	/**
	 * Retrieve the Loss Pay-out Lag
	 * 
	 * @return The Loss Pay-out Lag
	 */

	public int lossPayLag()
	{
		return _iLossPayLag;
	}

	/**
	 * Flag indicating whether or nor to use the Curve Recovery
	 * 
	 * @return TRUE - Use the Recovery From the Credit Curve
	 */

	public boolean useCurveRecovery()
	{
		return _bUseCurveRecovery;
	}

	/**
	 * Retrieve the Credit Curve Name
	 * 
	 * @return The Credit Curve Name
	 */

	public java.lang.String creditCurveName()
	{
		return _strCreditCurveName;
	}

	/**
	 * Retrieve the Accrual On Default Flag
	 * 
	 * @return TRUE - Accrual On Default
	 */

	public boolean accrualOnDefault()
	{
		return _bAccrualOnDefault;
	}

	/**
	 * Retrieve the Recovery Amount
	 * 
	 * @return The Recovery Amount
	 */

	public double recovery()
	{
		return _dblRecovery;
	}
}
