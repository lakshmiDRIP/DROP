# DROP SIMM Margin Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP SIMM Margin Package implements the ISDA SIMM Risk Factor Margin Metrics.


## Class Components

 * [***BucketAggregate***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/margin/BucketAggregate.java)
 <i>BucketAggregate</i> holds the Single Bucket Sensitivity Margin, the Cumulative Bucket Risk Factor
 Sensitivity Margin, as well as the Aggregate Risk Factor Maps.

 * [***BucketAggregateCR***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/margin/BucketAggregateCR.java)
 <i>BucketAggregateCR</i> holds the Single Bucket CR Sensitivity Margin, the Cumulative CR Bucket Risk Factor
 Sensitivity Margin, as well as the Aggregate CR Risk Factor Maps.

 * [***BucketAggregateIR***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/margin/BucketAggregateIR.java)
 <i>BucketAggregateIR</i> holds the Single Bucket IR Sensitivity Margin, the Cumulative Bucket Risk Factor
 Sensitivity Margin, as well as the IR Aggregate Risk Factor Maps.

 * [***RiskClassAggregate***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/margin/RiskClassAggregate.java)
 <i>RiskClassAggregate</i> holds the Bucket Aggregate and the Computed SIMM Margin for a single Risk Class.

 * [***RiskClassAggregateCR***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/margin/RiskClassAggregateCR.java)
 <i>RiskClassAggregateCR</i> holds the CR Bucket Aggregate and the Computed SIMM Margin for a single Risk
 Class.

 * [***RiskClassAggregateIR***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/margin/RiskClassAggregateIR.java)
 <i>RiskClassAggregateIR</i> holds the Bucket Aggregate and the Computed SIMM Margin for the IR Risk Class.

 * [***RiskFactorAggregate***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/margin/RiskFactorAggregate.java)
 <i>RiskFactorAggregate</i> holds the Weighted and Normalized Bucket Risk Factor Sensitivity along with the
 Normalization Factors.

 * [***RiskFactorAggregateCR***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/margin/RiskFactorAggregateCR.java)
 <i>RiskFactorAggregateCR</i> holds the Sensitivity Margin Aggregates for each of the CR Risk Factors - both
 Qualifying and Non-qualifying.

 * [***RiskFactorAggregateIR***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/margin/RiskFactorAggregateIR.java)
 <i>RiskFactorAggregateIR</i> holds the Sensitivity Margin Aggregates for each of the IR Risk Factors - OIS,
 LIBOR 1M, LIBOR 3M, LIBOR 6M LIBOR 12M, PRIME, and MUNICIPAL.

 * [***RiskMeasureAggregate***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/margin/RiskMeasureAggregate.java)
 <i>RiskMeasureAggregate</i> holds the Bucket Aggregate and the Computed SIMM Margin for a single Risk
 Measure.

 * [***RiskMeasureAggregateCR***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/margin/RiskMeasureAggregateCR.java)
 <i>RiskMeasureAggregateCR</i> holds the CR Bucket Aggregate and the Computed SIMM Margin for a single Risk
 Measure.

 * [***RiskMeasureAggregateIR***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/margin/RiskMeasureAggregateIR.java)
 <i>RiskMeasureAggregateIR</i> holds the Bucket Aggregate and the Computed SIMM Margin for the IR Risk
 Measure.

 * [***SensitivityAggregateCR***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/margin/SensitivityAggregateCR.java)
 <i>SensitivityAggregateCR</i> holds the IM Margin Sensitivity Co-variances within a single Bucket for each
 of the CR Component Risk Factors.

 * [***SensitivityAggregateIR***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/margin/SensitivityAggregateIR.java)
 <i>SensitivityAggregateIR</i> holds the IM Margin Sensitivity Co-variances within a single Currency for each
 of the IR Risk Factors - OIS, LIBOR 1M, LIBOR 3M, LIBOR 6M LIBOR 12M, PRIME, and MUNICIPAL.


## References

 * Andersen, L. B. G., M. Pykhtin, and A. Sokol (2017): Credit Exposure in the Presence of Initial Margin
 	https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2806156 <b>eSSRN</b>

 * Albanese, C., S. Caenazzo, and O. Frankel (2017): Regression Sensitivities for Initial Margin Calculations
 	https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2763488 <b>eSSRN</b>

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
