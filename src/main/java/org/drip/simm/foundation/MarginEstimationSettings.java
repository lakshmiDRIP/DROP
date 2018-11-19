
package org.drip.simm.foundation;

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
 * <i>MarginEstimationSettings</i> exposes the Customization Settings used in the Margin Estimation. The
 * References are:
 * 
 * <br><br>
 *  <ul>
 *  	<li>
 *  		Andersen, L. B. G., M. Pykhtin, and A. Sokol (2017): Credit Exposure in the Presence of Initial
 *  			Margin https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2806156 <b>eSSRN</b>
 *  	</li>
 *  	<li>
 *  		Albanese, C., S. Caenazzo, and O. Frankel (2017): Regression Sensitivities for Initial Margin
 *  			Calculations https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2763488 <b>eSSRN</b>
 *  	</li>
 *  	<li>
 *  		Anfuso, F., D. Aziz, P. Giltinan, and K. Loukopoulus (2017): A Sound Modeling and Back-testing
 *  			Framework for Forecasting Initial Margin Requirements
 *  				https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2716279 <b>eSSRN</b>
 *  	</li>
 *  	<li>
 *  		Caspers, P., P. Giltinan, R. Lichters, and N. Nowaczyk (2017): Forecasting Initial Margin
 *  			Requirements - A Model Evaluation https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2911167
 *  				<b>eSSRN</b>
 *  	</li>
 *  	<li>
 *  		International Swaps and Derivatives Association (2017): SIMM v2.0 Methodology
 *  			https://www.isda.org/a/oFiDE/isda-simm-v2.pdf
 *  	</li>
 *  </ul>
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Project</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm">SIMM</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/foundation">Foundation</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/NumericalOptimizer">Numerical Optimizer Library</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class MarginEstimationSettings
{

	/**
	 * FRTB Based Position - Principal Component Estimator
	 */

	public static final java.lang.String POSITION_PRINCIPAL_COMPONENT_COVARIANCE_ESTIMATOR_FRTB = "FRTB";

	/**
	 * ISDA Based Position - Principal Component Estimator
	 */

	public static final java.lang.String POSITION_PRINCIPAL_COMPONENT_COVARIANCE_ESTIMATOR_ISDA = "ISDA";

	private java.lang.String _positionPrincipalComponentScheme = "";
	private org.drip.simm.foundation.CurvatureEstimator _curvatureEstimator = null;

	/**
	 * Generate a Cornish-Fischer Instance of MarginEstimationSettings
	 * 
	 * @param positionPrincipalComponentScheme The Position Principal Component Scheme
	 * 
	 * @return Cornish-Fischer Instance of MarginEstimationSettings
	 */

	public static final MarginEstimationSettings CornishFischer (
		final java.lang.String positionPrincipalComponentScheme)
	{
		try
		{
			return new MarginEstimationSettings (
				positionPrincipalComponentScheme,
				org.drip.simm.foundation.CurvatureEstimatorResponseFunction.CornishFischer()
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate a ISDA Delta Instance of MarginEstimationSettings
	 * 
	 * @param positionPrincipalComponentScheme The Position Principal Component Scheme
	 * 
	 * @return ISDA Delta Instance of MarginEstimationSettings
	 */

	public static final MarginEstimationSettings ISDADelta (
		final java.lang.String positionPrincipalComponentScheme)
	{
		try
		{
			return new MarginEstimationSettings (
				positionPrincipalComponentScheme,
				org.drip.simm.foundation.CurvatureEstimatorISDADelta.Standard()
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate an FRTB Instance of MarginEstimationSettings
	 * 
	 * @param positionPrincipalComponentScheme The Position Principal Component Scheme
	 * 
	 * @return FRTB Instance of MarginEstimationSettings
	 */

	public static final MarginEstimationSettings FRTB (
		final java.lang.String positionPrincipalComponentScheme)
	{
		try
		{
			return new MarginEstimationSettings (
				positionPrincipalComponentScheme,
				org.drip.simm.foundation.CurvatureEstimatorFRTB.Standard()
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * MarginEstimationSettings Constructor
	 * 
	 * @param positionPrincipalComponentScheme The Position Principal Component Scheme
	 * @param curvatureEstimator The Curvature Estimator Function
	 * 
	 * @throws java.lang.Exception Throwm if the Inputs are Invalid
	 */

	public MarginEstimationSettings (
		final java.lang.String positionPrincipalComponentScheme,
		final org.drip.simm.foundation.CurvatureEstimator curvatureEstimator)
		throws java.lang.Exception
	{
		if (null == (_positionPrincipalComponentScheme = positionPrincipalComponentScheme) ||
			_positionPrincipalComponentScheme.isEmpty() ||
			null == (_curvatureEstimator = curvatureEstimator))
		{
			throw new java.lang.Exception ("MarginEstimationSettings Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Position Principal Component Scheme
	 * 
	 * @return The Position Principal Component Scheme
	 */

	public java.lang.String positionPrincipalComponentScheme()
	{
		return _positionPrincipalComponentScheme;
	}

	/**
	 * Retrieve the Curvature Estimator Function
	 * 
	 * @return The Curvature Estimator Function
	 */

	public org.drip.simm.foundation.CurvatureEstimator curvatureEstimator()
	{
		return _curvatureEstimator;
	}
}
