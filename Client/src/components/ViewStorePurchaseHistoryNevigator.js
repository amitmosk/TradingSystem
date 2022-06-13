
     
import React from 'react';
import { useParams } from 'react-router-dom';
import ViewStorePurchaseHistory from './ViewStorePurchaseHistory';

export default function ViewStorePurchaseHistoryNevigator() {
    const {id} = useParams();
    console.log("in View Store Purchase History Nevigator-> id ="+id);
    return (<ViewStorePurchaseHistory store_id={id}></ViewStorePurchaseHistory>);
    }






