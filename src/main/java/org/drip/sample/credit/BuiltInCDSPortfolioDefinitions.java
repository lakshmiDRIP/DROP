
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
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
 * Copyright (C) 2012 Lakshmi Krishnamurthy
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
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">Sample</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/credit/README.md">Credit Analytics</a></li>
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

		Set<String> setstrCDXNames = StandardCDXManager.GetCDXNames();

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

		Map<JulianDate, Integer> mapCDXSeries = StandardCDXManager.GetCDXSeriesMap ("ITRAXX.ENERGY");

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
