
package org.drip.state.nonlinear;

import org.drip.analytics.definition.ExplicitBootCurve;
import org.drip.function.definition.R1ToR1;
import org.drip.function.r1tor1solver.FixedPointFinderBrent;
import org.drip.function.r1tor1solver.FixedPointFinderOutput;
import org.drip.function.r1tor1solver.FixedPointFinderZheng;
import org.drip.function.r1tor1solver.InitializationHeuristics;
import org.drip.numerical.common.NumberUtil;
import org.drip.param.creator.MarketParamsBuilder;
import org.drip.param.definition.CalibrationParams;
import org.drip.param.market.CurveSurfaceQuoteContainer;
import org.drip.param.market.LatentStateFixingsContainer;
import org.drip.param.pricer.CreditPricerParams;
import org.drip.param.valuation.ValuationCustomizationParams;
import org.drip.param.valuation.ValuationParams;
import org.drip.product.definition.Component;
import org.drip.state.credit.ExplicitBootCreditCurve;
import org.drip.state.discount.ExplicitBootDiscountCurve;
import org.drip.state.discount.MergedDiscountForwardCurve;
import org.drip.state.forward.ForwardCurve;
import org.drip.state.govvie.GovvieCurve;
import org.drip.state.volatility.ExplicitBootVolatilityCurve;

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
 * Copyright (C) 2012 Lakshmi Krishnamurthy
 * Copyright (C) 2011 Lakshmi Krishnamurthy
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
 * <i>NonlinearCurveBuilder</i> calibrates the discount and credit/hazard curves from the components and
 * their quotes. NonlinearCurveCalibrator employs a set of techniques for achieving this calibration.
 *
 *  <br><br>
 *  <ul>
 *  	<li>
 * 			It bootstraps the nodes in sequence to calibrate the curve.
 *  	</li>
 *  	<li>
 * 			In conjunction with splining estimation techniques, it may also be used to perform dual sweep
 * 				calibration. The inner sweep achieves the calibration of the segment spline parameters, while
 * 				the outer sweep calibrates iteratively for the targeted boundary conditions.
 *  	</li>
 *  	<li>
 * 			It may also be used to custom calibrate a single Interest Rate/Hazard Rate/Volatility Node from
 * 				the corresponding Component.
 *  	</li>
 *  </ul>
 * 
 * CurveCalibrator bootstraps/cooks both discount curves and credit curves.
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

public class NonlinearCurveBuilder
{

	private static final boolean SetNode (
		final ExplicitBootCurve explicitBootCurve,
		final int nodeIndex,
		final boolean flat,
		final double value)
	{
		return flat ? explicitBootCurve.setFlatValue (value) :
			explicitBootCurve.setNodeValue (nodeIndex, value);
	}

	static class CreditCurveCalibrator extends R1ToR1
	{
		private boolean _flat = false;
		private int _curveSegmentIndex = -1;
		private GovvieCurve _govvieCurve = null;
		private String _calibrationMeasure = "";
		private double _calibrationValue = Double.NaN;
		private Component _calibrationComponent = null;
		private ValuationParams _valuationParams = null;
		private CalibrationParams _calibrationParams = null;
		private CreditPricerParams _creditPricerParams = null;
		private ExplicitBootCreditCurve _explicitBootCreditCurve = null;
		private MergedDiscountForwardCurve _mergedDiscountForwardCurve = null;
		private LatentStateFixingsContainer _latentStateFixingsContainer = null;
		private ValuationCustomizationParams _valuationCustomizationParams = null;

