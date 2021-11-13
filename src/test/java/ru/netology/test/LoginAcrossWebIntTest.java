package ru.netology.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import data.DataGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static data.DataGenerator.*;

public class LoginAcrossWebIntTest {
    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    void ShouldLoginActiveUser() {
        DataGenerator.UserInfo activeUser = createActiveUser();
        $("[name='login']").setValue(activeUser.getLogin());
        $("[name='password']").setValue(activeUser.getPassword());
        $("[data-test-id='action-login']").click();
        $(Selectors.withText("������ �������")).shouldBe(visible);

    }
    @Test
    void ShouldNotLoginActiveUserWithWrongLogin() {
        DataGenerator.UserInfo activeUser = createActiveUser();
        $("[name='login']").setValue(getLogin("en"));
        $("[name='password']").setValue(activeUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("[class='notification__content']").shouldHave(Condition.text("������� ������ ����� ��� ������"));

   }
    @Test
    void ShouldNotLoginActiveUserWithWrongPassword() {
        DataGenerator.UserInfo activeUser = createActiveUser();
        $("[name='login']").setValue(activeUser.getLogin());
        $("[name='password']").setValue(getPassword("en"));
        $("[data-test-id='action-login']").click();
        $("[class='notification__content']").shouldHave(Condition.text("������� ������ ����� ��� ������"));

    }
    @Test
    void ShouldNotLoginBlockedUser() {
        DataGenerator.UserInfo blockedUser = createBlockedUser();
        $("[name='login']").setValue(blockedUser.getLogin());
        $("[name='password']").setValue(blockedUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("[class='notification__content']").shouldHave(Condition.text("������������ ������������"));
    }
    @Test
    void ShouldNotLoginBlockedUserWithWrongLogin() {
        DataGenerator.UserInfo blockedUser = createBlockedUser();
        $("[name='login']").setValue(getLogin("en"));
        $("[name='password']").setValue(blockedUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("[class='notification__content']").shouldHave(Condition.text("������� ������ ����� ��� ������"));
    }
    @Test
    void ShouldNotLoginBlockedUserWithWrongPassword() {
        DataGenerator.UserInfo blockedUser = createBlockedUser();
        $("[name='login']").setValue(blockedUser.getLogin());
        $("[name='password']").setValue(getPassword("en"));
        $("[data-test-id='action-login']").click();
        $("[class='notification__content']").shouldHave(Condition.text("������� ������ ����� ��� ������"));
    }
    @Test
    void ShouldNotLoginUnregistered() {
        $("[name='login']").setValue(getLogin("en"));
        $("[name='password']").setValue(getPassword("en"));
        $("[data-test-id='action-login']").click();
        $("[class='notification__content']").shouldHave(Condition.text("������� ������ ����� ��� ������"));
    }
}
