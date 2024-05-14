const jwt_token_id = "jwt_token";

const jwt_token_prefix = "Bearer ";

function buildJwtHeader() {
    let jwt_token = sessionStorage.getItem(jwt_token_id);
    // to base64 -> btoa
    return {
        "Authorization": jwt_token_prefix + jwt_token
    };
}


function buildSimpleJwtHeader() {
    return {
        headers: {
            ...buildJwtHeader(),
        }
    };
}


function setJwtInStorage(jwt_value) {
    sessionStorage.setItem(jwt_token_id, jwt_value);
}


function removeJwtFromStorage() {
    sessionStorage.removeItem(jwt_token_id);
}


export { buildSimpleJwtHeader, setJwtInStorage, removeJwtFromStorage };