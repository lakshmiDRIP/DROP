# DROP Sample Andersen 2017 VM Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Sample Andersen 2017 VM illustrates calculating Andersen Pykhtin Sokol Regression VM.


## Class Components

 * [***EnsembleTradeFlowAdjustment***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/andersen2017vm/EnsembleTradeFlowAdjustment.java)
 <i>EnsembleTradeFlowAdjustment</i> generates the Trade Flow Adjusted Variation Margin from Sparse Nodes for
 a Fix-Float Swap across the Ensemble of Paths.

 * [***EnsembleVariationMarginEstimate***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/andersen2017vm/EnsembleVariationMarginEstimate.java)
 <i>EnsembleVariationMarginEstimate</i> generates the Ensemble of Dense Variation Margin Estimates from
 Sparse Nodes for a Fix-Float Swap across the Ensemble of Paths.

 * [***FixFloatAggressiveLong***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/andersen2017vm/FixFloatAggressiveLong.java)
 <i>FixFloatAggressiveLong</i> generates the Ensemble of Dense Variation Margin Estimates and the eventual
 Collateralized Variation Margin from Sparse Nodes for a Long Fix-Float Swap across the Ensemble of Paths
 using the Andersen, Albanese, and Pykhtin (2017) Aggressive Scheme.

 * [***FixFloatAggressiveShort***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/andersen2017vm/FixFloatAggressiveShort.java)
 <i>FixFloatAggressiveShort</i> generates the Ensemble of Dense Variation Margin Estimates and the eventual
 Collateralized Variation Margin from Sparse Nodes for a Short Fix-Float Swap across the Ensemble of Paths
 using the Andersen, Albanese, and Pykhtin (2017) Aggressive Scheme.

 * [***FixFloatClassicalMinusLong***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/andersen2017vm/FixFloatClassicalMinusLong.java)
 <i>FixFloatClassicalMinusLong</i> generates the Ensemble of Dense Variation Margin Estimates and the
 eventual Collateralized Variation Margin from Sparse Nodes for a Long Fix-Float Swap across the Ensemble of
 Paths using the Andersen, Albanese, and Pykhtin (2017) Classical- Scheme.

 * [***FixFloatClassicalMinusShort***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/andersen2017vm/FixFloatClassicalMinusShort.java)
 <i>FixFloatClassicalMinusShort</i> generates the Ensemble of Dense Variation Margin Estimates and the
 eventual Collateralized Variation Margin from Sparse Nodes for a Short Fix-Float Swap across the Ensemble of
 Paths using the Andersen, Albanese, and Pykhtin (2017) Classical- Scheme.

 * [***FixFloatClassicalPlusLong***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/andersen2017vm/FixFloatClassicalPlusLong.java)
 <i>FixFloatClassicalPlusLong</i> generates the Ensemble of Dense Variation Margin Estimates and the eventual
 Collateralized Variation Margin from Sparse Nodes for a Long Fix-Float Swap across the Ensemble of Paths
 using the Andersen, Albanese, and Pykhtin (2017) Classical+ Scheme.
 
 * [***FixFloatClassicalPlusShort***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/andersen2017vm/FixFloatClassicalPlusShort.java)
 <i>FixFloatClassicalPlusShort</i> generates the Ensemble of Dense Variation Margin Estimates and the
 eventual Collateralized Variation Margin from Sparse Nodes for a Short Fix-Float Swap across the Ensemble of
 Paths using the Andersen, Albanese, and Pykhtin (2017) Classical+ Scheme

 * [***FixFloatConservativeLong***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/andersen2017vm/FixFloatConservativeLong.java)
 <i>FixFloatConservativeLong</i> generates the Ensemble of Dense Variation Margin Estimates and the eventual
 Collateralized Variation Margin from Sparse Nodes for a Long Fix-Float Swap across the Ensemble of Paths
 using the Andersen, Albanese, and Pykhtin (2017) Conservative Scheme.

 * [***FixFloatConservativeShort***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/andersen2017vm/FixFloatConservativeShort.java)
 <i>FixFloatConservativeShort</i> generates the Ensemble of Dense Variation Margin Estimates and the eventual
 Collateralized Variation Margin from Sparse Nodes for a Short Fix-Float Swap across the Ensemble of Paths
 using the Andersen, Albanese, and Pykhtin (2017) Conservative Scheme.

 * [***PathTradeFlowAdjustment***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/andersen2017vm/PathTradeFlowAdjustment.java)
 <i>PathTradeFlowAdjustment</i> generates the Trade Flow Adjusted Variation Margin from Sparse Nodes for a
 Fix-Float Swap.


## References

 * Albanese, C., and L. Andersen (2014): Accounting for OTC Derivatives: Funding Adjustments and the
 Re-Hypothecation Option, eSSRN, https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2482955 <b>eSSRN</b>

 * Andersen, L. B. G., M. Pykhtin, and A. Sokol (2017): Re-thinking Margin Period of Risk
 https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2902737 <b>eSSRN</b>

 * Andersen, L. B. G., M. Pykhtin, and A. Sokol (2017): Credit Exposure in the Presence of Initial Margin
 https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2806156 <b>eSSRN</b>

 * Burgard, C., and M. Kjaer (2017): Derivatives Funding, Netting, and Accounting
 https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2534011 <b>eSSRN</b>

 * Pykhtin, M. (2009): Modeling Counter-party Credit Exposure in the Presence of Margin Agreements
 http://www.risk-europe.com/protected/michael-pykhtin.pdf


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
