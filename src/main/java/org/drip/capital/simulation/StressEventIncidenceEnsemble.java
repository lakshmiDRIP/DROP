
package org.drip.capital.simulation;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Quantitative Risk Analytics
 */

/**
 * <i>StressEventIncidenceEnsemble</i> holds the Ensemble of Stress Event Occurrences. The References are:
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

public class StressEventIncidenceEnsemble
{
	private java.util.List<org.drip.capital.simulation.StressEventIncidence> _stressEventIncidenceList =
		null;

	/**
	 * StressEventIncidenceEnsemble Constructor
	 */

	public StressEventIncidenceEnsemble()
	{
		_stressEventIncidenceList = new
			java.util.ArrayList<org.drip.capital.simulation.StressEventIncidence>();
	}

	/**
	 * Retrieve the List of Stress Event Incidences
	 * 
	 * @return The List of Stress Event Incidences
	 */

	public java.util.List<org.drip.capital.simulation.StressEventIncidence> stressEventIncidenceList()
	{
		return _stressEventIncidenceList;
	}

	/**
	 * Add the Specified Stress Event Incidence
	 * 
	 * @param stressEventIncidence The Stress Event Incidence
	 * 
	 * @return TRUE - The Stress Event Incidence successfully added
	 */

	public boolean addStressEventIncidence (
		final org.drip.capital.simulation.StressEventIncidence stressEventIncidence)
	{
		if (null == stressEventIncidence)
		{
			return false;
		}

		_stressEventIncidenceList.add (stressEventIncidence);

		return true;
	}

	/**
	 * Compute the Gross PnL
	 * 
	 * @return The Gross PnL
	 */

	public double grossPnL()
	{
		double grossPnL = 0.;

		for (org.drip.capital.simulation.StressEventIncidence stressEventIncidence :
			_stressEventIncidenceList)
		{
			grossPnL = grossPnL + stressEventIncidence.pnl();
		}

		return grossPnL;
	}

	/**
	 * Compute the Gross PAA Category PnL Decomposition
	 * 
	 * @return The Gross PAA Category PnL Decomposition
	 */

	public java.util.Map<java.lang.String, java.lang.Double> grossPAACategoryPnLDecomposition()
	{
		java.util.Map<java.lang.String, java.lang.Double> paaCategoryPnLDecomposition =
			new org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();

		for (org.drip.capital.simulation.StressEventIncidence stressEventIncidence :
			_stressEventIncidenceList)
		{
			java.util.Map<java.lang.String, java.lang.Double> incidencePAACategoryPnLDecomposition =
				stressEventIncidence.paaCategoryPnLDecomposition();

			if (null == incidencePAACategoryPnLDecomposition)
			{
				continue;
			}

			java.lang.String stressEventNamePrefix = stressEventIncidence.name() + "::";

			for (java.util.Map.Entry<java.lang.String, java.lang.Double>
				incidencePAACategoryPnLDecompositionEntry : incidencePAACategoryPnLDecomposition.entrySet())
			{
				java.lang.String paaCategoryName = stressEventNamePrefix +
					incidencePAACategoryPnLDecompositionEntry.getKey();

				if (paaCategoryPnLDecomposition.containsKey (paaCategoryName))
				{
					paaCategoryPnLDecomposition.put (
						paaCategoryName,
						incidencePAACategoryPnLDecompositionEntry.getValue() +
							paaCategoryPnLDecomposition.get (
								paaCategoryName
							)
					);
				}
				else
				{
					paaCategoryPnLDecomposition.put (
						paaCategoryName,
						incidencePAACategoryPnLDecompositionEntry.getValue()
					);
				}
			}
		}

		return paaCategoryPnLDecomposition;
	}
}
