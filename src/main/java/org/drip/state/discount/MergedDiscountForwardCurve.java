
package org.drip.state.discount;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.drip.analytics.cashflow.CompositePeriod;
import org.drip.analytics.date.JulianDate;
import org.drip.analytics.daycount.Convention;
import org.drip.analytics.definition.LatentStateStatic;
import org.drip.analytics.input.CurveConstructionInputSet;
import org.drip.analytics.support.CaseInsensitiveTreeMap;
import org.drip.analytics.support.CompositePeriodBuilder;
import org.drip.numerical.common.NumberUtil;
import org.drip.numerical.differentiation.WengertJacobian;
import org.drip.param.creator.MarketParamsBuilder;
import org.drip.param.market.CurveSurfaceQuoteContainer;
import org.drip.param.period.ComposableFixedUnitSetting;
import org.drip.param.period.CompositePeriodSetting;
import org.drip.param.period.UnitCouponAccrualSetting;
import org.drip.param.valuation.ValuationParams;
import org.drip.product.definition.CalibratableComponent;
import org.drip.product.rates.Stream;
import org.drip.spline.basis.PolynomialFunctionSetParams;
import org.drip.spline.params.SegmentCustomBuilderControl;
import org.drip.spline.params.SegmentInelasticDesignControl;
import org.drip.spline.stretch.BoundarySettings;
import org.drip.spline.stretch.MultiSegmentSequence;
import org.drip.spline.stretch.MultiSegmentSequenceBuilder;
import org.drip.state.forward.ForwardCurve;
import org.drip.state.forward.ForwardRateEstimator;
import org.drip.state.identifier.ForwardLabel;
import org.drip.state.identifier.FundingLabel;
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
 * <i>MergedDiscountForwardCurve</i> is the Stub for the Merged Discount and Forward Curve Functionality. It
 * extends the both the Curve and the DiscountFactorEstimator instances by implementing their functions, and
 * exposing the following:
 * 
 *  <ul>
 *  	<li>Set the Discount Curve Turns'</li>
 *  	<li>Apply the Turns' DF Adjustment</li>
 *  	<li>Construct the Native Forward Curve for the given Tenor from the Discount Curve</li>
 *  	<li>Compute the Forward Rate between two Dates</li>
 *  	<li>Compute the Forward Rate between two Tenors</li>
 *  	<li>Calculate the implied rate to the given date</li>
 *  	<li>Calculate the implied rate to the given tenor</li>
 *  	<li>Compute the LIBOR between 2 dates given the Day Count</li>
 *  	<li>Compute the LIBOR between 2 dates</li>
 *  	<li>Calculate the LIBOR to the given tenor at the specified Julian Date</li>
 *  	<li>Calculate the DV01 of the Par Swap that Matures at the given date</li>
 *  	<li>Estimate the manifest measure value for the given date</li>
 *  	<li>Proxy the Manifest Measure Value using the Closest Node for the given Date</li>
 *  	<li>Retrieve the Forward Curve that might be implied by the Latent State of this Discount Curve Instance corresponding to the specified Floating Rate Index</li>
 *  	<li>Retrieve the Latent State Quantification Metric</li>
 *  	<li>Retrieve the Manifest Measure Jacobian of the Discount Factor to the given date</li>
 *  	<li>Retrieve the Manifest Measure Jacobian of the Discount Factor to the date implied by the given Tenor</li>
 *  	<li>Calculate the Jacobian of PV at the given date to the Manifest Measure of each component in the calibration set to the DF</li>
 *  	<li>Retrieve the Jacobian of the Forward Rate to the Manifest Measure between the given dates</li>
 *  	<li>Retrieve the Jacobian of the Forward Rate to the Manifest Measure at the given date</li>
 *  	<li>Retrieve the Jacobian for the Zero Rate to the given date</li>
 *  	<li>Convert the inferred Formulation Constraint into a "Truthness" Entity</li>
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
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/discount/README.md">Discount Curve Spline Latent State</a></td></tr>
 *  </table>
 *
 * @author Lakshmi Krishnamurthy
 */

public abstract class MergedDiscountForwardCurve extends DiscountCurve
{
	private static final int NUM_DF_QUADRATURES = 5;

	protected String _currency = "";
	protected int _epochDate = Integer.MIN_VALUE;
	protected TurnListDiscountFactor _turnListDiscountFactor = null;
	protected CurveConstructionInputSet _curveConstructionInputSet = null;

