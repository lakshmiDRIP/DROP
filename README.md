# DROP

**v3.70**  *13 August 2018*

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP implements the model libraries and provides systems for fixed income valuation and adjustments, asset allocation and transaction cost analytics, and supporting libraries in numerical optimization and statistical learning.

DROP is composed of four main modules.

 * [***Asset Allocation Module***](https://lakshmidrip.github.io/DROP/AssetAllocationModule.html)   =>   [Asset Allocation Library](https://lakshmidrip.github.io/DROP/AssetAllocationLibrary.html)    |   [Transaction Cost Analytics Library](https://lakshmidrip.github.io/DROP/TransactionCostLibrary.html)
 * [***Fixed Income Analytics Module***](https://lakshmidrip.github.io/DROP/FixedIncomeModule.html)   =>   [Fixed Income Analytics Library](https://lakshmidrip.github.io/DROP/FixedIncomeLibrary.html)    |   [Asset Backed Analytics Library](https://lakshmidrip.github.io/DROP/AssetBackedLibrary.html)    |   [XVA Analytics Library](https://lakshmidrip.github.io/DROP/XVALibrary.html)    |   [Exposure Analytics Library](https://lakshmidrip.github.io/DROP/ExposureLibrary.html)
 * [***Numerical Optimization Module***](https://lakshmidrip.github.io/DROP/NumericalOptimizerModule.html)
 * [***Statistical Learning Module***](https://lakshmidrip.github.io/DROP/StatisticalLearningModule.html)

## Pointers

[![Travis](https://travis-ci.org/lakshmiDRIP/DROP.svg)](https://travis-ci.org/lakshmiDRIP/DROP)    [![CircleCI](https://img.shields.io/circleci/project/github/lakshmiDRIP/DROP.svg)](https://circleci.com/gh/lakshmiDRIP/workflows/DROP)    [![CircleCI](https://circleci.com/gh/lakshmiDRIP/DROP.svg?style=svg)](https://circleci.com/gh/lakshmiDRIP/DROP)    [![Build status](https://ci.appveyor.com/api/projects/status/m5p8sfeth4cewr4v?svg=true)](https://ci.appveyor.com/project/lakshmiDRIP/drop)    
[![Git](https://img.shields.io/github/release/lakshmiDRIP/DROP.svg)](https://github.com/lakshmiDRIP/DROP/releases)    
[![Dependency Status](https://www.versioneye.com/user/projects/5a2e15d50fb24f6ad613a09f/badge.svg?style=flat-square)](https://www.versioneye.com/user/projects/5a2e15d50fb24f6ad613a09f)    [![Waffle.io - Columns and their card count](https://badge.waffle.io/lakshmiDRIP/DROP.svg?columns=all)](https://waffle.io/lakshmiDRIP/DROP)    
[![Stack Overflow](http://img.shields.io/:stack%20overflow-drip-brightgreen.svg)](http://stackoverflow.com/questions/tagged/drip)    [![Git](http://dmlc.github.io/img/apache2.svg)](./LICENSE)    
[![Join the chat at https://gitter.im/lakshmiDRIPDROP](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/lakshmiDRIPDROP?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)    

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/7270e4b57c50483699448bf32721ab10)](https://www.codacy.com/app/lakshmiDRIP/DROP?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=DROP/DROP&amp;utm_campaign=Badge_Grade)   [![Codacy Badge](https://api.codacy.com/project/badge/Coverage/7270e4b57c50483699448bf32721ab10)](https://www.codacy.com/app/lakshmiDRIP/DROP?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=DROP/DROP&amp;utm_campaign=Badge_Coverage)   [![codecov.io](http://codecov.io/github/lakshmiDRIP/DROP/coverage.svg?branch=master)](https://codecov.io/gh/lakshmiDRIP/DROP/branch/master)   [![Coverage Status](https://coveralls.io/repos/github/lakshmiDRIP/DROP/badge.svg?branch=master)](https://coveralls.io/github/lakshmiDRIP/DROP?branch=master)   [![Coverity Status](https://scan.coverity.com/projects/14574/badge.svg)](https://scan.coverity.com/projects/lakshmidrip-drop)    
[![Documentation Status](https://readthedocs.org/projects/dripdrop/badge/?version=latest)](http://dripdrop.readthedocs.io/en/latest/?badge=latest)  [![Javadoc](https://readthedocs.org/projects/xgboost/badge/?version=latest)](https://lakshmidrip.github.io/DROP/Javadoc/index.html)  [![Other](https://readthedocs.org/projects/xgboost/badge/?version=latest)](https://github.com/lakshmiDRIP/DROP/tree/master/Docs)

[![Average time to resolve an issue](http://isitmaintained.com/badge/resolution/lakshmiDRIP/DROP.svg)](http://isitmaintained.com/project/lakshmiDRIP/DROP "Average time to resolve an issue")   [![Percentage of issues still open](http://isitmaintained.com/badge/open/lakshmiDRIP/DROP.svg)](http://isitmaintained.com/project/lakshmiDRIP/DROP "Percentage of issues still open")

[![Awesome](https://cdn.rawgit.com/sindresorhus/awesome/d7305f38d29fed78fa85652e3a63e154dd8e8829/media/badge.svg)](https://github.com/sindresorhus/awesome)

## Project Modules

 [**Analytics**](https://github.com/lakshmiDRIP/DROP/issues?q=is%3Aopen+is%3Aissue+label%3Aanalytics)   |   [**Asset Backed**](https://github.com/lakshmiDRIP/DROP/issues?q=is%3Aopen+is%3Aissue+label%3A%22asset+backed%22)   |   [**Dynamics**](https://github.com/lakshmiDRIP/DROP/issues?q=is%3Aopen+is%3Aissue+label%3Adynamics)   |   [**Execution**](https://github.com/lakshmiDRIP/DROP/issues?q=is%3Aopen+is%3Aissue+label%3Aexecution)   |   [**Exposure**](https://github.com/lakshmiDRIP/DROP/issues?q=is%3Aopen+is%3Aissue+label%3Aexposure)

## Installation

 Installation is as simple as building a jar and dropping into the classpath. There are no other dependencies.

## Samples

  [**Java Samples**](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample)   |   [**Excel Samples**](https://github.com/lakshmiDRIP/DROP/tree/master/Excel)   |   [**Test Data**](https://github.com/lakshmiDRIP/DROP/tree/master/Daemons)

## Documentation

 [**Javadoc API**](https://lakshmidrip.github.io/DROP/Javadoc/index.html) | [**DROP Specifications**](https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal) | [**Reference Specifications**](https://github.com/lakshmiDRIP/DROP/tree/master/Docs/External) | [**Release Notes**](https://github.com/lakshmiDRIP/DROP/tree/master/ReleaseNotes) | User guide is a work in progress!

## Misc

  [**JUnit Tests**](https://lakshmidrip.github.io/DROP/junit/index.html)   |   [**Jacoco Coverage**](https://lakshmidrip.github.io/DROP/jacoco/index.html)   |   [**Jacoco Session**](https://lakshmidrip.github.io/DROP/jacoco/jacoco-sessions.html)   |   [**Credit Attributions**](https://lakshmidrip.github.io/DROP/credits.html)   |   [**Version Specifications**](https://lakshmidrip.github.io/DROP/version.html)

## Contact

lakshmidrip7977@gmail.com

[![codecov.io](https://codecov.io/gh/lakshmiDRIP/DROP/branch/master/graphs/sunburst.svg)](https://codecov.io/gh/lakshmiDRIP/DROP/branch/master)  [![codecov.io](https://codecov.io/gh/lakshmiDRIP/DROP/branch/master/graphs/icicle.svg)](https://codecov.io/gh/lakshmiDRIP/DROP/branch/master)  [![codecov.io](https://codecov.io/gh/lakshmiDRIP/DROP/branch/master/graphs/tree.svg)](https://codecov.io/gh/lakshmiDRIP/DROP/branch/master)  [![codecov.io](https://codecov.io/gh/lakshmiDRIP/DROP/branch/master/graphs/commits.svg)](https://codecov.io/gh/lakshmiDRIP/DROP/branch/master)  
