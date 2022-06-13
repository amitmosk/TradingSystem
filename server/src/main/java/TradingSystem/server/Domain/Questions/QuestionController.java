package TradingSystem.server.Domain.Questions;

import TradingSystem.server.Domain.UserModule.AssignUser;
import TradingSystem.server.Domain.Utils.Exception.ObjectDoesntExsitException;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Entity
public class QuestionController implements iQuestionController {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long question_controller_id;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "buyer_to_store",
            joinColumns = {@JoinColumn(name = "controller", referencedColumnName = "question_controller_id")})
    @MapKeyColumn(name = "question_id") // the key column
    private Map<Integer, BuyerQuestion> buyer_to_store;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "user_to_admin",
            joinColumns = {@JoinColumn(name = "controller", referencedColumnName = "question_controller_id")})
    @MapKeyColumn(name = "question_id") // the key column
    private Map<Integer, UserQuestion> user_to_admin;
    private AtomicInteger question_ids_counter;



    private static class SingletonHolder{
        private static QuestionController instance = new QuestionController();
    }

    public QuestionController() {
        this.buyer_to_store = new ConcurrentHashMap<>();
        this.user_to_admin = new ConcurrentHashMap<>();
        this.question_ids_counter = new AtomicInteger(1);

    }

    public static QuestionController getInstance(){
        return QuestionController.SingletonHolder.instance;
    }

    public List<String> get_all_user_questions(String user_email) {
        List<String> answer = new ArrayList<>();
        String temp;
        for ( BuyerQuestion question : this.buyer_to_store.values())
        {
            if (question.getSender().get_user_email().equals(user_email)){
                temp = question.toString_for_user();
                answer.add(temp);
            }
        }
        for ( UserQuestion question : this.user_to_admin.values())
        {
            if (question.getSender().get_user_email().equals(user_email)){
                temp = question.toString_for_user();
                answer.add(temp);
            }
        }
        return answer;
    }

    public void setQuestion_controller_id(Long question_controller_id) {
        this.question_controller_id = question_controller_id;
    }


    public Long getQuestion_controller_id() {
        return question_controller_id;
    }


    @Override
    public void add_buyer_question(String message, AssignUser sender, int store_id){
        int question_id = this.question_ids_counter.getAndIncrement();
        BuyerQuestion question_to_add = new BuyerQuestion(question_id, message, sender, store_id);
        this.buyer_to_store.put(question_id, question_to_add);
    }

    public void add_user_question(String message, AssignUser sender){
        int question_id = this.question_ids_counter.getAndIncrement();
        UserQuestion question_to_add = new UserQuestion(question_id, message, sender);
        this.user_to_admin.put(question_id, question_to_add);
    }

    public void answer_buyer_question(int question_id, String answer) throws ObjectDoesntExsitException {
        if (!this.buyer_to_store.containsKey(question_id))
        {
            throw new ObjectDoesntExsitException("Question does not exist");
        }
        Question question = this.buyer_to_store.get(question_id);
        question.setAnswer(answer);
        question.getSender().add_notification("your question got answered");

    }

    public void answer_user_question(int question_id, String answer) throws ObjectDoesntExsitException {
        if (!this.user_to_admin.containsKey(question_id))
        {
            throw new ObjectDoesntExsitException("Question does not exist");
        }
        Question question = this.user_to_admin.get(question_id);
        question.setAnswer(answer);
        question.getSender().add_notification("your question got answered");

    }

    @Override
    public List<String> view_buyers_to_store_questions(int store_id) {
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

    @Override
    public List<String> view_users_to_admin_questions() {
        List<String> questionsList_to_return = new LinkedList<String>();
        for (UserQuestion question : this.user_to_admin.values())
        {
            String temp = question.toString();
            questionsList_to_return.add(temp);

        }
        return questionsList_to_return;
    }




}



