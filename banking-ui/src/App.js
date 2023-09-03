import './App.css';
import "../node_modules/bootstrap/dist/css/bootstrap.min.css"
import Navbar from './layout/Navbar';
// import Home from './Pages/Home';
import Login from './Pages/Login';
import ViewAccounts from './Account/ViewAccounts';

import EditAccount from './Account/EditAccountForm'
import CreateAccount from './Account/AddAccountForm';
import BalanceHistory from './Balance/BalanceHistory';
import ViewUserAccountDetails from './Account/ViewUserAccountDetails';
import TransactionHistory from './Transaction/TransactionHistory';
import TransactionForm from './Transaction/TransactionForm';
import TransferFunds from './Transaction/TransferFunds';

function App() {
  return (
    <div className="App">
            <Navbar />
      {/* Done Hay */}
    {/* <ViewAccounts/>       */}
    {/* < ViewUserAccountDetails /> */}


      {/* <BalanceHistory /> */}

      {/* <TransactionHistory /> */}
{/* <TransactionForm /> */}

<TransferFunds />
      
    </div>
  );
}

export default App;
