
     
import React from 'react';
import { useParams } from 'react-router-dom';
import ManagerPermissions from './ManagerPermissions';

export default function ManagerPermissionsNevigator({user_email}) {
    console.log("email = "+user_email);
    const {id, email} = useParams();
    console.log("ManagerPermissions-> id ="+id);
    return (<ManagerPermissions store_id={id} user_email={user_email}></ManagerPermissions>);
    }






