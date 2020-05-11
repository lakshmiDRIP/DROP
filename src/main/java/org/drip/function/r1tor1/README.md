# DROP Function R<sup>1</sup> Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Function R<sup>1</sup> Package contains several Built-in R<sup>1</sup> To R<sup>1</sup> Functions.


## Class Components

 * [***AlmgrenEnhancedEulerUpdate***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/r1tor1/AlmgrenEnhancedEulerUpdate.java)
 <i>AlmgrenEnhancedEulerUpdate</i> is a R<sup>1</sup> To R<sup>1</sup> Function that is used in Almgren
 (2009, 2012) to illustrate the Construction of the Enhanced Euler Update Scheme.

 * [***AndersenPiterbargMeanReverter***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/r1tor1/AndersenPiterbargMeanReverter.java)
 <i>AndersenPiterbargMeanReverter</i> implements the mean-reverting Univariate Function.

 * [***Bennett***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/r1tor1/Bennett.java)
 <i>Bennett</i> is implementation of the Bennett's Function used in the Estimation of the Bennett's
 Concentration Inequality.

 * [***BernsteinPolynomial***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/r1tor1/BernsteinPolynomial.java)
 <i>BernsteinPolynomial</i> provides the evaluation of the BernsteinPolynomial and its derivatives for aspecified variate. 

 * [***ExponentialDecay***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/r1tor1/ExponentialDecay.java)
 <i>ExponentialDecay</i> implements the scaled exponential decay Univariate Function.

 * [***ExponentialTension***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/r1tor1/ExponentialTension.java)
 <i>ExponentialTension</i> provides the evaluation of the Exponential Tension Function and its derivatives
 for a specified variate.

 * [***FlatUnivariate***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/r1tor1/FlatUnivariate.java)
 <i>FlatUnivariate</i> implements the level constant Univariate Function.

 * [***FunctionClassSupremum***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/r1tor1/FunctionClassSupremum.java)
 <i>FunctionClassSupremum</i> implements the Univariate Function that corresponds to the Supremum among the
 specified Class of Functions.

 * [***HyperbolicTension***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/r1tor1/HyperbolicTension.java)
 <i>HyperbolicTension</i> provides the evaluation of the Hyperbolic Tension Function and its derivatives for
 a specified variate.

 * [***ISDABucketCurvatureTenorScaler***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/r1tor1/ISDABucketCurvatureTenorScaler.java)
 <i>ISDABucketCurvatureTenorScaler</i> generates the ISDA SIMM Tenor Scaling Factor for a given Bucket
 Curvature.

 * [***LinearRationalShapeControl***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/r1tor1/LinearRationalShapeControl.java)
 <i>LinearRationalShapeControl</i> implements the deterministic rational shape control functionality on top
 of the estimator basis splines inside - [0,...,1) - Globally [x_0,...,x_1):

  			y = 1 / [1 + lambda * x]

		where is the normalized ordinate mapped as

 			x === (x - x_i-1) / (x_i - x_i-1)

 * [***LinearRationalTensionExponential***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/r1tor1/LinearRationalTensionExponential.java)
 <i>LinearRationalTensionExponential</i> provides the evaluation of the Convolution of the Linear Rational
 and the Tension Exponential Functions and its derivatives for a specified variate.

 * [***MonicPolynomial***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/r1tor1/MonicPolynomial.java)
 <i>MonicPolynomial</i> implements the Multi-root R<sup>1</sup> to R<sup>1</sup> Monic Polynomial.

 * [***NaturalLogSeriesElement***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/r1tor1/NaturalLogSeriesElement.java)
 <i>NaturalLogSeriesElement</i> implements an element in the natural log series expansion.

 * [***OffsetIdempotent***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/r1tor1/OffsetIdempotent.java)
 <i>OffsetIdempotent</i> provides the Implementation of the Offset Idempotent Operator - f(x) = x - C.

 * [***Polynomial***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/r1tor1/Polynomial.java)
 <i>Polynomial</i> provides the evaluation of the n<sup>th</sup> order Polynomial and its derivatives for a
 specified variate.

 * [***QuadraticRationalShapeControl***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/r1tor1/QuadraticRationalShapeControl.java)
 <i>QuadraticRationalShapeControl</i> implements the deterministic rational shape control functionality on
 top of the estimator basis splines inside - [0,...,1) - Globally [x_0,...,x_1):

 			y = 1 / [1 + lambda * x * (1-x)]

		where is the normalized ordinate mapped as

 			x ==== (x - x_i-1) / (x_i - x_i-1)

 * [***R1UnivariateCIRPDF***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/r1tor1/R1UnivariateCIRPDF.java)
 <i>R1UnivariateCIRPDF</i> exposes the R<sup>1</sup> Univariate Cox-Ingersoll-Ross Probability Density Function.

 * [***SABRLIBORCapVolatility***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/r1tor1/SABRLIBORCapVolatility.java)
 <i>SABRLIBORCapVolatility</i> implements the Deterministic, Non-local Cap Volatility Scheme.

 * [***Sinc***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/r1tor1/Sinc.java)
 <i>Sinc</i> computes the Pi Z-Scaled Reciprocal of the Sine Function of Pi times the Argument.

 * [***UnivariateConvolution***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/r1tor1/UnivariateConvolution.java)
 <i>UnivariateConvolution</i> provides the evaluation of the Convolution au1 * au2 and its derivatives for a
 specified variate.

 * [***UnivariateReciprocal***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/r1tor1/UnivariateReciprocal.java)
 <i>UnivariateReciprocal</i> provides the evaluation 1/f(x) instead of f(x) for a given f.

 * [***UnivariateReflection***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/r1tor1/UnivariateReflection.java)
 <i>UnivariateReflection</i> provides the evaluation f(1-x) instead of f(x) for a given f.


