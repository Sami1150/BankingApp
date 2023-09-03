import React, { useState, useEffect } from 'react';
import axios from 'axios';


const AccountTable = ({ accounts }) => {
  let account = null;

  // Check if accounts is an array and has at least one record
  if (Array.isArray(accounts) && accounts.length > 0) {
    account = accounts[0];
  } else if (accounts) {
    // If it's not an array but is a single account, use it
    account = accounts;
  }

  // Conditional rendering
  if (!account) {
    return <p>No account data available.</p>;
  }

  return (
    <div className="account-details">
      <h2>Account Details</h2>
      <table className="table table-striped vertical-table">
        <tbody>
          <tr>
            <td>ID:</td>
            <td>{account.id}</td>
          </tr>
          <tr>
            <td>Name:</td>
            <td>{account.name}</td>
          </tr>
          <tr>
            <td>Email:</td>
            <td>{account.email}</td>
          </tr>
          <tr>
            <td>Address:</td>
            <td>{account.address}</td>
          </tr>
        </tbody>
      </table>
    </div>
  );
};




const App = () => {
  const [accounts, setAccounts] = useState([]);

  useEffect(() => {
    loadAccounts();
  }, []);

  const loadAccounts = async () => {
    try {
      const response = await axios.get('/api/v1/account', {
        withCredentials: true,
        headers: {
            'Authorization': 'Basic ' + btoa('admin:admin'),},});
      setAccounts(response.data.content);
    } catch (error) {
      console.error('Error loading accounts:', error);
    }
  };

  return (
    <div className="container">
      <AccountTable accounts={accounts} />
    </div>
  );
};

export default App;