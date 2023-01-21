
package org.drip.function.rdtor1solver;

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
 * <i>VariateInequalityConstraintMultiplier</i> holds the Variates and their Inequality Constraint
 * Multipliers in either the Absolute or the Incremental Forms.
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

public class VariateInequalityConstraintMultiplier
{

	/**
	 * Flag Indicating whether the Variate Contents are to be Logged "Before" Bounding
	 */

	public static boolean s_preBoundBlog = false;

	/**
	 * Flag Indicating whether the Variate Contents are to be Logged "After" Bounding
	 */

	public static boolean s_postBoundBlog = false;

	private boolean _incremental = false;
	private double[] _variateArray = null;
	private double[] _constraintMultiplierArray = null;

	/**
	 * Add the Specified VariateInequalityConstraintMultiplier Instances together
	 * 
	 * @param baseVariateConstriantMultiplier VariateInequalityConstraintMultiplier Instance Base
	 * @param incrementalVariateConstriantMultiplier VariateInequalityConstraintMultiplier Instance Increment
	 * @param incrementFactor The Increment Factor - 1. corresponds to Full Increment
	 * @param boundMultivariateFunctionArray Array of Bounded Multivariate Stubs
	 * 
	 * @return The Added VariateInequalityConstraintMultiplier Instance
	 */

