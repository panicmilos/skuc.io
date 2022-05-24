import { User } from "../imports";

export type TwoFactResponse = {
  id: string;
  userId: User;
  userKey: User;
  active: boolean;
}