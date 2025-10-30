import Button from "react-bootstrap/Button";
import Card from "react-bootstrap/Card";
import { Container, Row, Col } from "react-bootstrap";
import { useState, useEffect } from "react";

const Clienti = () => {
  const [Clienti, setClienti] = useState([]);

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
        setClienti(data || []);
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
          <Card style={{ width: "18rem" }}>
            <Card.Img variant="top" src="holder.js/100px180" />
            <Card.Body>
              <Card.Title>Card Title</Card.Title>
              <Card.Text>
                Some quick example text to build on the card title and make up
                the bulk of the card's content.
              </Card.Text>
              <Button variant="primary">Go somewhere</Button>
            </Card.Body>
          </Card>
        </Row>
      </Container>
    </>
  );
};

export default Clienti;
