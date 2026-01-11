
package org.drip.simm.common;

import java.util.ArrayList;
import java.util.List;

import org.drip.measure.state.LabelledRdCorrelation;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2025 Lakshmi Krishnamurthy
 * Copyright (C) 2024 Lakshmi Krishnamurthy
 * Copyright (C) 2023 Lakshmi Krishnamurthy
 * Copyright (C) 2022 Lakshmi Krishnamurthy
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
 * <i>CrossRiskClassCorrelation24</i> contains the SIMM 2.4 Correlation between the Different Risk Classes.
 * 	The References are:
 * 
 * <br><br>
 *  <ul>
 *  	<li>
 *  		Andersen, L. B. G., M. Pykhtin, and A. Sokol (2017): Credit Exposure in the Presence of Initial
 *  			Margin https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2806156 <b>eSSRN</b>
 *  	</li>
 *  	<li>
 *  		Albanese, C., S. Caenazzo, and O. Frankel (2017): Regression Sensitivities for Initial Margin
 *  			Calculations https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2763488 <b>eSSRN</b>
 *  	</li>
 *  	<li>
 *  		Anfuso, F., D. Aziz, P. Giltinan, and K. Loukopoulus (2017): A Sound Modeling and Back-testing
 *  			Framework for Forecasting Initial Margin Requirements
 *  				https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2716279 <b>eSSRN</b>
 *  	</li>
 *  	<li>
 *  		Caspers, P., P. Giltinan, R. Lichters, and N. Nowaczyk (2017): Forecasting Initial Margin
 *  			Requirements - A Model Evaluation https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2911167
 *  				<b>eSSRN</b>
 *  	</li>
 *  	<li>
 *  		International Swaps and Derivatives Association (2021): SIMM v2.4 Methodology
 *  			https://www.isda.org/a/CeggE/ISDA-SIMM-v2.4-PUBLIC.pdf
 *  	</li>
 *  </ul>
 *
 * 	It provides the following Functionality:
 *
 *  <ul>
 * 		<li>Correlation between Interest Rate and Credit Qualifying Risk Classes</li>
 * 		<li>Correlation between Interest Rate and Credit Non-Qualifying Risk Classes</li>
 * 		<li>Correlation between Interest Rate and Equity Risk Classes</li>
 * 		<li>Correlation between Interest Rate and Commodity Risk Classes</li>
 * 		<li>Correlation between Interest Rate and FX Risk Classes</li>
 * 		<li>Correlation between Credit Qualifying and Credit Non-Qualifying Risk Classes</li>
 * 		<li>Correlation between Credit Qualifying and Equity Risk Classes</li>
 * 		<li>Correlation between Credit Qualifying and Commodity Risk Classes</li>
 * 		<li>Correlation between Credit Qualifying and FX Risk Classes</li>
 * 		<li>Correlation between Credit Non Qualifying and Equity Risk Classes</li>
 * 		<li>Correlation between Credit Non Qualifying and Commodity Risk Classes</li>
 * 		<li>Correlation between Credit Non Qualifying and FX Risk Classes</li>
 * 		<li>Correlation between Equity and Commodity Risk Classes</li>
 * 		<li>Correlation between Equity and FX Risk Classes</li>
 * 		<li>Correlation between Commodity and FX Risk Classes</li>
 * 		<li>Generate the Corresponding Risk Class Correlation Matrix as a <code>LabelCorrelation</code> Instance</li>
 *  </ul>
 *
 *	<br>
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/MarginAnalyticsLibrary.md">Initial and Variation Margin Analytics</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/README.md">Initial Margin Analytics based on ISDA SIMM and its Variants</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/common/README.md">Common Cross Risk Factor Utilities</a></td></tr>
 *  </table>
 *	<br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class CrossRiskClassCorrelation24
{

	/**
	 * Correlation between Interest Rate and Credit Qualifying Risk Classes
	 */

	public static final double IR_CRQ = 0.32;

	/**
	 * Correlation between Interest Rate and Credit Non-Qualifying Risk Classes
	 */

	public static final double IR_CRNQ = 0.19;

	/**
	 * Correlation between Interest Rate and Equity Risk Classes
	 */

	public static final double IR_EQ = 0.33;

	/**
	 * Correlation between Interest Rate and Commodity Risk Classes
	 */

	public static final double IR_CT = 0.41;

	/**
	 * Correlation between Interest Rate and FX Risk Classes
	 */

	public static final double IR_FX = 0.28;

	/**
	 * Correlation between Credit Qualifying and Credit Non-Qualifying Risk Classes
	 */

	public static final double CRQ_CRNQ = 0.45;

	/**
	 * Correlation between Credit Qualifying and Equity Risk Classes
	 */

	public static final double CRQ_EQ = 0.69;

	/**
	 * Correlation between Credit Qualifying and Commodity Risk Classes
	 */

	public static final double CRQ_CT = 0.52;

	/**
	 * Correlation between Credit Qualifying and FX Risk Classes
	 */

	public static final double CRQ_FX = 0.42;

	/**
	 * Correlation between Credit Non Qualifying and Equity Risk Classes
	 */

	public static final double CRNQ_EQ = 0.48;

	/**
	 * Correlation between Credit Non Qualifying and Commodity Risk Classes
	 */

	public static final double CRNQ_CT = 0.40;

	/**
	 * Correlation between Credit Non Qualifying and FX Risk Classes
	 */

	public static final double CRNQ_FX = 0.14;

	/**
	 * Correlation between Equity and Commodity Risk Classes
	 */

	public static final double EQ_CT = 0.52;

	/**
	 * Correlation between Equity and FX Risk Classes
	 */

	public static final double EQ_FX = 0.34;

	/**
	 * Correlation between Commodity and FX Risk Classes
	 */

	public static final double CT_FX = 0.38;

	/**
	 * Generate the Corresponding Risk Class Correlation Matrix as a LabelCorrelation Instance
	 * 
	 * @return The Risk Class Correlation Matrix
	 */

	public static final LabelledRdCorrelation Matrix()
	{
		double[][] riskClassCorrelationMatrix = new double[6][6];

		for (int i = 0 ; i < 6; ++i)
		{
			riskClassCorrelationMatrix[i][i] = 1.;
		}

		riskClassCorrelationMatrix[0][1] = IR_CRQ;
		riskClassCorrelationMatrix[1][0] = IR_CRQ;
		riskClassCorrelationMatrix[0][2] = IR_CRNQ;
		riskClassCorrelationMatrix[2][0] = IR_CRNQ;
		riskClassCorrelationMatrix[0][3] = IR_EQ;
		riskClassCorrelationMatrix[3][0] = IR_EQ;
		riskClassCorrelationMatrix[0][4] = IR_CT;
		riskClassCorrelationMatrix[4][0] = IR_CT;
		riskClassCorrelationMatrix[0][5] = IR_FX;
		riskClassCorrelationMatrix[5][0] = IR_FX;

		riskClassCorrelationMatrix[1][2] = CRQ_CRNQ;
		riskClassCorrelationMatrix[2][1] = CRQ_CRNQ;
		riskClassCorrelationMatrix[1][3] = CRQ_EQ;
		riskClassCorrelationMatrix[3][1] = CRQ_EQ;
		riskClassCorrelationMatrix[1][4] = CRQ_CT;
		riskClassCorrelationMatrix[4][1] = CRQ_CT;
		riskClassCorrelationMatrix[1][5] = CRQ_FX;
		riskClassCorrelationMatrix[5][1] = CRQ_FX;

		riskClassCorrelationMatrix[2][3] = CRNQ_EQ;
		riskClassCorrelationMatrix[3][2] = CRNQ_EQ;
		riskClassCorrelationMatrix[2][4] = CRNQ_CT;
		riskClassCorrelationMatrix[4][2] = CRNQ_CT;
		riskClassCorrelationMatrix[2][5] = CRNQ_FX;
		riskClassCorrelationMatrix[5][2] = CRNQ_FX;

		riskClassCorrelationMatrix[3][4] = EQ_CT;
		riskClassCorrelationMatrix[4][3] = EQ_CT;
		riskClassCorrelationMatrix[3][5] = EQ_FX;
		riskClassCorrelationMatrix[5][3] = EQ_FX;

		riskClassCorrelationMatrix[4][5] = CT_FX;
		riskClassCorrelationMatrix[5][4] = CT_FX;

		List<String> chargramList = new ArrayList<String>();

		chargramList.add (
			Chargram.IR
		);

		chargramList.add (
			Chargram.CRQ
		);

		chargramList.add (
			Chargram.CRNQ
		);

		chargramList.add (
			Chargram.EQ
		);

		chargramList.add (
			Chargram.CT
		);

		chargramList.add (
			Chargram.FX
		);

		try
		{
			return new LabelledRdCorrelation (
				chargramList,
				riskClassCorrelationMatrix
			);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
