
package org.drip.state.discount;

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
 * <i>TurnListDiscountFactor</i> implements the discounting based off of the turns list. Its functions add a
 * turn instance to the current set, and concurrently apply the discount factor inside the range to each
 * relevant turn.
 *
 *  <br><br>
 *  <ul>
 *		<li><b>Project</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state">State</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/discount">Discount</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/FixedIncome">Fixed Income Analytics Library</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class TurnListDiscountFactor {
	private java.util.List<org.drip.analytics.definition.Turn> _lsTurn = null;

	/**
	 * Empty TurnListDiscountFactor constructor
	 */

	public TurnListDiscountFactor()
	{
	}

	/**
	 * Add a Turn Instance to the Discount Curve
	 * 
	 * @param turn The Turn Instance to be added
	 * 
	 * @return TRUE - Successfully added
	 */

	public boolean addTurn (
		final org.drip.analytics.definition.Turn turn)
	{
		if (null == turn) return false;

		if (null == _lsTurn) _lsTurn = new java.util.ArrayList<org.drip.analytics.definition.Turn>();

		_lsTurn.add (turn);

		return true;
	}

	/**
	 * Apply the Turns' DF Adjustment
	 * 
	 * @param iStartDate Turn Start Date
	 * @param iFinishDate Turn Finish Date
	 * 
	 * @return Turns' DF Adjustment
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are invalid
	 */

	public double turnAdjust (
		final int iStartDate,
		final int iFinishDate)
		throws java.lang.Exception
	{
		if (null == _lsTurn || 0 == _lsTurn.size()) return 1.;

		if (iStartDate >= iFinishDate) return 1.;

		double dblTurnAdjust = 1.;

		for (org.drip.analytics.definition.Turn turn : _lsTurn) {
			if (null == turn || iStartDate >= turn.finishDate() || iFinishDate <= turn.startDate()) continue;

			int iEffectiveStart = turn.startDate() > iStartDate ? turn.startDate() : iStartDate;

			int iEffectiveFinish = turn.finishDate() < iFinishDate ? turn.finishDate() : iFinishDate;

			dblTurnAdjust *= java.lang.Math.exp (turn.spread() * (iEffectiveStart - iEffectiveFinish) /
				365.25);
		}

		return dblTurnAdjust;
	}
}
