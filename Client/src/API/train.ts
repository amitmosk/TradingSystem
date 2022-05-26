// import {Result} from "../Common";
// import {
//     ADMIN_GET_LOGIN_STATS_PATH
// } from "./ApiPaths";
// import {LoginStats} from "../Data/LoginStats";

const EMPLOYEE_BASE_REST_API_URL = "http://localhost:8080/amit";

const axios = require('axios');

// const instance = axios.create(
//     {withCredentials : true}
// );

export class train {
    
    async loginStats(date: string) {
        const res =await axios.get(EMPLOYEE_BASE_REST_API_URL);
        return res.data;
    }
    
}
export default train