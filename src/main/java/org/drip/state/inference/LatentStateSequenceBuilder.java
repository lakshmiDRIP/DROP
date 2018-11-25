
package org.drip.state.inference;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
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
 * <i>LatentStateSequenceBuilder</i> holds the logic behind building the bootstrap segments contained in the
 * given Stretch.
 *
 *  <br><br>
 *  <ul>
 *		<li><b>Project</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state">State</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/inference">Inference</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/FixedIncome">Fixed Income Analytics Library</a></li>
 *  </ul>
 * <br><br>
 * 
 * It extends SegmentSequenceBuilder by implementing/customizing the calibration of the starting as well as
 *  the subsequent segments.
 *
 * @author Lakshmi Krishnamurthy
 */

public class LatentStateSequenceBuilder implements org.drip.spline.stretch.SegmentSequenceBuilder {
	private org.drip.spline.grid.Span _span = null;
	private double _dblEpochResponse = java.lang.Double.NaN;
	private org.drip.spline.stretch.BoundarySettings _bs = null;
	private org.drip.state.estimator.CurveStretch _stretch = null;
	private org.drip.param.pricer.CreditPricerParams _pricerParams = null;
	private org.drip.param.market.CurveSurfaceQuoteContainer _csqs = null;
	private org.drip.param.valuation.ValuationParams _valParams = null;
	private org.drip.spline.params.StretchBestFitResponse _sbfr = null;
	private org.drip.param.valuation.ValuationCustomizationParams _vcp = null;
	private org.drip.state.inference.LatentStateStretchSpec _stretchSpec = null;
	private org.drip.spline.params.StretchBestFitResponse _sbfrQuoteSensitivity = null;
	private
		org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.spline.params.PreceedingManifestSensitivityControl>
			_mapPMSC = null;

	private java.util.Map<java.lang.Double, org.drip.spline.params.ResponseValueSensitivityConstraint>
		_mapRVSC = new
			java.util.HashMap<java.lang.Double, org.drip.spline.params.ResponseValueSensitivityConstraint>();

	private org.drip.spline.params.PreceedingManifestSensitivityControl getPMSC (
		final java.lang.String strManifestMeasure)
	{
		return _mapPMSC.containsKey (strManifestMeasure) ? _mapPMSC.get (strManifestMeasure) : null;
	}

