
package org.drip.exposure.universe;

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
 * <i>MarketVertexGenerator</i> generates the Market Realizations at a Trajectory Vertex needed for computing
 * the Valuation Adjustment. The References are:
 *  
 * <br><br>
 *  	<ul>
 *  		<li>
 *  			Burgard, C., and M. Kjaer (2013): Funding Costs, Funding Strategies <i>Risk</i> <b>23
 *  				(12)</b> 82-87
 *  		</li>
 *  		<li>
 *  			Burgard, C., and M. Kjaer (2014): In the Balance <i>Risk</i> <b>24 (11)</b> 72-75
 *  		</li>
 *  		<li>
 *  			Burgard, C., and M. Kjaer (2014): PDE Representations of Derivatives with Bilateral Counter-
 *  				party Risk and Funding Costs <i>Journal of Credit Risk</i> <b>7 (3)</b> 1-19
 *  		</li>
 *  		<li>
 *  			Gregory, J. (2009): Being Two-faced over Counter-party Credit Risk <i>Risk</i> <b>20 (2)</b>
 *  				86-90
 *  		</li>
 *  		<li>
 *  			Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives
 *  				Pricing <i>Risk</i> <b>21 (2)</b> 97-102
 *  		</li>
 *  		<li>
 *  	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ExposureAnalyticsLibrary.md">Exposure Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/README.md">Exposure</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/universe/README.md">Universe</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class MarketVertexGenerator
{
	private int _spotDate = -1;
	private double[] _ycfWidth = null;
	private int[] _eventDateArray = null;
	private org.drip.exposure.evolver.EntityDynamicsContainer _entityDynamicsContainer = null;
	private org.drip.exposure.evolver.LatentStateDynamicsContainer _latentStateDynamicsContainer = null;
	private org.drip.exposure.evolver.PrimarySecurityDynamicsContainer _primarySecurityDynamicsContainer =
		null;

	private org.drip.measure.process.DiffusionEvolver evolver (
		final org.drip.state.identifier.LatentStateLabel latentStateLabel)
	{
		org.drip.exposure.evolver.TerminalLatentState terminalLatentState =
			_latentStateDynamicsContainer.terminal (latentStateLabel);

		if (null == terminalLatentState)
		{
			terminalLatentState = _latentStateDynamicsContainer.terminal (latentStateLabel);
		}

		return null == terminalLatentState ? null : terminalLatentState.evolver();
	}

	private java.util.List<org.drip.measure.realization.JumpDiffusionVertex[]> latentStateVertexArrayList (
		final java.util.List<org.drip.state.identifier.LatentStateLabel> latentStateLabelList,
		final org.drip.exposure.universe.MarketVertex initialMarketVertex,
		final org.drip.exposure.universe.LatentStateWeiner latentStateWeiner,
		final int terminalDate)
	{
		if (null == latentStateLabelList)
		{
			return null;
		}

		java.util.List<org.drip.measure.realization.JumpDiffusionVertex[]> latentStateVertexArrayList = new
			java.util.ArrayList<org.drip.measure.realization.JumpDiffusionVertex[]>();

		for (org.drip.state.identifier.LatentStateLabel latentStateLabel : latentStateLabelList)
		{
			org.drip.measure.process.DiffusionEvolver latentStateDiffusionEvolver = evolver
				(latentStateLabel);

			if (null == latentStateDiffusionEvolver)
			{
				continue;
			}

			try
			{
				latentStateVertexArrayList.add (
					latentStateDiffusionEvolver.vertexSequence (
						new org.drip.measure.realization.JumpDiffusionVertex (
							terminalDate,
							initialMarketVertex.latentStateValue (latentStateLabel),
							0.,
							false
						),
						org.drip.measure.realization.JumpDiffusionEdgeUnit.Diffusion (
							_ycfWidth,
							latentStateWeiner.incrementArray (latentStateLabel)
						),
						_ycfWidth
					)
				);
			}
			catch (java.lang.Exception e)
			{
				e.printStackTrace();
			}
		}

		return latentStateVertexArrayList;
	}

	private org.drip.measure.realization.JumpDiffusionVertex[] overnightReplicatorVertexArray (
		final org.drip.exposure.universe.MarketVertex initialMarketVertex,
		final org.drip.exposure.universe.LatentStateWeiner latentStateWeiner,
		final int terminalDate)
	{
		org.drip.exposure.evolver.PrimarySecurity overnightReplicator =
			_primarySecurityDynamicsContainer.overnight();

		try
		{
			return overnightReplicator.evolver().vertexSequenceReverse (
				new org.drip.measure.realization.JumpDiffusionVertex (
					terminalDate,
					initialMarketVertex.overnightReplicator(),
					0.,
					false
				),
				org.drip.measure.realization.JumpDiffusionEdgeUnit.Diffusion (
					_ycfWidth,
					latentStateWeiner.incrementArray (overnightReplicator.label())
				),
				_ycfWidth
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	private org.drip.measure.realization.JumpDiffusionVertex[] csaReplicatorVertexArray (
		final org.drip.exposure.universe.MarketVertex initialMarketVertex,
		final org.drip.exposure.universe.LatentStateWeiner latentStateWeiner,
		final int terminalDate)
	{
		org.drip.exposure.evolver.PrimarySecurity csaReplicator = _primarySecurityDynamicsContainer.csa();

		try
		{
			return csaReplicator.evolver().vertexSequenceReverse (
				new org.drip.measure.realization.JumpDiffusionVertex (
					terminalDate,
					initialMarketVertex.csaReplicator(),
					0.,
					false
				),
				org.drip.measure.realization.JumpDiffusionEdgeUnit.Diffusion (
					_ycfWidth,
					latentStateWeiner.incrementArray (csaReplicator.label())
				),
				_ycfWidth
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	private org.drip.measure.realization.JumpDiffusionVertex[] dealerSeniorFundingReplicatorVertexArray (
		final org.drip.exposure.universe.MarketVertex initialMarketVertex,
		final org.drip.exposure.universe.LatentStateWeiner latentStateWeiner,
		final int terminalDate)
	{
		org.drip.exposure.evolver.PrimarySecurity dealerSeniorFundingReplicator =
			_primarySecurityDynamicsContainer.dealerSeniorFunding();

		try
		{
			return dealerSeniorFundingReplicator.evolver().vertexSequenceReverse (
				new org.drip.measure.realization.JumpDiffusionVertex (
					terminalDate,
					initialMarketVertex.dealer().seniorFundingReplicator(),
					0.,
					false
				),
				org.drip.measure.realization.JumpDiffusionEdgeUnit.Diffusion (
					_ycfWidth,
					latentStateWeiner.incrementArray (dealerSeniorFundingReplicator.label())
				),
				_ycfWidth
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	private org.drip.measure.realization.JumpDiffusionVertex[]
		dealerSubordinateFundingReplicatorVertexArray (
			final org.drip.exposure.universe.MarketVertex initialMarketVertex,
			final org.drip.exposure.universe.LatentStateWeiner latentStateWeiner,
			final int terminalDate)
	{
		org.drip.exposure.evolver.PrimarySecurity dealerSubordinateFundingReplicator =
			_primarySecurityDynamicsContainer.dealerSubordinateFunding();

		double initialDealerSubordinateFundingReplicator =
			initialMarketVertex.dealer().subordinateFundingReplicator();

		if (null == dealerSubordinateFundingReplicator ||
			!org.drip.quant.common.NumberUtil.IsValid (initialDealerSubordinateFundingReplicator))
		{
			return null;
		}

		try
		{
			return dealerSubordinateFundingReplicator.evolver().vertexSequenceReverse (
				new org.drip.measure.realization.JumpDiffusionVertex (
					terminalDate,
					initialDealerSubordinateFundingReplicator,
					0.,
					false
				),
				org.drip.measure.realization.JumpDiffusionEdgeUnit.Diffusion (
					_ycfWidth,
					latentStateWeiner.incrementArray (dealerSubordinateFundingReplicator.label())
				),
				_ycfWidth
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	private org.drip.measure.realization.JumpDiffusionVertex[] clientFundingReplicatorVertexArray (
		final org.drip.exposure.universe.MarketVertex initialMarketVertex,
		final org.drip.exposure.universe.LatentStateWeiner latentStateWeiner,
		final int terminalDate)
	{
		org.drip.exposure.evolver.PrimarySecurity clientFundingReplicator =
			_primarySecurityDynamicsContainer.clientFunding();

		try
		{
			return clientFundingReplicator.evolver().vertexSequenceReverse (
				new org.drip.measure.realization.JumpDiffusionVertex (
					terminalDate,
					initialMarketVertex.client().seniorFundingReplicator(),
					0.,
					false
				),
				org.drip.measure.realization.JumpDiffusionEdgeUnit.Diffusion (
					_ycfWidth,
					latentStateWeiner.incrementArray (clientFundingReplicator.label())
				),
				_ycfWidth
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	private org.drip.measure.realization.JumpDiffusionVertex[] dealerHazardVertexArray (
		final org.drip.exposure.universe.MarketVertex initialMarketVertex,
		final org.drip.exposure.universe.LatentStateWeiner latentStateWeiner,
		final int terminalDate)
	{
		try
		{
			return _entityDynamicsContainer.dealerHazardRateEvolver().vertexSequence (
				new org.drip.measure.realization.JumpDiffusionVertex (
					terminalDate,
					initialMarketVertex.dealer().hazardRate(),
					0.,
					false
				),
				org.drip.measure.realization.JumpDiffusionEdgeUnit.Diffusion (
					_ycfWidth,
					latentStateWeiner.incrementArray (_entityDynamicsContainer.dealerHazardLabel())
				),
				_ycfWidth
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	private org.drip.measure.realization.JumpDiffusionVertex[] clientHazardVertexArray (
		final org.drip.exposure.universe.MarketVertex initialMarketVertex,
		final org.drip.exposure.universe.LatentStateWeiner latentStateWeiner,
		final int terminalDate)
	{
		try
		{
			return _entityDynamicsContainer.clientHazardRateEvolver().vertexSequence (
				new org.drip.measure.realization.JumpDiffusionVertex (
					terminalDate,
					initialMarketVertex.client().hazardRate(),
					0.,
					false
				),
				org.drip.measure.realization.JumpDiffusionEdgeUnit.Diffusion (
					_ycfWidth,
					latentStateWeiner.incrementArray (_entityDynamicsContainer.clientHazardLabel())
				),
				_ycfWidth
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	private org.drip.measure.realization.JumpDiffusionVertex[] dealerSeniorRecoveryVertexArray (
		final org.drip.exposure.universe.MarketVertex initialMarketVertex,
		final org.drip.exposure.universe.LatentStateWeiner latentStateWeiner,
		final int terminalDate)
	{
		try
		{
			return _entityDynamicsContainer.dealerSeniorRecoveryRateEvolver().vertexSequence (
				new org.drip.measure.realization.JumpDiffusionVertex (
					terminalDate,
					initialMarketVertex.dealer().seniorRecoveryRate(),
					0.,
					false
				),
				org.drip.measure.realization.JumpDiffusionEdgeUnit.Diffusion (
					_ycfWidth,
					latentStateWeiner.incrementArray (_entityDynamicsContainer.dealerSeniorRecoveryLabel())
				),
				_ycfWidth
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	private org.drip.measure.realization.JumpDiffusionVertex[] dealerSubordinateRecoveryVertexArray (
		final org.drip.exposure.universe.MarketVertex initialMarketVertex,
		final org.drip.exposure.universe.LatentStateWeiner latentStateWeiner,
		final int terminalDate)
	{
		org.drip.measure.process.DiffusionEvolver dealerSubordinateRecoveryRateEvolver =
			_entityDynamicsContainer.dealerSubordinateRecoveryRateEvolver();

		if (null == dealerSubordinateRecoveryRateEvolver)
		{
			return null;
		}

		try
		{
			return dealerSubordinateRecoveryRateEvolver.vertexSequence (
				new org.drip.measure.realization.JumpDiffusionVertex (
					terminalDate,
					initialMarketVertex.dealer().subordinateRecoveryRate(),
					0.,
					false
				),
				org.drip.measure.realization.JumpDiffusionEdgeUnit.Diffusion (
					_ycfWidth,
					latentStateWeiner.incrementArray
						(_entityDynamicsContainer.dealerSubordinateRecoveryLabel())
				),
				_ycfWidth
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	private org.drip.measure.realization.JumpDiffusionVertex[] clientRecoveryVertexArray (
		final org.drip.exposure.universe.MarketVertex initialMarketVertex,
		final org.drip.exposure.universe.LatentStateWeiner latentStateWeiner,
		final int terminalDate)
	{
		try
		{
			return _entityDynamicsContainer.clientRecoveryRateEvolver().vertexSequence (
				new org.drip.measure.realization.JumpDiffusionVertex (
					terminalDate,
					initialMarketVertex.client().seniorRecoveryRate(),
					0.,
					false
				),
				org.drip.measure.realization.JumpDiffusionEdgeUnit.Diffusion (
					_ycfWidth,
					latentStateWeiner.incrementArray (_entityDynamicsContainer.clientRecoveryLabel())
				),
				_ycfWidth
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * MarketVertexGenerator Constructor
	 * 
	 * @param spotDate The Spot Date
	 * @param eventDateArray Array of the Event Dates
	 * @param entityDynamicsContainer The Dealer/Client Entity Latent State Dynamics Container
	 * @param primarySecurityDynamicsContainer The Primary Security Dynamics Container
	 * @param latentStateDynamicsContainer The Latent State Dynamics Container
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public MarketVertexGenerator (
		final int spotDate,
		final int[] eventDateArray,
		final org.drip.exposure.evolver.EntityDynamicsContainer entityDynamicsContainer,
		final org.drip.exposure.evolver.PrimarySecurityDynamicsContainer primarySecurityDynamicsContainer,
		final org.drip.exposure.evolver.LatentStateDynamicsContainer latentStateDynamicsContainer)
		throws java.lang.Exception
	{
		if (0 >= (_spotDate = spotDate) ||
			null == (_eventDateArray = eventDateArray) ||
			null == (_entityDynamicsContainer = entityDynamicsContainer))
		{
			throw new java.lang.Exception ("MarketVertexGenerator Constructor => Invalid Inputs");
		}

		int eventVertexCount = _eventDateArray.length;
		_latentStateDynamicsContainer = latentStateDynamicsContainer;
		_primarySecurityDynamicsContainer = primarySecurityDynamicsContainer;
		_ycfWidth = 0 == eventVertexCount ? null : new double[eventVertexCount];

		if (0 == eventVertexCount ||
			0. >= (_ycfWidth[0] = ((double) (_eventDateArray[0] - _spotDate)) / 365.25))
		{
			throw new java.lang.Exception ("MarketVertexGenerator Constructor => Invalid Inputs");
		}

		for (int eventVertexIndex = 1; eventVertexIndex < eventVertexCount; ++eventVertexIndex)
		{
			if (0. >= (_ycfWidth[eventVertexIndex] = ((double) (_eventDateArray[eventVertexIndex] -
				_eventDateArray[eventVertexIndex - 1])) / 365.25))
			{
				throw new java.lang.Exception ("MarketVertexGenerator Constructor => Invalid Inputs");
			}
		}
	}

	/**
	 * Retrieve the Spot Date
	 * 
	 * @return The Spot Date
	 */

	public int spotDate()
	{
		return _spotDate;
	}

	/**
	 * Retrieve the Time Width Array
	 * 
	 * @return The Time Width Array
	 */

	public double[] timeWidth()
	{
		return _ycfWidth;
	}

	/**
	 * Retrieve the Entity Dynamics Container
	 * 
	 * @return The Entity Dynamics Container
	 */

	public org.drip.exposure.evolver.EntityDynamicsContainer entityDynamicsContainer()
	{
		return _entityDynamicsContainer;
	}

	/**
	 * Retrieve the Primary Security Dynamics Container
	 * 
	 * @return The Primary Security Dynamics Container
	 */

	public org.drip.exposure.evolver.PrimarySecurityDynamicsContainer primarySecurityDynamicsContainer()
	{
		return _primarySecurityDynamicsContainer;
	}

	/**
	 * Retrieve the Latent State Dynamics Container
	 * 
	 * @return The Latent State Dynamics Container
	 */

	public org.drip.exposure.evolver.LatentStateDynamicsContainer latentStateDynamicsContainer()
	{
		return _latentStateDynamicsContainer;
	}

	/**
	 * Retrieve the Vertex Date Array
	 * 
	 * @return The Vertex Date Array
	 */

	public int[] vertexDates()
	{
		int eventDateCount = _eventDateArray.length;
		int[] vertexDateArray = new int[eventDateCount + 1];
		vertexDateArray[0] = _spotDate;

		for (int i = 0; i < eventDateCount; ++i)
			vertexDateArray[i + 1] = _eventDateArray[i];

		return vertexDateArray;
	}

	/**
	 * Generate the Trajectory of the Simulated Market Vertexes
	 * 
	 * @param initialMarketVertex The Initial Market Vertex
	 * @param latentStateWeiner The Latent State Weiner Instance
	 * 
	 * @return The Trajectory of the Simulated Market Vertexes
	 */

	public java.util.Map<java.lang.Integer, org.drip.exposure.universe.MarketVertex> marketVertex (
		final org.drip.exposure.universe.MarketVertex initialMarketVertex,
		final org.drip.exposure.universe.LatentStateWeiner latentStateWeiner)
	{
		if (null == initialMarketVertex ||
			null == latentStateWeiner)
		{
			return null;
		}

		int latentStateCount = latentStateWeiner.stateCount();

		if (7 > latentStateCount)
		{
			return null;
		}

		org.drip.exposure.evolver.LatentStateVertexContainer latentStateVertexContainerInitial =
			initialMarketVertex.latentStateVertexContainer();

		java.util.List<org.drip.state.identifier.LatentStateLabel> latentStateLabelList = null ==
			latentStateVertexContainerInitial ? null : latentStateVertexContainerInitial.labelList();

		double clientSurvivalProbabilityExponent = 0.;
		double dealerSurvivalProbabilityExponent = 0.;
		int eventVertexCount = _eventDateArray.length;
		int terminalDate = _eventDateArray[eventVertexCount - 1];

		int latentStateLabelCount = null == latentStateLabelList ? 0 : latentStateLabelList.size();

		org.drip.measure.realization.JumpDiffusionVertex[] overnightReplicatorVertexArray =
			overnightReplicatorVertexArray (
				initialMarketVertex,
				latentStateWeiner,
				terminalDate
			);

		org.drip.measure.realization.JumpDiffusionVertex[] csaReplicatorVertexArray =
			csaReplicatorVertexArray (
				initialMarketVertex,
				latentStateWeiner,
				terminalDate
			);

		org.drip.measure.realization.JumpDiffusionVertex[] dealerSeniorFundingReplicatorVertexArray =
			dealerSeniorFundingReplicatorVertexArray (
				initialMarketVertex,
				latentStateWeiner,
				terminalDate
			);

		org.drip.measure.realization.JumpDiffusionVertex[] dealerSubordinateFundingReplicatorVertexArray =
			dealerSubordinateFundingReplicatorVertexArray (
				initialMarketVertex,
				latentStateWeiner,
				terminalDate
			);

		org.drip.measure.realization.JumpDiffusionVertex[] clientFundingReplicatorVertexArray =
			clientFundingReplicatorVertexArray (
				initialMarketVertex,
				latentStateWeiner,
				terminalDate
			);

		org.drip.measure.realization.JumpDiffusionVertex[] dealerHazardVertexArray =
			dealerHazardVertexArray (
				initialMarketVertex,
				latentStateWeiner,
				terminalDate
			);

		org.drip.measure.realization.JumpDiffusionVertex[] clientHazardVertexArray =
			clientHazardVertexArray (
				initialMarketVertex,
				latentStateWeiner,
				terminalDate
			);

		org.drip.measure.realization.JumpDiffusionVertex[] dealerSeniorRecoveryVertexArray =
			dealerSeniorRecoveryVertexArray (
				initialMarketVertex,
				latentStateWeiner,
				terminalDate
			);

		org.drip.measure.realization.JumpDiffusionVertex[] dealerSubordinateRecoveryVertexArray =
			dealerSubordinateRecoveryVertexArray (
				initialMarketVertex,
				latentStateWeiner,
				terminalDate
			);

		org.drip.measure.realization.JumpDiffusionVertex[] clientRecoveryVertexArray =
			clientRecoveryVertexArray (
				initialMarketVertex,
				latentStateWeiner,
				terminalDate
			);

		java.util.List<org.drip.measure.realization.JumpDiffusionVertex[]> latentStateVertexArrayList =
			latentStateVertexArrayList (
				latentStateLabelList,
				initialMarketVertex,
				latentStateWeiner,
				terminalDate
			);

		if (null == overnightReplicatorVertexArray ||
			null == csaReplicatorVertexArray ||
			null == dealerSeniorFundingReplicatorVertexArray ||
			null == clientFundingReplicatorVertexArray ||
			null == dealerHazardVertexArray ||
			null == clientHazardVertexArray ||
			null == dealerSeniorRecoveryVertexArray ||
			null == clientRecoveryVertexArray)
		{
			return null;
		}

		double initialCSAReplicator = csaReplicatorVertexArray[0].value();

		double initialOvernightReplicator = overnightReplicatorVertexArray[0].value();

		double initialDealerSeniorFundingReplicator = dealerSeniorFundingReplicatorVertexArray[0].value();

		double initialDealerSubordinateFundingReplicator =
			null == dealerSubordinateFundingReplicatorVertexArray ||
			null == dealerSubordinateFundingReplicatorVertexArray[0] ?
			java.lang.Double.NaN : dealerSubordinateFundingReplicatorVertexArray[0].value();

		double initialClientFundingReplicator = clientFundingReplicatorVertexArray[0].value();

		java.util.Map<java.lang.Integer, org.drip.exposure.universe.MarketVertex> marketVertexTrajectory =
			new java.util.TreeMap<java.lang.Integer, org.drip.exposure.universe.MarketVertex>();

		for (int eventVertexIndex = 1; eventVertexIndex <= eventVertexCount; ++eventVertexIndex)
		{
			double clientHazardRate = clientHazardVertexArray[eventVertexIndex].value();

			double dealerHazardRate = dealerHazardVertexArray[eventVertexIndex].value();

			double csaReplicatorFinish =  csaReplicatorVertexArray[eventVertexIndex].value();

			double overnightReplicatorFinish = overnightReplicatorVertexArray[eventVertexIndex].value();

			double clientFundingReplicatorFinish =
				clientFundingReplicatorVertexArray[eventVertexIndex].value();

			double dealerSeniorFundingReplicatorFinish =
				dealerSeniorFundingReplicatorVertexArray[eventVertexIndex].value();

			double timeWidth = _ycfWidth[eventVertexIndex - 1];
			double timeWidthReciprocal = 1. / timeWidth;
			clientSurvivalProbabilityExponent += clientHazardRate * timeWidth;
			dealerSurvivalProbabilityExponent += dealerHazardRate * timeWidth;

			double overnightRate = timeWidthReciprocal * java.lang.Math.log (overnightReplicatorFinish /
				initialOvernightReplicator);

			double dealerSubordinateFundingReplicatorFinish =
				null == dealerSubordinateFundingReplicatorVertexArray ||
				null == dealerSubordinateFundingReplicatorVertexArray[eventVertexIndex] ?
				java.lang.Double.NaN :
				dealerSubordinateFundingReplicatorVertexArray[eventVertexIndex].value();

			try
			{
				org.drip.exposure.universe.MarketVertexEntity dealerMarketVertex =
					new org.drip.exposure.universe.MarketVertexEntity (
						java.lang.Math.exp (-1. * dealerSurvivalProbabilityExponent),
						dealerHazardRate,
						dealerSeniorRecoveryVertexArray[eventVertexIndex].value(),
						timeWidthReciprocal * java.lang.Math.log (dealerSeniorFundingReplicatorFinish /
							initialDealerSeniorFundingReplicator) - overnightRate,
						dealerSeniorFundingReplicatorFinish,
						null == dealerSubordinateFundingReplicatorVertexArray ||
						null == dealerSubordinateRecoveryVertexArray[eventVertexIndex] ? java.lang.Double.NaN
							: dealerSubordinateRecoveryVertexArray[eventVertexIndex].value(),
						null == dealerSubordinateFundingReplicatorVertexArray ? java.lang.Double.NaN :
							timeWidthReciprocal *
							java.lang.Math.log (dealerSubordinateFundingReplicatorFinish /
								initialDealerSubordinateFundingReplicator) - overnightRate,
						dealerSubordinateFundingReplicatorFinish
					);

				org.drip.exposure.universe.MarketVertexEntity clientMarketVertex =
					new org.drip.exposure.universe.MarketVertexEntity (
						java.lang.Math.exp (-1. * clientSurvivalProbabilityExponent),
						clientHazardRate,
						clientRecoveryVertexArray[eventVertexIndex].value(),
						timeWidthReciprocal * java.lang.Math.log (clientFundingReplicatorFinish /
							initialClientFundingReplicator) - overnightRate,
						clientFundingReplicatorFinish,
						java.lang.Double.NaN,
						java.lang.Double.NaN,
						java.lang.Double.NaN
					);

				org.drip.exposure.evolver.LatentStateVertexContainer latentStateVertexContainer = new
					org.drip.exposure.evolver.LatentStateVertexContainer();

				if (null != latentStateVertexArrayList && null != latentStateLabelList)
				{
					for (int latentStateLabelIndex = 0; latentStateLabelIndex < latentStateLabelCount;
						++latentStateLabelIndex)
					{
						org.drip.state.identifier.LatentStateLabel latentStateLabel =
							latentStateLabelList.get (latentStateLabelIndex);

						if (null != latentStateLabel)
						{
							latentStateVertexContainer.addLatentStateValue (
								latentStateLabel,
								latentStateVertexArrayList.get
									(latentStateLabelIndex)[eventVertexIndex].value()
							);
						}
					}
				}

				org.drip.exposure.universe.MarketVertex marketVertex =
					new org.drip.exposure.universe.MarketVertex (
						new org.drip.analytics.date.JulianDate (_eventDateArray[eventVertexIndex - 1]),
						overnightRate,
						overnightReplicatorFinish,
						timeWidthReciprocal * java.lang.Math.log (csaReplicatorFinish / initialCSAReplicator)
							- overnightRate,
						csaReplicatorFinish,
						dealerMarketVertex,
						clientMarketVertex,
						latentStateVertexContainer
					);

				marketVertexTrajectory.put (
					_eventDateArray[eventVertexIndex - 1],
					marketVertex
				);
			}
			catch (java.lang.Exception e)
			{
				e.printStackTrace();

				return null;
			}

			initialCSAReplicator = csaReplicatorFinish;
			initialOvernightReplicator = overnightReplicatorFinish;
			initialClientFundingReplicator = clientFundingReplicatorFinish;
			initialDealerSeniorFundingReplicator = dealerSeniorFundingReplicatorFinish;
			initialDealerSubordinateFundingReplicator = dealerSubordinateFundingReplicatorFinish;
		}

		try
		{
			double clientHazardVertexEpochal = clientHazardVertexArray[0].value();

			double dealerHazardVertexEpochal = dealerHazardVertexArray[0].value();

			marketVertexTrajectory.put (
				initialMarketVertex.anchorDate().julian(),
				new org.drip.exposure.universe.MarketVertex (
					new org.drip.analytics.date.JulianDate (_spotDate),
					0.,
					overnightReplicatorVertexArray[0].value(),
					0.,
					csaReplicatorVertexArray[0].value(),
					new org.drip.exposure.universe.MarketVertexEntity (
						java.lang.Math.exp (-1. * _ycfWidth[0] * dealerHazardVertexEpochal),
						dealerHazardVertexEpochal,
						dealerSeniorRecoveryVertexArray[0].value(),
						initialMarketVertex.dealer().seniorFundingSpread(),
						dealerSeniorFundingReplicatorVertexArray[0].value(),
						null == dealerSubordinateFundingReplicatorVertexArray ||
						null == dealerSubordinateRecoveryVertexArray[0] ? java.lang.Double.NaN :
							dealerSubordinateRecoveryVertexArray[0].value(),
						null == dealerSubordinateFundingReplicatorVertexArray ? java.lang.Double.NaN :
							initialMarketVertex.dealer().subordinateFundingSpread(),
						null == dealerSubordinateFundingReplicatorVertexArray ||
							null == dealerSubordinateFundingReplicatorVertexArray[0] ?
							java.lang.Double.NaN :
							dealerSubordinateFundingReplicatorVertexArray[0].value()
					),
					new org.drip.exposure.universe.MarketVertexEntity (
						java.lang.Math.exp (-1. * _ycfWidth[0] * clientHazardVertexEpochal),
						clientHazardVertexEpochal,
						clientRecoveryVertexArray[0].value(),
						initialMarketVertex.client().seniorFundingSpread(),
						clientFundingReplicatorVertexArray[0].value(),
						java.lang.Double.NaN,
						java.lang.Double.NaN,
						java.lang.Double.NaN
					),
					initialMarketVertex.latentStateVertexContainer()
				)
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();

			return null;
		}

		return marketVertexTrajectory;
	}
}
