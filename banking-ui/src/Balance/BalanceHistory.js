import React from 'react'
export default function BalanceHistory() {
    return (
        <div className='container'>
            <table className="table table striped">
                <thead>
                    <tr>
                        <th scope="col">Id</th>
                        <th scope="col">Date</th>
                        <th scope="col">Amount</th>
                        <th scope="col">Balance Type</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <th scope="row">1</th>
                        <td>01/09/2023</td>
                        <td>2000.00</td>
                        <td>CR</td>
    

                    </tr>
                    <tr>
                        <th scope="row">2</th>
                        <td>01/10/2023</td>
                        <td>-150.0</td>
                        <td>DB</td>
                    </tr>
                </tbody>
            </table>
        </div>
    )
}
