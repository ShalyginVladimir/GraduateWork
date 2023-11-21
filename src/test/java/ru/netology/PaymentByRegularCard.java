package ru.netology;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import page.WebPage;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PaymentByRegularCard {
    WebPage webPage = new WebPage();

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    void clearDatabaseTables() {
        DataHelperDB.clearTables();
    }


    @Test
    @DisplayName("Оплата тура с валидной картой с статусом APPROVED")
    void testCashValidCardApproved() {
        webPage.buyWithCash();
        webPage.setCardNumber(DataHelper.getCardNumberApproved());
        webPage.setCardMonth(DataHelper.getMonth());
        webPage.setCardOwner(DataHelper.getOwner());
        webPage.setCardCVV(DataHelper.getCvc());
        webPage.clickContinueButton();
        webPage.messageSuccessfully();
        assertEquals("APPROVED", DataHelperDB.findPayStatus());
    }

    @Test
    @DisplayName("Оплата тура с валидной картой с статусом DECLINED")
    void testCashValidCardDeclined() {
        webPage.buyWithCash();
        webPage.setCardNumber(DataHelper.getCardNumberDeclined());
        webPage.setCardMonth(DataHelper.getMonth());
        webPage.setCardOwner(DataHelper.getOwner());
        webPage.setCardCVV(DataHelper.getCvc());
        webPage.clickContinueButton();
        webPage.messageError();
        assertEquals("DECLINED", DataHelperDB.findPayStatus());
    }

    @Test
    @DisplayName("Оплата тура по не полностью заполненной карте")
    void testCashUnfilledCard() {
        webPage.buyWithCash();
        webPage.setCardNumber(DataHelper.getCardNumberNotFilled());
        webPage.setCardMonth(DataHelper.getMonth());
        webPage.setCardOwner(DataHelper.getOwner());
        webPage.setCardCVV(DataHelper.getCvc());
        webPage.clickContinueButton();
        webPage.messageIncorrectFormat();
        assertEquals(0, DataHelperDB.getOrderEntityCount());
    }

    @Test
    @DisplayName("Оплата тура по карте с истекшим сроком действия (месяц)")
    void testCashLastMonth() {
        webPage.buyWithCash();
        webPage.setCardNumber(DataHelper.getCardNumberApproved());
        webPage.setCardMonth(DataHelper.getMonthOneMonthAgo());
        webPage.setCardYear(DataHelper.getCurrentYear());
        webPage.setCardOwner(DataHelper.getOwner());
        webPage.setCardCVV(DataHelper.getCvc());
        webPage.clickContinueButton();
        webPage.messageInvalidDate();
    }

    @Test
    @DisplayName("Оплата тура по карте с невалидным месяцем 00")
    void testCashMonth00() {
        webPage.buyWithCash();
        webPage.setCardNumber(DataHelper.getCardNumberApproved());
        webPage.setCardMonth(DataHelper.getInvalidMonth());
        webPage.setCardOwner(DataHelper.getOwner());
        webPage.setCardCVV(DataHelper.getCvc());
        webPage.clickContinueButton();
        webPage.messageInvalidDate();
    }

    @Test
    @DisplayName("Оплата тура по карте с не заполненным полем месяц")
    void testCashEmptyMonth() {
        webPage.buyWithCash();
        webPage.setCardNumber(DataHelper.getCardNumberApproved());
        webPage.setCardOwner(DataHelper.getOwner());
        webPage.setCardCVV(DataHelper.getCvc());
        webPage.clickContinueButton();
        webPage.messageIncorrectFormat();
    }

    @Test
    @DisplayName("Оплата тура по карте с истекшим сроком действия (год)")
    void testCashLastYear() {
        webPage.buyWithCash();
        webPage.setCardNumber(DataHelper.getCardNumberApproved());
        webPage.setCardMonth(DataHelper.getMonth());
        webPage.setCardOwner(DataHelper.getOwner());
        webPage.setCardCVV(DataHelper.getCvc());
        webPage.clickContinueButton();
        webPage.messageCardExpired();
    }

    @Test
    @DisplayName("Оплата тура по карте с годом + 6 лет от текущего ")
    void testCashYearPlus6() {
        webPage.buyWithCash();
        webPage.setCardNumber(DataHelper.getCardNumberApproved());
        webPage.setCardMonth(DataHelper.getMonth());
        webPage.setCardOwner(DataHelper.getOwner());
        webPage.setCardCVV(DataHelper.getCvc());
        webPage.clickContinueButton();
        webPage.messageInvalidDate();
    }

    @Test
    @DisplayName("Оплата тура по карте с незаполненным полем год")
    void testCashEmptyYear() {
        webPage.buyWithCash();
        webPage.setCardNumber(DataHelper.getCardNumberApproved());
        webPage.setCardMonth(DataHelper.getMonth());
        webPage.setCardOwner(DataHelper.getOwner());
        webPage.setCardCVV(DataHelper.getCvc());
        webPage.clickContinueButton();
        webPage.messageIncorrectFormat();
    }

    @Test
    @DisplayName("Оплата тура с вводом цифр в поле Владелец")
    void testCashNumbersOwner() {
        webPage.buyWithCash();
        webPage.setCardNumber(DataHelper.getCardNumberApproved());
        webPage.setCardMonth(DataHelper.getMonth());
        webPage.setCardOwner(DataHelper.getNumberOwner());
        webPage.setCardCVV(DataHelper.getCvc());
        webPage.clickContinueButton();
        webPage.messageRequiredField();
    }

    @Test
    @DisplayName("Оплата тура с вводом специальных символов в поле Владелец")
    void testCashSymbolsOwner() {
        webPage.buyWithCash();
        webPage.setCardNumber(DataHelper.getCardNumberApproved());
        webPage.setCardMonth(DataHelper.getMonth());
        webPage.setCardOwner(DataHelper.getSpecialCharactersOwner());
        webPage.setCardCVV(DataHelper.getCvc());
        webPage.clickContinueButton();
        webPage.messageRequiredField();
    }

    @Test
    @DisplayName("Оплата тура с пустым полем Владелец")
    void testCashEmptyOwner() {
        webPage.buyWithCash();
        webPage.setCardNumber(DataHelper.getCardNumberApproved());
        webPage.setCardMonth(DataHelper.getMonth());
        webPage.setCardOwner(DataHelper.getEmptyOwner());
        webPage.setCardCVV(DataHelper.getCvc());
        webPage.clickContinueButton();
        webPage.messageRequiredField();
    }

    @Test
    @DisplayName("Оплата тура с вводом одной цифры в поле CVC/CVV")
    void testCash1CVC() {
        webPage.buyWithCash();
        webPage.setCardNumber(DataHelper.getCardNumberApproved());
        webPage.setCardMonth(DataHelper.getMonth());
        webPage.setCardOwner(DataHelper.getOwner());
        webPage.setCardCVV(DataHelper.get1Cvc());
        webPage.clickContinueButton();
        webPage.messageIncorrectFormat();
    }

    @Test
    @DisplayName("Оплата тура с вводом двух цифр в поле CVC/CVV")
    void testCash2CVC() {
        webPage.buyWithCash();
        webPage.setCardNumber(DataHelper.getCardNumberApproved());
        webPage.setCardMonth(DataHelper.getMonth());
        webPage.setCardOwner(DataHelper.getOwner());
        webPage.setCardCVV(DataHelper.get2Cvc());
        webPage.clickContinueButton();
        webPage.messageIncorrectFormat();
    }

    @Test
    @DisplayName("Оплата тура с пустым значением в поле CVC/CVV")
    void testCashEmptyCVC() {
        webPage.buyWithCash();
        webPage.setCardNumber(DataHelper.getCardNumberApproved());
        webPage.setCardMonth(DataHelper.getMonth());
        webPage.setCardOwner(DataHelper.getOwner());
        webPage.setCardCVV(DataHelper.getEmptyCvc());
        webPage.clickContinueButton();
        webPage.messageIncorrectFormat();
    }

    @Test
    @DisplayName("Оплата тура с не заполненными полями")
    void testCashEmptyFields() {
        webPage.buyWithCash();
        webPage.setCardOwner(DataHelper.getEmptyOwner());
        webPage.setCardCVV(DataHelper.getEmptyCvc());
        webPage.clickContinueButton();
        webPage.messageIncorrectFormat();
    }
}