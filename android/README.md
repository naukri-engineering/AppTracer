**Features**

1. Quick and easy set up with just a few lines of code.
2. Detailed information about time taken by web services and the native rendering.
3. Compatible with all the Android versions from 2.3 onwards.
4. Batched transfers to conserve battery and bandwidth. Limits the number of network calls as well.
5. Toggle enabled to disable the feature from your application at any time.
6. Contextual data (memory, cpu etc..) to come in the next version.

**Integration**

To integrate the library in your application, follow the steps below,

1. Download and include this library in your project
2. Configure mandatory parameters. An exception is thrown if these are not provided.
   * uri: The endpoint to send the screen load time/API load time. It should return a response code 202/204 without a response body. Response body, if any, would be ignored.
   * appId: Your unique application id. This could be the id that NewMonk server has created for you.
3. Configure optional parameters.
   * interval: The frequency at which data should be sent to server. Default value is 1 hour.
   * cleanOnError: Indicates if local data should be cleared irrespective of the web service response code. Default value is false. This can be used if in case of service failures you do not want the local data storage to explode.
4. Initialize - Just call initLoadtime() in your application class passing the desired parameters.
    ```Java
    ScreenLoadTime.getInstance().initLoadTime(getApplicationContext(), APP_ID, LOAD_TIME_URL, AlarmManager.INTERVAL_DAY, true);
    ```
5. Define user identifier – In order to link data with users, optionally you can define a user id. This is needed only once, when a user logs in to your app. Remember to set it to blank on logout.
6. Record screen load time – To calculate load time of a particular screen, mark the start and end times of your screen and then call setScreenLoadTime(). The method expects a few arguments,
   * Screen Name: Unique name of the screen
   * Start Load Time: The start time (in milliseconds).
   * Screen Instance Id: This is an important parameter. It should be a unique value for each instance of the screen. You can ask AppTracer to generate it for you by calling getScreenInstanceId() or you can generate one of your own.
    ```Java
    mScreenLoadTime.setScreenLoadTime(“ScreenName”, mStartLoadTime, mScreenInstanceId);
    ```
7. Record web service time - To calculate web service load time use startApiLoadTime() just before the network call and endApiLoadTime() just after you get a response from it. Remember to pass the same instance id as for the screen load time.
    ```Java
    mScreenLoadTime.startApiLoadTime(mScreenInstanceId,"API Name");
    mScreenLoadTime.endApiLoadTime(mScreenInstanceId,"API Name");
    ```
8. Disable this feature: If needed, you can disable this feature anytime from your application by calling disableScreenLoadTime()


**Contributors**

* Vishnu Anand

* Minni Arora

* Sudeep SR

**Contact Us**

Get in touch with us with your suggestions, thoughts and queries at engineering@naukri.com

**License**

Please see [LICENSE.md](LICENSE.md) for details

**Acknowledgement**

This product includes Volley downloaded from https://android.googlesource.com/platform/frameworks/volley which is licensed under the Apache License, Version 2.0
