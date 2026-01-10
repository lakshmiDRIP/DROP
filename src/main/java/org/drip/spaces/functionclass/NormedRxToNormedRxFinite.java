
package org.drip.spaces.functionclass;

import org.drip.function.definition.R1ToR1;
import org.drip.numerical.common.NumberUtil;
import org.drip.spaces.cover.FunctionClassCoveringBounds;
import org.drip.spaces.cover.MaureyOperatorCoveringBounds;
import org.drip.spaces.cover.ScaleSensitiveCoveringBounds;
import org.drip.spaces.instance.GeneralizedValidatedVector;
import org.drip.spaces.instance.ValidatedR1;
import org.drip.spaces.instance.ValidatedRd;
import org.drip.spaces.metric.GeneralizedMetricVectorSpace;
import org.drip.spaces.metric.R1Field;
import org.drip.spaces.metric.RdBanach;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2025 Lakshmi Krishnamurthy
 * Copyright (C) 2024 Lakshmi Krishnamurthy
 * Copyright (C) 2023 Lakshmi Krishnamurthy
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
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
 * <i>NormedRxToNormedRxFinite</i> exposes the Space of Functions that are a Transform from the Normed
 * 	R<sup>x</sup> To Normed R<sup>d</sup> Spaces. The References are:
 *
 * <br><br>
 *  <ul>
 *  	<li>
 *  		Carl, B. (1985): Inequalities of the Bernstein-Jackson type and the Degree of Compactness of
 *  			Operators in Banach Spaces <i>Annals of the Fourier Institute</i> <b>35 (3)</b> 79-118
 *  	</li>
 *  	<li>
 *  		Carl, B., and I. Stephani (1990): <i>Entropy, Compactness, and the Approximation of Operators</i>
 *  			<b>Cambridge University Press</b> Cambridge UK 
 *  	</li>
 *  	<li>
 *  		Williamson, R. C., A. J. Smola, and B. Scholkopf (2000): Entropy Numbers of Linear Function
 *  			Classes, in: <i>Proceedings of the 13th Annual Conference on Computational Learning
 *  				Theory</i> <b>ACM</b> New York
 *  	</li>
 *  </ul>
 *
 *  It provides the following Functionality:
 *
 *  <ul>
 * 		<li>Retrieve the Input Vector Space</li>
 * 		<li>Retrieve the Output Vector Space</li>
 * 		<li>Compute the Operator Population Metric Norm</li>
 * 		<li>Compute the Operator Population Supremum Norm</li>
 * 		<li>Compute the Operator Sample Metric Norm</li>
 * 		<li>Compute the Operator Sample Supremum Norm</li>
 * 		<li>Retrieve the Agnostic Covering Number Upper/Lower Bounds for the Function Class</li>
 * 		<li>Retrieve the Maurey Constant</li>
 * 		<li>Retrieve the Scale-Sensitive Covering Number Upper/Lower Bounds given the Specified Sample for the Function Class</li>
 * 		<li>Compute the Output Dimension</li>
 * 		<li>Compute the Maurey Covering Number Upper Bounds for Operator Population Metric Norm</li>
 * 		<li>Compute the Maurey Covering Number Upper Bounds for Operator Population Supremum Norm</li>
 * 		<li>Compute the Maurey Covering Number Upper Bounds for Operator Sample Metric Norm</li>
 * 		<li>Compute the Maurey Covering Number Upper Bounds for Operator Sample Supremum Norm</li>
 *  </ul>
 *
 *  <br>
 *  <style>table, td, th {
 *  	padding: 1px; border: 2px solid #008000; border-radius: 8px; background-color: #dfff00;
 *		text-align: center; color:  #0000ff;
 *  }
 *  </style>
 *  
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/StatisticalLearningLibrary.md">Statistical Learning Library</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces/README.md">R<sup>1</sup> and R<sup>d</sup> Vector/Tensor Spaces (Validated and/or Normed), and Function Classes</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces/functionclass/README.md">Normed Finite Spaces Function Class</a></td></tr>
 *  </table>
 *
 * @author Lakshmi Krishnamurthy
 */

public abstract class NormedRxToNormedRxFinite
{
	private double _maureyConstant = Double.NaN;

