
package org.drip.xva.proto;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
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
 * <i>PositionSchemaSpecification</i> contains the Specifications of a Position Schema. The References are:
 *
 *  <br><br>
 *  <ul>
 *  	<li>
 *  		Burgard, C., and M. Kjaer (2014): PDE Representations of Derivatives with Bilateral Counter-party
 *  			Risk and Funding Costs <i>Journal of Credit Risk</i> <b>7 (3)</b> 1-19
 *  	</li>
 *  	<li>
 *  		Burgard, C., and M. Kjaer (2014): In the Balance <i>Risk</i> <b>24 (11)</b> 72-75
 *  	</li>
 *  	<li>
 *  		Gregory, J. (2009): Being Two-faced over Counter-party Credit Risk <i>Risk</i> <b>20 (2)</b>
 *  			86-90
 *  	</li>
 *  	<li>
 *  		Li, B., and Y. Tang (2007): <i>Quantitative Analysis, Derivatives Modeling, and Trading
 *  			Strategies in the Presence of Counter-party Credit Risk for the Fixed Income Market</i>
 *  			<b>World Scientific Publishing</b> Singapore
 *  	</li>
 *  	<li>
 *  		Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives Pricing
 *  			<i>Risk</i> <b>21 (2)</b> 97-102
 *  	</li>
 *  </ul>
 *
 *  <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/XVAAnalyticsLibrary.md">XVA Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva">XVA</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/proto">Proto</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class PositionSchemaSpecification extends org.drip.xva.proto.ObjectSpecification
{
	private org.drip.xva.proto.FundingGroupSpecification _fundingGroupSpecification = null;
	private org.drip.xva.proto.PositionGroupSpecification _positionGroupSpecification = null;
	private org.drip.xva.proto.CreditDebtGroupSpecification _creditDebtGroupSpecification = null;
	private org.drip.xva.proto.CollateralGroupSpecification _collateralGroupSpecification = null;

	/**
	 * PositionSchemaSpecification Constructor
	 * 
	 * @param id The Position Group ID
	 * @param name The Position Group Name
	 * @param positionGroupSpecification The Position Group Specification
	 * @param collateralGroupSpecification The Collateral Group Specification
	 * @param creditDebtGroupSpecification The Credit Debt Group Specification
	 * @param fundingGroupSpecification The Funding Group Specification
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public PositionSchemaSpecification (
		final java.lang.String id,
		final java.lang.String name,
		final org.drip.xva.proto.PositionGroupSpecification positionGroupSpecification,
		final org.drip.xva.proto.CollateralGroupSpecification collateralGroupSpecification,
		final org.drip.xva.proto.CreditDebtGroupSpecification creditDebtGroupSpecification,
		final org.drip.xva.proto.FundingGroupSpecification fundingGroupSpecification)
		throws java.lang.Exception
	{
		super (
			id,
			name
		);

		if (null == (_positionGroupSpecification = positionGroupSpecification) ||
			null == (_collateralGroupSpecification = collateralGroupSpecification) ||
			null == (_creditDebtGroupSpecification = creditDebtGroupSpecification) ||
			null == (_fundingGroupSpecification = fundingGroupSpecification))
		{
			throw new java.lang.Exception ("PositionSchemaSpecification Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Margin Group Specification
	 * 
	 * @return The Margin Group Specification
	 */

	public org.drip.xva.proto.PositionGroupSpecification positionGroupSpecification()
	{
		return _positionGroupSpecification;
	}

	/**
	 * Retrieve the Collateral Group Specification
	 * 
	 * @return The Collateral Group Specification
	 */

	public org.drip.xva.proto.CollateralGroupSpecification collateralGroupSpecification()
	{
		return _collateralGroupSpecification;
	}

	/**
	 * Retrieve the Credit Debt Group Specification
	 * 
	 * @return The Credit Debt Group Specification
	 */

	public org.drip.xva.proto.CreditDebtGroupSpecification creditDebtGroupSpecification()
	{
		return _creditDebtGroupSpecification;
	}

	/**
	 * Retrieve the Funding Group Specification
	 * 
	 * @return The Funding Group Specification
	 */

	public org.drip.xva.proto.FundingGroupSpecification fundingGroupSpecification()
	{
		return _fundingGroupSpecification;
	}
}
