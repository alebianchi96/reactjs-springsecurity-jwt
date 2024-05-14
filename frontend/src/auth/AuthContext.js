
import axios from 'axios';
import React, { createContext, useState } from 'react';

import { buildBeUrl } from '../service/routes/routes.service';

import { setJwtInStorage, removeJwtFromStorage } from '../service/utils/jwt_utils';

// Step 1: Create a context
export const AuthContext = createContext();

// Step 2: Create a provider component
export const AuthProvider = ({ children }) => {

    const [isAuthenticated, setIsAuthenticated] = useState(false);

    async function loginInContext({ loginForm, resetLoginForm }) {

        try {
            let res = await axios.post(buildBeUrl("user/login"), loginForm);
            if (res.status === 200 && res.data) {
                setJwtInStorage(res.data);
                setIsAuthenticated(true);
                return true;
            }
            console.log("Login non riuscita: ", res);
            resetLoginForm();
            return false;
        } catch ({ code, response }) {
            console.log("Login non riuscita: ", response?.data);
            resetLoginForm();
            return false;
        }

    };

    function logoutInContext() {
        removeJwtFromStorage();
        setIsAuthenticated(false);
    };


    return (
        <AuthContext.Provider value={{ isAuthenticated, loginInContext, logoutInContext }}>
            {children}
        </AuthContext.Provider>
    );
};

