# DROP Exposure CSA Dynamics Package

<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

DROP Exposure CSA Dynamics Package implements the CSA Numeraire Basis/Measure Dynamics.

## Class Components

 * [***FundingBasisEvolver***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/csadynamics/FundingBasisEvolver.java)
 <i>FundingBasisEvolver</i> implements a Two Factor Stochastic Funding Model Evolver with a Log Normal
 Forward Process and a Mean Reverting Diffusion Process for the Funding Spread.

 * [***NumeraireInducedMeasureShift***](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/csadynamics/NumeraireInducedMeasureShift.java)
 <i>NumeraireInducedMeasureShift</i> computes the Shift of the Forward Terminal Distribution between the
 Non-CSA and the CSA Cases.


# References

 * Antonov, A., and M. Arneguy (2009): Analytical Formulas for Pricing CMS Products in the LIBOR Market Model
 	with Stochastic Volatility https://papers.ssrn.com/sol3/Papers.cfm?abstract_id=1352606 <b>eSSRN</b>

 * Burgard, C., and M. Kjaer (2009): Modeling and successful Management of Credit Counter-party Risk of
 	Derivative Portfolios <i>ICBI Conference</i> <b>Rome</b>

 * Gregory, J. (2009): Being Two-faced over Counter-party Credit Risk <i>Risk</i> <b>20 (2)</b> 86-90

 * Johannes, M., and S. Sundaresan (2007): Pricing Collateralized Swaps <i>Journal of Finance</i> <b>62</b>
 	383-410

 * Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives Pricing
 	<i>Risk</i> <b>21 (2)</b> 97-102


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
