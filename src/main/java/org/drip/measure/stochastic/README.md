# DROP Measure Stochastic Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Measure Stochastic implements R<sup>1</sup> R<sup>1</sup> To R<sup>1</sup> Stochastic Process.


## Class Components

 * [***LabelBase***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/stochastic/LabelBase.java)
 <i>LabelBase</i> is the Base Class that holds the Labeled Latent State Vertex Content.

 * [***LabelCorrelation***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/stochastic/LabelCorrelation.java)
 <i>LabelCorrelation</i> holds the Correlations between any Stochastic Variates identified by their Labels.

 * [***LabelCovariance***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/stochastic/LabelCovariance.java)
 <i>LabelCovariance</i> holds the Covariance between any Stochastic Variates identified by their Labels, as well as their Means.

 * [***LabelRdVertex***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/stochastic/LabelRdVertex.java)
 <i>LabelRdVertex</i> holds the Labeled R<sup>d</sup> Multi-Factor Latent State Vertex Realizations.

 * [***R1R1ToR1***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/stochastic/R1R1ToR1.java)
 <i>R1R1ToR1</i> interface exposes the stubs for the evaluation of the objective function and its derivatives
 for a R<sup>1</sup> Deterministic + R<sup>1</sup> Random To R<sup>1</sup> Stochastic Function with one
 Random Component.


## DROP Specifications

 * Albanese, C., S. Caenazzo, and O. Frankel (2017): Regression Sensitivities for Initial Margin Calculations
 https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2763488 <b>eSSRN</b>

 * Andersen, L. B. G., M. Pykhtin, and A. Sokol (2017): Credit Exposure in the Presence of Initial Margin
 https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2806156 <b>eSSRN</b>

 * Anfuso, F., D. Aziz, P. Giltinan, and K. Loukopoulus (2017): A Sound Modeling and Back-testing Framework
 for Forecasting Initial Margin Requirements https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2716279
 <b>eSSRN</b>

 * Caspers, P., P. Giltinan, R. Lichters, and N. Nowaczyk (2017): Forecasting Initial Margin Requirements - A
 Model Evaluation https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2911167 <b>eSSRN</b>

 * International Swaps and Derivatives Association (2017): SIMM v2.0 Methodology
 https://www.isda.org/a/oFiDE/isda-simm-v2.pdf


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
