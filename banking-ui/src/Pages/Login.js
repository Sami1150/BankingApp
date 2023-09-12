import React, { Component } from 'react';
import Axios from 'axios';

class Login extends Component {
  handleLoginClick = () => {
    Axios.post('/login')
      .then(function (response) {
        // Handle the success response here
        console.log('GET request to /login was successful:', response);
        // You can update the UI or do other things with the response data here
      })
      .catch(function (error) {
        // Handle any errors that occurred during the GET request
        console.error('Error making GET request to /login:', error);
      });
  };

  render() {
    return (
      <div>
        <meta charSet="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta name="description" content="" />
        <meta name="author" content="" />
        <title>Please sign in Sami Ahmad</title>
        <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M" crossOrigin="anonymous" />
        <link href="https://getbootstrap.com/docs/4.0/examples/signin/signin.css" rel="stylesheet" crossOrigin="anonymous" />
        <div className="container">
          <form className="form-signin" method="post" action="/login">
            <h2 className="form-signin-heading">Please sign in</h2>
            <p>
              <label htmlFor="username" className="sr-only">Username</label>
              <input type="text" id="username" name="username" className="form-control" placeholder="Username" required autoFocus />
            </p>
            <p>
              <label htmlFor="password" className="sr-only">Password</label>
              <input type="password" id="password" name="password" className="form-control" placeholder="Password" required />
            </p>
            {/* <input name="_csrf" type="hidden" defaultValue="ecdb1d91-3b5b-442f-9ccb-4f9a1e256883" /> */}
            <button className="btn btn-lg btn-primary btn-block" onClick={this.handleLoginClick}>Sign in</button>
          </form>
        </div>
      </div>
    );
  }
}

export default Login;
