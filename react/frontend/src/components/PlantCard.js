import {
    Card
  } from "react-bootstrap";

function PlantCard(props) {
    const styleImg = {
        height:"200px",
        width: "200px",
    }

   const plant = props.plant;

  return (
      <Card>
        <Card.Img variant="top" src={plant.picture} style={styleImg} />
        <Card.Body>
          <Card.Title>{plant.name}</Card.Title>
          <Card.Text>
            {/* {plant.category.name} */}
          </Card.Text>
        </Card.Body>
        <Card.Footer>
          <small className="text-muted">Last updated 3 mins ago</small>
        </Card.Footer>
      </Card>
  );
}

export default PlantCard;