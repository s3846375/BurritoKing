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

* VIP Customer
    New customers are registered as non-VIP by default. After logging in,they have the option to upgrade to VIP status by providing an email address to subscribe to promotions. VIP customers have the following additional features:
    - Order a meal (a burrito, one serving of fries, and a soda) with a $3 discount.
    - Use credits when paying for orders, specifying the number of credits to redeem. Every 100 credits can be used as one dollar.
    - Earn credits for each dollar spent on an order (excluding redeemed amounts).


### Controllers
- Main
- ChooseQtyCtrl
- CollectDateTimeCtrl
- EditProfileCtrl
- FoodCartCtrl
- FoodCartEditCtrl
- FoodCartEditQtyCtrl
- FoodMenuCtrl
- LoginCtrl
- MainMenuCtrl
- OrderDateTimeCtrl
- OrderHistoryExportCtrl
- OrderPaymentCtrl
- RedeemCreditCtrl
- RegisterCtrl
- UpgradeVIPCtrl

### Models
- Burrito
- CreditCardAuthenticator
- DatabaseManager
- FoodItem
- Fries
- Meal
- Order
- OrderDetail
- Restaurant
- SessionManager
- Soda
- StatusEnum
- User
- UserVIP

### Views
- ChooseQty
- CollectDateTime
- EditProfile
- EditQty
- FoodCart
- FoodCartEdit
- FoodMenu
- Login
- MainMenu
- OrderDateTime
- OrderHistoryExport
- OrderPayment
- RedeemCredit
- Register
- UpgradeVIP

### Tests
- CancelOrderTest
- CollectOrderTest
- CreditCardAuthenticatorTest
- CreditRedeemaleTest
- PrepareTimeTest
- TotalCostTest
- UpdateCreditTest

### IDE and java version
* IDE: IntelliJ
* Java Version: 22.0.1
* JavaFX Version: 21
* Database: SQLite