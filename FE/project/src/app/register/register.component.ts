import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, FormsModule],
  template: `
    <div class="auth-container">
      <h2>Register</h2>
      <form (ngSubmit)="onRegister()">
        <div class="form-group">
          <label for="username">Username:</label>
          <input type="text" id="username" [(ngModel)]="username" name="username" required>
        </div>
        <div class="form-group">
          <label for="firstname">First Name:</label>
          <input type="text" id="firstname" [(ngModel)]="firstname" name="firstname" required>
        </div>
        <div class="form-group">
          <label for="lastname">Last Name:</label>
          <input type="text" id="lastname" [(ngModel)]="lastname" name="lastname" required>
        </div>
        <div class="form-group">
          <label for="email">Email:</label>
          <input type="email" id="email" [(ngModel)]="email" name="email" required>
        </div>
        <div class="form-group">
          <label for="password">Password:</label>
          <input type="password" id="password" [(ngModel)]="password" name="password" required>
        </div>
        <button type="submit">Register</button>
      </form>
    </div>
  `,
  styles: [`
    .auth-container {
      max-width: 400px;
      margin: 50px auto;
      padding: 20px;
      border: 1px solid #ccc;
      border-radius: 5px;
    }
    .form-group {
      margin-bottom: 15px;
    }
    .form-group label {
      display: block;
      margin-bottom: 5px;
    }
    .form-group input {
      width: 100%;
      padding: 8px;
      border: 1px solid #ccc;
      border-radius: 4px;
    }
    button {
      background-color: #28a745;
      color: white;
      padding: 10px 15px;
      border: none;
      border-radius: 4px;
      cursor: pointer;
    }
    button:hover {
      background-color: #218838;
    }
  `]
})
export class RegisterComponent {
  username: string = '';
  firstname: string = '';
  lastname: string = '';
  email: string = '';
  password: string = '';

  constructor(private router: Router) {}

  onRegister() {
    const registerData = {
      username: this.username,
      password: this.password,
      firstname: this.firstname,
      lastname: this.lastname,
      email: this.email
    };

    fetch('http://localhost:8080/register', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(registerData)
    })
    .then(response => response.json())
    .then(data => {
      console.log('Registration successful:', data);
      alert('Registration successful!');
      this.router.navigate(['/login']); // Navigate to login after registration
    })
    .catch(error => {
      console.error('Registration error:', error);
      alert('Registration failed. Please try again.');
    });
  }
}