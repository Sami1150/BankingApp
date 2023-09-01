import React, { useState } from 'react';

const EditAccount = () => {
  const [AccountData, setAccountData] = useState({
    name: '',
    email: '',
    address: '',
  });

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setAccountData({
      ...AccountData,
      [name]: value,
    });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    // You can perform actions with the Account data here, such as sending it to an API
    console.log('Account Data:', AccountData);
  };

  return (
    <div className="container">
      <h1>Edit Account</h1>
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
            value={AccountData.name}
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
            value={AccountData.email}
            onChange={handleInputChange}
          />
        </div>
        <div className="mb-3">
          <label htmlFor="address" className="form-label">
            Address
          </label>
          <textarea
            className="form-control"
            id="address"
            name="address"
            rows="3"
            value={AccountData.address}
            onChange={handleInputChange}
          />
        </div>
        <button type="submit" className="btn btn-primary">
          Edit Account
        </button>
      </form>
    </div>
  );
};

export default EditAccount;
