import React, { useState, useEffect } from 'react';
import axios from 'axios';

const TransactionTable = ({ transactions }) => {
  // Check if transactions is an array and has at least one record
  if (!transactions || transactions.length === 0){
    return (
      <div className="transaction-history">
        <h2>Transaction History</h2>
        <p>No Transaction History Data Available</p>
      </div>
    );
  }
  else if (Array.isArray(transactions) && transactions.length > 0) {
    return (
      <div className="transaction-history">
        <h2>Transaction History</h2>
        <table className="table table-striped">
          <thead>
            <tr>
              <th>Transaction ID</th>
              <th>Date</th>
              <th>Amount</th>
              <th>Transaction Type</th>
              <th>Description</th>
            </tr>
          </thead>
          <tbody>
            {transactions.map((transaction) => (
              <tr key={transaction.transaction_id}>
                <td>{transaction.transaction_id}</td>
                <td>{transaction.date}</td>
                <td>{transaction.amount}</td>
                <td>{transaction.transactionType}</td>
                <td>{transaction.description}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    );
  } else if (transactions) {
    // If it's not an array but is a single transaction, use it
    return (
      <div className="transaction-history">
        <h2>Transaction History</h2>
        <table className="table table-striped">
          <thead>
            <tr>
              <th>Transaction ID</th>
              <th>Date</th>
              <th>Amount</th>
              <th>Transaction Type</th>
              <th>Description</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td>{transactions.transaction_id}</td>
              <td>{transactions.date}</td>
              <td>{transactions.amount}</td>
              <td>{transactions.transactionType}</td>
              <td>{transactions.description}</td>
            </tr>
          </tbody>
        </table>
      </div>
    );
  }
};

const App = () => {
  const [transactions, setTransactions] = useState([]);

  useEffect(() => {
    loadTransactions();
  }, []);

  const loadTransactions = async () => {
    try {
      const response = await axios.get('/api/v1/transaction', {
        withCredentials: true,
        headers: {
          'Authorization': 'Basic ' + btoa('admin:admin'),
        },
      });
      setTransactions(response.data.content);
      console.log(transactions);
    } catch (error) {
      console.error('Error loading transaction history:', error);
    }
  };

  return (
    <div className="container">
      <TransactionTable transactions={transactions} />
    </div>
  );
};

export default App;
