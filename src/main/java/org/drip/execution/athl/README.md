# DROP Execution ATHL Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Execution ATHL Package implements the Almgren, Thum, Hauptmann, and Li (2005) Calibration.

## Class Components

 * [***Calibration Empirics***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/athl/CalibrationEmpirics.java)
 <i>CalibrationEmpirics</i> contains the Universal Market Impact Exponent/Coefficients that have been
 determined empirically by Almgren, Thum, Hauptmann, and Li (2005), using the Parameterization of Almgren
 (2003).

 * [***Dynamics Parameters***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/athl/DynamicsParameters.java)
 <i>DynamicsParameters</i> generates the Variants of the Market Dynamics Parameters constructed using the
 Methodologies presented in Almgren, Thum, Hauptmann, and Li (2005), using the Parameterization of Almgren
 (2003).

 * [***IJK***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/athl/IJK.java)
 <i>IJK</i> holds the Empirical Signals that have been emitted off of a Transaction Run using the Scheme by
 Almgren, Thum, Hauptmann, and Li (2005), using the Parameterization of Almgren (2003).

 * [***Permanent Impact No Arbitrage***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/athl/PermanentImpactNoArbitrage.java)
 <i>PermanentImpactNoArbitrage</i> implements the Linear Permanent Market Impact with Coefficients that have
  been determined empirically by Almgren, Thum, Hauptmann, and Li (2005), using the no Quasi-Arbitrage
  Criterion identified by Huberman and Stanzl (2004).

 * [***Permanent Impact Quasi Arbitrage***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/athl/PermanentImpactQuasiArbitrage.java)
 <i>PermanentImpactQuasiArbitrage</i> implements the Linear Permanent Market Impact with Coefficients that
 have been determined empirically by Almgren, Thum, Hauptmann, and Li (2005), independent of the no Quasi-
 Arbitrage Criterion identified by Huberman and Stanzl (2004).

 * [***Temporary Impact***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/athl/TemporaryImpact.java)
 <i>TemporaryImpact</i> implements the Temporary Market Impact with Exponent/Coefficients that have been
 determined empirically by Almgren, Thum, Hauptmann, and Li (2005), using the Parameterization of Almgren
 (2003).

 * [***Transaction Realization***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/athl/TransactionRealization.java)
 <i>TransactionRealization</i> holds the Suite of Empirical Drift/Wander Signals that have been emitted off
 of a Transaction Run using the Scheme by Almgren, Thum, Hauptmann, and Li (2005), using the Parameterization
 of Almgren (2003).

 * [***Transaction Signal***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/athl/TransactionSignal.java)
 <i>TransactionSignal</i> holds the Realized Empirical Signals that have been emitted off of a Transaction
 Run, decomposed using the Scheme by Almgren, Thum, Hauptmann, and Li (2005), based off of the
 Parameterization of Almgren (2003).


# References

 * Almgren, R., and N. Chriss (1999): Value under Liquidation <i>Risk</i> <b>12 (12)</b>

 * Almgren, R., and N. Chriss (2000): Optimal Execution of Portfolio Transactions <i>Journal of Risk</i> <b>3
 	(2)</b> 5-39

 * Almgren, R. (2003): Optimal Execution with Nonlinear Impact Functions and Trading-Enhanced Risk <i>Applied
 	Mathematical Finance</i> <b>10 (1)</b> 1-18

 * Almgren, R., and N. Chriss (2003): Bidding Principles <i>Risk</i> 97-102

 * Almgren, R., C. Thum, E. Hauptmann, and H. Li (2005): Equity Market Impact <i>Risk</i> <b>18 (7)</b> 57-62

 * Huberman, G., and W. Stanzl (2004): Price Manipulation and Quasi-arbitrage <i>Econometrics</i> <b>72
 	(4)</b> 1247-1275


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
