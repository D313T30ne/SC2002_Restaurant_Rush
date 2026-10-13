# SC2002 Restaurant Rush

A Java command-line, turn-based restaurant management game for the SC2002 group assignment. The manager prioritizes seating, orders, kitchen work, and serving while working with limited staff.

## Current Status

The repository currently contains package and class skeletons only. `Main` has no `main` method, the controller and domain classes have no behavior, and the test directories are empty. There is no build tool or test framework configured, and the `stage-1`, `stage-2`, and `stage-3` Git tags have not been created. The project is not playable yet; add runnable build instructions and test results when implementation is in place.

## Project Structure

```text
src/restaurantrush/
	Main.java                  Application entry point
	boundary/                  CLI input and state display
	control/                   Turn orchestration, configuration, arrival and result rules
	entity/customer/           Customer types and customer state
	entity/menu/               Menu items and combo meals
	entity/order/              Orders and order status
	entity/restaurant/         Restaurant, tables and waiting queue
	entity/staff/              Staff roles and waiter tasks
	pricing/                   Pricing policies
	util/                      Shared utilities such as money handling
test/
	restaurantrush/             Tests, organized by production package
docs/
	declarations/              Required signed declarations
	report/                    Final PDF report and design evidence
	test-notes/                Reproducible test scenarios and results
	uml/                       Class and sequence diagrams
```

The package layout is a sensible starting point for parallel work: it separates the CLI, game coordination, domain objects, and pricing. It does not by itself prevent merge conflicts. In particular, `GameController`, `GameConfig`, shared entity APIs, and `Main` will be integration hotspots until their contracts are agreed.

## Parallel Development

- Assign one primary owner per package or set of files. Avoid having multiple contributors edit `GameController` or the same entity class at the same time.
- Agree on public constructors and method signatures for cross-package calls before splitting work. Keep a short shared API/design note or use the UML class diagram as the contract.
- Keep game rules in control/domain classes; keep `boundary` limited to collecting manager choices and displaying state.
- Develop and test a feature on a branch, then integrate it through a small pull request. Coordinate any shared API change with its callers and update tests in the same change.
- Keep tests under the matching `test/restaurantrush` package. Add a test framework/build tool before relying on automated test commands.

## Required Game Scope

### Base Setting

- 18 turns, 2 tables, one Waiter (one task per turn), one Chef (one preparation unit per turn), and one Cashier (one payment per turn).
- One arrival at the start of turns 1, 4, 7, 10, 13, and 16.
- Menu: Salad ($9.00, 1 preparation unit), Burger ($15.00, 1), and Pasta ($18.00, 2).
- Victory after turn 18 requires at least 4 paid customers, revenue of at least $45, and average paid-customer satisfaction of at least 60. The game always uses all 18 turns.
- Customers choose their own food by a documented deterministic rule or seeded randomness. Store money as integer cents where fractional prices are possible.

### Turn Flow

At each turn, display the turn, revenue, average satisfaction of paid customers, queue, occupied tables, customer satisfaction, order status, and available staff tasks. Resolve arrivals, manager choices, waiter work before chef work, serving, eating and payment, then satisfaction loss and abandonment. Invalid choices must explain the problem and allow another choice without consuming the staff task. Revenue includes paid orders only.

### Stages

1. **Stage 1 (`stage-1`)**: Regular customers, the base menu, Waiter, Chef, Cashier, tables, orders, queue, full turn flow, and victory/defeat. Include reproducible victory and defeat scenarios in `docs/test-notes/`.
2. **Stage 2 (`stage-2`)**: Add VIP customers (5 satisfaction loss per waiting turn and 10% discount) and Critic customers (12 satisfaction loss and a documented distinct preference or service reaction). Dispatch customer-specific behavior through customer objects, not type-check chains in the turn controller. A reproducible run must include both types.
3. **Stage 3 (`stage-3`)**: Add a Host who can seat one queued customer per turn in addition to the Waiter; `ComboMeal` made from two menu items, priced at 90% of their combined price and rounded to cents; and Happy Hour, activated once per game for the current and following turn. Happy Hour discounts new orders by 20% and restores up to 15 satisfaction to customers still waiting for a table or food at the start of each active turn. Apply the VIP discount before Happy Hour and lock the price when an order is taken.

Each stage must be a separate runnable version built on the previous stage, with no later-stage features included in earlier tags. Explain any changes to earlier classes when documenting stage transitions.

## Build, Run, and Test

No build system, test framework, or runnable game is configured yet. `Main` must first gain an entry point and the application must be implemented. Then document the supported JDK, exact compile/run commands, test command, and expected results here. The required `stage-1` victory and defeat runs belong in `docs/test-notes/`.

## Design and Submission Checklist

- Add a final UML class diagram matching Stage 3, including visibility, inheritance, associations/multiplicities, and examples of encapsulation, polymorphism, and composition.
- Add a sequence diagram showing manager choices, arrival, order, waiter/chef work, READY to SERVED, eating, payment, result checking, and at least one Stage 2 or Stage 3 behavior.
- In the PDF report, explain key responsibilities, applicable design principles, trade-offs, an alternative design, and how choices affect the result.
- Submit source, this README, the PDF report, the recorded video or accessible link, and the required declarations.
- Record the repository URL and video timestamps in this README when available. The video should show launch, constrained staff choices, serving and payment, VIP/Critic behavior, a Stage 3 feature, victory and defeat, tags, and diagrams.

### Stage History (Fill In During Development)

| Transition         | New files | Existing files changed or removed and why | Commit or compare link |
| ------------------ | --------- | ----------------------------------------- | ---------------------- |
| Stage 1 to Stage 2 | TBD       | TBD                                       | TBD                    |
| Stage 2 to Stage 3 | TBD       | TBD                                       | TBD                    |

Repository URL: TBD
Video link: TBD
Video timestamps: TBD
