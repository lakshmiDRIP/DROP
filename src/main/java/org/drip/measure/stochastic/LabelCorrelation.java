
package org.drip.measure.stochastic;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * 
 *  This file is part of DRIP, a free-software/open-source library for buy/side financial/trading model
 *  	libraries targeting analysts and developers
 *  	https://lakshmidrip.github.io/DRIP/
 *  
 *  DRIP is composed of four main libraries:
 *  
 *  - DRIP Fixed Income - https://lakshmidrip.github.io/DRIP-Fixed-Income/
 *  - DRIP Asset Allocation - https://lakshmidrip.github.io/DRIP-Asset-Allocation/
 *  - DRIP Numerical Optimizer - https://lakshmidrip.github.io/DRIP-Numerical-Optimizer/
 *  - DRIP Statistical Learning - https://lakshmidrip.github.io/DRIP-Statistical-Learning/
 * 
 *  - DRIP Fixed Income: Library for Instrument/Trading Conventions, Treasury Futures/Options,
 *  	Funding/Forward/Overnight Curves, Multi-Curve Construction/Valuation, Collateral Valuation and XVA
 *  	Metric Generation, Calibration and Hedge Attributions, Statistical Curve Construction, Bond RV
 *  	Metrics, Stochastic Evolution and Option Pricing, Interest Rate Dynamics and Option Pricing, LMM
 *  	Extensions/Calibrations/Greeks, Algorithmic Differentiation, and Asset Backed Models and Analytics.
 * 
 *  - DRIP Asset Allocation: Library for model libraries for MPT framework, Black Litterman Strategy
 *  	Incorporator, Holdings Constraint, and Transaction Costs.
 * 
 *  - DRIP Numerical Optimizer: Library for Numerical Optimization and Spline Functionality.
 * 
 *  - DRIP Statistical Learning: Library for Statistical Evaluation and Machine Learning.
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
 * LabelCorrelation holds the Correlations between any Stochastic Variates identified by their Labels. The
 *  References are:
 *  
 *  - Andersen, L. B. G., M. Pykhtin, and A. Sokol (2017): Credit Exposure in the Presence of Initial Margin,
 *  	https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2806156, eSSRN.
 *  
 *  - Albanese, C., S. Caenazzo, and O. Frankel (2017): Regression Sensitivities for Initial Margin
 *  	Calculations, https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2763488, eSSRN.
 *  
 *  - Anfuso, F., D. Aziz, P. Giltinan, and K. Loukopoulus (2017): A Sound Modeling and Back-testing
 *  	Framework for Forecasting Initial Margin Requirements,
 *  	https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2716279, eSSRN.
 *  
 *  - Caspers, P., P. Giltinan, R. Lichters, and N. Nowaczyk (2017): Forecasting Initial Margin Requirements
 *  	- A Model Evaluation https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2911167, eSSRN.
 *  
 *  - International Swaps and Derivatives Association (2017): SIMM v2.0 Methodology,
 *  	https://www.isda.org/a/oFiDE/isda-simm-v2.pdf.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class LabelCorrelation
{
	private double[][] _matrix = null;
	private java.util.List<java.lang.String> _labelList = null;

	private java.util.Map<java.lang.String, java.lang.Integer> _labelIndexMap = new
		java.util.HashMap<java.lang.String, java.lang.Integer>();

	/**
	 * LabelCorrelation Constructor
	 * 
	 * @param labelList The List of Labels
	 * @param matrix The Correlation Matrix
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public LabelCorrelation (
		final java.util.List<java.lang.String> labelList,
		final double[][] matrix)
		throws java.lang.Exception
	{
		if (null == (_labelList = labelList) ||
			null == (_matrix = matrix))
		{
			throw new java.lang.Exception ("LabelCorrelation Constructor => Invalid Inputs");
		}

		int labelCount = _labelList.size();

		if (0 == labelCount || labelCount != _matrix.length)
		{
			throw new java.lang.Exception ("LabelCorrelation Constructor => Invalid Inputs");
		}

		for (int labelIndex = 0; labelIndex < labelCount; ++labelIndex)
		{
			_labelIndexMap.put (
				_labelList.get (labelIndex),
				labelIndex
			);

			if (null == _matrix[labelIndex] || labelCount != _matrix[labelIndex].length ||
				!org.drip.quant.common.NumberUtil.IsValid (_matrix[labelIndex]))
			{
				throw new java.lang.Exception ("LabelCorrelation Constructor => Invalid Inputs");
			}
		}
	}

	/**
	 * Retrieve the Cross-Label Correlation Matrix
	 * 
	 * @return The Cross-Label Correlation Matrix
	 */

	public double[][] matrix()
	{
		return _matrix;
	}

	/**
	 * Retrieve the Label List
	 * 
	 * @return The Label List
	 */

	public java.util.List<java.lang.String> labelList()
	{
		return _labelList;
	}

	/**
	 * Retrieve the Correlation Entry for the Pair of Labels
	 * 
	 * @param label1 Label #1
	 * @param label2 Label #2
	 * 
	 * @return The Correlation Entry
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double entry (
		final java.lang.String label1,
		final java.lang.String label2)
		throws java.lang.Exception
	{
		if (null == label1 || !_labelList.contains (label1) ||
			null == label2 || !_labelList.contains (label2))
		{
			throw new java.lang.Exception ("LabelCorrelation::entry => Invalid Inputs");
		}

		return _matrix[_labelIndexMap.get (label1)][_labelIndexMap.get (label2)];
	}

	/**
	 * Generate the InterestRateTenorCorrelation Instance that corresponds to the Tenor sub-space
	 * 
	 * @param subTenorList The sub-Tenor List
	 * 
	 * @return The InterestRateTenorCorrelation Instance
	 */

	public LabelCorrelation subTenor (
		final java.util.List<java.lang.String> subTenorList)
	{
		if (null == subTenorList)
		{
			return null;
		}

		int subTenorSize = subTenorList.size();

		if (0 == subTenorSize)
		{
			return null;
		}

		double[][] subTenorMatrix = new double[subTenorSize][subTenorSize];

		for (int subTenorOuterIndex = 0; subTenorOuterIndex < subTenorSize; ++subTenorOuterIndex)
		{
			for (int subTenorInnerIndex = 0; subTenorInnerIndex < subTenorSize; ++subTenorInnerIndex)
			{
				try
				{
					subTenorMatrix[subTenorOuterIndex][subTenorInnerIndex] = entry (
						subTenorList.get (subTenorOuterIndex),
						subTenorList.get (subTenorInnerIndex)
					);
				}
				catch (java.lang.Exception e)
				{
					e.printStackTrace();

					return null;
				}
			}
		}

		try
		{
			return new LabelCorrelation (
				subTenorList,
				subTenorMatrix
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
