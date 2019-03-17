
package org.drip.function.definition;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
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
 * <i>UnitVector</i> implements the Normalized R<sup>d</sup> Unit Vector.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalOptimizerLibrary.md">Numerical Optimizer</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/feed/README.md">Function</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/feed/definition/README.md">Definition</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class UnitVector {
	private double[] _adblComponent;

	/**
	 * Construct an Instance of the Unit Vector from the Input Vector
	 * 
	 * @param adbl The Input Double Vector
	 * 
	 * @return The Unit Vector Instance
	 */

	public static final UnitVector Standard (
		final double[] adbl)
	{
		if (null == adbl) return null;

		int iDimension = adbl.length;
		double dblGradientModulus = 0.;
		double[] adblComponent = new double[iDimension];

		if (0 == iDimension) return null;

		for (int i = 0; i < iDimension; ++i) {
			if (!org.drip.numerical.common.NumberUtil.IsValid (adbl[i])) return null;

			dblGradientModulus += adbl[i] * adbl[i];
		}

		if (0. == dblGradientModulus) return null;

		dblGradientModulus = java.lang.Math.sqrt (dblGradientModulus);

		for (int i = 0; i < iDimension; ++i)
			adblComponent[i] = adbl[i] / dblGradientModulus;

		return new UnitVector (adblComponent);
	}

	protected UnitVector (
		final double[] adblComponent)
	{
		_adblComponent = adblComponent;
	}

	/**
	 * Retrieve the Unit Vector's Component Array
	 * 
	 * @return The Unit Vector's Component Array
	 */

	public double[] component()
	{
		return _adblComponent;
	}

	/**
	 * Compute the Directional Increment along the Vector
	 * 
	 * @param adblVariate The Starting R^d Variate
	 * @param dblStepLength The Step Length
	 * 
	 * @return The Directionally Incremented Vector
	 */

	public double[] directionalIncrement (
		final double[] adblVariate,
		final double dblStepLength)
	{
		if (null == adblVariate || !org.drip.numerical.common.NumberUtil.IsValid (dblStepLength)) return null;

		int iVariateDimension = adblVariate.length;
		double[] adblIncrementedVariate = new double[iVariateDimension];

		if (iVariateDimension != _adblComponent.length) return null;

		for (int i = 0; i < iVariateDimension; ++i) {
			if (!org.drip.numerical.common.NumberUtil.IsValid (adblVariate[i])) return null;

			adblIncrementedVariate[i] = adblVariate[i] + dblStepLength * _adblComponent[i];
		}

		return adblIncrementedVariate;
	}
}
