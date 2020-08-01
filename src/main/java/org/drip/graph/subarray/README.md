# DROP Graph Sub-array Path Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Graph Sub-array Path Package implements the Algorithms for Sub-set Sum, k-Sum, and Maximum Sub-array Problems.


## Class Components

 * [***HorowitzSahni***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/subarray/HorowitzSahni.java)
 <i>HorowitzSahni</i> implements the Sub-set Sum Check using the Horowitz-Sahni Scheme.

 * [***Kadane***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/subarray/Kadane.java)
 <i>Kadane</i> implements the Kadane Algorithm for the Maximum Sub-array Problem.

 * [***PolynomialTimeApproximate***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/subarray/PolynomialTimeApproximate.java)
 <i>PolynomialTimeApproximate</i> implements the Approximate Sub-set Sum Check using a Polynomial Time Scheme.

 * [***PseudoPolynomialDP***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/subarray/PseudoPolynomialDP.java)
 <i>PseudoPolynomialDP</i> implements the Sub-set Sum Check using a Pseudo-Polynomial Time Dynamic Programming Scheme.

 * [***SubsetSum***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/subarray/SubsetSum.java)
 <i>SubsetSum</i> finds out is there is a non-empty Subset in the specified Array that adds up to the Specified Target.

 * [***ThreeSum***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/subarray/ThreeSum.java)
 <i>ThreeSum</i> exposes the Check that indicates if the Set of Numbers contains 3 that Sum to Zero.

 * [***ThreeSumQuadraticComparator***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/subarray/ThreeSumQuadraticComparator.java)
 <i>ThreeSumQuadraticComparator</i> implements the Check that indicates if the Set of Numbers contains 3 that Sum to Zero using a Binary Search Comparator, leading to a Quadratic Time Algorithm.

 * [***ThreeSumQuadraticHash***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/subarray/ThreeSumQuadraticHash.java)
 <i>ThreeSumQuadraticHash</i> implements the Check that indicates if the Set of Numbers contains 3 that Sum to Zero using a Hash-table, leading to a Quadratic Time Algorithm.

 * [***ThreeSumVariantBuilder***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/subarray/ThreeSumVariantBuilder.java)
 <i>ThreeSumVariantBuilder</i> converts the specified 3SUM Variant into a Standard 3SUM Problem.


# References

 * Bentley, J. (1984): Programming Pearls: Algorithm Design Techniques <i>Communications of the ACM</i> <b>27 (9)</b> 865-873

 * Bentley, J. (1989): <i>Programming Pearls <sup>nd</sup> Edition</i> <b>Addison-Wesley</b> Reading MA

 * Bringmann, K. (2017): A near-linear Pseudo-polynomial Time Algorithm for Subset Sums <i>Proceedings of the 28<sup>th</sup> Annual ACM SIAM Symposium on Discrete Algorithms</i> 1073-1084

 * Chan, T. M. (2018): More Logarithmic Factor Speedups for 3SUM, (median+) Convolution, and some Geometric 3SUM Hard Problems <i>Proceedings of the 29<sup>th</sup> Annual ACM SIAM Symposium on Discrete Algorithms</i> 881-897

 * Gajentaan, A., and M. H. Overmars (1995): On a Class of O(n<sup>2</sup>) Problems in Computational Geometry <i>Computational Geometry: Theory and Applications</i> <b>5 (3)</b> 165-185

 * Gries, D. (1982): A Note on a Standard Strategy for developing Loop Invariants and Loops <i>Science of Computer Programming</i> <b>2 (3)</b> 207-214

 * Horowitz, E., and S. Sahni (1974): Computing Partitions with Applications to the Knapsack Problem <i>Journal of the ACM</i> <b>21 (2)</b> 277-292

 * Kleinberg, J., and E. Tardos (2022): <i>Algorithm Design 2<sup>nd</sup> Edition</i> <b>Pearson</b>

 * Koiliaris, K., and C. Xu (2016): A Faster Pseudo-polynomial Time Algorithm for Subset Sum https://arxiv.org/abs/1507.02318 <b>arXiV</b>

 * Kopelowitz, T., S. Pettie, and E. Porat (2014): Higher Lower Bounds from the 3SUM Conjecture https://arxiv.org/abs/1407.6756 <b>arXiV</b>

 * Patrascu, M. (2010): Towards Polynomial Lower Bounds for Dynamic Problems <i>Proceedings of the 42<sup>nd</sup> ACM Symposium on Theory of Computing</i> 603-610

 * Takaoka, T. (2002): Efficient Algorithms for the Maximum Sub-array Problem by Distance Matrix Multiplication https://www.sciencedirect.com/science/article/pii/S1571066104003135?via%3Dihub

 * Wikipedia (2020): 3Sum https://en.wikipedia.org/wiki/3SUM

 * Wikipedia (2020): Maximum Sub-array Problem https://en.wikipedia.org/wiki/Maximum_subarray_problem

 * Wikipedia (2020): Subset Sum Problem https://en.wikipedia.org/wiki/Subset_sum_problem


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
