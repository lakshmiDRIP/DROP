# DROP Graph Selection Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Graph Selection Package implements k<sup>th</sup> Order Statistics Selectors.


## Class Components

 * [***FloydRivestPartitionControl***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/selection/FloydRivestPartitionControl.java)
 <i>FloydRivestPartitionControl</i> implements the Control Parameters for the Floyd-Rivest Selection Algorithm.

 * [***FloydRivestSelector***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/selection/FloydRivestSelector.java)
 <i>FloydRivestSelector</i> implements the Floyd-Rivest Selection Algorithm.

 * [***HashSelector***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/selection/HashSelector.java)
 <i>HashSelector</i> implements the Hash-table Based Selection Algorithm.

 * [***IntroselectControl***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/selection/IntroselectControl.java)
 <i>IntroselectControl</i> contains the Introselect-based Control Schemes to augment Quickselect.

 * [***Introselector***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/selection/Introselector.java)
 <i>Introselector</i> implements the Introselect Algorithm.

 * [***MedianOfMediansSelector***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/selection/MedianOfMediansSelector.java)
 <i>MedianOfMediansSelector</i> implements the QuickSelect Algorithm using the Median-of-Medians Pivot Generation Strategy.

 * [***OrderStatisticSelector***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/selection/OrderStatisticSelector.java)
 <i>OrderStatisticSelector</i> exposes the Functionality to Select the k<sup>th</sup> Extremum Order Statistic.

 * [***PartialSortSelector***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/selection/PartialSortSelector.java)
 <i>PartialSortSelector</i> implements the Partial Sorting Based Selection Algorithm.

 * [***QuickSelector***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/selection/QuickSelector.java)
 <i>QuickSelector</i> implements the Hoare's QuickSelect Algorithm.


# References

 * Blum, M., R. W. Floyd, V. Pratt, R. L. Rivest, and R. E. Tarjan (1973): Time Bounds for Selection <i>Journal of Computer and System Sciences</i> <b>7 (4)</b> 448-461

 * Cormen, T., C. E. Leiserson, R. Rivest, and C. Stein (2009): <i>Introduction to Algorithms 3<sup>rd</sup> Edition</i> <b>MIT Press</b>

 * Eppstein, D. (2007): Blum-style Analysis of Quickselect https://11011110.github.io/blog/2007/10/09/blum-style-analysis-of.html

 * Floyd, R. W., and R. L. Rivest (1975): Expected Time Bounds for Selection <i>Communications of the ACM</i> <b>18 (3)</b> 165-172

 * Floyd, R. W., and R. L. Rivest (1975): The Algorithm SELECT; for finding the i<sup>th</sup> smallest of n Elements <i>Communications of the ACM</i> <b>18 (3)</b> 173

 * Hoare, C. A. R. (1961): Algorithm 65: Find <i>Communications of the ACM</i> <b>4 (1)</b> 321-322

 * Knuth, D. (1997): <i>The Art of Computer Programming 3<sup>rd</sup> Edition</i> <b>Addison-Wesley</b>

 * Musser, D. R. (1997): Introselect Sorting and Selection Algorithms <i>Software: Practice and Experience</i> <b>27 (8)</b> 983-993

 * Wikipedia (2019): Floyd-Rivest Algorithm https://en.wikipedia.org/wiki/Floyd%E2%80%93Rivest_algorithm

 * Wikipedia (2019): Quickselect https://en.wikipedia.org/wiki/Quickselect

 * Wikipedia (2019): Selection Algorithm https://en.wikipedia.org/wiki/Selection_algorithm

 * Wikipedia (2020): Introselect https://en.wikipedia.org/wiki/Introselect

 * Wikipedia (2020): Median Of Medians https://en.wikipedia.org/wiki/Median_of_medians


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
