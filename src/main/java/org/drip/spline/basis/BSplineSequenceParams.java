
package org.drip.spline.basis;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
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
 * provides functionality to:
 *
 * <br><br>
 *  <ul>
 *  	<li>
 * 			Retrieve the B Spline Order
 *  	</li>
 *  	<li>
 * 			Retrieve the Number of Basis Functions
 *  	</li>
 *  	<li>
 * 			Retrieve the Processed Basis Derivative Order
 *  	</li>
 *  	<li>
 * 			Retrieve the Basis Hat Type
 *  	</li>
 *  	<li>
 * 			Retrieve the Shape Control Type
 *  	</li>
 *  	<li>
 * 			Retrieve the Tension
 *  	</li>
 *  	<li>
 * 			Retrieve the Array of Predictor Ordinates
 *  	</li>
 *  </ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/SplineBuilderLibrary.md">Spline Builder Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/README.md">Basis Splines and Linear Compounders across a Broad Family of Spline Basis Functions</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/basis/README.md">Basis Spline Construction/Customization Parameters</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class BSplineSequenceParams {
	private int _iNumBasis = -1;
	private int _iBSplineOrder = -1;
	private int _iProcBasisDerivOrder = -1;
	private java.lang.String _strHatType = "";
	private double _dblTension = java.lang.Double.NaN;
	private java.lang.String _strShapeControlType = "";

	public BSplineSequenceParams (
		final java.lang.String strHatType,
		final java.lang.String strShapeControlType,
		final int iNumBasis,
		final int iBSplineOrder,
		final double dblTension,
		final int iProcBasisDerivOrder)
		throws java.lang.Exception
	{
		_iNumBasis = iNumBasis;
		_strHatType = strHatType;
		_dblTension = dblTension;
		_iBSplineOrder = iBSplineOrder;
		_strShapeControlType = strShapeControlType;
		_iProcBasisDerivOrder = iProcBasisDerivOrder;
	}

	/**
	 * Retrieve the B Spline Order
	 * 
	 * @return The B Spline Order
	 */

	public int bSplineOrder()
	{
		return _iBSplineOrder;
	}

	/**
	 * Retrieve the Number of Basis Functions
	 * 
	 * @return The Number of Basis Functions
	 */

	public int numBasis()
	{
		return _iNumBasis;
	}

	/**
	 * Retrieve the Processed Basis Derivative Order
	 * 
	 * @return The Processed Basis Derivative Order
	 */

	public int procBasisDerivOrder()
	{
		return _iProcBasisDerivOrder;
	}

	/**
	 * Retrieve the Basis Hat Type
	 * 
	 * @return The Basis Hat Type
	 */

	public java.lang.String hat()
	{
		return _strHatType;
	}

	/**
	 * Retrieve the Shape Control Type
	 * 
	 * @return The Shape Control Type
	 */

	public java.lang.String shapeControl()
	{
		return _strShapeControlType;
	}

	/**
	 * Retrieve the Tension
	 * 
	 * @return The Tension
	 */

	public double tension()
	{
		return _dblTension;
	}

	/**
	 * Retrieve the Array of Predictor Ordinates
	 * 
	 * @return The Array of Predictor Ordinates
	 */

	public double[] predictorOrdinates()
	{
		int iNumPredictorOrdinate = _iBSplineOrder + _iNumBasis;
		double[] adblPredictorOrdinate = new double[iNumPredictorOrdinate];
		double dblPredictorOrdinateIncrement = 1. / (_iBSplineOrder + _iNumBasis - 1);

		for (int i = 0; i < iNumPredictorOrdinate; ++i)
			adblPredictorOrdinate[i] = dblPredictorOrdinateIncrement * i;

		return adblPredictorOrdinate;
	}
}
