
package org.drip.state.representation;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
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
 * <i>LatentStateSpecification</i> holds the fields necessary to specify a complete Latent State. It includes
 * the Latent State Type, the Latent State Label, and the Latent State Quantification metric.
 *
 *  <br><br>
 *  <ul>
 *		<li><b>Project</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state">State</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/representation">Representation</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/FixedIncome">Fixed Income Analytics Library</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class LatentStateSpecification {
	private java.lang.String _strLatentState = "";
	private org.drip.state.identifier.LatentStateLabel _label = null;
	private java.lang.String _strLatentStateQuantificationMetric = "";

	/**
	 * LatentStateSpecification constructor
	 * 
	 * @param strLatentState The Latent State
	 * @param strLatentStateQuantificationMetric The Latent State Quantification Metric
	 * @param label The Specific Latent State Label
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are invalid
	 */

	public LatentStateSpecification (
		final java.lang.String strLatentState,
		final java.lang.String strLatentStateQuantificationMetric,
		final org.drip.state.identifier.LatentStateLabel label)
		throws java.lang.Exception
	{
		if (null == (_strLatentState = strLatentState) || _strLatentState.isEmpty() || null ==
			(_strLatentStateQuantificationMetric = strLatentStateQuantificationMetric) ||
				_strLatentStateQuantificationMetric.isEmpty() || null == (_label = label))
			throw new java.lang.Exception ("LatentStateSpecification ctr: Invalid Inputs");
	}

	/**
	 * Retrieve the Latent State
	 * 
	 * @return The Latent State
	 */

	public java.lang.String latentState()
	{
		return _strLatentState;
	}

	/**
	 * Retrieve the Latent State Label
	 * 
	 * @return The Latent State Label
	 */

	public org.drip.state.identifier.LatentStateLabel label()
	{
		return _label;
	}

	/**
	 * Retrieve the Latent State Quantification Metric
	 * 
	 * @return The Latent State Quantification Metric
	 */

	public java.lang.String latentStateQuantificationMetric()
	{
		return _strLatentStateQuantificationMetric;
	}

	/**
	 * Does the Specified Latent State Specification Instance match the current one?
	 * 
	 * @param lssOther The "Other" Latent State Specification Instance
	 * 
	 * @return TRUE - Matches the Specified Latent State Specification Instance
	 */

	public boolean match (
		final LatentStateSpecification lssOther)
	{
		return null == lssOther ? false : _strLatentState.equalsIgnoreCase (lssOther.latentState()) &&
			_strLatentStateQuantificationMetric.equalsIgnoreCase (lssOther.latentStateQuantificationMetric())
				&& _label.match (lssOther.label());
	}

	/**
	 * Display the Latent State Details
	 * 
	 * @param strComment The Comment Prefix
	 */

	public void displayString (
		final java.lang.String strComment)
	{
		System.out.println ("\t[LatentStateSpecification]: " + _strLatentState + " | " +
			_strLatentStateQuantificationMetric + " | " + _label.fullyQualifiedName());
	}
}
