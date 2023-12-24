
package org.drip.state.estimator;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.drip.analytics.date.JulianDate;
import org.drip.analytics.support.CaseInsensitiveHashMap;
import org.drip.state.identifier.LatentStateLabel;

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
 * <i>PredictorResponseWeightConstraint</i> holds the Linearized Constraints (and, optionally, their quote
 * sensitivities) necessary needed for the Linear Calibration. Linearized Constraints are expressed as
 * 
 * 			Sum_i[Predictor Weight_i * Function (Response_i)] = Constraint Value
 * 
 * where Function can either be univariate function, or weighted spline basis set. To this end, it
 * implements the following functionality:
 * 
 *  <ul>
 *  	<li><i>PredictorResponseWeightConstraint</i> constructor</li>
 *  	<li>Add a Predictor/Response Weight entry to the Linearized Constraint</li>
 *  	<li>Update the Constraint Value</li>
 *  	<li>Update the Constraint Value Sensitivity</li>
 *  	<li>Retrieve the Constraint Value</li>
 *  	<li>Retrieve the Constraint Value Sensitivity</li>
 *  	<li>Add a Merging Latent State Label</li>
 *  	<li>Return the Set of Merged Latent State Labels</li>
 *  	<li>Retrieve the Predictor To-From Response Weight Map</li>
 *  	<li>Retrieve the Predictor To-From Response Weight Sensitivity Map</li>
 *  	<li>"Absorb" the other <i>PredictorResponseWeightConstraint</i> Instance into the Current One</li>
 *  	<li>Return the Set of Available Sensitivities (if any)</li>
 *  	<li>Display the Constraints and the corresponding Weights</li>
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

public class PredictorResponseWeightConstraint
{
	private HashSet<LatentStateLabel> _latentStateLabelSet = null;

	private PredictorResponseRelationSetup _calibrationPredictorResponseRelationSetup =
		new PredictorResponseRelationSetup();

	private CaseInsensitiveHashMap<PredictorResponseRelationSetup> _predictorResponseRelationSetupMap =
		new CaseInsensitiveHashMap<PredictorResponseRelationSetup>();

	private PredictorResponseRelationSetup predictorResponseRelationSetup (
		final String manifestMeasure)
	{
		if (null == manifestMeasure || manifestMeasure.isEmpty()) {
			return null;
		}

		if (!_predictorResponseRelationSetupMap.containsKey (manifestMeasure)) {
			_predictorResponseRelationSetupMap.put (manifestMeasure, new PredictorResponseRelationSetup());
		}

		return _predictorResponseRelationSetupMap.get (manifestMeasure);
	}

	/**
	 * Empty <i>PredictorResponseWeightConstraint</i> constructor
	 */

	public PredictorResponseWeightConstraint()
	{
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
		return _calibrationPredictorResponseRelationSetup.addPredictorResponseWeight (
			predictor,
			responseWeight
		);
	}

	/**
	 * Add a Predictor/Response Weight entry to the Linearized Constraint
	 * 
	 * @param manifestMeasure The Manifest Measure
	 * @param predictor The Predictor Node
	 * @param dResponseWeightDManifestMeasure The Response Weight-to-Manifest Measure Sensitivity at the Node
	 * 
	 * @return TRUE - Successfully added
	 */

	public boolean addDResponseWeightDManifestMeasure (
		final String manifestMeasure,
		final double predictor,
		final double dResponseWeightDManifestMeasure)
	{
		return predictorResponseRelationSetup (manifestMeasure).addPredictorResponseWeight (
			predictor,
			dResponseWeightDManifestMeasure
		);
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
		return _calibrationPredictorResponseRelationSetup.updateValue (value);
	}

	/**
	 * Update the Constraint Value Sensitivity
	 * 
	 * @param manifestMeasure The Manifest Measure
	 * @param dValueDManifestMeasure The Constraint Value Sensitivity Update Increment
	 * 
	 * @return TRUE - This Sensitivity Update Succeeded
	 */

	public boolean updateDValueDManifestMeasure (
		final String manifestMeasure,
		final double dValueDManifestMeasure)
	{
		return predictorResponseRelationSetup (manifestMeasure).updateValue (dValueDManifestMeasure);
	}

	/**
	 * Retrieve the Constraint Value
	 * 
	 * @return The Constraint Value
	 */

	public double getValue()
	{
		return _calibrationPredictorResponseRelationSetup.getValue();
	}

	/**
	 * Retrieve the Constraint Value Sensitivity
	 * 
	 * @param manifestMeasure The Manifest Measure
	 * 
	 * @return The Constraint Value Sensitivity
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are invalid
	 */

	public double getDValueDManifestMeasure (
		final String manifestMeasure)
		throws Exception
	{
		if (!_predictorResponseRelationSetupMap.containsKey (manifestMeasure)) {
			throw new Exception (
				"PredictorResponseWeightConstraint::getDValueDManifestMeasure => Cannot locate manifest measure "
					+ manifestMeasure
			);
		}

		return _predictorResponseRelationSetupMap.get (manifestMeasure).getValue();
	}

