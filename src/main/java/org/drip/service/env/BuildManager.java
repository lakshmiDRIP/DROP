
package org.drip.service.env;

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
 * <i>BuildManager</i> maintains a Log of the Build Records.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationSupportLibrary.md">Computation Support</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/README.md">Environment, Product/Definition Containers, and Scenario/State Manipulation APIs</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/env/README.md">Library Module Loader Environment Manager</a></li>
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
					"6.47.0",
					"15.0.1+9-18",
					"Sun Aug 11 01:51:44 EST 2024"
				),
				new org.drip.service.env.BuildRecord (
					"6.46.0",
					"15.0.1+9-18",
					"Sun Aug 04 17:15:49 EST 2024"
				),
				new org.drip.service.env.BuildRecord (
					"6.45.0",
					"15.0.1+9-18",
					"Sun Jul 28 15:17:44 EST 2024"
				),
				new org.drip.service.env.BuildRecord (
					"6.44.0",
					"15.0.1+9-18",
					"Sun Jul 14 08:33:21 EST 2024"
				),
				new org.drip.service.env.BuildRecord (
					"6.43.0",
					"15.0.1+9-18",
					"Mon Jul 08 19:15:14 EST 2024"
				),
				new org.drip.service.env.BuildRecord (
					"6.42.0",
					"15.0.1+9-18",
					"Fri Jul 05 23:15:52 EST 2024"
				),
				new org.drip.service.env.BuildRecord (
					"6.41.0",
					"15.0.1+9-18",
					"Tue Jul 02 22:12:45 EST 2024"
				),
				new org.drip.service.env.BuildRecord (
					"6.40.0",
					"15.0.1+9-18",
					"Fri Jun 28 21:12:32 EST 2024"
				),
				new org.drip.service.env.BuildRecord (
					"6.39.0",
					"15.0.1+9-18",
					"Sat Jun 22 23:14:52 EST 2024"
				),
				new org.drip.service.env.BuildRecord (
					"6.38.0",
					"15.0.1+9-18",
					"Sun Jun 16 04:24:36 EST 2024"
				),
				new org.drip.service.env.BuildRecord (
					"6.37.0",
					"15.0.1+9-18",
					"Sat Jun 15 14:35:50 EST 2024"
				),
				new org.drip.service.env.BuildRecord (
					"6.36.0",
					"15.0.1+9-18",
					"Mon Jun 10 00:39:14 EST 2024"
				),
				new org.drip.service.env.BuildRecord (
					"6.35.0",
					"15.0.1+9-18",
					"Sat Jun 08 22:50:22 EST 2024"
				),
				new org.drip.service.env.BuildRecord (
					"6.34.0",
					"15.0.1+9-18",
					"Sat Jun 08 14:51:10 EST 2024"
				),
				new org.drip.service.env.BuildRecord (
					"6.33.0",
					"15.0.1+9-18",
					"Sat Jun 08 00:49:42 EST 2024"
				),
				new org.drip.service.env.BuildRecord (
					"6.32.0",
					"15.0.1+9-18",
					"Mon Jun 03 21:21:19 EST 2024"
				),
				new org.drip.service.env.BuildRecord (
					"6.31.0",
					"15.0.1+9-18",
					"Sun Jun 02 16:38:29 EST 2024"
				),
				new org.drip.service.env.BuildRecord (
					"6.30.0",
					"15.0.1+9-18",
					"Sat Jun 01 21:54:47 EST 2024"
				),
				new org.drip.service.env.BuildRecord (
					"6.29.0",
					"15.0.1+9-18",
					"Sat Jun 01 15:41:18 EST 2024"
				),
				new org.drip.service.env.BuildRecord (
					"6.28.0",
					"15.0.1+9-18",
					"Mon May 27 21:40:53 EST 2024"
				),
				new org.drip.service.env.BuildRecord (
					"6.27.0",
					"15.0.1+9-18",
					"Mon May 27 13:30:28 EST 2024"
				),
				new org.drip.service.env.BuildRecord (
					"6.26.0",
					"15.0.1+9-18",
					"Sun May 26 04:14:20 EST 2024"
				),
				new org.drip.service.env.BuildRecord (
					"6.25.0",
					"15.0.1+9-18",
					"Sat May 25 23:38:31 EST 2024"
				),
				new org.drip.service.env.BuildRecord (
					"6.24.0",
					"15.0.1+9-18",
					"Sat May 25 21:17:48 EST 2024"
				),
				new org.drip.service.env.BuildRecord (
					"6.23.0",
					"15.0.1+9-18",
					"Sun Mar 24 21:42:13 EST 2024"
				),
				new org.drip.service.env.BuildRecord (
					"6.22.0",
					"15.0.1+9-18",
					"Sun Mar 24 18:57:49 EST 2024"
				),
				new org.drip.service.env.BuildRecord (
					"6.21.0",
					"15.0.1+9-18",
					"Sun Mar 17 19:17:32 EST 2024"
				),
				new org.drip.service.env.BuildRecord (
					"6.20.0",
					"15.0.1+9-18",
					"Sun Mar 17 13:22:53 EST 2024"
				),
				new org.drip.service.env.BuildRecord (
					"6.19.0",
					"15.0.1+9-18",
					"Sat Mar 16 16:45:28 EST 2024"
				),
				new org.drip.service.env.BuildRecord (
					"6.18.0",
					"15.0.1+9-18",
					"Sat Mar 16 10:58:27 EST 2024"
				),
				new org.drip.service.env.BuildRecord (
					"6.17.0",
					"15.0.1+9-18",
					"Wed Feb 28 22:15:31 EST 2024"
				),
				new org.drip.service.env.BuildRecord (
					"6.16.0",
					"15.0.1+9-18",
					"Wed Feb 28 00:55:23 EST 2024"
				),
				new org.drip.service.env.BuildRecord (
					"6.15.0",
					"15.0.1+9-18",
					"Mon Feb 26 06:42:02 EST 2024"
				),
				new org.drip.service.env.BuildRecord (
					"6.14.0",
					"15.0.1+9-18",
					"Sat Feb 24 22:12:59 EST 2024"
				),
				new org.drip.service.env.BuildRecord (
					"6.13.0",
					"15.0.1+9-18",
					"Fri Feb 23 08:30:42 EST 2024"
				),
				new org.drip.service.env.BuildRecord (
					"6.12.0",
					"15.0.1+9-18",
					"Thu Feb 22 23:56:01 EST 2024"
				),
				new org.drip.service.env.BuildRecord (
					"6.11.0",
					"15.0.1+9-18",
					"Wed Feb 07 13:52:31 EST 2024"
				),
				new org.drip.service.env.BuildRecord (
					"6.10.0",
					"15.0.1+9-18",
					"Sun Feb 04 16:54:31 EST 2024"
				),
				new org.drip.service.env.BuildRecord (
					"6.09.0",
					"15.0.1+9-18",
					"Sat Feb 03 02:34:39 EST 2024"
				),
				new org.drip.service.env.BuildRecord (
					"6.08.0",
					"15.0.1+9-18",
					"Sat Jan 27 22:01:41 EST 2024"
				),
				new org.drip.service.env.BuildRecord (
					"6.07.0",
					"15.0.1+9-18",
					"Sun Jan 21 20:18:09 EST 2024"
				),
				new org.drip.service.env.BuildRecord (
					"6.06.0",
					"15.0.1+9-18",
					"Fri Jan 19 19:25:36 EST 2024"
				),
				new org.drip.service.env.BuildRecord (
					"6.05.0",
					"15.0.1+9-18",
					"Mon Jan 15 14:32:15 EST 2024"
				),
				new org.drip.service.env.BuildRecord (
					"6.04.0",
					"15.0.1+9-18",
					"San Jan 14 13:01:41 EST 2024"
				),
				new org.drip.service.env.BuildRecord (
					"6.03.0",
					"15.0.1+9-18",
					"Fri Jan 12 16:00:29 EST 2024"
				),
				new org.drip.service.env.BuildRecord (
					"6.02.0",
					"15.0.1+9-18",
					"Sun Jan 07 23:17:10 EST 2024"
				),
				new org.drip.service.env.BuildRecord (
					"6.01.0",
					"15.0.1+9-18",
					"Sun Jan 07 02:06:25 EST 2024"
				),
				new org.drip.service.env.BuildRecord (
					"6.00.0",
					"15.0.1+9-18",
					"Sat Jan 06 02:36:14 EST 2024"
				),
				new org.drip.service.env.BuildRecord (
					"5.99.0",
					"15.0.1+9-18",
					"Tue Jan 02 21:34:10 EST 2024"
				),
				new org.drip.service.env.BuildRecord (
					"5.98.0",
					"15.0.1+9-18",
					"Mon Jan 01 15:28:54 EST 2024"
				),
				new org.drip.service.env.BuildRecord (
					"5.97.0",
					"15.0.1+9-18",
					"Thu Dec 28 02:52:46 EST 2023"
				),
				new org.drip.service.env.BuildRecord (
					"5.96.0",
					"15.0.1+9-18",
					"Tue Dec 26 12:15:00 EST 2023"
				),
				new org.drip.service.env.BuildRecord (
					"5.95.0",
					"15.0.1+9-18",
					"Mon Dec 25 12:53:32 EST 2023"
				),
				new org.drip.service.env.BuildRecord (
					"5.94.0",
					"15.0.1+9-18",
					"Sun Dec 24 16:17:54 EST 2023"
				),
				new org.drip.service.env.BuildRecord (
					"5.93.0",
					"15.0.1+9-18",
					"Sat Dec 23 19:17:16 EST 2023"
				),
				new org.drip.service.env.BuildRecord (
					"5.92.0",
					"15.0.1+9-18",
					"Sat Dec 23 08:42:04 EST 2023"
				),
				new org.drip.service.env.BuildRecord (
					"5.91.0",
					"15.0.1+9-18",
					"Mon Dec 18 14:24:56 EST 2023"
				),
				new org.drip.service.env.BuildRecord (
					"5.90.0",
					"15.0.1+9-18",
					"Sat Dec 16 11:28:26 EST 2023"
				),
				new org.drip.service.env.BuildRecord (
					"5.89.0",
					"15.0.1+9-18",
					"Sun Dec 10 17:09:30 EST 2023"
				),
				new org.drip.service.env.BuildRecord (
					"5.88.0",
					"15.0.1+9-18",
					"Sun Dec 03 12:52:48 EST 2023"
				),
				new org.drip.service.env.BuildRecord (
					"5.87.0",
					"15.0.1+9-18",
					"Fri Nov 24 20:37:50 EST 2023"
				),
				new org.drip.service.env.BuildRecord (
					"5.86.0",
					"15.0.1+9-18",
					"Thu Nov 23 13:20:31 EST 2023"
				),
				new org.drip.service.env.BuildRecord (
					"5.85.0",
					"15.0.1+9-18",
					"Sun Aug 13 00:50:57 EST 2023"
				),
				new org.drip.service.env.BuildRecord (
					"5.84.0",
					"15.0.1+9-18",
					"Sat Apr 02 08:52:22 EST 2023"
				),
				new org.drip.service.env.BuildRecord (
					"5.83.0",
					"15.0.1+9-18",
					"Fri Apr 14 20:19:23 EST 2023"
				),
				new org.drip.service.env.BuildRecord (
					"5.82.0",
					"15.0.1+9-18",
					"Mon Apr 03 20:21:19 EST 2023"
				),
				new org.drip.service.env.BuildRecord (
					"5.81.0",
					"15.0.1+9-18",
					"Sat Apr 01 14:36:23 EST 2023"
				),
				new org.drip.service.env.BuildRecord (
					"5.80.0",
					"15.0.1+9-18",
					"Tue Mar 14 00:51:51 EST 2023"
				),
				new org.drip.service.env.BuildRecord (
					"5.79.0",
					"15.0.1+9-18",
					"Sun Mar 11 05:20:39 EST 2023"
				),
				new org.drip.service.env.BuildRecord (
					"5.78.0",
					"15.0.1+9-18",
					"Sun Feb 26 17:59:31 EST 2023"
				),
				new org.drip.service.env.BuildRecord (
					"5.77.0",
					"15.0.1+9-18",
					"Fri Feb 24 21:47:35 EST 2023"
				),
				new org.drip.service.env.BuildRecord (
					"5.76.0",
					"15.0.1+9-18",
					"Thu Feb 23 19:55:07 EST 2023"
				),
				new org.drip.service.env.BuildRecord (
					"5.75.0",
					"15.0.1+9-18",
					"Sat Feb 18 18:02:21 EST 2023"
				),
				new org.drip.service.env.BuildRecord (
					"5.74.0",
					"15.0.1+9-18",
					"Fri Feb 03 19:58:41 EST 2023"
				),
				new org.drip.service.env.BuildRecord (
					"5.73.0",
					"15.0.1+9-18",
					"Sun Jan 21 11:51:12 EST 2023"
				),
				new org.drip.service.env.BuildRecord (
					"5.72.0",
					"15.0.1+9-18",
					"Sat Jan 07 22:19:47 EST 2023"
				),
				new org.drip.service.env.BuildRecord (
					"5.71.0",
					"15.0.1+9-18",
					"Fri Jan 06 21:32:51 EST 2023"
				),
				new org.drip.service.env.BuildRecord (
					"5.70.0",
					"15.0.1+9-18",
					"Mon Jan 02 18:07:13 EST 2023"
				),
				new org.drip.service.env.BuildRecord (
					"5.69.0",
					"15.0.1+9-18",
					"Mon Dec 26 21:00:17 EST 2022"
				),
				new org.drip.service.env.BuildRecord (
					"5.68.0",
					"15.0.1+9-18",
					"Sat Dec 24 12:58:51 EST 2022"
				),
				new org.drip.service.env.BuildRecord (
					"5.67.0",
					"15.0.1+9-18",
					"Sat Dec 24 12:23:51 EST 2022"
				),
				new org.drip.service.env.BuildRecord (
					"5.66.0",
					"15.0.1+9-18",
					"Sat Dec 24 11:30:47 EST 2022"
				),
				new org.drip.service.env.BuildRecord (
					"5.65.0",
					"15.0.1+9-18",
					"Sat Nov 26 20:50:49 EST 2022"
				),
				new org.drip.service.env.BuildRecord (
					"5.64.0",
					"15.0.1+9-18",
					"Sat Nov 26 20:16:35 EST 2022"
				),
				new org.drip.service.env.BuildRecord (
					"5.63.0",
					"15.0.1+9-18",
					"Sat Nov 26 19:43:09 EST 2022"
				),
				new org.drip.service.env.BuildRecord (
					"5.62.0",
					"15.0.1+9-18",
					"Mon Oct 24 19:48:37 EST 2022"
				),
				new org.drip.service.env.BuildRecord (
					"5.61.0",
					"15.0.1+9-18",
					"Fri Oct 07 07:37:43 EST 2022"
				),
				new org.drip.service.env.BuildRecord (
					"5.60.0",
					"15.0.1+9-18",
					"Thu Sep 22 12:18:09 EST 2022"
				),
				new org.drip.service.env.BuildRecord (
					"5.59.0",
					"15.0.1+9-18",
					"Sat Sep 10 19:51:08 EST 2022"
				),
				new org.drip.service.env.BuildRecord (
					"5.58.0",
					"15.0.1+9-18",
					"Fri Sep 09 11:42:03 EST 2022"
				),
				new org.drip.service.env.BuildRecord (
					"5.57.0",
					"15.0.1+9-18",
					"Tue Sep 06 22:52:19 EST 2022"
				),
				new org.drip.service.env.BuildRecord (
					"5.56.0",
					"15.0.1+9-18",
					"Mon Sep 05 16:21:07 EST 2022"
				),
				new org.drip.service.env.BuildRecord (
					"5.55.0",
					"15.0.1+9-18",
					"Sun Aug 21 14:18:13 EST 2022"
				),
				new org.drip.service.env.BuildRecord (
					"5.54.0",
					"15.0.1+9-18",
					"Sun Aug 14 08:57:42 EST 2022"
				),
				new org.drip.service.env.BuildRecord (
					"5.53.0",
					"15.0.1+9-18",
					"Thu Aug 11 11:42:04 EST 2022"
				),
				new org.drip.service.env.BuildRecord (
					"5.52.0",
					"15.0.1+9-18",
					"Sun Jul 31 21:28:46 EST 2022"
				),
				new org.drip.service.env.BuildRecord (
					"5.51.0",
					"15.0.1+9-18",
					"Sun Jul 31 20:19:14 EST 2022"
				),
				new org.drip.service.env.BuildRecord (
					"5.50.0",
					"15.0.1+9-18",
					"Sat Jun 01 21:53:14 EST 2022"
				),
				new org.drip.service.env.BuildRecord (
					"5.49.0",
					"15.0.1+9-18",
					"Sun Jun 05 22:35:51 EST 2022"
				),
				new org.drip.service.env.BuildRecord (
					"5.48.0",
					"15.0.1+9-18",
					"Sun Jun 05 22:14:17 EST 2022"
				),
				new org.drip.service.env.BuildRecord (
					"5.47.0",
					"15.0.1+9-18",
					"Sun Jun 05 21:47:42 EST 2022"
				),
				new org.drip.service.env.BuildRecord (
					"5.46.0",
					"15.0.1+9-18",
					"Sun Jun 05 21:22:26 EST 2022"
				),
				new org.drip.service.env.BuildRecord (
					"5.45.0",
					"15.0.1+9-18",
					"Sun Jun 05 20:56:38 EST 2022"
				),
				new org.drip.service.env.BuildRecord (
					"5.44.0",
					"15.0.1+9-18",
					"Sun May 22 22:49:03 EST 2022"
				),
				new org.drip.service.env.BuildRecord (
					"5.43.0",
					"15.0.1+9-18",
					"Sun May 22 20:37:15 EST 2022"
				),
				new org.drip.service.env.BuildRecord (
					"5.42.0",
					"15.0.1+9-18",
					"Fri May 13 22:28:29 EST 2022"
				),
				new org.drip.service.env.BuildRecord (
					"5.41.0",
					"15.0.1+9-18",
					"Fri May 13 21:55:04 EST 2022"
				),
				new org.drip.service.env.BuildRecord (
					"5.40.0",
					"15.0.1+9-18",
					"Mon Apr 25 00:34:47 EST 2022"
				),
				new org.drip.service.env.BuildRecord (
					"5.39.0",
					"15.0.1+9-18",
					"Sun Mar 27 00:46:26 EST 2022"
				),
				new org.drip.service.env.BuildRecord (
					"5.38.0",
					"15.0.1+9-18",
					"Sun Mar 20 23:53:28 EST 2022"
				),
				new org.drip.service.env.BuildRecord (
					"5.37.0",
					"15.0.1+9-18",
					"Sat Mar 19 15:47:38 EST 2022"
				),
				new org.drip.service.env.BuildRecord (
					"5.36.0",
					"15.0.1+9-18",
					"Sun Mar 06 19:14:59 EST 2022"
				),
				new org.drip.service.env.BuildRecord (
					"5.35.0",
					"15.0.1+9-18",
					"Mon Feb 21 15:39:42 EST 2022"
				),
				new org.drip.service.env.BuildRecord (
					"5.34.0",
					"15.0.1+9-18",
					"Sun Feb 20 01:02:10 EST 2022"
				),
				new org.drip.service.env.BuildRecord (
					"5.33.0",
					"15.0.1+9-18",
					"Thu Feb 03 01:27:38 EST 2022"
				),
				new org.drip.service.env.BuildRecord (
					"5.32.0",
					"15.0.1+9-18",
					"Sun Jan 16 12:36:02 EST 2022"
				),
				new org.drip.service.env.BuildRecord (
					"5.31.0",
					"15.0.1+9-18",
					"Wed Jan 12 20:19:46 EST 2022"
				),
				new org.drip.service.env.BuildRecord (
					"5.30.0",
					"15.0.1+9-18",
					"Sat Dec 25 23:55:03 EST 2021"
				),
				new org.drip.service.env.BuildRecord (
					"5.29.0",
					"15.0.1+9-18",
					"Sun Dec 19 16:26:58 EST 2021"
				),
				new org.drip.service.env.BuildRecord (
					"5.28.0",
					"15.0.1+9-18",
					"Mon Dec 13 15:48:51 EST 2021"
				),
				new org.drip.service.env.BuildRecord (
					"5.27.0",
					"15.0.1+9-18",
					"Thu Dec 09 21:09:25 EST 2021"
				),
				new org.drip.service.env.BuildRecord (
					"5.26.0",
					"15.0.1+9-18",
					"Thu Dec 09 20:26:25 EST 2021"
				),
				new org.drip.service.env.BuildRecord (
					"5.25.0",
					"15.0.1+9-18",
					"Sat Nov 27 21:11:47 EST 2021"
				),
				new org.drip.service.env.BuildRecord (
					"5.24.0",
					"15.0.1+9-18",
					"Sun Nov 21 00:10:08 EST 2021"
				),
				new org.drip.service.env.BuildRecord (
					"5.23.0",
					"15.0.1+9-18",
					"Mon Nov 15 22:35:51 EST 2021"
				),
				new org.drip.service.env.BuildRecord (
					"5.22.0",
					"15.0.1+9-18",
					"Fri Nov 05 21:39:41 EST 2021"
				),
				new org.drip.service.env.BuildRecord (
					"5.21.0",
					"15.0.1+9-18",
					"Sun Oct 31 08:55:25 EST 2021"
				),
				new org.drip.service.env.BuildRecord (
					"5.20.0",
					"15.0.1+9-18",
					"Sun Oct 24 20:27:38 EST 2021"
				),
				new org.drip.service.env.BuildRecord (
					"5.19.0",
					"15.0.1+9-18",
					"Fri Oct 22 11:50:26 EST 2021"
				),
				new org.drip.service.env.BuildRecord (
					"5.18.0",
					"15.0.1+9-18",
					"Wed Oct 20 21:52:17 EST 2021"
				),
				new org.drip.service.env.BuildRecord (
					"5.17.0",
					"15.0.1+9-18",
					"Tue Oct 19 21:16:30 EST 2021"
				),
				new org.drip.service.env.BuildRecord (
					"5.16.0",
					"15.0.1+9-18",
					"Fri Oct 15 21:45:15 EST 2021"
				),
				new org.drip.service.env.BuildRecord (
					"5.15.0",
					"15.0.1+9-18",
					"Fri Oct 15 21:03:46 EST 2021"
				),
				new org.drip.service.env.BuildRecord (
					"5.14.0",
					"15.0.1+9-18",
					"Mon Oct 11 16:07:03 EST 2021"
				),
				new org.drip.service.env.BuildRecord (
					"5.13.0",
					"15.0.1+9-18",
					"Sun Oct 10 16:33:24 EST 2021"
				),
				new org.drip.service.env.BuildRecord (
					"5.12.0",
					"15.0.1+9-18",
					"Fri Oct 08 21:27:30 EST 2021"
				),
				new org.drip.service.env.BuildRecord (
					"5.11.0",
					"15.0.1+9-18",
					"Thu Oct 07 23:00:35 EST 2021"
				),
				new org.drip.service.env.BuildRecord (
					"5.10.0",
					"15.0.1+9-18",
					"Thu Sep 30 22:32:28 EST 2021"
				),
				new org.drip.service.env.BuildRecord (
					"5.09.0",
					"15.0.1+9-18",
					"Thu Sep 30 21:26:58 EST 2021"
				),
				new org.drip.service.env.BuildRecord (
					"5.08.0",
					"15.0.1+9-18",
					"Sat Sep 25 17:10:26 EST 2021"
				),
				new org.drip.service.env.BuildRecord (
					"5.07.0",
					"15.0.1+9-18",
					"Sat Sep 25 13:38:02 EST 2021"
				),
				new org.drip.service.env.BuildRecord (
					"5.06.0",
					"15.0.1+9-18",
					"Fri Aug 27 21:00:27 EST 2021"
				),
				new org.drip.service.env.BuildRecord (
					"5.05.0",
					"15.0.1+9-18",
					"Sun Aug 22 15:59:06 EST 2021"
				),
				new org.drip.service.env.BuildRecord (
					"5.04.0",
					"15.0.1+9-18",
					"Sat Aug 14 13:00:00 EST 2021"
				),
				new org.drip.service.env.BuildRecord (
					"5.03.0",
					"15.0.1+9-18",
					"Sat Aug 07 13:12:16 EST 2021"
				),
				new org.drip.service.env.BuildRecord (
					"5.02.0",
					"15.0.1+9-18",
					"Sat Jul 31 18:14:03 EST 2021"
				),
				new org.drip.service.env.BuildRecord (
					"5.01.0",
					"15.0.1+9-18",
					"Sat Jul 03 23:21:09 EST 2021"
				),
				new org.drip.service.env.BuildRecord (
					"5.00.0",
					"15.0.1+9-18",
					"Sat Jul 03 21:15:46 EST 2021"
				),
				new org.drip.service.env.BuildRecord (
					"4.99.0",
					"15.0.1+9-18",
					"Tue Apr 06 22:25:31 EST 2021"
				),
				new org.drip.service.env.BuildRecord (
					"4.98.0",
					"15.0.1+9-18",
					"Sat Mar 20 23:33:55 EST 2021"
				),
				new org.drip.service.env.BuildRecord (
					"4.97.0",
					"15.0.1+9-18",
					"Sat Mar 20 20:01:24 EST 2021"
				),
				new org.drip.service.env.BuildRecord (
					"4.95.0",
					"15.0.1+9-18",
					"Fri Mar 12 22:02:51 EST 2021"
				),
				new org.drip.service.env.BuildRecord (
					"4.94.0",
					"15.0.1+9-18",
					"Wed Mar 10 21:48:13 EST 2021"
				),
				new org.drip.service.env.BuildRecord (
					"4.93.0",
					"15.0.1+9-18",
					"Sat Mar 06 14:46:32 EST 2021"
				),
				new org.drip.service.env.BuildRecord (
					"4.92.0",
					"1.8.0_112",
					"Thu Mar 04 21:16:41 EST 2021"
				),
				new org.drip.service.env.BuildRecord (
					"4.91.0",
					"1.8.0_112",
					"Tue Mar 02 22:34:44 EST 2021"
				),
				new org.drip.service.env.BuildRecord (
					"4.90.0",
					"1.8.0_112",
					"Sun Aug 30 14:21:20 EST 2020"
				),
				new org.drip.service.env.BuildRecord (
					"4.89.0",
					"1.8.0_112",
					"Tue Jul 21 21:08:35 EST 2020"
				),
				new org.drip.service.env.BuildRecord (
					"4.88.0",
					"1.8.0_112",
					"Sun Jul 12 14:49:32 EST 2020"
				),
				new org.drip.service.env.BuildRecord (
					"4.87.0",
					"1.8.0_112",
					"Sun Jul 05 23:52:22 EST 2020"
				),
				new org.drip.service.env.BuildRecord (
					"4.86.0",
					"1.8.0_112",
					"Fri Jul 03 15:45:49 EST 2020"
				),
				new org.drip.service.env.BuildRecord (
					"4.85.0",
					"1.8.0_112",
					"Sat Jun 06 14:04:03 EST 2020"
				),
				new org.drip.service.env.BuildRecord (
					"4.84.0",
					"1.8.0_112",
					"Fri May 29 11:56:48 EST 2020"
				),
				new org.drip.service.env.BuildRecord (
					"4.83.0",
					"1.8.0_112",
					"Sat May 23 14:41:47 EST 2020"
				),
				new org.drip.service.env.BuildRecord (
					"4.82.0",
					"1.8.0_112",
					"Tue May 19 21:33:22 EST 2020"
				),
				new org.drip.service.env.BuildRecord (
					"4.81.0",
					"1.8.0_112",
					"Mon May 18 09:43:43 EST 2020"
				),
				new org.drip.service.env.BuildRecord (
					"4.80.0",
					"1.8.0_112",
					"Mon May 11 11:52:00 EST 2020"
				),
				new org.drip.service.env.BuildRecord (
					"4.79.0",
					"1.8.0_112",
					"Fri May 08 02:07:34 EST 2020"
				),
				new org.drip.service.env.BuildRecord (
					"4.78.0",
					"1.8.0_112",
					"Sun May 03 22:50:49 EST 2020"
				),
				new org.drip.service.env.BuildRecord (
					"4.77.0",
					"1.8.0_112",
					"Fri May 01 01:54:21 EST 2020"
				),
				new org.drip.service.env.BuildRecord (
					"4.76.0",
					"1.8.0_112",
					"Mon Apr 27 09:22:59 EST 2020"
				),
				new org.drip.service.env.BuildRecord (
					"4.75.0",
					"1.8.0_112",
					"Sun Apr 26 17:17:02 EST 2020"
				),
				new org.drip.service.env.BuildRecord (
					"4.74.0",
					"1.8.0_112",
					"Sat Apr 04 23:25:14 EST 2020"
				),
				new org.drip.service.env.BuildRecord (
					"4.73.0",
					"1.8.0_112",
					"Wed Apr 01 09:30:30 EST 2020"
				),
				new org.drip.service.env.BuildRecord (
					"4.72.0",
					"1.8.0_112",
					"Fri Mar 27 12:49:31 EST 2020"
				),
				new org.drip.service.env.BuildRecord (
					"4.71.0",
					"1.8.0_112",
					"Wed Mar 18 21:34:32 EST 2020"
				),
				new org.drip.service.env.BuildRecord (
					"4.70.0",
					"1.8.0_112",
					"Sat Mar 14 04:42:05 EST 2020"
				),
				new org.drip.service.env.BuildRecord (
					"4.69.0",
					"1.8.0_112",
					"Fri Mar 13 11:51:11 EST 2020"
				),
				new org.drip.service.env.BuildRecord (
					"4.68.0",
					"1.8.0_112",
					"Thu Mar 12 11:17:34 EST 2020"
				),
				new org.drip.service.env.BuildRecord (
					"4.67.0",
					"1.8.0_112",
					"Wed Mar 11 04:53:54 EST 2020"
				),
				new org.drip.service.env.BuildRecord (
					"4.66.0",
					"1.8.0_112",
					"Sun Jan 26 23:00:02 EST 2020"
				),
				new org.drip.service.env.BuildRecord (
					"4.65.0",
					"1.8.0_112",
					"Fri Jan 24 23:32:00 EST 2020"
				),
				new org.drip.service.env.BuildRecord (
					"4.64.0",
					"1.8.0_112",
					"Sat Jan 18 23:49:45 EST 2020"
				),
				new org.drip.service.env.BuildRecord (
					"4.63.0",
					"1.8.0_112",
					"Sat Jan 18 14:17:19 EST 2020"
				),
				new org.drip.service.env.BuildRecord (
					"4.62.0",
					"1.8.0_112",
					"Sat Jan 11 16:17:35 EST 2020"
				),
				new org.drip.service.env.BuildRecord (
					"4.61.0",
					"1.8.0_112",
					"Fri Jan 10 20:48:19 EST 2020"
				),
				new org.drip.service.env.BuildRecord (
					"4.60.0",
					"1.8.0_112",
					"Sat Jan 04 14:05:17 EST 2020"
				),
				new org.drip.service.env.BuildRecord (
					"4.59.0",
					"1.8.0_112",
					"Sat Jan 04 12:13:43 EST 2020"
				),
				new org.drip.service.env.BuildRecord (
					"4.58.0",
					"1.8.0_112",
					"Mon Dec 30 05:29:13 EST 2019"
				),
				new org.drip.service.env.BuildRecord (
					"4.57.0",
					"1.8.0_112",
					"Sun Dec 29 11:10:11 EST 2019"
				),
				new org.drip.service.env.BuildRecord (
					"4.56.0",
					"1.8.0_112",
					"Mon Dec 23 12:03:47 EST 2019"
				),
				new org.drip.service.env.BuildRecord (
					"4.55.0",
					"1.8.0_112",
					"Mon Dec 23 04:05:35 EST 2019"
				),
				new org.drip.service.env.BuildRecord (
					"4.54.0",
					"1.8.0_112",
					"Mon Dec 16 00:27:03 EST 2019"
				),
				new org.drip.service.env.BuildRecord (
					"4.53.0",
					"1.8.0_112",
					"Sat Dec 14 05:05:57 EST 2019"
				),
				new org.drip.service.env.BuildRecord (
					"4.52.0",
					"1.8.0_112",
					"Sat Dec 07 20:41:08 EST 2019"
				),
				new org.drip.service.env.BuildRecord (
					"4.51.0",
					"1.8.0_112",
					"Fri Dec 06 23:50:54 EST 2019"
				),
				new org.drip.service.env.BuildRecord (
					"4.50.0",
					"1.8.0_112",
					"Sat Nov 30 12:38:53 EST 2019"
				),
				new org.drip.service.env.BuildRecord (
					"4.49.0",
					"1.8.0_112",
					"Fri Nov 29 19:05:11 EST 2019"
				),
				new org.drip.service.env.BuildRecord (
					"4.48.0",
					"1.8.0_112",
					"Sat Nov 23 16:10:15 EST 2019"
				),
				new org.drip.service.env.BuildRecord (
					"4.47.0",
					"1.8.0_112",
					"Thu Nov 21 00:28:12 EST 2019"
				),
				new org.drip.service.env.BuildRecord (
					"4.46.0",
					"1.8.0_112",
					"Sun Nov 17 21:56:27 EST 2019"
				),
				new org.drip.service.env.BuildRecord (
					"4.45.0",
					"1.8.0_112",
					"Thu Nov 14 21:13:59 EST 2019"
				),
				new org.drip.service.env.BuildRecord (
					"4.44.0",
					"1.8.0_112",
					"Sun Nov 10 00:24:55 EST 2019"
				),
				new org.drip.service.env.BuildRecord (
					"4.43.0",
					"1.8.0_112",
					"Sat Nov 09 20:11:52 EST 2019"
				),
				new org.drip.service.env.BuildRecord (
					"4.42.0",
					"1.8.0_112",
					"Sun Nov 03 21:55:17 EST 2019"
				),
				new org.drip.service.env.BuildRecord (
					"4.41.0",
					"1.8.0_112",
					"Sat Nov 02 22:26:06 EST 2019"
				),
				new org.drip.service.env.BuildRecord (
					"4.40.0",
					"1.8.0_112",
					"Sun Oct 27 17:37:08 EST 2019"
				),
				new org.drip.service.env.BuildRecord (
					"4.39.0",
					"1.8.0_112",
					"Fri Oct 25 22:16:27 EST 2019"
				),
				new org.drip.service.env.BuildRecord (
					"4.38.0",
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
