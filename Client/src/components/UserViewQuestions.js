

import React, { Component } from 'react';
import Link from '@mui/material/Button';
import HomeIcon from '@mui/icons-material/Home';
import Card from '@mui/material/Card';
import { Question } from '../ServiceObjects/Question';
import { UserApi } from '../API/UserApi';
import Snackbar from "@mui/material/Snackbar";
import Alert from "@mui/material/Alert";

export default class UserViewQuestions extends Component {
    static displayName = UserViewQuestions.name;
    constructor(props) {
        super(props);
        this.state = {
            questions: [],
            snackbar: null,
        };
        this.userApi = new UserApi();

    }




    async componentDidMount() {
        console.log("in UserViewQuestions!\n");
        const response = await this.userApi.get_user_questions();
        const questions = response.value;
        console.log("questions = " + questions);
        // alert(response.message);
        if (!response.was_excecption) {
            if (response.message == "The system is not available right now, come back later")
                this.setState({ snackbar: { children: response.message, severity: "success" } });
            console.log("in UserViewQuestions - success!\n");
            console.log(response);
            const final_questions = [];
            let splitted_questions = [];
            questions.map(q => {
                splitted_questions = q.split(",");
                console.log("spplited= " + splitted_questions);
                const user_email = splitted_questions[0];
                const question_id = splitted_questions[1];
                const message_date = splitted_questions[2];
                const answer_date = splitted_questions[3];
                const message = splitted_questions[4];
                const answer = splitted_questions[5];
                const has_answer = splitted_questions[6];
                const que = new Question(question_id, message_date, answer_date, message, answer, has_answer, user_email);
                final_questions.push(que);
            });
            this.setState({
                questions: final_questions,
            })


        }
        else {
            this.setState({ snackbar: { children: response.message, severity: "error" } });
        }
    }


    render() {
        const { redirectTo } = this.state
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




                </div>
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
            </main>
        );

    }
}












{/* <Typography component="legend">Controlled</Typography>
      <Rating
        name="simple-controlled"
        value={value}
        onChange={(event, newValue) => {
          setValue(newValue);
        }}
      />
      <Typography component="legend">Read only</Typography>
      <Rating name="read-only" value={value} readOnly />
      <Typography component="legend">Disabled</Typography>
      <Rating name="disabled" value={value} disabled />
      <Typography component="legend">No rating given</Typography>
      <Rating name="no-value" value={null} /> */}