# DROP Exposure Regression Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Exposure Regression Package computes the Regression Based Path Exposure Generation.

## Class Components

 * [***AndersenPykhtinSokolSegment***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/regression/AndersenPykhtinSokolSegment.java)
 <i>AndersenPykhtinSokolSegment</i> generates the Segment Regression Based Exposures off of the corresponding
 Pillar Vertexes using the Pykhtin (2009) Scheme with the Andersen, Pykhtin, and Sokol (2017) Adjustments
 applied.

 * [***AndersenPykhtinSokolStretch***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/regression/AndersenPykhtinSokolStretch.java)
 <i>AndersenPykhtinSokolStretch</i> generates the Regression Based Path Exposures off of the Pillar Vertexes
 using the Pykhtin (2009) Scheme. Eventual Unadjusted Variation Margin Calculation follows Andersen, Pykhtin,
 and Sokol (2017).

 * [***LocalVolatilityGenerationControl***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/regression/LocalVolatilityGenerationControl.java)
 <i>LocalVolatilityGenerationControl</i> holds the Parameters the control the Calculation of the Local
 Volatility in the Pykhtin (2009) Brownian Bridge Calibration.

 * [***PillarVertex***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/regression/PillarVertex.java)
 <i>PillarVertex</i> hold the Date and the Exposure of each Vertex Pillar.

 * [***PykhtinBrownianBridgeSegment***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/regression/PykhtinBrownianBridgeSegment.java)
 <i>PykhtinBrownianBridgeSegment</i> generates the Segment Regression Based Exposures off of the
 corresponding Pillar Vertexes using the Pykhtin (2009) Scheme.

 * [***PykhtinBrownianBridgeStretch***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/regression/PykhtinBrownianBridgeStretch.java)
 <i>PykhtinBrownianBridgeStretch</i> generates the Regression Based Path Exposures off of the Pillar Vertexes
 using the Pykhtin (2009) Scheme.

 * [***PykhtinPillar***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/regression/PykhtinPillar.java)
 <i>PykhtinPillar</i> holds the Details of the Pillar Vertex Realization Point - the Realization Value, the
 Order Index, the CDF, the Transform Variate, and the Local Volatility - in accordance with the Pykhtin
 (2009) Scheme.

 * [***PykhtinPillarDynamics***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/regression/PykhtinPillarDynamics.java)
 <i>PykhtinPillarDynamics</i> generates the Dynamics off of the Pillar Vertex Exposure Realizations to be
 used in eventual Exposure Regression using the Pykhtin (2009) Scheme.


# References

 * Andersen, L. B. G., M. Pykhtin, and A. Sokol (2017): Re-thinking Margin Period of Risk
 https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2902737 <b>eSSRN</b>

 * Andersen, L. B. G., M. Pykhtin, and A. Sokol (2017): Credit Exposure in the Presence of Initial Margin
 https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2806156 <b>eSSRN</b>

 * Albanese, C., and L. Andersen (2014): Accounting for OTC Derivatives: Funding Adjustments and the Re-
 Hypothecation Option https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2482955 <b>eSSRN</b>

 * Burgard, C., and M. Kjaer (2017): Derivatives Funding, Netting, and Accounting
 https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2534011 <b>eSSRN</b>

 * Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives Pricing
 <i>Risk</i> <b>21 (2)</b> 97-102


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
