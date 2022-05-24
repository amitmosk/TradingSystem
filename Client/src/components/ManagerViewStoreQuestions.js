
     
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
import { UserApi } from '../API/UserApi';
import { StoreApi } from '../API/StoreApi';
import { useLocation, useParams } from 'react-router-dom';







export default function ManagerViewStoreQuestions() {
    const manager_view_store_questions = async (store_id) => {   
        const storeApi = new StoreApi(); 
        console.log("in manager_view_store_questions!\n");
        const response = await storeApi.manager_view_store_questions(store_id);
        const questions = response.value;
        alert(response.message);
        console.log(response);
        console.log(response.was_exception);
        if (!response.was_exception) {
    
            console.log("in UserViewQuestions - success!\n");    
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
                return final_questions
    
    
        }
        else {
            console.log("in else");
            return ([]);
        }
    }
    // console.log(location);
    // console.log(location.state);
    let c= useParams();
    let d = useLocation();
    console.log("printt");
    console.log(d);
    console.log(c);
    // const {id} = useParams();
    // console.log("id = "+id);
    const {id} = useParams();
    console.log(id);
    const questions = manager_view_store_questions(id);
    console.log("questions ============ ");
    console.log("questions = "+questions);
    // const questions = [];
    
    const answer_user_questions_fields = ["question_id", "answer"];

            return (
                <main class="LoginMain">
                    <div class="LoginWindow">
                    <Link href="/"><HomeIcon></HomeIcon></Link>
                        <row><h1>User Questions </h1></row>
                        {/* {[0,1,2,3,4,5,6,7].map((item) => (
                            <Card >
                            {item}
                            </Card>
                        ))} */}
                                   {[0,1,2,3,4,5,6,7].length == 0 ? <h3 style={{ color: 'red' }}>No Questions To Show</h3> : 
                        [0,1,2,3,4,5,6,7].map((question) => (
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
                         {/* <FormDialog outlinedVar="text" fields={answer_user_questions_fields} getValues={manager_answer_question} name="Answer Users Questions"></FormDialog> */}
        
                            
                       
                    </div>
                </main>
            );
        
    }







