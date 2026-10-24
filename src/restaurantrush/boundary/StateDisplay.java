package restaurantrush.boundary;

import restaurantrush.entity.customer.Customer;
import restaurantrush.entity.restaurant.Restaurant;
import restaurantrush.entity.restaurant.Table;
import restaurantrush.entity.restaurant.WaitingQueue;

// TODO: waiting for Zeph's Restaurant class methods
public class StateDisplay {
    public void renderStartOfTurn(int turn, Restaurant r) {
        System.out.println("\n========================================");
        System.out.println("               Start of turn: " + turn               );
        System.out.println("========================================");
        System.out.println("The restaurant is now open.");
        System.out.println("Revenue       : " + r.getRevenue() / 100);

        if (r.getPaidCount() == 0) {
            System.out.println("Average satisfaction: N/A");
        } else {
            System.out.println("Average satisfaction: " + r.getAverageSatisfaction());
        }

        System.out.println("Waiting Queue: ");
        WaitingQueue wq = r.getWaitingQueue();
        if (wq.isEmpty()) {
            System.out.println("(empty)");
        } else {
            for (Customer c : wq.getCustomers()) {
                System.out.println("id: " + c.getId() + " Satisfaction: " + c.getSatisfaction());
            }
        }

        System.out.println("Occupied Tables: ");
        for (Table table : r.getTables()) {
            if (!table.isFree()) {
                System.out.println("Table No. " + table.getNumber());
            }
        }
        System.out.println("Staff Actions Available: ");
        System.out.println("Waiter: " + r.getWaiterActions());
        System.out.println("Chef: " + r.getChefActions());
        System.out.println("Cashier: " + r.getCashierActions());
    }

    public void renderEndOfTurn(int turn, Restaurant r) {
        System.out.println("\n========================================");
        System.out.println("         End of turn: " + turn + "         ");
        System.out.println("========================================");
        System.out.println("Results:\n");
        System.out.println("Served Count        : " + r.getServedCount());
        System.out.println("Unhappy Departures  : " + r.getUnhappyDepartures());
        System.out.println("Revenue             : " + r.getRevenue() / 100);

        if (r.getPaidCount() == 0) {
            System.out.println("Average satisfaction: N/A");
        } else {
            System.out.println("Average satisfaction: " + r.getAverageSatisfaction());
        }
        System.out.printf("Turns used          : " + turn);
    }

    public void renderFinal(GameResult res, Restaurant r) {
        switch (res) {
            case VICTORY:
                System.out.println("Victory");
            case DEFEAT:
                System.out.println("Defeat");
        }
    }
}
