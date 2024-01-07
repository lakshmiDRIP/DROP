
package org.drip.spline.params;

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
 * <i>SegmentInelasticDesignControl</i> implements basis per-segment inelastic parameter set. It exports the
 * 	following functionality:
 *
 * <br>
 *  <ul>
 * 		<li>Create the C<sup>2</sup> Inelastic Design Params</li>
 * 		<li>Create the Inelastic Design Parameters for the desired C<sup>k</sup> Criterion and the Roughness Penalty Order</li>
 * 		<li><i>SegmentInelasticDesignControl</i> Constructor</li>
 * 		<li>Retrieve the Continuity Order</li>
 * 		<li>Retrieve the Length Penalty Parameters</li>
 * 		<li>Retrieve the Curvature Penalty Parameters</li>
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
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/README.md">Basis Splines and Linear Compounders across a Broad Family of Spline Basis Functions</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/params/README.md">Spline Segment Construction Control Parameters</a></td></tr>
 *  </table>
 *  <br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class SegmentInelasticDesignControl
{
	private int _k = -1;
	private SegmentFlexurePenaltyControl _lengthSegmentFlexurePenaltyControl = null;
	private SegmentFlexurePenaltyControl _curvatureSegmentFlexurePenaltyControl = null;

	/**
	 * Create the C<sup>2</sup> Inelastic Design Params
	 * 
	 * @return <i>SegmentInelasticDesignControl</i> instance
	 */

	public static final SegmentInelasticDesignControl MakeC2DesignInelasticControl()
	{
		try {
			return new SegmentInelasticDesignControl (2, null, new SegmentFlexurePenaltyControl (2, 1.));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Create the Inelastic Design Parameters for the desired C<sup>k</sup> Criterion and the Roughness
	 * 	Penalty Order
	 * 
	 * @param k Continuity Order
	 * @param curvaturePenaltyDerivativeOrder Curvature Penalty Derivative Order
	 * 
	 * @return <i>SegmentInelasticDesignControl</i> instance
	 */

	public static final SegmentInelasticDesignControl Create (
		final int k,
		final int curvaturePenaltyDerivativeOrder)
	{
		try {
			return new SegmentInelasticDesignControl (
				k,
				null,
				new SegmentFlexurePenaltyControl (curvaturePenaltyDerivativeOrder, 1.)
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Constructor for the Segment Inelastic Design Parameters given the desired C<sup>k</sup>, the Segment
	 * 	Length and the Roughness Penalty Order
	 * 
	 * @param k Continuity Order
	 * @param lengthSegmentFlexurePenaltyControl Segment Length Penalty
	 * @param curvatureSegmentFlexurePenaltyControl Segment Curvature Penalty
	 * 
	 * @throws Exception Thrown if the Inputs are invalid
	 */

	public SegmentInelasticDesignControl (
		final int k,
		final org.drip.spline.params.SegmentFlexurePenaltyControl lengthSegmentFlexurePenaltyControl,
		final org.drip.spline.params.SegmentFlexurePenaltyControl curvatureSegmentFlexurePenaltyControl)
		throws Exception
	{
		if (0 > (_k = k)) {
			throw new Exception ("SegmentInelasticDesignControl ctr: Invalid Inputs");
		}

		_lengthSegmentFlexurePenaltyControl = lengthSegmentFlexurePenaltyControl;
		_curvatureSegmentFlexurePenaltyControl = curvatureSegmentFlexurePenaltyControl;
	}

	/**
	 * Retrieve the Continuity Order
	 * 
	 * @return The Continuity Order
	 */

	public int Ck()
	{
		return _k;
	}

	/**
	 * Retrieve the Length Penalty Parameters
	 * 
	 * @return The Length Penalty Parameters
	 */

	public SegmentFlexurePenaltyControl lengthPenaltyControl()
	{
		return _lengthSegmentFlexurePenaltyControl;
	}

	/**
	 * Retrieve the Curvature Penalty Parameters
	 * 
	 * @return The Curvature Penalty Parameters
	 */

	public SegmentFlexurePenaltyControl curvaturePenaltyControl()
	{
		return _curvatureSegmentFlexurePenaltyControl;
	}
}
