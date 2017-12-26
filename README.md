# DROP

**v3.24**  *25 December 2017*

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP implements the model libraries and provides systems for fixed income valuation and adjustments, asset allocation and transaction cost analytics, and supporting libraries in numerical optimization and statistical learning.

DROP is composed of four main libraries:

 [Asset Allocation](https://lakshmidrip.github.io/DROP/AssetAllocationModule.html) | [Fixed Income Analytics](https://lakshmidrip.github.io/DROP/FixedIncomeModule.html) | [Numerical Optimization](https://lakshmidrip.github.io/DROP/NumericalOptimizerModule.html) | [Statistical Learning](https://lakshmidrip.github.io/DROP/StatisticalLearningModule.html)

## Pointers

<p style="text-align:center">
<a href="https://travis-ci.org/lakshmiDRIP/DROP"><img src="https://travis-ci.org/lakshmiDRIP/DROP.svg"></a>
<a href="https://circleci.com/gh/lakshmiDRIP/DROP"><img src="https://circleci.com/gh/lakshmiDRIP/DROP.svg?style=svg"></a>
<a href="https://circleci.com/gh/lakshmiDRIP/workflows/DROP"><img src="https://img.shields.io/circleci/project/github/lakshmiDRIP/DROP.svg"></a>
<a href="https://ci.appveyor.com/project/lakshmiDRIP/drop"><img src="https://ci.appveyor.com/api/projects/status/32r7s2skrgm9ubva?svg=true"></a>
</br>

<a href="https://github.com/lakshmiDRIP/DROP/releases"><img src="https://img.shields.io/github/release/lakshmiDRIP/DROP.svg"></a>
</br>

<a href="https://www.versioneye.com/user/projects/5a2e15d50fb24f6ad613a09f"><img src="https://www.versioneye.com/user/projects/5a2e15d50fb24f6ad613a09f/badge.svg?style=flat-square"></a>
<a href="https://waffle.io/lakshmiDRIP/DROP"><img src="https://badge.waffle.io/lakshmiDRIP/DROP.svg?columns=all"></a>
</br>

<a href="LICENSE"><img src="http://dmlc.github.io/img/apache2.svg"></a>
<a href="http://stackoverflow.com/questions/tagged/drip"><img src="http://img.shields.io/:stack%20overflow-drip-brightgreen.svg"></a>
<a href="https://gitter.im/lakshmiDRIPDROP?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge"><img src="https://badges.gitter.im/Join%20Chat.svg"></a>
</br>

<a href="https://www.codacy.com/app/lakshmiDRIP/DROP?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=DROP/DROP&amp;utm_campaign=Badge_Grade"><img src="https://api.codacy.com/project/badge/Grade/7270e4b57c50483699448bf32721ab10"></a>
<a href="https://www.codacy.com/app/lakshmiDRIP/DROP?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=DROP/DROP&amp;utm_campaign=Badge_Coverage"><img src="https://api.codacy.com/project/badge/Coverage/7270e4b57c50483699448bf32721ab10"></a>
<a href="https://codecov.io/gh/lakshmiDRIP/DROP/branch/master"><img src="http://codecov.io/github/lakshmiDRIP/DROP/coverage.svg?branch=master"></a>
<a href="https://coveralls.io/github/lakshmiDRIP/DROP?branch=master"><img src="https://coveralls.io/repos/github/lakshmiDRIP/DROP/badge.svg?branch=master"></a>
<a href="https://scan.coverity.com/projects/lakshmidrip-drop"><img src="https://scan.coverity.com/projects/14574/badge.svg"></a>
</br>

<a href="http://dripdrop.readthedocs.io/en/latest/?badge=latest"><img src="https://readthedocs.org/projects/dripdrop/badge/?version=latest"></a>
<a href="https://lakshmidrip.github.io/DROP/Javadoc/index.html"><img src="https://readthedocs.org/projects/xgboost/badge/?version=latest"></a>
<a href="https://github.com/lakshmiDRIP/DROP/tree/master/Docs"><img src="https://readthedocs.org/projects/xgboost/badge/?version=latest"></a>
</br>

<a href="https://github.com/sindresorhus/awesome"><img src="https://cdn.rawgit.com/sindresorhus/awesome/d7305f38d29fed78fa85652e3a63e154dd8e8829/media/badge.svg"></a>
</p>

[![Travis](https://travis-ci.org/lakshmiDRIP/DROP.svg)](https://travis-ci.org/lakshmiDRIP/DROP)    [![CircleCI](https://img.shields.io/circleci/project/github/lakshmiDRIP/DROP.svg)](https://circleci.com/gh/lakshmiDRIP/workflows/DROP)    [![CircleCI](https://circleci.com/gh/lakshmiDRIP/DROP.svg?style=svg)](https://circleci.com/gh/lakshmiDRIP/DROP)    [![Build status](https://ci.appveyor.com/api/projects/status/m5p8sfeth4cewr4v?svg=true)](https://ci.appveyor.com/project/lakshmiDRIP/drop)    
[![Git](https://img.shields.io/github/release/lakshmiDRIP/DROP.svg)](https://github.com/lakshmiDRIP/DROP/releases)    
[![Dependency Status](https://www.versioneye.com/user/projects/5a2e15d50fb24f6ad613a09f/badge.svg?style=flat-square)](https://www.versioneye.com/user/projects/5a2e15d50fb24f6ad613a09f)    [![Waffle.io - Columns and their card count](https://badge.waffle.io/lakshmiDRIP/DROP.svg?columns=all)](https://waffle.io/lakshmiDRIP/DROP)    
[![Stack Overflow](http://img.shields.io/:stack%20overflow-drip-brightgreen.svg)](http://stackoverflow.com/questions/tagged/drip)    [![Git](http://dmlc.github.io/img/apache2.svg)](./LICENSE)    
[![Join the chat at https://gitter.im/lakshmiDRIPDROP](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/lakshmiDRIPDROP?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)    

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/7270e4b57c50483699448bf32721ab10)](https://www.codacy.com/app/lakshmiDRIP/DROP?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=DROP/DROP&amp;utm_campaign=Badge_Grade)   [![Codacy Badge](https://api.codacy.com/project/badge/Coverage/7270e4b57c50483699448bf32721ab10)](https://www.codacy.com/app/lakshmiDRIP/DROP?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=DROP/DROP&amp;utm_campaign=Badge_Coverage)   [![codecov.io](http://codecov.io/github/lakshmiDRIP/DROP/coverage.svg?branch=master)](https://codecov.io/gh/lakshmiDRIP/DROP/branch/master)   [![Coverage Status](https://coveralls.io/repos/github/lakshmiDRIP/DROP/badge.svg?branch=master)](https://coveralls.io/github/lakshmiDRIP/DROP?branch=master)   [![Coverity Status](https://scan.coverity.com/projects/14574/badge.svg)](https://scan.coverity.com/projects/lakshmidrip-drop)    
[![Documentation Status](https://readthedocs.org/projects/dripdrop/badge/?version=latest)](http://dripdrop.readthedocs.io/en/latest/?badge=latest)  [![Javadoc](https://readthedocs.org/projects/xgboost/badge/?version=latest)](https://lakshmidrip.github.io/DROP/Javadoc/index.html)  [![Other](https://readthedocs.org/projects/xgboost/badge/?version=latest)](https://github.com/lakshmiDRIP/DROP/tree/master/Docs)

[![Awesome](https://cdn.rawgit.com/sindresorhus/awesome/d7305f38d29fed78fa85652e3a63e154dd8e8829/media/badge.svg)](https://github.com/sindresorhus/awesome)

## Installation

 Installation is as simple as building a jar and dropping into the classpath. There are no other dependencies.

## Samples

  [Java Samples](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample) | [Excel Samples](https://github.com/lakshmiDRIP/DROP/tree/master/Excel) | [Test Data](https://github.com/lakshmiDRIP/DROP/tree/master/Daemons)

## Documentation

 [Javadoc API](https://lakshmidrip.github.io/DROP/Javadoc/index.html) | [DROP Specifications](https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal) | [Reference Specifications](https://github.com/lakshmiDRIP/DROP/tree/master/Docs/External) | [Release Notes](https://github.com/lakshmiDRIP/DROP/tree/master/ReleaseNotes) | User guide is a work in progress!

## Misc

  [JUnit](https://lakshmidrip.github.io/DROP/junit/index.html) | [Jacoco Coverage](https://lakshmidrip.github.io/DROP/jacoco/index.html) | [Jacoco Session](https://lakshmidrip.github.io/DROP/jacoco/jacoco-sessions.html)

## Contact

lakshmidrip7977@gmail.com

[![codecov.io](https://codecov.io/gh/lakshmiDRIP/DROP/branch/master/graphs/sunburst.svg)](https://codecov.io/gh/lakshmiDRIP/DROP/branch/master)  [![codecov.io](https://codecov.io/gh/lakshmiDRIP/DROP/branch/master/graphs/icicle.svg)](https://codecov.io/gh/lakshmiDRIP/DROP/branch/master)  [![codecov.io](https://codecov.io/gh/lakshmiDRIP/DROP/branch/master/graphs/tree.svg)](https://codecov.io/gh/lakshmiDRIP/DROP/branch/master)  [![codecov.io](https://codecov.io/gh/lakshmiDRIP/DROP/branch/master/graphs/commits.svg)](https://codecov.io/gh/lakshmiDRIP/DROP/branch/master)  
