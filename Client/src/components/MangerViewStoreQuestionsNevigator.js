import React, { Component } from 'react';
import { useLocation, useParams } from 'react-router-dom';
import ManagerViewStoreQuestions from './ManagerViewStoreQuestions';


export default function MangerViewStoreQuestionsNevigator() {
    const {id} = useParams();
    console.log("in ManagerViewStoreQuestions nevigator, id = "+id);
    return (<ManagerViewStoreQuestions store_id={id}></ManagerViewStoreQuestions>);
    }






