import React, { createContext, useState, useContext } from "react"
import jwt_decode from "jwt-decode"
import Cookies from 'js-cookie';

export const AuthContext = createContext({
	user: null,
	handleLogin: (token) => {},
	handleLogout: () => {}
})

export const AuthProvider = ({ children }) => {
	const [user, setUser] = useState(null)

	const handleLogin = (token) => {
		const decodedUser = jwt_decode(token);
		const subData = JSON.parse(decodedUser.sub || "{}");
		const roleName = subData?.name || "";
		localStorage.setItem("userRole", roleName);
		localStorage.setItem("token", token);
		setUser(decodedUser);
	}

	const handleLogout = () => {
		localStorage.removeItem("userRole")
		localStorage.removeItem("token")
		Cookies.remove("userName", { path: '/' });
		setUser(null)
	}

	return (
		<AuthContext.Provider value={{ user, handleLogin, handleLogout }}>
			{children}
		</AuthContext.Provider>
	)
}

export const useAuth = () => {
	return useContext(AuthContext)
}

