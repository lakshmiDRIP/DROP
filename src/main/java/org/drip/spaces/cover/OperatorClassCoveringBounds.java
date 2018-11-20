
package org.drip.spaces.cover;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
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
 * <i>OperatorClassCoveringBounds</i> implements the estimate Lower/Upper Bounds and/or Absolute Values of
 * the Covering Number for the Operator Class. The Main References are:
 *
 * <br><br>
 *  <ul>
 *  	<li>
 *  		Guo, Y., P. L. Bartlett, J. Shawe-Taylor, and R. C. Williamson (1999): Covering Numbers for
 *  			Support Vector Machines, in: <i>Proceedings of the 12th Annual Conference of Computational
 *  				Learning Theory</i> <b>ACM</b> New York 267-277
 *  	</li>
 *  </ul>
 *
 * <br><br>
 *  <ul>
 *		<li><b>Project</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces">Spaces</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces/cover">Covering Number</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/StatisticalLearning">Statistical Learning Library</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

/**
 * @author Spooky
 *
 */
public interface OperatorClassCoveringBounds {

	/**
	 * Lower Bound of the Operator Entropy Number
	 * 
	 * @return Lower Bound of the Operator Entropy Number
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public abstract double entropyNumberLowerBound()
		throws java.lang.Exception;

	/**
	 * Upper Bound of the Operator Entropy Number
	 * 
	 * @return Upper Bound of the Operator Entropy Number
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public abstract double entropyNumberUpperBound()
		throws java.lang.Exception;

	/**
	 * Compute the Entropy Number Index of the Operator
	 * 
	 * @return The Entropy Number Index of the Operator
	 */

	public abstract int entropyNumberIndex();

	/**
	 * Compute the Metric Norm of the Operator
	 * 
	 * @return The Metric Norm of the Operator
	 * 
	 * @throws java.lang.Exception Thrown if the Metric Norm cannot be computed
	 */

	public abstract double norm()
		throws java.lang.Exception;

	/**
	 * Compute the Entropy Number Asymptotic Behavior
	 * 
	 * @return the Entropy Number Asymptote Instance
	 */

	public abstract org.drip.learning.bound.DiagonalOperatorCoveringBound entropyNumberAsymptote();
}
