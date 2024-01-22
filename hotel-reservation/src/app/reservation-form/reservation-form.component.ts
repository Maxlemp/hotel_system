import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-reservation-form',
  templateUrl: './reservation-form.component.html',
  styleUrls: ['./reservation-form.component.css'],
})
export class ReservationFormComponent {
  guestName: string = '';
  checkInDate: string = '';
  checkOutDate: string = '';
  roomType: string='';
  numberOfNights!: number 
  constructor(private http: HttpClient,     private router: Router ) {}

  handleSubmit() {
    const newReservation = {
      guestName: this.guestName,
      checkInDate: this.checkInDate,
      checkOutDate: this.checkOutDate,
      roomType: this.roomType,
      numberOfNights: this.numberOfNights
    };

    this.createReservation(newReservation);
  }
  private createReservation(newReservation: any) {
    const headers = { 'Content-Type': 'application/json' };
  
    this.http.post('http://localhost:8080/api/reservations', newReservation, { headers }).subscribe(
      (response: any) => {
        console.log('Reservation created:', response.data);
      },
      (error) => {
        console.error('Error creating reservation:', error);
      }
    );
  }
  
  checkReservation(){
    this.router.navigate(['/reservation-list']);
  }
}
