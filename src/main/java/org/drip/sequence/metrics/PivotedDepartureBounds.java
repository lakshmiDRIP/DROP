
package org.drip.sequence.metrics;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
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
 * <i>PivotedDepartureBounds</i> holds the Lower/Upper Probability Bounds in regards to the Specified
 * Pivot-Centered Sequence.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/StatisticalLearningLibrary.md">Statistical Learning Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sequence">Sequence</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sequence/metrics">Metrics</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class PivotedDepartureBounds {

	/**
	 * PIVOT ANCHOR TYPE - ZERO
	 */

	public static final int PIVOT_ANCHOR_TYPE_ZERO = 1;

	/**
	 * PIVOT ANCHOR TYPE - MEAN
	 */

	public static final int PIVOT_ANCHOR_TYPE_MEAN = 2;

	/**
	 * PIVOT ANCHOR TYPE - CUSTOM
	 */

	public static final int PIVOT_ANCHOR_TYPE_CUSTOM = 4;

	private int _iPivotAnchorType = -1;
	private double _dblLower = java.lang.Double.NaN;
	private double _dblUpper = java.lang.Double.NaN;
	private double _dblCustomPivotAnchor = java.lang.Double.NaN;

	/**
	 * PivotedDepartureBounds Constructor
	 * 
	 * @param iPivotAnchorType The Type of the Pivot Anchor
	 * @param dblCustomPivotAnchor The Custom Pivot Anchor
	 * @param dblLower Lower Bound
	 * @param dblUpper Upper Bound
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public PivotedDepartureBounds (
		final int iPivotAnchorType,
		final double dblCustomPivotAnchor,
		final double dblLower,
		final double dblUpper)
		throws java.lang.Exception
	{
		_dblLower = dblLower;
		_dblUpper = dblUpper;
		_iPivotAnchorType = iPivotAnchorType;
		_dblCustomPivotAnchor = dblCustomPivotAnchor;

		if ((!org.drip.quant.common.NumberUtil.IsValid (_dblLower) &&
			!org.drip.quant.common.NumberUtil.IsValid (_dblUpper)) || (PIVOT_ANCHOR_TYPE_CUSTOM ==
				_iPivotAnchorType && !org.drip.quant.common.NumberUtil.IsValid (_dblCustomPivotAnchor)))
			throw new java.lang.Exception ("PivotedDepartureBounds ctr: Invalid Inputs");
	}

	/**
	 * Retrieve the Lower Probability Bound
	 * 
	 * @return The Lower Probability Bound
	 */

	public double lower()
	{
		return _dblLower;
	}

	/**
	 * Retrieve the Upper Probability Bound
	 * 
	 * @return The Upper Probability Bound
	 */

	public double upper()
	{
		return _dblUpper;
	}

	/**
	 * Retrieve the Pivot Anchor Type
	 * 
	 * @return The Pivot Anchor Type
	 */

	public int pivotAnchorType()
	{
		return _iPivotAnchorType;
	}

	/**
	 * Retrieve the Custom Pivot Anchor
	 * 
	 * @return The Custom Pivot Anchor
	 */

	public double customPivotAnchor()
	{
		return _dblCustomPivotAnchor;
	}
}