	protected NormedRxToNormedRxFinite (
		final double maureyConstant)
		throws Exception
	{
		if (!NumberUtil.IsValid (_maureyConstant = maureyConstant) || 0. >= _maureyConstant) {
			throw new Exception ("NormedRxToNormedRxFinite ctr => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Input Vector Space
	 * 
	 * @return The Input Vector Space
	 */

	public abstract GeneralizedMetricVectorSpace inputMetricVectorSpace();

	/**
	 * Retrieve the Output Vector Space
	 * 
	 * @return The Output Vector Space
	 */

	public abstract GeneralizedMetricVectorSpace outputMetricVectorSpace();

	/**
	 * Compute the Operator Population Metric Norm
	 * 
	 * @return The Operator Population Metric Norm
	 * 
	 * @throws Exception Thrown if the Operator Norm cannot be computed
	 */

	public abstract double operatorPopulationMetricNorm()
		throws Exception;

	/**
	 * Compute the Operator Population Supremum Norm
	 * 
	 * @return The Operator Population Supremum Norm
	 * 
	 * @throws Exception Thrown if the Operator Population Supremum Norm cannot be computed
	 */

	public abstract double operatorPopulationSupremumNorm()
		throws Exception;

	/**
	 * Compute the Operator Sample Metric Norm
	 * 
	 * @param generalizedValidatedVector The Validated Vector Space Instance
	 * 
	 * @return The Operator Sample Metric Norm
	 * 
	 * @throws Exception Thrown if the Operator Norm cannot be computed
	 */

	public abstract double operatorSampleMetricNorm (
		final GeneralizedValidatedVector generalizedValidatedVector
	) throws Exception;

	/**
	 * Compute the Operator Sample Supremum Norm
	 * 
	 * @param generalizedValidatedVector The Validated Vector Space Instance
	 * 
	 * @return The Operator Sample Supremum Norm
	 * 
	 * @throws Exception Thrown if the Operator Sample Supremum Norm cannot be computed
	 */

	public abstract double operatorSampleSupremumNorm (
		final GeneralizedValidatedVector generalizedValidatedVector
	) throws Exception;

	/**
	 * Retrieve the Agnostic Covering Number Upper/Lower Bounds for the Function Class
	 * 
	 * @return The Agnostic Covering Number Upper/Lower Bounds for the Function Class
	 */

	public abstract FunctionClassCoveringBounds agnosticCoveringNumberBounds();

	/**
	 * Retrieve the Maurey Constant
	 * 
	 * @return The Maurey Constant
	 */

	public double maureyConstant()
	{
		return _maureyConstant;
	}

	/**
	 * Retrieve the Scale-Sensitive Covering Number Upper/Lower Bounds given the Specified Sample for the
	 *  Function Class
	 * 
	 * @param generalizedValidatedVector The Validated Instance Vector Sequence
	 * @param r1ToR1FatShatteringFunction The Cover Fat Shattering Coefficient R<sup>1</sup> To R<sup>1</sup>
	 * 
	 * @return The Scale-Sensitive Covering Number Upper/Lower Bounds given the Specified Sample for the
	 *  Function Class
	 */

	public FunctionClassCoveringBounds scaleSensitiveCoveringBounds (
		final GeneralizedValidatedVector generalizedValidatedVector,
		final R1ToR1 r1ToR1FatShatteringFunction)
	{
		if (null == generalizedValidatedVector || null == r1ToR1FatShatteringFunction) {
			return null;
		}

		int sampleSize = -1;

		if (generalizedValidatedVector instanceof ValidatedR1) {
			double[] instanceArray = ((ValidatedR1) generalizedValidatedVector).instance();

			if (null == instanceArray) {
				return null;
			}

			sampleSize = instanceArray.length;
		} else if (generalizedValidatedVector instanceof ValidatedRd) {
			double[][] instanceGrid = ((ValidatedRd) generalizedValidatedVector).instance();

			if (null == instanceGrid) {
				return null;
			}

			sampleSize = instanceGrid.length;
		}

		try {
			return new ScaleSensitiveCoveringBounds (r1ToR1FatShatteringFunction, sampleSize);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Compute the Output Dimension
	 * 
	 * @return The Output Dimension
	 * 
	 * @throws Exception Thrown if the Output Dimension is Invalid
	 */

	public int outputDimension()
		throws Exception
	{
		GeneralizedMetricVectorSpace generalizedMetricVectorSpaceOutput = outputMetricVectorSpace();

		if (!(generalizedMetricVectorSpaceOutput instanceof R1Field) &&
			!(generalizedMetricVectorSpaceOutput instanceof RdBanach))
		{
			throw new Exception ("NormedRxToNormedRxFinite::dimension => Invalid Inputs");
		}

		return generalizedMetricVectorSpaceOutput instanceof R1Field ? 1 :
			((RdBanach) generalizedMetricVectorSpaceOutput).dimension();
	}

	/**
	 * Compute the Maurey Covering Number Upper Bounds for Operator Population Metric Norm
	 * 
	 * @return The Maurey Operator Covering Number Upper Bounds Instance Corresponding to the Operator
	 *  Population Metric Norm
	 */

	public MaureyOperatorCoveringBounds populationMetricCoveringBounds()
	{
		try {
			return new MaureyOperatorCoveringBounds (
				_maureyConstant,
				outputDimension(),
				operatorPopulationMetricNorm()
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Compute the Maurey Covering Number Upper Bounds for Operator Population Supremum Norm
	 * 
	 * @return The Maurey Operator Covering Number Upper Bounds Instance Corresponding to the Operator
	 *  Population Supremum Norm
	 */

	public MaureyOperatorCoveringBounds populationSupremumCoveringBounds()
	{
		try {
			return new MaureyOperatorCoveringBounds (
				_maureyConstant,
				outputDimension(),
				operatorPopulationSupremumNorm()
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Compute the Maurey Covering Number Upper Bounds for Operator Sample Metric Norm
	 * 
	 * @param generalizedValidatedVector The Validated Vector Space Instance
	 * 
	 * @return The Maurey Operator Covering Number Upper Bounds Instance Corresponding to the Operator Sample
	 *  Metric Norm
	 */

	public MaureyOperatorCoveringBounds sampleMetricCoveringBounds (
		final GeneralizedValidatedVector generalizedValidatedVector)
	{
		try {
			return new MaureyOperatorCoveringBounds (
				_maureyConstant,
				outputDimension(),
				operatorSampleMetricNorm (generalizedValidatedVector)
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Compute the Maurey Covering Number Upper Bounds for Operator Sample Supremum Norm
	 * 
	 * @param generalizedValidatedVector The Validated Vector Space Instance
	 * 
	 * @return The Maurey Operator Covering Number Upper Bounds Instance Corresponding to the Operator Sample
	 *  Supremum Norm
	 */

	public MaureyOperatorCoveringBounds sampleSupremumCoveringBounds (
		final GeneralizedValidatedVector generalizedValidatedVector)
	{
		try {
			return new MaureyOperatorCoveringBounds (
				_maureyConstant,
				outputDimension(),
				operatorSampleSupremumNorm (generalizedValidatedVector)
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
