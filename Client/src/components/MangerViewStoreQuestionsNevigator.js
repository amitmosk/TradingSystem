
     
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
import { StoreApi } from '../API/StoreApi';
import { useLocation, useParams } from 'react-router-dom';
import ManagerViewStoreQuestions from './ManagerViewStoreQuestions';


export default function MangerViewStoreQuestionsNevigator() {
    const {id} = useParams();
    console.log("in ManagerViewStoreQuestions nevigator");
    return (<ManagerViewStoreQuestions store_id={id}></ManagerViewStoreQuestions>);
    }






