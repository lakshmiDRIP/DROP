
package org.drip.capital.gsstdesign;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Quantitative Risk Analytics
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
 * @author Lakshmi Krishnamurthy
 */

public class CreditSpreadEvent
{
	private java.lang.String _scenario = "";
	private org.drip.capital.gsstdesign.Criterion _snpGSCI = null;
	private org.drip.capital.gsstdesign.Criterion _fxChange = null;
	private org.drip.capital.gsstdesign.Criterion _ust5YChange = null;
	private org.drip.capital.gsstdesign.Criterion _snp500Return = null;
	private org.drip.capital.gsstdesign.Criterion _wtiSpotReturn = null;
	private org.drip.capital.gsstdesign.Criterion _baaSpreadChange = null;
	private org.drip.capital.gsstdesign.Criterion _ust10YMinus3MChange = null;
	private org.drip.capital.gsstdesign.SystemicStressShockIndicator _systemicStressShockIndicator = null;

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
		final org.drip.capital.gsstdesign.SystemicStressShockIndicator systemicStressShockIndicator)
	{
		try
		{
			return new CreditSpreadEvent (
				scenario,
				org.drip.capital.gsstdesign.Criterion.BaaSpreadChange (
					baaSpreadChange
				),
				org.drip.capital.gsstdesign.Criterion.SnP500AnnualReturn (
					snp500AnnualReturn
				),
				org.drip.capital.gsstdesign.Criterion.UST5YAbsoluteChange (
					ust5YAbsoluteChange
				),
				org.drip.capital.gsstdesign.Criterion.UST10YMinus3MAbsoluteChange (
					ust10YMinus3MAbsoluteChange
				),
				org.drip.capital.gsstdesign.Criterion.FXRateChange (
					fxRateChange
				),
				org.drip.capital.gsstdesign.Criterion.WTISpotReturn (
					wtiSpotReturn
				),
				org.drip.capital.gsstdesign.Criterion.SnPGSCINonEnergyCommodityIndex (
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
		final org.drip.capital.gsstdesign.Criterion baaSpreadChange,
		final org.drip.capital.gsstdesign.Criterion snp500Return,
		final org.drip.capital.gsstdesign.Criterion ust5YChange,
		final org.drip.capital.gsstdesign.Criterion ust10YMinus3MChange,
		final org.drip.capital.gsstdesign.Criterion fxChange,
		final org.drip.capital.gsstdesign.Criterion wtiSpotReturn,
		final org.drip.capital.gsstdesign.Criterion snpGSCI,
		final org.drip.capital.gsstdesign.SystemicStressShockIndicator systemicStressShockIndicator)
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

	public org.drip.capital.gsstdesign.Criterion baaSpreadChange()
	{
		return _baaSpreadChange;
	}

	/**
	 * Retrieve the SnP 500 Return Criterion
	 * 
	 * @return The SnP 500 Return Criterion
	 */

	public org.drip.capital.gsstdesign.Criterion snp500Return()
	{
		return _snp500Return;
	}

	/**
	 * Retrieve the 5Y UST Change Criterion
	 * 
	 * @return The 5Y UST Change Criterion
	 */

	public org.drip.capital.gsstdesign.Criterion ust5YChange()
	{
		return _ust5YChange;
	}

	/**
	 * Retrieve the 10Y - 3M UST Change Criterion
	 * 
	 * @return The 10Y - 3M UST Change Criterion
	 */

	public org.drip.capital.gsstdesign.Criterion ust10YMinus3MChange()
	{
		return _ust10YMinus3MChange;
	}

	/**
	 * Retrieve the FX Change Criterion
	 * 
	 * @return The FX Change Criterion
	 */

	public org.drip.capital.gsstdesign.Criterion fxChange()
	{
		return _fxChange;
	}

	/**
	 * Retrieve the WTI Spot Return Criterion
	 * 
	 * @return The WTI Spot Return Criterion
	 */

	public org.drip.capital.gsstdesign.Criterion wtiSpotReturn()
	{
		return _wtiSpotReturn;
	}

	/**
	 * Retrieve the SnP GSCI Criterion
	 * 
	 * @return The SnP GSCI Criterion
	 */

	public org.drip.capital.gsstdesign.Criterion snpGSCI()
	{
		return _snpGSCI;
	}

	/**
	 * Retrieve the Systemic Stress Shock Indicator
	 * 
	 * @return The Systemic Stress Shock Indicator
	 */

	public org.drip.capital.gsstdesign.SystemicStressShockIndicator systemicStressShockIndicator()
	{
		return _systemicStressShockIndicator;
	}
}
