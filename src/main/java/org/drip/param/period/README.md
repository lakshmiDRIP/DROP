# DROP Param Period Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Param Period Package implements the Composite Composable Period Builder Settings.


## Class Components

 * [***ComposableFixedUnitSetting***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/param/period/ComposableFixedUnitSetting.java)
 <i>ComposableFixedUnitSetting</i> contains the fixed unit details. Currently it holds the coupon currency,
 the fixed coupon, and the basis.

 * [***ComposableFloatingUnitSetting***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/param/period/ComposableFloatingUnitSetting.java)
 <i>ComposableFloatingUnitSetting</i> contains the cash flow period composable sub period details.

 * [***ComposableUnitBuilderSetting***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/param/period/ComposableUnitBuilderSetting.java)
 <i>ComposableUnitBuilderSetting</i> contains the composable unit builder details. Currently it holds the
 coupon currency, the fixed coupon, and the basis.

 * [***CompositePeriodSetting***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/param/period/CompositePeriodSetting.java)
 <i>CompositePeriodSetting</i> implements the custom setting parameters for the composite coupon period.

 * [***FixingSetting***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/param/period/FixingSetting.java)
 <i>FixingSetting</i> implements the custom setting parameters for the Latent State Fixing Settings.

 * [***UnitCouponAccrualSetting***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/param/period/UnitCouponAccrualSetting.java)
 <i>UnitCouponAccrualSetting</i> contains the cash flow periods Coupon/Accrual details. Currently it holds
 the frequency, the calendar, the coupon/accrual day counts and the EOM adjustment flags, and flag indicating
 whether the coupon is computed from frequency.


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
