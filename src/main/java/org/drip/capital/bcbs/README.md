# DROP Capital BCBS Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Capital BCBS Package implements BCBS and Jurisdictional Capital Ratios.


## Class Components

 * [***BalanceSheet***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/bcbs/BalanceSheet.java)
 <i>BalanceSheet</i> holds the Quantities used to compute the Capital/Liquidity Ratios in the BCBS Standards.

 * [***BalanceSheetCapital***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/bcbs/BalanceSheetCapital.java)
 <i>BalanceSheetCapital</i> holds the Quantities used to compute the Capital Compliance Ratios in the BCBS
 Standards.

 * [***BalanceSheetFunding***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/bcbs/BalanceSheetFunding.java)
 <i>BalanceSheetFunding</i> holds the Quantities used to compute the Stable FUnding Ratios in the BCBS
 Standards.

 * [***BalanceSheetLiquidity***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/bcbs/BalanceSheetLiquidity.java)
 <i>BalanceSheetLiquidity</i> holds the Liquidity Related Fields needed for computing the Compliance Ratios.

 * [***CapitalMetrics***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/bcbs/CapitalMetrics.java)
 <i>CapitalMetrics</i> holds the Realized Capital Metrics.

 * [***CapitalMetricsStandard***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/bcbs/CapitalMetricsStandard.java)
 <i>CapitalMetricsStandard</i> implements the Basel Capital Metrics Standards.

 * [***HighQualityLiquidAsset***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/bcbs/HighQualityLiquidAsset.java)
 <i>HighQualityLiquidAsset</i> contains the Amounts and the Settings associated with Levels 1, 2A, and 2B.

 * [***HighQualityLiquidAssetSettings***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/bcbs/HighQualityLiquidAssetSettings.java)
 <i>HighQualityLiquidAssetSettings</i> holds the Risk-Weights and the Haircuts associated with Levels 1, 2A,
 and 2B.

 * [***HighQualityLiquidAssetStandard***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/bcbs/HighQualityLiquidAssetStandard.java)
 <i>HighQualityLiquidAssetStandard</i> contains the Regulatory HQLA Ratios associated with Levels 1, 2A, and
 2B.

 * [***LiquidityMetrics***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/bcbs/LiquidityMetrics.java)
<i>LiquidityMetrics</i> holds the Realized Liquidity Metrics.


## References

 * Basel Committee on Banking Supervision (2017): Basel III Leverage Ratio Framework and Disclosure Requirements https://www.bis.org/publ/bcbs270.pdf

 * Central Banking (2013): Fed and FDIC agree 6% Leverage Ratio for US SIFIs https://www.centralbanking.com/central-banking/news/2280726/fed-and-fdic-agree-6-leverage-ratio-for-us-sifis

 * European Banking Agency (2013): Implementing Basel III in Europe: CRD IV Package https://eba.europa.eu/regulation-and-policy/implementing-basel-iii-europe

 * Federal Reserve (2014): Liquidity Coverage Ratio – Liquidity Risk Measurements, Standards, and Monitoring https://www.federalregister.gov/documents/2014/10/10/2014-22520

 * Wikipedia (2018): Basel III https://en.wikipedia.org/wiki/Basel_III


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
