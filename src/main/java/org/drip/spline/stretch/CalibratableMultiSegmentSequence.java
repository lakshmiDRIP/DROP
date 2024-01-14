
package org.drip.spline.stretch;

import org.drip.function.definition.R1ToR1;
import org.drip.function.r1tor1solver.FixedPointFinderOutput;
import org.drip.function.r1tor1solver.FixedPointFinderZheng;
import org.drip.numerical.common.NumberUtil;
import org.drip.numerical.differentiation.WengertJacobian;
import org.drip.numerical.integration.R1ToR1Integrator;
import org.drip.spline.params.SegmentBasisFlexureConstraint;
import org.drip.spline.params.SegmentCustomBuilderControl;
import org.drip.spline.params.SegmentPredictorResponseDerivative;
import org.drip.spline.params.SegmentResponseValueConstraint;
import org.drip.spline.params.SegmentStateCalibrationInputs;
import org.drip.spline.params.StretchBestFitResponse;
import org.drip.spline.segment.LatentStateResponseModel;
import org.drip.spline.segment.Monotonocity;

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
 * <i>CalibratableMultiSegmentSequence</i> implements the MultiSegmentSequence span that spans multiple
 * 	segments. It holds the ordered segment sequence, segment sequence builder, the segment control
 *  parameters, and, if available, the spanning Jacobian. It provides a variety of customization for the
 *  segment construction and state representation control.
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
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/stretch/README.md">Multi-Segment Sequence Spline Stretch</a></td></tr>
 *  </table>
 *  <br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class CalibratableMultiSegmentSequence
	extends R1ToR1
	implements MultiSegmentSequence
{
	private static final int MAXIMA_PREDICTOR_ORDINATE_NODE = 1;
	private static final int MINIMA_PREDICTOR_ORDINATE_NODE = 2;
	private static final int MONOTONE_PREDICTOR_ORDINATE_NODE = 4;

	private String _name = "";
	private SegmentSequenceBuilder _segmentSequenceBuilder = null;
	private WengertJacobian _coefficientToEdgeParamsWengertJacobian = null;
	private LatentStateResponseModel[] _latentStateResponseModelArray = null;
	private SegmentCustomBuilderControl[] _segmentCustomBuilderControlArray = null;

	private boolean setDCoeffDEdgeParams (
		final int nodeIndex,
		final WengertJacobian coefficientToEdgeParamsWengertJacobian)
	{
		if (null == coefficientToEdgeParamsWengertJacobian) {
			return false;
		}

		int parameterIndex = 0 == nodeIndex ? 0 : 2;

		if (!_coefficientToEdgeParamsWengertJacobian.accumulatePartialFirstDerivative (
			0,
			nodeIndex,
			coefficientToEdgeParamsWengertJacobian.firstDerivative (0, parameterIndex)
		)) {
			return false;
		}

		if (!_coefficientToEdgeParamsWengertJacobian.accumulatePartialFirstDerivative (
			1,
			nodeIndex,
			coefficientToEdgeParamsWengertJacobian.firstDerivative (1, parameterIndex)
		)) {
			return false;
		}

		if (!_coefficientToEdgeParamsWengertJacobian.accumulatePartialFirstDerivative (
			2,
			nodeIndex,
			coefficientToEdgeParamsWengertJacobian.firstDerivative (2, parameterIndex)
		)) {
			return false;
		}

		return _coefficientToEdgeParamsWengertJacobian.accumulatePartialFirstDerivative (
			3,
			nodeIndex,
			coefficientToEdgeParamsWengertJacobian.firstDerivative (3, parameterIndex)
		);
	}

	private final WengertJacobian setDResponseDEdgeResponse (
		final int nodeIndex,
		final WengertJacobian responseToEdgeParamsWengertJacobian)
	{
		if (null == responseToEdgeParamsWengertJacobian) {
			return null;
		}

		WengertJacobian responseToEdgeWengertJacobian = null;
		int segmentCount = _latentStateResponseModelArray.length;

		try {
			responseToEdgeWengertJacobian = new WengertJacobian (1, segmentCount + 1);
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}

		for (int segmentIndex = 0; segmentIndex <= segmentCount; ++segmentIndex) {
			if (segmentIndex == nodeIndex) {
				if (!responseToEdgeWengertJacobian.accumulatePartialFirstDerivative (
					0,
					segmentIndex,
					responseToEdgeParamsWengertJacobian.firstDerivative (
						0,
						LatentStateResponseModel.LEFT_NODE_VALUE_PARAMETER_INDEX
					)
				) || !responseToEdgeWengertJacobian.accumulatePartialFirstDerivative (
					0,
					segmentIndex + 1,
					responseToEdgeParamsWengertJacobian.firstDerivative (
						0,
						LatentStateResponseModel.RIGHT_NODE_VALUE_PARAMETER_INDEX
					)
				)) {
					return null;
				}
			}
		}

		return responseToEdgeWengertJacobian;
	}

	/**
	 * <i>CalibratableMultiSegmentSequence</i> constructor - Construct a sequence of Basis Spline Segments
	 * 
	 * @param name Name of the Stretch
	 * @param latentStateResponseModelArray Array of Segments
	 * @param segmentCustomBuilderControlArray Array of Segment Builder Parameters
	 * 
	 * @throws Exception Thrown if the inputs are invalid
	 */

	public CalibratableMultiSegmentSequence (
		final String name,
		final LatentStateResponseModel[] latentStateResponseModelArray,
		final SegmentCustomBuilderControl[] segmentCustomBuilderControlArray)
		throws Exception
	{
		super (null);

		if (null == latentStateResponseModelArray || null == segmentCustomBuilderControlArray ||
			null == (_name = name) || _name.isEmpty()) {
			throw new Exception ("CalibratableMultiSegmentSequence ctr => Invalid inputs!");
		}

		int segmentCount = latentStateResponseModelArray.length;
		_latentStateResponseModelArray = new LatentStateResponseModel[segmentCount];
		_segmentCustomBuilderControlArray = new SegmentCustomBuilderControl[segmentCount];

		if (0 == segmentCount || segmentCount != segmentCustomBuilderControlArray.length) {
			throw new Exception ("CalibratableMultiSegmentSequence ctr => Invalid inputs!");
		}

		for (int segmentIndex = 0; segmentIndex < segmentCount; ++segmentIndex) {
			if (null == (
					_latentStateResponseModelArray[segmentIndex] =
					latentStateResponseModelArray[segmentIndex]
				) || null == (
					_segmentCustomBuilderControlArray[segmentIndex] =
					segmentCustomBuilderControlArray[segmentIndex]
				)
			) {
				throw new Exception ("CalibratableMultiSegmentSequence ctr => Invalid inputs!");
			}
		}
	}

	@Override public String name()
	{
		return _name;
	}

	@Override public LatentStateResponseModel[] segments()
	{
		return _latentStateResponseModelArray;
	}

	@Override public SegmentCustomBuilderControl[] segmentBuilderControl()
	{
		return _segmentCustomBuilderControlArray;
	}

	@Override public boolean setup (
		final SegmentSequenceBuilder segmentSequenceBuilder,
		final int calibrationDetail)
	{
		if (null == (_segmentSequenceBuilder = segmentSequenceBuilder) ||
			!_segmentSequenceBuilder.setStretch (this)) {
			return false;
		}

		if (BoundarySettings.BOUNDARY_CONDITION_FLOATING ==
			_segmentSequenceBuilder.getCalibrationBoundaryCondition().boundaryCondition()) {
			if (!_segmentSequenceBuilder.calibStartingSegment (0.) ||
				!_segmentSequenceBuilder.calibSegmentSequence (1) ||
				!_segmentSequenceBuilder.manifestMeasureSensitivity (0.)
			) {
				return false;
			}
		} else if (0 != (MultiSegmentSequence.CALIBRATE & calibrationDetail)) {
			FixedPointFinderOutput fixedPointFinderOutput = null;

			if (null == fixedPointFinderOutput || !fixedPointFinderOutput.containsRoot()) {
				try {
					fixedPointFinderOutput = new FixedPointFinderZheng (
						0.,
						this,
						true
					).findRoot();
				} catch (Exception e) {
					e.printStackTrace();

					return false;
				}
			}

			if (null == fixedPointFinderOutput || !NumberUtil.IsValid (fixedPointFinderOutput.getRoot()) ||
				!_segmentSequenceBuilder.manifestMeasureSensitivity (0.)) {
				System.out.println ("FPOP: " + fixedPointFinderOutput);

				return false;
			}
		}

		if (0 != (MultiSegmentSequence.CALIBRATE_JACOBIAN & calibrationDetail)) {
			int segmentCount = _latentStateResponseModelArray.length;

			try {
				if (null == (
					_coefficientToEdgeParamsWengertJacobian = new WengertJacobian (
						_latentStateResponseModelArray[0].basisEvaluator().numBasis(),
						segmentCount + 1
					)
				)) {
					return false;
				}
			} catch (Exception e) {
				e.printStackTrace();

				return false;
			}

			WengertJacobian headWengertJacobian = _latentStateResponseModelArray[0].jackDCoeffDEdgeInputs();

			if (!setDCoeffDEdgeParams (0, headWengertJacobian) ||
				!setDCoeffDEdgeParams (1, headWengertJacobian)) {
				return false;
			}

			for (int segmentIndex = 1; segmentIndex < segmentCount; ++segmentIndex) {
				if (!setDCoeffDEdgeParams (
					segmentIndex + 1,
					_latentStateResponseModelArray[segmentIndex].jackDCoeffDEdgeInputs()
				)) {
					return false;
				}
			}
		}

		return true;
	}

	@Override public boolean setup (
		final SegmentResponseValueConstraint leadingSegmentResponseValueConstraint,
		final SegmentResponseValueConstraint[] segmentResponseValueConstraintArray,
		final StretchBestFitResponse stretchBestFitResponse,
		final BoundarySettings boundarySettings,
		final int calibrationDetail)
	{
		try {
			return setup (
				new CkSegmentSequenceBuilder (
					leadingSegmentResponseValueConstraint,
					segmentResponseValueConstraintArray,
					stretchBestFitResponse,
					boundarySettings
				),
				calibrationDetail
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override public boolean setup (
		final double leftStretchResponseValue,
		final SegmentResponseValueConstraint[] segmentResponseValueConstraintArray,
		final StretchBestFitResponse stretchBestFitResponse,
		final BoundarySettings bBoundarySettings,
		final int calibrationDetail)
	{
		return setup (
			SegmentResponseValueConstraint.FromPredictorResponsePair (
				getLeftPredictorOrdinateEdge(),
				leftStretchResponseValue
			),
			segmentResponseValueConstraintArray,
			stretchBestFitResponse,
			bBoundarySettings,
			calibrationDetail
		);
	}

	@Override public boolean setup (
		final double leftStretchResponseValue,
		final double[] segmentRightResponseValueArray,
		final StretchBestFitResponse stretchBestFitResponse,
		final BoundarySettings boundarySettings,
		final int calibrationDetail)
	{
		int segmentCount = _latentStateResponseModelArray.length;
		SegmentResponseValueConstraint[] rightSegmentResponseValueConstraintArray =
			new SegmentResponseValueConstraint[segmentCount];

		if (0 == segmentCount || segmentCount != segmentRightResponseValueArray.length) {
			return false;
		}

		try {
			for (int segmentIndex = 0; segmentIndex < segmentCount; ++segmentIndex) {
				rightSegmentResponseValueConstraintArray[segmentIndex] = new SegmentResponseValueConstraint (
					new double[] {_latentStateResponseModelArray[segmentIndex].right()},
					new double[] {1.},
					segmentRightResponseValueArray[segmentIndex]
				);
			}
		} catch (Exception e) {
			e.printStackTrace();

			return false;
		}

		return setup (
			leftStretchResponseValue,
			rightSegmentResponseValueConstraintArray,
			stretchBestFitResponse,
			boundarySettings,
			calibrationDetail
		);
	}

	@Override public boolean setupHermite (
		final SegmentPredictorResponseDerivative[] leftSegmentPredictorResponseDerivativeArray,
		final SegmentPredictorResponseDerivative[] rightSegmentPredictorResponseDerivativeArray,
		final SegmentResponseValueConstraint[][] segmentResponseValueConstraintGrid,
		final StretchBestFitResponse sbfr,
		final int iSetupMode)
	{
		if (null == leftSegmentPredictorResponseDerivativeArray ||
			null == rightSegmentPredictorResponseDerivativeArray) {
			return false;
		}

		int segmentCount = _latentStateResponseModelArray.length;

		if (segmentCount != leftSegmentPredictorResponseDerivativeArray.length ||
			segmentCount != rightSegmentPredictorResponseDerivativeArray.length || (
				null != segmentResponseValueConstraintGrid &&
				segmentCount != segmentResponseValueConstraintGrid.length
			)
		) {
			return false;
		}

		for (int segmentIndex = 0; segmentIndex < segmentCount; ++segmentIndex) {
			try {
				int segmentConstraintCount = 0;
				SegmentBasisFlexureConstraint[] segmentBasisFlexureConstraintArray = null;

				if (null != segmentResponseValueConstraintGrid &&
					null != segmentResponseValueConstraintGrid[segmentIndex]) {
					segmentBasisFlexureConstraintArray =
						new SegmentBasisFlexureConstraint[segmentConstraintCount = segmentResponseValueConstraintGrid[segmentIndex].length];

					for (int segmentConstraintIndex = 0; segmentConstraintIndex < segmentConstraintCount;
						++segmentConstraintIndex) {
						segmentBasisFlexureConstraintArray[segmentConstraintIndex] =
							null == segmentResponseValueConstraintGrid[segmentIndex][segmentConstraintIndex]
								? null :
								segmentResponseValueConstraintGrid[segmentIndex][segmentConstraintIndex].responseIndexedBasisConstraint (
									_latentStateResponseModelArray[segmentIndex].basisEvaluator(),
									_latentStateResponseModelArray[segmentIndex]
								);
					}
				}

				if (0 != (MultiSegmentSequence.CALIBRATE & iSetupMode) &&
					!_latentStateResponseModelArray[segmentIndex].calibrateState (
						new SegmentStateCalibrationInputs (
							new double[] {
								_latentStateResponseModelArray[segmentIndex].left(),
								_latentStateResponseModelArray[segmentIndex].right()
							},
							new double[] {
								leftSegmentPredictorResponseDerivativeArray[segmentIndex].responseValue(),
								rightSegmentPredictorResponseDerivativeArray[segmentIndex].responseValue()
							},
							leftSegmentPredictorResponseDerivativeArray[segmentIndex].getDResponseDPredictorOrdinate(),
							rightSegmentPredictorResponseDerivativeArray[segmentIndex].getDResponseDPredictorOrdinate(),
							segmentBasisFlexureConstraintArray,
							null == sbfr ? null : sbfr.sizeToSegment (
								_latentStateResponseModelArray[segmentIndex]
							)
						)
					)
				) {
					return false;
				}
			} catch (Exception e) {
				e.printStackTrace();

				return false;
			}
		}

		if (0 != (MultiSegmentSequence.CALIBRATE_JACOBIAN & iSetupMode)) {
			try {
				if (null == (
					_coefficientToEdgeParamsWengertJacobian = new WengertJacobian (
						_latentStateResponseModelArray[0].basisEvaluator().numBasis(),
						segmentCount + 1
					)
				)) {
					return false;
				}
			} catch (Exception e) {
				e.printStackTrace();

				return false;
			}

			WengertJacobian headCoefficientToEdgeParamsWengertJacobian =
				_latentStateResponseModelArray[0].jackDCoeffDEdgeInputs();

			if (!setDCoeffDEdgeParams (0, headCoefficientToEdgeParamsWengertJacobian) ||
				!setDCoeffDEdgeParams (1, headCoefficientToEdgeParamsWengertJacobian)) {
				return false;
			}

			for (int segmentIndex = 1; segmentIndex < segmentCount; ++segmentIndex) {
				if (!setDCoeffDEdgeParams (
					segmentIndex + 1,
					_latentStateResponseModelArray[segmentIndex].jackDCoeffDEdgeInputs()
				)) {
					return false;
				}
			}
		}

		return true;
	}

	@Override public double evaluate (
		final double leftSlope)
		throws Exception
	{
		if (null == _segmentSequenceBuilder ||
			!_segmentSequenceBuilder.calibStartingSegment (leftSlope) ||
			!_segmentSequenceBuilder.calibSegmentSequence (1)) {
			throw new Exception ("CalibratableMultiSegmentSequence::evaluate => cannot set up segments!");
		}

		BoundarySettings boundarySettings = _segmentSequenceBuilder.getCalibrationBoundaryCondition();

		int boundaryCondition = boundarySettings.boundaryCondition();

		if (BoundarySettings.BOUNDARY_CONDITION_NATURAL == boundaryCondition) {
			return calcRightEdgeDerivative (boundarySettings.rightDerivOrder());
		}

		if (BoundarySettings.BOUNDARY_CONDITION_FINANCIAL == boundaryCondition) {
			return calcRightEdgeDerivative (boundarySettings.rightDerivOrder());
		}

		if (BoundarySettings.BOUNDARY_CONDITION_NOT_A_KNOT == boundaryCondition) {
			return calcRightEdgeDerivative (boundarySettings.rightDerivOrder()) -
				calcLeftEdgeDerivative (boundarySettings.leftDerivOrder());
		}

		throw new Exception (
			"CalibratableMultiSegmentSequence::evaluate => Boundary Condition " + boundaryCondition +
				" unknown"
		);
	}

	@Override public double integrate (
		final double begin,
		final double end)
		throws Exception
	{
		return R1ToR1Integrator.Boole (this, begin, end);
	}

	@Override public boolean setLeftNode (
		final double stretchLeftResponse,
		final double stretchLeftResponseSlope,
		final double stretchRightResponse,
		final StretchBestFitResponse stretchBestFitResponse)
	{
		return _latentStateResponseModelArray[0].calibrate (
			SegmentResponseValueConstraint.FromPredictorResponsePair (
				getLeftPredictorOrdinateEdge(),
				stretchLeftResponse
			),
			stretchLeftResponseSlope,
			SegmentResponseValueConstraint.FromPredictorResponsePair (
				getRightPredictorOrdinateEdge(),
				stretchRightResponse
			),
			null == stretchBestFitResponse ? null : stretchBestFitResponse.sizeToSegment (
				_latentStateResponseModelArray[0]
			)
		);
	}

	@Override public double responseValue (
		final double predictorOrdinate)
		throws Exception
	{
		return _latentStateResponseModelArray[containingIndex (predictorOrdinate, true, true)].responseValue
			(predictorOrdinate);
	}

	@Override public double responseValueDerivative (
		final double predictorOrdinate,
		final int iOrder)
		throws java.lang.Exception
	{
		return _latentStateResponseModelArray[containingIndex (predictorOrdinate, true, true)].calcResponseValueDerivative
			(predictorOrdinate, iOrder);
	}

	@Override public SegmentPredictorResponseDerivative calcSPRD (
		final double predictorOrdinate)
	{
		int index = -1;

		try {
			index = containingIndex (predictorOrdinate, true, true);
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}

		int ck = _segmentCustomBuilderControlArray[index].inelasticParams().Ck();

		double derivativeArray[] = new double[ck];

		try {
			for (int ci = 0; ci < ck; ++ci) {
				derivativeArray[ci] = _latentStateResponseModelArray[index].calcResponseValueDerivative (
					predictorOrdinate,
					ci
				);
			}

			return new SegmentPredictorResponseDerivative (
				_latentStateResponseModelArray[index].responseValue (predictorOrdinate),
				derivativeArray
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override public WengertJacobian jackDResponseDCalibrationInput (
		final double predictorOrdinate,
		final int order)
	{
		int index = -1;

		try {
			index = containingIndex (predictorOrdinate, true, true);
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}

		return setDResponseDEdgeResponse (
			index,
			_latentStateResponseModelArray[index].jackDResponseDEdgeInput (predictorOrdinate, order)
		);
	}

	@Override public WengertJacobian jackDResponseDManifestMeasure (
		final String manifestMeasure,
		final double predictorOrdinate,
		final int order)
	{
		int priorImpactFadeIndex = 0;
		int segmentCount = _latentStateResponseModelArray.length;

		try {
			int containingIndex = containingIndex (predictorOrdinate, true, true);

			boolean containsSegmentImpactFade = _latentStateResponseModelArray[containingIndex].impactFade (
				manifestMeasure
			);

			if (!containsSegmentImpactFade && 0 != containingIndex) {
				for (int index = containingIndex - 1; index >= 0; --index) {
					if (_latentStateResponseModelArray[index].impactFade (manifestMeasure)) {
						priorImpactFadeIndex = index;
						break;
					}
				}
			}

			WengertJacobian responseToManifestMeasureWengertJacobian = new WengertJacobian (1, segmentCount);

			for (int segmentIndex = 0; segmentIndex < segmentCount; ++segmentIndex) {
				double responseToManifestMeasureSensitivity = 0.;

				if (segmentIndex == containingIndex) {
					responseToManifestMeasureSensitivity =
						_latentStateResponseModelArray[segmentIndex].calcDResponseDManifest (
							manifestMeasure,
							predictorOrdinate,
							order
						);
				} else if (segmentIndex == containingIndex - 1) {
					responseToManifestMeasureSensitivity =
						_latentStateResponseModelArray[segmentIndex + 1].calcDResponseDPreceedingManifest (
							manifestMeasure,
							predictorOrdinate,
							order
						);
				} else if (!containsSegmentImpactFade &&
					segmentIndex >= priorImpactFadeIndex &&
					segmentIndex < containingIndex - 1) {
					responseToManifestMeasureSensitivity =
						_latentStateResponseModelArray[segmentIndex].calcDResponseDManifest (
							manifestMeasure,
							_latentStateResponseModelArray[segmentIndex].right(),
							order
						);
				}

				if (!responseToManifestMeasureWengertJacobian.accumulatePartialFirstDerivative (
					0,
					segmentIndex,
					responseToManifestMeasureSensitivity)) {
					return null;
				}
			}

			return responseToManifestMeasureWengertJacobian;
		} catch (Exception e) {
			// e.printStackTrace();
		}

		return null;
	}

	@Override public Monotonocity monotoneType (
		final double predictorOrdinate)
	{
		int index = -1;

		try {
			index = containingIndex (predictorOrdinate, true, true);
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}

		return _latentStateResponseModelArray[index].monotoneType();
	}

	@Override public boolean isLocallyMonotone()
		throws Exception
	{
		int segmentCount = _latentStateResponseModelArray.length;

		for (int segmentIndex = 0; segmentIndex < segmentCount; ++segmentIndex) {
			Monotonocity monotonocity = null;

			try {
				monotonocity = _latentStateResponseModelArray[segmentIndex].monotoneType();
			} catch (java.lang.Exception e) {
				e.printStackTrace();
			}

			if (null == monotonocity || Monotonocity.MONOTONIC != monotonocity.type()) {
				return false;
			}
		}

		return true;
	}

	@Override public boolean isCoMonotone (
		final double[] measuredResponseArray)
		throws Exception
	{
		int segmentCount = _latentStateResponseModelArray.length;
		int[] nodeMinimaxIndex = new int[segmentCount + 1];
		int[] monotoneTypeArray = new int[segmentCount];

		if (null == measuredResponseArray || measuredResponseArray.length != segmentCount + 1) {
			throw new Exception (
				"CalibratableMultiSegmentSequence::isCoMonotone => Data input inconsistent with the segment"
			);
		}

		for (int segmentIndex = 0; segmentIndex < segmentCount + 1; ++segmentIndex) {
			if (0 == segmentIndex || segmentCount == segmentIndex) {
				nodeMinimaxIndex[segmentIndex] = MONOTONE_PREDICTOR_ORDINATE_NODE;
			} else {
				if (measuredResponseArray[segmentIndex - 1] < measuredResponseArray[segmentIndex] &&
					measuredResponseArray[segmentIndex + 1] < measuredResponseArray[segmentIndex]) {
					nodeMinimaxIndex[segmentIndex] = MAXIMA_PREDICTOR_ORDINATE_NODE;
				} else if (measuredResponseArray[segmentIndex - 1] > measuredResponseArray[segmentIndex] &&
					measuredResponseArray[segmentIndex + 1] > measuredResponseArray[segmentIndex]) {
					nodeMinimaxIndex[segmentIndex] = MINIMA_PREDICTOR_ORDINATE_NODE;
				} else {
					nodeMinimaxIndex[segmentIndex] = MONOTONE_PREDICTOR_ORDINATE_NODE;
				}
			}

			if (segmentIndex < segmentCount) {
				Monotonocity monotonocity = _latentStateResponseModelArray[segmentIndex].monotoneType();

				if (null != monotonocity) {
					monotoneTypeArray[segmentIndex] = monotonocity.type();
				}
			}
		}

		for (int segmentIndex = 1; segmentIndex < segmentCount; ++segmentIndex) {
			if (MAXIMA_PREDICTOR_ORDINATE_NODE == nodeMinimaxIndex[segmentIndex]) {
				if (Monotonocity.MAXIMA != monotoneTypeArray[segmentIndex] &&
					Monotonocity.MAXIMA != monotoneTypeArray[segmentIndex - 1]) {
					return false;
				}
			} else if (MINIMA_PREDICTOR_ORDINATE_NODE == nodeMinimaxIndex[segmentIndex]) {
				if (Monotonocity.MINIMA != monotoneTypeArray[segmentIndex] &&
					Monotonocity.MINIMA != monotoneTypeArray[segmentIndex - 1]) {
					return false;
				}
			}
		}

		return true;
	}

	@Override public boolean isKnot (
		final double predictorOrdinate)
	{
		if (!NumberUtil.IsValid (predictorOrdinate)) {
			return false;
		}

		int segmentCount = _latentStateResponseModelArray.length;

		for (int segmentIndex = 0; segmentIndex < segmentCount; ++segmentIndex) {
			if (predictorOrdinate == _latentStateResponseModelArray[segmentIndex].left()) {
				return false;
			}
		}

		return predictorOrdinate == _latentStateResponseModelArray[segmentCount - 1].left();
	}

	@Override public double calcLeftEdgeDerivative (
		final int order)
		throws Exception
	{
		return _latentStateResponseModelArray[0].calcResponseValueDerivative (
			_latentStateResponseModelArray[0].left(),
			order
		);
	}

	@Override public double calcRightEdgeDerivative (
		final int order)
		throws java.lang.Exception
	{
		org.drip.spline.segment.LatentStateResponseModel lsrm = _latentStateResponseModelArray[_latentStateResponseModelArray.length - 1];

		return lsrm.calcResponseValueDerivative (lsrm.right(), order);
	}

	@Override public boolean resetNode (
		final int iPredictorOrdinateIndex,
		final double dblResponseReset)
	{
		if (0 == iPredictorOrdinateIndex || 1 == iPredictorOrdinateIndex || _latentStateResponseModelArray.length <
			iPredictorOrdinateIndex || !org.drip.numerical.common.NumberUtil.IsValid (dblResponseReset))
			return false;

		return _latentStateResponseModelArray[iPredictorOrdinateIndex - 1].calibrate (_latentStateResponseModelArray[iPredictorOrdinateIndex - 2],
			dblResponseReset, null);
	}

	@Override public boolean resetNode (
		final int iPredictorOrdinateIndex,
		final org.drip.spline.params.SegmentResponseValueConstraint srvcReset)
	{
		if (0 == iPredictorOrdinateIndex || 1 == iPredictorOrdinateIndex || _latentStateResponseModelArray.length <
			iPredictorOrdinateIndex || null == srvcReset)
			return false;

		return _latentStateResponseModelArray[iPredictorOrdinateIndex - 1].calibrate (_latentStateResponseModelArray[iPredictorOrdinateIndex - 2], srvcReset,
			null);
	}

	@Override public org.drip.function.definition.R1ToR1 toAU()
	{
		org.drip.function.definition.R1ToR1 au = new
			org.drip.function.definition.R1ToR1 (null)
		{
			@Override public double evaluate (
				final double dblVariate)
				throws java.lang.Exception
			{
				return responseValue (dblVariate);
			}

			@Override public double derivative (
				final double dblVariate,
				final int iOrder)
				throws java.lang.Exception
			{
				return responseValueDerivative (dblVariate, iOrder);
			}
		};

		return au;
	}

	@Override public boolean in (
		final double dblPredictorOrdinate)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblPredictorOrdinate))
			throw new java.lang.Exception ("CalibratableMultiSegmentSequence::in => Invalid inputs");

		return dblPredictorOrdinate >= getLeftPredictorOrdinateEdge() && dblPredictorOrdinate <=
			getRightPredictorOrdinateEdge();
	}

	@Override public double getLeftPredictorOrdinateEdge()
	{
		return _latentStateResponseModelArray[0].left();
	}

	@Override public double getRightPredictorOrdinateEdge()
	{
		return _latentStateResponseModelArray[_latentStateResponseModelArray.length - 1].right();
	}

	@Override public int containingIndex (
		final double dblPredictorOrdinate,
		final boolean bIncludeLeft,
		final boolean bIncludeRight)
		throws java.lang.Exception
	{
		if (!in (dblPredictorOrdinate))
			throw new java.lang.Exception
				("CalibratableMultiSegmentSequence::containingIndex => Predictor Ordinate not in the Stretch Range");

		int iNumSegment = _latentStateResponseModelArray.length;

		for (int i = 0 ; i < iNumSegment; ++i) {
			boolean bLeftValid = bIncludeLeft ? _latentStateResponseModelArray[i].left() <= dblPredictorOrdinate : _latentStateResponseModelArray[i].left() <
				dblPredictorOrdinate;

			boolean bRightValid = bIncludeRight ? _latentStateResponseModelArray[i].right() >= dblPredictorOrdinate :
				_latentStateResponseModelArray[i].right() > dblPredictorOrdinate;

			if (bLeftValid && bRightValid) return i;
		}

		throw new java.lang.Exception
			("CalibratableMultiSegmentSequence::containingIndex => Cannot locate Containing Index");
	}

	@Override public CalibratableMultiSegmentSequence clipLeft (
		final java.lang.String strName,
		final double dblPredictorOrdinate)
	{
		int iNumSegment = _latentStateResponseModelArray.length;
		int iContainingPredictorOrdinateIndex = 0;

		try {
			iContainingPredictorOrdinateIndex = containingIndex (dblPredictorOrdinate, true, false);
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		int iNumClippedSegment = iNumSegment - iContainingPredictorOrdinateIndex;
		org.drip.spline.segment.LatentStateResponseModel[] aCS = new
			org.drip.spline.segment.LatentStateResponseModel[iNumClippedSegment];
		org.drip.spline.params.SegmentCustomBuilderControl[] aSCBC = new
			org.drip.spline.params.SegmentCustomBuilderControl[iNumClippedSegment];

		for (int i = 0; i < iNumClippedSegment; ++i) {
			if (null == (aCS[i] = 0 == i ?
				_latentStateResponseModelArray[iContainingPredictorOrdinateIndex].clipLeftOfPredictorOrdinate (dblPredictorOrdinate)
					: _latentStateResponseModelArray[i + iContainingPredictorOrdinateIndex]))
				return null;

			aSCBC[i] = _segmentCustomBuilderControlArray[i + iContainingPredictorOrdinateIndex];
		}

		try {
			return new CalibratableMultiSegmentSequence (strName, aCS, aSCBC);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override public CalibratableMultiSegmentSequence clipRight (
		final java.lang.String strName,
		final double dblPredictorOrdinate)
	{
		int iContainingPredictorOrdinateIndex = 0;

		try {
			iContainingPredictorOrdinateIndex = containingIndex (dblPredictorOrdinate, false, true);
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		org.drip.spline.segment.LatentStateResponseModel[] aCS = new
			org.drip.spline.segment.LatentStateResponseModel[iContainingPredictorOrdinateIndex + 1];
		org.drip.spline.params.SegmentCustomBuilderControl[] aSCBC = new
			org.drip.spline.params.SegmentCustomBuilderControl[iContainingPredictorOrdinateIndex + 1];

		for (int i = 0; i <= iContainingPredictorOrdinateIndex; ++i) {
			if (null == (aCS[i] = iContainingPredictorOrdinateIndex == i ?
				_latentStateResponseModelArray[iContainingPredictorOrdinateIndex].clipRightOfPredictorOrdinate (dblPredictorOrdinate)
					: _latentStateResponseModelArray[i]))
				return null;

			aSCBC[i] = _segmentCustomBuilderControlArray[i];
		}

		try {
			return new CalibratableMultiSegmentSequence (strName, aCS, aSCBC);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override public double curvatureDPE()
		throws java.lang.Exception
	{
		double dblDPE = 0.;

		for (org.drip.spline.segment.LatentStateResponseModel lsrm : _latentStateResponseModelArray)
			dblDPE += lsrm.curvatureDPE();

		return dblDPE;
	}

	@Override public double lengthDPE()
		throws java.lang.Exception
	{
		double dblDPE = 0.;

		for (org.drip.spline.segment.LatentStateResponseModel lsrm : _latentStateResponseModelArray)
			dblDPE += lsrm.lengthDPE();

		return dblDPE;
	}

	@Override public double bestFitDPE (
		final org.drip.spline.params.StretchBestFitResponse rbfr)
		throws java.lang.Exception
	{
		if (null == rbfr) return 0.;

		double dblDPE = 0.;

		for (org.drip.spline.segment.LatentStateResponseModel lsrm : _latentStateResponseModelArray)
			dblDPE += lsrm.bestFitDPE (rbfr.sizeToSegment (lsrm));

		return dblDPE;
	}

	@Override public org.drip.state.representation.MergeSubStretchManager msm()
	{
		return null;
	}

	@Override public java.lang.String displayString()
	{
		java.lang.StringBuffer sb = new java.lang.StringBuffer();

		for (int i = 0; i < _latentStateResponseModelArray.length; ++i)
			sb.append (_latentStateResponseModelArray[i].displayString() + " \n");

		return sb.toString();
	}
}
