
package org.drip.service.product;

import java.util.ArrayList;
import java.util.List;

import org.drip.analytics.date.JulianDate;
import org.drip.analytics.support.CaseInsensitiveHashMap;
import org.drip.historical.attribution.PositionChangeComponents;
import org.drip.historical.engine.HorizonChangeExplainExecutor;
import org.drip.historical.engine.TreasuryBondExplainProcessor;
import org.drip.param.market.CurveSurfaceQuoteContainer;
import org.drip.service.template.LatentMarketStateBuilder;
import org.drip.service.template.TreasuryBuilder;
import org.drip.state.govvie.GovvieCurve;

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
 * Copyright (C) 2016 Lakshmi Krishnamurthy
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
 * <i>TreasuryAPI</i> demonstrates the Details behind the Pricing and the Scenario Runs behind a Treasury
 * 	Bond. It provides the following Functionality:
 *
 *  <ul>
 * 		<li>Compute the Horizon Change Attribution Details for the Specified Treasury Bond</li>
 * 		<li>Generate the Govvie Curve Horizon Metrics #1</li>
 * 		<li>Generate the Govvie Curve Horizon Metrics #2</li>
 *  </ul>
 *
 *	<br>
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationSupportLibrary.md">Computation Support</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/README.md">Environment, Product/Definition Containers, and Scenario/State Manipulation APIs</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/product/README.md">Product Horizon PnL Attribution Decomposition</a></td></tr>
 *  </table>
 *	<br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class TreasuryAPI
{

	/**
	 * Compute the Horizon Change Attribution Details for the Specified Treasury Bond
	 * 
	 * @param day1GovvieCurve First Day Govvie Curve
	 * @param day2GovvieCurve Second Date Govvie Curve
	 * @param rollDownGovvieCurveMap Map of the Roll Down Govvie Curves
	 * @param maturityTenor Treasury Bond Maturity Tenor
	 * @param treasuryCode Treasury Bond Code
	 * 
	 * @return The Horizon Change Attribution Instance
	 */

	public static final PositionChangeComponents HorizonChangeAttribution (
		final GovvieCurve day1GovvieCurve,
		final GovvieCurve day2GovvieCurve,
		final CaseInsensitiveHashMap<GovvieCurve> rollDownGovvieCurveMap,
		final String maturityTenor,
		final String treasuryCode)
	{
		if (null == day1GovvieCurve || null == rollDownGovvieCurveMap || 0 == rollDownGovvieCurveMap.size())
		{
			return null;
		}

		double firstGovvieCurveYield = Double.NaN;

		try {
			firstGovvieCurveYield = day1GovvieCurve.yld (maturityTenor);
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}

		JulianDate day1GovvieCurveEpochDate = day1GovvieCurve.epoch();

		CurveSurfaceQuoteContainer firstCurveSurfaceQuoteContainer = new CurveSurfaceQuoteContainer();

		if (!firstCurveSurfaceQuoteContainer.setGovvieState (day1GovvieCurve)) {
			return null;
		}

		CurveSurfaceQuoteContainer secondCurveSurfaceQuoteContainer = new CurveSurfaceQuoteContainer();

		if (!secondCurveSurfaceQuoteContainer.setGovvieState (day2GovvieCurve)) {
			return null;
		}

		CaseInsensitiveHashMap<CurveSurfaceQuoteContainer> rollDownCurveSurfaceQuoteContainerMap =
			new CaseInsensitiveHashMap<CurveSurfaceQuoteContainer>();

		for (String rollDownTenor : rollDownGovvieCurveMap.keySet()) {
			CurveSurfaceQuoteContainer rollDownCurveSurfaceQuoteContainer = new CurveSurfaceQuoteContainer();

			GovvieCurve rollDownGovvieCurve = rollDownGovvieCurveMap.get (rollDownTenor);

			if (null == rollDownGovvieCurve ||
				!rollDownCurveSurfaceQuoteContainer.setGovvieState (rollDownGovvieCurve))
			{
				return null;
			}

			rollDownCurveSurfaceQuoteContainerMap.put (rollDownTenor, rollDownCurveSurfaceQuoteContainer);
		}

		try {
			return HorizonChangeExplainExecutor.GenerateAttribution (
				new
				TreasuryBondExplainProcessor (
					TreasuryBuilder.FromCode (
						treasuryCode,
						day1GovvieCurveEpochDate,
						day1GovvieCurveEpochDate.addTenor (maturityTenor),
						firstGovvieCurveYield
					),
					"Yield",
					firstGovvieCurveYield,
					day1GovvieCurveEpochDate,
					day2GovvieCurve.epoch(),
					firstCurveSurfaceQuoteContainer,
					secondCurveSurfaceQuoteContainer,
					rollDownCurveSurfaceQuoteContainerMap
				)
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the Govvie Curve Horizon Metrics #1
	 * 
	 * @param day1GovvieCurveEpochDate The First Date
	 * @param secondCurveDate The Second Date
	 * @param govvieTreasuryInstrumentTenorArray Array of Govvie Curve Treasury Instrument Maturity Tenors
	 * @param firstGovvieTreasuryInstrumentArray Array of First Date Govvie Curve Treasury Instrument Quotes
	 * @param secondGovvieTreasuryInstrumentArray Array of Second Date Govvie Curve Treasury Instrument Quotes
	 * @param maturityTenor Treasury Bond Maturity Tenor
	 * @param treasuryCode Treasury Bond Code
	 * @param rollDownHorizonTenorArray Array of the Roll Down Horizon Tenors
	 * @param latentStateType Latent State Type
	 * 
	 * @return The Govvie Curve Horizon Metrics
	 */

	public static final PositionChangeComponents HorizonChangeAttribution (
		final JulianDate day1GovvieCurveEpochDate,
		final JulianDate secondCurveDate,
		final String[] govvieTreasuryInstrumentTenorArray,
		final double[] firstGovvieTreasuryInstrumentArray,
		final double[] secondGovvieTreasuryInstrumentArray,
		final String maturityTenor,
		final String treasuryCode,
		final String[] rollDownHorizonTenorArray,
		final int latentStateType)
	{
		if (null == day1GovvieCurveEpochDate || null == secondCurveDate ||
			day1GovvieCurveEpochDate.julian() >= secondCurveDate.julian())
		{
			return null;
		}

		int govvieTreasuryInstrumentCount = null == govvieTreasuryInstrumentTenorArray ? 0 :
			govvieTreasuryInstrumentTenorArray.length;
		int firstGovvieTreasuryInstrumentCount = null == firstGovvieTreasuryInstrumentArray ? 0 :
			firstGovvieTreasuryInstrumentArray.length;
		int secondGovvieTreasuryInstrumentCount = null == secondGovvieTreasuryInstrumentArray ? 0 :
			secondGovvieTreasuryInstrumentArray.length;
		int rollDownHorizonCount = null == rollDownHorizonTenorArray ? 0 : rollDownHorizonTenorArray .length;
		JulianDate[] day1GovvieCurveEpochDateEffectiveArray = new JulianDate[govvieTreasuryInstrumentCount];
		JulianDate[] day1GovvieCurveEpochDateMaturityArray = new JulianDate[govvieTreasuryInstrumentCount];
		JulianDate[] secondCurveDateEffectiveArray = new JulianDate[govvieTreasuryInstrumentCount];
		JulianDate[] secondCurveDateMaturityArray = new JulianDate[govvieTreasuryInstrumentCount];
		JulianDate[] rollDownEffectiveDateArray = new JulianDate[govvieTreasuryInstrumentCount];
		JulianDate[] rollDownMaturityDateArray = new JulianDate[govvieTreasuryInstrumentCount];

		CaseInsensitiveHashMap<GovvieCurve> rollDownGovvieCurveMap = 0 == rollDownHorizonCount ?
			null : new CaseInsensitiveHashMap<GovvieCurve>();

		if (0 == govvieTreasuryInstrumentCount ||
			govvieTreasuryInstrumentCount != firstGovvieTreasuryInstrumentCount ||
			govvieTreasuryInstrumentCount != secondGovvieTreasuryInstrumentCount)
		{
			return null;
		}

		for (int govvieTreasuryInstrument = 0;
			govvieTreasuryInstrument < govvieTreasuryInstrumentCount;
			++govvieTreasuryInstrument)
		{
			day1GovvieCurveEpochDateMaturityArray[govvieTreasuryInstrument] = (
				day1GovvieCurveEpochDateEffectiveArray[govvieTreasuryInstrument] = day1GovvieCurveEpochDate
			).addTenor (
				govvieTreasuryInstrumentTenorArray[govvieTreasuryInstrument]
			);

			secondCurveDateMaturityArray[govvieTreasuryInstrument] = (
				secondCurveDateEffectiveArray[govvieTreasuryInstrument] = secondCurveDate
			).addTenor (
				govvieTreasuryInstrumentTenorArray[govvieTreasuryInstrument]
			);
		}

		GovvieCurve day1GovvieCurve = LatentMarketStateBuilder.GovvieCurve (
			treasuryCode,
			day1GovvieCurveEpochDate,
			day1GovvieCurveEpochDateEffectiveArray,
			day1GovvieCurveEpochDateMaturityArray,
			firstGovvieTreasuryInstrumentArray,
			firstGovvieTreasuryInstrumentArray,
			"Yield",
			latentStateType
		);

		GovvieCurve day2GovvieCurve = LatentMarketStateBuilder.GovvieCurve (
			treasuryCode,
			secondCurveDate,
			secondCurveDateEffectiveArray,
			secondCurveDateMaturityArray,
			secondGovvieTreasuryInstrumentArray,
			secondGovvieTreasuryInstrumentArray,
			"Yield",
			latentStateType
		);

		GovvieCurve rollDownGovvieCurve = LatentMarketStateBuilder.GovvieCurve (
			treasuryCode,
			secondCurveDate,
			secondCurveDateEffectiveArray,
			secondCurveDateMaturityArray,
			firstGovvieTreasuryInstrumentArray,
			firstGovvieTreasuryInstrumentArray,
			"Yield",
			latentStateType
		);

		if (null == rollDownGovvieCurve) {
			return null;
		}

		rollDownGovvieCurveMap.put ("Native", rollDownGovvieCurve);

		for (int rollDownHorizon = 0; rollDownHorizon < rollDownHorizonCount; ++rollDownHorizon) {
			JulianDate rollDownDate =
				day1GovvieCurveEpochDate.addTenor (rollDownHorizonTenorArray[rollDownHorizon]);

			for (int govvieTreasuryInstrument = 0;
				govvieTreasuryInstrument < govvieTreasuryInstrumentCount;
				++govvieTreasuryInstrument)
			{
				rollDownMaturityDateArray[govvieTreasuryInstrument] = (
					rollDownEffectiveDateArray[govvieTreasuryInstrument] = rollDownDate
				).addTenor (
					govvieTreasuryInstrumentTenorArray[govvieTreasuryInstrument]
				);
			}

			GovvieCurve horizonRollDownGovvieCurve = LatentMarketStateBuilder.GovvieCurve (
				treasuryCode,
				rollDownDate,
				rollDownEffectiveDateArray,
				rollDownMaturityDateArray,
				firstGovvieTreasuryInstrumentArray,
				firstGovvieTreasuryInstrumentArray,
				"Yield",
				latentStateType
			);

			if (null == horizonRollDownGovvieCurve) {
				return null;
			}

			rollDownGovvieCurveMap.put (
				rollDownHorizonTenorArray[rollDownHorizon],
				horizonRollDownGovvieCurve
			);
		}

		return HorizonChangeAttribution (
			day1GovvieCurve,
			day2GovvieCurve,
			rollDownGovvieCurveMap,
			maturityTenor,
			treasuryCode
		);
	}

	/**
	 * Generate the Govvie Curve Horizon Metrics #2
	 * 
	 * @param spotDateArray Array of the Spot Dates
	 * @param horizonGap The Horizon Gap
	 * @param govvieTreasuryInstrumentTenorArray Array of Govvie Curve Treasury Instrument Maturity Tenors
	 * @param govvieTreasuryInstrumentQuoteGrid Array of Govvie Curve Treasury Instrument Quotes
	 * @param maturityTenor Treasury Bond Maturity Tenor
	 * @param treasuryCode Treasury Bond Code
	 * @param rollDownHorizonTenorArray Array of the Roll Down Horizon Tenors
	 * @param latentStateType Latent State Type
	 * 
	 * @return The Govvie Curve Horizon Metrics
	 */

	public static final List<PositionChangeComponents> HorizonChangeAttribution (
		final JulianDate[] spotDateArray,
		final int horizonGap,
		final String[] govvieTreasuryInstrumentTenorArray,
		final double[][] govvieTreasuryInstrumentQuoteGrid,
		final String maturityTenor,
		final String treasuryCode,
		final String[] rollDownHorizonTenorArray,
		final int latentStateType)
	{
		if (null == spotDateArray || 0 >= horizonGap || null == govvieTreasuryInstrumentQuoteGrid) {
			return null;
		}

		int closeDaysCount = spotDateArray.length;
		int rollDownTenorCount = null == rollDownHorizonTenorArray ? 0 : rollDownHorizonTenorArray.length;

		if (0 == closeDaysCount ||
			closeDaysCount != govvieTreasuryInstrumentQuoteGrid.length ||
			0 == rollDownTenorCount)
		{
			return null;
		}

		List<PositionChangeComponents> positionChangeComponentsList =
			new ArrayList<PositionChangeComponents>();

		for (int closeDaysIndex = horizonGap; closeDaysIndex < closeDaysCount; ++closeDaysIndex) {
			PositionChangeComponents positionChangeComponents = HorizonChangeAttribution (
				spotDateArray[closeDaysIndex - horizonGap],
				spotDateArray[closeDaysIndex],
				govvieTreasuryInstrumentTenorArray,
				govvieTreasuryInstrumentQuoteGrid[closeDaysIndex - horizonGap],
				govvieTreasuryInstrumentQuoteGrid[closeDaysIndex],
				maturityTenor,
				treasuryCode,
				rollDownHorizonTenorArray,
				latentStateType
			);

			if (null != positionChangeComponents) {
				positionChangeComponentsList.add (positionChangeComponents);
			}
		}

		return positionChangeComponentsList;
	}
}
