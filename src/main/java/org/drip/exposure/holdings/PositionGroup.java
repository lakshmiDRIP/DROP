
package org.drip.exposure.holdings;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting analytics/risk, transaction cost analytics,
 *  	asset liability management analytics, capital, exposure, and margin analytics, valuation adjustment
 *  	analytics, and portfolio construction analytics within and across fixed income, credit, commodity,
 *  	equity, FX, and structured products. It also includes auxiliary libraries for algorithm support,
 *  	numerical analysis, numerical optimization, spline builder, model validation, statistical learning,
 *  	and computational support.
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
 * <i>PositionGroup</i> holds the Settings that correspond to a Position/Collateral Group. The References
 * are:
 *  
 * <br><br>
 *  	<ul>
 *  		<li>
 *  			Burgard, C., and M. Kjaer (2013): Funding Costs, Funding Strategies <i>Risk</i> <b>23
 *  				(12)</b> 82-87
 *  		</li>
 *  		<li>
 *  			Burgard, C., and M. Kjaer (2014): In the Balance <i>Risk</i> <b>24 (11)</b> 72-75
 *  		</li>
 *  		<li>
 *  			Burgard, C., and M. Kjaer (2014): PDE Representations of Derivatives with Bilateral Counter-
 *  				party Risk and Funding Costs <i>Journal of Credit Risk</i> <b>7 (3)</b> 1-19
 *  		</li>
 *  		<li>
 *  			Burgard, C., and M. Kjaer (2017): Derivatives Funding, Netting, and Accounting
 *  				https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2534011 <b>eSSRN</b>
 *  		</li>
 *  		<li>
 *  			Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives
 *  				Pricing <i>Risk</i> <b>21 (2)</b> 97-102
 *  		</li>
 *  		<li>
 *  	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ExposureAnalyticsLibrary.md">Exposure Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/README.md">Exposure Group Level Collateralized/Uncollateralized Exposure</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/holdings/README.md">Holdings Exposure - Position and Dependencies</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class PositionGroup
{
	private org.drip.xva.netting.CollateralGroupPath _collateralGroupPath = null;
	private org.drip.exposure.holdings.PositionGroupEstimator _positionGroupEstimator = null;
	private org.drip.xva.proto.PositionSchemaSpecification _positionGroupSpecification = null;

	/**
	 * PositionGroup Constructor
	 * 
	 * @param positionGroupSpecification The Position Group Specification
	 * @param positionGroupEstimator The Position Group Estimator
	 * 
	 * @throws java.lang.Exception Thrown if Inputs are Invalid
	 */

	public PositionGroup (
		final org.drip.xva.proto.PositionSchemaSpecification positionGroupSpecification,
		final org.drip.exposure.holdings.PositionGroupEstimator positionGroupEstimator)
		throws java.lang.Exception
	{
		if (null == (_positionGroupSpecification = positionGroupSpecification) ||
			null == (_positionGroupEstimator = positionGroupEstimator))
		{
			throw  new java.lang.Exception ("PositionGroup Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Position Group Specification
	 * 
	 * @return The Position Group Specification
	 */

	public org.drip.xva.proto.PositionSchemaSpecification positionGroupSpecification()
	{
		return _positionGroupSpecification;
	}

	/**
	 * Retrieve the Position Group Estimator
	 * 
	 * @return The Position Group Estimator
	 */

	public org.drip.exposure.holdings.PositionGroupEstimator positionGroupEstimator()
	{
		return _positionGroupEstimator;
	}

	/**
	 * Set the Collateral Group Path
	 * 
	 * @param collateralGroupPath The Collateral Group Path
	 * 
	 * @return TRUE - The Collateral Group Path Successfully Set
	 */

	public boolean setCollateralGroupPath (
		final org.drip.xva.netting.CollateralGroupPath collateralGroupPath)
	{
		if (null == collateralGroupPath)
		{
			return false;
		}

		_collateralGroupPath = collateralGroupPath;
		return true;
	}

	/**
	 * Retrieve the Collateral Group Path
	 * 
	 * @return The Collateral Group Path
	 */

	public org.drip.xva.netting.CollateralGroupPath collateralGroupPath()
	{
		return _collateralGroupPath;
	}

	/**
	 * Generate the Position Group Value Array at the specified Vertexes
	 * 
	 * @param marketPath The Market Path
	 * 
	 * @return The Position Group Value Array
	 */

	public double[] valueArray (
		final org.drip.exposure.universe.MarketPath marketPath)
	{
		if (null == marketPath)
		{
			return null;
		}

		org.drip.analytics.date.JulianDate[] vertexDateArray = marketPath.anchorDates();

		int vertexCount = vertexDateArray.length;
		double[] positionGroupValueArray = 0 == vertexCount ? null : new double[vertexCount];

		if (0 == vertexCount)
		{
			return null;
		}

		for (int i = 0; i < vertexCount; ++i)
		{
			int forwardDate = vertexDateArray[i].julian();

			try {
				positionGroupValueArray[i] = _positionGroupEstimator.variationMarginEstimate (
					forwardDate,
					marketPath
				);
			}
			catch (java.lang.Exception e)
			{
				e.printStackTrace();

				return null;
			}
		}

		return positionGroupValueArray;
	}
}
