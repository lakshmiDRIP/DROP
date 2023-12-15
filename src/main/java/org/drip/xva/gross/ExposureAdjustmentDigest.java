
package org.drip.xva.gross;

import org.drip.measure.statistics.UnivariateDiscreteThin;

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
 * <i>ExposureAdjustmentDigest</i> holds the "thin" Statistics of the Aggregations across Multiple Path
 * Projection Runs along the Granularity of a Counter Party Group (i.e., across multiple Funding and
 * Credit/Debt Netting groups). The References are:
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

public class ExposureAdjustmentDigest
{
	private UnivariateDiscreteThin _cvaThinStatistics = null;
	private UnivariateDiscreteThin _dvaThinStatistics = null;
	private UnivariateDiscreteThin _fbaThinStatistics = null;
	private UnivariateDiscreteThin _fcaThinStatistics = null;
	private UnivariateDiscreteThin _fdaThinStatistics = null;
	private UnivariateDiscreteThin _fvaThinStatistics = null;
	private UnivariateDiscreteThin _ucvaThinStatistics = null;
	private UnivariateDiscreteThin _sfvaThinStatistics = null;
	private UnivariateDiscreteThin _cvaclThinStatistics = null;
	private UnivariateDiscreteThin _ftdcvaThinStatistics = null;
	private UnivariateDiscreteThin _ucolvaThinStatistics = null;
	private UnivariateDiscreteThin _totalvaThinStatistics = null;
	private UnivariateDiscreteThin _ftdcolvaThinStatistics = null;
	private UnivariateDiscreteThin[] _fundingExposureThinStatisticsArray = null;
	private UnivariateDiscreteThin[] _collateralizedExposureThinStatistics = null;
	private UnivariateDiscreteThin[] _fundingExposurePVThinStatisticsArray = null;
	private UnivariateDiscreteThin[] _collateralizedExposureThinStatisticsPV = null;
	private UnivariateDiscreteThin[] _uncollateralizedExposureThinStatisticsArray = null;
	private UnivariateDiscreteThin[] _uncollateralizedExposurePVThinStatisticsArray = null;
	private UnivariateDiscreteThin[] _collateralizedPositiveExposureThinStatisticsArray = null;
	private UnivariateDiscreteThin[] _collateralizedNegativeExposureThinStatisticsArray = null;
	private UnivariateDiscreteThin[] _collateralizedPositiveExposurePVThinStatisticsArray = null;
	private UnivariateDiscreteThin[] _collateralizedNegativeExposurePVThinStatisticsArray = null;
	private UnivariateDiscreteThin[] _uncollateralizedPositiveExposureThinStatisticsArray = null;
	private UnivariateDiscreteThin[] _uncollateralizedNegativeExposureThinStatisticsArray = null;
	private UnivariateDiscreteThin[] _uncollateralizedPositiveExposurePVThinStatisticsArray = null;
	private UnivariateDiscreteThin[] _uncollateralizedNegativeExposurePVThinStatisticsArray = null;

