import { FormControl, InputGroup, Button, Container , Image} from "react-bootstrap";

function SearchForm() {
    const searchImg="../assets/magnifying-glass-1976105.svg";
  return (
    <div>
      <Container className= "pt-3">
        <InputGroup className="mb-3">
          <InputGroup.Prepend>
            <Button variant="outline-secondary">  <Image src={searchImg} alt="search" width="20"/></Button>
          </InputGroup.Prepend>
          <FormControl aria-describedby="basic-addon1" />
        </InputGroup>
      </Container>
    </div>
  );
}

export default SearchForm;
