# DROP Sample Fix Float Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Sample Fix Float demonstrates Analytics for Coupon, Floater, and Amortizing IRS Variants.


## Class Components

 * [***AmortizingCapitalizingAccruingSwap***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/fixfloat/AmortizingCapitalizingAccruingSwap.java)
 <i>AmortizingCapitalizingAccruingSwap</i> demonstrates the construction and Valuation of in-advance
 Amortizing, Accruing, and Capitalizing Swaps.

 * [***CustomFixFloatSwap***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/fixfloat/CustomFixFloatSwap.java)
 <i>CustomFixFloatSwap</i> demonstrates the Construction and Valuation of a Custom Fix-Float Swap.

 * [***InAdvanceIMMSwap***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/fixfloat/InAdvanceIMMSwap.java)
 <i>InAdvanceIMMSwap</i> demonstrates the Construction and Valuation of a In-Advance IMM Swap.

 * [***InAdvanceSwap***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/fixfloat/InAdvanceSwap.java)
 <i>InAdvanceSwap</i> discount curve calibration and input instrument calibration quote recovery. It shows
 the following:
 	* Construct the Array of Deposit/Swap Instruments and their Quotes from the given set of parameters.
  	* Construct the Deposit/Swap Instrument Set Stretch Builder.
 	* Set up the Linear Curve Calibrator using the following parameters:
 		* Cubic Exponential Mixture Basis Spline Set
 		* C<sub>k</sub> = 2
 		* Segment Curvature Penalty = 2
 		* Quadratic Rational Shape Controller
 		* Natural Boundary Setting
 	* Construct the Shape Preserving Discount Curve by applying the linear curve calibrator to the array of
 	Cash and Swap Stretches.
 	* Cross-Comparison of the Cash/Swap Calibration Instrument "Rate" metric across the different curve
 	construction methodologies.

 * [***InArrearsSwap***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/fixfloat/InArrearsSwap.java)
 <i>InArrearsSwap</i> demonstrates the Construction and Valuation of a In-Arrears Swap.

 * [***JurisdictionOTCIndexDefinitions***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/fixfloat/JurisdictionOTCIndexDefinitions.java)
 <i>JurisdictionOTCIndexDefinitions</i> contains all the pre-fixed definitions of the Jurisdiction-specific
 OTC Fix-Float IRS contracts.

 * [***JurisdictionOTCIndexSwaps***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/fixfloat/JurisdictionOTCIndexSwaps.java)
 <i>JurisdictionOTCIndexSwaps</i> contains curve construction and valuation of the common
 Jurisdiction-specific OTC IRS.

 * [***LongTenorSwap***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/fixfloat/LongTenorSwap.java)
 <i>LongTenorSwap</i> demonstrates the Construction and Valuation of In-Advance and In-Arrears Long Tenor
 Swap.

 * [***RollerCoasterSwap***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/fixfloat/RollerCoasterSwap.java)
 <i>RollerCoasterSwap</i> demonstrates the construction and Valuation of In-Advance Roller-Coaster Swap.

 * [***ShortTenorSwap***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/fixfloat/ShortTenorSwap.java)
 <i>ShortTenorSwap</i> demonstrates the Construction and Valuation of In-Advance and In-Arrears Short Tenor
 Swap.

 * [***StepUpStepDown***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/fixfloat/StepUpStepDown.java)
 <i>StepUpStepDown</i> demonstrates the construction and Valuation of in-advance step-up and step-down swaps.


## DROP Specifications

 * Main                     => https://lakshmidrip.github.io/DROP/
 * Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * GitHub                   => https://github.com/lakshmiDRIP/DROP
 * Repo Layout Taxonomy     => https://github.com/lakshmiDRIP/DROP/blob/master/Taxonomy.md
 * Javadoc                  => https://lakshmidrip.github.io/DROP/Javadoc/index.html
 * Technical Specifications => https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal
 * Release Versions         => https://lakshmidrip.github.io/DROP/version.html
 * Community Credits        => https://lakshmidrip.github.io/DROP/credits.html
 * Issues Catalog           => https://github.com/lakshmiDRIP/DROP/issues
 * JUnit                    => https://lakshmidrip.github.io/DROP/junit/index.html
 * Jacoco                   => https://lakshmidrip.github.io/DROP/jacoco/index.html
