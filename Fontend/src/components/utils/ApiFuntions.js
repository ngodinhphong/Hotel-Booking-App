import axios from "axios";

axios.defaults.withCredentials = true;

export const api = axios.create({
    baseURL: "http://localhost:8088/"
})

export const getHeader = () => {
	const token = localStorage.getItem("token")
	return {
		Authorization: `Bearer ${token}`,
	}
}

/* chức năng thêm room mới */
export async function addRoom(roomType, roomPrice, image) {
    try {
        const formData = new FormData()
        formData.append("roomType", roomType)
        formData.append("roomPrice", roomPrice)
        formData.append("image", image)

        const response = await api.post("/room/add", formData,{
			headers: getHeader()
		})
        return response.data

    } catch(error){
        console.log("loi lay du lieu add")
        throw new Error("Error fetch room types")
    }
    
}

/* chức năng lấy dữ liệu roomTypes */
export async function getRoomTypes() {
    try {
        const baseResponse = await api.get("/room/types")
        return baseResponse.data

    } catch (error) {
        throw new Error("Error fetch room types")
    }
}

export async function getAllRooms() {
	try {
		const result = await api.get("room/all")
		return result.data
	} catch (error) {
		throw new Error("Error fetching rooms")
	}
}

export async function deleteRoom(id) {
	try {
		const result = await api.delete(`room/delete/${id}`, {
			headers: getHeader()
		})
		return result.data
	} catch (error) {
		throw new Error(`Error deleting room ${error.message}`)
	}
}

export async function updateRoom(id, roomData) {
	const formData = new FormData()
	formData.append("roomType", roomData.roomType)
	formData.append("roomPrice", roomData.roomPrice)
	formData.append("image", roomData.image)
	const response = await api.put(`/room/update/${id}`, formData,{
		headers: getHeader()
	})
	return response.data
}

export async function getRoomById(roomId) {
	try {
		const result = await api.get(`/room/rooms/${roomId}`)
		return result.data
	} catch (error) {
		throw new Error(`Error fetching room ${error.message}`)
	}
}

export async function bookRoom(id, booking) {
	try {
		const response = await api.post(`/bookings/add/${id}`, booking)
		return response.data

	} catch (error) {
		if (error.response && error.response.data) {
			throw new Error(error.response.data)
		} else {
			throw new Error(`Error booking room : ${error.message}`)
		}
	}
}

export async function getAllBookings() {
	try {
		const result = await api.get("/bookings/all-bookings")
		return result.data
	} catch (error) {
		throw new Error(`Error fetching bookings : ${error.message}`)
	}
}

export async function getBookingByConfirmationCode(confirmationCode) {
	try {
		const result = await api.get(`/bookings/confirmation/${confirmationCode}`)
		return result.data
	} catch (error) {
		if (error.response && error.response.data) {
			throw new Error(error.response.data)
		} else {
			throw new Error(`Error find booking : ${error.message}`)
		}
	}
}

export async function cancelBooking(bookingId) {
	try {
		const result = await api.delete(`/bookings/delete/${bookingId}`)
		return result.data
	} catch (error) {
		throw new Error(`Error cancelling booking :${error.message}`)
	}
}

export async function getAvailableRooms(checkInDate, checkOutDate, roomType) {
	const result = await api.get(
		`room/available-rooms?checkInDate=${checkInDate}
		&checkOutDate=${checkOutDate}&roomType=${roomType}`
	)
	return result
}

export async function registerUser(registration) {
	try {
		const response = await api.post("/author/register-user", registration)
		return response.data
	} catch (error) {
		if (error.reeponse && error.response.data) {
			throw new Error(error.response.data)
		} else {
			throw new Error(`User registration error : ${error.message}`)
		}
	}
}

export async function loginUser(login) {
	try {
		const response = await api.post("/author/login", login)
		if (response.data.statusCode == 200) {
			return response.data
		} else {
			return null
		}
	} catch (error) {
		console.error(error)
		return null
	}
}

export async function getUserProfile(userId) {
	try {
		const response = await api.get(`user/profile/${userId}`, {
			headers: getHeader()
		})
		return response.data
	} catch (error) {
		throw error
	}
}

export async function deleteUser(userId) {
	try {
		const response = await api.delete(`/user/delete/${userId}`, {
			headers: getHeader()
		})
		return response.data
	} catch (error) {
		return error.message
	}
}

export async function getUser(id) {
	try {
		const response = await api.get(`/user/${id}`, {
			headers: getHeader()
		})
		return response.data
	} catch (error) {
		throw error
	}
}

export async function getBookingsByUserId(userId) {
	try {
		const response = await api.get(`/bookings/user/${userId}`, {
			headers: getHeader()
		})
		return response.data
	} catch (error) {
		console.error("Error fetching bookings:", error.message)
		throw new Error("Failed to fetch bookings")
	}
}

export async function updateUser(id, userData) {
	try {
		const formData = new FormData();
    	formData.append('email', userData.email);
    	formData.append('firstName', userData.firstName);
    	formData.append('lastName', userData.lastName);
    	if (userData.avatar) {
        	formData.append('avatar', userData.avatar);
    	}

    	const response = await api.put(`/user/update/${id}`, formData, {
        	headers: {
        	    ...getHeader(),
        	    'Content-Type': 'multipart/form-data',
        	},
    	});
    	return response.data;
	} catch (error) {
		console.log(error.message)
		throw error
	}

}

export async function changePassword(userName, oldPassword, newPassword) {
    try {
		const formData = new FormData()
        formData.append("userName", userName)
        formData.append("oldPassword", oldPassword)
        formData.append("newPassword", newPassword)

        const response = await api.put(`/user/change-password`, formData);
        return response.data; 
    } catch (error) {
		console.log(error.response.data);
        throw error.response.data ; 
    }
};