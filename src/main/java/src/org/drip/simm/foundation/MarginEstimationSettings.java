
package org.drip.simm.foundation;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
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
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/MarginAnalyticsLibrary.md">Initial and Variation Margin Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/README.md">Initial Margin Analytics based on ISDA SIMM and its Variants</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/foundation/README.md">Foundation Utilities for ISDA SIMM</a></li>
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

	public static final String POSITION_PRINCIPAL_COMPONENT_COVARIANCE_ESTIMATOR_FRTB = "FRTB";

	/**
	 * ISDA Based Position - Principal Component Estimator
	 */

	public static final String POSITION_PRINCIPAL_COMPONENT_COVARIANCE_ESTIMATOR_ISDA = "ISDA";

	private CurvatureEstimator _curvatureEstimator = null;
	private String _positionPrincipalComponentScheme = "";

	/**
	 * Generate a Cornish-Fischer Instance of MarginEstimationSettings
	 * 
	 * @param positionPrincipalComponentScheme The Position Principal Component Scheme
	 * 
	 * @return Cornish-Fischer Instance of MarginEstimationSettings
	 */

	public static final MarginEstimationSettings CornishFischer (
		final String positionPrincipalComponentScheme)
	{
		try
		{
			return new MarginEstimationSettings (
				positionPrincipalComponentScheme,
				CurvatureEstimatorResponseFunction.CornishFischer()
			);
		}
		catch (Exception e)
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
		final String positionPrincipalComponentScheme)
	{
		try
		{
			return new MarginEstimationSettings (
				positionPrincipalComponentScheme,
				CurvatureEstimatorISDADelta.Standard()
			);
		}
		catch (Exception e)
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
		final String positionPrincipalComponentScheme)
	{
		try
		{
			return new MarginEstimationSettings (
				positionPrincipalComponentScheme,
				CurvatureEstimatorFRTB.Standard()
			);
		}
		catch (Exception e)
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
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public MarginEstimationSettings (
		final String positionPrincipalComponentScheme,
		final CurvatureEstimator curvatureEstimator)
		throws Exception
	{
		if (null == (_positionPrincipalComponentScheme = positionPrincipalComponentScheme) ||
				_positionPrincipalComponentScheme.isEmpty() ||
			null == (_curvatureEstimator = curvatureEstimator)
		)
		{
			throw new Exception (
				"MarginEstimationSettings Constructor => Invalid Inputs"
			);
		}
	}

	/**
	 * Retrieve the Position Principal Component Scheme
	 * 
	 * @return The Position Principal Component Scheme
	 */

	public String positionPrincipalComponentScheme()
	{
		return _positionPrincipalComponentScheme;
	}

	/**
	 * Retrieve the Curvature Estimator Function
	 * 
	 * @return The Curvature Estimator Function
	 */

	public CurvatureEstimator curvatureEstimator()
	{
		return _curvatureEstimator;
	}
}
