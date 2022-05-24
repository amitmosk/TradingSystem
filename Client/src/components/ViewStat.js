
     
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
const Item = styled(Paper)(({ theme }) => ({
    backgroundColor: theme.palette.mode === 'dark' ? '#1A2027' : '#fff',
    ...theme.typography.body2,
    padding: theme.spacing(2),
    textAlign: 'center',
    color: theme.palette.text.secondary,
}));
  
export default class ViewStat extends Component {
    static displayName = ViewStat.name;
    constructor(props) {
        super(props);
        this.state = { 
            stat:undefined,
            login_per_minutes:undefined,
            logout_per_minutes:undefined,
            connect_per_minutes:undefined,
            register_per_minutes:undefined,
            buy_cart_per_minutes:undefined,
            
        };
        this.adminApi = new AdminApi();

    }    

    
    async componentDidMount() {
        console.log("in get market stat\n");
        let response = await this.adminApi.get_market_stats();
        console.log("response = " + response);
        const stats = response.value;
        alert(response.message);
        if (!response.was_excecption) {

            console.log("in get market stats - success!\n");
            console.log(response);
            this.setState({
                login_per_minutes: stats.login_per_minutes,
                logout_per_minutes: stats.logout_per_minutes,
                connect_per_minutes: stats.connect_per_minutes,
                register_per_minutes: stats.register_per_minutes,
                buy_cart_per_minutes: stats.buy_cart_per_minutes
                  })

           


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
                        <row><h3>Market Statistics</h3></row>
                        {/* {[0,1,2,3,4,5,6,7].map((item) => (
                            <Card >
                            {item}
                            </Card>
                        ))} */}
                      <Card >

                    <div> init system time: {this.state.init_system_time}</div>
                    <div> login per minutes: {this.state.login_per_minutes}</div>
                    <div> logout per minutes: {this.state.logout_per_minutes}</div>
                    <div> connect per minutes: {this.state.connect_per_minutes}</div>
                    <div> register per minutes: {this.state.register_per_minutes}</div>
                    <div> buy cart per minutes: {this.state.buy_cart_per_minutes}</div>

</Card>
                        {/* <Grid item xs={3}>  <Item variant="outlined"> <FormDialog outlinedVar="text" fields={this.state.answer_user_questions_fields} getValues={this.manager_answer_question.bind(this)} name="Answer Users Questions"></FormDialog></Item ></Grid> */}


                                   
                            
                       
                    </div>
                </main>
            );
        
    }
}






