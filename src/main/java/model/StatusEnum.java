package model;

public enum StatusEnum {
    AWAIT_COLLECTION("Await Collection"),
    COLLECTED("Collected"),
    CANCELLED("Cancelled");

    private final String displayName;

    StatusEnum(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }

    /**
     * Use to convert string to enum when importing data from database
     * **/
    public static StatusEnum fromString(String status) {
        switch (status) {
            case "Await Collection":
                return AWAIT_COLLECTION;
            case "Collected":
                return COLLECTED;
            case "Cancelled":
                return CANCELLED;
            default:
                throw new IllegalArgumentException("Unknown order status: " + status);
        }
    }
}
