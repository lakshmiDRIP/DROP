
package org.drip.sample.credit;

/*
 * Generic imports
 */

import java.util.*;

import org.drip.analytics.date.*;
import org.drip.analytics.support.CaseInsensitiveTreeMap;
import org.drip.product.definition.*;
import org.drip.service.env.*;

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
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
 * Copyright (C) 2012 Lakshmi Krishnamurthy
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
 * <i>BuiltInCDSPortfolioDefinitions</i> displays the Built-in CDS Portfolios. It shows the following:
 *  
 * <br><br>
 *  <ul>
 *  	<li>
 * 			Construct the CDX.NA.IG 5Y Series 17 index by name and series.
 *  	</li>
 *  	<li>
 * 			Construct the on-the-run CDX.NA.IG 5Y Series index.
 *  	</li>
 *  	<li>
 * 			 List all the built-in CDX's - their names and descriptions.
 *  	</li>
 *  	<li>
 *  		Construct the on-the run CDX.EM 5Y corresponding to T - 1Y.
 *  	</li>
 *  	<li>
 *  		Construct the on-the run ITRAXX.ENERGY 5Y corresponding to T - 7Y.
 *  	</li>
 *  	<li>
 *  		 Retrieve the full set of date/index series set for ITRAXX.ENERGY.
 *  	</li>
 *  </ul>
 *  
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/cashflow/README.md">Single Name Portfolio CDS Analytics</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class BuiltInCDSPortfolioDefinitions {

	/*
	 * Sample demonstrating the creation/usage of the CDX API
	 * 
	 *  	USE WITH CARE: This sample ignores errors and does not handle exceptions.
	 */

	private static final void BasketCDSAPISample()
	{
		JulianDate dtToday = DateUtil.CreateFromYMD (
			2013,
			DateUtil.MAY,
			10
		);

		/*
		 * Construct the CDX.NA.IG 5Y Series 17 index by name and series
		 */

		/* BasketProduct bpCDX = CreditAnalytics.MakeCDX (
			"CDX.NA.IG",
			17,
			"5Y"
		); */

		/*
		 * Construct the on-the-run CDX.NA.IG 5Y Series index
		 */

		/* BasketProduct bpCDXOTR = CreditAnalytics.MakeCDX (
			"CDX.NA.IG",
			dtToday,
			"5Y"
		); */

		/*
		 * List of all the built-in CDX names
		 */

		Set<String> setstrCDXNames = StandardCDXManager.GetIndexNames();

		/*
		 * Descriptions of all the built-in CDX names
		 */

		CaseInsensitiveTreeMap<String> mapCDXDescr = StandardCDXManager.GetCDXDescriptions();

		/*
		 * Construct the on-the run CDX.EM 5Y corresponding to T - 1Y
		 */

		BasketProduct bpPresetOTR = StandardCDXManager.GetOnTheRun (
			"CDX.EM",
			dtToday.subtractTenor ("1Y"),
			"5Y"
		);

		/*
		 * Construct the on-the run ITRAXX.ENERGY 5Y corresponding to T - 7Y
		 */

		BasketProduct bpPreLoadedOTR = StandardCDXManager.GetOnTheRun (
			"ITRAXX.ENERGY",
			dtToday.subtractTenor ("7Y"),
			"5Y"
		);

		/*
		 * Retrieve the full set of date/index series set for ITRAXX.ENERGY
		 */

		Map<JulianDate, Integer> mapCDXSeries = StandardCDXManager.GetIndexSeriesMap ("ITRAXX.ENERGY");

		// System.out.println (bpCDX.name() + ": " + bpCDX.effective() + "=>" + bpCDX.maturity());

		// System.out.println (bpCDXOTR.name() + ": " + bpCDXOTR.effective() + "=>" + bpCDXOTR.maturity());

		int i = 0;

		for (String strCDX : setstrCDXNames)
			System.out.println ("CDX[" + i++ + "]: " + strCDX);

		for (Map.Entry<String, String> meCDXDescr : mapCDXDescr.entrySet())
			System.out.println ("[" + meCDXDescr.getKey() + "]: " + meCDXDescr.getValue());

		System.out.println (bpPresetOTR.name() + ": " + bpPresetOTR.effective() + "=>" + bpPresetOTR.maturity());

		System.out.println (bpPreLoadedOTR.name() + ": " + bpPreLoadedOTR.effective() + "=>" + bpPreLoadedOTR.maturity());

		for (Map.Entry<JulianDate, Integer> me : mapCDXSeries.entrySet())
			System.out.println ("ITRAXX.ENERGY[" + me.getValue() + "]: " + me.getKey());
	}

	/**
	 * Entry Point
	 * 
	 * @param astrArgs Command Line Argument Array
	 * 
	 * @throws Exception Thrown on Error/Exception Situation
	 */

	public static final void main (
		final String astrArgs[])
		throws Exception
	{
		// String strConfig = "c:\\Lakshmi\\BondAnal\\Config.xml";

		String strConfig = "";

		EnvManager.InitEnv (strConfig);

		BasketCDSAPISample();

		EnvManager.TerminateEnv();
	}
}
