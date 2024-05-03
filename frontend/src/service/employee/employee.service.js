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

    console.log("-> list");

    try {

        let _res = await axios
            .get(base_url + "api/employees", ch);

        console.log("list_response", _res);

        return _res.data;

    } catch (error) {

        let { response } = error;

        if (response?.status === 403) {
            console.error("unauthorized");
            console.error(error);

            // TODO - redirect user to login_page

        } else {
            console.log("err-listEmployees", error);

            // TODO - use logger

        }

    }

    return [];

}

async function addEmployee() { console.log("-> add"); }

async function updtEmployee() { console.log("-> updt"); }

async function deleteEmployee() { console.log("-> delete"); }

export {
    listEmployees, addEmployee, updtEmployee, deleteEmployee
};