	/**
	 * ExposureAdjustmentDigest Constructor
	 * 
	 * @param ucolvaArray The Array of Unilateral Collateral VA
	 * @param ftdcolvaArray The Array of Bilateral Collateral VA
	 * @param ucvaArray The Array of UCVA
	 * @param ftdcvaArray The Array of FTD CVA
	 * @param cvaArray The Array of CVA
	 * @param cvaclArray The Array of CVA Contra-Liabilities
	 * @param dvaArray The Array of DVA
	 * @param fvaArray The Array of FVA
	 * @param fdaArray The Array of FDA
	 * @param fcaArray The Array of FCA
	 * @param fbaArray The Array of FBA
	 * @param sfvaArray The Array of SFVA
	 * @param totalVAArray The Array of Total VA
	 * @param collateralizedExposureGrid Grid of Collateralized Exposure
	 * @param collateralizedExposurePVGrid Grid of Collateralized Exposure PV
	 * @param collateralizedPositiveExposurGride Grid of Collateralized Positive Exposure
	 * @param collateralizedPositiveExposurePVGrid Grid of Collateralized Positive Exposure PV
	 * @param collateralizedNegativeExposureGrid Grid of Collateralized Negative Exposure
	 * @param collateralizedNegativeExposurePVGrid Grid of Collateralized Negative Exposure PV
	 * @param uncollateralizedExposureGrid Grid of Uncollateralized Exposure
	 * @param uncollateralizedExposurePVGrid Grid of Uncollateralized Exposure PV
	 * @param uncollateralizedPositiveExposureGrid Grid of Uncollateralized Positive Exposure
	 * @param uncollateralizedPositiveExposurePVGrid Grid of Uncollateralized Positive Exposure PV
	 * @param uncollateralizedNegativeExposureGrid Grid of Uncollateralized Negative Exposure
	 * @param uncollateralizedNegativeExposurePVGrid Grid of Uncollateralized Negative Exposure PV
	 * @param fundingExposureGrid Grid of Funding Exposure
	 * @param fundingExposurePVGrid Grid of Funding Exposure PV
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public ExposureAdjustmentDigest (
		final double[] ucolvaArray,
		final double[] ftdcolvaArray,
		final double[] ucvaArray,
		final double[] ftdcvaArray,
		final double[] cvaArray,
		final double[] cvaclArray,
		final double[] dvaArray,
		final double[] fvaArray,
		final double[] fdaArray,
		final double[] fcaArray,
		final double[] fbaArray,
		final double[] sfvaArray,
		final double[] totalVAArray,
		final double[][] collateralizedExposureGrid,
		final double[][] collateralizedExposurePVGrid,
		final double[][] collateralizedPositiveExposureGrid,
		final double[][] collateralizedPositiveExposurePVGrid,
		final double[][] collateralizedNegativeExposureGrid,
		final double[][] collateralizedNegativeExposurePVGrid,
		final double[][] uncollateralizedExposureGrid,
		final double[][] uncollateralizedExposurePVGrid,
		final double[][] uncollateralizedPositiveExposureGrid,
		final double[][] uncollateralizedPositiveExposurePVGrid,
		final double[][] uncollateralizedNegativeExposureGrid,
		final double[][] uncollateralizedNegativeExposurePVGrid,
		final double[][] fundingExposureGrid,
		final double[][] fundingExposurePVGrid)
		throws Exception
	{
		if (null == collateralizedExposureGrid ||
			null == collateralizedExposurePVGrid ||
			null == collateralizedPositiveExposureGrid ||
			null == collateralizedPositiveExposurePVGrid ||
			null == collateralizedNegativeExposureGrid ||
			null == collateralizedNegativeExposurePVGrid ||
			null == uncollateralizedExposureGrid ||
			null == uncollateralizedExposurePVGrid ||
			null == uncollateralizedPositiveExposureGrid ||
			null == uncollateralizedPositiveExposurePVGrid ||
			null == uncollateralizedNegativeExposureGrid ||
			null == uncollateralizedNegativeExposurePVGrid ||
			null == fundingExposureGrid ||
			null == fundingExposurePVGrid) {
			throw new Exception ("ExposureAdjustmentDigest Constructor => Invalid Inputs");
		}

		_ucolvaThinStatistics = new UnivariateDiscreteThin (ucolvaArray);

		_ftdcolvaThinStatistics = new UnivariateDiscreteThin (ftdcolvaArray);

		_ucvaThinStatistics = new UnivariateDiscreteThin (ucvaArray);

		_ftdcvaThinStatistics = new UnivariateDiscreteThin (ftdcvaArray);

		_cvaThinStatistics = new UnivariateDiscreteThin (cvaArray);

		_cvaclThinStatistics = new UnivariateDiscreteThin (cvaclArray);

		_dvaThinStatistics = new UnivariateDiscreteThin (dvaArray);

		_fvaThinStatistics = new UnivariateDiscreteThin (fvaArray);

		_fdaThinStatistics = new UnivariateDiscreteThin (fdaArray);

		_fcaThinStatistics = new UnivariateDiscreteThin (fcaArray);

		_fbaThinStatistics = new UnivariateDiscreteThin (fbaArray);

		_sfvaThinStatistics = new UnivariateDiscreteThin (sfvaArray);

		_totalvaThinStatistics = new UnivariateDiscreteThin (totalVAArray);

		int vertexCount = collateralizedExposureGrid.length;
		_fundingExposureThinStatisticsArray = new UnivariateDiscreteThin[vertexCount];
		_collateralizedExposureThinStatistics = new UnivariateDiscreteThin[vertexCount];
		_fundingExposurePVThinStatisticsArray = new UnivariateDiscreteThin[vertexCount];
		_collateralizedExposureThinStatisticsPV = new UnivariateDiscreteThin[vertexCount];
		_uncollateralizedExposureThinStatisticsArray = new UnivariateDiscreteThin[vertexCount];
		_uncollateralizedExposurePVThinStatisticsArray = new UnivariateDiscreteThin[vertexCount];
		_collateralizedNegativeExposureThinStatisticsArray = new UnivariateDiscreteThin[vertexCount];
		_collateralizedPositiveExposureThinStatisticsArray = new UnivariateDiscreteThin[vertexCount];
		_collateralizedNegativeExposurePVThinStatisticsArray = new UnivariateDiscreteThin[vertexCount];
		_collateralizedPositiveExposurePVThinStatisticsArray = new UnivariateDiscreteThin[vertexCount];
		_uncollateralizedNegativeExposureThinStatisticsArray = new UnivariateDiscreteThin[vertexCount];
		_uncollateralizedPositiveExposureThinStatisticsArray = new UnivariateDiscreteThin[vertexCount];
		_uncollateralizedNegativeExposurePVThinStatisticsArray = new UnivariateDiscreteThin[vertexCount];
		_uncollateralizedPositiveExposurePVThinStatisticsArray = new UnivariateDiscreteThin[vertexCount];

		if (0 == vertexCount ||
			vertexCount != collateralizedExposurePVGrid.length ||
			vertexCount != collateralizedPositiveExposureGrid.length ||
			vertexCount != collateralizedPositiveExposurePVGrid.length ||
			vertexCount != collateralizedNegativeExposureGrid.length ||
			vertexCount != collateralizedNegativeExposurePVGrid.length ||
			vertexCount != uncollateralizedExposureGrid.length ||
			vertexCount != uncollateralizedExposurePVGrid.length ||
			vertexCount != uncollateralizedPositiveExposureGrid.length ||
			vertexCount != collateralizedPositiveExposurePVGrid.length ||
			vertexCount != collateralizedNegativeExposurePVGrid.length ||
			vertexCount != fundingExposureGrid.length ||
			vertexCount != fundingExposurePVGrid.length) {
			throw new Exception ("ExposureAdjustmentDigest Constructor => Invalid Inputs");
		}

		for (int i = 0 ; i < vertexCount; ++i) {
			_collateralizedExposureThinStatistics[i] = new
				UnivariateDiscreteThin (collateralizedExposureGrid[i]);

			_collateralizedExposureThinStatisticsPV[i] = new
				UnivariateDiscreteThin (collateralizedExposurePVGrid[i]);

			_collateralizedPositiveExposureThinStatisticsArray[i] = new
				UnivariateDiscreteThin (collateralizedPositiveExposureGrid[i]);

			_collateralizedPositiveExposurePVThinStatisticsArray[i] = new
				UnivariateDiscreteThin (collateralizedPositiveExposurePVGrid[i]);

			_collateralizedNegativeExposureThinStatisticsArray[i] = new
				UnivariateDiscreteThin (collateralizedNegativeExposureGrid[i]);

			_collateralizedNegativeExposurePVThinStatisticsArray[i] = new
				UnivariateDiscreteThin (collateralizedNegativeExposurePVGrid[i]);

			_uncollateralizedExposureThinStatisticsArray[i] = new
				UnivariateDiscreteThin (uncollateralizedExposureGrid[i]);

			_uncollateralizedExposurePVThinStatisticsArray[i] = new
				UnivariateDiscreteThin (uncollateralizedExposurePVGrid[i]);

			_uncollateralizedPositiveExposureThinStatisticsArray[i] = new
				UnivariateDiscreteThin (uncollateralizedPositiveExposureGrid[i]);

			_uncollateralizedPositiveExposurePVThinStatisticsArray[i] = new
				UnivariateDiscreteThin (uncollateralizedPositiveExposurePVGrid[i]);

			_uncollateralizedNegativeExposureThinStatisticsArray[i] = new
				UnivariateDiscreteThin (uncollateralizedNegativeExposureGrid[i]);

			_uncollateralizedNegativeExposurePVThinStatisticsArray[i] = new
				UnivariateDiscreteThin (uncollateralizedNegativeExposurePVGrid[i]);
		}
	}

	/**
	 * Retrieve the Univariate Thin Statistics for the Collateralized Exposure
	 * 
	 * @return Univariate Thin Statistics for the Collateralized Exposure
	 */

