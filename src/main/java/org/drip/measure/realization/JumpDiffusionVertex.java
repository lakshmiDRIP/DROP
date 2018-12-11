
package org.drip.measure.realization;

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
 * <i>JumpDiffusionVertex</i> holds the Snapshot Values of the Realized R<sup>d</sup> Variable - its Value,
 * whether it has terminated, and the Cumulative Hazard Integral - and Time.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalOptimizerLibrary.md">Numerical Optimizer Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure">Measure</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/realization">Realization</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class JumpDiffusionVertex {
	private boolean _bJumpOccurred = false;
	private double _dblTime = java.lang.Double.NaN;
	private double _dblValue = java.lang.Double.NaN;
	private double _dblCumulativeHazardIntegral = java.lang.Double.NaN;

	/**
	 * JumpDiffusionVertex Constructor
	 * 
	 * @param dblTime The Time Instant
	 * @param dblValue The Random Variable Value
	 * @param dblCumulativeHazardIntegral The Jump Occurrence Cumulative Hazard Integral
	 * @param bJumpOccurred TRUE - Jump Occurred
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public JumpDiffusionVertex (
		final double dblTime,
		final double dblValue,
		final double dblCumulativeHazardIntegral,
		final boolean bJumpOccurred)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (_dblTime = dblTime) ||
			!org.drip.quant.common.NumberUtil.IsValid (_dblValue = dblValue) ||
				!org.drip.quant.common.NumberUtil.IsValid (_dblCumulativeHazardIntegral =
					dblCumulativeHazardIntegral))
			throw new java.lang.Exception ("JumpDiffusionVertex Constructor => Invalid Inputs");

		_bJumpOccurred = bJumpOccurred;
	}

	/**
	 * Retrieve the Evolution Time Instant
	 * 
	 * @return The Evolution Time Instant
	 */

	public double time()
	{
		return _dblTime;
	}

	/**
	 * Retrieve the Realized Random Value
	 * 
	 * @return The Realized Random Value
	 */

	public double value()
	{
		return _dblValue;
	}

	/**
	 * Retrieve the Jump Occurred Flag
	 * 
	 * @return TRUE - Jump Occurred
	 */

	public boolean jumpOccurred()
	{
		return _bJumpOccurred;
	}

	/**
	 * Retrieve the Jump Occurrence Cumulative Hazard Integral
	 * 
	 * @return The Jump Occurrence Cumulative Hazard Integral
	 */

	public final double cumulativeHazardIntegral()
	{
		return _dblCumulativeHazardIntegral;
	}
}