## References

 * Almgren, R. F., and N. Chriss (2000): Optimal Execution of Portfolio Transactions <i>Journal of Risk</i> <b>3 (2)</b> 5-39

 * Almgren, R. F. (2009): Optimal Trading in a Dynamic Market https://www.math.nyu.edu/financial_mathematics/content/02_financial/2009-2.pdf

 * Almgren, R. F. (2012): Optimal Trading with Stochastic Liquidity and Volatility <i>SIAM Journal of Financial Mathematics</i> <b>3 (1)</b> 163-181

 * Andersen and Piterbarg (2010): <i>Interest Rate Modeling (3 Volumes)</i> <b>Atlantic Financial Press</b>

 * Andersen, L. B. G., M. Pykhtin, and A. Sokol (2017): Credit Exposure in the Presence of Initial Margin
 https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2806156 <b>eSSRN</b>

 * Albanese, C., S. Caenazzo, and O. Frankel (2017): Regression Sensitivities for Initial Margin Calculations
 https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2763488 <b>eSSRN</b>

 * Anfuso, F., D. Aziz, P. Giltinan, and K. Loukopoulus (2017): A Sound Modeling and Back-testing Framework
 for Forecasting Initial Margin Requirements https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2716279
 <b>eSSRN</b>

 * Bogoliubov, N. N., and D. P. Sankevich (1994): N. N. Bogoliubov and Statistical Mechanics <i>Russian Mathematical Surveys</i> <b>49 (5)</b> 19-49

 * Caspers, P., P. Giltinan, R. Lichters, and N. Nowaczyk (2017): Forecasting Initial Margin Requirements; A
 Model Evaluation https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2911167 <b>eSSRN</b>

 * Holubec, V., K. Kroy, and S. Steffenoni (2019): Physically Consistent Numerical Solver for Time-dependent Fokker-Planck Equations <i>Physical Review E</i> <b>99 (4)</b> 032117

 * International Swaps and Derivatives Association (2017): SIMM v2.0 Methodology
 https://www.isda.org/a/oFiDE/isda-simm-v2.pdf

 * Kadanoff, L. P. (2000): <i>Statistical Physics: Statics, Dynamics, and Re-normalization</i> <b>World Scientific</b>

 * Ottinger, H. C. (1996): <i>Stochastic Processes in Polymeric Fluids</i> <b>Springer-Verlag</b> Berlin-Heidelberg

 * Rebonato, R., K. McKay, and R. White (2009): <i>The SABR/LIBOR Market Model: Pricing, Calibration, and
 Hedging for Complex Interest-Rate Derivatives</i> <b>John Wiley and Sons</b>

 * Wikipedia (2019): Fokker-Planck Equation https://en.wikipedia.org/wiki/Fokker%E2%80%93Planck_equation


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
