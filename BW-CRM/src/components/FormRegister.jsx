import Button from "react-bootstrap/Button";
import { Container, Row, Col, Form, Alert, Spinner } from "react-bootstrap";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import pp from "../assets/pp.jpg";

const FormRegister = () => {
  const [username, setUsername] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [isLoading, setIsLoading] = useState(false)

  const [error, setError] = useState([]);
  const [alert, setAlert] = useState(false);

  const navigate = useNavigate();

  const saveForm = (e) => {
    e.preventDefault();
setIsLoading(true);
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
          navigate("/login");
          return response.json();
        } else {
          setIsLoading(false);
          response.json().then((data) => {
            const stringError = [];
            data.errors.forEach((e) => stringError.push(e + " "));
            console.log(data.errors);
            setError(stringError);
          });
          throw new Error("Error!");
        }
      })
      .catch(() => {
        setAlert(true);
        console.error;
      });
  };

  return (
    <>
      <Container fluid className="p-5 font-nunito">
        <Row className="text-center justify-content-center">
          <Col xs={8}>
            <img className="w-100" src={pp} />
          </Col>
        </Row>
        <Row className="justify-content-center border-row p-2 pb-4">
          <Col xs={10} className="text-center">
            <h1 className=" fw-bolder">Sign up!</h1>
          </Col>
          <Col xs={10} md={6} className="bg-white">
            <Form onSubmit={saveForm}>
              <Form.Group className="mb-3" controlId="username">
                <Form.Label className="fw-bolder text-pink">
                  Username
                </Form.Label>
                <Form.Control
                  type="text"
                  placeholder="Enter username"
                  value={username}
                  onChange={(e) => setUsername(e.target.value)}
                  required
                />
              </Form.Group>

              <Form.Group className="mb-3" controlId="email">
                <Form.Label className="fw-bolder">Email address</Form.Label>
                <Form.Control
                  type="email"
                  placeholder="Enter email"
                  value={email}
                  onChange={(e) => setEmail(e.target.value)}
                  required
                />
              </Form.Group>

              <Form.Group className="mb-3" controlId="formBasicPassword">
                <Form.Label className="fw-bolder text-pink">
                  Password
                </Form.Label>
                <Form.Control
                  type="password"
                  placeholder="Password"
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                  required
                />
              </Form.Group>

              <Form.Group className="mb-3" controlId="firstName">
                <Form.Label className="fw-bolder">First name</Form.Label>
                <Form.Control
                  type="text"
                  placeholder="Enter firstname"
                  value={firstName}
                  onChange={(e) => setFirstName(e.target.value)}
                  required
                />
              </Form.Group>

              <Form.Group className="mb-3" controlId="lastName">
                <Form.Label className="fw-bolder text-pink">
                  Last name
                </Form.Label>
                <Form.Control
                  type="text"
                  placeholder="Enter last name"
                  value={lastName}
                  onChange={(e) => setLastName(e.target.value)}
                  required
                />
              </Form.Group>
              <Form.Group className="mb-3" controlId="termsCheckbox">
                <Form.Check
                  type="checkbox"
                  required
                  label={ <p>Trust the <span className="text-pink fw-bold">Powerpuff</span> power!</p> }
                  className="pink-checkbox"
                />
              </Form.Group>

              <Button className="btn-pink d-block mx-auto" type="submit">
                Submit
              </Button>
            </Form>
            {alert && (
              <Alert variant="danger" className="mt-3">
               {error}
              </Alert>
            )}
            {isLoading && (<>
            <div className="text-center w-100 p-4">
              <Spinner animation="grow" variant="dark" /> <Spinner animation="grow" variant="dark" /> <Spinner animation="grow" variant="dark" /></div></>
            )}
          </Col>
        </Row>
      </Container>
    </>
  );
};
export default FormRegister;
