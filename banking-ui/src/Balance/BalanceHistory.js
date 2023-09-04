import React, { useState, useEffect } from 'react';
import axios from 'axios';

const BalanceHistoryTable = ({ balanceHistory }) => {
  // Check if balanceHistory is an array and has at least one record
  if (Array.isArray(balanceHistory) && balanceHistory.length > 0) {
    return (
      <div className="balance-history">
        <h2>Balance History</h2>
        <table className="table table-striped">
          <thead>
            <tr>
              <th>Balance Id</th>
              <th>Date</th>
              <th>Amount</th>
              <th>Balance Type</th>
            </tr>
          </thead>
          <tbody>
            {balanceHistory.map((balance) => (
              <tr key={balance.balance_id}>
                <td>{balance.balance_id}</td>
                <td>{balance.date}</td>
                <td>{balance.amount}</td>
                <td>{balance.balanceType}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    );
  } else if (balanceHistory) {
    // If it's not an array but is a single balance history, use it
    return (
      <div className="balance-history">
        <h2>Balance History</h2>
        <table className="table table-striped">
          <thead>
            <tr>
              <th>Balance Id</th>
              <th>Date</th>
              <th>Amount</th>
              <th>Balance Type</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td>{balanceHistory.balance_id}</td>
              <td>{balanceHistory.date}</td>
              <td>{balanceHistory.amount}</td>
              <td>{balanceHistory.balanceType}</td>
            </tr>
          </tbody>
        </table>
      </div>
    );
  } else if (balanceHistory.length ===0){
    // If balanceHistory is empty or undefined, display a message
    return (
      <div className="balance-history">
        <h2>Balance History</h2>
        <p>No Balance History Data Available</p>
      </div>
    );
  }
};

const BalanceHistory = () => {
  const [balanceHistory, setBalanceHistory] = useState([]);

  useEffect(() => {
    loadBalanceHistory();
  }, []);

  const loadBalanceHistory = async () => {
    try {
      const response = await axios.get('/api/v1/balance', {
        withCredentials: true,
        headers: {
          'Authorization': 'Basic ' + btoa('admin:admin'),
        },
      });
      setBalanceHistory(response.data.content);
    } catch (error) {
      console.error('Error loading balance history:', error);
    }
  };

  return (
    <div className="container">
      <BalanceHistoryTable balanceHistory={balanceHistory} />
    </div>
  );
};

export default BalanceHistory;
