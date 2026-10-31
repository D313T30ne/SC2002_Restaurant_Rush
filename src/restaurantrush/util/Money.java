package restaurantrush.util;

public class Money implements Comparable<Money> {
    private long cents;

    // To instantiate money, use Money.ofCents(long) instead.
    private Money(long cents) {
        this.cents = cents;
    }

    public static Money ofCents(long cents) {
        return new Money(cents);
    }

    public Money add(Money other) {
        return new Money(this.cents + other.cents);
    }

    // Comparable<Money> interface
    public int compareTo(Money other) {
        return Long.compare(this.cents, other.cents);
    }

    public String toString() {
        return String.format("$%.2f", this.cents / 100.0);
    }

    public Money applyPercentDiscount(int percent) {
        if (percent < 0 || percent > 100) {
            throw new IllegalArgumentException("Percent must be 0-100: " + percent);
        }
        long discounted = (cents * (100 - percent) + 50) / 100;
        return new Money(discounted);
    }
}
