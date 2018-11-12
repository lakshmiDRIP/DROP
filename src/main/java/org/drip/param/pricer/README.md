# DROP Param Pricer Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Param Pricer Package implements the Pricing Parameters Customization Settings Control.


## Class Components

 * [***CreditPricerParams***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/param/pricer/CreditPricerParams.java)
 <i>CreditPricerParams</i> contains the Credit Pricer Parameters - the discrete unit size, calibration mode
 on/off, survival to pay/end date, and the discretization scheme.

 * [***GenericPricer***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/param/pricer/GenericPricer.java)
 <i>GenericPricer</i> is the Base Stub on top which all the Custom Pricers are implemented.

 * [***HestonOptionPricerParams***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/param/pricer/HestonOptionPricerParams.java)
 <i>HestonOptionPricerParams</i> holds the parameters that drive the dynamics of the Heston stochastic
 volatility model.

 * [***PricerParams***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/param/pricer/PricerParams.java)
 <i>PricerParams</i> exposes the Parameters needed for the Pricing Run.


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
