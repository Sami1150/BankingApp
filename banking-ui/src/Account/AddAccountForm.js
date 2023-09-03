import React, { useState } from 'react';
import axios from 'axios';

const AddAccountForm = ({ onAdd }) => {
  const [newAccount, setNewAccount] = useState({
    name: '',
    email: '',
    address: '',
  });

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setNewAccount({
      ...newAccount,
      [name]: value,
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      // Send a POST request to create a new account
      const response = await axios.post('/api/v1/account/add', newAccount, {
        withCredentials: true,
        headers: {
          'Authorization': 'Basic ' + btoa('admin:admin'),
        },
        
      });

      // Call the onAdd function to add the new account to the list
      onAdd(response.data);

      // Clear the form fields
      setNewAccount({
        name: '',
        email: '',
        address: '',
      });
    } catch (error) {
      console.error('Error creating account:', error);
    }
  };

  return (
    <div className="add-account-form">
      <h2>Add New Account</h2>
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
      </form>
    </div>
  );
};

export default AddAccountForm;
