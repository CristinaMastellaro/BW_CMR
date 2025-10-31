import Button from "react-bootstrap/Button";
import Card from "react-bootstrap/Card";
import { Container, Row, Col, Form } from "react-bootstrap";
import { useState, useEffect } from "react";
import aa from "../assets/all.jpg";

const Clienti = () => {
  const [clienti, setClienti] = useState([]);
  const [criterio, setCriterio] = useState("");
  const [nomeContatto, setNomeContatto] = useState("");
  const [fatturato, setFatturato] = useState();
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
                className="d-flex flex-column flex-md-row justify-content-between align-items-center">
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
    size="sm">
    Filter
  </Button>
    </Col>
  </Row> 
</Form>
            </Col>
          </Row>
          <Row className="justify-content-center mt-5">
            {clienti.map((cliente) => (
              <Col key={cliente.id} xs={5}>
                <Card>
                  <Card.Img variant="top" src={cliente.logoAziendale} />
                  <Card.Body>
                    <Card.Title>{cliente.nomeContatto}</Card.Title>
                    <Card.Text>{cliente.ragioneSociale}</Card.Text>
                    <Card.Text>
                      Email:{cliente.email}, Annual turnover:{" "}
                      {cliente.fatturatoAnnuale}
                      {cliente.partitaIva}
                      {cliente.dataInserimento}
                    </Card.Text>
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
