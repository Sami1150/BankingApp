// import React from 'react';
// import 'bootstrap/dist/css/bootstrap.min.css';
// import { Link } from 'react-router-dom';
// import './Navbar.css';

// const NavigationBar = () => {
//     return (

//     <nav className="navbar navbar-expand-lg bg-body-tertiary">
//   <div className="container-fluid">
//     <Link className="navbar-brand" to="/">Equity Bank</Link>
//     <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
//       <span className="navbar-toggler-icon"></span>
//     </button>
//     <div className="collapse navbar-collapse" id="navbarSupportedContent">
//       <ul className="navbar-nav me-auto mb-2 mb-lg-0">
//         <li className="nav-item">
//           <Link className="nav-link active" aria-current="page" to="/">Home</Link>
//         </li>
//         <li className="nav-item">
//           <Link className="nav-link" to="/BalanceHistory">Balance History</Link>
//         </li>
//         <li className="nav-item">
//           <Link className="nav-link" to="/TransactionHistory">Transaction History</Link>
//         </li>
//         <li className="nav-item">
//           <Link className="nav-link" to="/TransactionForm">Transaction</Link>
//         </li>
//         <li className="nav-item">
//           <Link className="nav-link" to="/TransferFunds">Transfer Funds</Link>
//         </li>
//         <li className="nav-item">
//           <Link className="nav-link" to="/ViewAccounts">Admin Panel</Link>
//         </li>
//         <li className="nav-item">
//           <Link className="nav-link" to="/Logout">Logout</Link>
//         </li>
//       </ul>
//     </div>
//   </div>
// </nav>
//     );
//   };
  
//   export default NavigationBar;
  


const UserNavbar = () => {
  return (
    <div className="App">
      <Router>
        <nav className="navbar navbar-expand-lg bg-body-tertiary">
          <div className="container-fluid">
            <Link className="navbar-brand" to="/">
              Equity Bank
            </Link>
            <button
              className="navbar-toggler"
              type="button"
              data-bs-toggle="collapse"
              data-bs-target="#navbarSupportedContent"
              aria-controls="navbarSupportedContent"
              aria-expanded="false"
              aria-label="Toggle navigation"
            >
              <span className="navbar-toggler-icon"></span>
            </button>
            <div
              className="collapse navbar-collapse"
              id="navbarSupportedContent"
            >
              <ul className="navbar-nav me-auto mb-2 mb-lg-0">
                <li className="nav-item">
                  <Link className="nav-link active" aria-current="page" to="/">
                    Home
                  </Link>
                </li>
                <li className="nav-item">
                  <Link className="nav-link" to="/BalanceHistory">
                    Balance History
                  </Link>
                </li>
                <li className="nav-item">
                  <Link className="nav-link" to="/TransactionHistory">
                    Transaction History
                  </Link>
                </li>
                <li className="nav-item">
                  <Link className="nav-link" to="/TransactionForm">
                    Transaction
                  </Link>
                </li>
                <li className="nav-item">
                  <Link className="nav-link" to="/TransferFunds">
                    Transfer Funds
                  </Link>
                </li>
                <li className="nav-item">
                  <Link className="nav-link" to="/ViewAccounts">
                    Admin Panel
                  </Link>
                </li>
                <li className="nav-item">
                  <Link className="nav-link" to="/Logout">
                    Logout
                  </Link>
                </li>
              </ul>
            </div>
          </div>
        </nav>

        <Routes>
          <Route path="/" element={<ViewUserAccountDetails />} />
          <Route path="/BalanceHistory" element={<BalanceHistory />} />
          <Route
            path="/TransactionHistory"
            element={<TransactionHistory />}
          />
          <Route path="/TransactionForm" element={<TransactionForm />} />
          <Route path="/TransferFunds" element={<TransferFunds />} />
          <Route path="/Logout" element={<LogoutButton />} />
          <Route path="/ViewAccounts" element={<ViewAccounts />} />
        </Routes>
      </Router>
    </div>
  );
};