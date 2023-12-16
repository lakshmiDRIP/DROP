
package org.drip.state.sequence;

import org.drip.analytics.date.JulianDate;
import org.drip.service.template.LatentMarketStateBuilder;
import org.drip.state.curve.BasisSplineGovvieYield;
import org.drip.state.nonlinear.FlatForwardDiscountCurve;

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
 * <i>GovvieBuilderSettings</i> exposes the Functionality to generate a Sequence of Govvie Curve Realizations
 * across Multiple Paths.
 *
 *  <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/README.md">Latent State Inference and Creation Utilities</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/sequence/README.md">Monte Carlo Path State Realizations</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class GovvieBuilderSettings
{
	private String _treasuryCode = "";
	private JulianDate _spotDate = null;
	private String[] _tenorArray = null;
	private double[] _treasuryYieldArray = null;
	private double[] _treasuryCouponArray = null;
	private double[] _forwardYieldGroundArray = null;
	private BasisSplineGovvieYield _groundYieldCurve = null;

	protected static final BasisSplineGovvieYield GovvieCurve (
		final JulianDate spotDate,
		final String code,
		final String[] tenorArray,
		final double[] couponArray,
		final double[] yieldArray)
		throws Exception
	{
		int instrumentCount = tenorArray.length;
		JulianDate[] maturityDateArray = new JulianDate[instrumentCount];
		JulianDate[] effectiveDateArray = new JulianDate[instrumentCount];

		for (int i = 0; i < instrumentCount; ++i) {
			maturityDateArray[i] = (effectiveDateArray[i] = spotDate).addTenor (tenorArray[i]);
		}

		return (BasisSplineGovvieYield) LatentMarketStateBuilder.GovvieCurve (
			code,
			spotDate,
			effectiveDateArray,
			maturityDateArray,
			couponArray,
			yieldArray,
			"Yield",
			LatentMarketStateBuilder.SHAPE_PRESERVING
		);
	}

	/**
	 * GovvieBuilderSettings Constructor
	 * 
	 * @param spotDate The Spot Date
	 * @param treasuryCode The Treasury Code
	 * @param tenorArray Array of Maturity Tenors
	 * @param treasuryCouponArray Array of Treasury Coupon
	 * @param treasuryYieldArray Array of Treasury Yield
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public GovvieBuilderSettings (
		final JulianDate spotDate,
		final String treasuryCode,
		final java.lang.String[] tenorArray,
		final double[] treasuryCouponArray,
		final double[] treasuryYieldArray)
		throws Exception
	{
		if (null == (
			_groundYieldCurve = GovvieCurve (
				_spotDate = spotDate,
				_treasuryCode = treasuryCode,
				_tenorArray = tenorArray,
				_treasuryCouponArray = treasuryCouponArray,
				_treasuryYieldArray = treasuryYieldArray
			)
		)) {
			throw new Exception ("GovvieBuilderSettings Constructor => Invalid Inputs");
		}

		FlatForwardDiscountCurve groundFlatForwardDiscountCurve = _groundYieldCurve.flatForward
			(_tenorArray);

		if (null == groundFlatForwardDiscountCurve ||
			null == (_forwardYieldGroundArray = groundFlatForwardDiscountCurve.nodeValues())
		) {
			throw new Exception ("GovvieBuilderSettings Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Spot Date
	 * 
	 * @return The Spot Date
	 */

	public JulianDate spot()
	{
		return _spotDate;
	}

	/**
	 * Retrieve the Treasury Code
	 * 
	 * @return The Treasury Code
	 */

	public String code()
	{
		return _treasuryCode;
	}

	/**
	 * Retrieve the Treasury Maturity Tenor Array
	 * 
	 * @return The Treasury Maturity Tenor Array
	 */

	public String[] tenors()
	{
		return _tenorArray;
	}

	/**
	 * Retrieve the Calibration Treasury Coupon Array
	 * 
	 * @return The Calibration Treasury Coupon Array
	 */

	public double[] coupon()
	{
		return _treasuryCouponArray;
	}

	/**
	 * Retrieve the Calibration Treasury Yield Array
	 * 
	 * @return The Calibration Treasury Yield Array
	 */

	public double[] yield()
	{
		return _treasuryYieldArray;
	}

	/**
	 * Retrieve the Ground State Govvie Curve
	 * 
	 * @return The Ground State Govvie Curve
	 */

	public BasisSplineGovvieYield groundState()
	{
		return _groundYieldCurve;
	}

	/**
	 * Retrieve the Ground Forward Yield Array
	 * 
	 * @return The Ground Forward Yield Array
	 */

	public double[] groundForwardYield()
	{
		return _forwardYieldGroundArray;
	}

	/**
	 * Retrieve the Calibration Instrument Dimension
	 * 
	 * @return The Calibration Instrument Dimension
	 */

	public int dimension()
	{
		return _tenorArray.length;
	}
}
