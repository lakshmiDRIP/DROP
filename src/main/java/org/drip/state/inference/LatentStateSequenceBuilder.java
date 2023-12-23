
package org.drip.state.inference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.drip.analytics.support.CaseInsensitiveHashMap;
import org.drip.numerical.common.NumberUtil;
import org.drip.param.market.CurveSurfaceQuoteContainer;
import org.drip.param.pricer.CreditPricerParams;
import org.drip.param.valuation.ValuationCustomizationParams;
import org.drip.param.valuation.ValuationParams;
import org.drip.product.definition.CalibratableComponent;
import org.drip.spline.grid.Span;
import org.drip.spline.params.PreceedingManifestSensitivityControl;
import org.drip.spline.params.ResponseValueSensitivityConstraint;
import org.drip.spline.params.SegmentResponseValueConstraint;
import org.drip.spline.params.StretchBestFitResponse;
import org.drip.spline.segment.LatentStateResponseModel;
import org.drip.spline.stretch.BoundarySettings;
import org.drip.spline.stretch.MultiSegmentSequence;
import org.drip.spline.stretch.SegmentSequenceBuilder;
import org.drip.state.estimator.CurveStretch;
import org.drip.state.estimator.PredictorResponseWeightConstraint;

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
 * <i>LatentStateSequenceBuilder</i> holds the logic behind building the bootstrap segments contained in the
 * given Stretch.
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
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/inference/README.md">Latent State Stretch Sequence Inference</a></td></tr>
 *  </table>
 * 
 * It extends <i>SegmentSequenceBuilder</i> by implementing/customizing the calibration of the starting as
 *  well as the subsequent segments.
 *
 * @author Lakshmi Krishnamurthy
 */

public class LatentStateSequenceBuilder implements SegmentSequenceBuilder
{
	private Span _span = null;
	private CurveStretch _curveStretch = null;
	private double _epochResponse = Double.NaN;
	private ValuationParams _valuationParams = null;
	private BoundarySettings _boundarySettings = null;
	private CreditPricerParams _creditPricerParams = null;
	private StretchBestFitResponse _stretchBestFitResponse = null;
	private CurveSurfaceQuoteContainer _curveSurfaceQuoteContainer = null;
	private LatentStateStretchSpec _latentStateStretchSpecification = null;
	private ValuationCustomizationParams _valuationCustomizationParams = null;
	private StretchBestFitResponse _stretchBestFitResponseQuoteSensitivity = null;
	private CaseInsensitiveHashMap<PreceedingManifestSensitivityControl>
		_preceedingManifestSensitivityControlMap = null;

	private Map<Double, ResponseValueSensitivityConstraint> _responseValueSensitivityConstraintMap =
		new HashMap<Double, ResponseValueSensitivityConstraint>();

	private PreceedingManifestSensitivityControl getPreceedingManifestSensitivityControl (
		final String manifestMeasure)
	{
		return _preceedingManifestSensitivityControlMap.containsKey (manifestMeasure) ?
			_preceedingManifestSensitivityControlMap.get (manifestMeasure) : null;
	}

