
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
 * <i>InteriorPointBarrierControl</i> contains the Barrier Iteration Control Parameters.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalOptimizerLibrary.md">Numerical Optimizer</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/feed/README.md">Function</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/feed/rdtor1solver/README.md">R<sup>d</sup> To R<sup>1</sup> Solver</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class InteriorPointBarrierControl
	extends org.drip.function.rdtor1solver.ConvergenceControl
{
	private int _decayStepCount = -1;
	private double _decayVelocity = java.lang.Double.NaN;
	private double _initialStrength = java.lang.Double.NaN;

	/**
	 * Construct a Standard InteriorPointBarrierControl Instance
	 * 
	 * @return The Standard InteriorPointBarrierControl Instance
	 */

	public static InteriorPointBarrierControl Standard()
	{
		try
		{
			return new InteriorPointBarrierControl (
				VARIATE_CONSTRAINT_SEQUENCE_CONVERGENCE,
				5.0e-02,
				1.0e-06,
				1.0e+04,
				0.5,
				70
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * InteriorPointBarrierControl Constructor
	 * 
	 * @param convergenceType The Convergence Type
	 * @param relativeTolerance The Objective Function Relative Tolerance
	 * @param absoluteTolerance The Objective Function Absolute Tolerance
	 * @param initialStrength The Initial Barrier Strength Level
	 * @param decayVelocity The Barrier Decay Velocity
	 * @param decayStepCount The Number Barrier Decay Steps
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public InteriorPointBarrierControl (
		final int convergenceType,
		final double relativeTolerance,
		final double absoluteTolerance,
		final double initialStrength,
		final double decayVelocity,
		final int decayStepCount)
		throws java.lang.Exception
	{
		super (
			convergenceType,
			relativeTolerance,
			absoluteTolerance,
			100
		);

		if (!org.drip.numerical.common.NumberUtil.IsValid (_initialStrength = initialStrength) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_decayVelocity = decayVelocity) ||
				1 > (_decayStepCount = decayStepCount))
		{
			throw new java.lang.Exception ("InteriorPointBarrierControl Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Number of Decay Steps
	 * 
	 * @return The Number of Decay Steps
	 */

	public int decayStepCount()
	{
		return _decayStepCount;
	}

	/**
	 * Retrieve the Initial Barrier Strength
	 * 
	 * @return The Initial Barrier Strength
	 */

	public double initialStrength()
	{
		return _initialStrength;
	}

	/**
	 * Retrieve the Decay Velocity
	 * 
	 * @return The Decay Velocity
	 */

	public double decayVelocity()
	{
		return _decayVelocity;
	}
}
