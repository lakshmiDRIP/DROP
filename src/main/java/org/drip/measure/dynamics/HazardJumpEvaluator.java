
package org.drip.measure.dynamics;


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
 * <i>HazardJumpEvaluator</i> implements the Hazard Jump Process Point Event Indication Evaluator that guides
 * the Single Factor Jump-Termination Random Process Variable Evolution.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module</b>        = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure">Measure</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/dynamics">Dynamics</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/FixedIncome">Fixed Income Analytics</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class HazardJumpEvaluator extends org.drip.measure.dynamics.SingleJumpEvaluator {
	private double _dblMagnitude = java.lang.Double.NaN;
	private double _dblHazardRate = java.lang.Double.NaN;

	/**
	 * Generate a Standard Instance of HazardJumpEvaluator
	 * 
	 * @param dblHazardRate The Hazard Rate
	 * @param dblMagnitude The Magnitude
	 * 
	 * @return The Standard Instance of HazardJumpEvaluator
	 */

	public static final HazardJumpEvaluator Standard (
		final double dblHazardRate,
		final double dblMagnitude)
	{
		org.drip.measure.dynamics.LocalEvaluator leDensity = new org.drip.measure.dynamics.LocalEvaluator() {
			@Override public double value (
				final org.drip.measure.realization.JumpDiffusionVertex jdv)
				throws java.lang.Exception
			{
				return -1. * dblHazardRate * java.lang.Math.exp (-1. * dblHazardRate);
			}
		};

		org.drip.measure.dynamics.LocalEvaluator leMagnitude = new org.drip.measure.dynamics.LocalEvaluator()
		{
			@Override public double value (
				final org.drip.measure.realization.JumpDiffusionVertex jdv)
				throws java.lang.Exception
			{
				return dblMagnitude;
			}
		};

		try {
			return new HazardJumpEvaluator (dblHazardRate, dblMagnitude, leDensity, leMagnitude);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	private HazardJumpEvaluator (
		final double dblHazardRate,
		final double dblMagnitude,
		final org.drip.measure.dynamics.LocalEvaluator leDensity,
		final org.drip.measure.dynamics.LocalEvaluator leMagnitude)
		throws java.lang.Exception
	{
		super (leDensity, leMagnitude);

		if (!org.drip.quant.common.NumberUtil.IsValid (_dblHazardRate = dblHazardRate) || 0. > _dblHazardRate
			|| !org.drip.quant.common.NumberUtil.IsValid (_dblMagnitude = dblMagnitude) || 0. >
				_dblMagnitude)
			throw new java.lang.Exception ("HazardJumpEvaluator Constructor => Invalid Inputs");
	}

	/**
	 * Retrieve the Hazard Rate
	 * 
	 * @return The Hazard Rate
	 */

	public double hazardRate()
	{
		return _dblHazardRate;
	}

	/**
	 * Retrieve the Magnitude
	 * 
	 * @return The Magnitude
	 */

	public double magnitude()
	{
		return _dblMagnitude;
	}
}
