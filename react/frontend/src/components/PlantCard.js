import { Card } from "react-bootstrap";

function PlantCard(props) {
  const styleImg = {
    height: "200px",
    width: "200px",
  };

  const plant = props.plant;

  return (
    <Card className="mt-2">
      <div className="d-flex justify-content-center bg-light">
        <Card.Img
          variant="top"
          src={"http://localhost:8080/gallery/" + plant.picture}
          style={styleImg}
        />
      </div>
      <Card.Body>
        <Card.Title>{plant.name}</Card.Title>
        <Card.Text>{plant.subcategoryDto.category.name}</Card.Text>
      </Card.Body>
    </Card>
  );
}

export default PlantCard;
