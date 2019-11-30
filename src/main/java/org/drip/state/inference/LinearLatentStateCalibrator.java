
package org.drip.state.inference;

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
 * <i>LinearLatentStateCalibrator</i> calibrates/constructs the Latent State Stretch/Span from the
 * calibration instrument details. The span construction may be customized using specific settings provided
 * in GlobalControlCurveParams.
 *
 *  <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/README.md">Latent State Inference and Creation Utilities</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/inference/README.md">Latent State Stretch Sequence Inference</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class LinearLatentStateCalibrator extends org.drip.state.estimator.GlobalControlCurveParams {

	/**
	 * LinearLatentStateCalibrator constructor
	 * 
	 * @param scbc Segment Builder Control Parameters
	 * @param bs The Calibration Boundary Condition
	 * @param iCalibrationDetail The Calibration Detail
	 * @param sbfr Curve Fitness Weighted Response
	 * @param sbfrSensitivity Curve Fitness Weighted Response Sensitivity
	 * 
	 * @throws java.lang.Exception Thrown if the inputs are invalid
	 */

	public LinearLatentStateCalibrator (
		final org.drip.spline.params.SegmentCustomBuilderControl scbc,
		final org.drip.spline.stretch.BoundarySettings bs,
		final int iCalibrationDetail,
		final org.drip.spline.params.StretchBestFitResponse sbfr,
		final org.drip.spline.params.StretchBestFitResponse sbfrSensitivity)
		throws java.lang.Exception
	{
		super ("", scbc, bs, iCalibrationDetail, sbfr, sbfrSensitivity);
	}

	/**
	 * Calibrate the Span from the Instruments in the Stretches and their Details.
	 * 
	 * @param aStretchSpec The Stretch Sequence constituting the Span
	 * @param dblEpochResponse Segment Sequence Left-most Response Value
	 * @param valParams Valuation Parameter
	 * @param pricerParams Pricer Parameter
	 * @param vcp The Valuation Customization Parameters
	 * @param csqs The Market Parameters Surface and Quote
	 * 
	 * @return Instance of the Latent State Span
	 */

	public org.drip.spline.grid.OverlappingStretchSpan calibrateSpan (
		final org.drip.state.inference.LatentStateStretchSpec[] aStretchSpec,
		final double dblEpochResponse,
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs)
	{
		if (null == aStretchSpec || null == valParams) return null;

		int iNumStretch = aStretchSpec.length;
		org.drip.spline.grid.OverlappingStretchSpan oss = null;

		if (0 == iNumStretch) return null;

		for (org.drip.state.inference.LatentStateStretchSpec stretchSpec : aStretchSpec) {
			if (null == stretchSpec) continue;

			org.drip.state.inference.LatentStateSegmentSpec[] aSegmentSpec = stretchSpec.segmentSpec();

			int iNumCalibComp = aSegmentSpec.length;
			org.drip.state.estimator.CurveStretch cs = null;
			double[] adblPredictorOrdinate = new double[iNumCalibComp + 1];
			org.drip.spline.params.SegmentCustomBuilderControl[] aSCBC = new
				org.drip.spline.params.SegmentCustomBuilderControl[iNumCalibComp];

			for (int i = 0; i <= iNumCalibComp; ++i) {
				adblPredictorOrdinate[i] = 0 == i ? valParams.valueDate() :
					aSegmentSpec[i - 1].component().maturityDate().julian();

				if (i != iNumCalibComp) aSCBC[i] = segmentBuilderControl (stretchSpec.name());
			}

			try {
				cs = new org.drip.state.estimator.CurveStretch (stretchSpec.name(),
					org.drip.spline.stretch.MultiSegmentSequenceBuilder.CreateSegmentSet
						(adblPredictorOrdinate, aSCBC), aSCBC);

				if (!cs.setup (new org.drip.state.inference.LatentStateSequenceBuilder (dblEpochResponse,
					stretchSpec, valParams, pricerParams, csqs, vcp, oss, bestFitWeightedResponse(), new
						org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.spline.params.PreceedingManifestSensitivityControl>(),
					bestFitWeightedResponseSensitivity(), calibrationBoundaryCondition()),
						calibrationDetail())) {
					System.out.println ("\tMSS Setup Failed!");

					return null;
				}
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}

			if (null == oss) {
				try {
					oss = new org.drip.spline.grid.OverlappingStretchSpan (cs);
				} catch (java.lang.Exception e) {
					e.printStackTrace();

					return null;
				}
			} else {
				if (!oss.addStretch (cs)) return null;
			}
		}

		return oss;
	}
}
