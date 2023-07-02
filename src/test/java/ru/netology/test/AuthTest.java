package ru.netology.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.data.DataGenerator.Registration.getRegisteredUser;
import static ru.netology.data.DataGenerator.Registration.getUser;

public class AuthTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successfully login with active registered user")
    void shouldSuccessfulLoginWithActiveRegisteredUser() {
        var registeredUser = getRegisteredUser("active");

        $("[data-test-id=login] input").setValue(registeredUser.getLogin());
        $("[data-test-id=password] input").setValue(registeredUser.getPassword());
        $("button.button").click();
        $("h2").shouldHave(exactText("Личный кабинет")).shouldBe(visible, Duration.ofSeconds(15));

    }

    @Test
    @DisplayName("Should not login with not registered user")
    void shouldNotLoginNotRegisteredUser() {
        var notRegisteredUser = getUser("active");

        $("[data-test-id=login] input").setValue(notRegisteredUser.getLogin());
        $("[data-test-id=password] input").setValue(notRegisteredUser.getPassword());
        $("button.button").click();
        $("[data-test-id=error-notification] .notification__content").shouldHave(text("Ошибка! Неверно указан логин или пароль")).shouldBe(visible, Duration.ofSeconds(15));
    }

    @Test
    @DisplayName("Should not login with blocked user")
    void shouldNotLoginWithBlockedUser() {
        var blockedUser = getRegisteredUser("blocked");
        $("[data-test-id=login] input").setValue(blockedUser.getLogin());
        $("[data-test-id=password] input").setValue(blockedUser.getPassword());
        $("button.button").click();
        $("[data-test-id=error-notification] .notification__content").shouldHave(text("Ошибка! Пользователь заблокирован")).shouldBe(visible, Duration.ofSeconds(15));
    }

    @Test
    @DisplayName("Should not login with wrong user")
    void shouldNotLoginWithWrongUser() {
        var wrongUser = getRegisteredUser("active");
        $("[data-test-id=login] input").setValue("wrongUser");
        $("[data-test-id=password] input").setValue(wrongUser.getPassword());
        $("button.button").click();
        $("[data-test-id=error-notification] .notification__content").shouldHave(text("Ошибка! Неверно указан логин или пароль")).shouldBe(visible, Duration.ofSeconds(15));
    }

    @Test
    @DisplayName("Should not login with wrong password")
    void shouldNotLoginWithWrongPassword() {
        var wrongPassword = getRegisteredUser("active");
        $("[data-test-id=login] input").setValue(wrongPassword.getLogin());
        $("[data-test-id=password] input").setValue("wrongPassword");
        $("button.button").click();
        $("[data-test-id=error-notification] .notification__content").shouldHave(text("Ошибка! Неверно указан логин или пароль")).shouldBe(visible, Duration.ofSeconds(15));
    }

}

