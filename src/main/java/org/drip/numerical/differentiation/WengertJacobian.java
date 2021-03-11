
package org.drip.numerical.differentiation;

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
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
 * Copyright (C) 2012 Lakshmi Krishnamurthy
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
 * <i>WengertJacobian</i> contains the Jacobian of the given set of Wengert variables to the set of
 * parameters. It exposes the following functionality:
 * 
 * <br><br>
 *  <ul>
 *  	<li>
 * 			Set/Retrieve the Wengert variables
 *  	</li>
 *  	<li>
 * 			Accumulate the Partials
 *  	</li>
 *  	<li>
 * 			Scale the partial entries
 *  	</li>
 *  	<li>
 * 			Merge the Jacobian with another
 *  	</li>
 *  	<li>
 * 			Retrieve the WengertJacobian elements
 *  	</li>
 *  	<li>
 * 			Display the contents of the WengertJacobian
 *  	</li>
 *  </ul>
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical">Numerical Quadrature, Differentiation, Eigenization, Linear Algebra, and Utilities</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/differentiation">R<sup>1</sup> To R<sup>1</sup> Numerical Differentiation</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class WengertJacobian {
	private double[] _adblWengert = null;
	private double[][] _aadblDWengertDParameter = null;

	/**
	 * WengertJacobian constructor
	 * 
	 * @param iNumWengerts Number of Wengert variables
	 * @param iNumParameters Number of Parameters
	 * 
	 * @throws java.lang.Exception Thrown if the inputs are invalid
	 */

	public WengertJacobian (
		final int iNumWengerts,
		final int iNumParameters)
		throws java.lang.Exception
	{
		if (0 >= iNumWengerts || 0 >= iNumParameters)
			throw new java.lang.Exception ("WengertJacobian constructor: Invalid inputs");

		_adblWengert = new double[iNumWengerts];
		_aadblDWengertDParameter = new double[iNumWengerts][iNumParameters];

		for (int iWengertIndex = 0; iWengertIndex < _aadblDWengertDParameter.length; ++iWengertIndex) {
			for (int iParameterIndex = 0; iParameterIndex < _aadblDWengertDParameter[0].length;
				++iParameterIndex)
				_aadblDWengertDParameter[iWengertIndex][iParameterIndex] = 0.;
		}
	}

	/**
	 * Retrieve the number of Wengert Variables
	 * 
	 * @return Number of Wengert Variables
	 */

	public int numWengerts()
	{
		return null == _adblWengert ? 0 : _adblWengert.length;
	}

	/**
	 * Retrieve the number of Parameters
	 * 
	 * @return Number of Parameters
	 */

	public int numParameters()
	{
		return (null == _aadblDWengertDParameter || null == _aadblDWengertDParameter[0]) ? 0 :
			_aadblDWengertDParameter[0].length;
	}

	/**
	 * Set the Value for the Wengert variable
	 * 
	 * @param iWengertIndex Wengert Variable Index 
	 * @param dblWengert The Value for the Wengert Variable
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setWengert (
		final int iWengertIndex,
		final double dblWengert)
	{
		if (0 > iWengertIndex || iWengertIndex >= _adblWengert.length ||
			!org.drip.numerical.common.NumberUtil.IsValid (dblWengert))
			return false;

		_adblWengert[iWengertIndex] = dblWengert;
		return true;
	}

	/**
	 * Get the Value for the Wengert Variable
	 * 
	 * @param iIndex Wengert Variable Index 
	 * 
	 * @return The Value for the Wengert variable
	 * 
	 * @throws java.lang.Exception Thrown if the inputs are invalid
	 */

	public double wengert (
		final int iIndex)
		throws java.lang.Exception
	{
		if (0 > iIndex || iIndex >= _adblWengert.length)
			throw new java.lang.Exception ("WengertJacobian::wengert => Invalid Wengert Variable Index!");

		return _adblWengert[iIndex];
	}

	/**
	 * Accumulate {D(Wengert)}/{D(Parameter)}
	 * 
	 * @param iWengertIndex Wengert Variable Index
	 * @param iParameterIndex Parameter Index
	 * @param dblDWengertDParameter The incremental {D(Wengert)}/{D(Parameter)}
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean accumulatePartialFirstDerivative (
		final int iWengertIndex,
		final int iParameterIndex,
		final double dblDWengertDParameter)
	{
		if (0 > iParameterIndex || iParameterIndex >= _aadblDWengertDParameter[0].length || 0 > iWengertIndex
			|| iWengertIndex >= _adblWengert.length || !org.drip.numerical.common.NumberUtil.IsValid
				(dblDWengertDParameter))
			return false;

		_aadblDWengertDParameter[iWengertIndex][iParameterIndex] += dblDWengertDParameter;
		return true;
	}

	/**
	 * Retrieve {D(Wengert)}/{D(Parameter)} for the Wengert and the parameter identified by their indices
	 * 
	 * @param iWengertIndex Wengert Variable Index
	 * @param iParameterIndex Parameter Index
	 * 
	 * @return {D(Wengert)}/{D(Parameter)}
	 */

	public double firstDerivative (
		final int iWengertIndex,
		final int iParameterIndex)
	{
		return _aadblDWengertDParameter[iWengertIndex][iParameterIndex];
	}

	/**
	 * Accumulate and merge partial entries from the other CurveWengertJacobian
	 * 
	 * @param wjOther CurveWengertJacobian to be accumulated and merged
	 * 
	 * @return TRUE - Successfully accumulated and merged
	 */

	public boolean cumulativeMerge (
		final org.drip.numerical.differentiation.WengertJacobian wjOther)
	{
		if (null == wjOther) return false;

		for (int iWengertIndex = 0; iWengertIndex < _aadblDWengertDParameter.length; ++iWengertIndex) {
			for (int iParameterIndex = 0; iParameterIndex < _aadblDWengertDParameter[0].length;
				++iParameterIndex)
				_aadblDWengertDParameter[iWengertIndex][iParameterIndex] += wjOther.firstDerivative
					(iWengertIndex, iParameterIndex);
		}

		return true;
	}

	/**
	 * Accumulate and merge the weighted partial entries from the other CurveWengertJacobian
	 * 
	 * @param wjOther CurveWengertJacobian to be accumulated and merged
	 * @param dblWeight The Weight
	 * 
	 * @return TRUE - Successfully accumulated and merged
	 */

	public boolean cumulativeMerge (
		final org.drip.numerical.differentiation.WengertJacobian wjOther,
		final double dblWeight)
	{
		if (null == wjOther || !org.drip.numerical.common.NumberUtil.IsValid (dblWeight)) return false;

		for (int iWengertIndex = 0; iWengertIndex < _aadblDWengertDParameter.length; ++iWengertIndex) {
			for (int iParameterIndex = 0; iParameterIndex < _aadblDWengertDParameter[0].length;
				++iParameterIndex)
				_aadblDWengertDParameter[iWengertIndex][iParameterIndex] += wjOther.firstDerivative
					(iWengertIndex, iParameterIndex) * dblWeight;
		}

		return true;
	}

	/**
	 * Scale the partial entries
	 * 
	 * @param dblScale Factor by which the partials are to be scaled by
	 * 
	 * @return TRUE - Scaling down successful
	 */

	public boolean scale (
		final double dblScale)
	{
		if (0 >= dblScale) return false;

		for (int iWengertIndex = 0; iWengertIndex < _aadblDWengertDParameter.length; ++iWengertIndex) {
			for (int iParameterIndex = 0; iParameterIndex < _aadblDWengertDParameter[0].length;
				++iParameterIndex)
				_aadblDWengertDParameter[iWengertIndex][iParameterIndex] *= dblScale;
		}

		return true;
	}

	/**
	 * Stringifies the contents of WengertJacobian
	 * 
	 * @return Stringified WengertJacobian
	 */

	public java.lang.String displayString()
	{
		java.lang.StringBuffer sb = new java.lang.StringBuffer();

		for (int iWengertIndex = 0; iWengertIndex < _aadblDWengertDParameter.length; ++iWengertIndex) {
			java.lang.StringBuffer sbDWengertDParameter = new java.lang.StringBuffer();

			sbDWengertDParameter.append ("Wengert{" + iWengertIndex + "} => [");

			for (int iParameterIndex = 0; iParameterIndex < _aadblDWengertDParameter[0].length;
				++iParameterIndex) {
				if (0 != iParameterIndex) sbDWengertDParameter.append (", ");

				sbDWengertDParameter.append (org.drip.service.common.FormatUtil.FormatDouble
					(_aadblDWengertDParameter[iWengertIndex][iParameterIndex], 1, 3, 1.));
			}

			sb.append (sbDWengertDParameter).append ("]\n");
		}

		return sb.toString();
	}
}
