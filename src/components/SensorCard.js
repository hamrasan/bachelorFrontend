import {
  Card,
  CardGroup,
  ListGroupItem,
  ListGroup,
  Button,
} from "react-bootstrap";
import { Link } from "react-router-dom";


function SensorCard(props) {
  const styleImg = {
    maxHeight: "200px",
    maxWidth: "200px",
    margin: "10px 25px",
  };

  const styleCard = {
    maxWidth: "250px",
    minWidth: "250px",
    marginBottom: "25px",
    marginRight: "0px",
  };

  const styleHistoryCard = {
    minWidth: "370px",
    maxWidth: "370px",
    marginBottom: "25px",
    marginRight: "0px",
  };

  const makeFormattedDate = (oldDate) => {
    let date = new Date(oldDate);
    return date.toLocaleDateString() + " " + date.toLocaleTimeString();
  };

  const sensor = props.sensor;

  return (
    <CardGroup>
      <Card style={styleCard}>
        <Card.Img variant="top" src={props.picture} style={styleImg} />
        <Card.Body>
          <Card.Title>{props.name}</Card.Title>
          <Card.Text>{props.text}</Card.Text>
        </Card.Body>
        <Card.Footer>
          <small className="text-muted">
            {sensor != null ? makeFormattedDate(sensor.date) : ""}{" "}
          </small>
        </Card.Footer>
      </Card>

      <Card style={styleHistoryCard} className="text-center">
        <Card.Body>
          <ListGroup className="list-group-flush">
            {props.history.length > 0 ? (
              props.history.map((item) => (
                <ListGroupItem>
                  {" "}
                  <span className="text-secondary">
                    {makeFormattedDate(item.date)}{" "}
                  </span>{" "}
                  - <span className="font-weight-bold"> {item.value} </span>
                </ListGroupItem>
              ))
            ) : (
              <span>História nie je dostupná</span>
            )}
            {props.history.length > 4 ? (
              <Link to={"/history/" + props.pathName + "/" + props.gardenName }>
                <Button variant="outline-info" className="mt-4 ml-2">
                  Celá história
                </Button>
              </Link>
            ) : null}
          </ListGroup>
        </Card.Body>
      </Card>
    </CardGroup>
  );
}

export default SensorCard;
