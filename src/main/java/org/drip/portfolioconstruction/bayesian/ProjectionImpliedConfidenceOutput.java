
package org.drip.portfolioconstruction.bayesian;

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
 * <i>ProjectionImpliedConfidenceOutput</i> holds the Results of the Idzorek 2005 Black Litterman Intuitive
 * Projection Confidence Level Estimation Run. The References are:
 *  
 *  <br><br>
 *  	<ul>
 *  		<li>
 *  			He. G., and R. Litterman (1999): <i>The Intuition behind the Black-Litterman Model
 *  				Portfolios</i> <b>Goldman Sachs Asset Management</b>
 *  		</li>
 *  		<li>
 *  			Idzorek, T. (2005): <i>A Step-by-Step Guide to the Black-Litterman Model: Incorporating
 *  				User-Specified Confidence Levels</i> <b>Ibbotson Associates</b> Chicago, IL
 *  		</li>
 *  	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AssetAllocationAnalyticsLibrary.md">Asset Allocation Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/README.md">Portfolio Construction under Allocation Constraints</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/bayesian/README.md">Black Litterman Bayesian Portfolio Construction</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class ProjectionImpliedConfidenceOutput
{
	private double[] _unadjustedWeightArray = null;
	private org.drip.portfolioconstruction.bayesian.BlackLittermanOutput _fullConfidenceOutput = null;
	private org.drip.portfolioconstruction.bayesian.BlackLittermanCustomConfidenceOutput
		_customConfidenceOutput = null;

	/**
	 * ProjectionImpliedConfidenceOutput Constructor
	 * 
	 * @param unadjustedWeightArray Array of the Unadjusted Weights
	 * @param customConfidenceOutput The Custom Confidence Black Litterman Run Output
	 * @param fullConfidenceOutput The Full Confidence Black Litterman Run Output
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public ProjectionImpliedConfidenceOutput (
		final double[] unadjustedWeightArray,
		final org.drip.portfolioconstruction.bayesian.BlackLittermanCustomConfidenceOutput
			customConfidenceOutput,
		final org.drip.portfolioconstruction.bayesian.BlackLittermanOutput fullConfidenceOutput)
		throws java.lang.Exception
	{
		if (null == (_unadjustedWeightArray = unadjustedWeightArray) || 0 == _unadjustedWeightArray.length ||
			null == (_customConfidenceOutput = customConfidenceOutput) ||
			null == (_fullConfidenceOutput = fullConfidenceOutput))
		{
			throw new java.lang.Exception
				("ProjectionImpliedConfidenceOutput Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Array of the Unadjusted Equilibrium Weights
	 * 
	 * @return The Array of the Unadjusted Equilibrium Weights
	 */

	public double[] unadjustedWeightArray()
	{
		return _unadjustedWeightArray;
	}

	/**
	 * Retrieve the Custom Projection Confidence Black Litterman Run Output
	 * 
	 * @return The Custom Projection Confidence Black Litterman Run Output
	 */

	public org.drip.portfolioconstruction.bayesian.BlackLittermanCustomConfidenceOutput
		customConfidenceOutput()
	{
		return _customConfidenceOutput;
	}

	/**
	 * Retrieve the Full Projection Confidence Black Litterman Run Output
	 * 
	 * @return The Full Projection Confidence Black Litterman Run Output
	 */

	public org.drip.portfolioconstruction.bayesian.BlackLittermanOutput fullConfidenceOutput()
	{
		return _fullConfidenceOutput;
	}

	/**
	 * Retrieve the Custom Projection Induced Equilibrium Asset Deviation Array
	 * 
	 * @return The Custom Projection Induced Equilibrium Asset Deviation Array
	 */

	public double[] customProjectionConfidenceDeviation()
	{
		return _customConfidenceOutput.allocationAdjustmentTiltArray();
	}

	/**
	 * Retrieve the Custom Projection Induced Equilibrium Asset Weight Array
	 * 
	 * @return The Custom Projection Induced Equilibrium Asset Weight Array
	 */

	public double[] customProjectionConfidenceWeight()
	{
		return _customConfidenceOutput.adjustedOptimizationOutput().optimalPortfolio().weightArray();
	}

	/**
	 * Retrieve the Full Projection Induced Equilibrium Asset Deviation Array
	 * 
	 * @return The Full Projection Induced Equilibrium Asset Deviation Array
	 */

	public double[] fullProjectionConfidenceDeviation()
	{
		return _fullConfidenceOutput.allocationAdjustmentTiltArray();
	}

	/**
	 * Retrieve the Full Projection Induced Equilibrium Asset Weight Array
	 * 
	 * @return The Full Projection Induced Equilibrium Asset Weight Array
	 */

	public double[] fullProjectionConfidenceWeight()
	{
		return _fullConfidenceOutput.adjustedOptimizationOutput().optimalPortfolio().weightArray();
	}

	/**
	 * Compute the Array of the Custom Projection Induced Confidence Level
	 * 
	 * @return The Array of the Custom Projection Induced Confidence Level
	 */

	public double[] impliedConfidenceLevelArray()
	{
		int assetCount = _unadjustedWeightArray.length;
		double[] impliedConfidenceLevelArray = new double[assetCount];

		double[] fullProjectionConfidenceDeviationArray =
			_fullConfidenceOutput.allocationAdjustmentTiltArray();

		double[] customProjectionConfidenceDeviationArray =
			_customConfidenceOutput.allocationAdjustmentTiltArray();

		for (int assetIndex = 0; assetIndex < assetCount; ++assetIndex)
		{
			impliedConfidenceLevelArray[assetIndex] = customProjectionConfidenceDeviationArray[assetIndex] /
				fullProjectionConfidenceDeviationArray[assetIndex];
		}

		return impliedConfidenceLevelArray;
	}
}
