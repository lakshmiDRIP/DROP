
package org.drip.analytics.input;

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
 * <i>LatentStateShapePreservingCCIS</i> contains the Parameters needed for the Curve Calibration/Estimation.
 * It contains the following:
 *
 *	<br><br>
 *  <ul>
 *  	<li>
 *  		Calibration Valuation Parameters
 *  	</li>
 *  	<li>
 *  		Calibration Quoting Parameters
 *  	</li>
 *  	<li>
 *  		Calibration Market Parameters
 *  	</li>
 *  	<li>
 *  		Calibration Pricing Parameters
 *  	</li>
 *  	<li>
 *  		Array of Calibration Span Representation
 *  	</li>
 *  	<li>
 *  		Map of Calibration Quotes
 *  	</li>
 *  	<li>
 *  		Map of Calibration Measures
 *  	</li>
 *  	<li>
 *  		Double Map of the Date/Index Fixings
 *  	</li>
 *  	<li>
 *  		The Shape Preserving Linear Latent State Calibrator
 *  	</li>
 *  </ul>
 *	<br><br>
 *
 * Additional functions provide for retrieval of the above and specific instrument quotes.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/README.md">Analytics</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/input/README.md">Input</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class LatentStateShapePreservingCCIS implements
	org.drip.analytics.input.CurveConstructionInputSet {
	private org.drip.param.valuation.ValuationParams _valParam = null;
	private org.drip.param.pricer.CreditPricerParams _pricerParam = null;
	private org.drip.param.market.CurveSurfaceQuoteContainer _csqs = null;
	private org.drip.param.market.LatentStateFixingsContainer _lsfc = null;
	private org.drip.param.valuation.ValuationCustomizationParams _vcp = null;
	private org.drip.state.inference.LatentStateStretchSpec[] _aStretchSpec = null;
	private org.drip.state.inference.LinearLatentStateCalibrator _llscShapePreserving = null;

	/**
	 * LatentStateShapePreservingCCIS constructor
	 * 
	 * @param llscShapePreserving Shape Preserving LinearLatentStateCalibrator instance
	 * @param aStretchSpec Array of the Latent State Stretch Representation Specifications
	 * @param valParam Valuation Parameters
	 * @param pricerParam Pricer Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param csqs Market Parameters
	 * 
	 * @throws java.lang.Exception Thrown if Inputs are invalid
	 */

	public LatentStateShapePreservingCCIS (
		final org.drip.state.inference.LinearLatentStateCalibrator llscShapePreserving,
		final org.drip.state.inference.LatentStateStretchSpec[] aStretchSpec,
		final org.drip.param.valuation.ValuationParams valParam,
		final org.drip.param.pricer.CreditPricerParams pricerParam,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs)
		throws java.lang.Exception
	{
		if (null == (_llscShapePreserving = llscShapePreserving) || null == (_aStretchSpec = aStretchSpec) ||
			null == (_valParam = valParam))
			throw new java.lang.Exception ("LatentStateShapePreservingCCIS ctr: Invalid Inputs");

		_vcp = vcp;
		_csqs = csqs;
		int iNumStretchNULL = 0;
		_pricerParam = pricerParam;
		int iNumStretch = _aStretchSpec.length;

		if (0 == iNumStretch)
			throw new java.lang.Exception ("LatentStateShapePreservingCCIS ctr: Invalid Inputs");

		for (int i = 0; i < iNumStretch; ++i) {
			if (null == _aStretchSpec[i]) ++iNumStretchNULL;
		}

		if (iNumStretchNULL == iNumStretch)
			throw new java.lang.Exception ("LatentStateShapePreservingCCIS ctr: Invalid Inputs");
	}

	@Override public org.drip.param.valuation.ValuationParams valuationParameter()
	{
		return _valParam;
	}

	@Override public org.drip.param.valuation.ValuationCustomizationParams quotingParameter()
	{
		return _vcp;
	}

	@Override public org.drip.product.definition.CalibratableComponent[] components()
	{
		java.util.List<org.drip.product.definition.CalibratableComponent> lsComp = new
			java.util.ArrayList<org.drip.product.definition.CalibratableComponent>();

		for (org.drip.state.inference.LatentStateStretchSpec stretchSpec : _aStretchSpec) {
			if (null == stretchSpec) continue;

			org.drip.state.inference.LatentStateSegmentSpec[] aSegmentSpec = stretchSpec.segmentSpec();

			if (null == aSegmentSpec) continue;

			int iNumComp = aSegmentSpec.length;

			if (0 == iNumComp) continue;

			for (int i = 0; i < iNumComp; ++i) {
				if (null == aSegmentSpec[i]) continue;

				org.drip.product.definition.CalibratableComponent comp = aSegmentSpec[i].component();

				if (null != comp) lsComp.add (comp);
			}
		}

		int iNumComp = lsComp.size();

		if (0 == iNumComp) return null;

		org.drip.product.definition.CalibratableComponent[] aComp = new
			org.drip.product.definition.CalibratableComponent[iNumComp];

		for (int i = 0; i < iNumComp; ++i)
			aComp[i] = lsComp.get (i);

		return aComp;
	}

	@Override public
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>
			quoteMap()
	{
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>
			mapInstrumentQuote = new
				org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>();

		for (org.drip.state.inference.LatentStateStretchSpec stretchSpec : _aStretchSpec) {
			if (null == stretchSpec) continue;

			org.drip.state.inference.LatentStateSegmentSpec[] aSegmentSpec = stretchSpec.segmentSpec();

			for (org.drip.state.inference.LatentStateSegmentSpec segmentSpec : aSegmentSpec) {
				org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapMeasureQuote = new
					org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>();

				org.drip.product.calib.ProductQuoteSet pqs = segmentSpec.manifestMeasures();

				for (java.lang.String strManifestMeasure : pqs.fields()) {
					try {
						mapMeasureQuote.put (strManifestMeasure, pqs.get (strManifestMeasure));
					} catch (java.lang.Exception e) {
						e.printStackTrace();
					}
				}

				mapInstrumentQuote.put (segmentSpec.component().primaryCode(), mapMeasureQuote);
			}
		}

		return mapInstrumentQuote;
	}

	@Override public org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.String[]> measures()
	{
		return null;
	}

	@Override public org.drip.param.market.LatentStateFixingsContainer fixing()
	{
		return _lsfc;
	}

	/**
	 * Retrieve the Pricer Parameters
	 * 
	 * @return The Pricer Parameters
	 */

	public org.drip.param.pricer.CreditPricerParams pricerParameter()
	{
		return _pricerParam;
	}

	/**
	 * Retrieve the Market Parameters
	 * 
	 * @return The Market Parameters
	 */

	public org.drip.param.market.CurveSurfaceQuoteContainer marketParameters()
	{
		return _csqs;
	}

	/**
	 * Retrieve the Array of Latent State Stretch Representation Specifications
	 * 
	 * @return The Array of Latent State Stretch Representation Specifications
	 */

	public org.drip.state.inference.LatentStateStretchSpec[] stretchSpec()
	{
		return _aStretchSpec;
	}

	/**
	 * Retrieve the Shape Preserving Linear Latent State Calibrator
	 * 
	 * @return The Shape Preserving Linear Latent State Calibrator
	 */

	public org.drip.state.estimator.GlobalControlCurveParams shapePreservingLLSC()
	{
		return _llscShapePreserving;
	}
}
