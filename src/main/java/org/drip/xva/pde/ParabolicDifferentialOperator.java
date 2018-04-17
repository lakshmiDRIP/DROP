
package org.drip.xva.pde;

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
 * ParabolicDifferentialOperator sets up the Parabolic Differential Equation based on the Ito Evolution
 * 	Differential for the Reference Underlier Asset, as laid out in Burgard and Kjaer (2014). The References
 * 	are:
 *  
 *  - Burgard, C., and M. Kjaer (2014): PDE Representations of Derivatives with Bilateral Counter-party Risk
 *  	and Funding Costs, Journal of Credit Risk, 7 (3) 1-19.
 *  
 *  - Cesari, G., J. Aquilina, N. Charpillon, X. Filipovic, G. Lee, and L. Manda (2009): Modeling, Pricing,
 *  	and Hedging Counter-party Credit Exposure - A Technical Guide, Springer Finance, New York.
 *  
 *  - Gregory, J. (2009): Being Two-faced over Counter-party Credit Risk, Risk 20 (2) 86-90.
 *  
 *  - Li, B., and Y. Tang (2007): Quantitative Analysis, Derivatives Modeling, and Trading Strategies in the
 *  	Presence of Counter-party Credit Risk for the Fixed Income Market, World Scientific Publishing,
 *  	Singapore.
 * 
 *  - Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives Pricing, Risk
 *  	21 (2) 97-102.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class ParabolicDifferentialOperator
{
	private org.drip.exposure.evolver.PrimarySecurity _tradeable = null;

	/**
	 * ParabolicDifferentialOperator Constructor
	 * 
	 * @param tradeable The Tradeable Position
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public ParabolicDifferentialOperator (
		final org.drip.exposure.evolver.PrimarySecurity tradeable)
		throws java.lang.Exception
	{
		if (null == (_tradeable = tradeable))
		{
			throw new java.lang.Exception ("ParabolicDifferentialOperator Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Tradeable Position
	 * 
	 * @return The Tradeable Position
	 */

	public org.drip.exposure.evolver.PrimarySecurity asset()
	{
		return _tradeable;
	}

	/**
	 * Compute the Theta for the Derivative from the Asset Edge Value
	 * 
	 * @param evolutionTrajectoryVertex The Derivative's Evolution Trajectory Vertex
	 * @param positionValueVertex The Position Value Vertex
	 * 
	 * @return The Theta
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double theta (
		final org.drip.xva.derivative.EvolutionTrajectoryVertex evolutionTrajectoryVertex,
		final double positionValueVertex)
		throws java.lang.Exception
	{
		if (null == evolutionTrajectoryVertex ||
			!org.drip.quant.common.NumberUtil.IsValid (positionValueVertex))
		{
			throw new java.lang.Exception ("ParabolicDifferentialOperator::theta => Invalid Inputs");
		}

		org.drip.xva.derivative.PositionGreekVertex positionGreekVertex =
			evolutionTrajectoryVertex.positionGreekVertex();

		double volatility = _tradeable.evolver().evaluator().volatility().value (
			new org.drip.measure.realization.JumpDiffusionVertex (
				evolutionTrajectoryVertex.time(),
				positionValueVertex,
				0.,
				false
			)
		);

		return
			0.5 * volatility * volatility * positionValueVertex * positionValueVertex *
				positionGreekVertex.derivativeXVAValueGamma() -
			_tradeable.cashAccumulationRate() * positionValueVertex *
				positionGreekVertex.derivativeXVAValueDelta();
	}

	/**
	 * Compute the Up/Down Thetas
	 *  
	 * @param evolutionTrajectoryVertex The Derivative's Evolution Trajectory Vertex
	 * @param positionValueVertex The Asset Numeraire Vertex Value
	 * @param shift The Amount to Shift the Reference Underlier Numeraire By
	 * 
	 * @return The Array of the Up/Down Thetas
	 */

	public double[] thetaUpDown (
		final org.drip.xva.derivative.EvolutionTrajectoryVertex evolutionTrajectoryVertex,
		final double positionValueVertex,
		final double shift)
	{
		if (null == evolutionTrajectoryVertex ||
			!org.drip.quant.common.NumberUtil.IsValid (positionValueVertex) ||
			!org.drip.quant.common.NumberUtil.IsValid (shift))
		{
			return null;
		}

		org.drip.xva.derivative.PositionGreekVertex positionGreekVertex =
			evolutionTrajectoryVertex.positionGreekVertex();

		double positionValueVertexDown = positionValueVertex - shift;
		double positionValueVertexUp = positionValueVertex + shift;
		double volatility = java.lang.Double.NaN;

		try
		{
			volatility = _tradeable.evolver().evaluator().volatility().value (
				new org.drip.measure.realization.JumpDiffusionVertex (
					evolutionTrajectoryVertex.time(),
					positionValueVertex,
					0.,
					false
				)
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();

			return null;
		}

		double gammaCoefficient = 0.5 * volatility * volatility *
			positionGreekVertex.derivativeXVAValueGamma();

		double deltaCoefficient = -1. * _tradeable.cashAccumulationRate() *
			positionGreekVertex.derivativeXVAValueDelta();

		return new double[]
		{
			gammaCoefficient * positionValueVertexDown * positionValueVertexDown + deltaCoefficient *
				positionValueVertexDown,
			gammaCoefficient * positionValueVertex * positionValueVertex + deltaCoefficient *
				positionValueVertex,
			gammaCoefficient * positionValueVertexUp * positionValueVertexUp + deltaCoefficient *
				positionValueVertexUp
		};
	}
}
