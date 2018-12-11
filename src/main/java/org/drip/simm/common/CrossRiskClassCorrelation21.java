
package org.drip.simm.common;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
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
 * <i>CrossRiskClassCorrelation21</i> contains the SIMM 2.1 Correlation between the Different Risk Classes.
 * The References are:
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
 *  		International Swaps and Derivatives Association (2017): SIMM v2.0 Methodology
 *  			https://www.isda.org/a/oFiDE/isda-simm-v2.pdf
 *  	</li>
 *  </ul>
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ExposureAnalyticsLibrary.md">Exposure Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm">SIMM</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/common">Common</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class CrossRiskClassCorrelation21
{

	/**
	 * Correlation between Interest Rate and Credit Qualifying Risk Classes
	 */

	public static final double IR_CRQ = 0.25;

	/**
	 * Correlation between Interest Rate and Credit Non-Qualifying Risk Classes
	 */

	public static final double IR_CRNQ = 0.15;

	/**
	 * Correlation between Interest Rate and Equity Risk Classes
	 */

	public static final double IR_EQ = 0.19;

	/**
	 * Correlation between Interest Rate and Commodity Risk Classes
	 */

	public static final double IR_CT = 0.30;

	/**
	 * Correlation between Interest Rate and FX Risk Classes
	 */

	public static final double IR_FX = 0.26;

	/**
	 * Correlation between Credit Qualifying and Credit Non-Qualifying Risk Classes
	 */

	public static final double CRQ_CRNQ = 0.26;

	/**
	 * Correlation between Credit Qualifying and Equity Risk Classes
	 */

	public static final double CRQ_EQ = 0.65;

	/**
	 * Correlation between Credit Qualifying and Commodity Risk Classes
	 */

	public static final double CRQ_CT = 0.45;

	/**
	 * Correlation between Credit Qualifying and FX Risk Classes
	 */

	public static final double CRQ_FX = 0.24;

	/**
	 * Correlation between Credit Non Qualifying and Equity Risk Classes
	 */

	public static final double CRNQ_EQ = 0.17;

	/**
	 * Correlation between Credit Non Qualifying and Commodity Risk Classes
	 */

	public static final double CRNQ_CT = 0.22;

	/**
	 * Correlation between Credit Non Qualifying and FX Risk Classes
	 */

	public static final double CRNQ_FX = 0.11;

	/**
	 * Correlation between Equity and Commodity Risk Classes
	 */

	public static final double EQ_CT = 0.39;

	/**
	 * Correlation between Equity and FX Risk Classes
	 */

	public static final double EQ_FX = 0.23;

	/**
	 * Correlation between Commodity and FX Risk Classes
	 */

	public static final double CT_FX = 0.32;

	/**
	 * Retrieve the Correlation between Interest Rate and Credit Qualifying Risk Classes
	 * 
	 * @return Correlation between Interest Rate and Credit Qualifying Risk Classes
	 */

	public static final double IR_CRQ()
	{
		return IR_CRQ;
	}

	/**
	 * Retrieve the Correlation between Interest Rate and Credit Qualifying Risk Classes
	 * 
	 * @return Correlation between Interest Rate and Credit Qualifying Risk Classes
	 */

	public static final double CRQ_IR()
	{
		return IR_CRQ;
	}

	/**
	 * Retrieve the Correlation between Interest Rate and Credit Non Qualifying Risk Classes
	 * 
	 * @return Correlation between Interest Rate and Credit Non Qualifying Risk Classes
	 */

	public static final double IR_CRNQ()
	{
		return IR_CRNQ;
	}

	/**
	 * Retrieve the Correlation between Interest Rate and Credit Non Qualifying Risk Classes
	 * 
	 * @return Correlation between Interest Rate and Credit Non Qualifying Risk Classes
	 */

	public static final double CRNQ_IR()
	{
		return IR_CRNQ;
	}

	/**
	 * Retrieve the Correlation between Interest Rate and Equity Risk Classes
	 * 
	 * @return Correlation between Interest Rate and Equity Risk Classes
	 */

	public static final double IR_EQ()
	{
		return IR_EQ;
	}

	/**
	 * Retrieve the Correlation between Interest Rate and Equity Risk Classes
	 * 
	 * @return Correlation between Interest Rate and Equity Risk Classes
	 */

	public static final double EQ_IR()
	{
		return IR_EQ;
	}

	/**
	 * Retrieve the Correlation between Interest Rate and Commodity Risk Classes
	 * 
	 * @return Correlation between Interest Rate and Commodity Risk Classes
	 */

	public static final double IR_CT()
	{
		return IR_CT;
	}

	/**
	 * Retrieve the Correlation between Interest Rate and Commodity Risk Classes
	 * 
	 * @return Correlation between Interest Rate and Commodity Risk Classes
	 */

	public static final double CT_IR()
	{
		return IR_CT;
	}

	/**
	 * Retrieve the Correlation between Interest Rate and FX Risk Classes
	 * 
	 * @return Correlation between Interest Rate and FX Risk Classes
	 */

	public static final double IR_FX()
	{
		return IR_FX;
	}

	/**
	 * Retrieve the Correlation between Interest Rate and FX Risk Classes
	 * 
	 * @return Correlation between Interest Rate and FX Risk Classes
	 */

	public static final double FX_IR()
	{
		return IR_FX;
	}

	/**
	 * Retrieve the Correlation between Credit Qualifying and Credit Non-Qualifying Risk Classes
	 * 
	 * @return Correlation between Credit Qualifying and Credit Non-Qualifying Risk Classes
	 */

	public static final double CRQ_CRNQ()
	{
		return CRQ_CRNQ;
	}

	/**
	 * Retrieve the Correlation between Credit Qualifying and Credit Non-Qualifying Risk Classes
	 * 
	 * @return Correlation between Credit Qualifying and Credit Non-Qualifying Risk Classes
	 */

	public static final double CNRQ_CNQ()
	{
		return CRQ_CRNQ;
	}

	/**
	 * Retrieve the Correlation between Credit Qualifying and Equity Risk Classes
	 * 
	 * @return Correlation between Credit Qualifying and Equity Risk Classes
	 */

	public static final double CRQ_EQ()
	{
		return CRQ_EQ;
	}

	/**
	 * Retrieve the Correlation between Credit Qualifying and Equity Risk Classes
	 * 
	 * @return Correlation between Credit Qualifying and Equity Risk Classes
	 */

	public static final double EQ_CRQ()
	{
		return CRQ_EQ;
	}

	/**
	 * Retrieve the Correlation between Credit Qualifying and Commodity Risk Classes
	 * 
	 * @return Correlation between Credit Qualifying and Commodity Risk Classes
	 */

	public static final double CRQ_CT()
	{
		return CRQ_CT;
	}

	/**
	 * Retrieve the Correlation between Credit Qualifying and Commodity Risk Classes
	 * 
	 * @return Correlation between Credit Qualifying and Commodity Risk Classes
	 */

	public static final double CT_CRQ()
	{
		return CRQ_CT;
	}

	/**
	 * Retrieve the Correlation between Credit Qualifying and FX Risk Classes
	 * 
	 * @return Correlation between Credit Qualifying and FX Risk Classes
	 */

	public static final double CRQ_FX()
	{
		return CRQ_FX;
	}

	/**
	 * Retrieve the Correlation between Credit Qualifying and FX Risk Classes
	 * 
	 * @return Correlation between Credit Qualifying and FX Risk Classes
	 */

	public static final double FX_CRQ()
	{
		return CRQ_FX;
	}

	/**
	 * Retrieve the Correlation between Credit Non Qualifying and Equity Risk Classes
	 * 
	 * @return Correlation between Credit Non Qualifying and Equity Risk Classes
	 */

	public static final double CNRQ_EQ()
	{
		return CRNQ_EQ;
	}

	/**
	 * Retrieve the Correlation between Credit Non Qualifying and Equity Risk Classes
	 * 
	 * @return Correlation between Credit Non Qualifying and Equity Risk Classes
	 */

	public static final double EQ_CNRQ()
	{
		return CRNQ_EQ;
	}

	/**
	 * Retrieve the Correlation between Credit Non Qualifying and Commodity Risk Classes
	 * 
	 * @return Correlation between Credit Non Qualifying and Commodity Risk Classes
	 */

	public static final double CNRQ_CT()
	{
		return CRNQ_CT;
	}

	/**
	 * Retrieve the Correlation between Credit Non Qualifying and Commodity Risk Classes
	 * 
	 * @return Correlation between Credit Non Qualifying and Commodity Risk Classes
	 */

	public static final double CT_CNRQ()
	{
		return CRNQ_CT;
	}

	/**
	 * Retrieve the Correlation between Credit Non Qualifying and FX Risk Classes
	 * 
	 * @return Correlation between Credit Non Qualifying and FX Risk Classes
	 */

	public static final double CreditNonQualifying_FX()
	{
		return CRNQ_FX;
	}

	/**
	 * Retrieve the Correlation between Credit Non Qualifying and FX Risk Classes
	 * 
	 * @return Correlation between Credit Non Qualifying and FX Risk Classes
	 */

	public static final double FX_CNRQ()
	{
		return CRNQ_FX;
	}

	/**
	 * Retrieve the Correlation between Equity and Commodity Risk Classes
	 * 
	 * @return Correlation between Equity and Commodity Risk Classes
	 */

	public static final double EQ_CT()
	{
		return EQ_CT;
	}

	/**
	 * Retrieve the Correlation between Equity and Commodity Risk Classes
	 * 
	 * @return Correlation between Equity and Commodity Risk Classes
	 */

	public static final double CT_EQ()
	{
		return EQ_CT;
	}

	/**
	 * Retrieve the Correlation between Equity and FX Risk Classes
	 * 
	 * @return Correlation between Equity and FX Risk Classes
	 */

	public static final double EQ_FX()
	{
		return EQ_FX;
	}

	/**
	 * Retrieve the Correlation between Equity and FX Risk Classes
	 * 
	 * @return Correlation between Equity and FX Risk Classes
	 */

	public static final double FX_EQ()
	{
		return EQ_FX;
	}

	/**
	 * Retrieve the Correlation between Commodity and FX Risk Classes
	 * 
	 * @return Correlation between Commodity and FX Risk Classes
	 */

	public static final double CT_FX()
	{
		return CT_FX;
	}

	/**
	 * Retrieve the Correlation between Commodity and FX Risk Classes
	 * 
	 * @return Correlation between Commodity and FX Risk Classes
	 */

	public static final double FX_CT()
	{
		return CT_FX;
	}

	/**
	 * Generate the Corresponding Risk Class Correlation Matrix as a LabelCorrelation Instance
	 * 
	 * @return The Risk Class Correlation Matrix
	 */

	public static final org.drip.measure.stochastic.LabelCorrelation Matrix()
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

		java.util.List<java.lang.String> chargramList = new java.util.ArrayList<java.lang.String>();

		chargramList.add (org.drip.simm.common.Chargram.IR);

		chargramList.add (org.drip.simm.common.Chargram.CRQ);

		chargramList.add (org.drip.simm.common.Chargram.CRNQ);

		chargramList.add (org.drip.simm.common.Chargram.EQ);

		chargramList.add (org.drip.simm.common.Chargram.CT);

		chargramList.add (org.drip.simm.common.Chargram.FX);

		try
		{
			return new org.drip.measure.stochastic.LabelCorrelation (
				chargramList,
				riskClassCorrelationMatrix
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
