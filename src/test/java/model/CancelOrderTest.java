package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CancelOrderTest {


    /**
     * Test for canceling an order that is "await collection"
     **/
    @Test
    void cancelAwaitOrder() {
        OrderDetail orderDetail = new OrderDetail("User1", 1, "01/12/2023", "11:00", 15, "Burrito x1, Fries x2", StatusEnum.AWAIT_COLLECTION, 9);
        assertEquals(true, orderDetail.cancelOrder());
    }

    /**
     * Test for canceling an order that is "collected"
     **/
    @Test
    void cancelCollectOrder() {
        OrderDetail orderDetail = new OrderDetail("User1", 2, "15/11/2024", "22:00", 50.5, "Burrito x3, Fries x1, Soda x1", StatusEnum.COLLECTED, 18);
        assertEquals(false, orderDetail.cancelOrder());
    }

    /**
     * Test for canceling an order that is "canceled"
     **/
    @Test
    void cancelCanceledOrder() {
        OrderDetail oorderDetail = new OrderDetail("User1", 3, "23/09/2024", "12:30", 43.5, "Burrito x1, Fries x5, Meal x2", StatusEnum.CANCELLED, 18);
        assertEquals(false, oorderDetail.cancelOrder());
    }

}