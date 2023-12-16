import React, { useEffect, useState } from 'react';
import axios from 'axios';

const ReservationList = () => {
  const [reservations, setReservations] = useState([]);

  useEffect(() => {
    axios.get('http://localhost:8080/api/reservations')
      .then(response => setReservations(response.data))
      .catch(error => console.error('Error fetching reservations:', error));
  }, []);

  return (
    <div>
      <h2>Reservations</h2>
      <ul>
        {reservations.map(reservation => (
          <li key={reservation.id}>
            {reservation.guestName} - {reservation.checkInDate} to {reservation.checkOutDate}
          </li>
        ))}
      </ul>
    </div>
  );
};

export default ReservationList;
