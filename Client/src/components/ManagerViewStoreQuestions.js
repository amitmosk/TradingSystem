import React, { Component } from 'react';
import Link from '@mui/material/Button';
import HomeIcon from '@mui/icons-material/Home';
import { StoreApi } from '../API/StoreApi';
import FormDialog from './FormDialog';
import Card from '@mui/material/Card';
import { Question } from '../ServiceObjects/Question';
import Snackbar from "@mui/material/Snackbar";
import Alert from "@mui/material/Alert"; 


export default class ManagerViewStoreQuestions extends Component {

    static displayName = ManagerViewStoreQuestions.name;
    constructor(props) {
        super(props);
        this.state = { 
            questions:[],
            answer_user_questions_fields: ["question_id", "answer"],
            store_id:this.props.store_id, 
            snackbar: null,

        };
        this.storeApi = new StoreApi();

    }    

    
    
    
    async componentDidMount() {
        
        
        console.log("in manager_view_store_questions!\n");
        const store_id = this.props.store_id;
        console.log("store id = "+store_id);
        const response = await this.storeApi.manager_view_store_questions(store_id);
       
        console.log("in UserViewQuestions!\n");
        const questions = response.value;
        console.log("questions = "+questions);
        // alert(response.message);
        if (!response.was_excecption) {
            this.setState({ snackbar: { children: response.message, severity: "success" } });
            console.log("in UserViewQuestions - success!\n");
            console.log(response.value);
            
                  //
                  //static create( question_id,  message_date,  answer_date,  message,  answer, has_answer) {
            const final_questions=[];
            let splitted_questions=[];
            questions.map(q=>{ splitted_questions = q.split(",");
            console.log("spplited= "+splitted_questions);
            const user_email = splitted_questions[0];
            const question_id = splitted_questions[1];
            const message_date = splitted_questions[2];
            const answer_date = splitted_questions[3];
            const message = splitted_questions[4];
            const answer = splitted_questions[5];
            const has_answer = splitted_questions[6];
            const que=new Question (question_id, message_date, answer_date, message, answer, has_answer, user_email);
            final_questions.push(que);
                });
            this.setState({
                questions:final_questions,
                    })

        }
        else {
            this.setState({ snackbar: { children: response.message, severity: "error" } });

        }
    }
    async manager_answer_question(values) {
        console.log("in manager_answer_question!\n");
        const store_id = this.state.store_id;
        const question_id = values[0];
        const answer = values[1];
        console.log(store_id +" , "+question_id+" , "+answer);

        const response = await this.storeApi.manager_answer_question(store_id, question_id, answer);
        // alert(response.message);
        if (!response.was_execption) {
            this.setState({ snackbar: { children: response.message, severity: "success" } });
            console.log("in manager_answer_question - success!\n");
            //show history
        }
        else {
            this.setState({ snackbar: { children: response.message, severity: "error" } });

        }
    }
      

    render() {
        const {redirectTo} = this.state
            return (
                <main class="LoginMain">
                    <div class="LoginWindow">
                    <Link href="/"><HomeIcon></HomeIcon></Link>
                        <row><h1>User Questions (User)</h1></row>
                        {/* {[0,1,2,3,4,5,6,7].map((item) => (
                            <Card >
                            {item}
                            </Card>
                        ))} */}
                        {this.state.questions.length == 0 ? <h3 style={{ color: 'red' }}>No Questions To Show</h3> : 
                        this.state.questions.map((question) => (
                            <Card >
                            <h3 style={{ color: 'blue' }}> {question.email}</h3>
                            <div> {question.question_id}</div>
                            <div> {question.message_date}</div>
                            <div> {question.answer_date}</div>
                            <div> {question.answer}</div>
                            <div> {question.has_answer}</div>
                            {/* <div> question : {question}</div> */}
                           

                            </Card>
                        ))}
                        <FormDialog outlinedVar="text" fields={this.state.answer_user_questions_fields} getValues={this.manager_answer_question.bind(this)} name="Answer Users Questions"></FormDialog>
                        {!!this.state.snackbar && (
                        <Snackbar
                        open
                        anchorOrigin={{ vertical: "bottom", horizontal: "center" }}
                        onClose={this.handleCloseSnackbar}
                        autoHideDuration={6000}
                        >
                        <Alert
                            {...this.state.snackbar}
                            onClose={this.handleCloseSnackbar}
                        />
                        </Snackbar>
                    )}      
                       
                    </div>
                </main>
            );
        
    }
}


