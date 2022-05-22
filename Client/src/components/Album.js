import React, { Component } from 'react';
import PrimarySearchAppBar from '@material-ui/core/AppBar';
import Button from '@material-ui/core/Button';
import Card from '@material-ui/core/Card';
import CardActions from '@material-ui/core/CardActions';
import CardContent from '@material-ui/core/CardContent';
import CardMedia from '@material-ui/core/CardMedia';
import CssBaseline from '@material-ui/core/CssBaseline';
import Grid from '@material-ui/core/Grid';
import Toolbar from '@material-ui/core/Toolbar';
import Typography from '@material-ui/core/Typography';
import { makeStyles } from '@material-ui/core/styles';
import Link from '@mui/material/Button';
import MenuListComposition from './MenuListComposition';
import { Container, Row, Col } from 'react-grid-system';
import Header from "./Header"
import { ConnectApi } from '../API/ConnectApi';
import { ProductApi } from '../API/ProductApi';
import { ThemeProvider, createMuiTheme } from '@mui/material/styles';
import { withStyles } from '@material-ui/styles';
import HomePageSearch from './HomePageSearch';
import AppBar from "@mui/material/AppBar";
import { StoreApi } from '../API/StoreApi';
import AccountMenu from './AccountMenu';
import FormDialog from './FormDialog';
import { AdminApi } from '../API/AdminApi';
//import MenuItem from '@material-ui/core';
//const useStyles = makeStyles((theme) => ({
const useStyles = makeStyles(theme => ({
    icon: {
        marginRight: theme.spacing(2),
    },
    heroContent: {
        backgroundColor: theme.palette.background.paper,
        padding: theme.spacing(8, 0, 6),
    },
    heroButtons: {
        marginTop: theme.spacing(4),
    },
    cardGrid: {
        paddingTop: theme.spacing(8),
        paddingBottom: theme.spacing(8),
    },
    card: {
        height: '100%',
        display: 'flex',
        flexDirection: 'column',
    },
    cardMedia: {
        paddingTop: '56.25%', // 16:9
    },
    cardContent: {
        flexGrow: 1,
    },
    footer: {
        backgroundColor: "theme.palette.background.paper",
        padding: theme.spacing(6),
    },
}));

const cards = [1, 2, 3, 4, 5, 6];


class Album extends React.Component {

        constructor(props) {
            super(props);
            this.state = {
                username: "Guest",
                stores:[],
                open_store_fields:["Store name"],
                send_question_to_admin_fields:["Enter your question"],

            };
            this.connectAPI = new ConnectApi();
            this.productApi = new ProductApi();
            this.storeApi = new StoreApi();
            this.adminApi = new AdminApi();
        }
        async componentDidMount() {
            console.log("in component did mount");
            this.setState({
                username:this.props.user_name
            });
            // let response = await this.storeApi.get_all_stores();
            // let stores = response.value;
            // this.setState({
            //     stores:stores,
            // });
            //TODO: Show Stores
        }
        copyright() {
            return (
                <Typography variant="body2" color="textSecondary" align="center">
                    {'Copyright © '}
                    <Link color="inherit" href="https://mui.com/">
                        Market System
                    </Link>{' '}
                    {new Date().getFullYear()}
                    {'.'}
                </Typography>
            );
        }
        async open_store(values) {
            const store_name = values[0];
            let response = await this.storeApi.open_store(store_name);
            if (!response.was_exception)
            {
               
                

            }
            else
            {
                if (response.value == "CATCH")
                {
                    //catch - connection error
                    alert(response.message)
                }
                else
                {
                    //then - but operation failed
                }

            }
        }
        async send_question_to_admin(values) {
            const question = values[0];
            let response = await this.adminApi.send_question_to_admin(question);
            if (!response.was_exception)
            {

            }
            else
            {

            }
        }
        



    render() {
 
            //const classes = useStyles();
            const { classes } = this.props;


            return (
                <React.Fragment>
                    <CssBaseline />
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


                    <main>
                        <sideBar />
                        <Container>
                            <Row><Col></Col>
                                <Col xs={5}>
                                    <Header title="Welcome To Ebay"></Header>
                                </Col>
                                <Col></Col>
                            </Row>
                            <Row>
                                
                            </Row>
                            <Row>
                            {/* <FormDialog fields={this.state.open_store_fields} getValues={this.open_store.bind(this)} name="Open Store"></FormDialog> */}
                            {/* <FormDialog fields={this.state.send_question_to_admin_fields} getValues={this.send_question_to_admin.bind(this)} name="Send question to admin"></FormDialog> */}
                                <Col><row><MenuListComposition item1="Open Store" item2="Send Complaint" item3={ <Link href="/AdminPage" underline="hover" >
                                {'Admin Operations'}
                                </Link>}>
                                </MenuListComposition></row> </Col>
                                <Col xs={7}>
                                <HomePageSearch/>
                                <Header title="Stores"></Header>
                                    <Container className={classes.cardGrid} maxWidth="md">
                                        {/* End hero unit */}

                                        <Grid container spacing={4}>

                                            {cards.map((card) => ( //switch crads with this.state.stores
                                                <Grid item key={card} xs={12} sm={6} md={4}>

                                                    <Card className={classes.card}>
                                                        <CardMedia
                                                            className={classes.cardMedia}
                                                            image="https://source.unsplash.com/random"
                                                            title="Image title"
                                                        />
                                                        <CardContent className={classes.cardContent}>
                                                            <Typography gutterBottom variant="h5" component="h2">
                                                                Heading
                                                            </Typography>

                                                        </CardContent>
                                                        <CardActions>
                                                            <Button size="small" color="primary">
                                                                View
                                                            </Button>
                                                            <Button size="small" color="primary">
                                                                Edit
                                                            </Button>
                                                        </CardActions>
                                                    </Card>
                                                </Grid>
                                            ))}
                                        </Grid>
                                    </Container>
                                </Col>
                               

                                <Row><Col></Col>
                                    <Col>
                                        
                                    </Col>

                                </Row>
                                <Row><Col></Col><Col xs={7}>
                                   
                                </Col>
                                    <Col></Col></Row>
                            </Row>

                        </Container>


                    </main>

                    {/* Footer */}
                    <footer className={classes.footer}>
                        <Typography variant="h6" align="center" gutterBottom>
                            Footer
                        </Typography>
                        <Typography variant="subtitle1" align="center" color="textSecondary" component="p">
                            Something here to give the footer a purpose!
                        </Typography>
                        <copyright />
                    </footer>
                    {/* End footer */}
                </React.Fragment >
            );
        }
    }
export default withStyles(useStyles)(Album);