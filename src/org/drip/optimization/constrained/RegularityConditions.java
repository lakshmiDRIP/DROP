
package org.drip.optimization.constrained;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
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
 * RegularityConditions holds the Results of the Verification of the Regularity Conditions/Constraint
 *  Qualifications at the specified (possibly) Optimal Variate and the corresponding Fritz John Multipliers.
 *  The References are:
 * 
 * 	- Boyd, S., and L. van den Berghe (2009): Convex Optimization, Cambridge University Press, Cambridge UK.
 * 
 * 	- Eustaquio, R., E. Karas, and A. Ribeiro (2008): Constraint Qualification for Nonlinear Programming,
 * 		Technical Report, Federal University of Parana.
 * 
 * 	- Karush, A. (1939): Minima of Functions of Several Variables with Inequalities as Side Constraints,
 * 		M. Sc., University of Chicago, Chicago IL.
 * 
 * 	- Kuhn, H. W., and A. W. Tucker (1951): Nonlinear Programming, Proceedings of the Second Berkeley
 * 		Symposium, University of California, Berkeley CA 481-492.
 * 
 * 	- Ruszczynski, A. (2006): Nonlinear Optimization, Princeton University Press, Princeton NJ.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class RegularityConditions {
	private double[] _adblVariate = null;
	private org.drip.optimization.constrained.FritzJohnMultipliers _fjm = null;
	private org.drip.optimization.regularity.ConstraintQualifierLCQ _cqLCQ = null;
	private org.drip.optimization.regularity.ConstraintQualifierCRCQ _cqCRCQ = null;
	private org.drip.optimization.regularity.ConstraintQualifierLICQ _cqLICQ = null;
	private org.drip.optimization.regularity.ConstraintQualifierMFCQ _cqMFCQ = null;
	private org.drip.optimization.regularity.ConstraintQualifierQNCQ _cqQNCQ = null;
	private org.drip.optimization.regularity.ConstraintQualifierSCCQ _cqSCCQ = null;
	private org.drip.optimization.regularity.ConstraintQualifierCPLDCQ _cqCPLDCQ = null;

	/**
	 * Construct a Standard Instance of RegularityConditions
	 * 
	 * @param adblVariate The Candidate Variate Array
	 * @param fjm The Fritz John Multipliers
	 * @param bValidLCQ The LCQ Validity Flag
	 * @param bValidLICQ The LICQ Validity Flag
	 * @param bValidMFCQ The MFCQ Validity Flag
	 * @param bValidCRCQ The CRCQ Validity Flag
	 * @param bValidCPLDCQ The CPLDCQ Validity Flag
	 * @param bValidQNCQ The QNCQ Validity Flag
	 * @param bValidSCCQ The SCCQ Validity Flag
	 * 
	 * @return The Standard Instance of CandidateRegularity
	 */

	public static final RegularityConditions Standard (
		final double[] adblVariate,
		final org.drip.optimization.constrained.FritzJohnMultipliers fjm,
		final boolean bValidLCQ,
		final boolean bValidLICQ,
		final boolean bValidMFCQ,
		final boolean bValidCRCQ,
		final boolean bValidCPLDCQ,
		final boolean bValidQNCQ,
		final boolean bValidSCCQ)
	{
		try {
			return new RegularityConditions (adblVariate, fjm, new
				org.drip.optimization.regularity.ConstraintQualifierLCQ (bValidLCQ), new
					org.drip.optimization.regularity.ConstraintQualifierLICQ (bValidLICQ), new
						org.drip.optimization.regularity.ConstraintQualifierMFCQ (bValidMFCQ), new
							org.drip.optimization.regularity.ConstraintQualifierCRCQ (bValidCRCQ), new
								org.drip.optimization.regularity.ConstraintQualifierCPLDCQ (bValidCPLDCQ),
									new org.drip.optimization.regularity.ConstraintQualifierQNCQ
										(bValidQNCQ), new
											org.drip.optimization.regularity.ConstraintQualifierSCCQ
												(bValidSCCQ));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * RegularityConditions Constructor
	 * 
	 * @param adblVariate The Candidate Variate Array
	 * @param fjm The Fritz John Multipliers
	 * @param cqLCQ LCQ Constraint Qualifier Instance
	 * @param cqLICQ LICQ Constraint Qualifier Instance
	 * @param cqMFCQ MFCQ Constraint Qualifier Instance
	 * @param cqCRCQ CRCQ Constraint Qualifier Instance
	 * @param cqCPLDCQ CPLDCQ Constraint Qualifier Instance
	 * @param cqQNCQ QNCQ Constraint Qualifier Instance
	 * @param cqSCCQ SCCQ Constraint Qualifier Instance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public RegularityConditions (
		final double[] adblVariate,
		final org.drip.optimization.constrained.FritzJohnMultipliers fjm,
		final org.drip.optimization.regularity.ConstraintQualifierLCQ cqLCQ,
		final org.drip.optimization.regularity.ConstraintQualifierLICQ cqLICQ,
		final org.drip.optimization.regularity.ConstraintQualifierMFCQ cqMFCQ,
		final org.drip.optimization.regularity.ConstraintQualifierCRCQ cqCRCQ,
		final org.drip.optimization.regularity.ConstraintQualifierCPLDCQ cqCPLDCQ,
		final org.drip.optimization.regularity.ConstraintQualifierQNCQ cqQNCQ,
		final org.drip.optimization.regularity.ConstraintQualifierSCCQ cqSCCQ)
		throws java.lang.Exception
	{
		if (null == (_adblVariate = adblVariate) || 0 == _adblVariate.length || null == (_fjm = fjm) || null
			== (_cqLCQ = cqLCQ) || null == (_cqLICQ = cqLICQ) || null == (_cqMFCQ = cqMFCQ) || null ==
				(_cqCRCQ = cqCRCQ) || null == (_cqCPLDCQ = cqCPLDCQ) || null == (_cqQNCQ = cqQNCQ) || null ==
					(_cqSCCQ = cqSCCQ))
			throw new java.lang.Exception ("RegularityConditions Constructor => Invalid Inputs");
	}

	/**
	 * Retrieve the Candidate Variate Array
	 * 
	 * @return The Candidate Variate Array
	 */

	public double[] variate()
	{
		return _adblVariate;
	}

	/**
	 * Retrieve the Fritz John Mutipliers
	 * 
	 * @return The Fritz John Mutipliers
	 */

	public org.drip.optimization.constrained.FritzJohnMultipliers fjm()
	{
		return _fjm;
	}

	/**
	 * Retrieve the LCQ Constraint Qualifier
	 * 
	 * @return The LCQ Constraint Qualifier
	 */

	public org.drip.optimization.regularity.ConstraintQualifierLCQ lcq()
	{
		return _cqLCQ;
	}

	/**
	 * Retrieve the LICQ Constraint Qualifier
	 * 
	 * @return The LICQ Constraint Qualifier
	 */

	public org.drip.optimization.regularity.ConstraintQualifierLICQ licq()
	{
		return _cqLICQ;
	}

	/**
	 * Retrieve the MFCQ Constraint Qualifier
	 * 
	 * @return The MFCQ Constraint Qualifier
	 */

	public org.drip.optimization.regularity.ConstraintQualifierMFCQ mfcq()
	{
		return _cqMFCQ;
	}

	/**
	 * Retrieve the CRCQ Constraint Qualifier
	 * 
	 * @return The CRCQ Constraint Qualifier
	 */

	public org.drip.optimization.regularity.ConstraintQualifierCRCQ crcq()
	{
		return _cqCRCQ;
	}

	/**
	 * Retrieve the CPLDCQ Constraint Qualifier
	 * 
	 * @return The CPLDCQ Constraint Qualifier
	 */

	public org.drip.optimization.regularity.ConstraintQualifierCPLDCQ cpldcq()
	{
		return _cqCPLDCQ;
	}

	/**
	 * Retrieve the QNCQ Constraint Qualifier
	 * 
	 * @return The QNCQ Constraint Qualifier
	 */

	public org.drip.optimization.regularity.ConstraintQualifierQNCQ qncq()
	{
		return _cqQNCQ;
	}

	/**
	 * Retrieve the SCCQ Constraint Qualifier
	 * 
	 * @return The SCCQ Constraint Qualifier
	 */

	public org.drip.optimization.regularity.ConstraintQualifierSCCQ sccq()
	{
		return _cqSCCQ;
	}

	/**
	 * Indicate the Ordered Gross Regularity Validity across all the Constraint Qualifiers
	 * 
	 * @return TRUE - The Ordered Regularity Criteria is satisfied across all the Constraint Qualifiers
	 */

	public boolean valid()
	{
		return _cqLICQ.valid() && _cqCRCQ.valid() && _cqMFCQ.valid() && _cqCPLDCQ.valid() && _cqQNCQ.valid();
	}

	/**
	 * Retrieve the Array of Strength Orders as specified in Eustaquio, Karas, and Ribeiro (2008)
	 * 
	 * @return The Array of Strength Orders as specified in Eustaquio, Karas, and Ribeiro (2008)
	 */

	public java.lang.String[] strengthOrder()
	{
		return new java.lang.String[] {"EUSTAQUIO KARAS RIBEIRO STRENGTH ORDER #1: " + _cqLICQ.display() +
			" >> " + _cqMFCQ.display() + " >> " + _cqCPLDCQ.display() + " >> " + _cqQNCQ.display(),
				"EUSTAQUIO KARAS RIBEIRO STRENGTH ORDER #2: " + _cqLICQ.display() + " >> " +
					_cqCRCQ.display() + " >> " + _cqCPLDCQ.display() + " >> " + _cqQNCQ.display(),
						"EUSTAQUIO KARAS RIBEIRO STRENGTH ORDER #3: " + _cqLCQ.display() + " >> " +
							_cqLICQ.display() + " >> " + _cqMFCQ.display() + " >> " + _cqCRCQ.display() +
								" >> " + _cqCPLDCQ.display() + " >> " + _cqQNCQ.display() + " >> " + " >> " +
									_cqSCCQ.display()};
	}
}
