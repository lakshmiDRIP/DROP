# DROP Portfolio Construction Constraint Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Portfolio Construction Constraint Package implements the Portfolio Construction Constraint Term Suite.


## Class Components

 * [***LimitBudgetTerm***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/constraint/LimitBudgetTerm.java)
 <i>LimitBudgetTerm</i> holds the Details of a Limit Budget Constraint Term.

 * [***LimitBudgetTermNet***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/constraint/LimitBudgetTermNet.java)
 <i>LimitBudgetTermNet</i> holds the Details of a Limit Net Budget Constraint Term.

 * [***LimitBudgetTermTransactionCharge***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/constraint/LimitBudgetTermTransactionCharge.java)
 <i>LimitBudgetTermTransactionCharge</i> holds the Details of a After Transaction Charge Limit Budget
 Constraint Term.

 * [***LimitChargeTermIssuer***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/constraint/LimitChargeTermIssuer.java)
 <i>LimitChargeTermIssuer</i> constrains the Limit Issuer Transaction Charge Term.

 * [***LimitExposureTerm***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/constraint/LimitExposureTerm.java)
 <i>LimitExposureTerm</i> holds the Details of a Limit Exposure Constraint Term - Limits can be
 Absolute/Net etc.

 * [***LimitExposureTermAbsolute***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/constraint/LimitExposureTermAbsolute.java)
 <i>LimitExposureTermAbsolute</i> holds the Details of a Limit Absolute Exposure Constraint Term.

 * [***LimitExposureTermIssuer***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/constraint/LimitExposureTermIssuer.java)
 <i>LimitExposureTermIssuer</i> abstracts the Limit Issuer Exposure Constraint Term.

 * [***LimitExposureTermIssuerLong***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/constraint/LimitExposureTermIssuerLong.java)
 <i>LimitExposureTermIssuerLong</i> holds the Details of a Limit Issuer Long Exposure Constraint Term.

 * [***LimitExposureTermIssuerNet***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/constraint/LimitExposureTermIssuerNet.java)
 <i>LimitExposureTermIssuerNet</i> holds the Details of a Limit Issuer Net Exposure Constraint Term.

 * [***LimitExposureTermIssuerShort***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/constraint/LimitExposureTermIssuerShort.java)
 <i>LimitExposureTermIssuerShort</i> holds the Details of a Limit Issuer Short Exposure Constraint Term.

 * [***LimitExposureTermNet***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/constraint/LimitExposureTermNet.java)
 <i>LimitExposureTermNet</i> holds the Details of a Limit Net Exposure Constraint Term.

 * [***LimitHoldingsTerm***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/constraint/LimitHoldingsTerm.java)
 <i>LimitHoldingsTerm</i> holds the Details of a Limit Holdings Constraint Term - Limits can be
 Absolute/Net etc.

 * [***LimitHoldingsTermIssuer***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/constraint/LimitHoldingsTermIssuer.java)
 <i>LimitHoldingsTermIssuer</i> abstracts the Limit Issuer Holdings Constraint Term.

 * [***LimitHoldingsTermIssuerLong***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/constraint/LimitHoldingsTermIssuerLong.java)
 <i>LimitHoldingsTermIssuerLong</i> holds the Details of Limit Issuer Long Holdings Constraint Term.

 * [***LimitHoldingsTermIssuerLongShort***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/constraint/LimitHoldingsTermIssuerLongShort.java)
 <i>LimitHoldingsTermIssuerLongShort</i> holds the Details of Limit Issuer Long/Short Holdings Ratio
 Constraint Term.

 * [***LimitHoldingsTermIssuerNet***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/constraint/LimitHoldingsTermIssuerNet.java)
 <i>LimitHoldingsTermIssuerNet</i> holds the Details of Limit Issuer Net Holdings Constraint Term.

 * [***LimitHoldingsTermIssuerShort***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/constraint/LimitHoldingsTermIssuerShort.java)
 <i>LimitHoldingsTermIssuerShort</i> holds the Details of Limit Issuer Short Holdings Constraint Term.

 * [***LimitHoldingsTermIssuerWeightedAverage***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/constraint/LimitHoldingsTermIssuerWeightedAverage.java)
 <i>LimitHoldingsTermIssuerWeightedAverage</i> holds the Details of Weighted Average Issuer Limit Holdings
 Constraint Term.

 * [***LimitHoldingsTermMinimumPeriod***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/constraint/LimitHoldingsTermMinimumPeriod.java)
 <i>LimitHoldingsTermMinimumPeriod</i> holds the Details of Limit Minimum Holdings Period Constraint Term.

 * [***LimitHoldingsTermModelDeviation***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/constraint/LimitHoldingsTermModelDeviation.java)
 <i>LimitHoldingsTermModelDeviation</i> holds the Details of a Limit Holdings Benchmark Weights Absolute
 Deviation Constraint Term.

 * [***LimitNamesTermIssuer***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/constraint/LimitNamesTermIssuer.java)
 <i>LimitNamesTermIssuer</i> holds the Details of a Limit Count of Issuer Names Constraint Term.

 * [***LimitNamesTermIssuerLong***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/constraint/LimitNamesTermIssuerLong.java)
 <i>LimitNamesTermIssuerLong</i> holds the Details of Count of the Total Long Active Assets in the Holdings.

 * [***LimitNamesTermIssuerShort***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/constraint/LimitNamesTermIssuerShort.java)
 <i>LimitNamesTermIssuerShort</i> holds the Details of Count of the Total Short Active Assets in the
 Holdings.

 * [***LimitNamesTermIssuerTotal***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/constraint/LimitNamesTermIssuerTotal.java)
 <i>LimitNamesTermIssuerTotal</i> holds the Details of Count of the Total Active Assets in the Holdings.

 * [***LimitRiskTerm***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/constraint/LimitRiskTerm.java)
 <i>LimitRiskTerm</i> holds the Details of a Limit Risk Constraint Term.

 * [***LimitRiskTermMarginal***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/constraint/LimitRiskTermMarginal.java)
 <i>LimitRiskTermMarginal</i> holds the Details of a Relative Marginal Contribution Based Limit Risk
 Constraint Term.

 * [***LimitRiskTermVariance***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/constraint/LimitRiskTermVariance.java)
 <i>LimitRiskTermVariance</i> holds the Details of a Variance Based Limit Risk Constraint Term.

 * [***LimitTaxTerm***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/constraint/LimitTaxTerm.java)
 <i>LimitTaxTerm</i> holds the Details of a Limit Tax Constraint Term.

 * [***LimitTaxTermGrossGains***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/constraint/LimitTaxTermGrossGains.java)
 <i>LimitTaxTermGrossGains</i> holds the Details of a Limit Gross Tax Gains Constraint Term.

 * [***LimitTaxTermGrossLoss***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/constraint/LimitTaxTermGrossLoss.java)
 <i>LimitTaxTermGrossLoss</i> holds the Details of a Limit Gross Tax Loss Constraint Term.

 * [***LimitTaxTermLiability***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/constraint/LimitTaxTermLiability.java)
 <i>LimitTaxTermLiability</i> holds the Details of a Limit Tax Liability Constraint Term.

 * [***LimitTaxTermLongGains***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/constraint/LimitTaxTermLongGains.java)
 <i>LimitTaxTermLongGains</i> holds the Details of a Limit Long Term Tax Gains Constraint Term.

 * [***LimitTaxTermNetLoss***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/constraint/LimitTaxTermNetLoss.java)
 <i>LimitTaxTermNetLoss</i> holds the Details of a Limit Net Tax Loss Constraint Term.

 * [***LimitThresholdTermIssuer***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/constraint/LimitThresholdTermIssuer.java)
 <i>LimitThresholdTermIssuer</i> abstracts the Issuer Target Portfolio Holdings as long as they are not Zero.

 * [***LimitThresholdTermIssuerLong***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/constraint/LimitThresholdTermIssuerLong.java)
 <i>LimitThresholdTermIssuerLong</i> implements the Issuer Long Portfolio Holdings as long as they are not
 Zero.

 * [***LimitThresholdTermIssuerNet***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/constraint/LimitThresholdTermIssuerNet.java)
 <i>LimitThresholdTermIssuerNet</i> implements the Issuer Net Portfolio Holdings as long as they are not
 Zero.

 * [***LimitThresholdTermIssuerShort***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/constraint/LimitThresholdTermIssuerShort.java)
 <i>LimitThresholdTermIssuerShort</i> implements the Issuer Short Portfolio Holdings as long as they are not
 Zero.

 * [***LimitTradesTermIssuer***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/constraint/LimitTradesTermIssuer.java)
 <i>LimitTradesTermIssuer</i> abstracts the Issuer Targets the Count of Portfolio Trades.

 * [***LimitTradesTermIssuerBuy***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/constraint/LimitTradesTermIssuerBuy.java)
 <i>LimitTradesTermIssuerBuy</i> abstracts the Issuer Targets the Count of Total Buy Portfolio Trades.

 * [***LimitTradesTermIssuerSell***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/constraint/LimitTradesTermIssuerSell.java)
 <i>LimitTradesTermIssuerBuy</i> abstracts the Issuer Targets the Count of Total Sell Portfolio Trades.

 * [***LimitTradesTermIssuerTotal***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/constraint/LimitTradesTermIssuerTotal.java)
 <i>LimitTradesTermIssuerTotal</i> abstracts the Issuer Targets the Count of Total Portfolio Trades.

 * [***LimitTurnoverTermIssuer***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/constraint/LimitTurnoverTermIssuer.java)
 <i>LimitTurnoverTermIssuer</i> abstracts the Issuer Targets the Turnover of Portfolio Trades.

 * [***LimitTurnoverTermIssuerBuy***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/constraint/LimitTurnoverTermIssuerBuy.java)
 <i>LimitTuroverTermIssuerBuy</i> abstracts the Issuer Targets the Turnover of Total Buy Portfolio Trades.

 * [***LimitTurnoverTermIssuerNet***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/constraint/LimitTurnoverTermIssuerNet.java)
 <i>LimitTurnoverTermIssuerNet</i> abstracts the Issuer Targets the Turnover of Total Net Portfolio Trades.

 * [***LimitTurnoverTermIssuerSell***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/constraint/LimitTurnoverTermIssuerSell.java)
 <i>LimitTurnoverTermIssuerSell</i> abstracts the Issuer Targets the Turnover of Total Sell Portfolio Trades.

 * [***LimitTurnoverTermIssuerShort***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/constraint/LimitTurnoverTermIssuerShort.java)
 <i>LimitTurnoverTermIssuerShort</i> abstracts the Issuer Targets the Turnover of Total Short Portfolio
 Trades.


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
