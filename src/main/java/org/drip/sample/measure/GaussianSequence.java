
package org.drip.sample.measure;

import org.drip.measure.discrete.SequenceGenerator;
import org.drip.quant.common.FormatUtil;
import org.drip.service.env.EnvManager;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting risk, transaction costs, exposure, margin
 *  	calculations, valuation adjustment, and portfolio construction within and across fixed income,
 *  	credit, commodity, equity, FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three modules:
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
 * 	- Statistical Learning
 * 	- Numerical Optimizer
 * 	- Spline Builder
 * 	- Algorithm Support
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
 * <i>GaussianSequence</i> demonstrates the Generation of R<sup>1</sup> and Correlated/Uncorrelated
 * R<sup>d</sup> Gaussian Random Number Sequence.
 *  
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalSupportLibrary.md">Numerical Support Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">Sample</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/measure/README.md">Probability Measure Generators</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class GaussianSequence {

	private static final void CorrelatedSequence (
		final int iCount,
		final double[][] aadblCorrelation,
		final String strHeader)
		throws Exception
	{
		double[][] aadblGaussianJoint = SequenceGenerator.GaussianJoint (
			iCount,
			aadblCorrelation
		);

		System.out.println();

		System.out.println ("\t||----------------------------------------------------||");

		System.out.println (strHeader);

		System.out.println ("\t||----------------------------------------------------||");

		for (int i = 0; i < iCount; ++i) {
			String strDump = "\t||" + FormatUtil.FormatDouble (i, 2, 0, 1.) + " |";

			for (int j = 0; j < aadblCorrelation.length; ++j)
				strDump = strDump + " " + FormatUtil.FormatDouble (aadblGaussianJoint[i][j], 1, 6, 1.) + " |";

			System.out.println (strDump + "|");
		}

		System.out.println ("\t||----------------------------------------------------||");

		System.out.println();
	}

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		int iCount = 50;
		double[][] aadblCorrelation1 = new double[][] {
			{1.00, 0.70, 0.25, 0.05},
			{0.70, 1.00, 0.13, 0.01},
			{0.25, 0.13, 1.00, 0.00},
			{0.05, 0.01, 0.00, 1.00}
		};
		double[][] aadblCorrelation2 = new double[][] {
			{1.00, 0.00, 0.00, 0.00},
			{0.00, 1.00, 0.00, 0.00},
			{0.00, 0.00, 1.00, 0.00},
			{0.00, 0.00, 0.00, 1.00}
		};

		CorrelatedSequence (
			iCount,
			aadblCorrelation1,
			"\t||            CORRELATED GAUSSIAN SEQUENCE            ||"
		);

		CorrelatedSequence (
			iCount,
			aadblCorrelation2,
			"\t||           UNCORRELATED GAUSSIAN SEQUENCE           ||"
		);

		EnvManager.TerminateEnv();
	}
}
