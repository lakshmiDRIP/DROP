
package org.drip.param.pricer;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting analytics/risk, transaction cost analytics,
 *  	asset liability management analytics, capital, exposure, and margin analytics, valuation adjustment
 *  	analytics, and portfolio construction analytics within and across fixed income, credit, commodity,
 *  	equity, FX, and structured products. It also includes auxiliary libraries for algorithm support,
 *  	numerical analysis, numerical optimization, spline builder, model validation, statistical learning,
 *  	and computational support.
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
 * <i>HestonOptionPricerParams</i> holds the parameters that drive the dynamics of the Heston stochastic
 * volatility model.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/param/README.md">Product Cash Flow, Valuation, Market, Pricing, and Quoting Parameters</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/param/pricer/README.md">Pricing Parameters Customization Settings Control</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class HestonOptionPricerParams {
	private int _iPayoffTransformScheme = -1;
	private double _dblRho = java.lang.Double.NaN;
	private double _dblKappa = java.lang.Double.NaN;
	private double _dblSigma = java.lang.Double.NaN;
	private double _dblTheta = java.lang.Double.NaN;
	private double _dblLambda = java.lang.Double.NaN;
	private int _iMultiValuePhaseTrackerType =
		org.drip.numerical.fourier.PhaseAdjuster.MULTI_VALUE_BRANCH_POWER_PHASE_TRACKER_KAHL_JACKEL;

	/**
	 * HestonOptionPricerParams constructor
	 * 
	 * @param iPayoffTransformScheme The Payoff Transformation Scheme
	 * @param dblRho Rho
	 * @param dblKappa Kappa
	 * @param dblSigma Sigma
	 * @param dblTheta Theta
	 * @param dblLambda Lambda
	 * @param iMultiValuePhaseTrackerType The Multi Valued Phase Tracking Error Corrector
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public HestonOptionPricerParams (
		final int iPayoffTransformScheme,
		final double dblRho,
		final double dblKappa,
		final double dblSigma,
		final double dblTheta,
		final double dblLambda,
		final int iMultiValuePhaseTrackerType)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (_dblRho = dblRho) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_dblKappa = dblKappa) ||
				!org.drip.numerical.common.NumberUtil.IsValid (_dblSigma = dblSigma) ||
					!org.drip.numerical.common.NumberUtil.IsValid (_dblTheta = dblTheta) ||
						!org.drip.numerical.common.NumberUtil.IsValid (_dblLambda = dblLambda))
			throw new java.lang.Exception ("HestonOptionPricerParams ctr: Invalid Inputs!");

		_iPayoffTransformScheme = iPayoffTransformScheme;
		_iMultiValuePhaseTrackerType = iMultiValuePhaseTrackerType;
	}

	/**
	 * Retrieve Kappa
	 * 
	 * @return The Kappa
	 */

	public double kappa()
	{
		return _dblKappa;
	}

	/**
	 * Retrieve Lambda
	 * 
	 * @return The Lambda
	 */

	public double lambda()
	{
		return _dblLambda;
	}

	/**
	 * Retrieve Rho
	 * 
	 * @return The Rho
	 */

	public double rho()
	{
		return _dblRho;
	}

	/**
	 * Retrieve Sigma
	 * 
	 * @return The Sigma
	 */

	public double sigma()
	{
		return _dblSigma;
	}

	/**
	 * Retrieve Theta
	 * 
	 * @return The Theta
	 */

	public double theta()
	{
		return _dblTheta;
	}

	/**
	 * Return the Multi Valued Principal Branch Maintaining Phase Tracker Type
	 * 
	 * @return The Multi Valued Principal Branch Maintaining Phase Tracker Type
	 */

	public int phaseTrackerType()
	{
		return _iMultiValuePhaseTrackerType;
	}

	/**
	 * Return the Payoff Fourier Transformation Scheme
	 * 
	 * @return The Payoff Fourier Transformation Scheme
	 */

	public int payoffTransformScheme()
	{
		return _iPayoffTransformScheme;
	}
}
