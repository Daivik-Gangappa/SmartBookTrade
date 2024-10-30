import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import BookList from './components/BookList';
import BuyBook from './components/BuyBook';
import SellBook from './components/SellBook';

function App() {
  return (
    <Router>
      <div>
        <nav className="navbar navbar-expand-lg navbar-light bg-light">
          <a className="navbar-brand" href="/">Textbook Web Application</a>
        </nav>
        <div className="container mt-4">
          <Routes>
			<Route path="/" element={<BookList />} />
			<Route path="/buy/:id" element={<BuyBook />} />
			<Route path="/sell/:id" element={<SellBook />} />
		  </Routes>
        </div>
      </div>
    </Router>
  );
}

export default App;
