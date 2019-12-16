
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
 * <i>Criterion</i> contains the Specification Details of a Credit Spread Event Criterion. The References
 * 	are:
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

public class Criterion
{
	private java.lang.String _name = "";
	private java.lang.String _description = "";
	private double _value = java.lang.Double.NaN;
	private int _unit = java.lang.Integer.MIN_VALUE;

	/**
	 * Construct the Baa Spread Change Criterion
	 * 
	 * @param baaSpreadChange Baa Spread Change in Basis Points
	 * 
	 * @return Baa Spread Change Criterion
	 */

	public static final Criterion BaaSpreadChange (
		final double baaSpreadChange)
	{
		try
		{
			return new Criterion (
				"Baa Spread Change",
				"Baa Spread Change",
				org.drip.capital.systemicscenario.CriterionUnit.BASIS_POINT,
				baaSpreadChange
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the SnP 500 Annual Return Criterion
	 * 
	 * @param snp500AnnualReturn SnP 500 Annual Return in Percentage
	 * 
	 * @return SnP 500 Annual Return Criterion
	 */

	public static final Criterion SnP500AnnualReturn (
		final double snp500AnnualReturn)
	{
		try
		{
			return new Criterion (
				"SnP 500 Annual Return",
				"SnP 500 Annual Return",
				org.drip.capital.systemicscenario.CriterionUnit.PERCENT,
				snp500AnnualReturn
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the UST 5Y Absolute Change Criterion
	 * 
	 * @param ust5YAbsoluteChange UST 5Y Absolute Change in Basis Points
	 * 
	 * @return UST 5Y Absolute Change Criterion
	 */

	public static final Criterion UST5YAbsoluteChange (
		final double ust5YAbsoluteChange)
	{
		try
		{
			return new Criterion (
				"UST 5Y Absolute Change",
				"UST 5Y Absolute Change",
				org.drip.capital.systemicscenario.CriterionUnit.BASIS_POINT,
				ust5YAbsoluteChange
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the UST 10Y - 3M Absolute Change Criterion
	 * 
	 * @param ust10YMinus3MAbsoluteChange UST 10Y - 3M Absolute Change in Basis Points
	 * 
	 * @return UST 10Y - 3M Absolute Change Criterion
	 */

	public static final Criterion UST10YMinus3MAbsoluteChange (
		final double ust10YMinus3MAbsoluteChange)
	{
		try
		{
			return new Criterion (
				"UST 10Y - 3M Absolute Change",
				"UST 10Y - 3M Absolute Change",
				org.drip.capital.systemicscenario.CriterionUnit.BASIS_POINT,
				ust10YMinus3MAbsoluteChange
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the FX Rate Change Criterion
	 * 
	 * @param fxRateChange FX Rate Change in Percentage
	 * 
	 * @return FX Rate Change Criterion
	 */

	public static final Criterion FXRateChange (
		final double fxRateChange)
	{
		try
		{
			return new Criterion (
				"FX Rate Change",
				"FX Rate Change in USD/EUR. Prior to 1999 in German DEM/USD",
				org.drip.capital.systemicscenario.CriterionUnit.PERCENT,
				fxRateChange
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the WTI Spot Return Criterion
	 * 
	 * @param wtiSpotReturn WTI Spot Return in Percentage
	 * 
	 * @return WTI Spot Return Criterion
	 */

	public static final Criterion WTISpotReturn (
		final double wtiSpotReturn)
	{
		try
		{
			return new Criterion (
				"WTI Spot Return",
				"WTI Spot Return from 1946",
				org.drip.capital.systemicscenario.CriterionUnit.PERCENT,
				wtiSpotReturn
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the SnP GSCI Non-energy Commodity Index Criterion
	 * 
	 * @param snpGSCINonEnergyCommodityIndex SnP GSCI Non-energy Commodity Index in Percentage
	 * 
	 * @return SnP GSCI Non-energy Commodity Index Criterion
	 */

	public static final Criterion SnPGSCINonEnergyCommodityIndex (
		final double snpGSCINonEnergyCommodityIndex)
	{
		try
		{
			return new Criterion (
				"SnP GSCI Non-energy Commodity Index",
				"SnP GSCI Non-energy Commodity Index",
				org.drip.capital.systemicscenario.CriterionUnit.PERCENT,
				snpGSCINonEnergyCommodityIndex
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Criterion Constructor
	 * 
	 * @param name Criterion Name
	 * @param description Criterion Description
	 * @param unit Criterion Unit
	 * @param value Criterion Value
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public Criterion (
		final java.lang.String name,
		final java.lang.String description,
		final int unit,
		final double value)
		throws java.lang.Exception
	{
		if (null == (_name = name) || _name.isEmpty() ||
			null == (_description = description) || _description.isEmpty() ||
			-1 >= (_unit = unit))
		{
			throw new java.lang.Exception ("Criterion Constructor => Invalid Inputs");
		}

		_value = value;
	}

	/**
	 * Retrieve the Criterion Name
	 * 
	 * @return The Criterion Name
	 */

	public java.lang.String name()
	{
		return _name;
	}

	/**
	 * Retrieve the Criterion Description
	 * 
	 * @return The Criterion Description
	 */

	public java.lang.String description()
	{
		return _description;
	}

	/**
	 * Retrieve the Criterion Unit
	 * 
	 * @return The Criterion Unit
	 */

	public int unit()
	{
		return _unit;
	}

	/**
	 * Retrieve the Criterion Value
	 * 
	 * @return The Criterion Value
	 */

	public double value()
	{
		return _value;
	}
}
