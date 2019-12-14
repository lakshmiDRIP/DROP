
package org.drip.capital.setting;

/*!
 * Copyright (C) 2019 Quantitative Risk Analytics
 */

/**
 * <i>CapitalAllocationControl</i> holds the Parameters guiding the Capital Allocation Settings. The
 * 	References are:
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
 * @author Lakshmi Krishnamurthy
 */

public class CapitalAllocationControl
{
	private boolean _useMarginal = false;
	private org.drip.capital.allocation.CorrelationCategoryBetaManager _correlationCategoryBetaManager =
		null;
	private java.util.Map<java.lang.String, org.drip.capital.allocation.EntityCapitalAssignmentSetting>
		_entityCapitalAssignmentSettingMap = null;

	/**
	 * CapitalAllocationControl Constructor
	 * 
	 * @param useMarginal TRUE - Use the Marginal PnL Attribution
	 * @param capitalUnitCategoryMap Capital Unit Correlation Category Map
	 * @param correlationCategoryBetaManager Correlation Category Beta Manager
	 * @param entityCapitalAssignmentSettingMap Entity Capital Assignment Setting Map 
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public CapitalAllocationControl (
		final boolean useMarginal,
		final java.util.Map<java.lang.String, java.lang.Integer> capitalUnitCategoryMap,
		final org.drip.capital.allocation.CorrelationCategoryBetaManager correlationCategoryBetaManager,
		final java.util.Map<java.lang.String, org.drip.capital.allocation.EntityCapitalAssignmentSetting>
			entityCapitalAssignmentSettingMap)
		throws java.lang.Exception
	{
		if (null == (_correlationCategoryBetaManager = correlationCategoryBetaManager) ||
			null == (_entityCapitalAssignmentSettingMap = entityCapitalAssignmentSettingMap))
		{
			throw new java.lang.Exception (
				"CapitalAllocationControl Constructor => Invalid Inputs"
			);
		}

		_useMarginal = useMarginal;
	}

	/**
	 * Retrieve the "Use Marginal" Flag
	 * 
	 * @return The "Use Marginal" Flag
	 */

	public boolean useMarginal()
	{
		return _useMarginal;
	}

	/**
	 * Retrieve the Correlation Category Beta Map
	 * 
	 * @return The Correlation Category Beta Map
	 */

	public org.drip.capital.allocation.CorrelationCategoryBetaManager correlationCategoryBetaManager()
	{
		return _correlationCategoryBetaManager;
	}

	/**
	 * Retrieve the Entity Capital Assignment Setting Map
	 * 
	 * @return The Entity Capital Assignment Setting Map
	 */

	public java.util.Map<java.lang.String, org.drip.capital.allocation.EntityCapitalAssignmentSetting>
		entityCapitalAssignmentSettingMap()
	{
		return _entityCapitalAssignmentSettingMap;
	}
}
