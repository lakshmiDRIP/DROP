
package org.drip.capital.label;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Quantitative Risk Analytics
 */

/**
 * <i>BusinessRegionRiskTypeCoordinate</i> implements the Capital Unit Coordinate based on Business, Region,
 * 	and Risk Type. The References are:
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

public class BusinessRegionRiskTypeCoordinate
	extends org.drip.capital.label.RegionRiskTypeCoordinate
{
	private java.lang.String _business = "";

	/**
	 * Construct a Standard Instance of BusinessRegionRiskTypeCoordinate from the FQN
	 * 
	 * @param fullyQualifiedName The FQN
	 * 
	 * @return Standard Instance of BusinessRegionRiskTypeCoordinate from the FQN
	 */

	public static final BusinessRegionRiskTypeCoordinate Standard (
		final java.lang.String fullyQualifiedName)
	{
		if (null == fullyQualifiedName || fullyQualifiedName.isEmpty())
		{
			return null;
		}

		java.lang.String[] businessRegionRiskType = org.drip.numerical.common.StringUtil.Split (
			fullyQualifiedName,
			org.drip.capital.label.Coordinate.FQN_DELIMITER
		);

		if (null == businessRegionRiskType || 3 != businessRegionRiskType.length)
		{
			return null;
		}

		try
		{
			return new BusinessRegionRiskTypeCoordinate (
				businessRegionRiskType[0],
				businessRegionRiskType[1],
				businessRegionRiskType[2]
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * BusinessRegionRiskTypeCoordinate Constructor
	 * 
	 * @param business iVAST Business
	 * @param region iVAST Region
	 * @param riskType iVAST  Risk Type
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public BusinessRegionRiskTypeCoordinate (
		final java.lang.String business,
		final java.lang.String region,
		final java.lang.String riskType)
		throws java.lang.Exception
	{
		super (
			region,
			riskType
		);

		if (null == (_business = business) || _business .isEmpty())
		{
			throw new java.lang.Exception ("BusinessRegionRiskTypeCoordinate Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the iVAST Business
	 * 
	 * @return The iVAST Business
	 */

	public java.lang.String business()
	{
		return _business;
	}

	@Override public java.lang.String fullyQualifiedName()
	{
		return _business + org.drip.capital.label.Coordinate.FQN_DELIMITER + region() +
			org.drip.capital.label.Coordinate.FQN_DELIMITER + riskType();
	}

	/**
	 * Retrieve the Region-Risk Type Node Identifier
	 * 
	 * @return The Region-Risk Type Node Identifier
	 */

	public org.drip.capital.label.RegionRiskTypeCoordinate regionRiskTypeCoordinate()
	{
		return org.drip.capital.label.RegionRiskTypeCoordinate.Standard (
			region() + org.drip.capital.label.Coordinate.FQN_DELIMITER + riskType()
		);
	}
}