		CreditCurveCalibrator (
			final ValuationParams valuationParams,
			final Component calibrationComponent,
			final double calibrationValue,
			final String calibrationMeasure,
			final boolean flat,
			final int curveSegmentIndex,
			final ExplicitBootCreditCurve explicitBootCreditCurve,
			final MergedDiscountForwardCurve mergedDiscountForwardCurve,
			final GovvieCurve govvieCurve,
			final CreditPricerParams creditPricerParams,
			final LatentStateFixingsContainer latentStateFixingsContainer,
			final ValuationCustomizationParams valuationCustomizationParams,
			final CalibrationParams calibrationParams)
			throws Exception
		{
			super (null);

			_flat = flat;
			_govvieCurve = govvieCurve;
			_valuationParams = valuationParams;
			_calibrationValue = calibrationValue;
			_curveSegmentIndex = curveSegmentIndex;
			_calibrationMeasure = calibrationMeasure;
			_calibrationComponent = calibrationComponent;
			_explicitBootCreditCurve = explicitBootCreditCurve;
			_mergedDiscountForwardCurve = mergedDiscountForwardCurve;
			_latentStateFixingsContainer = latentStateFixingsContainer;
			_valuationCustomizationParams = valuationCustomizationParams;

			if (null == (_calibrationParams = calibrationParams)) {
				_calibrationParams = new CalibrationParams (_calibrationMeasure, 0, null);
			}

			_creditPricerParams = new CreditPricerParams (
				creditPricerParams.unitSize(),
				_calibrationParams,
				creditPricerParams.survivalToPayDate(),
				creditPricerParams.discretizationScheme()
			);
		}

		@Override public double evaluate (
			final double rate)
			throws Exception
		{
			if (!SetNode (_explicitBootCreditCurve, _curveSegmentIndex, _flat, rate)) {
				throw new Exception (
					"NonlinearCurveBuilder::CreditCurveCalibrator::evaluate => Cannot set Rate = " + rate +
						" for node " + _curveSegmentIndex
				);
			}

			return _calibrationValue - _calibrationComponent.measureValue (
				_valuationParams,
				_creditPricerParams,
				MarketParamsBuilder.Create (
					_mergedDiscountForwardCurve,
					_govvieCurve,
					_explicitBootCreditCurve,
					null,
					null,
					null,
					_latentStateFixingsContainer
				),
				_valuationCustomizationParams,
				_calibrationMeasure
			);
		}
	}

	/**
	 * Calibrate a single Hazard Rate Node from the corresponding Component
	 * 
	 * @param valuationParams Calibration Valuation Parameters
	 * @param calibrationComponent The Calibration Component
	 * @param calibrationValue The Value to be Calibrated to
	 * @param calibrationMeasure The Calibration Measure
	 * @param flat TRUE - Calibrate a Flat Curve across all Tenors
	 * @param curveSegmentIndex The Curve Segment Index
	 * @param explicitBootCreditCurve The Credit Curve to be calibrated
	 * @param mergedDiscountForwardCurve The discount curve to be bootstrapped
	 * @param govvieCurve The Govvie Curve
	 * @param creditPricerParams Input Pricer Parameters
	 * @param latentStateFixingsContainer The Latent State Fixings Container
	 * @param valuationCustomizationParams Valuation Customization Parameters
	 * @param calibrationParams The Calibration Parameters
	 * 
	 * @return The successfully calibrated State Hazard Rate Point
	 */

