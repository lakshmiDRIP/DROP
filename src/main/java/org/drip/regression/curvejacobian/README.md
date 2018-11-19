# DROP Regression Curve Jacobian Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Regression Curve Jacobian implements the Curve Jacobian Reconciliation Regression Engine.


## Class Components

 * [***CashJacobianRegressorSet***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/regression/curvejacobian/CashJacobianRegressorSet.java)
 <i>CashJacobianRegressorSet</i> implements the regression analysis set for the Cash product related
 Sensitivity Jacobians. Specifically, it computes the PVDF micro-Jack.

 * [***CurveJacobianRegressionEngine***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/regression/curvejacobian/CurveJacobianRegressionEngine.java)
 <i>CurveJacobianRegressionEngine</i> implements the RegressionEngine for the curve Jacobian regression. It
 adds the CashJacobianRegressorSet, the EDFJacobianRegressorSet, the IRSJacobianRegressorSet, and the
 DiscountCurveJacobianRegressorSet, and launches the regression engine.

 * [***DiscountCurveJacobianRegressorSet***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/regression/curvejacobian/DiscountCurveJacobianRegressorSet.java)
 <i>DiscountCurveJacobianRegressorSet</i> implements the regression analysis for the full discount curve
 (built from cash/future/swap) Sensitivity Jacobians. Specifically, it computes the PVDF micro-Jack.

 * [***EDFJacobianRegressorSet***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/regression/curvejacobian/EDFJacobianRegressorSet.java)
 <i>EDFJacobianRegressorSet</i> implements the regression analysis set for the EDF product related
 Sensitivity Jacobians. Specifically, it computes the PVDF micro-Jack.

 * [***IRSJacobianRegressorSet***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/regression/curvejacobian/IRSJacobianRegressorSet.java)
 <i>IRSJacobianRegressorSet</i> implements the regression analysis set for the IRS product related
 Sensitivity Jacobians. Specifically, it computes the PVDF micro-Jack.


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
