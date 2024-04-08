import React, { useContext } from "react"
import MainHeader from "../layout/MainHeader"
import HotelService from "../common/HotelService"
import Parallax from "../common/Parallax"
import { useLocation } from "react-router-dom"
import RoomCarousel from "../common/RoomCarousel"
const Home = () => {
	const location = useLocation()

	// const message = location.state && location.state.message
	// const currentUser = localStorage.getItem("userId")
	return (
		<section>
			<MainHeader/>
			<section className="container">
				<RoomCarousel/>
				<Parallax/>
				<HotelService/>
				<Parallax/>
				<RoomCarousel/>

			</section>
			{/* {message && <p className="text-warning px-5">{message}</p>}
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
			</div> */}
		</section>
	)
}

export default Home
