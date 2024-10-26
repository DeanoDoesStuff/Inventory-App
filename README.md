•	Briefly summarize the requirements and goals of the app you developed. What user needs was this app designed to address?

Some notes on what the goals of this app were. The user needed the ability to view a login page which had the functionality to allow test input for login information. Create an account if none existed for that user, and login that user if account credentials already existed. This account data was in permanent storage on a sqlite database. Once logged in, the user would be taken to an inventory viewer page. Here, the user would view any inventory items and those items quantities. Text inputs allowed the user to input new inventory items and that items quantity. Each item row in the view had a delete button for removing an item from inventory. All inventory items were stored in a sqlite database. If an item quantity reached zero, the row would highlight red and notify the user. So long as that user accepted mobile notifications.

•	What screens and features were necessary to support user needs and produce a user-centered UI for the app? How did your UI designs keep users in mind? Why were your designs successful?

The app only needed a login screen and an inventory screen. The login screen used shared text inputs between creating an account and logging in.

•	How did you approach the process of coding your app? What techniques or strategies did you use? How could those techniques or strategies be applied in the future?

After breaking down the requirements into small tasks and steps, I got to work implementing a rough outline of the UI. This was done in short sprints, with each task being implemented sequentially. I’d write the login inputs, then add in the buttons and place in static test data to ensure it all looked correct. After the UI was roughly complete, I began implementing the data aspect, including the databases, any changes to the UI were performed to allow for dynamic data from the database. 

•	How did you test to ensure your code was functional? Why is this process important, and what did it reveal?
This depended on where I was at in the project. Early on, I wrote in static data to test the UI implementation. Later, I would go back over each requirement unit and refactor to allow for dynamic data from the databases.

•	Consider the full app design and development process from initial planning to finalization. Where did you have to innovate to overcome a challenge?
The refactor process from my static test data in the inventory view into the database data was a bear of a task. Luckily, the process for database implementation for the login screen was comparatively straightforward, so I could utilize a lot of the same principle again for the inventory system.

•	In what specific component of your mobile app were you particularly successful in demonstrating your knowledge, skills, and experience?

I particularly liked my recycler view implementation for the inventory screen. I wanted more flavor for when an item reaches zero, and I felt that highlighting the row did a good job of expressing the urgency of that item. 
