
     
import React from 'react';
import { useParams } from 'react-router-dom';
import ViewBidsStatus from './ViewBidsStatus';


export default function ViewBidsStatusNevigator() {
    const {id} = useParams();
    console.log("store page ViewBidsStatusNevigator-> store id ="+id);
    return (<ViewBidsStatus store_id={id}></ViewBidsStatus>);
    }






