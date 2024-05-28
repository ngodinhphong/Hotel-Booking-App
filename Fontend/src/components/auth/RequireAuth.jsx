import React from "react"
import { Navigate, useLocation } from "react-router-dom"
import Cookies from 'js-cookie';

const RequireAuth = ({ children }) => {
	const user = Cookies.get("userName")
	const location = useLocation()
	if (!user) {
		return <Navigate to="/login" state={{ path: location.pathname }} />
	}
	return children
}
export default RequireAuth
