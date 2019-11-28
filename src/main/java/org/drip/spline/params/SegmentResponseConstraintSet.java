
package org.drip.spline.params;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
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
 * <i>SegmentResponseConstraintSet</i> holds the set of SegmentResponseValueConstraint (Base + One/more
 * Sensitivities) for the given Segment. It exposes functions to add/retrieve the base RVC as well as
 * additional RVC sensitivities.
 *
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/SplineBuilderLibrary.md">Spline Builder Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/README.md">Basis Splines and Linear Compounders across a Broad Family of Spline Basis Functions</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/params/README.md">Spline Segment Construction Control Parameters</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class SegmentResponseConstraintSet {
	private org.drip.spline.params.SegmentResponseValueConstraint _srvcBase = null;
	private org.drip.spline.params.SegmentResponseValueConstraint _srvcSensitivity = null;

	/**
	 * Empty SegmentResponseConstraintSet Constructor
	 */

	public SegmentResponseConstraintSet()
	{
	}

	/**
	 * Add the Base Segment Response Value Constraint
	 * 
	 * @param srvcBase The Base Segment Response Value Constraint
	 * 
	 * @return TRUE - The Base Segment Response Value Constraint Successfully Set
	 */

	public boolean addBase (
		final org.drip.spline.params.SegmentResponseValueConstraint srvcBase)
	{
		if (null == srvcBase) return false;

		_srvcBase = srvcBase;
		return true;
	}

	/**
	 * Add the Base Segment Response Value Constraint Sensitivity
	 * 
	 * @param srvcSensitivity The Base Segment Response Value Constraint Sensitivity
	 * 
	 * @return TRUE - The Base Segment Response Value Constraint Sensitivity Successfully Set
	 */

	public boolean addSensitivity (
		final org.drip.spline.params.SegmentResponseValueConstraint srvcSensitivity)
	{
		if (null == srvcSensitivity) return false;

		_srvcSensitivity = srvcSensitivity;
		return true;
	}

	/**
	 * Retrieve the Base Segment Response Value Constraint
	 * 
	 * @return The Base Segment Response Value Constraint
	 */

	public org.drip.spline.params.SegmentResponseValueConstraint getBase()
	{
		return _srvcBase;
	}

	/**
	 * Retrieve the Base Segment Response Value Constraint Sensitivity
	 * 
	 * @return The Base Segment Response Value Constraint Sensitivity
	 */

	public org.drip.spline.params.SegmentResponseValueConstraint getSensitivity()
	{
		return _srvcSensitivity;
	}
}
