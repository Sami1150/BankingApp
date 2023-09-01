import React from 'react'
import { AiTwotoneDelete } from 'react-icons/ai';

export default function ViewAccounts() {
    return (
        <div className='container'>
            <table class="table">
                <thead>
                    <tr>
                        <th scope="col">Id</th>
                        <th scope="col">Name</th>
                        <th scope="col">Email</th>
                        <th scope="col">Address</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <th scope="row">1</th>
                        <td>Mark</td>
                        <td>Otto</td>
                        <td>Chungi</td>

                    </tr>
                    <tr>
                        <th scope="row">2</th>
                        <td>Jacob</td>
                        <td>Thornton</td>
                        <td>City</td>


                    </tr>
                </tbody>
            </table>
        </div>
    )
}
