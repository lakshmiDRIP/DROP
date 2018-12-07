# DROP Quant Linear Algebra Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Quant Linear Algebra Package implements the Linear Algebra Matrix Transform Library.


## Class Components

 * [***LinearizationOutput***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/quant/linearalgebra/LinearizationOutput.java)
 <i>LinearizationOutput</i> holds the output of a sequence of linearization operations. It contains the
 transformed original matrix, the transformed RHS, and the method used for the linearization operation.

 * [***LinearSystemSolver***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/quant/linearalgebra/LinearSystemSolver.java)
 <i>LinearSystemSolver</i> implements the solver for a system of linear equations given by

											A * x = B

 where A is the matrix, x the set of variables, and B is the result to be solved for. It exports the
 following functions:
 	* Row Regularization and Diagonal Pivoting
 	* Check for Diagonal Dominance
 	* Solving the linear system using any one of the following: Gaussian Elimination, Gauss Seidel reduction,
 		or matrix inversion.

 * [***Matrix***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/quant/linearalgebra/Matrix.java)
 <i>Matrix</i> implements Matrix manipulation routines. It exports the following functionality:
 	* Matrix Inversion using Closed form solutions (for low-dimension matrices), or using Gaussian
 		elimination
 	* Matrix Product
 	* Matrix Diagonalization and Diagonal Pivoting
 	* Matrix Regularization through Row Addition/Row Swap

 * [***MatrixComplementTransform***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/quant/linearalgebra/MatrixComplementTransform.java)
 <i>MatrixComplementTransform</i> holds the results of Matrix transforms on the source and the complement,
 e.g., during a Matrix Inversion Operation.

 * [***QR***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/quant/linearalgebra/QR.java)
 <i>QR</i> holds the Results of QR Decomposition - viz., the Q and the R Matrices.


## DROP Specifications

 * Main                     => https://lakshmidrip.github.io/DROP/
 * Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * GitHub                   => https://github.com/lakshmiDRIP/DROP
 * Javadoc                  => https://lakshmidrip.github.io/DROP/Javadoc/index.html
 * Repo Layout Taxonomy     => https://github.com/lakshmiDRIP/DROP/blob/master/Taxonomy.md
 * Technical Specifications => https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal
 * Release Versions         => https://lakshmidrip.github.io/DROP/version.html
 * Community Credits        => https://lakshmidrip.github.io/DROP/credits.html
 * Issues Catalog           => https://github.com/lakshmiDRIP/DROP/issues
 * JUnit                    => https://lakshmidrip.github.io/DROP/junit/index.html
 * Jacoco                   => https://lakshmidrip.github.io/DROP/jacoco/index.html
