import {
  FormControl,
  InputGroup,
  Button,
  Container,
  Image,
} from "react-bootstrap";

function SearchForm(props) {
  const searchImg = "../assets/magnifying-glass-1976105.svg";
  return (
    <div>
        <InputGroup>
          <InputGroup.Prepend>
            <InputGroup.Text>
              <Image src={searchImg} alt="search" width="20" />
            </InputGroup.Text>
          </InputGroup.Prepend>
          <FormControl
            aria-describedby="basic-addon1"
            value={props.value}
            onChange={(e) => {
              props.onChange(e.target.value);
            }}
          />
        </InputGroup>
    </div>
  );
}

export default SearchForm;
