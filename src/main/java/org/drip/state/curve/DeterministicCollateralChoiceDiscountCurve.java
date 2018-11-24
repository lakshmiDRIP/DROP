
package org.drip.state.curve;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
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
 * <i>DeterministicCollateralChoiceDiscountCurve</i> implements the Dynamically Switchable Collateral Choice
 * Discount Curve among the choice of provided "deterministic" collateral curves.
 *
 *  <br><br>
 *  <ul>
 *		<li><b>Project</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state">State</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/curve">Curve</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/FixedIncome">Fixed Income Analytics Library</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class DeterministicCollateralChoiceDiscountCurve extends org.drip.state.discount.MergedDiscountForwardCurve {
	private int _iDiscreteCollateralizationIncrement = -1;
	private org.drip.state.discount.MergedDiscountForwardCurve _dcDomesticCollateralized = null;
	private org.drip.state.curve.ForeignCollateralizedDiscountCurve[] _aFCDC = null;

	/**
	 * DeterministicCollateralChoiceDiscountCurve constructor
	 * 
	 * @param dcDomesticCollateralized The Domestic Collateralized Curve
	 * @param aFCDC Array of The Foreign Collateralized Curves
	 * @param iDiscreteCollateralizationIncrement The Discrete Collateralization Increment
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public DeterministicCollateralChoiceDiscountCurve (
		final org.drip.state.discount.MergedDiscountForwardCurve dcDomesticCollateralized,
		final org.drip.state.curve.ForeignCollateralizedDiscountCurve[] aFCDC,
		final int iDiscreteCollateralizationIncrement)
		throws java.lang.Exception
	{
		super (dcDomesticCollateralized.epoch().julian(), dcDomesticCollateralized.currency(), null);

		if (0 >= (_iDiscreteCollateralizationIncrement = iDiscreteCollateralizationIncrement))
			throw new java.lang.Exception
				("DeterministicCollateralChoiceDiscountCurve ctr: Invalid Collateralization Increment!");

		_aFCDC = aFCDC;
		_dcDomesticCollateralized = dcDomesticCollateralized;
	}

	@Override public double df (
		final int iDate)
		throws java.lang.Exception
	{
		if (null == _aFCDC) return _dcDomesticCollateralized.df (iDate);

		int iNumCollateralizer = _aFCDC.length;

		if (0 == iNumCollateralizer) return _dcDomesticCollateralized.df (iDate);

		if (iDate <= _iEpochDate) return 1.;

		double dblDF = 1.;
		int iWorkoutDate = _iEpochDate;

		while (java.lang.Math.abs (iDate - iWorkoutDate) > _iDiscreteCollateralizationIncrement) {
			int iWorkoutEndDate = iWorkoutDate + _iDiscreteCollateralizationIncrement;

			double dblDFIncrement = _dcDomesticCollateralized.df (iWorkoutEndDate) /
				_dcDomesticCollateralized.df (iWorkoutDate);

			for (int i = 0; i < iNumCollateralizer; ++i) {
				double dblCollateralizerDFIncrement = _aFCDC[i].df (iWorkoutEndDate) / _aFCDC[i].df
					(iWorkoutDate);

				if (dblCollateralizerDFIncrement < dblDFIncrement)
					dblDFIncrement = dblCollateralizerDFIncrement;
			}

			dblDF *= dblDFIncrement;
			iWorkoutDate = iWorkoutEndDate;
		}

		if (iDate > iWorkoutDate) {
			double dblDFIncrement = _dcDomesticCollateralized.df (iDate) / _dcDomesticCollateralized.df
				(iWorkoutDate);

			for (int i = 0; i < iNumCollateralizer; ++i) {
				double dblCollateralizerDFIncrement = _aFCDC[i].df (iDate) / _aFCDC[i].df (iWorkoutDate);

				if (dblCollateralizerDFIncrement < dblDFIncrement)
					dblDFIncrement = dblCollateralizerDFIncrement;
			}

			dblDF *= dblDFIncrement;
		}

		return dblDF;
	}

	@Override public double forward (
		final int iDate1,
		final int iDate2)
		throws java.lang.Exception
	{
		if (iDate1 < _iEpochDate || iDate2 < _iEpochDate) return 0.;

		return 365.25 / (iDate2 - iDate1) * java.lang.Math.log (df (iDate1) / df (iDate2));
	}

	@Override public double zero (
		final int iDate)
		throws java.lang.Exception
	{
		if (iDate < _iEpochDate) return 0.;

		return -365.25 / (iDate - _iEpochDate) * java.lang.Math.log (df (iDate));
	}

	@Override public org.drip.state.forward.ForwardRateEstimator forwardRateEstimator (
		final int iDate,
		final org.drip.state.identifier.ForwardLabel fri)
	{
		return null;
	}

	@Override public java.lang.String latentStateQuantificationMetric()
	{
		return null;
	}

	@Override public DiscountFactorDiscountCurve parallelShiftManifestMeasure (
		final java.lang.String strManifestMeasure,
		final double dblShift)
	{
		return null;
	}

	@Override public DiscountFactorDiscountCurve shiftManifestMeasure (
		final int iSpanIndex,
		final java.lang.String strManifestMeasure,
		final double dblShift)
	{
		return null;
	}

	@Override public org.drip.state.discount.MergedDiscountForwardCurve customTweakManifestMeasure (
		final java.lang.String strManifestMeasure,
		final org.drip.param.definition.ManifestMeasureTweak rvtp)
	{
		return null;
	}

	@Override public DiscountFactorDiscountCurve parallelShiftQuantificationMetric (
		final double dblShift)
	{
		return null;
	}

	@Override public org.drip.analytics.definition.Curve customTweakQuantificationMetric (
		final org.drip.param.definition.ManifestMeasureTweak rvtp)
	{
		return null;
	}

	@Override public org.drip.quant.calculus.WengertJacobian jackDDFDManifestMeasure (
		final int iDate,
		final java.lang.String strManifestMeasure)
	{
		return null;
	}

	@Override public boolean setCCIS (
		final org.drip.analytics.input.CurveConstructionInputSet ccis)
	{
		return false;
	}

	@Override public org.drip.product.definition.CalibratableComponent[] calibComp()
	{
		return null;
	}

	@Override public org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> manifestMeasure (
		final java.lang.String strInstr)
	{
		return null;
	}
}
