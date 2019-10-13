# DROP Analytics Cash-flow Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Analytics Cash-flow Package implements the Unit/Composite Cash Flow Periods.


## Class Components

 * [***Bullet***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/cashflow/Bullet.java)
 <i>Bullet</i> is designed to hold the Point Realizations of the Latent States relevant to Terminal Valuation
 of a Bullet Cash Flow. Current it contains the Period Dates, Period Latent State Identifiers, and the
 "Extensive" Fields.

 * [***Composable Unit Fixed Period***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/cashflow/ComposableUnitFixedPeriod.java)
 <i>ComposableUnitFixedPeriod</i> represents the Fixed Cash Flow Periods' Composable Period Details.
 Currently it holds the Accrual Start Date, the Accrual End Date, the Fixed Coupon, the Basis Spread, the 
 Coupon Rate, and the Accrual Day Counts, as well as the EOM Adjustment Flags.

 * [***Composable Unit Floating Period***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/cashflow/ComposableUnitFloatingPeriod.java)
 <i>ComposableUnitFloatingPeriod</i> contains the Floating Cash Flow Periods' Composable Period Details.
 Currently it holds the Accrual Start Date, the Accrual End Date, the Fixing Date, the Spread over the
 Index, and the corresponding Reference Index Period.

 * [***Composable Unit Period***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/cashflow/ComposableUnitPeriod.java)
 <i>ComposableUnitPeriod</i> represents the Cash Flow Periods' Composable Unit Period Details. Currently it
 holds the Accrual Start Date, the Accrual End Date, the Fixed Coupon, the Basis Spread, the Coupon and the
 Accrual Day Counts, as well as the EOM Adjustment Flags.

 * [***Composite Fixed Period***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/cashflow/CompositeFixedPeriod.java)
 <i>CompositeFixedPeriod</i> implements the composed fixed coupon period functionality. It customizes the
 Period Quote Set and the Basis Quote for the Fixed Period.

 * [***Composite Floating Period***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/cashflow/CompositeFloatingPeriod.java)
 <i>CompositeFloatingPeriod</i> implements the Composite Floating Coupon Period Functionality. It customizes
 the Period Quote Set and the Basis Quote for the Floating Period.

 * [***Composite Period***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/cashflow/CompositePeriod.java)
 <i>CompositePeriod</i> implements the Composite Coupon Period Functionality. It contains the Composite
 Period Coupon Frequency, Tenor, Accrual Compounding Rule, Day Count, Base Notional, Coupon/Notional
 Schedules, Pay Currency, Credit Label, FX Fixing Setting, and the List of Composable Period Units.

 * [***Loss Quadrature Metrics***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/cashflow/LossQuadratureMetrics.java)
 <i>LossPeriodCurveFactors</i> is an Implementation of the Period Class enhanced by the Loss Period Measures.
 It exports the following Functionality:
 	* Start/End Survival Probabilities
 	* Period Effective Notional/Recovery/Discount Factor
 	* Serialization into and De-serialization out of Byte Arrays

 * [***Reference Index Period***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/cashflow/ReferenceIndexPeriod.java)
 <i>ReferenceIndexPeriod</i> contains the Cash Flow Period Details. Currently it holds the Start Date, the
 End Date, the Fixing Date, and the Reference Latent State Label.


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
