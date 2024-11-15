package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UpdateCreditTest {

    /**
     * Test the update credit points after redeemed $3 and final payment is $45.
     **/
    @Test
    void updateCreditTest1() {
        UserVIP vip = new UserVIP("User1", "1111", "Captain", "Morgan", "1111@gmail.com", 350);
        int redeemAmount = 3;
        double finalPayment = 45;
        vip.updateCredit(redeemAmount, finalPayment);
        assertEquals(95, vip.getCreditPoint());
    }

    /**
     * Test the update credit points after no redeemed credit.
     **/
    @Test
    void updateCreditTest2() {
        UserVIP vip = new UserVIP("User1", "1111", "Captain", "Morgan", "1111@gmail.com", 0);
        int redeemAmount = 0;
        double finalPayment = 45;
        vip.updateCredit(redeemAmount, finalPayment);
        assertEquals(45, vip.getCreditPoint());
    }

    /**
     * Test the update credit points after redeemed credit $2 and final payment is $27.5.
     **/
    @Test
    void updateCreditTest3() {
        UserVIP vip = new UserVIP("User1", "1111", "Captain", "Morgan", "1111@gmail.com", 220);
        int redeemAmount = 2;
        double finalPayment = 27.5;
        vip.updateCredit(redeemAmount, finalPayment);
        assertEquals(47, vip.getCreditPoint());
    }

    /**
     * Test the update credit points after redeemed same amount as subtotal (final payment $0).
     **/
    @Test
    void updateCreditTest4() {
        UserVIP vip = new UserVIP("User1", "1111", "Captain", "Morgan", "1111@gmail.com", 2000);
        int redeemAmount = 15;
        double finalPayment = 0.0;
        vip.updateCredit(redeemAmount, finalPayment);
        assertEquals(500, vip.getCreditPoint());
    }

    /**
     * Test the update credit points after redeemed all credits and final payment is $35.5.
     **/
    @Test
    void updateCreditTest5() {
        UserVIP vip = new UserVIP("User1", "1111", "Captain", "Morgan", "1111@gmail.com", 300);
        int redeemAmount = 3;
        double finalPayment = 35.5;
        vip.updateCredit(redeemAmount, finalPayment);
        assertEquals(35, vip.getCreditPoint());
    }
}