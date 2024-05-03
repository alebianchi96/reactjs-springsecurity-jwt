function get_jwt_authentication_header() {
    let jwt_token = sessionStorage.getItem("jwt_token");
    // to base64 -> btoa
    return {
        "Authorization": "Bearer " + jwt_token
    };
}

const default_user = {
    "username": "ale",
    "password": "ale"
    // "password": "al"
};

export { get_jwt_authentication_header, default_user };
