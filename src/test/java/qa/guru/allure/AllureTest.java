package qa.guru.allure;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static io.qameta.allure.Allure.step;
import static org.openqa.selenium.By.linkText;

@DisplayName("Allure github tests")
public class AllureTest {

    private static final String REPOSITORY = "dmitrii-slayer/qa_hw_9_allure_report";
    private static final String ISSUE_TEXT = "Test issue for Allure HW";
    private static final int ISSUE_NUMBER = 80;


    // лямбда шаги
    @Test
    @DisplayName("Тест с использованием лямбда шагов")
    public void testLambdaStep() {
        SelenideLogger.addListener("allure", new AllureSelenide());

        step("Открываем главную страницу", () -> {
            open("https://github.com");
        });
        step("Ищем репозиторий " + REPOSITORY, () -> {
            $(".header-search-input").click();
            $(".header-search-input").sendKeys(REPOSITORY);
            $(".header-search-input").submit();
        });
        step("Кликаем по ссылке репозитория " + REPOSITORY, () -> {
            $(linkText(REPOSITORY)).click();
        });
        step("Открываем таб Issues", () -> {
            $("#issues-tab").click();
        });
        step("Проверяем наличие Issue с текстом " + ISSUE_TEXT, () -> {
            $(withText(ISSUE_TEXT)).shouldBe(visible);
        });
//        step("Проверяем наличие Issue с номером " + ISSUE_NUMBER, () -> {
//            $(withText("#" + ISSUE_NUMBER)).should(exist);
//        });
    }

    // шаги с аннотацией @Step
    @Test
    @DisplayName("Тест с использованием шагов с аннотацией @Step")
    public void testAnnotatedStep() {
        SelenideLogger.addListener("allure", new AllureSelenide());
        WebSteps steps = new WebSteps();

        steps.openMainPage();
        steps.searchForRepository(REPOSITORY);
        steps.clickOnRepositoryLink(REPOSITORY);
        steps.openIssuesTab();
        steps.shouldSeeIssueWithText(ISSUE_TEXT);
//        steps.shouldSeeIssueWithNumber(ISSUE_NUMBER);

    }

    // чистый Selenide (с Listener)
    @Test
    @DisplayName("Тест с использованием чистого Selenide с Listener")
    public void testIssueSearch() {
        SelenideLogger.addListener("allure", new AllureSelenide());

        open("https://github.com");

        $(".header-search-input").click();
        $(".header-search-input").sendKeys("dmitrii-slayer/qa_hw_9_allure_report");
        $(".header-search-input").submit();

        $(linkText("dmitrii-slayer/qa_hw_9_allure_report")).click();
        $("#issues-tab").click();
        $(withText("Test issue for Allure HW")).shouldBe(visible);
//        $(withText("#80")).should(Condition.exist);
    }

}
