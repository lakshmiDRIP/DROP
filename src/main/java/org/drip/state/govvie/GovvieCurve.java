
package org.drip.state.govvie;

import org.drip.analytics.date.JulianDate;
import org.drip.analytics.daycount.ActActDCParams;
import org.drip.analytics.daycount.Convention;
import org.drip.analytics.input.CurveConstructionInputSet;
import org.drip.analytics.support.CaseInsensitiveTreeMap;
import org.drip.analytics.support.Helper;
import org.drip.numerical.differentiation.WengertJacobian;
import org.drip.param.definition.ManifestMeasureTweak;
import org.drip.product.definition.CalibratableComponent;
import org.drip.state.discount.DiscountCurve;
import org.drip.state.identifier.GovvieLabel;
import org.drip.state.identifier.LatentStateLabel;
import org.drip.state.representation.LatentState;

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
 * <i>GovvieCurve</i> is the Stub for the Govvie Curve for the specified Govvie/Treasury. It implements the
 *  following Functionality.
 * 
 *  <ul>
 *		<li>Retrieve the Yield Frequency</li>
 *		<li>Retrieve the Yield Day Count</li>
 *		<li>Retrieve the Manifest Measure Jacobian of the Forward Rate to the given date</li>
 *		<li>Retrieve the Manifest Measure Jacobian of the Forward Rate to the date implied by the given Tenor</li>
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
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/govvie/README.md">Govvie Latent State Curve Estimator</a></td></tr>
 *  </table>
 *
 * @author Lakshmi Krishnamurthy
 */

public abstract class GovvieCurve extends DiscountCurve implements YieldEstimator
{
	private static final int NUM_DF_QUADRATURES = 5;

	private int _frequency = 2;
	private String _currency = "";
	private String _treasuryCode = "";
	protected int _epochDate = Integer.MIN_VALUE;
	private String _dayCountConvention = "DCAct_Act_UST";
	protected CurveConstructionInputSet _curveConstructionInputSet = null;

	protected GovvieCurve (
		final int epochDate,
		final String treasuryCode,
		final String currency)
		throws Exception
	{
		if (null == (_treasuryCode = treasuryCode) || _treasuryCode.isEmpty() ||
			null == (_currency = currency) || _currency.isEmpty()) {
			throw new Exception ("GovvieCurve ctr: Invalid Inputs");
		}

		_epochDate = epochDate;
	}

	@Override public JulianDate epoch()
	{
		return new JulianDate (_epochDate);
	}

	@Override public String currency()
	{
		return _currency;
	}

	@Override public LatentStateLabel label()
	{
		return GovvieLabel.Standard (_treasuryCode);
	}

	@Override public double yld (
		final JulianDate date)
		throws Exception
	{
		if (null == date) {
			throw new Exception ("GovvieCurve::yld => Invalid Inputs");
		}

		return yld (date.julian());
	}

	@Override public double yld (
		final String tenor)
		throws Exception
	{
		return yld (epoch().addTenor (tenor));
	}

	@Override public double forwardYield (
		final int date1,
		final int date2)
		throws Exception
	{
		if (date1 >= date2) {
			throw new Exception ("GovvieCurve::forwardYield => Invalid Inputs");
		}

		ActActDCParams actActDCParams = ActActDCParams.FromFrequency (_frequency);

		return Helper.DF2Yield (
			_frequency,
			Helper.Yield2DF (
				_frequency,
				yld (date2),
				Convention.YearFraction (
					_epochDate,
					date2,
					_dayCountConvention,
					false,
					actActDCParams,
					_currency
				)
			) / Helper.Yield2DF (
				_frequency,
				yld (date1),
				Convention.YearFraction (
					_epochDate,
					date1,
					_dayCountConvention,
					false,
					actActDCParams,
					_currency
				)
			),
			Convention.YearFraction (
				date1,
				date2,
				_dayCountConvention,
				false,
				actActDCParams,
				_currency
			)
		);
	}

	@Override public double df (
		final int date)
		throws Exception
	{
		return Helper.Yield2DF (
			_frequency,
			yld (date),
			Convention.YearFraction (
				_epochDate,
				date,
				_dayCountConvention,
				false,
				ActActDCParams.FromFrequency (_frequency),
				_currency
			)
		);
	}

