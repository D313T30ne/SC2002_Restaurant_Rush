# Design discussion

## A table of key abstractions and their responsibilities.
| Key Abstraction | Type | Role | Responsibility |
| --------------- | ---- | ----- | -------------- |
| GameCLI | Class | Boundary | Provides the interface between the user and the system. Renders prompts and receives user input. |
| GameController | Class | Control | Runs the core business logic of the game flow. It processes user actions and modifies the restaurant state. |
| Restaurant | Class | Entity | An object that stores all important data within a single playthrough, such as the Table, WaitingQueue, Order, revenue, satisfaction and customer statistics. |
| PricingPolicy | Interface | Control | An interface that implements the **Strategy Pattern** to provide encapsulation for different pricing policies such as StandardPricing, and HappyHourPolicy. |
| Money | Class | Entity | Serves as a "wallet" for the restaurant to provide abstraction for adding, comparing and printing monetary values. |
| Menu | Class | Entity | Manages the collection of MenuItems. It encapsulates the list by restricting direct modification of the dishes offered in the restaurant. |

## SRP, OCP, LSP, ISP and DIP, only where genuinely reflected in your code.
| SOLID principle applied | Explanation |
| ----------------------- | ----------- |
| SRP | GameCLI performs the printing of UI, while GameController runs the game flow. All classes stick to one responsiblity to reduce code clutter. |
| OCP | PricingPolicy interface is used in Waiter class to determine the correct pricing for different customers or scenarios. By creating abstraction using a `priceFor()` method, the application is open to extension by adding more pricing policies without modifying the Waiter class source code. |
| LSP | Between MenuItem and ComboMeal, the system manages the Menu using the polymorphic collection `List<MenuItem>`. Since ComboMeal extends MenuItem, the subtype ComboMeal is substitutable for a MenuItem without altering the correctness of the program. |
| DIP | The high-level module Waiter is responsible for taking an order and applying the appropriate pricing. It does not depend on low-level modules with specific mathematical implementations such as `StandardPricing` or `HappyHourPolicy`. Instead, it depends on the `PricingPolicy` interface, which is an abstraction of both pricing types. This ensures both high and low-level modules depend on a common abstraction. The result is **loose coupling**, allowing us to add or modify pricing strategies without breaking the Waiter class. |

## Trade-offs, and one alternative design you considered.
### `Money` object versus `int cents` for revenue (Flexibility vs Simplicity)
Initially, we considered to use primitive `int` variables to represent the balance of the restaurant in cents. This is the simple, performance efficient way as a running count of revenue can be recorded as a static variable.

After consideration, we realised that making a Money class with a special custom method such as `toString()` leads to greater code reusability since we no longer have to duplicate string formatting code in the GameCLI.

This led to a trade-off where using a `Money` class meant more code complexity and computational overhead compared to `int cents` , but promoted greater code reusability. Therefore, our team decided that the flexibility of having a `Money` class outweighed the slight performance gain of using primitive integers.

## How the player’s choices change a result.
### Customer Satisfaction
The player must decide whether to use Waiter to seat new customers, or to serve food to customers who are already waiting. If a player seats too many customers without taking Waiter or Chef actions, the player may find it difficult to maintain the satisfaction level of the customers, leading to a lower average satisfaction. If satisfaction reaches zero, unhappy departure is triggered, causing a loss in revenue as well.

If the Restaurant's average satisfaction of paid customers falls below 60 at the end of turn 18, the result is Defeat.

### Revenue
The player should prioritise high value orders, determined by taking the highest `Price / Preparation` Units ratio orders first. This ensures that the restaurant receives the highest revenue in the smallest number of turns.

If the player made poor choices, unhappy departures are triggered. If the Restaurant's revenue falls below $45 at the end of turn 18, the result is Defeat.

### Paid Customers
It takes minimally 4 turns from a seated customer to a paid customer. Since the game requires only 4 paid customers at the end of turn 18, it is possible to complete the game.

If the Restaurant had less than 4 paid customers at the end of turn 18, the result is Defeat.