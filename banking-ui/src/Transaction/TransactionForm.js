import React, { useState } from 'react';
import axios from 'axios';

const TransactionForm = () => {
  const [amount, setAmount] = useState(0);
  const [transactionType, setTransactionType] = useState('addfunds'); // Default to add funds

  const handleAmountChange = (event) => {
    setAmount(event.target.value);
  };

  const handleTransactionTypeChange = (event) => {
    setTransactionType(event.target.value);
  };

  const handleSubmit = async (event) => {
    event.preventDefault();

    try {
      // Make a POST request based on the selected transaction type
      const response = await axios.post(
        `/api/v1/transaction/${transactionType}`,null,
        {
          params: {amount:parseFloat(amount)} // Convert amount to a number
        },
        {
          withCredentials: true,
          headers: {
            'Content-Type': 'application/json', // Add this line
            'Authorization': 'Basic ' + btoa('admin:admin'), // Add your authorization headers here
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
              <h2 className="card-title">Transaction Form</h2>
              <form onSubmit={handleSubmit}>
                <div className="mb-3">
                  <label htmlFor="amount" className="form-label">
                    Amount:
                  </label>
                  <input
                    type="amount"
                    id="amount"
                    name="amount"
                    value={amount}
                    onChange={handleAmountChange}
                    className="form-control"
                    required
                  />
                </div>
                <div className="mb-3">
                  <label htmlFor="transactionType" className="form-label">
                    Transaction Type:
                  </label>
                  <select
                    id="transactionType"
                    name="transactionType"
                    value={transactionType}
                    onChange={handleTransactionTypeChange}
                    className="form-select"
                  >
                    <option value="addfunds">Add Funds</option>
                    <option value="withdrawfunds">Withdraw Funds</option>
                  </select>
                </div>
                <button type="submit" className="btn btn-primary">
                  Submit
                </button>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

  export default TransactionForm;
