
package org.drip.sample.algo;

import java.util.List;

import org.drip.quant.common.FormatUtil;
import org.drip.spaces.big.SubMatrixSetExtractor;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
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
 * SubMatrixSetStringExtraction demonstrates the Extraction and Usage of the Inner Sub-matrices of a given
 * 	Master Matrix.
 *
 * @author Lakshmi Krishnamurthy
 */

public class SubMatrixSetExtraction {

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		int iSize = 4;
		double[][] aadbl = new double[iSize][iSize];

		for (int i = 0; i < iSize; ++i) {
			for (int j = 0; j < iSize; ++j)
				aadbl[i][j] = Math.random() - 0.5;
		}

		System.out.println ();

		for (int iStartRow = 0; iStartRow < iSize; ++iStartRow) {
			for (int iStartColumn = 0; iStartColumn < iSize; ++iStartColumn) {
				double dblMaxCompositeSum = -1. * iSize * iSize;

				List<double[][]> lsSubMatrix = SubMatrixSetExtractor.SquareSubMatrixList (
					aadbl,
					iStartRow,
					iStartColumn
				);

				for (double[][] aadblSubMatrix : lsSubMatrix) {
					double dblCompositeSum = SubMatrixSetExtractor.CompositeValue (aadblSubMatrix);

					if (dblCompositeSum > dblMaxCompositeSum) dblMaxCompositeSum = dblCompositeSum;
				}

				double dblMaxCompositeSumCombined = SubMatrixSetExtractor.MaxCompositeSubMatrix (
					aadbl,
					iStartRow,
					iStartColumn
				);

				double dblMaxCompositeSumLean = SubMatrixSetExtractor.LeanMaxCompositeSubMatrix (
					aadbl,
					iStartRow,
					iStartColumn
				);

				System.out.println (
					"\tMax[" + iStartRow + "][" + iStartColumn + "] => " +
					FormatUtil.FormatDouble (dblMaxCompositeSum, 1, 4, 1.) + " | " +
					FormatUtil.FormatDouble (dblMaxCompositeSumCombined, 1, 4, 1.) + " | " +
					FormatUtil.FormatDouble (dblMaxCompositeSumLean, 1, 4, 1.)
				);
			}
		}

		System.out.println ();
	}
}
