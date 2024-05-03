import axios from 'axios';
import { get_jwt_authentication_header } from '../authentication/authentication.service';
import { base_url } from '../routes/routes.service';

const custom_headers = () => {
    return {
        headers: {
            ...get_jwt_authentication_header(),
        }
    };
};

async function listEmployees() {

    let ch = custom_headers();

    console.log("-> list", ch);

    let res = (await axios
        .get(base_url + "api/employees", ch)
    ).data;
    console.log("list_response", res);
    return res;
}

async function addEmployee() { console.log("-> add"); }

async function updtEmployee() { console.log("-> updt"); }

async function deleteEmployee() { console.log("-> delete"); }

export {
    listEmployees, addEmployee, updtEmployee, deleteEmployee
};
