import React, { useState } from 'react';
import axios from 'axios';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

const AddAccountForm = ({ onAdd }) => {
  const [newAccount, setNewAccount] = useState({
    name: '',
    email: '',
    address: '',
  });

  const [password, setPassword] = useState(''); // Separate state for password

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setNewAccount({
      ...newAccount,
      [name]: value,
    });
  };

  const handlePasswordChange = (e) => {
    setPassword(e.target.value); // Update the password state
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      // Send a POST request to create a new account with the password as a parameter
      const response = await axios.post('/api/v1/account/add', newAccount, {
        params: {
          password: password, // Pass the password as a parameter
        },
        withCredentials: true,
        headers: {
          'Authorization': 'Basic ' + btoa('admin:admin'),
        },
      });

      if (response.status === 200) {
        // Show a success toast notification
        toast.success('Account created successfully!', {
          position: toast.POSITION.TOP_RIGHT,
        });
        
        // Call the onAdd function to add the new account to the list
        onAdd(response.data);

        // Clear the form fields and password
        setNewAccount({
          name: '',
          email: '',
          address: '',
        });
        setPassword('');
      } else {
        // Show an error toast notification
        toast.error('Error creating account. Please try again.', {
          position: toast.POSITION.TOP_RIGHT,
        });
      }
    } catch (error) {
      // Handle errors and show an error toast notification
      toast.error('Error creating account. Please try again.', {
        position: toast.POSITION.TOP_RIGHT,
      });
      console.error('Error creating account:', error);
    }
  };

  return (
    <div className="add-account-form">
      {/* <h2>Add New Account</h2> */}
      <br />
      <form onSubmit={handleSubmit}>
        <div className="mb-3">
          <label htmlFor="name" className="form-label">
            Name
          </label>
          <input
            type="text"
            className="form-control"
            id="name"
            name="name"
            value={newAccount.name}
            onChange={handleInputChange}
          />
        </div>
        <div className="mb-3">
          <label htmlFor="email" className="form-label">
            Email
          </label>
          <input
            type="email"
            className="form-control"
            id="email"
            name="email"
            value={newAccount.email}
            onChange={handleInputChange}
          />
        </div>
        <div className="mb-3">
          <label htmlFor="password" className="form-label">
            Password
          </label>
          <input
            type="password" // Use type="password" for password input
            className="form-control"
            id="password"
            name="password"
            value={password}
            onChange={handlePasswordChange}
          />
        </div>
        <div className="mb-3">
          <label htmlFor="address" className="form-label">
            Address
          </label>
          <input
            type="text"
            className="form-control"
            id="address"
            name="address"
            value={newAccount.address}
            onChange={handleInputChange}
          />
        </div>
        <button type="submit" className="btn btn-primary">
          Create Account
        </button>
        <br />
      <br />
      </form>
      <ToastContainer />
    </div>
  );
};

export default AddAccountForm;
