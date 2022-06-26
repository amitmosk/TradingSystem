import React, { Component } from 'react';
import FormDialog from './FormDialog';
import { AdminApi } from '../API/AdminApi';
import { Question } from '../ServiceObjects/Question';
import Snackbar from "@mui/material/Snackbar";
import Alert from "@mui/material/Alert";
import BasicTable from './BasicTable';
import Grid from '@mui/material/Grid';


export default class AdminViewUserQuestions extends Component {
    static displayName = AdminViewUserQuestions.name;
    constructor(props) {
        super(props);
        this.state = {
            questions: [],
            snackbar: null,
            rows: [],
        };
        this.adminApi = new AdminApi();
        this.admin_answer_user_question_fields = ["Question ID", "Answer"];

    }


    async admin_answer_user_question(values) {
        console.log("in admin answer user question user!\n");
        const question_id = values[0];
        const answer = values[1];
        const response = await this.adminApi.admin_answer_user_question(question_id, answer);
        if (!response.was_exception) {
            this.setState({ snackbar: { children: response.message, severity: "success" } });
            window.location.reload();
        }
        else {
            this.setState({ snackbar: { children: response.message, severity: "error" } });
        }
    }

    async componentDidMount() {
        this.setState({ rows: [] });
        console.log("in admin_view_users_questions!\n");
        const response = await this.adminApi.admin_view_users_questions();
        const questions = response.value;
        console.log("questions = " + questions);
        // alert(response.message);
        if (!response.was_excecption) {
            //   this.setState({ snackbar: { children: response.message, severity: "success" } });
            console.log("in AdminViewUserQuestions - success!\n");
            console.log(response);
            const final_questions = [];
            let splitted_questions = [];
            let rows = [];
            const all_rows = [];
            questions.map(q => {
                splitted_questions = q.split(",");
                console.log("spplited= " + splitted_questions);

                // console.log(mes);
                let user_email = splitted_questions[0];
                user_email = user_email.substring(user_email.indexOf('{') + 1, user_email.length);
                const question_id = splitted_questions[1];
                const message_date = splitted_questions[2];
                const answer_date = splitted_questions[3];
                const message = splitted_questions[4];
                const answer = splitted_questions[5];
                let has_answer = splitted_questions[6];
                has_answer = has_answer.substring(0, has_answer.length - 1);
                const left_side = [];
                const right_side = [];

                rows.push(question_id.split("="));
                rows.push(user_email.split("="));
                rows.push(message_date.split("="));
                rows.push(message.split("="));
                rows.push(answer_date.split("="));
                rows.push(answer.split("="));
                rows.push(has_answer.split("="));
                all_rows.push(rows);
                rows = [];


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
                const que = new Question(question_id, message_date, answer_date, message, answer, has_answer, user_email);
                final_questions.push(que);
            });
            console.log(all_rows);

            this.setState({
                questions: final_questions,
            })
            this.setState({
                rows: all_rows,
            })


        }
        else {
            this.setState({ snackbar: { children: response.message, severity: "error" } });
        }
    }


    render() {
        return (
            <main >
                <div class="LoginWindow">

                    <row><h1>User Questions (Admin)</h1></row>

                    <Grid container spacing={6} paddingRight={25} paddingLeft={25} paddingTop={10}>
                        {this.state.questions.length == 0 ? <h3 style={{ color: 'red' }}>No Questions To Show</h3> :
                            this.state.rows.map(rows => <BasicTable rowss={rows}></BasicTable>)
                        }

                    </Grid>
                    {this.state.questions.length !== 0 ? <FormDialog fields={this.admin_answer_user_question_fields} getValues={this.admin_answer_user_question.bind(this)} name="Answer Question"></FormDialog> : null}

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









