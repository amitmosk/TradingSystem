
     
import React from 'react';
import {useParams } from 'react-router-dom';
import ViewStaffInformation from './ViewStaffInformation';
export default function ViewStaffInformationNevigator() {
    const {id} = useParams();
    console.log("staff information nevigator-> id ="+id);
    return (<ViewStaffInformation store_id={id}></ViewStaffInformation>);
    }






