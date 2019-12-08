
package org.drip.function.r1tor1solver;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
 * Copyright (C) 2012 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting analytics/risk, transaction cost analytics,
 *  	asset liability management analytics, capital, exposure, and margin analytics, valuation adjustment
 *  	analytics, and portfolio construction analytics within and across fixed income, credit, commodity,
 *  	equity, FX, and structured products. It also includes auxiliary libraries for algorithm support,
 *  	numerical analysis, numerical optimization, spline builder, model validation, statistical learning,
 *  	and computational support.
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
 * 	- JUnit                    => https://lakshmidrip.github.io/DROP/junit/index.html
 * 	- Jacoco                   => https://lakshmidrip.github.io/DROP/jacoco/index.html
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
 * <i>FixedPointFinderBrent</i> customizes FixedPointFinderBracketing by applying the Brent's scheme of
 * compound variate selector.
 * <br><br>
 * Brent's scheme, as implemented here, is described in http://www.credit-trader.org. This implementation
 * 	retains absolute shifts that have happened to the variate for the past 2 iterations as the discriminant
 * 	that determines the next variate to be generated.
 * <br><br>
 * FixedPointFinderBrent uses the following parameters specified in VariateIterationSelectorParams:
 * <br>
 * <ul>
 * 	<li>
 * 		The Variate Primitive that is regarded as the "fast" method
 * 	</li>
 * 	<li>
 * 		The Variate Primitive that is regarded as the "robust" method
 * 	</li>
 * 	<li>
 * 		The relative variate shift that determines when the "robust" method is to be invoked over the "fast"
 * 	</li>
 * 	<li>
 * 		The lower bound on the variate shift between iterations that serves as the fall-back to the "robust"
 * 	</li>
 * </ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/README.md">R<sup>d</sup> To R<sup>d</sup> Function Analysis</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/r1tor1solver/README.md">Built-in R<sup>1</sup> To R<sup>1</sup> Solvers</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class FixedPointFinderBrent extends org.drip.function.r1tor1solver.FixedPointFinderBracketing {
	private double _dblVariateIterativeShift = java.lang.Double.NaN;
	private double _dblPreviousVariateIterativeShift = java.lang.Double.NaN;
	private org.drip.function.r1tor1solver.VariateIterationSelectorParams _visp = null;

	@Override protected double iterateCompoundVariate (
		final double dblCurrentVariate,
		final double dblContraVariate,
		final double dblCurrentOF,
		final double dblContraPointOF,
		final org.drip.function.r1tor1solver.FixedPointFinderOutput rfop)
		throws java.lang.Exception
	{
		double dblNextVariate = calcNextVariate (dblCurrentVariate, dblContraVariate, dblCurrentOF,
			dblContraPointOF, _visp.getFastVariateIteratorPrimitive(), rfop);

		double dblVariateEstimateShift = java.lang.Math.abs (dblNextVariate - dblCurrentVariate);

		if (org.drip.numerical.common.NumberUtil.IsValid (_dblVariateIterativeShift) ||
			_visp.getRobustVariateIteratorPrimitive() == _iIteratorPrimitive) {
			if (dblVariateEstimateShift < _visp.getRelativeVariateShift() * _dblVariateIterativeShift &&
				_dblVariateIterativeShift > 0.5 * _visp.getVariateShiftLowerBound()) {
				_iIteratorPrimitive = _visp.getFastVariateIteratorPrimitive();

				_dblPreviousVariateIterativeShift = _dblVariateIterativeShift;
				_dblVariateIterativeShift = dblVariateEstimateShift;
				return dblNextVariate;
			}

			_iIteratorPrimitive = _visp.getRobustVariateIteratorPrimitive();

			_dblPreviousVariateIterativeShift = _dblVariateIterativeShift;
			_dblVariateIterativeShift = dblVariateEstimateShift;

			return calcNextVariate (dblCurrentVariate, dblContraVariate, dblCurrentOF, dblContraPointOF,
				_visp.getRobustVariateIteratorPrimitive(), rfop);
		}

		if (org.drip.numerical.common.NumberUtil.IsValid (_dblPreviousVariateIterativeShift) &&
			(dblVariateEstimateShift < _visp.getRelativeVariateShift() * _dblPreviousVariateIterativeShift &&
				_dblPreviousVariateIterativeShift > 0.5 * _visp.getVariateShiftLowerBound())) {
			_iIteratorPrimitive = _visp.getFastVariateIteratorPrimitive();

			_dblPreviousVariateIterativeShift = _dblVariateIterativeShift;
			_dblVariateIterativeShift = dblVariateEstimateShift;
			return dblNextVariate;
		}

		_iIteratorPrimitive = _visp.getRobustVariateIteratorPrimitive();

		_dblPreviousVariateIterativeShift = _dblVariateIterativeShift;
		_dblVariateIterativeShift = dblVariateEstimateShift;

		return calcNextVariate (dblCurrentVariate, dblContraVariate, dblCurrentOF, dblContraPointOF,
			_visp.getRobustVariateIteratorPrimitive(), rfop);
	}

	/**
	 * FixedPointFinderBrent constructor
	 * 
	 * @param dblOFGoal OF Goal
	 * @param of Objective Function
	 * @param bWhine TRUE - Balk on Encountering Exception
	 * 
	 * @throws java.lang.Exception Propogated from below
	 */

	public FixedPointFinderBrent (
		final double dblOFGoal,
		final org.drip.function.definition.R1ToR1 of,
		final boolean bWhine)
		throws java.lang.Exception
	{
		super (dblOFGoal, of, null, org.drip.function.r1tor1solver.VariateIteratorPrimitive.BISECTION, bWhine);

		_visp = new org.drip.function.r1tor1solver.VariateIterationSelectorParams();
	}
}
