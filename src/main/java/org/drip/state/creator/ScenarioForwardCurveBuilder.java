
package org.drip.state.creator;

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
 * Copyright (C) 2013 Lakshmi Krishnamurthy
 * Copyright (C) 2012 Lakshmi Krishnamurthy
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
 * <i>ScenarioForwardCurveBuilder</i> implements the the construction of the scenario Forward curve using the
 * input discount curve instruments, and a wide variety of custom builds. It implements the following
 * functionality:
 *
 *  <br><br>
 *  <ul>
 *  	<li>
 * 			Non-linear Custom Discount Curve
 *  	</li>
 *  	<li>
 * 			Shape Preserving Discount Curve Builds - Standard Cubic Polynomial/Cubic KLK Hyperbolic Tension,
 * 				and other Custom Builds
 *  	</li>
 *  	<li>
 * 			Smoothing Local/Control Custom Build - DC/Forward/Zero Rate LSQM's
 *  	</li>
 *  	<li>
 * 			"Industry Standard Methodologies" - DENSE/DUALDENSE/CUSTOMDENSE and Hagan-West Forward
 * 				Interpolator Schemes
 *  	</li>
 *  </ul>
 *
 *  <br><br>
 *  <ul>
 *		<li><b>Project</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state">State</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/creator">Creator</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/FixedIncome">Fixed Income Analytics Library</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class ScenarioForwardCurveBuilder {

	/**
	 * Build the Shape Preserving Forward Curve using the Custom Parameters
	 * 
	 * @param llsc The Linear Latent State Calibrator Instance
	 * @param aStretchSpec Array of the Latent State Stretches
	 * @param fri The Floating Rate Index
	 * @param valParam Valuation Parameters
	 * @param pricerParam Pricer Parameters
	 * @param csqs Market Parameters
	 * @param quotingParam Quoting Parameters
	 * @param dblEpochResponse The Starting Response Value
	 * 
	 * @return Instance of the Shape Preserving Discount Curve
	 */

	public static final org.drip.state.forward.ForwardCurve ShapePreservingForwardCurve (
		final org.drip.state.inference.LinearLatentStateCalibrator llsc,
		final org.drip.state.inference.LatentStateStretchSpec[] aStretchSpec,
		final org.drip.state.identifier.ForwardLabel fri,
		final org.drip.param.valuation.ValuationParams valParam,
		final org.drip.param.pricer.CreditPricerParams pricerParam,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams quotingParam,
		final double dblEpochResponse)
	{
		if (null == llsc) return null;

		try {
			org.drip.state.forward.ForwardCurve fc = new org.drip.state.curve.BasisSplineForwardRate (fri,
				llsc.calibrateSpan (aStretchSpec, dblEpochResponse, valParam, pricerParam, quotingParam,
					csqs));

			return fc.setCCIS (new org.drip.analytics.input.LatentStateShapePreservingCCIS (llsc,
				aStretchSpec, valParam, pricerParam, quotingParam, csqs)) ? fc : null;
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an instance of the Shape Preserver of the desired basis type, using the specified basis set
	 * 	builder parameters.
	 * 
	 * @param strName Curve Name
	 * @param fri The Floating Rate Index
	 * @param valParams Valuation Parameters
	 * @param pricerParam Pricer Parameters
	 * @param csqs Market Parameters
	 * @param quotingParam Quoting Parameters
	 * @param strBasisType The Basis Type
	 * @param fsbp The Function Set Basis Parameters
	 * @param aCalibComp Array of Calibration Components
	 * @param strManifestMeasure The Calibration Manifest Measure
	 * @param adblQuote Array of Calibration Quotes
	 * @param dblEpochResponse The Stretch Start DF
	 * 
	 * @return Instance of the Shape Preserver of the desired basis type
	 */

	public static final org.drip.state.forward.ForwardCurve ShapePreservingForwardCurve (
		final java.lang.String strName,
		final org.drip.state.identifier.ForwardLabel fri,
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParam,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams quotingParam,
		final java.lang.String strBasisType,
		final org.drip.spline.basis.FunctionSetBuilderParams fsbp,
		final org.drip.product.definition.CalibratableComponent[] aCalibComp,
		final java.lang.String strManifestMeasure,
		final double[] adblQuote,
		final double dblEpochResponse)
	{
		if (null == strName || strName.isEmpty() || null == strBasisType || strBasisType.isEmpty() || null ==
			valParams || null == fsbp || null == strManifestMeasure || strManifestMeasure.isEmpty())
			return null;

		int iNumQuote = null == adblQuote ? 0 : adblQuote.length;
		int iNumComp = null == aCalibComp ? 0 : aCalibComp.length;

		if (0 == iNumComp || iNumComp != iNumQuote) return null;

		try {
			org.drip.state.identifier.ForwardLabel forwardLabel = null;

			if (aCalibComp[0] instanceof org.drip.product.rates.DualStreamComponent)
				forwardLabel = ((org.drip.product.rates.DualStreamComponent)
					aCalibComp[0]).derivedStream().forwardLabel();
			else {
				org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.identifier.ForwardLabel>
					mapForwardLabel = aCalibComp[0].forwardLabel();

				if (null != mapForwardLabel && 0 != mapForwardLabel.size())
					forwardLabel = mapForwardLabel.get ("BASE");
			}

			org.drip.state.representation.LatentStateSpecification[] aLSS = new
				org.drip.state.representation.LatentStateSpecification[] {new
					org.drip.state.representation.LatentStateSpecification
						(org.drip.analytics.definition.LatentStateStatic.LATENT_STATE_FORWARD,
							org.drip.analytics.definition.LatentStateStatic.FORWARD_QM_FORWARD_RATE,
								forwardLabel)};

			org.drip.state.inference.LatentStateSegmentSpec[] aSegmentSpec = new
				org.drip.state.inference.LatentStateSegmentSpec[iNumComp];

			for (int i = 0; i < iNumComp; ++i) {
				org.drip.product.calib.ProductQuoteSet pqs = aCalibComp[i].calibQuoteSet (aLSS);

				if (null == pqs || !pqs.set (strManifestMeasure, adblQuote[i])) return null;

				aSegmentSpec[i] = new org.drip.state.inference.LatentStateSegmentSpec (aCalibComp[i], pqs);
			}

			org.drip.state.inference.LatentStateStretchSpec[] aStretchSpec = new
				org.drip.state.inference.LatentStateStretchSpec[] {new
					org.drip.state.inference.LatentStateStretchSpec (strName, aSegmentSpec)};

			org.drip.state.inference.LinearLatentStateCalibrator llsc = new
				org.drip.state.inference.LinearLatentStateCalibrator (new
					org.drip.spline.params.SegmentCustomBuilderControl (strBasisType, fsbp,
						org.drip.spline.params.SegmentInelasticDesignControl.Create (2, 2), new
							org.drip.spline.params.ResponseScalingShapeControl (true, new
								org.drip.function.r1tor1.QuadraticRationalShapeControl (0.)), null),
									org.drip.spline.stretch.BoundarySettings.FinancialStandard(),
										org.drip.spline.stretch.MultiSegmentSequence.CALIBRATE, null, null);

			return ShapePreservingForwardCurve (llsc, aStretchSpec, fri, valParams, pricerParam, csqs,
				quotingParam, dblEpochResponse);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an Instance of the Flat Forward Rate Forward Curve
	 * 
	 * @param dtStart The Forward Curve Start Date
	 * @param fri The Floating Rate Index
	 * @param dblFlatForwardRate The Flat Forward Rate
	 * 
	 * @return Instance of the Flat Forward Rate Forward Curve
	 */

	public static final org.drip.state.forward.ForwardCurve FlatForwardForwardCurve (
		final org.drip.analytics.date.JulianDate dtStart,
		final org.drip.state.identifier.ForwardLabel fri,
		final double dblFlatForwardRate)
	{
		try {
			return new org.drip.state.nonlinear.FlatForwardForwardCurve (dtStart, fri, dblFlatForwardRate);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
