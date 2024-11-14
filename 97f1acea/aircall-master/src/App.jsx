import React from 'react';

import Header from './Header.jsx';
import Content from './Content.jsx';

const App = () => {
  return (
    <div className='container-sm w-75 mx-auto bg-success bg-opacity-25'>
      <Header />
      <Content />
    </div>
  );
};

export default App;
