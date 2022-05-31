import React from 'react';
import { useParams } from 'react-router-dom';
import MyStores from './MyStores';

export default function MyStoresNevigator() {
    const {user} = useParams();
    console.log("MyStoresNevigator-> user ="+user);
    return (<MyStores user={user}></MyStores>);
    }

