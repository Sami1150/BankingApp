import React, { useState } from 'react';
import axios from 'axios';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

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
      const response = await axios.post(
        `/api/v1/transaction/${transactionType}`,
        null,
        {
          params: { amount: parseFloat(amount) },
          withCredentials: true,
          headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Basic ' + btoa('admin:admin'),
          },
        }
      );

      if (response.status === 200) {
        // Show a success toast notification
        toast.success('Transaction successful!', {
          position: toast.POSITION.TOP_RIGHT,
        });
      } else {
        // Show an error toast notification
        toast.error('Transaction failed. Funds not available.', {
          position: toast.POSITION.TOP_RIGHT,
        });
      }
    } catch (error) {
      // Handle errors and show an error toast notification
      toast.error('Transaction failed. Funds not available.', {
        position: toast.POSITION.TOP_RIGHT,
      });
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
      <ToastContainer />
    </div>
  );
};

export default TransactionForm;
