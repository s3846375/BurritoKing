# BurritoKing
BurritoKing allows customers to order food from the Burrito King restaurant, offering features such as order status tracking, order history export, and discounts for VIP customers. Built with JavaFX for the user interface and SQLite for data storage, the application follows an MVC (Model-View-Controller) architecture, separating the program into three main components: Model, View, and Controller. The application contain the following key features:

* The Restaurant
    - Sells burritos for $7, fries for $4, and sodas for $2.50 each.
    - Burritos are prepared in batches of 2, with each batch taking 9 minutes.
    - Fries are served instantly if there are portions available in the warming tray. If there aren’t enough fries for an order, they are cooked in batches of 5, which take 8 minutes, and can be prepared in parallel with burritos.
    - Sodas are poured instantly.

* Customer
    - Create a profile with a unique username, password, first name, and last name.
    - Edit profile details (first name, last name, and password; the username is not changeable).
    - Once logged in, the customer dashboard displays the user's first name, last name, and any active order (including a detailed summary) that has not been picked up.
    - Add food items to a shopping basket and update the basket contents as long as the customer has not checked out.
    - Display the total price and estimated waiting time before checking out an order.
    - Provides (simulated) credit card information to place orders and make payments, with input validation (16-digit card number, expiry date, and 3-digit CVV).
    - View all order details (date, time, total price, and status).
    - Update the status of orders from "await collection" to “collected” or “cancelled.”
    - Export order history to a CSV file, allowing the customer to select which orders and what information to export.

* VIP Customer <be> 

    New customers are registered as non-VIP by default. After logging in,they have the option to upgrade to VIP status by providing an email address to subscribe to promotions. VIP customers have the following additional features:
    - Order a meal (a burrito, one serving of fries, and a soda) with a $3 discount.
    - Use credits when paying for orders, specifying the number of credits to redeem. Every 100 credits can be used as one dollar.
    - Earn credits for each dollar spent on an order (excluding redeemed amounts).

### IDE and java version
* IDE: IntelliJ
* Java Version: 22.0.1
* JavaFX Version: 21
* Database: SQLite


### Controllers
- ``Main``: Launches the JavaFX application and initialises the database.
- ``ChooseQtyCtrl``: Controls food item quantity selection and manages transitions to the food menu.
- ``CollectDateTimeCtrl``: Handles the input and validation of collection datetime for orders, status updates, and navigation to MainMenu.
- ``EditProfileCtrl``: Manages the user's profile editing, updates the database with new information, and handles navigation to the login screen.
- ``FoodCartCtrl``: Displays the summary of the active order, allows navigation to food menu, cart editing, and payment screens.
- ``FoodCartEditCtrl``: Displays the food items in the active order, allows users to select and edit food quantities.
- ``FoodCartEditQtyCtrl``: Allows users to edit the quantity of a selected food item in the active order and updates the order accordingly.
- ``FoodMenuCtrl``: Manages food menu display, controls the visibility of the Meal button for VIP users, and handles navigation to food quantity selection and the food cart.
- ``LoginCtrl``: Manages user login validation, sets the session user and order history, and navigates to the main menu or registration screen.
- ``MainMenuCtrl``: Manages the main menu for the user, displaying their active orders, providing options for profile editing, upgrading to VIP, canceling orders, and navigating to other views like order history and food menu.
- ``OrderDateTimeCtrl``: Manages the order date and time selection, allows user to finalize the order, update remaining items like Fries, reset the active order, and save the order details to history.
- ``OrderHistoryExportCtrl``: Handles the export of the user's order history to a CSV file. It allows the user to select specific order details, choose a file name and directory.
- ``OrderPaymentCtrl``: Handles order payment process, including credit card validation, redeeming VIP credits, and updating the final payment.
- ``RedeemCreditCtrl``: Manages the process of redeeming VIP credit for an order, allowing the user to select the redeemable amount and updating the final payment accordingly.
- ``RegisterCtrl``: Handles user registration by validating input, checking for existing usernames, and saving new user details to the database.
- ``UpgradeVIPCtrl``: Manages the process of upgrading a user to VIP status, including email validation and saving to the database. 

### Models
- ``FoodItem``: Represents an item on a menu with a unit price and quantity. 
- ``Burrito``: Extends FoodItem and represents a Burrito item with a predefined batch preparation time and batch size.
- ``Fries``: Extends FoodItem and represents a fries item with a predefined batch preparation time and batch size.
- ``Soda``: Extends FoodItem and represents a soda item.
- ``Meal``: Extends FoodItem and represents a meal item.
- ``CreditCardAuthenticator``: Provides a utility method to validate credit card information including card number, expiry date, and CVV.
- ``DatabaseManager``: Handles the creation and management of SQLite database tables and operations for users, VIP users, and order history.
- ``Order``: Manages a customer's food order, storing a list of FoodItem objects. It provides methods to add new items or update the quantity of existing items, as well as remove items. The class also includes a method to calculate the total price of the order by summing the price of each food item based on its quantity.
- ``OrderDetail``: Represents a customer's order with details, providing methods for updating order status, validating collection time, and retrieving formatted order detail string.
- ``Restaurant``: Manages food prices, remaining fries, and meal discounts. It provides methods to calculate preparation time for orders, update remaining fries based on orders, and retrieve food prices.
- ``SessionManager``: The SessionManager class follows the Singleton pattern to manage the current session of a user. It handles user information, active orders, order details, and interaction with the restaurant. 
- ``StatusEnum``: Represents the possible states of an order. It defines three statuses: AWAIT_COLLECTION, COLLECTED, and CANCELLED.
- ``User``: Represents a user with a username, password, first name, and last name.
- ``UserVIP``: Extends the User class and represents a VIP user with additional features such as email and credit points. It provides methods for calculating redeemable amounts based on the user's credit points and subtotal, as well as updating the user's credit points after a transaction.

### Views
- ``ChooseQty``:
- ``CollectDateTime``:
- ``EditProfile``:
- ``EditQty``:
- ``FoodCart``:
- ``FoodCartEdit``:
- ``FoodMenu``:
- ``Login``:
- ``MainMenu``:
- ``OrderDateTime``:
- ``OrderHistoryExport``:
- ``OrderPayment``:
- ``RedeemCredit``:
- ``Register``:
- ``UpgradeVIP``:

### Tests
- CancelOrderTest
- CollectOrderTest
- CreditCardAuthenticatorTest
- CreditRedeemaleTest
- PrepareTimeTest
- TotalCostTest
- UpdateCreditTest

