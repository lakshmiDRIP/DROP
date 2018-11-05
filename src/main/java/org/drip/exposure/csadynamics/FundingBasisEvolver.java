
package org.drip.exposure.csadynamics;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting risk, transaction costs, exposure, margin
 *  	calculations, and portfolio construction within and across fixed income, credit, commodity, equity,
 *  	FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three main modules:
 *  
 *  - DROP Analytics Core - https://lakshmidrip.github.io/DROP-Analytics-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Numerical Core - https://lakshmidrip.github.io/DROP-Numerical-Core/
 * 
 * 	DROP Analytics Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Asset Backed Analytics
 * 	- XVA Analytics
 * 	- Exposure and Margin Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Numerical Core implements libraries for the following:
 * 	- Statistical Learning Library
 * 	- Numerical Optimizer Library
 * 	- Machine Learning Library
 * 	- Spline Builder Library
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
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
 * <i>FundingBasisEvolver</i> implements a Two Factor Stochastic Funding Model Evolver with a Log Normal
 *  Forward Process and a Mean Reverting Diffusion Process for the Funding Spread. The References are:
 *  
 *  <br>
 *  	<ul>
 *  		<li>
 *  			Antonov, A., and M. Arneguy (2009): Analytical Formulas for Pricing CMS Products in the LIBOR
 *  				Market Model with Stochastic Volatility 
 *  				https://papers.ssrn.com/sol3/Papers.cfm?abstract_id=1352606 <b>eSSRN</b>
 *  		</li>
 *  		<li>
 *  			Burgard, C., and M. Kjaer (2009): Modeling and successful Management of Credit Counter-party
 *  				Risk of Derivative Portfolios <i>ICBI Conference</i> <b>Rome</b>
 *  		</li>
 *  		<li>
 *  			Gregory, J. (2009): Being Two-faced over Counter-party Credit Risk <i>Risk</i> <b>20 (2)</b>
 *  				86-90
 *  		</li>
 *  		<li>
 *  			Johannes, M., and S. Sundaresan (2007): Pricing Collateralized Swaps <i>Journal of
 *  				Finance</i> <b>62</b> 383-410
 *  		</li>
 *  		<li>
 *  			Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives
 *  				Pricing <i>Risk</i> <b>21 (2)</b> 97-102
 *  		</li>
 *  	</ul>
 * 	<br>
 *  <ul>
 *		<li><b>Module</b>        = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure">Exposure</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/csadynamics">CSA Dynamics</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/Exposure">Exposure Analytics</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class FundingBasisEvolver
{
	private double _correlation = java.lang.Double.NaN;
	private org.drip.measure.dynamics.DiffusionEvaluatorLogarithmic _underlyingEvolver = null;
	private org.drip.measure.dynamics.DiffusionEvaluatorMeanReversion _fundingSpreadEvolver = null;

	/**
	 * FundingBasisEvolver Constructor
	 * 
	 * @param underlyingEvolver The Underlying Diffusion Evolver
	 * @param fundingSpreadEvolver The Funding Spread Diffusion Evolver
	 * @param correlation Correlation between the Underlying and the Funding Spread Processes
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public FundingBasisEvolver (
		final org.drip.measure.dynamics.DiffusionEvaluatorLogarithmic underlyingEvolver,
		final org.drip.measure.dynamics.DiffusionEvaluatorMeanReversion fundingSpreadEvolver,
		final double correlation)
		throws java.lang.Exception
	{
		if (null == (_underlyingEvolver = underlyingEvolver) ||
			null == (_fundingSpreadEvolver = fundingSpreadEvolver) ||
			!org.drip.quant.common.NumberUtil.IsValid (_correlation = correlation) ||
			1. < _correlation || -1. > _correlation)
		{
			throw new java.lang.Exception ("FundingBasisEvolver Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Underlying Diffusion Evolver
	 * 
	 * @return The Underlying Diffusion Evolver
	 */

	public org.drip.measure.dynamics.DiffusionEvaluatorLogarithmic underlyingEvolver()
	{
		return _underlyingEvolver;
	}

	/**
	 * Retrieve the Funding Spread Diffusion Evolver
	 * 
	 * @return The Funding Spread Diffusion Evolver
	 */

	public org.drip.measure.dynamics.DiffusionEvaluatorMeanReversion fundingSpreadEvolver()
	{
		return _fundingSpreadEvolver;
	}

	/**
	 * Retrieve the Correlation between the Underlying and the Funding Spread Processes
	 * 
	 * @return The Correlation between the Underlying and the Funding Spread Processes
	 */

	public double underlyingFundingSpreadCorrelation()
	{
		return _correlation;
	}

	/**
	 * Generate the CSA Forward Diffusion Process
	 * 
	 * @return The CSA Forward Diffusion Process
	 */

	public org.drip.measure.process.DiffusionEvolver csaForwardProcess()
	{
		try
		{
			org.drip.measure.dynamics.LocalEvaluator driftEvolver = new
				org.drip.measure.dynamics.LocalEvaluator()
			{
				@Override public double value (
					final org.drip.measure.realization.JumpDiffusionVertex vertex)
					throws java.lang.Exception
				{
					return 0.;
				}
			};

			org.drip.measure.dynamics.LocalEvaluator volatilityEvolver = new
				org.drip.measure.dynamics.LocalEvaluator()
			{
				@Override public double value (
					final org.drip.measure.realization.JumpDiffusionVertex vertex)
					throws java.lang.Exception
				{
					if (null == vertex)
						throw new java.lang.Exception
							("FundingBasisEvolver::CSAForwardVolatility::Evaluator::value => Invalid Inputs");

					return vertex.value() * _underlyingEvolver.volatilityValue();
				}
			};

			return new org.drip.measure.process.DiffusionEvolver (
				new org.drip.measure.dynamics.DiffusionEvaluator (
					driftEvolver,
					volatilityEvolver
				)
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the Funding Numeraire Diffusion Process
	 * 
	 * @param tenor The Tenor of the Underlying Forward
	 * 
	 * @return The Funding Numeraire Diffusion Process
	 */

	public org.drip.measure.process.DiffusionEvolver fundingNumeraireProcess (
		final java.lang.String tenor)
	{
		try {
			double meanReversionSpeed = _fundingSpreadEvolver.meanReversionRate();

			double b = org.drip.analytics.support.Helper.TenorToYearFraction (tenor);

			if (0. != meanReversionSpeed)
			{
				b = (1. - java.lang.Math.exp (-1. * meanReversionSpeed * b)) / meanReversionSpeed;
			}

			final double piterbarg2010BFactor = b;

			org.drip.measure.dynamics.LocalEvaluator driftEvaluator = new
				org.drip.measure.dynamics.LocalEvaluator()
			{
				@Override public double value (
					final org.drip.measure.realization.JumpDiffusionVertex jumpDiffusionVertex)
					throws java.lang.Exception
				{
					return 0.;
				}
			};

			org.drip.measure.dynamics.LocalEvaluator volatilityEvaluator = new
				org.drip.measure.dynamics.LocalEvaluator()
			{
				@Override public double value (
					final org.drip.measure.realization.JumpDiffusionVertex jumpDiffusionVertex)
					throws java.lang.Exception
				{
					if (null == jumpDiffusionVertex)
					{
						throw new java.lang.Exception
							("FundingBasisEvolver::CSAFundingNumeraireVolatility::Evaluator::value => Invalid Inputs");
					}

					return -1. * jumpDiffusionVertex.value() * piterbarg2010BFactor *
						_fundingSpreadEvolver.volatilityValue();
				}
			};

			return new org.drip.measure.process.DiffusionEvolver (
				new org.drip.measure.dynamics.DiffusionEvaluator (
					driftEvaluator,
					volatilityEvaluator
				)
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the Funding Spread Numeraire Diffusion Process
	 * 
	 * @param tenor The Tenor of the Underlying Forward
	 * 
	 * @return The Funding Spread Numeraire Diffusion Process
	 */

	public org.drip.measure.process.DiffusionEvolver fundingSpreadNumeraireProcess (
		final java.lang.String tenor)
	{
		try
		{
			double meanReversionSpeed = _fundingSpreadEvolver.meanReversionRate();

			double b = org.drip.analytics.support.Helper.TenorToYearFraction (tenor);

			if (0. != meanReversionSpeed)
			{
				b = (1. - java.lang.Math.exp (-1. * meanReversionSpeed * b)) / meanReversionSpeed;
			}

			final double piterbarg2010BFactor = b;

			org.drip.measure.dynamics.LocalEvaluator driftEvaluator = new
				org.drip.measure.dynamics.LocalEvaluator()
			{
				@Override public double value (
					final org.drip.measure.realization.JumpDiffusionVertex jumpDiffusionVertex)
					throws java.lang.Exception
				{
					return 0.;
				}
			};

			org.drip.measure.dynamics.LocalEvaluator volatilityEvaluator = new
				org.drip.measure.dynamics.LocalEvaluator()
			{
				@Override public double value (
					final org.drip.measure.realization.JumpDiffusionVertex jumpDiffusionVertex)
					throws java.lang.Exception
				{
					if (null == jumpDiffusionVertex)
					{
						throw new java.lang.Exception
							("FundingBasisEvolver::CSAFundingSpreadNumeraireVolatility::Evaluator::value => Invalid Inputs");
					}

					return -1. * jumpDiffusionVertex.value() * piterbarg2010BFactor *
						_fundingSpreadEvolver.volatilityValue();
				}
			};

			return new org.drip.measure.process.DiffusionEvolver (
				new org.drip.measure.dynamics.DiffusionEvaluator (
					driftEvaluator,
					volatilityEvaluator
				)
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Compute the CSA vs. No CSA Forward Ratio
	 * 
	 * @param tenor The Tenor of the Underlying Forward
	 * 
	 * @return The CSA vs. No CSA Forward Ratio
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double CSANoCSARatio (
		final java.lang.String tenor)
		throws java.lang.Exception
	{
		double underlyingVolatility = _underlyingEvolver.volatilityValue();

		double meanReversionSpeed = _fundingSpreadEvolver.meanReversionRate();

		double fundingSpreadVolatility = _fundingSpreadEvolver.volatilityValue();

		double maturity = org.drip.analytics.support.Helper.TenorToYearFraction (tenor);

		if (0. == meanReversionSpeed)
		{
			return java.lang.Math.exp (-0.5 * _correlation * underlyingVolatility *
				fundingSpreadVolatility * maturity * maturity);
		}

		double b = (1. - java.lang.Math.exp (-1. * meanReversionSpeed * maturity)) /
			meanReversionSpeed;

		return java.lang.Math.exp (-1. * _correlation * underlyingVolatility *
			fundingSpreadVolatility * (maturity - b) / meanReversionSpeed);
	}
}
