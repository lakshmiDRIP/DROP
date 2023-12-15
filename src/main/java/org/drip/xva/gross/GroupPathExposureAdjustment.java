
package org.drip.xva.gross;

import org.drip.analytics.date.JulianDate;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2025 Lakshmi Krishnamurthy
 * Copyright (C) 2024 Lakshmi Krishnamurthy
 * Copyright (C) 2023 Lakshmi Krishnamurthy
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

public class GroupPathExposureAdjustment implements PathExposureAdjustment
{
	private MonoPathExposureAdjustment[] _monoPathExposureAdjustmentArray = null;

	/**
	 * GroupPathExposureAdjustment Constructor
	 * 
	 * @param monoPathExposureAdjustmentArray Array of Single Counter Party Path Exposure Adjustments
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public GroupPathExposureAdjustment (
		final MonoPathExposureAdjustment[] monoPathExposureAdjustmentArray)
		throws Exception
	{
		if (null == (_monoPathExposureAdjustmentArray = monoPathExposureAdjustmentArray) ||
			0 == _monoPathExposureAdjustmentArray.length) {
			throw new Exception ("GroupPathExposureAdjustment Constructor => Invalid Inputs");
		}

		int adiabatGroupCount = _monoPathExposureAdjustmentArray.length;

		if (0 == adiabatGroupCount) {
			throw new Exception ("GroupPathExposureAdjustment Constructor => Invalid Inputs");
		}

		for (int adiabatGroupIndex = 0; adiabatGroupIndex < adiabatGroupCount; ++adiabatGroupIndex) {
			if (null == _monoPathExposureAdjustmentArray[adiabatGroupIndex]) {
				throw new Exception ("GroupPathExposureAdjustment Constructor => Invalid Inputs");
			}
		}
	}

	/**
	 * Retrieve the Array of Counter Party Group Paths
	 * 
	 * @return Array of Counter Party Group Paths
	 */

	public MonoPathExposureAdjustment[] adiabatGroupPaths()
	{
		return _monoPathExposureAdjustmentArray;
	}

	@Override public JulianDate[] vertexDates()
	{
		return _monoPathExposureAdjustmentArray[0].vertexDates();
	}

	@Override public double[] vertexCollateralizedExposure()
	{
		int vertexCount = vertexDates().length;

		double[] collateralizedExposureArray = new double[vertexCount];
		int adiabatGroupCount = _monoPathExposureAdjustmentArray.length;

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
			collateralizedExposureArray[vertexIndex] = 0.;
		}

