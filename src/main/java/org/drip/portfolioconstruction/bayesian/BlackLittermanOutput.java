
package org.drip.portfolioconstruction.bayesian;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
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
 * <i>BlackLittermanOutput</i> holds the essential Outputs generated from either a Full or a Custom
 * Confidence of the Projection Black Litterman Bayesian Combination Run. The References are:
 *  
 *  <br><br>
 *  	<ul>
 *  		<li>
 *  			He. G., and R. Litterman (1999): <i>The Intuition behind the Black-Litterman Model
 *  				Portfolios</i> <b>Goldman Sachs Asset Management</b>
 *  		<li>
 *  		</li>
 *  			Idzorek, T. (2005): <i>A Step-by-Step Guide to the Black-Litterman Model: Incorporating
 *  				User-Specified Confidence Levels</i> <b>Ibbotson Associates</b> Chicago, IL
 *  		</li>
 *  	</ul>
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module</b>        = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction">Portfolio Construction</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/bayesian">Bayesian/a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/AssetAllocation">Asset Allocation Library</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class BlackLittermanOutput {
	private double[] _adblAllocationAdjustmentTilt = null;
	private org.drip.portfolioconstruction.allocator.ForwardReverseOptimizationOutput _frooAdjusted = null;

	/**
	 * BlackLittermanOutput Constructor
	 * 
	 * @param frooAdjusted The Black Litterman Adjusted Forward Reverse Equilibrium Optimization Output
	 * @param adblAllocationAdjustmentTilt Array of the Allocation Adjustment Tilts
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public BlackLittermanOutput (
		final org.drip.portfolioconstruction.allocator.ForwardReverseOptimizationOutput frooAdjusted,
		final double[] adblAllocationAdjustmentTilt)
		throws java.lang.Exception
	{
		if (null == (_frooAdjusted = frooAdjusted) || null == (_adblAllocationAdjustmentTilt =
			adblAllocationAdjustmentTilt) || 0 == _adblAllocationAdjustmentTilt.length)
			throw new java.lang.Exception ("BlackLittermanOutput Constructor => Invalid Inputs");
	}

	/**
	 * Retrieve the Adjusted Forward Equilibrium Optimization Metrics
	 * 
	 * @return The Adjusted Forward Equilibrium Optimization Metrics
	 */

	public org.drip.portfolioconstruction.allocator.ForwardReverseOptimizationOutput adjustedMetrics()
	{
		return _frooAdjusted;
	}

	/**
	 * Retrieve the Array of the Black Litterman Allocation Adjustment Tilts
	 * 
	 * @return The Array of the Black Litterman Allocation Adjustment Tilts
	 */

	public double[] allocationAdjustmentTilt()
	{
		return _adblAllocationAdjustmentTilt;
	}
}