	@Override public double df (
		final JulianDate date)
		throws Exception
	{
		if (null == date) {
			throw new Exception ("GovvieCurve::df => Invalid Inputs");
		}

		return df (date.julian());
	}

	@Override public double df (
		final String tenor)
		throws Exception
	{
		return df (new JulianDate (_epochDate).addTenor (tenor));
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
			throw new Exception ("GovvieCurve::effectiveDF => Got null for date");
		}

		return effectiveDF (date1.julian(), date2.julian());
	}

	@Override public double effectiveDF (
		final String tenor1,
		final String tenor2)
		throws Exception
	{
		if (null == tenor1 || tenor1.isEmpty() || null == tenor2 || tenor2.isEmpty()) {
			throw new Exception ("GovvieCurve::effectiveDF => Got bad tenor");
		}

		JulianDate startDate = epoch();

		return effectiveDF (startDate.addTenor (tenor1), startDate.addTenor (tenor2));
	}

	@Override public double yieldDF (
		final int iDate,
		final double dblDCF)
		throws java.lang.Exception
	{
		return org.drip.analytics.support.Helper.Yield2DF (_frequency, yld (iDate), dblDCF);
	}

	@Override public boolean setCCIS (
		final CurveConstructionInputSet curveConstructionInputSet)
	{
		_curveConstructionInputSet = curveConstructionInputSet;
		return true;
	}

	@Override public CalibratableComponent[] calibComp()
	{
		return null;
	}

	@Override public CaseInsensitiveTreeMap<Double> manifestMeasure (
		final String instrument)
	{
		return null;
	}

	@Override public LatentState parallelShiftManifestMeasure (
		final String manifestMeasure,
		final double shift)
	{
		return null;
	}

	@Override public LatentState shiftManifestMeasure (
		final int spanIndex,
		final String manifestMeasure,
		final double shift)
	{
		return null;
	}

	@Override public LatentState customTweakManifestMeasure (
		final String manifestMeasure,
		final ManifestMeasureTweak manifestMeasureTweak)
	{
		return null;
	}

	@Override public LatentState parallelShiftQuantificationMetric (
		final double shift)
	{
		return null;
	}

	@Override public LatentState customTweakQuantificationMetric (
		final ManifestMeasureTweak manifestMeasureTweak)
	{
		return null;
	}

	/**
	 * Retrieve the Yield Frequency
	 * 
	 * @return The Yield Frequency
	 */

	public int freq()
	{
		return _frequency;
	}

	/**
	 * Retrieve the Yield Day Count
	 * 
	 * @return The Yield Day Count
	 */

	public String dayCount()
	{
		return _dayCountConvention;
	}

	/**
	 * Retrieve the Manifest Measure Jacobian of the Forward Rate to the given date
	 * 
	 * @param manifestMeasure Manifest Measure
	 * @param date Date
	 * 
	 * @return The Manifest Measure Jacobian of the Forward Rate to the given date
	 */

	public abstract WengertJacobian jackDForwardDManifestMeasure (
		final String manifestMeasure,
		final int date
	);

	/**
	 * Retrieve the Manifest Measure Jacobian of the Forward Rate to the given date
	 * 
	 * @param manifestMeasure Manifest Measure
	 * @param date Date
	 * 
	 * @return The Manifest Measure Jacobian of the Forward Rate to the given date
	 */

	public WengertJacobian jackDForwardDManifestMeasure (
		final String manifestMeasure,
		final JulianDate date)
	{
		return null == date ? null : jackDForwardDManifestMeasure (manifestMeasure, date.julian());
	}

	/**
	 * Retrieve the Manifest Measure Jacobian of the Forward Rate to the date implied by the given Tenor
	 * 
	 * @param manifestMeasure Manifest Measure
	 * @param tenor Tenor
	 * 
	 * @return The Manifest Measure Jacobian of the Forward Rate to the date implied by the given Tenor
	 */

	public WengertJacobian jackDForwardDManifestMeasure (
		final String manifestMeasure,
		final String tenor)
	{
		try {
			return null == tenor || tenor.isEmpty() ? null :
				jackDForwardDManifestMeasure (manifestMeasure, epoch().addTenor (tenor));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
