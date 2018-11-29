# DROP Portfolio Construction ALM Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Portfolio Construction ALM Package implements the Sharpe-Tint Asset Liability Manager.


## Class Components

 * [***DiscountRate***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/alm/DiscountRate.java)
 <i>DiscountRate</i> holds the Cash Flow Discount Rate Parameters for each Type, i.e., Discount Rates for
 Working Age Income, Pension Benefits, and Basic Consumption.

 * [***ExpectedBasicConsumption***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/alm/ExpectedBasicConsumption.java)
 <i>ExpectedBasicConsumption</i> holds the Parameters required for estimating the Investor's Basic
 Consumption Profile.

 * [***ExpectedNonFinancialIncome***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/alm/ExpectedNonFinancialIncome.java)
 <i>ExpectedNonFinancialIncome</i> holds the Parameters required for estimating the Investor's Non-Financial
 Income Profile.

 * [***InvestorCliffSettings***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/alm/InvestorCliffSettings.java)
 <i>InvestorCliffSettings</i> contains the Investor's Time Cliff Settings Parameters such as the Retirement
 and the Mortality Ages.

 * [***NetLiabilityCashFlow***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/alm/NetLiabilityCashFlow.java)
 <i>NetLiabilityCashFlow</i> holds the Investor Time Snap's Singular Liability Flow Details.

 * [***NetLiabilityMetrics***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/alm/NetLiabilityMetrics.java)
 <i>NetLiabilityMetrics</i> holds the Results of the Computation of the Net Liability Cash Flows and PV
 Metrics.

 * [***NetLiabilityStream***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/alm/NetLiabilityStream.java)
 <i>NetLiabilityStream</i> holds the Investor's Horizon, Consumption, and Income Settings needed to generate
 and value the Net Liability Cash Flow Stream.


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