	public UnivariateDiscreteThin[] collateralizedExposure()
	{
		return _collateralizedExposureThinStatistics;
	}

	/**
	 * Retrieve the Univariate Thin Statistics for the Collateralized Exposure PV
	 * 
	 * @return Univariate Thin Statistics for the Collateralized Exposure PV
	 */

	public UnivariateDiscreteThin[] collateralizedExposurePV()
	{
		return _collateralizedExposureThinStatisticsPV;
	}

	/**
	 * Retrieve the Univariate Thin Statistics for the Collateralized Positive Exposure
	 * 
	 * @return Univariate Thin Statistics for the Collateralized Positive Exposure
	 */

	public UnivariateDiscreteThin[] collateralizedPositiveExposure()
	{
		return _collateralizedPositiveExposureThinStatisticsArray;
	}

	/**
	 * Retrieve the Univariate Thin Statistics for the Collateralized Positive Exposure PV
	 * 
	 * @return Univariate Thin Statistics for the Collateralized Positive Exposure PV
	 */

	public UnivariateDiscreteThin[] collateralizedPositiveExposurePV()
	{
		return _collateralizedPositiveExposurePVThinStatisticsArray;
	}

	/**
	 * Retrieve the Univariate Thin Statistics for the Collateralized Negative Exposure
	 * 
	 * @return Univariate Thin Statistics for the Collateralized Negative Exposure
	 */

