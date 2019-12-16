
package org.drip.capital.simulation;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
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
 * <i>FSPnLDecomposition</i> holds the Per FS PnL Decomposition. The References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Bank for International Supervision(2005): Stress Testing at Major Financial Institutions: Survey
 * 				Results and Practice https://www.bis.org/publ/cgfs24.htm
 * 		</li>
 * 		<li>
 * 			Glasserman, P. (2004): <i>Monte Carlo Methods in Financial Engineering</i> <b>Springer</b>
 * 		</li>
 * 		<li>
 * 			Kupiec, P. H. (2000): Stress Tests and Risk Capital <i>Risk</i> <b>2 (4)</b> 27-39
 * 		</li>
 * 	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/CapitalAnalyticsLibrary.md">Capital Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/README.md">Basel Market Risk and Operational Capital</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/simulation/README.md">Economic Risk Capital Simulation Ensemble</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class FSPnLDecomposition
{
	private java.util.Map<java.lang.String, org.drip.capital.stress.PnLSeries> _fsMap = null;

	/**
	 * Construct a Standard Instance of FSPnLDecomposition
	 * 
	 * @param notional Notional the PnL is based upon
	 * 
	 * @return Standard Instance of FSPnLDecomposition
	 */

	public static final FSPnLDecomposition Standard (
		final double notional)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (
			notional
		))
		{
			return null;
		}

		java.util.Map<java.lang.String, org.drip.capital.stress.PnLSeries> fsMap = new
			org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.capital.stress.PnLSeries>();

		java.util.Set<java.lang.String> fsTypeSet =
			org.drip.capital.env.CapitalEstimationContextManager.ContextContainer().volatilityScaleContext().fsTypeAdjustmentMap().keySet();

		for (java.lang.String fsType : fsTypeSet)
		{
			try
			{
				fsMap.put (
					fsType,
					new org.drip.capital.stress.PnLSeries (
						new double[]
						{
							notional * (java.lang.Math.random() - 0.5),
							notional * (java.lang.Math.random() - 0.5),
							notional * (java.lang.Math.random() - 0.5),
						}
					)
					{
						@Override public double composite()
						{
							double sum = 0.;
	
							double[] outcomeArray = outcomeArray();

							for (double outcome : outcomeArray)
							{
								sum = sum + outcome;
							}
	
							return sum / java.lang.Math.sqrt (
								outcomeArray.length
							);
						}
					}
				);
			}
			catch (java.lang.Exception e)
			{
				e.printStackTrace();

				return null;
			}
		}

		try
		{
			return new FSPnLDecomposition (
				fsMap
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * FSPnLDecomposition Constructor
	 * 
	 * @param fsMap FS PnL Decomposition Map
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public FSPnLDecomposition (
		final java.util.Map<java.lang.String, org.drip.capital.stress.PnLSeries> fsMap)
		throws java.lang.Exception
	{
		if (null == (_fsMap = fsMap))
		{
			throw new java.lang.Exception (
				"FSPnLDecomposition Constructor => Invalid Inputs"
			);
		}
	}

	/**
	 * Retrieve the FS PnL Decomposition Map
	 * 
	 * @return FS PnL Decomposition Map
	 */

	public java.util.Map<java.lang.String, org.drip.capital.stress.PnLSeries> fsMap()
	{
		return _fsMap;
	}

	/**
	 * Retrieve the Cross-RF Gross PnL
	 * 
	 * @return Cross-RF Gross PnL
	 */

	public double grossPnL()
	{
		if (null == _fsMap || 0 == _fsMap.size())
		{
			return 0.;
		}

		double total = 0.;

		for (java.util.Map.Entry<java.lang.String, org.drip.capital.stress.PnLSeries> fsEntry :
			_fsMap.entrySet())
		{
			total = total + fsEntry.getValue().composite();
		}

		return total;
	}

	/**
	 * Apply the FS Type Specific Volatility Scaling to the PnL Decomposition
	 * 
	 * @param fsTypeAdjustmentMap FS Type Volatility Adjustment Map
	 * @param pnlScaler The PnL Scaler
	 * 
	 * @return Volatility Adjusted FS PnL Decomposition Map
	 */

	public java.util.Map<java.lang.String, java.lang.Double> applyVolatilityAdjustment (
		final java.util.Map<java.lang.String, java.lang.Double> fsTypeAdjustmentMap,
		final double pnlScaler)
	{
		if (null == fsTypeAdjustmentMap || 0 == fsTypeAdjustmentMap.size() ||
			!org.drip.numerical.common.NumberUtil.IsValid (
				pnlScaler
			)
		)
		{
			return null;
		}

		java.util.Map<java.lang.String, java.lang.Double> volatilityAdjustedFSMap = new
			org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();

		for (java.util.Map.Entry<java.lang.String, org.drip.capital.stress.PnLSeries> fsMapEntry :
			_fsMap.entrySet())
		{
			java.lang.String fsType = fsMapEntry.getKey();

			if (!fsTypeAdjustmentMap.containsKey (
				fsType
			))
			{
				return null;
			}

			volatilityAdjustedFSMap.put (
				fsType,
				fsMapEntry.getValue().composite() * fsTypeAdjustmentMap.get (
					fsType
				) * pnlScaler
			);
		}

		return volatilityAdjustedFSMap;
	}
}
