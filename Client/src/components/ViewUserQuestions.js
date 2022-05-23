
     
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
const Item = styled(Paper)(({ theme }) => ({
    backgroundColor: theme.palette.mode === 'dark' ? '#1A2027' : '#fff',
    ...theme.typography.body2,
    padding: theme.spacing(2),
    textAlign: 'center',
    color: theme.palette.text.secondary,
}));
  
export default class ViewUserQuestions extends Component {
    static displayName = ViewUserQuestions.name;
    constructor(props) {
        super(props);
        this.state = { 
            questions:this.props.questions,
            answer_user_questions_fields:["Question ID"],
        };
        this.storeApi = new StoreApi();

    }    

    
    
    
    async componentDidMount() {
      
    }
      

        

        

        
    async manager_answer_question(values) {
        console.log("in manager_answer_question!\n");
        const store_id = values[0];
        const question_id = values[1];
        const answer = values[2];

        const response = await this.storeApi.manager_answer_question(store_id, question_id, answer);
        alert(response.message);
        if (!response.was_execption) {
            console.log("in manager_answer_question - success!\n");
            //show history
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
                        <row><h3>User Questions</h3></row>
                        {[0,1,2,3,4,5,6,7].map((item) => (
                            <Card >
                            {item}
                            </Card>
                        ))}
                        {this.state.questions.map((question) => (
                            <Card >

                            <div> question_id: {question.question_id}</div>
                            <div> message_date: {question.message_date}</div>
                            <div> answer_date: {question.answer_date}</div>
                            <div> answer: {question.answer}</div>
                            <div> has_answer: {question.has_answer}</div>

                            </Card>
                        ))}
                        <Grid item xs={3}>  <Item variant="outlined"> <FormDialog outlinedVar="text" fields={this.state.answer_user_questions_fields} getValues={this.manager_answer_question.bind(this)} name="Answer Users Questions"></FormDialog></Item ></Grid>


                       


                        
                        

                           
                      


                     

                                   
                            
                       
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