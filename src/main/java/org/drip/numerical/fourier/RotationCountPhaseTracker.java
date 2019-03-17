
package org.drip.numerical.fourier;

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
 * <i>RotationCountPhaseTracker</i> implements the standard technique to preserve the trajectory along the
 * principal branch in multi-valued complex operations. This is most common in Fourier inversion quadrature
 * runs.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalOptimizerLibrary.md">Numerical Optimizer Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical">Numerical Analysis</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/fourier">Specific Fourier Transform Functionality - Rotation Counter, Phase Adjuster</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class RotationCountPhaseTracker {

	/**
	 * APPLY_NONE - Do not Apply Rotation Count
	 */

	public static final int APPLY_NONE = 0;

	/**
	 * APPLY_BACKWARD - Decrement Rotation Count
	 */

	public static final int APPLY_BACKWARD = 1;

	/**
	 * APPLY_FORWARD - Increment Rotation Count
	 */

	public static final int APPLY_FORWARD = 2;

	private int _iRotationDirection = APPLY_NONE;
	private double _dblPreviousPhase = java.lang.Double.NaN;

	/**
	 * Empty RotationCountPhaseTracker constructor - Initialize to "NO ROTATION COUNT"
	 */

	public RotationCountPhaseTracker()
	{
		_iRotationDirection = APPLY_NONE;
	}

	/**
	 * Set the Direction on which the rotation count is to be applied
	 * 
	 * @param iRotationDirection The Rotation Direction
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setDirection (
		final int iRotationDirection)
	{
		_iRotationDirection = iRotationDirection;
		return true;
	}

	/**
	 * Get the Direction on which the rotation count is to be applied
	 * 
	 * @return The Rotation Direction
	 */

	public int getDirection()
	{
		return _iRotationDirection;
	}

	/**
	 * Set the Previous Phase
	 * 
	 * @param dblPreviousPhase The Previous Phase
	 * 
	 * @return TRUE - Previous Phase Successfully set
	 */

	public boolean setPreviousPhase (
		final double dblPreviousPhase)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblPreviousPhase)) return false;

		_dblPreviousPhase = dblPreviousPhase;
		return true;
	}

	/**
	 * Get the Previous Phase
	 * 
	 * @return The Previous Phase
	 */

	public double getPreviousPhase()
	{
		return _dblPreviousPhase;
	}

	/**
	 * Apply the Rotation Count Adjustment in accordance with the direction, (optionally) record the previous
	 * 	phase.
	 * 
	 * @param dblCurrentPhase The Phase to be Updated
	 * @param bApply TRUE - Record the Previous Phase
	 * 
	 * @return The Updated Phase
	 * 
	 * @throws java.lang.Exception Thrown if the Operation cannot be performed
	 */

	public double updateAndApply (
		final double dblCurrentPhase,
		final boolean bApply)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblCurrentPhase))
			throw new java.lang.Exception ("RotationCountPhaseTracker::updateAndApply => Invalid Inputs");

		double dblUpdatedPhase = dblCurrentPhase;

		if (APPLY_FORWARD == _iRotationDirection) {
			while (dblUpdatedPhase < _dblPreviousPhase)
				dblUpdatedPhase += 2. * java.lang.Math.PI;
		} else if (APPLY_BACKWARD == _iRotationDirection) {
			while (dblUpdatedPhase > _dblPreviousPhase)
				dblUpdatedPhase -= 2. * java.lang.Math.PI;
		} else if (APPLY_NONE != _iRotationDirection)
			throw new java.lang.Exception ("RotationCountPhaseTracker::updateAndApply => Invalid State");

		if (bApply) _dblPreviousPhase = dblUpdatedPhase;

		return dblUpdatedPhase;
	}
}
