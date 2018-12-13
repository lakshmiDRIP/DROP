
package org.drip.exposure.regression;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
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
 * <i>PykhtinBrownianBridgeSegment</i> generates the Segment Regression Based Exposures off of the
 * corresponding Pillar Vertexes using the Pykhtin (2009) Scheme. The References are:
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
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ExposureAnalyticsLibrary.md">Exposure Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/README.md">Exposure</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/regression/README.md">Regression</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class PykhtinBrownianBridgeSegment
{
	private org.drip.exposure.regression.PillarVertex _leftPillar = null;
	private org.drip.exposure.regression.PillarVertex _rightPillar = null;
	private org.drip.function.definition.R1ToR1 _rightPillarLocalVolatility = null;

	/**
	 * PykhtinBrownianBridgeSegment Constructor
	 * 
	 * @param leftPillar The Left Pillar Vertex
	 * @param rightPillar The Right Pillar Vertex
	 * @param rightPillarLocalVolatility The Right Pillar Local Volatility
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public PykhtinBrownianBridgeSegment (
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
			throw new java.lang.Exception ("PykhtinBrownianBridgeSegment Constructor => Invalid Inputs");
		}
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
		final java.util.Map<java.lang.Integer, java.lang.Double> denseExposureTrajectory,
		final java.util.Map<java.lang.Integer, java.lang.Double> wanderTrajectory)
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

		denseExposureTrajectory.put (
			leftPillarDate,
			leftPillarExposure
		);

		denseExposureTrajectory.put (
			rightPillarDate,
			rightPillarExposure
		);

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
			int date = leftPillarDate + dateIndex;

			if (!wanderTrajectory.containsKey (date))
			{
				return false;
			}

			denseExposureTrajectory.put (
				date,
				rightPillarExposure - localDrift * (dateWidth - dateIndex) + localVolatility * urgency *
					wanderTrajectory.get (date) * java.lang.Math.sqrt (dateIndex * (dateWidth - dateIndex))
			);
		}

		return true;
	}
}
