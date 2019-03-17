
package org.drip.measure.joint;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
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
 * <i>Evolver</i> exposes the Functionality that guides the Multi-Factor Random Process Variable Evolution.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalOptimizerLibrary.md">Numerical Optimizer Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure">Measure</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/joint">Joint</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public abstract class Evolver {
	private double[][] _aadblCorrelation = null;
	private org.drip.measure.dynamics.LocalEvaluator[] _aLDEVDrift = null;
	private org.drip.measure.dynamics.LocalEvaluator[] _aLDEVVolatility = null;

	protected Evolver (
		final org.drip.measure.dynamics.LocalEvaluator[] aLDEVDrift,
		final org.drip.measure.dynamics.LocalEvaluator[] aLDEVVolatility,
		final double[][] aadblCorrelation)
		throws java.lang.Exception
	{
		if (null == (_aLDEVDrift = aLDEVDrift) || null == (_aLDEVVolatility = aLDEVVolatility) || null ==
			(_aadblCorrelation = aadblCorrelation))
			throw new java.lang.Exception ("Evolver Constructor => Invalid Inputs");

		int iNumFactor = _aLDEVDrift.length;

		if (0 == iNumFactor || iNumFactor != _aLDEVVolatility.length || iNumFactor !=
			_aadblCorrelation.length)
			throw new java.lang.Exception ("Evolver Constructor => Invalid Inputs");

		for (int i = 0; i < iNumFactor; ++i) {
			if (null == _aLDEVDrift[i] || null == _aLDEVVolatility[i] || null == _aadblCorrelation[i] ||
				iNumFactor != _aadblCorrelation[i].length || !org.drip.numerical.common.NumberUtil.IsValid
					(_aadblCorrelation[i]))
				throw new java.lang.Exception ("Evolver Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Array of the LDEV Drift Functions of the Individual Marginal Processes
	 * 
	 * @return The Array of the LDEV Drift Function of the Individual Marginal Processes
	 */

	public org.drip.measure.dynamics.LocalEvaluator[] driftLDEV()
	{
		return _aLDEVDrift;
	}

	/**
	 * Retrieve the Array of the LDEV Volatility Function of the Individual Marginal Processes
	 * 
	 * @return The Array of the LDEV Volatility Function of the Individual Marginal Processes
	 */

	public org.drip.measure.dynamics.LocalEvaluator[] volatilityLDEV()
	{
		return _aLDEVVolatility;
	}

	/**
	 * Retrieve the Correlation Matrix
	 * 
	 * @return The Correlation Matrix
	 */

	public double[][] correlation()
	{
		return _aadblCorrelation;
	}

	/**
	 * Generate the Adjacent Increment from the Array of the specified Random Variate
	 * 
	 * @param js The Joint Snap
	 * @param adblRandomVariate The Array of Random Variates
	 * @param adblRandomUnitRealization The Array of Random Stochastic Realization Variate Units
	 * @param dblTimeIncrement The Time Increment Evolution Unit
	 * 
	 * @return The Joint Level Realization
	 */

	public abstract org.drip.measure.joint.Edge increment (
		final org.drip.measure.joint.Vertex js,
		final double[] adblRandomVariate,
		final double[] adblRandomUnitRealization,
		final double dblTimeIncrement);

	/**
	 * Generate the Array of the Adjacent Increments from the Array of the specified Random Variate
	 * 
	 * @param aJS Array of Joint Snap Instances
	 * @param aadblRandomVariate Array of R^d Variates
	 * @param aadblRandomUnitRealization Array of R^d Stochastic Realization Units
	 * @param dblTimeIncrement The Time Increment Evolution Unit
	 * 
	 * @return Array of the Joint Level Realization
	 */

	public abstract org.drip.measure.joint.Edge[][] incrementSequence (
		final org.drip.measure.joint.Vertex[] aJS,
		final double[][] aadblRandomVariate,
		final double[][] aadblRandomUnitRealization,
		final double dblTimeIncrement);
}
