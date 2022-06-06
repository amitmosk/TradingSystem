import React, { Component } from 'react';
import Button from '@mui/material/Button';
import Link from '@mui/material/Button';
import HomeIcon from '@mui/icons-material/Home';
import Snackbar from "@mui/material/Snackbar";
import Alert from "@mui/material/Alert"; 
import { Utils } from '../ServiceObjects/Utils';
import { Payment } from '../ServiceObjects/Payment';
import { Supply } from '../ServiceObjects/Supply';
export default class PaymentPage extends Component {
    static displayName = PaymentPage.name;

    constructor(props) {
        super(props);
        this.state = { 
            paymentError: undefined,
            creditnumber: "",
            month_year: "",
            holder: "",
            ccv: "",
            id: "",
            snackbar: null,
        };
        this.handleInputChange = this.handleInputChange.bind(this);

    }

    handleInputChange(event){
        const target = event.target;
        console.log(target.name)
        console.log(target.value)
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

    async check_payment(){
        let creditnumber = this.state.creditnumber;
        let ccv = this.state.ccv;
        let holder = this.state.holder;
        let id = this.state.id;
        const date = this.state.month_year.split('-');
        let month = date[1];
        let year = date[0];
        console.log(creditnumber)
        console.log(month)
        console.log(year)
        console.log(holder)
        console.log(ccv)
        console.log(id)
        // TODO : check input
        if (!Utils.check_credit_number(creditnumber))
        {
            this.setState({ snackbar: { children: "Illegal Payment Info", severity: "error" } });
            return;
        }
        if (!Utils.check_month(month))
        {
            this.setState({ snackbar: { children: "Illegal Payment Info", severity: "error" } });
            return;
        }
        if (!Utils.check_year_later(year))
        {
            this.setState({ snackbar: { children: "Illegal Payment Info", severity: "error" } });
            return;
        }
        if (!Utils.check_holder(holder))
        {
            this.setState({ snackbar: { children: "Illegal Payment Info", severity: "error" } });
            return;
        }
        if (!Utils.check_ccv(ccv))
        {
            this.setState({ snackbar: { children: "Illegal Payment Info", severity: "error" } });
            return;
        }
        if (!Utils.check_id(id))
        {
            this.setState({ snackbar: { children: "Illegal Payment Info", severity: "error" } });
            return;
        }


        const payment_info = Payment.create(creditnumber, month, year, holder, ccv, id);
        this.props.update(payment_info);


    }
    
    render() {
        const {redirectTo} = this.state
            return (
                <main class="LoginMain">
                    <div class="LoginWindow">
                        <h3>Payment</h3>
                        <form class="LoginForm" onSubmit={this.handleSubmit}>
                            {this.state.loginError ?
                                <div class="CenterItemContainer"><label>{this.state.loginError}</label></div> : null}
                            <input type="creditnumber" name="creditnumber" value={this.state.creditnumber} onChange={this.handleInputChange}
                                    placeholder="Credit Number" required/>
                            <input type="month" name="month_year" value={this.state.month_year} onChange={this.handleInputChange}
                                    placeholder="mm/yyyy" required/>
                            <input type="holder" name="holder" value={this.state.holder} onChange={this.handleInputChange}
                                placeholder="Holder" required/>
                            <input type="ccv" name="ccv" value={this.state.ccv} onChange={this.handleInputChange}
                                placeholder="CCV" required/>
                            <input type="id" name="id" value={this.state.id} onChange={this.handleInputChange}
                                placeholder="ID" required/>
                           
                            {/* <select name="role" value={this.state.role} required>
                                <option value="member">Member</option>
                                <option value="admin">Admin</option>
                            </select> */}
                            <div className="ConnectRegister">
                                
                                {/* <Link to="/register">Create new account</Link> */}
                                <Button onClick={() => this.check_payment()} variant="contained">Confirm</Button>
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