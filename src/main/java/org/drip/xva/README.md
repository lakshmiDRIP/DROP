# DROP XVA

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP XVA implements Valuation Adjustments that account for Collateral, CC Credit/Debt and Funding Overhead.


## Component Packages

 * [***Basel***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/basel)
 DROP XVA Basel Package implements the XVA Based Basel Accounting Measures.

 * [***Definition***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/definition)
 DROP XVA Definition Package holds the XVA Definition - Close Out, Universe.

 * [***Derivative***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/derivative)
 DROP XVA Derivative Package implements the Burgard Kjaer Dynamic Portfolio Replication.

 * [***Dynamics***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/dynamics)
 DROP XVA Dynamics Package implements the XVA Dynamics - Settings and Evolution.

 * [***Gross***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/gross)
 DROP XVA Gross Package implements the XVA Gross Adiabat Exposure Aggregation.

 * [***Hypothecation***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/hypothecation)
 DROP XVA Hypothecation Package implements the XVA Hypothecation Group Amount Estimation.

 * [***Netting***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/netting)
 DROP XVA Netting Package holds the Credit/Debt/Funding Netting Groups.

 * [***PDE***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/pde)
 DROP XVA PDE Package implements the Burgard Kjaer PDE Evolution Scheme.

 * [***Proto***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/proto)
 DROP XVA Proto Package contains the Collateral, Counter Party, Netting Groups.

 * [***Settings***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/settings)
 DROP XVA Settings Package holds the XVA Group and Path Settings.

 * [***Strategy***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/strategy)
 DROP XVA Strategy Package contains the Replication Strategy Based Funding/Netting Group.

 * [***Topology***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/topology)
 DROP XVA Topology Package holds the Collateral, Credit/Debt, Funding Topologies.

 * [***Vertex***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/vertex)
 DROP XVA Vertex Package implements the XVA Hypothecation Group Vertex Generators.


# References

 * Albanese, C., and L. Andersen (2014): Accounting for OTC Derivatives: Funding Adjustments and the
 	Re-Hypothecation Option https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2482955 <b>eSSRN</b>

 * Albanese, C., L. Andersen, and, S. Iabichino (2015): The FVA Puzzle: Accounting, Risk Management, and
 	Collateral Trading https://papers.ssrn.com/sol3/paper.cfm?abstract_id_2517301 <b>eSSRN</b>

 * Andersen, L. B. G., M. Pykhtin, and A. Sokol (2017): Re-thinking Margin Period of Risk
 	https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2902737 <b>eSSRN</b>

 * Andersen, L. B. G., M. Pykhtin, and A. Sokol (2017): Credit Exposure in the Presence of Initial Margin
 	https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2806156 <b>eSSRN</b>

 * Anfuso, F., D. Aziz, P. Giltinan, and K Loukopoulus (2017): A Sound Modeling and Back testing Framework
 	for Forecasting Initial Margin Requirements https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2716279
 	<b>eSSRN</b>

 * BCBS (2012): <i>Consultative Document: Application of Own Credit Risk Adjustments to Derivatives</i>
 	<b>Basel Committee on Banking Supervision</b>

 * BCBS (2015): <i>Margin Requirements for Non-centrally Cleared Derivatives</i>
 	https://www.bis.org/bcbs/publ/d317.pdf

 * Burgard, C., and M. Kjaer (2013): Funding Strategies, Funding Costs <i>Risk</i> <b>24 (12)</b> 82-87

 * Burgard, C., and M. Kjaer (2014): PDE Representations of Derivatives with Bilateral Counter-party Risk and
 	Funding Costs <i>Journal of Credit Risk</i> <b>7 (3)</b> 1-19

 * Burgard, C., and M. Kjaer (2014): In the Balance <i>Risk</i> <b>24 (11)</b> 72-75

 * Burgard, C., and M. Kjaer (2017): Derivatives Funding, Netting, and Accounting
 	https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2534011 <b>eSSRN</b>

 * Cesari, G., J. Aquilina, N. Charpillon, X. Filipovic, G. Lee, and L. Manda (2009): <i>Modeling, Pricing,
 	and Hedging Counter-party Credit Exposure - A Technical Guide</i> <b>Springer Finance</b> New York

 * Gregory, J. (2009): Being Two-faced over Counter-party Credit Risk <i>Risk</i> <b>20 (2)</b> 86-90

 * Li, B., and Y. Tang (2007): <i>Quantitative Analysis, Derivatives Modeling, and Trading Strategies in the
 	Presence of Counter-party Credit Risk for the Fixed Income Market</i> <b>World Scientific Publishing</b>
 	Singapore

 * Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives Pricing
 	<i>Risk</i> <b>21 (2)</b> 97-102

 * Pykhtin, M. (2009): Modeling Credit Exposure for Collateralized Counter-parties <i>Journal of Credit
 	Risk</i> <b>5 (4)</b> 3-27


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
