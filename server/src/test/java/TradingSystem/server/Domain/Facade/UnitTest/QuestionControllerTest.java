package TradingSystem.server.Domain.Facade.UnitTest;

import TradingSystem.server.Domain.Questions.QuestionController;
import static org.junit.jupiter.api.Assertions.*;

import TradingSystem.server.Domain.UserModule.AssignUser;
import TradingSystem.server.Domain.UserModule.User;
import TradingSystem.server.Service.NotificationHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

class QuestionControllerTest {
    private QuestionController questionController;
    private AssignUser assigned_user;
    private final String email = "amit@gmail.com";

    @BeforeEach
    void setUp() {
        NotificationHandler.setTestsHandler();
        User new_user = new User();
        try {
            new_user.register(email, "aA123456", "gal", "brown", LocalDate.now().minusYears(30).toString());
            assigned_user = new_user.state_if_assigned();
        }catch (Exception e){
            fail("failed to initialized new user for question controller tests");
        }
        questionController = new QuestionController();
    }


    @Test
    void add_buyer_question() {
        questionController.add_buyer_question("why?", assigned_user, 1);
        List<String> answer = questionController.view_buyers_to_store_questions(1);
        System.out.println(answer.get(0));
        assertEquals(answer.size(), 1);
    }

    @Test
    void add_user_question() {
        questionController.add_user_question("why?", assigned_user);
        List<String> answer = questionController.view_users_to_admin_questions();
        System.out.println(answer.get(0));
        assertEquals(answer.size(), 1);
    }

    @Test
    void answer_buyer_question() {
        try {
            questionController.add_buyer_question("why?", assigned_user, 1);
            questionController.answer_buyer_question(1, "like this");
            List<String> answer = questionController.view_buyers_to_store_questions(1);
            System.out.println(answer.get(0));
            assertEquals(answer.size(), 1);
        } catch (Exception e) {
            assertTrue(false, e.getMessage());
        }
    }

    @Test
    void answer_user_question() {
        try {
            questionController.add_user_question("why?", assigned_user);
            questionController.answer_user_question(1, "sorry");
            List<String> answer = questionController.view_users_to_admin_questions();
            System.out.println(answer.get(0));
            assertEquals(answer.size(), 1);
        } catch (Exception e) {
            assertTrue(false, e.getMessage());
        }
    }

    @Test
    void view_store_questions() {
        questionController.add_buyer_question("why?", assigned_user, 1);
        questionController.add_buyer_question("why2?", assigned_user, 1);
        questionController.add_buyer_question("why3?", assigned_user, 1);
        questionController.add_buyer_question("why4?", assigned_user, 1);
        questionController.add_buyer_question("why5?", assigned_user, 1);
        List<String> answer = questionController.view_buyers_to_store_questions(1);
        System.out.println(answer.size());
        assertEquals(answer.size(), 5);
    }

    @Test
    void view_admin_questions() {
        questionController.add_user_question("why?", assigned_user);
        questionController.add_user_question("what?", assigned_user);
        questionController.add_user_question("???", assigned_user);
        questionController.add_user_question("who?", assigned_user);
        questionController.add_user_question("whete?", assigned_user);
        List<String> answer = questionController.view_users_to_admin_questions();
        System.out.println(answer.size());
        assertEquals(answer.size(), 5);
    }
}