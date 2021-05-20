import ProfileStyle from "../components/ProfileStyle";
import contextValue from "../appContext";
import { useContext } from "react";

function MyProfile() {
  let context = useContext(contextValue);
  console.log(context.user);
  const picture = context.user.gender=="WOMAN" ? "assets/gardenwoman.png" : "assets/gardener.png";

  return (
    <div>
      <ProfileStyle picture={picture} user={context.user} />
    </div>
  );
}

export default MyProfile;