		for (int adiabatGroupIndex = 0; adiabatGroupIndex < adiabatGroupCount; ++adiabatGroupIndex) {
			double[] counterPartyGroupCollateralizedExposureArray =
				_monoPathExposureAdjustmentArray[adiabatGroupIndex].vertexCollateralizedExposure();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
				collateralizedExposureArray[vertexIndex] +=
					counterPartyGroupCollateralizedExposureArray[vertexIndex];
			}
		}

		return collateralizedExposureArray;
	}

	@Override public double[] vertexCollateralizedExposurePV()
	{
		int vertexCount = vertexDates().length;

		double[] collateralizedExposurePVArray = new double[vertexCount];
		int adiabatGroupCount = _monoPathExposureAdjustmentArray.length;

		for (int j = 0; j < vertexCount; ++j) {
			collateralizedExposurePVArray[j] = 0.;
		}

		for (int adiabatGroupIndex = 0; adiabatGroupIndex < adiabatGroupCount; ++adiabatGroupIndex) {
			double[] counterPartyGroupCollateralizedExposurePVArray =
				_monoPathExposureAdjustmentArray[adiabatGroupIndex].vertexCollateralizedExposurePV();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
				collateralizedExposurePVArray[vertexIndex] +=
					counterPartyGroupCollateralizedExposurePVArray[vertexIndex];
			}
		}

		return collateralizedExposurePVArray;
	}

	@Override public double[] vertexUncollateralizedExposure()
	{
		int vertexCount = vertexDates().length;

		double[] uncollateralizedExposureArray = new double[vertexCount];
		int adiabatGroupCount = _monoPathExposureAdjustmentArray.length;

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
			uncollateralizedExposureArray[vertexIndex] = 0.;
		}

		for (int adiabatGroupIndex = 0; adiabatGroupIndex < adiabatGroupCount; ++adiabatGroupIndex) {
			double[] pathUncollateralizedExposureArray =
				_monoPathExposureAdjustmentArray[adiabatGroupIndex].vertexUncollateralizedExposure();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
				uncollateralizedExposureArray[vertexIndex] += pathUncollateralizedExposureArray[vertexIndex];
			}
		}

		return uncollateralizedExposureArray;
	}

	@Override public double[] vertexUncollateralizedExposurePV()
	{
		int vertexCount = vertexDates().length;

		double[] uncollateralizedExposurePVArray = new double[vertexCount];
		int adiabatGroupCount = _monoPathExposureAdjustmentArray.length;

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
			uncollateralizedExposurePVArray[vertexIndex] = 0.;
		}

		for (int adiabatGroupIndex = 0; adiabatGroupIndex < adiabatGroupCount; ++adiabatGroupIndex) {
			double[] pathUncollateralizedExposurePVArray =
				_monoPathExposureAdjustmentArray[adiabatGroupIndex].vertexUncollateralizedExposurePV();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
				uncollateralizedExposurePVArray[vertexIndex] +=
					pathUncollateralizedExposurePVArray[vertexIndex];
			}
		}

		return uncollateralizedExposurePVArray;
	}

	@Override public double[] vertexCollateralizedPositiveExposure()
	{
		int vertexCount = vertexDates().length;

		double[] collateralizedPositiveExposureArray = new double[vertexCount];
		int adiabatGroupCount = _monoPathExposureAdjustmentArray.length;

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
			collateralizedPositiveExposureArray[vertexIndex] = 0.;
		}

		for (int adiabatGroupIndex = 0; adiabatGroupIndex < adiabatGroupCount; ++adiabatGroupIndex) {
			double[] pathCollateralizedPositiveExposureArray =
				_monoPathExposureAdjustmentArray[adiabatGroupIndex].vertexCollateralizedPositiveExposure();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
				collateralizedPositiveExposureArray[vertexIndex] +=
					pathCollateralizedPositiveExposureArray[vertexIndex];
			}
		}

		return collateralizedPositiveExposureArray;
	}

	@Override public double[] vertexCollateralizedPositiveExposurePV()
	{
		int vertexCount = vertexDates().length;

		double[] collateralizedPositiveExposurePVArray = new double[vertexCount];
		int adiabatGroupCount = _monoPathExposureAdjustmentArray.length;

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
			collateralizedPositiveExposurePVArray[vertexIndex] = 0.;
		}

		for (int adiabatGroupIndex = 0; adiabatGroupIndex < adiabatGroupCount; ++adiabatGroupIndex) {
			double[] pathCollateralizedPositiveExposurePVArray =
				_monoPathExposureAdjustmentArray[adiabatGroupIndex].vertexCollateralizedPositiveExposurePV();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
				collateralizedPositiveExposurePVArray[vertexIndex] +=
					pathCollateralizedPositiveExposurePVArray[vertexIndex];
			}
		}

		return collateralizedPositiveExposurePVArray;
	}

	@Override public double[] vertexUncollateralizedPositiveExposure()
	{
		int vertexCount = vertexDates().length;

		double[] uncollateralizedPositiveExposureArray = new double[vertexCount];
		int adiabatGroupCount = _monoPathExposureAdjustmentArray.length;

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
			uncollateralizedPositiveExposureArray[vertexIndex] = 0.;
		}

		for (int adiabatGroupIndex = 0; adiabatGroupIndex < adiabatGroupCount; ++adiabatGroupIndex) {
			double[] pathUncollateralizedPositiveExposure =
				_monoPathExposureAdjustmentArray[adiabatGroupIndex].vertexUncollateralizedPositiveExposure();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
				uncollateralizedPositiveExposureArray[vertexIndex] +=
					pathUncollateralizedPositiveExposure[vertexIndex];
			}
		}

		return uncollateralizedPositiveExposureArray;
	}

	@Override public double[] vertexUncollateralizedPositiveExposurePV()
	{
		int vertexCount = vertexDates().length;

		int adiabatGroupCount = _monoPathExposureAdjustmentArray.length;
		double[] uncollateralizedPositiveExposurePVArray = new double[vertexCount];

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
			uncollateralizedPositiveExposurePVArray[vertexIndex] = 0.;
		}

		for (int adiabatGroupIndex = 0; adiabatGroupIndex < adiabatGroupCount; ++adiabatGroupIndex) {
			double[] pathUncollateralizedPositiveExposurePVArray =
				_monoPathExposureAdjustmentArray[adiabatGroupIndex].vertexUncollateralizedPositiveExposurePV();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
				uncollateralizedPositiveExposurePVArray[vertexIndex] +=
					pathUncollateralizedPositiveExposurePVArray[vertexIndex];
			}
		}

		return uncollateralizedPositiveExposurePVArray;
	}

	@Override public double[] vertexCollateralizedNegativeExposure()
	{
		int vertexCount = vertexDates().length;

		int adiabatGroupCount = _monoPathExposureAdjustmentArray.length;
		double[] collateralizedNegativeExposureArray = new double[vertexCount];

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
			collateralizedNegativeExposureArray[vertexIndex] = 0.;
		}

		for (int adiabatGroupIndex = 0; adiabatGroupIndex < adiabatGroupCount; ++adiabatGroupIndex) {
			double[] pathCollateralizedNegativeExposureArray =
				_monoPathExposureAdjustmentArray[adiabatGroupIndex].vertexCollateralizedNegativeExposure();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
				collateralizedNegativeExposureArray[vertexIndex] +=
					pathCollateralizedNegativeExposureArray[vertexIndex];
			}
		}

		return collateralizedNegativeExposureArray;
	}

	@Override public double[] vertexCollateralizedNegativeExposurePV()
	{
		int vertexCount = vertexDates().length;

		double[] collateralizedNegativeExposurePVArray = new double[vertexCount];
		int adiabatGroupCount = _monoPathExposureAdjustmentArray.length;

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
			collateralizedNegativeExposurePVArray[vertexIndex] = 0.;
		}

		for (int adiabatGroupIndex = 0; adiabatGroupIndex < adiabatGroupCount; ++adiabatGroupIndex) {
			double[] pathCollateralizedNegativeExposurePVArray =
				_monoPathExposureAdjustmentArray[adiabatGroupIndex].vertexCollateralizedNegativeExposurePV();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
				collateralizedNegativeExposurePVArray[vertexIndex] +=
					pathCollateralizedNegativeExposurePVArray[vertexIndex];
			}
		}

		return collateralizedNegativeExposurePVArray;
	}

	@Override public double[] vertexUncollateralizedNegativeExposure()
	{
		int vertexCount = vertexDates().length;

		int adiabatGroupCount = _monoPathExposureAdjustmentArray.length;
		double[] uncollateralizedNegativeExposureArray = new double[vertexCount];

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
			uncollateralizedNegativeExposureArray[vertexIndex] = 0.;
		}

		for (int adiabatGroupIndex = 0; adiabatGroupIndex < adiabatGroupCount; ++adiabatGroupIndex) {
			double[] adblPathUncollateralizedNegativeExposureArray =
				_monoPathExposureAdjustmentArray[adiabatGroupIndex].vertexUncollateralizedNegativeExposure();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
				uncollateralizedNegativeExposureArray[vertexIndex] +=
					adblPathUncollateralizedNegativeExposureArray[vertexIndex];
			}
		}

		return uncollateralizedNegativeExposureArray;
	}

	@Override public double[] vertexUncollateralizedNegativeExposurePV()
	{
		int vertexCount = vertexDates().length;

		int adiabatGroupCount = _monoPathExposureAdjustmentArray.length;
		double[] uncollateralizedNegativeExposurePVArray = new double[vertexCount];

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
			uncollateralizedNegativeExposurePVArray[vertexIndex] = 0.;
		}

		for (int adiabatGroupIndex = 0; adiabatGroupIndex < adiabatGroupCount; ++adiabatGroupIndex) {
			double[] pathUncollateralizedNegativeExposurePVArray =
				_monoPathExposureAdjustmentArray[adiabatGroupIndex].vertexUncollateralizedNegativeExposurePV();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
				uncollateralizedNegativeExposurePVArray[vertexIndex] +=
					pathUncollateralizedNegativeExposurePVArray[vertexIndex];
			}
		}

		return uncollateralizedNegativeExposurePVArray;
	}

	@Override public double[] vertexFundingExposure()
	{
		int vertexCount = vertexDates().length;

		double[] fundingExposureArray = new double[vertexCount];
		int adiabatGroupCount = _monoPathExposureAdjustmentArray.length;

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
			fundingExposureArray[vertexIndex] = 0.;
		}

		for (int adiabatGroupIndex = 0; adiabatGroupIndex < adiabatGroupCount; ++adiabatGroupIndex) {
			double[] pathFundingExposureArray =
				_monoPathExposureAdjustmentArray[adiabatGroupIndex].vertexFundingExposure();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
				fundingExposureArray[vertexIndex] += pathFundingExposureArray[vertexIndex];
			}
		}

		return fundingExposureArray;
	}

	@Override public double[] vertexFundingExposurePV()
	{
		int vertexCount = vertexDates().length;

		double[] fundingExposureArray = new double[vertexCount];
		int adiabatGroupCount = _monoPathExposureAdjustmentArray.length;

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
			fundingExposureArray[vertexIndex] = 0.;
		}

		for (int adiabatGroupIndex = 0; adiabatGroupIndex < adiabatGroupCount; ++adiabatGroupIndex) {
			double[] pathFundingExposureArray =
				_monoPathExposureAdjustmentArray[adiabatGroupIndex].vertexFundingExposurePV();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
				fundingExposureArray[vertexIndex] += pathFundingExposureArray[vertexIndex];
			}
		}

		return fundingExposureArray;
	}

	@Override public double unilateralCollateralAdjustment()
	{
		double unilateralCollateralAdjustment = 0.;
		int adiabatGroupCount = _monoPathExposureAdjustmentArray.length;

		for (int adiabatGroupIndex = 0; adiabatGroupIndex < adiabatGroupCount; ++adiabatGroupIndex) {
			unilateralCollateralAdjustment +=
				_monoPathExposureAdjustmentArray[adiabatGroupIndex].unilateralCollateralAdjustment();
		}

		return unilateralCollateralAdjustment;
	}

	@Override public double bilateralCollateralAdjustment()
	{
		double bilateralCollateralAdjustment = 0.;
		int adiabatGroupCount = _monoPathExposureAdjustmentArray.length;

		for (int adiabatGroupIndex = 0; adiabatGroupIndex < adiabatGroupCount; ++adiabatGroupIndex) {
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

		for (int adiabatGroupIndex = 0; adiabatGroupIndex < adiabatGroupCount; ++adiabatGroupIndex) {
			unilateralCreditAdjustment +=
				_monoPathExposureAdjustmentArray[adiabatGroupIndex].unilateralCreditAdjustment();
		}

		return unilateralCreditAdjustment;
	}

	@Override public double bilateralCreditAdjustment()
	{
		double bilateralCreditAdjustment = 0.;
		int adiabatGroupCount = _monoPathExposureAdjustmentArray.length;

		for (int adiabatGroupIndex = 0; adiabatGroupIndex < adiabatGroupCount; ++adiabatGroupIndex) {
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

		for (int adiabatGroupIndex = 0; adiabatGroupIndex < adiabatGroupCount; ++adiabatGroupIndex) {
			contraLiabilityCreditAdjustment +=
				_monoPathExposureAdjustmentArray[adiabatGroupIndex].contraLiabilityCreditAdjustment();
		}

		return contraLiabilityCreditAdjustment;
	}

	@Override public double unilateralDebtAdjustment()
	{
		double unilateralDebtAdjustment = 0.;
		int adiabatGroupCount = _monoPathExposureAdjustmentArray.length;

		for (int adiabatGroupIndex = 0; adiabatGroupIndex < adiabatGroupCount; ++adiabatGroupIndex) {
			unilateralDebtAdjustment +=
				_monoPathExposureAdjustmentArray[adiabatGroupIndex].unilateralDebtAdjustment();
		}

		return unilateralDebtAdjustment;
	}

	@Override public double bilateralDebtAdjustment()
	{
		double bilateralDebtAdjustment = 0.;
		int adiabatGroupCount = _monoPathExposureAdjustmentArray.length;

		for (int adiabatGroupIndex = 0; adiabatGroupIndex < adiabatGroupCount; ++adiabatGroupIndex) {
			bilateralDebtAdjustment +=
				_monoPathExposureAdjustmentArray[adiabatGroupIndex].bilateralDebtAdjustment();
		}

		return bilateralDebtAdjustment;
	}

	@Override public double debtAdjustment()
	{
		double debtAdjustment = 0.;
		int adiabatGroupCount = _monoPathExposureAdjustmentArray.length;

		for (int adiabatGroupIndex = 0; adiabatGroupIndex < adiabatGroupCount; ++adiabatGroupIndex) {
			debtAdjustment += _monoPathExposureAdjustmentArray[adiabatGroupIndex].debtAdjustment();
		}

		return debtAdjustment;
	}

	@Override public double contraAssetDebtAdjustment()
	{
		double contraAssetDebtAdjustment = 0.;
		int adiabatGroupCount = _monoPathExposureAdjustmentArray.length;

		for (int adiabatGroupIndex = 0; adiabatGroupIndex < adiabatGroupCount; ++adiabatGroupIndex) {
			contraAssetDebtAdjustment +=
				_monoPathExposureAdjustmentArray[adiabatGroupIndex].contraAssetDebtAdjustment();
		}

		return contraAssetDebtAdjustment;
	}

	@Override public double unilateralFundingValueAdjustment()
	{
		double unilateralFundingValueAdjustment = 0.;
		int adiabatGroupCount = _monoPathExposureAdjustmentArray.length;

		for (int adiabatGroupIndex = 0; adiabatGroupIndex < adiabatGroupCount; ++adiabatGroupIndex) {
			unilateralFundingValueAdjustment +=
				_monoPathExposureAdjustmentArray[adiabatGroupIndex].unilateralFundingValueAdjustment();
		}

		return unilateralFundingValueAdjustment;
	}

	@Override public double bilateralFundingValueAdjustment()
	{
		double bilateralFundingValueAdjustment = 0.;
		int adiabatGroupCount = _monoPathExposureAdjustmentArray.length;

		for (int adiabatGroupIndex = 0; adiabatGroupIndex < adiabatGroupCount; ++adiabatGroupIndex) {
			bilateralFundingValueAdjustment +=
				_monoPathExposureAdjustmentArray[adiabatGroupIndex].bilateralFundingValueAdjustment();
		}

		return bilateralFundingValueAdjustment;
	}

	@Override public double fundingValueAdjustment()
	{
		double fundingValueAdjustment = 0.;
		int adiabatGroupCount = _monoPathExposureAdjustmentArray.length;

		for (int adiabatGroupIndex = 0; adiabatGroupIndex < adiabatGroupCount; ++adiabatGroupIndex) {
			fundingValueAdjustment +=
				_monoPathExposureAdjustmentArray[adiabatGroupIndex].fundingValueAdjustment();
		}

		return fundingValueAdjustment;
	}

	@Override public double fundingDebtAdjustment()
	{
		double fundingDebtAdjustment = 0.;
		int adiabatGroupCount = _monoPathExposureAdjustmentArray.length;

		for (int adiabatGroupIndex = 0; adiabatGroupIndex < adiabatGroupCount; ++adiabatGroupIndex) {
			fundingDebtAdjustment +=
				_monoPathExposureAdjustmentArray[adiabatGroupIndex].fundingDebtAdjustment();
		}

		return fundingDebtAdjustment;
	}

	@Override public double fundingCostAdjustment()
	{
		double fundingCostAdjustment = 0.;
		int adiabatGroupCount = _monoPathExposureAdjustmentArray.length;

		for (int adiabatGroupIndex = 0; adiabatGroupIndex < adiabatGroupCount; ++adiabatGroupIndex) {
			fundingCostAdjustment +=
				_monoPathExposureAdjustmentArray[adiabatGroupIndex].fundingCostAdjustment();
		}

		return fundingCostAdjustment;
	}

	@Override public double fundingBenefitAdjustment()
	{
		double fundingBenefitAdjustment = 0.;
		int adiabatGroupCount = _monoPathExposureAdjustmentArray.length;

		for (int adiabatGroupIndex = 0; adiabatGroupIndex < adiabatGroupCount; ++adiabatGroupIndex) {
			fundingBenefitAdjustment +=
				_monoPathExposureAdjustmentArray[adiabatGroupIndex].fundingBenefitAdjustment();
		}

		return fundingBenefitAdjustment;
	}

	@Override public double symmetricFundingValueAdjustment()
	{
		double symmetricFundingValueAdjustment = 0.;
		int adiabatGroupCount = _monoPathExposureAdjustmentArray.length;

		for (int adiabatGroupIndex = 0; adiabatGroupIndex < adiabatGroupCount; ++adiabatGroupIndex) {
			symmetricFundingValueAdjustment +=
				_monoPathExposureAdjustmentArray[adiabatGroupIndex].symmetricFundingValueAdjustment();
		}

		return symmetricFundingValueAdjustment;
	}

	@Override public double totalAdjustment()
	{
		double totalAdjustment = 0.;
		int adiabatGroupCount = _monoPathExposureAdjustmentArray.length;

		for (int adiabatGroupIndex = 0; adiabatGroupIndex < adiabatGroupCount; ++adiabatGroupIndex) {
			totalAdjustment +=
				_monoPathExposureAdjustmentArray[adiabatGroupIndex].creditAdjustment() +
				_monoPathExposureAdjustmentArray[adiabatGroupIndex].debtAdjustment() +
				_monoPathExposureAdjustmentArray[adiabatGroupIndex].fundingValueAdjustment();
		}

		return totalAdjustment;
	}
}
