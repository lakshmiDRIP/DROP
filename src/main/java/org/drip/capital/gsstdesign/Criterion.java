
package org.drip.capital.gsstdesign;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Quantitative Risk Analytics
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
				org.drip.capital.gsstdesign.CriterionUnit.BASIS_POINT,
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
				org.drip.capital.gsstdesign.CriterionUnit.PERCENT,
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
				org.drip.capital.gsstdesign.CriterionUnit.BASIS_POINT,
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
				org.drip.capital.gsstdesign.CriterionUnit.BASIS_POINT,
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
				org.drip.capital.gsstdesign.CriterionUnit.PERCENT,
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
				org.drip.capital.gsstdesign.CriterionUnit.PERCENT,
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
				org.drip.capital.gsstdesign.CriterionUnit.PERCENT,
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
