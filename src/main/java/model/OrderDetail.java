package model;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class OrderDetail {

    private String username;
    private int orderNo;
    private String orderDate;
    private String orderTime;
    private double orderCost;
    private String orderDetail;
    private StatusEnum orderStatus;
    private final double prepareTime;

    public OrderDetail(String username, int orderNo, String orderDate, String orderTime, double orderCost, String orderDetail, StatusEnum orderStatus, double prepareTime) {
        this.username = username;
        this.orderNo = orderNo;
        this.orderDate = orderDate;
        this.orderTime = orderTime;
        this.orderCost = orderCost;
        this.orderDetail = orderDetail;
        this.orderStatus = orderStatus;
        this.prepareTime = prepareTime;
    }

    public double getPrepareTime() {
        return prepareTime;
    }

    public String getUsername() {
        return username;
    }

    public int getOrderNo() {
        return orderNo;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public double getOrderCost() {
        return orderCost;
    }

    public String getOrderDetail() {
        return orderDetail;
    }

    /**
     * Validates collect order time and changes status to "collected" if input date time valid.
     **/
    public boolean collectOrder(String collectDate, String collectTime) {
        try {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

            // Parse order date and time
            LocalDate orderDate = LocalDate.parse(getOrderDate(), dateFormatter);
            LocalTime orderTime = LocalTime.parse(getOrderTime(), timeFormatter);

            // Parse input date and time
            LocalDate collectDateParsed = LocalDate.parse(collectDate, dateFormatter);
            LocalTime collectTimeParsed = LocalTime.parse(collectTime, timeFormatter);

            // Combine date and time into LocalDateTime
            LocalDateTime orderDateTime = LocalDateTime.of(orderDate, orderTime);
            LocalDateTime collectDateTime = LocalDateTime.of(collectDateParsed, collectTimeParsed);

            // Calculate the difference in minutes between orderDateTime and collectDateTime
            Duration duration = Duration.between(orderDateTime, collectDateTime);
            long minutesDifference = duration.toMinutes();

            // Compare the difference with the preparation time
            if ( minutesDifference >= prepareTime) {
                setOrderStatus(StatusEnum.COLLECTED);
                return true;
            }
            return false;

        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Returns a string of specified attributes
     **/
    public String getSelectedAttributes(List<String> attributes) {
        StringBuilder sb = new StringBuilder();
        sb.append("| ");
        for (String attribute : attributes) {
            switch (attribute) {
                case "orderNo":
                    sb.append(String.format("%2d  | ", orderNo));
                    break;
                case "orderDate":
                    sb.append(String.format("%s | ", orderDate));
                    break;
                case "orderTime":
                    sb.append(String.format("%s | ", orderTime));
                    break;
                case "orderCost":
                    sb.append(String.format("$%4.1f | ", orderCost));
                    break;
                case "orderDetail":
                    sb.append(String.format("%-36s | ", orderDetail));
                    break;
                case "orderStatus":
                    sb.append(String.format("%-16s |", orderStatus));
                    break;
                default:
                    sb.append("Invalid attribute: ").append(attribute);
            }
        }
        return sb.toString();
    }


    public void setOrderStatus(StatusEnum orderStatus) {
        this.orderStatus = orderStatus;
    }

    public StatusEnum getOrderStatus() {
        return orderStatus;
    }

    public boolean cancelOrder() {
        if (orderStatus == StatusEnum.AWAIT_COLLECTION) {
            setOrderStatus(StatusEnum.CANCELLED);
            return true;
        }
        return false;
    }

}
