package Domain.Communication;

import Domain.UserModule.User;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class QuestionHandler {
    private Map<Integer, BuyerQuestion> buyer_to_store = new ConcurrentHashMap<>();
    private Map<Integer, UserQuestion> user_to_admin = new ConcurrentHashMap<>();
    private Map<Integer, SystemQuestion> system_to_user = new ConcurrentHashMap<>();
    private AtomicInteger question_ids_counter;




    private static class SingletonHolder{
        private static QuestionHandler instance = new QuestionHandler();
    }

    public QuestionHandler() {
        this.buyer_to_store = new ConcurrentHashMap<>();
        this.user_to_admin = new ConcurrentHashMap<>();
        this.system_to_user = new ConcurrentHashMap<>();
        this.question_ids_counter = new AtomicInteger(1);

    }

    public static QuestionHandler getInstance(){
        return QuestionHandler.SingletonHolder.instance;
    }

    public void add_buyer_question(String message, String buyer_email, int store_id){
        int question_id = this.question_ids_counter.getAndIncrement();
        BuyerQuestion question_to_add = new BuyerQuestion(question_id, message, buyer_email, store_id);
        this.buyer_to_store.put(question_id, question_to_add);
    }

    public void add_user_question(String message, String buyer_email){
        int question_id = this.question_ids_counter.getAndIncrement();
        UserQuestion question_to_add = new UserQuestion(question_id, message, buyer_email);
        this.user_to_admin.put(question_id, question_to_add);
    }

    public void add_system_question(String message, String user_email){
        int question_id = this.question_ids_counter.getAndIncrement();
        SystemQuestion question_to_add = new SystemQuestion(question_id, message, user_email);
        this.system_to_user.put(question_id, question_to_add);
    }

    public void answer_buyer_question(int question_id, String answer) {
        if (!this.buyer_to_store.containsKey(question_id))
        {
            throw new IllegalArgumentException("Question does not exist");
        }
        Question question = this.buyer_to_store.get(question_id);
        question.setAnswer(answer);
    }

    public void answer_user_question(int question_id, String answer) {
        if (!this.user_to_admin.containsKey(question_id))
        {
            throw new IllegalArgumentException("Question does not exist");
        }
        Question question = this.user_to_admin.get(question_id);
        question.setAnswer(answer);
    }

    public List<String> view_store_questions(int store_id) {
        List<String> questionsList_to_return = new LinkedList<String>();
        for (BuyerQuestion question : this.buyer_to_store.values())
        {
            if (question.get_store_id() == store_id)
            {
                String temp = question.toString();
                questionsList_to_return.add(temp);
            }
        }
        return questionsList_to_return;
    }

    public List<String> view_admin_questions() {
        List<String> questionsList_to_return = new LinkedList<String>();
        for (UserQuestion question : this.user_to_admin.values())
        {
            String temp = question.toString();
            questionsList_to_return.add(temp);

        }
        return questionsList_to_return;
    }




}

/*
buyer -> store
store -> buyer
user -> admin
admin -> user
system -> user
 */





