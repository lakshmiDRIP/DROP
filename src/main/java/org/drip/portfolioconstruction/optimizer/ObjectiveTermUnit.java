
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
 * <i>ObjectiveTermUnit</i> holds the Details of a Single Objective Term that forms the Strategy.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module</b>        = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction">Portfolio Construction</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/optimizer">Optimizer</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/AssetAllocation">Asset Allocation Library</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class ObjectiveTermUnit {
	private boolean _bIsActive = false;
	private double _dblWeight = java.lang.Double.NaN;
	private org.drip.portfolioconstruction.optimizer.ObjectiveTerm _objTerm = null;

	/**
	 * ObjectiveTermUnit Constructor
	 * 
	 * @param objTerm The Objective Term
	 * @param dblWeight The Objective Term Weight
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public ObjectiveTermUnit (
		final org.drip.portfolioconstruction.optimizer.ObjectiveTerm objTerm,
		final double dblWeight)
		throws java.lang.Exception
	{
		if (null == (_objTerm = objTerm) || !org.drip.quant.common.NumberUtil.IsValid (_dblWeight =
			dblWeight))
			throw new java.lang.Exception ("ObjectiveTermUnit Constructor => Invalid Inputs");

		_bIsActive = true;
	}

	/**
	 * Indicate if the Objective Term is Active
	 * 
	 * @return TRUE - The Objective Term is Active
	 */

	public boolean isActive()
	{
		return _bIsActive;
	}

	/**
	 * Turn ON the Objective Term Unit
	 * 
	 * @return The Objective Term Unit is ON
	 */

	public boolean activate()
	{
		boolean bIsActiveOld = _bIsActive;
		_bIsActive = true;
		return bIsActiveOld;
	}

	/**
	 * Turn OFF the Objective Term Unit
	 * 
	 * @return The Objective Term Unit is OFF
	 */

	public boolean deactivate()
	{
		boolean bIsActiveOld = _bIsActive;
		_bIsActive = false;
		return bIsActiveOld;
	}

	/**
	 * Weight of the Objective Term
	 * 
	 * @return Weight of the Objective Term
	 */

	public double weight()
	{
		return _dblWeight;
	}

	/**
	 * Retrieve the Objective Term
	 * 
	 * @return TRUE - The Objective Term
	 */

	public org.drip.portfolioconstruction.optimizer.ObjectiveTerm term()
	{
		return _objTerm;
	}
}
