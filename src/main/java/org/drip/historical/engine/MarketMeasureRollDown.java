
package org.drip.historical.engine;

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
 * <i>MarketMeasureRollDown</i> holds the Map of the Market Measure Roll Down Values for the Native as well
 * as the Additional Horizon Tenors.
 * <br><br>
 *  <ul>
 *		<li><b>Module</b>        = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/historical">Historical</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/historical/engine">Engine</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/FixedIncome">Fixed Income Analytics</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class MarketMeasureRollDown {
	private double _dblInnate = java.lang.Double.NaN;

	private org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double> _mapHorizonMetric = new
		org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();

	/**
	 * MarketMeasureRollDown Constructor
	 * 
	 * @param dblInnate The Native Roll Down Market Metric
	 * 
	 * @throws java.lang.Exception Thrown if Inputs are Invalid
	 */

	public MarketMeasureRollDown (
		final double dblInnate)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (_dblInnate = dblInnate))
			throw new java.lang.Exception ("MarketMeasureRollDown Constructor => Invalid Inputs");
	}

	/**
	 * Retrieve the Innate Roll Down Market Measure
	 * 
	 * @return The Innate Roll Down Market Measure
	 */

	public double innate()
	{
		return _dblInnate;
	}

	/**
	 * Add the Custom Horizon Market Measure Roll Down Metric Value
	 * 
	 * @param strHorizon The Custom Horizon
	 * @param dblHorizonRollDown The Custom Horizon Market Measure Roll Down Metric Value
	 * 
	 * @return TRUE - The Custom Horizon Market Measure Roll Down Metric Value successfully set
	 */

	public boolean add (
		final java.lang.String strHorizon,
		final double dblHorizonRollDown)
	{
		if (null == strHorizon || strHorizon.isEmpty() || !org.drip.quant.common.NumberUtil.IsValid
			(dblHorizonRollDown))
			return false;

		_mapHorizonMetric.put (strHorizon, dblHorizonRollDown);

		return true;
	}

	/**
	 * Retrieve the Horizon Market Metric
	 * 
	 * @param strHorizon The Horizon
	 * 
	 * @return The Roll Down Market Metric corresponding to the Horizon
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double horizon (
		final java.lang.String strHorizon)
		throws java.lang.Exception
	{
		if (null == strHorizon || strHorizon.isEmpty() || !_mapHorizonMetric.containsKey (strHorizon))
			throw new java.lang.Exception ("MarketMeasureRollDown::horizon => Invalid Inputs");

		return _mapHorizonMetric.get (strHorizon);
	}

	/**
	 * Retrieve the Roll Down Horizon Metric Map
	 * 
	 * @return The Roll Down Horizon Metric Map
	 */

	public org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double> horizon()
	{
		return _mapHorizonMetric;
	}
}
