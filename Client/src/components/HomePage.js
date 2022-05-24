import React, { Component } from 'react';
import Typography from '@material-ui/core/Typography';
import Link from '@mui/material/Button';
import { ConnectApi } from '../API/ConnectApi';
import { ProductApi } from '../API/ProductApi';
import { Container } from '@mui/material';
import Box from '@mui/material/Box';
import { AdminApi } from '../API/AdminApi';
import { StoreApi } from '../API/StoreApi';
import PrimarySearchAppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import Grid from '@mui/material/Grid';
import HomePageSearch from './HomePageSearch';
import Header from "./Header";
import FormDialog from './FormDialog';
import AccountMenu from './AccountMenu';
import Card from '@material-ui/core/Card';
import CardActions from '@material-ui/core/CardActions';
import CardContent from '@material-ui/core/CardContent';
import CardMedia from '@material-ui/core/CardMedia';
import ShoppingCart from './ShoppingCart';
import { Row, Col } from 'react-grid-system';
import MenuListComposition from './MenuListComposition';
import NavBar from './NavBar';
// const Copyright = {
//     return (
//         <Typography variant="body2" color="textSecondary" align="center">
//             {'Copyright © '}
//             <Link color="inherit" href="https://mui.com/">
//                 Market System
//             </Link>{' '}
//             {new Date().getFullYear()}
//             {'.'}
//         </Typography>
//     )
// }

const style = {
    width: '100%',
    maxWidth: 360,
    bgcolor: 'background.paper',
};


export default class HomePage extends Component {

    constructor(props) {
        super(props);
        this.state = {
            username: "Guest",
            stores: [],
            open_store_fields: ["Store name"],
            send_question_to_admin_fields: ["Enter your question"],
            products: [],

        };
        this.connectAPI = new ConnectApi();
        this.productApi = new ProductApi();
        this.storeApi = new StoreApi();
        this.adminApi = new AdminApi();
        this.logout = this.logout.bind(this);
    }
    async componentDidMount() {
        console.log("in component did mount");
        // this.setState({
        //     username: this.props.user_name
        // });
        // let response = await this.storeApi.get_all_stores();
        // let stores = response.value;
        // this.setState({
        //     stores:stores,
        // });
        //TODO: Show Stores
    }
    async open_store(values) {
        const store_name = values[0];
        let response = await this.storeApi.open_store(store_name);
        alert(response.message);
        if (!response.was_exception) {

        }
        else {

        }
    }

      async logout(){
        console.log("in logout home page");
        let response = await this.connectAPI.logout();
        
        alert(response.message);
        if (!response.was_exception)
        {
            const user = response.value;
            this.props.updateUserState(user);
        }
      }

      
    async send_question_to_admin(values) {
        const question = values[0];
        let response = await this.adminApi.send_question_to_admin(question);
        alert(response.message);
        if (!response.was_exception) {

        }
        else {

        }
    }
    show_products(products) {
        console.log("in show product");
         this.setState({
            products:products,
        });
    //    return (<ShoppingCart products={products}></ShoppingCart>);
        return (<ShoppingCart products={this.state.products}></ShoppingCart>);
    }

    render() {

        return (
            <>
                <Container>

                    <Box sx={{ flexGrow: 1 }}>


                        <h1 class="Header" align="center">
                            Welcome To Ebay
                        </h1>
                        {/* <HomePageSearch sx={{ height: '5%' }} /> */}
                    </Box>
                    <Header position="center" align="center" title="Our products"></Header>
                    <Grid align="center">
                    {/* <Row><ShoppingCart/></Row> */}
                    <Row><h1 style={{color: "white"}}>operations</h1></Row>
                    <Row>
                        <Col><FormDialog fields={this.state.open_store_fields} getValues={this.open_store.bind(this)} name="Open Store"></FormDialog></Col>
                        <Col><FormDialog fields={this.state.send_question_to_admin_fields} getValues={this.send_question_to_admin.bind(this)} name="Send question to admin"></FormDialog></Col>
                        <Col><Link href="/AdminPage" underline="hover" >{'Admin Operations'}</Link></Col>
                    </Row>
                    </Grid>
                </Container>

            </>
        );
    }

}