import axios from 'axios';
import { buildSimpleJwtHeader } from '../utils/jwt_utils';
import { buildBeUrl } from '../routes/routes.service';

const endpoints = {
    "list": "api/employees"
};


async function listEmployees() {
    return await axios.get(buildBeUrl(endpoints.list), buildSimpleJwtHeader());
}

async function addEmployee() { console.log("-> add"); }

async function updtEmployee() { console.log("-> updt"); }

async function deleteEmployee() { console.log("-> delete"); }

export {
    listEmployees, addEmployee, updtEmployee, deleteEmployee
};
