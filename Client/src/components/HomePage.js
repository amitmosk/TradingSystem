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
    }
    async componentDidMount() {
        console.log("in component did mount");
        this.setState({
            username: this.props.user_name
        });
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
        if (!response.was_exception) {

        }
        else {

        }
    }
    async send_question_to_admin(values) {
        const question = values[0];
        let response = await this.adminApi.send_question_to_admin(question);
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
                <PrimarySearchAppBar position="relative" color="white">
                    <Toolbar>
                        <Typography variant="h6" color="inherit" noWrap>
                            Hello {this.state.username},
                        </Typography>

                        <Link
                            href="/Login"
                            component="button"
                            variant="body2"
                            position="right"
                            onClick={() => {
                                console.info("I'm Login button, add link.");
                            }}
                        >
                            Login
                        </Link>
                        <Link
                            href="/Register"
                            component="button"
                            variant="body2"
                            position="right"
                            onClick={() => {

                                console.info("I'm Register button, add link.");
                            }}
                        >
                            \ Register
                        </Link>
                        <AccountMenu />

                    </Toolbar>

                </PrimarySearchAppBar>
                <Container>

                    <Box sx={{ flexGrow: 1 }}>


                        <h3 class="Header" align="center">
                            Welcome To Ebay
                        </h3>
                        <HomePageSearch show_products={this.show_products.bind(this)} sx={{ height: '5%' }} />
                    </Box>
                    <Grid container>
                        
                        <Col><row><MenuListComposition item1={<FormDialog fields={this.state.open_store_fields} getValues={this.open_store.bind(this)} name="Open Store"></FormDialog>} 
                        item2={<FormDialog fields={this.state.send_question_to_admin_fields} getValues={this.send_question_to_admin.bind(this)} name="Send question to admin"></FormDialog>} 
                        item3={ <Link href="/AdminPage" underline="hover" >
                                {'Admin Operations'}
                                </Link>}>
                                </MenuListComposition></row> </Col>
                        <Header position="right" align="right" title="Stores"></Header>
                        {/* {this.show_products()} */}

                    </Grid>
                </Container>

            </>
        );
    }

}