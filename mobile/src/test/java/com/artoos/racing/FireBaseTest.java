package com.artoos.racing;

import com.artoos.racing.utils.FirebaseSetup;

/**
 * Created by Nakhimovich on 8/23/14.
 */
public class FireBaseTest extends BaseTestCase
{
    public void testDataOperations() throws Exception
    {
        FirebaseSetup firebase=new FirebaseSetup();
        firebase.firebaseStuff();


    }
}
