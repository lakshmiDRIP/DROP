
package org.drip.portfolioconstruction.composite;

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
 * <i>Benchmark</i> holds the Details of a given Benchmark.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AssetAllocationAnalyticsLibrary.md">Asset Allocation Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction">Portfolio Construction</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/composite">Composite</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class Benchmark
	extends org.drip.portfolioconstruction.core.Block
{
	private java.lang.String _type = "";
	private java.lang.String _category = "";
	private org.drip.portfolioconstruction.composite.Holdings _holdings = null;

	/**
	 * Construct a Standard Benchmark Instance Without Cash
	 * 
	 * @param name The Benchmark Name
	 * @param type The Benchmark Type
	 * @param category The Benchmark Category
	 * @param holdings The Benchmark Holdings
	 * 
	 * @return The Standard Benchmark Instance Without Cash
	 */

	public static final Benchmark Standard (
		final java.lang.String name,
		final java.lang.String type,
		final java.lang.String category,
		final org.drip.portfolioconstruction.composite.Holdings holdings)
	{
		try
		{
			return new Benchmark (
				name,
				name,
				name,
				type,
				category,
				holdings
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Benchmark Constructor
	 * 
	 * @param name The Benchmark Name
	 * @param id The Benchmark ID
	 * @param description The Benchmark Description
	 * @param type The Benchmark Type
	 * @param category The Benchmark Category
	 * @param holdings The Benchmark Holdings
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public Benchmark (
		final java.lang.String name,
		final java.lang.String id,
		final java.lang.String description,
		final java.lang.String type,
		final java.lang.String category,
		final org.drip.portfolioconstruction.composite.Holdings holdings)
		throws java.lang.Exception
	{
		super (
			name,
			id,
			description
		);

		if (null == (_type = type) || _type.isEmpty() ||
			null == (_category = category) || _category.isEmpty() ||
			null == (_holdings = holdings))
		{
			throw new java.lang.Exception ("Benchmark Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Benchmark Type
	 * 
	 * @return The Benchmark Type
	 */

	public java.lang.String type()
	{
		return _type;
	}

	/**
	 * Retrieve the Benchmark Category
	 * 
	 * @return The Benchmark Category
	 */

	public java.lang.String category()
	{
		return _category;
	}

	/**
	 * Retrieve the Benchmark Holdings
	 * 
	 * @return The Benchmark Holdings
	 */

	public org.drip.portfolioconstruction.composite.Holdings holdings()
	{
		return _holdings;
	}
}
