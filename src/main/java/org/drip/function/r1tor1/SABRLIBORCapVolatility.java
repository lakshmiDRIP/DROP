
package org.drip.function.r1tor1;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting risk, transaction costs, exposure, margin
 *  	calculations, valuation adjustment, and portfolio construction within and across fixed income,
 *  	credit, commodity, equity, FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three modules:
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
 * 	- Statistical Learning
 * 	- Numerical Optimizer
 * 	- Spline Builder
 * 	- Algorithm Support
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
 * <i>SABRLIBORCapVolatility</i> implements the Deterministic, Non-local Cap Volatility Scheme detailed in:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Rebonato, R., K. McKay, and R. White (2009): <i>The SABR/LIBOR Market Model: Pricing,
 * 				Calibration, and Hedging for Complex Interest-Rate Derivatives</i> <b>John Wiley and Sons</b>
 * 		</li>
 * 	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalOptimizerLibrary.md">Numerical Optimizer</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/feed/README.md">Function</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/feed/r1tor1/README.md">R<sup>1</sup> To R<sup>1</sup></a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class SABRLIBORCapVolatility extends org.drip.function.definition.R1ToR1 {
	private double _dblA = java.lang.Double.NaN;
	private double _dblB = java.lang.Double.NaN;
	private double _dblC = java.lang.Double.NaN;
	private double _dblD = java.lang.Double.NaN;
	private double _dblEpoch = java.lang.Double.NaN;

	/**
	 * SABRLIBORCapVolatility Constructor
	 * 
	 * @param dblEpoch Epoch
	 * @param dblA A
	 * @param dblB B
	 * @param dblC C
	 * @param dblD D
	 * 
	 * @throws java.lang.Exception Thrown if Inputs are Invalid
	 */

	public SABRLIBORCapVolatility (
		final double dblEpoch,
		final double dblA,
		final double dblB,
		final double dblC,
		final double dblD)
		throws java.lang.Exception
	{
		super (null);

		if (!org.drip.quant.common.NumberUtil.IsValid (_dblEpoch = dblEpoch) ||
				!org.drip.quant.common.NumberUtil.IsValid (_dblA = dblA) ||
					!org.drip.quant.common.NumberUtil.IsValid (_dblB = dblB) ||
						!org.drip.quant.common.NumberUtil.IsValid (_dblC = dblC) ||
							!org.drip.quant.common.NumberUtil.IsValid (_dblD = dblD))
			throw new java.lang.Exception ("SABRLIBORCapVolatility ctr: Invalid Inputs");
	}

	@Override public double evaluate (
		final double dblVariate)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblVariate))
			throw new java.lang.Exception ("SABRLIBORCapVolatility::evaluate => Invalid Inputs");

		double dblDateGap = dblVariate - _dblEpoch;

		return (_dblB * dblDateGap + _dblA) * java.lang.Math.exp (-1. * _dblC * dblDateGap) + _dblD;
	}

	/**
	 * Return "A"
	 * 
	 * @return "A"
	 */

	public double A()
	{
		return _dblA;
	}

	/**
	 * Return "B"
	 * 
	 * @return "B"
	 */

	public double B()
	{
		return _dblB;
	}

	/**
	 * Return "C"
	 * 
	 * @return "C"
	 */

	public double C()
	{
		return _dblC;
	}

	/**
	 * Return "D"
	 * 
	 * @return "D"
	 */

	public double D()
	{
		return _dblD;
	}

	/**
	 * Return the Epoch
	 * 
	 * @return The Epoch
	 */

	public double epoch()
	{
		return _dblEpoch;
	}
}