	public static final boolean CreditCurve (
		final ValuationParams valuationParams,
		final Component calibrationComponent,
		final double calibrationValue,
		final String calibrationMeasure,
		final boolean flat,
		final int curveSegmentIndex,
		final ExplicitBootCreditCurve explicitBootCreditCurve,
		final MergedDiscountForwardCurve mergedDiscountForwardCurve,
		final GovvieCurve govvieCurve,
		final CreditPricerParams creditPricerParams,
		final LatentStateFixingsContainer latentStateFixingsContainer,
		final ValuationCustomizationParams valuationCustomizationParams,
		final CalibrationParams calibrationParams)
	{
		try {
			FixedPointFinderOutput fixedPointFinderOutput = new FixedPointFinderZheng (
				0.,
				new CreditCurveCalibrator (
					valuationParams,
					calibrationComponent,
					calibrationValue,
					calibrationMeasure,
					flat,
					curveSegmentIndex,
					explicitBootCreditCurve,
					mergedDiscountForwardCurve,
					govvieCurve,
					creditPricerParams,
					latentStateFixingsContainer,
					valuationCustomizationParams,
					calibrationParams
				),
				true
			).findRoot();

			return null != fixedPointFinderOutput && fixedPointFinderOutput.containsRoot();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Calibrate a Single Discount Curve Segment from the corresponding Component
	 * 
	 * @param valuationParams Calibration Valuation Parameters
	 * @param component The Calibration Component
	 * @param calibrationValue The Value to be Calibrated to
	 * @param calibrationMeasure The Calibration Measure
	 * @param flat TRUE - Calibrate a Flat Curve across all Tenors
	 * @param curveSegmentIndex The Curve Segment Index
	 * @param explicitBootDiscountCurve The discount curve to be bootstrapped
	 * @param govvieCurve The Govvie Curve
	 * @param latentStateFixingsContainer Latent State Fixings Container
	 * @param valuationCustomizationParams Valuation Customization Parameters
	 * 
	 * @return The successfully calibrated State IR Point
	 * 
	 * @throws Exception Thrown if the Bootstrapping is unsuccessful
	 */

	public static final double DiscountCurveNode (
		final ValuationParams valuationParams,
		final Component component,
		final double calibrationValue,
		final String calibrationMeasure,
		final boolean flat,
		final int curveSegmentIndex,
		final ExplicitBootDiscountCurve explicitBootDiscountCurve,
		final GovvieCurve govvieCurve,
		final LatentStateFixingsContainer latentStateFixingsContainer,
		final ValuationCustomizationParams valuationCustomizationParams)
		throws Exception
	{
		if (null == component) {
			throw new Exception ("NonlinearCurveBuilder::DiscountCurveNode => Invalid inputs!");
		}

		R1ToR1 nodeObjectiveFunction = new R1ToR1 (null) {
			@Override public double evaluate (
				final double value)
				throws Exception
			{
				if (!SetNode (explicitBootDiscountCurve, curveSegmentIndex, flat, value)) {
					throw new Exception (
						"NonlinearCurveBuilder::DiscountCurveNode => Cannot set Value = " + value +
							" for node " + curveSegmentIndex
					);
				}

				return calibrationValue - component.measureValue (
					valuationParams,
					new CreditPricerParams (1, new CalibrationParams (calibrationMeasure, 0, null), true, 0),
					MarketParamsBuilder.Create (
						explicitBootDiscountCurve,
						govvieCurve,
						null,
						null,
						null,
						null,
						latentStateFixingsContainer
					),
					valuationCustomizationParams,
					calibrationMeasure
				);
			}
		};

		FixedPointFinderOutput fixedPointFinderOutput = new FixedPointFinderBrent (
			0.,
			nodeObjectiveFunction,
			true
		).findRoot();

		if (null == fixedPointFinderOutput || !fixedPointFinderOutput.containsRoot()) {
			throw new Exception (
				"NonlinearCurveBuilder::DiscountCurveNode => Cannot calibrate IR segment for node #" +
					curveSegmentIndex
			);
		}

		return fixedPointFinderOutput.getRoot();
	}

	/**
	 * Boot-strap a Discount Curve from the set of calibration components
	 * 
	 * @param valuationParams Calibration Valuation Parameters
	 * @param calibrationComponentArray Array of the calibration components
	 * @param calibrationValueArray Array of Calibration Values
	 * @param calibrationMeasureArray Array of Calibration Measures
	 * @param bump Amount to bump the Quotes by
	 * @param flat TRUE - Calibrate a Flat Curve across all Tenors
	 * @param explicitBootDiscountCurve The discount curve to be bootstrapped
	 * @param govvieCurve The Govvie Curve
	 * @param latentStateFixingsContainer Latent State Fixings Container
	 * @param valuationCustomizationParams Valuation Customization Parameters
	 * 
	 * @return TRUE - Bootstrapping was successful
	 */

	public static final boolean DiscountCurve (
		final ValuationParams valuationParams,
		final Component[] calibrationComponentArray,
		final double[] calibrationValueArray,
		final String[] calibrationMeasureArray,
		final double bump,
		final boolean flat,
		final ExplicitBootDiscountCurve explicitBootDiscountCurve,
		final GovvieCurve govvieCurve,
		final LatentStateFixingsContainer latentStateFixingsContainer,
		final ValuationCustomizationParams valuationCustomizationParams)
	{
		if (null == calibrationValueArray || null == calibrationComponentArray ||
			null == calibrationMeasureArray || !NumberUtil.IsValid (bump)) {
			return false;
		}

		int calibrationComponentCount = calibrationComponentArray.length;

		if (0 == calibrationComponentCount || calibrationValueArray.length != calibrationComponentCount ||
			calibrationMeasureArray.length != calibrationComponentCount) {
			return false;
		}

		for (int i = 0; i < calibrationComponentCount; ++i) {
			try {
				if (!NumberUtil.IsValid (
					DiscountCurveNode (
						valuationParams,
						calibrationComponentArray[i],
						calibrationValueArray[i] + bump,
						calibrationMeasureArray[i],
						flat,
						i,
						explicitBootDiscountCurve,
						govvieCurve,
						latentStateFixingsContainer,
						valuationCustomizationParams
					)
				)) {
					return false;
				}
			} catch (Exception e) {
				e.printStackTrace();

				return false;
			}
		}

		return true;
	}

	/**
	 * Calibrate a Single Volatility Curve Segment from the corresponding Component
	 * 
	 * @param valuationParams Calibration Valuation Parameters
	 * @param calibrationComponent The Calibration Component
	 * @param calibrationValue The Value to be Calibrated to
	 * @param calibrationMeasure The Calibration Measure
	 * @param flat TRUE - Calibrate a Flat Curve across all Tenors
	 * @param curveSegmentIndex The Curve Segment Index
	 * @param explicitBootVolatilityCurve The Volatility Curve to be bootstrapped
	 * @param mergedDiscountForwardCurve The Discount Curve
	 * @param forwardCurve The Forward Curve
	 * @param latentStateFixingsContainer Latent State Fixings Container
	 * @param valuationCustomizationParams Valuation Customization Parameters
	 * 
	 * @return The successfully calibrated State IR Point
	 * 
	 * @throws Exception Thrown if the Bootstrapping is unsuccessful
	 */

	public static final double VolatilityCurveNode (
		final ValuationParams valuationParams,
		final Component calibrationComponent,
		final double calibrationValue,
		final String calibrationMeasure,
		final boolean flat,
		final int curveSegmentIndex,
		final ExplicitBootVolatilityCurve explicitBootVolatilityCurve,
		final MergedDiscountForwardCurve mergedDiscountForwardCurve,
		final ForwardCurve forwardCurve,
		final LatentStateFixingsContainer latentStateFixingsContainer,
		final ValuationCustomizationParams valuationCustomizationParams)
		throws Exception
	{
		if (null == calibrationComponent) {
			throw new Exception ("NonlinearCurveBuilder::VolatilityCurveNode => Invalid inputs!");
		}

		R1ToR1 r1r1VolatilityMetric = new R1ToR1 (null) {
			@Override public double evaluate (
				final double value)
				throws Exception
			{
				if (!SetNode (explicitBootVolatilityCurve, curveSegmentIndex, flat, value)) {
					throw new Exception (
						"NonlinearCurveBuilder::VolatilityCurveNode => Cannot set Value = " + value +
							" for node " + curveSegmentIndex
						);
				}

				CurveSurfaceQuoteContainer curveSurfaceQuoteContainer = MarketParamsBuilder.Create (
					mergedDiscountForwardCurve,
					null,
					null,
					null,
					null,
					null,
					latentStateFixingsContainer
				);

				if (null == curveSurfaceQuoteContainer ||
					!curveSurfaceQuoteContainer.setForwardState (forwardCurve) ||
					!curveSurfaceQuoteContainer.setForwardVolatility (explicitBootVolatilityCurve)) {
					throw new Exception (
						"NonlinearCurveBuilder::VolatilityCurveNode => Cannot set Value = " + value +
							" for node " + curveSegmentIndex
						);
				}

				return calibrationValue - calibrationComponent.measureValue (
					valuationParams,
					new CreditPricerParams (1, new CalibrationParams (calibrationMeasure, 0, null), true, 0),
					curveSurfaceQuoteContainer,
					valuationCustomizationParams,
					calibrationMeasure
				);
			}
		};

		FixedPointFinderOutput fixedPointFinderOutput = (
			new FixedPointFinderBrent (0., r1r1VolatilityMetric, true)
		).findRoot (InitializationHeuristics.FromHardSearchEdges (0.00001, 5.));

		if (null == fixedPointFinderOutput || !fixedPointFinderOutput.containsRoot()) {
			throw new Exception (
				"NonlinearCurveBuilder::VolatilityCurveNode => Cannot calibrate segment for node #" +
					curveSegmentIndex + " => " + calibrationValue
				);
		}

		return fixedPointFinderOutput.getRoot();
	}

	/**
	 * Boot-strap a Volatility Curve from the set of calibration components
	 * 
	 * @param valuationParams Calibration Valuation Parameters
	 * @param calibrationComponentArray Array of the calibration components
	 * @param calibrationValueArray Array of Calibration Values
	 * @param calibrationMeasureArray Array of Calibration Measures
	 * @param bump Amount to bump the Quotes by
	 * @param flat TRUE - Calibrate a Flat Curve across all Tenors
	 * @param explicitBootVolatilityCurve The Volatility Curve to be bootstrapped
	 * @param mergedDiscountForwardCurve The Discount Curve
	 * @param forwardCurve The Forward Curve
	 * @param latentStateFixingsContainer Latent State Fixings Container
	 * @param valuationCustomizationParams Valuation Customization Parameters
	 * 
	 * @return TRUE - Bootstrapping was successful
	 */

	public static final boolean VolatilityCurve (
		final ValuationParams valuationParams,
		final Component[] calibrationComponentArray,
		final double[] calibrationValueArray,
		final String[] calibrationMeasureArray,
		final double bump,
		final boolean flat,
		final ExplicitBootVolatilityCurve explicitBootVolatilityCurve,
		final MergedDiscountForwardCurve mergedDiscountForwardCurve,
		final ForwardCurve forwardCurve,
		final LatentStateFixingsContainer latentStateFixingsContainer,
		final ValuationCustomizationParams valuationCustomizationParams)
	{
		if (null == calibrationValueArray || null == calibrationComponentArray ||
			null == calibrationMeasureArray || !NumberUtil.IsValid (bump)) {
			return false;
		}

		int calibrationComponentCount = calibrationComponentArray.length;

		if (0 == calibrationComponentCount || calibrationValueArray.length != calibrationComponentCount ||
			calibrationMeasureArray.length != calibrationComponentCount) {
			return false;
		}

		for (int i = 0; i < calibrationComponentCount; ++i) {
			try {
				if (!NumberUtil.IsValid (
					VolatilityCurveNode (
						valuationParams,
						calibrationComponentArray[i],
						calibrationValueArray[i] + bump,
						calibrationMeasureArray[i],
						flat,
						i,
						explicitBootVolatilityCurve,
						mergedDiscountForwardCurve,
						forwardCurve,
						latentStateFixingsContainer,
						valuationCustomizationParams
					)
				)) {
					return false;
				}
			} catch (Exception e) {
				e.printStackTrace();

				return false;
			}
		}

		return true;
	}
}
