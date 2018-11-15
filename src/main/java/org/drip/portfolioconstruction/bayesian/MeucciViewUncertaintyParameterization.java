
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
 * <i>MeucciViewUncertaintyParameterization</i> demonstrates the Meucci Parameterization for the View
 * Projection Uncertainty Matrix. The Reference is:
 *  
 *  <br><br>
 *  	<ul>
 *  		<li>
 *  			Meucci, A. (2005): <i>Risk and Asset Allocation</i> <b>Springer Finance</b>
 *  		</li>
 *  	</ul>
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module</b>        = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction">Portfolio Construction</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/bayesian">Bayesian</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/AssetAllocation">Asset Allocation Library</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class MeucciViewUncertaintyParameterization {

	/**
	 * Generate the Projection Co-variance from the Scoping Co-variance and the Meucci Alpha Parameter
	 * 
	 * @param aadblScopingCovariance The Scoping Co-variance
	 * @param dblAlpha Meucci Alpha Parameter
	 * 
	 * @return The Projection Co-variance Instance
	 */

	public static final org.drip.measure.gaussian.Covariance ProjectionCovariance (
		final double[][] aadblScopingCovariance,
		final double dblAlpha)
	{
		if (null == aadblScopingCovariance || !org.drip.quant.common.NumberUtil.IsValid (dblAlpha))
			return null;

		int iNumScopingEntity = aadblScopingCovariance.length;
		double[][] aadblProjectionCovariance = 0 == iNumScopingEntity ? null : new
			double[iNumScopingEntity][iNumScopingEntity];

		if (0 == iNumScopingEntity) return null;

		for (int i = 0; i < iNumScopingEntity; ++i) {
			if (null == aadblScopingCovariance[i] || iNumScopingEntity != aadblScopingCovariance[i].length)
				return null;

			for (int j = 0; j < iNumScopingEntity; ++j) {
				if (!org.drip.quant.common.NumberUtil.IsValid (aadblScopingCovariance[i][j])) return null;

				aadblProjectionCovariance[i][j] = dblAlpha * aadblScopingCovariance[i][j];
			}
		}

		try {
			return new org.drip.measure.gaussian.Covariance (aadblProjectionCovariance);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
