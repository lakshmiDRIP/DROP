
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
 * <i>BuildRecord</i> records the Build Log - DROP Version, Java Version, and Build Time Stamp.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Project</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service">Service</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/env">Env</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/NumericalOptimizer">Numerical Optimizer Library</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class BuildRecord
{
	private java.lang.String _strTimeStamp = "";
	private java.lang.String _strDRIPVersion = "";
	private java.lang.String _strJavaVersion = "";

	/**
	 * BuildRecord Constructor
	 * 
	 * @param strDRIPVersion The DRIP Build Version
	 * @param strJavaVersion The Java Build Version
	 * @param strTimeStamp The DRIP Build Time Stamp
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public BuildRecord (
		final java.lang.String strDRIPVersion,
		final java.lang.String strJavaVersion,
		final java.lang.String strTimeStamp)
		throws java.lang.Exception
	{
		if (null == (_strDRIPVersion = strDRIPVersion) || _strDRIPVersion.isEmpty() ||
			null == (_strJavaVersion = strJavaVersion) || _strJavaVersion.isEmpty() ||
			null == (_strTimeStamp = strTimeStamp) || _strTimeStamp.isEmpty())
			throw new java.lang.Exception ("BuildRecord Constructor => Invalid Inputs");
	}

	/**
	 * Retrieve the DRIP Build Version
	 * 
	 * @return The DRIP Build Version
	 */

	public java.lang.String dripVersion()
	{
		return _strDRIPVersion;
	}

	/**
	 * Retrieve the Java Build Version
	 * 
	 * @return The Java Build Version
	 */

	public java.lang.String javaVersion()
	{
		return _strJavaVersion;
	}

	/**
	 * Retrieve the Build Time Stamp
	 * 
	 * @return The Build Time Stamp
	 */

	public java.lang.String timeStamp()
	{
		return _strTimeStamp;
	}
}
