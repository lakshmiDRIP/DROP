
package org.drip.state.estimator;

import java.util.Map;
import java.util.TreeMap;

import org.drip.numerical.common.NumberUtil;

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
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
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
 * <i>PredictorResponseRelationSetup</i> holds the Linearized Constraints (and, optionally, their quote
 * sensitivities) necessary needed for the Linear Calibration. Linearized Constraints are expressed as
 * 
 * 			Sum_i[Predictor Weight_i * Function (Response_i)] = Constraint Value
 * 
 * where Function can either be univariate function, or weighted spline basis set. To this end, it implements
 * the following functionality:
 * 
 *  <ul>
 *  	<li><i>PredictorResponseRelationSetup</i> constructor</li>
 *  	<li>Update the Constraint Value</li>
 *  	<li>Add a Predictor/Response Weight entry to the Linearized Constraint</li>
 *  	<li>Retrieve the Constraint Value</li>
 *  	<li>Retrieve the Predictor To-From Response Weight Map</li>
 *  	<li>Absorb the "Other" <i>PredictorResponseRelationSetup</i> onto the current one</li>
 *  </ul>
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
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/estimator/README.md">Multi-Pass Customized Stretch Curve</a></td></tr>
 *  </table>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class PredictorResponseRelationSetup
{
	private double _value = 0.;

	private TreeMap<Double, Double> _predictorResponseWeightMap = new TreeMap<Double, Double>();

	/**
	 * Empty <i>PredictorResponseRelationSetup</i> constructor
	 */

	public PredictorResponseRelationSetup()
	{
	}

	/**
	 * Update the Constraint Value
	 * 
	 * @param value The Constraint Value Update Increment
	 * 
	 * @return TRUE - This Update Succeeded
	 */

	public boolean updateValue (
		final double value)
	{
		if (!NumberUtil.IsValid (value)) {
			return false;
		}

		_value += value;
		return true;
	}

	/**
	 * Add a Predictor/Response Weight entry to the Linearized Constraint
	 * 
	 * @param predictor The Predictor Node
	 * @param responseWeight The Response Weight at the Node
	 * 
	 * @return TRUE - Successfully added
	 */

	public boolean addPredictorResponseWeight (
		final double predictor,
		final double responseWeight)
	{
		if (!NumberUtil.IsValid (predictor) || !NumberUtil.IsValid (responseWeight)) {
			return false;
		}

		double responseWeightPrior = _predictorResponseWeightMap.containsKey (predictor) ?
			_predictorResponseWeightMap.get (predictor) : 0.;

		_predictorResponseWeightMap.put (
			predictor,
			responseWeight + responseWeightPrior
		);

		return true;
	}

	/**
	 * Retrieve the Constraint Value
	 * 
	 * @return The Constraint Value
	 */

	public double getValue()
	{
		return _value;
	}

	/**
	 * Retrieve the Predictor To-From Response Weight Map
	 * 
	 * @return The Predictor To-From Response Weight Map
	 */

	public TreeMap<Double, Double> getPredictorResponseWeight()
	{
		return _predictorResponseWeightMap;
	}

	/**
	 * Absorb the "Other" <i>PredictorResponseRelationSetup</i> onto the current one
	 * 
	 * @param predictorResponseRelationSetupOther The "Other" PRRS
	 * 
	 * @return TRUE - At least one Entry was absorbed
	 */

	public boolean absorb (
		final PredictorResponseRelationSetup predictorResponseRelationSetupOther)
	{
		if (null == predictorResponseRelationSetupOther ||
			!updateValue (predictorResponseRelationSetupOther.getValue())) {
			return false;
		}

		TreeMap<Double, Double> predictorResponseWeightMapOther =
			predictorResponseRelationSetupOther.getPredictorResponseWeight();

		if (null == predictorResponseWeightMapOther || 0 == predictorResponseWeightMapOther.size()) {
			return true;
		}

		for (Map.Entry<Double, Double> mapEntry : predictorResponseWeightMapOther.entrySet()) {
			if (null != mapEntry && !addPredictorResponseWeight (mapEntry.getKey(), mapEntry.getValue())) {
				return false;
			}
		}

		return true;
	}
}