	public UnivariateDiscreteThin[] collateralizedNegativeExposure()
	{
		return _collateralizedNegativeExposureThinStatisticsArray;
	}

	/**
	 * Retrieve the Univariate Thin Statistics for the Collateralized Negative Exposure PV
	 * 
	 * @return Univariate Thin Statistics for the Collateralized Negative Exposure PV
	 */

	public UnivariateDiscreteThin[] collateralizedNegativeExposurePV()
	{
		return _collateralizedNegativeExposurePVThinStatisticsArray;
	}

	/**
	 * Retrieve the Univariate Thin Statistics for the Uncollateralized Exposure
	 * 
	 * @return Univariate Thin Statistics for the Uncollateralized Exposure
	 */

	public UnivariateDiscreteThin[] uncollateralizedExposure()
	{
		return _uncollateralizedExposureThinStatisticsArray;
	}

	/**
	 * Retrieve the Univariate Thin Statistics for the Uncollateralized Exposure PV
	 * 
	 * @return Univariate Thin Statistics for the Uncollateralized Exposure PV
	 */

	public UnivariateDiscreteThin[] uncollateralizedExposurePV()
	{
		return _uncollateralizedExposurePVThinStatisticsArray;
	}

	/**
	 * Retrieve the Univariate Thin Statistics for the Uncollateralized Positive Exposure
	 * 
	 * @return Univariate Thin Statistics for the Uncollateralized Positive Exposure
	 */

	public UnivariateDiscreteThin[] uncollateralizedPositiveExposure()
	{
		return _uncollateralizedPositiveExposureThinStatisticsArray;
	}

	/**
	 * Retrieve the Univariate Thin Statistics for the Uncollateralized Positive Exposure PV
	 * 
	 * @return Univariate Thin Statistics for the Uncollateralized Positive Exposure PV
	 */

	public UnivariateDiscreteThin[] uncollateralizedPositiveExposurePV()
	{
		return _uncollateralizedPositiveExposurePVThinStatisticsArray;
	}

	/**
	 * Retrieve the Univariate Thin Statistics for the Uncollateralized Negative Exposure
	 * 
	 * @return Univariate Thin Statistics for the Uncollateralized Negative Exposure
	 */

	public UnivariateDiscreteThin[] uncollateralizedNegativeExposure()
	{
		return _uncollateralizedNegativeExposureThinStatisticsArray;
	}

	/**
	 * Retrieve the Univariate Thin Statistics for the Uncollateralized Negative Exposure PV
	 * 
	 * @return Univariate Thin Statistics for the Uncollateralized Negative Exposure PV
	 */

	public UnivariateDiscreteThin[] uncollateralizedNegativeExposurePV()
	{
		return _uncollateralizedNegativeExposurePVThinStatisticsArray;
	}

