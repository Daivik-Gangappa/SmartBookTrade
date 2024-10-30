import React from 'react';
import { useNavigate } from 'react-router-dom';

function BookItem({ book }) {
  const navigate = useNavigate();

  const handleBuy = () => {
    navigate(`/buy/${book.id}`);
  };

  const handleSell = () => {
    navigate(`/sell/${book.id}`);
  };

  return (
    <div className="col-md-4">
      <div className="card mb-4">
        <div className="card-body">
          <h5 className="card-title">{book.title}</h5>
          <h6 className="card-subtitle mb-2 text-muted">{book.author}</h6>
          <p className="card-text">Price: ${book.currentPrice.toFixed(2)}</p>
          <button className="btn btn-primary mr-2" onClick={handleSell}>Sell Book</button>
          <button className="btn btn-secondary" onClick={handleBuy}>Buy Book</button>
        </div>
      </div>
    </div>
  );
}

export default BookItem;
