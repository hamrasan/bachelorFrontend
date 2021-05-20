import { Form } from "react-bootstrap";

function GardenFilterCheckbox(props) {
  const item = props.item;
  const checked = props.checked;

  return (
          <Form.Check inline defaultChecked={checked} onClick={() => props.handleFilter(item.id)} label={item.name} type="checkbox" id={item.id} />
  );
}

export default GardenFilterCheckbox;