	/**
	 * Retrieve the Univariate Thin Statistics for the Funding Exposure
	 * 
	 * @return Univariate Thin Statistics for the Funding Exposure
	 */

	public UnivariateDiscreteThin[] fundingExposure()
	{
		return _fundingExposureThinStatisticsArray;
	}

	/**
	 * Retrieve the Univariate Thin Statistics for the Funding Exposure PV
	 * 
	 * @return Univariate Thin Statistics for the Funding Exposure PV
	 */

	public UnivariateDiscreteThin[] fundingExposurePV()
	{
		return _fundingExposurePVThinStatisticsArray;
	}

	/**
	 * Retrieve the Univariate Thin Statistics for Unilateral Collateral VA
	 * 
	 * @return Univariate Thin Statistics for Unilateral Collateral VA
	 */

	public UnivariateDiscreteThin ucolva()
	{
		return _ucolvaThinStatistics;
	}

	/**
	 * Retrieve the Univariate Thin Statistics for Bilateral Collateral VA
	 * 
	 * @return Univariate Thin Statistics for Bilateral Collateral VA
	 */

	public UnivariateDiscreteThin ftdcolva()
	{
		return _ftdcolvaThinStatistics;
	}

	/**
	 * Retrieve the Univariate Thin Statistics for UCVA
	 * 
	 * @return Univariate Thin Statistics for UCVA
	 */

	public UnivariateDiscreteThin ucva()
	{
		return _ucvaThinStatistics;
	}

	/**
	 * Retrieve the Univariate Thin Statistics for FTD CVA
	 * 
	 * @return Univariate Thin Statistics for FTD CVA
	 */

	public UnivariateDiscreteThin ftdcva()
	{
		return _ftdcvaThinStatistics;
	}

	/**
	 * Retrieve the Univariate Thin Statistics for CVA
	 * 
	 * @return Univariate Thin Statistics for CVA
	 */

	public UnivariateDiscreteThin cva()
	{
		return _cvaThinStatistics;
	}

	/**
	 * Retrieve the Univariate Thin Statistics for CVA Contra-Liabilities
	 * 
	 * @return Univariate Thin Statistics for CVA Contra-Liabilities
	 */

	public UnivariateDiscreteThin cvacl()
	{
		return _cvaclThinStatistics;
	}

	/**
	 * Retrieve the Univariate Thin Statistics for DVA
	 * 
	 * @return Univariate Thin Statistics for DVA
	 */

	public UnivariateDiscreteThin dva()
	{
		return _dvaThinStatistics;
	}

	/**
	 * Retrieve the Univariate Thin Statistics for FVA
	 * 
	 * @return Univariate Thin Statistics for FVA
	 */

	public UnivariateDiscreteThin fva()
	{
		return _fvaThinStatistics;
	}

	/**
	 * Retrieve the Univariate Thin Statistics for FDA
	 * 
	 * @return Univariate Thin Statistics for FDA
	 */

	public UnivariateDiscreteThin fda()
	{
		return _fdaThinStatistics;
	}

	/**
	 * Retrieve the Univariate Thin Statistics for DVA2
	 * 
	 * @return Univariate Thin Statistics for DVA2
	 */

	public UnivariateDiscreteThin dva2()
	{
		return _fdaThinStatistics;
	}

	/**
	 * Retrieve the Univariate Thin Statistics for FCA
	 * 
	 * @return Univariate Thin Statistics for FCA
	 */

	public UnivariateDiscreteThin fca()
	{
		return _fcaThinStatistics;
	}

	/**
	 * Retrieve the Univariate Thin Statistics for FBA
	 * 
	 * @return Univariate Thin Statistics for FBA
	 */

	public UnivariateDiscreteThin fba()
	{
		return _fbaThinStatistics;
	}

	/**
	 * Retrieve the Univariate Thin Statistics for SFVA
	 * 
	 * @return Univariate Thin Statistics for SFVA
	 */

	public UnivariateDiscreteThin sfva()
	{
		return _sfvaThinStatistics;
	}

	/**
	 * Retrieve the Univariate Thin Statistics for Total VA
	 * 
	 * @return Univariate Thin Statistics for Total VA
	 */

	public UnivariateDiscreteThin totalVA()
	{
		return _totalvaThinStatistics;
	}
}
