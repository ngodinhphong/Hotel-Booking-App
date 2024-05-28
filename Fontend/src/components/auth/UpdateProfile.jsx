import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { getUser, updateUser } from '../utils/ApiFuntions';
import Cookies from 'js-cookie';

const UpdateProfile = () => {
    const [userData, setUserData] = useState({
        id: "",
        email: "",
        firstName: "",
        lastName: "",
        avatar: "",
    });

    const [message, setMessage] = useState("");
    const [errorMessage, setErrorMessage] = useState("");
    const [preview, setPreview] = useState(null);
    const [isLoading, setIsLoading] = useState(false); // State for loading indicator
    const [emailError, setEmailError] = useState('');
    const [firstNameError, setFirstNameError] = useState('');
    const [lastNameError, setLastNameError] = useState('');
    const navigate = useNavigate();

    const userId = Cookies.get("userName");

    useEffect(() => {
        const fetchUser = async () => {
            try {
                const response = await getUser(userId);
                const userData = response.data;
                setUserData({
                    id: userData.id,
                    email: userData.email,
                    firstName: userData.firstName,
                    lastName: userData.lastName,
                    avatar: null,
                });
                setPreview(userData.avatar);
            } catch (error) {
                console.error(error);
            }
        };

        fetchUser();
    }, [userId]);

    useEffect(() => {
        if (message || errorMessage) {
            const timer = setTimeout(() => {
                setMessage("");
                setErrorMessage("");
            }, 3000); // Hide after 3 seconds

            return () => clearTimeout(timer); // Cleanup timer on component unmount
        }
    }, [message, errorMessage]);

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setUserData({ ...userData, [name]: value });
    };

    const handleFileChange = (e) => {
        const file = e.target.files[0];
        setUserData({ ...userData, avatar: file });
        setPreview(URL.createObjectURL(file));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setIsLoading(true); // Start loading
        setEmailError('');
        setFirstNameError('');
        setLastNameError('');

        if (!userData.email) {
            setEmailError('Email is required.');
            setIsLoading(false); // Stop loading
            return;
        }

        if (!userData.firstName) {
            setFirstNameError('First name is required.');
            setIsLoading(false); // Stop loading
            return;
        }

        if (!userData.lastName) {
            setLastNameError('Last name is required.');
            setIsLoading(false); // Stop loading
            return;
        }

        try {
            const response = await updateUser(userData.id, userData);
            setMessage(response.message);

            // Update userName cookie
            Cookies.set("userName", userData.email, { path: '/' });

            // Do not navigate to profile page
            // navigate('/profile');
        } catch (error) {
            if (error.response && error.response.status === 409) {
                setErrorMessage(error.response.data);
            } else {
                setErrorMessage('An error occurred while updating the profile.');
            }
        } finally {
            setIsLoading(false); // End loading
        }
    };

    return (
        <div className="container">
            {errorMessage && (
                <p
                    className="text-danger text-center"
                    style={{ fontSize: '1.5em', marginBottom: '-20px', marginTop: '20px' }}
                >
                    {errorMessage}
                </p>
            )}
            {message && (
                <p
                    className="text-success text-center"
                    style={{ fontSize: '1.5em', marginBottom: '-20px', marginTop: '20px' }}
                >
                    {message}
                </p>
            )}
            <div className="card p-5 mt-5" style={{ maxWidth: "800px", margin: "0 auto", backgroundColor: "lightblue" }}>
                <h2 className="text-center">Update Profile</h2>
                <form onSubmit={handleSubmit}>
                    <div className="form-group text-center">
                        <div>
                            <img
                                src={preview || "https://themindfulaimanifesto.org/wp-content/uploads/2020/09/male-placeholder-image.jpeg"}
                                alt="Avatar"
                                className="rounded-circle"
                                style={{ width: '200px', height: '200px', objectFit: 'cover', margin: '10px' }} // Add margin to create spacing
                            />
                        </div>
                        <label htmlFor="avatar" className="btn btn-primary mt-4">
                            Choose Image
                            <input
                                type="file"
                                className="form-control"
                                id="avatar"
                                name="avatar"
                                style={{ display: 'none' }}
                                onChange={handleFileChange}
                            />
                        </label>
                    </div>
                    <div className="form-group">
                        {emailError && <p className="text-danger mt-2 mb-0">{emailError}</p>}
                        <label htmlFor="email">Email</label>
                        <input
                            type="email"
                            className="form-control"
                            id="email"
                            name="email"
                            value={userData.email}
                            onChange={handleInputChange}
                            style={{ fontSize: '1.2em' }}
                        />
                        
                    </div>
                    <div className="form-group">
                        {firstNameError && <p className="text-danger mt-2 mb-0">{firstNameError}</p>}
                        <label htmlFor="firstName">First Name</label>
                        <input
                            type="text"
                            className="form-control"
                            id="firstName"
                            name="firstName"
                            value={userData.firstName}
                            onChange={handleInputChange}
                            style={{ fontSize: '1.2em' }}
                        />
                        
                    </div>
                    <div className="form-group">
                        {lastNameError && <p className="text-danger mt-2 mb-0">{lastNameError}</p>}
                        <label htmlFor="lastName">Last Name</label>
                        <input
                            type="text"
                            className="form-control"
                            id="lastName"
                            name="lastName"
                            value={userData.lastName}
                            onChange={handleInputChange}
                            style={{ fontSize: '1.2em' }}
                        />
                        
                    </div>
                    
                    <div className="d-flex justify-content-start mt-3">
                        <button type="submit" className="btn btn-primary mx-2">
                            Update
                        </button>
                        <button type="button" className="btn btn-secondary" onClick={() => navigate('/profile')}>
                            Profile
                        </button>
                    </div>
                </form>
            </div>
        </div>
    );
};

export default UpdateProfile;
