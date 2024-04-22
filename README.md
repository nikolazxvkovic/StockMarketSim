<br />
<p align="center">
  <h1 align="center">Stock Market Simulation</h1>

  <p align="center">
    A simulation of a simplified stock market.
  </p>

## Table of Contents

* [About the Project](#about-the-project)
  * [Built With](#built-with)
* [Getting Started](#getting-started)
  * [Prerequisites](#prerequisites)
  * [Installation](#installation)
  * [Running](#running)
* [Diagram](#diagram)
* [Modules](#modules)
* [Notes](#notes)
* [Evaluation](#evaluation)
* [Extras](#extras)

## About The Project

A simulation of a simplified stock market.

## Getting Started



### Prerequisites

* [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) or higher
* [Maven 3.6](https://maven.apache.org/download.cgi) or higher

### Installation

1. Navigate to the `stocks` directory
2. Clean and build the project using:
```sh
mvn clean compile
```

### Running
In order to start the code, run Main in the stock-application module, followed by running the Main class in the trader-application. Note that you can use MultiRun to ease the process. 

### Diagram

![Beautiful Image](/stocks/images/AOOP%20Assignment%203%20(1).jpg)

## Modules
Our 'Stonks' project consists of a number of modules such as:


1. **Command Module:**

The 'Command' module includes the main classes used for the Command design pattern. We use the Command interface from the said module to create commands in the message-queue module such as the MqPutCommand, MqStockCommand, MqTraderCommand and a number of others in the stock-application and trader-application modules. This interface contains a method that when overridden, it enables executing commands and paired with the Command Handler, we are able to register and execute said commands.

2. **Message Queue:**

The 'Message Queue' module holds implementations of message queues and it works well alongside the networking module, in order to be able to send and receive messages between Server(s) and Clients. This module also contains different implementations of message handlers, producers and consumers, it simplifies communication and increases performance.


3. **Networking:**

    This module holds implementations for server, clients and client handlers. The server is used to receive requests from the client and then the client handler is supposed to decide what to do with these messages. Essentially, the networking module aims to create a network with clients connected to it where communication takes place through messages.

4. **Stock Application:**

    This module acts as the stock exchange itself, handling incoming and outgoing messages. It uses a networked message queue and continuously polls the message queue for Buy Limit/Sell Limit Orders from the trader application otherwise known as the client. The Stock application sends back information periodically to the client with the updated information regarding the stocks and the traders themselves. A role of the price in a limit order is defined by:
  * For "buy" orders, the maximum share price the trader wishes to buy their shares at.
  * For "sell" orders,  the minimum share price a trader wishes to sell their shares for.
 
    When a new order comes in, it will first try to match it against existing orders. For "sell" orders, this means finding the buy order with the highest price (provided   that this price is higher than the limit price). For buy orders, this means finding the sell order with the lowest price (provided that this price is lower than the limit price). The stock app will do this until the order is resolved. When no matching orders can be found (anymore), the buy/sell order should be added to the bids/asks. Each time an order is (partially) resolved, the transaction is added to the transaction history of the trader that placed the order. A transaction contains information on how many of which stock was traded for how much.

   The Stock application keeps track of the following:
    * Information about the stocks.
    * Information about the orders (bids and asks).
    * Information about each trader

5. **Trader Application:**

    The trader application is always listening for messages from the stock application about information of all the stocks. Whenever a relevant update is made in the Stock application the trader information is updated as well. A stock order that a trader bot (client) makes is determined by a trading strategy. Our trading strategy is:

    * Choose randomly between buying and selling.
    * Choose a random stock.
    * Choose a random amount.
   
    The limit price can be determined by slightly randomizing the current stock price. In the case of buy orders, traders should put the price slightly higher than the current price. In the case of sell orders, traders should put the price slightly lower than the current price.
    The traders cannot sell stocks that they do not own. When a trader makes a sell order, the shares will stay in their portfolio until that order is resolved and the trader is informed. They should not be able to sell those same shares again while the order has not been resolved yet. Also, a trader bot (client) should not be able to buy stocks if it does not have the funds to do so.


6. **Stock Market UI:**
    The stock market UI module is the implementation for the view. We use it in order to display the stock information and trader information. It gets updated everytime a transaction is complete. The module determines all the row and column headers that are going to be displayed in the view. In essence, the User Interface is a Database Table displaying the data from the stock application and the trader application.
 


## Design
1. **Command Pattern:**
  The command pattern is used throughout our program. We implement the initial command pattern in the Command Module. We then use different commands in the following modules:
  * The Stock Application (Buy/Sell Command)
  * Message Queue (MqPut/MqStock/MqTrade Command)
  The reason we use the Command Pattern is that it reduces coupling between the Client and the Server. It allows use to keep the implementation of them as separate as possible. Another advantage is that it adds scalability. We can add new commands quite easily which means that we have easy extendability. Let's take an example related to the program of this assignment: if we were to add different kinds of orders, not just limit orders, the command pattern would allow us to add more commands to process these new orders that the traders want to create.
  

2. **Factory Method Pattern:**
  The Factory Method is used together with the Command Pattern. It allows use to use the Command Handler and create the commands we need. It decouples classes and allows us to facilitate the creation of our relatively complex commands such as the Buy and Selling Commands. Using factories also allows us to have a simpler way of creating more commands and generally being nicer to read.

3. **Facade Pattern:**
  The Facade Pattern is used throughout the project. Specifically, in the Main classes of the Stock Application and Trader Applications. We store the implementation in other classes, hiding most of it from the user. By doing so, we eliminate circular dependencies which allows us not to have the trader application and the stock application depend on each other.

4. **Strategy Pattern:**
  The Strategy Pattern is used in our Trader Application. The reason we use the strategy pattern is that, even though we are only required to create one type of strategy for building buy and sell orders, we want to be able to create more at any point in time with ease. Therefore, we make use of the strategy pattern where they have different behavior, but a similar structure. The clients, otherwise known as the Trader Bots, should not know that we do this pattern. In the future, it allows us to easily add more strategies and therefore allows extendability.


<!--
List all the design patterns you used in your program. For every pattern, describe the following:
- Where it is used in your application.
- What benefit it provides in your application. Try to be specific here. For example, don't just mention a pattern improves maintainability, but explain in what way it does so.
-->

## Evaluation

In terms of stability, our code implementation is relatively working. Of course, there are parts that work/behave or are designed better than others, which is to be expected in a project of this size. Our initial focus was on writing error-free, easy-to-read code, spending large amounts of time on small details, and it has shifted throughout the assignment to completing as many of the requirements as possible and expending them as much as the time allowed us to. Some of the parts that do work well are the strategy, the commands, the loader and resolving incoming orders, where we went through multiple implementations for each one of them before settling on one that best suited our needs. One of the main issues we ran into was connecting the two applications: the stock and the trader application, where we had multiple failed attempts of allowing them to communicate with each other without them depending on one another, but have found a way to keep them decoupled. However, it only works to some point, so there are certainly things we would like to fix and improve. The implementation of the view exists but due to the fact that the two applications struggle to work together, we were unfortunately not able to put it to use. In terms of testing, we have written a few simple tests which allowed us to understand what was not behaving as expected. Another thing we wished we could have done was allowing the orders to be sent at a chosen interval of time. In conclusion, while there are numerous things we would've wished to have done or done differently, we have put a lot of time and effort into the project and it has also been a valuable learning experience.

<!--
Discuss the stability of your implementation. What works well? Are there any bugs? Is everything tested properly? Are there still features that have not been implemented? Also, if you had the time, what improvements would you make to your implementation? Are there things which you would have done completely differently? Try to aim for at least 250 words.
-->

___
