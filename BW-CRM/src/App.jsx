import "./App.css";
import "bootstrap/dist/css/bootstrap.min.css";
import FormRegister from "./components/FormRegister";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import LoginForm from "./components/LoginForm";
import Clienti from "./components/Clienti";
function App() {
  return (
    <>
      <BrowserRouter>
        <Routes>
          <Route element={<FormRegister />} path="/" />
          <Route element={<LoginForm />} path="/login" />
          <Ruote element={<Clienti />} path="/homepage" />
        </Routes>
      </BrowserRouter>
    </>
  );
}

export default App;
