
package org.drip.xva.basel;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
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
 * <i>OTCAccountingModusFVAFDA</i> implements the Basel Accounting Scheme using the FVA/FDA Specification of
 * the Streamlined Accounting Framework for OTC Derivatives, as described in Albanese and Andersen (2014).
 * The References are:
 *
 *  <br><br>
 *  <ul>
 *  	<li>
 *  		Albanese, C., and L. Andersen (2014): Accounting for OTC Derivatives: Funding Adjustments and the
 *  			Re-Hypothecation Option https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2482955
 *  			<b>eSSRN</b>
 *  	</li>
 *  	<li>
 *  		BCBS (2012): <i>Consultative Document: Application of Own Credit Risk Adjustments to
 *  			Derivatives</i> <b>Basel Committee on Banking Supervision</b>
 *  	</li>
 *  	<li>
 *  		Burgard, C., and M. Kjaer (2014): PDE Representations of Derivatives with Bilateral Counter-party
 *  			Risk and Funding Costs <i>Journal of Credit Risk</i> <b>7 (3)</b> 1-19
 *  	</li>
 *  	<li>
 *  		Burgard, C., and M. Kjaer (2014): In the Balance <i>Risk</i> <b>24 (11)</b> 72-75
 *  	</li>
 *  	<li>
 *  		Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives Pricing
 *  			<i>Risk</i> <b>21 (2)</b> 97-102
 *  	</li>
 *  </ul>
 *
 *  <br><br>
 *  <ul>
 *		<li><b>Project</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva">XVA</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/basel">Basel</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/XVA">XVA Analytics Library</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class OTCAccountingModusFVAFDA extends org.drip.xva.basel.OTCAccountingModus
{

	/**
	 * OTCAccountingModusFVAFDA Constructor
	 * 
	 * @param exposureAdjustmentAggregator The Counter Party Group Aggregator Instance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public OTCAccountingModusFVAFDA (
		final org.drip.xva.gross.ExposureAdjustmentAggregator exposureAdjustmentAggregator)
		throws java.lang.Exception
	{
		super (exposureAdjustmentAggregator);
	}

	@Override public double contraAssetAdjustment()
	{
		org.drip.xva.gross.ExposureAdjustmentAggregator exposureAdjustmentAggregator = aggregator();

		return exposureAdjustmentAggregator.ucva().amount() + exposureAdjustmentAggregator.fva().amount();
	}

	@Override public double contraLiabilityAdjustment()
	{
		org.drip.xva.gross.ExposureAdjustmentAggregator exposureAdjustmentAggregator = aggregator();

		return exposureAdjustmentAggregator.cvacl().amount() + exposureAdjustmentAggregator.ftddva().amount()
			+ exposureAdjustmentAggregator.fda().amount();
	}

	@Override public org.drip.xva.basel.OTCAccountingPolicy feePolicy (
		final org.drip.xva.gross.ExposureAdjustmentAggregator exposureAdjustmentAggregatorNext)
	{
		if (null == exposureAdjustmentAggregatorNext)
		{
			return null;
		}

		org.drip.xva.gross.ExposureAdjustmentAggregator exposureAdjustmentAggregator = aggregator();

		double collateralVAChange = exposureAdjustmentAggregatorNext.colva().amount() -
			exposureAdjustmentAggregator.colva().amount();

		double contraLiabilityChange = exposureAdjustmentAggregatorNext.ftddva().amount() +
			exposureAdjustmentAggregatorNext.fda().amount() +
			exposureAdjustmentAggregatorNext.cvacl().amount() -
			exposureAdjustmentAggregator.ftddva().amount() - exposureAdjustmentAggregator.fda().amount() -
			exposureAdjustmentAggregator.cvacl().amount();

		try
		{
			return new org.drip.xva.basel.OTCAccountingPolicy (
				exposureAdjustmentAggregatorNext.ucva().amount() +
				exposureAdjustmentAggregatorNext.fva().amount() -
				exposureAdjustmentAggregator.ucva().amount() -
				exposureAdjustmentAggregator.fva().amount() + collateralVAChange,
				0.,
				contraLiabilityChange,
				contraLiabilityChange
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
