import Button from "react-bootstrap/Button";
import { Container, Row, Col, Form, Alert } from "react-bootstrap";
import { useState } from "react";
import { useNavigate } from "react-router-dom";

const LoginForm = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  const [error, setError] = useState([]);
  const [alert, setAlert] = useState(false);

  const navigate = useNavigate();

  const saveForm = (e) => {
    e.preventDefault();

    fetch("http://localhost:3001/auth/login", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        username: username,
        password: password,
      }),
    })
      .then((response) => {
        if (response.ok) {
          return response.json();
        } else {
          response.json().then((data) => {
            const stringError = [];
            data.errors.forEach((e) => stringError.push(e + " "));
            console.log(data.errors);
            setError(stringError);
          });
          throw new Error("Error!");
        }
      })
      .then((data) => {
        localStorage.setItem("token", data.token);
        console.log("token", data.token);
        navigate("/homepage");
      })
      .catch(() => {
        setAlert(true);
        console.error;
      });
  };

  return (
    <Container>
      <Row className="justify-content-center mt-5">
        <Col xs={12} md={8}>
          <Form onSubmit={saveForm}>
            <Form.Group className="mb-3" controlId="username">
              <Form.Label>Username</Form.Label>
              <Form.Control
                type="text"
                placeholder="Enter username"
                value={username}
                onChange={(e) => setUsername(e.target.value)}
                required
              />
            </Form.Group>

            <Form.Group className="mb-3" controlId="formBasicPassword">
              <Form.Label>Password</Form.Label>
              <Form.Control
                type="password"
                placeholder="Password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                required
              />
            </Form.Group>

            <Button variant="primary" type="submit">
              Submit
            </Button>
          </Form>
          {alert && (
            <Alert variant="danger" className="mt-3">
              ERRORE! {error}
            </Alert>
          )}
        </Col>
      </Row>
    </Container>
  );
};

export default LoginForm;
