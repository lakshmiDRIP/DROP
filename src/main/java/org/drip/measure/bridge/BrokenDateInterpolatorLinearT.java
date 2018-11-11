
package org.drip.measure.bridge;

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
 * <i>BrokenDateInterpolatorLinearT</i> Interpolates using Two Stochastic Value Nodes with Linear Scheme. The
 * Scheme is Linear in Time.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module</b>        = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure">Measure</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/bridge">Bridge</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/FixedIncome">Fixed Income Analytics</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class BrokenDateInterpolatorLinearT implements org.drip.measure.bridge.BrokenDateInterpolator {
	private double _dblT1 = java.lang.Double.NaN;
	private double _dblT2 = java.lang.Double.NaN;
	private double _dblV1 = java.lang.Double.NaN;
	private double _dblV2 = java.lang.Double.NaN;

	/**
	 * BrokenDateInterpolatorLinearT Constructor
	 * 
	 * @param dblT1 T1
	 * @param dblT2 T2
	 * @param dblV1 V1
	 * @param dblV2 V2
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public BrokenDateInterpolatorLinearT (
		final double dblT1,
		final double dblT2,
		final double dblV1,
		final double dblV2)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (_dblT1 = dblT1) ||
			!org.drip.quant.common.NumberUtil.IsValid (_dblT2 = dblT2) ||
				!org.drip.quant.common.NumberUtil.IsValid (_dblV1 = dblV1) ||
					!org.drip.quant.common.NumberUtil.IsValid (_dblV2 = dblV2)|| _dblT1 >= _dblT2)
			throw new java.lang.Exception ("BrokenDateInterpolatorLinearT Constructor => Invalid Inputs");
	}

	/**
	 * Retrieve T1
	 * 
	 * @return T1
	 */

	public double t1()
	{
		return _dblT1;
	}

	/**
	 * Retrieve T2
	 * 
	 * @return T2
	 */

	public double t2()
	{
		return _dblT2;
	}

	/**
	 * Retrieve V1
	 * 
	 * @return V1
	 */

	public double v1()
	{
		return _dblV1;
	}

	/**
	 * Retrieve V2
	 * 
	 * @return V2
	 */

	public double v2()
	{
		return _dblV2;
	}

	@Override public double interpolate (
		final double dblT)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblT) || dblT < _dblT1 || dblT > _dblT2)
			throw new java.lang.Exception ("BrokenDateInterpolatorLinearT::interpolate => Invalid Inputs");

		return ((_dblT2 - dblT) * _dblV1 + (dblT - _dblT1) * _dblV2) / (_dblT2 - _dblT1);
	}
}
