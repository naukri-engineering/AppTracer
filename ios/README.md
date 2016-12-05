
### Features
***
1. Quick and easy setup with just a few lines of code.

2. Detailed screen/API load time reports along with OS, device and application details.

3. Compatible with all iOS 7.1 and above.

4. If there is no network coverage, reports are stored locally and sent later on the next run.

5. Find out how you app behaves by measuring the load time of Screens and Api.

6. Flexible enough to be integrated in both Swift and Objective-C projects.

7. Supports development as well as production environments.

8. All the page/api load logs can be stored in document directory of the application or backend system like [New Monk](https://github.com/naukri-engineering/NewMonk).



### Integration
***
To integrate the library in your application, follow the below mentioned steps:
`

1. Download the framework and bundle in your project.

2. For Objective C project write `#import <Tracer/Tracer.h>` in AppDelegate of project.

3. For Swift project write `#import <Tracer/Tracer.h>` in Bridging header file of project.

4. Configure optional properties:

    **enableDebug**: Used to enable or disable the log/print entries from this library. This is an optional variable. Use “true” for enabling log and “false” for disabling it. Default value is enabled “true”.

    **enableTracerEngine**: Used to enable or disable the library anytime. This is an optional variable. Use “true” for enabling Tracer library and “false” for disabling it. Default value is enabled “true”.

5. Initialize the library with the following code:
`

    #### Swift:
    ***
    ```swift
        let K_NEW_MONK_PAGE_LOAD_URL = "http://www.myNewMonkPageLoadUrl.com/pageload.php"   //Pass here your own Page load Url obtained from New Monk
        let K_NEW_MONK_APP_ID      =   13    //Pass here your own app_id obtained from New Monk
        
        /**
        * Some code
        * goes here
        */
        
        AppTracer.initaliseTracerForLoggingOn(ATFileTracer())
                             /*
                             * OR
                             */
        let tracerNewmonkObj = ATNewMonkTracer()
        tracerNewmonkObj.monkAppId = K_NEW_MONK_APP_ID
        tracerNewmonkObj.monkUrl = K_NEW_MONK_PAGE_LOAD_URL
        tracerNewmonkObj.userId = "test1@gmail.com"
        AppTracer.initaliseTracerForLoggingOn(tracerNewmonkObj)

    ```

    #### Objective-C:
    ***
    ```objc
        #import <Tracer/Tracer.h>
        #define K_NEW_MONK_PAGE_LOAD_URL  @"https://www.myNewMonkApp.com/pageLoad.php"  //Pass here your own Page load Url obtained from New Monk
        #define K_NEW_MONK_APP_ID         25   //Pass here your own app_id obtained from New Monk

 
        /**
        * Some code
        * goes here
        */

        //file logging During Development
        [AppTracer initaliseTracerForLoggingOn:[ATFileTracer new]];

                            /*
                            * OR
                            */
        //New Monk logging for Production
        ATNewMonkTracer *tracerObj = [ATNewMonkTracer new];
        tracerObj.monkUrl = K_NEW_MONK_PAGE_LOAD_URL;
        tracerObj.monkAppId = K_NEW_MONK_APP_ID;         
        tracerObj.userId = @"test1@gmail.com";
        [AppTracer initaliseTracerForLoggingOn:tracerObj];

        
    ```
6. For more details please refer to the Tracer sample attached.

###  Troubleshooting:

#### The framework is created in Objective C and xcode 7.3 ####
***
If you encounter below errors:

#### Error:
```Objc
If you are unable to build the archive or archive of the app is successful but organizer does not show the archived file.
```

#### Solution: 
* In Build Settings > Deployment` : Set `“Skip Install” to  NO.

***
#### Error:
```swift
dyld: Library not loaded: @rpath/Tracer.framework/Tracer
Referenced from: /Users/syshen/Library/Developer/CoreSimulator/Devices/..../Tracer.app/Tracer
Reason: image not found
```

#### Solution: 
* Ensure you have added Tracer.framework in `General > Embedded libraries` and `General > Linked Frameworks and Libraries`
* In Build Settings > Build options` : Set `“Embedded Content Contains Swift Code” to  Yes.
* And make sure the `“Runpath Search Paths” is set as “@executable_path/Frameworks”`.


### Contributors
***
* Himanshu Gupta

* Minni Arora

* Samarth Singla

### Contact Us
***
Get in touch with us with your suggestions, thoughts and queries at engineering@naukri.com


### License
***
Please see [LICENSE.md](LICENSE.md) for details
