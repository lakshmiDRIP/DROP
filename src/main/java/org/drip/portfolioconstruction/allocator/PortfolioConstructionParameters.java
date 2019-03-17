
package org.drip.portfolioconstruction.allocator;

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
 * <i>PortfolioConstructionParameters</i> holds the Parameters needed to construct the Portfolio.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AssetAllocationAnalyticsLibrary.md">Asset Allocation Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction">Portfolio Construction</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/allocator">Allocator</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class PortfolioConstructionParameters {
	private java.lang.String[] _astrAssetID = null;
	private org.drip.portfolioconstruction.allocator.CustomRiskUtilitySettings _crus = null;
	private org.drip.portfolioconstruction.allocator.PortfolioEqualityConstraintSettings _pecs = null;

	/**
	 * PortfolioConstructionParameters Constructor
	 * 
	 * @param astrAssetID Array of Asset IDs
	 * @param crus The Quadratic Custom Risk Utility Settings
	 * @param pecs The Portfolio Equality Constraint Settings
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public PortfolioConstructionParameters (
		final java.lang.String[] astrAssetID,
		final org.drip.portfolioconstruction.allocator.CustomRiskUtilitySettings crus,
		final org.drip.portfolioconstruction.allocator.PortfolioEqualityConstraintSettings pecs)
		throws java.lang.Exception
	{
		if (null == (_astrAssetID = astrAssetID) || 0 == _astrAssetID.length || null == (_crus = crus) ||
			null == (_pecs = pecs))
			throw new java.lang.Exception ("PortfolioConstructionParameters Constructor => Invalid Inputs");
	}

	/**
	 * Retrieve the Asset ID Array
	 * 
	 * @return The Asset ID Array
	 */

	public java.lang.String[] assets()
	{
		return _astrAssetID;
	}

	/**
	 * Retrieve the Instance of the Quadratic Custom Risk Utility Settings
	 * 
	 * @return The Quadratic Custom Risk Utility Settings
	 */

	public org.drip.portfolioconstruction.allocator.CustomRiskUtilitySettings optimizerSettings()
	{
		return _crus;
	}

	/**
	 * Retrieve the Instance of the Portfolio Equality Constraint Settings
	 * 
	 * @return The Portfolio Equality Constraint Settings
	 */

	public org.drip.portfolioconstruction.allocator.PortfolioEqualityConstraintSettings constraintSettings()
	{
		return _pecs;
	}

	/**
	 * Retrieve the Fully Invested Equality Constraint

	 * @return The Fully Invested Equality Constraint
	 */

	public org.drip.function.definition.RdToR1 fullyInvestedConstraint()
	{
		try {
			return new org.drip.function.rdtor1.AffineMultivariate
				(org.drip.function.rdtor1.ObjectiveConstraintVariateSet.Unitary (assets().length), -1.);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Retrieve the Mandatory Returns Constraint
	 * 
	 * @param ausp The Asset Universe Statistical Properties Instance
	 * 
	 * @return The Mandatory Returns Constraint
	 */

	public org.drip.function.definition.RdToR1 returnsConstraint (
		final org.drip.portfolioconstruction.params.AssetUniverseStatisticalProperties ausp)
	{
		if (null == ausp) return null;

		java.lang.String[] astrAssetID = assets();

		int iNumAsset = astrAssetID.length;
		double[] adblAssetReturn = new double[iNumAsset];

		for (int i = 0; i < iNumAsset; ++i) {
			org.drip.portfolioconstruction.params.AssetStatisticalProperties aspAsset = ausp.asp
				(astrAssetID[i]);

			if (null == aspAsset) return null;

			adblAssetReturn[i] = aspAsset.expectedReturn();
		}

		try {
			return new org.drip.function.rdtor1.AffineMultivariate (adblAssetReturn, -1. *
				_pecs.returnsConstraint());
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Retrieve the Equality Constraint R^d To R^1 Corresponding to the Specified Constraint Type
	 * 
	 * @param ausp The Asset Universe Statistical Properties Instance
	 * 
	 * @return The Equality Constraint R^d To R^1  Corresponding to the Specified Constraint Type
	 */

	public org.drip.function.definition.RdToR1[] equalityConstraintRdToR1 (
		final org.drip.portfolioconstruction.params.AssetUniverseStatisticalProperties ausp)
	{
		if (org.drip.portfolioconstruction.allocator.PortfolioEqualityConstraintSettings.NO_CONSTRAINT ==
			_pecs.constraintType())
			return null;

		java.util.List<org.drip.function.definition.RdToR1> lsRdToR1 = new
			java.util.ArrayList<org.drip.function.definition.RdToR1>();

		if (0 !=
			(org.drip.portfolioconstruction.allocator.PortfolioEqualityConstraintSettings.FULLY_INVESTED_CONSTRAINT
			& _pecs.constraintType()))
			lsRdToR1.add (fullyInvestedConstraint());

		if (0 !=
			(org.drip.portfolioconstruction.allocator.PortfolioEqualityConstraintSettings.RETURNS_CONSTRAINT
			& _pecs.constraintType())) {
			org.drip.function.definition.RdToR1 rdToR1ReturnsConstraint = returnsConstraint (ausp);

			if (null == rdToR1ReturnsConstraint) return null;

			lsRdToR1.add (rdToR1ReturnsConstraint);
		}

		int iNumConstraint = lsRdToR1.size();

		org.drip.function.definition.RdToR1[] aConstraintRdToR1 = new
			org.drip.function.definition.RdToR1[iNumConstraint];

		for (int i = 0; i < iNumConstraint; ++i)
			aConstraintRdToR1[i] = lsRdToR1.get (i);

		return aConstraintRdToR1;
	}

	/**
	 * Retrieve the Equality Constraint Values Corresponding to the Specified Constraint Type
	 * 
	 * @param ausp The Asset Universe Statistical Properties Instance
	 * 
	 * @return The Equality Constraint Values Corresponding to the Specified Constraint Type
	 */

	public double[] equalityConstraintValue (
		final org.drip.portfolioconstruction.params.AssetUniverseStatisticalProperties ausp)
	{
		double dblReturnsConstraint = _pecs.returnsConstraint();

		return org.drip.numerical.common.NumberUtil.IsValid (dblReturnsConstraint) ? new double[] {1.,
			dblReturnsConstraint} : new double[] {1.};
	}
}
