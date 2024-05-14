import axios from 'axios';

import { base_url } from '../routes/routes.service';

async function registerUser({ username, password }) {
    let res = await axios
        .post(base_url + "user/register", { username, password });
    console.log("register-response", res);

    //TODO: manca la gestione del 403 in caso, ad esempio di token scaduto

}


export { registerUser };