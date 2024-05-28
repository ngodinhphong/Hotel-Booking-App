import React, { useEffect, useState } from "react"
import { Link, useParams } from "react-router-dom"
import { getRoomById, updateRoom } from "../utils/ApiFuntions"

const EditRoom = () => {
	const [room, setRoom] = useState({
		image: "",
		roomType: "",
		roomPrice: ""
	})

	const [imagePreview, setImagePreview] = useState("")
	const [successMessage, setSuccessMessage] = useState("")
	const [errorMessage, setErrorMessage] = useState("")
	const {id} = useParams()

	const handleImageChange = (e) => {
		const selectedImage = e.target.files[0]
		setRoom({ ...room, image: selectedImage })
		setImagePreview(URL.createObjectURL(selectedImage))
	}

	const handleInputChange = (event) => {
		const { name, value } = event.target
		setRoom({ ...room, [name]: value })
	}

	useEffect(() => {
		const fetchRoom = async () => {
			try {
				const roomData = await getRoomById(id)
				setRoom(roomData.data)
				setImagePreview(roomData.data.image)
			} catch (error) {
				console.error(error)
			}
		}

		fetchRoom()
	}, [id])

	const handleSubmit = async (e) => {
		e.preventDefault()

		try {
			const response = await updateRoom(id, room)
			console.log(response.statusCode)
			if (response.statusCode === 200) {
				setSuccessMessage(response.message)
				const updatedRoomData = await getRoomById(id)
				setRoom(updatedRoomData)
				setImagePreview(updatedRoomData.data.image)
				setErrorMessage("")
			} else {
				setErrorMessage(response.message)
			}
		} catch (error) {
			console.error(error)
			setErrorMessage(error.message)
		}
	}

	return (
		<div className="container mt-5 mb-5">
			<h3 className="text-center mb-5 mt-5">Edit Room</h3>
			<div className="row justify-content-center">
				<div className="col-md-8 col-lg-6">
					{successMessage && (
						<div className="alert alert-success" role="alert">
							{successMessage}
						</div>
					)}
					{errorMessage && (
						<div className="alert alert-danger" role="alert">
							{errorMessage}
						</div>
					)}
					<form onSubmit={handleSubmit}>
						<div className="mb-3">
							<label htmlFor="roomType" className="form-label hotel-color">
								Room Type
							</label>
							<input
								type="text"
								className="form-control"
								id="roomType"
								name="roomType"
								value={room.roomType}
								onChange={handleInputChange}
							/>
						</div>
						<div className="mb-3">
							<label htmlFor="roomPrice" className="form-label hotel-color">
								Room Price
							</label>
							<input
								type="number"
								className="form-control"
								id="roomPrice"
								name="roomPrice"
								value={room.roomPrice}
								onChange={handleInputChange}
							/>
						</div>

						<div className="mb-3">
							<label htmlFor="image" className="form-label hotel-color">
							image
							</label>
							<input
								required
								type="file"
								className="form-control"
								id="image"
								name="image"
								onChange={handleImageChange}
							/>
							{imagePreview && (
								<img
									src={imagePreview}
									alt="Room preview"
									style={{ maxWidth: "400px", maxHeight: "400" }}
									className="mt-3"
								/>
							)}
						</div>
						<div className="d-grid gap-2 d-md-flex mt-2">
							<Link to={"/existing-rooms"} className="btn btn-outline-info ml-5">
								back
							</Link>
							<button type="submit" className="btn btn-outline-warning">
								Edit Room
							</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	)
}
export default EditRoom
