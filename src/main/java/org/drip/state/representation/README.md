# DROP State Representation Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP State Representation Package contains the Latent State Merge Sub-stretch.


## Class Components

 * [***LatentState***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/representation/LatentState.java)
 <i>LatentState</i> exposes the functionality to manipulate the hidden Variable's Latent State. Specifically
 it exports functions to:
 	* Produce node shifted, parallel shifted, and custom manifest-measure tweaked variants of the Latent
 		State
 	* Produce parallel shifted and custom quantification metric tweaked variants of the Latent State

 * [***LatentStateMergeSubStretch***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/representation/LatentStateMergeSubStretch.java)
 <i>LatentStateMergeSubStretch</i> implements merged stretch that is common to multiple latent states. It is
 identified by the start/end date predictor ordinates, and the Latent State Label. Its methods provide the
 following functionality:
 	* Identify if the specified predictor ordinate belongs to the sub stretch
 	* Shift that sub stretch start/end
 	* Identify if the this overlaps the supplied sub stretch, and coalesce them if possible
 	* Retrieve the label, start, and end

 * [***LatentStateSpecification***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/representation/LatentStateSpecification.java)
 <i>LatentStateSpecification</i> holds the fields necessary to specify a complete Latent State. It includes
 the Latent State Type, the Latent State Label, and the Latent State Quantification metric.

 * [***MergeSubStretchManager***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/representation/MergeSubStretchManager.java)
 <i>MergeSubStretchManager</i> manages the different discount-forward merge stretches. It provides
 functionality to create, expand, or contract the merge stretches.


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
