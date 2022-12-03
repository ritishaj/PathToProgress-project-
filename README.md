# Path to Progress 

## *the fitbit for your mind :D*

#### Path to Progress is an application that will allow its users to track their academic progress for their courses throughout the semester. 
Bombed your math midterm? Thinking of dropping out because of this insane failure? It's not the end of the world! Path to Progress is the perfect app for users to strategize exactly how to bounce back with concrete numbers and data. Whether you have a certain academic goal in mind, or you just want to pass the class, Path to Progress will lay out the **path** for you (* *pun intended*).  

I picked this project to design a system for students to have a record of all their little successes and failure, hopefully gaining motivation as they see their progress throughout the course. The same way workout apps track physical fitness, Path to Progress tracks your *mental fitness*. 

## User Stories 
- As a user, I want to be able to select the courses within the given institution and add it to a list of courses for the semester.
- As a user, I want to be able to set a certain grade as an academic goal for each course.
- As I user, I want to be able to log in and out of my account. 
- As I user, I want to be able to see my current grade in past and current courses. 
- As I user, I want to be able to end the tracking when the course is over and add it to the list of past courses. 
- As a user, when I select log out, I want the option of saving my data to file 
- As a user, when I start the application, I want the option to load my data from file.


### Instructions for Grader 
- You can generate the first required event related to adding Xs to a Y by clicking add new courses in the account display 
and selecting a course 
- You can generate the second required event related to adding Xs to a Y by going to view current courses and selecting 
a course to end (you can see a display of this in view past courses).
- You can locate my visual component by logging in to the account menu display 
- You can save the state of my application by clicking the logout button and selecting yes to saving courses. 
- You can reload the state of my application by logging in and selecting yes to loading saved courses. 

**_NOTE:_** for ending current courses, make sure to **SELECT** the course first, click **OK** on the popup and then click on the 
button **End Course** to avoid getting a null pointer exception within the running program, otherwise you will need to 
log out and try again. If you click the **End Course** button multiple times, this may also result in a null pointer exception and you'll have to restart the application to properly run the code.

### Phase 4 Task 2: 
#### _Adding a course to current courses:_
<p>Thu Dec 01 17:56:50 PST 2022<br> 
CPSC110 added to current courses :(</p>

#### _Adding an academic goal for that course:_
<p>Thu Dec 01 17:56:50 PST 2022<br>
grade goal for CPSC110: 88 added!</p>

#### _Ending/Removing a course from current courses:_
<p>Thu Dec 01 17:56:58 PST 2022<br>
CPSC110 removed!</p>

#### _Ending a course, aka adding it to past courses:_
<p>Thu Dec 01 17:56:58 PST 2022<br>
CPSC110 added to past courses :)</p>

### Phase 4 Task 3: 

#### _WAYS TO REFACTOR:_
- Establishing a single point of control in GUI classes (maybe in LoginDisplay) for User field so the code is less repetitive and coupling is low 
- Improving cohesion of User class by creating a different class for Course Manager to handle adding/removing courses 
- Handle exceptions by creating checked exceptions (for things like adding a course that is already there, username is invalid, etc.) instead of if-then statements
- Shifting some elements into fields of GUI classes instead of having them as dependencies to make the code more consistent and more concise (ex. Some GUI classes have fields for JSON Reader/Writer and some have them as dependencies)
- Creating a HashMap for courses and their corresponding grade goals instead of having two separate lists with matching indexes because these two types of elements are associated with each other (each course has a grade goal)
- Make the functionality of methods in GUI classes easier to understand by breaking it down to clean up the code - this can possibly be done by extracting them and renaming them
- Refactoring code to spawn the LoginDisplay GUI from the main method so it does not depend on the console-based UI

#### Citations: 
- Data persistence(JSON) methods, classes and interfaces: 
https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
- GUI methods: 
- https://docs.oracle.com/javase/tutorial/uiswing/examples/components/index.html



  