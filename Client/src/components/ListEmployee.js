import React, { useEffect, useState } from 'react';
//import StatsApi from '../API/train';
//import EmployeeService from '../ServiceObjects/EmployeeService'
const axios = require('axios');
const EMPLOYEE_BASE_REST_API_URL = "http://localhost:8080/amit";

//const foo = async () =>  await EmployeeService.getName();

async function ListEmployee()  {
    let x = " fuck"
    // StatsApi = new StatsApi();
    // const fetchedStats = await StatsApi.loginState();
    //x = await foo();

    return (
        <div className="container">
            <h2 className="text-center">
                ListEmployee
            </h2>
            <table className="table table-bordered table-stripped">
                <thead>
                    <th>Employee Id</th>
                    <th>Employee First Name</th>
                    <th>Employee Last Name</th>
                </thead>
                <body>
                    {
                        
                    }
                </body>
            </table>
            ListEmployee
            {x}
            
        </div>
    )
}

export default ListEmployee


// import React, { useState, useEffect } from 'react'
// import EmployeeService from '../Services/EmployeeService'
// const axios = require('axios');

// const EMPLOYEE_BASE_REST_API_URL = "http://localhost:8080/amit";
// const setEmployee = [{
//     "id": 1,
//     "name": "Eylon"
// },
// {
//     "id": 2,
//     "name": "Tom"
//     }];

// const ListEmployee = () => {
//     const [Employees, setEmployees] = useState([])
//     useEffect(() => {
//         EmployeeService.getAllEmployees().then((response) => {
//             setEmployees(setEmployee)
//             console.log(response.data)
//         }).catch(error => {
//                 console.log(error)
//             })
//       }, [])
    
//     return (
//         <div className="container">
//             <h2 className="text-center">
//                 ListEmployee
//             </h2>
//             <table className="table table-bordered table-stripped">
//                 <thead>
//                     <th>Employee Id</th>
//                     <th>Employee First Name</th>
//                     <th>Employee Last Name</th>
//                 </thead>
//                 <body>
//                     {setEmployee.map(
//                         setEmployee => <tr> key = setEmployee.id
//                             <td> {setEmployee.id} </td>
//                             <td> {setEmployee.name} </td>
//                         </tr>
//                     )}
//                 </body>
//             </table>
//             ListEmployee
//         </div>
//     )
// }

// export default ListEmployee

