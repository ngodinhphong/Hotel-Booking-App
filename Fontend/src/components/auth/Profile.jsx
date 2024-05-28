import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import moment from "moment";
import { deleteUser, getBookingsByUserId, getUser } from "../utils/ApiFuntions";
import Cookies from 'js-cookie';

const Profile = () => {
	const [user, setUser] = useState({
		id: "",
		email: "",
		firstName: "",
		lastName: "",
		role: { id: "", name: "" },
		avatar: ""
	});

	const [bookings, setBookings] = useState([]);
	const [message, setMessage] = useState("");
	const [errorMessage, setErrorMessage] = useState("");
	const navigate = useNavigate();

	const userId = Cookies.get("userName");
	const token = localStorage.getItem("token");

	useEffect(() => {
		const fetchUser = async () => {
			try {
				const response = await getUser(userId);
				const userData = response.data;
				setUser(userData);
			} catch (error) {
				console.error(error);
			}
		};

		fetchUser();
	}, [userId]);

	useEffect(() => {
		const fetchBookings = async () => {
			try {
				const response = await getBookingsByUserId(userId);
				setBookings(response.data);
			} catch (error) {
				console.error("Error fetching bookings:", error.message);
				setErrorMessage(error.message);
			}
		};

		fetchBookings();
	}, [userId]);

	const handleDeleteAccount = async () => {
		const confirmed = window.confirm(
			"Are you sure you want to delete your account? This action cannot be undone."
		);
		if (confirmed) {
			await deleteUser(userId)
				.then((response) => {
					setMessage(response.data);
					localStorage.removeItem("token");
					localStorage.removeItem("userId");
					Cookies.remove("userName", { path: '/' });
					navigate("/");
					window.location.reload();
				})
				.catch((error) => {
					setErrorMessage(error.data);
				});
		}
	};

	const avatarUrl = user.avatar || "https://themindfulaimanifesto.org/wp-content/uploads/2020/09/male-placeholder-image.jpeg";

	return (
		<div className="container">
			{errorMessage && <p className="text-danger">{errorMessage}</p>}
			{message && <p className="text-success">{message}</p>}
			{user ? (
				<div className="card p-5 mt-5" style={{ backgroundColor: "whitesmoke" }}>
					<h2 className="card-title text-center">USER INFORMATION</h2>
					<div className="card-body">
						<div className="col-md-10 mx-auto">
							<div className="card mb-3 shadow">
								<div className="row g-0">
									<div className="col-md-12 d-flex align-items-start">
										<img
											src={avatarUrl}
											alt="Profile"
											className="rounded-circle"
											style={{ width: "150px", height: "150px", objectFit: "cover", margin: "10px" }}
										/>
										<div className="card-body">
											<div className="form-group row d-flex align-items-center">
												<label className="col-md-3 col-form-label fw-bold">ID:</label>
												<div className="col-md-9">
													<p className="card-text">{user.id}</p>
												</div>
											</div>
											<hr />

											<div className="form-group row d-flex align-items-center">
												<label className="col-md-3 col-form-label fw-bold">Email:</label>
												<div className="col-md-9">
													<p className="card-text">{user.email}</p>
												</div>
											</div>
											<hr />

											<div className="form-group row d-flex align-items-center">
												<label className="col-md-3 col-form-label fw-bold">First Name:</label>
												<div className="col-md-9">
													<p className="card-text">{user.firstName}</p>
												</div>
											</div>
											<hr />

											<div className="form-group row d-flex align-items-center">
												<label className="col-md-3 col-form-label fw-bold">Last Name:</label>
												<div className="col-md-9">
													<p className="card-text">{user.lastName}</p>
												</div>
											</div>
											<hr />

											<div className="form-group row d-flex align-items-center">
												<label className="col-md-3 col-form-label fw-bold">Role:</label>
												<div className="col-md-9">
													<p className="card-text">{user.role.name}</p>
												</div>
											</div>
											<hr />
										</div>
									</div>
								</div>
							</div>

							<div className="d-flex justify-content-center">
								<div className="mx-2">
									<button className="btn btn-primary btn-sm" onClick={() => navigate('/update-profile')}>
										Update Profile
									</button>
								</div>
								<div className="mx-2">
									<button className="btn btn-warning btn-sm" onClick={() => navigate('/change-password')}>
										Change Password
									</button>
								</div>
								<div className="mx-2">
									<button className="btn btn-danger btn-sm" onClick={handleDeleteAccount}>
										Close account
									</button>
								</div>
							</div>

							<h4 className="card-title text-center mt-4">Booking History</h4>

							{bookings.length > 0 ? (
								<table className="table table-bordered table-hover shadow">
									<thead>
										<tr>
											<th scope="col">Booking ID</th>
											<th scope="col">Room ID</th>
											<th scope="col">Room Type</th>
											<th scope="col">Check In Date</th>
											<th scope="col">Check Out Date</th>
											<th scope="col">Confirmation Code</th>
											<th scope="col">Status</th>
										</tr>
									</thead>
									<tbody>
										{bookings.map((booking, index) => (
											<tr key={index}>
												<td>{booking.id}</td>
												<td>{booking.room.id}</td>
												<td>{booking.room.roomType}</td>
												<td>
													{moment(booking.checkInDate).format("MMM Do, YYYY")}
												</td>
												<td>
													{moment(booking.checkOutDate).format("MMM Do, YYYY")}
												</td>
												<td>{booking.bookingConfirmationCode}</td>
												<td className="text-success">On-going</td>
											</tr>
										))}
									</tbody>
								</table>
							) : (
								<p>You have not made any bookings yet.</p>
							)}

						</div>
					</div>
				</div>
			) : (
				<p>Loading user data...</p>
			)}
		</div>
	);
};

export default Profile;
