import React, { useState } from 'react';
import axios from 'axios';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

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

    // Check if the amount is greater than 100
    if (parseFloat(amount) <= 0) {
      // Show an error toast notification
      toast.error('Amount must be greater than 0', {
        position: toast.POSITION.TOP_RIGHT,
      });
      return; // Exit the function if the amount is not valid
    }

    try {
      const response = await axios.post(
        '/api/v1/transaction/transferfunds',
        null,
        {
          params: {
            amount: parseFloat(amount),
            email: email,
          },
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
        toast.error('Transaction failed. Something went wrong.', {
          position: toast.POSITION.TOP_RIGHT,
        });
      }

      console.log('Transaction successful:', response.data);
    } catch (error) {
      // Handle errors and show an error toast notification
      toast.error('Transaction failed. Something went wrong.', {
        position: toast.POSITION.TOP_RIGHT,
      });
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
      <ToastContainer />
    </div>
  );
};

export default TransferFunds;
