
package org.drip.dynamics.evolution;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting analytics/risk, transaction cost analytics,
 *  	asset liability management analytics, capital, exposure, and margin analytics, valuation adjustment
 *  	analytics, and portfolio construction analytics within and across fixed income, credit, commodity,
 *  	equity, FX, and structured products. It also includes auxiliary libraries for algorithm support,
 *  	numerical analysis, numerical optimization, spline builder, model validation, statistical learning,
 *  	graph builder/navigator, and computational support.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three modules:
 *  
 *  - DROP Product Core - https://lakshmidrip.github.io/DROP-Product-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Computational Core - https://lakshmidrip.github.io/DROP-Computational-Core/
 * 
 * 	DROP Product Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Loan Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 *  - Asset Liability Management Analytics
 * 	- Capital Estimation Analytics
 * 	- Exposure Analytics
 * 	- Margin Analytics
 * 	- XVA Analytics
 * 
 * 	DROP Computational Core implements libraries for the following:
 * 	- Algorithm Support
 * 	- Computation Support
 * 	- Function Analysis
 *  - Graph Algorithm
 *  - Model Validation
 * 	- Numerical Analysis
 * 	- Numerical Optimizer
 * 	- Spline Builder
 *  - Statistical Learning
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
 * <i>LSQMPointRecord</i> contains the Record of the Evolving Point Latent State Quantification Metrics.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/dynamics/README.md">HJM, Hull White, LMM, and SABR Dynamic Evolution Models</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/dynamics/evolution/README.md">Latent State Evolution Edges/Vertexes</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class LSQMPointRecord {
	private java.util.Map<java.lang.String, java.util.Map<java.lang.String, java.lang.Double>> _mmLSQMValue =
		new java.util.HashMap<java.lang.String, java.util.Map<java.lang.String, java.lang.Double>>();

	/**
	 * Empty LSQMPointRecord Constructor
	 */

	public LSQMPointRecord()
	{
	}

	/**
	 * Retrieve the Latent State Labels
	 * 
	 * @return The Latent State Labels
	 */

	public java.util.Set<java.lang.String> latentStateLabel()
	{
		return _mmLSQMValue.keySet();
	}

	/**
	 * Indicate if Quantification Metrics are available for the specified Latent State
	 * 
	 * @param lsl The Latent State Label
	 * 
	 * @return TRUE - Quantification Metrics are available for the specified Latent State
	 */

	public boolean containsLatentState (
		final org.drip.state.identifier.LatentStateLabel lsl)
	{
		return null == lsl ? false : _mmLSQMValue.containsKey (lsl.fullyQualifiedName());
	}

	/**
	 * Set the LSQM Value
	 * 
	 * @param lsl The Latent State Label
	 * @param strQM The Quantification Metric
	 * @param dblValue The QM's Value
	 * 
	 * @return TRUE - The QM successfully set
	 */

	public boolean setQM (
		final org.drip.state.identifier.LatentStateLabel lsl,
		final java.lang.String strQM,
		final double dblValue)
	{
		if (null == lsl || null == strQM || strQM.isEmpty() || !org.drip.numerical.common.NumberUtil.IsValid
			(dblValue))
			return false;

		java.lang.String strLatentStateLabel = lsl.fullyQualifiedName();

		java.util.Map<java.lang.String, java.lang.Double> mapLSQM = _mmLSQMValue.containsKey
			(strLatentStateLabel) ? _mmLSQMValue.get (strLatentStateLabel) : new
				java.util.HashMap<java.lang.String, java.lang.Double>();

		mapLSQM.put (strQM, dblValue);

		_mmLSQMValue.put (lsl.fullyQualifiedName(), mapLSQM);

		return true;
	}

	/**
	 * Indicate if the Value for the specified Quantification Metric is available
	 * 
	 * @param lsl The Latent State Label
	 * @param strQM The Quantification Metric
	 * 
	 * @return TRUE - The Requested Value is available
	 */

	public boolean containsQM (
		final org.drip.state.identifier.LatentStateLabel lsl,
		final java.lang.String strQM)
	{
		if (null == lsl || null == strQM || strQM.isEmpty()) return false;

		java.lang.String strLatentStateLabel = lsl.fullyQualifiedName();

		return _mmLSQMValue.containsKey (strLatentStateLabel) && _mmLSQMValue.get
			(strLatentStateLabel).containsKey (strQM);
	}

	/**
	 * Retrieve the specified Quantification Metric Value
	 * 
	 * @param lsl The Latent State Label
	 * @param strQM The Quantification Metric
	 * 
	 * @return The Quantification Metric Value
	 * 
	 * @throws java.lang.Exception Thrown if the Quantification Metric is not available
	 */

	public double qm (
		final org.drip.state.identifier.LatentStateLabel lsl,
		final java.lang.String strQM)
		throws java.lang.Exception
	{
		if (null == lsl || null == strQM || strQM.isEmpty())
			throw new java.lang.Exception ("LSQMPointRecord::qm => Invalid Inputs");

		java.lang.String strLatentStateLabel = lsl.fullyQualifiedName();

		if (!_mmLSQMValue.containsKey (strLatentStateLabel))
			throw new java.lang.Exception ("LSQMPointRecord::qm => Invalid Inputs");

		java.util.Map<java.lang.String, java.lang.Double> mapLSQM = _mmLSQMValue.get (strLatentStateLabel);

		if (!mapLSQM.containsKey (strQM))
			throw new java.lang.Exception ("LSQMPointRecord::qm => No LSQM Entry");

		return mapLSQM.get (strQM);
	}
}
