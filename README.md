# Overview
Facillity Management Services is  a system which supports the management of buildings, allowing users to view rooms inside a certain facility that are available for daily usage, inspection, or maintenance and effectively schedule the rooms to satisfy their needs<br/>
The program uses:<br/>
* ***Front End*** : Android Studio  //in Development
* ***Back End***  : Java<br/>
* ***Framework*** : SpringBoot<br/>
* ***Database***  : PostgreSQL<br/>
* ***Testing***   : Junit and Integration Test (Mockito) //in Development

# Database Workflow
Below here is the UML Diagram of of the application. Click the picture to Zoom in.
<p align="center">
  <img src="https://github.com/AlbertSugi/Facility-Management-Services/blob/master/FacilityManagementServices/FacilityManagement.jpg"><br/>
</p><br/>

# Description
* Seperation of Concerns impelemented by dividing the layers between Client, Model and DAO. <br/>
* In the Model Package, it is further seperated to Facility, User, Inspection, Use and Maintanence. <br/>
* In each model, there are Service facades which connects the model with the clients. 
* Domain driven development is applied here as the usertypes (Faculty, Inspector and Technician) will retrieve all information needed for the request and direct it to the degsinated request (Userequest, Inspectionrequest and Maintainencerequest) <br/>

**There are 3 main patterns impelemented here:** <br/>
  1.Observer Pattern: <br/>
  When a room in a facility at a given time is booked, it will ask the user whether he/she wants to get notified, in case one cancels (removes schedule). 
  If yes, He/she will be notified. There is a viaSMS implementation using twillo API, but can be further improved using other means of notification (viaSMS is a child of Observer). 
  Using composition, the Observarable interface is implemented in schedule class, while the Observer in user. Then the trigger will be within service classes, where the user gets notified once the schedule is removed/vacated. 
  Note: Vacate uses current time to fasten the end date to the current date, while remove totally removes it from the schedu le, not considering the date
  
  2.Visitor Pattern: <br/>
  In this application, the User class visits the Facility class. In this case, the Facility accepts the User class. While visiting the Facility class, the User will get the Facility information
  and store it to the request classes. This aligns with the domain driven development as conceptually, a user will visit a facility to book the facility. 
  
  3.Facade Pattern:<br/>
  For each packages in the model, there will be a service interface that will act as a facade to interact with the client. <br/>
  
  
  
# To Run the Application
1. Create an account at https://www.elephantsql.com <br/>
2. Import the attached SQL code to the SQL Query Browser <br/>
3. Change the connection in the DB_Helper class:<br/>
   connection = DriverManager.getConnection(#Change codes here);
4. Run the desired client in Client class. 


 


