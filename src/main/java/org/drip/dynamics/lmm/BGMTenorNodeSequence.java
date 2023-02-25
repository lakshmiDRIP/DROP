
package org.drip.dynamics.lmm;

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
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
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
 * <i>BGMTenorNodeSequence</i> contains the Point Nodes of the Latent State Quantifiers and their Increments
 * present in the specified BGMForwardTenorSnap Instance. The References are:
 *
 *	<br><br>
 *  <ul>
 *  	<li>
 *  		Goldys, B., M. Musiela, and D. Sondermann (1994): <i>Log-normality of Rates and Term Structure
 *  			Models</i> <b>The University of New South Wales</b>
 *  	</li>
 *  	<li>
 *  		Musiela, M. (1994): <i>Nominal Annual Rates and Log-normal Volatility Structure</i> <b>The
 *  			University of New South Wales</b>
 *  	</li>
 *  	<li>
 * 			Brace, A., D. Gatarek, and M. Musiela (1997): The Market Model of Interest Rate Dynamics
 * 				<i>Mathematical Finance</i> <b>7 (2)</b> 127-155
 *  	</li>
 *  </ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/dynamics/README.md">HJM, Hull White, LMM, and SABR Dynamic Evolution Models</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/dynamics/lmm/README.md">LMM Based Latent State Evolution</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class BGMTenorNodeSequence {
	private int[] _aiDate = null;
	private double[] _adblLIBOR = null;
	private double[] _adblLIBORIncrement = null;
	private double[] _adblDiscountFactor = null;
	private double[] _adblSpotRateIncrement = null;
	private double[] _adblDiscountFactorIncrement = null;
	private double[] _adblContinuousForwardRateIncrement = null;
	private double[] _adblInstantaneousNominalForwardRate = null;
	private double[] _adblInstantaneousEffectiveForwardRate = null;

	/**
	 * BGMTenorNodeSequence Constructor
	 * 
	 * @param aBFTS Array of the BGM Forward Tenor Snap Instances
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public BGMTenorNodeSequence (
		final org.drip.dynamics.lmm.BGMForwardTenorSnap[] aBFTS)
		throws java.lang.Exception
	{
		if (null == aBFTS) throw new java.lang.Exception ("BGMTenorNodeSequence ctr: Invalid Inputs!");

		int iNumSnap = aBFTS.length;
		_aiDate = new int[iNumSnap];
		_adblLIBOR = new double[iNumSnap];
		_adblLIBORIncrement = new double[iNumSnap];
		_adblDiscountFactor = new double[iNumSnap];
		_adblSpotRateIncrement = new double[iNumSnap];
		_adblDiscountFactorIncrement = new double[iNumSnap];
		_adblContinuousForwardRateIncrement = new double[iNumSnap];
		_adblInstantaneousNominalForwardRate = new double[iNumSnap];
		_adblInstantaneousEffectiveForwardRate = new double[iNumSnap];

		if (0 == iNumSnap) throw new java.lang.Exception ("BGMTenorNodeSequence ctr: Invalid Inputs!");

		for (int i = 0; i < iNumSnap; ++i) {
			_aiDate[i] = aBFTS[i].date();

			_adblLIBOR[i] = aBFTS[i].libor();

			_adblLIBORIncrement[i] = aBFTS[i].liborIncrement();

			_adblDiscountFactor[i] = aBFTS[i].discountFactor();

			_adblSpotRateIncrement[i] = aBFTS[i].spotRateIncrement();

			_adblDiscountFactorIncrement[i] = aBFTS[i].discountFactorIncrement();

			_adblInstantaneousNominalForwardRate[i] = aBFTS[i].instantaneousNominalForwardRate();

			_adblInstantaneousEffectiveForwardRate[i] = aBFTS[i].instantaneousEffectiveForwardRate();

			_adblContinuousForwardRateIncrement[i] = aBFTS[i].continuouslyCompoundedForwardIncrement();
		}
	}

	/**
	 * Retrieve the Array of Tenor Dates
	 * 
	 * @return The Array of Tenor Dates
	 */

	public int[] dates()
	{
		return _aiDate;
	}

	/**
	 * Retrieve the Array of Tenor LIBOR Rates
	 * 
	 * @return The Array of Tenor LIBOR Rates
	 */

	public double[] liborRates()
	{
		return _adblLIBOR;
	}

	/**
	 * Retrieve the Array of Tenor LIBOR Rate Increments
	 * 
	 * @return The Array of Tenor LIBOR Rate Increments
	 */

	public double[] liborRateIncrements()
	{
		return _adblLIBORIncrement;
	}

	/**
	 * Retrieve the Array of Tenor Discount Factors
	 * 
	 * @return The Array of Tenor Discount Factors
	 */

	public double[] discountFactors()
	{
		return _adblDiscountFactor;
	}

	/**
	 * Retrieve the Array of Tenor Discount Factor Increments
	 * 
	 * @return The Array of Tenor Discount Factor Increments
	 */

	public double[] discountFactorIncrements()
	{
		return _adblDiscountFactorIncrement;
	}

	/**
	 * Retrieve the Array of Tenor Instantaneous Effective Annual Forward Rate
	 * 
	 * @return The Array of Tenor Instantaneous Effective Annual Forward Rate
	 */

	public double[] instantaneousEffectiveForwardRates()
	{
		return _adblInstantaneousEffectiveForwardRate;
	}

	/**
	 * Retrieve the Array of Tenor Instantaneous Nominal Annual Forward Rate
	 * 
	 * @return The Array of Tenor Instantaneous Nominal Annual Forward Rate
	 */

	public double[] instantaneousNominalForwardRates()
	{
		return _adblInstantaneousNominalForwardRate;
	}

	/**
	 * Retrieve the Array of Tenor Instantaneous Continuously Compounded Forward Rate Increments
	 * 
	 * @return The Array of Tenor Instantaneous Continuously Compounded Forward Rate Increments
	 */

	public double[] continuousForwardRateIncrements()
	{
		return _adblContinuousForwardRateIncrement;
	}

	/**
	 * Retrieve the Array of Tenor Spot Rate Increments
	 * 
	 * @return The Array of Tenor Spot Rate Increments
	 */

	public double[] spotRateIncrements()
	{
		return _adblSpotRateIncrement;
	}

	@Override public java.lang.String toString()
	{
		int iNumTenor = _aiDate.length;
		java.lang.String strDateDump = "\t |";
		java.lang.String strPartition = "\t |";
		java.lang.String strLIBORDump = "\t |";
		java.lang.String strLIBORIncrementDump = "\t |";
		java.lang.String strDiscountFactorDump = "\t |";
		java.lang.String strSpotRateIncrementDump = "\t |";
		java.lang.String strDiscountFactorIncrementDump = "\t |";
		java.lang.String strContinuousForwardIncrementDump = "\t |";
		java.lang.String strInstantaneousNominalForwardDump = "\t |";
		java.lang.String strInstantaneousEffectiveForwardDump = "\t |";

		for (int i = 0; i < iNumTenor; ++i) {
			strPartition += "-------------";

			strDateDump += " " + new org.drip.analytics.date.JulianDate (_aiDate[i]) + " |";

			strLIBORDump += "  " + org.drip.service.common.FormatUtil.FormatDouble (_adblLIBOR[i], 1, 3, 100.)
				+ "%   |";

			strLIBORIncrementDump += "    " + org.drip.service.common.FormatUtil.FormatDouble
				(_adblLIBORIncrement[i], 2, 0, 10000.) + "     |";

			strDiscountFactorDump += "  " + org.drip.service.common.FormatUtil.FormatDouble
				(_adblDiscountFactor[i], 2, 3, 100.) + "   |";

			strDiscountFactorIncrementDump += "    " + org.drip.service.common.FormatUtil.FormatDouble
				(_adblDiscountFactorIncrement[i], 2, 0, 10000.) + "     |";

			strContinuousForwardIncrementDump += "    " + org.drip.service.common.FormatUtil.FormatDouble
				(_adblContinuousForwardRateIncrement[i], 2, 0, 10000.) + "     |";

			strSpotRateIncrementDump += "    " + org.drip.service.common.FormatUtil.FormatDouble
				(_adblSpotRateIncrement[i], 2, 0, 10000.) + "     |";

			strInstantaneousEffectiveForwardDump += "    " + org.drip.service.common.FormatUtil.FormatDouble
				(_adblInstantaneousEffectiveForwardRate[i], 2, 0, 10000.) + "     |";

			strInstantaneousNominalForwardDump += "    " + org.drip.service.common.FormatUtil.FormatDouble
				(_adblInstantaneousNominalForwardRate[i], 2, 0, 10000.) + "     |";
		}

		return "\n" + strPartition + "|\n" + strDateDump + "|\n" + strPartition + "|\n" + strLIBORDump +
			"|\n" + strLIBORIncrementDump + "|\n" + strDiscountFactorDump + "|\n" +
				strDiscountFactorIncrementDump + "|\n" + strContinuousForwardIncrementDump + "|\n" +
					strSpotRateIncrementDump + "|\n" + strInstantaneousEffectiveForwardDump + "|\n" +
						strInstantaneousNominalForwardDump + "|\n" + strPartition + "|\n";
	}
}
