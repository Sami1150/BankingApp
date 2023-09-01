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
                        <th scope='col'>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <th scope="row">1</th>
                        <td>Mark</td>
                        <td>Otto</td>
                        <td>Chungi</td>
                        <td>
                            <button className='btn'><h4><AiTwotoneDelete /></h4></button>
                        </td>

                    </tr>
                    <tr>
                        <th scope="row">2</th>
                        <td>Jacob</td>
                        <td>Thornton</td>
                        <td>City</td>
                        <td>
                            <button className='btn'><h4><AiTwotoneDelete /></h4></button>
                        </td>

                    </tr>
                </tbody>
            </table>
        </div>
    )
}
