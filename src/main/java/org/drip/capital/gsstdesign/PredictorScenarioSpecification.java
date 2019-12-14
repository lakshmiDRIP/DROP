
package org.drip.capital.gsstdesign;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Quantitative Risk Analytics
 */

/**
 * <i>PredictorScenarioSpecification</i> specifies the Full Stress Scenario Specification for the given
 * 	Predictor across Market Segments. The References are:
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

public class PredictorScenarioSpecification
{
	private java.lang.String _name = "";
	private java.lang.String _category = "";
	private java.util.Map<java.lang.String, org.drip.capital.gsstdesign.StressScenarioSpecification>
		_segmentScenarioSpecificationMap = null;

	/**
	 * PredictorScenarioSpecification Constructor
	 * 
	 * @param name Predictor Name
	 * @param category Predictor Category
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public PredictorScenarioSpecification (
		final java.lang.String name,
		final java.lang.String category)
		throws java.lang.Exception
	{
		if (null == (_name = name) || _name.isEmpty() ||
			null == (_category = category) || _category.isEmpty())
		{
			throw new java.lang.Exception ("PredictorScenarioSpecification Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Predictor Name
	 * 
	 * @return The Predictor Name
	 */

	public java.lang.String name()
	{
		return _name;
	}

	/**
	 * Retrieve the Predictor Category
	 * 
	 * @return The Predictor Category
	 */

	public java.lang.String category()
	{
		return _category;
	}

	/**
	 * Retrieve the Market Segment Stress Scenario Specification Map
	 * 
	 * @return The Market Segment Stress Scenario Specification Map
	 */

	public java.util.Map<java.lang.String, org.drip.capital.gsstdesign.StressScenarioSpecification>
		segmentScenarioSpecificationMap()
	{
		return _segmentScenarioSpecificationMap;
	}

	/**
	 * Add the Stress Scenario Specification
	 * 
	 * @param marketSegment The Market Segment
	 * @param segmentScenarioSpecification The Stress Scenario Specification
	 * 
	 * @return TRUE - The Stress Scenario Specification successfully added
	 */

	public boolean addStressScenarioSpecification (
		final java.lang.String marketSegment,
		final org.drip.capital.gsstdesign.StressScenarioSpecification segmentScenarioSpecification)
	{
		if (null == marketSegment || marketSegment.isEmpty() ||
			null == segmentScenarioSpecification)
		{
			return false;
		}

		if (null == _segmentScenarioSpecificationMap)
		{
			_segmentScenarioSpecificationMap = new
				org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.capital.gsstdesign.StressScenarioSpecification>();
		}

		_segmentScenarioSpecificationMap.put (
			marketSegment,
			segmentScenarioSpecification
		);

		return true;
	}

	/**
	 * Indicate the Presence of the Market Segment
	 * 
	 * @param marketSegment Market Segment
	 * 
	 * @return TRUE - The Market Segment is Present
	 */

	public boolean containsStressScenarioSpecification (
		final java.lang.String marketSegment)
	{
		return null != marketSegment && !marketSegment.isEmpty() &&
			_segmentScenarioSpecificationMap.containsKey (marketSegment);
	}

	/**
	 * Retrieve the Stress Scenario Specification given the Market Segment
	 * 
	 * @param marketSegment Market Segment
	 * 
	 * @return The Stress Scenario Specification
	 */

	public org.drip.capital.gsstdesign.StressScenarioSpecification stressScenarioSpecification (
		final java.lang.String marketSegment)
	{
		return containsStressScenarioSpecification (marketSegment) ?
			_segmentScenarioSpecificationMap.get (marketSegment) : null;
	}
}
