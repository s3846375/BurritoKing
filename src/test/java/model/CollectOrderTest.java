package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CollectOrderTest {

    /**
     * Test for collecting an order that's on the same date and before prepare time
     **/
    @Test
    void collectBeforePreparetime() {
        OrderDetail orderDetail = new OrderDetail("User1", 4, "01/06/2024", "12:30", 43.5, "Burrito x1, Fries x5, Meal x2", StatusEnum.AWAIT_COLLECTION, 18);
        assertEquals(false, orderDetail.collectOrder("01/06/2024", "12:40"));
    }

    /**
     * Test for collecting an order that's on the same date and after prepare time
     **/
    @Test
    void collectAfterPreparetime() {
        OrderDetail orderDetail = new OrderDetail("User1", 4, "01/06/2024", "12:30", 43.5, "Burrito x1, Fries x5, Meal x2", StatusEnum.AWAIT_COLLECTION, 18);
        assertEquals(true, orderDetail.collectOrder("01/06/2024", "12:50"));
    }

    /**
     * Test for collecting an order that's on the same date and right on time
     **/
    @Test
    void collectRightOnTime() {
        OrderDetail orderDetail = new OrderDetail("User1", 4, "01/06/2024", "12:30", 43.5, "Burrito x1, Fries x5, Meal x2", StatusEnum.AWAIT_COLLECTION, 18);
        assertEquals(true, orderDetail.collectOrder("01/06/2024", "12:48"));
    }

    /**
     * Test for collecting an order on a date before order date
     **/
    @Test
    void collectBeforeOrderDate() {
        OrderDetail orderDetail = new OrderDetail("User1", 4, "01/06/2024", "12:30", 43.5, "Burrito x1, Fries x5, Meal x2", StatusEnum.AWAIT_COLLECTION, 18);
        assertEquals(false, orderDetail.collectOrder("02/05/2024", "12:40"));
    }

    /**
     * Test for collecting an order on a date after order date
     **/
    @Test
    void collectAfterOrderDate() {
        OrderDetail orderDetail = new OrderDetail("User1", 4, "01/06/2024", "12:30", 43.5, "Burrito x1, Fries x5, Meal x2", StatusEnum.AWAIT_COLLECTION, 18);
        assertEquals(true, orderDetail.collectOrder("01/07/2024", "12:40"));
    }

    /**
     * Test date time input format validation.
     **/
    @Test
    void collectOrderInput() {
        OrderDetail orderDetail = new OrderDetail("User1", 4, "01/06/2024", "12:30", 43.5, "Burrito x1, Fries x5, Meal x2", StatusEnum.AWAIT_COLLECTION, 18);
        assertEquals(false, orderDetail.collectOrder("1/7/2024", "12:40"));
        assertEquals(false, orderDetail.collectOrder("01/06/2024", "15.0"));
    }


}