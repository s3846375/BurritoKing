package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CreditRedeemaleTest {

    /**
     * Test the redeemable amount when subtotal is lager than the available credits to redeem
     **/
    @Test
    void subtotalLargerRedeemable() {
        UserVIP vip = new UserVIP("User1", "1111", "Captain", "Morgan", "1111@gmail.com", 350);
        assertEquals(3, vip.getRedeemableAmount(48));
    }

    /**
     * Test the redeemable amount when subtotal is lesser than the available credits to redeem
     **/
    @Test
    void subtotalLesserRedeemable() {
        UserVIP vip = new UserVIP("User2", "2222", "Jack", "Daniels", "2222@gmail.com", 2200);
        assertEquals(15, vip.getRedeemableAmount(15));
    }


    /**
     * Test the redeemable amount when subtotal value has decimal
     **/
    @Test
    void subtotalDecimalRedeemable() {
        UserVIP vip1 = new UserVIP("User3", "3333", "Jim", "Beam", "3333@gmail.com", 3000);
        UserVIP vip2 = new UserVIP("User4", "4444", "Gin", "Tonic", "4444@gmail.com", 280);
        assertEquals(27, vip1.getRedeemableAmount(27.5));
        assertEquals(2, vip2.getRedeemableAmount(27.5));
    }

}