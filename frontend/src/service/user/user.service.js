import { do_post } from '../../client/http_rest_client';

import { buildBeUrl } from '../routes/routes.service';


const endpoints = {
    "register": "user/register"
};


async function registerUser({ username, password }) {
    let res = await do_post({
        "url": buildBeUrl(endpoints.register),
        "body": { username, password }
    });

    console.log("register-response", res);

    //TODO: manca la gestione del 403 in caso, ad esempio di token scaduto

}


export { registerUser };