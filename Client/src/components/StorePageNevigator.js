
     
import React from 'react';
import { useParams } from 'react-router-dom';
import StorePage from './StorePage';
import { ConnectApi } from '../API/ConnectApi';
import { useEffect } from 'react';
import {useState} from "react";

export default function StorePageNevigator() {
    const {id} = useParams();
    const [user, setUser] = useState(null);
    const connectApi = new ConnectApi();
    useEffect(()=>{get_online_user()}, []);
    const get_online_user = async () => {
      let response = await connectApi.get_online_user()
      if(!response.was_exception)
      {
        setUser(response.value);

      }
      else
      {

      }
      
  }
    console.log("store page nevigator-> id ="+id);
    console.log("store page nevigator-> user ="+user);
    console.log(user);
    if(user!==null)
        return (<StorePage store_id={id} stores_managed={user.storesManaged}></StorePage>);
    }
 






