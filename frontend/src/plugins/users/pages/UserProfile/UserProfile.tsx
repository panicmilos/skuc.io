import { FC, useContext } from "react";
import {
  Container,
  AuthContext,
} from '../../imports';
import { User } from "../../models/User";
import { PasswordForm } from "./PasswordForm";
import { ProfileForm } from "./ProfileForm";

export const UserProfile: FC = () => {

  const { user } = useContext(AuthContext);

  return (
    <>
      <Container>
        <ProfileForm user={user as User}></ProfileForm>
        <PasswordForm user={user}></PasswordForm>
      </Container>
    </>
  );
};
