import React, { useState } from 'react';
import axios from 'axios';
// import { useHistory } from 'react-router-dom'; // Import useHistory

const LogoutButton = () => {
//   const history = useHistory(); // Initialize history

  const handleLogout = async () => {
    try {
      // Send a POST request to the server's logout endpoint
      const response = await axios.post('logout', null, {
        withCredentials: true, // Include credentials if needed
        headers: {
          'Authorization': 'Basic ' + btoa('admin:admin'), // Add your authorization headers here
        },
      });

      // Handle the logout response
      if (response.status === 200) {
        // Logout was successful
        console.log('User logged out');
        // history.push('http://localhost:8080'); // Redirect to the desired URL
      } else {
        // Handle logout failure
        console.error('Logout failed');
      }
    } catch (error) {
      // Handle errors, e.g., network issues
      console.error('Error during logout:', error);
    }
  };

  return (
    <button onClick={handleLogout}>Logout</button>
  );
};

export default LogoutButton;
