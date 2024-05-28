import React, { createContext, useState } from "react";

export const showAccount = createContext();

export const AccountProvider = ({ children }) => {
    const [disPlayAccount, setShowAccount] = useState(false);

    const handleAccountClick = () => {
        setShowAccount(!showAccount);
    };

    return (
        <AccountContext.Provider value={{ disPlayAccount, handleAccountClick }}>
            {children}
        </AccountContext.Provider>
    );
};