mygpoclient-java
================

Java implementation mygpoclient.

My original goal for this project was to try to do a direct port from Python to Java.  I have tried to stay pretty close to the original source: http://thp.io/2010/mygpoclient/

RECENT MILESTONES:
* 20120718 - Transitioned to Gson library and removed Json.org dependency.
* 20120713 - FeedService implemented

TODO:
* Build Test Cases and automate testing
* ~~Use GSON library instead of JSON library for easier translation of JSON web services.~~
* Finish out the incomplete functions and add any new ones
* Test for valid inputs (add @assert) where appropriate
* Create command-line capability
* Build jar as executable jar instead of just library

