package ru.netology.web;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;


public class CardDelivery {

    public String generateDate(int days) {
        return LocalDate.now ().plusDays (days).format (DateTimeFormatter.ofPattern ("dd.MM.yyyy"));
    }
    
    @Test
    public void russianLettersAndASpace() {
        String planningDate = generateDate (3);
        Configuration.holdBrowserOpen = true;
        open ("http://localhost:9999");
        $ ("[data-test-id='city'] input").val ("Владивосток");
        $x ("//input [@placeholder='Дата встречи']").doubleClick ();
        $x ("//input [@placeholder='Дата встречи']").sendKeys (" ");
        $x ("//input [@placeholder='Дата встречи']").val (planningDate);
        $ ("[data-test-id='name'] input").val ("Иванов Иван");
        $ ("[data-test-id='phone'] input").val ("+79999999999");
        $ ("[data-test-id='agreement']").click ();
        $ ("span[class=button__content]").click ();
        $ ("[data-test-id=notification]").shouldBe (visible, Duration.ofSeconds (15));
        $ ("[data-test-id=notification] [class='notification__content']").shouldHave (exactText ("Встреча успешно забронирована на " + planningDate));
    }

    @Test
    void russianLettersHyphenAndSpace() {
        String planningDate = generateDate (3);
        Configuration.holdBrowserOpen = true;
        open ("http://localhost:9999");
        $ ("[data-test-id='city'] input").val ("Владивосток");
        $x ("//input [@placeholder='Дата встречи']").doubleClick ();
        $x ("//input [@placeholder='Дата встречи']").sendKeys (" ");
        $x ("//input [@placeholder='Дата встречи']").val (planningDate);
        $ ("[data-test-id='name'] input").val ("Иванов-Петров Иван");
        $ ("[data-test-id='phone'] input").val ("+79999999999");
        $ ("[data-test-id='agreement']").click ();
        $ ("span[class=button__content]").click ();
        $ ("[data-test-id=notification]").shouldBe (visible, Duration.ofSeconds (15));
        $ ("[data-test-id=notification] [class='notification__content']").shouldHave (exactText ("Встреча успешно забронирована на " + planningDate));
    }
}