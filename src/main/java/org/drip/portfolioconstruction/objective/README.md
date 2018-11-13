# DROP Portfolio Construction Objective Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Portfolio Construction Objective Package implements the Portfolio Construction Objective Term Suite.


## Class Components

 * [***CustomNetTaxGainsTerm***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/objective/CustomNetTaxGainsTerm.java)
 <i>CustomNetTaxGainsTerm</i> holds the Details of the Portfolio Custom Net Tax Gain Objective Term.

 * [***CustomTransactionChargeTerm***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/objective/CustomTransactionChargeTerm.java)
 <i>CustomTransactionChargeTerm</i> implements the Objective Term that models the Custom Transaction Charge
 associated with a Portfolio Transaction.

 * [***ExpectedReturnsTerm***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/objective/ExpectedReturnsTerm.java)
 <i>ExpectedReturnsTerm</i> holds the Details of the Portfolio Expected Returns Based Objective Terms.
 Expected Returns can be Absolute or in relation to a Benchmark.

 * [***FixedChargeBuyTerm***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/objective/FixedChargeBuyTerm.java)
 <i>FixedChargeBuyTerm</i> implements the Objective Term that optimizes the Charges incurred by the Buy
 Trades in the Target Portfolio under a Fixed Charge from the Starting Allocation.

 * [***FixedChargeSellTerm***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/objective/FixedChargeSellTerm.java)
 <i>FixedChargeSellTerm</i> implements the Objective Term that optimizes the Charge incurred by the Sell
 Trades in the Target Portfolio under a Fixed Charge from the Starting Allocation.

 * [***FixedChargeTerm***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/objective/FixedChargeTerm.java)
 <i>FixedChargeTerm</i> implements the Objective Term that optimizes the Charge incurred by the Buy/Sell
 Trades in the Target Portfolio under a Fixed Charge from the Starting Allocation.

 * [***GoldmanSachsShortfallTerm***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/objective/GoldmanSachsShortfallTerm.java)
 <i>GoldmanSachsShortfallTerm</i> implements the Objective Term that optimizes the Charge incurred by the
 Buy/Sell Trades in the Target Portfolio using the Goldman Sachs Shortfall Model from the Starting
 Allocation.

 * [***LinearChargeBuyTerm***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/objective/LinearChargeBuyTerm.java)
 <i>LinearChargeBuyTerm</i> implements the Objective Term that optimizes the Charge incurred by the Buy
 Trades in the Target Portfolio under a Linear Transaction Charge from the Starting Allocation.

 * [***LinearChargeSellTerm***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/objective/LinearChargeSellTerm.java)
 <i>LinearChargeSellTerm</i> implements the Objective Term that optimizes the Charge incurred by the Sell
 Trades in the Target Portfolio under a Linear Transaction Charge from the Starting Allocation.

 * [***LinearChargeTerm***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/objective/LinearChargeTerm.java)
 <i>LinearChargeTerm</i> implements the Objective Term that optimizes the Charge of the Buy/Sell Trades in
 the Target Portfolio under a Linear Transaction Charge from the Starting Allocation.

 * [***LongTiltTerm***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/objective/LongTiltTerm.java)
 <i>LongTiltTerm</i> holds the Details of Long Tilt Unit Objective Term.

 * [***MarketImpactChargeTerm***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/objective/MarketImpactChargeTerm.java)
 <i>MarketImpactChargeTerm</i> implements the Objective Term that optimizes the Charge incurred by the
 Buy/Sell Trades in the Target Portfolio under a specified Market Impact Charge from the Starting Allocation.

 * [***NetTaxGainsTerm***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/objective/NetTaxGainsTerm.java)
 <i>NetTaxGainsTerm</i> holds the Details of the Portfolio Net Tax Gain Objective Term.

 * [***NetTiltTerm***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/objective/NetTiltTerm.java)
 <i>NetTiltTerm</i> holds the Details of Net Tilt Unit Objective Term.

 * [***ReturnsTerm***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/objective/ReturnsTerm.java)
 <i>ReturnsTerm</i> holds the Details of the Portfolio Returns Based Objective Terms. Returns can be Absolute
 or in relation to a Benchmark.

 * [***RiskTerm***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/objective/RiskTerm.java)
 <i>RiskTerm</i> holds the Details of the Portfolio Risk Objective Term. Risk can be Absolute or in relation
 to a Benchmark, and can be measured as Variance or Standard Deviation.

 * [***RobustErrorTerm***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/objective/RobustErrorTerm.java)
 <i>RobustErrorTerm</i> optimizes the Error in the Target Expected Absolute Return of the Portfolio on the
 Absence of Benchmark, and the Error in the Benchmark-Adjusted Returns Otherwise.

 * [***ShortSellChargeTerm***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/objective/ShortSellChargeTerm.java)
 <i>ShortSellChargeTerm</i> implements the Objective Term that optimizes the Charge incurred by Short Sell
 Trades in the Target Portfolio from the Starting Allocation.

 * [***ShortTiltTerm***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/objective/ShortTiltTerm.java)
 <i>ShortTiltTerm</i> holds the Details of Short Tilt Unit Objective Term.

 * [***StandardDeviationTerm***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/objective/StandardDeviationTerm.java)
 <i>StandardDeviationTerm</i> holds the Details of the Portfolio Risk (Standard Deviation) Objective Term.
 Standard Deviation can be Absolute or in relation to a Benchmark.

 * [***TaxationScheme***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/objective/TaxationScheme.java)
 <i>TaxationScheme</i> exposes Taxation related Functionality.

 * [***TaxLiabilityTerm***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/objective/TaxLiabilityTerm.java)
 <i>TaxLiabilityTerm</i> holds the Details of the Portfolio Net Tax Liability Objective Term.

 * [***TaxTerm***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/objective/TaxTerm.java)
 <i>TaxTerm</i> holds the Details of Abstract Tax Unit Objective Term.

 * [***TiltTerm***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/objective/TiltTerm.java)
 <i>TiltTerm</i> holds the Details of Abstract Tilt Unit Objective Term.

 * [***TransactionChargeTerm***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/objective/TransactionChargeTerm.java)
 <i>TransactionChargeTerm</i> implements the Objective Term that models the Charge associated with a
 Portfolio Transaction.

 * [***VarianceTerm***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/objective/VarianceTerm.java)
 <i>VarianceTerm</i> holds the Details of the Portfolio Risk (Variance) Objective Term. Variance can be
 Absolute or in relation to a Benchmark.


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
