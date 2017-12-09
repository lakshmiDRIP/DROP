
package org.drip.coverage.portfolioconstruction;

import org.drip.sample.assetallocationexcel.CMVMonthlyReconciler01;
import org.drip.sample.assetallocationexcel.CMVMonthlyReconciler02;
import org.drip.sample.assetallocationexcel.CMVMonthlyReconciler03;
import org.drip.sample.assetallocationexcel.CMVMonthlyReconciler04;
import org.drip.sample.assetallocationexcel.CMVMonthlyReconciler05;
import org.drip.sample.assetallocationexcel.CMVMonthlyReconciler06;
import org.drip.sample.assetallocationexcel.CMVMonthlyReconciler07;
import org.drip.sample.assetallocationexcel.CMVMonthlyReconciler08;
import org.drip.sample.assetallocationexcel.CMVMonthlyReconciler09;
import org.drip.sample.assetallocationexcel.CMVMonthlyReconciler10;
import org.drip.sample.assetallocationexcel.CMVReconciler1;
import org.drip.sample.assetallocationexcel.CMVReconciler3;
import org.drip.sample.assetallocationexcel.CMVReconciler4;
import org.drip.sample.assetallocationexcel.CMVReconciler5;
import org.drip.sample.assetallocationexcel.CMVReconciler6;
import org.drip.sample.assetallocationexcel.CMVReconciler7;
import org.drip.sample.assetallocationexcel.CMVReconciler8;

import org.junit.Test;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * 
 *  This file is part of DRIP, a free-software/open-source library for buy/side financial/trading model
 *  	libraries targeting analysts and developers
 *  	https://lakshmidrip.github.io/DRIP/
 *  
 *  DRIP is composed of four main libraries:
 *  
 *  - DRIP Fixed Income - https://lakshmidrip.github.io/DRIP-Fixed-Income/
 *  - DRIP Asset Allocation - https://lakshmidrip.github.io/DRIP-Asset-Allocation/
 *  - DRIP Numerical Optimizer - https://lakshmidrip.github.io/DRIP-Numerical-Optimizer/
 *  - DRIP Statistical Learning - https://lakshmidrip.github.io/DRIP-Statistical-Learning/
 * 
 *  - DRIP Fixed Income: Library for Instrument/Trading Conventions, Treasury Futures/Options,
 *  	Funding/Forward/Overnight Curves, Multi-Curve Construction/Valuation, Collateral Valuation and XVA
 *  	Metric Generation, Calibration and Hedge Attributions, Statistical Curve Construction, Bond RV
 *  	Metrics, Stochastic Evolution and Option Pricing, Interest Rate Dynamics and Option Pricing, LMM
 *  	Extensions/Calibrations/Greeks, Algorithmic Differentiation, and Asset Backed Models and Analytics.
 * 
 *  - DRIP Asset Allocation: Library for model libraries for MPT framework, Black Litterman Strategy
 *  	Incorporator, Holdings Constraint, and Transaction Costs.
 * 
 *  - DRIP Numerical Optimizer: Library for Numerical Optimization and Spline Functionality.
 * 
 *  - DRIP Statistical Learning: Library for Statistical Evaluation and Machine Learning.
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
 * AssetAllocationExcel holds the JUnit Code Coverage Tests for Excel Comparisons of Asset Allocation
 *  Portfolio Construction Module.
 *
 * @author Lakshmi Krishnamurthy
 */

public class AssetAllocationExcel
{
	@Test public void codeCoverageTest() throws Exception
	{
		CMVMonthlyReconciler01.main (null);

		CMVMonthlyReconciler02.main (null);

		CMVMonthlyReconciler03.main (null);

		CMVMonthlyReconciler04.main (null);

		CMVMonthlyReconciler05.main (null);

		CMVMonthlyReconciler06.main (null);

		CMVMonthlyReconciler07.main (null);

		CMVMonthlyReconciler08.main (null);

		CMVMonthlyReconciler09.main (null);

		CMVMonthlyReconciler10.main (null);

		CMVReconciler1.main (null);

		// CMVReconciler2.main (null);

		CMVReconciler3.main (null);

		CMVReconciler4.main (null);

		CMVReconciler5.main (null);

		CMVReconciler6.main (null);

		CMVReconciler7.main (null);

		CMVReconciler8.main (null);
	}
}
