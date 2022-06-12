import React, { Component } from 'react';
import Button from '@mui/material/Button';
import Link from '@mui/material/Button';
import HomeIcon from '@mui/icons-material/Home';
import { Navigate } from 'react-router-dom'; 
import { AdminApi } from '../API/AdminApi';
import Snackbar from "@mui/material/Snackbar";
import Alert from "@mui/material/Alert";

export default class AdminSendMessage extends Component {
    static displayName = AdminSendMessage.name;

    constructor(props) {
        super(props);
        this.state = { 
            to: undefined,
            subject: undefined,
            content: undefined,
            snackbar: null,
        };
        this.adminApi = new AdminApi();
        this.handleInputChange = this.handleInputChange.bind(this);


    }
    
    handleInputChange(event){
        const target = event.target;
        this.setState({
            [target.name]: target.value
        });
    }
    
    async handleSubmit(event){
        event.preventDefault();
        const {username, password, role} = this.state;
        const loginRedirectAndRes = await this.authApi.Login(username, password, role);
        if(loginRedirectAndRes) {
            const loginRes = loginRedirectAndRes.data;

            if (loginRes && loginRes.isSuccess) {
                this.props.loginUpdateHandler(username, this.getUserRole(role))
            } else {
                this.setState({
                    loginError: loginRes.error
                })
            }
        } else {
            this.setState({
                loginError: "You need to be a guest"
            })
        }
    }
   



    
    async componentDidMount() {
    }

    async send(){
        const {to, subject, content} = this.state;
        console.log("to is "+to+" , subject is "+subject+", content is "+content+"\n");
        const question = subject+"\n"+content;
        let response = await this.adminApi.admin_answer_user_question(question);
        if (!response.was_exception)
        {
            this.setState({ snackbar: { children: response.message, severity: "success" } });
        }
        else{
            this.setState({ snackbar: { children: response.message, severity: "error" } });

        }
    }
    
   
    
    render() {
        const {redirectTo} = this.state
        if (this.state.submitted) {
            console.log("have to route to homepage whe it will be ready\n\n\n");
            return <Navigate to="/HomePageSearch"/>
        } else {
            return (
                <main className="LoginMain">
                    <div className="LoginWindow">
                    <Link href="/"><HomeIcon></HomeIcon></Link>
                        <h3>Send Messages - (Admin)</h3>
                        <form className="LoginForm" >
                            {this.state.loginError ?
                                <div className="CenterItemContainer"><label>{this.state.loginError}</label></div> : null}
                            <div>To</div>
                            <input type="text" name="email" value={this.state.email} onChange={this.handleInputChange.bind(this)}
                                    placeholder="Email" required/>
                            <div>Subject</div>
                            <input type="password" name="password" value={this.state.password} onChange={this.handleInputChange.bind(this)}
                                    placeholder="Password" required/>
                                    {/* <InputBox /> */}
                            
                            <div className="ConnectRegister">
                                
                                <Button onClick={() => this.send()} variant="contained">Send </Button>
                                
                            </div>
                        </form>
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
}