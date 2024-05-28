import React, { useContext, useEffect, useState } from "react"
import MainHeader from "../layout/MainHeader"
import HotelService from "../common/HotelService"
import Parallax from "../common/Parallax"
import { useLocation } from "react-router-dom"
import RoomCarousel from "../common/RoomCarousel"
import RoomSearch from "../common/RoomSearch"
import Cookies from 'js-cookie';

const Home = () => {
	const location = useLocation();
	const message = location.state && location.state.message;
	const [currentUser, setCurrentUser] = useState(Cookies.get("userName"));
  
	useEffect(() => {
	  const interval = setInterval(() => {
		const userName = Cookies.get("userName");
		if (userName !== currentUser) {
		  setCurrentUser(userName);
		}
	  }, 1000);
  
	  return () => clearInterval(interval);
	}, [currentUser]);
	return (
		<section>
			{message && <p className="text-warning px-5">{message}</p>}
			{currentUser && (
				<h6 className="text-success text-center"> You are logged-In as {currentUser}</h6>
			)}
			<MainHeader />
			<div className="container">
				<RoomSearch />
				<RoomCarousel />
				<Parallax />
				<RoomCarousel />
				<HotelService />
				<Parallax />
				<RoomCarousel />
			</div>
		</section>
	)
}

export default Home
