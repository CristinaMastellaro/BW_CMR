import Button from "react-bootstrap/Button";
import Card from "react-bootstrap/Card";
import { Container, Row, Col, Form } from "react-bootstrap";
import { useState, useEffect } from "react";
import aa from "../assets/all.jpg";

const Clienti = () => {
  const [clienti, setClienti] = useState([]);
  const [criterio, setCriterio] = useState("");
  const [nomeContatto, setNomeContatto] = useState("");
  const [fatturato, setFatturato] = useState(null);
  const [dataInserimento, setDataInserimento] = useState("");
  const [dataUltimoContatto, setDataUltimoContatto] = useState("");
  const [provincia, setProvincia] = useState("");

  const token = localStorage.getItem("token");

  const getClienti = () => {
    fetch("http://localhost:3001/clienti", {
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
    })
      .then((response) => {
        if (response.ok) {
          return response.json();
        } else {
          response.json().then((data) => {
            console.log(data);
          });
          throw new Error("Error!");
        }
      })
      .then((data) => {
        console.log("data", data);
        setClienti(data.content || []);
      })
      .catch((err) => {
        console.error("Errore:", err);
      });
  };

  const ordinaClienti = (e) => {
    e.preventDefault();
    console.log(criterio);
    fetch(`http://localhost:3001/clienti/ordina?criterio=${criterio}`, {
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
    })
      .then((response) => {
        if (response.ok) {
          return response.json();
        } else {
          response.json().then((data) => {
            console.log(data);
          });
          throw new Error("Error!");
        }
      })
      .then((data) => {
        console.log("data", data);
        setClienti(data.content || []);
      })
      .catch((err) => {
        console.error("Errore:", err);
      });
  };

  const cercaClienti = (e) => {
    e.preventDefault();
    let filtro = "?";

    if (!(nomeContatto === null || nomeContatto === "")) {
      filtro += `nomeContatto=${nomeContatto}`;
    }

    if (!(fatturato === null || fatturato === "")) {
      if (!filtro.endsWith(`?`)) {
        filtro += "&";
      }
      filtro += `fatturato=${fatturato}`;
    }
    if (!(dataInserimento === null || dataInserimento === "")) {
      if (!filtro.endsWith(`?`)) {
        filtro += "&";
      }
      filtro += `dataInserimento=${dataInserimento}`;
    }
    if (!(dataUltimoContatto === null || dataUltimoContatto === "")) {
      if (!filtro.endsWith(`?`)) {
        filtro += "&";
      }
      filtro += `dataUltimoContatto=${dataUltimoContatto}`;
    }
    if (!(provincia === null || provincia === "")) {
      if (!filtro.endsWith(`?`)) {
        filtro += "&";
      }
      filtro += `provincia=${provincia}`;
    }

    console.log(filtro);
    fetch(`http://localhost:3001/clienti/cerca${filtro}`, {
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
    })
      .then((response) => {
        if (response.ok) {
          return response.json();
        } else {
          response.json().then((data) => {
            console.log(data);
          });
          throw new Error("Error!");
        }
      })
      .then((data) => {
        console.log("data", data);
        setClienti(data.content || []);
      })
      .catch((err) => {
        console.error("Errore:", err);
      });
  };

  useEffect(() => {
    getClienti();
  }, []);

  return (
    <>
      <Container fluid className="p-5 font-nunito">
        <Row className="text-center justify-content-center">
          <Col xs={8}>
            <img className="w-100" src={aa} />
          </Col>
        </Row>
        <div className="justify-content-center border-row3 p-2">
          <Row className="bg-white m-3 d-flex justify-content-center">
            <Col xs={10}>
              <strong className="d-block mb-2 fs-5">Order by</strong>
              <Form
                onSubmit={ordinaClienti}
                className="d-flex flex-column flex-md-row justify-content-between align-items-center"
              >
                {["radio"].map((type) => (
                  <div key={`inline-${type}`}>
                    <Form.Check
                      inline
                      label="Annual turnover"
                      name="group1"
                      type={type}
                      value="fatturatoAnnuale"
                      onChange={(e) => setCriterio(e.target.value)}
                      checked={criterio === "fatturatoAnnuale"}
                      id={`inline-${type}-1`}
                      className="blue-checkbox"
                    />
                    <Form.Check
                      inline
                      label="District"
                      name="group1"
                      type={type}
                      value="provincia"
                      onChange={(e) => setCriterio(e.target.value)}
                      checked={criterio === "provincia"}
                      id={`inline-${type}-2`}
                      className="blue-checkbox"
                    />
                    <Form.Check
                      inline
                      name="group1"
                      label="Date of entry"
                      type={type}
                      value="dataInserimento"
                      onChange={(e) => setCriterio(e.target.value)}
                      checked={criterio === "dataInserimento"}
                      id={`inline-${type}-3`}
                      className="blue-checkbox"
                    />
                    <Form.Check
                      inline
                      name="group1"
                      label="Date of last contact"
                      type={type}
                      value="dataUltimoContatto"
                      onChange={(e) => setCriterio(e.target.value)}
                      checked={criterio === "dataUltimoContatto"}
                      id={`inline-${type}-4`}
                      className="blue-checkbox"
                    />
                    <Form.Check
                      inline
                      name="group1"
                      label="Contact name"
                      type={type}
                      value="nomeContatto"
                      onChange={(e) => setCriterio(e.target.value)}
                      checked={criterio === "nomeContatto"}
                      id={`inline-${type}-5`}
                      className="blue-checkbox"
                    />
                  </div>
                ))}
                <Button type="submit" className="btn-blue d-block" size="sm">
                  Order
                </Button>
              </Form>
            </Col>
            <hr className="mt-4" />
            <Col xs={10} className="mb-2">
              <Form onSubmit={cercaClienti}>
                <Row>
                  <Col md={6}>
                    <Form.Group className="mb-2" controlId="nomeContatto">
                      <Form.Label className="fw-bolder text-blue">
                        Contact name
                      </Form.Label>
                      <Form.Control
                        type="text"
                        placeholder="Enter contact name"
                        value={nomeContatto}
                        onChange={(e) => setNomeContatto(e.target.value)}
                        size="sm"
                      />
                    </Form.Group>

                    <Form.Group className="mb-2" controlId="dataInserimento">
                      <Form.Label className="fw-bolder">
                        Date of entry
                      </Form.Label>
                      <Form.Control
                        type="text"
                        placeholder="Enter date of entry"
                        value={dataInserimento}
                        onChange={(e) => setDataInserimento(e.target.value)}
                        size="sm"
                      />
                    </Form.Group>

                    <Form.Group className="mb-2" controlId="provincia">
                      <Form.Label className="fw-bolder text-blue ">
                        District of legal address
                      </Form.Label>
                      <Form.Control
                        type="text"
                        placeholder="Enter district of legal address"
                        value={provincia}
                        onChange={(e) => setProvincia(e.target.value)}
                        size="sm"
                      />
                    </Form.Group>
                  </Col>

                  <Col md={6}>
                    <Form.Group className="mb-2" controlId="fatturato">
                      <Form.Label className="fw-bolder ">
                        Annual turnover
                      </Form.Label>
                      <Form.Control
                        type="text"
                        placeholder="Enter annual turnover"
                        value={fatturato}
                        onChange={(e) => setFatturato(e.target.value)}
                        size="sm"
                      />
                    </Form.Group>

                    <Form.Group className="mb-2" controlId="dataUltimoContatto">
                      <Form.Label className="fw-bolder text-blue">
                        Date of last contact
                      </Form.Label>
                      <Form.Control
                        type="text"
                        placeholder="Enter date of last contact"
                        value={dataUltimoContatto}
                        onChange={(e) => setDataUltimoContatto(e.target.value)}
                        size="sm"
                      />
                    </Form.Group>
                    <Button
                      type="submit"
                      className="btn-blue d-block ms-auto mt-5"
                      size="sm"
                    >
                      Filter
                    </Button>
                  </Col>
                </Row>
              </Form>
            </Col>
          </Row>
          <Row className="justify-content-center mt-3 g-3 px-5 py-2">
            <h1 className="text-center fw-bold">Clients</h1>
            {clienti.map((cliente) => (
              <Col key={cliente.id} xs={11} md={6} lg={4}>
                <Card
                  className="py-2"
                  style={{
                    "border-radius": "40px",
                    border: "2px solid #1424ffaf",
                  }}
                >
                  <Card.Body className="d-flex flex-column align-items-center">
                    <div className="d-flex justify-content-start mb-4 mt-2">
                      <Card.Img
                        className="me-3 rounded-circle"
                        style={{
                          width: "5rem",
                          height: "5rem",
                          border: "2px solid #1424ffaf",
                        }}
                        src={cliente.logoAziendale}
                      />
                      <div className="d-flex flex-column  justify-content-center">
                        <Card.Title className="fs-3 fw-bold mb-0 mt-2 text-blue">
                          {cliente.nomeContatto}
                        </Card.Title>
                        <Card.Text className="mb-2 fs-5">
                          {cliente.ragioneSociale}
                        </Card.Text>
                      </div>
                    </div>
                    <div className="border-top pt-4 ">
                      <Card.Text className="mb-0">
                        Email: {cliente.email}
                      </Card.Text>
                      <Card.Text className="mb-0">
                        Annual Turnover: {cliente.fatturatoAnnuale}
                      </Card.Text>
                      <Card.Text className="mb-0">
                        VAT Number: {cliente.partitaIva}
                      </Card.Text>
                      <Card.Text className="mb-0">
                        Entry Date: {cliente.dataInserimento}
                      </Card.Text>
                      <Card.Text className="mb-0">
                        Last Contact date: {cliente.dataUltimoContatto}
                      </Card.Text>
                      <Card.Text className="mb-0">PEC: {cliente.pec}</Card.Text>
                      <Card.Text className="mb-0">
                        Phone Number: {cliente.telefono}
                      </Card.Text>
                      <p className="mt-3 fs-5 mb-0 fw-bolder text-blue">
                        Contact
                      </p>
                      <Card.Text className="mb-0">
                        Email: {cliente.emailContatto}
                      </Card.Text>
                      <Card.Text className="mb-0">
                        Name: {cliente.nomeContatto}
                      </Card.Text>
                      <Card.Text className="mb-0">
                        Surname: {cliente.cognomeContatto}
                      </Card.Text>
                      <Card.Text className="mb-0">
                        Phone Number: {cliente.telefonoContatto}
                      </Card.Text>
                    </div>
                  </Card.Body>
                </Card>
              </Col>
            ))}
          </Row>
        </div>
      </Container>
    </>
  );
};

export default Clienti;
