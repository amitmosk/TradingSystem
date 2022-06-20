
     
import React from 'react';
import { useParams } from 'react-router-dom';
import ViewAppointmentsStatus from './ViewAppointmentsStatus';


export default function ViewAppointmentsStatusNevigator() {
    const {id} = useParams();
    return (<ViewAppointmentsStatus store_id={id}></ViewAppointmentsStatus>);
    }






