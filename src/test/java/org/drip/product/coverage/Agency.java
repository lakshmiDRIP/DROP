
package org.drip.product.coverage;

import org.junit.Test;
import static org.junit.Assert.*;

public class Agency
{
	@Test public void agencyFixedBullet1() throws Exception
	{
        	org.drip.sample.agency.AgencyFixedBullet1.main (null);

        	org.drip.sample.agency.AgencyFixedBullet2.main (null);
    	}
}
