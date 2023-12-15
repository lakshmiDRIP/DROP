
package org.drip.xva.gross;

import org.drip.analytics.date.JulianDate;
import org.drip.spline.params.SegmentCustomBuilderControl;
import org.drip.spline.stretch.BoundarySettings;
import org.drip.spline.stretch.MultiSegmentSequence;
import org.drip.spline.stretch.MultiSegmentSequenceBuilder;
import org.drip.xva.basel.ValueAdjustment;
import org.drip.xva.settings.StandardizedExposureGeneratorScheme;

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
 * <i>ExposureAdjustmentAggregator</i> aggregates across Multiple Exposure/Adjustment Paths belonging to the
 * Counter Party. The References are:
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

public class ExposureAdjustmentAggregator
{
	private PathExposureAdjustment[] _pathExposureAdjustmentArray = null;

	/**
	 * ExposureAdjustmentAggregator Constructor
	 * 
	 * @param pathExposureAdjustmentArray Array of the Counter Party Group Paths
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public ExposureAdjustmentAggregator (
		final PathExposureAdjustment[] pathExposureAdjustmentArray)
		throws Exception
	{
		if (null == (_pathExposureAdjustmentArray = pathExposureAdjustmentArray) ||
			0 == _pathExposureAdjustmentArray.length)
		{
			throw new Exception ("ExposureAdjustmentAggregator Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Array of Group Path Exposure Adjustments
	 * 
	 * @return Array of Group Path Exposure Adjustments
	 */

	public PathExposureAdjustment[] pathExposureAdjustmentArray()
	{
		return _pathExposureAdjustmentArray;
	}

	/**
	 * Retrieve the Array of the Vertex Anchor Dates
	 * 
	 * @return The Array of the Vertex Anchor Dates
	 */

	public JulianDate[] vertexDates()
	{
		return _pathExposureAdjustmentArray[0].vertexDates();
	}

	/**
	 * Retrieve the Array of Collateralized Exposures
	 * 
	 * @return The Array of Collateralized Exposures
	 */

	public double[] collateralizedExposure()
	{
		int vertexCount = vertexDates().length;

		int pathCount = _pathExposureAdjustmentArray.length;
		double[] collateralizedExposureArray = new double[vertexCount];

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
			collateralizedExposureArray[vertexIndex] = 0.;
		}

