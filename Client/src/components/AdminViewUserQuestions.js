import React, { Component } from 'react';
import Link from '@mui/material/Button';
import HomeIcon from '@mui/icons-material/Home';
import Card from '@mui/material/Card';
import { AdminApi } from '../API/AdminApi';
import { Question } from '../ServiceObjects/Question';
import Snackbar from "@mui/material/Snackbar";
import Alert from "@mui/material/Alert";
import BasicTable from './BasicTable';
  
export default class AdminViewUserQuestions extends Component {
    static displayName = AdminViewUserQuestions.name;
    constructor(props) {
        super(props);
        this.state = { 
            questions:[],
            snackbar: null,
            rows:[], 
        };
        this.adminApi = new AdminApi();

    }    

    
    
    
    async componentDidMount() {
        console.log("in admin_view_users_questions!\n");
        const response = await this.adminApi.admin_view_users_questions();
        const questions = response.value;
        console.log("questions = "+questions);
        // alert(response.message);
        if (!response.was_excecption) {
            this.setState({ snackbar: { children: response.message, severity: "success" } });
            console.log("in AdminViewUserQuestions - success!\n");
            console.log(response);
            const final_questions=[];
            let splitted_questions=[];
            const rows = [];
            questions.map(q=>{ splitted_questions = q.split(",");
            console.log("spplited= "+splitted_questions);
            
            // console.log(mes);
            let user_email = splitted_questions[0];
            user_email = user_email.substring(user_email.indexOf('{')+1, user_email.length);
            const question_id = splitted_questions[1];
            const message_date = splitted_questions[2];
            const answer_date = splitted_questions[3];
            const message = splitted_questions[4];
            const answer = splitted_questions[5];
            let has_answer = splitted_questions[6];
            has_answer = has_answer.substring(0,has_answer.length-1);
            const left_side = [];
            const right_side = [];
            

            rows.push(user_email.split("="));
            rows.push(question_id.split("="));
            rows.push(message_date.split("="));
            rows.push(answer_date.split("="));
            rows.push(answer.split("="));
            rows.push(has_answer.split("="));
            console.log(rows);



            left_side.push(user_email.split("=")[0]);
            left_side.push(question_id.split("=")[0]);
            left_side.push(message_date.split("=")[0]);
            left_side.push(answer_date.split("=")[0]);
            left_side.push(answer.split("=")[0]);
            left_side.push(has_answer.split("=")[0]);

            right_side.push(user_email.split("=")[1]);
            right_side.push(question_id.split("=")[1]);
            right_side.push(message_date.split("=")[1]);
            right_side.push(answer_date.split("=")[1]);
            right_side.push(answer.split("=")[1]);
            right_side.push(has_answer.split("=")[1]);



            console.log(user_email);
            console.log(question_id);
            console.log(message_date);
            console.log(answer_date);
            console.log(answer);


            console.log(left_side);
            console.log(right_side);
            const que=new Question (question_id, message_date, answer_date, message, answer, has_answer, user_email);
            final_questions.push(que);
                });
            this.setState({
                questions:final_questions,
                    })
            this.setState({
                rows:rows,
                    })


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
                    
                        <row><h1>User Questions (Admin)</h1></row>
                        <BasicTable rowss = {this.state.rows}></BasicTable>
                        {/* {[0,1,2,3,4,5,6,7].map((item) => (
                            <Card >
                            {item}
                            </Card>
                        ))} */}
                        {this.state.questions.length == 0 ? <h3 style={{ color: 'red' }}>No Questions To Show</h3> : 
                        this.state.questions.map((question) => (
                            <Card >
                            <h3 style={{ color: 'blue' }}> {question.email}</h3>
                            {/* (question_id, message_date, answer_date, message, answer, has_answer, user_email); */}
                            <div> {question.question_id}</div>
                            <div> {question.message_date}</div>
                            <div> {question.answer_date}</div>
                            <div> {question.message}</div>
                            <div> {question.answer}</div>
                            <div> {question.has_answer}</div>
                            {/* <div> question : {question}</div> */}
                           

                            </Card>
                        ))}


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









