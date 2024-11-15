package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CreditCardAuthenticatorTest {

    /**
     * Test card number with 16 digits, future expiry date (current MM/yy is 06/24), and 3 digit cvv
     **/
    @Test
    void testValidCreditCard() {
        assertEquals(true, CreditCardAuthenticator.validateCreditCard("1234567890123456", "02/28", "123"));
    }

    /**
     * Test card number that are not 16 digits
     **/
    @Test
    void testInValidCreditCardNumber() {
        assertEquals(false, CreditCardAuthenticator.validateCreditCard("123456789012345", "02/25", "123"));
        assertEquals(false, CreditCardAuthenticator.validateCreditCard("12345678901234567", "02/25", "123"));
        assertEquals(false, CreditCardAuthenticator.validateCreditCard("abcdef1234567890", "02/25", "123"));
    }

    /**
     * Test card expiry date that is not in the future
     **/
    @Test
    void testInValidCreditCardDate() {
        assertEquals(false, CreditCardAuthenticator.validateCreditCard("1234567890123456", "01/24", "123"));
        assertEquals(false, CreditCardAuthenticator.validateCreditCard("1234567890123456", "06/22", "123"));
    }

    /**
     * Test card expiry date wrong format
     **/
    @Test
    void testInValidDateFormat() {
        assertEquals(false, CreditCardAuthenticator.validateCreditCard("1234567890123456", "01/2028", "123"));
        assertEquals(false, CreditCardAuthenticator.validateCreditCard("1234567890123456", "6/25", "123"));
    }

    /**
     * Test card cvv that is not 3 digits
     **/
    @Test
    void testInValidCreditCardCVV() {
        assertEquals(false, CreditCardAuthenticator.validateCreditCard("1234567890123456", "02/28", "11"));
        assertEquals(false, CreditCardAuthenticator.validateCreditCard("1234567890123456", "02/28", "2222"));
        assertEquals(false, CreditCardAuthenticator.validateCreditCard("1234567890123456", "02/28", "abc"));
        assertEquals(false, CreditCardAuthenticator.validateCreditCard("1234567890123456", "02/28", "1.5"));
    }

}