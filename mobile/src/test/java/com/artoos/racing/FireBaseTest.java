package com.artoos.racing;

import com.artoos.racing.utils.FirebaseSetup;

import org.junit.Test;

/**
 * Created by Nakhimovich on 8/23/14.
 */
public class FireBaseTest extends BaseTestCase
{
    @Test
    public void testDataOperations() throws Exception
    {
        FirebaseSetup firebase=new FirebaseSetup();
        firebase.firebaseStuff();
    }
}
