
package org.drip.measure.process;

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
 * <i>DiffusionEvolver</i> implements the Functionality that guides the Single Factor R<sup>1</sup> Diffusion
 * Random Process Variable Evolution.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/README.md">R<sup>d</sup> Continuous/Discrete Probability Measures</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/process/README.md">Jump Diffusion Evolver Process Variants</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class DiffusionEvolver {
	private org.drip.measure.dynamics.DiffusionEvaluator _de = null;

	/**
	 * DiffusionEvolver Constructor
	 * 
	 * @param de The Diffusion Evaluator Instance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public DiffusionEvolver (
		final org.drip.measure.dynamics.DiffusionEvaluator de)
		throws java.lang.Exception
	{
		if (null == (_de = de))
			throw new java.lang.Exception ("DiffusionEvolver Constructor => Invalid Inputs");
	}

	/**
	 * Retrieve the Diffusion Evaluator
	 * 
	 * @return The Diffusion Evaluator
	 */

	public org.drip.measure.dynamics.DiffusionEvaluator evaluator()
	{
		return _de;
	}

	/**
	 * Generate the JumpDiffusionEdge Instance from the specified Jump Diffusion Instance
	 * 
	 * @param jdv The JumpDiffusionVertex Instance
	 * @param jdeu The Random Unit Realization
	 * @param dblTimeIncrement The Time Increment Evolution Unit
	 * 
	 * @return The JumpDiffusionEdge Instance
	 */

	public org.drip.measure.realization.JumpDiffusionEdge increment (
		final org.drip.measure.realization.JumpDiffusionVertex jdv,
		final org.drip.measure.realization.JumpDiffusionEdgeUnit jdeu,
		final double dblTimeIncrement)
	{
		if (null == jdv || null == jdeu || !org.drip.numerical.common.NumberUtil.IsValid (dblTimeIncrement))
			return null;

		double dblPreviousValue = jdv.value();

		try {
			org.drip.measure.dynamics.LocalEvaluator leVolatility = _de.localVolatilityEvaluator();

			return org.drip.measure.realization.JumpDiffusionEdge.Standard (dblPreviousValue,
				_de.localDriftEvaluator().value (jdv) * dblTimeIncrement, null == leVolatility ? 0. : leVolatility.value
					(jdv) * jdeu.diffusion() * java.lang.Math.sqrt (java.lang.Math.abs (dblTimeIncrement)),
						null, jdeu);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the JumpDiffusionEdge Instance Backwards from the specified Jump Diffusion Instance
	 * 
	 * @param jdv The JumpDiffusionVertex Instance
	 * @param jdeu The Random Unit Realization
	 * @param dblTimeIncrement The Time Increment Evolution Unit
	 * 
	 * @return The Reverse JumpDiffusionEdge Instance
	 */

	public org.drip.measure.realization.JumpDiffusionEdge incrementReverse (
		final org.drip.measure.realization.JumpDiffusionVertex jdv,
		final org.drip.measure.realization.JumpDiffusionEdgeUnit jdeu,
		final double dblTimeIncrement)
	{
		if (null == jdv || null == jdeu || !org.drip.numerical.common.NumberUtil.IsValid (dblTimeIncrement))
			return null;

		double dblPreviousValue = jdv.value();

		try {
			org.drip.measure.dynamics.LocalEvaluator leVolatility = _de.localVolatilityEvaluator();

			return org.drip.measure.realization.JumpDiffusionEdge.Standard (dblPreviousValue, -1. *
				_de.localDriftEvaluator().value (jdv) * dblTimeIncrement, null == leVolatility ? 0. : -1. *
					leVolatility.value (jdv) * jdeu.diffusion() * java.lang.Math.sqrt (java.lang.Math.abs
						(dblTimeIncrement)), null, jdeu);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the Array of Adjacent JumpDiffusionEdge from the specified Random Variate Array
	 * 
	 * @param jdv The JumpDiffusionVertex Instance
	 * @param aJDEU Array of Random Unit Realizations
	 * @param dblTimeIncrement The Time Increment Evolution Unit
	 * 
	 * @return The Array of Adjacent JumpDiffusionEdge
	 */

	public org.drip.measure.realization.JumpDiffusionEdge[] incrementSequence (
		final org.drip.measure.realization.JumpDiffusionVertex jdv,
		final org.drip.measure.realization.JumpDiffusionEdgeUnit[] aJDEU,
		final double dblTimeIncrement)
	{
		if (null == aJDEU) return null;

		int iNumTimeStep = aJDEU.length;
		org.drip.measure.realization.JumpDiffusionVertex jdvIter = jdv;
		org.drip.measure.realization.JumpDiffusionEdge[] aJDE = 0 == iNumTimeStep ? null : new
			org.drip.measure.realization.JumpDiffusionEdge[iNumTimeStep];

		if (0 == iNumTimeStep) return null;

		for (int i = 0; i < iNumTimeStep; ++i) {
			if (null == (aJDE[i] = increment (jdvIter, aJDEU[i], dblTimeIncrement))) return null;

			try {
				boolean bJumpOccurred = false;
				double dblHazardIntegral = 0.;

				org.drip.measure.realization.StochasticEdgeJump sej = aJDE[i].stochasticJumpEdge();

				if (null != sej) {
					bJumpOccurred = sej.jumpOccurred();

					dblHazardIntegral = sej.hazardIntegral();
				}

				jdvIter = new org.drip.measure.realization.JumpDiffusionVertex (jdvIter.time() +
					dblTimeIncrement, aJDE[i].finish(), jdvIter.cumulativeHazardIntegral() +
						dblHazardIntegral, bJumpOccurred || jdvIter.jumpOccurred());
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		return aJDE;
	}

	/**
	 * Generate the Array of JumpDiffusionVertex Snaps from the specified Random Variate Array
	 * 
	 * @param jdv The JumpDiffusionVertex Instance
	 * @param aJDEU Array of Random Unit Realizations
	 * @param dblTimeIncrement The Time Increment Evolution Unit
	 * 
	 * @return The Array of JumpDiffusionVertex Snaps
	 */

	public org.drip.measure.realization.JumpDiffusionVertex[] vertexSequence (
		final org.drip.measure.realization.JumpDiffusionVertex jdv,
		final org.drip.measure.realization.JumpDiffusionEdgeUnit[] aJDEU,
		final double dblTimeIncrement)
	{
		if (null == aJDEU) return null;

		int iNumVertex = aJDEU.length + 1;
		org.drip.measure.realization.JumpDiffusionVertex jdvPrev = jdv;
		org.drip.measure.realization.JumpDiffusionVertex[] aJDV = new
			org.drip.measure.realization.JumpDiffusionVertex[iNumVertex];
		aJDV[0] = jdv;

		for (int i = 0; i < iNumVertex - 1; ++i) {
			org.drip.measure.realization.JumpDiffusionEdge jde = increment (jdvPrev, aJDEU[i],
				dblTimeIncrement);

			if (null == jde) return null;

			try {
				org.drip.measure.realization.StochasticEdgeJump sej = jde.stochasticJumpEdge();

				boolean bJumpOccurred = false;
				double dblHazardIntegral = 0.;

				if (null != sej) {
					bJumpOccurred = sej.jumpOccurred();

					dblHazardIntegral = sej.hazardIntegral();
				}

				jdvPrev = aJDV[i + 1] = new org.drip.measure.realization.JumpDiffusionVertex (jdvPrev.time()
					+ dblTimeIncrement, jde.finish(), jdvPrev.cumulativeHazardIntegral() + dblHazardIntegral,
						bJumpOccurred || jdvPrev.jumpOccurred());
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		return aJDV;
	}

	/**
	 * Generate the Array of JumpDiffusionVertex Snaps from the specified Random Variate Array
	 * 
	 * @param jdv The JumpDiffusionVertex Instance
	 * @param aJDEU Array of Random Unit Realizations
	 * @param adblTimeIncrement Array of Time Increment Evolution Units
	 * 
	 * @return The Array of JumpDiffusionVertex Snaps
	 */

	public org.drip.measure.realization.JumpDiffusionVertex[] vertexSequence (
		final org.drip.measure.realization.JumpDiffusionVertex jdv,
		final org.drip.measure.realization.JumpDiffusionEdgeUnit[] aJDEU,
		final double[] adblTimeIncrement)
	{
		if (null == aJDEU || null == adblTimeIncrement) return null;

		int iNumVertex = aJDEU.length + 1;
		org.drip.measure.realization.JumpDiffusionVertex jdvPrev = jdv;
		org.drip.measure.realization.JumpDiffusionVertex[] aJDV = new
			org.drip.measure.realization.JumpDiffusionVertex[iNumVertex];
		aJDV[0] = jdv;

		if (iNumVertex != adblTimeIncrement.length + 1) return null;

		for (int i = 0; i < iNumVertex - 1; ++i) {
			org.drip.measure.realization.JumpDiffusionEdge jde = increment (jdvPrev, aJDEU[i],
				adblTimeIncrement[i]);

			if (null == jde) return null;

			try {
				org.drip.measure.realization.StochasticEdgeJump sej = jde.stochasticJumpEdge();

				boolean bJumpOccurred = false;
				double dblHazardIntegral = 0.;

				if (null != sej) {
					bJumpOccurred = sej.jumpOccurred();

					dblHazardIntegral = sej.hazardIntegral();
				}

				jdvPrev = aJDV[i + 1] = new org.drip.measure.realization.JumpDiffusionVertex (jdvPrev.time()
					+ adblTimeIncrement[i], jde.finish(), jdvPrev.cumulativeHazardIntegral() +
						dblHazardIntegral, bJumpOccurred || jdvPrev.jumpOccurred());
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		return aJDV;
	}

	/**
	 * Generate the Array of JumpDiffusionVertex Snaps Backwards from the specified Random Variate Array
	 * 
	 * @param jdv The JumpDiffusionVertex Instance
	 * @param aJDEU Array of Random Unit Realizations
	 * @param adblTimeIncrement Array of Time Increment Evolution Units
	 * 
	 * @return The Array of Reverse JumpDiffusionVertex Snaps
	 */

	public org.drip.measure.realization.JumpDiffusionVertex[] vertexSequenceReverse (
		final org.drip.measure.realization.JumpDiffusionVertex jdv,
		final org.drip.measure.realization.JumpDiffusionEdgeUnit[] aJDEU,
		final double[] adblTimeIncrement)
	{
		if (null == aJDEU || null == adblTimeIncrement) return null;

		int iNumVertex = aJDEU.length + 1;
		org.drip.measure.realization.JumpDiffusionVertex jdvPrev = jdv;
		org.drip.measure.realization.JumpDiffusionVertex[] aJDV = new
			org.drip.measure.realization.JumpDiffusionVertex[iNumVertex];
		aJDV[iNumVertex - 1] = jdv;

		if (iNumVertex != adblTimeIncrement.length + 1) return null;

		for (int i = iNumVertex - 2; i >= 0; --i) {
			org.drip.measure.realization.JumpDiffusionEdge jde = incrementReverse (jdvPrev, aJDEU[i],
				adblTimeIncrement[i]);

			if (null == jde) return null;

			try {
				org.drip.measure.realization.StochasticEdgeJump sej = jde.stochasticJumpEdge();

				boolean bJumpOccurred = false;
				double dblHazardIntegral = 0.;

				if (null != sej) {
					bJumpOccurred = sej.jumpOccurred();

					dblHazardIntegral = sej.hazardIntegral();
				}

				jdvPrev = aJDV[i] = new org.drip.measure.realization.JumpDiffusionVertex (jdvPrev.time() -
					adblTimeIncrement[i], jde.finish(), jdvPrev.cumulativeHazardIntegral() +
						dblHazardIntegral, bJumpOccurred || jdvPrev.jumpOccurred());
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		return aJDV;
	}

	/**
	 * Generate the Adjacent JumpDiffusionEdge Instance from the specified Random Variate and a Weiner Driver
	 * 
	 * @param jdv The JumpDiffusionVertex Instance
	 * @param dblTimeIncrement The Time Increment Evolution Unit
	 * 
	 * @return The Adjacent JumpDiffusionEdge Instance
	 */

	public org.drip.measure.realization.JumpDiffusionEdge weinerIncrement (
		final org.drip.measure.realization.JumpDiffusionVertex jdv,
		final double dblTimeIncrement)
	{
		try {
			return increment (jdv, org.drip.measure.realization.JumpDiffusionEdgeUnit.GaussianDiffusion
				(dblTimeIncrement), dblTimeIncrement);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the Adjacent JumpDiffusionEdge Instance from the specified Random Variate and a Jump Driver
	 * 
	 * @param jdv The JumpDiffusionVertex Instance
	 * @param dblTimeIncrement The Time Increment Evolution Unit
	 * 
	 * @return The Adjacent JumpDiffusionEdge Instance
	 */

	public org.drip.measure.realization.JumpDiffusionEdge jumpIncrement (
		final org.drip.measure.realization.JumpDiffusionVertex jdv,
		final double dblTimeIncrement)
	{
		return increment (jdv, org.drip.measure.realization.JumpDiffusionEdgeUnit.UniformJump
			(dblTimeIncrement), dblTimeIncrement);
	}

	/**
	 * Generate the Adjacent JumpDiffusionEdge Instance from the specified Random Variate and Jump/Weiner
	 * 	Drivers
	 * 
	 * @param jdv The JumpDiffusionVertex Instance
	 * @param dblTimeIncrement The Time Increment Evolution Unit
	 * 
	 * @return The Adjacent JumpDiffusionEdge Instance
	 */

	public org.drip.measure.realization.JumpDiffusionEdge jumpWeinerIncrement (
		final org.drip.measure.realization.JumpDiffusionVertex jdv,
		final double dblTimeIncrement)
	{
		try {
			return increment (jdv, new org.drip.measure.realization.JumpDiffusionEdgeUnit (dblTimeIncrement,
				org.drip.measure.gaussian.NormalQuadrature.Random(), java.lang.Math.random()),
					dblTimeIncrement);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the Adjacent JumpDiffusionEdge Instance from the specified Random Variate and Weiner/Jump
	 * 	Drivers
	 * 
	 * @param jdv The JumpDiffusionVertex Instance
	 * @param dblTimeIncrement The Time Increment Evolution Unit
	 * 
	 * @return The Adjacent JumpDiffusionEdge Instance
	 */

	public org.drip.measure.realization.JumpDiffusionEdge weinerJumpIncrement (
		final org.drip.measure.realization.JumpDiffusionVertex jdv,
		final double dblTimeIncrement)
	{
		try {
			return increment (jdv, new org.drip.measure.realization.JumpDiffusionEdgeUnit (dblTimeIncrement,
				org.drip.measure.gaussian.NormalQuadrature.Random(), java.lang.Math.random()),
					dblTimeIncrement);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
