import React, { Component } from 'react';
import Button from '@mui/material/Button';
import Link from '@mui/material/Button';
import HomeIcon from '@mui/icons-material/Home';
import Snackbar from "@mui/material/Snackbar";
import Alert from "@mui/material/Alert"; 

export default class Payment extends Component {
    static displayName = Payment.name;

    constructor(props) {
        super(props);
        this.state = { 
            paymentError: undefined,
            creditnumber: undefined,
            experationdate: undefined,
            cvv: undefined,
            snackbar: null,
        };

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

    async pay(){
        let creditnumber = this.state.creditnumber;
        let experationdate = this.state.experationdate;
        let cvv = this.state.cvv;
        console.log("creditnumber is "+creditnumber+" , experationdate is "+experationdate+", experationdate is "+experationdate+" cvv is "+cvv+"\n");

    }
    
    render() {
        const {redirectTo} = this.state
            return (
                <main class="LoginMain">
                    <div class="LoginWindow">
                    <Link href="/"><HomeIcon></HomeIcon></Link>
                        <h3>Payment</h3>
                        <form class="LoginForm" onSubmit={this.handleSubmit}>
                            {this.state.loginError ?
                                <div class="CenterItemContainer"><label>{this.state.loginError}</label></div> : null}
                            <input type="creditnumber" name="creditnumber" value={this.state.creditnumber}
                                    placeholder="Credit Number" required/>
                            <input type="experationdate" name="experationdate" value={this.state.experationdate}
                                    placeholder="Experation Date" required/>
                            <input type="cvv" name="cvv" value={this.state.cvv}
                                    placeholder="CVV" required/>
                           
                            {/* <select name="role" value={this.state.role} required>
                                <option value="member">Member</option>
                                <option value="admin">Admin</option>
                            </select> */}
                            <div className="ConnectRegister">
                                
                                {/* <Link to="/register">Create new account</Link> */}
                                <Button onClick={() => this.pay()} variant="contained">Pay </Button>
                                {/* <input class="action" type="submit" value="Login"/> */}
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
                        </form>
                    </div>
                </main>
            );
        
    }
}