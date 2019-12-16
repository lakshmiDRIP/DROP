
package org.drip.capital.systemicscenario;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
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
 * <i>CreditSpreadEvent</i> contains the Specifications of Criteria corresponding to a Credit Spread Event.
 * 	The References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Bank for International Supervision(2005): Stress Testing at Major Financial Institutions: Survey
 * 				Results and Practice https://www.bis.org/publ/cgfs24.htm
 * 		</li>
 * 		<li>
 * 			Glasserman, P. (2004): <i>Monte Carlo Methods in Financial Engineering</i> <b>Springer</b>
 * 		</li>
 * 		<li>
 * 			Kupiec, P. H. (2000): Stress Tests and Risk Capital <i>Risk</i> <b>2 (4)</b> 27-39
 * 		</li>
 * 	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/CapitalAnalyticsLibrary.md">Capital Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/README.md">Basel Market Risk and Operational Capital</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/systemicscenario/README.md">Systemic Stress Scenario Design/Construction</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class CreditSpreadEvent
{
	private java.lang.String _scenario = "";
	private org.drip.capital.systemicscenario.Criterion _snpGSCI = null;
	private org.drip.capital.systemicscenario.Criterion _fxChange = null;
	private org.drip.capital.systemicscenario.Criterion _ust5YChange = null;
	private org.drip.capital.systemicscenario.Criterion _snp500Return = null;
	private org.drip.capital.systemicscenario.Criterion _wtiSpotReturn = null;
	private org.drip.capital.systemicscenario.Criterion _baaSpreadChange = null;
	private org.drip.capital.systemicscenario.Criterion _ust10YMinus3MChange = null;
	private org.drip.capital.systemicscenario.SystemicStressShockIndicator _systemicStressShockIndicator =
		null;

	/**
	 * Construct a Standard CreditSpreadEvent Instance
	 * 
	 * @param scenario Credit Spread Event Scenario
	 * @param baaSpreadChange Baa Spread Change in Basis Points
	 * @param snp500AnnualReturn SnP 500 Annual Return in Percentage
	 * @param ust5YAbsoluteChange UST 5Y Absolute Change in Basis Points
	 * @param ust10YMinus3MAbsoluteChange UST 10Y - 3M Absolute Change in Basis Points
	 * @param fxRateChange FX Rate Change in Percentage
	 * @param wtiSpotReturn WTI Spot Return in Percentage
	 * @param snpGSCINonEnergyCommodityIndex SnP GSCI Non-energy Commodity Index in Percentage
	 * @param systemicStressShockIndicator Credit Event Systemic Stress Shock Indicator
	 * 
	 * @return CreditSpreadEvent Instance
	 */

	public static final CreditSpreadEvent Standard (
		final java.lang.String scenario,
		final double baaSpreadChange,
		final double snp500AnnualReturn,
		final double ust5YAbsoluteChange,
		final double ust10YMinus3MAbsoluteChange,
		final double fxRateChange,
		final double wtiSpotReturn,
		final double snpGSCINonEnergyCommodityIndex,
		final org.drip.capital.systemicscenario.SystemicStressShockIndicator systemicStressShockIndicator)
	{
		try
		{
			return new CreditSpreadEvent (
				scenario,
				org.drip.capital.systemicscenario.Criterion.BaaSpreadChange (
					baaSpreadChange
				),
				org.drip.capital.systemicscenario.Criterion.SnP500AnnualReturn (
					snp500AnnualReturn
				),
				org.drip.capital.systemicscenario.Criterion.UST5YAbsoluteChange (
					ust5YAbsoluteChange
				),
				org.drip.capital.systemicscenario.Criterion.UST10YMinus3MAbsoluteChange (
					ust10YMinus3MAbsoluteChange
				),
				org.drip.capital.systemicscenario.Criterion.FXRateChange (
					fxRateChange
				),
				org.drip.capital.systemicscenario.Criterion.WTISpotReturn (
					wtiSpotReturn
				),
				org.drip.capital.systemicscenario.Criterion.SnPGSCINonEnergyCommodityIndex (
					snpGSCINonEnergyCommodityIndex
				),
				systemicStressShockIndicator
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * CreditSpreadEvent Constructor
	 * 
	 * @param scenario Credit Spread Event Scenario
	 * @param baaSpreadChange Baa Spread Change Criterion
	 * @param snp500Return SnP 500 Return Criterion
	 * @param ust5YChange 5Y UST Change Criterion
	 * @param ust10YMinus3MChange 10Y - 3M UST Change Criterion
	 * @param fxChange FX Change Criterion
	 * @param wtiSpotReturn WTI Spot Return Criterion
	 * @param snpGSCI SnP GSCI Criterion
	 * @param systemicStressShockIndicator Credit Event Systemic Stress Shock Indicator
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public CreditSpreadEvent (
		final java.lang.String scenario,
		final org.drip.capital.systemicscenario.Criterion baaSpreadChange,
		final org.drip.capital.systemicscenario.Criterion snp500Return,
		final org.drip.capital.systemicscenario.Criterion ust5YChange,
		final org.drip.capital.systemicscenario.Criterion ust10YMinus3MChange,
		final org.drip.capital.systemicscenario.Criterion fxChange,
		final org.drip.capital.systemicscenario.Criterion wtiSpotReturn,
		final org.drip.capital.systemicscenario.Criterion snpGSCI,
		final org.drip.capital.systemicscenario.SystemicStressShockIndicator systemicStressShockIndicator)
		throws java.lang.Exception
	{
		if (null == (_scenario = scenario) || _scenario.isEmpty() ||
			null == (_baaSpreadChange = baaSpreadChange) ||
			null == (_snp500Return = snp500Return) ||
			null == (_ust5YChange = ust5YChange) ||
			null == (_ust10YMinus3MChange = ust10YMinus3MChange) ||
			null == (_fxChange = fxChange) ||
			null == (_wtiSpotReturn = wtiSpotReturn) ||
			null == (_snpGSCI = snpGSCI) ||
			null == (_systemicStressShockIndicator = systemicStressShockIndicator))
		{
			throw new java.lang.Exception ("CreditSpreadEvent Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Credit Spread Event Scenario
	 * 
	 * @return The Credit Spread Event Scenario
	 */

	public java.lang.String scenario()
	{
		return _scenario;
	}

	/**
	 * Retrieve the Baa Spread Change Criterion
	 * 
	 * @return The Baa Spread Change Criterion
	 */

	public org.drip.capital.systemicscenario.Criterion baaSpreadChange()
	{
		return _baaSpreadChange;
	}

	/**
	 * Retrieve the SnP 500 Return Criterion
	 * 
	 * @return The SnP 500 Return Criterion
	 */

	public org.drip.capital.systemicscenario.Criterion snp500Return()
	{
		return _snp500Return;
	}

	/**
	 * Retrieve the 5Y UST Change Criterion
	 * 
	 * @return The 5Y UST Change Criterion
	 */

	public org.drip.capital.systemicscenario.Criterion ust5YChange()
	{
		return _ust5YChange;
	}

	/**
	 * Retrieve the 10Y - 3M UST Change Criterion
	 * 
	 * @return The 10Y - 3M UST Change Criterion
	 */

	public org.drip.capital.systemicscenario.Criterion ust10YMinus3MChange()
	{
		return _ust10YMinus3MChange;
	}

	/**
	 * Retrieve the FX Change Criterion
	 * 
	 * @return The FX Change Criterion
	 */

	public org.drip.capital.systemicscenario.Criterion fxChange()
	{
		return _fxChange;
	}

	/**
	 * Retrieve the WTI Spot Return Criterion
	 * 
	 * @return The WTI Spot Return Criterion
	 */

	public org.drip.capital.systemicscenario.Criterion wtiSpotReturn()
	{
		return _wtiSpotReturn;
	}

	/**
	 * Retrieve the SnP GSCI Criterion
	 * 
	 * @return The SnP GSCI Criterion
	 */

	public org.drip.capital.systemicscenario.Criterion snpGSCI()
	{
		return _snpGSCI;
	}

	/**
	 * Retrieve the Systemic Stress Shock Indicator
	 * 
	 * @return The Systemic Stress Shock Indicator
	 */

	public org.drip.capital.systemicscenario.SystemicStressShockIndicator systemicStressShockIndicator()
	{
		return _systemicStressShockIndicator;
	}
}
