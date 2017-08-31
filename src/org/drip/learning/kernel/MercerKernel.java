
package org.drip.learning.kernel;

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
 * MercerKernal exposes the Functionality behind the Eigenized Kernel that is Normed R^x X Normed R^x To
 *  Supremum R^1.
 *  
 *  The References are:
 *  
 *  1) Ash, R. (1965): Information Theory, Inter-science New York.
 *  
 *  2) Konig, H. (1986): Eigenvalue Distribution of Compact Operators, Birkhauser, Basel, Switzerland. 
 *  
 *  3) Smola, A. J., A. Elisseff, B. Scholkopf, and R. C. Williamson (2000): Entropy Numbers for Convex
 *  	Combinations and mlps, in: Advances in Large Margin Classifiers, A. Smola, P. Bartlett, B. Scholkopf,
 *  	and D. Schuurmans - editors, MIT Press, Cambridge, MA.
 *
 * @author Lakshmi Krishnamurthy
 */

public class MercerKernel extends org.drip.learning.kernel.SymmetricRdToNormedR1Kernel {
	private org.drip.learning.kernel.IntegralOperatorEigenContainer _ioec = null;

	/**
	 * MercerKernel Constructor
	 * 
	 * @param ioec The Container of the Eigen Components
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public MercerKernel (
		final org.drip.learning.kernel.IntegralOperatorEigenContainer ioec)
		throws java.lang.Exception
	{
		super (ioec.inputMetricVectorSpace(), ioec.outputMetricVectorSpace());

		_ioec = ioec;
	}

	/**
	 * Retrieve the Suite of Eigen Components
	 * 
	 * @return The Suite of Eigen Components
	 */

	public org.drip.learning.kernel.IntegralOperatorEigenContainer eigenComponentSuite()
	{
		return _ioec;
	}

	@Override public double evaluate (
		final double[] adblX,
		final double[] adblY)
		throws java.lang.Exception
	{
		org.drip.learning.kernel.IntegralOperatorEigenComponent[] aEigenComp = _ioec.eigenComponents();

		double dblValue = 0.;
		int iNumEigenComp = aEigenComp.length;

		for (int i = 0; i < iNumEigenComp; ++i)
			dblValue += aEigenComp[i].evaluate (adblX, adblY);

		return dblValue;
	}
}
