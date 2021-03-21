
package org.drip.xva.gross;

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
 * <i>GroupPathExposureAdjustment</i> cumulates the Exposures and the Adjustments across Multiple
 * Netting/Funding Groups on a Single Path Projection Run across multiple Counter Party Groups the constitute
 * a Book. The References are:
 *
 *  <br><br>
 *  <ul>
 *  	<li>
 *  		Albanese, C., and L. Andersen (2014): Accounting for OTC Derivatives: Funding Adjustments and the
 *  			Re-Hypothecation Option https://papers.ssrn.com/sol3/paper.cfm?abstract_id=2482955
 *  			<b>eSSRN</b>
 *  	</li>
 *  	<li>
 *  		Burgard, C., and M. Kjaer (2013): Funding Strategies, Funding Costs <i>Risk</i> <b>24 (12)</b>
 *  			82-87
 *  	</li>
 *  	<li>
 *  		Burgard, C., and M. Kjaer (2014): PDE Representations of Derivatives with Bilateral Counter-party
 *  			Risk and Funding Costs <i>Journal of Credit Risk</i> <b>7 (3)</b> 1-19
 *  	</li>
 *  	<li>
 *  		Burgard, C., and M. Kjaer (2017): Derivatives Funding, Netting, and Accounting
 *  			https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2534011 <b>eSSRN</b>
 *  	</li>
 *  	<li>
 *  		Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives Pricing
 *  			<i>Risk</i> <b>21 (2)</b> 97-102
 *  	</li>
 *  </ul>
 *
 *  <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/XVAAnalyticsLibrary.md">XVA Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/README.md">Valuation Adjustments that account for Collateral, CC Credit/Debt and Funding Overhead</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/gross/README.md">XVA Gross Adiabat Exposure Aggregation</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class GroupPathExposureAdjustment implements org.drip.xva.gross.PathExposureAdjustment
{
	private org.drip.xva.gross.MonoPathExposureAdjustment[] _monoPathExposureAdjustmentArray = null;

	/**
	 * GroupPathExposureAdjustment Constructor
	 * 
	 * @param monoPathExposureAdjustmentArray Array of Single Counter Party Path Exposure Adjustments
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public GroupPathExposureAdjustment (
		final org.drip.xva.gross.MonoPathExposureAdjustment[] monoPathExposureAdjustmentArray)
		throws java.lang.Exception
	{
		if (null == (_monoPathExposureAdjustmentArray = monoPathExposureAdjustmentArray) ||
			0 == _monoPathExposureAdjustmentArray.length)
		{
			throw new java.lang.Exception ("GroupPathExposureAdjustment Constructor => Invalid Inputs");
		}

		int adiabatGroupCount = _monoPathExposureAdjustmentArray.length;

		if (0 == adiabatGroupCount)
		{
			throw new java.lang.Exception ("GroupPathExposureAdjustment Constructor => Invalid Inputs");
		}

		for (int adiabatGroupIndex = 0; adiabatGroupIndex < adiabatGroupCount; ++adiabatGroupIndex)
		{
			if (null == _monoPathExposureAdjustmentArray[adiabatGroupIndex])
			{
				throw new java.lang.Exception ("GroupPathExposureAdjustment Constructor => Invalid Inputs");
			}
		}
	}

	/**
	 * Retrieve the Array of Counter Party Group Paths
	 * 
	 * @return Array of Counter Party Group Paths
	 */

	public org.drip.xva.gross.MonoPathExposureAdjustment[] adiabatGroupPaths()
	{
		return _monoPathExposureAdjustmentArray;
	}

	@Override public org.drip.analytics.date.JulianDate[] vertexDates()
	{
		return _monoPathExposureAdjustmentArray[0].vertexDates();
	}

	@Override public double[] vertexCollateralizedExposure()
	{
		int vertexCount = vertexDates().length;

		double[] collateralizedExposure = new double[vertexCount];
		int adiabatGroupCount = _monoPathExposureAdjustmentArray.length;

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
		{
			collateralizedExposure[vertexIndex] = 0.;
		}

		for (int adiabatGroupIndex = 0; adiabatGroupIndex < adiabatGroupCount; ++adiabatGroupIndex)
		{
			double[] counterPartyGroupCollateralizedExposure =
				_monoPathExposureAdjustmentArray[adiabatGroupIndex].vertexCollateralizedExposure();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				collateralizedExposure[vertexIndex] += counterPartyGroupCollateralizedExposure[vertexIndex];
			}
		}

		return collateralizedExposure;
	}

	@Override public double[] vertexCollateralizedExposurePV()
	{
		int vertexCount = vertexDates().length;

		double[] collateralizedExposurePV = new double[vertexCount];
		int adiabatGroupCount = _monoPathExposureAdjustmentArray.length;

		for (int j = 0; j < vertexCount; ++j)
		{
			collateralizedExposurePV[j] = 0.;
		}

		for (int adiabatGroupIndex = 0; adiabatGroupIndex < adiabatGroupCount; ++adiabatGroupIndex)
		{
			double[] counterPartyGroupCollateralizedExposurePV =
				_monoPathExposureAdjustmentArray[adiabatGroupIndex].vertexCollateralizedExposurePV();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				collateralizedExposurePV[vertexIndex] +=
					counterPartyGroupCollateralizedExposurePV[vertexIndex];
			}
		}

		return collateralizedExposurePV;
	}

	@Override public double[] vertexUncollateralizedExposure()
	{
		int vertexCount = vertexDates().length;

		double[] uncollateralizedExposure = new double[vertexCount];
		int adiabatGroupCount = _monoPathExposureAdjustmentArray.length;

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
		{
			uncollateralizedExposure[vertexIndex] = 0.;
		}

		for (int adiabatGroupIndex = 0; adiabatGroupIndex < adiabatGroupCount; ++adiabatGroupIndex)
		{
			double[] pathUncollateralizedExposure =
				_monoPathExposureAdjustmentArray[adiabatGroupIndex].vertexUncollateralizedExposure();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				uncollateralizedExposure[vertexIndex] += pathUncollateralizedExposure[vertexIndex];
			}
		}

		return uncollateralizedExposure;
	}

	@Override public double[] vertexUncollateralizedExposurePV()
	{
		int vertexCount = vertexDates().length;

		double[] uncollateralizedExposurePV = new double[vertexCount];
		int adiabatGroupCount = _monoPathExposureAdjustmentArray.length;

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
		{
			uncollateralizedExposurePV[vertexIndex] = 0.;
		}

		for (int adiabatGroupIndex = 0; adiabatGroupIndex < adiabatGroupCount; ++adiabatGroupIndex)
		{
			double[] pathUncollateralizedExposurePV =
				_monoPathExposureAdjustmentArray[adiabatGroupIndex].vertexUncollateralizedExposurePV();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				uncollateralizedExposurePV[vertexIndex] += pathUncollateralizedExposurePV[vertexIndex];
			}
		}

		return uncollateralizedExposurePV;
	}

	@Override public double[] vertexCollateralizedPositiveExposure()
	{
		int vertexCount = vertexDates().length;

		double[] collateralizedPositiveExposure = new double[vertexCount];
		int adiabatGroupCount = _monoPathExposureAdjustmentArray.length;

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
		{
			collateralizedPositiveExposure[vertexIndex] = 0.;
		}

		for (int adiabatGroupIndex = 0; adiabatGroupIndex < adiabatGroupCount; ++adiabatGroupIndex)
		{
			double[] pathCollateralizedPositiveExposure =
				_monoPathExposureAdjustmentArray[adiabatGroupIndex].vertexCollateralizedPositiveExposure();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				collateralizedPositiveExposure[vertexIndex] +=
					pathCollateralizedPositiveExposure[vertexIndex];
			}
		}

		return collateralizedPositiveExposure;
	}

	@Override public double[] vertexCollateralizedPositiveExposurePV()
	{
		int vertexCount = vertexDates().length;

		double[] collateralizedPositiveExposurePV = new double[vertexCount];
		int adiabatGroupCount = _monoPathExposureAdjustmentArray.length;

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
		{
			collateralizedPositiveExposurePV[vertexIndex] = 0.;
		}

		for (int adiabatGroupIndex = 0; adiabatGroupIndex < adiabatGroupCount; ++adiabatGroupIndex)
		{
			double[] pathCollateralizedPositiveExposurePV =
				_monoPathExposureAdjustmentArray[adiabatGroupIndex].vertexCollateralizedPositiveExposurePV();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				collateralizedPositiveExposurePV[vertexIndex] +=
					pathCollateralizedPositiveExposurePV[vertexIndex];
			}
		}

		return collateralizedPositiveExposurePV;
	}

	@Override public double[] vertexUncollateralizedPositiveExposure()
	{
		int vertexCount = vertexDates().length;

		double[] uncollateralizedPositiveExposure = new double[vertexCount];
		int adiabatGroupCount = _monoPathExposureAdjustmentArray.length;

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
		{
			uncollateralizedPositiveExposure[vertexIndex] = 0.;
		}

		for (int adiabatGroupIndex = 0; adiabatGroupIndex < adiabatGroupCount; ++adiabatGroupIndex)
		{
			double[] pathUncollateralizedPositiveExposure =
				_monoPathExposureAdjustmentArray[adiabatGroupIndex].vertexUncollateralizedPositiveExposure();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				uncollateralizedPositiveExposure[vertexIndex] +=
					pathUncollateralizedPositiveExposure[vertexIndex];
			}
		}

		return uncollateralizedPositiveExposure;
	}

	@Override public double[] vertexUncollateralizedPositiveExposurePV()
	{
		int vertexCount = vertexDates().length;

		int adiabatGroupCount = _monoPathExposureAdjustmentArray.length;
		double[] uncollateralizedPositiveExposurePV = new double[vertexCount];

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
		{
			uncollateralizedPositiveExposurePV[vertexIndex] = 0.;
		}

		for (int adiabatGroupIndex = 0; adiabatGroupIndex < adiabatGroupCount; ++adiabatGroupIndex)
		{
			double[] pathUncollateralizedPositiveExposurePV =
				_monoPathExposureAdjustmentArray[adiabatGroupIndex].vertexUncollateralizedPositiveExposurePV();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				uncollateralizedPositiveExposurePV[vertexIndex] +=
					pathUncollateralizedPositiveExposurePV[vertexIndex];
			}
		}

		return uncollateralizedPositiveExposurePV;
	}

	@Override public double[] vertexCollateralizedNegativeExposure()
	{
		int vertexCount = vertexDates().length;

		double[] collateralizedNegativeExposure = new double[vertexCount];
		int adiabatGroupCount = _monoPathExposureAdjustmentArray.length;

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
		{
			collateralizedNegativeExposure[vertexIndex] = 0.;
		}

		for (int adiabatGroupIndex = 0; adiabatGroupIndex < adiabatGroupCount; ++adiabatGroupIndex)
		{
			double[] pathCollateralizedNegativeExposure =
				_monoPathExposureAdjustmentArray[adiabatGroupIndex].vertexCollateralizedNegativeExposure();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				collateralizedNegativeExposure[vertexIndex] +=
					pathCollateralizedNegativeExposure[vertexIndex];
			}
		}

		return collateralizedNegativeExposure;
	}

	@Override public double[] vertexCollateralizedNegativeExposurePV()
	{
		int vertexCount = vertexDates().length;

		double[] collateralizedNegativeExposurePV = new double[vertexCount];
		int adiabatGroupCount = _monoPathExposureAdjustmentArray.length;

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
		{
			collateralizedNegativeExposurePV[vertexIndex] = 0.;
		}

		for (int adiabatGroupIndex = 0; adiabatGroupIndex < adiabatGroupCount; ++adiabatGroupIndex)
		{
			double[] pathCollateralizedNegativeExposurePV =
				_monoPathExposureAdjustmentArray[adiabatGroupIndex].vertexCollateralizedNegativeExposurePV();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				collateralizedNegativeExposurePV[vertexIndex] +=
					pathCollateralizedNegativeExposurePV[vertexIndex];
			}
		}

		return collateralizedNegativeExposurePV;
	}

	@Override public double[] vertexUncollateralizedNegativeExposure()
	{
		int vertexCount = vertexDates().length;

		double[] uncollateralizedNegativeExposure = new double[vertexCount];
		int adiabatGroupCount = _monoPathExposureAdjustmentArray.length;

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
		{
			uncollateralizedNegativeExposure[vertexIndex] = 0.;
		}

		for (int adiabatGroupIndex = 0; adiabatGroupIndex < adiabatGroupCount; ++adiabatGroupIndex)
		{
			double[] adblPathUncollateralizedNegativeExposure =
				_monoPathExposureAdjustmentArray[adiabatGroupIndex].vertexUncollateralizedNegativeExposure();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				uncollateralizedNegativeExposure[vertexIndex] +=
					adblPathUncollateralizedNegativeExposure[vertexIndex];
			}
		}

		return uncollateralizedNegativeExposure;
	}

	@Override public double[] vertexUncollateralizedNegativeExposurePV()
	{
		int vertexCount = vertexDates().length;

		int adiabatGroupCount = _monoPathExposureAdjustmentArray.length;
		double[] uncollateralizedNegativeExposurePV = new double[vertexCount];

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
		{
			uncollateralizedNegativeExposurePV[vertexIndex] = 0.;
		}

		for (int adiabatGroupIndex = 0; adiabatGroupIndex < adiabatGroupCount; ++adiabatGroupIndex)
		{
			double[] pathUncollateralizedNegativeExposurePV =
				_monoPathExposureAdjustmentArray[adiabatGroupIndex].vertexUncollateralizedNegativeExposurePV();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				uncollateralizedNegativeExposurePV[vertexIndex] +=
					pathUncollateralizedNegativeExposurePV[vertexIndex];
			}
		}

		return uncollateralizedNegativeExposurePV;
	}

	@Override public double[] vertexFundingExposure()
	{
		int vertexCount = vertexDates().length;

		double[] fundingExposure = new double[vertexCount];
		int adiabatGroupCount = _monoPathExposureAdjustmentArray.length;

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
		{
			fundingExposure[vertexIndex] = 0.;
		}

		for (int adiabatGroupIndex = 0; adiabatGroupIndex < adiabatGroupCount; ++adiabatGroupIndex)
		{
			double[] pathFundingExposure =
				_monoPathExposureAdjustmentArray[adiabatGroupIndex].vertexFundingExposure();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				fundingExposure[vertexIndex] += pathFundingExposure[vertexIndex];
			}
		}

		return fundingExposure;
	}

	@Override public double[] vertexFundingExposurePV()
	{
		int vertexCount = vertexDates().length;

		double[] fundingExposure = new double[vertexCount];
		int adiabatGroupCount = _monoPathExposureAdjustmentArray.length;

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
		{
			fundingExposure[vertexIndex] = 0.;
		}

		for (int adiabatGroupIndex = 0; adiabatGroupIndex < adiabatGroupCount; ++adiabatGroupIndex)
		{
			double[] pathFundingExposure =
				_monoPathExposureAdjustmentArray[adiabatGroupIndex].vertexFundingExposurePV();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				fundingExposure[vertexIndex] += pathFundingExposure[vertexIndex];
			}
		}

		return fundingExposure;
	}

	@Override public double unilateralCollateralAdjustment()
	{
		double unilateralCollateralAdjustment = 0.;
		int adiabatGroupCount = _monoPathExposureAdjustmentArray.length;

		for (int adiabatGroupIndex = 0; adiabatGroupIndex < adiabatGroupCount; ++adiabatGroupIndex)
		{
			unilateralCollateralAdjustment +=
				_monoPathExposureAdjustmentArray[adiabatGroupIndex].unilateralCollateralAdjustment();
		}

		return unilateralCollateralAdjustment;
	}

	@Override public double bilateralCollateralAdjustment()
	{
		double bilateralCollateralAdjustment = 0.;
		int adiabatGroupCount = _monoPathExposureAdjustmentArray.length;

		for (int adiabatGroupIndex = 0; adiabatGroupIndex < adiabatGroupCount; ++adiabatGroupIndex)
		{
			bilateralCollateralAdjustment +=
				_monoPathExposureAdjustmentArray[adiabatGroupIndex].bilateralCollateralAdjustment();
		}

		return bilateralCollateralAdjustment;
	}

	@Override public double collateralAdjustment()
	{
		return bilateralCollateralAdjustment();
	}

	@Override public double unilateralCreditAdjustment()
	{
		double unilateralCreditAdjustment = 0.;
		int adiabatGroupCount = _monoPathExposureAdjustmentArray.length;

		for (int adiabatGroupIndex = 0; adiabatGroupIndex < adiabatGroupCount; ++adiabatGroupIndex)
		{
			unilateralCreditAdjustment +=
				_monoPathExposureAdjustmentArray[adiabatGroupIndex].unilateralCreditAdjustment();
		}

		return unilateralCreditAdjustment;
	}

	@Override public double bilateralCreditAdjustment()
	{
		double bilateralCreditAdjustment = 0.;
		int adiabatGroupCount = _monoPathExposureAdjustmentArray.length;

		for (int adiabatGroupIndex = 0; adiabatGroupIndex < adiabatGroupCount; ++adiabatGroupIndex)
		{
			bilateralCreditAdjustment +=
				_monoPathExposureAdjustmentArray[adiabatGroupIndex].bilateralCreditAdjustment();
		}

		return bilateralCreditAdjustment;
	}

	@Override public double creditAdjustment()
	{
		return bilateralCreditAdjustment();
	}

	@Override public double contraLiabilityCreditAdjustment()
	{
		double contraLiabilityCreditAdjustment = 0.;
		int adiabatGroupCount = _monoPathExposureAdjustmentArray.length;

		for (int adiabatGroupIndex = 0; adiabatGroupIndex < adiabatGroupCount; ++adiabatGroupIndex)
		{
			contraLiabilityCreditAdjustment +=
				_monoPathExposureAdjustmentArray[adiabatGroupIndex].contraLiabilityCreditAdjustment();
		}

		return contraLiabilityCreditAdjustment;
	}

	@Override public double unilateralDebtAdjustment()
	{
		double unilateralDebtAdjustment = 0.;
		int adiabatGroupCount = _monoPathExposureAdjustmentArray.length;

		for (int adiabatGroupIndex = 0; adiabatGroupIndex < adiabatGroupCount; ++adiabatGroupIndex)
		{
			unilateralDebtAdjustment +=
				_monoPathExposureAdjustmentArray[adiabatGroupIndex].unilateralDebtAdjustment();
		}

		return unilateralDebtAdjustment;
	}

	@Override public double bilateralDebtAdjustment()
	{
		double bilateralDebtAdjustment = 0.;
		int adiabatGroupCount = _monoPathExposureAdjustmentArray.length;

		for (int adiabatGroupIndex = 0; adiabatGroupIndex < adiabatGroupCount; ++adiabatGroupIndex)
		{
			bilateralDebtAdjustment +=
				_monoPathExposureAdjustmentArray[adiabatGroupIndex].bilateralDebtAdjustment();
		}

		return bilateralDebtAdjustment;
	}

	@Override public double debtAdjustment()
	{
		double debtAdjustment = 0.;
		int adiabatGroupCount = _monoPathExposureAdjustmentArray.length;

		for (int adiabatGroupIndex = 0; adiabatGroupIndex < adiabatGroupCount; ++adiabatGroupIndex)
		{
			debtAdjustment += _monoPathExposureAdjustmentArray[adiabatGroupIndex].debtAdjustment();
		}

		return debtAdjustment;
	}

	@Override public double contraAssetDebtAdjustment()
	{
		double contraAssetDebtAdjustment = 0.;
		int adiabatGroupCount = _monoPathExposureAdjustmentArray.length;

		for (int adiabatGroupIndex = 0; adiabatGroupIndex < adiabatGroupCount; ++adiabatGroupIndex)
		{
			contraAssetDebtAdjustment +=
				_monoPathExposureAdjustmentArray[adiabatGroupIndex].contraAssetDebtAdjustment();
		}

		return contraAssetDebtAdjustment;
	}

	@Override public double unilateralFundingValueAdjustment()
	{
		double unilateralFundingValueAdjustment = 0.;
		int adiabatGroupCount = _monoPathExposureAdjustmentArray.length;

		for (int adiabatGroupIndex = 0; adiabatGroupIndex < adiabatGroupCount; ++adiabatGroupIndex)
		{
			unilateralFundingValueAdjustment +=
				_monoPathExposureAdjustmentArray[adiabatGroupIndex].unilateralFundingValueAdjustment();
		}

		return unilateralFundingValueAdjustment;
	}

	@Override public double bilateralFundingValueAdjustment()
	{
		double bilateralFundingValueAdjustment = 0.;
		int adiabatGroupCount = _monoPathExposureAdjustmentArray.length;

		for (int adiabatGroupIndex = 0; adiabatGroupIndex < adiabatGroupCount; ++adiabatGroupIndex)
		{
			bilateralFundingValueAdjustment +=
				_monoPathExposureAdjustmentArray[adiabatGroupIndex].bilateralFundingValueAdjustment();
		}

		return bilateralFundingValueAdjustment;
	}

	@Override public double fundingValueAdjustment()
	{
		double fundingValueAdjustment = 0.;
		int adiabatGroupCount = _monoPathExposureAdjustmentArray.length;

		for (int adiabatGroupIndex = 0; adiabatGroupIndex < adiabatGroupCount; ++adiabatGroupIndex)
		{
			fundingValueAdjustment +=
				_monoPathExposureAdjustmentArray[adiabatGroupIndex].fundingValueAdjustment();
		}

		return fundingValueAdjustment;
	}

	@Override public double fundingDebtAdjustment()
	{
		double fundingDebtAdjustment = 0.;
		int adiabatGroupCount = _monoPathExposureAdjustmentArray.length;

		for (int adiabatGroupIndex = 0; adiabatGroupIndex < adiabatGroupCount; ++adiabatGroupIndex)
		{
			fundingDebtAdjustment +=
				_monoPathExposureAdjustmentArray[adiabatGroupIndex].fundingDebtAdjustment();
		}

		return fundingDebtAdjustment;
	}

	@Override public double fundingCostAdjustment()
	{
		double fundingCostAdjustment = 0.;
		int adiabatGroupCount = _monoPathExposureAdjustmentArray.length;

		for (int adiabatGroupIndex = 0; adiabatGroupIndex < adiabatGroupCount; ++adiabatGroupIndex)
		{
			fundingCostAdjustment +=
				_monoPathExposureAdjustmentArray[adiabatGroupIndex].fundingCostAdjustment();
		}

		return fundingCostAdjustment;
	}

	@Override public double fundingBenefitAdjustment()
	{
		double fundingBenefitAdjustment = 0.;
		int adiabatGroupCount = _monoPathExposureAdjustmentArray.length;

		for (int adiabatGroupIndex = 0; adiabatGroupIndex < adiabatGroupCount; ++adiabatGroupIndex)
		{
			fundingBenefitAdjustment +=
				_monoPathExposureAdjustmentArray[adiabatGroupIndex].fundingBenefitAdjustment();
		}

		return fundingBenefitAdjustment;
	}

	@Override public double symmetricFundingValueAdjustment()
	{
		double symmetricFundingValueAdjustment = 0.;
		int adiabatGroupCount = _monoPathExposureAdjustmentArray.length;

		for (int adiabatGroupIndex = 0; adiabatGroupIndex < adiabatGroupCount; ++adiabatGroupIndex)
		{
			symmetricFundingValueAdjustment +=
				_monoPathExposureAdjustmentArray[adiabatGroupIndex].symmetricFundingValueAdjustment();
		}

		return symmetricFundingValueAdjustment;
	}

	@Override public double totalAdjustment()
	{
		double totalAdjustment = 0.;
		int adiabatGroupCount = _monoPathExposureAdjustmentArray.length;

		for (int adiabatGroupIndex = 0; adiabatGroupIndex < adiabatGroupCount; ++adiabatGroupIndex)
		{
			totalAdjustment +=
				_monoPathExposureAdjustmentArray[adiabatGroupIndex].creditAdjustment() +
				_monoPathExposureAdjustmentArray[adiabatGroupIndex].debtAdjustment() +
				_monoPathExposureAdjustmentArray[adiabatGroupIndex].fundingValueAdjustment();
		}

		return totalAdjustment;
	}
}