	/**
	 * Add a Merging Latent State Label
	 * 
	 * @param mergeLatentStateLabel The Merging Latent State Label
	 * 
	 * @return TRUE - The Latent State Label Successfully Added
	 */

	public boolean addMergeLabel (
		final LatentStateLabel mergeLatentStateLabel)
	{
		if (null == mergeLatentStateLabel) {
			return false;
		}

		if (null == _latentStateLabelSet) {
			_latentStateLabelSet = new HashSet<LatentStateLabel>();
		}

		_latentStateLabelSet.add (mergeLatentStateLabel);

		return true;
	}

	/**
	 * Return the Set of Merged Latent State Labels
	 * 
	 * @return The Set of Merged Latent State Labels
	 */

	public Set<LatentStateLabel> mergeLabelSet()
	{
		return _latentStateLabelSet;
	}

	/**
	 * Retrieve the Predictor To-From Response Weight Map
	 * 
	 * @return The Predictor To-From Response Weight Map
	 */

	public TreeMap<Double, Double> getPredictorResponseWeight()
	{
		return _calibrationPredictorResponseRelationSetup.getPredictorResponseWeight();
	}

	/**
	 * Retrieve the Predictor To-From Response Weight Sensitivity Map
	 * 
	 * @param manifestMeasure The Manifest Measure
	 * 
	 * @return The Predictor To-From Response Weight Sensitivity Map
	 */

	public TreeMap<Double, Double> getDResponseWeightDManifestMeasure (
		final String manifestMeasure)
	{
		return !_predictorResponseRelationSetupMap.containsKey (manifestMeasure) ? null :
			_predictorResponseRelationSetupMap.get (manifestMeasure).getPredictorResponseWeight();
	}

	/**
	 * "Absorb" the other <i>PredictorResponseWeightConstraint</i> Instance into the Current One
	 * 
	 * @param otherPredictorResponseWeightConstraint The "Other" PRWC Instance
	 * 
	 * @return TRUE - At least one entry of the "Other" was absorbed
	 */

	public boolean absorb (
		final PredictorResponseWeightConstraint otherPredictorResponseWeightConstraint)
	{
		if (null == otherPredictorResponseWeightConstraint ||
			!_calibrationPredictorResponseRelationSetup.absorb (
				otherPredictorResponseWeightConstraint._calibrationPredictorResponseRelationSetup
			)
		) {
			return false;
		}

		if (0 == otherPredictorResponseWeightConstraint._predictorResponseRelationSetupMap.size()) {
			return true;
		}

		if (0 != _predictorResponseRelationSetupMap.size()) {
			for (Map.Entry<String, PredictorResponseRelationSetup> mapEntry :
				_predictorResponseRelationSetupMap.entrySet()) {
				String key = mapEntry.getKey();

				if (otherPredictorResponseWeightConstraint._predictorResponseRelationSetupMap.containsKey
					(key)) {
					mapEntry.getValue().absorb (
						otherPredictorResponseWeightConstraint._predictorResponseRelationSetupMap.get (key)
					);
				}
			}
		}

		for (Map.Entry<String, PredictorResponseRelationSetup> mapEntry :
			otherPredictorResponseWeightConstraint._predictorResponseRelationSetupMap.entrySet()) {
			String key = mapEntry.getKey();

			if (!_predictorResponseRelationSetupMap.containsKey (key)) {
				_predictorResponseRelationSetupMap.put (key, mapEntry.getValue());
			}
		}

		Set<LatentStateLabel> latentStateLabelSet = otherPredictorResponseWeightConstraint.mergeLabelSet();

		if (null == latentStateLabelSet || 0 == latentStateLabelSet.size()) {
			return true;
		}

		for (LatentStateLabel latentStateLabel : latentStateLabelSet) {
			if (!addMergeLabel (latentStateLabel)) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Return the Set of Available Sensitivities (if any)
	 * 
	 * @return The Set of Available Sensitivities
	 */

	public Set<String> sensitivityKeys()
	{
		return _predictorResponseRelationSetupMap.keySet();
	}

	/**
	 * Display the Constraints and the corresponding Weights
	 * 
	 * @param comment The Prefix Comment
	 */

	public void displayString (
		final String comment)
	{
		Map<Double, Double> predictorResponseWeightMap =
			_calibrationPredictorResponseRelationSetup.getPredictorResponseWeight();

		if (null != predictorResponseWeightMap && 0 != predictorResponseWeightMap.size()) {
			for (Map.Entry<Double, Double> mapEntry : predictorResponseWeightMap.entrySet()) {
				double date = mapEntry.getKey();

				System.out.println (
					"\t\t" + comment + " - " + new JulianDate ((int) date) + " => " + mapEntry.getValue()
				);
			}
		}

		System.out.println (
			"\t" + comment + " Constraint: " + _calibrationPredictorResponseRelationSetup.getValue()
		);

		if (null != _latentStateLabelSet) {
			String latentStateLabels = "\t" + comment + " Labels:";

			for (LatentStateLabel latentStateLabel : _latentStateLabelSet) {
				latentStateLabels += " " + latentStateLabel.fullyQualifiedName();
			}

			System.out.println (latentStateLabels);
		}

		System.out.flush();
	}
}
