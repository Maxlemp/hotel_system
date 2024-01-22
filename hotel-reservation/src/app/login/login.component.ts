import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent {
  username: string = '';
  password: string = '';
  httpOptions: { headers: HttpHeaders } = { headers: new HttpHeaders({ 'Content-Type': 'application/json' }) };

  constructor(private http: HttpClient, private router: Router) {}

  handleSubmit() {
    const token = localStorage.getItem('token');
    console.log(token)
    const credentials = { username: this.username, password: this.password };

    if (token) {
      this.httpOptions.headers = this.httpOptions.headers.set('Authorization', `Bearer ${token}`);
    }

    this.http.post<any>('http://localhost:8080/api/login', credentials, this.httpOptions).subscribe(
      (response) => {
        const newToken = response;
        console.log('Login successful. Received token:', newToken);
        localStorage.setItem('token', newToken);
console.log(newToken)
        // Set the Authorization header for future requests
        this.httpOptions.headers = this.httpOptions.headers.set('Authorization', `Bearer ${newToken}`);

        // You can navigate to another route or perform other actions after successful login
      },
      (error) => {
        console.error('Error logging in:', error);

        if (error.status === 401) {
          console.error('Invalid credentials. Please try again.');
        } else if (error.status === 500) {
          console.error('Server error. Please try again later.');
        } else {
          console.error('Unexpected error. Please try again later.');
        }
      }
    );
    this.router.navigate(['/reservation-form']);
  }
}
