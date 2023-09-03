import React, { useState } from 'react';
import axios from 'axios';

const TransferFunds = () => {
  const [email, setEmail] = useState('');
  const [amount, setAmount] = useState(0);

  const handleEmailChange = (event) => {
    setEmail(event.target.value);
  };

  const handleAmountChange = (event) => {
    setAmount(event.target.value);
  };

  const handleSubmit = async (event) => {
    event.preventDefault();

    try {
      // Make a POST request to transfer funds
      const response = await axios.post(
        '/api/v1/transaction/transferfunds',
        null,
        {
          params: {
            amount: parseFloat(amount), // Convert amount to a number
            email: email,
          },
          withCredentials: true,
          headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Basic ' + btoa('admin:admin'),
          },
        }
      );

      // Handle the response, you can show a success message or redirect as needed
      console.log('Transaction successful:', response.data);
    } catch (error) {
      // Handle errors, you can show an error message to the user
      console.error('Error making transaction:', error);
    }
  };

  return (
    <div className="container">
      <div className="row">
        <div className="col-md-6 offset-md-3">
          <div className="card">
            <div className="card-body">
              <h2 className="card-title">Transfer Funds Form</h2>
              <form onSubmit={handleSubmit}>
                <div className="mb-3">
                  <label htmlFor="email" className="form-label">
                    Receiver's Email:
                  </label>
                  <input
                    type="email"
                    id="email"
                    name="email"
                    value={email}
                    onChange={handleEmailChange}
                    className="form-control"
                    required
                  />
                </div>
                <div className="mb-3">
                  <label htmlFor="amount" className="form-label">
                    Amount:
                  </label>
                  <input
                    type="number"
                    id="amount"
                    name="amount"
                    value={amount}
                    onChange={handleAmountChange}
                    className="form-control"
                    required
                  />
                </div>
                <button type="submit" className="btn btn-primary">
                  Transfer Funds
                </button>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default TransferFunds;
