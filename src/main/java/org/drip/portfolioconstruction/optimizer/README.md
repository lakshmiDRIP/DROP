# DROP Portfolio Construction Optimizer Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Portfolio Construction Optimizer Package implements the Core Portfolio Construction Optimizer Suite.


## Class Components

 * [***ConstraintHierarchy***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/optimizer/ConstraintHierarchy.java)
 <i>ConstraintHierarchy</i> holds the Details of a given set of Constraint Terms.

 * [***ConstraintRealization***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/optimizer/ConstraintRealization.java)
 <i>ConstraintRealization</i> holds the Realized Set of Values coming out of an Optimizer Run, along with the
 Bounds.

 * [***ConstraintTerm***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/optimizer/ConstraintTerm.java)
 <i>ConstraintTerm</i> holds the Details of a given Constraint Term.

 * [***FormulationTerm***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/optimizer/FormulationTerm.java)
 <i>FormulationTerm</i> holds the Core Objective/Constraint Formulation Terms. It includes the R<sup>d</sup>
 To R<sup>1</sup> Objective and/or Constraints as well as their Category.

 * [***ObjectiveFunction***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/optimizer/ObjectiveFunction.java)
 <i>ObjectiveFunction</i> holds the Terms composing the Objective Function and their Weights.

 * [***ObjectiveTerm***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/optimizer/ObjectiveTerm.java)
 <i>ObjectiveTerm</i> holds the Details of a given Objective Term.

 * [***ObjectiveTermUnit***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/optimizer/ObjectiveTermUnit.java)
 <i>ObjectiveTermUnit</i> holds the Details of a Single Objective Term that forms the Strategy.

 * [***Rebalancer***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/optimizer/Rebalancer.java)
 <i>Rebalancer</i> holds the Details of a given Rebalancing Run.

 * [***RebalancerAnalytics***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/optimizer/RebalancerAnalytics.java)
 <i>RebalancerAnalytics</i> holds the Analytics from a given Rebalancing Run.

 * [***Scope***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/optimizer/Scope.java)
 <i>Scope</i> holds the Applicability "Zone" for a given Constraint Term.

 * [***SoftConstraint***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/optimizer/SoftConstraint.java)
 <i>SoftConstraint</i> holds the Details of a Soft Constraint.

 * [***Strategy***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/optimizer/Strategy.java)
 <i>Strategy</i> holds the Details of a given Strategy.

 * [***Unit***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/optimizer/Unit.java)
 <i>Unit</i> specifies the Denomination of the Limits for a given Constraint Term.


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
