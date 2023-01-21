# DROP Regression Core Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Regression Core implements the Regression Engine Core and the Unit Regressors.


## Class Components

 * [***RegressionEngine***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/regression/core/RegressionEngine.java)
 <i>RegressionEngine</i> provides the control and frame-work functionality for the General Purpose Regression
 Suite. It invokes the following steps as part of the execution.
 	* Initialize the regression environment. This step sets up the regression sets, and adds individual
 		regressors to the set.
 	* Invoke the regressors in each set one by one.
 	* Collect the results and details of the regression runs.
 	* Compile the regression statistics.
 	* Optionally display the regression statistics.

 * [***RegressionRunDetail***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/regression/core/RegressionRunDetail.java)
 <i>RegressionRunDetail</i> contains named field level detailed output of the regression activity.

 * [***RegressionRunOutput***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/regression/core/RegressionRunOutput.java)
 <i>RegressionRunOutput</i> contains the output of a single regression activity. It holds the following.
 	* The execution time
 	* The Success/failure status of the run
 	* The regression scenario that was executed
 	* The Completion time for the regression module
 	* The Regression Run Detail for the regression run

 * [***RegressorSet***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/regression/core/RegressorSet.java)
 <i>RegressorSet</i> interface provides the Regression set stubs. It contains a set regressors and is
 associated with a unique name. It provides functionality to set up the contained regressors.

 * [***UnitRegressionExecutor***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/regression/core/UnitRegressionExecutor.java)
 <i>UnitRegressionExecutor</i> implements the UnitRegressor, and splits the regression execution into pre-,
 execute, and post-regression. It provides default implementations for pre-regression and post-regression.
 Most typical regressors only need to over-ride the execRegression method.

 * [***UnitRegressionStat***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/regression/core/UnitRegressionStat.java)
 <i>UnitRegressionStat</i> creates the statistical details for the Unit Regressor. It holds the following:
 	* Execution Initialization Delay
 	* Execution time mean, variance, maximum, and minimum
 	* The full list of individual execution times

 * [***UnitRegressor***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/regression/core/UnitRegressor.java)
 <i>UnitRegressor</i> provides the stub functionality for the Individual Regressors. Its derived classes
 implement the actual regression run. Individual regressors are named.


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
