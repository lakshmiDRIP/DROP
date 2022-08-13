
package org.drip.simm.margin;

import org.drip.numerical.common.NumberUtil;

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
 * <i>BucketAggregateIR</i> holds the Single Bucket IR Sensitivity Margin, the Cumulative Bucket Risk Factor
 * Sensitivity Margin, as well as the IR Aggregate Risk Factor Maps. The References are:
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
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/margin/README.md">ISDA SIMM Risk Factor Margin Metrics</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class BucketAggregateIR
{
	private double _sensitivityMarginVariance = Double.NaN;
	private double _cumulativeSensitivityMargin = Double.NaN;
	private RiskFactorAggregateIR _riskFactorAggregate = null;
	private SensitivityAggregateIR _sensitivityAggregate = null;

	/**
	 * BucketAggregateIR Constructor
	 * 
	 * @param riskFactorAggregate The IR Risk Factor Aggregate
	 * @param sensitivityAggregate The IR Sensitivity Aggregate
	 * @param sensitivityMarginVariance The Bucket's Sensitivity Margin Variance
	 * @param cumulativeSensitivityMargin The Cumulative Risk Factor Sensitivity Margin
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public BucketAggregateIR (
		final RiskFactorAggregateIR riskFactorAggregate,
		final SensitivityAggregateIR sensitivityAggregate,
		final double sensitivityMarginVariance,
		final double cumulativeSensitivityMargin)
		throws Exception
	{
		if (null == (_riskFactorAggregate = riskFactorAggregate) ||
			null == (_sensitivityAggregate = sensitivityAggregate) ||
			!NumberUtil.IsValid (
				_sensitivityMarginVariance = sensitivityMarginVariance
			) ||
			!NumberUtil.IsValid (
				_cumulativeSensitivityMargin = cumulativeSensitivityMargin
			)
		)
		{
			throw new Exception (
				"BucketAggregateIR Constructor => Invalid Inputs"
			);
		}
	}

	/**
	 * Retrieve the IR Risk Factor Aggregate
	 * 
	 * @return The IR Risk Factor Aggregate
	 */

	public RiskFactorAggregateIR riskFactorAggregate()
	{
		return _riskFactorAggregate;
	}

	/**
	 * Retrieve the IR Sensitivity Aggregate
	 * 
	 * @return The IR Sensitivity Aggregate
	 */

	public SensitivityAggregateIR sensitivityAggregate()
	{
		return _sensitivityAggregate;
	}

	/**
	 * Retrieve the Bucket's Sensitivity Margin Variance
	 * 
	 * @return The Bucket's Sensitivity Margin Variance
	 */

	public double sensitivityMarginVariance()
	{
		return _sensitivityMarginVariance;
	}

	/**
	 * Retrieve the Bucket's Cumulative Risk Factor Sensitivity Margin
	 * 
	 * @return The Bucket's Cumulative Risk Factor Sensitivity Margin
	 */

	public double cumulativeSensitivityMargin()
	{
		return _cumulativeSensitivityMargin;
	}

	/**
	 * Compute the ISDA SIMM Position Principal Component Co-variance
	 * 
	 * @return The ISDA SIMM Position Principal Component Co-variance
	 */

	public double positionPrincipalComponentCovarianceISDA()
	{
		double sensitivityMargin = Math.sqrt (
			_sensitivityMarginVariance
		);

		return Math.max (
			Math.min (
				_cumulativeSensitivityMargin,
				sensitivityMargin
			),
			-1. * sensitivityMargin
		);
	}

	/**
	 * Compute the FRTB SBA-C Position Principal Component Co-variance
	 * 
	 * @return The FRTB SBA-C Position Principal Component Co-variance
	 */

	public double positionPrincipalComponentCovarianceFRTB()
	{
		return _cumulativeSensitivityMargin;
	}
}
