
package org.drip.feed.transformer;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
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
 * <i>CreditCDSIndexMarksReconstitutor</i> transforms the Credit CDS Index Closes - Feed Inputs into Formats
 * suitable for Valuation Metrics and Sensitivities Generation.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationSupportLibrary.md">Computation Support</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/feed/README.md">Load, Transform, and compute Target Metrics across Feeds</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/feed/transformer/README.md">Market Data Reconstitutive Feed Transformer</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class CreditCDSIndexMarksReconstitutor {

	private static final double[] FundingFixingMarksIndex (
		final org.drip.feed.loader.CSVGrid csvGridFundingFixingMarks,
		final org.drip.analytics.date.JulianDate[] adtFundingFixingSpot,
		final org.drip.analytics.date.JulianDate dtCreditIndexSpot)
	{
		int iNumFundingFixingSpot = adtFundingFixingSpot.length;
		int iNumFundingFixingMaturityTenor =
			org.drip.feed.transformer.FundingFixFloatMarksReconstitutor.s_astrFixFloatTenor.length;

		for (int i = 0; i < iNumFundingFixingSpot; ++i) {
			if (dtCreditIndexSpot.julian() == adtFundingFixingSpot[i].julian()) {
				double[] adblFundingFixingFixFloatQuote = new double[iNumFundingFixingMaturityTenor];

				for (int j = 0; j < iNumFundingFixingMaturityTenor; ++j)
					adblFundingFixingFixFloatQuote[j] = csvGridFundingFixingMarks.doubleArrayAtColumn
						(j + 1)[i];

				return adblFundingFixingFixFloatQuote;
			}
		}

		return null;
	}

	/**
	 * Regularize the Credit Index Feed Marks
	 * 
	 * @param strFundingFixingMarksLocation The Funding Fixing Marks Location
	 * @param strCreditIndexMarksLocation The Credit Index Marks Location
	 * @param strIndexFullName Full Name of the Index
	 * @param iSpotDateIndex Spot Date Column Index
	 * @param iQuotedSpreadIndex Quoted Spread Column Index
	 * 
	 * @return TRUE - The Regularization is Successful
	 */

	public static final boolean RegularizeCloses (
		final java.lang.String strFundingFixingMarksLocation,
		final java.lang.String strCreditIndexMarksLocation,
		final java.lang.String strIndexFullName,
		final int iSpotDateIndex,
		final int iQuotedSpreadIndex)
	{
		org.drip.feed.loader.CSVGrid csvGridFundingFixingMarks = org.drip.feed.loader.CSVParser.StringGrid
			(strFundingFixingMarksLocation, true);

		if (null == csvGridFundingFixingMarks) return false;

		org.drip.analytics.date.JulianDate[] adtFundingFixingMarksSpot =
			csvGridFundingFixingMarks.dateArrayAtColumn (0);

		if (null == adtFundingFixingMarksSpot || 0 == adtFundingFixingMarksSpot.length) return false;

		org.drip.feed.loader.CSVGrid csvGridCreditIndexMarks = org.drip.feed.loader.CSVParser.StringGrid
			(strCreditIndexMarksLocation, true);

		if (null == csvGridCreditIndexMarks) return false;

		org.drip.analytics.date.JulianDate[] adtCreditIndexMarksSpot =
			csvGridCreditIndexMarks.dateArrayAtColumn (iSpotDateIndex);

		if (null == adtCreditIndexMarksSpot) return false;

		int iNumClose = adtCreditIndexMarksSpot.length;
		int iNumFundingFixingMaturityTenor =
			org.drip.feed.transformer.FundingFixFloatMarksReconstitutor.s_astrFixFloatTenor.length;

		if (0 == iNumClose) return false;

		double[] adblQuotedSpread = csvGridCreditIndexMarks.doubleArrayAtColumn (iQuotedSpreadIndex);

		if (null == adblQuotedSpread || iNumClose != adblQuotedSpread.length) return false;

		java.lang.String strDump = "CloseDate";

		for (int j = 0; j < iNumFundingFixingMaturityTenor; ++j)
			strDump += "," +
				org.drip.feed.transformer.FundingFixFloatMarksReconstitutor.s_astrFixFloatTenor[j];

		System.out.println (strDump + ",CreditIndexName,QuotedSpread");

		for (int i = 0; i < iNumClose; ++i) {
			double[] adblFundingFixingFixFloatQuote = FundingFixingMarksIndex (csvGridFundingFixingMarks,
				adtFundingFixingMarksSpot, adtCreditIndexMarksSpot[i]);

			if (null == adblFundingFixingFixFloatQuote) continue;

			strDump = "";

			for (int j = 0; j < iNumFundingFixingMaturityTenor; ++j)
				strDump += "," + adblFundingFixingFixFloatQuote[j];

			System.out.println (adtCreditIndexMarksSpot[i] + strDump + "," + strIndexFullName + "," + (0.0001
				* adblQuotedSpread[i]));
		}

		return true;
	}
}
