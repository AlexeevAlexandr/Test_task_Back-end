# **Test task Back-end**

**Instructions how to use:**
---
   1. Install, if not installed, and launch your MongoDb database;
   
   2. command to installing app: _mvn package_

   3. command to launching app: _java -jar target/test-1.0-SNAPSHOT.jar_
   ---
   At now, the project should be launched.
   There are three web pages:
   1. open the browser and follow the link (for example if you use localhost):
        _http://localhost:8080/home_
        
        (this page is intentionally left blank and can be filled in the future)
        
   2. _http://localhost:8080/conferenceRooms_
   
        On this page you can see a list of conference rooms and add a new one.
        If your list is empty, this means that you have not yet created any conference room.
        And you can do it to click on [_"Click to add Conference room"_](http://localhost:8080/addConferenceRoom).
        
   3. _http://localhost:8080/conferences_
   
        On this page you can see a list of conference and add a new one.
        To add click [_"Click to add Conference"_](http://localhost:8080/addConference).
        You can also set or change the conference status , view the registered participants list, 
        set or change the conference room, register new participants and delete the conference.
        To remove participants, click on the button with the conference participants number, 
        and then you can delete any participant.