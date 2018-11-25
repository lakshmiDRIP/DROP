
package org.drip.xva.netting;

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
 * <i>CollateralGroupPath</i> accumulates the Vertex Realizations of the Sequence in a Single Path Projection
 * Run along the Granularity of a Regular Collateral Hypothecation Group. The References are:
 *
 *  <br><br>
 *  <ul>
 *  	<li>
 *  		Burgard, C., and M. Kjaer (2014): PDE Representations of Derivatives with Bilateral Counter-party
 *  			Risk and Funding Costs <i>Journal of Credit Risk</i> <b>7 (3)</b> 1-19
 *  	</li>
 *  	<li>
 *  		Burgard, C., and M. Kjaer (2014): In the Balance <i>Risk</i> <b>24 (11)</b> 72-75
 *  	</li>
 *  	<li>
 *  		Gregory, J. (2009): Being Two-faced over Counter-party Credit Risk <i>Risk</i> <b>20 (2)</b>
 *  			86-90
 *  	</li>
 *  	<li>
 *  		Li, B., and Y. Tang (2007): <i>Quantitative Analysis, Derivatives Modeling, and Trading
 *  			Strategies in the Presence of Counter-party Credit Risk for the Fixed Income Market</i>
 *  			<b>World Scientific Publishing</b> Singapore
 *  	</li>
 *  	<li>
 *  		Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives Pricing
 *  			<i>Risk</i> <b>21 (2)</b> 97-102
 *  	</li>
 *  </ul>
 *
 *  <br><br>
 *  <ul>
 *		<li><b>Project</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva">XVA</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/netting">Netting</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/XVA">XVA Analytics Library</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class CollateralGroupPath
{
	private double _overnightReplicatorStart = java.lang.Double.NaN;
	private org.drip.exposure.universe.MarketPath _marketPath = null;
	private org.drip.xva.hypothecation.CollateralGroupVertex[] _collateralGroupVertexArray = null;

	/**
	 * CollateralGroupPath Constructor
	 * 
	 * @param collateralGroupVertexArray The Array of Collateral Hypothecation Group Trajectory Vertexes
	 * @param marketPath The Market Path
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public CollateralGroupPath (
		final org.drip.xva.hypothecation.CollateralGroupVertex[] collateralGroupVertexArray,
		final org.drip.exposure.universe.MarketPath marketPath)
		throws java.lang.Exception
	{
		if (null == (_collateralGroupVertexArray = collateralGroupVertexArray) ||
			null == (_marketPath = marketPath))
		{
			throw new java.lang.Exception ("CollateralGroupPath Constructor => Invalid Inputs");
		}

		_overnightReplicatorStart = _marketPath.epochalMarketVertex().overnightReplicator();

		int vertexCount = _collateralGroupVertexArray.length;

		if (1 >= vertexCount)
		{
			throw new java.lang.Exception ("CollateralGroupPath Constructor => Invalid Inputs");
		}

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
		{
			if (null == _collateralGroupVertexArray[vertexIndex])
			{
				throw new java.lang.Exception ("CollateralGroupPath Constructor => Invalid Inputs");
			}

			if (0 != vertexIndex && _collateralGroupVertexArray[vertexIndex - 1].vertexDate().julian() >=
				_collateralGroupVertexArray[vertexIndex].vertexDate().julian())
			{
				throw new java.lang.Exception ("CollateralGroupPath Constructor => Invalid Inputs");
			}
		}
	}

	/**
	 * Retrieve the Array of Collateral Group Trajectory Vertexes
	 * 
	 * @return The Array of Collateral Group Trajectory Vertexes
	 */

	public org.drip.xva.hypothecation.CollateralGroupVertex[] collateralGroupVertex()
	{
		return _collateralGroupVertexArray;
	}

	/**
	 * Retrieve the Market Path
	 * 
	 * @return The Market Path
	 */

	public org.drip.exposure.universe.MarketPath marketPath()
	{
		return _marketPath;
	}

	/**
	 * Retrieve the Array of the Vertex Anchor Dates
	 * 
	 * @return The Array of the Vertex Anchor Dates
	 */

	public org.drip.analytics.date.JulianDate[] vertexDates()
	{
		int vertexCount = _collateralGroupVertexArray.length;
		org.drip.analytics.date.JulianDate[] vertexDateArray = new
			org.drip.analytics.date.JulianDate[vertexCount];

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
		{
			vertexDateArray[vertexIndex] = _collateralGroupVertexArray[vertexIndex].vertexDate();
		}

		return vertexDateArray;
	}

	/**
	 * Retrieve the Array of Vertex Collateralized Exposures
	 * 
	 * @return The Array of Vertex Collateralized Exposures
	 */

	public double[] vertexCollateralizedExposure()
	{
		int vertexCount = _collateralGroupVertexArray.length;
		double[] collateralizedExposure = new double[vertexCount];

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
		{
			collateralizedExposure[vertexIndex] = _collateralGroupVertexArray[vertexIndex].collateralized();
		}

		return collateralizedExposure;
	}

	/**
	 * Retrieve the Array of Vertex Collateralized Exposure PV
	 * 
	 * @return The Array of Vertex Collateralized Exposure PV
	 */

	public double[] vertexCollateralizedExposurePV()
	{
		int vertexCount = _collateralGroupVertexArray.length;
		double[] collateralizedExposurePV = new double[vertexCount];

		org.drip.analytics.date.JulianDate[] vertexDateArray = _marketPath.anchorDates();

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
		{
			collateralizedExposurePV[vertexIndex] = _collateralGroupVertexArray[vertexIndex].collateralized()
				* _overnightReplicatorStart /
				_marketPath.marketVertex (vertexDateArray[vertexIndex].julian()).overnightReplicator();
		}

		return collateralizedExposurePV;
	}

	/**
	 * Retrieve the Array of Vertex Uncollateralized Exposures
	 * 
	 * @return The Array of Vertex Uncollateralized Exposures
	 */

	public double[] vertexUncollateralizedExposure()
	{
		int vertexCount = _collateralGroupVertexArray.length;
		double[] uncollateralizedExposure = new double[vertexCount];

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
		{
			uncollateralizedExposure[vertexIndex] =
				_collateralGroupVertexArray[vertexIndex].uncollateralized();
		}

		return uncollateralizedExposure;
	}

	/**
	 * Retrieve the Array of Vertex Uncollateralized Exposure PV
	 * 
	 * @return The Array of Vertex Uncollateralized Exposure PV
	 */

	public double[] vertexUncollateralizedExposurePV()
	{
		int vertexCount = _collateralGroupVertexArray.length;
		double[] uncollateralizedExposurePV = new double[vertexCount];

		org.drip.analytics.date.JulianDate[] vertexDateArray = _marketPath.anchorDates();

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
		{
			uncollateralizedExposurePV[vertexIndex] =
				_collateralGroupVertexArray[vertexIndex].uncollateralized() *
				_overnightReplicatorStart /
				_marketPath.marketVertex (vertexDateArray[vertexIndex].julian()).overnightReplicator();
		}

		return uncollateralizedExposurePV;
	}

	/**
	 * Retrieve the Array of Vertex Credit Exposures
	 * 
	 * @return The Array of Vertex Credit Exposures
	 */

	public double[] vertexCreditExposure()
	{
		int vertexCount = _collateralGroupVertexArray.length;
		double[] creditExposure = new double[vertexCount];

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
		{
			creditExposure[vertexIndex] = _collateralGroupVertexArray[vertexIndex].credit();
		}

		return creditExposure;
	}

	/**
	 * Retrieve the Array of Vertex Credit Exposure PV
	 * 
	 * @return The Array of Vertex Credit Exposure PV
	 */

	public double[] vertexCreditExposurePV()
	{
		int vertexCount = _collateralGroupVertexArray.length;
		double[] creditExposurePV = new double[vertexCount];

		org.drip.analytics.date.JulianDate[] vertexDateArray = _marketPath.anchorDates();

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
		{
			creditExposurePV[vertexIndex] = _collateralGroupVertexArray[vertexIndex].credit() *
				_overnightReplicatorStart /
				_marketPath.marketVertex (vertexDateArray[vertexIndex].julian()).overnightReplicator();
		}

		return creditExposurePV;
	}

	/**
	 * Retrieve the Array of Vertex Debt Exposures
	 * 
	 * @return The Array of Vertex Debt Exposures
	 */

	public double[] vertexDebtExposure()
	{
		int vertexCount = _collateralGroupVertexArray.length;
		double[] debtExposure = new double[vertexCount];

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
		{
			debtExposure[vertexIndex] = _collateralGroupVertexArray[vertexIndex].debt();
		}

		return debtExposure;
	}

	/**
	 * Retrieve the Array of Vertex Debt Exposures PV
	 * 
	 * @return The Array of Vertex Debt Exposures PV
	 */

	public double[] vertexDebtExposurePV()
	{
		int vertexCount = _collateralGroupVertexArray.length;
		double[] debtExposurePV = new double[vertexCount];

		org.drip.analytics.date.JulianDate[] vertexDateArray = _marketPath.anchorDates();

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
		{
			debtExposurePV[vertexIndex] = _collateralGroupVertexArray[vertexIndex].debt() *
				_overnightReplicatorStart /
				_marketPath.marketVertex (vertexDateArray[vertexIndex].julian()).overnightReplicator();
		}

		return debtExposurePV;
	}

	/**
	 * Retrieve the Array of Vertex Funding Exposures
	 * 
	 * @return The Array of Vertex Funding Exposures
	 */

	public double[] vertexFundingExposure()
	{
		int vertexCount = _collateralGroupVertexArray.length;
		double[] fundingExposure = new double[vertexCount];

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
		{
			fundingExposure[vertexIndex] = _collateralGroupVertexArray[vertexIndex].funding();
		}

		return fundingExposure;
	}

	/**
	 * Retrieve the Array of Vertex Funding Exposures PV
	 * 
	 * @return The Array of Vertex Funding Exposures PV
	 */

	public double[] vertexFundingExposurePV()
	{
		int vertexCount = _collateralGroupVertexArray.length;
		double[] fundingExposurePV = new double[vertexCount];

		org.drip.analytics.date.JulianDate[] vertexDateArray = _marketPath.anchorDates();

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
		{
			fundingExposurePV[vertexIndex] = _collateralGroupVertexArray[vertexIndex].funding() *
				_overnightReplicatorStart /
				_marketPath.marketVertex (vertexDateArray[vertexIndex].julian()).overnightReplicator();
		}

		return fundingExposurePV;
	}

	/**
	 * Retrieve the Array of Vertex Collateral Balances
	 * 
	 * @return The Array of Vertex Collateral Balances
	 */

	public double[] vertexCollateralBalance()
	{
		int vertexCount = _collateralGroupVertexArray.length;
		double[] collateralizedBalance = new double[vertexCount];

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
		{
			collateralizedBalance[vertexIndex] =
				_collateralGroupVertexArray[vertexIndex].variationMarginPosting();
		}

		return collateralizedBalance;
	}

	/**
	 * Retrieve the Array of Vertex Collateral Balances PV
	 * 
	 * @return The Array of Vertex Collateral Balances PV
	 */

	public double[] vertexCollateralBalancePV()
	{
		int vertexCount = _collateralGroupVertexArray.length;
		double[] collateralizedBalancePV = new double[vertexCount];

		org.drip.analytics.date.JulianDate[] vertexDateArray = _marketPath.anchorDates();

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
		{
			collateralizedBalancePV[vertexIndex] =
				_collateralGroupVertexArray[vertexIndex].variationMarginPosting() * _overnightReplicatorStart /
				_marketPath.marketVertex (vertexDateArray[vertexIndex].julian()).overnightReplicator();
		}

		return collateralizedBalancePV;
	}

	/**
	 * Compute Period-wise Path Collateral Spread 01
	 * 
	 * @return The Period-wise Path Collateral Spread 01
	 */

	public double[] periodCollateralSpread01()
	{
		org.drip.analytics.date.JulianDate[] vertexDateArray = _marketPath.anchorDates();

		double[] vertexCollateralBalancePV = vertexCollateralBalancePV();

		int vertexCount = vertexCollateralBalancePV.length;
		double[] periodCollateralValueAdjustment = new double[vertexCount - 1];

		for (int vertexIndex = 1; vertexIndex < vertexCount; ++vertexIndex)
		{
			periodCollateralValueAdjustment[vertexIndex - 1] = -0.5 *
				(vertexCollateralBalancePV[vertexIndex - 1] + vertexCollateralBalancePV[vertexIndex]) *
				(vertexDateArray[vertexIndex].julian() - vertexDateArray[vertexIndex - 1].julian()) / 365.25;
		}

		return periodCollateralValueAdjustment;
	}

	/**
	 * Compute Period-wise Path Collateral Value Adjustment
	 * 
	 * @return The Period-wise Path Collateral Value Adjustment
	 */

	public double[] periodCollateralValueAdjustment()
	{
		org.drip.analytics.date.JulianDate[] vertexDateArray = _marketPath.anchorDates();

		double[] vertexCollateralBalancePV = vertexCollateralBalancePV();

		int vertexCount = vertexCollateralBalancePV.length;
		double[] periodCollateralValueAdjustment = new double[vertexCount - 1];

		for (int vertexIndex = 1; vertexIndex < vertexCount; ++vertexIndex)
		{
			int previousVertexDate = vertexDateArray[vertexIndex - 1].julian();

			int currentVertexDate = vertexDateArray[vertexIndex].julian();

			double periodIntegrandStart = vertexCollateralBalancePV[vertexIndex - 1] *
				_marketPath.marketVertex (previousVertexDate).csaSpread();

			double periodIntegrandEnd = vertexCollateralBalancePV[vertexIndex] *
				_marketPath.marketVertex (currentVertexDate).csaSpread();

			periodCollateralValueAdjustment[vertexIndex - 1] =
				-0.5 * (periodIntegrandStart + periodIntegrandEnd) *
				(currentVertexDate - previousVertexDate) / 365.25;
		}

		return periodCollateralValueAdjustment;
	}
}
