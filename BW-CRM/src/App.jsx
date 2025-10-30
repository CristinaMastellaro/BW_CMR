
import './App.css'
import 'bootstrap/dist/css/bootstrap.min.css';
import FormRegister from './components/FormRegister'
import { BrowserRouter, Routes, Route } from "react-router-dom";

function App() {


  return (
    <>
    <BrowserRouter>
    <Routes>
      
      <Route element={<FormRegister/>} path="/" />
  
    </Routes>
    </BrowserRouter>
    
    </>
  )
}

export default App