	public static final VariateInequalityConstraintMultiplier Add (
		final VariateInequalityConstraintMultiplier baseVariateConstriantMultiplier,
		final VariateInequalityConstraintMultiplier incrementalVariateConstriantMultiplier,
		final double incrementFactor,
		final org.drip.function.rdtor1.BoundMultivariate[] boundMultivariateFunctionArray)
	{
		if (null == baseVariateConstriantMultiplier ||
			null == incrementalVariateConstriantMultiplier ||
			baseVariateConstriantMultiplier.incremental() ||
			!incrementalVariateConstriantMultiplier.incremental() ||
			!org.drip.numerical.common.NumberUtil.IsValid (incrementFactor) || 1. < incrementFactor)
		{
			return null;
		}

		double[] baseVariateArray = baseVariateConstriantMultiplier.variateArray();

		double[] incrementalVariateArray = incrementalVariateConstriantMultiplier.variateArray();

		double[] baseConstraintMultiplierArray = baseVariateConstriantMultiplier.constraintMultiplierArray();

		double[] incrementalConstraintMultiplierArray =
			incrementalVariateConstriantMultiplier.constraintMultiplierArray();

		int variateCount = baseVariateArray.length;
		double[] variateArray = new double[variateCount];
		int boundMultivariateFunctionCount = null == boundMultivariateFunctionArray ?
			0 : boundMultivariateFunctionArray.length;
		int constraintCount = null == baseConstraintMultiplierArray ? 0 :
			baseConstraintMultiplierArray.length;
		int constraintCountIncrementCount = null == incrementalConstraintMultiplierArray ? 0 :
			incrementalConstraintMultiplierArray.length;
		double[] constraintMultiplierArray = 0 == constraintCount ? null : new double[constraintCount];

		if (variateCount != incrementalVariateArray.length ||
			constraintCount != constraintCountIncrementCount)
		{
			return null;
		}

		for (int variateIndex = 0;
			variateIndex < variateCount;
			++variateIndex)
		{
			variateArray[variateIndex] = baseVariateArray[variateIndex] +
				incrementFactor * incrementalVariateArray[variateIndex];
		}

		if (s_preBoundBlog)
		{
			java.lang.String dump = "\tB";

			for (int variateIndex = 0;
				variateIndex < variateCount;
				++variateIndex)
			{
				dump += " " + org.drip.service.common.FormatUtil.FormatDouble (
					variateArray[variateIndex],
					2,
					2,
					100.
				) + " |";
			}

			System.out.println (dump);
		}

		for (int constraintIndex = 0;
			constraintIndex < constraintCount;
			++constraintIndex)
		{
			if (0. > (
				constraintMultiplierArray[constraintIndex] = baseConstraintMultiplierArray[constraintIndex] +
					incrementFactor * incrementalConstraintMultiplierArray[constraintIndex]
			))
			{
				constraintMultiplierArray[constraintIndex] = 0.;
			}

			if (boundMultivariateFunctionCount <= constraintIndex ||
				null == boundMultivariateFunctionArray[constraintIndex])
			{
				continue;
			}

			int boundVariateIndex = boundMultivariateFunctionArray[constraintIndex].boundVariateIndex();

			try
			{
				if (boundMultivariateFunctionArray[constraintIndex].violated (
					variateArray[boundVariateIndex]
				))
				{
					variateArray[boundVariateIndex] =
						boundMultivariateFunctionArray[constraintIndex].boundValue();
				}
			}
			catch (java.lang.Exception e)
			{
				e.printStackTrace();

				return null;
			}
		}

		if (s_postBoundBlog)
		{
			java.lang.String dump = "\tA";

			for (int variateIndex = 0;
				variateIndex < variateCount;
				++variateIndex)
			{
				dump += " " + org.drip.service.common.FormatUtil.FormatDouble (
					variateArray[variateIndex],
					2,
					2,
					100.
				) + " |";
			}

			System.out.println (dump);
		}

		try
		{
			return new VariateInequalityConstraintMultiplier (
				false,
				variateArray,
				constraintMultiplierArray
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Add the Specified VariateInequalityConstraintMultiplier Instances together
	 * 
	 * @param baseVariateConstriantMultiplier VariateInequalityConstraintMultiplier Instance Base
	 * @param incrementalVariateConstriantMultiplier VariateInequalityConstraintMultiplier Instance Increment
	 * @param boundMultivariateFunctionArray Array of Bounded Multivariate Stubs
	 * 
	 * @return The Added VariateInequalityConstraintMultiplier Instance
	 */

	public static final VariateInequalityConstraintMultiplier Add (
		final VariateInequalityConstraintMultiplier baseVariateConstriantMultiplier,
		final VariateInequalityConstraintMultiplier incrementalVariateConstriantMultiplier,
		final org.drip.function.rdtor1.BoundMultivariate[] boundMultivariateFunctionArray)
	{
		return Add (
			baseVariateConstriantMultiplier,
			incrementalVariateConstriantMultiplier,
			1.,
			boundMultivariateFunctionArray
		);
	}

	/**
	 * Subtract the Second VariateInequalityConstraintMultiplier Instance from the First
	 * 
	 * @param baseVariateConstriantMultiplier VariateInequalityConstraintMultiplier Instance Base
	 * @param incrementalVariateConstriantMultiplier VariateInequalityConstraintMultiplier Instance Increment
	 * @param incrementFactor The Increment Factor - 1. corresponds to Full Increment
	 * @param boundMultivariateFunctionArray Array of Bounded Multivariate Stubs
	 * 
	 * @return The Subtracted VariateInequalityConstraintMultiplier Instance
	 */

	public static final VariateInequalityConstraintMultiplier Subtract (
		final VariateInequalityConstraintMultiplier baseVariateConstriantMultiplier,
		final VariateInequalityConstraintMultiplier incrementalVariateConstriantMultiplier,
		final double incrementFactor,
		final org.drip.function.rdtor1.BoundMultivariate[] boundMultivariateFunctionArray)
	{
		if (null == baseVariateConstriantMultiplier ||
			null == incrementalVariateConstriantMultiplier ||
			baseVariateConstriantMultiplier.incremental() ||
			!incrementalVariateConstriantMultiplier.incremental() ||
			!org.drip.numerical.common.NumberUtil.IsValid (incrementFactor) || 1. < incrementFactor)
		{
			return null;
		}

		double[] baseVariateArray = baseVariateConstriantMultiplier.variateArray();

		double[] incrementalVariateIncrement = incrementalVariateConstriantMultiplier.variateArray();

		double[] baseConstraintMultiplierArray = baseVariateConstriantMultiplier.constraintMultiplierArray();

		double[] incrementalConstraintMultiplierArray =
			incrementalVariateConstriantMultiplier.constraintMultiplierArray();

		int variateCount = baseVariateArray.length;
		double[] variateArray = new double[variateCount];
		int constraintCount = null == baseConstraintMultiplierArray ? 0 :
			baseConstraintMultiplierArray.length;
		int boundMultivariateFunctionCount = null == boundMultivariateFunctionArray ?
			0 : boundMultivariateFunctionArray.length;
		int constraintIncrementCount = null == incrementalConstraintMultiplierArray ? 0 :
			incrementalConstraintMultiplierArray.length;
		double[] constraintMultiplierArray = 0 == constraintCount ? null : new
			double[constraintCount];

		if (variateCount != incrementalVariateIncrement.length ||
			constraintCount != constraintIncrementCount)
		{
			return null;
		}

		for (int variateIndex = 0;
			variateIndex < variateCount;
			++variateIndex)
		{
			variateArray[variateIndex] = baseVariateArray[variateIndex] -
				incrementFactor * incrementalVariateIncrement[variateIndex];
		}

		if (s_preBoundBlog)
		{
			java.lang.String dump = "\tB";

			for (int variateIndex = 0;
				variateIndex < variateCount;
				++variateIndex)
			{
				dump += " " + org.drip.service.common.FormatUtil.FormatDouble (
					variateArray[variateIndex],
					2,
					2,
					100.
				) + " |";
			}

			System.out.println (dump);
		}

		for (int constraintIndex = 0;
			constraintIndex < constraintCount;
			++constraintIndex)
		{
			if (0. > (
				constraintMultiplierArray[constraintIndex] = baseConstraintMultiplierArray[constraintIndex] -
					incrementFactor * incrementalConstraintMultiplierArray[constraintIndex]
			))
			{
				constraintMultiplierArray[constraintIndex] = 0.;
			}

			if (boundMultivariateFunctionCount <= constraintIndex ||
				null == boundMultivariateFunctionArray[constraintIndex])
			{
				continue;
			}

			int boundVariateIndex = boundMultivariateFunctionArray[constraintIndex].boundVariateIndex();

			try
			{
				if (boundMultivariateFunctionArray[constraintIndex].violated (
					variateArray[boundVariateIndex]
				))
				{
					variateArray[boundVariateIndex] =
						boundMultivariateFunctionArray[constraintIndex].boundValue();
				}
			}
			catch (java.lang.Exception e)
			{
				e.printStackTrace();

				return null;
			}
		}

		if (s_postBoundBlog)
		{
			java.lang.String dump = "\tA";

			for (int variateIndex = 0;
				variateIndex < variateCount;
				++variateIndex)
			{
				dump += " " + org.drip.service.common.FormatUtil.FormatDouble (
					variateArray[variateIndex],
					2,
					2,
					100.
				) + " |";
			}

			System.out.println (dump);
		}

		try
		{
			return new VariateInequalityConstraintMultiplier (
				false,
				variateArray,
				constraintMultiplierArray
			);
		} 
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Subtract the Second VariateInequalityConstraintMultiplier Instance from the First
	 * 
	 * @param baseVariateConstriantMultiplier VariateInequalityConstraintMultiplier Instance Base
	 * @param incrementalVariateConstriantMultiplier VariateInequalityConstraintMultiplier Instance Increment
	 * @param boundMultivariateFunctionArray Array of Bounded Multivariate Stubs
	 * 
	 * @return The Subtracted VariateInequalityConstraintMultiplier Instance
	 */

	public static final VariateInequalityConstraintMultiplier Subtract (
		final VariateInequalityConstraintMultiplier baseVariateConstriantMultiplier,
		final VariateInequalityConstraintMultiplier incrementalVariateConstriantMultiplier,
		final org.drip.function.rdtor1.BoundMultivariate[] boundMultivariateFunctionArray)
	{
		return Subtract (
			baseVariateConstriantMultiplier,
			incrementalVariateConstriantMultiplier,
			1.,
			boundMultivariateFunctionArray
		);
	}

	/**
	 * Compare the Specified VariateInequalityConstraintMultiplier Instances
	 * 
	 * @param variateConstraint1 VariateInequalityConstraintMultiplier Instance #1
	 * @param variateConstraint2 VariateInequalityConstraintMultiplier Instance #2
	 * @param relativeTolerance The Relative Tolerance Between the Variates
	 * @param absoluteToleranceFallback The Absolute Tolerance Fall-back Between the Variates
	 * @param comparisonVariate The Number of Variates to Compare
	 * 
	 * @return TRUE - The VariateInequalityConstraintMultiplier Instances are Close (Enough)
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public static final boolean Compare (
		final VariateInequalityConstraintMultiplier variateConstraint1,
		final VariateInequalityConstraintMultiplier variateConstraint2,
		final double relativeTolerance,
		final double absoluteToleranceFallback,
		final int comparisonVariate)
		throws java.lang.Exception
	{
		if (null == variateConstraint1 || variateConstraint1.incremental() ||
			null == variateConstraint2 || variateConstraint2.incremental() ||
			!org.drip.numerical.common.NumberUtil.IsValid (relativeTolerance) ||
			!org.drip.numerical.common.NumberUtil.IsValid (absoluteToleranceFallback) ||
				0. > absoluteToleranceFallback)
		{
			throw new java.lang.Exception ("VariateInequalityConstraintMultiplier::Compare => Invalid Inputs");
		}

		double[] variateArray1 = variateConstraint1.variateArray();

		double[] variateArray2 = variateConstraint2.variateArray();

		int variateCount = variateArray1.length;

		if (variateCount != variateArray2.length || comparisonVariate > variateCount)
		{
			throw new java.lang.Exception ("VariateInequalityConstraintMultiplier::Compare => Invalid Inputs");
		}

		for (int comparisonIndex = 0;
			comparisonIndex < comparisonVariate;
			++comparisonIndex)
		{
			if (!org.drip.numerical.common.NumberUtil.IsValid (
				variateArray1[comparisonIndex]
			) || !org.drip.numerical.common.NumberUtil.IsValid (
				variateArray2[comparisonIndex]
			))
			{
				throw new java.lang.Exception
					("VariateInequalityConstraintMultiplier::Compare => Invalid Inputs");
			}

			double absoluteTolerance = java.lang.Math.abs (
				variateArray1[comparisonIndex] * relativeTolerance
			);

			if (absoluteTolerance < absoluteToleranceFallback)
			{
				absoluteTolerance = absoluteToleranceFallback;
			}

			if (absoluteTolerance < java.lang.Math.abs (
				variateArray1[comparisonIndex] - variateArray2[comparisonIndex]
			))
			{
				return false;
			}
		}

		return true;
	}

	/**
	 * VariateInequalityConstraintMultiplier Constructor
	 * 
	 * @param incremental TRUE - Tuple represents an Incremental Unit
	 * @param variateArray Array of Variates
	 * @param constraintMultiplierArray Array of Constraint Multipliers
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public VariateInequalityConstraintMultiplier (
		final boolean incremental,
		final double[] variateArray,
		final double[] constraintMultiplierArray)
		throws java.lang.Exception
	{
		if (null == (_variateArray = variateArray) || 0 == _variateArray.length)
		{
			throw new java.lang.Exception
				("VariateInequalityConstraintMultiplier Constructor => Invalid Inputs");
		}

		_incremental = incremental;
		_constraintMultiplierArray = constraintMultiplierArray;
	}

	/**
	 * Retrieve the Incremental Flag
	 * 
	 * @return TRUE - Tuple is Incremental
	 */

	public boolean incremental()
	{
		return _incremental;
	}

	/**
	 * Retrieve the Array of Variates
	 * 
	 * @return Array of Variates
	 */

	public double[] variateArray()
	{
		return _variateArray;
	}

	/**
	 * Retrieve the Constraint Multipliers
	 * 
	 * @return Array of Constraint Multipliers
	 */

	public double[] constraintMultiplierArray()
	{
		return _constraintMultiplierArray;
	}

	/**
	 * Retrieve the Consolidated Variate/Constraint Multiplier Array
	 * 
	 * @return The Consolidated Variate/Constraint Multiplier Array
	 */

	public double[] variateConstraintMultipler()
	{
		int variateCount = _variateArray.length;
		int variateConstraintCount = variateCount + (null == _constraintMultiplierArray ? 0 :
			_constraintMultiplierArray.length);
		double[] variateConstraintArray = new double[variateConstraintCount];

		for (int variateConstraintIndex = 0;
			variateConstraintIndex < variateConstraintCount;
			++variateConstraintIndex)
		{
			variateConstraintArray[variateConstraintIndex] = variateConstraintIndex < variateCount ?
				_variateArray[variateConstraintIndex] :
				_constraintMultiplierArray[variateConstraintIndex - variateCount];
		}

		return variateConstraintArray;
	}

	/**
	 * Retrieve the Sized Vector Instance corresponding to the Increment
	 * 
	 * @return The Sized Vector Instance corresponding to the Increment
	 */

	public org.drip.function.definition.SizedVector incrementVector()
	{
		return _incremental ? org.drip.function.definition.SizedVector.Standard (
			variateConstraintMultipler()
		) : null;
	}

	/**
	 * Retrieve the Sized Vector Instance corresponding to the Variate Increment
	 * 
	 * @return The Sized Vector Instance corresponding to the Variate Increment
	 */

	public org.drip.function.definition.SizedVector variateIncrementVector()
	{
		return _incremental ? org.drip.function.definition.SizedVector.Standard (
			_variateArray
		) : null;
	}
}
