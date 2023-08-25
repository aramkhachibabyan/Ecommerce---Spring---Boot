package com.smartCode.ecommerce.util.constants;

public enum OrderStatus {
    PENDING("Pending"),
    ACCEPTED("Accepted"),
    COLLECTED("Collected"),
    ON_THE_WAY("On the way"),
    DELIVERED("Delivered"),
    CANCELED("Canceled");

    private final String name;

    OrderStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
