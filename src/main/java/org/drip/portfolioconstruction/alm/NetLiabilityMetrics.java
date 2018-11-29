
package org.drip.portfolioconstruction.alm;

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
 * <i>NetLiabilityMetrics</i> holds the Results of the Computation of the Net Liability Cash Flows and PV
 * Metrics.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AssetAllocationAnalyticsLibrary.md">Asset Allocation Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction">Portfolio Construction</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/alm">ALM</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class NetLiabilityMetrics {
	private double _dblBasicConsumptionPV = java.lang.Double.NaN;
	private double _dblWorkingAgeIncomePV = java.lang.Double.NaN;
	private double _dblPensionBenefitsIncomePV = java.lang.Double.NaN;
	private java.util.List<org.drip.portfolioconstruction.alm.NetLiabilityCashFlow> _lsNLCF = null;

	/**
	 * NetLiabilityMetrics Constructor
	 * 
	 * @param lsNLCF List of Net Liability Cash Flows
	 * @param dblWorkingAgeIncomePV PV of the Working Age Income
	 * @param dblPensionBenefitsIncomePV PV of the Pension Benefits Income
	 * @param dblBasicConsumptionPV PV of the Basic Consumption
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public NetLiabilityMetrics (
		final java.util.List<org.drip.portfolioconstruction.alm.NetLiabilityCashFlow> lsNLCF,
		final double dblWorkingAgeIncomePV,
		final double dblPensionBenefitsIncomePV,
		final double dblBasicConsumptionPV)
		throws java.lang.Exception
	{
		if (null == (_lsNLCF = lsNLCF) || !org.drip.quant.common.NumberUtil.IsValid (_dblWorkingAgeIncomePV =
			dblWorkingAgeIncomePV) || !org.drip.quant.common.NumberUtil.IsValid (_dblPensionBenefitsIncomePV
				= dblPensionBenefitsIncomePV) || !org.drip.quant.common.NumberUtil.IsValid
					(_dblBasicConsumptionPV = dblBasicConsumptionPV))
			throw new java.lang.Exception ("NetLiabilityMetrics Constructor => Invalid Inputs");
	}

	/**
	 * Retrieve the List of Net Liability Cash Flows
	 * 
	 * @return The List of Net Liability Cash Flows
	 */

	public java.util.List<org.drip.portfolioconstruction.alm.NetLiabilityCashFlow> cashFlowList()
	{
		return _lsNLCF;
	}

	/**
	 * Retrieve the PV of the Working Age Income
	 * 
	 * @return The PV of the Working Age Income
	 */

	public double workingAgeIncomePV()
	{
		return _dblWorkingAgeIncomePV;
	}

	/**
	 * Retrieve the PV of the Pension Benefits Income
	 * 
	 * @return The PV of the Pension Benefits Income
	 */

	public double pensionBenefitsIncomePV()
	{
		return _dblPensionBenefitsIncomePV;
	}

	/**
	 * Retrieve the PV of the Basic Consumption
	 * 
	 * @return The PV of the Basic Consumption
	 */

	public double basicConsumptionPV()
	{
		return _dblBasicConsumptionPV;
	}
}
