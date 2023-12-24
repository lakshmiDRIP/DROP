
package org.drip.state.forward;

import org.drip.analytics.date.JulianDate;
import org.drip.analytics.definition.Curve;
import org.drip.analytics.input.CurveConstructionInputSet;
import org.drip.analytics.support.CaseInsensitiveTreeMap;
import org.drip.numerical.differentiation.WengertJacobian;
import org.drip.param.definition.ManifestMeasureTweak;
import org.drip.product.definition.CalibratableComponent;
import org.drip.state.identifier.ForwardLabel;
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
 * <i>ForwardCurve</i> is the stub for the forward curve functionality. It extends the Curve object by
 * exposing the following functions:
 *
 *  <br><br>
 *  <ul>
 *  	<li>The name/epoch of the forward rate instance</li>
 *  	<li>The index/currency/tenor associated with the forward rate instance</li>
 *  	<li>Forward Rate to a specific date/tenor</li>
 *  	<li>Generate scenario tweaked Latent State from the base forward curve corresponding to mode adjusted (flat/parallel/custom) manifest measure/quantification metric</li>
 *  	<li>Retrieve array of latent state manifest measure, instrument quantification metric, and the array of calibration components</li>
 *  	<li>Set/retrieve curve construction input instrument sets</li>
 *  	<li>Retrieve the Manifest Measure Jacobian of the Forward Rate to the date</li>
 *  </ul>
 *
 *  <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/README.md">Latent State Inference and Creation Utilities</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/forward/README.md">Forward Latent State Curve Estimator</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public abstract class ForwardCurve implements ForwardRateEstimator, Curve
{
	private ForwardLabel _forwardLabel = null;
	private int _epochDate = Integer.MIN_VALUE;

	protected ForwardCurve (
		final int epochDate,
		final ForwardLabel forwardLabel)
		throws Exception
	{
		if (null == (_forwardLabel = forwardLabel)) {
			throw new Exception ("ForwardCurve ctr: Invalid Inputs");
		}

		_epochDate = epochDate;
	}

	@Override public LatentStateLabel label()
	{
		return _forwardLabel;
	}

	@Override public String currency()
	{
		return _forwardLabel.currency();
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

	@Override public String tenor()
	{
		return _forwardLabel.tenor();
	}

	@Override public ForwardLabel index()
	{
		return _forwardLabel;
	}

	@Override public double forward (
		final JulianDate date)
		throws Exception
	{
		if (null == date) {
			throw new Exception ("ForwardRate::forward got null for date");
		}

		return forward (date);
	}

	@Override public double forward (
		final String tenor)
		throws Exception
	{
		if (null == tenor || tenor.isEmpty()) {
			throw new Exception ("ForwardRate::forward got bad tenor");
		}

		return forward (epoch().addTenor (tenor));
	}

	@Override public boolean setCCIS (
		final CurveConstructionInputSet curveConstructionInputSet)
	{
		return true;
	}

	@Override public CalibratableComponent[] calibComp()
	{
		return null;
	}

	@Override public CaseInsensitiveTreeMap<Double> manifestMeasure (
		final String instrumentCode)
	{
		return null;
	}

	@Override public LatentState parallelShiftManifestMeasure (
		final String manifestMeasure,
		final double shift)
	{
		return null;
	}

	@Override public org.drip.state.representation.LatentState shiftManifestMeasure (
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
