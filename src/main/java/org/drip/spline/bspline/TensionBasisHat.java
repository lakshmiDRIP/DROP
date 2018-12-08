
package org.drip.spline.bspline;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting risk, transaction costs, exposure, margin
 *  	calculations, and portfolio construction within and across fixed income, credit, commodity, equity,
 *  	FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three main modules:
 *  
 *  - DROP Analytics Core - https://lakshmidrip.github.io/DROP-Analytics-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Numerical Core - https://lakshmidrip.github.io/DROP-Numerical-Core/
 * 
 * 	DROP Analytics Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Asset Backed Analytics
 * 	- XVA Analytics
 * 	- Exposure and Margin Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Numerical Core implements libraries for the following:
 * 	- Statistical Learning Library
 * 	- Numerical Optimizer Library
 * 	- Machine Learning Library
 * 	- Spline Builder Library
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
 * 	- Javadoc                  => https://lakshmidrip.github.io/DROP/Javadoc/index.html
 * 	- Technical Specifications => https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal
 * 	- Release Versions         => https://lakshmidrip.github.io/DROP/version.html
 * 	- Community Credits        => https://lakshmidrip.github.io/DROP/credits.html
 * 	- Issues Catalog           => https://github.com/lakshmiDRIP/DROP/issues
 * 	- JUnit                    => https://lakshmidrip.github.io/DROP/junit/index.html
 * 	- Jacoco                   => https://lakshmidrip.github.io/DROP/jacoco/index.html
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
 * <i>TensionBasisHat</i> implements the common basis hat function that form the basis for all B Splines. It
 * contains the left/right ordinates, the tension, and the normalizer.
 *
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/SplineBuilderLibrary.md">Spline Builder Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline">Spline</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/bspline">B Spline</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public abstract class TensionBasisHat extends org.drip.function.definition.R1ToR1 {
	private double _dblTension = java.lang.Double.NaN;
	private double _dblLeftPredictorOrdinate = java.lang.Double.NaN;
	private double _dblRightPredictorOrdinate = java.lang.Double.NaN;

	protected TensionBasisHat (
		final double dblLeftPredictorOrdinate,
		final double dblRightPredictorOrdinate,
		final double dblTension)
		throws java.lang.Exception
	{
		super (null);

		if (!org.drip.quant.common.NumberUtil.IsValid (_dblLeftPredictorOrdinate = dblLeftPredictorOrdinate)
			|| !org.drip.quant.common.NumberUtil.IsValid (_dblRightPredictorOrdinate =
				dblRightPredictorOrdinate) || !org.drip.quant.common.NumberUtil.IsValid (_dblTension =
					dblTension))
			throw new java.lang.Exception ("TensionBasisHat ctr: Invalid Inputs");
	}

	/**
	 * Identifies if the ordinate is local to the range
	 * 
	 * @param dblPredictorOrdinate The Predictor Ordinate
	 * 
	 * @return TRUE - The Ordinate is local to the Specified Range
	 * 
	 * @throws java.lang.Exception Thrown if the inputs are invalid
	 */

	public boolean in (
		final double dblPredictorOrdinate)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblPredictorOrdinate))
			throw new java.lang.Exception ("TensionBasisHat::in => Invalid Input");

		return dblPredictorOrdinate >= _dblLeftPredictorOrdinate && dblPredictorOrdinate <=
			_dblRightPredictorOrdinate;
	}

	/**
	 * Retrieve the Left Predictor Ordinate
	 * 
	 * @return The Left Predictor Ordinate
	 */

	public double left()
	{
		return _dblLeftPredictorOrdinate;
	}

	/**
	 * Retrieve the Right Predictor Ordinate
	 * 
	 * @return The Right Predictor Ordinate
	 */

	public double right()
	{
		return _dblRightPredictorOrdinate;
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
	 * Compute the Normalizer
	 * 
	 * @return The Normalizer
	 * 
	 * @throws java.lang.Exception Thrown if the Normalizer cannot be computed
	 */

	public abstract double normalizer()
		throws java.lang.Exception;
}
