
package org.drip.state.discount;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
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
 *  <br><br>
 *  <ul>
 *  	<li>
 * 			Forward Rate to a specific date/tenor, and effective rate between a date interval
 *  	</li>
 *  	<li>
 * 			Discount Factor to a specific date/tenor, and effective discount factor between a date interval
 *  	</li>
 *  	<li>
 * 			Zero Rate to a specific date/tenor
 *  	</li>
 *  	<li>
 *  		Value Jacobian for Forward rate, discount factor, and zero rate
 *  	</li>
 *  	<li>
 *  		Cross Jacobian between each of Forward rate, discount factor, and zero rate
 *  	</li>
 *  	<li>
 *  		Quote Jacobian to Forward rate, discount factor, and zero rate
 *  	</li>
 *  	<li>
 *  		QM (DF/Zero/Forward) to Quote Jacobian
 *  	</li>
 *  	<li>
 *  		Latent State Quantification Metric, and the canonical truthness transformations
 *  	</li>
 *  	<li>
 *  		Implied/embedded ForwardRateEstimator
 *  	</li>
 *  	<li>
 *  		Turns set/unset/adjust
 *  	</li>
 *  </ul>
 *
 *  <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/README.md">Latent State Inference and Creation Utilities</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/discount/README.md">Discount Curve Spline Latent State</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public abstract class MergedDiscountForwardCurve extends org.drip.state.discount.DiscountCurve {
	private static final int NUM_DF_QUADRATURES = 5;

	protected java.lang.String _strCurrency = "";
	protected int _iEpochDate = java.lang.Integer.MIN_VALUE;
	protected org.drip.state.discount.TurnListDiscountFactor _tldf = null;
	protected org.drip.analytics.input.CurveConstructionInputSet _ccis = null;

	protected MergedDiscountForwardCurve (
		final int iEpochDate,
		final java.lang.String strCurrency,
		final org.drip.state.discount.TurnListDiscountFactor tldf)
		throws java.lang.Exception
	{
		if (null == (_strCurrency = strCurrency) || _strCurrency.isEmpty() ||
			!org.drip.numerical.common.NumberUtil.IsValid (_iEpochDate = iEpochDate))
			throw new java.lang.Exception ("MergedDiscountForwardCurve ctr: Invalid Inputs");

		_tldf = tldf;
	}

	@Override public org.drip.state.identifier.LatentStateLabel label()
	{
		return org.drip.state.identifier.FundingLabel.Standard (_strCurrency);
	}

	@Override public java.lang.String currency()
	{
		return _strCurrency;
	}

	@Override public org.drip.analytics.date.JulianDate epoch()
	{
		try {
			return new org.drip.analytics.date.JulianDate (_iEpochDate);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Set the Discount Curve Turns'
	 * 
	 * @param tldf Turn List Discount Factor
	 * 
	 * @return TRUE - Valid Turn List Discount Factor Set
	 */

	public boolean setTurns (
		final org.drip.state.discount.TurnListDiscountFactor tldf)
	{
		return null != (_tldf = tldf);
	}

	/**
	 * Apply the Turns' DF Adjustment
	 * 
	 * @param iStartDate Turn Start Date
	 * @param iFinishDate Turn Finish Date
	 * 
	 * @return Turns' DF Adjustment
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are invalid
	 */

	public double turnAdjust (
		final int iStartDate,
		final int iFinishDate)
		throws java.lang.Exception
	{
		return null == _tldf ? 1. : _tldf.turnAdjust (iStartDate, iFinishDate);
	}

	/**
	 * Apply the Turns' DF Adjustment
	 * 
	 * @param iFinishDate Turn Finish Date
	 * 
	 * @return Turns' DF Adjustment
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are invalid
	 */

	protected double turnAdjust (
		final int iFinishDate)
		throws java.lang.Exception
	{
		return turnAdjust (epoch().julian(), iFinishDate);
	}

	/**
	 * Construct the Native Forward Curve for the given Tenor from the Discount Curve
	 * 
	 * @param strTenor The Tenor
	 * 
	 * @return The Tenor-Native Forward Curve
	 */

	public org.drip.state.forward.ForwardCurve nativeForwardCurve (
		final java.lang.String strTenor)
	{
		if (null == strTenor || strTenor.isEmpty()) return null;

		try {
			org.drip.state.forward.ForwardCurve fcNative = new org.drip.state.forward.ForwardCurve
				(epoch().julian(), org.drip.state.identifier.ForwardLabel.Standard (_strCurrency + "-" +
					strTenor)) {
				@Override public double forward (
					final int iDate)
					throws java.lang.Exception
				{
					return forward (new org.drip.analytics.date.JulianDate (iDate));
				}

				@Override public double forward (
					final org.drip.analytics.date.JulianDate dt)
					throws java.lang.Exception
				{
					if (null == dt)
						throw new java.lang.Exception
							("MergedDiscountForwardCurve::nativeForwardCurve => Invalid Input");

					return libor (dt.subtractTenor (strTenor).julian(), strTenor);
				}

				@Override public double forward (
					final java.lang.String strTenor)
					throws java.lang.Exception
				{
					if (null == strTenor || strTenor.isEmpty())
						throw new java.lang.Exception
							("MergedDiscountForwardCurve::nativeForwardCurve => Invalid Input");

					return forward (epoch().addTenor (strTenor));
				}

				@Override public org.drip.numerical.differentiation.WengertJacobian jackDForwardDManifestMeasure (
					final java.lang.String strManifestMeasure,
					final int iDate)
				{
					return null;
				}
			};

			return fcNative;
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override public double df (
		final org.drip.analytics.date.JulianDate dt)
		throws java.lang.Exception
	{
		if (null == dt) throw new java.lang.Exception ("MergedDiscountForwardCurve::df got null for date");

		return df (dt.julian());
	}

	@Override public double df (
		final java.lang.String strTenor)
		throws java.lang.Exception
	{
		if (null == strTenor || strTenor.isEmpty())
			throw new java.lang.Exception ("MergedDiscountForwardCurve::df got bad tenor");

		return df (epoch().addTenor (strTenor));
	}

	@Override public double effectiveDF (
		final int iDate1,
		final int iDate2)
		throws java.lang.Exception
	{
		if (iDate1 == iDate2) return df (iDate1);

		int iNumQuadratures = 0;
		double dblEffectiveDF = 0.;
		int iQuadratureWidth = (iDate2 - iDate1) / NUM_DF_QUADRATURES;

		if (0 == iQuadratureWidth) iQuadratureWidth = 1;

		for (int iDate = iDate1; iDate <= iDate2; iDate += iQuadratureWidth) {
			++iNumQuadratures;

			dblEffectiveDF += (df (iDate) + df (iDate + iQuadratureWidth));
		}

		return dblEffectiveDF / (2. * iNumQuadratures);
	}

	@Override public double effectiveDF (
		final org.drip.analytics.date.JulianDate dt1,
		final org.drip.analytics.date.JulianDate dt2)
		throws java.lang.Exception
	{
		if (null == dt1 || null == dt2)
			throw new java.lang.Exception ("MergedDiscountForwardCurve::effectiveDF => Got null for date");

		return effectiveDF (dt1.julian(), dt2.julian());
	}

	@Override public double effectiveDF (
		final java.lang.String strTenor1,
		final java.lang.String strTenor2)
		throws java.lang.Exception
	{
		if (null == strTenor1 || strTenor1.isEmpty() || null == strTenor2 || strTenor2.isEmpty())
			throw new java.lang.Exception ("MergedDiscountForwardCurve::effectiveDF => Got bad tenor");

		org.drip.analytics.date.JulianDate dtStart = epoch();

		return effectiveDF (dtStart.addTenor (strTenor1), dtStart.addTenor (strTenor2));
	}

	/**
	 * Compute the Forward Rate between two Dates
	 * 
	 * @param iDate1 First Date
	 * @param iDate2 Second Date
	 * 
	 * @return The Forward Rate
	 * 
	 * @throws java.lang.Exception Thrown if the Forward Rate cannot be calculated
	 */

	public abstract double forward (
		final int iDate1,
		final int iDate2)
		throws java.lang.Exception;

	/**
	 * Compute the Forward Rate between two Tenors
	 * 
	 * @param strTenor1 Tenor Start
	 * @param strTenor2 Tenor End
	 * 
	 * @return The Forward Rate
	 * 
	 * @throws java.lang.Exception Thrown if the Forward Rate cannot be calculated
	 */

	public double forward (
		final java.lang.String strTenor1,
		final java.lang.String strTenor2)
		throws java.lang.Exception
	{
		if (null == strTenor1 || strTenor1.isEmpty() || null == strTenor2 || strTenor2.isEmpty())
			throw new java.lang.Exception ("MergedDiscountForwardCurve::forward => Invalid Date");

		org.drip.analytics.date.JulianDate dtStart = epoch();

		return forward (dtStart.addTenor (strTenor1).julian(), dtStart.addTenor (strTenor2).julian());
	}

	/**
	 * Calculate the implied rate to the given date
	 * 
	 * @param iDate Date
	 * 
	 * @return Implied rate
	 * 
	 * @throws java.lang.Exception Thrown if the discount factor cannot be calculated
	 */

	public abstract double zero (
		final int iDate)
		throws java.lang.Exception;

	/**
	 * Calculate the implied rate to the given tenor
	 * 
	 * @param strTenor Tenor
	 * 
	 * @return Implied rate
	 * 
	 * @throws java.lang.Exception Thrown if the discount factor cannot be calculated
	 */

	public double zero (
		final java.lang.String strTenor)
		throws java.lang.Exception
	{
		if (null == strTenor || strTenor.isEmpty())
			throw new java.lang.Exception ("MergedDiscountForwardCurve::zero => Invalid date");

		org.drip.analytics.date.JulianDate dtStart = epoch();

		return forward (dtStart.julian(), dtStart.addTenor (strTenor).julian());
	}

	/**
	 * Compute the LIBOR between 2 dates given the Day Count
	 * 
	 * @param iDate1 First Date
	 * @param iDate2 Second Date
	 * @param dblDCF Day Count Fraction
	 * 
	 * @return LIBOR
	 * 
	 * @throws java.lang.Exception Thrown if the discount factor cannot be calculated
	 */

	public double libor (
		final int iDate1,
		final int iDate2,
		final double dblDCF)
		throws java.lang.Exception
	{
		if (iDate1 == iDate2 || !org.drip.numerical.common.NumberUtil.IsValid (dblDCF) || 0. == dblDCF)
			throw new java.lang.Exception ("MergedDiscountForwardCurve::libor => Invalid input dates");

		return ((df (iDate1) / df (iDate2)) - 1.) / dblDCF;
	}

	/**
	 * Compute the LIBOR between 2 dates
	 * 
	 * @param iDate1 First Date
	 * @param iDate2 Second Date
	 * 
	 * @return LIBOR
	 * 
	 * @throws java.lang.Exception Thrown if the discount factor cannot be calculated
	 */

	public double libor (
		final int iDate1,
		final int iDate2)
		throws java.lang.Exception
	{
		if (iDate1 == iDate2)
			throw new java.lang.Exception ("MergedDiscountForwardCurve::libor => Invalid input dates");

		return libor (iDate1, iDate2, org.drip.analytics.daycount.Convention.YearFraction (iDate1, iDate2,
			"Act/360", false, null, ""));
	}

	/**
	 * Calculate the LIBOR to the given tenor at the specified date
	 * 
	 * @param iStartDate Start Date
	 * @param strTenor Tenor
	 * 
	 * @return LIBOR
	 * 
	 * @throws java.lang.Exception Thrown if LIBOR cannot be calculated
	 */

	public double libor (
		final int iStartDate,
		final java.lang.String strTenor)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (iStartDate) || null == strTenor || strTenor.isEmpty())
			throw new java.lang.Exception ("MergedDiscountForwardCurve::libor => Invalid Inputs");

		return libor (iStartDate, new org.drip.analytics.date.JulianDate (iStartDate).addTenor
			(strTenor).julian());
	}

	/**
	 * Calculate the LIBOR to the given tenor at the specified Julian Date
	 * 
	 * @param dt Julian Date
	 * @param strTenor Tenor
	 * 
	 * @return LIBOR
	 * 
	 * @throws java.lang.Exception Thrown if LIBOR cannot be calculated
	 */

	public double libor (
		final org.drip.analytics.date.JulianDate dt,
		final java.lang.String strTenor)
		throws java.lang.Exception
	{
		if (null == dt)
			throw new java.lang.Exception ("MergedDiscountForwardCurve::libor => Invalid Inputs");

		return libor (dt.julian(), strTenor);
	}

	/**
	 * Calculate the DV01 of the Par Swap that Matures at the given date
	 * 
	 * @param iDate Date
	 * 
	 * @return DV01 of the Par Swap that Matures at the given date
	 * 
	 * @throws java.lang.Exception Thrown if DV01 cannot be calculated
	 */

	public double parSwapDV01 (
		final int iDate)
		throws java.lang.Exception
	{
		java.lang.String strCurrency = currency();

		org.drip.analytics.date.JulianDate dtStart = epoch().addDays (2);

		org.drip.param.period.UnitCouponAccrualSetting ucasFixed = new
			org.drip.param.period.UnitCouponAccrualSetting (2, "Act/360", false, "Act/360", false,
				strCurrency, true,
					org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC);

		org.drip.param.period.ComposableFixedUnitSetting cfusFixed = new
			org.drip.param.period.ComposableFixedUnitSetting ("6M",
				org.drip.analytics.support.CompositePeriodBuilder.EDGE_DATE_SEQUENCE_REGULAR, null, 0., 0.,
					strCurrency);

		org.drip.param.period.CompositePeriodSetting cpsFixed = new
			org.drip.param.period.CompositePeriodSetting (2, "6M", strCurrency, null, 1., null, null, null,
				null);

		java.util.List<java.lang.Integer> lsFixedStreamEdgeDate =
			org.drip.analytics.support.CompositePeriodBuilder.BackwardEdgeDates (dtStart, new
				org.drip.analytics.date.JulianDate (iDate), "6M", null,
					org.drip.analytics.support.CompositePeriodBuilder.SHORT_STUB);

		org.drip.product.rates.Stream fixedStream = new org.drip.product.rates.Stream
			(org.drip.analytics.support.CompositePeriodBuilder.FixedCompositeUnit (lsFixedStreamEdgeDate,
				cpsFixed, ucasFixed, cfusFixed));

		org.drip.param.market.CurveSurfaceQuoteContainer csqs =
			org.drip.param.creator.MarketParamsBuilder.Create (this, null, null, null, null, null, null,
				null);

		java.util.Map<java.lang.String, java.lang.Double> mapFixStream = fixedStream.value
			(org.drip.param.valuation.ValuationParams.Spot (dtStart, 0, "",
				org.drip.analytics.daycount.Convention.DATE_ROLL_ACTUAL), null, csqs, null);

		return mapFixStream.get ("DV01");
	}

	/**
	 * Estimate the manifest measure value for the given date
	 * 
	 * @param strManifestMeasure The Manifest Measure to be Estimated
	 * @param iDate Date
	 * 
	 * @return The estimated calibrated measure value
	 * 
	 * @throws java.lang.Exception Thrown if the estimated manifest measure cannot be computed
	 */

	public double estimateManifestMeasure (
		final java.lang.String strManifestMeasure,
		final int iDate)
		throws java.lang.Exception
	{
		if (null == strManifestMeasure || strManifestMeasure.isEmpty())
			throw new java.lang.Exception
				("MergedDiscountForwardCurve::estimateManifestMeasure => Invalid input");

		org.drip.product.definition.CalibratableComponent[] aCalibComp = calibComp();

		if (null == aCalibComp)
			throw new java.lang.Exception
				("MergedDiscountForwardCurve::estimateManifestMeasure => Calib Components not available");

		int iNumComponent = aCalibComp.length;

		if (0 == iNumComponent)
			throw new java.lang.Exception
				("MergedDiscountForwardCurve::estimateManifestMeasure => Calib Components not available");

		java.util.List<java.lang.Integer> lsDate = new java.util.ArrayList<java.lang.Integer>();

		java.util.List<java.lang.Double> lsQuote = new java.util.ArrayList<java.lang.Double>();

		for (int i = 0; i < iNumComponent; ++i) {
			if (null == aCalibComp[i])
				throw new java.lang.Exception
					("MergedDiscountForwardCurve::estimateManifestMeasure => Cannot locate a component");

			org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapManifestMeasure =
				manifestMeasure (aCalibComp[i].primaryCode());

			if (mapManifestMeasure.containsKey (strManifestMeasure)) {
				lsDate.add (aCalibComp[i].maturityDate().julian());

				lsQuote.add (mapManifestMeasure.get (strManifestMeasure));
			}
		}

		int iNumEstimationComponent = lsDate.size();

		if (0 == iNumEstimationComponent)
			throw new java.lang.Exception
				("MergedDiscountForwardCurve::estimateManifestMeasure => Estimation Components not available");

		int[] aiDate = new int[iNumEstimationComponent];
		double[] adblQuote = new double[iNumEstimationComponent];
		org.drip.spline.params.SegmentCustomBuilderControl[] aSBP = new
			org.drip.spline.params.SegmentCustomBuilderControl[iNumEstimationComponent - 1];

		if (1 == iNumEstimationComponent) return lsQuote.get (0);

		org.drip.spline.params.SegmentCustomBuilderControl sbp = new
			org.drip.spline.params.SegmentCustomBuilderControl
				(org.drip.spline.stretch.MultiSegmentSequenceBuilder.BASIS_SPLINE_POLYNOMIAL, new
					org.drip.spline.basis.PolynomialFunctionSetParams (4),
						org.drip.spline.params.SegmentInelasticDesignControl.Create (2, 2), null, null);

		for (int i = 0; i < iNumEstimationComponent; ++i) {
			if (0 != i) aSBP[i - 1] = sbp;

			aiDate[i] = lsDate.get (i);

			adblQuote[i] = lsQuote.get (i);
		}

		org.drip.spline.stretch.MultiSegmentSequence regime =
			org.drip.spline.stretch.MultiSegmentSequenceBuilder.CreateCalibratedStretchEstimator
				("DISC_CURVE_REGIME", aiDate, adblQuote, aSBP, null,
					org.drip.spline.stretch.BoundarySettings.NaturalStandard(),
						org.drip.spline.stretch.MultiSegmentSequence.CALIBRATE);

		if (null == regime)
			throw new java.lang.Exception
				("MergedDiscountForwardCurve::estimateManifestMeasure => Cannot create Spline Stretch");

		double dblRegimeLeftExtreme = regime.getLeftPredictorOrdinateEdge();

		if (iDate <= dblRegimeLeftExtreme) return regime.responseValue (dblRegimeLeftExtreme);

		double dblRegimeRightExtreme = regime.getRightPredictorOrdinateEdge();

		if (iDate >= dblRegimeRightExtreme) return regime.responseValue (dblRegimeRightExtreme);

		return regime.responseValue (iDate);
	}

	/**
	 * Proxy the Manifest Measure Value using the Closest Node for the given Date
	 * 
	 * @param strManifestMeasure The Manifest Measure to be Proxied
	 * @param iDate Date
	 * 
	 * @return The Measure Value Proxy
	 * 
	 * @throws java.lang.Exception Thrown if the Manifest Measure Proxy cannot be computed
	 */

	public double proxyManifestMeasure (
		final java.lang.String strManifestMeasure,
		final int iDate)
		throws java.lang.Exception
	{
		if (null == strManifestMeasure || strManifestMeasure.isEmpty())
			throw new java.lang.Exception
				("MergedDiscountForwardCurve::proxyManifestMeasure => Invalid input");

		org.drip.product.definition.CalibratableComponent[] aCalibComp = calibComp();

		if (null == aCalibComp)
			throw new java.lang.Exception
				("MergedDiscountForwardCurve::proxyManifestMeasure => Calib Components not available");

		int iNumComponent = aCalibComp.length;

		if (0 == iNumComponent)
			throw new java.lang.Exception
				("MergedDiscountForwardCurve::proxyManifestMeasure => Calib Components not available");

		java.util.List<java.lang.Integer> lsDate = new java.util.ArrayList<java.lang.Integer>();

		java.util.List<java.lang.Double> lsQuote = new java.util.ArrayList<java.lang.Double>();

		for (int i = 0; i < iNumComponent; ++i) {
			if (null == aCalibComp[i])
				throw new java.lang.Exception
					("MergedDiscountForwardCurve::proxyManifestMeasure => Cannot locate a component");

			org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapManifestMeasure =
				manifestMeasure (aCalibComp[i].primaryCode());

			if (mapManifestMeasure.containsKey (strManifestMeasure)) {
				lsDate.add (aCalibComp[i].maturityDate().julian());

				lsQuote.add (mapManifestMeasure.get (strManifestMeasure));
			}
		}

		int iNumEstimationComponent = lsDate.size();

		if (0 == iNumEstimationComponent)
			throw new java.lang.Exception
				("MergedDiscountForwardCurve::proxyManifestMeasure => Estimation Components not available");

		if (1 == iNumEstimationComponent) return lsQuote.get (0);

		int iDatePrev = lsDate.get (0);

		if (iDate <= iDatePrev) return lsQuote.get (0);

		for (int i = 1; i < iNumEstimationComponent; ++i) {
			int iDateCurr = lsDate.get (i);

			if (iDatePrev <= iDate && iDate < iDateCurr)
				return iDate - iDatePrev > iDateCurr - iDate ? lsQuote.get (i) : lsQuote.get (i - 1);

			iDatePrev = iDateCurr;
		}

		return lsQuote.get (iNumEstimationComponent - 1);
	}

	@Override public boolean setCCIS (
		final org.drip.analytics.input.CurveConstructionInputSet ccis)
	{
		if (null == ccis) return false;

		_ccis = ccis;
		return true;
	}

	/**
	 * Retrieve the Forward Curve that might be implied by the Latent State of this Discount Curve Instance
	 * 	corresponding to the specified Floating Rate Index
	 * 
	 * @param iDate The Date
	 * @param fri The Floating Rate Index
	 * 
	 * @return The Forward Curve Implied by the Discount Curve Latent State
	 */

	public abstract org.drip.state.forward.ForwardRateEstimator forwardRateEstimator (
		final int iDate,
		final org.drip.state.identifier.ForwardLabel fri);

	/**
	 * Retrieve the Latent State Quantification Metric
	 * 
	 * @return The Latent State Quantification Metric
	 */

	public abstract java.lang.String latentStateQuantificationMetric();

	/**
	 * Retrieve the Manifest Measure Jacobian of the Discount Factor to the given date
	 * 
	 * @param iDate Date
	 * @param strManifestMeasure Manifest Measure
	 * 
	 * @return The Manifest Measure Jacobian of the Discount Factor to the given date
	 */

	public abstract org.drip.numerical.differentiation.WengertJacobian jackDDFDManifestMeasure (
		final int iDate,
		final java.lang.String strManifestMeasure);

	/**
	 * Retrieve the Manifest Measure Jacobian of the Discount Factor to the given date
	 * 
	 * @param dt Date
	 * @param strManifestMeasure Manifest Measure
	 * 
	 * @return The Manifest Measure Jacobian of the Discount Factor to the given date
	 */

	public org.drip.numerical.differentiation.WengertJacobian jackDDFDManifestMeasure (
		final org.drip.analytics.date.JulianDate dt,
		final java.lang.String strManifestMeasure)
	{
		if (null == dt) return null;

		return jackDDFDManifestMeasure (dt.julian(), strManifestMeasure);
	}

	/**
	 * Retrieve the Manifest Measure Jacobian of the Discount Factor to the date implied by the given Tenor
	 * 
	 * @param strTenor Tenor
	 * @param strManifestMeasure Manifest Measure
	 * 
	 * @return The Manifest Measure Jacobian of the Discount Factor to the date implied by the given Tenor
	 */

	public org.drip.numerical.differentiation.WengertJacobian jackDDFDManifestMeasure (
		final java.lang.String strTenor,
		final java.lang.String strManifestMeasure)
	{
		if (null == strTenor || strTenor.isEmpty()) return null;

		try {
			return jackDDFDManifestMeasure (epoch().addTenor (strTenor), strManifestMeasure);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Calculate the Jacobian of PV at the given date to the Manifest Measure of each component in the
	 * 	calibration set to the DF
	 * 
	 * @param iDate Date for which the Jacobian is needed
	 * 
	 * @return The Jacobian
	 */

	public org.drip.numerical.differentiation.WengertJacobian compJackDPVDManifestMeasure (
		final int iDate)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (iDate)) return null;

		org.drip.product.definition.CalibratableComponent[] aCalibComp = calibComp();

		if (null == aCalibComp || 0 == aCalibComp.length) return null;

		int iNumParameters = 0;
		int iNumComponents = aCalibComp.length;
		org.drip.numerical.differentiation.WengertJacobian wjCompPVDF = null;

		org.drip.param.valuation.ValuationParams valParams = org.drip.param.valuation.ValuationParams.Spot
			(iDate);

		org.drip.param.market.CurveSurfaceQuoteContainer csqs =
			org.drip.param.creator.MarketParamsBuilder.Create (this, null, null, null, null, null,
				null, null == _ccis ? null : _ccis.fixing());

		for (int i = 0; i < iNumComponents; ++i) {
			org.drip.numerical.differentiation.WengertJacobian wjCompDDirtyPVDManifestMeasure =
				aCalibComp[i].jackDDirtyPVDManifestMeasure (valParams, null, csqs, null);

			if (null == wjCompDDirtyPVDManifestMeasure) return null;

			iNumParameters = wjCompDDirtyPVDManifestMeasure.numParameters();

			if (null == wjCompPVDF) {
				try {
					wjCompPVDF = new org.drip.numerical.differentiation.WengertJacobian (iNumComponents,
						iNumParameters);
				} catch (java.lang.Exception e) {
					e.printStackTrace();

					return null;
				}
			}

			for (int k = 0; k < iNumParameters; ++k) {
				if (!wjCompPVDF.accumulatePartialFirstDerivative (i, k,
					wjCompDDirtyPVDManifestMeasure.firstDerivative (0, k)))
					return null;
			}
		}

		return wjCompPVDF;
	}

	/**
	 * Calculate the Jacobian of PV at the given date to the Manifest Measure of each component in the
	 * 	calibration set to the DF
	 * 
	 * @param dt Date for which the Jacobian is needed
	 * 
	 * @return The Jacobian
	 */

	public org.drip.numerical.differentiation.WengertJacobian compJackDPVDManifestMeasure (
		final org.drip.analytics.date.JulianDate dt)
	{
		return null == dt ? null : compJackDPVDManifestMeasure (dt.julian());
	}

	/**
	 * Retrieve the Jacobian of the Forward Rate to the Manifest Measure between the given dates
	 * 
	 * @param iDate1 Date 1
	 * @param iDate2 Date 2
	 * @param strManifestMeasure Manifest Measure
	 * @param dblElapsedYear The Elapsed Year (in the appropriate Day Count) between dates 1 and 2
	 * 
	 * @return The Jacobian
	 */

	public org.drip.numerical.differentiation.WengertJacobian jackDForwardDManifestMeasure (
		final int iDate1,
		final int iDate2,
		final java.lang.String strManifestMeasure,
		final double dblElapsedYear)
	{
		if (iDate1 == iDate2) return null;

		org.drip.numerical.differentiation.WengertJacobian wjDDFDManifestMeasureDate1 = jackDDFDManifestMeasure
			(iDate1, strManifestMeasure);

		if (null == wjDDFDManifestMeasureDate1) return null;

		int iNumQuote = wjDDFDManifestMeasureDate1.numParameters();

		if (0 == iNumQuote) return null;

		org.drip.numerical.differentiation.WengertJacobian wjDDFDManifestMeasureDate2 = jackDDFDManifestMeasure
			(iDate2, strManifestMeasure);

		if (null == wjDDFDManifestMeasureDate2 || iNumQuote != wjDDFDManifestMeasureDate2.numParameters())
			return null;

		double dblDF1 = java.lang.Double.NaN;
		double dblDF2 = java.lang.Double.NaN;
		org.drip.numerical.differentiation.WengertJacobian wjDForwardDManifestMeasure = null;

		try {
			dblDF1 = df (iDate1);

			dblDF2 = df (iDate2);

			wjDForwardDManifestMeasure = new org.drip.numerical.differentiation.WengertJacobian (1, iNumQuote);
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		double dblDForwardDManifestMeasure1iScale = 1. / dblDF2;
		double dblDForwardDManifestMeasure2iScale = dblDF1 / (dblDF2 * dblDF2);
		double dblInverseAnnualizedTenorLength = 1. / dblElapsedYear;

		for (int i = 0; i < iNumQuote; ++i) {
			double dblDForwardDQManifestMeasurei = ((wjDDFDManifestMeasureDate1.firstDerivative (0, i) *
				dblDForwardDManifestMeasure1iScale) - (wjDDFDManifestMeasureDate2.firstDerivative (0, i) *
					dblDForwardDManifestMeasure2iScale)) * dblInverseAnnualizedTenorLength;

			if (!wjDForwardDManifestMeasure.accumulatePartialFirstDerivative (0, i,
				dblDForwardDQManifestMeasurei))
				return null;
		}

		return wjDForwardDManifestMeasure;
	}

	/**
	 * Retrieve the Jacobian of the Forward Rate to the Manifest Measure between the given dates
	 * 
	 * @param dt1 Julian Date 1
	 * @param dt2 Julian Date 2
	 * @param strManifestMeasure Manifest Measure
	 * @param dblElapsedYear The Elapsed Year (in the appropriate Day Count) between dates 1 and 2
	 * 
	 * @return The Jacobian
	 */

	public org.drip.numerical.differentiation.WengertJacobian jackDForwardDManifestMeasure (
		final org.drip.analytics.date.JulianDate dt1,
		final org.drip.analytics.date.JulianDate dt2,
		final java.lang.String strManifestMeasure,
		final double dblElapsedYear)
	{
		if (null == dt1 || null == dt2) return null;

		return jackDForwardDManifestMeasure (dt1.julian(), dt2.julian(), strManifestMeasure, dblElapsedYear);
	}

	/**
	 * Retrieve the Jacobian of the Forward Rate to the Manifest Measure at the given date
	 * 
	 * @param dt Given Julian Date
	 * @param strTenor Tenor
	 * @param strManifestMeasure Manifest Measure
	 * @param dblElapsedYear The Elapsed Year (in the appropriate Day Count) implied by the Tenor
	 * 
	 * @return The Jacobian
	 */

	public org.drip.numerical.differentiation.WengertJacobian jackDForwardDManifestMeasure (
		final org.drip.analytics.date.JulianDate dt,
		final java.lang.String strTenor,
		final java.lang.String strManifestMeasure,
		final double dblElapsedYear)
	{
		if (null == dt || null == strTenor || strTenor.isEmpty()) return null;

		return jackDForwardDManifestMeasure (dt.julian(), dt.addTenor (strTenor).julian(),
			strManifestMeasure, dblElapsedYear);
	}

	/**
	 * Retrieve the Jacobian for the Zero Rate to the given date
	 * 
	 * @param iDate Date
	 * @param strManifestMeasure Manifest Measure
	 * 
	 * @return The Jacobian
	 */

	public org.drip.numerical.differentiation.WengertJacobian zeroRateJack (
		final int iDate,
		final java.lang.String strManifestMeasure)
	{
		int iEpochDate = epoch().julian();

		return jackDForwardDManifestMeasure (iEpochDate, iDate, strManifestMeasure, 1. * (iDate - iEpochDate) /
			365.25);
	}

	/**
	 * Retrieve the Jacobian for the Zero Rate to the given date
	 * 
	 * @param dt Julian Date
	 * @param strManifestMeasure Manifest Measure
	 * 
	 * @return The Jacobian
	 */

	public org.drip.numerical.differentiation.WengertJacobian zeroRateJack (
		final org.drip.analytics.date.JulianDate dt,
		final java.lang.String strManifestMeasure)
	{
		return null == dt? null : zeroRateJack (dt.julian(), strManifestMeasure);
	}

	/**
	 * Convert the inferred Formulation Constraint into a "Truthness" Entity
	 * 
	 * @param strLatentStateQuantificationMetric Latent State Quantification Metric
	 * 
	 * @return Map of the Truthness Entities
	 */

	public java.util.Map<java.lang.Integer, java.lang.Double> canonicalTruthness (
		final java.lang.String strLatentStateQuantificationMetric)
	{
		if (null == strLatentStateQuantificationMetric ||
			(!org.drip.analytics.definition.LatentStateStatic.DISCOUNT_QM_ZERO_RATE.equalsIgnoreCase
				(strLatentStateQuantificationMetric) && !
					org.drip.analytics.definition.LatentStateStatic.DISCOUNT_QM_DISCOUNT_FACTOR.equalsIgnoreCase
			(strLatentStateQuantificationMetric)))
			return null;

		org.drip.product.definition.CalibratableComponent[] aCC = calibComp();

		if (null == aCC) return null;

		int iNumComp = aCC.length;
		boolean bFirstCashFlow = true;

		if (0 == iNumComp) return null;

		java.util.Map<java.lang.Integer, java.lang.Double> mapCanonicalTruthness = new
			java.util.TreeMap<java.lang.Integer, java.lang.Double>();

		if (org.drip.analytics.definition.LatentStateStatic.DISCOUNT_QM_DISCOUNT_FACTOR.equalsIgnoreCase
			(strLatentStateQuantificationMetric))
			mapCanonicalTruthness.put (_iEpochDate, 1.);

		for (org.drip.product.definition.CalibratableComponent cc : aCC) {
			if (null == cc) continue;

			java.util.List<org.drip.analytics.cashflow.CompositePeriod> lsCouponPeriod = cc.couponPeriods();

			if (null == lsCouponPeriod || 0 == lsCouponPeriod.size()) continue;

			for (org.drip.analytics.cashflow.CompositePeriod cpnPeriod : lsCouponPeriod) {
				if (null == cpnPeriod) continue;

				int iPeriodPayDate = cpnPeriod.payDate();

				if (iPeriodPayDate >= _iEpochDate) {
					try {
						if (org.drip.analytics.definition.LatentStateStatic.DISCOUNT_QM_DISCOUNT_FACTOR.equalsIgnoreCase
							(strLatentStateQuantificationMetric))
							mapCanonicalTruthness.put (iPeriodPayDate, df (iPeriodPayDate));
						else if (org.drip.analytics.definition.LatentStateStatic.DISCOUNT_QM_ZERO_RATE.equalsIgnoreCase
							(strLatentStateQuantificationMetric)) {
							if (bFirstCashFlow) {
								bFirstCashFlow = false;

								mapCanonicalTruthness.put (_iEpochDate, zero (iPeriodPayDate));
							}

							mapCanonicalTruthness.put (iPeriodPayDate, zero (iPeriodPayDate));
						}
					} catch (java.lang.Exception e) {
						e.printStackTrace();

						return null;
					}
				}
			}
		}

		return mapCanonicalTruthness;
	}
}
