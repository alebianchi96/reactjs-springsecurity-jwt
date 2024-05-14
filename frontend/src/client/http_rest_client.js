/**
 * Anziche' usare direttamente 'axios' occorre utilizzare questo
 * client REST al fine di gestire in modo corretto il refresh del token jwt
 * 
 */

import axios from 'axios';
import { setJwtInStorage } from '../service/utils/jwt_utils';

async function do_get({
    url, header
}) {
    let res = await axios.get(url, header);
    refresh_jwt_token(res);
    return res;
}


async function do_post({
    url, header, body
}) {
    let res = null;
    if (!header) {
        res = await axios.post(url, body);
    } else {
        res = await axios.post(url, body, header);
    }
    refresh_jwt_token(res);
    return res;
}

/**
 * Per ogni chiamata effettuata verso il BE, se quest'ultimo restituisce
 * una nuova versione del token di autenticazione occorre sostituirla alla precedente
 * 
 * 
 * @param {} res 
 */
function refresh_jwt_token(res) {
    if (res?.headers?.xx_auth_jwt_token) {
        let new_jwt_token = res?.headers?.xx_auth_jwt_token;
        setJwtInStorage(new_jwt_token);
    }
}


export { do_get, do_post };