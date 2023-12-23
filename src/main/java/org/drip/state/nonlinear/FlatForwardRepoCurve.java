
package org.drip.state.nonlinear;

import org.drip.numerical.common.NumberUtil;
import org.drip.product.definition.Component;
import org.drip.state.repo.ExplicitBootRepoCurve;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2025 Lakshmi Krishnamurthy
 * Copyright (C) 2024 Lakshmi Krishnamurthy
 * Copyright (C) 2023 Lakshmi Krishnamurthy
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
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
 * <i>FlatForwardRepoCurve</i> manages the Repo Latent State, using the Forward Repo Rate as the State
 * Response Representation.
 *
 *  <br>
 *  <style>table, td, th {
 *  	padding: 1px; border: 2px solid #008000; border-radius: 8px; background-color: #dfff00;
 *		text-align: center; color:  #0000ff;
 *  }
 *  </style>
 *  
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/README.md">Latent State Inference and Creation Utilities</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/nonlinear/README.md">Nonlinear (i.e., Boot) Latent State Construction</a></td></tr>
 *  </table>
 *
 * @author Lakshmi Krishnamurthy
 */

public class FlatForwardRepoCurve extends ExplicitBootRepoCurve
{
	private int[] _dateArray = null;
	private double[] _repoForwardArray = null;

	/**
	 * FlatForwardRepoCurve Constructor
	 * 
	 * @param epochDate Epoch Date
	 * @param repoComponent The Repo Component
	 * @param pillarDateArray Array of Pillar Dates
	 * @param forwardRepoRateArray Array of Repo Forward Rates
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public FlatForwardRepoCurve (
		final int epochDate,
		final Component repoComponent,
		final int[] pillarDateArray,
		final double[] forwardRepoRateArray)
		throws Exception
	{
		super (epochDate, repoComponent);

		if (null == (_dateArray = pillarDateArray) || null == (_repoForwardArray = forwardRepoRateArray) ||
			_dateArray.length != _repoForwardArray.length) {
			throw new Exception ("FlatForwardRepoCurve ctr => Invalid Inputs");
		}

		int pillarCount = _dateArray.length;

		for (int i = 0; i < pillarCount; ++i) {
			if (!NumberUtil.IsValid (_dateArray[i]) || !NumberUtil.IsValid (_repoForwardArray[i])) {
				throw new Exception ("FlatForwardRepoCurve ctr => Invalid Inputs");
			}
		}
	}

	@Override public double repo (
		final int date)
		throws Exception
	{
		if (date >= component().maturityDate().julian()) {
			throw new Exception ("FlatForwardRepoCurve::repo => Invalid Input");
		}

		if (date <= epoch().julian()) {
			return _repoForwardArray[0];
		}

		int pillarCount = _repoForwardArray.length;

		for (int i = 1; i < pillarCount; ++i) {
			if (_dateArray[i - 1] <= date && _dateArray[i] > date) {
				return _repoForwardArray[i];
			}
		}

		return _repoForwardArray[pillarCount - 1];
	}

	@Override public boolean setNodeValue (
		final int nodeIndex,
		final double value)
	{
		if (!NumberUtil.IsValid (value) || nodeIndex > _repoForwardArray.length) {
			return false;
		}

		for (int forwardRepoNodeIndex = nodeIndex; forwardRepoNodeIndex < _repoForwardArray.length;
			++forwardRepoNodeIndex) {
			_repoForwardArray[forwardRepoNodeIndex] = value;
		}

		return true;
	}

	@Override public boolean bumpNodeValue (
		final int nodeIndex,
		final double value)
	{
		if (!NumberUtil.IsValid (value) || nodeIndex > _repoForwardArray.length) {
			return false;
		}

		for (int forwardRepoNodeIndex = nodeIndex; forwardRepoNodeIndex < _repoForwardArray.length;
			++forwardRepoNodeIndex) {
			_repoForwardArray[forwardRepoNodeIndex] += value;
		}

		return true;
	}

	@Override public boolean setFlatValue (
		final double value)
	{
		if (!NumberUtil.IsValid (value)) {
			return false;
		}

		for (int forwardRepoNodeIndex = 0; forwardRepoNodeIndex < _repoForwardArray.length;
			++forwardRepoNodeIndex) {
			_repoForwardArray[forwardRepoNodeIndex] = value;
		}

		return true;
	}
}
