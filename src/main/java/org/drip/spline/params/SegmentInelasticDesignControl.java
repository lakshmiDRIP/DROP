
package org.drip.spline.params;

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
 * <i>SegmentInelasticDesignControl</i> implements basis per-segment inelastic parameter set. It exports the
 * following functionality:
 *
 * <br><br>
 *  <ul>
 *  	<li>
 *  		Retrieve the Continuity Order.
 *  	</li>
 *  	<li>
 *  		Retrieve the Length Penalty and the Curvature Penalty Parameters.
 *  	</li>
 *  	<li>
 *  		Create the C2 Inelastic Design Parameters.
 *  	</li>
 *  	<li>
 *  		Create the Inelastic Design Parameters for the desired Ck Criterion and the Roughness Penalty.
 *  			Order.
 *  	</li>
 *  </ul>
 *
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/SplineBuilderLibrary.md">Spline Builder Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/README.md">Basis Splines and Linear Compounders across a Broad Family of Spline Basis Functions</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/params/README.md">Spline Segment Construction Control Parameters</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class SegmentInelasticDesignControl {
	private int _iCk = -1;
	private org.drip.spline.params.SegmentFlexurePenaltyControl _sfpcLength = null;
	private org.drip.spline.params.SegmentFlexurePenaltyControl _sfpcCurvature = null;

	/**
	 * Create the C2 Inelastic Design Params
	 * 
	 * @return SegmentInelasticDesignControl instance
	 */

	public static final SegmentInelasticDesignControl MakeC2DesignInelasticControl()
	{
		try {
			return new SegmentInelasticDesignControl (2, null, new
				org.drip.spline.params.SegmentFlexurePenaltyControl (2, 1.));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Create the Inelastic Design Parameters for the desired Ck Criterion and the Roughness Penalty Order
	 * 
	 * @param iCk Continuity Order
	 * @param iCurvaturePenaltyDerivativeOrder Curvature Penalty Derivative Order
	 * 
	 * @return SegmentInelasticDesignControl instance
	 */

	public static final SegmentInelasticDesignControl Create (
		final int iCk,
		final int iCurvaturePenaltyDerivativeOrder)
	{
		try {
			return new SegmentInelasticDesignControl (iCk, null, new
				org.drip.spline.params.SegmentFlexurePenaltyControl (iCurvaturePenaltyDerivativeOrder, 1.));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Constructor for the Segment Inelastic Design Parameters given the desired Ck, the Segment Length and
	 *  the Roughness Penalty Order
	 * 
	 * @param iCk Continuity Order
	 * @param sfpcLength Segment Length Penalty
	 * @param sfpcCurvature Segment Curvature Penalty
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are invalid
	 */

	public SegmentInelasticDesignControl (
		final int iCk,
		final org.drip.spline.params.SegmentFlexurePenaltyControl sfpcLength,
		final org.drip.spline.params.SegmentFlexurePenaltyControl sfpcCurvature)
		throws java.lang.Exception
	{
		if (0 > (_iCk = iCk))
			throw new java.lang.Exception ("SegmentInelasticDesignControl ctr: Invalid Inputs");

		_sfpcLength = sfpcLength;
		_sfpcCurvature = sfpcCurvature;
	}

	/**
	 * Retrieve the Continuity Order
	 * 
	 * @return The Continuity Order
	 */

	public int Ck()
	{
		return _iCk;
	}

	/**
	 * Retrieve the Length Penalty Parameters
	 * 
	 * @return The Length Penalty Parameters
	 */

	public org.drip.spline.params.SegmentFlexurePenaltyControl lengthPenaltyControl()
	{
		return _sfpcLength;
	}

	/**
	 * Retrieve the Curvature Penalty Parameters
	 * 
	 * @return The Curvature Penalty Parameters
	 */

	public org.drip.spline.params.SegmentFlexurePenaltyControl curvaturePenaltyControl()
	{
		return _sfpcCurvature;
	}
}
