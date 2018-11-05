
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
 * <i>NumeraireInducedMeasureShift</i> computes the Shift of the Forward Terminal Distribution between the
 * Non-CSA and the CSA Cases. The References are:
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

public class NumeraireInducedMeasureShift
{
	private double _csaForward = java.lang.Double.NaN;
	private double _noCSAForward = java.lang.Double.NaN;
	private double _terminalVariance = java.lang.Double.NaN;

	/**
	 * NumeraireInducedMeasureShift Constructor
	 * 
	 * @param csaForward The CSA Implied Forward Value
	 * @param noCSAForward The No CSA Implied Forward Value
	 * @param terminalVariance The Terminal Variance of the Underlying
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public NumeraireInducedMeasureShift (
		final double csaForward,
		final double noCSAForward,
		final double terminalVariance)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (_csaForward = csaForward) ||
			!org.drip.quant.common.NumberUtil.IsValid (_noCSAForward = noCSAForward) ||
			!org.drip.quant.common.NumberUtil.IsValid (_terminalVariance = terminalVariance))
		{
			throw new java.lang.Exception ("NumeraireInducedMeasureShift Constructor => Invalid Inputs");
		}
	}

	/**
	 * Return the Value of the Forward Contract under CSA
	 * 
	 * @return The Value of the Forward Contract under CSA
	 */

	public double csaForward()
	{
		return _csaForward;
	}

	/**
	 * Return the Value of the Forward Contract under No CSA Criterion
	 * 
	 * @return The Value of the Forward Contract under No CSA Criterion
	 */

	public double noCSAForward()
	{
		return _noCSAForward;
	}

	/**
	 * Return the Terminal Variance of the Underlying
	 * 
	 * @return The Terminal Variance of the Underlying
	 */

	public double terminalVariance()
	{
		return _terminalVariance;
	}

	/**
	 * Return the Linear Strike Coefficient of the Relative Measure Differential
	 * 
	 * @return The Linear Strike Coefficient of the Relative Measure Differential
	 */

	public double alpha1()
	{
		return (_noCSAForward - _csaForward) / _terminalVariance;
	}

	/**
	 * Return the Constant Strike Coefficient of the Relative Measure Differential
	 * 
	 * @return The Constant Strike Coefficient of the Relative Measure Differential
	 */

	public double alpha0()
	{
		return 1. - alpha1() * _csaForward;
	}

	/**
	 * Compute the No CSA/CSA Density Re-scaling using the Antonov and Arneguy (2009) Linear Proxy Approach
	 * 
	 * @param k The Strike at which the Density Re-scaling is Sought
	 * 
	 * @return The No CSA/CSA Density Re-scaling using the Antonov and Arneguy (2009) Linear Proxy Approach
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double densityRescale (
		final double k)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (k))
		{
			throw new java.lang.Exception ("NumeraireInducedMeasureShift::densityRescale => Invalid Inputs");
		}

		double dblAlpha1 = (_noCSAForward - _csaForward) / _terminalVariance;
		return 1. - dblAlpha1 * _csaForward + dblAlpha1 * k;
	}
}
