
package org.drip.exposure.regression;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
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
 * <i>AndersenPykhtinSokolSegment</i> generates the Segment Regression Based Exposures off of the
 * corresponding Pillar Vertexes using the Pykhtin (2009) Scheme with the Andersen, Pykhtin, and Sokol (2017)
 * Adjustments applied. The References are:
 *  
 * <br><br>
 *  	<ul>
 *  		<li>
 *  			Andersen, L. B. G., M. Pykhtin, and A. Sokol (2017): Re-thinking Margin Period of Risk
 *  				https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2902737 <b>eSSRN</b>
 *  		</li>
 *  		<li>
 *  			Andersen, L. B. G., M. Pykhtin, and A. Sokol (2017): Credit Exposure in the Presence of
 *  				Initial Margin https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2806156 <b>eSSRN</b>
 *  		</li>
 *  		<li>
 *  			Albanese, C., and L. Andersen (2014): Accounting for OTC Derivatives: Funding Adjustments and
 *  				the Re-Hypothecation Option https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2482955
 *  				<b>eSSRN</b>
 *  		</li>
 *  		<li>
 *  			Burgard, C., and M. Kjaer (2017): Derivatives Funding, Netting, and Accounting
 *  				https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2534011 <b>eSSRN</b>
 *  		</li>
 *  		<li>
 *  			Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives
 *  				Pricing <i>Risk</i> <b>21 (2)</b> 97-102
 *  		</li>
 *  	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ExposureAnalyticsLibrary.md">Exposure Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/README.md">Exposure Group Level Collateralized/Uncollateralized Exposure</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/regression/README.md">Regression Based Path Exposure Generation</a></li>
 *  </ul>
 *  
 * @author Lakshmi Krishnamurthy
 */

public class AndersenPykhtinSokolSegment
{
	private int _epochDate = -1;
	private org.drip.exposure.regression.PillarVertex _leftPillar = null;
	private org.drip.exposure.regression.PillarVertex _rightPillar = null;
	private org.drip.function.definition.R1ToR1 _rightPillarLocalVolatility = null;

	/**
	 * AndersenPykhtinSokolSegment Constructor
	 * 
	 * @param epochDate The Path Epoch Date
	 * @param leftPillar The Left Pillar Vertex
	 * @param rightPillar The Right Pillar Vertex
	 * @param rightPillarLocalVolatility The Right Pillar Local Volatility
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public AndersenPykhtinSokolSegment (
		final int epochDate,
		final org.drip.exposure.regression.PillarVertex leftPillar,
		final org.drip.exposure.regression.PillarVertex rightPillar,
		final org.drip.function.definition.R1ToR1 rightPillarLocalVolatility)
		throws java.lang.Exception
	{
		if (null == (_leftPillar = leftPillar) ||
			null == (_rightPillar = rightPillar) ||
			_leftPillar.date() >= _rightPillar.date() ||
			null == (_rightPillarLocalVolatility = rightPillarLocalVolatility))
		{
			throw new java.lang.Exception ("AndersenPykhtinSokolSegment Constructor => Invalid Inputs");
		}

		_epochDate = epochDate;
	}

	/**
	 * Retrieve the Epoch Date
	 * 
	 * @return The Epoch Date
	 */

	public int epochDate()
	{
		return _epochDate;
	}

	/**
	 * Retrieve the Left Pillar Vertex
	 * 
	 * @return The Left Pillar Vertex
	 */

	public org.drip.exposure.regression.PillarVertex leftPillar()
	{
		return _leftPillar;
	}

	/**
	 * Retrieve the Right Pillar Vertex
	 * 
	 * @return The Right Pillar Vertex
	 */

	public org.drip.exposure.regression.PillarVertex rightPillar()
	{
		return _rightPillar;
	}

	/**
	 * Retrieve the Right Pillar Local Volatility
	 * 
	 * @return The Right Pillar Local Volatility
	 */

	public org.drip.function.definition.R1ToR1 rightPillarLocalVolatility()
	{
		return _rightPillarLocalVolatility;
	}

	/**
	 * Generate the Dense (Complete) Segment Exposures
	 * 
	 * @param denseExposureTrajectory The Dense Exposure Trajectory
	 * @param wanderTrajectory The Wander Date Trajectory
	 * 
	 * @return The Dense (Complete) Segment Exposures
	 */

	public boolean denseExposureTrajectoryUpdate (
		final double[] denseExposureTrajectory,
		final double[] wanderTrajectory)
	{
		if (null == denseExposureTrajectory || null == wanderTrajectory)
		{
			return false;
		}

		int leftPillarDate = _leftPillar.date();

		int rightPillarDate = _rightPillar.date();

		double leftPillarExposure = _leftPillar.exposure();

		double rightPillarExposure = _rightPillar.exposure();

		int dateWidth = rightPillarDate - leftPillarDate;
		double urgency = 1. / dateWidth;
		double localVolatility = java.lang.Double.NaN;
		double localDrift = (rightPillarExposure - leftPillarExposure) * urgency;
		denseExposureTrajectory[leftPillarDate - _epochDate] = leftPillarExposure;
		denseExposureTrajectory[rightPillarDate - _epochDate] = rightPillarExposure;

		try
		{
			localVolatility = _rightPillarLocalVolatility.evaluate (rightPillarExposure);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();

			return false;
		}

		for (int dateIndex = dateWidth - 1; dateIndex > 0; --dateIndex)
		{
			int epochIndex = leftPillarDate + dateIndex - _epochDate;

			denseExposureTrajectory[epochIndex] = rightPillarExposure - localDrift * (dateWidth - dateIndex)
				+ localVolatility * urgency * wanderTrajectory[epochIndex] * java.lang.Math.sqrt (dateIndex *
					(dateWidth - dateIndex));
		}

		return true;
	}
}
