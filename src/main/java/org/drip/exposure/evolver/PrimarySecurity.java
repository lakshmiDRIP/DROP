
package org.drip.exposure.evolver;

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
 * <i>PrimarySecurity</i> holds Definitions and Parameters that specify a Primary Security in XVA Terms. The
 *  References are:
 *  
 *  <br>
 *  	<ul>
 *  		<li>
 *  			Burgard, C., and M. Kjaer (2014): PDE Representations of Derivatives with Bilateral Counter-
 *  				party Risk and Funding Costs <i>Journal of Credit Risk</i> <b>7 (3)</b> 1-19
 *  		</li>
 *  		<li>
 *  			Cesari, G., J. Aquilina, N. Charpillon, X. Filipovic, G. Lee, and L. Manda (2009): Modeling,
 *  				Pricing, and Hedging Counter-party Credit Exposure - A Technical Guide <i>Springer
 *  					Finance</i> <b>New York</b>
 *  		</li>
 *  		<li>
 *  			Gregory, J. (2009): Being Two-faced over Counter-party Credit Risk <i>Risk</i> <b>20 (2)</b>
 *  				86-90
 *  		</li>
 *  		<li>
 *  			Li, B., and Y. Tang (2007): Quantitative Analysis, Derivatives Modeling, and Trading
 *  				Strategies in the Presence of Counter-party Credit Risk for the Fixed Income Market
 *  				<i>World Scientific Publishing </i> <b>Singapore</b>
 *  		</li>
 *  		<li>
 *  			Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives
 *  				Pricing <i>Risk</i> <b>21 (2)</b> 97-102
 *  		</li>
 *  		<li>
 *  	</ul>
 * 	<br>
 *  <ul>
 *		<li><b>Module</b>        = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure">Exposure</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/evolver">Evolver</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/Exposure">Exposure Analytics</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class PrimarySecurity extends org.drip.exposure.evolver.TerminalLatentState
{
	private java.lang.String _id = "";
	private double _repoRate = java.lang.Double.NaN;

	/**
	 * PrimarySecurity Constructor
	 * 
	 * @param id The Security ID
	 * @param label The Latent State Label
	 * @param evolver The Primary Security Evolver
	 * @param repoRate The Repo Rate
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public PrimarySecurity (
		final java.lang.String id,
		final org.drip.state.identifier.LatentStateLabel label,
		final org.drip.measure.process.DiffusionEvolver evolver,
		final double repoRate)
		throws java.lang.Exception
	{
		super (
			label,
			evolver
		);

		if (null == (_id = id) || _id.isEmpty() ||
			!org.drip.quant.common.NumberUtil.IsValid (_repoRate = repoRate))
		{
			throw new java.lang.Exception ("PrimarySecurity Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Security ID
	 * 
	 * @return The Repo Rate
	 */

	public java.lang.String id()
	{
		return _id;
	}

	/**
	 * Retrieve the Repo Rate
	 * 
	 * @return The Repo Rate
	 */

	public double repoRate()
	{
		return _repoRate;
	}

	/**
	 * Indicate if the PrimarySecurity is Repo-able
	 * 
	 * @return TRUE - The PrimarySecurity is Repo-able
	 */

	public boolean isRepoable()
	{
		return 0. != _repoRate;
	}

	/**
	 * Retrieve the Cash Accumulation Rate
	 * 
	 * @return The Cash Accumulation Rate
	 */

	public double cashAccumulationRate()
	{
		return -1. * _repoRate;
	}
}
