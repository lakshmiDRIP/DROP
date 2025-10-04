
package org.drip.optimization.constrained;

import org.drip.optimization.regularity.ConstraintQualifierCPLDCQ;
import org.drip.optimization.regularity.ConstraintQualifierCRCQ;
import org.drip.optimization.regularity.ConstraintQualifierLCQ;
import org.drip.optimization.regularity.ConstraintQualifierLICQ;
import org.drip.optimization.regularity.ConstraintQualifierMFCQ;
import org.drip.optimization.regularity.ConstraintQualifierQNCQ;
import org.drip.optimization.regularity.ConstraintQualifierSCCQ;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2025 Lakshmi Krishnamurthy
 * Copyright (C) 2024 Lakshmi Krishnamurthy
 * Copyright (C) 2023 Lakshmi Krishnamurthy
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
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
 * <i>RegularityConditions</i> holds the Results of the Verification of the Regularity Conditions/Constraint
 * 	Qualifications at the specified (possibly) Optimal Variate and the corresponding Fritz John Multipliers.
 * 	It provides the following Functions:
 * 	<ul>
 * 		<li>Construct a Standard Instance of <i>RegularityConditions</i></li>
 * 		<li><i>RegularityConditions</i> Constructor</li>
 * 		<li>Retrieve the Fritz John Mutipliers</li>
 * 		<li>Retrieve the LCQ Constraint Qualifier</li>
 * 		<li>Retrieve the LICQ Constraint Qualifier</li>
 * 		<li>Retrieve the MFCQ Constraint Qualifier</li>
 * 		<li>Retrieve the CRCQ Constraint Qualifier</li>
 * 		<li>Retrieve the CPLDCQ Constraint Qualifier</li>
 * 		<li>Retrieve the QNCQ Constraint Qualifier</li>
 * 		<li>Retrieve the SCCQ Constraint Qualifier</li>
 * 		<li>Indicate the Ordered Gross Regularity Validity across all the Constraint Qualifiers</li>
 * 	</ul>
 * 
 * The References are:
 * <br>
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
 * <br>
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalOptimizerLibrary.md">Numerical Optimizer Library</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/optimization/README.md">Necessary, Sufficient, and Regularity Checks for Gradient Descent and LP/MILP/MINLP Schemes</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/optimization/constrained/README.md">KKT Fritz-John Constrained Optimizer</a></td></tr>
 *  </table>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class RegularityConditions
{
	private double[] _variateArray = null;
	private FritzJohnMultipliers _fritzJohnMultipliers = null;
	private ConstraintQualifierLCQ _lcqConstraintQualifier = null;
	private ConstraintQualifierCRCQ _crcqConstraintQualifier = null;
	private ConstraintQualifierLICQ _licqConstraintQualifier = null;
	private ConstraintQualifierMFCQ _mfcqConstraintQualifier = null;
	private ConstraintQualifierQNCQ _qncqConstraintQualifier = null;
	private ConstraintQualifierSCCQ _sccqConstraintQualifier = null;
	private ConstraintQualifierCPLDCQ _cpldcqConstraintQualifier = null;

	/**
	 * Construct a Standard Instance of <i>RegularityConditions</i>
	 * 
	 * @param variateArray The Candidate Variate Array
	 * @param fritzJohnMultipliers The Fritz John Multipliers
	 * @param validLCQ The LCQ Validity Flag
	 * @param validLICQ The LICQ Validity Flag
	 * @param validMFCQ The MFCQ Validity Flag
	 * @param validCRCQ The CRCQ Validity Flag
	 * @param validCPLDCQ The CPLDCQ Validity Flag
	 * @param validQNCQ The QNCQ Validity Flag
	 * @param validSCCQ The SCCQ Validity Flag
	 * 
	 * @return The Standard Instance of <i>RegularityConditions</i>
	 */

	public static final RegularityConditions Standard (
		final double[] variateArray,
		final FritzJohnMultipliers fritzJohnMultipliers,
		final boolean validLCQ,
		final boolean validLICQ,
		final boolean validMFCQ,
		final boolean validCRCQ,
		final boolean validCPLDCQ,
		final boolean validQNCQ,
		final boolean validSCCQ)
	{
		try {
			return new RegularityConditions (
				variateArray,
				fritzJohnMultipliers,
				new ConstraintQualifierLCQ (validLCQ),
				new ConstraintQualifierLICQ (validLICQ),
				new ConstraintQualifierMFCQ (validMFCQ),
				new ConstraintQualifierCRCQ (validCRCQ),
				new ConstraintQualifierCPLDCQ (validCPLDCQ),
				new ConstraintQualifierQNCQ (validQNCQ),
				new ConstraintQualifierSCCQ (validSCCQ)
			);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * <i>RegularityConditions</i> Constructor
	 * 
	 * @param variateArray The Candidate Variate Array
	 * @param fritzJohnMultipliers The Fritz John Multipliers
	 * @param lcqConstraintQualifier LCQ Constraint Qualifier Instance
	 * @param licqConstraintQualifier LICQ Constraint Qualifier Instance
	 * @param mfcqConstraintQualifier MFCQ Constraint Qualifier Instance
	 * @param crcqConstraintQualifier CRCQ Constraint Qualifier Instance
	 * @param cpldcqConstraintQualifier CPLDCQ Constraint Qualifier Instance
	 * @param qncqConstraintQualifier QNCQ Constraint Qualifier Instance
	 * @param sccqConstraintQualifier SCCQ Constraint Qualifier Instance
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public RegularityConditions (
		final double[] variateArray,
		final FritzJohnMultipliers fritzJohnMultipliers,
		final ConstraintQualifierLCQ lcqConstraintQualifier,
		final ConstraintQualifierLICQ licqConstraintQualifier,
		final ConstraintQualifierMFCQ mfcqConstraintQualifier,
		final ConstraintQualifierCRCQ crcqConstraintQualifier,
		final ConstraintQualifierCPLDCQ cpldcqConstraintQualifier,
		final ConstraintQualifierQNCQ qncqConstraintQualifier,
		final ConstraintQualifierSCCQ sccqConstraintQualifier)
		throws Exception
	{
		if (null == (_variateArray = variateArray) || 0 == _variateArray.length ||
			null == (_fritzJohnMultipliers = fritzJohnMultipliers) ||
			null == (_lcqConstraintQualifier = lcqConstraintQualifier) ||
			null == (_licqConstraintQualifier = licqConstraintQualifier) ||
			null == (_mfcqConstraintQualifier = mfcqConstraintQualifier) ||
			null == (_crcqConstraintQualifier = crcqConstraintQualifier) ||
			null == (_cpldcqConstraintQualifier = cpldcqConstraintQualifier) ||
			null == (_qncqConstraintQualifier = qncqConstraintQualifier) ||
			null == (_sccqConstraintQualifier = sccqConstraintQualifier))
		{
			throw new Exception ("RegularityConditions Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Candidate Variate Array
	 * 
	 * @return The Candidate Variate Array
	 */

	public double[] variateArray()
	{
		return _variateArray;
	}

	/**
	 * Retrieve the Fritz John Mutipliers
	 * Retrieve the LCQ Constraint Qualifier
	 * Retrieve the LICQ Constraint Qualifier
	 * Retrieve the MFCQ Constraint Qualifier
	 * Retrieve the CRCQ Constraint Qualifier
	 * Retrieve the CPLDCQ Constraint Qualifier
	 * Retrieve the QNCQ Constraint Qualifier
	 * Retrieve the SCCQ Constraint Qualifier
	 * Indicate the Ordered Gross Regularity Validity across all the Constraint Qualifiers
	 * Retrieve the Array of Strength Orders as specified in Eustaquio, Karas, and Ribeiro (2008)
	 * 
	 * @return The Fritz John Mutipliers
	 */

	public FritzJohnMultipliers fritzJohnMultipliers()
	{
		return _fritzJohnMultipliers;
	}

	/**
	 * Retrieve the LCQ Constraint Qualifier
	 * 
	 * @return The LCQ Constraint Qualifier
	 */

	public ConstraintQualifierLCQ lcqConstraintQualifier()
	{
		return _lcqConstraintQualifier;
	}

	/**
	 * Retrieve the LICQ Constraint Qualifier
	 * 
	 * @return The LICQ Constraint Qualifier
	 */

	public ConstraintQualifierLICQ licqConstraintQualifier()
	{
		return _licqConstraintQualifier;
	}

	/**
	 * Retrieve the MFCQ Constraint Qualifier
	 * 
	 * @return The MFCQ Constraint Qualifier
	 */

	public ConstraintQualifierMFCQ mfcqConstraintQualifier()
	{
		return _mfcqConstraintQualifier;
	}

	/**
	 * Retrieve the CRCQ Constraint Qualifier
	 * 
	 * @return The CRCQ Constraint Qualifier
	 */

	public ConstraintQualifierCRCQ crcq()
	{
		return _crcqConstraintQualifier;
	}

	/**
	 * Retrieve the CPLDCQ Constraint Qualifier
	 * 
	 * @return The CPLDCQ Constraint Qualifier
	 */

	public ConstraintQualifierCPLDCQ cpldcq()
	{
		return _cpldcqConstraintQualifier;
	}

	/**
	 * Retrieve the QNCQ Constraint Qualifier
	 * 
	 * @return The QNCQ Constraint Qualifier
	 */

	public ConstraintQualifierQNCQ qncq()
	{
		return _qncqConstraintQualifier;
	}

	/**
	 * Retrieve the SCCQ Constraint Qualifier
	 * 
	 * @return The SCCQ Constraint Qualifier
	 */

	public ConstraintQualifierSCCQ sccq()
	{
		return _sccqConstraintQualifier;
	}

	/**
	 * Indicate the Ordered Gross Regularity Validity across all the Constraint Qualifiers
	 * 
	 * @return TRUE - The Ordered Regularity Criteria is satisfied across all the Constraint Qualifiers
	 */

	public boolean valid()
	{
		return _licqConstraintQualifier.valid() &&
			_crcqConstraintQualifier.valid() &&
			_mfcqConstraintQualifier.valid() &&
			_cpldcqConstraintQualifier.valid() &&
			_qncqConstraintQualifier.valid();
	}

	/**
	 * Retrieve the Array of Strength Orders as specified in Eustaquio, Karas, and Ribeiro (2008)
	 * 
	 * @return The Array of Strength Orders as specified in Eustaquio, Karas, and Ribeiro (2008)
	 */

	public String[] strengthOrder()
	{
		return new String[] {
			"EUSTAQUIO KARAS RIBEIRO STRENGTH ORDER #1: " + _licqConstraintQualifier.display() +
				" >> " + _mfcqConstraintQualifier.display() +
				" >> " + _cpldcqConstraintQualifier.display() +
				" >> " + _qncqConstraintQualifier.display(),
			"EUSTAQUIO KARAS RIBEIRO STRENGTH ORDER #2: " + _licqConstraintQualifier.display() +
				" >> " + _crcqConstraintQualifier.display() +
				" >> " + _cpldcqConstraintQualifier.display() +
				" >> " + _qncqConstraintQualifier.display(),
			"EUSTAQUIO KARAS RIBEIRO STRENGTH ORDER #3: " + _lcqConstraintQualifier.display() +
				" >> " + _licqConstraintQualifier.display() +
				" >> " + _mfcqConstraintQualifier.display() +
				" >> " + _crcqConstraintQualifier.display() +
				" >> " + _cpldcqConstraintQualifier.display() +
				" >> " + _qncqConstraintQualifier.display() +
				" >> " + " >> " + _sccqConstraintQualifier.display()
		};
	}
}
