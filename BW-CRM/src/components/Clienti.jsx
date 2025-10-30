import Button from "react-bootstrap/Button";
import Card from "react-bootstrap/Card";
import { Container, Row, Col } from "react-bootstrap";
import { useState, useEffect } from "react";

const Clienti = () => {
  const [clienti, setClienti] = useState([]);

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
  useEffect(() => {
    getClienti();
  }, []);

  return (
    <>
      <Container>
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
