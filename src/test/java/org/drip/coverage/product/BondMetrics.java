
package org.drip.coverage.product;

import org.drip.sample.bondmetrics.Agartala;
import org.drip.sample.bondmetrics.Ahmedabad;
import org.drip.sample.bondmetrics.Ajmer;
import org.drip.sample.bondmetrics.Akola;
import org.drip.sample.bondmetrics.Ambattur;
import org.drip.sample.bondmetrics.Asansol;
import org.drip.sample.bondmetrics.Belgaum;
import org.drip.sample.bondmetrics.Bellary;
import org.drip.sample.bondmetrics.Bengaluru;
import org.drip.sample.bondmetrics.Bhagalpur;
import org.drip.sample.bondmetrics.Bhatpara;
import org.drip.sample.bondmetrics.Bhilai;
import org.drip.sample.bondmetrics.Bokaro;
import org.drip.sample.bondmetrics.Parbhani;
import org.drip.sample.bondmetrics.Chennai;
import org.drip.sample.bondmetrics.Coimbatore;
import org.drip.sample.bondmetrics.Delhi;
import org.drip.sample.bondmetrics.Dumdum;
import org.drip.sample.bondmetrics.Durgapur;
import org.drip.sample.bondmetrics.Erode;
import org.drip.sample.bondmetrics.Tumkur;
import org.drip.sample.bondmetrics.Gaya;
import org.drip.sample.bondmetrics.Goa;
import org.drip.sample.bondmetrics.Gopalpur;
import org.drip.sample.bondmetrics.Gulbarga;
import org.drip.sample.bondmetrics.Hyderabad;
import org.drip.sample.bondmetrics.Jaipur;
import org.drip.sample.bondmetrics.Jalgaon;
import org.drip.sample.bondmetrics.Jammu;
import org.drip.sample.bondmetrics.Jamnagar;
import org.drip.sample.bondmetrics.Jhansi;
import org.drip.sample.bondmetrics.Jullundar;
import org.drip.sample.bondmetrics.Kochi;
import org.drip.sample.bondmetrics.Kolhapur;
import org.drip.sample.bondmetrics.Kolkata;
import org.drip.sample.bondmetrics.Kottayam;
import org.drip.sample.bondmetrics.Latur;
import org.drip.sample.bondmetrics.Loni;
import org.drip.sample.bondmetrics.Lucknow;
import org.drip.sample.bondmetrics.Ludhiana;
import org.drip.sample.bondmetrics.Madurai;
import org.drip.sample.bondmetrics.Malegaon;
import org.drip.sample.bondmetrics.Mangalore;
import org.drip.sample.bondmetrics.Mumbai;
import org.drip.sample.bondmetrics.Muzaffarnagar;
import org.drip.sample.bondmetrics.Muzaffarpur;
import org.drip.sample.bondmetrics.Nanded;
import org.drip.sample.bondmetrics.Noida;
import org.drip.sample.bondmetrics.Panihati;
import org.drip.sample.bondmetrics.Patiala;
import org.drip.sample.bondmetrics.Puducherry;
import org.drip.sample.bondmetrics.Pune;
import org.drip.sample.bondmetrics.Rajahmundry;
import org.drip.sample.bondmetrics.Rajkot;
import org.drip.sample.bondmetrics.RajpurSonarpur;
// import org.drip.sample.bondmetrics.Reconciler_Call;
// import org.drip.sample.bondmetrics.Reconciler_Fixed;
// import org.drip.sample.bondmetrics.Reconciler_Float;
// import org.drip.sample.bondmetrics.Reconciler_Sink;
import org.drip.sample.bondmetrics.Rourkela;
import org.drip.sample.bondmetrics.SangliMirajKhupwad;
import org.drip.sample.bondmetrics.Siliguri;
import org.drip.sample.bondmetrics.Thane;
import org.drip.sample.bondmetrics.Thiruvananthapuram;
import org.drip.sample.bondmetrics.Tirunelveli;
import org.drip.sample.bondmetrics.Udaipur;
import org.drip.sample.bondmetrics.Ujjain;
import org.drip.sample.bondmetrics.Ulhasnagar;

