import ProfileStyle from "../components/ProfileStyle";

import { Container} from "react-bootstrap";

function MyProfile(){
    const picture = "assets/profilovka.jpg";

    return(
        <div>
            <ProfileStyle picture={picture} />
        </div>
    )
}

export default MyProfile;