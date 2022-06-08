import { FC, useContext, useEffect, useState } from "react";
import {
  Container,
  AuthContext,
} from '../../imports';
import { User } from "../../models/User";
import { useUsersService } from "../../services";
import { PasswordForm } from "./PasswordForm";
import { ProfileForm } from "./ProfileForm";

export const UserProfile: FC = () => {

  const { user: loggedUser } = useContext(AuthContext);
  const [userService] = useUsersService(loggedUser?.groupId ?? '');

  const [user, setUser] = useState<User|undefined>();
  useEffect(() => {
    userService.fetch(loggedUser?.id ?? '')
    .then(setUser);
  }, []);

  return (
    <>
      <Container>
        <ProfileForm user={user as User}></ProfileForm>
        <PasswordForm user={user}></PasswordForm>
      </Container>
    </>
  );
};
