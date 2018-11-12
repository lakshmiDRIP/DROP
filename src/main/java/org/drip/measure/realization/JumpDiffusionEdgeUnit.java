
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
 * <i>JumpDiffusionEdgeUnit</i> holds the Jump Diffusion R<sup>d</sup> Unit Edge Realizations.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module</b>        = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure">Measure</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/realization">Realization</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/FixedIncome">Fixed Income Analytics</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class JumpDiffusionEdgeUnit {
	private double _dblJump = java.lang.Double.NaN;
	private double _dblDiffusion = java.lang.Double.NaN;
	private double _dblTimeIncrement = java.lang.Double.NaN;

	/**
	 * Generate a R^1 Uniform Diffusion Realization
	 * 
	 * @param dblTimeIncrement The Time Increment
	 * 
	 * @return The R^1 Uniform Diffusion Realization
	 */

	public static final JumpDiffusionEdgeUnit UniformDiffusion (
		final double dblTimeIncrement)
	{
		try {
			return new JumpDiffusionEdgeUnit (dblTimeIncrement, java.lang.Math.random(), 0.);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate a R^1 Gaussian Diffusion Realization
	 * 
	 * @param dblTimeIncrement The Time Increment
	 * 
	 * @return The R^1 Gaussian Diffusion Realization
	 */

	public static final JumpDiffusionEdgeUnit GaussianDiffusion (
		final double dblTimeIncrement)
	{
		try {
			return new JumpDiffusionEdgeUnit (dblTimeIncrement,
				org.drip.measure.gaussian.NormalQuadrature.Random(), 0.);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate a R^1 Uniform Jump Realization
	 * 
	 * @param dblTimeIncrement The Time Increment
	 * 
	 * @return The R^1 Uniform Jump Realization
	 */

	public static final JumpDiffusionEdgeUnit UniformJump (
		final double dblTimeIncrement)
	{
		try {
			return new JumpDiffusionEdgeUnit (dblTimeIncrement, 0., java.lang.Math.random());
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate a R^1 Gaussian Jump Realization
	 * 
	 * @param dblTimeIncrement The Time Increment
	 * 
	 * @return The R^1 Gaussian Jump Realization
	 */

	public static final JumpDiffusionEdgeUnit GaussianJump (
		final double dblTimeIncrement)
	{
		try {
			return new JumpDiffusionEdgeUnit (dblTimeIncrement, 0.,
				org.drip.measure.gaussian.NormalQuadrature.Random());
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate an Array of R^1 Diffusion Realizations
	 * 
	 * @param adblTimeIncrement The Array of Time Increments
	 * @param adblDiffusionRealization The Array of Diffusion Realizations
	 * 
	 * @return Array of R^1 Diffusion Realizations
	 */

	public static final JumpDiffusionEdgeUnit[] Diffusion (
		final double[] adblTimeIncrement,
		final double[] adblDiffusionRealization)
	{
		if (null == adblDiffusionRealization) return null;

		int iSize = adblDiffusionRealization.length;
		JumpDiffusionEdgeUnit[] aJDU = 0 == iSize ? null : new JumpDiffusionEdgeUnit[iSize];

		if (0 == iSize || iSize != adblTimeIncrement.length) return null;

		for (int i = 0; i < iSize; ++i) {
			try {
				aJDU[i] = new JumpDiffusionEdgeUnit (adblTimeIncrement[i], adblDiffusionRealization[i], 0.);
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		return aJDU;
	}

	/**
	 * Generate an Array of R^1 Jump Realizations
	 * 
	 * @param adblTimeIncrement The Array of Time Increments
	 * @param adblJumpRealization The Array of Jump Realizations
	 * 
	 * @return Array of R^1 Jump Realizations
	 */

	public static final JumpDiffusionEdgeUnit[] Jump (
		final double[] adblTimeIncrement,
		final double[] adblJumpRealization)
	{
		if (null == adblJumpRealization) return null;

		int iSize = adblJumpRealization.length;
		JumpDiffusionEdgeUnit[] aJDU = 0 == iSize ? null : new JumpDiffusionEdgeUnit[iSize];

		if (0 == iSize || iSize != adblTimeIncrement.length) return null;

		for (int i = 0; i < iSize; ++i) {
			try {
				aJDU[i] = new JumpDiffusionEdgeUnit (adblTimeIncrement[i], 0., adblJumpRealization[i]);
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		return aJDU;
	}

	/**
	 * Generate an Array of R^1 Jump Diffusion Realizations
	 * 
	 * @param adblTimeIncrement The Array of Time Increments
	 * @param adblDiffusionRealization The Array of Diffusion Realizations
	 * @param adblJumpRealization The Array of Jump Realizations
	 * 
	 * @return Array of R^1 Jump Diffusion Realizations
	 */

	public static final JumpDiffusionEdgeUnit[] JumpDiffusion (
		final double[] adblTimeIncrement,
		final double[] adblDiffusionRealization,
		final double[] adblJumpRealization)
	{
		if (null == adblDiffusionRealization || null == adblJumpRealization) return null;

		int iSize = adblDiffusionRealization.length;
		JumpDiffusionEdgeUnit[] aJDEU = 0 == iSize ? null : new JumpDiffusionEdgeUnit[iSize];

		if (0 == iSize || iSize != adblJumpRealization.length || iSize != adblTimeIncrement.length)
			return null;

		for (int i = 0; i < iSize; ++i) {
			try {
				aJDEU[i] = new JumpDiffusionEdgeUnit (adblTimeIncrement[i], adblDiffusionRealization[i],
					adblJumpRealization[i]);
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		return aJDEU;
	}

	/**
	 * JumpDiffusionEdgeUnit Constructor
	 * 
	 * @param dblTimeIncrement The Edge Time Increment
	 * @param dblDiffusion The Diffusion Random Variable
	 * @param dblJump The Jump Random Variable
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public JumpDiffusionEdgeUnit (
		final double dblTimeIncrement,
		final double dblDiffusion,
		final double dblJump)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (_dblTimeIncrement = dblTimeIncrement) || 0. ==
			_dblTimeIncrement || !org.drip.quant.common.NumberUtil.IsValid (_dblDiffusion = dblDiffusion) ||
				!org.drip.quant.common.NumberUtil.IsValid (_dblJump = dblJump))
			throw new java.lang.Exception ("JumpDiffusionEdgeUnit Constructor => Invalid Inputs");
	}

	/**
	 * Retrieve the Edge Time Increment
	 * 
	 * @return The Edge Time Increment
	 */

	public double timeIncrement()
	{
		return _dblTimeIncrement;
	}

	/**
	 * Retrieve the Diffusion Unit Random Variable
	 * 
	 * @return The Diffusion Unit Random Variable
	 */

	public double diffusion()
	{
		return _dblDiffusion;
	}

	/**
	 * Retrieve the Jump Unit Random Variable
	 * 
	 * @return The Jump Unit Random Variable
	 */

	public double jump()
	{
		return _dblJump;
	}
}
