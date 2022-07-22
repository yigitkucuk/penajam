Project group number: 4 

Project Title: PenaJam 

Project Description: PenaJam is an android application which allows musicians to share posts looking for band-members or apply for band auditions, look through music related events and chat with each other through private chatrooms. 

The project's current status: Our application is mainly done but we are adding and improving some of our functionality (star-level filtering system, video upload), we can say that it’s %80 done. 

What has been done (Works/ doesn’t work/ remaining): Registering and logging in is available, it is possible to post an audition and apply for it. You can see your own profile and edit it via settings. There is a chat working but we haven’t made it private just yet. The functions we implemented are working smoothly but there are some features that remain to be done such as fetching data from the internet for displaying music-related events, making our chatroom private and small touchups. 

Each member's contribution: 

Yiğit  -> Mainly worked with databases and created back-end systems. Also designed some of the layouts and created drawables and backgrounds.
          Created the chatroom functionality, authentication funcitonality, database storage functionality and view adapters.
          Worked on files: User.java, Userbase.java, Users.java RegisterActivity.java, LoginActivity.java, RecyclerViewAdapter.java, Post.java, Message.java,                            Navigation.java (Interface), ChatActivity.java, FirstFragment.java, SecondFragment.java and build.gradle files.
                           Also, the respective .xml files and other .xml files.

Alphan -> Mainly worked with front-end of the application. Also worked on the back-end to fully implement audition-post functionality.
          Created the audition-post functionality, image upload functionality, image storage in database functionality and view adapters.
          He created many of the layouts, and they were later finalized by other members.
          Worked on files: RegisterActivity.java, LoginActivity.java, SettingsActivity.java, ProfilePageActivity.java, ProfilePage.java, PostActivity.java,
                           NewRecyclerViewAdapter.java, NewPostActivity.java, Model.java, MainScreenActivity.java, ImageRecyclerView.java
                           Also, the respective .xml files and other.xml files.

Barış  -> Mainly worked with back-end implementations. He created the system to demonstrate all users and their usernames.
          Created the user-demonstration functionality, and user-data fetch functionality.
          Worked on files: Users.java, Userlist.java, User.java, Userbase.java, ShowUserActivity.java, LoginActivity.java,                                                              RegisterActivity.java
                           Also, the respective .xml files.

Defne  -> Mainly worked with front-end GUI designs. Helped implementing Image upload functionality.
          Helped store image url in database, and implementing image upload functionality.
          Worked on files: RegisterActivity.java, LoginActivity.java, MainScreenActivity.java, SettingsActivity.java, ProfilePageActivity.java,                                          ProfileActivity.java, 
                           Also, the respective .xml files and other layouts.

Ege    -> Mainly worked with back-end systems, helped create messages, chatroom and user classes. Also revised some of the layouts.
          Worked on Files: ChatActivity.java, Message.java, LoginActivity.java, RegisterActivity.java, Userbase.java, User.java, Users.java.
                           Also, the respective .xml files.
                                  

Software used with version numbers, libraries:  

Android Studio Chipmunk | 2021.2.1 

Java SE 18.0. 1.1 

'androidx.appcompat:appcompat:1.4.2' 

'com.google.android.material:material:1.6.1' 

'androidx.constraintlayout:constraintlayout:2.1.4' 

'androidx.navigation:navigation-fragment:2.5.0' 

'androidx.navigation:navigation-ui:2.5.0' 

'com.google.firebase:firebase-auth:21.0.6' 

'com.google.firebase:firebase-database:20.0.5' 

'com.google.firebase:firebase-storage:20.0.1' 

'junit:junit:4.13.2' 

'androidx.test.ext:junit:1.1.3' 

'androidx.test.espresso:espresso-core:3.4.0' 

'com.google.android.material:material:1.6.1' 

'com.firebaseui:firebase-ui-database:8.0.1' 

'com.squareup.picasso:picasso:2.8' 

'com.github.bumptech.glide:glide:4.13.2' 

'com.github.bumptech.glide:compiler:4.13.2' 

Instructions to run and compile on Android:     As the application is on the Android platform, we will just deploy it as an executable to Android and users                                                   will just be able to download and enter the app by clicking the app icon.
                              
Instructions to test on Windows, Linux, MacOS:  Clone the repository to your local machine via "Get From VCS" button in Android Studio.
                                                Open the repository.
                                                Create an Emulator.
                                                Build and Run.
       
                                                

