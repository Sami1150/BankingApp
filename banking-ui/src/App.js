import './App.css';
import "../node_modules/bootstrap/dist/css/bootstrap.min.css"
import Navbar from './layout/Navbar';
import Login from './Pages/Login';
import ViewAccounts from './Account/ViewAccounts';
import EditAccount from './Account/EditAccount'
import CreateAccount from './Account/AddAccount';
import BalanceHistory from './Balance/BalanceHistory';

function App() {
  return (
    <div className="App">
      <Navbar />
      {/* <Home /> */}
      {/* <Login /> */}
      {/* <ViewAccounts/> */}
      {/* <EditAccount /> */}
      {/* <CreateAccount /> */}
      <BalanceHistory />
    </div>
  );
}

export default App;
