
     
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
import { UserApi } from '../API/UserApi';
import { useParams } from 'react-router-dom';
const Item = styled(Paper)(({ theme }) => ({
    backgroundColor: theme.palette.mode === 'dark' ? '#1A2027' : '#fff',
    ...theme.typography.body2,
    padding: theme.spacing(2),
    textAlign: 'center',
    color: theme.palette.text.secondary,
}));
//   export default function ShoppingCart(data) {
    // export function AAA () {
    //     const { testvalue } = useParams();
    //     console.log(testvalue);
    // }

export default class ManagerViewStoreQuestions extends Component {

    static displayName = ManagerViewStoreQuestions.name;
    constructor(props) {
        super(props);
        this.state = { 
            questions:[],
            answer_user_questions_fields: ["question_id", "answer"],

        };
        this.storeApi = new StoreApi();
        // const testvalue = this.props.match.params.testvalue;
        console.log("yallllaaa");
        console.log(this.props);
        // console.log(this.props);
        // console.log(this.props.location);
        // console.log(this.props.location.state);
        
        // // console.log(testvalue);
        // console.log("params = " +this.props.parms);
        // console.log("params = " +this.props.parms.testvalue);

    }    

    
    
    
    async componentDidMount() {
        
        
        console.log("in manager_view_store_questions!\n");
        const store_id = this.props.store_id;
        console.log("store id = "+store_id);
        const response = await this.storeApi.manager_view_store_questions(store_id);
       
        console.log("in UserViewQuestions!\n");
        const questions = response.value;
        console.log("questions = "+questions);
        alert(response.message);
        if (!response.was_excecption) {

            console.log("in UserViewQuestions - success!\n");
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
    async manager_answer_question(values) {
        console.log("in manager_answer_question!\n");
        const store_id = this.state.store_id;
        const question_id = values[0];
        const answer = values[1];


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



                       


                        
                        

                           
                      


                     

                                   
                            
                       
                    </div>
                </main>
            );
        
    }
}










//==========================================================================================================
//function component











     



















// import React, { Component } from 'react';
// import "./Login.css";
// import Button from '@mui/material/Button';
// import Link from '@mui/material/Button';
// import HomeIcon from '@mui/icons-material/Home';
// import { ConnectApi } from '../API/ConnectApi';
// import Register from "./Register.js";
// import Box from '@mui/material/Box';
// import ImageListItem from '@mui/material/Box';
// import ImageList from '@mui/material/Box';
// import List from '@mui/material/List';
// import ListItem from '@mui/material/ListItem';
// import ListItemText from '@mui/material/ListItemText';
// import ListSubheader from '@mui/material/ListSubheader';
// import { CartApi } from '../API/CartApi';
// import MenuListComposition from './MenuListComposition';
// import { Container, Row, Col } from 'react-grid-system';
// import { Paper } from '@mui/material';
// import { Typography } from '@mui/material';
// import Rating from '@mui/material/Rating';
// import BasicRating from './Rating';
// import ShoppingCart from './ShoppingCart';
// import { ThirtyFpsRounded } from '@mui/icons-material';
// import Grid from '@mui/material/Grid';
// import FormDialog from './FormDialog';
// import Card from '@mui/material/Card';
// import { experimentalStyled as styled } from '@mui/material/styles';
// import { AdminApi } from '../API/AdminApi';
// import { Question } from '../ServiceObjects/Question';
// import { UserApi } from '../API/UserApi';
// import { StoreApi } from '../API/StoreApi';
// import { useLocation, useParams } from 'react-router-dom';







// export default function ManagerViewStoreQuestions() {
//     const manager_view_store_questions = async (store_id) => {   
//         const storeApi = new StoreApi(); 
//         console.log("in manager_view_store_questions!\n");
//         const response = await storeApi.manager_view_store_questions(store_id);
//         const questions = response.value;
//         alert(response.message);
//         console.log(response);
//         console.log(response.was_exception);
//         if (!response.was_exception) {
    
//             console.log("in UserViewQuestions - success!\n");    
//                   //
//                   //static create( question_id,  message_date,  answer_date,  message,  answer, has_answer) {
//             const final_questions=[];
//             let splitted_questions=[];
//             questions.map(q=>{ splitted_questions = q.split(",");
//             console.log("spplited= "+splitted_questions);
//             const user_email = splitted_questions[0];
//             const question_id = splitted_questions[1];
//             const message_date = splitted_questions[2];
//             const answer_date = splitted_questions[3];
//             const message = splitted_questions[4];
//             const answer = splitted_questions[5];
//             const has_answer = splitted_questions[6];
//             const que=new Question (question_id, message_date, answer_date, message, answer, has_answer, user_email);
//             final_questions.push(que);
//                 });
//             console.log("final questions = "+final_questions);
//             return final_questions;
    
    
//         }
//         else {
//             console.log("in else");
//             return ([]);
//         }
//     }
//     // console.log(location);
//     // console.log(location.state);
//     let c= useParams();
//     let d = useLocation();
//     console.log("printt");
//     console.log(d);
//     console.log(c);
//     // const {id} = useParams();
//     // console.log("id = "+id);
//     const {id} = useParams();
//     console.log(id);
//     const questions = manager_view_store_questions(id).then(res=>res.data).catch(err => undefined);
//     console.log("questions ============ ");
//     console.log("questions = "+questions);
//     // const questions = [];
    
//     const answer_user_questions_fields = ["question_id", "answer"];

//             return (
//                 <main class="LoginMain">
//                     <div class="LoginWindow">
//                     <Link href="/"><HomeIcon></HomeIcon></Link>
//                         <row><h1>User Questions </h1></row>
//                         {/* {[0,1,2,3,4,5,6,7].map((item) => (
//                             <Card >
//                             {item}
//                             </Card>
//                         ))} */}
//                                    {questions.length == 0 ? <h3 style={{ color: 'red' }}>No Questions To Show</h3> : 
//                         questions.map((question) => (
//                             <Card >
//                             <h3 style={{ color: 'blue' }}> {question.email}</h3>
//                             <div> {question.question_id}</div>
//                             <div> {question.message_date}</div>
//                             <div> {question.answer_date}</div>
//                             <div> {question.answer}</div>
//                             <div> {question.has_answer}</div>
//                             {/* <div> question : {question}</div> */}
                           

//                             </Card>
//                         ))}
//                          {/* <FormDialog outlinedVar="text" fields={answer_user_questions_fields} getValues={manager_answer_question} name="Answer Users Questions"></FormDialog> */}
        
                            
                       
//                     </div>
//                 </main>
//             );
        
//     }