	private SegmentResponseValueConstraint segmentCalibResponseConstraint (
		final PredictorResponseWeightConstraint predictorResponseWeightConstraint)
	{
		TreeMap<Double, Double> predictorResponseWeightMap =
			predictorResponseWeightConstraint.getPredictorResponseWeight();

		if (null == predictorResponseWeightMap || 0 == predictorResponseWeightMap.size()) {
			return null;
		}

		Set<Map.Entry<Double, Double>> predictorResponseWeightMapEntrySet =
			predictorResponseWeightMap.entrySet();

		if (null == predictorResponseWeightMapEntrySet || 0 == predictorResponseWeightMapEntrySet.size()) {
			return null;
		}

		double constraint = 0.;

		List<Double> predictorList = new ArrayList<Double>();

		List<Double> responseLSQMLoadingList = new ArrayList<Double>();

		for (Map.Entry<Double, Double> predictorResponseWeightMapEntry : predictorResponseWeightMapEntrySet)
		{
			if (null == predictorResponseWeightMapEntry) {
				return null;
			}

			double dblPredictorDate = predictorResponseWeightMapEntry.getKey();

			try {
				if (null != _span && _span.in (dblPredictorDate)) {
					constraint -= _span.calcResponseValue (dblPredictorDate) *
						predictorResponseWeightMapEntry.getValue();
				} else if (_curveStretch.inBuiltRange (dblPredictorDate)) {
					constraint -= _curveStretch.responseValue (dblPredictorDate) *
						predictorResponseWeightMapEntry.getValue();
				} else {
					predictorList.add (dblPredictorDate);

					responseLSQMLoadingList.add (predictorResponseWeightMapEntry.getValue());
				}
			} catch (Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		int predictorListSize = predictorList.size();

		double[] predictorArray = new double[predictorListSize];
		double[] responseLSQMLoadingArray = new double[predictorListSize];

		for (int predictorListIndex = 0; predictorListIndex < predictorListSize; ++predictorListIndex) {
			predictorArray[predictorListIndex] = predictorList.get (predictorListIndex);

			responseLSQMLoadingArray[predictorListIndex] = responseLSQMLoadingList.get (predictorListIndex);
		}

		try {
			return new SegmentResponseValueConstraint (
				predictorArray,
				responseLSQMLoadingArray,
				predictorResponseWeightConstraint.getValue() + constraint
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	private SegmentResponseValueConstraint segmentSensResponseConstraint (
		final PredictorResponseWeightConstraint predictorResponseWeightConstraint,
		final String manifestMeasure)
	{
		TreeMap<Double, Double> predictorSensitivityLoadingMap =
			predictorResponseWeightConstraint.getDResponseWeightDManifestMeasure (manifestMeasure);

		if (null == predictorSensitivityLoadingMap || 0 == predictorSensitivityLoadingMap.size()) {
			return null;
		}

		Set<Map.Entry<Double, Double>> predictorSensitivityLoadingMapEntrySet =
			predictorSensitivityLoadingMap.entrySet();

		if (null == predictorSensitivityLoadingMapEntrySet ||
			0 == predictorSensitivityLoadingMapEntrySet.size()) {
			return null;
		}

		double sensitivityLoadingConstraint = 0.;

		List<Double> predictorList = new ArrayList<Double>();

		List<Double> sensitivityLoadingList = new ArrayList<Double>();

		for (Map.Entry<Double, Double> predictorSensitivityLoadingMapEntry :
			predictorSensitivityLoadingMapEntrySet) {
			if (null == predictorSensitivityLoadingMapEntry) {
				return null;
			}

			double predictorDate = predictorSensitivityLoadingMapEntry.getKey();

			try {
				if (null != _span && _span.in (predictorDate)) {
					sensitivityLoadingConstraint -= _span.calcResponseValue (predictorDate) *
						predictorSensitivityLoadingMapEntry.getValue();
				} else if (_curveStretch.inBuiltRange (predictorDate)) {
					sensitivityLoadingConstraint -= _curveStretch.responseValue (predictorDate) *
						predictorSensitivityLoadingMapEntry.getValue();
				} else {
					predictorList.add (predictorDate);

					sensitivityLoadingList.add (predictorSensitivityLoadingMapEntry.getValue());
				}
			} catch (Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		int predictorListSize = predictorList.size();

		double[] predictorArray = new double[predictorListSize];
		double[] sensitivityLoadingArray = new double[predictorListSize];

		for (int predictorListIndex = 0; predictorListIndex < predictorListSize; ++predictorListIndex) {
			predictorArray[predictorListIndex] = predictorList.get (predictorListIndex);

			sensitivityLoadingArray[predictorListIndex] = sensitivityLoadingList.get (predictorListIndex);
		}

		try {
			return new SegmentResponseValueConstraint (
				predictorArray,
				sensitivityLoadingArray,
				predictorResponseWeightConstraint.getDValueDManifestMeasure (manifestMeasure) +
					sensitivityLoadingConstraint
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	private boolean generateSegmentConstraintSet (
		final double segmentRight,
		final PredictorResponseWeightConstraint predictorResponseWeightConstraint)
	{
		SegmentResponseValueConstraint baseSegmentResponseValueConstraint = segmentCalibResponseConstraint
			(predictorResponseWeightConstraint);

		if (null == baseSegmentResponseValueConstraint) {
			return false;
		}

		ResponseValueSensitivityConstraint responseValueSensitivityConstraint = null;

		try {
			responseValueSensitivityConstraint = new ResponseValueSensitivityConstraint (
				baseSegmentResponseValueConstraint
			);
		} catch (Exception e) {
			e.printStackTrace();

			return false;
		}

		Set<String> sensitivitySet = predictorResponseWeightConstraint.sensitivityKeys();

		if (null == sensitivitySet || 0 == sensitivitySet.size()) {
			_responseValueSensitivityConstraintMap.put (segmentRight, responseValueSensitivityConstraint);

			return true;
		}

		for (String manifestMeasure : sensitivitySet) {
			SegmentResponseValueConstraint sensitivitySegmentResponseValueConstraint =
				segmentSensResponseConstraint (predictorResponseWeightConstraint, manifestMeasure);

			if (null == sensitivitySegmentResponseValueConstraint ||
				!responseValueSensitivityConstraint.addManifestMeasureSensitivity (
					manifestMeasure,
					sensitivitySegmentResponseValueConstraint
				)) {
				continue;
			}
		}

		_responseValueSensitivityConstraintMap.put (segmentRight, responseValueSensitivityConstraint);

		return true;
	}

	/**
	 * LatentStateSequenceBuilder constructor
	 * 
	 * @param epochResponse Segment Sequence Left-most Response Value
	 * @param latentStateStretchSpecification The Latent State Stretch Specification
	 * @param valuationParams Valuation Parameter
	 * @param creditPricerParams Credit Pricer Parameter
	 * @param curveSurfaceQuoteContainer The Market Parameter Set
	 * @param valuationCustomizationParams Valuation Customization Parameters
	 * @param span The Containing Span this Stretch will become a part of
	 * @param stretchBestFitResponse Stretch Fitness Weighted Response
	 * @param preceedingManifestSensitivityControlMap Map of Preceeding Manifest Sensitivity Control
	 * 	Parameters
	 * @param stretchBestFitResponseQuoteSensitivity Stretch Fitness Weighted Response Quote Sensitivity
	 * @param boundarySettings The Calibration Boundary Condition
	 * 
	 * @throws Exception Thrown if the Inputs are invalid
	 */

	public LatentStateSequenceBuilder (
		final double epochResponse,
		final LatentStateStretchSpec latentStateStretchSpecification,
		final ValuationParams valuationParams,
		final CreditPricerParams creditPricerParams,
		final CurveSurfaceQuoteContainer curveSurfaceQuoteContainer,
		final ValuationCustomizationParams valuationCustomizationParams,
		final Span span,
		final StretchBestFitResponse stretchBestFitResponse,
		final CaseInsensitiveHashMap<PreceedingManifestSensitivityControl>
			preceedingManifestSensitivityControlMap,
		final StretchBestFitResponse stretchBestFitResponseQuoteSensitivity,
		final BoundarySettings boundarySettings)
		throws Exception
	{
		if (!NumberUtil.IsValid (_epochResponse = epochResponse) ||
			null == (_latentStateStretchSpecification = latentStateStretchSpecification) ||
			null == (_valuationParams = valuationParams) || null == (_boundarySettings = boundarySettings) ||
			null == (_preceedingManifestSensitivityControlMap = preceedingManifestSensitivityControlMap)) {
			throw new Exception ("LatentStateSequenceBuilder ctr: Invalid Inputs");
		}

		_span = span;
		_creditPricerParams = creditPricerParams;
		_stretchBestFitResponse = stretchBestFitResponse;
		_curveSurfaceQuoteContainer = curveSurfaceQuoteContainer;
		_valuationCustomizationParams = valuationCustomizationParams;
		_stretchBestFitResponseQuoteSensitivity = stretchBestFitResponseQuoteSensitivity;
	}

	@Override public boolean setStretch (
		final MultiSegmentSequence multiSegmentSequence)
	{
		if (null == multiSegmentSequence || !(multiSegmentSequence instanceof CurveStretch)) {
			return false;
		}

		LatentStateResponseModel[] latentStateResponseModelArray =
			((CurveStretch) multiSegmentSequence).segments();

		return null != latentStateResponseModelArray &&
			latentStateResponseModelArray.length == _latentStateStretchSpecification.segmentSpec().length;
	}

	@Override public org.drip.spline.stretch.BoundarySettings getCalibrationBoundaryCondition()
	{
		return _boundarySettings;
	}

	@Override public boolean calibStartingSegment (
		final double leftSlope)
	{
		if (null == _curveStretch || !_curveStretch.clearBuiltRange()) {
			return false;
		}

		CalibratableComponent calibratableComponent =
			_latentStateStretchSpecification.segmentSpec()[0].component();

		if (null == calibratableComponent) {
			return false;
		}

		LatentStateResponseModel[] latentStateResponseModelArray = _curveStretch.segments();

		if (null == latentStateResponseModelArray || 0 == latentStateResponseModelArray.length) {
			return false;
		}

		PredictorResponseWeightConstraint predictorResponseWeightConstraint =
			calibratableComponent.calibPRWC (
			_valuationParams,
			_creditPricerParams,
			_curveSurfaceQuoteContainer,
			_valuationCustomizationParams,
			_latentStateStretchSpecification.segmentSpec()[0].manifestMeasures()
		);

		double segmentRight = latentStateResponseModelArray[0].right();

		if (null == predictorResponseWeightConstraint ||
			!generateSegmentConstraintSet (segmentRight, predictorResponseWeightConstraint)) {
			return false;
		}

		SegmentResponseValueConstraint leadingSegmentResponseValueConstraint =
			SegmentResponseValueConstraint.FromPredictorResponsePair (
				_valuationParams.valueDate(),
				_epochResponse
			);

		return null != leadingSegmentResponseValueConstraint &&
			latentStateResponseModelArray[0].calibrate (
				leadingSegmentResponseValueConstraint,
				leftSlope,
				_responseValueSensitivityConstraintMap.get (segmentRight).base(),
				null == _stretchBestFitResponse ? null : _stretchBestFitResponse.sizeToSegment (
					latentStateResponseModelArray[0]
				)
			) && _curveStretch.markSegmentBuilt (0, predictorResponseWeightConstraint.mergeLabelSet());
	}

	@Override public boolean calibSegmentSequence (
		final int startingSegmentIndex)
	{
		LatentStateResponseModel[] latentStateResponseModelArray = _curveStretch.segments();

		LatentStateSegmentSpec[] latentStateSegmentSpecificationArray =
			_latentStateStretchSpecification.segmentSpec();

		int iNumSegment = latentStateResponseModelArray.length;

		if (null == latentStateSegmentSpecificationArray ||
			latentStateSegmentSpecificationArray.length != iNumSegment) {
			return false;
		}

		for (int segmentIndex = startingSegmentIndex; segmentIndex < iNumSegment; ++segmentIndex) {
			CalibratableComponent calibratableComponent =
				latentStateSegmentSpecificationArray[segmentIndex].component();

			if (null == calibratableComponent) {
				return false;
			}

			PredictorResponseWeightConstraint predictorResponseWeightConstraint =
				calibratableComponent.calibPRWC (
					_valuationParams,
					_creditPricerParams,
					_curveSurfaceQuoteContainer,
					_valuationCustomizationParams,
					latentStateSegmentSpecificationArray[segmentIndex].manifestMeasures()
				);

			double segmentRight = latentStateResponseModelArray[segmentIndex].right();

			if (null == predictorResponseWeightConstraint ||
				!generateSegmentConstraintSet (segmentRight, predictorResponseWeightConstraint)) {
				return false;
			}

			if (!latentStateResponseModelArray[startingSegmentIndex].calibrate (
				0 == startingSegmentIndex ? null : latentStateResponseModelArray[startingSegmentIndex - 1],
				_responseValueSensitivityConstraintMap.get (segmentRight).base(),
				null == _stretchBestFitResponse ? null : _stretchBestFitResponse.sizeToSegment (
					latentStateResponseModelArray[startingSegmentIndex]
				)) || !_curveStretch.markSegmentBuilt (
					startingSegmentIndex,
					predictorResponseWeightConstraint.mergeLabelSet()
				)
			) {
				return false;
			}
		}

		return true;
	}

	@Override public boolean manifestMeasureSensitivity (
		final double dblLeftSlopeSensitivity)
	{
		org.drip.spline.segment.LatentStateResponseModel[] aLSRM = _curveStretch.segments();

		int iNumSegment = aLSRM.length;

		for (int iSegment = 0; iSegment < iNumSegment; ++iSegment) {
			double dblSegmentRight = aLSRM[iSegment].right();

			org.drip.spline.params.ResponseValueSensitivityConstraint rvsc = _responseValueSensitivityConstraintMap.get (dblSegmentRight);

			java.util.Set<java.lang.String> setstrManifestMeasures = rvsc.manifestMeasures();

			if (null == setstrManifestMeasures || 0 == setstrManifestMeasures.size()) return false;

			for (java.lang.String strManifestMeasure : setstrManifestMeasures) {
				if (!aLSRM[iSegment].setPreceedingManifestSensitivityControl (strManifestMeasure, getPreceedingManifestSensitivityControl
					(strManifestMeasure)))
					return false;

				if (0 == iSegment) {
					if (!aLSRM[0].manifestMeasureSensitivity (strManifestMeasure,
						org.drip.spline.params.SegmentResponseValueConstraint.FromPredictorResponsePair
							(_valuationParams.valueDate(), _epochResponse), rvsc.base(),
								dblLeftSlopeSensitivity,
									org.drip.spline.params.SegmentResponseValueConstraint.FromPredictorResponsePair
						(_valuationParams.valueDate(), 0.), rvsc.manifestMeasureSensitivity (strManifestMeasure),
							null == _stretchBestFitResponseQuoteSensitivity ? null : _stretchBestFitResponseQuoteSensitivity.sizeToSegment
								(aLSRM[0])))
						return false;
				} else {
					if (!aLSRM[iSegment].manifestMeasureSensitivity (aLSRM[iSegment - 1], strManifestMeasure,
						rvsc.base(), rvsc.manifestMeasureSensitivity (strManifestMeasure), null ==
							_stretchBestFitResponseQuoteSensitivity ? null : _stretchBestFitResponseQuoteSensitivity.sizeToSegment
								(aLSRM[iSegment])))
						return false;
				}
			}
		}

		return true;
	}
}
