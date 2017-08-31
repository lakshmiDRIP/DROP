
package org.drip.portfolioconstruction.bayesian;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * 
 *  This file is part of DRIP, a free-software/open-source library for buy/side financial/trading model
 *  	libraries targeting analysts and developers
 *  	https://lakshmidrip.github.io/DRIP/
 *  
 *  DRIP is composed of four main libraries:
 *  
 *  - DRIP Fixed Income - https://lakshmidrip.github.io/DRIP-Fixed-Income/
 *  - DRIP Asset Allocation - https://lakshmidrip.github.io/DRIP-Asset-Allocation/
 *  - DRIP Numerical Optimizer - https://lakshmidrip.github.io/DRIP-Numerical-Optimizer/
 *  - DRIP Statistical Learning - https://lakshmidrip.github.io/DRIP-Statistical-Learning/
 * 
 *  - DRIP Fixed Income: Library for Instrument/Trading Conventions, Treasury Futures/Options,
 *  	Funding/Forward/Overnight Curves, Multi-Curve Construction/Valuation, Collateral Valuation and XVA
 *  	Metric Generation, Calibration and Hedge Attributions, Statistical Curve Construction, Bond RV
 *  	Metrics, Stochastic Evolution and Option Pricing, Interest Rate Dynamics and Option Pricing, LMM
 *  	Extensions/Calibrations/Greeks, Algorithmic Differentiation, and Asset Backed Models and Analytics.
 * 
 *  - DRIP Asset Allocation: Library for model libraries for MPT framework, Black Litterman Strategy
 *  	Incorporator, Holdings Constraint, and Transaction Costs.
 * 
 *  - DRIP Numerical Optimizer: Library for Numerical Optimization and Spline Functionality.
 * 
 *  - DRIP Statistical Learning: Library for Statistical Evaluation and Machine Learning.
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
 * ProjectionExposure holds the Projection Exposure Loadings that Weight the Exposure to the Projection Pick
 * 	Portfolio. The Reference is:
 *  
 *  - He. G., and R. Litterman (1999): The Intuition behind the Black-Litterman Model Portfolios, Goldman
 *  	Sachs Asset Management
 *
 * @author Lakshmi Krishnamurthy
 */

public class ProjectionExposure {
	private double[] _adblIntraViewComponent = null;
	private double[] _adblInterViewComponent = null;
	private double[] _adblPriorViewComponent = null;
	private double[][] _aadblCompositeConfidenceCovariance = null;

	/**
	 * ProjectionExposure Constructor
	 * 
	 * @param adblIntraViewComponent Array of Per-View View-Specific Exposure Component
	 * @param adblInterViewComponent Array of Per-View Exposure Contribution from other Views
	 * @param adblPriorViewComponent Array of View-Specific Per-View Components
	 * @param aadblCompositeConfidenceCovariance Composite Confidence Co-variance Matrix
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public ProjectionExposure (
		final double[] adblIntraViewComponent,
		final double[] adblInterViewComponent,
		final double[] adblPriorViewComponent,
		final double[][] aadblCompositeConfidenceCovariance)
		throws java.lang.Exception
	{
		if (null == (_adblIntraViewComponent = adblIntraViewComponent) || null == (_adblInterViewComponent =
			adblInterViewComponent) || null == (_adblPriorViewComponent = adblPriorViewComponent) || null ==
				(_aadblCompositeConfidenceCovariance = aadblCompositeConfidenceCovariance))
			throw new java.lang.Exception ("ProjectionExposure Constructor => Invalid Inputs");

		int iNumView = _adblIntraViewComponent.length;

		if (0 == iNumView || iNumView != _adblInterViewComponent.length || iNumView !=
			_adblPriorViewComponent.length || iNumView != _aadblCompositeConfidenceCovariance.length)
			throw new java.lang.Exception ("ProjectionExposure Constructor => Invalid Inputs");

		for (int i = 0; i < iNumView; ++i) {
			if (!org.drip.quant.common.NumberUtil.IsValid (_adblIntraViewComponent[i]) ||
				!org.drip.quant.common.NumberUtil.IsValid (_adblInterViewComponent[i]) ||
					!org.drip.quant.common.NumberUtil.IsValid (_adblPriorViewComponent[i]) || null ==
						_aadblCompositeConfidenceCovariance[i] || iNumView !=
							_aadblCompositeConfidenceCovariance[i].length)
				throw new java.lang.Exception ("ProjectionExposure Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Single View Joint Contribution Component
	 * 
	 * @return The Single View Joint Contribution Component
	 */

	public double[] intraViewComponent()
	{
		return _adblIntraViewComponent;
	}

	/**
	 * Retrieve the View/View Joint Contribution Component
	 * 
	 * @return The View/View Joint Contribution Component
	 */

	public double[] interViewComponent()
	{
		return _adblInterViewComponent;
	}

	/**
	 * Retrieve the Prior/View Joint Contribution Component
	 * 
	 * @return The Prior/View Joint Contribution Component
	 */

	public double[] priorViewComponent()
	{
		return _adblPriorViewComponent;
	}

	/**
	 * Retrieve the Composite Confidence Co-variance
	 * 
	 * @return The Composite Confidence Co-variance
	 */

	public double[][] compositeConfidenceCovariance()
	{
		return _aadblCompositeConfidenceCovariance;
	}

	/**
	 * Compute the Array of Cumulative View Loading Components
	 * 
	 * @return The Array of Cumulative View Loading Components
	 */

	public double[] cumulativeViewComponent()
	{
		int iNumView = _adblIntraViewComponent.length;
		double[] adblViewLoading = new double[iNumView];

		for (int i = 0; i < iNumView; ++i)
			adblViewLoading[i] = _adblIntraViewComponent[i] + _adblInterViewComponent[i] +
				_adblPriorViewComponent[i];

		return adblViewLoading;
	}
}