	protected MergedDiscountForwardCurve (
		final int epochDate,
		final String currency,
		final TurnListDiscountFactor turnListDiscountFactor)
		throws Exception
	{
		if (null == (_currency = currency) || _currency.isEmpty() ||
			!NumberUtil.IsValid (_epochDate = epochDate)) {
			throw new Exception ("MergedDiscountForwardCurve ctr: Invalid Inputs");
		}

		_turnListDiscountFactor = turnListDiscountFactor;
	}

	@Override public LatentStateLabel label()
	{
		return FundingLabel.Standard (_currency);
	}

	@Override public String currency()
	{
		return _currency;
	}

	@Override public JulianDate epoch()
	{
		try {
			return new JulianDate (_epochDate);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Set the Discount Curve Turns'
	 * 
	 * @param turnListDiscountFactor Turn List Discount Factor
	 * 
	 * @return TRUE - Valid Turn List Discount Factor Set
	 */

	public boolean setTurns (
		final TurnListDiscountFactor turnListDiscountFactor)
	{
		return null != (_turnListDiscountFactor = turnListDiscountFactor);
	}

	/**
	 * Apply the Turns' DF Adjustment
	 * 
	 * @param startDate Turn Start Date
	 * @param finishDate Turn Finish Date
	 * 
	 * @return Turns' DF Adjustment
	 * 
	 * @throws Exception Thrown if the Inputs are invalid
	 */

	public double turnAdjust (
		final int startDate,
		final int finishDate)
		throws Exception
	{
		return null == _turnListDiscountFactor ? 1. : _turnListDiscountFactor.turnAdjust (
			startDate,
			finishDate
		);
	}

	/**
	 * Apply the Turns' DF Adjustment
	 * 
	 * @param finishDate Turn Finish Date
	 * 
	 * @return Turns' DF Adjustment
	 * 
	 * @throws Exception Thrown if the Inputs are invalid
	 */

	protected double turnAdjust (
		final int finishDate)
		throws Exception
	{
		return turnAdjust (epoch().julian(), finishDate);
	}

	/**
	 * Construct the Native Forward Curve for the given Tenor from the Discount Curve
	 * 
	 * @param tenor The Tenor
	 * 
	 * @return The Tenor-Native Forward Curve
	 */

	public ForwardCurve nativeForwardCurve (
		final java.lang.String tenor)
	{
		if (null == tenor || tenor.isEmpty()) return null;

		try {
			return new ForwardCurve (
				epoch().julian(),
				ForwardLabel.Standard (_currency + "-" + tenor)
			) {
				@Override public double forward (
					final int date)
					throws Exception
				{
					return forward (new JulianDate (date));
				}

				@Override public double forward (
					final JulianDate date)
					throws Exception
				{
					if (null == date) {
						throw new Exception (
							"MergedDiscountForwardCurve::nativeForwardCurve => Invalid Input"
						);
					}

					return libor (date.subtractTenor (tenor).julian(), tenor);
				}

				@Override public double forward (
					final String tenor)
					throws Exception
				{
					if (null == tenor || tenor.isEmpty()) {
						throw new Exception (
							"MergedDiscountForwardCurve::nativeForwardCurve => Invalid Input"
						);
					}

					return forward (epoch().addTenor (tenor));
				}

				@Override public WengertJacobian jackDForwardDManifestMeasure (
					final String manifestMeasure,
					final int date)
				{
					return null;
				}
			};
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override public double df (
		final JulianDate date)
		throws Exception
	{
		if (null == date) {
			throw new Exception ("MergedDiscountForwardCurve::df got null for date");
		}

		return df (date.julian());
	}

	@Override public double df (
		final String tenor)
		throws Exception
	{
		if (null == tenor || tenor.isEmpty()) {
			throw new Exception ("MergedDiscountForwardCurve::df got bad tenor");
		}

		return df (epoch().addTenor (tenor));
	}

	@Override public double effectiveDF (
		final int date1,
		final int date2)
		throws Exception
	{
		if (date1 == date2) {
			return df (date1);
		}

		int quadratureCount = 0;
		double effectiveDiscountFactor = 0.;
		int quadratureWidth = (date2 - date1) / NUM_DF_QUADRATURES;

		if (0 == quadratureWidth) {
			quadratureWidth = 1;
		}

		for (int date = date1; date <= date2; date += quadratureWidth) {
			++quadratureCount;

			effectiveDiscountFactor += (df (date) + df (date + quadratureWidth));
		}

		return effectiveDiscountFactor / (2. * quadratureCount);
	}

	@Override public double effectiveDF (
		final JulianDate date1,
		final JulianDate date2)
		throws java.lang.Exception
	{
		if (null == date1 || null == date2) {
			throw new Exception ("MergedDiscountForwardCurve::effectiveDF => Got null for date");
		}

		return effectiveDF (date1.julian(), date2.julian());
	}

	@Override public double effectiveDF (
		final String tenor1,
		final String tenor2)
		throws Exception
	{
		if (null == tenor1 || tenor1.isEmpty() || null == tenor2 || tenor2.isEmpty()) {
			throw new Exception ("MergedDiscountForwardCurve::effectiveDF => Got bad tenor");
		}

		JulianDate startDate = epoch();

		return effectiveDF (startDate.addTenor (tenor1), startDate.addTenor (tenor2));
	}

	/**
	 * Compute the Forward Rate between two Dates
	 * 
	 * @param date1 First Date
	 * @param date2 Second Date
	 * 
	 * @return The Forward Rate
	 * 
	 * @throws Exception Thrown if the Forward Rate cannot be calculated
	 */

	public abstract double forward (
		final int date1,
		final int date2)
		throws Exception;

	/**
	 * Compute the Forward Rate between two Tenors
	 * 
	 * @param tenor1 Tenor Start
	 * @param tenor2 Tenor End
	 * 
	 * @return The Forward Rate
	 * 
	 * @throws Exception Thrown if the Forward Rate cannot be calculated
	 */

	public double forward (
		final String tenor1,
		final String tenor2)
		throws Exception
	{
		if (null == tenor1 || tenor1.isEmpty() || null == tenor2 || tenor2.isEmpty()) {
			throw new Exception ("MergedDiscountForwardCurve::forward => Invalid Date");
		}

		JulianDate startDate = epoch();

		return forward (startDate.addTenor (tenor1).julian(), startDate.addTenor (tenor2).julian());
	}

	/**
	 * Calculate the implied rate to the given date
	 * 
	 * @param date Date
	 * 
	 * @return Implied rate
	 * 
	 * @throws Exception Thrown if the discount factor cannot be calculated
	 */

	public abstract double zero (
		final int date)
		throws Exception;

	/**
	 * Calculate the implied rate to the given tenor
	 * 
	 * @param tenor Tenor
	 * 
	 * @return Implied rate
	 * 
	 * @throws Exception Thrown if the discount factor cannot be calculated
	 */

	public double zero (
		final String tenor)
		throws Exception
	{
		if (null == tenor || tenor.isEmpty()) {
			throw new Exception ("MergedDiscountForwardCurve::zero => Invalid date");
		}

		JulianDate startDate = epoch();

		return forward (startDate.julian(), startDate.addTenor (tenor).julian());
	}

	/**
	 * Compute the LIBOR between 2 dates given the Year Fraction from the Day Count Convention
	 * 
	 * @param date1 First Date
	 * @param date2 Second Date
	 * @param yearFraction Year Fraction
	 * 
	 * @return LIBOR
	 * 
	 * @throws Exception Thrown if the discount factor cannot be calculated
	 */

	public double libor (
		final int date1,
		final int date2,
		final double yearFraction)
		throws Exception
	{
		if (date1 == date2 || !NumberUtil.IsValid (yearFraction) || 0. == yearFraction) {
			throw new Exception ("MergedDiscountForwardCurve::libor => Invalid input dates");
		}

		return ((df (date1) / df (date2)) - 1.) / yearFraction;
	}

	/**
	 * Compute the LIBOR between 2 dates
	 * 
	 * @param date1 First Date
	 * @param date2 Second Date
	 * 
	 * @return LIBOR
	 * 
	 * @throws Exception Thrown if the discount factor cannot be calculated
	 */

	public double libor (
		final int date1,
		final int date2)
		throws Exception
	{
		if (date1 == date2) {
			throw new Exception ("MergedDiscountForwardCurve::libor => Invalid input dates");
		}

		return libor (date1, date2, Convention.YearFraction (date1, date2, "Act/360", false, null, ""));
	}

	/**
	 * Calculate the LIBOR to the given tenor at the specified date
	 * 
	 * @param startDate Start Date
	 * @param tenor Tenor
	 * 
	 * @return LIBOR
	 * 
	 * @throws Exception Thrown if LIBOR cannot be calculated
	 */

	public double libor (
		final int startDate,
		final String tenor)
		throws Exception
	{
		if (!NumberUtil.IsValid (startDate) || null == tenor || tenor.isEmpty()) {
			throw new Exception ("MergedDiscountForwardCurve::libor => Invalid Inputs");
		}

		return libor (startDate, new JulianDate (startDate).addTenor (tenor).julian());
	}

	/**
	 * Calculate the LIBOR to the given tenor at the specified Julian Date
	 * 
	 * @param date Julian Date
	 * @param tenor Tenor
	 * 
	 * @return LIBOR
	 * 
	 * @throws Exception Thrown if LIBOR cannot be calculated
	 */

	public double libor (
		final JulianDate date,
		final String tenor)
		throws Exception
	{
		if (null == date) {
			throw new Exception ("MergedDiscountForwardCurve::libor => Invalid Inputs");
		}

		return libor (date.julian(), tenor);
	}

	/**
	 * Calculate the DV01 of the Par Swap that Matures at the given date
	 * 
	 * @param date Date
	 * 
	 * @return DV01 of the Par Swap that Matures at the given date
	 * 
	 * @throws Exception Thrown if DV01 cannot be calculated
	 */

	public double parSwapDV01 (
		final int date)
		throws Exception
	{
		String currency = currency();

		JulianDate startDate = epoch().addDays (2);

		return new Stream (
			CompositePeriodBuilder.FixedCompositeUnit (
				CompositePeriodBuilder.BackwardEdgeDates (
					startDate,
					new JulianDate (date),
					"6M",
					null,
					CompositePeriodBuilder.SHORT_STUB
				),
				new CompositePeriodSetting (
					2,
					"6M",
					currency,
					null,
					1.,
					null,
					null,
					null,
					null
				),
				new UnitCouponAccrualSetting (
					2,
					"Act/360",
					false,
					"Act/360",
					false,
					currency,
					true,
					CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC
				),
				new ComposableFixedUnitSetting (
					"6M",
					CompositePeriodBuilder.EDGE_DATE_SEQUENCE_REGULAR,
					null,
					0.,
					0.,
					currency
				)
			)
		).value (
			ValuationParams.Spot (startDate, 0, "", Convention.DATE_ROLL_ACTUAL),
			null,
			MarketParamsBuilder.Create (this, null, null, null, null, null, null, null),
			null
		).get ("DV01");
	}

	/**
	 * Estimate the manifest measure value for the given date
	 * 
	 * @param manifestMeasure The Manifest Measure to be Estimated
	 * @param date Date
	 * 
	 * @return The estimated calibrated measure value
	 * 
	 * @throws Exception Thrown if the estimated manifest measure cannot be computed
	 */

	public double estimateManifestMeasure (
		final String manifestMeasure,
		final int date)
		throws Exception
	{
		if (null == manifestMeasure || manifestMeasure.isEmpty()) {
			throw new Exception ("MergedDiscountForwardCurve::estimateManifestMeasure => Invalid input");
		}

		CalibratableComponent[] calibratableComponentArray = calibComp();

		if (null == calibratableComponentArray) {
			throw new Exception (
				"MergedDiscountForwardCurve::estimateManifestMeasure => Calib Components not available"
			);
		}

		int componentCount = calibratableComponentArray.length;

		if (0 == componentCount) {
			throw new Exception (
				"MergedDiscountForwardCurve::estimateManifestMeasure => Calib Components not available"
			);
		}

		List<Integer> dateList = new ArrayList<Integer>();

		List<Double> quoteList = new ArrayList<Double>();

		for (int componentIndex = 0; componentIndex < componentCount; ++componentIndex) {
			if (null == calibratableComponentArray[componentIndex]) {
				throw new Exception (
					"MergedDiscountForwardCurve::estimateManifestMeasure => Cannot locate a component"
				);
			}

			CaseInsensitiveTreeMap<Double> manifestMeasureMap = manifestMeasure
				(calibratableComponentArray[componentIndex].primaryCode());

			if (manifestMeasureMap.containsKey (manifestMeasure)) {
				dateList.add (calibratableComponentArray[componentIndex].maturityDate().julian());

				quoteList.add (manifestMeasureMap.get (manifestMeasure));
			}
		}

		int estimationComponentCount = dateList.size();

		if (0 == estimationComponentCount) {
			throw new Exception (
				"MergedDiscountForwardCurve::estimateManifestMeasure => Estimation Components not available"
			);
		}

		int[] dateArray = new int[estimationComponentCount];
		double[] quoteArray = new double[estimationComponentCount];
		SegmentCustomBuilderControl[] segmentCustomBuilderControlArray =
			new SegmentCustomBuilderControl[estimationComponentCount - 1];

		if (1 == estimationComponentCount) {
			return quoteList.get (0);
		}

		for (int estimationComponentIndex = 0; estimationComponentIndex < estimationComponentCount;
			++estimationComponentIndex) {
			if (0 != estimationComponentIndex) {
				segmentCustomBuilderControlArray[estimationComponentIndex - 1] =
					new SegmentCustomBuilderControl (
						MultiSegmentSequenceBuilder.BASIS_SPLINE_POLYNOMIAL,
						new PolynomialFunctionSetParams (4),
						SegmentInelasticDesignControl.Create (2, 2),
						null,
						null
					);
			}

			dateArray[estimationComponentIndex] = dateList.get (estimationComponentIndex);

			quoteArray[estimationComponentIndex] = quoteList.get (estimationComponentIndex);
		}

		MultiSegmentSequence regimeMultiSegmentSequence =
			MultiSegmentSequenceBuilder.CreateCalibratedStretchEstimator (
				"DISC_CURVE_REGIME",
				dateArray,
				quoteArray,
				segmentCustomBuilderControlArray,
				null,
				BoundarySettings.NaturalStandard(),
				MultiSegmentSequence.CALIBRATE
			);

		if (null == regimeMultiSegmentSequence) {
			throw new Exception (
				"MergedDiscountForwardCurve::estimateManifestMeasure => Cannot create Spline Stretch"
			);
		}

		double regimeLeftExtreme = regimeMultiSegmentSequence.getLeftPredictorOrdinateEdge();

		if (date <= regimeLeftExtreme) {
			return regimeMultiSegmentSequence.responseValue (regimeLeftExtreme);
		}

		double regimeRightExtreme = regimeMultiSegmentSequence.getRightPredictorOrdinateEdge();

		if (date >= regimeRightExtreme) {
			return regimeMultiSegmentSequence.responseValue (regimeRightExtreme);
		}

		return regimeMultiSegmentSequence.responseValue (date);
	}

	/**
	 * Proxy the Manifest Measure Value using the Closest Node for the given Date
	 * 
	 * @param manifestMeasure The Manifest Measure to be Proxied
	 * @param date Date
	 * 
	 * @return The Measure Value Proxy
	 * 
	 * @throws Exception Thrown if the Manifest Measure Proxy cannot be computed
	 */

	public double proxyManifestMeasure (
		final String manifestMeasure,
		final int date)
		throws Exception
	{
		if (null == manifestMeasure || manifestMeasure.isEmpty()) {
			throw new Exception ("MergedDiscountForwardCurve::proxyManifestMeasure => Invalid input");
		}

		CalibratableComponent[] calibratableComponentArray = calibComp();

		if (null == calibratableComponentArray) {
			throw new Exception (
				"MergedDiscountForwardCurve::proxyManifestMeasure => Calib Components not available"
			);
		}

		int componentCount = calibratableComponentArray.length;

		if (0 == componentCount) {
			throw new Exception (
				"MergedDiscountForwardCurve::proxyManifestMeasure => Calib Components not available"
			);
		}

		List<Integer> dateList = new ArrayList<Integer>();

		List<Double> quoteList = new ArrayList<Double>();

		for (int componentIndex = 0; componentIndex < componentCount; ++componentIndex) {
			if (null == calibratableComponentArray[componentIndex]) {
				throw new Exception (
					"MergedDiscountForwardCurve::proxyManifestMeasure => Cannot locate a component"
				);
			}

			CaseInsensitiveTreeMap<Double> manifestMeasureMap = manifestMeasure
				(calibratableComponentArray[componentIndex].primaryCode());

			if (manifestMeasureMap.containsKey (manifestMeasure)) {
				dateList.add (calibratableComponentArray[componentIndex].maturityDate().julian());

				quoteList.add (manifestMeasureMap.get (manifestMeasure));
			}
		}

		int estimationComponentCount = dateList.size();

		if (0 == estimationComponentCount) {
			throw new Exception (
				"MergedDiscountForwardCurve::proxyManifestMeasure => Estimation Components not available"
			);
		}

		if (1 == estimationComponentCount) {
			return quoteList.get (0);
		}

		int previousDate = dateList.get (0);

		if (date <= previousDate) {
			return quoteList.get (0);
		}

		for (int estimationComponentIndex = 1; estimationComponentIndex < estimationComponentCount;
			++estimationComponentIndex) {
			int currentDate = dateList.get (estimationComponentIndex);

			if (previousDate <= date && date < currentDate) {
				return date - previousDate > currentDate - date ? quoteList.get (estimationComponentIndex) :
					quoteList.get (estimationComponentIndex - 1);
			}

			previousDate = currentDate;
		}

		return quoteList.get (estimationComponentCount - 1);
	}

	@Override public boolean setCCIS (
		final CurveConstructionInputSet curveConstructionInputSet)
	{
		if (null == curveConstructionInputSet) {
			return false;
		}

		_curveConstructionInputSet = curveConstructionInputSet;
		return true;
	}

	/**
	 * Retrieve the Forward Curve that might be implied by the Latent State of this Discount Curve Instance
	 * 	corresponding to the specified Floating Rate Index
	 * 
	 * @param date The Date
	 * @param forwardLabel The Floating Rate Index
	 * 
	 * @return The Forward Curve Implied by the Discount Curve Latent State
	 */

	public abstract ForwardRateEstimator forwardRateEstimator (
		final int date,
		final ForwardLabel forwardLabel
	);

	/**
	 * Retrieve the Latent State Quantification Metric
	 * 
	 * @return The Latent State Quantification Metric
	 */

	public abstract String latentStateQuantificationMetric();

	/**
	 * Retrieve the Manifest Measure Jacobian of the Discount Factor to the given date
	 * 
	 * @param date Date
	 * @param manifestMeasure Manifest Measure
	 * 
	 * @return The Manifest Measure Jacobian of the Discount Factor to the given date
	 */

	public abstract WengertJacobian jackDDFDManifestMeasure (
		final int date,
		final String manifestMeasure
	);

	/**
	 * Retrieve the Manifest Measure Jacobian of the Discount Factor to the given date
	 * 
	 * @param date Date
	 * @param manifestMeasure Manifest Measure
	 * 
	 * @return The Manifest Measure Jacobian of the Discount Factor to the given date
	 */

	public WengertJacobian jackDDFDManifestMeasure (
		final JulianDate date,
		final String manifestMeasure)
	{
		return null == date ? null : jackDDFDManifestMeasure (date.julian(), manifestMeasure);
	}

	/**
	 * Retrieve the Manifest Measure Jacobian of the Discount Factor to the date implied by the given Tenor
	 * 
	 * @param tenor Tenor
	 * @param manifestMeasure Manifest Measure
	 * 
	 * @return The Manifest Measure Jacobian of the Discount Factor to the date implied by the given Tenor
	 */

	public WengertJacobian jackDDFDManifestMeasure (
		final String tenor,
		final String manifestMeasure)
	{
		try {
			return null == tenor || tenor.isEmpty() ? null :
				jackDDFDManifestMeasure (epoch().addTenor (tenor), manifestMeasure);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Calculate the Jacobian of PV at the given date to the Manifest Measure of each component in the
	 * 	calibration set to the DF
	 * 
	 * @param date Date for which the Jacobian is needed
	 * 
	 * @return The Jacobian
	 */

	public WengertJacobian compJackDPVDManifestMeasure (
		final int date)
	{
		if (!NumberUtil.IsValid (date)) {
			return null;
		}

		CalibratableComponent[] calibratableComponentArray = calibComp();

		if (null == calibratableComponentArray || 0 == calibratableComponentArray.length) {
			return null;
		}

		int parameterCount = 0;
		WengertJacobian wengertJacobianComponentPVDF = null;
		int componentCount = calibratableComponentArray.length;

		ValuationParams valuationParams = ValuationParams.Spot (date);

		CurveSurfaceQuoteContainer curveSurfaceQuoteContainer = MarketParamsBuilder.Create (
			this,
			null,
			null,
			null,
			null,
			null,
			null,
			null == _curveConstructionInputSet ? null : _curveConstructionInputSet.fixing()
		);

		for (int componentIndex = 0; componentIndex < componentCount; ++componentIndex) {
			WengertJacobian wengertJacobianComponentDDirtyPVDManifestMeasure =
				calibratableComponentArray[componentIndex].jackDDirtyPVDManifestMeasure (
					valuationParams,
					null,
					curveSurfaceQuoteContainer,
					null
				);

			if (null == wengertJacobianComponentDDirtyPVDManifestMeasure) {
				return null;
			}

			parameterCount = wengertJacobianComponentDDirtyPVDManifestMeasure.numParameters();

			if (null == wengertJacobianComponentPVDF) {
				try {
					wengertJacobianComponentPVDF = new WengertJacobian (componentCount, parameterCount);
				} catch (Exception e) {
					e.printStackTrace();

					return null;
				}
			}

			for (int parameterIndex = 0; parameterIndex < parameterCount; ++parameterIndex) {
				if (!wengertJacobianComponentPVDF.accumulatePartialFirstDerivative (
					componentIndex,
					parameterIndex,
					wengertJacobianComponentDDirtyPVDManifestMeasure.firstDerivative (0, parameterIndex)
				)) {
					return null;
				}
			}
		}

		return wengertJacobianComponentPVDF;
	}

	/**
	 * Calculate the Jacobian of PV at the given date to the Manifest Measure of each component in the
	 * 	calibration set to the DF
	 * 
	 * @param date Date for which the Jacobian is needed
	 * 
	 * @return The Jacobian
	 */

	public WengertJacobian compJackDPVDManifestMeasure (
		final JulianDate date)
	{
		return null == date ? null : compJackDPVDManifestMeasure (date.julian());
	}

	/**
	 * Retrieve the Jacobian of the Forward Rate to the Manifest Measure between the given dates
	 * 
	 * @param date1 Date 1
	 * @param date2 Date 2
	 * @param manifestMeasure Manifest Measure
	 * @param elapsedYearFraction The Elapsed Year (in the appropriate Day Count) between dates 1 and 2
	 * 
	 * @return The Jacobian
	 */

	public WengertJacobian jackDForwardDManifestMeasure (
		final int date1,
		final int date2,
		final String manifestMeasure,
		final double elapsedYearFraction)
	{
		if (date1 == date2) {
			return null;
		}

		WengertJacobian wengertJacobianDDFDManifestMeasureDate1 = jackDDFDManifestMeasure (
			date1,
			manifestMeasure
		);

		if (null == wengertJacobianDDFDManifestMeasureDate1) {
			return null;
		}

		int quoteCount = wengertJacobianDDFDManifestMeasureDate1.numParameters();

		if (0 == quoteCount) {
			return null;
		}

		WengertJacobian wengertJacobianDDFDManifestMeasureDate2 = jackDDFDManifestMeasure (
			date2,
			manifestMeasure
		);

		if (null == wengertJacobianDDFDManifestMeasureDate2 ||
			quoteCount != wengertJacobianDDFDManifestMeasureDate2.numParameters()) {
			return null;
		}

		double discountFactor1 = Double.NaN;
		double discountFactor2 = Double.NaN;
		WengertJacobian wengertJacobianDForwardDManifestMeasure = null;

		try {
			discountFactor1 = df (date1);

			discountFactor2 = df (date2);

			wengertJacobianDForwardDManifestMeasure = new WengertJacobian (1, quoteCount);
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}

		double dForwardDManifestMeasure1Scale = 1. / discountFactor2;
		double inverseAnnualizedTenorLength = 1. / elapsedYearFraction;
		double dForwardDManifestMeasure2Scale = discountFactor1 / (discountFactor2 * discountFactor2);

		for (int quoteIndex = 0; quoteIndex < quoteCount; ++quoteIndex) {
			if (!wengertJacobianDForwardDManifestMeasure.accumulatePartialFirstDerivative (
				0,
				quoteIndex, (
					wengertJacobianDDFDManifestMeasureDate1.firstDerivative (0, quoteIndex) *
						dForwardDManifestMeasure1Scale -
					wengertJacobianDDFDManifestMeasureDate2.firstDerivative (0, quoteIndex) *
						dForwardDManifestMeasure2Scale
					) * inverseAnnualizedTenorLength
				)) {
				return null;
			}
		}

		return wengertJacobianDForwardDManifestMeasure;
	}

	/**
	 * Retrieve the Jacobian of the Forward Rate to the Manifest Measure between the given dates
	 * 
	 * @param date1 Julian Date 1
	 * @param date2 Julian Date 2
	 * @param manifestMeasure Manifest Measure
	 * @param elapsedYearFraction The Elapsed Year (in the appropriate Day Count) between dates 1 and 2
	 * 
	 * @return The Jacobian
	 */

	public WengertJacobian jackDForwardDManifestMeasure (
		final JulianDate date1,
		final JulianDate date2,
		final String manifestMeasure,
		final double elapsedYearFraction)
	{
		return null == date1 || null == date2 ? null : jackDForwardDManifestMeasure (
			date1.julian(),
			date2.julian(),
			manifestMeasure,
			elapsedYearFraction
		);
	}

	/**
	 * Retrieve the Jacobian of the Forward Rate to the Manifest Measure at the given date
	 * 
	 * @param date Given Julian Date
	 * @param tenor Tenor
	 * @param manifestMeasure Manifest Measure
	 * @param elapsedYearFraction The Elapsed Year (in the appropriate Day Count) implied by the Tenor
	 * 
	 * @return The Jacobian
	 */

	public WengertJacobian jackDForwardDManifestMeasure (
		final JulianDate date,
		final String tenor,
		final String manifestMeasure,
		final double elapsedYearFraction)
	{
		return null == date || null == tenor || tenor.isEmpty() ? null : jackDForwardDManifestMeasure (
			date.julian(),
			date.addTenor (tenor).julian(),
			manifestMeasure,
			elapsedYearFraction
		);
	}

	/**
	 * Retrieve the Jacobian for the Zero Rate to the given date
	 * 
	 * @param date Date
	 * @param manifestMeasure Manifest Measure
	 * 
	 * @return The Jacobian
	 */

	public WengertJacobian zeroRateJack (
		final int date,
		final String manifestMeasure)
	{
		int epochDate = epoch().julian();

		return jackDForwardDManifestMeasure (
			epochDate,
			date,
			manifestMeasure,
			1. * (date - epochDate) / 365.25
		);
	}

	/**
	 * Retrieve the Jacobian for the Zero Rate to the given date
	 * 
	 * @param date Julian Date
	 * @param manifestMeasure Manifest Measure
	 * 
	 * @return The Jacobian
	 */

	public WengertJacobian zeroRateJack (
		final JulianDate date,
		final String manifestMeasure)
	{
		return null == date ? null : zeroRateJack (date.julian(), manifestMeasure);
	}

	/**
	 * Convert the inferred Formulation Constraint into a "Truthness" Entity
	 * 
	 * @param latentStateQuantificationMetric Latent State Quantification Metric
	 * 
	 * @return Map of the Truthness Entities
	 */

	public Map<Integer, Double> canonicalTruthness (
		final String latentStateQuantificationMetric)
	{
		if (null == latentStateQuantificationMetric || (
			!LatentStateStatic.DISCOUNT_QM_ZERO_RATE.equalsIgnoreCase (latentStateQuantificationMetric) &&
			!LatentStateStatic.DISCOUNT_QM_DISCOUNT_FACTOR.equalsIgnoreCase (latentStateQuantificationMetric)
		)) {
			return null;
		}

		CalibratableComponent[] calibratableComponentArray = calibComp();

		if (null == calibratableComponentArray) {
			return null;
		}

		boolean firstCashFlow = true;

		if (0 == calibratableComponentArray.length) {
			return null;
		}

		Map<Integer, Double> canonicalTruthnessMap = new TreeMap<Integer, Double>();

		if (LatentStateStatic.DISCOUNT_QM_DISCOUNT_FACTOR.equalsIgnoreCase (latentStateQuantificationMetric))
		{
			canonicalTruthnessMap.put (_epochDate, 1.);
		}

		for (CalibratableComponent calibratableComponent : calibratableComponentArray) {
			if (null == calibratableComponent) {
				continue;
			}

			List<CompositePeriod> couponPeriodList = calibratableComponent.couponPeriods();

			if (null == couponPeriodList || 0 == couponPeriodList.size()) continue;

			for (CompositePeriod compositePeriod : couponPeriodList) {
				if (null == compositePeriod) {
					continue;
				}

				int periodPayDate = compositePeriod.payDate();

				if (periodPayDate >= _epochDate) {
					try {
						if (LatentStateStatic.DISCOUNT_QM_DISCOUNT_FACTOR.equalsIgnoreCase
							(latentStateQuantificationMetric)) {
							canonicalTruthnessMap.put (periodPayDate, df (periodPayDate));
						} else if (LatentStateStatic.DISCOUNT_QM_ZERO_RATE.equalsIgnoreCase
							(latentStateQuantificationMetric)) {
							if (firstCashFlow) {
								firstCashFlow = false;

								canonicalTruthnessMap.put (_epochDate, zero (periodPayDate));
							}

							canonicalTruthnessMap.put (periodPayDate, zero (periodPayDate));
						}
					} catch (Exception e) {
						e.printStackTrace();

						return null;
					}
				}
			}
		}

		return canonicalTruthnessMap;
	}
}
