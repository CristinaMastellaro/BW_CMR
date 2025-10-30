import Button from "react-bootstrap/Button";
import { Container, Row, Col, Form, Alert } from "react-bootstrap";
import { useState } from "react";
import { useNavigate } from "react-router-dom";

const FormRegister = () => {
  const [username, setUsername] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");

  const [error, setError] = useState([]);
  const [alert, setAlert] = useState(false);

  const navigate = useNavigate();

  const saveForm = (e) => {
    e.preventDefault();

    fetch("http://localhost:3001/auth/register", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        username: username,
        email: email,
        password: password,
        firstname: firstName,
        lastname: lastName,
      }),
    })
      .then((response) => {
        if (response.ok) {
          return response.json();
        } else {
          const stringError = [];
          const errori = response.json().then((data) => {
            console.log(data.errors);
          });

          errori.forEach((e) => stringError.push(e));
          setError(stringError);
          console.log("string: " + stringError);
          throw new Error("Error!");
        }
      })
      .catch(() => {
        setAlert(true);
        console.error;
      });

    // navigate("/login");
  };

  return (
    <>
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

              <Form.Group className="mb-3" controlId="email">
                <Form.Label>Email address</Form.Label>
                <Form.Control
                  type="email"
                  placeholder="Enter email"
                  value={email}
                  onChange={(e) => setEmail(e.target.value)}
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

              <Form.Group className="mb-3" controlId="firstName">
                <Form.Label>First name</Form.Label>
                <Form.Control
                  type="text"
                  placeholder="Enter firstname"
                  value={firstName}
                  onChange={(e) => setFirstName(e.target.value)}
                  required
                />
              </Form.Group>

              <Form.Group className="mb-3" controlId="lastName">
                <Form.Label>Last name</Form.Label>
                <Form.Control
                  type="text"
                  placeholder="Enter last name"
                  value={lastName}
                  onChange={(e) => setLastName(e.target.value)}
                  required
                />
              </Form.Group>

              <Button variant="primary" type="submit">
                Submit
              </Button>
            </Form>
            {alert && <Alert variant="danger">ERRORE! {error}</Alert>}
          </Col>
        </Row>
      </Container>
    </>
  );
};
export default FormRegister;
