# DROP Product FX Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Product FX Package implements the FX Forwards, Cross Currency Swaps.


## Class Components

 * [***ComponentPair***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/fx/ComponentPair.java)
 <i>ComponentPair</i> contains the implementation of the dual cross currency components. It is composed of
 two different Rates Components - one each for each currency.

 * [***DomesticCollateralizedForeignForward***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/fx/DomesticCollateralizedForeignForward.java)
 <i>DomesticCollateralizedForeignForward</i> contains the Domestic Currency Collateralized Foreign Payout FX
 forward product contract details.

 * [***ForeignCollateralizedDomesticForward***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/fx/ForeignCollateralizedDomesticForward.java)
 <i>ForeignCollateralizedDomesticForward</i> contains the Foreign Currency Collateralized Domestic Payout FX
 forward product contract details.

 * [***FXForwardComponent***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/fx/FXForwardComponent.java)
 <i>FXForwardComponent</i> contains the Standard FX forward Component contract details - the effective date,
 the maturity date, the currency pair and the product code. It also exports a calibrator that computes the
 forward points from the discount curve.


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
