import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { AiTwotoneDelete, AiTwotoneEdit } from 'react-icons/ai';
import AddAccountForm from '../Account/AddAccountForm';
import EditAccountForm from '../Account/EditAccountForm';

const AccountTable = ({ accounts, onDelete, onEdit }) => {
  const handleDelete = async (id) => {
    try {
      // Send a DELETE request to the API endpoint to delete the account
      await axios.delete(`/api/v1/account/${id}`, {
        withCredentials: true,
        headers: {
          'Authorization': 'Basic ' + btoa('admin:admin'),
        },
      });

      // Call the onDelete function to remove the deleted account from the UI
      onDelete(id);

      // Show a success toast notification
      toast.success('Account deleted successfully!', {
        position: toast.POSITION.TOP_RIGHT,
      });
    } catch (error) {
      // Show an error toast notification
      toast.error('Error deleting account. Please try again.', {
        position: toast.POSITION.TOP_RIGHT,
      });

      console.error('Error deleting account:', error);
    }
  };

  return (
    <table className="table table-striped shadow">
      <thead className='thead-dark'>
        <tr>
          <th>ID</th>
          <th>Name</th>
          <th>Email</th>
          <th>Address</th>
          <th>Action</th>
        </tr>
      </thead>
      <tbody>
        {accounts.map((account) => (
          <tr key={account.id}>
            <td>{account.id}</td>
            <td>{account.name}</td>
            <td>{account.email}</td>
            <td>{account.address}</td>
            <td>
              <button className='btn' onClick={() => onEdit(account)}>
                <h4><AiTwotoneEdit /></h4>
              </button>
              <button className='btn' onClick={() => handleDelete(account.id)}>
                <h4><AiTwotoneDelete /></h4>
              </button>
            </td>
          </tr>
        ))}
      </tbody>
    </table>
  );
};

const ViewAccounts = () => {
  const [accounts, setAccounts] = useState([]);
  const [editingAccount, setEditingAccount] = useState(null);
  const [showEditForm, setShowEditForm] = useState(false);
  const [showAddForm, setShowAddForm] = useState(false); // Add this state

  useEffect(() => {
    loadAccounts();
  }, []);

  const loadAccounts = async () => {
    try {
      const response = await axios.get('/api/v1/account', {
        withCredentials: true,
        headers: {
          'Authorization': 'Basic ' + btoa('admin:admin'),
        },
      });
      setAccounts(response.data.content);
    } catch (error) {
      console.error('Error loading accounts:', error);
    }
  };

  const handleDeleteAccount = (id) => {
    // Update the accounts state by filtering out the deleted account
    setAccounts((prevAccounts) =>
      prevAccounts.filter((account) => account.id !== id)
    );
  };

  const handleAddNewAccount = () => {
    // Show the add account form when the button is clicked
    setShowAddForm(true);
  };

  // Add this function to handle adding the new account to the list
  const handleAddAccount = (newAccount) => {
    setAccounts([...accounts, newAccount]);
    setShowAddForm(false); // Hide the add account form

    // Show a success toast notification
    toast.success('Account added successfully!', {
      position: toast.POSITION.TOP_RIGHT,
    });
  };

  const handleEditAccount = (account) => {
    // Set the account being edited and show the edit form
    setEditingAccount(account);
    setShowEditForm(true);
  };

  const handleSaveEditedAccount = (editedAccount) => {
    // Update the accounts state with the edited account
    setAccounts((prevAccounts) =>
      prevAccounts.map((account) =>
        account.id === editedAccount.id ? editedAccount : account
      )
    );

    // Show a success toast notification
    toast.success('Account updated successfully!', {
      position: toast.POSITION.TOP_RIGHT,
    });
  };

  const handleCloseEditForm = () => {
    // Reset the editingAccount and hide the edit form
    setEditingAccount(null);
    setShowEditForm(false);
  };

  return (
    <div className="container">
      <h1>Account List</h1>
      <br />
      <button
        type="button"
        className="btn btn-primary"
        onClick={handleAddNewAccount}
      >
        Add New Account
      </button>
      <br />
      {showAddForm && ( // Render the add account form when showAddForm is true
        <AddAccountForm onAdd={handleAddAccount} />
      )}
      {showEditForm && (
        <EditAccountForm
          account={editingAccount}
          onSave={handleSaveEditedAccount}
          onClose={handleCloseEditForm}
        />
      )}
      <AccountTable
        accounts={accounts}
        onDelete={handleDeleteAccount}
        onEdit={handleEditAccount}
      />
      <ToastContainer />
    </div>
  );
};

export default ViewAccounts;
