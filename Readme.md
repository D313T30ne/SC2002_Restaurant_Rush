# SC2002 Restaurant Rush

A turn-based restaurant management game for the command line, written in Java. The manager has 18 turns to seat customers, take orders, cook, serve and collect payment, and has to keep the average satisfaction high enough to win.

Group 9, lab SCE3.

## Build and Run

You need a JDK (tested with JDK 21). Run these commands from the project root.

**macOS / Linux**

```bash
javac -d out $(find src -name "*.java")
java -cp out restaurantrush.Main
```

**Windows (Command Prompt)**

```bat
dir /s /b src\*.java > sources.txt
javac -d out @sources.txt
java -cp out restaurantrush.Main
```

Or open the folder in IntelliJ IDEA and run `Main.java`.

### Reproducible runs

The game reads one choice per line, so the exact victory and defeat inputs in the report (Section 4) can be played back. Put the comma-separated inputs in a file called `inputs.txt`, then run (macOS / Linux):

```bash
tr -d ' ' < inputs.txt | tr ',' '\n' | java -cp out restaurantrush.Main
```

On Windows, type or paste the inputs one per line into the running game.

`master` holds the Stage 3 code. To run an earlier stage, check out its tag first (`git checkout stage-1` or `git checkout stage-2`), build, run, and then go back with `git checkout master`.

## Project Structure

```text
SC2002_Restaurant_Rush/
├── Readme.md
├── DESIGN.md                           # key abstractions, SOLID, trade-offs, player choices
├── .gitignore
├── SC2002_Restaurant_Rush.iml          # IntelliJ project files (.idea/ too)
├── docs/
│   ├── SC2002_Restaurant_Rush_Group_Assignment.docx   # the assignment brief
│   ├── Stage1-ToDo.txt
│   ├── stage-1-class.png
│   ├── uml/                            # class diagrams (Stage 1 to 3) and the Stage 3 sequence diagram
│   └── declarations/                   # declarations on the use of GAI
└── src/restaurantrush/
    ├── Main.java                       # entry point
    │
    ├── boundary/
    │   ├── GameCLI.java                # menus, prompts, collects the manager's choices
    │   ├── StateDisplay.java           # start-of-turn and end-of-turn displays
    │   └── InputReader.java            # input validation, re-prompts on bad input
    │
    ├── control/
    │   ├── GameController.java         # runs the 18 turns and the six steps of each turn
    │   ├── GameConfig.java             # base setting: turns, tables, arrival plan
    │   ├── ArrivalScheduler.java       # who arrives on turns 1, 4, 7, 10, 13 and 16
    │   ├── ObjectiveChecker.java       # victory or defeat check after turn 18
    │   └── GameResult.java             # enum: VICTORY, DEFEAT
    │
    ├── entity/
    │   ├── customer/
    │   │   ├── Customer.java           # abstract: satisfaction, state, waiting loss, bill
    │   │   ├── RegularCustomer.java
    │   │   ├── VIPCustomer.java        # (Stage 2) 5 loss per waiting turn, 10% discount
    │   │   ├── CriticCustomer.java     # (Stage 2) 12 loss per waiting turn
    │   │   └── CustomerState.java      # enum of the customer lifecycle
    │   │
    │   ├── staff/
    │   │   ├── Staff.java              # abstract: name, startTurn(), hasActed(), getRole()
    │   │   ├── Waiter.java
    │   │   ├── WaiterTask.java         # enum of the Waiter's tasks
    │   │   ├── Chef.java
    │   │   ├── Cashier.java
    │   │   ├── Host.java               # (Stage 3) seats one queued customer per turn
    │   │   └── ActionResult.java       # success or failure of a staff action, with a reason
    │   │
    │   ├── order/
    │   │   ├── Order.java
    │   │   └── OrderStatus.java        # enum: PLACED, PREPARING, READY, SERVED, PAID, CANCELLED
    │   │
    │   ├── menu/
    │   │   ├── MenuItem.java           # name, price in cents, preparation units
    │   │   ├── Menu.java               # Salad, Burger, Pasta
    │   │   └── ComboMeal.java          # (Stage 3) two menu items at 90% of their combined price
    │   │
    │   └── restaurant/
    │       ├── Restaurant.java         # holds tables, queue, staff, orders, revenue
    │       ├── Table.java
    │       └── WaitingQueue.java
    │
    ├── pricing/
    │   ├── PricingPolicy.java          # interface: price for an order
    │   ├── StandardPricing.java
    │   └── HappyHourPolicy.java        # (Stage 3) 20% off, wraps another PricingPolicy
    │
    └── util/
        └── Money.java                  # integer cents, formats as dollars
```

## Stages

1. **Stage 1 (`stage-1`)**: Regular customers, the base menu, Waiter, Chef, Cashier, tables, orders, queue, full turn flow, and victory/defeat. Includes reproducible victory and defeat scenarios.
2. **Stage 2 (`stage-2`)**: Adds VIP customers (5 satisfaction loss per waiting turn and 10% discount) and Critic customers (12 satisfaction loss and a distinct preference). Customer-specific behaviour is dispatched through the customer objects, not type-check chains in the turn controller.
3. **Stage 3 (`stage-3`)**: Adds a Host who can seat one queued customer per turn in addition to the Waiter; `ComboMeal`, made from two menu items and priced at 90% of their combined price, rounded to cents; and Happy Hour, activated once per game for the current and following turn. Happy Hour discounts new orders by 20% and restores up to 15 satisfaction to customers still waiting for a table or food at the start of each active turn. The VIP discount is applied before Happy Hour, and the price is locked when an order is taken.

## Stage History

| Transition | New files | Existing files changed or removed, and why | Commit or compare link |
| ---------- | --------- | ------------------------------------------ | ---------------------- |
| Stage 1 to Stage 2 | `VIPCustomer.java`, `CriticCustomer.java` | `GameConfig.java`: the arrival plan now includes VIP and Critic customers. | [stage-1...stage-2](https://github.com/D313T30ne/SC2002_Restaurant_Rush/compare/stage-1...stage-2) |
| Stage 2 to Stage 3 | `Host.java`, `ComboMeal.java`, `HappyHourPolicy.java` | `Restaurant.java`: holds the Host. `Menu.java`: holds the combo meals. `GameController.java`: adds the Host step and triggers Happy Hour recovery. | [stage-2...stage-3](https://github.com/D313T30ne/SC2002_Restaurant_Rush/compare/stage-2...stage-3) |

## Documentation

- **Class diagrams:** `docs/uml/` (Stage 1, Stage 2 and Stage 3).
- **Sequence diagram:** `docs/uml/sequence_diagram_stage_3.png`, with an editable `sequence_diagram_stage_3.drawio.svg`.
- **Design discussion:** [`DESIGN.md`](DESIGN.md).
- **Declarations:** `docs/declarations/`.

## Links

Repository URL: https://github.com/D313T30ne/SC2002_Restaurant_Rush.git

Video link: TBD

Video timestamps:

| Member | Segment | Timestamp |
| ------ | ------- | --------- |
| TBD | TBD | TBD |
