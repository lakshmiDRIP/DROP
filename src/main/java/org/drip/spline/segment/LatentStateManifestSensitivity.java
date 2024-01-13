
package org.drip.spline.segment;

import org.drip.numerical.common.NumberUtil;
import org.drip.spline.params.PreceedingManifestSensitivityControl;

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
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
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
 * <i>LatentStateManifestSensitivity</i> contains the Manifest Sensitivity generation control parameters and
 * 	the Manifest Sensitivity outputs related to the given Segment.
 *
 * <br>
 *  <ul>
 *  	<li><i>LatentStateManifestSensitivity</i> Constructor</li>
 * 		<li>Set the Array containing the Sensitivities of the Basis Coefficients to the Local Manifest Measure</li>
 * 		<li>Get the Array containing the Sensitivities of the Basis Coefficients to the Local Manifest Measure</li>
 * 		<li>Set the Array containing the Sensitivities of the Basis Coefficients to the Preceding Manifest Measure</li>
 * 		<li>Get the Array containing the Sensitivities of the Basis Coefficients to the Preceding Manifest Measure</li>
 * 		<li>Set the Sensitivity of the Segment Response to the Preceding Manifest Measure</li>
 * 		<li>Get the Sensitivity of the Segment Response to the Preceding Manifest Measure</li>
 * 		<li>Get the Preceding Manifest Measure Sensitivity Control Parameters</li>
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
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/README.md">Basis Splines and Linear Compounders across a Broad Family of Spline Basis Functions</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/segment/README.md">Flexure Penalizing Best Fit Segment</a></td></tr>
 *  </table>
 *  <br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class LatentStateManifestSensitivity
{
	private double _responseToPrecedingManifestSensitivity = Double.NaN;
	private double[] _basisCoefficientToLocalManifestSensitivityArray = null;
	private double[] _basisCoefficientToPrecedingManifestSensitivityArray = null;
	private PreceedingManifestSensitivityControl _precedingManifestSensitivityControl = null;

	/**
	 * <i>LatentStateManifestSensitivity</i> constructor
	 * 
	 * @param precedingManifestSensitivityControl Preceding Manifest Measure Sensitivity Control Parameters
	 * 
	 * @throws Exception Thrown if the Inputs are invalid
	 */

	public LatentStateManifestSensitivity (
		final PreceedingManifestSensitivityControl precedingManifestSensitivityControl)
		throws Exception
	{
		if (null == (_precedingManifestSensitivityControl = precedingManifestSensitivityControl)) {
			_precedingManifestSensitivityControl = new PreceedingManifestSensitivityControl (true, 0, null);
		}
	}

	/**
	 * Set the Array containing the Sensitivities of the Basis Coefficients to the Local Manifest Measure
	 * 
	 * @param basisCoefficientToLocalManifestSensitivityArray The Array containing the Sensitivities of the
	 *  Basis Coefficients to the Local Manifest Measure
	 * 
	 * @return TRUE - Basis Coefficient Manifest Measure Sensitivity Array Entries successfully set
	 */

	public boolean setDBasisCoeffDLocalManifest (
		final double[] basisCoefficientToLocalManifestSensitivityArray)
	{
		if (null == basisCoefficientToLocalManifestSensitivityArray) {
			return false;
		}

		int coefficientCount = basisCoefficientToLocalManifestSensitivityArray.length;
		_basisCoefficientToLocalManifestSensitivityArray = new double[coefficientCount];

		if (0 == coefficientCount) {
			return false;
		}

		for (int coefficientIndex = 0; coefficientIndex < coefficientCount; ++coefficientIndex) {
			if (!NumberUtil.IsValid (
				_basisCoefficientToLocalManifestSensitivityArray[coefficientIndex] =
				basisCoefficientToLocalManifestSensitivityArray[coefficientIndex]
			)) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Get the Array containing the Sensitivities of the Basis Coefficients to the Local Manifest Measure
	 * 
	 * @return The Array containing the Sensitivities of the Basis Coefficients to the Local Manifest Measure
	 */

	public double[] getDBasisCoeffDLocalManifest()
	{
		return _basisCoefficientToLocalManifestSensitivityArray;
	}

	/**
	 * Set the Array containing the Sensitivities of the Basis Coefficients to the Preceding Manifest
	 * 	Measure
	 * 
	 * @param basisCoefficientToPrecedingManifestSensitivityArray The Array containing the Sensitivities of
	 *  the Basis Coefficients to the Preceding Manifest Measure
	 * 
	 * @return TRUE - Array Entries successfully set
	 */

	public boolean setDBasisCoeffDPreceedingManifest (
		final double[] basisCoefficientToPrecedingManifestSensitivityArray)
	{
		if (null == basisCoefficientToPrecedingManifestSensitivityArray) {
			return false;
		}

		int coefficientCount = basisCoefficientToPrecedingManifestSensitivityArray.length;
		_basisCoefficientToPrecedingManifestSensitivityArray = new double[coefficientCount];

		if (0 == coefficientCount) {
			return false;
		}

		for (int coefficientIndex = 0; coefficientIndex < coefficientCount; ++coefficientIndex) {
			if (!NumberUtil.IsValid (
				_basisCoefficientToPrecedingManifestSensitivityArray[coefficientIndex] =
				basisCoefficientToPrecedingManifestSensitivityArray[coefficientIndex]
			)) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Get the Array containing the Sensitivities of the Basis Coefficients to the Preceding Manifest
	 *	Measure
	 * 
	 * @return The Array containing the Sensitivities of the Basis Coefficients to the Preceding Manifest
	 * 	Measure
	 */

	public double[] getDBasisCoeffDPreceedingManifest()
	{
		return _basisCoefficientToPrecedingManifestSensitivityArray;
	}

	/**
	 * Set the Sensitivity of the Segment Response to the Preceding Manifest Measure
	 * 
	 * @param responseToPrecedingManifestSensitivity Sensitivity of the Segment Response to the Preceding
	 *  Manifest Measure
	 * 
	 * @return TRUE - Sensitivity of the Segment Response to the Preceding Manifest Measure successfully
	 * 	set
	 */

	public boolean setDResponseDPreceedingManifest (
		final double responseToPrecedingManifestSensitivity)
	{
		if (!NumberUtil.IsValid (responseToPrecedingManifestSensitivity)) {
			return false;
		}

		_responseToPrecedingManifestSensitivity = responseToPrecedingManifestSensitivity;
		return true;
	}

	/**
	 * Get the Sensitivity of the Segment Response to the Preceding Manifest Measure
	 * 
	 * @return The Sensitivity of the Segment Response to the Preceding Manifest Measure
	 */

	public double getDResponseDPreceedingManifest()
	{
		return _responseToPrecedingManifestSensitivity;
	}

	/**
	 * Get the Preceding Manifest Measure Sensitivity Control Parameters
	 * 
	 * @return The Preceding Manifest Measure Sensitivity Control Parameters
	 */

	public PreceedingManifestSensitivityControl getPMSC()
	{
		return _precedingManifestSensitivityControl;
	}
}
