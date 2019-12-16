
package org.drip.capital.allocation;

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
 * <i>EntityElasticityAttribution</i> holds the Attributions across all Entity Components into Fixed, Float,
 * 	and Pro-rata Elasticities. The References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Bank for International Supervision (2005): Stress Testing at Major Financial Institutions: Survey
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
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/allocation/README.md">Economic Risk Capital Entity Allocation</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class EntityElasticityAttribution
{
	private org.drip.capital.allocation.EntityComponentElasticityAttribution _noStress = null;
	private org.drip.capital.allocation.EntityComponentElasticityAttribution _systemic = null;
	private org.drip.capital.allocation.EntityComponentElasticityAttribution _correlated = null;
	private org.drip.capital.allocation.EntityComponentElasticityAttribution _idiosyncratic = null;

	/**
	 * EntityElasticityAttribution Constructor
	 * 
	 * @param correlationCategoryBetaManager The Correlation Category Beta Manager
	 * @param unitLoading The Unit Loading Flag
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public EntityElasticityAttribution (
		final org.drip.capital.allocation.CorrelationCategoryBetaManager correlationCategoryBetaManager,
		final boolean unitLoading)
		throws java.lang.Exception
	{
		_systemic = new org.drip.capital.allocation.EntityComponentElasticityAttribution (
			correlationCategoryBetaManager,
			unitLoading
		);

		_correlated = new org.drip.capital.allocation.EntityComponentElasticityAttribution (
			correlationCategoryBetaManager,
			unitLoading
		);

		_idiosyncratic = new org.drip.capital.allocation.EntityComponentElasticityAttribution (
			correlationCategoryBetaManager,
			unitLoading
		);

		_noStress = new org.drip.capital.allocation.EntityComponentElasticityAttribution (
			correlationCategoryBetaManager,
			unitLoading
		);
	}

	/**
	 * Retrieve the Systemic Elasticity Attribution
	 * 
	 * @return The Systemic Elasticity Attribution
	 */

	public org.drip.capital.allocation.EntityComponentElasticityAttribution systemic()
	{
		return _systemic;
	}

	/**
	 * Retrieve the Correlated Elasticity Attribution
	 * 
	 * @return The Correlated Elasticity Attribution
	 */

	public org.drip.capital.allocation.EntityComponentElasticityAttribution correlated()
	{
		return _correlated;
	}

	/**
	 * Retrieve the Idiosyncratic Elasticity Attribution
	 * 
	 * @return The Idiosyncratic Elasticity Attribution
	 */

	public org.drip.capital.allocation.EntityComponentElasticityAttribution idiosyncratic()
	{
		return _idiosyncratic;
	}

	/**
	 * Retrieve the "No Stress" Elasticity Attribution
	 * 
	 * @return The "No Stress" Elasticity Attribution
	 */

	public org.drip.capital.allocation.EntityComponentElasticityAttribution noStress()
	{
		return _noStress;
	}

	/**
	 * Accumulate the Systemic Fixed Attribution
	 * 
	 * @param systemicFixedAttribution Systemic Fixed Attribution
	 * 
	 * @return TRUE - The Systemic Fixed Attribution successfully updated
	 */

	public boolean accumulateSystemicFixed (
		final double systemicFixedAttribution)
	{
		return _systemic.accumulateFixed (
			systemicFixedAttribution
		);
	}

	/**
	 * Accumulate the Correlated Fixed Attribution
	 * 
	 * @param correlatedFixedAttribution Correlated Fixed Attribution
	 * 
	 * @return TRUE - The Correlated Fixed Attribution successfully updated
	 */

	public boolean accumulateCorrelatedFixed (
		final double correlatedFixedAttribution)
	{
		return _correlated.accumulateFixed (
			correlatedFixedAttribution
		);
	}

	/**
	 * Accumulate the Idiosyncratic Fixed Attribution
	 * 
	 * @param idiosyncraticFixedAttribution Idiosyncratic Fixed Attribution
	 * 
	 * @return TRUE - The Idiosyncratic Fixed Attribution successfully updated
	 */

	public boolean accumulateIdiosyncraticFixed (
		final double idiosyncraticFixedAttribution)
	{
		return _idiosyncratic.accumulateFixed (
			idiosyncraticFixedAttribution
		);
	}

	/**
	 * Accumulate the No-Stress Fixed Attribution
	 * 
	 * @param noStressFixedAttribution No-Stress Fixed Attribution
	 * 
	 * @return TRUE - The No-Stress Fixed Attribution successfully updated
	 */

	public boolean accumulateNoStressFixed (
		final double noStressFixedAttribution)
	{
		return _noStress.accumulateFixed (
			noStressFixedAttribution
		);
	}

	/**
	 * Accumulate the Systemic Floating Attribution
	 * 
	 * @param systemicFloatingAttribution Systemic Floating Attribution
	 * 
	 * @return TRUE - The Systemic Floating Attribution successfully updated
	 */

	public boolean accumulateSystemicFloating (
		final double systemicFloatingAttribution)
	{
		return _systemic.accumulateFloating (
			systemicFloatingAttribution
		);
	}

	/**
	 * Accumulate the Correlated Floating Attribution
	 * 
	 * @param correlatedFloatingAttribution Correlated Floating Attribution
	 * 
	 * @return TRUE - The Correlated Floating Attribution successfully updated
	 */

	public boolean accumulateCorrelatedFloating (
		final double correlatedFloatingAttribution)
	{
		return _correlated.accumulateFloating (
			correlatedFloatingAttribution
		);
	}

	/**
	 * Accumulate the Idiosyncratic Floating Attribution
	 * 
	 * @param idiosyncraticFloatingAttribution Idiosyncratic Floating Attribution
	 * 
	 * @return TRUE - The Idiosyncratic Floating Attribution successfully updated
	 */

	public boolean accumulateIdiosyncraticFloating (
		final double idiosyncraticFloatingAttribution)
	{
		return _idiosyncratic.accumulateFloating (
			idiosyncraticFloatingAttribution
		);
	}

	/**
	 * Accumulate the No-Stress Floating Attribution
	 * 
	 * @param noStressFloatingAttribution No-Stress Floating Attribution
	 * 
	 * @return TRUE - The No-Stress Floating Attribution successfully updated
	 */

	public boolean accumulateNoStressFloating (
		final double noStressFloatingAttribution)
	{
		return _noStress.accumulateFloating (
			noStressFloatingAttribution
		);
	}

	/**
	 * Accumulate the Systemic Pro-Rata Attribution
	 * 
	 * @param systemicProRataAttribution Systemic Pro-Rata Attribution
	 * 
	 * @return TRUE - The Systemic Pro-Rata Attribution successfully updated
	 */

	public boolean accumulateSystemicProRata (
		final double systemicProRataAttribution)
	{
		return _systemic.accumulateProRata (
			systemicProRataAttribution
		);
	}

	/**
	 * Accumulate the Correlated Pro-Rata Attribution
	 * 
	 * @param correlatedProRataAttribution Correlated Pro-Rata Attribution
	 * 
	 * @return TRUE - The Correlated Pro-Rata Attribution successfully updated
	 */

	public boolean accumulateCorrelatedProRata (
		final double correlatedProRataAttribution)
	{
		return _correlated.accumulateProRata (
			correlatedProRataAttribution
		);
	}

	/**
	 * Accumulate the Idiosyncratic Pro-Rata Attribution
	 * 
	 * @param idiosyncraticProRataAttribution Idiosyncratic Pro-Rata Attribution
	 * 
	 * @return TRUE - The Idiosyncratic Pro-Rata Attribution successfully updated
	 */

	public boolean accumulateIdiosyncraticProRata (
		final double idiosyncraticProRataAttribution)
	{
		return _idiosyncratic.accumulateProRata (
			idiosyncraticProRataAttribution
		);
	}

	/**
	 * Accumulate the No-Stress Pro-Rata Attribution
	 * 
	 * @param noStressProRataAttribution No-Stress Pro-Rata Attribution
	 * 
	 * @return TRUE - The No-Stress Pro-Rata Attribution successfully updated
	 */

	public boolean accumulateNoStressProRata (
		final double noStressProRataAttribution)
	{
		return _noStress.accumulateProRata (
			noStressProRataAttribution
		);
	}

	/**
	 * Accumulate the Systemic Attribution with the Beta-Adjusted Component Attribution
	 * 
	 * @param systemicAttribution The Systemic Attribution
	 * @param systemicAllocationCategory The Systemic Allocation Category
	 * @param systemicAllocationScheme The Systemic Allocation Scheme
	 * 
	 * @return TRUE - The Systemic Partition with the Beta-Adjusted Increment
	 */

	public boolean accumulateSystemic (
		final double systemicAttribution,
		final int systemicAllocationCategory,
		final int systemicAllocationScheme)
	{
		return _systemic.accumulate (
			systemicAttribution,
			systemicAllocationCategory,
			systemicAllocationScheme
		);
	}

	/**
	 * Accumulate the Correlated Attribution with the Beta-Adjusted Component Attribution
	 * 
	 * @param correlatedAttribution The Correlated Attribution
	 * @param correlatedAllocationCategory The Correlated Allocation Category
	 * @param correlatedAllocationScheme The Correlated Allocation Scheme
	 * 
	 * @return TRUE - The Correlated Partition with the Beta-Adjusted Increment
	 */

	public boolean accumulateCorrelated (
		final double correlatedAttribution,
		final int correlatedAllocationCategory,
		final int correlatedAllocationScheme)
	{
		return _correlated.accumulate (
			correlatedAttribution,
			correlatedAllocationCategory,
			correlatedAllocationScheme
		);
	}

	/**
	 * Accumulate the Idiosyncratic Attribution with the Beta-Adjusted Component Attribution
	 * 
	 * @param idiosyncraticAttribution The Idiosyncratic Attribution
	 * @param idiosyncraticAllocationCategory The Idiosyncratic Allocation Category
	 * @param idiosyncraticAllocationScheme The Idiosyncratic Allocation Scheme
	 * 
	 * @return TRUE - The Idiosyncratic Partition with the Beta-Adjusted Increment
	 */

	public boolean accumulateIdiosyncratic (
		final double idiosyncraticAttribution,
		final int idiosyncraticAllocationCategory,
		final int idiosyncraticAllocationScheme)
	{
		return _idiosyncratic.accumulate (
			idiosyncraticAttribution,
			idiosyncraticAllocationCategory,
			idiosyncraticAllocationScheme
		);
	}

	/**
	 * Accumulate the No Stress Attribution with the Beta-Adjusted Component Attribution
	 * 
	 * @param noStressAttribution The No Stress Attribution
	 * @param noStressAllocationCategory The No Stress Allocation Category
	 * @param noStressAllocationScheme The No Stress Allocation Scheme
	 * 
	 * @return TRUE - The No Stress Partition with the Beta-Adjusted Increment
	 */

	public boolean accumulateNoStress (
		final double noStressAttribution,
		final int noStressAllocationCategory,
		final int noStressAllocationScheme)
	{
		return _noStress.accumulate (
			noStressAttribution,
			noStressAllocationCategory,
			noStressAllocationScheme
		);
	}

	/**
	 * Accumulate with the Beta-Adjusted Component Attribution
	 * 
	 * @param pnlAttribution PnL Attribution
	 * @param entityCapitalAssignmentSetting Entity Capital Assignment Setting
	 * 
	 * @return TRUE - The Accumulation is successful
	 */

	public boolean accumulate (
		final org.drip.capital.explain.PnLAttribution pnlAttribution,
		final org.drip.capital.allocation.EntityCapitalAssignmentSetting entityCapitalAssignmentSetting)
	{
		if (null == pnlAttribution ||
			null == entityCapitalAssignmentSetting)
		{
			return false;
		}

		if (!accumulateSystemic (
			pnlAttribution.systemicPnL(),
			entityCapitalAssignmentSetting.systemicAllocationCategory(),
			entityCapitalAssignmentSetting.systemicAllocationScheme()
		))
		{
			return false;
		}

		if (!accumulateCorrelated (
			pnlAttribution.correlatedPnL(),
			entityCapitalAssignmentSetting.correlatedAllocationCategory(),
			entityCapitalAssignmentSetting.correlatedAllocationScheme()
		))
		{
			return false;
		}

		if (!accumulateIdiosyncratic (
			pnlAttribution.idiosyncraticGrossPnL(),
			entityCapitalAssignmentSetting.idiosyncraticAllocationCategory(),
			entityCapitalAssignmentSetting.idiosyncraticAllocationScheme()
		))
		{
			return false;
		}

		if (!accumulateNoStress (
			pnlAttribution.fsGrossPnL(),
			entityCapitalAssignmentSetting.noStressAllocationCategory(),
			entityCapitalAssignmentSetting.noStressAllocationScheme()
		))
		{
			return false;
		}

		return true;
	}

	/**
	 * Retrieve the Total Systemic Component Capital
	 * 
	 * @return The Total Systemic Component Capital
	 */

	public double systemicTotal()
	{
		return _systemic.fixed() + _systemic.floating() + _systemic.proRata();
	}

	/**
	 * Retrieve the Total Correlated Component Capital
	 * 
	 * @return The Total Correlated Component Capital
	 */

	public double correlatedTotal()
	{
		return _correlated.fixed() + _correlated.floating() + _correlated.proRata();
	}

	/**
	 * Retrieve the Total Idiosyncratic Component Capital
	 * 
	 * @return The Total Idiosyncratic Component Capital
	 */

	public double idiosyncraticTotal()
	{
		return _idiosyncratic.fixed() + _idiosyncratic.floating() + _idiosyncratic.proRata();
	}

	/**
	 * Retrieve the Total No Stress Component Capital
	 * 
	 * @return The Total No Stress Component Capital
	 */

	public double noStressTotal()
	{
		return _noStress.fixed() + _noStress.floating() + _noStress.proRata();
	}

	/**
	 * Retrieve the Fixed Beta Capital Component
	 * 
	 * @return The Fixed Beta Capital Component
	 */

	public double fixed()
	{
		return _systemic.fixed() + _correlated.fixed() + _idiosyncratic.fixed() + _noStress.fixed();
	}

	/**
	 * Retrieve the Floating Beta Capital Component
	 * 
	 * @return The Floating Beta Capital Component
	 */

	public double floating()
	{
		return _systemic.floating() + _correlated.floating() + _idiosyncratic.floating() +
			_noStress.floating();
	}

	/**
	 * Retrieve the Pro-Rata Beta Capital Component
	 * 
	 * @return The Pro-Rata Beta Capital Component
	 */

	public double proRata()
	{
		return _systemic.proRata() + _correlated.proRata() + _idiosyncratic.proRata() + _noStress.proRata();
	}

	/**
	 * Retrieve the Pro-Rata Systemic Capital
	 *  
	 * @return The Pro-Rata Systemic Capital
	 */

	public double systemicProRata()
	{
		return _systemic.proRata();
	}

	/**
	 * Retrieve the Pro-Rata Correlated Capital
	 *  
	 * @return The Pro-Rata Correlated Capital
	 */

	public double correlatedProRata()
	{
		return _correlated.proRata();
	}

	/**
	 * Retrieve the Pro-Rata Idiosyncratic Capital
	 *  
	 * @return The Pro-Rata Idiosyncratic Capital
	 */

	public double idiosyncraticProRata()
	{
		return _idiosyncratic.proRata();
	}

	/**
	 * Retrieve the Pro-Rata No-Stress Capital
	 *  
	 * @return The Pro-Rata No-Stress Capital
	 */

	public double noStressProRata()
	{
		return _noStress.proRata();
	}
}
