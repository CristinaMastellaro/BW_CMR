import Button from "react-bootstrap/Button";
import Card from "react-bootstrap/Card";
import { Container, Row, Col, Form } from "react-bootstrap";
import { useState, useEffect } from "react";

const Clienti = () => {
  const [clienti, setClienti] = useState([]);
  const [criterio, setCriterio] = useState("");

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

  const ordinaClienti = (e)=> {
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
  }
  useEffect(() => {
    getClienti();
  }, []);

  return (
    <>
      <Container>
        <Row><Col>
          <Form onSubmit={ordinaClienti}> 
            Ordina per:
      {['radio'].map((type) => (
        <div key={`inline-${type}`} className="mb-3">
          <Form.Check
            inline
            label="Fatturato annuale"
            name="group1"
            type={type}
            value="fatturatoAnnuale"
            onChange= {(e)=> setCriterio(e.target.value)}
            checked={criterio === "fatturatoAnnuale"}
            id={`inline-${type}-1`}
          />
          <Form.Check
            inline
            label="Provincia"
            name="group1"
            type={type}
            value="provincia"
             onChange= {(e)=> setCriterio(e.target.value)}
            checked={criterio === "provincia"}
            id={`inline-${type}-2`}
          />
          <Form.Check
            inline
            name="group1"
            label="Data Inserimento"
            type={type}
            value="dataInserimento"
             onChange= {(e)=> setCriterio(e.target.value)}
            checked={criterio === "dataInserimento"}
            id={`inline-${type}-3`}
          />
          <Form.Check
            inline
             name="group1"
            label="Data ultimo contatto"
            type={type}
            value="dataUltimoContatto"
             onChange= {(e)=> setCriterio(e.target.value)}
            checked={criterio === "dataUltimoContatto"}
            id={`inline-${type}-4`}
          />
          <Form.Check
            inline
             name="group1"
            label="Nome contatto"
            type={type}
            value="nomeContatto"
             onChange= {(e)=> setCriterio(e.target.value)}
            checked={criterio === "nomeContatto"}
            id={`inline-${type}-5`}
          />
        </div>
      ))}
      <Button type="submit">Ordina</Button>
    </Form>
        </Col></Row>
        <Row className="justify-content-center mt-5">
          {clienti.map((cliente) => (
            <Col key={cliente.id} xs={5}>
              <Card>
                <Card.Img variant="top" src={cliente.logoAziendale} />
                <Card.Body>
                  <Card.Title>{cliente.nomeContatto}</Card.Title>
                  <Card.Text>{cliente.ragioneSociale}</Card.Text>
                  <Card.Text>
                    Email:{cliente.email}, Fatturato Annuale:{" "}
                    {cliente.fatturatoAnnuale}
                    {cliente.partitaIva}
                    {cliente.dataInserimento}
                  </Card.Text>
                </Card.Body>
              </Card>
            </Col>
          ))}
        </Row>
      </Container>
    </>
  );
};

export default Clienti;
