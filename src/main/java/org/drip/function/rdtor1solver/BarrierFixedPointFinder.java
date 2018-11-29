
package org.drip.function.rdtor1solver;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
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
 * <i>BarrierFixedPointFinder</i> invokes the Iterative Finders for locating the Fixed Point of
 * R<sup>d</sup> To R<sup>1</sup> Convex/Non-Convex Functions Under Inequality Constraints using Barrier
 * Sequences of decaying Strengths.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalOptimizerLibrary.md">Numerical Optimizer</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/feed">Function</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/feed/rdtor1solver">R<sup>d</sup> To R<sup>1</sup> Solver</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class BarrierFixedPointFinder {
	private org.drip.function.definition.RdToR1 _rdToR1ObjectiveFunction = null;
	private org.drip.function.rdtor1descent.LineStepEvolutionControl _lsec = null;
	private org.drip.function.rdtor1solver.InteriorPointBarrierControl _ipbc = null;
	private org.drip.function.definition.RdToR1[] _aRdToR1InequalityConstraint = null;

	/**
	 * BarrierFixedPointFinder Constructor
	 * 
	 * @param rdToR1ObjectiveFunction The Objective Function
	 * @param aRdToR1InequalityConstraint Array of Inequality Constraints
	 * @param ipbc Interior Point Barrier Strength Control Parameters
	 * @param lsec Line Step Evolution Verifier Control Parameters
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public BarrierFixedPointFinder (
		final org.drip.function.definition.RdToR1 rdToR1ObjectiveFunction,
		final org.drip.function.definition.RdToR1[] aRdToR1InequalityConstraint,
		final org.drip.function.rdtor1solver.InteriorPointBarrierControl ipbc,
		final org.drip.function.rdtor1descent.LineStepEvolutionControl lsec)
		throws java.lang.Exception
	{
		if (null == (_rdToR1ObjectiveFunction = rdToR1ObjectiveFunction) || null ==
			(_aRdToR1InequalityConstraint = aRdToR1InequalityConstraint) || null == (_ipbc = ipbc))
			throw new java.lang.Exception ("BarrierFixedPointFinder Constructor => Invalid Inputs");

		_lsec = lsec;
		int iNumInequalityConstraint = _aRdToR1InequalityConstraint.length;

		if (0 == iNumInequalityConstraint)
			throw new java.lang.Exception ("BarrierFixedPointFinder Constructor => Invalid Inputs");

		for (int i = 0; i < iNumInequalityConstraint; ++i) {
			if (null == _aRdToR1InequalityConstraint[i])
				throw new java.lang.Exception ("BarrierFixedPointFinder Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Objective Function
	 * 
	 * @return The Objective Function
	 */

	public org.drip.function.definition.RdToR1 objectiveFunction()
	{
		return _rdToR1ObjectiveFunction;
	}

	/**
	 * Retrieve the Array of Inequality Constraints
	 * 
	 * @return The Array of Inequality Constraints
	 */

	public org.drip.function.definition.RdToR1[] inequalityConstraints()
	{
		return _aRdToR1InequalityConstraint;
	}

	/**
	 * Retrieve the Interior Point Barrier Strength Control Parameters
	 * 
	 * @return The Interior Point Barrier Strength Control Parameters
	 */

	public org.drip.function.rdtor1solver.InteriorPointBarrierControl control()
	{
		return _ipbc;
	}

	/**
	 * Solve for the Optimal Variate-Inequality Constraint Multiplier Tuple using the Barrier Iteration
	 *  Parameters provided by the IPBC Instance
	 *  
	 * @param adblStartingVariate The Starting Variate Sequence
	 * 
	 * @return The Optimal Variate-Inequality Constraint Multiplier Tuple
	 */

	public org.drip.function.rdtor1solver.VariateInequalityConstraintMultiplier solve (
		final double[] adblStartingVariate)
	{
		int iOutstandingDecaySteps = _ipbc.numDecaySteps();

		double dblBarrierStrength = _ipbc.initialStrength();

		double dblBarrierDecayVelocity = _ipbc.decayVelocity();

		int iNumInequalityConstraint = _aRdToR1InequalityConstraint.length;
		org.drip.function.rdtor1solver.VariateInequalityConstraintMultiplier vicm = null;
		double[] adblStartingInequalityConstraintMultiplier = new double[iNumInequalityConstraint];

		try {
			for (int i = 0; i < iNumInequalityConstraint; ++i)
				adblStartingInequalityConstraintMultiplier[i] = dblBarrierStrength /
					_aRdToR1InequalityConstraint[i].evaluate (adblStartingVariate);

			vicm = new org.drip.function.rdtor1solver.VariateInequalityConstraintMultiplier (false,
				adblStartingVariate, adblStartingInequalityConstraintMultiplier);
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		while (--iOutstandingDecaySteps >= 0) {
			try {
				org.drip.function.rdtor1solver.InteriorFixedPointFinder bfpf = new
					org.drip.function.rdtor1solver.InteriorFixedPointFinder (_rdToR1ObjectiveFunction,
						_aRdToR1InequalityConstraint, _lsec, _ipbc, dblBarrierStrength);

				if (null == (vicm = bfpf.find (vicm))) return null;
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}

			dblBarrierStrength *= dblBarrierDecayVelocity;
		}

		return vicm;
	}
}
