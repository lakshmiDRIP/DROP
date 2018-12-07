
package org.drip.portfolioconstruction.optimizer;

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
 * <i>ObjectiveFunction</i> holds the Terms composing the Objective Function and their Weights.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AssetAllocationAnalyticsLibrary.md">Asset Allocation Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction">Portfolio Construction</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/strategy">Strategy</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class ObjectiveFunction extends org.drip.function.definition.RdToR1 {
	private int _iDimension = -1;

	private java.util.List<org.drip.portfolioconstruction.optimizer.ObjectiveTermUnit> _lsOTU = new
		java.util.ArrayList<org.drip.portfolioconstruction.optimizer.ObjectiveTermUnit>();

	/**
	 * Empty Objective Function Constructor
	 */

	public ObjectiveFunction()
	{
		super (null);
	}

	/**
	 * Add the Objective Term Unit Instance
	 * 
	 * @param otu The Objective Term Unit Instance
	 * 
	 * @return TRUE - The Objective Term Unit Instance successfully added
	 */

	public boolean addObjectiveTermUnit (
		final org.drip.portfolioconstruction.optimizer.ObjectiveTermUnit otu)
	{
		if (null == otu || _lsOTU.contains (otu)) return false;

		_lsOTU.add (otu);

		return true;
	}

	/**
	 * Retrieve the List of Objective Terms
	 * 
	 * @return The List of Objective Terms
	 */

	public java.util.List<org.drip.portfolioconstruction.optimizer.ObjectiveTermUnit> list()
	{
		return _lsOTU;
	}

	@Override public int dimension()
	{
		return _iDimension;
	}

	@Override public double evaluate (
		final double[] adblVariate)
		throws java.lang.Exception
	{
		double dblValue = 0.;

		for (org.drip.portfolioconstruction.optimizer.ObjectiveTermUnit otu : _lsOTU) {
			if (otu.isActive()) dblValue += otu.weight() * otu.term().rdtoR1().evaluate (adblVariate);
		}

		return dblValue;
	}

	@Override public double derivative (
		final double[] adblVariate,
		final int iVariateIndex,
		final int iOrder)
		throws java.lang.Exception
	{
		double dblValue = 0.;

		for (org.drip.portfolioconstruction.optimizer.ObjectiveTermUnit otu : _lsOTU) {
			if (otu.isActive())
				dblValue += otu.weight() * otu.term().rdtoR1().derivative (
					adblVariate,
					iVariateIndex,
					iOrder
				);
		}

		return dblValue;
	}
}
