export { USERS_SERVICE_URL } from "../../urls";

export type { FeaturePlugin, Theme } from "../../core";
export type User = {
  id: string;
  name: string;
  lastName: string;
  email: string;
  phoneNumber: string;
  roles: Role[] | undefined;
  groupId: string;
};
export type Role = {
  id: string;
  name: string;
  permissions: string[];
};

export {
  Card,
  Col,
  Container,
  Form,
  Button,
  FormTextInput,
  Modal,
  NotificationService,
  extractErrorMessage,
  Service
} from "../../core";

export { AuthContext, is2FactCode, setAxiosInterceptors } from '../auth-context';
