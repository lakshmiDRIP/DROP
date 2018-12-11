
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
 * <i>RiskGroupPrincipalCovariance</i> contains the Cross Risk-Group Principal Component Based Co-variance.
 * The References are:
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
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ExposureAnalyticsLibrary.md">Exposure Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm">SIMM</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/foundation">Foundation</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class RiskGroupPrincipalCovariance
{
	private double _extraGroupCorrelation = java.lang.Double.NaN;
	private org.drip.quant.eigen.EigenComponent _principalEigenComponent = null;

	/**
	 * Construct the Standard RiskGroupPrincipalCovariance Instance from the Bucket Correlation Matrix and
	 *  the Cross Correlation Entry
	 * 
	 * @param intraGroupCorrelationMatrix The Intra-Group Correlation Matrix
	 * @param extraGroupCorrelation Cross Group Correlation
	 * 
	 * @return The Standard RiskGroupPrincipalCovariance Instance
	 */

	public static final RiskGroupPrincipalCovariance Standard (
		final double[][] intraGroupCorrelationMatrix,
		final double extraGroupCorrelation)
	{
		try
		{
			return new RiskGroupPrincipalCovariance (
				new org.drip.quant.eigen.PowerIterationComponentExtractor (
					30,
					0.000001,
					false
				).principalComponent (intraGroupCorrelationMatrix),
				extraGroupCorrelation
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * RiskGroupPrincipalCovariance Constructor
	 * 
	 * @param principalEigenComponent Intra-Group Principal Eigen-Component
	 * @param extraGroupCorrelation Cross Group Correlation
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public RiskGroupPrincipalCovariance (
		final org.drip.quant.eigen.EigenComponent principalEigenComponent,
		final double extraGroupCorrelation)
		throws java.lang.Exception
	{
		if (null == (_principalEigenComponent = principalEigenComponent) ||
			!org.drip.quant.common.NumberUtil.IsValid (_extraGroupCorrelation = extraGroupCorrelation) ||
				-1. > _extraGroupCorrelation || 1. < _extraGroupCorrelation)
		{
			throw new java.lang.Exception ("RiskGroupPrincipalCovariance Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Intra-Group Principal Eigen-Component
	 * 
	 * @return The Intra-Group Principal Eigen-Component
	 */

	public org.drip.quant.eigen.EigenComponent principalEigenComponent()
	{
		return _principalEigenComponent;
	}

	/**
	 * Retrieve the Cross Group Correlation
	 * 
	 * @return The Cross Group Correlation
	 */

	public double extraGroupCorrelation()
	{
		return _extraGroupCorrelation;
	}

	/**
	 * Retrieve the Scaled Principal Eigen-vector
	 * 
	 * @return The Scaled Principal Eigen-vector
	 */

	public double[] scaledPrincipalEigenvector()
	{
		double scaleFactor = java.lang.Math.sqrt (_principalEigenComponent.eigenvalue());

		double[] principalEigenvector = _principalEigenComponent.eigenvector();

		int componentCount = principalEigenvector.length;
		double[] scaledPrincipalEigenvector = new double[componentCount];

		for (int componentIndex = 0; componentIndex < componentCount; ++componentIndex)
		{
			scaledPrincipalEigenvector[componentIndex] = principalEigenvector[componentIndex] * scaleFactor;
		}

		return scaledPrincipalEigenvector;
	}

	/**
	 * Retrieve the Unadjusted Cross-Group Co-variance
	 * 
	 * @return The Unadjusted Cross-Group Co-variance
	 */

	public double[][] unadjustedCovariance()
	{
		double[] scaledPrincipalEigenvector = scaledPrincipalEigenvector();

		return org.drip.quant.linearalgebra.Matrix.CrossProduct (
			scaledPrincipalEigenvector,
			scaledPrincipalEigenvector
		);
	}

	/**
	 * Retrieve the Adjusted Cross-Group Co-variance
	 * 
	 * @return The Adjusted Cross-Group Co-variance
	 */

	public double[][] adjustedCovariance()
	{
		return org.drip.quant.linearalgebra.Matrix.Scale2D (
			unadjustedCovariance(),
			_extraGroupCorrelation
		);
	}
}
