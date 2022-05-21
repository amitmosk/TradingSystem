import React from 'react';
import PrimarySearchAppBar from '@material-ui/core/AppBar';
// import AppBar from 'material-ui/AppBar';
import Button from '@material-ui/core/Button';
import AccountCircleIcon from '@material-ui/icons/AccountCircle';
import Card from '@material-ui/core/Card';
import CardActions from '@material-ui/core/CardActions';
import CardContent from '@material-ui/core/CardContent';
import CardMedia from '@material-ui/core/CardMedia';
import CssBaseline from '@material-ui/core/CssBaseline';
import Grid from '@material-ui/core/Grid';
import Toolbar from '@material-ui/core/Toolbar';
import Typography from '@material-ui/core/Typography';
import { makeStyles } from '@material-ui/core/styles';
//import Container from '@material-ui/core/Container';
import Link from '@material-ui/core/Link';
//import PrimarySearchAppBar from '@material-ui/core'
import { ButtonGroup } from '@material-ui/core';
import MenuListComposition from './MenuListComposition';
import { Container, Row, Col } from 'react-grid-system';
import MenuList from '@material-ui/core/MenuList';
import Header from "./Header"

//import MenuItem from '@material-ui/core';

function Copyright() {
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

const useStyles = makeStyles((theme) => ({
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

export default function Album() {
    const classes = useStyles();

    return (
        <React.Fragment>
            <CssBaseline />
            <PrimarySearchAppBar position="relative" color="white">
                <Toolbar>
                    <Typography variant="h6" color="inherit" noWrap>
                        Hello Guest,
                    </Typography>
                    <Link
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
                        component="button"
                        variant="body2"
                        position="right"
                        onClick={() => {
                            console.info("I'm Register button, add link.");
                        }}
                    >
                        \ Register
                    </Link>
                </Toolbar>
            </PrimarySearchAppBar>


            <main>
                <sideBar />
                {/* Hero unit */}
                {/* <div className={classes.heroContent}>
                    <Container maxWidth="sm">
                        <Typography component="h1" variant="h2" align="center" color="textPrimary" gutterBottom>
                            Featured Categories
                        </Typography>
                        <div className={classes.heroButtons}>
                           
                        </div>
                    </Container>
                </div> */}





                <Container>
                    <Row><Col></Col>
                        <Col xs={5}>
                            <Header title="Featured Categories"></Header>
                        </Col>
                        <Col></Col>
                    </Row>
                    <Row>
                        <Col><row><MenuListComposition item1="Open Store" item2="Send Complaint" item3="Admin Operations">
                        </MenuListComposition></row> </Col>
                        <Col xs={7}>
                            <Container className={classes.cardGrid} maxWidth="md">
                                {/* End hero unit */}

                                <Grid container spacing={4}>

                                    {cards.map((card) => (
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
                        <Col><MenuListComposition item1="My Acount" item2="My Cart" item3="Purchase History">
                        </MenuListComposition>
                        </Col>

                        <Row><Col></Col>
                            <Col>
                                <Header title="Stores"></Header>
                            </Col>

                        </Row>
                        <Row><Col></Col><Col xs={7}>
                            <Container className={classes.cardGrid} maxWidth="md">
                                {/* End hero unit */}

                                <Grid container spacing={4}>

                                    {cards.map((card) => (
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
                <Copyright />
            </footer>
            {/* End footer */}
        </React.Fragment >
    );
}