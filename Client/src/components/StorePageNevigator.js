
     
import React from 'react';
import { useParams } from 'react-router-dom';
import StorePage from './StorePage';


export default function StorePageNevigator() {
    const {id} = useParams();
    console.log("store page nevigator-> id ="+id);
    return (<StorePage store_id={id}></StorePage>);
    }






