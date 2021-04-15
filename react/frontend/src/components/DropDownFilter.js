import { Container, Form, Row } from "react-bootstrap";
import GardenFilterCheckbox from "../components/GardenFilterCheckbox";

function DropdownFilter(props) {
  const categories = props.categories;
  const categoryFilter = props.categoryFilter;
  const handleFilter = props.handleFilter;

  let count = 0;
  const mappedCategories = categories.map((category) => {
    count++;
    return <GardenFilterCheckbox key={count} item={category} checked={categoryFilter.includes(category.id)} handleFilter={handleFilter}/>;
  });

  return (
    <Container className="pt-3">
      Filtruj podľa:
      <Form>{mappedCategories}
      </Form>
    </Container>
  );
}

export default DropdownFilter;
