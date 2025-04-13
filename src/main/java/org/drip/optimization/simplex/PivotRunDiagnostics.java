
package org.drip.optimization.simplex;

import java.util.HashMap;
import java.util.Map;

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
 * <i>PivotRunDiagnostics</i> augments the Pivot Run Sequence with Column Pivoting Diagnostics. The
 * 	References are:
 * 
 * <br><br>
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
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalOptimizerLibrary.md">Numerical Optimizer Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/optimization/README.md">Necessary, Sufficient, and Regularity Checks for Gradient Descent in a Constrained Optimization Setup</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/optimization/simplex">R<sup>d</sup> to R<sup>1</sup> Simplex Scheme</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class PivotRunDiagnostics
	extends PivotRun
{
	private Map<Integer, ColumnPivotingDiagnostics> _columnPivotingMap = null;

	/**
	 * <i>PivotRunDiagnostics</i> Constructor
	 * 
	 * @param basicFeasibleSolution Basic Feasible Solution
	 */

	public PivotRunDiagnostics (
		final double[] basicFeasibleSolution)
	{
		super (basicFeasibleSolution);

		_columnPivotingMap = new HashMap<Integer, ColumnPivotingDiagnostics>();
	}

	/**
	 * Retrieve the Map of <i>ColumnPivotingDiagnostics<i> Records
	 * 
	 * @return Map of <i>ColumnPivotingDiagnostics<i> Records
	 */

	public Map<Integer, ColumnPivotingDiagnostics> columnPivotingMap()
	{
		return _columnPivotingMap;
	}

	/**
	 * Add a <i>ColumnPivotingDiagnostics<i> Record
	 * 
	 * @param minimumRatioRun Minimum Ratio Run
	 * @param rowUnitScaler Row Unit Scaler
	 * 
	 * @return TRUE - The<i>ColumnPivotingDiagnostics<i> Record successfully added
	 */

	public boolean addColumnPivoting (
		final MinimumRatioRun minimumRatioRun,
		final double rowUnitScaler)
	{
		if (null == minimumRatioRun) {
			return false;
		}

		try {
			_columnPivotingMap.put (
				minimumRatioRun.enteringVariableColumnIndex(),
				new ColumnPivotingDiagnostics (minimumRatioRun, rowUnitScaler)
			);

			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Update the Tableau corresponding to Pivot Column Index
	 * 
	 * @param tableauColumnIndex The Pivot Tableau Column Index
	 * @param updatedTableau Tableau to be Updated
	 * 
	 * @return TRUE - The Tableau corresponding to Pivot Column Index successfully updated
	 */

	public boolean updateColumnPivotingTableau (
		final int tableauColumnIndex,
		final double[][] updatedTableau)
	{
		_columnPivotingMap.get (tableauColumnIndex).updateTableau (updatedTableau);

		return true;
	}

	/**
	 * Convert the State to a JSON-like String
	 * 
	 * @return State to a JSON-like String
	 */

	@Override public String toString()
	{
		String display = super.toString();

		display += "Pivot Run Diagnostics - Column Pivoting Map: " + _columnPivotingMap;
		return display;
	}
}
