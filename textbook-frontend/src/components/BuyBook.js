import React, { useState } from 'react';
import axios from 'axios';

function BuyBook({ match }) {
  const [condition, setCondition] = useState('normal');
  const bookId = match.params.id;

  const handleSubmit = (e) => {
    e.preventDefault();
    axios.post(`/api/books/buy/${bookId}?condition=${condition}`)
      .then(response => {
        alert(response.data);
      })
      .catch(error => {
        console.error('Error buying book:', error);
        alert('Error buying book. Please try again.');
      });
  };

  return (
    <div className="container">
      <h2>Buy Book from Student</h2>
      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label>Condition:</label>
          <select className="form-control" value={condition} onChange={(e) => setCondition(e.target.value)}>
            <option value="normal">Normal Wear</option>
            <option value="damaged">Excessive Damage</option>
          </select>
        </div>
        <button type="submit" className="btn btn-success">Buy Book</button>
      </form>
    </div>
  );
}

export default BuyBook;
