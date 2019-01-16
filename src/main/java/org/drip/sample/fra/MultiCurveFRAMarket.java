
package org.drip.sample.fra;

import java.util.*;

import org.drip.analytics.date.*;
import org.drip.function.r1tor1.FlatUnivariate;
import org.drip.param.creator.*;
import org.drip.param.market.CurveSurfaceQuoteContainer;
import org.drip.param.valuation.ValuationParams;
import org.drip.product.creator.SingleStreamComponentBuilder;
import org.drip.product.fra.FRAMarketComponent;
import org.drip.sample.forward.*;
import org.drip.service.env.EnvManager;
import org.drip.state.creator.ScenarioDeterministicVolatilityBuilder;
import org.drip.state.discount.*;
import org.drip.state.forward.ForwardCurve;
import org.drip.state.identifier.*;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
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
 * <i>MultiCurveFRAMarket</i> contains the demonstration of the Market Multi-Curve FRA Product sample.
 *  
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">Sample</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/fra/README.md">FRA Analytics</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class MultiCurveFRAMarket {
	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		/*
		 * Initialize the Credit Analytics Library
		 */

		EnvManager.InitEnv ("");

		String strTenor = "6M";
		String strCurrency = "JPY";
		double dblEURIBOR6MVol = 0.37;
		double dblEONIAVol = 0.37;
		double dblEONIAEURIBOR6MCorrelation = 0.8;

		JulianDate dtToday = DateUtil.Today();

		MergedDiscountForwardCurve dcEONIA = OvernightIndexCurve.MakeDC (
			dtToday,
			strCurrency
		);

		ForwardCurve fcEURIBOR6M = IBOR6MQuarticPolyVanilla.Make6MForward (
			dtToday,
			strCurrency,
			strTenor
		);

		ForwardLabel fri = ForwardLabel.Create (
			strCurrency,
			strTenor
		);

		FundingLabel fundingLabel = FundingLabel.Standard (strCurrency);

		JulianDate dtForwardStart = dtToday.addTenor (strTenor);

		FRAMarketComponent fra = SingleStreamComponentBuilder.FRAMarket (
			dtForwardStart,
			fri,
			0.006
		);

		CurveSurfaceQuoteContainer mktParams = MarketParamsBuilder.Create (
			dcEONIA,
			fcEURIBOR6M,
			null,
			null,
			null,
			null,
			null,
			null
		);

		ValuationParams valParams = new ValuationParams (
			dtToday,
			dtToday,
			strCurrency
		);

		mktParams.setForwardVolatility (
			ScenarioDeterministicVolatilityBuilder.FlatForward (
				dtToday.julian(),
				VolatilityLabel.Standard (fri),
				fri.currency(),
				dblEURIBOR6MVol
			)
		);

		mktParams.setFundingVolatility (
			ScenarioDeterministicVolatilityBuilder.FlatForward (
				dtToday.julian(),
				VolatilityLabel.Standard (fundingLabel),
				fri.currency(),
				dblEONIAVol
			)
		);

		mktParams.setForwardFundingCorrelation (
			fri,
			fundingLabel,
			new FlatUnivariate (dblEONIAEURIBOR6MCorrelation)
		);

		Map<String, Double> mapFRAOutput = fra.value (
			valParams,
			null,
			mktParams,
			null
		);

		for (Map.Entry<String, Double> me : mapFRAOutput.entrySet())
			System.out.println ("\t" + me.getKey() + " => " + me.getValue());

		EnvManager.TerminateEnv();
	}
}