import org.junit.Test;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * 
 *  This file is part of DRIP, a free-software/open-source library for buy/side financial/trading model
 *  	libraries targeting analysts and developers
 *  	https://lakshmidrip.github.io/DRIP/
 *  
 *  DRIP is composed of four main libraries:
 *  
 *  - DRIP Fixed Income - https://lakshmidrip.github.io/DRIP-Fixed-Income/
 *  - DRIP Asset Allocation - https://lakshmidrip.github.io/DRIP-Asset-Allocation/
 *  - DRIP Numerical Optimizer - https://lakshmidrip.github.io/DRIP-Numerical-Optimizer/
 *  - DRIP Statistical Learning - https://lakshmidrip.github.io/DRIP-Statistical-Learning/
 * 
 *  - DRIP Fixed Income: Library for Instrument/Trading Conventions, Treasury Futures/Options,
 *  	Funding/Forward/Overnight Curves, Multi-Curve Construction/Valuation, Collateral Valuation and XVA
 *  	Metric Generation, Calibration and Hedge Attributions, Statistical Curve Construction, Bond RV
 *  	Metrics, Stochastic Evolution and Option Pricing, Interest Rate Dynamics and Option Pricing, LMM
 *  	Extensions/Calibrations/Greeks, Algorithmic Differentiation, and Asset Backed Models and Analytics.
 * 
 *  - DRIP Asset Allocation: Library for model libraries for MPT framework, Black Litterman Strategy
 *  	Incorporator, Holdings Constraint, and Transaction Costs.
 * 
 *  - DRIP Numerical Optimizer: Library for Numerical Optimization and Spline Functionality.
 * 
 *  - DRIP Statistical Learning: Library for Statistical Evaluation and Machine Learning.
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
 * BondMetrics holds the JUnit Code Coverage Tests for the Bond Metrics for the Product Module.
 *
 * @author Lakshmi Krishnamurthy
 */

public class BondMetrics
{
	@Test public void codeCoverageTest() throws Exception
	{
		Agartala.main (null);

		Ahmedabad.main (null);

		Ajmer.main (null);

		Akola.main (null);

		Ambattur.main (null);

		Asansol.main (null);

		Ahmedabad.main (null);

		Belgaum.main (null);

		Bellary.main (null);

		Bengaluru.main (null);

		Bhagalpur.main (null);

		Bhatpara.main (null);

		Bhilai.main (null);

		Bokaro.main (null);

		Parbhani.main (null);

		Chennai.main (null);

		Coimbatore.main (null);

		Delhi.main (null);

		Dumdum.main (null);

		Durgapur.main (null);

		Erode.main (null);

		Tumkur.main (null);

		Gaya.main (null);

		Goa.main (null);

		Gopalpur.main (null);

		Gulbarga.main (null);

		Hyderabad.main (null);

		Jaipur.main (null);

		Jalgaon.main (null);

		Jammu.main (null);

		Jamnagar.main (null);

		Jhansi.main (null);

		Jullundar.main (null);

		Kochi.main (null);

		Kolhapur.main (null);

		Kolkata.main (null);

		Kottayam.main (null);

		Latur.main (null);

		Loni.main (null);

		Lucknow.main (null);

		Ludhiana.main (null);

		Madurai.main (null);

		Malegaon.main (null);

		Mangalore.main (null);

		Mumbai.main (null);

		Muzaffarnagar.main (null);

		Muzaffarpur.main (null);

		Nanded.main (null);

		Noida.main (null);

		Panihati.main (null);

		Patiala.main (null);

		Puducherry.main (null);

		Pune.main (null);

		Rajahmundry.main (null);

		Rajkot.main (null);

		RajpurSonarpur.main (null);

		// Reconciler_Call.main (null);

		// Reconciler_Fixed.main (null);

		// Reconciler_Float.main (null);

		// Reconciler_Sink.main (null);

		Rourkela.main (null);

		SangliMirajKhupwad.main (null);

		Siliguri.main (null);

		Thane.main (null);

		Thiruvananthapuram.main (null);

		Tirunelveli.main (null);

		Udaipur.main (null);

		Ujjain.main (null);

		Ulhasnagar.main (null);
	}
}
