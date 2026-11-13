# SC2002 Restaurant Rush


## Project Structure

```text
restaurant-rush/
├── README.md
├── .gitignore
├── docs/
│   ├── uml/
│   ├── report/
│   ├── test-notes/
│   └── declarations/
├── src/restaurantrush/
    ├── Main.java
    │
    ├── boundary/                      
    │   ├── GameCLI.java                # main menu, collects manager choices
    │   ├── StateDisplay.java           # start-of-turn + end-of-turn displays
    │   └── InputReader.java            # input validation, re-prompt on bad input
    │
    ├── control/                        
    │   ├── GameController.java         # runs the 18 turns and the six steps
    │   ├── GameConfig.java             # base setting: turns, tables, arrival turns
    │   ├── ArrivalScheduler.java       # who arrives on turns 1, 4, 7, 10, 13, 16
    │   └── ObjectiveChecker.java       # victory/defeat check after turn 18
    │
    ├── entity/
    │   ├── customer/                  
    │   │   ├── Customer.java           # abstract: satisfaction, state, wait loss
    │   │   ├── RegularCustomer.java
    │   │   ├── CustomerState.java      # enum: WAITING, SEATED, ORDERED, EATING, READY_TO_PAY, PAID, LEFT
    │   │   ├── VIPCustomer.java        # (S2)
    │   │   └── CriticCustomer.java     # (S2)
    │   │
    │   ├── staff/                      
    │   │   ├── Staff.java              # abstract
    │   │   ├── Waiter.java
    │   │   ├── WaiterTask.java         # enum: SEAT, TAKE_ORDER, SERVE, WAIT
    │   │   ├── Chef.java
    │   │   ├── Cashier.java
    │   │   └── Host.java               # (S3)
    │   │
    │   ├── order/                      
    │   │   ├── Order.java
    │   │   └── OrderStatus.java        # enum: PLACED, PREPARING, READY, SERVED, PAID, CANCELLED
    │   │
    │   ├── menu/                       
    │   │   ├── MenuItem.java           # name, price in cents, prep units
    │   │   ├── Menu.java               # Salad, Burger, Pasta
    │   │   └── ComboMeal.java          # (S3)
    │   │
    │   └── restaurant/                 
    │       ├── Restaurant.java         # holds tables, queue, staff, revenue
    │       ├── Table.java
    │       └── WaitingQueue.java
    │
    ├── pricing/                        
    │   ├── PricingPolicy.java          # interface: calculate bill
    │   ├── StandardPricing.java
    │   └── HappyHourPolicy.java        # (S3)
    │
    └── util/                           
        └── Money.java                  # integer cents, formats as dollars
```

### Stages

1. **Stage 1 (`stage-1`)**: Regular customers, the base menu, Waiter, Chef, Cashier, tables, orders, queue, full turn flow, and victory/defeat. Include reproducible victory and defeat scenarios.
2. **Stage 2 (`stage-2`)**: Add VIP customers (5 satisfaction loss per waiting turn and 10% discount) and Critic customers (12 satisfaction loss and a documented distinct preference or service reaction). Dispatch customer-specific behavior through customer objects, not type-check chains in the turn controller. A reproducible run must include both types.
3. **Stage 3 (`stage-3`)**: Add a Host who can seat one queued customer per turn in addition to the Waiter; `ComboMeal` made from two menu items, priced at 90% of their combined price and rounded to cents; and Happy Hour, activated once per game for the current and following turn. Happy Hour discounts new orders by 20% and restores up to 15 satisfaction to customers still waiting for a table or food at the start of each active turn. Apply the VIP discount before Happy Hour and lock the price when an order is taken.

### Stage History (Fill In During Development)
 
| Transition         | New files                           | Existing files changed or removed and why      | Commit or compare link |
| ------------------ | ----------------------------------- | ---------------------------------------------- | ---------------------- |
| Stage 1 to Stage 2 | VIPCustomer.java CriticCustomer.Java|Changed arrival schedule to include new Customer| Stage-2 Tag            |
| Stage 2 to Stage 3 | Host.java ComboMeal.java HappyHourPolicy.java| Restaurant.java to hold Host and Menu.java for combos| Stage-3 Tag            |

Repository URL: https://github.com/D313T30ne/SC2002_Restaurant_Rush.git
Video link: TBD
Video timestamps: TBD
