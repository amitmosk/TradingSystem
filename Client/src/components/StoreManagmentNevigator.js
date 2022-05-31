
     
import React, { Component } from 'react';
import {useParams } from 'react-router-dom';
import StoreManagment from './StoreManagment';

export default function StoreManagmentNevigator() {
    const {id} = useParams();
    console.log("store managment nevigator-> id ="+id);
    return (<StoreManagment store_id={id}></StoreManagment>);
    }






