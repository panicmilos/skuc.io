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

  // const { user } = useContext(AuthContext);

  // TODO: REMOVE THIS
  const [userService] = useUsersService('4d1297cb-9f32-46d6-84dc-4ebcd847c830');
  const [user, setUser] = useState<User|undefined>();
  useEffect(() => {
    userService.fetch('4e36c06c-1807-4601-a3a9-320b06196738')
    .then(setUser);
  }, []);

  console.log(user);

  return (
    <>
      <Container>
        <ProfileForm user={user as User}></ProfileForm>
        <PasswordForm user={user}></PasswordForm>
      </Container>
    </>
  );
};
