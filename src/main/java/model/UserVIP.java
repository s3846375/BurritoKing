package model;

public class UserVIP extends User {
    private String email;
    private int creditPoint;

    public UserVIP(String username, String password, String firstName, String lastName, String email, int creditPoint) {
        super(username, password, firstName, lastName);
        this.email = email;
        this.creditPoint = creditPoint;
    }

    public int getCreditPoint() {
        return creditPoint;
    }

    /**
     * Round down the subtotal and transferred credit to the nearest integer.
     * Return the minimum value as the redeemable amount.
     */
    public int getRedeemableAmount(double subtotal) {
        double roundSubtotal = Math.floor(subtotal);
        double creditAmount = Math.floor(getCreditPoint() / 100);
        return (int) Math.min(roundSubtotal, creditAmount);
    }

    public void updateCredit(int redeemAmount, double finalPayment) {
        this.creditPoint += (int)Math.floor(finalPayment) - redeemAmount * 100;
    }

    @Override
    public String toString() {
        return getUsername()+ " " + getPassword() + " " + getFirstName() + " " + getLastName() + email + " " + getCreditPoint();
    }
}
