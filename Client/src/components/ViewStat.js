
     
import React, { Component } from 'react';
import Link from '@mui/material/Button';
import HomeIcon from '@mui/icons-material/Home';
import Card from '@mui/material/Card';
import { AdminApi } from '../API/AdminApi';
import Snackbar from "@mui/material/Snackbar";
import Alert from "@mui/material/Alert"; 


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
            snackbar: null,
            
        };
        this.adminApi = new AdminApi();

    }    

    
    async componentDidMount() {
        console.log("in get market stat\n");
        let response = await this.adminApi.get_market_stats();
        console.log("response = " + response);
        const stats = response.value;
        // alert(response.message);
        if (!response.was_excecption) {
            this.setState({ snackbar: { children: response.message, severity: "success" } });
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
            this.setState({ snackbar: { children: response.message, severity: "error" } });

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






