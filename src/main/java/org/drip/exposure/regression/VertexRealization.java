
package org.drip.exposure.regression;

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
 * VertexRealization holds the Vertex Exposure Realizations to be used in eventual Exposure Regression. The
 *  References are:
 *  
 *  - Andersen, L. B. G., M. Pykhtin, and A. Sokol (2017): Re-thinking Margin Period of Risk,
 *  	https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2902737, eSSRN.
 *  
 *  - Andersen, L. B. G., M. Pykhtin, and A. Sokol (2017): Credit Exposure in the Presence of Initial Margin,
 *  	https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2806156, eSSRN.
 *  
 *  - Albanese, C., and L. Andersen (2014): Accounting for OTC Derivatives: Funding Adjustments and the
 *  	Re-Hypothecation Option, eSSRN, https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2482955.
 *  
 *  - Burgard, C., and M. Kjaer (2017): Derivatives Funding, Netting, and Accounting, eSSRN,
 *  	https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2534011.
 * 
 *  - Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives Pricing, Risk
 *  	21 (2) 97-102.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class VertexRealization
{
	private java.util.List<java.lang.Double> _exposureList = null;

	/**
	 * Construct an Instance of UncollateralizedVertexExposure from the Exposure Array
	 * 
	 * @param exposureArray The Exposure Array
	 * 
	 * @return The VertexRealization Instance
	 */

	public static final VertexRealization Standard (
		final double[] exposureArray)
	{
		if (null == exposureArray)
		{
			return null;
		}

		java.util.List<java.lang.Double> exposureList = new java.util.ArrayList<java.lang.Double>();

		int exposureCount = exposureArray.length;

		if (0 == exposureCount)
		{
			return null;
		}

		for (double exposure : exposureArray)
		{
			if (!org.drip.quant.common.NumberUtil.IsValid (exposure))
			{
				return null;
			}

			exposureList.add (exposure);
		}

		java.util.Collections.sort (exposureList);

		try
		{
			return new VertexRealization (exposureList);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	protected VertexRealization (
		final java.util.List<java.lang.Double> exposureList)
		throws java.lang.Exception
	{
		if (null == (_exposureList = exposureList) || 0 == _exposureList.size())
		{
			throw new java.lang.Exception ("VertexRealization Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Exposure Set
	 * 
	 * @return The Exposure Set
	 */

	public java.util.List<java.lang.Double> exposureList()
	{
		return _exposureList;
	}

	/**
	 * Retrieve the Realization Dynamics Array
	 * 
	 * @param localVolatilityGenerationControl The Local Volatility Generation Control
	 * 
	 * @return The Realization Dynamics Array
	 */

	public org.drip.exposure.regression.RealizationPoint[] realizationDynamicsArray (
		final org.drip.exposure.regression.LocalVolatilityGenerationControl localVolatilityGenerationControl)
	{
		if (null == localVolatilityGenerationControl)
		{
			return null;
		}

		int realizationCount = _exposureList.size();

		double[] uniformCPDArray = localVolatilityGenerationControl.uniformCPDArray();

		int localVolatilityIndexShift = localVolatilityGenerationControl.localVolatilityIndexShift();

		double[] impliedBrownianVariateArray = localVolatilityGenerationControl.impliedBrownianVariateArray();

		int realizationIndex = 0;
		double[] exposureArray = new double[realizationCount];
		int localVolatilityIndexFloor = localVolatilityIndexShift;
		double[] localVolatilityArray = new double[realizationCount];
		int localVolatilityIndexCeiling = realizationCount - localVolatilityIndexShift;
		org.drip.exposure.regression.RealizationPoint[] realizationPointArray = new
			org.drip.exposure.regression.RealizationPoint[realizationCount];

		for (double exposure : _exposureList)
		{
			exposureArray[realizationIndex++] = exposure;
		}

		for (int realizationCoordinate = localVolatilityIndexFloor;
			realizationCoordinate < localVolatilityIndexCeiling;
			++realizationCoordinate)
		{
			localVolatilityArray[realizationCoordinate] =
				(exposureArray[realizationCoordinate - localVolatilityIndexShift] -
					exposureArray[realizationCoordinate + localVolatilityIndexShift]) /
				(impliedBrownianVariateArray[realizationCoordinate - localVolatilityIndexShift] -
					impliedBrownianVariateArray[realizationCoordinate + localVolatilityIndexShift]);
		}

		for (int realizationCoordinate = 0;
			realizationCoordinate < localVolatilityIndexFloor;
			++realizationCoordinate)
		{
			localVolatilityArray[realizationCoordinate] = localVolatilityArray[localVolatilityIndexFloor];
		}

		for (int realizationCoordinate = localVolatilityIndexCeiling;
			realizationCoordinate < realizationCount;
			++realizationCoordinate)
		{
			localVolatilityArray[realizationCoordinate] = localVolatilityArray[localVolatilityIndexCeiling - 1];
		}

		for (int realizationCoordinate = 0; realizationCoordinate < realizationCount;
			++realizationCoordinate)
		{
			try
			{
				realizationPointArray[realizationCoordinate] = new
					org.drip.exposure.regression.RealizationPoint (
						exposureArray[realizationCoordinate],
						realizationCoordinate,
						uniformCPDArray[realizationCoordinate],
						impliedBrownianVariateArray[realizationCoordinate],
						localVolatilityArray[realizationCoordinate]
					);

				++realizationIndex;
			}
			catch (java.lang.Exception e)
			{
				e.printStackTrace();

				return null;
			}
		}

		return realizationPointArray;
	}

	/**
	 * Generate a Local Volatility R^1 To R^1
	 * 
	 * @param localVolatilityGenerationControl The Local Volatility Generation Control
	 * @param realizationPointArray The Array of the Realization Points
	 * 
	 * @return The Local Volatility R^1 To R^1
	 */

	public org.drip.function.definition.R1ToR1 localVolatilityR1ToR1 (
		final org.drip.exposure.regression.LocalVolatilityGenerationControl localVolatilityGenerationControl,
		final org.drip.exposure.regression.RealizationPoint[] realizationPointArray)
	{
		if (null == localVolatilityGenerationControl)
		{
			return null;
		}

		int vertexCount = realizationPointArray.length;
		double[] exposureArray = new double[vertexCount];
		double[] localVolatilityArray = new double[vertexCount];

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
		{
			exposureArray[vertexIndex] = realizationPointArray[vertexIndex].exposure();

			localVolatilityArray[vertexIndex] = realizationPointArray[vertexIndex].localVolatility();
		}

		org.drip.spline.stretch.MultiSegmentSequence multiSegmentSequence =
			org.drip.spline.stretch.MultiSegmentSequenceBuilder.CreateCalibratedStretchEstimator (
				"LocalVolatilityR1ToR1_" + org.drip.quant.common.StringUtil.GUID(),
				exposureArray,
				localVolatilityArray,
				localVolatilityGenerationControl.segmentCustomBuilderControlArray(),
				null,
				org.drip.spline.stretch.BoundarySettings.NaturalStandard(),
				org.drip.spline.stretch.MultiSegmentSequence.CALIBRATE
			);

		return null == multiSegmentSequence ? null : multiSegmentSequence.toAU();
	}

	/**
	 * Generate a Local Volatility R^1 To R^1
	 * 
	 * @param localVolatilityGenerationControl The Local Volatility Generation Control
	 * 
	 * @return The Local Volatility R^1 To R^1
	 */

	public org.drip.function.definition.R1ToR1 localVolatilityR1ToR1 (
		final org.drip.exposure.regression.LocalVolatilityGenerationControl localVolatilityGenerationControl)
	{
		return localVolatilityR1ToR1 (
			localVolatilityGenerationControl,
			realizationDynamicsArray (localVolatilityGenerationControl)
		);
	}
}
