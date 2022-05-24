
     
import React, { Component } from 'react';
import "./Login.css";
import Button from '@mui/material/Button';
import Link from '@mui/material/Button';
import HomeIcon from '@mui/icons-material/Home';
import { ConnectApi } from '../API/ConnectApi';
import Register from "./Register.js";
import Box from '@mui/material/Box';
import ImageListItem from '@mui/material/Box';
import ImageList from '@mui/material/Box';
import { StoreApi } from '../API/StoreApi';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemText from '@mui/material/ListItemText';
import ListSubheader from '@mui/material/ListSubheader';
import { CartApi } from '../API/CartApi';
import MenuListComposition from './MenuListComposition';
import { Container, Row, Col } from 'react-grid-system';
import { Paper } from '@mui/material';
import { Typography } from '@mui/material';
import Rating from '@mui/material/Rating';
import BasicRating from './Rating';
import ShoppingCart from './ShoppingCart';
import { ThirtyFpsRounded } from '@mui/icons-material';
import Grid from '@mui/material/Grid';
import FormDialog from './FormDialog';
import Card from '@mui/material/Card';
import { experimentalStyled as styled } from '@mui/material/styles';
import { AdminApi } from '../API/AdminApi';
import { Question } from '../ServiceObjects/Question';
const Item = styled(Paper)(({ theme }) => ({
    backgroundColor: theme.palette.mode === 'dark' ? '#1A2027' : '#fff',
    ...theme.typography.body2,
    padding: theme.spacing(2),
    textAlign: 'center',
    color: theme.palette.text.secondary,
}));
  
export default class AdminViewUserQuestions extends Component {
    static displayName = AdminViewUserQuestions.name;
    constructor(props) {
        super(props);
        this.state = { 
            questions:[],
        };
        this.adminApi = new AdminApi();

    }    

    
    
    
    async componentDidMount() {
        console.log("in admin_view_users_questions!\n");
        const response = await this.adminApi.admin_view_users_questions();
        const questions = response.value;
        console.log("questions = "+questions);
        alert(response.message);
        if (!response.was_excecption) {

            console.log("in AdminViewUserQuestions - success!\n");
            console.log(response);
            
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
            


            // console.log("qqqqq");
            // console.log(questions);
            // console.log(questions[0]);
            // console.log(questions[0].split(","));
            // console.log(final_questions);
            
            // console.log(questions_arr);
           


        }
        else {

        }
    }
      

    render() {
        const {redirectTo} = this.state
            return (
                <main class="LoginMain">
                    <div class="LoginWindow">
                    <Link href="/"><HomeIcon></HomeIcon></Link>
                        <row><h1>User Questions (Admin)</h1></row>
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


                       


                        
                        

                           
                      


                     

                                   
                            
                       
                    </div>
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