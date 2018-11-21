# DROP Product Credit Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Product Credit Package implements the Credit Products - Components and Baskets.


## Class Components

 * [***BondBasket***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/credit/BondBasket.java)
 <i>BondBasket</i> implements the bond basket product contract details. Contains the basket name, basket
 notional, component bonds, and their weights.

 * [***BondComponent***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/credit/BondComponent.java)
 <i>BondComponent</i> is the base class that extends CreditComponent abstract class and implements the
 functionality behind bonds of all kinds. Bond static data is captured in a set of 11 container classes;
 BondTSYParams, BondCouponParams, BondNotionalParams, BondFloaterParams, BondCurrencyParams,
 BondIdentifierParams, CompCRValParams, BondCFTerminationEvent, BondFixedPeriodGenerationParams, and one
 EmbeddedOptionSchedule object instance each for the call and the put objects. Each of these parameter set
 can be set separately.

 * [***CDSBasket***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/credit/CDSBasket.java)
 <i>CDSBasket</i> implements the basket default swap product contract details. It contains effective date,
 maturity date, coupon, coupon day count, coupon frequency, basket components, basket notional, loss pay lag,
 and optionally the outstanding notional schedule and the flat basket recovery. It also contains methods to
 serialize out of and de serialize into byte arrays.

 * [***CDSComponent***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/credit/CDSComponent.java)
 <i>CDSComponent</i> implements the credit default swap product contract details. It exposes the following
 functionality:
 	* Methods to extract effective date, maturity date, coupon, coupon day count, coupon frequency,
 	contingent credit, currency, basket notional, credit valuation parameters, and optionally the outstanding
 	notional schedule.
 	* Methods to compute the Jacobians to/from quote to latent state/manifest measures
 	* Serialization into and de serialization out of byte arrays
 	* CDS specific methods such as such loss metric/Jacobian estimation, quote flat spread calibration etc:


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
