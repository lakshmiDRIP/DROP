
package org.drip.optimization.simplex;

import org.drip.numerical.common.NumberUtil;
import org.drip.service.common.FormatUtil;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2025 Lakshmi Krishnamurthy
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
 * <i>ColumnPivotingDiagnostics</i> holds the Diagnostics of a Column Pivoting Run. It provides the following
 * 	Functions:
 * 	<ul>
 * 		<li><i>MinimumRatioRun</i> Constructor</li>
 * 	</ul>
 * 
 * The References are:
 * <br>
 * 	<ul>
 *  	<li>
 * 			Dadush, D., and S. Huiberts (2020): A Friendly Smoothed Analysis of the Simplex Method <i>SIAM
 * 				Journal on Computing</i> <b>49 (5)</b> 449-499
 *  	</li>
 * 		<li>
 * 			Dantzig, G. B., and M. N. Thapa (1997): <i>Linear Programming 1: Introduction</i>
 * 				<b>Springer-Verlag</b> New York NY
 * 		</li>
 * 		<li>
 * 			Murty, K. G. (1983): <i>Linear Programming</i> <b>John Wiley and Sons</b> New York NY
 * 		</li>
 * 		<li>
 * 			Nering, E. D., and A. W. Tucker (1993): <i>Linear Programs and Related Problems</i>
 * 				<b>Academic Press</b> Cambridge MA
 * 		</li>
 * 		<li>
 * 			Padberg, M. (1999): <i> Linear Optimization and Extensions 2<sup>nd</sup> Edition</i>
 * 				<b>Springer-Verlag</b> New York NY
 * 		</li>
 * 	</ul>
 *
 * <br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalOptimizerLibrary.md">Numerical Optimizer Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/optimization/README.md">Necessary, Sufficient, and Regularity Checks for Gradient Descent in a Constrained Optimization Setup</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/optimization/simplex/README.md">R<sup>d</sup> to R<sup>1</sup> Simplex Scheme</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class ColumnPivotingDiagnostics
{
	private double[][] _updatedTableau = null;
	private double _rowUnitScaler = Double.NaN;
	private MinimumRatioRun _minimumRatioRun = null;

	/**
	 * <i>ColumnPivotingDiagnostics</i> Constructor
	 * 
	 * @param minimumRatioRun Minimum Ratio Run
	 * @param rowUnitScaler Row Unit Scaler
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public ColumnPivotingDiagnostics (
		final MinimumRatioRun minimumRatioRun,
		final double rowUnitScaler)
		throws Exception
	{
		if (null == (_minimumRatioRun = minimumRatioRun) ||
			!NumberUtil.IsValid (_rowUnitScaler = rowUnitScaler))
		{
			throw new Exception ("ColumnPivotingDiagnostics Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Minimum Ratio Run
	 * 
	 * @return Minimum Ratio Run
	 */

	public MinimumRatioRun minimumRatioRun()
	{
		return _minimumRatioRun;
	}

	/**
	 * Retrieve the Row Unit Scaler
	 * 
	 * @return Row Unit Scaler
	 */

	public double rowUnitScaler()
	{
		return _rowUnitScaler;
	}

	/**
	 * Retrieve the Updated Tableau
	 * 
	 * @return Updated Tableau
	 */

	public double[][] updatedTableau()
	{
		return _updatedTableau;
	}

	/**
	 * Update the Simplex Tableau
	 * 
	 * @param updatedTableau Simplex Tableau to Update
	 * 
	 * @return TRUE - Simplex Tableau Updated successfully
	 */

	public boolean updateTableau (
		final double[][] updatedTableau)
	{
		if (null == updatedTableau) {
			_updatedTableau = null;
			return false;
		}

		if (null == _updatedTableau) {
			_updatedTableau = new double[updatedTableau.length][updatedTableau[0].length];
		}

		for (int tableauRowIndex = 0; tableauRowIndex < updatedTableau.length; ++tableauRowIndex) {
			for (int tableauColumnIndex = 0;
				tableauColumnIndex < updatedTableau[tableauRowIndex].length;
				++tableauColumnIndex)
			{
				_updatedTableau[tableauRowIndex][tableauColumnIndex] =
					updatedTableau[tableauRowIndex][tableauColumnIndex];
			}
		}

		return true;
	}

	/**
	 * Convert the State to a JSON-like String
	 * 
	 * @return State to a JSON-like String
	 */

	@Override public String toString()
	{
		return "Column Pivoting Diagnostics: [" + _minimumRatioRun +
			"]; Row Unit Scaler =>" + FormatUtil.FormatDouble (_rowUnitScaler, 4, 3, 1.) + (
			null == _updatedTableau ? "" : "\n" +
				NumberUtil.MatrixToString ("Updated Tableau", _updatedTableau, 4, 3)
		);
	}
}
