import React, { useEffect, useState } from 'react';
import axios from 'axios';

function SellBook({ match }) {
  const bookId = match.params.id;
  const [book, setBook] = useState(null);

  useEffect(() => {
    axios.get(`/api/books/all/${bookId}`)
      .then(response => {
        setBook(response.data);
      })
      .catch(error => {
        console.error('Error fetching book:', error);
      });
  }, [bookId]);

  const handleSell = () => {
    axios.post(`/api/books/sell/${bookId}`)
      .then(response => {
        alert(response.data);
      })
      .catch(error => {
        console.error('Error selling book:', error);
        alert('Error selling book. Please try again.');
      });
  };

  if (!book) {
    return <p>Loading book details...</p>;
  }

  return (
    <div className="container">
      <h2>Sell Book to Customer</h2>
      <h3>{book.title}</h3>
      <p>Author: {book.author}</p>
      <p>Price: ${book.currentPrice.toFixed(2)}</p>
      <button className="btn btn-primary" onClick={handleSell}>Sell Book</button>
    </div>
  );
}

export default SellBook;
