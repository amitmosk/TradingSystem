
export class Question  {

    // protected final int question_id;
    // protected final String message_date;
    // protected String answer_date;
    // protected String message;
    // protected String answer;
    // protected boolean has_answer;
    // protected final AssignUser sender; // assign user
    constructor(data) {
        this.question_id = data.question_id;
        this.message_date = data.message_date;
        this.answer_date = data.answer_date;
        this.message = data.message;
        this.answer = data.answer;
        this.has_answer = data.has_answer;
        // this.sender = new AssignUser(data.sender)

    }
    
    static create( question_id,  message_date,  answer_date,  message,  answer, has_answer) {
        return new Question({
            question_id : question_id,
            message_date : message_date,
            answer_date : answer_date,
            message : message,
            answer : answer,
            has_answer : has_answer,
        })

    }
}