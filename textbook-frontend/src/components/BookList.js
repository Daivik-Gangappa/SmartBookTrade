import React, { useEffect, useState } from 'react';
import axios from 'axios';
import BookItem from './BookItem';

function BookList() {
  const [books, setBooks] = useState([]);

  useEffect(() => {
    axios.get('/api/books/inventory')
      .then(response => {
        setBooks(response.data);
      })
      .catch(error => {
        console.error('Error fetching books:', error);
      });
  }, []);

  return (
    <div className="container">
      <h2>Available Books</h2>
      <div className="row">
        {books.map(book => (
          <BookItem key={book.id} book={book} />
        ))}
      </div>
    </div>
  );
}

export default BookList;
