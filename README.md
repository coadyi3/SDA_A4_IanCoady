# SDA_A4_IanCoady

This Assignment was a follow on from our previous work in assignment 3 on recycler views and tabbed views, for this assignment we had to adapt our tutors code on a basic library boook rental app.

This project was designed primarilly to examine the users understand of the Google Firebase Platform and the use of shared preferences for data persistance in an application. This app tests the user to read, update and delete data from a firebase database.

Any code adapted from third parties are credited and referenced inside of the code itself in comments above the areas the code is adapted into.

Special mention to the documentaion provided at Googles Firebase website (https://firebase.google.com/docs), which was licenced in areas where code was used. This was my primary resorce for completing this project as I had never had any experience with Google Firebase before, but it was clearly explaned and documetated very well so I found it relatively simple to get to grips with the concepts involved in setting up and using Firebase.

This application contains one main acitivity split into 3 seperate tabbed activities and one other activity allowing the user to check out a book.

The first tabbed acitivy is just a standard welcome page with a logo informing the user of the library opening hours.

The second tabbed activity is a recycler view containing different books from different authors which are available for rental. Inside each recycler list item there is a button which opens the check out activity but this button cannot be pressed until the user inputs their details into the third activity.

The third tabbed activity as mentioned above is a settings form where the user can unput their details which will allow them to check out books. These user inputs are then validated before they are saved into Shared Preferences which will allow the date to be persistant across sessions. There is also a delete button which allows the user to clear their stored information.

Finally, once the user has entered their valid details they are allowed to check out a book of their own choosing. Inside the checkout activity, the user is required to select the date they would like to rent the book and then click check out button, once clicked, the users name, the title of the book, the current time and the selected time are stored in the data base on Firebase. The book is also marked as unavailable to if you were to leave the check out acitivty and try check out that particular book again it would not be possible. There is also a button to mark the book as available upon return, this clears the times from the database and marks the book as availble for someone else to rent.
