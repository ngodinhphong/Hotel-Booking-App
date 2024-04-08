import axios from "axios"

export const api = axios.create({
    baseURL: "http://localhost:8088/"
})

/* chức năng thêm room mới */
export async function addRoom(roomType, roomPrice, image) {
    try {
        const formData = new FormData()
        formData.append("roomType", roomType)
        formData.append("roomPrice", roomPrice)
        formData.append("image", image)

        const response = await api.post("/room/add", formData)
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

export async function deleteRoom(roomId) {
	try {
		const result = await api.delete(`room/delete/${roomId}`)
		return result.data
	} catch (error) {
		throw new Error(`Error deleting room ${error.message}`)
	}
}

export async function updateRoom(roomId, roomData) {
	const formData = new FormData()
	formData.append("roomType", roomData.roomType)
	formData.append("roomPrice", roomData.roomPrice)
	formData.append("image", roomData.image)
	const response = await api.put(`/room/update/${roomId}`, formData)
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