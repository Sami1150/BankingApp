import React, { useEffect, useState } from 'react'
import axios from 'axios'

export default function Home () {

    const [Account,setAccount]=useState([])
    useEffect(()=>{
        loadAccounts();
    },[])

    const loadAccounts = async() => {
        const result =await axios.get("http://localhost:8080")
        console.log(result);
    }

  return (
    <div classNameName="container">
        <div classNameName='py-6'>
        <table className="table border shadow">
  <thead>
    <tr>
      <th scope="col">#</th>
      <th scope="col">First</th>
      <th scope="col">Last</th>
      <th scope="col">Handle</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th scope="row">1</th>
      <td>Mark</td>
      <td>Otto</td>
      <td>@mdo</td>
    </tr>
    <tr>
      <th scope="row">2</th>
      <td>Jacob</td>
      <td>Thornton</td>
      <td>@fat</td>
    </tr>
    <tr>
      <th scope="row">3</th>
      <td colspan="2">Larry the Bird</td>
      <td>@twitter</td>
    </tr>
  </tbody>
</table>
        </div>

    </div>
  )
}
