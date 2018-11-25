
package org.drip.xva.settings;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
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
 * <i>StandardizedExposureGeneratorScheme</i> holds the Fields for the Generation of the Conservative
 * Exposure Measures generated using the Standardized Basel Scheme. The References are:
 * 
 *  <br><br>
 *  <ul>
 *  	<li>
 *  		Andersen, L. B. G., M. Pykhtin, and A. Sokol (2017): Re-thinking Margin Period of Risk
 *  			https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2902737 <b>eSSRN</b>
 *  	</li>
 *  	<li>
 *  		Andersen, L. B. G., M. Pykhtin, and A. Sokol (2017): Credit Exposure in the Presence of Initial
 *  			Margin https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2806156 <b>eSSRN</b>
 *  	</li>
 *  	<li>
 *  		Anfuso, F., D. Aziz, P. Giltinan, and K Loukopoulus (2017): A Sound Modeling and Back Testing
 *  			Framework for Forecasting Initial Margin Requirements
 *  			https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2716279 <b>eSSRN</b>
 *  	</li>
 *  	<li>
 *  		BCBS (2015): Margin Requirements for Non-centrally Cleared Derivatives
 *  			https://www.bis.org/bcbs/publ/d317.pdf
 *  	</li>
 *  	<li>
 *  		Pykhtin, M. (2009): Modeling Credit Exposure for Collateralized Counter-parties <i>Journal of
 *  			Credit Risk</i> <b>5 (4)</b> 3-27
 *  	</li>
 *  </ul>
 *
 *  <br><br>
 *  <ul>
 *		<li><b>Project</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva">XVA</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/settings">Settings</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/XVA">XVA Analytics Library</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class StandardizedExposureGeneratorScheme
{

	/**
	 * Basel Standard Time Integrand
	 */

	public static final int BASEL_STANDARD_TIME_INTEGRAND = 365;

	private int _timeIntegrand = -1;
	private double _eadMultiplier = java.lang.Double.NaN;
	private org.drip.spline.params.SegmentCustomBuilderControl _collateralizedExposureSegmentBuilderControl =
		null;
	private org.drip.spline.params.SegmentCustomBuilderControl
		_collateralizedPositiveExposureSegmentBuilderControl = null;

	/**
	 * Construct a Basel Instance of the StandardizedExposureGeneratorScheme
	 * 
	 * @param eadMultiplier The EAD Multiplier
	 * 
	 * @return The StandardizedExposureGeneratorScheme Instance
	 */

	public static final StandardizedExposureGeneratorScheme Basel (
		final double eadMultiplier)
	{
		try
		{
			org.drip.spline.params.SegmentCustomBuilderControl segmentCustomBuilderControl = new
				org.drip.spline.params.SegmentCustomBuilderControl (
					org.drip.spline.stretch.MultiSegmentSequenceBuilder.BASIS_SPLINE_POLYNOMIAL,
					new org.drip.spline.basis.PolynomialFunctionSetParams (2),
					org.drip.spline.params.SegmentInelasticDesignControl.Create (
						0,
						2
					),
					new org.drip.spline.params.ResponseScalingShapeControl (
						true,
						new org.drip.function.r1tor1.QuadraticRationalShapeControl (0.)
					),
					null
				);

			return new StandardizedExposureGeneratorScheme (
				eadMultiplier,
				BASEL_STANDARD_TIME_INTEGRAND,
				segmentCustomBuilderControl,
				segmentCustomBuilderControl
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * StandardizedExposureGeneratorScheme Constructor
	 * 
	 * @param eadMultiplier The EAD Multiplier
	 * @param timeIntegrand The Time Integrand
	 * @param collateralizedExposureSegmentBuilderControl The Collateralized Segment Builder Control
	 * @param collateralizedPositiveExposureSegmentBuilderControl The Collateralized Positive Segment Builder
	 *  Control
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public StandardizedExposureGeneratorScheme (
		final double eadMultiplier,
		final int timeIntegrand,
		final org.drip.spline.params.SegmentCustomBuilderControl collateralizedExposureSegmentBuilderControl,
		final org.drip.spline.params.SegmentCustomBuilderControl
			collateralizedPositiveExposureSegmentBuilderControl)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (_eadMultiplier = eadMultiplier) ||0. >= _eadMultiplier
			|| 0 >= (_timeIntegrand = timeIntegrand) ||
			null == (_collateralizedExposureSegmentBuilderControl =
				collateralizedExposureSegmentBuilderControl) ||
			null == (_collateralizedPositiveExposureSegmentBuilderControl =
				collateralizedPositiveExposureSegmentBuilderControl))
		{
			throw new java.lang.Exception
				("StandardizedExposureGeneratorScheme Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the EAD Multiplier
	 * 
	 * @return The EAD Multiplier
	 */

	public double eadMultiplier()
	{
		return _eadMultiplier;
	}

	/**
	 * Retrieve the Time Integrand
	 * 
	 * @return The Time Integrand
	 */

	public int timeIntegrand()
	{
		return _timeIntegrand;
	}

	/**
	 * Retrieve the Collateralized Exposure Segment Builder Control
	 * 
	 * @return The Collateralized Exposure Segment Builder Control
	 */

	public org.drip.spline.params.SegmentCustomBuilderControl collateralizedExposureSegmentBuilderControl()
	{
		return _collateralizedExposureSegmentBuilderControl;
	}

	/**
	 * Retrieve the Collateralized Positive Exposure Segment Builder Control
	 * 
	 * @return The Collateralized Positive Exposure Segment Builder Control
	 */

	public org.drip.spline.params.SegmentCustomBuilderControl
		collateralizedPositiveExposureSegmentBuilderControl()
	{
		return _collateralizedPositiveExposureSegmentBuilderControl;
	}
}
