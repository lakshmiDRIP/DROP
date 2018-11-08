# DROP Function R<sup>1</sup> Solver Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Function R<sup>1</sup> Package contains several Built-in R<sup>1</sup> To R<sup>1</sup> Solvers.

## Class Components

 * [***BracketingControlParams***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/r1tor1solver/BracketingControlParams.java)
 <i>BracketingControlParams</i> implements the control parameters for bracketing solutions.
 BracketingControlParams provides the following parameters.
 	* The starting variate from which the search for bracketing begins
 	* The initial width for the brackets
 	* The factor by which the width expands with each iterative search
 	* The number of such iterations.

 * [***BracketingOutput***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/r1tor1solver/BracketingOutput.java)
 <i>BracketingOutput</i> carries the results of the bracketing initialization. In addition to the fields of
 ExecutionInitializationOutput, BracketingOutput holds the left/right bracket variates and the corresponding
 values for the objective function.

 * [***BracketingOutput***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/r1tor1solver/BracketingOutput.java)
 <i>BracketingOutput</i> carries the results of the bracketing initialization. In addition to the fields of
 ExecutionInitializationOutput, BracketingOutput holds the left/right bracket variates and the corresponding
 values for the objective function.

 * [***ConvergenceControlParams***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/r1tor1solver/ConvergenceControlParams.java)
 <i>ConvergenceControlParams</i> holds the fields needed for the controlling the execution of Newton's
 method. ConvergenceControlParams does that using the following parameters.

 * [***ConvergenceOutput***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/r1tor1solver/ConvergenceOutput.java)
 <i>ConvergenceOutput</i> extends the ExecutionInitializationOutput by retaining the starting variate that
 results from the convergence zone search. ConvergenceOutput does not add any new field to
 ExecutionInitializationOutput.

 * [***ExecutionControl***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/r1tor1solver/ExecutionControl.java)
 <i>ExecutionControl</i> implements the core fixed point search execution control and customization
 functionality. ExecutionControl is used for a) calculating the absolute tolerance, and b) determining
 whether the OF has reached the goal. ExecutionControl determines the execution termination using its
 ExecutionControlParams instance.

 * [***ExecutionControlParams***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/r1tor1solver/ExecutionControlParams.java)
 <i>ExecutionControlParams</i> holds the parameters needed for controlling the execution of the fixed point
 finder. ExecutionControlParams fields control the fixed point search in one of the following ways:
 	* Number of iterations after which the search is deemed to have failed
 	* Relative Objective Function Tolerance Factor which, when reached by the objective function, will
 		indicate that the fixed point has been reached Variate Convergence Factor, factor applied to the
 		initial variate to determine the absolute convergence.
 	* Absolute Tolerance fall-back, which is used to determine that the fixed point has been reached when the
 		relative tolerance factor becomes zero
 	* Absolute Variate Convergence Fall-back, fall-back used to determine if the variate has converged.

 * [***ExecutionInitializationOutput***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/r1tor1solver/ExecutionInitializationOutput.java)
 <i>ExecutionInitializationOutput</i> holds the output of the root initializer calculation. The following are
 the fields held by ExecutionInitializationOutput.
 	* Whether the initialization completed successfully
 	* The number of iterations, the number of objective function calculations, and the time taken for the
 		initialization
 	* The starting variate from the initialization

 * [***ExecutionInitializer***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/r1tor1solver/ExecutionInitializer.java)
 <i>ExecutionInitializer</i> implements the initialization execution and customization functionality.
 ExecutionInitializer performs two types of variate initialization:
 	* Bracketing initialization: This brackets the fixed point using the bracketing algorithm described in
 		https://lakshmidrip.github.io/DROP-Numerical-Core/. If successful, a pair of variate/OF coordinate
 		nodes that bracket the fixed point are generated. These brackets are eventually used by routines that
 		iteratively determine the fixed point. Bracketing initialization is controlled by the parameters in
 		BracketingControlParams.
 	* Convergence Zone initialization: This generates a variate that lies within the convergence zone for the
 		iterative determination of the fixed point using the Newton's method. Convergence Zone Determination
 		is controlled by the parameters in ConvergenceControlParams.
 ExecutionInitializer behavior can be customized/optimized through several of the initialization heuristics
 techniques implemented in the InitializationHeuristics class.

 * [***FixedPointFinderBracketing***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/r1tor1solver/FixedPointFinderBracketing.java)
 <i>FixedPointFinderBracketing</i> customizes the FixedPointFinder for bracketing based fixed point finder
 functionality.

 FixedPointFinderBracketing applies the following customization:
 	* Initializes the fixed point finder by computing the starting brackets
 	* Iterating the next search variate using one of the specified variate iterator primitives.

 By default, FixedPointFinderBracketing does not do compound iterations of the variate using any schemes -
 	that is done by classes that extend it.

 * [***FixedPointFinderBrent***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/r1tor1solver/FixedPointFinderBrent.java)
 <i>FixedPointFinderBrent</i> customizes FixedPointFinderBracketing by applying the Brent's scheme of
 compound variate selector.

 Brent's scheme, as implemented here, is described in http://www.credit-trader.org. This implementation
 	retains absolute shifts that have happened to the variate for the past 2 iterations as the discriminant
 	that determines the next variate to be generated.

 FixedPointFinderBrent uses the following parameters specified in VariateIterationSelectorParams:
 	* The Variate Primitive that is regarded as the "fast" method
 	* The Variate Primitive that is regarded as the "robust" method
 	* The relative variate shift that determines when the "robust" method is to be invoked over the "fast"
 	* The lower bound on the variate shift between iterations that serves as the fall-back to the "robust"

 * [***FixedPointFinderNewton***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/r1tor1solver/FixedPointFinderNewton.java)
 <i>FixedPointFinderNewton</i> customizes the FixedPointFinder for Open (Newton's) fixed point finder
 	functionality.

 FixedPointFinderNewton applies the following customization:
 	* Initializes the fixed point finder by computing a starting variate in the convergence zone
 	* Iterating the next search variate using the Newton's method.

 * [***FixedPointFinderOutput***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/r1tor1solver/FixedPointFinderOutput.java)
 <i>FixedPointFinderOutput</i> holds the result of the fixed point search.

 * FixedPointFinderOutput contains the following fields:
 	* Whether the search completed successfully
 	* The number of iterations, the number of objective function base/derivative calculations, and the time
 		taken for the search
 	* The output from initialization

 * [***FixedPointFinderOutput***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/r1tor1solver/FixedPointFinderOutput.java)
 <i>FixedPointFinderZheng</i> implements the fixed point locator using Zheng's improvement to Brent's
 	method.

 FixedPointFinderZheng overrides the iterateCompoundVariate method to achieve the desired simplification in
 	the iterative variate selection.

 * [***IteratedBracket***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/r1tor1solver/IteratedBracket.java)
 <i>IteratedBracket</i> holds the left/right bracket variates and the corresponding values for the objective
 function during each iteration.

 * [***IteratedVariate***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/r1tor1solver/IteratedVariate.java)
 <i>IteratedVariate</i> holds the variate and the corresponding value for the objective function during each
 iteration.

 * [***VariateIterationSelectorParams***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/r1tor1solver/VariateIterationSelectorParams.java)
 <i>VariateIterationSelectorParams</i> implements the control parameters for the compound variate selector
 scheme used in Brent's method.

 Brent's method uses the following fields in VariateIterationSelectorParams to generate the next variate:
 	* The Variate Primitive that is regarded as the "fast" method
 	* The Variate Primitive that is regarded as the "robust" method
 	* The relative variate shift that determines when the "robust" method is to be invoked over the "fast"
 	* The lower bound on the variate shift between iterations that serves as the fall-back to the "robust"

 * [***VariateIteratorPrimitive***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/r1tor1solver/VariateIteratorPrimitive.java)
<i>VariateIteratorPrimitive</i> implements the various Primitive Variate Iterator routines.

 VariateIteratorPrimitive implements the following iteration primitives:
 	* Bisection
 	* False Position
 	* Quadratic
 	* Inverse Quadratic
 	* Ridder

 It may be readily enhanced to accommodate additional primitives.


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
