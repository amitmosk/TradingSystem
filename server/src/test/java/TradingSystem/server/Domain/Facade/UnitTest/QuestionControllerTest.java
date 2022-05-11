package TradingSystem.server.Domain.Facade.UnitTest;

import TradingSystem.server.Domain.Questions.QuestionController;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

class QuestionControllerTest {
    private QuestionController questionController;
    private final String email = "amit@gmail.com";

    @BeforeEach
    void setUp() {
        questionController = new QuestionController();
    }


    @Test
    void add_buyer_question() {
        questionController.add_buyer_question("why?", email, 1);
        List<String> answer = questionController.view_buyers_to_store_questions(1);
        System.out.println(answer.get(0));
        assertEquals(answer.size(), 1);
    }

    @Test
    void add_user_question() {
        questionController.add_user_question("why?", email);
        List<String> answer = questionController.view_users_to_admin_questions();
        System.out.println(answer.get(0));
        assertEquals(answer.size(), 1);
    }

    @Test
    void answer_buyer_question() {
        try {
            questionController.add_buyer_question("why?", email, 1);
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
            questionController.add_user_question("why?", email);
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
        questionController.add_buyer_question("why?", email, 1);
        questionController.add_buyer_question("why2?", email, 1);
        questionController.add_buyer_question("why3?", email, 1);
        questionController.add_buyer_question("why4?", email, 1);
        questionController.add_buyer_question("why5?", email, 1);
        List<String> answer = questionController.view_buyers_to_store_questions(1);
        System.out.println(answer.size());
        assertEquals(answer.size(), 5);
    }

    @Test
    void view_admin_questions() {
        questionController.add_user_question("why?", email);
        questionController.add_user_question("what?", email);
        questionController.add_user_question("???", email);
        questionController.add_user_question("who?", email);
        questionController.add_user_question("whete?", email);
        List<String> answer = questionController.view_users_to_admin_questions();
        System.out.println(answer.size());
        assertEquals(answer.size(), 5);
    }
}