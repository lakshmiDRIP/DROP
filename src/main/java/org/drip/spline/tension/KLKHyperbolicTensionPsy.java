
package org.drip.spline.tension;

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
 * Copyright (C) 2013 Lakshmi Krishnamurthy
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
 * <i>KLKHyperbolicTensionPsy</i> implements the basic framework and the family of C2 Tension Splines
 * outlined in Koch and Lyche (1989), Koch and Lyche (1993), and Kvasov (2000) Papers.
 * KLKHyperbolicTensionPsy implements the custom evaluator, differentiator, and integrator for the KLK
 * Tension Psy Functions outlined in the publications above.
 *
 *  <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/SplineBuilderLibrary.md">Spline Builder Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline">Spline</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/tension">Tension</a></li>
 *  </ul>
 * <br><br>
 *  
 * @author Lakshmi Krishnamurthy
 */

public class KLKHyperbolicTensionPsy extends org.drip.function.definition.R1ToR1 {
	private double _dblTension = java.lang.Double.NaN;

	/**
	 * KLKHyperbolicTensionPsy constructor
	 * 
	 * @param dblTension Tension of the HyperbolicTension Function
	 * 
	 * @throws java.lang.Exception Thrown if the input is invalid
	 */

	public KLKHyperbolicTensionPsy (
		final double dblTension)
		throws java.lang.Exception
	{
		super (null);

		if (!org.drip.numerical.common.NumberUtil.IsValid (_dblTension = dblTension))
			throw new java.lang.Exception ("KLKHyperbolicTensionPsy ctr: Invalid Inputs");
	}

	@Override public double evaluate (
		final double dblVariate)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblVariate))
			throw new java.lang.Exception ("KLKHyperbolicTensionPsy::evaluate => Invalid Inputs");

		return java.lang.Math.sinh (_dblTension * (1. - dblVariate)) / java.lang.Math.sinh (_dblTension);
	}

	@Override public double derivative (
		final double dblVariate,
		final int iOrder)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblVariate) || 0 > iOrder)
			throw new java.lang.Exception ("KLKHyperbolicTensionPsy::derivative => Invalid Inputs");

		return java.lang.Math.pow (-_dblTension, iOrder) * java.lang.Math.sinh (_dblTension * (1. -
			dblVariate)) / java.lang.Math.sinh (_dblTension);
	}

	@Override public double integrate (
		final double dblBegin,
		final double dblEnd)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblBegin) || !org.drip.numerical.common.NumberUtil.IsValid
			(dblEnd))
			throw new java.lang.Exception ("KLKHyperbolicTensionPsy::integrate => Invalid Inputs");

		return -1. * (java.lang.Math.cosh (_dblTension * (1. - dblEnd)) - java.lang.Math.cosh (_dblTension *
			(1. - dblBegin))) / (_dblTension * java.lang.Math.sinh (_dblTension));
	}

	/**
	 * Retrieve the Tension Parameter
	 * 
	 * @return Tension Parameter
	 */

	public double getTension()
	{
		return _dblTension;
	}

	public static final void main (
		final java.lang.String[] astrArgs)
		throws java.lang.Exception
	{
		KLKHyperbolicTensionPsy khtp = new KLKHyperbolicTensionPsy (2.);

		System.out.println ("KLKHyperbolicTensionPsy[0.0] = " + khtp.evaluate (0.0));

		System.out.println ("KLKHyperbolicTensionPsy[0.5] = " + khtp.evaluate (0.5));

		System.out.println ("KLKHyperbolicTensionPsy[1.0] = " + khtp.evaluate (1.0));

		System.out.println ("KLKHyperbolicTensionPsyDeriv[0.0] = " + khtp.derivative (0.0, 2));

		System.out.println ("KLKHyperbolicTensionPsyDeriv[0.5] = " + khtp.derivative (0.5, 2));

		System.out.println ("KLKHyperbolicTensionPsyDeriv[1.0] = " + khtp.derivative (1.0, 2));
	}
}
