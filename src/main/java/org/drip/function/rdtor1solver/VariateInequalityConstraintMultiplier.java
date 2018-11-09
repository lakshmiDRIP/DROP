
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
 * <i>VariateInequalityConstraintMultiplier</i> holds the Variates and their Inequality Constraint
 * Multipliers in either the Absolute or the Incremental Forms.
 * <br><br>
 *  <ul>
 *		<li><b>Module</b>        = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function">Function</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/rdtor1solver">R<sup>d</sup> To R<sup>1</sup> Solver</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/NumericalOptimizer">Numerical Optimizer Library</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class VariateInequalityConstraintMultiplier {

	/**
	 * Flag Indicating whether the Variate Contents are to be Logged "Before" Bounding
	 */

	public static boolean s_bPreBoundBlog = false;

	/**
	 * Flag Indicating whether the Variate Contents are to be Logged "After" Bounding
	 */

	public static boolean s_bPostBoundBlog = false;

	private double[] _adblVariate = null;
	private boolean _bIncremental = false;
	private double[] _adblConstraintMultiplier = null;

	/**
	 * Add the Specified VICM Instances together
	 * 
	 * @param vicmBase VICM Instance Base
	 * @param vicmIncrement VICM Instance Increment
	 * @param dblIncrementFactor The Increment Factor - 1. corresponds to Full Increment
	 * @param aBM Array of Bounded Multivariate Stubs
	 * 
	 * @return The Added VICM Instance
	 */

	public static final VariateInequalityConstraintMultiplier Add (
		final VariateInequalityConstraintMultiplier vicmBase,
		final VariateInequalityConstraintMultiplier vicmIncrement,
		final double dblIncrementFactor,
		final org.drip.function.rdtor1.BoundMultivariate[] aBM)
	{
		if (null == vicmBase || null == vicmIncrement || vicmBase.incremental() ||
			!vicmIncrement.incremental() || !org.drip.quant.common.NumberUtil.IsValid (dblIncrementFactor) ||
				1. < dblIncrementFactor)
			return null;

		double[] adblVariateBase = vicmBase.variates();

		double[] adblVariateIncrement = vicmIncrement.variates();

		double[] adblInequalityConstraintMultiplierBase = vicmBase.constraintMultipliers();

		double[] adblInequalityConstraintMultiplierIncrement = vicmIncrement.constraintMultipliers();

		int iNumVariate = adblVariateBase.length;
		int iNumBounder = null == aBM ? 0 : aBM.length;
		double[] adblVariate = new double[iNumVariate];
		int iNumInequalityConstraint = null == adblInequalityConstraintMultiplierBase ? 0 :
			adblInequalityConstraintMultiplierBase.length;
		int iNumInequalityConstraintIncrement = null == adblInequalityConstraintMultiplierIncrement ? 0 :
			adblInequalityConstraintMultiplierIncrement.length;
		double[] adblInequalityConstraintMultiplier = 0 == iNumInequalityConstraint ? null : new
			double[iNumInequalityConstraint];

		if (iNumVariate != adblVariateIncrement.length || iNumInequalityConstraint !=
			iNumInequalityConstraintIncrement)
			return null;

		for (int i = 0; i < iNumVariate; ++i)
			adblVariate[i] = adblVariateBase[i] + dblIncrementFactor * adblVariateIncrement[i];

		if (s_bPreBoundBlog) {
			java.lang.String strDump = "\tB";

			for (int i = 0; i < iNumVariate; ++i)
				strDump += " " + org.drip.quant.common.FormatUtil.FormatDouble (adblVariate[i], 2, 2, 100.) +
					" |";

			System.out.println (strDump);
		}

		for (int i = 0; i < iNumInequalityConstraint; ++i) {
			if (0. > (adblInequalityConstraintMultiplier[i] = adblInequalityConstraintMultiplierBase[i] +
				dblIncrementFactor * adblInequalityConstraintMultiplierIncrement[i]))
				adblInequalityConstraintMultiplier[i] = 0.;

			if (iNumBounder <= i || null == aBM[i]) continue;

			int iBoundVariateIndex = aBM[i].boundVariateIndex();

			try {
				if (aBM[i].violated (adblVariate[iBoundVariateIndex]))
					adblVariate[iBoundVariateIndex] = aBM[i].boundValue();
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		if (s_bPostBoundBlog) {
			java.lang.String strDump = "\tA";

			for (int i = 0; i < iNumVariate; ++i)
				strDump += " " + org.drip.quant.common.FormatUtil.FormatDouble (adblVariate[i], 2, 2, 100.) +
					" |";

			System.out.println (strDump);
		}

		try {
			return new VariateInequalityConstraintMultiplier (false, adblVariate,
				adblInequalityConstraintMultiplier);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Add the Specified VICM Instances together
	 * 
	 * @param vicmBase VICM Instance Base
	 * @param vicmIncrement VICM Instance Increment
	 * @param aBM Array of Bounded Multivariate Stubs
	 * 
	 * @return The Added VICM Instance
	 */

	public static final VariateInequalityConstraintMultiplier Add (
		final VariateInequalityConstraintMultiplier vicmBase,
		final VariateInequalityConstraintMultiplier vicmIncrement,
		final org.drip.function.rdtor1.BoundMultivariate[] aBM)
	{
		return Add (vicmBase, vicmIncrement, 1., aBM);
	}

	/**
	 * Subtract the Second VICM Instance from the First
	 * 
	 * @param vicmBase VICM Instance Base
	 * @param vicmIncrement VICM Instance Increment
	 * @param dblIncrementFactor The Increment Factor - 1. corresponds to Full Increment
	 * @param aBM Array of Bounded Multivariate Stubs
	 * 
	 * @return The Subtracted VICM Instance
	 */

	public static final VariateInequalityConstraintMultiplier Subtract (
		final VariateInequalityConstraintMultiplier vicmBase,
		final VariateInequalityConstraintMultiplier vicmIncrement,
		final double dblIncrementFactor,
		final org.drip.function.rdtor1.BoundMultivariate[] aBM)
	{
		if (null == vicmBase || null == vicmIncrement || vicmBase.incremental() ||
			!vicmIncrement.incremental() || !org.drip.quant.common.NumberUtil.IsValid (dblIncrementFactor) ||
				1. < dblIncrementFactor)
			return null;

		double[] adblVariateBase = vicmBase.variates();

		double[] adblVariateIncrement = vicmIncrement.variates();

		double[] adblInequalityConstraintMultiplierBase = vicmBase.constraintMultipliers();

		double[] adblInequalityConstraintMultiplierIncrement = vicmIncrement.constraintMultipliers();

		int iNumVariate = adblVariateBase.length;
		int iNumBounder = null == aBM ? 0 : aBM.length;
		double[] adblVariate = new double[iNumVariate];
		int iNumInequalityConstraint = null == adblInequalityConstraintMultiplierBase ? 0 :
			adblInequalityConstraintMultiplierBase.length;
		int iNumInequalityConstraintIncrement = null == adblInequalityConstraintMultiplierIncrement ? 0 :
			adblInequalityConstraintMultiplierIncrement.length;
		double[] adblInequalityConstraintMultiplier = 0 == iNumInequalityConstraint ? null : new
			double[iNumInequalityConstraint];

		if (iNumVariate != adblVariateIncrement.length || iNumInequalityConstraint !=
			iNumInequalityConstraintIncrement)
			return null;

		for (int i = 0; i < iNumVariate; ++i)
			adblVariate[i] = adblVariateBase[i] - dblIncrementFactor * adblVariateIncrement[i];

		if (s_bPreBoundBlog) {
			java.lang.String strDump = "\tB";

			for (int i = 0; i < iNumVariate; ++i)
				strDump += " " + org.drip.quant.common.FormatUtil.FormatDouble (adblVariate[i], 2, 2, 100.) +
					" |";

			System.out.println (strDump);
		}

		for (int i = 0; i < iNumInequalityConstraint; ++i) {
			if (0. > (adblInequalityConstraintMultiplier[i] = adblInequalityConstraintMultiplierBase[i] -
				dblIncrementFactor * adblInequalityConstraintMultiplierIncrement[i]))
				adblInequalityConstraintMultiplier[i] = 0.;

			if (iNumBounder <= i || null == aBM[i]) continue;

			int iBoundVariateIndex = aBM[i].boundVariateIndex();

			try {
				if (aBM[i].violated (adblVariate[iBoundVariateIndex]))
					adblVariate[iBoundVariateIndex] = aBM[i].boundValue();
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		if (s_bPostBoundBlog) {
			java.lang.String strDump = "\tA";

			for (int i = 0; i < iNumVariate; ++i)
				strDump += " " + org.drip.quant.common.FormatUtil.FormatDouble (adblVariate[i], 2, 2, 100.) +
					" |";

			System.out.println (strDump);
		}

		try {
			return new VariateInequalityConstraintMultiplier (false, adblVariate,
				adblInequalityConstraintMultiplier);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Subtract the Second VICM Instance from the First
	 * 
	 * @param vicmBase VICM Instance Base
	 * @param vicmIncrement VICM Instance Increment
	 * @param aBM Array of Bounded Multivariate Stubs
	 * 
	 * @return The Subtracted VICM Instance
	 */

	public static final VariateInequalityConstraintMultiplier Subtract (
		final VariateInequalityConstraintMultiplier vicmBase,
		final VariateInequalityConstraintMultiplier vicmIncrement,
		final org.drip.function.rdtor1.BoundMultivariate[] aBM)
	{
		return Subtract (vicmBase, vicmIncrement, 1., aBM);
	}

	/**
	 * Compare the Specified VICM Instances
	 * 
	 * @param vicm1 VICM Instance #1
	 * @param vicm2 VICM Instance #2
	 * @param dblRelativeTolerance The Relative Tolerance Between the Variates
	 * @param dblAbsoluteToleranceFallback The Absolute Tolerance Fall-back Between the Variates
	 * @param iNumComparisonVariate The Number of Variates to Compare
	 * 
	 * @return TRUE - The VICM Instances are Close (Enough)
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public static final boolean Compare (
		final VariateInequalityConstraintMultiplier vicm1,
		final VariateInequalityConstraintMultiplier vicm2,
		final double dblRelativeTolerance,
		final double dblAbsoluteToleranceFallback,
		final int iNumComparisonVariate)
		throws java.lang.Exception
	{
		if (null == vicm1 || null == vicm2 || vicm1.incremental() || vicm2.incremental() ||
			!org.drip.quant.common.NumberUtil.IsValid (dblRelativeTolerance) ||
				!org.drip.quant.common.NumberUtil.IsValid (dblAbsoluteToleranceFallback) || 0 >
					dblAbsoluteToleranceFallback)
			throw new java.lang.Exception ("VariateInequalityConstraintMultiplier::Compare => Invalid Inputs");

		double[] adblVariate1 = vicm1.variates();

		double[] adblVariate2 = vicm2.variates();

		int iNumVariate = adblVariate1.length;

		if (iNumVariate != adblVariate2.length || iNumComparisonVariate > iNumVariate)
			throw new java.lang.Exception ("VariateInequalityConstraintMultiplier::Compare => Invalid Inputs");

		for (int i = 0; i < iNumComparisonVariate; ++i) {
			if (!org.drip.quant.common.NumberUtil.IsValid (adblVariate1[i]) ||
				!org.drip.quant.common.NumberUtil.IsValid (adblVariate2[i]))
				throw new java.lang.Exception
					("VariateInequalityConstraintMultiplier::Compare => Invalid Inputs");

			double dblAbsoluteTolerance = java.lang.Math.abs (adblVariate1[i] * dblRelativeTolerance);

			if (dblAbsoluteTolerance < dblAbsoluteToleranceFallback)
				dblAbsoluteTolerance = dblAbsoluteToleranceFallback;

			if (dblAbsoluteTolerance < java.lang.Math.abs (adblVariate1[i] - adblVariate2[i])) return false;
		}

		return true;
	}

	/**
	 * VariateInequalityConstraintMultiplier Constructor
	 * 
	 * @param bIncremental TRUE - Tuple represents an Incremental Unit
	 * @param adblVariate Array of Variates
	 * @param adblConstraintMultiplier Array of Constraint Multipliers
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public VariateInequalityConstraintMultiplier (
		final boolean bIncremental,
		final double[] adblVariate,
		final double[] adblConstraintMultiplier)
		throws java.lang.Exception
	{
		if (null == (_adblVariate = adblVariate) || 0 == _adblVariate.length)
			throw new java.lang.Exception
				("VariateInequalityConstraintMultiplier Constructor => Invalid Inputs");

		_bIncremental = bIncremental;
		_adblConstraintMultiplier = adblConstraintMultiplier;
	}

	/**
	 * Retrieve the Incremental Flag
	 * 
	 * @return TRUE - Tuple is Incremental
	 */

	public boolean incremental()
	{
		return _bIncremental;
	}

	/**
	 * Retrieve the Array of Variates
	 * 
	 * @return Array of Variates
	 */

	public double[] variates()
	{
		return _adblVariate;
	}

	/**
	 * Retrieve the Constraint Multipliers
	 * 
	 * @return Array of Constraint Multipliers
	 */

	public double[] constraintMultipliers()
	{
		return _adblConstraintMultiplier;
	}

	/**
	 * Retrieve the Consolidated Variate/Constraint Multiplier Array
	 * 
	 * @return The Consolidated Variate/Constraint Multiplier Array
	 */

	public double[] variateConstraintMultipler()
	{
		int iNumVariate = _adblVariate.length;
		int iNumVariateConstraintMultipler = iNumVariate + (null == _adblConstraintMultiplier ? 0 :
			_adblConstraintMultiplier.length);
		double[] adblVariateConstraintMultipler = new double[iNumVariateConstraintMultipler];

		for (int i = 0; i < iNumVariateConstraintMultipler; ++i)
			adblVariateConstraintMultipler[i] = i < iNumVariate ? _adblVariate[i] :
				_adblConstraintMultiplier[i - iNumVariate];

		return adblVariateConstraintMultipler;
	}

	/**
	 * Retrieve the Sized Vector Instance corresponding to the Increment
	 * 
	 * @return The Sized Vector Instance corresponding to the Increment
	 */

	public org.drip.function.definition.SizedVector incrementVector()
	{
		return _bIncremental ? org.drip.function.definition.SizedVector.Standard
			(variateConstraintMultipler()) : null;
	}

	/**
	 * Retrieve the Sized Vector Instance corresponding to the Variate Increment
	 * 
	 * @return The Sized Vector Instance corresponding to the Variate Increment
	 */

	public org.drip.function.definition.SizedVector variateIncrementVector()
	{
		return _bIncremental ? org.drip.function.definition.SizedVector.Standard (_adblVariate) : null;
	}
}
