# DROP Analytics Output Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Analytics Output Package holds the Period Product Targeted Valuation Measures.

## Class Components

 * [***BasketMeasures***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/output/BasketMeasures.java)
 <i>BasketMeasures</i> is the place holder for the analytical basket measures, optionally across scenarios.
 It contains the following scenario measure maps:
 	* Unadjusted Base Measures
 	* Flat delta/gamma bump measure maps for IR/credit/RR bump curves
 	* Component/tenor bump double maps for IR/credit/RR curves
 	* Flat/component recovery bumped measure maps for recovery bumped credit curves
 	* Custom scenario measure map

 * [***BondCouponMeasures***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/output/BondCouponMeasures.java)
 <i>BondCouponMeasures</i> encapsulates the parsimonious but complete set of the cash-flow oriented coupon
 measures generated out of a full bond analytics run to a given work-out. These are:
 	* DV01
 	* PV Measures (Coupon PV, Index Coupon PV, PV)

 * [***BondEOSMetrics***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/output/BondEOSMetrics.java)
 <i>BondEOSMetrics</i> carries the Option Adjusted Metrics for a Bond with Embedded Options.

 * [***BondRVMeasures***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/output/BondRVMeasures.java)
 <i>BondRVMeasures</i> encapsulates the comprehensive set of RV measures calculated for the bond to the
 appropriate exercise:
 	* Work-out Information
 	* Price, Yield, and Yield01
 	* Spread Measures: Asset Swap/Credit/G/I/OAS/PECS/TSY/Z
 	* Basis Measures: Bond Basis, Credit Basis, Yield Basis
 	* Duration Measures: Macaulay/Modified Duration, Convexity

 * [***BondWorkoutMeasures***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/output/BondWorkoutMeasures.java)
 <i>BondWorkoutMeasures</i> encapsulates the parsimonius yet complete set of measures generated out of a full
  bond analytics run to a given work-out. It contains the following:
 	* Credit Risky/Credit Riskless Clean/Dirty Coupon Measures
 	* Credit Risky/Credit Riskless Par/Principal PV
 	* Loss Measures such as expected Recovery, Loss on instantaneous default, and default exposure
 		with/without recovery
 	* Unit Coupon measures such as Accrued 01, first coupon/index rate

 * [***BulletMetrics***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/output/BulletMetrics.java)
 <i>BulletMetrics</i> holds the results of the Bullet Cash flow metrics estimate output.

 * [***ComponentMeasures***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/output/ComponentMeasures.java)
 <i>ComponentMeasures</i> is the place holder for analytical single component output measures, optionally
 across scenarios. It contains measure maps for the following scenarios:
 	* Unadjusted Base IR/credit curves
 	* Flat delta/gamma bump measure maps for IR/credit bump curves
 	* Tenor bump double maps for IR/credit curves
 	* Flat/recovery bumped measure maps for recovery bumped credit curves
 	* Measure Maps generated for Custom Scenarios
 	* Accessor Functions for the above fields
 	* Serialize into and de-serialize out of byte arrays

 * [***CompositePeriodAccrualMetrics***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/output/CompositePeriodAccrualMetrics.java)
 <i>CompositePeriodAccrualMetrics</i> holds the results of the compounded Composed period Accrual Metrics
 Estimate Output.

 * [***CompositePeriodCouponMetrics***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/output/CompositePeriodCouponMetrics.java)
 <i>CompositePeriodCouponMetrics</i> holds the results of the compounded Composed period Full Coupon Metrics
 Estimate Output.

 * [***ConvexityAdjustment***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/output/ConvexityAdjustment.java)
 <i>ConvexityAdjustment</i> holds the dynamical convexity Adjustments between the Latent States.

 * [***ExerciseInfo***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/output/ExerciseInfo.java)
 <i>ExerciseInfo</i> is a place-holder for the set of exercise information. It contains the exercise date,
 the exercise factor, and the exercise type. It also exposes methods to serialize/de-serialize off of byte
 arrays.

 * [***UnitPeriodConvexityMetrics***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/output/UnitPeriodConvexityMetrics.java)
 <i>UnitPeriodConvexityMetrics</i> holds the results of a unit composable period convexity metrics estimate
 output.

 * [***UnitPeriodMetrics***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/output/UnitPeriodMetrics.java)
 <i>UnitPeriodMetrics</i> holds the results of a unit composable period metrics estimate output.

## DROP Specifications
 * Main                     => https://lakshmidrip.github.io/DROP/
 * Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * GitHub                   => https://github.com/lakshmiDRIP/DROP
 * Javadoc                  => https://lakshmidrip.github.io/DROP/Javadoc/index.html
 * Technical Specifications => https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal
 * Release Versions         => https://lakshmidrip.github.io/DROP/version.html
 * Community Credits        => https://lakshmidrip.github.io/DROP/credits.html
 * Issues Catalog           => https://github.com/lakshmiDRIP/DROP/issues
 * JUnit                    => https://lakshmidrip.github.io/DROP/junit/index.html
 * Jacoco                   => https://lakshmidrip.github.io/DROP/jacoco/index.html
