# Design discussion

## A table of key abstractions and their responsibilities.
| Key Abstraction | Type | Role | Responsibility |
| --------------- | ---- | ----- | -------------- |
| GameCLI | Class | Boundary | Provides the interface between the user and the system. Renders prompts and receives user input. |
| GameController | Class | Control | Runs the core business logic of the game flow. It processes user actions and modifies the restaurant state. |
| Restaurant | Class | Entity | An object that stores all important data within a single playthrough, such as the Table, WaitingQueue, Order, revenue, satisfaction and customer statistics. |
| PricingPolicy | Interface | Control | An interface that implements the **Strategy Pattern** to provide encapsulation for different pricing policies such as VIPPolicy, ComboMealPolicy, and HappyHourPolicy. |
| Money | Class | Entity | Serves as a "wallet" for the restaurant to provide abstraction for adding, comparing and printing monetary values. |
| Menu | Class | Entity | Manages the collection of MenuItems. It encapsulates the list by restricting direct modification of the dishes offered in the restaurant. |

## SRP, OCP, LSP, ISP and DIP, only where genuinely reflected in your code.
| SOLID principle applied | Explanation |
| ----------------------- | ----------- |
| SRP | GameCLI performs the printing of UI, while GameController runs the game flow. All classes stick to one responsiblity to reduce code clutter. |
| OCP | PricingPolicy interface is used in Waiter class to determine the correct pricing for different customers or scenarios. By creating abstraction using a `priceFor()` method, the application is open to extension by adding more pricing policies without modifying the Waiter class source code. |
| LSP | Between MenuItem and ComboMeal, the system manages the Menu using the polymorphic collection `List<MenuItem>`. Since ComboMeal extends MenuItem, the subtype ComboMeal is substitutable for a MenuItem without altering the correctness of the program. |

## Trade-offs, and one alternative design you considered.
### `Money` object versus `int cents` for revenue
Initially, we considered to use primitive int variables to represent the balance of the restaurant in cents. After consideration, we realised that making a Money class with a special custom method such as `toString()` leads to greater code reuseability since we no longer have to duplicate string formatting code in the GameCLI.

## How the player’s choices change a result.
### Customer Satisfaction
If a player seats too many customers without taking Waiter or Chef actions, the player may find it difficult to maintain the satisfaction level of the customers, leading to a lower average satisfaction.

### Revenue
The player should prioritise high value orders, determined by taking the highest Price / Preparation Units ratio orders first. This ensures that the restaurant receives the highest revenue in the smallest number of turns.

### Paid Customers
It takes minimally 4 turns from a seated customer to a paid customer. Since the game requires only 4 paid customers at the end of turn 18, it is possible to complete the game.