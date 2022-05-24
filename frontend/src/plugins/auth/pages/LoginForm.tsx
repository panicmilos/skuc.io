import { AuthService } from "../services/AuthService";
import { AuthResponse } from "../models/AuthResponse";
import {  } from "../../auth-context/utils";
import { FC, useContext, useEffect, useState } from "react";
import * as Yup from "yup";
import { createUseStyles } from "react-jss";
import { useNavigate } from "react-router-dom";
import {
  Card,
  Col,
  Container,
  Form,
  Theme,
  Button,
  FormTextInput,
  Modal,
  AuthContext,
  NotificationService,
  extractErrorMessage,
  is2FactCode,
  setAxiosInterceptors,
} from '../imports';
import axios, { AxiosError } from "axios";
import { EMAIL_REGEX, LOWER_CASE_REGEX, NUMERIC_REGEX, SPECIAL_CHARACTERS_REGEX } from "../../../core/constants";

type Props = {};

const useStyles = createUseStyles((theme: Theme) => ({
  outerContainer: {
    height: "100%",
    width: "100%",
    overflow: "hidden",
    display: "flex",
    justifyContent: "center",
    alignItems: "center",
  },
  container: {
    maxHeight: "250px",
    maxWidth: "400px",
  },
  submitButton: {
    marginTop: "1em",
    maxWidth: "100px",
    marginLeft: "auto",
    marginRight: "auto"
  },
}));

export const LoginForm: FC<Props> = () => {
  const classes = useStyles();

  const [authService] = useState(new AuthService());
  const [notificationService] = useState(new NotificationService());
  const { isAuthenticated, setUser, setAuthenticated } = useContext(AuthContext);
  const nav = useNavigate();

  const configureInterceptors = () => {
    setAxiosInterceptors(axios, () => {
      setAuthenticated(false);
      nav('/');
      sessionStorage.removeItem('jwt-token');
    });
  }

  useEffect(() => {
    if(isAuthenticated) {
      nav("/users/certificates");
    }
  // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [isAuthenticated]);

  const [is2FactModalOpened, set2FactModalOpened] = useState(false);
  const [twoFactAuthorization, setTwoFactAuthorization] = useState('');

  const schema = Yup.object().shape({
    email: Yup.string()
      .required(() => ({ email: "Email must be provided." }))
      .matches(EMAIL_REGEX, () => ({ 
        email: "Must be a valid email." 
      })),
    password: Yup.string()
      .required(() => ({ password: "Password must be provided." }))
      .matches(LOWER_CASE_REGEX, () => ({
        password: "Password must contain lower letters.",
      }))
      .matches(NUMERIC_REGEX, () => ({
        password: "Password must contain numbers.",
      }))
      .matches(SPECIAL_CHARACTERS_REGEX, () => ({
        password: "Password must contain special characters.",
      })),
  });

  const schema2Fact = Yup.object().shape({
    pin: Yup.number().required(() => ({ pin: "Pin must be provided." })),
  });

  const handleAuthResponse = (response: AuthResponse) => {
    sessionStorage.setItem("jwt-token", response.token.value);
    setAuthenticated(true);
    setUser(response.user);

    configureInterceptors();
  };

  return (
    <Container className={classes.outerContainer}>
      <Container className={classes.container}>
        <Card title="Login">
          <Form
            schema={schema}
            onSubmit={(values) => {
              authService
                .login(values.email, values.password)
                .then((response) => {
                  if (!is2FactCode(response.token.value)) {
                    handleAuthResponse(response);
                  } else {
                    setTwoFactAuthorization(response.token.value);
                    set2FactModalOpened(true);
                  }
                })
                .catch((error: AxiosError) => notificationService.error(extractErrorMessage(error.response?.data)));
            }}
          >
            <Container>
              <Col>
                <FormTextInput label="Email" name="email" type="text" />
              </Col>
              <Col>
                <FormTextInput
                  label="Password"
                  name="password"
                  type="password"
                />
              </Col>
              <Button className={classes.submitButton} type="submit">
                Login
              </Button>
            </Container>
          </Form>
        </Card>
        <Modal
          open={is2FactModalOpened}
          onClose={() => set2FactModalOpened(false)}
          title="2 Fact Code"
        >
          <Form
            schema={schema2Fact}
            onSubmit={(values) => {
              authService
                .login2Step(twoFactAuthorization, values.pin)
                .then((response) => {
                  handleAuthResponse(response);
                  set2FactModalOpened(false);
                })
                .catch((error: AxiosError) => notificationService.error(extractErrorMessage(error.response?.data)));
            }}
          >
            <Container>
              <Col>
                <FormTextInput label="PIN" name="pin" type="text" />
              </Col>
              <Button className={classes.submitButton} type="submit">
                Submit
              </Button>
            </Container>
          </Form>
        </Modal>
      </Container>
    </Container>
  );
};