	private org.drip.spline.params.SegmentResponseValueConstraint segmentCalibResponseConstraint (
		final org.drip.state.estimator.PredictorResponseWeightConstraint prwc)
	{
		java.util.TreeMap<java.lang.Double, java.lang.Double> mapPredictorLSQMLoading =
			prwc.getPredictorResponseWeight();

		if (null == mapPredictorLSQMLoading || 0 == mapPredictorLSQMLoading.size()) return null;

		java.util.Set<java.util.Map.Entry<java.lang.Double, java.lang.Double>> esPredictorLSQMLoading =
			mapPredictorLSQMLoading.entrySet();

		if (null == esPredictorLSQMLoading || 0 == esPredictorLSQMLoading.size()) return null;

		double dblConstraint = 0.;

		java.util.List<java.lang.Double> lsPredictor = new java.util.ArrayList<java.lang.Double>();

		java.util.List<java.lang.Double> lsResponseLSQMLoading = new java.util.ArrayList<java.lang.Double>();

		for (java.util.Map.Entry<java.lang.Double, java.lang.Double> me : esPredictorLSQMLoading) {
			if (null == me) return null;

			double dblPredictorDate = me.getKey();

			try {
				if (null != _span && _span.in (dblPredictorDate))
					dblConstraint -= _span.calcResponseValue (dblPredictorDate) * me.getValue();
				else if (_stretch.inBuiltRange (dblPredictorDate))
					dblConstraint -= _stretch.responseValue (dblPredictorDate) * me.getValue();
				else {
					lsPredictor.add (dblPredictorDate);

					lsResponseLSQMLoading.add (me.getValue());
				}
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		int iSize = lsPredictor.size();

		double[] adblPredictor = new double[iSize];
		double[] adblResponseLSQMLoading = new double[iSize];

		for (int i = 0; i < iSize; ++i) {
			adblPredictor[i] = lsPredictor.get (i);

			adblResponseLSQMLoading[i] = lsResponseLSQMLoading.get (i);
		}

		try {
			return new org.drip.spline.params.SegmentResponseValueConstraint (adblPredictor,
				adblResponseLSQMLoading, (prwc.getValue()) + dblConstraint);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	private org.drip.spline.params.SegmentResponseValueConstraint segmentSensResponseConstraint (
		final org.drip.state.estimator.PredictorResponseWeightConstraint prwc,
		final java.lang.String strManifestMeasure)
	{
		java.util.TreeMap<java.lang.Double, java.lang.Double> mapPredictorSensLoading =
			prwc.getDResponseWeightDManifestMeasure (strManifestMeasure);

		if (null == mapPredictorSensLoading || 0 == mapPredictorSensLoading.size()) return null;

		java.util.Set<java.util.Map.Entry<java.lang.Double, java.lang.Double>> esPredictorSensLoading =
			mapPredictorSensLoading.entrySet();

		if (null == esPredictorSensLoading || 0 == esPredictorSensLoading.size()) return null;

		double dblSensLoadingConstraint = 0.;

		java.util.List<java.lang.Double> lsPredictor = new java.util.ArrayList<java.lang.Double>();

		java.util.List<java.lang.Double> lsSensLoading = new java.util.ArrayList<java.lang.Double>();

		for (java.util.Map.Entry<java.lang.Double, java.lang.Double> me : esPredictorSensLoading) {
			if (null == me) return null;

			double dblPredictorDate = me.getKey();

			try {
				if (null != _span && _span.in (dblPredictorDate))
					dblSensLoadingConstraint -= _span.calcResponseValue (dblPredictorDate) * me.getValue();
				else if (_stretch.inBuiltRange (dblPredictorDate))
					dblSensLoadingConstraint -= _stretch.responseValue (dblPredictorDate) * me.getValue();
				else {
					lsPredictor.add (dblPredictorDate);

					lsSensLoading.add (me.getValue());
				}
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		int iSize = lsPredictor.size();

		double[] adblPredictor = new double[iSize];
		double[] adblSensLoading = new double[iSize];

		for (int i = 0; i < iSize; ++i) {
			adblPredictor[i] = lsPredictor.get (i);

			adblSensLoading[i] = lsSensLoading.get (i);
		}

		try {
			return new org.drip.spline.params.SegmentResponseValueConstraint (adblPredictor, adblSensLoading,
				prwc.getDValueDManifestMeasure (strManifestMeasure) + dblSensLoadingConstraint);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	private boolean generateSegmentConstraintSet (
		final double dblSegmentRight,
		final org.drip.state.estimator.PredictorResponseWeightConstraint prwc)
	{
		org.drip.spline.params.SegmentResponseValueConstraint srvcBase = segmentCalibResponseConstraint
			(prwc);

		if (null == srvcBase) return false;

		org.drip.spline.params.ResponseValueSensitivityConstraint rvsc = null;

		try {
			rvsc = new org.drip.spline.params.ResponseValueSensitivityConstraint (srvcBase);
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return false;
		}

		java.util.Set<java.lang.String> setstrSensitivity = prwc.sensitivityKeys();

		if (null == setstrSensitivity || 0 == setstrSensitivity.size()) {
			_mapRVSC.put (dblSegmentRight, rvsc);

			return true;
		}

		for (java.lang.String strManifestMeasure : setstrSensitivity) {
			org.drip.spline.params.SegmentResponseValueConstraint srvcSensitivity =
				segmentSensResponseConstraint (prwc, strManifestMeasure);

			if (null == srvcSensitivity || !rvsc.addManifestMeasureSensitivity (strManifestMeasure,
				srvcSensitivity))
				continue;
		}

		_mapRVSC.put (dblSegmentRight, rvsc);

		return true;
	}

	/**
	 * LatentStateSequenceBuilder constructor
	 * 
	 * @param dblEpochResponse Segment Sequence Left-most Response Value
	 * @param stretchSpec The Latent State Stretch Specification
	 * @param valParams Valuation Parameter
	 * @param pricerParams Pricer Parameter
	 * @param csqs The Market Parameter Set
	 * @param vcp Valuation Customization Parameters
	 * @param span The Containing Span this Stretch will become a part of
	 * @param sbfr Stretch Fitness Weighted Response
	 * @param mapPMSC Map of Preceeding Manifest Sensitivity Control Parameters
	 * @param sbfrQuoteSensitivity Stretch Fitness Weighted Response Quote Sensitivity
	 * @param bs The Calibration Boundary Condition
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are invalid
	 */

	public LatentStateSequenceBuilder (
		final double dblEpochResponse,
		final org.drip.state.inference.LatentStateStretchSpec stretchSpec,
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final org.drip.spline.grid.Span span,
		final org.drip.spline.params.StretchBestFitResponse sbfr,
		final
			org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.spline.params.PreceedingManifestSensitivityControl>
				mapPMSC,
		final org.drip.spline.params.StretchBestFitResponse sbfrQuoteSensitivity,
		final org.drip.spline.stretch.BoundarySettings bs)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (_dblEpochResponse = dblEpochResponse) || null ==
			(_stretchSpec = stretchSpec) || null == (_valParams = valParams) || null == (_bs = bs) || null ==
				(_mapPMSC = mapPMSC))
			throw new java.lang.Exception ("LatentStateSequenceBuilder ctr: Invalid Inputs");

		_vcp = vcp;
		_csqs = csqs;
		_sbfr = sbfr;
		_span = span;
		_pricerParams = pricerParams;
		_sbfrQuoteSensitivity = sbfrQuoteSensitivity;
	}

	@Override public boolean setStretch (
		final org.drip.spline.stretch.MultiSegmentSequence mss)
	{
		if (null == mss || !(mss instanceof org.drip.state.estimator.CurveStretch)) return false;

		_stretch = (org.drip.state.estimator.CurveStretch) mss;

		org.drip.spline.segment.LatentStateResponseModel[] aLSRM = _stretch.segments();

		if (null == aLSRM || aLSRM.length != _stretchSpec.segmentSpec().length) return false;

		return true;
	}

	@Override public org.drip.spline.stretch.BoundarySettings getCalibrationBoundaryCondition()
	{
		return _bs;
	}

	@Override public boolean calibStartingSegment (
		final double dblLeftSlope)
	{
		if (null == _stretch || !_stretch.clearBuiltRange()) return false;

		org.drip.product.definition.CalibratableComponent cfic =
			_stretchSpec.segmentSpec()[0].component();

		if (null == cfic) return false;

		org.drip.spline.segment.LatentStateResponseModel[] aLSRM = _stretch.segments();

		if (null == aLSRM || 0 == aLSRM.length) return false;

		org.drip.state.estimator.PredictorResponseWeightConstraint prwc = cfic.calibPRWC (_valParams,
			_pricerParams, _csqs, _vcp, _stretchSpec.segmentSpec()[0].manifestMeasures());

		double dblSegmentRight = aLSRM[0].right();

		if (null == prwc || !generateSegmentConstraintSet (dblSegmentRight, prwc)) return false;

		org.drip.spline.params.SegmentResponseValueConstraint rvcLeading =
			org.drip.spline.params.SegmentResponseValueConstraint.FromPredictorResponsePair
				(_valParams.valueDate(), _dblEpochResponse);

		if (null == rvcLeading) return false;

		return aLSRM[0].calibrate (rvcLeading, dblLeftSlope, _mapRVSC.get (dblSegmentRight).base(), null ==
			_sbfr ? null : _sbfr.sizeToSegment (aLSRM[0])) && _stretch.markSegmentBuilt (0,
				prwc.mergeLabelSet());
	}

	@Override public boolean calibSegmentSequence (
		final int iStartingSegment)
	{
		org.drip.spline.segment.LatentStateResponseModel[] aLSRM = _stretch.segments();

		org.drip.state.inference.LatentStateSegmentSpec[] aLSSS = _stretchSpec.segmentSpec();

		int iNumSegment = aLSRM.length;

		if (null == aLSSS || aLSSS.length != iNumSegment) return false;

		for (int iSegment = iStartingSegment; iSegment < iNumSegment; ++iSegment) {
			org.drip.product.definition.CalibratableComponent cfic = aLSSS[iSegment].component();

			if (null == cfic) return false;

			org.drip.state.estimator.PredictorResponseWeightConstraint prwc = cfic.calibPRWC (_valParams,
				_pricerParams, _csqs, _vcp, aLSSS[iSegment].manifestMeasures());

			double dblSegmentRight = aLSRM[iSegment].right();

			if (null == prwc || !generateSegmentConstraintSet (dblSegmentRight, prwc)) return false;

			if (!aLSRM[iSegment].calibrate (0 == iSegment ? null : aLSRM[iSegment - 1], _mapRVSC.get
				(dblSegmentRight).base(), null == _sbfr ? null : _sbfr.sizeToSegment (aLSRM[iSegment])) ||
					!_stretch.markSegmentBuilt (iSegment, prwc.mergeLabelSet()))
				return false;
		}

		return true;
	}

	@Override public boolean manifestMeasureSensitivity (
		final double dblLeftSlopeSensitivity)
	{
		org.drip.spline.segment.LatentStateResponseModel[] aLSRM = _stretch.segments();

		int iNumSegment = aLSRM.length;

		for (int iSegment = 0; iSegment < iNumSegment; ++iSegment) {
			double dblSegmentRight = aLSRM[iSegment].right();

			org.drip.spline.params.ResponseValueSensitivityConstraint rvsc = _mapRVSC.get (dblSegmentRight);

			java.util.Set<java.lang.String> setstrManifestMeasures = rvsc.manifestMeasures();

			if (null == setstrManifestMeasures || 0 == setstrManifestMeasures.size()) return false;

			for (java.lang.String strManifestMeasure : setstrManifestMeasures) {
				if (!aLSRM[iSegment].setPreceedingManifestSensitivityControl (strManifestMeasure, getPMSC
					(strManifestMeasure)))
					return false;

				if (0 == iSegment) {
					if (!aLSRM[0].manifestMeasureSensitivity (strManifestMeasure,
						org.drip.spline.params.SegmentResponseValueConstraint.FromPredictorResponsePair
							(_valParams.valueDate(), _dblEpochResponse), rvsc.base(),
								dblLeftSlopeSensitivity,
									org.drip.spline.params.SegmentResponseValueConstraint.FromPredictorResponsePair
						(_valParams.valueDate(), 0.), rvsc.manifestMeasureSensitivity (strManifestMeasure),
							null == _sbfrQuoteSensitivity ? null : _sbfrQuoteSensitivity.sizeToSegment
								(aLSRM[0])))
						return false;
				} else {
					if (!aLSRM[iSegment].manifestMeasureSensitivity (aLSRM[iSegment - 1], strManifestMeasure,
						rvsc.base(), rvsc.manifestMeasureSensitivity (strManifestMeasure), null ==
							_sbfrQuoteSensitivity ? null : _sbfrQuoteSensitivity.sizeToSegment
								(aLSRM[iSegment])))
						return false;
				}
			}
		}

		return true;
	}
}
