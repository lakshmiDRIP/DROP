
package org.drip.spline.params;

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
 * <i>SegmentCustomBuilderControl</i> holds the parameters the guide the creation/behavior of the segment. It
 * holds the segment elastic/inelastic parameters and the named basis function set.
 *
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/SplineBuilderLibrary.md">Spline Builder Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline">Spline</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/params">Params</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class SegmentCustomBuilderControl {
	private java.lang.String _strBasisSpline = "";
	private org.drip.spline.basis.FunctionSetBuilderParams _fsbp = null;
	private org.drip.spline.params.ResponseScalingShapeControl _rssc = null;
	private org.drip.spline.params.SegmentInelasticDesignControl _sdic = null;
	private org.drip.spline.params.PreceedingManifestSensitivityControl _pmsc = null;

	/**
	 * SegmentCustomBuilderControl constructor
	 * 
	 * @param strBasisSpline Named Segment Basis Spline
	 * @param fsbp Segment Basis Set Construction Parameters
	 * @param sdic Segment Design Inelastic Parameters
	 * @param rssc Segment Shape Controller
	 * @param pmsc Preceeding Manifest Sensitivity Control Parameters
	 * 
	 * @throws java.lang.Exception Thrown if inputs are invalid
	 */

	public SegmentCustomBuilderControl (
		final java.lang.String strBasisSpline,
		final org.drip.spline.basis.FunctionSetBuilderParams fsbp,
		final org.drip.spline.params.SegmentInelasticDesignControl sdic,
		final org.drip.spline.params.ResponseScalingShapeControl rssc,
		final org.drip.spline.params.PreceedingManifestSensitivityControl pmsc)
		throws java.lang.Exception
	{
		if (null == (_strBasisSpline = strBasisSpline) || null == (_fsbp = fsbp) || null == (_sdic = sdic))
			throw new java.lang.Exception ("SegmentCustomBuilderControl ctr => Invalid Inputs");

		_pmsc = pmsc;
		_rssc = rssc;
	}

	/**
	 * Retrieve the Basis Spline Name
	 * 
	 * @return The Basis Spline Name
	 */

	public java.lang.String basisSpline()
	{
		return _strBasisSpline;
	}

	/**
	 * Retrieve the Basis Set Parameters
	 * 
	 * @return The Basis Set Parameters
	 */

	public org.drip.spline.basis.FunctionSetBuilderParams basisSetParams()
	{
		return _fsbp;
	}

	/**
	 * Retrieve the Segment Inelastic Parameters
	 * 
	 * @return The Segment Inelastic Parameters
	 */

	public org.drip.spline.params.SegmentInelasticDesignControl inelasticParams()
	{
		return _sdic;
	}

	/**
	 * Retrieve the Segment Shape Controller
	 * 
	 * @return The Segment Shape Controller
	 */

	public org.drip.spline.params.ResponseScalingShapeControl shapeController()
	{
		return _rssc;
	}

	/**
	 * Retrieve the Preceeding Manifest Sensitivity Control Parameters
	 * 
	 * @return The Preceeding Manifest Sensitivity Control Parameters
	 */

	public org.drip.spline.params.PreceedingManifestSensitivityControl preceedingManifestSensitivityControl()
	{
		return _pmsc;
	}
}
