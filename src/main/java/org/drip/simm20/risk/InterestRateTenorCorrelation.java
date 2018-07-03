
package org.drip.simm20.risk;

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
 * InterestRateTenorCorrelation holds the ISDA SIMM 2.0 Tenor Correlations with a Single Currency/Curve. The
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

public class InterestRateTenorCorrelation
{
	private double[][] _matrix = null;
	private java.util.List<java.lang.String> _tenorList = null;

	private java.util.Map<java.lang.String, java.lang.Integer> _tenorIndexMap = new
		java.util.HashMap<java.lang.String, java.lang.Integer>();

	/**
	 * InterestRateTenorCorrelation Constructor
	 * 
	 * @param tenorList The List of Tenors
	 * @param matrix The Correlation Matrix
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public InterestRateTenorCorrelation (
		final java.util.List<java.lang.String> tenorList,
		final double[][] matrix)
		throws java.lang.Exception
	{
		if (null == (_tenorList = tenorList) ||
			null == (_matrix = matrix))
		{
			throw new java.lang.Exception ("InterestRateTenorCorrelation Constructor => Invalid Inputs");
		}

		int tenorCount = _tenorList.size();

		if (0 == tenorCount || tenorCount != _matrix.length)
		{
			throw new java.lang.Exception ("InterestRateTenorCorrelation Constructor => Invalid Inputs");
		}

		for (int tenorIndex = 0; tenorIndex < tenorCount; ++tenorIndex)
		{
			_tenorIndexMap.put (
				_tenorList.get (tenorIndex),
				tenorIndex
			);

			if (null == _matrix[tenorIndex] || tenorCount != _matrix[tenorIndex].length ||
				!org.drip.quant.common.NumberUtil.IsValid (_matrix[tenorIndex]))
			{
				throw new java.lang.Exception ("InterestRateTenorCorrelation Constructor => Invalid Inputs");
			}
		}
	}

	/**
	 * Retrieve the Cross-Tenor Correlation Matrix
	 * 
	 * @return The Cross-Tenor Correlation Matrix
	 */

	public double[][] matrix()
	{
		return _matrix;
	}

	/**
	 * Retrieve the Tenor List
	 * 
	 * @return The Tenor List
	 */

	public java.util.List<java.lang.String> tenorList()
	{
		return _tenorList;
	}

	/**
	 * Retrieve the Correlation Entry for the Pait of Tenors
	 * 
	 * @param tenor1 Tenor #1
	 * @param tenor2 Tenor #2
	 * 
	 * @return The Correlation Entry
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double entry (
		final java.lang.String tenor1,
		final java.lang.String tenor2)
		throws java.lang.Exception
	{
		if (null == tenor1 || !_tenorList.contains (tenor1) ||
			null == tenor2 || !_tenorList.contains (tenor2))
		{
			throw new java.lang.Exception ("InterestRateTenorCorrelation::entry => Invalid Inputs");
		}

		return _matrix[_tenorIndexMap.get (tenor1)][_tenorIndexMap.get (tenor2)];
	}

	/**
	 * Generate the InterestRateTenorCorrelation Instance that corresponds to the Tenor sub-space
	 * 
	 * @param subTenorList The sub-Tenor List
	 * 
	 * @return The InterestRateTenorCorrelation Instance
	 */

	public InterestRateTenorCorrelation subTenor (
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
			return new InterestRateTenorCorrelation (
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
