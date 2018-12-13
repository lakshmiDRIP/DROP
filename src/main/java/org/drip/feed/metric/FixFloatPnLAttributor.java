
package org.drip.feed.metric;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
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
 * <i>FixFloatPnLAttributor</i> generates the Date Valuation and Position Change PnL Explain Attributions for
 * the Standard OTC Fix Float Swap.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AlgorithmSupportLibrary.md">Algorithm Support</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/feed/README.md">Feed</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/feed/metric/README.md">Metric</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class FixFloatPnLAttributor {

	/**
	 * Generate the Explain Components for the specified Fix Float Product
	 * 
	 * @param strCurrency The Fix-Float Swap Currency
	 * @param strMaturityTenor The Fix-Float Swap Maturity Tenor
	 * @param iHorizonGap The Valuation Horizon Gap
	 * @param strFeedTranformLocation The Closing Funding Curve Quotes Location
	 * @param astrFundingDepositTenor The Funding Curve Deposit Instrument Maturity Tenors
	 * @param aiFundingDepositColumn The Funding Curve Deposit Instrument Quote Columns
	 * @param astrFundingFixFloatTenor The Funding Curve Fix Float Swap Instrument Maturity Tenors
	 * @param aiFundingFixFloatColumn The Funding Curve Fix Float Swap Instrument Quote Columns
	 * @param astrRollDownHorizonTenor Array of the Roll Down Horizon Tenors
	 * 
	 * @return List of the Position Change Components
	 */

	public static final java.util.List<org.drip.historical.attribution.PositionChangeComponents>
		TenorHorizonExplainComponents (
			final java.lang.String strCurrency,
			final java.lang.String strMaturityTenor,
			final int iHorizonGap,
			final java.lang.String strFeedTranformLocation,
			final java.lang.String[] astrFundingDepositTenor,
			final int[] aiFundingDepositColumn,
			final java.lang.String[] astrFundingFixFloatTenor,
			final int[] aiFundingFixFloatColumn,
			final java.lang.String[] astrRollDownHorizonTenor)
	{
		if (null == astrFundingDepositTenor || null == aiFundingDepositColumn || null ==
			astrFundingFixFloatTenor || null == aiFundingFixFloatColumn)
			return null;

		int iNumFundingDeposit = astrFundingDepositTenor.length;
		int iNumFundingFixFloat = astrFundingFixFloatTenor.length;
		double[][] aadblFundingDepositClose = new double[iNumFundingDeposit][];
		double[][] aadblFundingFixFloatClose = new double[iNumFundingFixFloat][];

		if (0 == iNumFundingDeposit || iNumFundingDeposit != aiFundingDepositColumn.length || 0 ==
			iNumFundingFixFloat || iNumFundingFixFloat != aiFundingFixFloatColumn.length)
			return null;

		org.drip.feed.loader.CSVGrid csvGrid = org.drip.feed.loader.CSVParser.StringGrid
			(strFeedTranformLocation, true);

		if (null == csvGrid) return null;

		org.drip.analytics.date.JulianDate[] adtClose = csvGrid.dateArrayAtColumn (0);

		int iNumClose = adtClose.length;
		double[][] aadblFundingDepositQuote = new double[iNumClose][iNumFundingDeposit];
		double[][] aadblFundingFixFloatQuote = new double[iNumClose][iNumFundingFixFloat];
		org.drip.analytics.date.JulianDate[] adtSpot = new org.drip.analytics.date.JulianDate[iNumClose];

		for (int i = 0; i < iNumFundingDeposit; ++i)
			aadblFundingDepositClose[i] = csvGrid.doubleArrayAtColumn (aiFundingDepositColumn[i]);

		for (int i = 0; i < iNumFundingFixFloat; ++i)
			aadblFundingFixFloatClose[i] = csvGrid.doubleArrayAtColumn (aiFundingFixFloatColumn[i]);

		for (int i = 0; i < iNumClose; ++i) {
			adtSpot[i] = adtClose[i];

			for (int j = 0; j < iNumFundingDeposit; ++j)
				aadblFundingDepositQuote[i][j] = aadblFundingDepositClose[j][i];

			for (int j = 0; j < iNumFundingFixFloat; ++j)
				aadblFundingFixFloatQuote[i][j] = aadblFundingFixFloatClose[j][i];
		}

		return org.drip.service.product.FixFloatAPI.HorizonChangeAttribution (adtSpot, iHorizonGap,
			astrFundingDepositTenor, aadblFundingDepositQuote, astrFundingFixFloatTenor,
				aadblFundingFixFloatQuote, strCurrency, strMaturityTenor, astrRollDownHorizonTenor,
					org.drip.service.template.LatentMarketStateBuilder.SHAPE_PRESERVING);
	}

	/**
	 * Generate the Tenor Horizon Explain Components
	 * 
	 * @param strCurrency The Fix-Float Swap Currency
	 * @param astrMaturityTenor Array of Fix-Float Swap Maturity Tenors
	 * @param aiHorizonGap Array of the Valuation Horizon Gaps
	 * @param strFeedTranformLocation The Closing Funding Curve Quotes Location
	 * @param astrFundingDepositTenor The Funding Curve Deposit Instrument Maturity Tenors
	 * @param aiFundingDepositColumn The Funding Curve Deposit Instrument Quote Columns
	 * @param astrFundingFixFloatTenor The Funding Curve Fix Float Swap Instrument Maturity Tenors
	 * @param aiFundingFixFloatColumn The Funding Curve Fix Float Swap Instrument Quote Columns
	 * @param astrRollDownHorizonTenor Array of the Roll Down Horizon Tenors
	 * 
	 * @return List of the Position Change Components
	 */

	public static final boolean TenorHorizonExplainComponents (
		final java.lang.String strCurrency,
		final java.lang.String[] astrMaturityTenor,
		final int[] aiHorizonGap,
		final java.lang.String strFeedTranformLocation,
		final java.lang.String[] astrFundingDepositTenor,
		final int[] aiFundingDepositColumn,
		final java.lang.String[] astrFundingFixFloatTenor,
		final int[] aiFundingFixFloatColumn,
		final java.lang.String[] astrRollDownHorizonTenor)
	{
		boolean bFirstRun = true;

		for (java.lang.String strMaturityTenor : astrMaturityTenor) {
			for (int iHorizonGap : aiHorizonGap) {
				java.util.List<org.drip.historical.attribution.PositionChangeComponents> lsPCC =
					org.drip.feed.metric.FixFloatPnLAttributor.TenorHorizonExplainComponents (strCurrency,
						strMaturityTenor, iHorizonGap, strFeedTranformLocation, astrFundingDepositTenor,
							aiFundingDepositColumn, astrFundingFixFloatTenor, aiFundingFixFloatColumn,
								astrRollDownHorizonTenor);

				if (null != lsPCC) {
					java.lang.String strHorizonTenor = 1 == iHorizonGap ? "1D" : ((iHorizonGap / 22) + "M");

					for (org.drip.historical.attribution.PositionChangeComponents pcc : lsPCC) {
						if (null != pcc) {
							if (bFirstRun) {
								System.out.println (pcc.header() + "horizontenor,");

								bFirstRun = false;
							}

							System.out.println (pcc.content() + strHorizonTenor + ",");
						}
					}
				}
			}
		}

		return true;
	}
}
