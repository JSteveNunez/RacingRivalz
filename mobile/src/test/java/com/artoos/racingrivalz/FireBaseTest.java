package com.artoos.racingrivalz;

import com.artoos.racingrivalz.utils.FirebaseHelper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;

/**
 * Created by Nakhimovich on 8/23/14.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(emulateSdk = 18)
public class FireBaseTest extends BaseTestCase
{
    @Test
    public void testDataOperations() throws Exception
    {
        FirebaseHelper firebase=FirebaseHelper.getInstance();
        firebase.seedRace();
    }
}
