
package org.drip.spline.basis;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2025 Lakshmi Krishnamurthy
 * Copyright (C) 2024 Lakshmi Krishnamurthy
 * Copyright (C) 2023 Lakshmi Krishnamurthy
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting analytics/risk, transaction cost analytics,
 *  	asset liability management analytics, capital, exposure, and margin analytics, valuation adjustment
 *  	analytics, and portfolio construction analytics within and across fixed income, credit, commodity,
 *  	equity, FX, and structured products. It also includes auxiliary libraries for algorithm support,
 *  	numerical analysis, numerical optimization, spline builder, model validation, statistical learning,
 *  	graph builder/navigator, and computational support.
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
 *  - Graph Algorithm
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
 * <i>BSplineSequenceParams</i> implements the parameter set for constructing the B Spline Sequence. It
 * 	provides functionality to:
 *
 *  <ul>
 * 		<li><i>BSplineSequenceParams</i> Constructor</li>
 * 		<li>Retrieve the B Spline Order</li>
 * 		<li>Retrieve the Number of Basis Functions</li>
 * 		<li>Retrieve the Processed Basis Derivative Order</li>
 * 		<li>Retrieve the Basis Hat Type</li>
 * 		<li>Retrieve the Shape Control Type</li>
 * 		<li>Retrieve the Tension</li>
 * 		<li>Retrieve the Array of Predictor Ordinates</li>
 *  </ul>
 *
 *  <br>
 *  <style>table, td, th {
 *  	padding: 1px; border: 2px solid #008000; border-radius: 8px; background-color: #dfff00;
 *		text-align: center; color:  #0000ff;
 *  }
 *  </style>
 *  
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/README.md">Basis Splines and Linear Compounders across a Broad Family of Spline Basis Functions</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/basis/README.md">Basis Spline Construction/Customization Parameters</a></td></tr>
 *  </table>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class BSplineSequenceParams
{
	private String _hatType = "";
	private double _tension = Double.NaN;
	private String _shapeControlType = "";
	private int _basisCount = Integer.MIN_VALUE;
	private int _bSplineOrder = Integer.MIN_VALUE;
	private int _processedBasisDerivativeOrder = Integer.MIN_VALUE;

	/**
	 * <i>BSplineSequenceParams</i> Constructor
	 * 
	 * @param hatType Hat Type
	 * @param shapeControlType Shape Controller Type
	 * @param basisCount Number of Basis
	 * @param bSplineOrder Spline Penalty Order
	 * @param tension Tension
	 * @param processedBasisDerivativeOrder Processed Basis Derivative Order
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public BSplineSequenceParams (
		final String hatType,
		final String shapeControlType,
		final int basisCount,
		final int bSplineOrder,
		final double tension,
		final int processedBasisDerivativeOrder)
		throws Exception
	{
		_hatType = hatType;
		_tension = tension;
		_basisCount = basisCount;
		_bSplineOrder = bSplineOrder;
		_shapeControlType = shapeControlType;
		_processedBasisDerivativeOrder = processedBasisDerivativeOrder;
	}

	/**
	 * Retrieve the B Spline Order
	 * 
	 * @return The B Spline Order
	 */

	public int bSplineOrder()
	{
		return _bSplineOrder;
	}

	/**
	 * Retrieve the Number of Basis Functions
	 * 
	 * @return The Number of Basis Functions
	 */

	public int numBasis()
	{
		return _basisCount;
	}

	/**
	 * Retrieve the Processed Basis Derivative Order
	 * 
	 * @return The Processed Basis Derivative Order
	 */

	public int procBasisDerivOrder()
	{
		return _processedBasisDerivativeOrder;
	}

	/**
	 * Retrieve the Basis Hat Type
	 * 
	 * @return The Basis Hat Type
	 */

	public String hat()
	{
		return _hatType;
	}

	/**
	 * Retrieve the Shape Control Type
	 * 
	 * @return The Shape Control Type
	 */

	public String shapeControl()
	{
		return _shapeControlType;
	}

	/**
	 * Retrieve the Tension
	 * 
	 * @return The Tension
	 */

	public double tension()
	{
		return _tension;
	}

	/**
	 * Retrieve the Array of Predictor Ordinates
	 * 
	 * @return The Array of Predictor Ordinates
	 */

	public double[] predictorOrdinates()
	{
		int predictorOrdinateCount = _bSplineOrder + _basisCount;
		double[] predictorOrdinateArray = new double[predictorOrdinateCount];
		double predictorOrdinateIncrement = 1. / (_bSplineOrder + _basisCount - 1);

		for (int predictorOrdinateIndex = 0; predictorOrdinateIndex < predictorOrdinateCount;
			++predictorOrdinateIndex) {
			predictorOrdinateArray[predictorOrdinateIndex] = predictorOrdinateIncrement *
				predictorOrdinateIndex;
		}

		return predictorOrdinateArray;
	}
}
