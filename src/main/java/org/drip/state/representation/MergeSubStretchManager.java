
package org.drip.state.representation;

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
 * <i>MergeSubStretchManager</i> manages the different discount-forward merge stretches. It provides
 * functionality to create, expand, or contract the merge stretches.
 *
 *  <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state">State</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/representation">Representation</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class MergeSubStretchManager {
	private java.util.List<org.drip.state.representation.LatentStateMergeSubStretch> _lsLSMS = null;

	/**
	 * Empty MergeSubStretchManager constructor
	 */

	public MergeSubStretchManager()
	{
		_lsLSMS = new java.util.ArrayList<org.drip.state.representation.LatentStateMergeSubStretch>();
	}

	/**
	 * Indicates whether the specified Latent State Label is Part of the Merge Stretch
	 * 
	 * @param dblDate The  Date Node
	 * @param lsl The Latent State Label
	 * 
	 * @return TRUE - The specified Latent State Label is Part of the Merge Stretch
	 */

	public boolean partOfMergeState (
		final double dblDate,
		final org.drip.state.identifier.LatentStateLabel lsl)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblDate) || null == lsl || null == _lsLSMS)
			return false;

		for (org.drip.state.representation.LatentStateMergeSubStretch lsms : _lsLSMS) {
			if (null != lsms && lsms.in (dblDate) && lsms.label().match (lsl)) return true;
		}

		return false;
	}

	/**
	 * Add the Specified Merge Stretch
	 * 
	 * @param lsms The Merge Stretch
	 * 
	 * @return TRUE - Successfully added
	 */

	public boolean addMergeStretch (
		final org.drip.state.representation.LatentStateMergeSubStretch lsms)
	{
		if (null == lsms) return false;

		for (org.drip.state.representation.LatentStateMergeSubStretch lsmsConstituent : _lsLSMS) {
			if (null == lsmsConstituent) continue;

			org.drip.state.representation.LatentStateMergeSubStretch lsmsCoalesced = lsmsConstituent.coalesce
				(lsms);

			if (null == lsmsCoalesced) continue;

			_lsLSMS.remove (lsmsConstituent);

			_lsLSMS.add (lsmsCoalesced);

			return true;
		}

		_lsLSMS.add (lsms);

		return true;
	}
}
