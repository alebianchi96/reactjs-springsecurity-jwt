import axios from 'axios';
import { default_user } from '../authentication/authentication.service';
import { base_url } from '../routes/routes.service';

async function registerUser() {
    let res = await axios
        .post(base_url + "user/register", default_user);
    console.log("register-response", res);
}


async function login() {

    try {
        let res = await axios.post(base_url + "user/login", default_user);
        if (res.status == 200 && res.data) {
            sessionStorage.setItem("jwt_token", res.data);
            return true;
        }
        console.log("Login non riuscita: ", res);
        return false;
    } catch ({ code, response }) {
        console.log("Login non riuscita: ", response?.data);
    }

}

function logout() {

    sessionStorage.removeItem("jwt_token");
    return true;

}

export { registerUser, login, logout };