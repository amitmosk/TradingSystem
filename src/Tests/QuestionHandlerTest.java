package Tests;

import Domain.Communication.QuestionHandler;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class QuestionHandlerTest {
private QuestionHandler questionHandler;
private final String email = "amit@gmail.com";
    @BeforeEach
    void setUp() {
        questionHandler = new QuestionHandler();
    }


    @Test
    void add_buyer_question() {
        questionHandler.add_buyer_question("why?",email, 1);
        List<String> answer = questionHandler.view_buyers_to_store_questions(1);
        System.out.println(answer.get(0));
        Assert.assertEquals(answer.size(),1);
    }

    @Test
    void add_user_question() {
        questionHandler.add_user_question("why?",email);
        List<String> answer = questionHandler.view_users_to_admin_questions();
        System.out.println(answer.get(0));
        Assert.assertEquals(answer.size(),1);
    }

    @Test
    void answer_buyer_question() {
        questionHandler.add_buyer_question("why?",email, 1);
        questionHandler.answer_buyer_question(1, "like this");
        List<String> answer = questionHandler.view_buyers_to_store_questions(1);
        System.out.println(answer.get(0));
        Assert.assertEquals(answer.size(),1);
    }

    @Test
    void answer_user_question() {
        questionHandler.add_user_question("why?",email);
        questionHandler.answer_user_question(1, "sorry");
        List<String> answer = questionHandler.view_users_to_admin_questions();
        System.out.println(answer.get(0));
        Assert.assertEquals(answer.size(),1);
    }

    @Test
    void view_store_questions() {
        questionHandler.add_buyer_question("why?",email, 1);
        questionHandler.add_buyer_question("why2?",email, 1);
        questionHandler.add_buyer_question("why3?",email, 1);
        questionHandler.add_buyer_question("why4?",email, 1);
        questionHandler.add_buyer_question("why5?",email, 1);
        List<String> answer = questionHandler.view_buyers_to_store_questions(1);
        System.out.println(answer.size());
        Assert.assertEquals(answer.size(),5);
    }

    @Test
    void view_admin_questions() {
        questionHandler.add_user_question("why?",email);
        questionHandler.add_user_question("what?",email);
        questionHandler.add_user_question("???",email);
        questionHandler.add_user_question("who?",email);
        questionHandler.add_user_question("whete?",email);
        List<String> answer = questionHandler.view_users_to_admin_questions();
        System.out.println(answer.size());
        Assert.assertEquals(answer.size(),5);
    }
}