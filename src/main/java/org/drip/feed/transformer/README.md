# DROP Feed Transformer Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Feed Transformer Package implements the Market Data Reconstitutive Feed Transformer.


## Class Components

 * [***CreditCDSIndexMarksReconstitutor***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/feed/transformer/CreditCDSIndexMarksReconstitutor.java)
 <i>CreditCDSIndexMarksReconstitutor</i> transforms the Credit CDS Index Closes - Feed Inputs into Formats
 suitable for Valuation Metrics and Sensitivities Generation.

 * [***FundingFixFloatMarksReconstitutor***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/feed/transformer/FundingFixFloatMarksReconstitutor.java)
 <i>FundingFixFloatMarksReconstitutor</i> transforms the Funding Instrument Manifest Measures (e.g., Forward
 Rate for Deposits, Forward Rate for Futures, and Swap Rates for Fix/Float Swap) Feed Inputs into Formats
 appropriate for Funding Curve Construction and Measure Generation.

 * [***FundingFuturesClosesReconstitutor***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/feed/transformer/FundingFuturesClosesReconstitutor.java)
 <i>FundingFuturesClosesReconstitutor</i> transforms the Funding Futures Closes- Feed Inputs into Formats
 suitable for Valuation Metrics and Sensitivities Generation.

 * [***GovvieTreasuryMarksReconstitutor***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/feed/transformer/GovvieTreasuryMarksReconstitutor.java)
 <i>GovvieTreasuryMarksReconstitutor</i> transforms the Treasury Marks (e.g., Yield) Feed Inputs into Formats
 appropriate for Govvie Curve Construction and Measure Generation.

 * [***OvernightIndexMarksReconstitutor***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/feed/transformer/OvernightIndexMarksReconstitutor.java)
 <i>OvernightIndexMarksReconstitutor</i> transforms the Overnight Instrument Manifest Measures (e.g.,
 Deposits and OIS) Feed Inputs into Formats appropriate for Overnight Curve Construction and Measure
 Generation.

 * [***TreasuryFuturesClosesReconstitutor***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/feed/transformer/TreasuryFuturesClosesReconstitutor.java)
 <i>TreasuryFuturesClosesReconstitutor</i> transforms the Treasury Futures Closes- Feed Inputs into Formats
 suitable for Valuation Metrics and Sensitivities Generation.


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
