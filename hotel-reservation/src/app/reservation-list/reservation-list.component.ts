import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-reservation-list',
  templateUrl: './reservation-list.component.html',
  styleUrls: ['./reservation-list.component.css'],
})
export class ReservationListComponent implements OnInit {
  reservations: any[] = [];

  constructor(private http: HttpClient) {}

  ngOnInit() {
    this.fetchReservations();
  }

  private fetchReservations() {
    this.http.get<any[]>('http://localhost:8080/api/reservations').subscribe(
      (response) => {
        this.reservations = response;
      },
      (error) => {
        console.error('Error fetching reservations:', error);
      }
    );
  }
}
