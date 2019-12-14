
package org.drip.capital.shell;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Quantitative Risk Analytics
 */

/**
 * <i>PredictorScenarioSpecificationContainer</i> maintains the Map of Predictors and their Scenario Stress
 * 	Specification as well the Map of Predictors and their Categories. The References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Bank for International Supervision(2005): Stress Testing at Major Financial Institutions: Survey
 * 				Results and Practice https://www.bis.org/publ/cgfs24.htm
 * 		</li>
 * 		<li>
 * 			Glasserman, P. (2004): <i>Monte-Carlo Methods in Financial Engineering</i> <b>Springer</b>
 * 		</li>
 * 		<li>
 * 			Kupiec, P. H. (2000): Stress Tests and Risk Capital <i>Risk</i> <b>2 (4)</b> 27-39
 * 		</li>
 * 	</ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class PredictorScenarioSpecificationContainer
{
	private java.util.Map<java.lang.String, java.util.List<java.lang.String>>
		_categoryPredictorListMap = null;
	private java.util.Map<java.lang.String, org.drip.capital.gsstdesign.PredictorScenarioSpecification>
		_predictorScenarioSpecificationMap = null;

	/**
	 * Empty PredictorScenarioSpecificationContainer Constructor
	 */

	public PredictorScenarioSpecificationContainer()
	{
	}

	/**
	 * Retrieve the Predictor Stress Scenario Specification Map
	 * 
	 * @return The Predictor Stress Scenario Specification Map
	 */

	public java.util.Map<java.lang.String, org.drip.capital.gsstdesign.PredictorScenarioSpecification>
		predictorScenarioSpecificationMap()
	{
		return _predictorScenarioSpecificationMap;
	}

	/**
	 * Retrieve the Map of the Categories to their corresponding Predictor Lists
	 * 
	 * @return Map of the Categories to their corresponding Predictor Lists
	 */

	public java.util.Map<java.lang.String, java.util.List<java.lang.String>> categoryPredictorListMap()
	{
		return _categoryPredictorListMap;
	}

	/**
	 * Add the specified Predictor Scenario Specification
	 * 
	 * @param predictorScenarioSpecification The Predictor Scenario Specification
	 * 
	 * @return TRUE - The Predictor Scenario Specification successfully added
	 */

	public boolean addPredictorScenarioSpecification (
		final org.drip.capital.gsstdesign.PredictorScenarioSpecification predictorScenarioSpecification)
	{
		if (null == predictorScenarioSpecification)
		{
			return false;
		}

		java.lang.String name = predictorScenarioSpecification.name();

		java.lang.String category = predictorScenarioSpecification.category();

		if (null == _predictorScenarioSpecificationMap)
		{
			_predictorScenarioSpecificationMap = new
				org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.capital.gsstdesign.PredictorScenarioSpecification>();
		}

		_predictorScenarioSpecificationMap.put (
			name,
			predictorScenarioSpecification
		);

		if (null == _categoryPredictorListMap)
		{
			_categoryPredictorListMap =
				new org.drip.analytics.support.CaseInsensitiveHashMap<java.util.List<java.lang.String>>();
		}

		if (_categoryPredictorListMap.containsKey (
			category
		))
		{
			_categoryPredictorListMap.get (
				category
			).add (
				name
			);
		}
		else
		{
			java.util.List<java.lang.String> nameList = new java.util.ArrayList<java.lang.String>();

			nameList.add (
				name
			);

			_categoryPredictorListMap.put (
				category,
				nameList
			);
		}

		return true;
	}

	/**
	 * Indicate if the Predictor has a Stress Specification Available
	 * 
	 * @param predictorName The Predictor
	 * 
	 * @return TRUE - The Predictor has a Stress Specification Available
	 */

	public boolean containsPredictor (
		final java.lang.String predictorName)
	{
		return null != predictorName && !predictorName.isEmpty() &&
			null != _predictorScenarioSpecificationMap &&
			_predictorScenarioSpecificationMap.containsKey (
				predictorName
			);
	}

	/**
	 * Indicate if the Category has Predictor(s) Available
	 * 
	 * @param category The Category
	 * 
	 * @return TRUE - The Category has Predictor(s) Available
	 */

	public boolean containsCategory (
		final java.lang.String category)
	{
		return null != category && !category.isEmpty() && null != _categoryPredictorListMap &&
			_categoryPredictorListMap.containsKey (
				category
			);
	}

	/**
	 * Retrieve the Predictor Scenario Specification
	 * 
	 * @param predictorName The Predictor
	 * 
	 * @return The Predictor Scenario Specification
	 */

	public org.drip.capital.gsstdesign.PredictorScenarioSpecification predictorScenarioSpecification (
		final java.lang.String predictorName)
	{
		return containsPredictor (
			predictorName
		) ? _predictorScenarioSpecificationMap.get (
			predictorName
		) : null;
	}

	/**
	 * Retrieve the Predictors corresponding to the Category
	 * 
	 * @param category The Category
	 * 
	 * @return The Predictors corresponding to the Category
	 */

	public java.util.List<java.lang.String> predictorList (
		final java.lang.String category)
	{
		return containsCategory (
			category
		) ? _categoryPredictorListMap.get (
			category
		) : null;
	}
}
