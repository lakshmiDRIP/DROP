
package org.drip.spaces.tensor;

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
 * <i>RdAggregate</i> exposes the basic Properties of the R<sup>d</sup> as a Sectional Super-position of
 * R<sup>1</sup> Vector Spaces.
 *
 * <br><br>
 *  <ul>
 *		<li><b>Project</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces">Spaces</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces/tensor">Tensor</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/StatisticalLearning">Statistical Learning Library</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public abstract class RdAggregate implements org.drip.spaces.tensor.RdGeneralizedVector {
	private org.drip.spaces.tensor.R1GeneralizedVector[] _aR1GV = null;

	protected RdAggregate (
		final org.drip.spaces.tensor.R1GeneralizedVector[] aR1GV)
		throws java.lang.Exception
	{
		if (null == (_aR1GV = aR1GV)) throw new java.lang.Exception ("RdAggregate ctr: Invalid Inputs");

		int iDimension = _aR1GV.length;

		if (0 == iDimension) throw new java.lang.Exception ("RdAggregate ctr: Invalid Inputs");

		for (int i = 0; i < iDimension; ++i) {
			if (null == _aR1GV[i]) throw new java.lang.Exception ("RdAggregate ctr: Invalid Inputs");
		}
	}

	@Override public int dimension()
	{
		return _aR1GV.length;
	}

	@Override public org.drip.spaces.tensor.R1GeneralizedVector[] vectorSpaces()
	{
		return _aR1GV;
	}

	@Override public boolean validateInstance (
		final double[] adblInstance)
	{
		if (null == adblInstance) return false;

		int iDimension = _aR1GV.length;

		if (adblInstance.length != iDimension) return false;

		for (int i = 0; i < iDimension; ++i) {
			if (!_aR1GV[i].validateInstance (adblInstance[i])) return false;
		}

		return true;
	}

	@Override public boolean match (
		final org.drip.spaces.tensor.GeneralizedVector gvOther)
	{
		if (null == gvOther || !(gvOther instanceof RdAggregate)) return false;

		RdAggregate rdaOther = (RdAggregate) gvOther;

		int iDimensionOther = rdaOther.dimension();

		if (iDimensionOther != dimension()) return false;

		org.drip.spaces.tensor.R1GeneralizedVector[] aR1GVOther = rdaOther.vectorSpaces();

		for (int i = 0; i < iDimensionOther; ++i) {
			if (!aR1GVOther[i].match (_aR1GV[i])) return false;
		}

		return true;
	}

	@Override public boolean subset (
		final org.drip.spaces.tensor.GeneralizedVector gvOther)
	{
		if (null == gvOther || !(gvOther instanceof RdAggregate)) return false;

		int iDimensionOther = _aR1GV.length;
		RdAggregate rdaOther = (RdAggregate) gvOther;

		org.drip.spaces.tensor.R1GeneralizedVector[] aR1GVOther = rdaOther.vectorSpaces();

		for (int i = 0; i < iDimensionOther; ++i) {
			if (!aR1GVOther[i].match (_aR1GV[i])) return false;
		}

		return true;
	}

	@Override public boolean isPredictorBounded()
	{
		int iDimension = _aR1GV.length;

		for (int i = 0; i < iDimension; ++i) {
			if (!_aR1GV[i].isPredictorBounded()) return false;
		}

		return true;
	}
}
