
package org.drip.capital.label;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Quantitative Risk Analytics
 */

/**
 * <i>CapitalUnitCoordinate</i> implements the Capital Unit Coordinate. The References are:
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

public class CapitalUnitCoordinate
	implements org.drip.capital.label.Coordinate
{
	private java.lang.String _id = "";
	private java.lang.String _riskType = "";

	/**
	 * Construct a Standard Instance of CapitalUnitCoordinate from the FQN
	 * 
	 * @param fullyQualifiedName The FQN
	 * 
	 * @return Standard Instance of CapitalUnitCoordinate from the FQN
	 */

	public static CapitalUnitCoordinate Standard (
		final java.lang.String fullyQualifiedName)
	{
		if (null == fullyQualifiedName || fullyQualifiedName.isEmpty())
		{
			return null;
		}

		java.lang.String[] idRiskType = org.drip.numerical.common.StringUtil.Split (
			fullyQualifiedName,
			org.drip.capital.label.Coordinate.FQN_DELIMITER
		);

		if (null == idRiskType || 2 != idRiskType.length)
		{
			return null;
		}

		try
		{
			return new CapitalUnitCoordinate (
				idRiskType[0],
				idRiskType[1]
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * CapitalUnitCoordinate Constructor
	 * 
	 * @param id Capital Unit ID
	 * @param riskType Capital Unit Risk Type
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public CapitalUnitCoordinate (
		final java.lang.String id,
		final java.lang.String riskType)
		throws java.lang.Exception
	{
		if (null == (_id = id) || _id.isEmpty() ||
			null == (_riskType = riskType) || _riskType.isEmpty())
		{
			throw new java.lang.Exception ("CapitalUnitCoordinate Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Capital Unit ID
	 * 
	 * @return The Capital Unit ID
	 */

	public java.lang.String id()
	{
		return _id;
	}

	/**
	 * Retrieve the Capital Unit Risk Type
	 * 
	 * @return The Capital Unit Risk Type
	 */

	public java.lang.String riskType()
	{
		return _riskType;
	}

	@Override public java.lang.String fullyQualifiedName()
	{
		return _id + org.drip.capital.label.Coordinate.FQN_DELIMITER + _riskType;
	}
}
