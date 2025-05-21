# Eighth Assignment - Restaurant

## Overview 👋

Welcome to your final assignment before the grand project! 🍽️🚀 We’re wrapping things up with a full-stack desktop application that simulates a restaurant ordering system. You’ll build it using **JavaFX** for the UI and **PostgreSQL** for persistent data storage.

You’ll implement essential user flows like **sign-up**, **login**, and **placing orders**, and use **JDBC** for direct interaction with the database. Your journey will bring together everything you've learned in this course — from file-based applications to database-backed systems with a graphical interface.
  
## Prerequisites ✅  

 Make sure you have installed these programs and services before starting your project:  
  
- Git  
- Java 23  
- Gradle   
- PostgreSQL  
- Scene Builder  
> 💡 You might need VPN or DNS setting for downloading JavaFX packages using Gradle.
  
## Objectives ✏️

By completing this assignment, you will:

- Build a **full-stack desktop application** that integrates a graphical user interface with a relational database.
    
- Understand how to **connect a Java application to a relational database** using JDBC.
    
- Gain hands-on experience in **designing and implementing UI using JavaFX**.

## Logic

### Entities

Your project should **at minimum** include the following entities to properly model a simple restaurant ordering system. The following entities are essential for managing users, menu items, and orders:

------

### 1. **User**

Represents a person using the system (e.g., a customer).

- **Attributes:**
  - `id`: Unique identifier
  - `username`
  - `password`
  - `email` *(optional)*
- **Relationships:**
  - One **User** can place many **Orders**.

------

### 2. **MenuItem**

Represents a single item on the restaurant's menu.

- **Attributes:**
  - `id`: Unique identifier
  - `name`
  - `description` *(optional)*
  - `price`
  - `category` *(optional, useful for the bonus part)*
- **Relationships:**
  - A **MenuItem** can appear in many **OrderDetails** (i.e., be ordered in many different orders).

------

### 3. **Order**

Represents a single order placed by a user.

- **Attributes:**
  - `id`: Unique identifier
  - `userId`: Reference to the `User` who placed the order
  - `createdAt`: Timestamp of when the order was placed
  - `totalPrice`: Total cost of the order (can be calculated or stored)
- **Relationships:**
  - An **Order** belongs to one **User**.
  - An **Order** has many **OrderDetails**, each representing a specific item in the order.

------

### 4. **OrderDetail**

Represents a line item in an order (i.e., a specific menu item and its quantity).

- **Attributes:**
  - `id`: Unique identifier
  - `orderId`: Reference to the associated `Order`
  - `menuItemId`: Reference to the selected `MenuItem`
  - `quantity`: Number of units of the menu item ordered
  - `price`: Price per unit at the time of ordering
- **Relationships:**
  - An **OrderDetail** belongs to one **Order** and one **MenuItem**.
  - Multiple **OrderDetails** make up an **Order**.

------

### Entity Relationships Summary

- A **User** ➝ can have many **Orders**.

- An **Order** ➝ belongs to one **User**, and has many **OrderDetails**.

- A **MenuItem** ➝ can appear in many **OrderDetails**.

- An **OrderDetail** ➝ connects one **Order** to one **MenuItem** with quantity and price.

  
### Scenario
Your project flow should go through the following steps
#### Step 1: Login / Sign-Up:
The customer creates a new account or logs into an existing one to access the restaurant's features.
* Accounts should contain a **Username** and a **Password**
  * Having a more detailed account setup is bonus
* Passwords should be hashed

#### Step 2: Display Menu:
A menu is displayed to the customer consisting of all the foods available in the restaurant.
* Menu Items should include " Food Name + Price "
  * Including the used ingredients is bonus

#### Step 3: Order Food:
The customer selects desired items and adds them to the cart for checkout.
* The customer should be able to add or delete selected foods from their order.

#### Step 4: Checkout
After the order is placed, a detailed receipt is shown with itemized costs and confirmation of the transaction.
* The receipt has to have the following information:
  1. Names of the ordered food items
  2. Quantity of each item
  3. Price per unit of each item
  4. Total price for each item (unit price × quantity)
  5. Total price of the order
* After the confirmation, the order should be saved to the database

### UI
pages

## Bonus

Category, Menu Item Flow, UI, Viewing order history, Picture

## Evaluation 📃

Your submission will be evaluated based on the following criteria:

- **Database Design and Integration (PostgreSQL)**
   You must design your database schema properly with clear relationships between entities (as explained in the Logic section). The application should interact with the PostgreSQL database to:
  - Store and retrieve user data
  - Save and update orders and their details
  - Load menu items dynamically
- **User Interface (JavaFX)**
   The UI should be clean, user-friendly, and visually organized. Use JavaFX effectively to implement different screens (e.g., login/signup, ordering, order summary).
- **Code Quality and Structure**
   Your code should be clean, modular, and follow object-oriented principles. Use appropriate naming, structure your classes and packages well, and avoid redundant or duplicate code.

------

> ⚠️ **Important:** Use of ChatGPT or any AI-based generative tools for completing any part of the assignment is **strictly prohibited**. Detection will result in an automatic **score of 0** with no warnings.

## Submission

The deadline for submitting your code is **Tuesday, June 4 (14th of Khordad)**.  
Good luck! 

