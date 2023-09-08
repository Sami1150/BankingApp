import React, { useState } from 'react';
import axios from 'axios';

export default function Login() {
  const [formData, setFormData] = useState({
    username: '',
    password: '',
  });

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      // Send a POST request to the /login endpoint with the form data
      const response = await axios.post('http://localhost:8080/login', formData);

      // Log the response to the console
      console.log('Login response:', response);

      // You can add logic here to handle the response as needed

    } catch (error) {
      // Handle any errors
      console.error('Login error:', error);
    }
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value,
    });
  };

  return (
    <div className="container">
      <form className="form-signin" onSubmit={handleSubmit}>
        <h2 className="form-signin-heading">Welcome to Equity Bank</h2>
        <h2 className="form-signin-heading">Please sign in</h2>
        <p>
          <label htmlFor="username" className="sr-only">
            Username
          </label>
          <input
            type="text"
            id="username"
            name="username"
            className="form-control"
            placeholder="Username"
            required
            autoFocus
            value={formData.username}
            onChange={handleInputChange}
          />
        </p>
        <p>
          <label htmlFor="password" className="sr-only">
            Password
          </label>
          <input
            type="password"
            id="password"
            name="password"
            className="form-control"
            placeholder="Password"
            required
            value={formData.password}
            onChange={handleInputChange}
          />
        </p>
        <button className="btn btn-lg btn-primary btn-block" type="submit">
          Sign in
        </button>
      </form>
    </div>
  );
}
