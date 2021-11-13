package data;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.Data;
import lombok.Value;


import java.util.Locale;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;


public class DataGenerator {
    public DataGenerator() {
    }
@Value
    public static class UserInfo {
       String login;
       String password;
       String status;

        public UserInfo(String login, String password, String status) {
            this.login = login;
            this.password = password;
            this.status = status;
        }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getStatus() {
        return status;
    }
}
    public static String getLogin(String locale) {
        Faker faker = new Faker(new Locale(locale));
        String login = faker.name().username();
        return login;
    }

    public static String getPassword(String locale) {
        Faker faker = new Faker(new Locale(locale));
        String password = faker.internet().password();
        return password;
    }

    public static void sendRequestToCreateUser(UserInfo user) {
    given()
            .spec(requestSpec)
            .body(user)
            .when()
            .post("/api/system/users")
        .then()
            .statusCode(200);

}

    private static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    public static UserInfo createActiveUser() {
        UserInfo user = new UserInfo(getLogin("en"), getPassword("en"), "active");
        sendRequestToCreateUser (user);
        return user;
    }
    public static UserInfo createBlockedUser() {
        UserInfo user = new UserInfo(getLogin("en"), getPassword("en"), "blocked");
        sendRequestToCreateUser (user);
        return user;
    }

}
