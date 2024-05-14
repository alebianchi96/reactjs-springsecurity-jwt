import { buildSimpleJwtHeader } from '../utils/jwt_utils';
import { buildBeUrl } from '../routes/routes.service';

import { do_get } from '../../client/http_rest_client';

const endpoints = {
    "list": "api/employees"
};


async function listEmployees() {
    return await do_get({
        "url": buildBeUrl(endpoints.list),
        "header": buildSimpleJwtHeader()
    });
}

async function addEmployee() { console.log("-> add"); }

async function updtEmployee() { console.log("-> updt"); }

async function deleteEmployee() { console.log("-> delete"); }

export {
    listEmployees, addEmployee, updtEmployee, deleteEmployee
};
