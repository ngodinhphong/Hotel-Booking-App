import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import Cookies from 'js-cookie';
import { changePassword } from '../utils/ApiFuntions';

const ChangePassword = () => {
    const [oldPassword, setOldPassword] = useState('');
    const [newPassword, setNewPassword] = useState('');
    const [confirmNewPassword, setConfirmNewPassword] = useState('');
    const [errorMessage, setErrorMessage] = useState('');
    const [successMessage, setSuccessMessage] = useState('');
    const [confirmNewPasswordError, setConfirmNewPasswordError] = useState('');
    const [isLoading, setIsLoading] = useState(false);
    const navigate = useNavigate();

    const userName = Cookies.get("userName");

    const handleSubmit = async (e) => {
        e.preventDefault();
        setErrorMessage('');
        setSuccessMessage('');
        setConfirmNewPasswordError('');

        if (newPassword !== confirmNewPassword) {
            setConfirmNewPasswordError('New passwords do not match.');
            return;
        }

        try {
            setIsLoading(true);
            const response = await changePassword(userName, oldPassword, newPassword);
            setSuccessMessage(response.message || 'Password changed successfully.');
        } catch (error) {
            console.log(error.statusCode);
            if (error.statusCode === 400) {
                setErrorMessage(error.message);
            } else {
                setErrorMessage('An error occurred while changing the password.');
            }
        } finally {
            setIsLoading(false);
        }
    };

    return (
        <div className="container">
            {isLoading && <p className="text-info text-center">Processing...</p>}
            <div className="card p-5 mt-5" style={{ maxWidth: "600px", margin: "0 auto", backgroundColor: "lightblue" }}>
                <h2 className="text-center">Change Password</h2>
                <form onSubmit={handleSubmit}>
                    {(errorMessage || successMessage) && (
                        <p className={`text-center mt-2 mb-2 ${successMessage ? 'text-success' : 'text-danger'}`}>
                            {errorMessage || successMessage}
                        </p>
                    )}
                    <div className="form-group">
                        <label htmlFor="oldPassword">Password</label>
                        <input
                            type="password"
                            className="form-control"
                            id="oldPassword"
                            name="oldPassword"
                            value={oldPassword}
                            onChange={(e) => setOldPassword(e.target.value)}
                            required
                            style={{ fontSize: '1.2em' }}
                        />
                    </div>
                    <div className="form-group">
                        <label htmlFor="newPassword">New Password</label>
                        <input
                            type="password"
                            className="form-control"
                            id="newPassword"
                            name="newPassword"
                            value={newPassword}
                            onChange={(e) => setNewPassword(e.target.value)}
                            required
                            style={{ fontSize: '1.2em' }}
                        />
                    </div>
                    <div className="form-group">
                        {confirmNewPasswordError && <p className="text-danger mt-2 mb-0">{confirmNewPasswordError}</p>}
                        <label htmlFor="confirmNewPassword">Confirm New Password</label>
                        <input
                            type="password"
                            className="form-control"
                            id="confirmNewPassword"
                            name="confirmNewPassword"
                            value={confirmNewPassword}
                            onChange={(e) => setConfirmNewPassword(e.target.value)}
                            required
                            style={{ fontSize: '1.2em' }}
                        />
                    </div>
                    <div className="d-flex justify-content-start">
                        <button type="submit" className="btn btn-primary mt-3">
                            Change Password
                        </button>
                        <button type="button" className="btn btn-secondary mt-3 ms-3" onClick={() => navigate('/profile')}>
                            Profile
                        </button>
                    </div>
                </form>
            </div>
        </div>
    );
};

export default ChangePassword;