		for (int pathIndex = 0; pathIndex < pathCount; ++pathIndex) {
			double[] pathCollateralizedExposureArray =
				_pathExposureAdjustmentArray[pathIndex].vertexCollateralizedExposure();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
				collateralizedExposureArray[vertexIndex] += pathCollateralizedExposureArray[vertexIndex];
			}
		}

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
			collateralizedExposureArray[vertexIndex] /= pathCount;
		}

		return collateralizedExposureArray;
	}

	/**
	 * Retrieve the Array of Collateralized Exposure PV's
	 * 
	 * @return The Array of Collateralized Exposure PV's
	 */

	public double[] collateralizedExposurePV()
	{
		int vertexCount = vertexDates().length;

		int pathCount = _pathExposureAdjustmentArray.length;
		double[] collateralizedExposurePVArray = new double[vertexCount];

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
			collateralizedExposurePVArray[vertexIndex] = 0.;
		}

		for (int pathIndex = 0; pathIndex < pathCount; ++pathIndex) {
			double[] pathCollateralizedExposurePVArray =
				_pathExposureAdjustmentArray[pathIndex].vertexCollateralizedExposurePV();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
				collateralizedExposurePVArray[vertexIndex] += pathCollateralizedExposurePVArray[vertexIndex];
			}
		}

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
			collateralizedExposurePVArray[vertexIndex] /= pathCount;
		}

		return collateralizedExposurePVArray;
	}

	/**
	 * Retrieve the Array of Uncollateralized Exposures
	 * 
	 * @return The Array of Uncollateralized Exposures
	 */

	public double[] uncollateralizedExposure()
	{
		int vertexCount = vertexDates().length;

		int pathCount = _pathExposureAdjustmentArray.length;
		double[] uncollateralizedExposureArray = new double[vertexCount];

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
			uncollateralizedExposureArray[vertexIndex] = 0.;
		}

		for (int pathIndex = 0; pathIndex < pathCount; ++pathIndex) {
			double[] pathUncollateralizedExposureArray =
				_pathExposureAdjustmentArray[pathIndex].vertexUncollateralizedExposure();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
				uncollateralizedExposureArray[vertexIndex] += pathUncollateralizedExposureArray[vertexIndex];
			}
		}

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			uncollateralizedExposureArray[vertexIndex] /= pathCount;

		return uncollateralizedExposureArray;
	}

	/**
	 * Retrieve the Array of Uncollateralized Exposure PV's
	 * 
	 * @return The Array of Uncollateralized Exposure PV's
	 */

	public double[] uncollateralizedExposurePV()
	{
		int vertexCount = vertexDates().length;

		int pathCount = _pathExposureAdjustmentArray.length;
		double[] uncollateralizedExposurePVArray = new double[vertexCount];

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
			uncollateralizedExposurePVArray[vertexIndex] = 0.;
		}

		for (int pathIndex = 0; pathIndex < pathCount; ++pathIndex) {
			double[] pathUncollateralizedExposurePVArray =
				_pathExposureAdjustmentArray[pathIndex].vertexUncollateralizedExposurePV();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
				uncollateralizedExposurePVArray[vertexIndex] +=
					pathUncollateralizedExposurePVArray[vertexIndex];
			}
		}

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
			uncollateralizedExposurePVArray[vertexIndex] /= pathCount;
		}

		return uncollateralizedExposurePVArray;
	}

	/**
	 * Retrieve the Array of Collateralized Positive Exposures
	 * 
	 * @return The Array of Collateralized Positive Exposures
	 */

	public double[] collateralizedPositiveExposure()
	{
		int vertexCount = vertexDates().length;

		int pathCount = _pathExposureAdjustmentArray.length;
		double[] collateralizedPositiveExposureArray = new double[vertexCount];

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
			collateralizedPositiveExposureArray[vertexIndex] = 0.;
		}

		for (int pathIndex = 0; pathIndex < pathCount; ++pathIndex) {
			double[] pathCollateralizedPositiveExposureArray =
				_pathExposureAdjustmentArray[pathIndex].vertexCollateralizedPositiveExposure();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
				collateralizedPositiveExposureArray[vertexIndex] +=
					pathCollateralizedPositiveExposureArray[vertexIndex];
			}
		}

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
			collateralizedPositiveExposureArray[vertexIndex] /= pathCount;
		}

		return collateralizedPositiveExposureArray;
	}

	/**
	 * Retrieve the Array of Collateralized Positive Exposure PV
	 * 
	 * @return The Array of Collateralized Positive Exposure PV
	 */

	public double[] collateralizedPositiveExposurePV()
	{
		int vertexCount = vertexDates().length;

		int pathCount = _pathExposureAdjustmentArray.length;
		double[] collateralizedPositiveExposurePVArray = new double[vertexCount];

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
			collateralizedPositiveExposurePVArray[vertexIndex] = 0.;
		}

		for (int pathIndex = 0; pathIndex < pathCount; ++pathIndex) {
			double[] pathCollateralizedPositiveExposurePVArray =
				_pathExposureAdjustmentArray[pathIndex].vertexCollateralizedPositiveExposurePV();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
				collateralizedPositiveExposurePVArray[vertexIndex] +=
					pathCollateralizedPositiveExposurePVArray[vertexIndex];
			}
		}

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
			collateralizedPositiveExposurePVArray[vertexIndex] /= pathCount;
		}

		return collateralizedPositiveExposurePVArray;
	}

	/**
	 * Retrieve the Array of Uncollateralized Positive Exposures
	 * 
	 * @return The Array of Uncollateralized Positive Exposures
	 */

	public double[] uncollateralizedPositiveExposure()
	{
		int vertexCount = vertexDates().length;

		int pathCount = _pathExposureAdjustmentArray.length;
		double[] uncollateralizedPositiveExposureArray = new double[vertexCount];

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
			uncollateralizedPositiveExposureArray[vertexIndex] = 0.;
		}

		for (int pathIndex = 0; pathIndex < pathCount; ++pathIndex) {
			double[] pathUncollateralizedPositiveExposureArray =
				_pathExposureAdjustmentArray[pathIndex].vertexUncollateralizedPositiveExposure();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
				uncollateralizedPositiveExposureArray[vertexIndex] +=
					pathUncollateralizedPositiveExposureArray[vertexIndex];
			}
		}

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
			uncollateralizedPositiveExposureArray[vertexIndex] /= pathCount;
		}

		return uncollateralizedPositiveExposureArray;
	}

	/**
	 * Retrieve the Array of Uncollateralized Positive Exposure PV
	 * 
	 * @return The Array of Uncollateralized Positive Exposure PV
	 */

	public double[] uncollateralizedPositiveExposurePV()
	{
		int vertexCount = vertexDates().length;

		int pathCount = _pathExposureAdjustmentArray.length;
		double[] uncollateralizedPositiveExposurePVArray = new double[vertexCount];

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
			uncollateralizedPositiveExposurePVArray[vertexIndex] = 0.;
		}

		for (int pathIndex = 0; pathIndex < pathCount; ++pathIndex) {
			double[] pathUncollateralizedPositiveExposurePVArray =
				_pathExposureAdjustmentArray[pathIndex].vertexUncollateralizedPositiveExposurePV();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
				uncollateralizedPositiveExposurePVArray[vertexIndex] +=
					pathUncollateralizedPositiveExposurePVArray[vertexIndex];
			}
		}

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
			uncollateralizedPositiveExposurePVArray[vertexIndex] /= pathCount;
		}

		return uncollateralizedPositiveExposurePVArray;
	}

	/**
	 * Retrieve the Array of Collateralized Negative Exposures
	 * 
	 * @return The Array of Collateralized Negative Exposures
	 */

	public double[] collateralizedNegativeExposure()
	{
		int vertexCount = vertexDates().length;

		int pathCount = _pathExposureAdjustmentArray.length;
		double[] collateralizedNegativeExposureArray = new double[vertexCount];

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
			collateralizedNegativeExposureArray[vertexIndex] = 0.;
		}

		for (int pathIndex = 0; pathIndex < pathCount; ++pathIndex) {
			double[] pathCollateralizedNegativeExposureArray =
				_pathExposureAdjustmentArray[pathIndex].vertexCollateralizedNegativeExposure();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
				collateralizedNegativeExposureArray[vertexIndex] +=
					pathCollateralizedNegativeExposureArray[vertexIndex];
			}
		}

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
			collateralizedNegativeExposureArray[vertexIndex] /= pathCount;
		}

		return collateralizedNegativeExposureArray;
	}

	/**
	 * Retrieve the Array of Collateralized Negative Exposure PV
	 * 
	 * @return The Array of Collateralized Negative Exposure PV
	 */

	public double[] collateralizedNegativeExposurePV()
	{
		int vertexCount = vertexDates().length;

		int pathCount = _pathExposureAdjustmentArray.length;
		double[] collateralizedNegativeExposurePVArray = new double[vertexCount];

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
			collateralizedNegativeExposurePVArray[vertexIndex] = 0.;
		}

		for (int pathIndex = 0; pathIndex < pathCount; ++pathIndex) {
			double[] pathCollateralizedNegativeExposurePVArray =
				_pathExposureAdjustmentArray[pathIndex].vertexCollateralizedNegativeExposurePV();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
				collateralizedNegativeExposurePVArray[vertexIndex] +=
					pathCollateralizedNegativeExposurePVArray[vertexIndex];
			}
		}

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
			collateralizedNegativeExposurePVArray[vertexIndex] /= pathCount;
		}

		return collateralizedNegativeExposurePVArray;
	}

	/**
	 * Retrieve the Array of Uncollateralized Negative Exposures
	 * 
	 * @return The Array of Uncollateralized Negative Exposures
	 */

	public double[] uncollateralizedNegativeExposure()
	{
		int vertexCount = vertexDates().length;

		int pathCount = _pathExposureAdjustmentArray.length;
		double[] uncollateralizedNegativeExposureArray = new double[vertexCount];

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
			uncollateralizedNegativeExposureArray[vertexIndex] = 0.;
		}

		for (int pathIndex = 0; pathIndex < pathCount; ++pathIndex) {
			double[] pathUncollateralizedNegativeExposureArray =
				_pathExposureAdjustmentArray[pathIndex].vertexUncollateralizedNegativeExposure();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
				uncollateralizedNegativeExposureArray[vertexIndex] +=
					pathUncollateralizedNegativeExposureArray[vertexIndex];
			}
		}

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
			uncollateralizedNegativeExposureArray[vertexIndex] /= pathCount;
		}

		return uncollateralizedNegativeExposureArray;
	}

	/**
	 * Retrieve the Array of Uncollateralized Negative Exposure PV
	 * 
	 * @return The Array of Uncollateralized Negative Exposure PV
	 */

	public double[] uncollateralizedNegativeExposurePV()
	{
		int vertexCount = vertexDates().length;

		int pathCount = _pathExposureAdjustmentArray.length;
		double[] uncollateralizedNegativeExposurePVArray = new double[vertexCount];

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
			uncollateralizedNegativeExposurePVArray[vertexIndex] = 0.;
		}

		for (int pathIndex = 0; pathIndex < pathCount; ++pathIndex) {
			double[] pathUncollateralizedNegativeExposurePVArray =
				_pathExposureAdjustmentArray[pathIndex].vertexUncollateralizedNegativeExposurePV();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
				uncollateralizedNegativeExposurePVArray[vertexIndex] +=
					pathUncollateralizedNegativeExposurePVArray[vertexIndex];
			}
		}

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
			uncollateralizedNegativeExposurePVArray[vertexIndex] /= pathCount;
		}

		return uncollateralizedNegativeExposurePVArray;
	}

	/**
	 * Retrieve the Array of Funding Exposures
	 * 
	 * @return The Array of Funding Exposures
	 */

	public double[] fundingExposure()
	{
		int vertexCount = vertexDates().length;

		double[] fundingExposureArray = new double[vertexCount];
		int pathCount = _pathExposureAdjustmentArray.length;

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
			fundingExposureArray[vertexIndex] = 0.;
		}

		for (int pathIndex = 0; pathIndex < pathCount; ++pathIndex) {
			double[] pathFundingExposureArray =
				_pathExposureAdjustmentArray[pathIndex].vertexFundingExposure();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
				fundingExposureArray[vertexIndex] += pathFundingExposureArray[vertexIndex];
			}
		}

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
			fundingExposureArray[vertexIndex] /= pathCount;
		}

		return fundingExposureArray;
	}

	/**
	 * Retrieve the Array of Funding Exposure PV
	 * 
	 * @return The Array of Funding Exposure PV
	 */

	public double[] fundingExposurePV()
	{
		int vertexCount = vertexDates().length;

		int pathCount = _pathExposureAdjustmentArray.length;
		double[] fundingExposurePVArray = new double[vertexCount];

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
			fundingExposurePVArray[vertexIndex] = 0.;
		}

		for (int pathIndex = 0; pathIndex < pathCount; ++pathIndex) {
			double[] pathFundingExposurePVArray =
				_pathExposureAdjustmentArray[pathIndex].vertexFundingExposurePV();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
				fundingExposurePVArray[vertexIndex] += pathFundingExposurePVArray[vertexIndex];
			}
		}

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
			fundingExposurePVArray[vertexIndex] /= pathCount;
		}

		return fundingExposurePVArray;
	}

	/**
	 * Retrieve the Expected Bilateral Collateral VA
	 * 
	 * @return The Expected Bilateral Collateral VA
	 */

	public ValueAdjustment ftdcolva()
	{
		double ftdcolva = 0.;
		int pathCount = _pathExposureAdjustmentArray.length;

		try {
			for (int pathIndex = 0; pathIndex < pathCount; ++pathIndex) {
				ftdcolva += _pathExposureAdjustmentArray[pathIndex].bilateralCollateralAdjustment();
			}
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}

		return ValueAdjustment.COLVA (ftdcolva / pathCount);
	}

	/**
	 * Retrieve the Expected Collateral VA
	 * 
	 * @return The Expected Collateral VA
	 */

	public ValueAdjustment colva()
	{
		double colva = 0.;
		int pathCount = _pathExposureAdjustmentArray.length;

		try {
			for (int pathIndex = 0; pathIndex < pathCount; ++pathIndex) {
				colva += _pathExposureAdjustmentArray[pathIndex].bilateralCollateralAdjustment();
			}
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}

		return ValueAdjustment.COLVA (colva / pathCount);
	}

	/**
	 * Retrieve the Expected Unilateral CVA
	 * 
	 * @return The Expected Unilateral CVA
	 */

	public ValueAdjustment ucva()
	{
		double ucva = 0.;
		int pathCount = _pathExposureAdjustmentArray.length;

		try {
			for (int pathIndex = 0; pathIndex < pathCount; ++pathIndex) {
				ucva += _pathExposureAdjustmentArray[pathIndex].unilateralCreditAdjustment();
			}
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}

		return ValueAdjustment.UCVA (ucva / pathCount);
	}

	/**
	 * Retrieve the Expected Bilateral/FTD CVA
	 * 
	 * @return The Expected Bilateral/FTD CVA
	 */

	public ValueAdjustment ftdcva()
	{
		double ftdcva = 0.;
		int pathCount = _pathExposureAdjustmentArray.length;

		try {
			for (int pathIndex = 0; pathIndex < pathCount; ++pathIndex) {
				ftdcva += _pathExposureAdjustmentArray[pathIndex].bilateralCreditAdjustment();
			}
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}

		return ValueAdjustment.FTDCVA (ftdcva / pathCount);
	}

	/**
	 * Retrieve the Expected CVA
	 * 
	 * @return The Expected CVA
	 */

	public ValueAdjustment cva()
	{
		return ftdcva();
	}

	/**
	 * Retrieve the Expected CVA Contra-Liability
	 * 
	 * @return The Expected CVA Contra-Liability
	 */

	public ValueAdjustment cvacl()
	{
		double cvacl = 0.;
		int pathCount = _pathExposureAdjustmentArray.length;

		try {
			for (int pathIndex = 0; pathIndex < pathCount; ++pathIndex) {
				cvacl += _pathExposureAdjustmentArray[pathIndex].contraLiabilityCreditAdjustment();
			}
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}

		return ValueAdjustment.CVACL (cvacl / pathCount);
	}

	/**
	 * Retrieve the Expected Unilateral DVA
	 * 
	 * @return The Expected Unilateral DVA
	 */

	public ValueAdjustment udva()
	{
		double udva = 0.;
		int pathCount = _pathExposureAdjustmentArray.length;

		try {
			for (int pathIndex = 0; pathIndex < pathCount; ++pathIndex) {
				udva += _pathExposureAdjustmentArray[pathIndex].unilateralDebtAdjustment();
			}
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}

		return ValueAdjustment.DVA (udva / pathCount);
	}

	/**
	 * Retrieve the Expected Bilateral DVA
	 * 
	 * @return The Expected Bilateral DVA
	 */

	public ValueAdjustment ftddva()
	{
		double ftddva = 0.;
		int pathCount = _pathExposureAdjustmentArray.length;

		try {
			for (int pathIndex = 0; pathIndex < pathCount; ++pathIndex) {
				ftddva += _pathExposureAdjustmentArray[pathIndex].bilateralDebtAdjustment();
			}
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}

		return ValueAdjustment.DVA (ftddva / pathCount);
	}

	/**
	 * Retrieve the Expected DVA
	 * 
	 * @return The Expected DVA
	 */

	public ValueAdjustment dva()
	{
		double dva = 0.;
		int pathCount = _pathExposureAdjustmentArray.length;

		try {
			for (int pathIndex = 0; pathIndex < pathCount; ++pathIndex) {
				dva += _pathExposureAdjustmentArray[pathIndex].debtAdjustment();
			}
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}

		return ValueAdjustment.DVA (dva / pathCount);
	}

	/**
	 * Retrieve the Expected FVA
	 * 
	 * @return The Expected FVA
	 */

	public ValueAdjustment fva()
	{
		double fva = 0.;
		int pathCount = _pathExposureAdjustmentArray.length;

		try {
			for (int pathIndex = 0; pathIndex < pathCount; ++pathIndex) {
				fva += _pathExposureAdjustmentArray[pathIndex].fundingValueAdjustment();
			}
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}

		return ValueAdjustment.FVA (fva / pathCount);
	}

	/**
	 * Retrieve the Expected FDA
	 * 
	 * @return The Expected FDA
	 */

	public ValueAdjustment fda()
	{
		double fda = 0.;
		int pathCount = _pathExposureAdjustmentArray.length;

		try {
			for (int pathIndex = 0; pathIndex < pathCount; ++pathIndex) {
				fda += _pathExposureAdjustmentArray[pathIndex].fundingDebtAdjustment();
			}
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}

		return ValueAdjustment.FDA (fda / pathCount);
	}

	/**
	 * Retrieve the Expected DVA2
	 * 
	 * @return The Expected DVA2
	 */

	public ValueAdjustment dva2()
	{
		return fda();
	}

	/**
	 * Retrieve the Expected FCA
	 * 
	 * @return The Expected FCA
	 */

	public ValueAdjustment fca()
	{
		double fca = 0.;
		int pathCount = _pathExposureAdjustmentArray.length;

		for (int pathIndex = 0; pathIndex < pathCount; ++pathIndex) {
			fca += _pathExposureAdjustmentArray[pathIndex].fundingCostAdjustment();
		}

		return ValueAdjustment.HYBRID (fca / pathCount);
	}

	/**
	 * Retrieve the Expected FBA
	 * 
	 * @return The Expected FBA
	 */

	public ValueAdjustment fba()
	{
		double fba = 0.;
		int pathCount = _pathExposureAdjustmentArray.length;

		for (int pathIndex = 0; pathIndex < pathCount; ++pathIndex) {
			fba += _pathExposureAdjustmentArray[pathIndex].fundingBenefitAdjustment();
		}

		return ValueAdjustment.HYBRID (fba / pathCount);
	}

	/**
	 * Retrieve the Expected SFVA
	 * 
	 * @return The Expected SFVA
	 */

	public ValueAdjustment sfva()
	{
		double sfva = 0.;
		int pathCount = _pathExposureAdjustmentArray.length;

		for (int pathIndex = 0; pathIndex < pathCount; ++pathIndex) {
			sfva += _pathExposureAdjustmentArray[pathIndex].symmetricFundingValueAdjustment();
		}

		return ValueAdjustment.HYBRID (sfva / pathCount);
	}

	/**
	 * Retrieve the Total VA
	 * 
	 * @return The Total VA
	 */

	public double total()
	{
		return cva().amount() + dva().amount() + fva().amount() + colva().amount();
	}

	/**
	 * Generate the "Digest" containing the "Thin" Path Statistics
	 * 
	 * @return The "Digest" containing the "Thin" Path Statistics
	 */

	public ExposureAdjustmentDigest digest()
	{
		int vertexCount = vertexDates().length;

		int pathCount = _pathExposureAdjustmentArray.length;
		double[] pathCVA = new double[pathCount];
		double[] pathDVA = new double[pathCount];
		double[] pathFBA = new double[pathCount];
		double[] pathFCA = new double[pathCount];
		double[] pathFDA = new double[pathCount];
		double[] pathFVA = new double[pathCount];
		double[] pathUCVA = new double[pathCount];
		double[] pathSFVA = new double[pathCount];
		double[] pathCVACL = new double[pathCount];
		double[] pathFTDCVA = new double[pathCount];
		double[] pathCOLVA = new double[pathCount];
		double[] pathTotalVA = new double[pathCount];
		double[] pathFTDCOLVA = new double[pathCount];
		double[][] fundingExposure = new double[vertexCount][pathCount];
		double[][] fundingExposurePV = new double[vertexCount][pathCount];
		double[][] collateralizedExposure = new double[vertexCount][pathCount];
		double[][] uncollateralizedExposure = new double[vertexCount][pathCount];
		double[][] collateralizedExposurePV = new double[vertexCount][pathCount];
		double[][] uncollateralizedExposurePV = new double[vertexCount][pathCount];
		double[][] collateralizedPositiveExposure = new double[vertexCount][pathCount];
		double[][] collateralizedNegativeExposure = new double[vertexCount][pathCount];
		double[][] uncollateralizedPositiveExposure = new double[vertexCount][pathCount];
		double[][] uncollateralizedNegativeExposure = new double[vertexCount][pathCount];
		double[][] collateralizedPositiveExposurePV = new double[vertexCount][pathCount];
		double[][] collateralizedNegativeExposurePV = new double[vertexCount][pathCount];
		double[][] uncollateralizedPositiveExposurePV = new double[vertexCount][pathCount];
		double[][] uncollateralizedNegativeExposurePV = new double[vertexCount][pathCount];

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
			for (int pathIndex = 0; pathIndex < pathCount; ++pathIndex) {
				collateralizedExposure[vertexIndex][pathIndex] = 0.;
				uncollateralizedExposure[vertexIndex][pathIndex] = 0.;
				collateralizedExposurePV[vertexIndex][pathIndex] = 0.;
				uncollateralizedExposurePV[vertexIndex][pathIndex] = 0.;
				collateralizedPositiveExposure[vertexIndex][pathIndex] = 0.;
				collateralizedNegativeExposure[vertexIndex][pathIndex] = 0.;
				uncollateralizedPositiveExposure[vertexIndex][pathIndex] = 0.;
				uncollateralizedNegativeExposure[vertexIndex][pathIndex] = 0.;
				collateralizedPositiveExposurePV[vertexIndex][pathIndex] = 0.;
				collateralizedNegativeExposurePV[vertexIndex][pathIndex] = 0.;
				uncollateralizedPositiveExposurePV[vertexIndex][pathIndex] = 0.;
				uncollateralizedNegativeExposurePV[vertexIndex][pathIndex] = 0.;
			}
		}

		for (int pathIndex = 0; pathIndex < pathCount; ++pathIndex) {
			double[] pathCollateralizedExposure =
				_pathExposureAdjustmentArray[pathIndex].vertexCollateralizedExposure();

			double[] pathCollateralizedExposurePV =
				_pathExposureAdjustmentArray[pathIndex].vertexCollateralizedExposurePV();

			double[] pathCollateralizedPositiveExposure =
				_pathExposureAdjustmentArray[pathIndex].vertexCollateralizedPositiveExposure();

			double[] pathCollateralizedPositiveExposurePV =
				_pathExposureAdjustmentArray[pathIndex].vertexCollateralizedPositiveExposurePV();

			double[] pathCollateralizedNegativeExposure =
				_pathExposureAdjustmentArray[pathIndex].vertexCollateralizedNegativeExposure();

			double[] pathCollateralizedNegativeExposurePV =
				_pathExposureAdjustmentArray[pathIndex].vertexCollateralizedNegativeExposurePV();

			double[] pathUncollateralizedExposure =
				_pathExposureAdjustmentArray[pathIndex].vertexUncollateralizedExposure();

			double[] pathUncollateralizedExposurePV =
				_pathExposureAdjustmentArray[pathIndex].vertexUncollateralizedExposurePV();

			double[] pathUncollateralizedPositiveExposure =
				_pathExposureAdjustmentArray[pathIndex].vertexUncollateralizedPositiveExposure();

			double[] pathUncollateralizedPositiveExposurePV =
				_pathExposureAdjustmentArray[pathIndex].vertexUncollateralizedPositiveExposurePV();

			double[] pathUncollateralizedNegativeExposure =
				_pathExposureAdjustmentArray[pathIndex].vertexUncollateralizedNegativeExposure();

			double[] pathUncollateralizedNegativeExposurePV =
				_pathExposureAdjustmentArray[pathIndex].vertexUncollateralizedNegativeExposurePV();

			double[] pathFundingExposure = _pathExposureAdjustmentArray[pathIndex].vertexFundingExposure();

			double[] pathFundingExposurePV =
				_pathExposureAdjustmentArray[pathIndex].vertexFundingExposurePV();

			try {
				pathCVA[pathIndex] = _pathExposureAdjustmentArray[pathIndex].creditAdjustment();

				pathDVA[pathIndex] = _pathExposureAdjustmentArray[pathIndex].debtAdjustment();

				pathFCA[pathIndex] = _pathExposureAdjustmentArray[pathIndex].fundingCostAdjustment();

				pathFDA[pathIndex] = _pathExposureAdjustmentArray[pathIndex].fundingDebtAdjustment();

				pathFVA[pathIndex] = _pathExposureAdjustmentArray[pathIndex].fundingValueAdjustment();

				pathFBA[pathIndex] = _pathExposureAdjustmentArray[pathIndex].fundingBenefitAdjustment();

				pathUCVA[pathIndex] = _pathExposureAdjustmentArray[pathIndex].unilateralCreditAdjustment();

				pathSFVA[pathIndex] =
					_pathExposureAdjustmentArray[pathIndex].symmetricFundingValueAdjustment();

				pathCVACL[pathIndex] =
					_pathExposureAdjustmentArray[pathIndex].contraLiabilityCreditAdjustment();

				pathFTDCVA[pathIndex] = _pathExposureAdjustmentArray[pathIndex].bilateralCreditAdjustment();

				pathCOLVA[pathIndex] =
					_pathExposureAdjustmentArray[pathIndex].bilateralCollateralAdjustment();

				pathFTDCOLVA[pathIndex] =
					_pathExposureAdjustmentArray[pathIndex].bilateralCollateralAdjustment();

				pathTotalVA[pathIndex] = _pathExposureAdjustmentArray[pathIndex].totalAdjustment();
			} catch (Exception e) {
				e.printStackTrace();

				return null;
			}

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
				collateralizedExposure[vertexIndex][pathIndex] =
					pathCollateralizedExposure[vertexIndex];
				collateralizedExposurePV[vertexIndex][pathIndex] =
					pathCollateralizedExposurePV[vertexIndex];
				collateralizedPositiveExposure[vertexIndex][pathIndex] =
					pathCollateralizedPositiveExposure[vertexIndex];
				collateralizedPositiveExposurePV[vertexIndex][pathIndex] =
					pathCollateralizedPositiveExposurePV[vertexIndex];
				collateralizedNegativeExposure[vertexIndex][pathIndex] =
					pathCollateralizedNegativeExposure[vertexIndex];
				collateralizedNegativeExposurePV[vertexIndex][pathIndex] =
					pathCollateralizedNegativeExposurePV[vertexIndex];
				uncollateralizedExposure[vertexIndex][pathIndex] =
					pathUncollateralizedExposure[vertexIndex];
				uncollateralizedExposurePV[vertexIndex][pathIndex] =
					pathUncollateralizedExposurePV[vertexIndex];
				uncollateralizedPositiveExposure[vertexIndex][pathIndex] =
					pathUncollateralizedPositiveExposure[vertexIndex];
				uncollateralizedPositiveExposurePV[vertexIndex][pathIndex] =
					pathUncollateralizedPositiveExposurePV[vertexIndex];
				uncollateralizedNegativeExposure[vertexIndex][pathIndex] =
					pathUncollateralizedNegativeExposure[vertexIndex];
				uncollateralizedNegativeExposurePV[vertexIndex][pathIndex] =
					pathUncollateralizedNegativeExposurePV[vertexIndex];
				fundingExposure[vertexIndex][pathIndex] = pathFundingExposure[vertexIndex];
				fundingExposurePV[vertexIndex][pathIndex] = pathFundingExposurePV[vertexIndex];
			}
		}

		try {
			return new org.drip.xva.gross.ExposureAdjustmentDigest (
				pathCOLVA,
				pathFTDCOLVA,
				pathUCVA,
				pathFTDCVA,
				pathCVA,
				pathCVACL,
				pathDVA,
				pathFVA,
				pathFDA,
				pathFCA,
				pathFBA,
				pathSFVA,
				pathTotalVA,
				collateralizedExposure,
				collateralizedExposurePV,
				collateralizedPositiveExposure,
				collateralizedPositiveExposurePV,
				collateralizedNegativeExposure,
				collateralizedNegativeExposurePV,
				uncollateralizedExposure,
				uncollateralizedExposurePV,
				uncollateralizedPositiveExposure,
				uncollateralizedPositiveExposurePV,
				uncollateralizedNegativeExposure,
				uncollateralizedNegativeExposurePV,
				fundingExposure,
				fundingExposurePV
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the Basel Exposure Digest
	 * 
	 * @param standardizedExposureGeneratorScheme The Standardized Basel Exposure Generation Scheme
	 * 
	 * @return The Basel Exposure Digest
	 */

	public BaselExposureDigest baselExposureDigest (
		final StandardizedExposureGeneratorScheme standardizedExposureGeneratorScheme)
	{
		if (null == standardizedExposureGeneratorScheme) {
			return null;
		}

		JulianDate[] vertexJulianDateArray = vertexDates();

		int vertexCount = vertexJulianDateArray.length;
		int[] vertexDateArray = new int[vertexCount];
		int pathCount = _pathExposureAdjustmentArray.length;
		double[] collateralizedPositiveExposureArray = new double[vertexCount];
		double[] effectiveCollateralizedPositiveExposureArray = new double[vertexCount];
		SegmentCustomBuilderControl[] collateralizedExposureSegmentBuilderControlArray =
			new SegmentCustomBuilderControl[vertexCount - 1];
		SegmentCustomBuilderControl[] collateralizedPositiveExposureSegmentBuilderControlArray =
			new SegmentCustomBuilderControl[vertexCount - 1];

		SegmentCustomBuilderControl collateralizedExposureSegmentBuilderControl =
			standardizedExposureGeneratorScheme.collateralizedExposureSegmentBuilderControl();

		SegmentCustomBuilderControl collateralizedPositiveExposureSegmentBuilderControl =
			standardizedExposureGeneratorScheme.collateralizedPositiveExposureSegmentBuilderControl();

		for (int i = 0; i < vertexCount - 1; ++i) {
			collateralizedExposureSegmentBuilderControlArray[i] =
				collateralizedExposureSegmentBuilderControl;
			collateralizedPositiveExposureSegmentBuilderControlArray[i] =
				collateralizedPositiveExposureSegmentBuilderControl;
		}

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
			collateralizedPositiveExposureArray[vertexIndex] = 0.;

			vertexDateArray[vertexIndex] = vertexJulianDateArray[vertexIndex].julian();
		}

		for (int pathIndex = 0; pathIndex < pathCount; ++pathIndex) {
			double[] pathCollateralizedPositiveExposureArray =
				_pathExposureAdjustmentArray[pathIndex].vertexCollateralizedPositiveExposure();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
				collateralizedPositiveExposureArray[vertexIndex] +=
					pathCollateralizedPositiveExposureArray[vertexIndex];
			}
		}

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
			collateralizedPositiveExposureArray[vertexIndex] /= pathCount;

			if (0 == vertexIndex) {
				effectiveCollateralizedPositiveExposureArray[0] = collateralizedPositiveExposureArray[0];
			} else {
				effectiveCollateralizedPositiveExposureArray[vertexIndex] =
					collateralizedPositiveExposureArray[vertexIndex] >
					effectiveCollateralizedPositiveExposureArray[vertexIndex - 1] ?
					collateralizedPositiveExposureArray[vertexIndex] :
					effectiveCollateralizedPositiveExposureArray[vertexIndex - 1];
			}
		}

		try {
			MultiSegmentSequence multiSegmentSequenceCollateralizedPositiveExposure =
				MultiSegmentSequenceBuilder.CreateCalibratedStretchEstimator (
					"CollateralizedPositiveExposure",
					vertexDateArray,
					collateralizedPositiveExposureArray,
					collateralizedExposureSegmentBuilderControlArray,
					null,
					BoundarySettings.NaturalStandard(),
					MultiSegmentSequence.CALIBRATE
				);

			MultiSegmentSequence multiSegmentSequenceEffectiveCollateralizedPositiveExposure =
				MultiSegmentSequenceBuilder.CreateCalibratedStretchEstimator (
					"EffectiveCollateralizedPositiveExposure",
					vertexDateArray,
					effectiveCollateralizedPositiveExposureArray,
					collateralizedPositiveExposureSegmentBuilderControlArray,
					null,
					BoundarySettings.NaturalStandard(),
					MultiSegmentSequence.CALIBRATE
				);

			if (null == multiSegmentSequenceCollateralizedPositiveExposure ||
				null == multiSegmentSequenceEffectiveCollateralizedPositiveExposure) {
				return null;
			}

			int exposureGeneratorTimeIntegrand = standardizedExposureGeneratorScheme.timeIntegrand();

			int integrandFinishDate = vertexDateArray[0] + exposureGeneratorTimeIntegrand;

			double effectiveExpectedPositiveExposure =
				multiSegmentSequenceEffectiveCollateralizedPositiveExposure.toAU().integrate (
					vertexDateArray[0],
					integrandFinishDate
				) / exposureGeneratorTimeIntegrand;

			return new BaselExposureDigest (
				collateralizedPositiveExposureArray[0],
				multiSegmentSequenceCollateralizedPositiveExposure.toAU().integrate (
					vertexDateArray[0],
					integrandFinishDate
				) / exposureGeneratorTimeIntegrand,
				effectiveCollateralizedPositiveExposureArray[vertexCount - 1],
				effectiveExpectedPositiveExposure,
				effectiveExpectedPositiveExposure * standardizedExposureGeneratorScheme.eadMultiplier()
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
