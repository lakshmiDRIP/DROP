
package org.drip.service.env;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting risk, transaction costs, exposure, margin
 *  	calculations, and portfolio construction within and across fixed income, credit, commodity, equity,
 *  	FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three main modules:
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
 * 	- Statistical Learning Library
 * 	- Numerical Optimizer Library
 * 	- Machine Learning Library
 * 	- Spline Builder Library
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
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
 * <i>BuildManager</i> maintains a Log of the Build Records.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AlgorithmSupportLibrary.md">Algorithm Support Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service">Service</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/env">Environment Shell</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class BuildManager
{
	private static org.drip.service.env.BuildRecord[] s_aBuildRecord = null;

	/**
	 * Initialize the Build Logs of the Build Manager
	 * 
	 * @return TRUE - The Build Manager Successfully Initialized
	 */

	public static final boolean Init()
	{
		if (null != s_aBuildRecord) return true;

		try {
			s_aBuildRecord = new org.drip.service.env.BuildRecord[] {
				new org.drip.service.env.BuildRecord (
					"4.37.0",
					"1.8.0_112",
					"Sat Oct 19 19:50:20 EST 2019"
				),
				new org.drip.service.env.BuildRecord (
					"4.37.0",
					"1.8.0_112",
					"Fri Oct 18 22:31:19 EST 2019"
				),
				new org.drip.service.env.BuildRecord (
					"4.36.0",
					"1.8.0_112",
					"Sun Oct 13 22:03:43 EST 2019"
				),
				new org.drip.service.env.BuildRecord (
					"4.19.0",
					"1.8.0_112",
					"Thu Jul 04 22:11:11 EST 2019"
				),
				new org.drip.service.env.BuildRecord (
					"4.18.0",
					"1.8.0_112",
					"Mon May 27 19:47:55 EST 2019"
				),
				new org.drip.service.env.BuildRecord (
					"4.17.0",
					"1.8.0_112",
					"Tue May 14 21:44:48 EST 2019"
				),
				new org.drip.service.env.BuildRecord (
					"4.16.0",
					"1.8.0_112",
					"Sat Apr 27 20:53:19 EST 2019"
				),
				new org.drip.service.env.BuildRecord (
					"4.15.0",
					"1.8.0_112",
					"Wed Apr 24 12:35:43 EST 2019"
				),
				new org.drip.service.env.BuildRecord (
					"4.14.0",
					"1.8.0_112",
					"Wed Apr 17 11:54:38 EST 2019"
				),
				new org.drip.service.env.BuildRecord (
					"4.13.0",
					"1.8.0_112",
					"Fri Apr 12 10:49:43 EST 2019"
				),
				new org.drip.service.env.BuildRecord (
					"4.12.0",
					"1.8.0_112",
					"Sat Apr 06 16:58:04 EST 2019"
				),
				new org.drip.service.env.BuildRecord (
					"4.11.0",
					"1.8.0_112",
					"Sat Mar 29 18:48:14 EST 2019"
				),
				new org.drip.service.env.BuildRecord (
					"4.10.0",
					"1.8.0_112",
					"Tue Mar 19 16:24:49 EST 2019"
				),
				new org.drip.service.env.BuildRecord (
					"4.08.0",
					"1.8.0_112",
					"Thu Mar 07 21:38:41 EST 2019"
				),
				new org.drip.service.env.BuildRecord (
					"4.07.0",
					"1.8.0_112",
					"Sat Mar 02 17:16:00 EST 2019"
				),
				new org.drip.service.env.BuildRecord (
					"4.06.0",
					"1.8.0_112",
					"Sat Feb 23 10:33:18 EST 2019"
				),
				new org.drip.service.env.BuildRecord (
					"4.05.0",
					"1.8.0_112",
					"Tue Feb 19 11:07:47 EST 2019"
				),
				new org.drip.service.env.BuildRecord (
					"4.04.0",
					"1.8.0_112",
					"Thu Feb 14 09:38:49 EST 2019"
				),
				new org.drip.service.env.BuildRecord (
					"4.03.0",
					"1.8.0_112",
					"Mon Feb 11 08:55:21 EST 2019"
				),
				new org.drip.service.env.BuildRecord (
					"4.02.0",
					"1.8.0_112",
					"Thu Jan 31 23:20:25 EST 2019"
				),
				new org.drip.service.env.BuildRecord (
					"4.01.0",
					"1.8.0_112",
					"Mon Jan 28 20:28:11 EST 2019"
				),
				new org.drip.service.env.BuildRecord (
					"4.00.0",
					"1.8.0_112",
					"Sun Jan 20 19:56:44 EST 2019"
				),
				new org.drip.service.env.BuildRecord (
					"3.98.0",
					"1.8.0_112",
					"Thu Jan 03 12:59:38 EST 2019"
				),
				new org.drip.service.env.BuildRecord (
					"3.97.0",
					"1.8.0_112",
					"Fri Dec 21 10:15:16 EST 2019"
				),
				new org.drip.service.env.BuildRecord (
					"3.96.0",
					"1.8.0_112",
					"Wed Dec 19 21:02:03 EST 2019"
				),
				new org.drip.service.env.BuildRecord (
					"3.95.0",
					"1.8.0_112",
					"Wed Dec 19 15:54:46 EST 2019"
				),
				new org.drip.service.env.BuildRecord (
					"3.94.0",
					"1.8.0_112",
					"Tue Dec 18 14:29:43 EST 2019"
				),
				new org.drip.service.env.BuildRecord (
					"3.93.0",
					"1.8.0_112",
					"Mon Dec 17 18:34:10 EST 2019"
				),
				new org.drip.service.env.BuildRecord (
					"3.92.0",
					"1.8.0_112",
					"Wed Dec 12 21:15:06 EST 2019"
				),
				new org.drip.service.env.BuildRecord (
					"3.91.0",
					"1.8.0_112",
					"Sun Dec 09 23:52:23 EST 2019"
				),
				new org.drip.service.env.BuildRecord (
					"3.90.0",
					"1.8.0_112",
					"Fri Dec 07 23:42:09 EST 2019"
				),
				new org.drip.service.env.BuildRecord (
					"3.89.0",
					"1.8.0_112",
					"Fri Dec 07 01:32:58 EST 2018"
				),
				new org.drip.service.env.BuildRecord (
					"3.88.0",
					"1.8.0_112",
					"Thu Nov 29 18:45:12 EST 2018"
				),
				new org.drip.service.env.BuildRecord (
					"3.87.0",
					"1.8.0_112",
					"Thu Nov 29 14:00:00 EST 2018"
				),
				new org.drip.service.env.BuildRecord (
					"3.86.0",
					"1.8.0_112",
					"Sun Nov 25 20:17:23 EST 2018"
				),
				new org.drip.service.env.BuildRecord (
					"3.85.0",
					"1.8.0_112",
					"Sat Nov 24 00:31:09 EST 2018"
				),
				new org.drip.service.env.BuildRecord (
					"3.84.0",
					"1.8.0_112",
					"Mon Nov 19 18:01:46 EST 2018"
				),
				new org.drip.service.env.BuildRecord (
					"3.83.0",
					"1.8.0_112",
					"Thu Nov 15 11:21:47 EST 2018"
				),
				new org.drip.service.env.BuildRecord (
					"3.82.0",
					"1.8.0_112",
					"Sun Nov 11 15:42:23 EST 2018"
				),
				new org.drip.service.env.BuildRecord (
					"3.81.0",
					"1.8.0_112",
					"Mon Nov 05 18:22:06 EST 2018"
				),
				new org.drip.service.env.BuildRecord (
					"3.80.0",
					"1.8.0_112",
					"Fri Nov 02 21:22:20 EST 2018"
				),
				new org.drip.service.env.BuildRecord (
					"3.79.0",
					"1.8.0_112",
					"Fri Oct 26 15:37:12 EST 2018"
				),
				new org.drip.service.env.BuildRecord (
					"3.78.0",
					"1.8.0_112",
					"Tue Oct 16 18:01:52 EST 2018"
				),
				new org.drip.service.env.BuildRecord (
					"3.77.0",
					"1.8.0_112",
					"Wed Oct 03 23:30:03 EST 2018"
				),
				new org.drip.service.env.BuildRecord (
					"3.76.0",
					"1.8.0_112",
					"Sat Sep 29 00:34:42 EST 2018"
				),
				new org.drip.service.env.BuildRecord (
					"3.75.0",
					"1.8.0_112",
					"Sun Sep 23 20:49:57 EST 2018"
				),
				new org.drip.service.env.BuildRecord (
					"3.74.0",
					"1.8.0_112",
					"Tue Sep 11 23:21:49 EST 2018"
				),
				new org.drip.service.env.BuildRecord (
					"3.73.0",
					"1.8.0_112",
					"Mon Sep 03 09:50:58 EST 2018"
				),
				new org.drip.service.env.BuildRecord (
					"3.72.0",
					"1.8.0_112",
					"Sat Aug 25 21:49:58 EST 2018"
				),
				new org.drip.service.env.BuildRecord (
					"3.71.0",
					"1.8.0_112",
					"Sun Aug 19 17:11:04 EST 2018"
				),
				new org.drip.service.env.BuildRecord (
					"3.70.0",
					"1.8.0_112",
					"Mon Aug 13 20:18:11 EST 2018"
				),
				new org.drip.service.env.BuildRecord (
					"3.68.0",
					"1.8.0_112",
					"Sat Aug 03 17:46:29 EST 2018"
				),
				new org.drip.service.env.BuildRecord (
					"3.67.0",
					"1.8.0_112",
					"Sun Jul 29 22:33:36 EST 2018"
				),
				new org.drip.service.env.BuildRecord (
					"3.65.0",
					"1.8.0_112",
					"Wed Jul 18 15:07:54 EST 2018"
				),
				new org.drip.service.env.BuildRecord (
					"3.61.0",
					"1.8.0_112",
					"Wed Jul 11 14:57:02 EST 2018"
				),
				new org.drip.service.env.BuildRecord (
					"3.56.0",
					"1.8.0_112",
					"Thu Jun 21 17:36:41 EST 2018"
				),
				new org.drip.service.env.BuildRecord (
					"3.54.0",
					"1.8.0_112",
					"Fri Jun 15 22:02:20 EST 2018"
				),
				new org.drip.service.env.BuildRecord (
					"3.52.0",
					"1.8.0_112",
					"Wed Jun 02 11:42:46 EST 2018"
				),
				new org.drip.service.env.BuildRecord (
					"3.45.0",
					"1.8.0_112",
					"Wed May 23 17:25:31 EST 2018"
				),
				new org.drip.service.env.BuildRecord (
					"3.44.0",
					"1.8.0_112",
					"Tue May 22 14:55:44 EST 2018"
				),
				new org.drip.service.env.BuildRecord (
					"3.43.0",
					"1.8.0_112",
					"Wed Apr 25 07:39:51 EST 2018"
				),
				new org.drip.service.env.BuildRecord (
					"3.42.0",
					"1.8.0_112",
					"Sat Apr 21 03:24:56 EST 2018"
				),
				new org.drip.service.env.BuildRecord (
					"3.41.0",
					"1.8.0_112",
					"Tue Apr 17 12:01:07 EST 2018"
				),
				new org.drip.service.env.BuildRecord (
					"3.40.0",
					"1.8.0_112",
					"Sat Apr 15 14:31:21 EST 2018"
				),
				new org.drip.service.env.BuildRecord (
					"3.39.0",
					"1.8.0_112",
					"Sat Apr 07 16:49:44 EST 2018"
				),
				new org.drip.service.env.BuildRecord (
					"3.38.0",
					"1.8.0_112",
					"Wed Apr 04 23:31:19 EST 2018"
				),
				new org.drip.service.env.BuildRecord (
					"3.37.0",
					"1.8.0_112",
					"Wed Mar 28 19:26:05 EST 2018"
				),
				new org.drip.service.env.BuildRecord (
					"3.36.0",
					"1.8.0_112",
					"Fri Mar 09 09:38:17 EST 2018"
				),
				new org.drip.service.env.BuildRecord (
					"3.35.0",
					"1.8.0_112",
					"Mon Mar 05 14:23:32 EST 2018"
				),
				new org.drip.service.env.BuildRecord (
					"3.34.0",
					"1.8.0_112",
					"Thu Feb 26 23:27:08 EST 2018"
				),
				new org.drip.service.env.BuildRecord (
					"3.33.0",
					"1.8.0_112",
					"Thu Feb 22 22:59:49 EST 2018"
				),
				new org.drip.service.env.BuildRecord (
					"3.32.0",
					"1.8.0_112",
					"Sat Feb 10 23:20:26 EST 2018"
				),
				new org.drip.service.env.BuildRecord (
					"3.31.0",
					"1.8.0_112",
					"Tue Feb 06 01:10:47 EST 2018"
				),
				new org.drip.service.env.BuildRecord (
					"3.30.0",
					"1.8.0_112",
					"Mon Jan 29 18:28:41 EST 2018"
				),
				new org.drip.service.env.BuildRecord (
					"3.29.0",
					"1.8.0_112",
					"Fri Jan 26 20:46:23 EST 2018"
				),
				new org.drip.service.env.BuildRecord (
					"3.28.0",
					"1.8.0_112",
					"Tue Jan 16 22:46:36 EST 2018"
				),
				new org.drip.service.env.BuildRecord (
					"3.27.0",
					"1.8.0_112",
					"Sat Jan 13 13:23:56 EST 2018"
				),
				new org.drip.service.env.BuildRecord (
					"3.26.0",
					"1.8.0_112",
					"Mon Jan 08 18:01:41 EST 2018"
				),
				new org.drip.service.env.BuildRecord (
					"3.25.0",
					"1.8.0_112",
					"Mon Dec 31 18:43:34 EST 2017"
				),
				new org.drip.service.env.BuildRecord (
					"3.24.0",
					"1.8.0_112",
					"Mon Dec 25 12:29:26 EST 2017"
				),
				new org.drip.service.env.BuildRecord (
					"3.23.0",
					"1.8.0_112",
					"Fri Dec 22 14:51:17 EST 2017"
				),
				new org.drip.service.env.BuildRecord (
					"3.22.0",
					"1.8.0_112",
					"Mon Dec 18 17:32:03 EST 2017"
				)
			};

			return true;
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Retrieve the Array of Build Records
	 * 
	 * @return Array of Build Records
	 */

	public static final org.drip.service.env.BuildRecord[] buildRecords()
	{
		return s_aBuildRecord;
	}

	/**
	 * Retrieve the Latest Build Record
	 * 
	 * @return Latest Build Record
	 */

	public static final org.drip.service.env.BuildRecord latestBuildRecord()
	{
		return s_aBuildRecord[0];
	}
}
