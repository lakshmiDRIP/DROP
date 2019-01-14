
package org.drip.sample.floatfloat;

import org.drip.market.otc.*;
import org.drip.service.env.EnvManager;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
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
 * <i>JurisdictionOTCIndexDefinitions</i> contains all the pre-fixed Definitions of the Jurisdiction OTC
 * Float-Float Swap Contracts.
 *  
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">Sample</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/floatfloat/README.md">Float Float Swap Analytics</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class JurisdictionOTCIndexDefinitions {
	private static final void DisplayOTCInfo (
		String strCurrency)
	{
		FloatFloatSwapConvention ffConv = IBORFloatFloatContainer.ConventionFromJurisdiction (strCurrency);

		System.out.println (
			"\t\t" + strCurrency + " => " +
			ffConv.referenceTenor() + " | " +
			ffConv.spotLag() + " | " +
			ffConv.basisOnDerivedStream() + " | " +
			ffConv.basisOnDerivedComponent() + " | " +
			ffConv.derivedCompoundedToReference() + " | " +
			ffConv.componentPair()
		);
	}

	public static final void main (
		String[] args)
	{
		EnvManager.InitEnv ("");

		System.out.println ("\n\t--------------------------------------------------------------------------------------------------------");

		System.out.println ("\t\tL -> R:");

		System.out.println ("\t\t\tCurrency");

		System.out.println ("\t\t\tReference Tenor");

		System.out.println ("\t\t\tSpot Lag");

		System.out.println ("\t\t\tBasis on Derived Stream");

		System.out.println ("\t\t\tBasis on Derived Component");

		System.out.println ("\t\t\tDerived Stream Compounded To Reference Stream");

		System.out.println ("\t\t\tComponent Pair");

		System.out.println ("\t--------------------------------------------------------------------------------------------------------");

		DisplayOTCInfo ("AUD");

		DisplayOTCInfo ("CAD");

		DisplayOTCInfo ("CHF");

		DisplayOTCInfo ("CNY");

		DisplayOTCInfo ("DKK");

		DisplayOTCInfo ("EUR");

		DisplayOTCInfo ("GBP");

		DisplayOTCInfo ("HKD");

		DisplayOTCInfo ("INR");

		DisplayOTCInfo ("JPY");

		DisplayOTCInfo ("NOK");

		DisplayOTCInfo ("NZD");

		DisplayOTCInfo ("PLN");

		DisplayOTCInfo ("SEK");

		DisplayOTCInfo ("SGD");

		DisplayOTCInfo ("USD");

		DisplayOTCInfo ("ZAR");

		System.out.println ("\t--------------------------------------------------------------------------------------------------------");

		EnvManager.TerminateEnv();
	}
}
