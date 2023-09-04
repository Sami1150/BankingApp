import './App.css';
import "../node_modules/bootstrap/dist/css/bootstrap.min.css"
import React from 'react';
import {
  BrowserRouter as Router,
  Routes,
  Route,
} from "react-router-dom";

import ViewAccounts from './Account/ViewAccounts';
import BalanceHistory from './Balance/BalanceHistory';
import ViewUserAccountDetails from './Account/ViewUserAccountDetails';
import TransactionHistory from './Transaction/TransactionHistory';
import TransactionForm from './Transaction/TransactionForm';
import TransferFunds from './Transaction/TransferFunds';
import LogoutButton from './Pages/Logout';
import AdminNavbar from './layout/AdminNavbar';
import UserNavbar from './layout/UserNavbar';
function App() {
  // return (
  //   <div className="App">
  //     <Router>
  //       <NavigationBar />
  //       {/* <Home /> */}
  //       <Routes>

  //         <Route path="/" element={<ViewUserAccountDetails />}></Route>
  //         <Route path="/BalanceHistory" element={<BalanceHistory />}></Route>
  //         <Route path="/TransactionHistory" element={<TransactionHistory />}></Route>
  //         <Route path="/TransactionForm" element={<TransactionForm />}></Route>
  //         <Route path="/TransferFunds" element={<TransferFunds />}></Route>
  //         <Route path="/Logout" element={<LogoutButton />}></Route>
  //         <Route path='/ViewAccounts' element={<ViewAccounts />}></Route>
  //       </Routes>

  //     </Router>
  //   </div>
  // );
  return (
    <div className="App">
      {/* <Router>
        <AdminNavbar />
        <Routes>

          <Route path="/" element={<ViewAccounts />}></Route>
          <Route path="/Logout" element={<LogoutButton />}></Route>
        </Routes>

      </Router> */}
      <UserNavbar />
    </div>
  );
}

export default App;
