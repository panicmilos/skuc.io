import { User } from "../imports";

export type AuthResponse = {
  user: User;
  token: Token;
}

export type Token = {
  value: string
}