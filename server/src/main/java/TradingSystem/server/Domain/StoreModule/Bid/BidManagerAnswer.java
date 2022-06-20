package TradingSystem.server.Domain.StoreModule.Bid;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class BidManagerAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private boolean has_answer;
    private boolean answer;
    private boolean has_permission;
    private boolean has_permission_nego;

    public BidManagerAnswer(boolean has_permission, boolean has_permission_nego) {
        this.has_permission = has_permission;
        this.has_permission_nego = has_permission_nego;
    }

    public boolean get_has_answer() {
        return has_answer;
    }

    public void setHas_answer(boolean has_answer) {
        this.has_answer = has_answer;
//        HibernateUtils.merge(this);
    }

    public boolean isHas_permission() {
        return has_permission;
    }

    public void setHas_permission(boolean has_permission) {
        this.has_permission = has_permission;
    }

    public boolean isHas_permission_nego() {
        return has_permission_nego;
    }

    public void setHas_permission_nego(boolean has_permission_nego) {
        this.has_permission_nego = has_permission_nego;
    }

    public boolean get_answer() {
        return answer;
    }

    public void setAnswer(boolean answer) {
        this.answer = answer;
//        HibernateUtils.merge(this);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public boolean isHas_answer() {
        return has_answer;
    }

    public boolean isAnswer() {
        return answer;
    }
}
