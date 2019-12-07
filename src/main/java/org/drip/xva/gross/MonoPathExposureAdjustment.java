
package org.drip.xva.gross;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
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
 * <i>MonoPathExposureAdjustment</i> aggregates the Exposures and the Adjustments across Multiple
 * Netting/Funding Groups on a Single Path Projection Run along the Granularity of a Counter Party Group. The
 * References are:
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
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/XVAAnalyticsLibrary.md">XVA Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/README.md">Valuation Adjustments that account for Collateral, CC Credit/Debt and Funding Overhead</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/gross/README.md">XVA Gross Adiabat Exposure Aggregation</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class MonoPathExposureAdjustment implements org.drip.xva.gross.PathExposureAdjustment
{
	private org.drip.xva.netting.FundingGroupPath[] _fundingGroupPathArray = null;

	/**
	 * MonoPathExposureAdjustment Constructor
	 * 
	 * @param fundingGroupPathArray The Array of Funding Group Paths
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public MonoPathExposureAdjustment (
		final org.drip.xva.netting.FundingGroupPath[] fundingGroupPathArray)
		throws java.lang.Exception
	{
		if (null == (_fundingGroupPathArray = fundingGroupPathArray))
		{
			throw new java.lang.Exception ("MonoPathExposureAdjustment Constructor => Invalid Inputs");
		}

		int fundingGroupCount = _fundingGroupPathArray.length;

		if (0 == fundingGroupCount)
		{
			throw new java.lang.Exception ("MonoPathExposureAdjustment Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Array of the Funding Group Trajectory Paths
	 * 
	 * @return The Array of the Funding Group Trajectory Paths
	 */

	public org.drip.xva.netting.FundingGroupPath[] fundingGroupTrajectoryPaths()
	{
		return _fundingGroupPathArray;
	}

	/**
	 * Retrieve the Array of Credit/Debt Netting Group Trajectory Paths
	 * 
	 * @return The Array of Credit/Debt Netting Group Trajectory Paths
	 */

	public org.drip.xva.netting.CreditDebtGroupPath[] creditDebtGroupTrajectoryPaths()
	{
		int creditDebtGroupPathIndex = 0;
		int fundingGroupCount = _fundingGroupPathArray.length;

		java.util.List<org.drip.xva.netting.CreditDebtGroupPath> creditDebtGroupPathList = new
			java.util.ArrayList<org.drip.xva.netting.CreditDebtGroupPath>();

		for (int fundingGroupIndex = 0; fundingGroupIndex < fundingGroupCount; ++fundingGroupIndex)
		{
			org.drip.xva.netting.CreditDebtGroupPath[] creditDebtGroupPathArray =
				_fundingGroupPathArray[fundingGroupIndex].creditDebtGroupPathArray();

			for (org.drip.xva.netting.CreditDebtGroupPath creditDebtGroupPath : creditDebtGroupPathArray)
			{
				creditDebtGroupPathList.add (creditDebtGroupPath);
			}
		}

		org.drip.xva.netting.CreditDebtGroupPath[] creditDebtGroupPathArray = new
			org.drip.xva.netting.CreditDebtGroupPath[creditDebtGroupPathList.size()];

		for (org.drip.xva.netting.CreditDebtGroupPath creditDebtGroupPath : creditDebtGroupPathList)
		{
			creditDebtGroupPathArray[creditDebtGroupPathIndex++] = creditDebtGroupPath;
		}

		return creditDebtGroupPathArray;
	}

	@Override public org.drip.analytics.date.JulianDate[] vertexDates()
	{
		return _fundingGroupPathArray[0].vertexDates();
	}

	@Override public double[] vertexCollateralizedExposure()
	{
		int vertexCount = vertexDates().length;

		double[] vertexCollateralizedExposure = new double[vertexCount];

		for (int j = 0; j < vertexCount; ++j)
		{
			vertexCollateralizedExposure[j] = 0.;
		}

		for (org.drip.xva.netting.FundingGroupPath fundingGroupPath : _fundingGroupPathArray)
		{
			double[] fundingGroupVertexCollateralizedExposure =
				fundingGroupPath.vertexCollateralizedExposure();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				vertexCollateralizedExposure[vertexIndex] +=
					fundingGroupVertexCollateralizedExposure[vertexIndex];
			}
		}

		return vertexCollateralizedExposure;
	}

	@Override public double[] vertexCollateralizedExposurePV()
	{
		int vertexCount = vertexDates().length;

		double[] vertexCollateralizedExposurePV = new double[vertexCount];

		for (int j = 0; j < vertexCount; ++j)
		{
			vertexCollateralizedExposurePV[j] = 0.;
		}

		for (org.drip.xva.netting.FundingGroupPath fundingGroupPath : _fundingGroupPathArray)
		{
			double[] fundingGroupVertexCollateralizedExposurePV =
				fundingGroupPath.vertexCollateralizedExposurePV();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				vertexCollateralizedExposurePV[vertexIndex] +=
					fundingGroupVertexCollateralizedExposurePV[vertexIndex];
			}
		}

		return vertexCollateralizedExposurePV;
	}

	@Override public double[] vertexCollateralizedPositiveExposure()
	{
		int vertexCount = vertexDates().length;

		double[] vertexCollateralizedPositiveExposure = new double[vertexCount];

		for (int j = 0; j < vertexCount; ++j)
		{
			vertexCollateralizedPositiveExposure[j] = 0.;
		}

		for (org.drip.xva.netting.FundingGroupPath fundingGroupPath : _fundingGroupPathArray)
		{
			double[] fundingGroupVertexCollateralizedPositiveExposure =
				fundingGroupPath.vertexCollateralizedPositiveExposure();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				vertexCollateralizedPositiveExposure[vertexIndex] +=
					fundingGroupVertexCollateralizedPositiveExposure[vertexIndex];
			}
		}

		return vertexCollateralizedPositiveExposure;
	}

	@Override public double[] vertexCollateralizedPositiveExposurePV()
	{
		int vertexCount = vertexDates().length;

		double[] vertexCollateralizedPositiveExposurePV = new double[vertexCount];

		for (int j = 0; j < vertexCount; ++j)
		{
			vertexCollateralizedPositiveExposurePV[j] = 0.;
		}

		for (org.drip.xva.netting.FundingGroupPath fundingGroupPath : _fundingGroupPathArray)
		{
			double[] fundingGroupVertexCollateralizedPositiveExposurePV =
				fundingGroupPath.vertexCollateralizedPositiveExposurePV();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				vertexCollateralizedPositiveExposurePV[vertexIndex] +=
					fundingGroupVertexCollateralizedPositiveExposurePV[vertexIndex];
			}
		}

		return vertexCollateralizedPositiveExposurePV;
	}

	@Override public double[] vertexCollateralizedNegativeExposure()
	{
		int vertexCount = vertexDates().length;

		double[] vertexCollateralizedNegativeExposure = new double[vertexCount];

		for (int j = 0; j < vertexCount; ++j)
		{
			vertexCollateralizedNegativeExposure[j] = 0.;
		}

		for (org.drip.xva.netting.FundingGroupPath fundingGroupPath : _fundingGroupPathArray)
		{
			double[] fundingGroupVertexCollateralizedNegativeExposure =
				fundingGroupPath.vertexCollateralizedNegativeExposure();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				vertexCollateralizedNegativeExposure[vertexIndex] +=
					fundingGroupVertexCollateralizedNegativeExposure[vertexIndex];
			}
		}

		return vertexCollateralizedNegativeExposure;
	}

	@Override public double[] vertexCollateralizedNegativeExposurePV()
	{
		int vertexCount = vertexDates().length;

		double[] vertexCollateralizedNegativeExposurePV = new double[vertexCount];

		for (int j = 0; j < vertexCount; ++j)
		{
			vertexCollateralizedNegativeExposurePV[j] = 0.;
		}

		for (org.drip.xva.netting.FundingGroupPath fundingGroupPath : _fundingGroupPathArray)
		{
			double[] fundingGroupVertexCollateralizedNegativeExposurePV =
				fundingGroupPath.vertexCollateralizedNegativeExposurePV();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				vertexCollateralizedNegativeExposurePV[vertexIndex] +=
					fundingGroupVertexCollateralizedNegativeExposurePV[vertexIndex];
			}
		}

		return vertexCollateralizedNegativeExposurePV;
	}

	@Override public double[] vertexUncollateralizedExposure()
	{
		int vertexCount = vertexDates().length;

		double[] vertexUncollateralizedExposure = new double[vertexCount];

		for (int j = 0; j < vertexCount; ++j)
		{
			vertexUncollateralizedExposure[j] = 0.;
		}

		for (org.drip.xva.netting.FundingGroupPath fundingGroupPath : _fundingGroupPathArray)
		{
			double[] fundingGroupVertexUncollateralizedExposure =
				fundingGroupPath.vertexUncollateralizedExposure();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				vertexUncollateralizedExposure[vertexIndex] +=
					fundingGroupVertexUncollateralizedExposure[vertexIndex];
			}
		}

		return vertexUncollateralizedExposure;
	}

	@Override public double[] vertexUncollateralizedExposurePV()
	{
		int vertexCount = vertexDates().length;

		double[] vertexUncollateralizedExposurePV = new double[vertexCount];

		for (int j = 0; j < vertexCount; ++j)
		{
			vertexUncollateralizedExposurePV[j] = 0.;
		}

		for (org.drip.xva.netting.FundingGroupPath fundingGroupPath : _fundingGroupPathArray)
		{
			double[] fundingGroupVertexUncollateralizedExposurePV =
				fundingGroupPath.vertexUncollateralizedExposurePV();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				vertexUncollateralizedExposurePV[vertexIndex] +=
					fundingGroupVertexUncollateralizedExposurePV[vertexIndex];
			}
		}

		return vertexUncollateralizedExposurePV;
	}

	@Override public double[] vertexUncollateralizedPositiveExposure()
	{
		int vertexCount = vertexDates().length;

		double[] vertexUncollateralizedPositiveExposure = new double[vertexCount];

		for (int j = 0; j < vertexCount; ++j)
		{
			vertexUncollateralizedPositiveExposure[j] = 0.;
		}

		for (org.drip.xva.netting.FundingGroupPath fundingGroupPath : _fundingGroupPathArray)
		{
			double[] fundingGroupVertexUncollateralizedPositiveExposure =
				fundingGroupPath.vertexUncollateralizedPositiveExposure();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				vertexUncollateralizedPositiveExposure[vertexIndex] +=
					fundingGroupVertexUncollateralizedPositiveExposure[vertexIndex];
			}
		}

		return vertexUncollateralizedPositiveExposure;
	}

	@Override public double[] vertexUncollateralizedPositiveExposurePV()
	{
		int vertexCount = vertexDates().length;

		double[] vertexUncollateralizedPositiveExposurePV = new double[vertexCount];

		for (int j = 0; j < vertexCount; ++j)
		{
			vertexUncollateralizedPositiveExposurePV[j] = 0.;
		}

		for (org.drip.xva.netting.FundingGroupPath fundingGroupPath : _fundingGroupPathArray)
		{
			double[] fundingGroupVertexUncollateralizedPositiveExposurePV =
				fundingGroupPath.vertexUncollateralizedPositiveExposurePV();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				vertexUncollateralizedPositiveExposurePV[vertexIndex] +=
					fundingGroupVertexUncollateralizedPositiveExposurePV[vertexIndex];
			}
		}

		return vertexUncollateralizedPositiveExposurePV;
	}

	@Override public double[] vertexUncollateralizedNegativeExposure()
	{
		int vertexCount = vertexDates().length;

		double[] vertexUncollateralizedNegativeExposure = new double[vertexCount];

		for (int j = 0; j < vertexCount; ++j)
		{
			vertexUncollateralizedNegativeExposure[j] = 0.;
		}

		for (org.drip.xva.netting.FundingGroupPath fundingGroupPath : _fundingGroupPathArray)
		{
			double[] fundingGroupVertexUncollateralizedNegativeExposure =
				fundingGroupPath.vertexUncollateralizedNegativeExposure();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				vertexUncollateralizedNegativeExposure[vertexIndex] +=
					fundingGroupVertexUncollateralizedNegativeExposure[vertexIndex];
			}
		}

		return vertexUncollateralizedNegativeExposure;
	}

	@Override public double[] vertexUncollateralizedNegativeExposurePV()
	{
		int vertexCount = vertexDates().length;

		double[] vertexUncollateralizedNegativeExposurePV = new double[vertexCount];

		for (int j = 0; j < vertexCount; ++j)
		{
			vertexUncollateralizedNegativeExposurePV[j] = 0.;
		}

		for (org.drip.xva.netting.FundingGroupPath fundingGroupPath : _fundingGroupPathArray)
		{
			double[] fundingGroupVertexUncollateralizedNegativeExposurePV =
				fundingGroupPath.vertexUncollateralizedNegativeExposurePV();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				vertexUncollateralizedNegativeExposurePV[vertexIndex] +=
					fundingGroupVertexUncollateralizedNegativeExposurePV[vertexIndex];
			}
		}

		return vertexUncollateralizedNegativeExposurePV;
	}

	@Override public double[] vertexFundingExposure()
	{
		int vertexCount = vertexDates().length;

		int fundingGroupCount = _fundingGroupPathArray.length;
		double[] vertexFundingExposure = new double[vertexCount];

		for (int j = 0; j < vertexCount; ++j)
		{
			vertexFundingExposure[j] = 0.;
		}

		for (int fundingGroupIndex = 0; fundingGroupIndex < fundingGroupCount; ++fundingGroupIndex)
		{
			double[] fundingGroupVertexExposure =
				_fundingGroupPathArray[fundingGroupIndex].vertexFundingExposure();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				vertexFundingExposure[vertexIndex] += fundingGroupVertexExposure[vertexIndex];
			}
		}

		return vertexFundingExposure;
	}

	@Override public double[] vertexFundingExposurePV()
	{
		int vertexCount = vertexDates().length;

		int fundingGroupCount = _fundingGroupPathArray.length;
		double[] vertexFundingExposurePV = new double[vertexCount];

		for (int j = 0; j < vertexCount; ++j)
		{
			vertexFundingExposurePV[j] = 0.;
		}

		for (int fundingGroupIndex = 0; fundingGroupIndex < fundingGroupCount; ++fundingGroupIndex)
		{
			double[] fundingGroupVertexExposurePV =
				_fundingGroupPathArray[fundingGroupIndex].vertexFundingExposurePV();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				vertexFundingExposurePV[vertexIndex] += fundingGroupVertexExposurePV[vertexIndex];
			}
		}

		return vertexFundingExposurePV;
	}

	@Override public double unilateralCreditAdjustment()
	{
		double unilateralCreditAdjustment = 0.;
		int fundingGroupCount = _fundingGroupPathArray.length;

		for (int fundingGroupIndex = 0; fundingGroupIndex < fundingGroupCount; ++fundingGroupIndex)
		{
			unilateralCreditAdjustment +=
				_fundingGroupPathArray[fundingGroupIndex].unilateralCreditAdjustment();
		}

		return unilateralCreditAdjustment;
	}

	@Override public double bilateralCreditAdjustment()
	{
		double bilateralCreditAdjustment = 0.;
		int fundingGroupCount = _fundingGroupPathArray.length;

		for (int fundingGroupIndex = 0; fundingGroupIndex < fundingGroupCount; ++fundingGroupIndex)
		{
			bilateralCreditAdjustment +=
				_fundingGroupPathArray[fundingGroupIndex].bilateralCreditAdjustment();
		}

		return bilateralCreditAdjustment;
	}

	@Override public double creditAdjustment()
	{
		return unilateralCreditAdjustment();
	}

	@Override public double contraLiabilityCreditAdjustment()
	{
		double contraLiabilityCreditAdjustment = 0.;
		int fundingGroupCount = _fundingGroupPathArray.length;

		for (int fundingGroupIndex = 0; fundingGroupIndex < fundingGroupCount; ++fundingGroupIndex)
		{
			contraLiabilityCreditAdjustment +=
				_fundingGroupPathArray[fundingGroupIndex].contraLiabilityCreditAdjustment();
		}

		return contraLiabilityCreditAdjustment;
	}

	@Override public double unilateralDebtAdjustment()
	{
		double unilateralDebtAdjustment = 0.;
		int fundingGroupCount = _fundingGroupPathArray.length;

		for (int fundingGroupIndex = 0; fundingGroupIndex < fundingGroupCount; ++fundingGroupIndex)
		{
			unilateralDebtAdjustment += _fundingGroupPathArray[fundingGroupIndex].unilateralDebtAdjustment();
		}

		return unilateralDebtAdjustment;
	}

	@Override public double bilateralDebtAdjustment()
	{
		double bilateralDebtAdjustment = 0.;
		int fundingGroupCount = _fundingGroupPathArray.length;

		for (int fundingGroupIndex = 0; fundingGroupIndex < fundingGroupCount; ++fundingGroupIndex)
		{
			bilateralDebtAdjustment += _fundingGroupPathArray[fundingGroupIndex].bilateralDebtAdjustment();
		}

		return bilateralDebtAdjustment;
	}

	@Override public double debtAdjustment()
	{
		return unilateralDebtAdjustment();
	}

	@Override public double contraAssetDebtAdjustment()
	{
		double contraAssetDebtAdjustment = 0.;
		int fundingGroupCount = _fundingGroupPathArray.length;

		for (int fundingGroupIndex = 0; fundingGroupIndex < fundingGroupCount; ++fundingGroupIndex)
		{
			contraAssetDebtAdjustment +=
				_fundingGroupPathArray[fundingGroupIndex].contraAssetDebtAdjustment();
		}

		return contraAssetDebtAdjustment;
	}

	@Override public double unilateralCollateralAdjustment()
	{
		double unilateralCollateralAdjustment = 0.;
		int fundingGroupCount = _fundingGroupPathArray.length;

		for (int fundingGroupIndex = 0; fundingGroupIndex < fundingGroupCount; ++fundingGroupIndex)
		{
			unilateralCollateralAdjustment +=
				_fundingGroupPathArray[fundingGroupIndex].unilateralCollateralAdjustment();
		}

		return unilateralCollateralAdjustment;
	}

	@Override public double bilateralCollateralAdjustment()
	{
		double bilateralCollateralAdjustment = 0.;
		int fundingGroupCount = _fundingGroupPathArray.length;

		for (int fundingGroupIndex = 0; fundingGroupIndex < fundingGroupCount; ++fundingGroupIndex)
		{
			bilateralCollateralAdjustment +=
				_fundingGroupPathArray[fundingGroupIndex].bilateralCollateralAdjustment();
		}

		return bilateralCollateralAdjustment;
	}

	@Override public double collateralAdjustment()
	{
		return bilateralCollateralAdjustment();
	}

	@Override public double unilateralFundingValueAdjustment()
	{
		double unilateralFundingValueAdjustment = 0.;
		int fundingGroupCount = _fundingGroupPathArray.length;

		for (int fundingGroupIndex = 0; fundingGroupIndex < fundingGroupCount; ++fundingGroupIndex)
		{
			unilateralFundingValueAdjustment +=
				_fundingGroupPathArray[fundingGroupIndex].unilateralFundingValueAdjustment();
		}

		return unilateralFundingValueAdjustment;
	}

	@Override public double bilateralFundingValueAdjustment()
	{
		double bilateralFundingValueAdjustment = 0.;
		int fundingGroupCount = _fundingGroupPathArray.length;

		for (int fundingGroupIndex = 0; fundingGroupIndex < fundingGroupCount; ++fundingGroupIndex)
		{
			bilateralFundingValueAdjustment +=
				_fundingGroupPathArray[fundingGroupIndex].bilateralFundingValueAdjustment();
		}

		return bilateralFundingValueAdjustment;
	}

	@Override public double fundingValueAdjustment()
	{
		double fundingValueAdjustment = 0.;
		int fundingGroupCount = _fundingGroupPathArray.length;

		for (int fundingGroupIndex = 0; fundingGroupIndex < fundingGroupCount; ++fundingGroupIndex)
		{
			fundingValueAdjustment += _fundingGroupPathArray[fundingGroupIndex].fundingValueAdjustment();
		}

		return fundingValueAdjustment;
	}

	@Override public double fundingDebtAdjustment()
	{
		double fundingDebtAdjustment = 0.;
		int fundingGroupCount = _fundingGroupPathArray.length;

		for (int fundingGroupIndex = 0; fundingGroupIndex < fundingGroupCount; ++fundingGroupIndex)
		{
			fundingDebtAdjustment += _fundingGroupPathArray[fundingGroupIndex].fundingDebtAdjustment();
		}

		return fundingDebtAdjustment;
	}

	@Override public double fundingCostAdjustment()
	{
		double fundingCostAdjustment = 0.;
		int fundingGroupCount = _fundingGroupPathArray.length;

		for (int fundingGroupIndex = 0; fundingGroupIndex < fundingGroupCount; ++fundingGroupIndex)
		{
			fundingCostAdjustment += _fundingGroupPathArray[fundingGroupIndex].fundingCostAdjustment();
		}

		return fundingCostAdjustment;
	}

	@Override public double fundingBenefitAdjustment()
	{
		double fundingBenefitAdjustment = 0.;
		int fundingGroupCount = _fundingGroupPathArray.length;

		for (int fundingGroupIndex = 0; fundingGroupIndex < fundingGroupCount; ++fundingGroupIndex)
		{
			fundingBenefitAdjustment += _fundingGroupPathArray[fundingGroupIndex].fundingBenefitAdjustment();
		}

		return fundingBenefitAdjustment;
	}

	@Override public double symmetricFundingValueAdjustment()
	{
		double symmetricFundingValueAdjustment = 0.;
		int fundingGroupCount = _fundingGroupPathArray.length;

		for (int fundingGroupIndex = 0; fundingGroupIndex < fundingGroupCount; ++fundingGroupIndex)
		{
			symmetricFundingValueAdjustment +=
				_fundingGroupPathArray[fundingGroupIndex].symmetricFundingValueAdjustment();
		}

		return symmetricFundingValueAdjustment;
	}

	@Override public double totalAdjustment()
	{
		return collateralAdjustment() + creditAdjustment() + debtAdjustment() + fundingValueAdjustment();
	}
}
