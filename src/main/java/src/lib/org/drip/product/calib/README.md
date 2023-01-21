# DROP Product Calib Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Product Calib Package holds the Curve/Surface Calibration Quote Sets.


## Class Components

 * [***CompositePeriodQuoteSet***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/calib/CompositePeriodQuoteSet.java)
 <i>CompositePeriodQuoteSet</i> implements the composite period's calibration quote set functionality.

 * [***DepositComponentQuoteSet***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/calib/DepositComponentQuoteSet.java)
 <i>DepositComponentQuoteSet</i> extends the ProductQuoteSet by implementing the Calibration Parameters for
 the Deposit Component. Currently it exposes the PV and the Rate Quote Fields.

 * [***FixedStreamQuoteSet***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/calib/FixedStreamQuoteSet.java)
 <i>FixedStreamQuoteSet</i> extends the ProductQuoteSet by implementing the Calibration Parameters for the
 Fixed Stream. Currently it exposes the PV and the Coupon Quote Fields.

 * [***FixFloatQuoteSet***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/calib/FixFloatQuoteSet.java)
 <i>FixFloatQuoteSet</i> extends the ProductQuoteSet by implementing the Calibration Parameters for the
 Fix-Float Swap Component. Currently it exposes the PV, the Reference Basis, and the Derived Basis Quote
 Fields.

 * [***FloatFloatQuoteSet***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/calib/FloatFloatQuoteSet.java)
 <i>FloatFloatQuoteSet</i> extends the ProductQuoteSet by implementing the Calibration Parameters for the
 Float-Float Swap Component. Currently it exposes the PV, the Reference Basis, and the Derived Basis Quote
 Fields.

 * [***FloatingStreamQuoteSet***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/calib/FloatingStreamQuoteSet.java)
 <i>FloatingStreamQuoteSet</i> extends the ProductQuoteSet by implementing the Calibration Parameters for the
 Floating Stream. Currently it exposes the PV and the Spread Quote Fields.

 * [***FRAComponentQuoteSet***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/calib/FRAComponentQuoteSet.java)
 <i>FRAComponentQuoteSet</i> extends the ProductQuoteSet by implementing the Calibration Parameters for the
 FRA Component. Currently it only exposes the FRA Rate Quote Field.

 * [***FuturesComponentQuoteSet***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/calib/FuturesComponentQuoteSet.java)
 <i>FuturesComponentQuoteSet</i> extends the ProductQuoteSet by implementing the Calibration Parameters for
 the Short-term Interest Rate Futures Component. Currently it exposes the Price and the Rate Quote Fields.

 * [***FXForwardQuoteSet***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/calib/FXForwardQuoteSet.java)
 <i>FXForwardQuoteSet</i> extends the ProductQuoteSet by implementing the Calibration Parameters for the FX
 Forward Component. Currently it exposes the Outright and the PIP Fields.

 * [***ProductQuoteSet***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/calib/ProductQuoteSet.java)
 <i>ProductQuoteSet</i> implements the Calibratable type-free Product Quote Shell. The derived calibration
 sets provide custom accessors.

 * [***StreamQuoteSet***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/calib/StreamQuoteSet.java)
 <i>StreamQuoteSet</i> extends the ProductQuoteSet by implementing the Calibration Parameters for the
 Universal Stream.

 * [***TreasuryBondQuoteSet***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/calib/TreasuryBondQuoteSet.java)
 <i>TreasuryBondQuoteSet</i> extends the ProductQuoteSet by implementing the Calibration Parameters for the
 Treasury Bond Component. Currently it exposes the Yield.

 * [***VolatilityProductQuoteSet***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/calib/VolatilityProductQuoteSet.java)
 <i>VolatilityProductQuoteSet</i> implements the Calibratable Volatility Product Quote Shell.


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
