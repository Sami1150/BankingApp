import React, { useState, useEffect } from 'react';
import axios from 'axios';

const EditAccountForm = ({ account, onSave, onClose }) => {
  const [editedAccount, setEditedAccount] = useState({ ...account });

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setEditedAccount({
      ...editedAccount,
      [name]: value,
    });
  };

  const handleSave = async () => {
    try {
      // Send a PUT request to update the account information
      await axios.put(`/api/v1/account/edit`, editedAccount, {
        withCredentials: true,
        headers: {
          'Authorization': 'Basic ' + btoa('admin:admin'),
        },
      });

      // Call the onSave function to update the account information in the parent component
      onSave(editedAccount);

      // Close the edit form
      onClose();
    } catch (error) {
      console.error('Error updating account:', error);
    }
  };

  return (
    <div className="edit-account-form">
      <h2>Edit Account</h2>
      <form>
        <div className="mb-3">
          <label htmlFor="email" className="form-label">
            Email
          </label>
          <input
            type="email"
            className="form-control"
            id="email"
            name="email"
            value={editedAccount.email}
            onChange={handleInputChange}
          />
        </div>
        <div className="mb-3">
          <label htmlFor="name" className="form-label">
            Name
          </label>
          <input
            type="text"
            className="form-control"
            id="name"
            name="name"
            value={editedAccount.name}
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
            value={editedAccount.address}
            onChange={handleInputChange}
          />
        </div>
        <button
          type="button"
          className="btn btn-primary"
          onClick={handleSave}
        >
          Save
        </button>
        <button
          type="button"
          className="btn btn-secondary"
          onClick={onClose}
        >
          Cancel
        </button>
      </form>
    </div>
  );
};
export default EditAccountForm;