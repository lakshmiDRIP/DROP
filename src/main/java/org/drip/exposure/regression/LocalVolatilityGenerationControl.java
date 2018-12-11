
package org.drip.exposure.regression;

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
 * <i>LocalVolatilityGenerationControl</i> holds the Parameters the control the Calculation of the Local
 * Volatility in the Pykhtin (2009) Brownian Bridge Calibration. The References are:
 *  
 * <br><br>
 *  	<ul>
 *  		<li>
 *  			Andersen, L. B. G., M. Pykhtin, and A. Sokol (2017): Re-thinking Margin Period of Risk
 *  				https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2902737 <b>eSSRN</b>
 *  		</li>
 *  		<li>
 *  			Andersen, L. B. G., M. Pykhtin, and A. Sokol (2017): Credit Exposure in the Presence of
 *  				Initial Margin https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2806156 <b>eSSRN</b>
 *  		</li>
 *  		<li>
 *  			Albanese, C., and L. Andersen (2014): Accounting for OTC Derivatives: Funding Adjustments and
 *  				the Re-Hypothecation Option https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2482955
 *  				<b>eSSRN</b>
 *  		</li>
 *  		<li>
 *  			Burgard, C., and M. Kjaer (2017): Derivatives Funding, Netting, and Accounting
 *  				https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2534011 <b>eSSRN</b>
 *  		</li>
 *  		<li>
 *  			Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives
 *  				Pricing <i>Risk</i> <b>21 (2)</b> 97-102
 *  		</li>
 *  	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ExposureAnalyticsLibrary.md">Exposure Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure">Exposure</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/regression">Regression</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class LocalVolatilityGenerationControl
{

	/**
	 * The Pyhktin (2009) Empirical Floor
	 */

	public static final int PYKHTIN_2009_EMPIRICAL_FLOOR = 20;

	/**
	 * The Pyhktin (2009) Empirical Ceiling Factor
	 */

	public static final double PYKHTIN_2009_EMPIRICAL_CEILING_FACTOR = 0.05;

	/**
	 * The Local Volatility Smooth Floor Bias
	 */

	public static final double LOCAL_VOLATILITY_SMOOTHING_FLOOR_BIAS = 0.90;

	private double[] _uniformCPDArray = null;
	private int _localVolatilityIndexShift = -1;
	private double[] _impliedBrownianVariateArray = null;
	private org.drip.spline.params.SegmentCustomBuilderControl[] _segmentCustomBuilderControlArray = null;

	/**
	 * Construct a Standard Instance of LocalVolatilityGenerationControl
	 * 
	 * @param ensembleSize Size of the Distribution Ensemble
	 * 
	 * @return Standard Instance of LocalVolatilityGenerationControl
	 */

	public static final LocalVolatilityGenerationControl Standard (
		final int ensembleSize)
	{
		if (PYKHTIN_2009_EMPIRICAL_FLOOR > ensembleSize)
		{
			return null;
		}

		double[] uniformCPDArray = new double[ensembleSize];
		double[] impliedBrownianVariateArray = new double[ensembleSize];
		int localVolatilityIndexShift = (int) (LOCAL_VOLATILITY_SMOOTHING_FLOOR_BIAS *
			PYKHTIN_2009_EMPIRICAL_FLOOR + (1 - LOCAL_VOLATILITY_SMOOTHING_FLOOR_BIAS) * ensembleSize);

		if (PYKHTIN_2009_EMPIRICAL_FLOOR > localVolatilityIndexShift)
		{
			return null;
		}

		for (int realizationCoordinate = 0;
			realizationCoordinate < ensembleSize;
			++realizationCoordinate)
		{
			try
			{
				impliedBrownianVariateArray[realizationCoordinate] =
					org.drip.measure.gaussian.NormalQuadrature.InverseCDF
						(uniformCPDArray[realizationCoordinate] = (((double) realizationCoordinate) + 0.5) /
							((double) ensembleSize));
			}
			catch (java.lang.Exception e)
			{
				e.printStackTrace();

				return null;
			}
		}

		try
		{
			org.drip.spline.params.SegmentCustomBuilderControl[] segmentCustomBuilderControlArray = new
				org.drip.spline.params.SegmentCustomBuilderControl[ensembleSize - 1];
			org.drip.spline.params.SegmentCustomBuilderControl segmentCustomBuilderControl = new
				org.drip.spline.params.SegmentCustomBuilderControl (
					org.drip.spline.stretch.MultiSegmentSequenceBuilder.BASIS_SPLINE_POLYNOMIAL,
					new org.drip.spline.basis.PolynomialFunctionSetParams (2),
					org.drip.spline.params.SegmentInelasticDesignControl.Create (
						0,
						2
					),
					new org.drip.spline.params.ResponseScalingShapeControl (
						true,
						new org.drip.function.r1tor1.QuadraticRationalShapeControl (0.)
					),
					null
				);

			for (int realizationCoordinate = 0;
				realizationCoordinate < ensembleSize - 1;
				++realizationCoordinate)
			{
				segmentCustomBuilderControlArray[realizationCoordinate] = segmentCustomBuilderControl;
			}

			return new LocalVolatilityGenerationControl (
				localVolatilityIndexShift,
				uniformCPDArray,
				impliedBrownianVariateArray,
				segmentCustomBuilderControlArray
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * LocalVolatilityGenerationControl Constructor
	 * 
	 * @param localVolatilityIndexShift The Local Volatility Index Shift
	 * @param uniformCPDArray The Uniform Cumulative Probability Density Array
	 * @param impliedBrownianVariateArray The Implied Brownian Variate Array
	 * @param segmentCustomBuilderControlArray Array of Segment Builder Control
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public LocalVolatilityGenerationControl (
		final int localVolatilityIndexShift,
		final double[] uniformCPDArray,
		final double[] impliedBrownianVariateArray,
		final org.drip.spline.params.SegmentCustomBuilderControl[] segmentCustomBuilderControlArray)
		throws java.lang.Exception
	{
		if (0 >= (_localVolatilityIndexShift = localVolatilityIndexShift) ||
			null == (_uniformCPDArray = uniformCPDArray) ||
			null == (_impliedBrownianVariateArray = impliedBrownianVariateArray) ||
			null == (_segmentCustomBuilderControlArray = segmentCustomBuilderControlArray))
		{
			throw new java.lang.Exception ("LocalVolatilityGenerationControl Constructor => Invalid Inputs");
		}

		int uniformCPDArraySize = _uniformCPDArray.length;

		if (0 == uniformCPDArraySize ||
			uniformCPDArraySize != _impliedBrownianVariateArray.length ||
			uniformCPDArraySize != _segmentCustomBuilderControlArray.length + 1)
		{
			throw new java.lang.Exception ("LocalVolatilityGenerationControl Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Local Volatility Index Shift
	 * 
	 * @return The Local Volatility Index Shift
	 */

	public int localVolatilityIndexShift()
	{
		return _localVolatilityIndexShift;
	}

	/**
	 * Retrieve the Uniform Cumulative Probability Density Array
	 * 
	 * @return The Uniform Cumulative Probability Density Array
	 */

	public double[] uniformCPDArray()
	{
		return _uniformCPDArray;
	}

	/**
	 * Retrieve the Implied Brownian Variate Array
	 * 
	 * @return The Implied Brownian Variate Array
	 */

	public double[] impliedBrownianVariateArray()
	{
		return _impliedBrownianVariateArray;
	}

	/**
	 * Retrieve the Custom Segment Builder Control Array
	 * 
	 * @return The Custom Segment Builder Control Array
	 */

	public org.drip.spline.params.SegmentCustomBuilderControl[] segmentCustomBuilderControlArray()
	{
		return _segmentCustomBuilderControlArray;
	}
}
