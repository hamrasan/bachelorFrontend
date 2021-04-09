import ProfileStyle from "../components/ProfileStyle";
import contextValue from "../appContext";
import { useContext } from "react";

import { Container, Row, Form, Col, Button } from "react-bootstrap";

function MyProfile() {
  let context = useContext(contextValue);
  const picture = "assets/profilovka.jpg";

  return (
    <div>
      <ProfileStyle picture={picture} user={context.user} />
    </div>
  );
}

export default MyProfile;
