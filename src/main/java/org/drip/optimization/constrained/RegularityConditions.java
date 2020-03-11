
package org.drip.optimization.constrained;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting analytics/risk, transaction cost analytics,
 *  	asset liability management analytics, capital, exposure, and margin analytics, valuation adjustment
 *  	analytics, and portfolio construction analytics within and across fixed income, credit, commodity,
 *  	equity, FX, and structured products. It also includes auxiliary libraries for algorithm support,
 *  	numerical analysis, numerical optimization, spline builder, model validation, statistical learning,
 *  	and computational support.
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
 * <i>RegularityConditions</i> holds the Results of the Verification of the Regularity Conditions/Constraint
 * Qualifications at the specified (possibly) Optimal Variate and the corresponding Fritz John Multipliers.
 * The References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Boyd, S., and L. van den Berghe (2009): <i>Convex Optimization</i> <b>Cambridge University
 * 				Press</b> Cambridge UK
 * 		</li>
 * 		<li>
 * 			Eustaquio, R., E. Karas, and A. Ribeiro (2008): <i>Constraint Qualification for Nonlinear
 * 				Programming</i> <b>Federal University of Parana</b>
 * 		</li>
 * 		<li>
 * 			Karush, A. (1939): <i>Minima of Functions of Several Variables with Inequalities as Side
 * 			Constraints</i> <b>University of Chicago</b> Chicago IL
 * 		</li>
 * 		<li>
 * 			Kuhn, H. W., and A. W. Tucker (1951): Nonlinear Programming <i>Proceedings of the Second Berkeley
 * 				Symposium</i> <b>University of California</b> Berkeley CA 481-492
 * 		</li>
 * 		<li>
 * 			Ruszczynski, A. (2006): <i>Nonlinear Optimization</i> <b>Princeton University Press</b> Princeton
 * 				NJ
 * 		</li>
 * 	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalOptimizerLibrary.md">Numerical Optimizer Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/optimization/README.md">Necessary, Sufficient, and Regularity Checks for Gradient Descent and LP/MILP/MINLP Schemes</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/optimization/constrained/README.md">KKT Fritz-John Constrained Optimizer</a></li>
 *  </ul>
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
