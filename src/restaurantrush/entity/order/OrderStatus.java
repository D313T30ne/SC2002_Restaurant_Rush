package restaurantrush.entity.order;

public enum OrderStatus {
    PLACED,     // Waiter has taken the order, no preparation done yet
    PREPARING,  // Chef has completed at least one unit, but not all of them
    READY,      // All preparation units done, can be served from the next turn
    SERVED,     // Waiter has delivered it, the customer is eating
    PAID,       // Cashier has collected payment
    CANCELLED   // Customer left before paying, order abandoned
}

//The normal path is PLACED → PREPARING → READY → SERVED → PAID

//A 1 unit prep time item goes straight from PLACED to READY, skipping PREPARING

//CANCELLED can come from PLACED, PREPARING or READY

// PAID and CANCELLED are end states. Revenue counts PAID only.