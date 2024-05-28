import React, { useEffect, useState } from "react";
import moment from "moment";
import { Form, FormControl, Button } from "react-bootstrap";
import BookingSummary from "./BookingSummary";
import { useNavigate, useParams } from "react-router-dom";
import { bookRoom, getRoomById, getUser } from "../utils/ApiFuntions";
import Cookies from 'js-cookie';

const BookingForm = () => {
    const [validated, setValidated] = useState(false);
    const [isSubmitted, setIsSubmitted] = useState(false);
    const [errorMessage, setErrorMessage] = useState("");
    const [roomPrice, setRoomPrice] = useState(0);
    const [currentUser, setCurrentUser] = useState({});
    const [booking, setBooking] = useState({
        guestFullName: "",
        guestEmail: "",
        checkInDate: "",
        checkOutDate: "",
        adults: "",
        children: ""
    });

    const userName = Cookies.get("userName");
    const { id } = useParams();
    const navigate = useNavigate();

    useEffect(() => {
        const fetchCurrentUser = async () => {
            try {
                const response = await getUser(userName);
                setCurrentUser(response.data);
                setBooking({
                    ...booking,
                    guestFullName: `${response.data.firstName}${response.data.lastName}`,
                    guestEmail: response.data.email
                });
            } catch (error) {
                console.error(error);
            }
        };

        fetchCurrentUser();
    }, []);

    const getRoomPriceById = async (id) => {
        try {
            const response = await getRoomById(id);
            setRoomPrice(response.data.roomPrice);
        } catch (error) {
            console.error(error);
        }
    };

    useEffect(() => {
        getRoomPriceById(id);
    }, [id]);

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setBooking({ ...booking, [name]: value });
        setErrorMessage("");
    };

    const calculatePayment = () => {
        const checkInDate = moment(booking.checkInDate);
        const checkOutDate = moment(booking.checkOutDate);
        const diffInDays = checkOutDate.diff(checkInDate, "days");
        const paymentPerDay = roomPrice ? roomPrice : 0;
        return diffInDays * paymentPerDay;
    };

    const isGuestCountValid = () => {
        const adultCount = parseInt(booking.adults);
        const childrenCount = parseInt(booking.children);
        const totalCount = adultCount + childrenCount;
        return totalCount >= 1 && adultCount >= 1;
    };

    const isCheckOutDateValid = () => {
        if (!moment(booking.checkOutDate).isSameOrAfter(moment(booking.checkInDate))) {
            setErrorMessage("Check-out date must be after check-in date");
            return false;
        } else {
            setErrorMessage("");
            return true;
        }
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        const form = e.currentTarget;
        if (form.checkValidity() === false || !isGuestCountValid() || !isCheckOutDateValid()) {
            e.stopPropagation();
        } else {
            setIsSubmitted(true);
        }
        setValidated(true);
    };

    const handleFormSubmit = async () => {
        try {
            const confirmationCode = await bookRoom(id, booking);
            console.log(confirmationCode);
            setIsSubmitted(true);
            const message = confirmationCode.message + " " + confirmationCode.data;
            navigate("/booking-success", { state: { message: message } });
        } catch (error) {
            const errorMessage = error.message;
            console.error(errorMessage);
            navigate("/booking-success", { state: { error: errorMessage } });
        }
    };

    return (
        <>
            <div className="container mb-5">
                <div className="row">
                    <div className="col-md-6">
                        <div className="card card-body mt-5">
                            <h4 className="card-title">Reserve Room</h4>

                            <Form noValidate validated={validated} onSubmit={handleSubmit}>
                                <Form.Group>
                                    <Form.Label htmlFor="guestFullName" className="hotel-color">
                                        Fullname
                                    </Form.Label>
                                    <FormControl
                                        required
                                        type="text"
                                        id="guestFullName"
                                        name="guestFullName"
                                        value={booking.guestFullName}
                                        placeholder="Enter your fullname"
                                        onChange={handleInputChange}
                                        disabled
                                    />
                                    <Form.Control.Feedback type="invalid">
                                        Please enter your fullname.
                                    </Form.Control.Feedback>
                                </Form.Group>

                                <Form.Group>
                                    <Form.Label htmlFor="guestEmail" className="hotel-color">
                                        Email
                                    </Form.Label>
                                    <FormControl
                                        required
                                        type="email"
                                        id="guestEmail"
                                        name="guestEmail"
                                        value={booking.guestEmail}
                                        placeholder="Enter your email"
                                        onChange={handleInputChange}
                                        disabled
                                    />
                                    <Form.Control.Feedback type="invalid">
                                        Please enter a valid email address.
                                    </Form.Control.Feedback>
                                </Form.Group>

                                <Form.Group>
                                    <fieldset style={{ border: "2px" }}>
                                        <legend>Lodging Period</legend>
                                        <div className="row">
                                            <div className="col-6">
                                                <Form.Label htmlFor="checkInDate" className="hotel-color">
                                                    Check-in date
                                                </Form.Label>
                                                <FormControl
                                                    required
                                                    type="date"
                                                    id="checkInDate"
                                                    name="checkInDate"
                                                    value={booking.checkInDate}
                                                    placeholder="check-in-date"
                                                    min={moment().format("YYYY-MM-DD")}
                                                    onChange={handleInputChange}
                                                />
                                                <Form.Control.Feedback type="invalid">
                                                    Please select a check in date.
                                                </Form.Control.Feedback>
                                            </div>

                                            <div className="col-6">
                                                <Form.Label htmlFor="checkOutDate" className="hotel-color">
                                                    Check-out date
                                                </Form.Label>
                                                <FormControl
                                                    required
                                                    type="date"
                                                    id="checkOutDate"
                                                    name="checkOutDate"
                                                    value={booking.checkOutDate}
                                                    placeholder="check-out-date"
                                                    min={moment().format("YYYY-MM-DD")}
                                                    onChange={handleInputChange}
                                                />
                                                <Form.Control.Feedback type="invalid">
                                                    Please select a check out date.
                                                </Form.Control.Feedback>
                                            </div>
                                            {errorMessage && <p className="error-message text-danger">{errorMessage}</p>}
                                        </div>
                                    </fieldset>
                                </Form.Group>

                                <Form.Group>
                                    <fieldset style={{ border: "2px" }}>
                                        <legend>Number of Guests</legend>
                                        <div className="row">
                                            <div className="col-6">
                                                <Form.Label htmlFor="adults" className="hotel-color">
                                                    Adults
                                                </Form.Label>
                                                <FormControl
                                                    required
                                                    type="number"
                                                    id="adults"
                                                    name="adults"
                                                    value={booking.adults}
                                                    min={1}
                                                    placeholder="0"
                                                    onChange={
														handleInputChange}
														/>
														<Form.Control.Feedback type="invalid">
															Please select at least 1 adult.
														</Form.Control.Feedback>
													</div>
													<div className="col-6">
														<Form.Label htmlFor="children" className="hotel-color">
															Children
														</Form.Label>
														<FormControl
															required
															type="number"
															id="children"
															name="children"
															value={booking.children}
															placeholder="0"
															min={0}
															onChange={handleInputChange}
														/>
														<Form.Control.Feedback type="invalid">
															Select 0 if no children
														</Form.Control.Feedback>
													</div>
												</div>
											</fieldset>
										</Form.Group>
		
										<div className="form-group mt-2 mb-2">
											<Button type="submit" className="btn btn-hotel">
												Continue
											</Button>
										</div>
									</Form>
								</div>
							</div>
		
							<div className="col-md-4">
								{isSubmitted && (
									<BookingSummary
										booking={booking}
										payment={calculatePayment()}
										onConfirm={handleFormSubmit}
										isFormValid={validated}
									/>
								)}
							</div>
						</div>
					</div>
				</>
			);
		};
		
		export default BookingForm;
		