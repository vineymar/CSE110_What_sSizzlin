# CSE110: What's Sizzlin
-------------------------
Welcome to What's Sizzlin
-------------------------

This is a test version that I have made. Will probably get rid of the google,facebook and twitter sign in.
Will keep email address, password, login with phone number, register user buttons


TODO:
-----------
MainScreen 
-----------
- Let's revamp this. Make it more clean. Maybe an orange background with our logo in the middle. White text for the title of "What's Sizzlin"   
- For the Logo, if we went with an orange background - we could potentially make the LogoColors of: white->orange and the orange->white. To make everything smoother. (so the apron would blend in with the background, and the grill would be white).
- The login with phone number button can be made into a long rectangle to match the width and height of the email/password boxes

-------------------            
Registration Screen 
-------------------
- We can potentially make the background the same as the MainScreen with a silhouette of our logo while the user enters the given field: Email/VeryEmail/Password/VerifyPassword (maybe) First/Last Names.
- Registering User's currently connect to the firebase database. (if the user tries to enter an email that's already taken or not valid, an error will occur).

--------------
Loading Screen
--------------
- Since everything will be the trademark orange background #FF631D, the loading screen could just be that background, with our logo either in the top/bottom corners, or logo in the middle with alpha=.5-.75
- These are considered SplashPages, if anyone wants to implement this between actual login, app start up, and other loading screens, please contact me and feel free. 

----------------
BottomNavigation
----------------
- I'm Currently in the process of trying to handle clickevent through the BottomNavigation bar with MenuItems. This in turn will start a new Intent View with the selected id for each MenuItem.
- I have created some Activity screens that need to be implemented. 

     ------------------------------------
     - SearchActivity - [Not Started]
     - PantryActivity - [Not Started]
     - PreferencesActivity- [Not Started] (This is userProfile)
     ------------------------------------
       As you can tell, these are the screens for each different icon in the BottomNavigationBar [Home, Search, Pantry,Preferences]
       
-----------
LoginButton
-----------
- LoginButton does not authenticate through firebase. I don't know what the deal with that is. Probably some error in my logic. But either way, for test purposes just go ahead and click the login button. 

-----------
LogoutButton
-----------
- We need a logout button. If implemented please tell me so I can make an instance of the user to logout.

----------------------
Database Search/Filter
----------------------
- Vinamr/Hayden say they have a very basic copy. Will need to coordinate to implement into master. 
