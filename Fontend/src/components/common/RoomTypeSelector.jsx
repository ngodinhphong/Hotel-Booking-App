import React, { useEffect, useState } from 'react';
import { getRoomTypes } from '../utils/ApiFuntions';

const RoomTypeSelector = ({ handleRoomInputChange, newRoom }) => {
  const [roomTypes, setRoomTypes] = useState([""])
  const [showNewRoomTypeInput, setShowNewRoomTypeInput] = useState(false)
  const [newRoomType, setNewRoomType] = useState("")

  useEffect(() => {
    getRoomTypes().then((data) => {
      setRoomTypes(data.data)
      
    })
  }, [])

  const handleNewRoomTypeInputChange = (e) => {
    setNewRoomType(e.target.value)
  }

  const handleAddNewRoomType = () => {
    if (newRoomType !== "") {
      const newTypeObject = { roomType: newRoomType }; // Tạo đối tượng mới với thuộc tính roomType
      setRoomTypes([...roomTypes, newTypeObject]); // Thêm đối tượng vào mảng roomTypes
      setNewRoomType(""); // Đặt lại giá trị của newRoomType thành rỗng
      setShowNewRoomTypeInput(false);
    }
  }

  return (
    <>
      {roomTypes.length > 0 && (
        <div>
          <select
            required
            className="form-select"
            name="roomType"
            onChange={(e) => {
              if (e.target.value === "Add New") {
                setShowNewRoomTypeInput(true);
              } else {
                handleRoomInputChange(e);
              }
            }}
            value={newRoom.roomType}>
            <option value="">Select a room type</option>
            <option value={"Add New"}>Add New</option>
            {roomTypes.map((type, index) => (
              <option key={index} value={type.roomType}>
                {type.roomType}
              </option>
            ))}
          </select>
          {showNewRoomTypeInput && (
            <div className="mt-2">
              <div className="input-group">
                <input
                  type="text"
                  className="form-control"
                  placeholder="Enter New Room Type"
                  value={newRoomType}
                  onChange={handleNewRoomTypeInputChange}
                />
                <button className="btn btn-hotel" type="button" onClick={handleAddNewRoomType}>
                  Add
                </button>
              </div>
            </div>
          )}
        </div>
      )}
    </>
  );
}

export default RoomTypeSelector
