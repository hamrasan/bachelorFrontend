import { Card, CardColumns, CardGroup, Button } from "react-bootstrap";
import { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import MyVerticallyCenteredModal from "./MyVerticallyCenteredModal";

function ValveCard(props) {
  
  const styleImg = {
    maxHeight: "150px",
    maxWidth: "150px",
  };

  const sensor = props.sensor;
  const [modalShowGardens, setModalShowGardens] = useState(false);
  const [modalShowValving, setModalShowValving] = useState(false);

  const handleSubmitGardens = (gardens) =>{
      console.log(gardens);
      props.updateGardens(props.valve.id, gardens);
      setModalShowGardens(false);
  }

  const handleSubmitImmediately = (length) =>{
    console.log(length);
    props.valvingImmediately(props.valve.name, length);
    setModalShowValving(false);
}
    
  useEffect(() => {

  }, [props.valve.gardens])


  return (
    <CardGroup>
      <Card className="mb-4 mr-0">
        <Card.Img variant="top" src={props.valve.picture} style={styleImg} />
        <Card.Body>
          <Card.Title>Polievač {props.number}</Card.Title>
          <Card.Text>
            V záhrade:
            <Card.Text>{props.valve.gardens.map((garden, index) => <span>{garden.name}{index<props.valve.gardens.length-1 ? ", ": " "} </span>)}
            </Card.Text>
          </Card.Text>
        </Card.Body>
      </Card>
      <Card className="mb-4 mr-0">
        <Card.Body>
            <Button variant="info" className="mt-2 mr-2"
             onClick={() => setModalShowValving(true)}
            >
              Zalej ihneď!
            </Button>
            <MyVerticallyCenteredModal
            show={modalShowValving}
            onHide={() => setModalShowValving(false)}
            title={"Zalej ihneď!"}
            bodyTitle = {"Zadaj dĺžku zalievania (v min):"}
            bodyText={"immediately"}
            onSubmit={handleSubmitImmediately}
          />
          <Link to={"/valving/schedule/" + props.valve.name}>
            <Button variant="info" className="mt-2 mr-2">
              Pravidelné polievanie
            </Button>
          </Link>
          <Button
            variant="info"
            className="mt-2 mr-2"
            onClick={() => setModalShowGardens(true)}
          >
            Pridať/odobrať záhradu
          </Button>
          <MyVerticallyCenteredModal
            show={modalShowGardens}
            onHide={() => setModalShowGardens(false)}
            title={"Pridať/odobrať záhradu"}
            bodyTitle = {"Vyber z možností:"}
            bodyText={"gardens"}
            gardens={props.gardens}
            onSubmit={handleSubmitGardens}
          />
        </Card.Body>
      </Card>
    </CardGroup>
  );
}

export default ValveCard;
