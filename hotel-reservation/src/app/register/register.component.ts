import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { catchError } from 'rxjs/operators';
import { throwError, of } from 'rxjs';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register-form',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
})
export class RegisterFormComponent {
  registrationForm: FormGroup;
  username: string = '';
  password: string = '';

  constructor(
    private http: HttpClient,
    private fb: FormBuilder,
    private router: Router
  ) {
    this.registrationForm = this.fb.group({
      username: ['', [Validators.required]],
      password: ['', [Validators.required]],
    });
  }
get usernameControl() {
    return this.registrationForm.get('username');
  }

  get passwordControl() {
    return this.registrationForm.get('password');
  }
  handleSubmit() {
    if (this.registrationForm.valid) {
      const newUser = {
        username: this.usernameControl?.value,
        password: this.passwordControl?.value,
      };
      this.handleRegistration(newUser);
    } else {
    }
  }

  private handleRegistration(user: any) {
    this.http
      .post<string>('http://localhost:8080/api/register', user)
      .pipe(
        catchError((error: HttpErrorResponse) => {
          if (error.status === 201) {
            console.log('User registered successfully');
            return of(error.error); // Return the error response (JWT token as a string)
          } else {
            console.error('Error registering user:', error);
            return throwError('Registration failed. Please try again.');
          }
        })
      )
      .subscribe(
        (response) => {
          console.log('Response:', response);
          if (response) {
            console.log(response.text);

            const token = response.text;
            console.log('User registered successfully. Received token:', token);

            // Store the token in a more secure way (e.g., HttpOnly cookie)
            localStorage.setItem('token', token);
          } else {
            console.error(
              'Unexpected response format. Please check the server. Response is null or not a string.',
              response
            );
          }
        },
        (error) => {
          console.error('Error in response:', error);

          // Log the raw response text for inspection
          console.log('Raw response text:', error.error);

          // Add any additional error handling as needed
        }
      );
    this.router.navigate(['/login']);
  }
}
