
package org.drip.service.api;

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
 * <i>InstrMetric</i> contains the fields that hold the result of the PnL metric calculations.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AlgorithmSupportLibrary.md">Algorithm Support Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service">Service</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/api">API</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class InstrMetric {
	private org.drip.service.api.ForwardRates _fwdMetric = null;
	private org.drip.service.api.ProductDailyPnL _pnlMetric = null;

	/**
	 * InstrMetric constructor
	 * 
	 * @param fwdMetric The Forward Rates Metric
	 * @param pnlMetric The Daily Carry/Roll PnL Metric
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public InstrMetric (
		final org.drip.service.api.ForwardRates fwdMetric,
		final org.drip.service.api.ProductDailyPnL pnlMetric)
		throws java.lang.Exception
	{
		if (null == (_fwdMetric = fwdMetric) || null == (_pnlMetric = pnlMetric))
			throw new java.lang.Exception ("InstrMetric ctr: Invalid Inputs");
	}

	/**
	 * Retrieve the Forward Metric
	 * 
	 * @return The Forward Metric
	 */

	public org.drip.service.api.ForwardRates fwdMetric()
	{
		return _fwdMetric;
	}

	/**
	 * Retrieve the PnL Metric
	 * 
	 * @return The PnL Metric
	 */

	public org.drip.service.api.ProductDailyPnL pnlMetric()
	{
		return _pnlMetric;
	}

	/**
	 * Reduce the PnL/forward metrics to an array
	 * 
	 * @return The Array containing the PnL/forward metrics
	 */

	public double[] toArray()
	{
		double[] adblPnLMetric = _pnlMetric.toArray();

		double[] adblFwdMetric = _fwdMetric.toArray();

		int i = 0;
		double[] adblInstrMetric = new double[adblFwdMetric.length + adblPnLMetric.length];

		for (double dbl : adblPnLMetric)
			adblInstrMetric[i++] = dbl;

		for (double dbl : adblFwdMetric)
			adblInstrMetric[i++] = dbl;

		return adblInstrMetric;
	}

	@Override public java.lang.String toString()
	{
		java.lang.StringBuffer sb = new java.lang.StringBuffer();

		boolean bStart = true;

		for (double dbl : toArray()) {
			if (bStart)
				bStart = false;
			else
				sb.append (",");

			sb.append (dbl);
		}

		return sb.toString();
	}
}
