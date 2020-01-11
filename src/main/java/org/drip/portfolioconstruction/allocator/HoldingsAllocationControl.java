
package org.drip.portfolioconstruction.allocator;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
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
 *  	and computational support.
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
 * <i>HoldingsAllocationControl</i> holds the Parameters needed to control the Portfolio Allocation.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AssetAllocationAnalyticsLibrary.md">Asset Allocation Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/README.md">Portfolio Construction under Allocation Constraints</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/allocator/README.md">MVO Based Portfolio Allocation Construction</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class HoldingsAllocationControl
{
	private java.lang.String[] _assetIDArray = null;
	private org.drip.portfolioconstruction.allocator.CustomRiskUtilitySettings _customRiskUtilitySettings =
		null;
	private org.drip.portfolioconstruction.allocator.EqualityConstraintSettings
		_equalityConstraintSettings = null;

	/**
	 * HoldingsAllocationControl Constructor
	 * 
	 * @param assetIDArray Array of Asset IDs
	 * @param customRiskUtilitySettings The Custom Risk Utility Settings
	 * @param equalityConstraintSettings The Portfolio Equality Constraint Settings
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public HoldingsAllocationControl (
		final java.lang.String[] assetIDArray,
		final org.drip.portfolioconstruction.allocator.CustomRiskUtilitySettings customRiskUtilitySettings,
		final org.drip.portfolioconstruction.allocator.EqualityConstraintSettings equalityConstraintSettings)
		throws java.lang.Exception
	{
		if (null == (_assetIDArray = assetIDArray) || 0 == _assetIDArray.length ||
			null == (_customRiskUtilitySettings = customRiskUtilitySettings) ||
			null == (_equalityConstraintSettings = equalityConstraintSettings))
		{
			throw new java.lang.Exception ("HoldingsAllocationControl Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Asset ID Array
	 * 
	 * @return The Asset ID Array
	 */

	public java.lang.String[] assetIDArray()
	{
		return _assetIDArray;
	}

	/**
	 * Retrieve the Instance of the Custom Risk Utility Settings
	 * 
	 * @return The Custom Risk Utility Settings
	 */

	public org.drip.portfolioconstruction.allocator.CustomRiskUtilitySettings customRiskUtilitySettings()
	{
		return _customRiskUtilitySettings;
	}

	/**
	 * Retrieve the Instance of the Portfolio Equality Constraint Settings
	 * 
	 * @return The Portfolio Equality Constraint Settings
	 */

	public org.drip.portfolioconstruction.allocator.EqualityConstraintSettings equalityConstraintSettings()
	{
		return _equalityConstraintSettings;
	}

	/**
	 * Retrieve the Fully Invested Equality Constraint

	 * @return The Fully Invested Equality Constraint
	 */

	public org.drip.function.definition.RdToR1 fullyInvestedConstraint()
	{
		try
		{
			return new org.drip.function.rdtor1.AffineMultivariate (
				org.drip.function.rdtor1.ObjectiveConstraintVariateSet.Unitary (_assetIDArray.length),
				-1.
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Retrieve the Mandatory Returns Constraint
	 * 
	 * @param assetUniverseStatisticalProperties The Asset Universe Statistical Properties Instance
	 * 
	 * @return The Mandatory Returns Constraint
	 */

	public org.drip.function.definition.RdToR1 returnsConstraint (
		final org.drip.portfolioconstruction.params.AssetUniverseStatisticalProperties
			assetUniverseStatisticalProperties)
	{
		if (null == assetUniverseStatisticalProperties)
		{
			return null;
		}

		int assetCount = _assetIDArray.length;
		double[] assetReturnsArray = new double[assetCount];

		for (int assetIndex = 0; assetIndex < assetCount; ++assetIndex)
		{
			org.drip.portfolioconstruction.params.AssetStatisticalProperties assetStatisticalProperties =
				assetUniverseStatisticalProperties.assetStatisticalProperties (_assetIDArray[assetIndex]);

			if (null == assetStatisticalProperties)
			{
				return null;
			}

			assetReturnsArray[assetIndex] = assetStatisticalProperties.expectedReturn();
		}

		try
		{
			return new org.drip.function.rdtor1.AffineMultivariate (
				assetReturnsArray,
				-1. * _equalityConstraintSettings.returnsConstraint()
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Retrieve the Equality Constraint R<sup>d</sup> To R<sup>1</sup> Corresponding to the Specified
	 * 	Constraint Type
	 * 
	 * @param assetUniverseStatisticalProperties The Asset Universe Statistical Properties Instance
	 * 
	 * @return The Equality Constraint R<sup>d</sup> To R<sup>1</sup> Corresponding to the Specified
	 * 	Constraint Type
	 */

	public org.drip.function.definition.RdToR1[] equalityConstraintArray (
		final org.drip.portfolioconstruction.params.AssetUniverseStatisticalProperties
			assetUniverseStatisticalProperties)
	{
		int constraintType = _equalityConstraintSettings.constraintType();

		if (org.drip.portfolioconstruction.allocator.EqualityConstraintSettings.NO_CONSTRAINT ==
			constraintType)
		{
			return null;
		}

		java.util.List<org.drip.function.definition.RdToR1> rdToR1ConstraintList = new
			java.util.ArrayList<org.drip.function.definition.RdToR1>();

		if (0 !=
			(org.drip.portfolioconstruction.allocator.EqualityConstraintSettings.FULLY_INVESTED_CONSTRAINT
			& constraintType))
		{
			rdToR1ConstraintList.add (fullyInvestedConstraint());
		}

		if (0 !=
			(org.drip.portfolioconstruction.allocator.EqualityConstraintSettings.RETURNS_CONSTRAINT
			& constraintType))
		{
			org.drip.function.definition.RdToR1 rdToR1ReturnsConstraint = returnsConstraint (
				assetUniverseStatisticalProperties
			);

			if (null == rdToR1ReturnsConstraint)
			{
				return null;
			}

			rdToR1ConstraintList.add (rdToR1ReturnsConstraint);
		}

		int constraintCount = rdToR1ConstraintList.size();

		org.drip.function.definition.RdToR1[] equalityConstraintArray =
			new org.drip.function.definition.RdToR1[constraintCount];

		for (int constraintIndex = 0; constraintIndex < constraintCount; ++constraintIndex)
		{
			equalityConstraintArray[constraintIndex] = rdToR1ConstraintList.get (constraintIndex);
		}

		return equalityConstraintArray;
	}

	/**
	 * Retrieve the Equality Constraint RHS Corresponding to the Specified Constraint Type
	 * 
	 * @return The Equality Constraint RHS Corresponding to the Specified Constraint Type
	 */

	public double[] equalityConstraintRHS()
	{
		double returnsConstraint = _equalityConstraintSettings.returnsConstraint();

		return org.drip.numerical.common.NumberUtil.IsValid (returnsConstraint) ? new double[] {
			1.,
			returnsConstraint
		} : new double[] {
			1.
		};
	}
}
