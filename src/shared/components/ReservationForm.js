import React, { useState } from 'react';
import axios from 'axios';

const ReservationForm = () => {
  const [guestName, setGuestName] = useState('');
  const [checkInDate, setCheckInDate] = useState('');
  const [checkOutDate, setCheckOutDate] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();

    const newReservation = { guestName, checkInDate, checkOutDate };

    try {
      const response = await axios.post('/api/reservations', newReservation);
      console.log('Reservation created:', response.data);
      // You can update the state or navigate to another page after successful submission
    } catch (error) {
      console.error('Error creating reservation:', error);
    }
  };

  return (
    <div>
      <h2>Create Reservation</h2>
      <form onSubmit={handleSubmit}>
        <label>Guest Name:
          <input type="text" value={guestName} onChange={(e) => setGuestName(e.target.value)} />
        </label>
        <br />
        <label>Check-In Date:
          <input type="date" value={checkInDate} onChange={(e) => setCheckInDate(e.target.value)} />
        </label>
        <br />
        <label>Check-Out Date:
          <input type="date" value={checkOutDate} onChange={(e) => setCheckOutDate(e.target.value)} />
        </label>
        <br />
        <button type="submit">Submit</button>
      </form>
    </div>
  );
};

export default ReservationForm;
