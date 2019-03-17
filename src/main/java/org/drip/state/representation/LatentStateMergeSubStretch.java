
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
 * <i>LatentStateMergeSubStretch</i> implements merged stretch that is common to multiple latent states. It
 * is identified by the start/end date predictor ordinates, and the Latent State Label. Its methods provide
 * the following functionality:
 *
 *  <br><br>
 *  <ul>
 *  	<li>
 *  		Identify if the specified predictor ordinate belongs to the sub stretch
 *  	</li>
 *  	<li>
 *  		Shift that sub stretch start/end
 *  	</li>
 *  	<li>
 *  		Identify if the this overlaps the supplied sub stretch, and coalesce them if possible
 *  	</li>
 *  	<li>
 *  		Retrieve the label, start, and end
 *  	</li>
 *  </ul>
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

public class LatentStateMergeSubStretch {
	private double _dblEndDate = java.lang.Double.NaN;
	private double _dblStartDate = java.lang.Double.NaN;
	private org.drip.state.identifier.LatentStateLabel _lsl = null;

	/**
	 * LatentStateMergeSubStretch constructor
	 * 
	 * @param dblStartDate Merge Stretch Start Date
	 * @param dblEndDate Merge Stretch End Date
	 * @param lsl The Latent State Label
	 * 
	 * @throws java.lang.Exception Thrown if Inputs are invalid
	 */

	public LatentStateMergeSubStretch (
		final double dblStartDate,
		final double dblEndDate,
		final org.drip.state.identifier.LatentStateLabel lsl)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (_dblStartDate = dblStartDate) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_dblEndDate = dblEndDate) || _dblStartDate >=
				_dblEndDate || null == (_lsl = lsl))
			throw new java.lang.Exception ("LatentStateMergeSubStretch ctr: Invalid Inputs");
	}

	/**
	 * Retrieve the Merge Stretch Start Date
	 * 
	 * @return The Merge Stretch Start Date
	 */

	public double start()
	{
		return _dblStartDate;
	}

	/**
	 * Shift/Adjust the Start Date
	 * 
	 * @param dblNewStartDate The new Date to be Shifted to
	 * 
	 * @return TRUE - Start Date successfully shifted
	 */

	public boolean shiftStart (
		final double dblNewStartDate)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblNewStartDate)) return false;

		_dblStartDate = dblNewStartDate;
		return true;
	}

	/**
	 * Retrieve the Merge Stretch End Date
	 * 
	 * @return The Merge Stretch End Date
	 */

	public double end()
	{
		return _dblEndDate;
	}

	/**
	 * Shift/Adjust the End Date
	 * 
	 * @param dblNewEndDate The new Date to be Shifted to
	 * 
	 * @return TRUE - End Date successfully shifted
	 */

	public boolean shiftEnd (
		final double dblNewEndDate)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblNewEndDate)) return false;

		_dblEndDate = dblNewEndDate;
		return true;
	}

	/**
	 * Retrieve the Latent State Label
	 * 
	 * @return The Latent State Label
	 */

	public org.drip.state.identifier.LatentStateLabel label()
	{
		return _lsl;
	}

	/**
	 * Indicate whether Specified Merge Stretch's Label matches with the current one
	 * 
	 * @param lsmsOther The Supplied Merge Stretch
	 * 
	 * @return TRUE - Merge Stretches Index Match
	 */

	public boolean indexMatch (
		final LatentStateMergeSubStretch lsmsOther)
	{
		return null != _lsl && null != lsmsOther && lsmsOther.label().match (label());
	}

	/**
	 * Indicate whether the specified Date is "inside" the Stretch Range.
	 * 
	 * @param dblDate Date whose "inside"ness is asked for
	 * 
	 * @return TRUE - Date is Inside
	 */

	public boolean in (
		final double dblDate)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblDate)) return false;

		return dblDate >= _dblStartDate && dblDate <= _dblEndDate;
	}

	/**
	 * Identify if the Supplied Merge Stretch overlaps with the provided one.
	 * 
	 * @param lsmlOther The Supplied Merge Stretch
	 * 
	 * @return TRUE - Merge Stretches Overlap
	 */

	public boolean overlap (
		final LatentStateMergeSubStretch lsmlOther)
	{
		return null == lsmlOther || lsmlOther.start() >= end() || lsmlOther.end() <= start() ? false : true;
	}

	/**
	 * Coalesce the supplied Merge Stretch with the current one (if possible) to create a new Merge Stretch
	 * 
	 * @param lsmlOther The Supplied Merge Stretch
	 * 
	 * @return The Coalesced Merge Stretch
	 */

	public LatentStateMergeSubStretch coalesce (
		final LatentStateMergeSubStretch lsmlOther)
	{
		if (!overlap (lsmlOther) || !indexMatch (lsmlOther)) return null;

		try {
			return new LatentStateMergeSubStretch (start() < lsmlOther.start() ? start() :
				lsmlOther.start(), end() > lsmlOther.end() ? end() : lsmlOther.end(), label());
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
